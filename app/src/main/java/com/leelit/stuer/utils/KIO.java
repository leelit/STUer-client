package com.leelit.stuer.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Leelit on 2015/12/26.
 */
public class KIO {

	/**
	 * 读取utf-8编码的输入流，转化为字符串
	 */
	public static String changeStreamToString(InputStream inputStream) throws IOException {
		return changeStreamToString(inputStream, "utf-8");
	}

	/**
	 * 读取指定编码的输入流，转化为字符串
	 */
	public static String changeStreamToString(InputStream inputStream, String charset) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
		StringBuilder sb = new StringBuilder();
		try {
			String str;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
		} finally {
			reader.close();
		}
		return sb.toString();
	}

	/**
	 * 将字符串转化为utf-8字节数组输出
	 */
	public static void writeDataToFile(String data, String savePath, String saveName) throws IOException {
		writeDataToFile(data, savePath, saveName, "utf-8");
	}

	/**
	 * 将字符串转化为指定编码字节数组输出
	 */
	public static void writeDataToFile(String data, String savePath, String saveName, String charset)
			throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath, saveName));
		fileOutputStream.write(data.getBytes(charset));
		fileOutputStream.close();
	}

	/**
	 * 不转换编码，原先是什么编码的输入流就以什么编码输出
	 */
	public static void writeDataToFile(InputStream inputStream, String savePath, String saveName) throws IOException {
		BufferedInputStream in = null;
		FileOutputStream out = null;
		try {
			in = new BufferedInputStream(inputStream);
			out = new FileOutputStream(new File(savePath, saveName));
			byte[] buffer = new byte[4 * 1024];
			int len;
			while ((len = in.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}

	/**
	 * 读取utf-8编码的文件
	 */
	public static String readDataFromFile(String savePath, String saveName) throws IOException {
		return readDataFromFile(savePath, saveName, "utf-8");
	}

	/**
	 * 读取指定编码的的文件
	 */
	public static String readDataFromFile(String savePath, String saveName, String charset) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(savePath, saveName));
		String data = changeStreamToString(inputStream, charset);
		return data;
	}
}
