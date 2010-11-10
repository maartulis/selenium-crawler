package cn.goso; 

//import org.testng.Assert; 
//import org.testng.annotations.Test; 


public class HelloWorld extends AbstractTest{ 
    private final String DEFAULT_TIMEOUT = "30000"; 
     
    public static void main(String[] args) {
		HelloWorld hello = new HelloWorld();
		hello.searchHelloWorldByGoogle();
	}
    public void searchHelloWorldByGoogle(){ 
        selenium.open("http://www.google.com/webhp?hl=en"); 
        selenium.waitForPageToLoad(DEFAULT_TIMEOUT); 
        selenium.type("q", "hello world"); 
        selenium.click("btnG"); 
        selenium.waitForPageToLoad(DEFAULT_TIMEOUT); 
        selenium.click("//a[@href='http://en.wikipedia.org/wiki/Hello_world_program']"); 
        selenium.waitForPageToLoad(DEFAULT_TIMEOUT); 
//        Assert.assertTrue(selenium.isTextPresent("\"Hello, World!\"")); 
    } 

}
