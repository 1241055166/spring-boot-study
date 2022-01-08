package com.henry.breakpoint.service;


import com.henry.breakpoint.model.dto.MultipartFileDTO;

import java.io.IOException;

/**
 * @author henry
 * @description 存储操作的service
 * @date 2021/5/21 11:11
 **/
public interface StorageService {

    /**
     * 删除全部数据
     */
    void deleteAll();

    /**
     * 初始化方法
     */
    void init();

    /**
     * 上传文件方法1
     *
     * @param multipartFileDTO
     * @throws IOException
     */
    void uploadFileRandomAccessFile(MultipartFileDTO multipartFileDTO) throws IOException;

    /**
     * 上传文件方法2
     * 处理文件分块，基于MappedByteBuffer来实现文件的保存
     *
     * @param multipartFileDTO
     * @throws IOException
     */
    boolean uploadFileByMappedByteBuffer(MultipartFileDTO multipartFileDTO) throws IOException;

}
