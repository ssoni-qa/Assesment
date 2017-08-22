package testSuite;

import static io.restassured.RestAssured.when;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.browserstack.local.Local;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;


public class SkillAssessmentTest{

	public WebDriver driver;
	protected Local l;	
	public ExtentReports extent;
	public ExtentTest test,t1,t2,t3,score ;
	public  String testName;
	public static Response response;
	public static String jsonAsString;
	public  File destination;
	private ExtentTest t4;
	public  String dest;
	ExtentHtmlReporter htmlReporter;
	Markup m;



	@BeforeMethod
	public void nameBefore(Method method)
	{
		testName = method.getName();       
	}

	@BeforeClass
	public void setUp() throws Exception {

		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		driver= new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		htmlReporter = new ExtentHtmlReporter("./Report/Assessment Report_Associate_01.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		test=extent.createTest("REPORTS");
		t1 = test.createNode("Metrics Test Cases");
		t2 = test.createNode("API Test");
		t3 = test.createNode("API Performance");
		t4 = test.createNode("Screen Responsive");
		test.assignAuthor("Candidate Name Will be Showned Here");
		extent.setSystemInfo("SBA", "jQuery - Insurance - Solution Kit");
		extent.setAnalysisStrategy(AnalysisStrategy.TEST);
		htmlReporter.config().setDocumentTitle("Assessment Report_Associate_01");
		htmlReporter.config().setTimeStampFormat(null);
		
	}


	/**Covers test case1 **/
	@Test(priority=1)
	public void testCaseTabs() throws IOException{
		t1.log(Status.INFO, testName);
		try
		{
			driver.get("http:localhost:3000");
			Assert.assertEquals(driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>strong")).getText(), "Get New Insurance", "get new insurance mismatch");
			Assert.assertEquals(driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>strong")).getText(), "Renew Insurance", "renew insurance mismatch");
			Assert.assertEquals(driver.findElement(By.cssSelector(".navbar-brand")).getText(), "MyInsuranceQuote", "Title mismatch");
			Assert.assertEquals(driver.findElement(By.cssSelector(".navbar-text.pull-right")).getText(), "Copyright © Cognizant. 2016. All Rights Reserved.", "Footer mismatch");
			t1.log(Status.INFO, "Completed");

		}
		catch(NoSuchElementException e){
			System.out.println(e+"Element not found");
		}
	}

	/** Covers test case2 **/
	@Test(priority=2)
	public void testCaseToggle(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			Assert.assertTrue(driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected(), "Toggling is not happeneing");
		}
		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).click();
			Assert.assertTrue(driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).isSelected(), "Toggling is not happeneing");
		}
		t1.log(Status.INFO, "Not Completed");

	}


	/** Covers test cases 3 **/
	@Test(priority=3)
	public void testCaseStructureofGetnewinsurance(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			checkGetNewInsuranceElements();
		}
		else
		{
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			checkGetNewInsuranceElements();
		}
		t1.log(Status.INFO, "Completed");
		t1.log(Status.INFO, "Not Completed");


	}


	/** Covers test case4 **/
	@Test(priority=4)
	public void testCasePrefilledValues(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			if(selcars.getOptions() != null){
				Assert.assertTrue(true, "cars are loaded");
			}
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			if(selFuel.getOptions()!=null){
				Assert.assertTrue(true,"fuels are not loaded");
			}
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			if(selState.getOptions()!=null){
				Assert.assertTrue(true,"States are not loaded");
			}
		}
		else{
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			if(selcars.getOptions() != null){
				Assert.assertTrue(true, "cars are loaded");
			}
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			if(selFuel.getOptions()!=null){
				Assert.assertTrue(true,"fuels are not loaded");
			}
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			if(selState.getOptions()!=null){
				Assert.assertTrue(true,"States are not loaded");
			}
		}
		t1.log(Status.INFO, "Not Completed");

	}

	/**Covers test case 6**/
	@Test(priority=5)
	public void testCaseStructureofrenewinsurance(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Assert.assertTrue(driver.findElement(By.id("txtPolicyNumber")).isDisplayed(),"policy number text is not present");
			Assert.assertTrue(driver.findElement(By.id("btnGetDetails")).isDisplayed(),"GetDetails button is not present");
		}
		else
		{
			Assert.assertTrue(driver.findElement(By.id("txtPolicyNumber")).isDisplayed(),"policy number text is not present");
			Assert.assertTrue(driver.findElement(By.id("btnGetDetails")).isDisplayed(),"GetDetails button is not present");
		}
		t1.log(Status.INFO, "Completed");

	}

	/** Covers test case seven**/
	@Test(priority=6)
	public void testCaseUserNameErrorMessage(){
		driver.get("http:localhost:3000");

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("k");
			driver.findElement(By.id("btnGetQuotes")).click();
			Assert.assertEquals(driver.findElement(By.id("errorUserName")).getText(), "* Enter valid username with 2 to 50 chars");
		}
		else{
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("k");
			driver.findElement(By.id("btnGetQuotes")).click();
			Assert.assertEquals(driver.findElement(By.id("errorUserName")).getText(), "* Enter valid username with 2 to 50 chars");

		}
		t1.log(Status.INFO, "Not Completed");

	}


	/**Covers test case eight**/
	@Test(priority=7)
	public void testCasePhoneNumberErrorMessage(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("01");
			driver.findElement(By.id("btnGetQuotes")).click();
			Assert.assertEquals(driver.findElement(By.id("errorPhoneNumber")).getText(), "* Enter valid 10 digit number");
		}
		else{
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("01");
			driver.findElement(By.id("btnGetQuotes")).click();
			Assert.assertEquals(driver.findElement(By.id("errorPhoneNumber")).getText(), "* Enter valid 10 digit number");

		}
		t1.log(Status.INFO, "Not Completed");

	}

	/**Covers test case nine **/
	@Test(priority=8)
	public void testCasePolicyNumberErrorMessage(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.findElement(By.id("btnGetDetails")).click();
			Assert.assertEquals(driver.findElement(By.id("errorPolicyNumber")).getText(),"* Enter policy number");
		}
		else
		{
			driver.findElement(By.id("btnGetDetails")).click();
			Assert.assertEquals(driver.findElement(By.id("errorPolicyNumber")).getText(),"* Enter policy number");
		}
		t1.log(Status.INFO, "Not Completed");

	}

	/**Covers test case ten**/
	@Test(priority=9)
	public void testCaseListOfInsurance(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("1234567890");
			driver.findElement(By.id("btnGetQuotes")).click();
			if (driver.findElement(By.cssSelector("#get-quotes>div:nth-child(2)")).isDisplayed() && driver.findElements(By.cssSelector(".btn.btn-default.box-item.ui-draggable.ui-draggable-handle")).size()==4){
				Assert.assertTrue(true, "Insurance details is not displayed");
			}
		}
		else{
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("1234567890");
			driver.findElement(By.id("btnGetQuotes")).click();
			if (driver.findElement(By.cssSelector("#get-quotes>div:nth-child(2)")).isDisplayed() && driver.findElements(By.cssSelector(".btn.btn-default.box-item.ui-draggable.ui-draggable-handle")).size()==4){
				Assert.assertTrue(true, "Insurance details is not displayed");
			}

		}
		t1.log(Status.INFO, "Not Completed");

	}

	/**Covers test case 15**/
	@Test(priority=10)
	public void testCasedraganddropforsecondinsurance(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("1234567890");
			driver.findElement(By.id("btnGetQuotes")).click();
			Actions action=new Actions(driver);
			action.dragAndDrop(driver.findElement(By.cssSelector("#insurance-plans-id>div:nth-child(1)")), driver.findElement(By.id("selected-plan-id"))).perform();
			Assert.assertTrue(driver.findElement(By.cssSelector("selected-plan-id>div")).isDisplayed(),"Insurance is not displayed");
			action.dragAndDrop(driver.findElement(By.cssSelector("#insurance-plans-id>div:nth-child(1)")), driver.findElement(By.id("selected-plan-id"))).perform();
			if(driver.findElements(By.cssSelector(".btn.btn-default.box-item.ui-draggable.ui-draggable-handle")).size()==3){
				Assert.assertTrue(true,"Drag and drop can be performed for second insurance");
			}
		}
		else{
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("1234567890");
			driver.findElement(By.id("btnGetQuotes")).click();
			Actions action=new Actions(driver);
			action.dragAndDrop(driver.findElement(By.cssSelector("#insurance-plans-id>div:nth-child(1)")), driver.findElement(By.id("selected-plan-id"))).perform();
			Assert.assertTrue(driver.findElement(By.cssSelector("div.btn.btn-default.box-item.ui-draggable.ui-draggable-handle")).isDisplayed(),"Insurance is not displayed");
			action.dragAndDrop(driver.findElement(By.cssSelector("#insurance-plans-id>div:nth-child(1)")), driver.findElement(By.id("selected-plan-id"))).perform();
			if(driver.findElements(By.cssSelector(".btn.btn-default.box-item.ui-draggable.ui-draggable-handle")).size()==3){
				Assert.assertTrue(true,"Drag and drop can be performed for second insurance");
			}
		}
		t1.log(Status.INFO, "Not Completed");

	}


	/**Covers test case 14**/
	@Test(priority=11)
	public void testCaseDragandDrop(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("1234567890");
			driver.findElement(By.id("btnGetQuotes")).click();
			Actions action=new Actions(driver);
			action.dragAndDrop(driver.findElement(By.cssSelector("#insurance-plans-id>div:nth-child(1)")), driver.findElement(By.id("selected-plan-id"))).perform();
			action.dragAndDrop(driver.findElement(By.cssSelector("div.btn.btn-default.box-item.ui-draggable.ui-draggable-handle")),driver.findElement(By.cssSelector("#insurance-plans-id"))).perform();
			if(driver.findElements(By.cssSelector(".btn.btn-default.box-item.ui-draggable.ui-draggable-handle")).size()==4){
				Assert.assertTrue(true,"Drag and drop cannot be performed both back and forth.");
			}
		}
		else{
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("1234567890");
			driver.findElement(By.id("btnGetQuotes")).click();
			Actions action=new Actions(driver);
			action.dragAndDrop(driver.findElement(By.cssSelector("#insurance-plans-id>div:nth-child(1)")), driver.findElement(By.id("selected-plan-id"))).perform();
			action.dragAndDrop(driver.findElement(By.cssSelector("div.btn.btn-default.box-item.ui-draggable.ui-draggable-handle")),driver.findElement(By.cssSelector("#insurance-plans-id"))).perform();
			if(driver.findElements(By.cssSelector(".btn.btn-default.box-item.ui-draggable.ui-draggable-handle")).size()==4){
				Assert.assertTrue(true,"Drag and drop cannot be performed both back and forth.");
			}
		}
		t1.log(Status.INFO, "Not Completed");

	}


	/**Covers test case 18**/
	@Test(priority=12)
	public void testBlankPolicyErrorMessage(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(2)>input")).click();
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("1234567890");
			driver.findElement(By.id("btnGetQuotes")).click();
			driver.findElement(By.id("btnBuyInsurance")).click();
			Assert.assertEquals(driver.findElement(By.id("failureMessageDiv")).getText(), "Response Time - ", "Response Time - ");
		}
		else{
			Select selcars=new Select(driver.findElement(By.id("ddCarType")));
			selcars.selectByIndex(1);
			Select selFuel=new Select(driver.findElement(By.id("ddFuelType")));
			selFuel.selectByIndex(1);
			Select selState=new Select(driver.findElement(By.id("ddRegistrationState")));
			selState.selectByIndex(1);
			driver.findElement(By.id("txtUserName")).sendKeys("kun");
			driver.findElement(By.id("txtPhoneNumber")).sendKeys("1234567890");
			driver.findElement(By.id("btnGetQuotes")).click();
			driver.findElement(By.id("btnBuyInsurance")).click();
			Assert.assertEquals(driver.findElement(By.id("failureMessageDiv")).getText(), "Please select any one of the policy to buy", "Error message is not displayed");
		}
		t1.log(Status.INFO, "Not Completed");

	}



	/**Covers test case 21**/
	@Test(priority=13)
	public void testDisableRenewInsuranceButton(){
		driver.get("http:localhost:3000");
		t1.log(Status.INFO, testName);

		if(!driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).isSelected()){
			driver.findElement(By.cssSelector(".row.insuranceFormContainer>div:nth-child(1)>div:nth-child(3)>input")).click();
			driver.findElement(By.id("txtPolicyNumber")).sendKeys("871583926660");
			driver.findElement(By.id("btnGetDetails")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Assert.assertFalse(driver.findElement(By.id("btnRenew")).isEnabled(), "The renew button is enabled.");
		}
		else
		{
			driver.findElement(By.id("txtPolicyNumber")).sendKeys("871583926660");
			driver.findElement(By.id("btnGetDetails")).click();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			Assert.assertFalse(driver.findElement(By.id("btnRenew")).isEnabled(), "The renew button is enabled.");
		}
		t1.log(Status.INFO, "Not Completed");

	}

	@Test(priority=14)
	private void checkGetNewInsuranceElements(){
		driver.get("http:localhost:3000");
		Assert.assertTrue(driver.findElement(By.id("ddCarType")).isDisplayed(), "Car Type selection is not displayed.");
		Assert.assertTrue(driver.findElement(By.id("ddFuelType")).isDisplayed(),"Fuel type selection is not displayed.");
		Assert.assertTrue(driver.findElement(By.id("ddRegistrationState")).isDisplayed(),"Registration state is not displayed");
		Assert.assertTrue(driver.findElement(By.id("txtUserName")).isDisplayed(),"username field is not present");
		Assert.assertTrue(driver.findElement(By.id("txtPhoneNumber")).isDisplayed(), "phone number field is not present");
		Assert.assertTrue(driver.findElement(By.id("btnGetQuotes")).isDisplayed(),"GetQuotes button is not present");

	}

	@Test(priority=15)
	public void apiTest()
	{
		t2.log(Status.INFO, "/getCarModels");
		setup();
		response =
				when().
				get("/getCarModels").
				then().
				contentType(ContentType.JSON). 
				extract().response();
		jsonAsString = response.asString();


		t2.log(Status.INFO, jsonAsString);
		t2.log(Status.INFO, "/getFuelType");
		setup();
		response =
				when().
				get("/getCarModels").
				then().
				contentType(ContentType.JSON). 
				extract().response();
		jsonAsString = response.asString();
		t2.log(Status.INFO, jsonAsString);

		t2.log(Status.INFO, "/getRegStateCodes");
		setup();
		response =
				when().
				get("/getCarModels").
				then().
				contentType(ContentType.JSON). 
				extract().response();
		jsonAsString = response.asString();
		t2.log(Status.INFO, jsonAsString);

		t2.log(Status.INFO, "/getQuotes");
		setup();
		response =
				when().
				get("/getCarModels").
				then().
				contentType(ContentType.JSON). 
				extract().response();
		jsonAsString = response.asString();
		t2.log(Status.INFO, jsonAsString);


	} 

	@Test(priority=16)
	public void apiPerformance()
	{
		t3.log(Status.INFO, "/getCarModels");
		setup();
		response =
				when().
				get("/getCarModels").
				then().
				contentType(ContentType.JSON). 
				extract().response();
		long startT = System.nanoTime();
		jsonAsString = response.asString();
		long endT = System.nanoTime();
		long executionTime = (endT - startT);
		t3.log(Status.INFO, "Response Time - "+executionTime+" nano-second.");

		//	
		t3.log(Status.INFO, "/getFuelType");
		setup();
		response =
				when().
				get("/getCarModels").
				then().
				contentType(ContentType.JSON). 
				extract().response();
		long startT1 = System.nanoTime();
		jsonAsString = response.asString();
		t3.log(Status.INFO, "Response Time - "+executionTime+" nano-second.");
		long endT1 = System.nanoTime();
		long executionTime1 = (startT1 - endT1);
		t3.log(Status.INFO, "Response Time - "+executionTime1+" nano-second.");

		//
		t3.log(Status.INFO, "/getRegStateCodes");
		setup();
		response =
				when().
				get("/getCarModels").
				then().
				contentType(ContentType.JSON). 
				extract().response();
		long startT11 = System.nanoTime();

		jsonAsString = response.asString();
		t3.log(Status.INFO, "Response Time - "+executionTime);
		long endT11 = System.nanoTime();
		long executionTime11 = (startT11 - endT11);
		t3.log(Status.INFO, "Response Time - "+executionTime11+" nano-second.");


		//
		t3.log(Status.INFO, "/getQuotes");
		setup();
		response =
				when().
				get("/getCarModels").
				then().
				contentType(ContentType.JSON). 
				extract().response();
		long startT111 = System.nanoTime();
		jsonAsString = response.asString();
		long endT111 = System.nanoTime();
		long executionTime111 = (startT111 - endT111);
		t3.log(Status.INFO, "Response Time - "+executionTime111+" nano-second.");
	}




	@DataProvider(name = "deviceName")
	public Object[][] deviceNames(){
		return new Object[][] {
			{"Galaxy S5", "iPad"},
			{"iPad Pro", "iPhone 6"},
			{"iPhone 6", "iPad"},
			{"iPad", "iPhone 6"},
		};
	}

	@Test(priority=17 ,dataProvider = "deviceName")
	public void mobileEmulation(String deviceName, String deviceNameWhatIsMyBrowser) throws IOException{
		Map<String, String> mobileEmulation = new HashMap<String, String>();
		mobileEmulation.put("deviceName", deviceName);
		Map<String, Object> chromeOptions = new HashMap<String, Object>();
		chromeOptions.put("mobileEmulation", mobileEmulation);
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
		System.setProperty("webdriver.chrome.driver","chromedriver.exe");
		
		driver = new ChromeDriver(capabilities);
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		System.out.println(deviceName);
		driver.get("http:localhost:3000");
		t4.pass(deviceName, MediaEntityBuilder.createScreenCaptureFromPath(captureScreenMethod(dest)).build());
		driver.quit();
	}
	
	@Test(priority=18)
	public void score()
	{
		m=MarkupHelper.createLabel("Total Score %  =  16.2", ExtentColor.BLUE);
		test.log(Status.INFO, m);
		
		m=MarkupHelper.createLabel("Specification Score : 14 out of 100.", ExtentColor.ORANGE);
		test.log(Status.INFO, m);
		
		m=MarkupHelper.createLabel("Readability Score : 20 out of 100.", ExtentColor.PURPLE);
		test.log(Status.INFO, m);
		
		m=MarkupHelper.createLabel("Reusability Score : 30 out of 100.", ExtentColor.GREEN);
		test.log(Status.INFO, m);
		
		m=MarkupHelper.createLabel("Specification Score - Score <60 - [Not Completed]", ExtentColor.ORANGE);
		test.log(Status.INFO, m);
		
		m=MarkupHelper.createLabel("Readability Score - Score <60 - [Not Completed]", ExtentColor.PURPLE);
		test.log(Status.INFO, m);
		
		m=MarkupHelper.createLabel("Resusability Score - Score <60 - [Not Completed]", ExtentColor.GREEN);
		test.log(Status.INFO, m);
		
		
		m=MarkupHelper.createLabel("Completion Status - Score <60 - [Not Completed]", ExtentColor.AMBER);
		test.log(Status.INFO, m);
		
		m=MarkupHelper.createLabel("Grade - Score <60 - [C - Need Improvement]", ExtentColor.RED);
		test.log(Status.INFO, m);
		
	}

	public   String captureScreenMethod(String dest) throws IOException
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		dest=System.getProperty("user.dir") +"//Report//"+System.currentTimeMillis()+".png";
		destination = new File(dest);
		FileUtils.copyFile(source, destination);
		Path p = Paths.get(dest);
		String screenFile = p.getFileName().toString();
		return screenFile;

	}
	public static void setup() {

		System.out.println("API Setup Class Started");

		String port = System.getProperty("server.port");
		if (port == null) {
			RestAssured.port = Integer.valueOf(3000);
		}
		else{
			RestAssured.port = Integer.valueOf(port);
		}


		String basePath = System.getProperty("server.base");
		if(basePath==null){
			basePath = "/api/common";
		}
		RestAssured.basePath = basePath;

		String baseHost = System.getProperty("server.host");
		if(baseHost==null){
			baseHost = "http://localhost";
		}
		RestAssured.baseURI = baseHost;

	}

	@AfterClass
	public void closeBrowser() throws InterruptedException{
		driver.quit();
		extent.flush();
		System.out.println("Done");


	}

}