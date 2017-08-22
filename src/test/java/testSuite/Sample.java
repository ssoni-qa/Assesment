package testSuite;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.browserstack.local.Local;

import io.restassured.response.Response;

public class Sample {

	public WebDriver driver;
	protected Local l;	
	public ExtentReports extent;
	public ExtentTest specTest,apiTest,apiPerf,respTest;
	public ExtentTest appQuality,codeQuality,skillTest;
	public  String testName;
	public static Response response;
	public static String jsonAsString;
	public  File destination;
	public  String dest;
	ExtentHtmlReporter htmlReporter;
	Markup m;

	@Test
	public void report()
	{
		htmlReporter = new ExtentHtmlReporter("./Report/Assessment Report_Associate_01.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("SBA", "jQuery - Insurance - Solution Kit");
		htmlReporter.config().setDocumentTitle("Assessment Report_Associate_01");


		//Creating Application Quality Test
		appQuality=extent.createTest("Application Quality");
		appQuality.assignAuthor("Candidate Name Will be Showned Here");
		specTest = appQuality.createNode("<b>Specification</b>");
		
		//For Specification
		specTest.log(Status.PASS, "<b>TS01</b><br/>"
				+ "Two tabs i.e. 'Get new insurance' tab and 'renew insurance' tab are created. "
				+ "Along with headers and footers of the page.<br/><b>Weightage 6</b>");
		specTest.log(Status.FAIL, "<b>TS02</b>"
				+ "<br/>Both tabs are able to switch when clicked on radio buttons on the top.<br/>"
				+ "<b>Score 0/7</b>.");
		
		//For API Accuracy
		
		apiTest=appQuality.createNode("<b>API Accuracy</b>");
		apiTest.log(Status.PASS, "<b>TS017</b><br/>"
				+ "User click on buy insurance and front end is making proper 'HTTP POST' request to buy an insurance. Once purchase is successful, proper success message is shown to user with policy number and number of days left for expiry."
				+ "<br/><b>Score 15/20</b>");
		apiTest.log(Status.FAIL, "<b>TS018</b>"
				+ "<br/>'Get details' button is clicked, making a 'HTTP GET' request fetch the details of insurance policy. If policy number does not exist. Show a proper error message.<br/>"
				+ "<b>Score 0/15</b>.");
		
		//For API Performance
		apiPerf=appQuality.createNode("<b>API Performance</b>");
		apiPerf.log(Status.PASS, "<b>TS028</b><br/>"
				+ "API POST Request , Response Time - 200015"
				+ "<br/><b>Score 15/20</b>");
		apiPerf.log(Status.FAIL, "<b>TS018</b>"
				+ "<br/>API GET Request , Response Time - 123658 , but actual was 23658<br/>"
				+ "<b>Score 0/15</b>.");
		
		
		//For Responsiveness
		respTest=appQuality.createNode("<b>Responsiveness</b>");
		respTest.log(Status.PASS, "<b>TS05</b><br/>"
				+ "Screen is able to fit in laptop, tablet and mobile. Which means screen should be responsive"
				+"Device Name - Tab"
				+ "<br/><b>Score 15/20</b>");
		respTest.log(Status.FAIL, "<b>TS013</b>"
				+ "<br/>Screen is able to fit in laptop, tablet and mobile. Which means screen should be responsive<br/>"
				+"Device Name - Iphone , Reason : 'Get Quotes' button css does not match as per designed specification."
				+ "<b>Score 0/15</b>.");
		
		//Main Code Quality Tab
		
		codeQuality=extent.createTest("<b>Code Quality</b>");
		codeQuality.log(Status.INFO, "<table border=\"1\"> <tr> <td><b>Reliability</b></td> <td>D</td> </tr> <tr> <td><b>Security</></td> <td>E</td> </tr> "
				+ "<tr> <td><b>Maintainability</b></td> <td>A</td> </tr> <tr> <td><b>Coverage</b></td> <td>0.0 %</td> </tr>"
				+ "<tr> <td><b>Duplications</b></td> <td>4.8 %</td></table>");
		
		
		//Main Skill Test
		
		skillTest=extent.createTest("<b>Skill BreakUp</b>");
		skillTest.log(Status.INFO, "<table border=\"1\"> <tr> <td><b>HTML5 - 	Elements/Tags</b></td> <td>Areas of Improvement</td> <td><b>TS01, TS02</></td> <td>100</td> <td><b>60</></td> </tr></table> ");
				
		
		//Final Matrix
		appQuality.log(Status.INFO, "<table border=\"1\"> <tr> <td><b>Specification</b></td> <td>20 %</td> </tr> <tr> <td><b>API Accuracy</></td> <td>20 %</td> </tr> "
				+ "<tr> <td><b>API Performance</b></td> <td>20 %</td> </tr> <tr> <td><b>Responsiveness</b></td> <td>20 %</td> </tr></table>");

		extent.flush();


	}

}
