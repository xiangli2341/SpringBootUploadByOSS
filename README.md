# SpringBoot-uploadByOSS
 AliyunOSS上传


使用AliOSS上传工具类写的一个上传，上传到本地+上传到aliOSS静态资源空间
```
配置util下OSSClientUtil 阿里云上传工具类
/**阿里云API的内或外网域名*/
public static String ENDPOINT = "";
/**OSS签名key*/
public static String ACCESS_KEY_ID = "";
/**OSS签名密钥*/
public static String ACCESS_KEY_SECRET = "";
/**存储空间名称*/
public static String BUCKETNAME = "";
```


配置完成之后 ，启动打开浏览器，默认8080端口，开始上传测试·

【submit】后返回如下字样; 去资源存储空间查看有无上传文件，并设置公共读权限，复制上传返回的链接在浏览器打开。访问打开即成功

#### Spring Boot - Upload Status
#### You successfully uploaded '【http://xxxx.oss-cn-hongkong.aliyuncs.com/xxxxx-xxxx-xxx-xx-xxxxx.jpg】'
