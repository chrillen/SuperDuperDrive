package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.controller.HomeController;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private String baseURL;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		baseURL = baseURL = "http://localhost:" + port;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	private void login(String username,String password) {
		driver.get(baseURL + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	private void signUp(String firstname,String lastname,String username,String password) {
		driver.get(baseURL + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);
	}

	@Test
	public  void testUnauthorizedAccessRestrictions() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void testSignupLogin() {
		this.signUp("Christian", "Sasse","chrille","test");
		this.login("chrille","test");

		HomePage homePage = new HomePage(driver);

		Assertions.assertEquals("Home", driver.getTitle());
		homePage.logout();
		Assertions.assertNotEquals("Home",driver.getTitle());
	}

	@Test
	public void testCreatesANote() {
		this.signUp("Christian", "Sasse","chrille","test");
		this.login("chrille","test");

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.createNote("test note title","blah blah blah",driver);
		driver.get(baseURL + "/home");

		Assertions.assertTrue(homePage.getNoteContent().contains("test note title"));
	}

	@Test
	public void testEditANote()  {
		this.signUp("Christian", "Sasse","chrille","test");
		this.login("chrille","test");

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.createNote("test note title","blah blah blah",driver);
		driver.get(baseURL + "/home");

		homePage.editNote("updating test title","updating note",driver);
		driver.get(baseURL + "/home");

		Assertions.assertTrue(homePage.getNoteContent().contains("updating test title"));
	}

	@Test
	public void testDeleteANote() {
		this.signUp("Christian", "Sasse","chrille","test");
		this.login("chrille","test");

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.createNote("test note title","blah blah blah",driver);
		driver.get(baseURL + "/home");

		homePage.deleteNote(driver);
		driver.get(baseURL + "/home");

		Assertions.assertTrue(homePage.getNoteContent().isBlank());
	}

	@Test
	public void testCreatesACredential() {
		this.signUp("Christian", "Sasse","chrille","test");
		this.login("chrille","test");

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.createCredential("www.idg.se","chrille","q2321321",driver);
		driver.get(baseURL + "/home");

		Assertions.assertFalse(homePage.getCredentialPassword().contains("q2321321"));
		Assertions.assertTrue(homePage.getCredentialUrl().contains("www.idg.se"));
	}

	@Test
	public void testEditACredential() {
		this.signUp("Christian", "Sasse","chrille","test");
		this.login("chrille","test");

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.createCredential("www.idg.se","chrille2","q2321321",driver);
		driver.get(baseURL + "/home");

		var decryptedPassword = homePage.editCredential("www.google.com","test" ,"aaaaa", driver);
		driver.get(baseURL + "/home");


		Assertions.assertTrue(homePage.getCredentialUrl().contains("www.google.com"));
		Assertions.assertTrue(decryptedPassword.equals("q2321321"));
	}

	@Test
	public void testDeleteCredential() {
		this.signUp("Christian", "Sasse","chrille","test");
		this.login("chrille","test");

		driver.get(baseURL + "/home");
		HomePage homePage = new HomePage(driver);

		homePage.createCredential("www.expressen.se","chrille3321245","aQWEQWE",driver);
		driver.get(baseURL + "/home");

		homePage.deleteCredential(driver);
		driver.get(baseURL + "/home");

		System.out.println(homePage.getCredentialUrl());

		Assertions.assertFalse(homePage.getCredentialUrl().contains("www.expressen.se"));
	}
}
