package cn.goso;

import com.thoughtworks.selenium.*;
import java.util.regex.Pattern;

public class test extends SeleneseTestCase {
	public void setUp() throws Exception {
		setUp("http://www.126.com", "*chrome");
	}
	public void testUntitled() throws Exception {
	}
}