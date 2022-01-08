1、运行项目 
2、访问swagger地址  http://localhost:8088/swagger-ui/
导出：
 点击 Download file

导入: http://localhost:8088/easyExcel/importMemberList
选择上面导出的文件导出即可

复杂导出： http://localhost:8088/easyExcel/exportOrderList
设置二级表头
@ExcelProperty(value = {"商品信息", "商品编码"})

实现过程

首先我们得把原来嵌套的订单商品信息给平铺了，创建一个专门的导出对象OrderData，包含订单和商品信息，二级表头可以通过设置@ExcelProperty的value为数组来实现；
/**
 * 订单导出
 * Created by macro on 2021/12/20.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderData {
    @ExcelProperty(value = "订单ID")
    @ColumnWidth(10)
    @CustomMerge(needMerge = true, isPk = true)
    private String id;
    @ExcelProperty(value = "订单编码")
    @ColumnWidth(20)
    @CustomMerge(needMerge = true)
    private String orderSn;
    @ExcelProperty(value = "创建时间")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd")
    @CustomMerge(needMerge = true)
    private Date createTime;
    @ExcelProperty(value = "收货地址")
    @CustomMerge(needMerge = true)
    @ColumnWidth(20)
    private String receiverAddress;
    @ExcelProperty(value = {"商品信息", "商品编码"})
    @ColumnWidth(20)
    private String productSn;
    @ExcelProperty(value = {"商品信息", "商品名称"})
    @ColumnWidth(20)
    private String name;
    @ExcelProperty(value = {"商品信息", "商品标题"})
    @ColumnWidth(30)
    private String subTitle;
    @ExcelProperty(value = {"商品信息", "品牌名称"})
    @ColumnWidth(20)
    private String brandName;
    @ExcelProperty(value = {"商品信息", "商品价格"})
    @ColumnWidth(20)
    private BigDecimal price;
    @ExcelProperty(value = {"商品信息", "商品数量"})
    @ColumnWidth(20)
    private Integer count;
}
然后将原来嵌套的Order对象列表转换为OrderData对象列表；
/**
 * EasyExcel导入导出测试Controller
 * Created by macro on 2021/12/20.
 */
@Controller
@Api(tags = "EasyExcelController", description = "EasyExcel导入导出测试")
@RequestMapping("/easyExcel")
public class EasyExcelController {
    private List<OrderData> convert(List<Order> orderList) {
        List<OrderData> result = new ArrayList<>();
        for (Order order : orderList) {
            List<Product> productList = order.getProductList();
            for (Product product : productList) {
                OrderData orderData = new OrderData();
                BeanUtil.copyProperties(product,orderData);
                BeanUtil.copyProperties(order,orderData);
                result.add(orderData);
            }
        }
        return result;
    }
}
再创建一个自定义注解CustomMerge，用于标记哪些属性需要合并，哪个是主键；
/**
 * 自定义注解，用于判断是否需要合并以及合并的主键
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CustomMerge {

    /**
     * 是否需要合并单元格
     */
    boolean needMerge() default false;

    /**
     * 是否是主键,即该字段相同的行合并
     */
    boolean isPk() default false;
}
再创建自定义单元格合并策略类CustomMergeStrategy，当Excel中两列主键相同时，合并被标记需要合并的列；
/**
 * 自定义单元格合并策略
 */
public class CustomMergeStrategy implements RowWriteHandler {
    /**
     * 主键下标
     */
    private Integer pkIndex;

    /**
     * 需要合并的列的下标集合
     */
    private List<Integer> needMergeColumnIndex = new ArrayList<>();

    /**
     * DTO数据类型
     */
    private Class<?> elementType;

    public CustomMergeStrategy(Class<?> elementType) {
        this.elementType = elementType;
    }

    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        // 如果是标题,则直接返回
        if (isHead) {
            return;
        }

        // 获取当前sheet
        Sheet sheet = writeSheetHolder.getSheet();

        // 获取标题行
        Row titleRow = sheet.getRow(0);

        if (null == pkIndex) {
            this.lazyInit(writeSheetHolder);
        }

        // 判断是否需要和上一行进行合并
        // 不能和标题合并，只能数据行之间合并
        if (row.getRowNum() <= 1) {
            return;
        }
        // 获取上一行数据
        Row lastRow = sheet.getRow(row.getRowNum() - 1);
        // 将本行和上一行是同一类型的数据(通过主键字段进行判断)，则需要合并
        if (lastRow.getCell(pkIndex).getStringCellValue().equalsIgnoreCase(row.getCell(pkIndex).getStringCellValue())) {
            for (Integer needMerIndex : needMergeColumnIndex) {
                CellRangeAddress cellRangeAddress = new CellRangeAddress(row.getRowNum() - 1, row.getRowNum(),
                        needMerIndex, needMerIndex);
                sheet.addMergedRegionUnsafe(cellRangeAddress);
            }
        }
    }

    /**
     * 初始化主键下标和需要合并字段的下标
     */
    private void lazyInit(WriteSheetHolder writeSheetHolder) {

        // 获取当前sheet
        Sheet sheet = writeSheetHolder.getSheet();

        // 获取标题行
        Row titleRow = sheet.getRow(0);
        // 获取DTO的类型
        Class<?> eleType = this.elementType;

        // 获取DTO所有的属性
        Field[] fields = eleType.getDeclaredFields();

        // 遍历所有的字段，因为是基于DTO的字段来构建excel，所以字段数 >= excel的列数
        for (Field theField : fields) {
            // 获取@ExcelProperty注解，用于获取该字段对应在excel中的列的下标
            ExcelProperty easyExcelAnno = theField.getAnnotation(ExcelProperty.class);
            // 为空,则表示该字段不需要导入到excel,直接处理下一个字段
            if (null == easyExcelAnno) {
                continue;
            }
            // 获取自定义的注解，用于合并单元格
            CustomMerge customMerge = theField.getAnnotation(CustomMerge.class);

            // 没有@CustomMerge注解的默认不合并
            if (null == customMerge) {
                continue;
            }

            for (int index = 0; index < fields.length; index++) {
                Cell theCell = titleRow.getCell(index);
                // 当配置为不需要导出时，返回的为null，这里作一下判断，防止NPE
                if (null == theCell) {
                    continue;
                }
                // 将字段和excel的表头匹配上
                if (easyExcelAnno.value()[0].equalsIgnoreCase(theCell.getStringCellValue())) {
                    if (customMerge.isPk()) {
                        pkIndex = index;
                    }

                    if (customMerge.needMerge()) {
                        needMergeColumnIndex.add(index);
                    }
                }
            }
        }

        // 没有指定主键，则异常
        if (null == this.pkIndex) {
            throw new IllegalStateException("使用@CustomMerge注解必须指定主键");
        }

    }
}
接下来在Controller中添加导出订单列表的接口，将我们自定义的合并策略CustomMergeStrategy给注册上去；
/**
 * EasyExcel导入导出测试Controller
 * Created by macro on 2021/12/20.
 */
@Controller
@Api(tags = "EasyExcelController", description = "EasyExcel导入导出测试")
@RequestMapping("/easyExcel")
public class EasyExcelController {
    
    @SneakyThrows
    @ApiOperation(value = "导出订单列表Excel")
    @RequestMapping(value = "/exportOrderList", method = RequestMethod.GET)
    public void exportOrderList(HttpServletResponse response) {
        List<Order> orderList = getOrderList();
        List<OrderData> orderDataList = convert(orderList);
        setExcelRespProp(response, "订单列表");
        EasyExcel.write(response.getOutputStream())
                .head(OrderData.class)
                .registerWriteHandler(new CustomMergeStrategy(OrderData.class))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("订单列表")
                .doWrite(orderDataList);
    }
}
在Swagger中访问接口测试，导出订单列表对应Excel；
