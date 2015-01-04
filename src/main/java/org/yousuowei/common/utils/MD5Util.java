/**
 * Copyright © 2014 videoHj. All rights reserved.
 * @Title: MD5Util.java
 * @Prject: ClientCommon
 * @Package: com.mipt.clientcommon.utils
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-28 下午1:59:16
 * @version: V1.0
 */

package org.yousuowei.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.util.StringUtils;

/**
 * @ClassName: MD5Util
 * @Description: TODO
 * @author: jie
 * @date: 2014-8-28 下午1:59:16
 */

public class MD5Util {

    /**
     * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
     */
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
	    '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String getFileMD5String(File file) throws IOException,
	    NoSuchAlgorithmException {
	return getFileMD5String(new FileInputStream(file));
    }

    public static String getFileMD5String(InputStream in) throws IOException,
	    NoSuchAlgorithmException {
	MessageDigest messagedigest = MessageDigest.getInstance("MD5");
	byte[] buffer = new byte[1024];
	int numRead = 0;
	while ((numRead = in.read(buffer)) > 0) {
	    messagedigest.update(buffer, 0, numRead);
	}
	in.close();
	return bufferToHex(messagedigest.digest());
    }

    public static boolean compareFileMd5(String filePath, String md5) {
	if (StringUtils.isEmpty(md5)) {
	    return false;
	}
	return compareFileMd5(new File(filePath), md5);
    }

    public static boolean compareFileMd5(File file, String md5) {
	if (StringUtils.isEmpty(md5) || null == file) {
	    return false;
	}
	String fileMd5;
	try {
	    fileMd5 = getFileMD5String(file);
	} catch (NoSuchAlgorithmException e) {
	    return false;
	} catch (IOException e) {
	    return false;
	}
	boolean isSame = md5.equals(fileMd5);
	return isSame;
    }

    private static String bufferToHex(byte bytes[]) {
	return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
	StringBuffer stringbuffer = new StringBuffer(2 * n);
	int k = m + n;
	for (int l = m; l < k; l++) {
	    appendHexPair(bytes[l], stringbuffer);
	}
	return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
	char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换
	// 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
	char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
	stringbuffer.append(c0);
	stringbuffer.append(c1);
    }

    public static void main(String[] args) throws IOException,
	    NoSuchAlgorithmException {
	String filePath = null;
	byte[] inBytes = new byte[1024];
	System.out.println("file path:(click 'Center' end)");
	while (System.in.read(inBytes) != -1) {
	    filePath = new String(inBytes).trim();
	    break;
	}
	System.out.println(filePath);
	if (null != filePath) {
	    File file = new File(filePath);
	    String md5 = getFileMD5String(file);
	    System.out.println("md5:" + md5);
	}
    }
}
