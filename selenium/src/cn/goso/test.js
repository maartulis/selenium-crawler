window.setInterval(function(){window.scroll(0,currentpos+=100);},3000)


"var currentpos=0;" +
"" +
"window.setInterval()" +
"while (currentpos < document.body.scrollHeight)" +
"{" +
	"currentpos+=100;" +
	"window.scroll(0,currentpos);" +
	"pause(1000)" +
"}"