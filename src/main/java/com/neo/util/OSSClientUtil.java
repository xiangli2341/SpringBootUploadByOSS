package com.neo.util;

import com.aliyun.oss.*;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


/**
 * @author RLY
 * 阿里云OSS上传文件工具
 * 支持普通文件上传，限制大小文件上传,限制大小图片上传
 */
public class OSSClientUtil {

    private static Logger log = LoggerFactory.getLogger(OSSClientUtil.class);

    /**阿里云API的内或外网域名*/
    public static String ENDPOINT = "";
    /**OSS签名key*/
    public static String ACCESS_KEY_ID = "";
    /**OSS签名密钥*/
    public static String ACCESS_KEY_SECRET = "";
    /**存储空间名称*/
    public static String BUCKETNAME = "";


    public static String getStartStaff() {
        return "http://"+BUCKETNAME+"."+ENDPOINT;
    }

//    /**
//     * 获取ossClient
//     * @return
//     */
//    public static OSSClient ossClientInitialization(){
//        return new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
//    }

//    /**
//     * 判断是否存在bucketName
//     * @return
//     */
//    private static boolean hasBucket(OSSClient ossClient){
//        return ossClient.doesBucketExist(BUCKETNAME);
//    }

    public static String createFileName(String mime) { // 需要创建一个文件名称
        String fileName = UUID.randomUUID() + "_" + mime + ".jpg";
        return fileName;
    }
    /**
     * 上传到OSS服务器  如果同名文件会覆盖服务器上的
     *
     * @param inputStream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return 出错返回"" ,唯一MD5数字签名
     */
    public static String uploadImage( InputStream inputStream, String fileName) {
        String resultStr  = "";
        try {
            /**
             * 创建OSS客户端
             */
            OSSClient ossClient = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
            //创建上传Object的Metadata
            ObjectMetadata metadata  = new ObjectMetadata();
            //上传的文件的长度
            metadata .setContentLength(inputStream.available());
            //指定该Object被下载时的网页的缓存行为
            metadata .setCacheControl("no-cache");
            //指定该Object下设置Header
            metadata .setHeader("Pragma", "no-cache");
            //指定该Object被下载时的内容编码格式
            metadata.setContentEncoding("utf-8");
            //文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
            //如果没有扩展名则填默认值application/octet-stream
            metadata .setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            metadata .setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(BUCKETNAME,fileName, inputStream, metadata);
            //解析结果
            resultStr = putResult.getETag();
            System.out.println(resultStr);
        } catch (IOException e) {
            log.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
        }
        return resultStr;
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }



}