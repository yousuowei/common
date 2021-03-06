package org.yousuowei.common.baidu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class BaiduFile {
    private static String host = "bcs.duapp.com";
    private static String accessKey = "BZSUBTtIlFN2MtwTrzIkWELc";
    private static String secretKey = "VEgxogN8a5CA3tGqBjA8eZFV9gQmC4zd";
    private static String bucket = "yousuowei-ota";
    private static String flag = "MBO";

    public static BaiduFileUploadInfo uploadFile(InputStream in) {
	String fileName = String.valueOf("/" + System.currentTimeMillis());

	BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
	BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
	baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8

	ObjectMetadata metadata = new ObjectMetadata();
	try {
	    metadata.setContentLength(in.available());
	} catch (IOException e) {
	    return null;
	}
	PutObjectRequest request = new PutObjectRequest(bucket, fileName, in,
		metadata);

	BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
	ObjectMetadata objectMetadata = response.getResult();
	BaiduFileUploadInfo info = new BaiduFileUploadInfo();
	info.setMd5(objectMetadata.getContentMD5());
	info.setUrl(getPath(fileName));
	return info;
    }

    public static String getPath(String name) {
	StringBuilder urlStr = new StringBuilder();
	urlStr.append("http://");
	urlStr.append(host);
	urlStr.append("/");
	urlStr.append(bucket);
	urlStr.append("/");
	urlStr.append(name);
	urlStr.append("?sign=");
	urlStr.append(flag);
	urlStr.append(":");
	urlStr.append(accessKey);
	urlStr.append(":");
	String Signature = createSignature(name);
	urlStr.append(Signature);
	return urlStr.toString();
    }

    private static String createSignature(String name) {
	String content = createContent(name);
	SecretKey key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA1");
	Mac mac = null;
	try {
	    mac = Mac.getInstance("HmacSHA1");
	} catch (NoSuchAlgorithmException e1) {
	    return null;
	}
	try {
	    mac.init(key);
	} catch (InvalidKeyException e1) {
	    return null;
	}
	byte[] hashBytes = mac.doFinal(content.getBytes());
	hashBytes = Base64.encodeBase64(hashBytes);
	try {
	    return URLEncoder.encode(new String(hashBytes), "utf-8");
	} catch (UnsupportedEncodingException e) {
	    return null;
	}
    }

    private static String createContent(String object) {
	StringBuilder content = new StringBuilder();
	content.append(flag);
	content.append("\n");
	content.append("Method=GET");
	content.append("\n");
	content.append("Bucket=");
	content.append(bucket);
	content.append("\n");
	content.append("Object=");
	content.append(object);
	content.append("\n");
	return content.toString();
    }

    public static void main(String[] args) {
	FileInputStream in;
	try {
	    in = new FileInputStream("g:/test/test.txt");
	    uploadFile(in);
	} catch (FileNotFoundException e) {
	}
    }

}
