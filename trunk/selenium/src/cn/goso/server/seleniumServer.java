package cn.goso.server;

import org.openqa.selenium.server.SeleniumServer;

public class seleniumServer {
public static void main(String[] args) {
	SeleniumServer server = null;
	try {
		server = new SeleniumServer();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
	try {
		server.start();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}   
//	server.stop();  

}
}
