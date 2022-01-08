package com.henry;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootEsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringBootEsApplicationTests {

	@Autowired
	RestHighLevelClient client;

	/**
	 * 删除索引库
	 */
	@Test
	public void testDelIndex() throws IOException {
		// 操作索引的对象
		IndicesClient indices = client.indices();
		// 删除索引的请求
		DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("ysx_course");
		// 删除索引
		DeleteIndexResponse response = indices.delete(deleteIndexRequest);
		// 得到响应
		boolean b = response.isAcknowledged();
		System.out.println(b);
	}

	/**
	 * 创建索引, 步骤和删除类似，
	 * 需要注意的是删除的时候需要指定 ES 库分片的数量和副本的数量，并且在创建索引的时候可以将映射一起指定了
	 * @throws IOException
	 */
	public void testAddIndex() throws IOException {
		// 操作索引的对象
		IndicesClient indices = client.indices();
		// 创建索引的请求
		CreateIndexRequest request = new CreateIndexRequest("ysx_course");
		request.settings(Settings.builder().put("number_of_shards", "1").put("number_of_replicas", "0"));
		// 创建映射
		request.mapping("doc", "{\n" +
				"                \"properties\": {\n" +
				"                    \"description\": {\n" +
				"                        \"type\": \"text\",\n" +
				"                        \"analyzer\": \"ik_max_word\",\n" +
				"                        \"search_analyzer\": \"ik_smart\"\n" +
				"                    },\n" +
				"                    \"name\": {\n" +
				"                        \"type\": \"text\",\n" +
				"                        \"analyzer\": \"ik_max_word\",\n" +
				"                        \"search_analyzer\": \"ik_smart\"\n" +
				"                    },\n" +
				"\"pic\":{                    \n" +
				"\"type\":\"text\",                        \n" +
				"\"index\":false                        \n" +
				"},                    \n" +
				"                    \"price\": {\n" +
				"                        \"type\": \"float\"\n" +
				"                    },\n" +
				"                    \"studymodel\": {\n" +
				"                        \"type\": \"keyword\"\n" +
				"                    },\n" +
				"                    \"timestamp\": {\n" +
				"                        \"type\": \"date\",\n" +
				"                        \"format\": \"yyyy-MM‐dd HH:mm:ss||yyyy‐MM‐dd||epoch_millis\"\n" +
				"                    }\n" +
				"                }\n" +
				"            }", XContentType.JSON);


		// 执行创建操作
		CreateIndexResponse response = indices.create(request);
		// 得到响应
		boolean b = response.isAcknowledged();
		System.out.println(b);
	}

	// 搜索全部记录
	@Test
	public void testSearchAll() throws IOException, ParseException {
		// 搜索请求对象
		SearchRequest searchRequest = new SearchRequest("ysx_course");
		// 指定类型
		searchRequest.types("_doc");
		// 搜索源构建对象
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 搜索方式
		// matchAllQuery搜索全部
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		// 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
		searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
		// 向搜索请求对象中设置搜索源
		searchRequest.source(searchSourceBuilder);
		// 执行搜索,向ES发起http请求
		SearchResponse searchResponse = client.search(searchRequest);
		// 搜索结果
		SearchHits hits = searchResponse.getHits();
		// 匹配到的总记录数
		long totalHits = hits.getTotalHits();
		// 得到匹配度高的文档
		SearchHit[] searchHits = hits.getHits();
		// 日期格式化对象
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(SearchHit hit:searchHits){
			// 文档的主键
			String id = hit.getId();
			// 源文档内容
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			String name = (String) sourceAsMap.get("name");
			// 由于前边设置了源文档字段过虑，这时description是取不到的
			String description = (String) sourceAsMap.get("description");
			// 学习模式
			String studymodel = (String) sourceAsMap.get("studymodel");
			// 价格
			Double price = (Double) sourceAsMap.get("price");
			// 日期
			Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp"));
			System.out.println(name);
			System.out.println(studymodel);
			System.out.println("你看不见我，看不见我~" + description);
			System.out.println(price);
		}

	}

	// 分页查询全部
	@Test
	public void testListPage() throws IOException, ParseException{
		// 搜索源构建对象
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		int page = 1; // 页码
		int size = 2; // 每页显示的条数
		int index = (page - 1) * size;
		searchSourceBuilder.from(index);
		searchSourceBuilder.size(1);
		// 2.创建并设置SearchRequest对象
		SearchRequest searchRequest = new SearchRequest();
		// 设置request要搜索的索引和类型
		searchRequest.indices("ysx_course").types("_doc");
		// 设置SearchSourceBuilder查询属性
		// 搜索方式
		// matchAllQuery搜索全部
		//searchRequest.source(searchSourceBuilder.query(QueryBuilders.matchAllQuery()));
		// termQuery 精确查询 根据条件查询
		//searchRequest.source(searchSourceBuilder.query(QueryBuilders.termQuery("studymodel", "202112")));
		// 根据id查询
		//searchRequest.source(searchSourceBuilder.query(QueryBuilders.termQuery("_id", "1")));
		// matchQuery全文检索
		searchRequest.source(searchSourceBuilder.query(QueryBuilders.matchQuery("description", "Spring开发").minimumShouldMatch("70%")));
		// 3.查询
		SearchResponse searchResponse = client.search(searchRequest);
		System.out.println(searchResponse.toString());
	}


	// 布尔查询
	@Test
	public void BoolSearch() throws IOException, ParseException{
		// 搜索源构建对象
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 搜索方式
		// 首先构造多关键字查询条件
		MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("Spring开发", "name", "description").field("name", 10);
		// 然后构造精确匹配查询条件
		TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("studymodel", "202112");
		// 组合两个条件，组合方式为 must 全满足
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(matchQueryBuilder);
		boolQueryBuilder.must(termQueryBuilder);
		// 将查询条件封装给查询对象
		searchSourceBuilder.query(boolQueryBuilder);
		// 2.创建并设置SearchRequest对象
		SearchRequest searchRequest = new SearchRequest();
		// 设置request要搜索的索引和类型
		searchRequest.indices("ysx_course").types("_doc");

		searchRequest.source(searchSourceBuilder);
		// 3.查询
		SearchResponse searchResponse = client.search(searchRequest);
		System.out.println(searchResponse.toString());
	}


	// 过滤器查询
	@Test
	public void filterSearch() throws IOException, ParseException{
		// 搜索源构建对象
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 搜索方式
		// 首先构造多关键字查询条件
		MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("Spring框架", "name", "description").field("name", 10);
		// 添加条件到布尔查询
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(matchQueryBuilder);
		// 通过布尔查询来构造过滤查询
		boolQueryBuilder.filter(QueryBuilders.termQuery("studymodel", "202112"));
		boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(60).lte(100));
		// 将查询条件封装给查询对象
		searchSourceBuilder.query(boolQueryBuilder);
		// 2.创建并设置SearchRequest对象
		SearchRequest searchRequest = new SearchRequest();
		// 设置request要搜索的索引和类型
		searchRequest.indices("ysx_course").types("_doc");

		searchRequest.source(searchSourceBuilder);
		// 3.查询
		SearchResponse searchResponse = client.search(searchRequest);
		System.out.println(searchResponse.toString());
	}

	// 排序查询
	@Test
	public void testSort() throws IOException, ParseException {
		// 搜索请求对象
		SearchRequest searchRequest = new SearchRequest("ysx_course");
		// 指定类型
		searchRequest.types("_doc");
		// 搜索源构建对象
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 搜索方式
		// 添加条件到布尔查询
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		// 通过布尔查询来构造过滤查询
		boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(0).lte(100));
		// 将查询条件封装给查询对象
		searchSourceBuilder.query(boolQueryBuilder);
		// 向搜索请求对象中设置搜索源
		searchRequest.source(searchSourceBuilder);

		// 设置排序规则
		searchSourceBuilder.sort("studymodel", SortOrder.DESC); // 第一排序规则
		searchSourceBuilder.sort("price", SortOrder.ASC); // 第二排序规则

		// 执行搜索,向ES发起http请求
		SearchResponse searchResponse = client.search(searchRequest);
		// 搜索结果
		SearchHits hits = searchResponse.getHits();
		// 匹配到的总记录数
		long totalHits = hits.getTotalHits();
		// 得到匹配度高的文档
		SearchHit[] searchHits = hits.getHits();
		// 日期格式化对象
		soutData(searchHits);
	}

	// 高亮查询
	@Test
	public void testHighLight() throws IOException, ParseException {
		// 搜索请求对象
		SearchRequest searchRequest = new SearchRequest("ysx_course");
		// 指定类型
		searchRequest.types("_doc");
		// 搜索源构建对象
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		// 搜索方式
		// 首先构造多关键字查询条件
		MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery("Spring开发", "name", "description").field("name", 10);
		// 添加条件到布尔查询
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(matchQueryBuilder);
		// 通过布尔查询来构造过滤查询
		boolQueryBuilder.filter(QueryBuilders.rangeQuery("price").gte(60).lte(100));
		// 将查询条件封装给查询对象
		searchSourceBuilder.query(boolQueryBuilder);
		// ***********************

		// 高亮查询
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.preTags("<em>"); // 高亮前缀
		highlightBuilder.postTags("</em>"); // 高亮后缀
		highlightBuilder.fields().add(new HighlightBuilder.Field("name")); // 高亮字段
		// 添加高亮查询条件到搜索源
		searchSourceBuilder.highlighter(highlightBuilder);

		// ***********************

		// 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
		searchSourceBuilder.fetchSource(new String[]{"name","studymodel","price","timestamp"},new String[]{});
		// 向搜索请求对象中设置搜索源
		searchRequest.source(searchSourceBuilder);
		// 执行搜索,向ES发起http请求
		SearchResponse searchResponse = client.search(searchRequest);
		// 搜索结果
		SearchHits hits = searchResponse.getHits();
		// 匹配到的总记录数
		long totalHits = hits.getTotalHits();
		// 得到匹配度高的文档
		SearchHit[] searchHits = hits.getHits();
		// 日期格式化对象
		soutData(searchHits);
	}

	/**
	 * 根据查询结果的数据结构来获取高亮的数据，替换原有的数据
	 * @param searchHits
	 * @throws ParseException
	 */
	private void soutData(SearchHit[] searchHits) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (SearchHit hit : searchHits) {
			// 文档的主键
			String id = hit.getId();
			// 源文档内容
			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
			String name = (String) sourceAsMap.get("name");

			// 获取高亮查询的内容。如果存在，则替换原来的name
			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
			if( highlightFields != null ){
				HighlightField nameField = highlightFields.get("name");
				if(nameField!=null){
					Text[] fragments = nameField.getFragments();
					StringBuffer stringBuffer = new StringBuffer();
					for (Text str : fragments) {
						stringBuffer.append(str.string());
					}
					name = stringBuffer.toString();
				}
			}

			// 由于前边设置了源文档字段过虑，这时description是取不到的
			String description = (String) sourceAsMap.get("description");
			// 学习模式
			String studymodel = (String) sourceAsMap.get("studymodel");
			// 价格
			Double price = (Double) sourceAsMap.get("price");
			// 日期
			Date timestamp = dateFormat.parse((String) sourceAsMap.get("timestamp"));
			System.out.println(name);
			System.out.println(id);
			System.out.println(studymodel);
			System.out.println("你看不见我，看不见我~" + description);
			System.out.println(price);
		}
	}




}
