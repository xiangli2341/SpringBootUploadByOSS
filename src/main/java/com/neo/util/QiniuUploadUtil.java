package com.neo.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.UUID;

public class QiniuUploadUtil {

	private static Logger logger = LoggerFactory.getLogger(QiniuUploadUtil.class);

	private static String accessKey = "";
	private static String secretKey = "";
	private static String bucket = "d";
	private static String startStaff = "";

	public static String getStartStaff() {
		return startStaff;
	}

	public static String upLoadImage(InputStream inputStream, String fileName) {
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);

		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		DefaultPutRet putRet = null;
		try {
			Response response = uploadManager.put(inputStream, fileName, upToken, null, null);
			// 解析上传成功的结果
			putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

		} catch (QiniuException ex) {
			Response r = ex.response;
			logger.error(ex.error());
			return null;
		}
		return putRet.key;
	}

	public static String createFileName(String mime) { // 需要创建一个文件名称
		String fileName = UUID.randomUUID() + "_" + mime + ".jpg";
		return fileName;
	}
	
	/**
	 * 生成上传的token
	 * @return
	 */
	public static String getToken() {
		// 构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone2());
		// ...其他参数参考类注释
		UploadManager uploadManager = new UploadManager(cfg);

		Auth auth = Auth.create(accessKey, secretKey);
		String upToken = auth.uploadToken(bucket);
		return upToken;
	}

}
