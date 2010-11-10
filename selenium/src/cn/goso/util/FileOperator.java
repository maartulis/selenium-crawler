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
			// ���� UTF-8 ���뷽ʽ���ֽ���ת��Ϊ�ַ���
			OutputStreamWriter isr = new OutputStreamWriter(fis, "UTF-8");
			// ���ַ����л�ȡ�ı������л���
			BufferedWriter br = new BufferedWriter(isr);

			br.write(content);

			br.close();
			isr.close();
			fis.close();
			// ����ı��ļ�����
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
