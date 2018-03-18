package com.zrbmyself.quickly.bean;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: ZhouRunBin
 * Date: 2018/3/18 0018
 * Time: 14:27
 * Description:
 */
public class FileParam {

    private String fieldName; //文件表单的字段名
    private String fileName; //上传文件的文件名
    private long fileSize;  //文件大小
    private String contentType; //判断文件类型
    private InputStream inputStream; //上传文件的字节输入流

    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
