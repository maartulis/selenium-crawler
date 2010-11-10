package cn.goso;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.goso.util.FileOperator;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.Selenium;

public class WeiboCrawler {
	private static SeleneseTestBase stb = new SeleneseTestBase();
	public static Selenium selenium = null;
	public static StringBuffer strb = new StringBuffer();
	public static int TotalNum = 0;

	public static void main(String[] args) throws SAXException, IOException, InterruptedException {

		WeiboCrawler wbcrawler = new WeiboCrawler();

		wbcrawler.WeiboInit();
		// wbcrawler.crawlWeibobyScrolling();
		wbcrawler.getMingRenWeibo();
		// wbcrawler.crawlWeiboPro("我是贺志明");

	}

	private void getMingRenWeibo() throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/rmss/microblog/sina/data/wobousernameTOP2000"), "UTF-8"));
		if (br != null) {
			String data = null;
			int i = 0;
			while ((data = br.readLine()) != null) {
				if (0 == i)
					data = data.substring(1).trim();
				System.out.println(data);
				i++;

				// ��ȡ
				try {
					crawlWeiboPro(data);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void WeiboInit() {

		try {
			stb.setUp("http://localhost:4444/", "*chrome");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selenium = stb.selenium;
		selenium.open("http://t.sina.com.cn/");
		selenium.type("loginname", "me.trace@gmail.com");
		selenium.type("password", "111111");
		selenium.click("remusrname");
		selenium.click("login_submit_btn");
		selenium.windowMaximize();
		selenium.waitForPageToLoad("300000");
	}

	private void crawlWeibobyScrolling() throws SAXException, IOException, InterruptedException {
		// TODO Auto-generated method stub

		// selenium.open("");
		selenium.open("http://t.sina.com.cn/n/我是贺志明");
		// selenium.refresh();
		selenium.waitForPageToLoad("300000");

		System.out.println("begin!!");

		Thread.sleep(1000);
		selenium.runScript("var currentpos=0;" + "window.setInterval(function(){window.scroll(0,currentpos+=600);},500)");
		Thread.sleep(10000);
		System.out.println("end!!");
		// selenium.waitForPageToLoad("30000");
		// getWeiboContent(selenium.getHtmlSource());

		// selenium.keyPressNative(String.valueOf(KeyEvent.VK_PAGE_DOWN));

		//
		// Thread.sleep(2000);
		System.out.println("Xpath number:" + selenium.getXpathCount("//div[@id='profileFeed']/div/div/a/em"));
		selenium.click("//div[@id='profileFeed']/div/div/a/em");
		selenium.waitForPageToLoad("300000");

		Thread.sleep(1000);
		selenium.runScript("var currentpos=0;" + "window.setInterval(function(){window.scroll(0,currentpos+=600);},500)");
		Thread.sleep(10000);
		for (int i = 3; 1 == selenium.getXpathCount("//div[@id='profileFeed']/div/div/a[" + i + "]/em").intValue(); i++) {

			System.out.println("Xpath number:" + selenium.getXpathCount("//div[@id='profileFeed']/div/div/a[" + i + "]/em"));
			selenium.click("//div[@id='profileFeed']/div/div/a[" + i + "]/em");
			// getWeiboContent(selenium.getHtmlSource());
			selenium.waitForPageToLoad("300000");
			selenium.runScript("var currentpos=0;" + "window.setInterval(function(){window.scroll(0,currentpos+=600);},500)");
			Thread.sleep(12000);
			System.out.println("---------------------" + i + "---------");

		}

		// selenium.close();

	}

	private void crawlWeibo(String weiboUsername) throws InterruptedException {
		// TODO Auto-generated method stub

		selenium.open("http://t.sina.com.cn/n/" + weiboUsername);
		// selenium.refresh();
		selenium.waitForPageToLoad("300000");
		// selenium.wa
		// selenium.refresh();

		try {
			// 第一页
			getWeiboContent(selenium.getHtmlSource(), weiboUsername);

			// 第二页
			System.out.println("Xpath number:" + selenium.getXpathCount("//div[@id='profileFeed']/div/div/a/em"));
			if (0 == selenium.getXpathCount("//div[@id='profileFeed']/div/div/a/em").intValue()) {
				System.out.println("没有链接！");
			} else {

				selenium.click("//div[@id='profileFeed']/div/div/a/em");
				// selenium.waitForPageToLoad("300000");
				// body/div/div/div/div/div/div/a[8]/em
				getWeiboContent(selenium.getHtmlSource(), weiboUsername);
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// selenium.wait(1);
		System.out.println("Xpath number:" + selenium.getXpathCount("//div[@id='profileFeed']/div/div/a[8]/em"));

		int numoftrying = 0;
		try {
			for (int i = 3; i < 1000; i++) {

				System.out.println("Xpath number:" + selenium.getXpathCount("//div[@id='profileFeed']/div/div/a[8]/em"));
				try {
					if (1 != selenium.getXpathCount("//div[@id='profileFeed']/div/div/a[8]/em").intValue()) {
						System.out.println("There is no weibo anymore");
						break;
					} else {
						selenium.click("//div[@id='profileFeed']/div/div/a[8]/em");
						// selenium.waitForPageToLoad("300000");
						getWeiboContent(selenium.getHtmlSource(), weiboUsername);
						Thread.sleep(2000);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}

				System.out.println("---------------------" + i + "---------");
				numoftrying = 0;
				while (1 != selenium.getXpathCount("//div[@id='profileFeed']/div/div/a[8]/em").intValue()) {
					if (numoftrying++ > 5) {
						break;
					}
					Thread.sleep(2000);
					System.out.println("休息2秒");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		FileOperator.writeFile("/home/rmss/microblog/sina/data/weiboContent/weibo_" + weiboUsername + "", strb.toString());
		strb = new StringBuffer();
		// selenium.close();

	}

	private void crawlWeiboPro(String weiboUsername) throws InterruptedException {
		// TODO Auto-generated method stub

		selenium.open("http://t.sina.com.cn/n/" + weiboUsername);
		selenium.waitForPageToLoad("300000");

		try {
			//第一页
			getWeiboContent(selenium.getHtmlSource(), weiboUsername);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int i = 0, numoftrying = 0;
		boolean isOver = false;
		while (!isOver) {
			System.out.println("Xpath number:" + selenium.getXpathCount("//EM[(text()='下一页')]"));
			try {
				//判断有无
				numoftrying = 0;
				while (selenium.getXpathCount("//EM[(text()='下一页')]").intValue() < 1) {
					if (numoftrying++ > 5 || (selenium.getXpathCount("//EM[(text()='上一页')]").intValue() > 0 &&
							selenium.getXpathCount("//EM[(text()='下一页')]").intValue() < 1)
							) {
						isOver = true;
						break;
					}
					if (numoftrying % 2 == 0) {
						selenium.refresh();
					}
					Thread.sleep(2000);
					System.out.println("休息2秒");
				}
				if (!isOver) {
					selenium.click("//EM[(text()='下一页')]");
					// selenium.waitForPageToLoad("300000");
					Thread.sleep(2000);

					getWeiboContent(selenium.getHtmlSource(), weiboUsername);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("------------" + weiboUsername + "---------" + i++ + "---------");

		}

		FileOperator.writeFile("/home/rmss/microblog/sina/data/weiboContent/weibo_" + weiboUsername + "", strb.toString());
		strb = new StringBuffer();
		TotalNum = 0;
		// selenium.close();

	}

	private void getWeiboContent(String html, String username) throws SAXException, IOException {
		// TODO Auto-generated method stub

		DOMParser parser = new DOMParser();
		parser.parse(new InputSource(new StringReader(html)));
		Document doc = parser.getDocument();
		// tags should be in upper case
		// String productsXpath =
		// "/HTML/BODY/DIV[2]/DIV[4]/DIV[2]/DIV/DIV[3]/UL[@class]/LI[9]";
		// div[@id='list_container_hot']/dl/dd/p[1]
		String productsXpath = "//P[@class='sms']";
		// div[@id='list_container_orgin']/dl/dd/p[1]
		NodeList products = null;
		try {
			products = XPathAPI.selectNodeList(doc, productsXpath);
			System.out.println("\n微博数: " + products.getLength());
			Node node = null;
			for (int i = 0; i < products.getLength(); i++) {
				node = products.item(i);
				if (0 == i)
					System.out.println("第" + TotalNum + "微博" + ":" + node.getTextContent());
				strb.append(username + " " + TotalNum++ +  ": " + node.getTextContent() + "\r\n");
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}

}
