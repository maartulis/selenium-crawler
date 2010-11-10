package cn.goso;

import com.thoughtworks.selenium.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class test2 extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://localhost:4444/", "*chrome");
	}

	public void testUntitled() throws Exception {
//		selenium.open("http://www.google.com");
//		selenium.type("q", "hezhiming");
//		selenium.click("btnG");
//		selenium.waitForPageToLoad("30000");
		
//        selenium.open("http://www.google.com/webhp?hl=en"); 
//        selenium.waitForPageToLoad("30000"); 
//        selenium.type("q", "hello world"); 
//        selenium.click("btnG"); 
//        selenium.waitForPageToLoad("30000"); 
//        selenium.click("//a[@href='http://en.wikipedia.org/wiki/Hello_world_program']"); 
//        selenium.waitForPageToLoad("30000"); 
        

//		selenium.open("http://www.xinhuanet.com/society/gdxw.htm");
//		selenium.click("link=何志明");
//		selenium.click("//table[@id='1']/tbody/tr/td/a/font");
//		selenium.waitForPageToLoad("30000");
//        System.out.println( selenium.getBodyText() );
//        System.out.println( selenium.getAllLinks() );
//        System.out.println( selenium.getHtmlSource() );
//		printComment(selenium.getHtmlSource());
//		String str = selenium.getHtmlSource();
//		System.out.println(str);
//		selenium.
		
		autoLoginWeibo();
		
		
	}
	
	private void autoLoginWeibo() throws SAXException, IOException {
		// TODO Auto-generated method stub

//		selenium.open("");

		selenium.open("http://t.sina.com.cn/");
		selenium.type("loginname","metrace@sina.com");
		selenium.type("password", "843090");
		selenium.click("remusrname");
		selenium.click("login_submit_btn");
		selenium.windowMaximize();
		selenium.waitForPageToLoad("3000");
		selenium.click("link=我的微博");
		selenium.waitForPageToLoad("3000");
		getWeiboContent(selenium.getHtmlSource());
		selenium.click("//div[@id='profileFeed']/div[3]/div[2]/a[6]/em");
		getWeiboContent(selenium.getHtmlSource());
		selenium.click("//div[@id='profileFeed']/div[3]/div[2]/a[7]/em");
		getWeiboContent(selenium.getHtmlSource());
		selenium.click("//div[@id='profileFeed']/div[3]/div[2]/a[7]/em");
		getWeiboContent(selenium.getHtmlSource());
		selenium.click("//div[@id='profileFeed']/div[3]/div[2]/a[7]/em");
		getWeiboContent(selenium.getHtmlSource());
		selenium.click("//div[@id='profileFeed']/div[3]/div[2]/a[7]/em");
		
		selenium.close();
		
	}
	private void getWeiboContent(String html) throws SAXException, IOException {
		// TODO Auto-generated method stub
		
		DOMParser parser = new DOMParser();
		parser.parse(new InputSource(new StringReader(html)));
		Document doc = parser.getDocument();
		// tags should be in upper case
		// String productsXpath =
		// "/HTML/BODY/DIV[2]/DIV[4]/DIV[2]/DIV/DIV[3]/UL[@class]/LI[9]";
		// div[@id='list_container_hot']/dl/dd/p[1]
		String productsXpath = "//LI[@class='MIB_linedot_l']";
		// div[@id='list_container_orgin']/dl/dd/p[1]
		NodeList products = null;
		try {
			products = XPathAPI.selectNodeList(doc, productsXpath);
			System.out.println("\n\n微博数: " + products.getLength());
			Node node = null;
			for (int i = 0; i < products.getLength(); i++) {
				node = products.item(i);
				System.out.println(i + ":\n" + node.getTextContent());
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	
	}

	private static void printComment(String html) throws SAXException,
			IOException {
		DOMParser parser = new DOMParser();
		parser.parse(new InputSource(new StringReader(html)));
		Document doc = parser.getDocument();
		// tags should be in upper case
		// String productsXpath =
		// "/HTML/BODY/DIV[2]/DIV[4]/DIV[2]/DIV/DIV[3]/UL[@class]/LI[9]";
		// div[@id='list_container_hot']/dl/dd/p[1]
		String productsXpath_hot = "//DIV[@id='list_container_hot']/DL/DD/P[1]";
		String productsXpath = "//DIV[@id='list_container_orgin']/DL/DD/P[1]";
		// div[@id='list_container_orgin']/dl/dd/p[1]
		NodeList products = null, products_hot = null;
		try {
			products_hot = XPathAPI.selectNodeList(doc, productsXpath_hot);
			products = XPathAPI.selectNodeList(doc, productsXpath);
			if (products_hot.getLength() != 0) {
				System.out.println("热门评论：" + products_hot.getLength());
				Node node_hot = null;
				for (int i = 0; i < products_hot.getLength(); i++) {
					node_hot = products_hot.item(i);
					System.out.println(i + ":\n" + node_hot.getTextContent());
				}

			}
			System.out.println("\n\n普通评论: " + products.getLength());
			Node node = null;
			for (int i = 0; i < products.getLength(); i++) {
				node = products.item(i);
				System.out.println(i + ":\n" + node.getTextContent());
			}
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}