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
public class IOUtils {

	/**
	 * 璇诲彇utf-8缂栫爜鐨勮緭鍏ユ祦锛岃浆鍖栦负瀛楃涓�
	 */
	public static String changeStreamToString(InputStream inputStream) throws IOException {
		return changeStreamToString(inputStream, "utf-8");
	}

	/**
	 * 璇诲彇鎸囧畾缂栫爜鐨勮緭鍏ユ祦锛岃浆鍖栦负瀛楃涓�
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
	 * 灏嗗瓧绗︿覆杞寲涓簎tf-8瀛楄妭鏁扮粍杈撳嚭
	 */
	public static void writeDataToFile(String data, String savePath, String saveName) throws IOException {
		writeDataToFile(data, savePath, saveName, "utf-8");
	}

	/**
	 * 灏嗗瓧绗︿覆杞寲涓烘寚瀹氱紪鐮佸瓧鑺傛暟缁勮緭鍑�
	 */
	public static void writeDataToFile(String data, String savePath, String saveName, String charset)
			throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(new File(savePath, saveName));
		fileOutputStream.write(data.getBytes(charset));
		fileOutputStream.close();
	}

	/**
	 * 涓嶈浆鎹㈢紪鐮侊紝鍘熷厛鏄粈涔堢紪鐮佺殑杈撳叆娴佸氨浠ヤ粈涔堢紪鐮佽緭鍑�
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
	 * 璇诲彇utf-8缂栫爜鐨勬枃浠�
	 */
	public static String readDataFromFile(String savePath, String saveName) throws IOException {
		return readDataFromFile(savePath, saveName, "utf-8");
	}

	/**
	 * 璇诲彇鎸囧畾缂栫爜鐨勭殑鏂囦欢
	 */
	public static String readDataFromFile(String savePath, String saveName, String charset) throws IOException {
		FileInputStream inputStream = new FileInputStream(new File(savePath, saveName));
		String data = changeStreamToString(inputStream, charset);
		return data;
	}
}
