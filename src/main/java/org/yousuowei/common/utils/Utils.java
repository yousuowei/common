package org.yousuowei.common.utils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.baidu.inf.iis.bcs.BaiduBCS;
import com.baidu.inf.iis.bcs.auth.BCSCredentials;
import com.baidu.inf.iis.bcs.model.ObjectMetadata;
import com.baidu.inf.iis.bcs.request.PutObjectRequest;
import com.baidu.inf.iis.bcs.response.BaiduBCSResponse;

public class Utils {

    public static Object toObject(Class<?> toClass, Object obj) {
	if (null == obj) {
	    return null;
	} else {
	    Object t = null;
	    try {
		t = toClass.newInstance();
		BeanUtils.copyProperties(t, obj);
	    } catch (InstantiationException e) {
	    } catch (IllegalAccessException e) {
	    } catch (InvocationTargetException e) {
	    }
	    return t;
	}
    }

    public static List<Object> toObjectList(Class<?> toClass, List<Object> list) {
	List<Object> result = null;
	if (null != list && list.size() > 0) {
	    result = new ArrayList<Object>(list.size());
	    for (Object obj : list) {
		result.add(toObject(toClass, obj));
	    }
	} else {
	    result = new ArrayList<Object>(0);
	}
	return result;
    }

    public static String uploadFile(File file) {
	String host = "bcs.duapp.com";
	String accessKey = "BZSUBTtIlFN2MtwTrzIkWELc";
	String secretKey = "VEgxogN8a5CA3tGqBjA8eZFV9gQmC4zd";
	String bucket = "yousuowei-ota";
	String fileName = String.valueOf("/" + System.currentTimeMillis());

	BCSCredentials credentials = new BCSCredentials(accessKey, secretKey);
	BaiduBCS baiduBCS = new BaiduBCS(credentials, host);
	baiduBCS.setDefaultEncoding("UTF-8"); // Default UTF-8

	PutObjectRequest request = new PutObjectRequest(bucket, fileName, file);
	ObjectMetadata metadata = new ObjectMetadata();
	// metadata.setContentType("text/html");
	request.setMetadata(metadata);
	BaiduBCSResponse<ObjectMetadata> response = baiduBCS.putObject(request);
	ObjectMetadata objectMetadata = response.getResult();
	System.out.println(objectMetadata.getContentMD5());
	return null;
    }

    public static void main(String[] args) {
	File file = new File("g:/test/test.txt");
	uploadFile(file);
    }

}
