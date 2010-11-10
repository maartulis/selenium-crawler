package cn.goso;

import java.io.IOException;
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

public class WBUserCrawler {
	private static SeleneseTestBase stb = new SeleneseTestBase();
	public static Selenium selenium = null;
	public static StringBuffer strb = new StringBuffer();

	public static void main(String[] args) throws SAXException, IOException, InterruptedException {


		WBUserCrawler wbusercrawler = new WBUserCrawler();

		wbusercrawler.WeiboInit();

//		crawlUserOfTop2000();
		wbusercrawler.crawlerTest();

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

	private static void crawlerTest() {
		System.out.println("Xpath number:" + selenium.getXpathCount("//EM[(text()='下一页')]"));
		selenium.click("//EM[(text()='下一页')]");
		System.out.println("Xpath number:" + selenium.getXpathCount("//A[@class='btn_num'][1]/em"));
		System.out.println("Xpath number:" + selenium.getXpathCount("//A[@class='btn_num'][1]/em"));
		
	}
	
	private static void crawlUserOfTop2000() throws InterruptedException {
		// TODO Auto-generated method stub

		// selenium.open("");
		selenium.open("http://t.sina.com.cn/pub/topmore");
		// selenium.click("link=我的微博");
		// selenium.waitForPageToLoad("30000");
		try {

			getUserName(selenium.getHtmlSource());

			Thread.sleep(2000);
			System.out.println("Xpath number:" + selenium.getXpathCount("//A[@class='btn_num'][1]/em"));
			selenium.click("//A[@class='btn_num'][1]/em");
			// body/div/div/div/div/div/div/a[8]/em
			getUserName(selenium.getHtmlSource());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// selenium.wait(1);
		Thread.sleep(2000);
		System.out.println("Xpath number:" + selenium.getXpathCount("//div[@id='profileFeed']/div/div/a[8]/em"));

		int numoftrying = 0;
		try {
			for (int i = 3; i < 1000; i++) {

				System.out.println("Xpath number:" + selenium.getXpathCount("//BODY/DIV/DIV/DIV/DIV/DIV/DIV/A[8]/em"));
				try {
					if (1 != selenium.getXpathCount("//BODY/DIV/DIV/DIV/DIV/DIV/DIV/A[8]/em").intValue()) {
						System.out.println("貌似已经没有了！");
						break;
					} else {
						selenium.click("//BODY/DIV/DIV/DIV/DIV/DIV/DIV/A[8]/em");
						getUserName(selenium.getHtmlSource());
						Thread.sleep(2000);
					}
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				System.out.println("---------------------" + i + "---------");
				numoftrying = 0;
				while (1 != selenium.getXpathCount("//BODY/DIV/DIV/DIV/DIV/DIV/DIV/A[8]/em").intValue()) {
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

		FileOperator.writeFile("D:\\My Project\\wobousername.txt", strb.toString());
		selenium.close();

	}

	private static void getUserName(String html) throws SAXException, IOException {
		// TODO Auto-generated method stub

		DOMParser parser = new DOMParser();
		parser.parse(new InputSource(new StringReader(html)));
		Document doc = parser.getDocument();
		// tags should be in upper case
		// String productsXpath =
		// "/HTML/BODY/DIV[2]/DIV[4]/DIV[2]/DIV/DIV[3]/UL[@class]/LI[9]";
		// div[@id='list_container_hot']/dl/dd/p[1]
		String productsXpath = "//SPAN[@class='name']";
		// div[@id='list_container_orgin']/dl/dd/p[1]
		NodeList products = null;
		try {
			products = XPathAPI.selectNodeList(doc, productsXpath);
			System.out.println("\n用户数: " + products.getLength());
			Node node = null;
			for (int i = 0; i < products.getLength(); i++) {
				node = products.item(i);
				System.out.println("第　" + i + "　个用户" + ":　" + node.getTextContent());
				strb.append(node.getTextContent() + "\r\n");
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		}

	}
}
