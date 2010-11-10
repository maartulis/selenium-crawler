package cn.goso.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileOperator {

	public static void writeFile(String url, String content) {
		FileOutputStream fis;
		try {
			fis = new FileOutputStream(url);
			// 按照 UTF-8 编码方式将字节流转化为字符流
			OutputStreamWriter isr = new OutputStreamWriter(fis, "UTF-8");
			// 从字符流中获取文本并进行缓冲
			BufferedWriter br = new BufferedWriter(isr);

			br.write(content);

			br.close();
			isr.close();
			fis.close();
			// 输出文本文件内容
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
