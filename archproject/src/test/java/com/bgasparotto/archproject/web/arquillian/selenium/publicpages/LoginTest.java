package com.bgasparotto.archproject.web.arquillian.selenium.publicpages;

import org.jboss.arquillian.persistence.UsingDataSet;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.bgasparotto.archproject.web.arquillian.selenium.SeleniumTestCase;

public class LoginTest extends SeleniumTestCase {

	@Override
	protected String targetUrl() {
		return "localhost:8080/archproject";
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		WebElement signInLink = driver.findElement(By.name("sign-in"));
		signInLink.click();
	}
	
	@Test
	public void shouldFindLogInElements() throws Exception {
		WebElement usernameInput = driver.findElement(By.id("j_username"));
		Assert.assertNotNull(usernameInput);
		
		WebElement passwordInput = driver.findElement(By.id("j_password"));
		Assert.assertNotNull(passwordInput);
		
		WebElement loginButton = driver.findElement(By.name("loginButton"));
		Assert.assertNotNull(loginButton);
	}
	
	@Test
	@UsingDataSet("DbUnit/dbunit-test-db.xml")
	public void shouldLoginToWelcomePageAndLogout() throws Exception {
	    WebElement usernameInput = driver.findElement(By.id("j_username"));
	    usernameInput.sendKeys("admin");
	    
	    WebElement passwordInput = driver.findElement(By.id("j_password"));
	    passwordInput.sendKeys("administrator");
	    
	    WebElement loginButton = driver.findElement(By.id("loginButton"));
		loginButton.click();	
		
		driver.findElement(By.id("main"));	
		driver.findElement(By.id("content"));
		
	    WebElement logoutButton = driver.findElement(By.id("logoutButton"));
		logoutButton.click();
	}
	
	@Test(expected=NoSuchElementException.class)
	@UsingDataSet("DbUnit/dbunit-test-db.xml")
	public void shouldFailWithIncorrectUserAndShowErrorMessage()
			throws Exception {
		WebElement usernameInput = driver.findElement(By.id("j_username"));
		usernameInput.sendKeys("admin");

		WebElement passwordInput = driver.findElement(By.id("j_password"));
		passwordInput.sendKeys("wrongpassword");

		WebElement loginButton = driver.findElement(By.id("loginButton"));
		loginButton.click();

		driver.findElement(By.id("logoutButton"));
	}
	
	@Test(expected=NoSuchElementException.class)
	@UsingDataSet("DbUnit/dbunit-test-db.xml")
	public void shouldFailWithIncorrectPasswordAndShowErrorMessage()
			throws Exception {
		WebElement usernameInput = driver.findElement(By.id("j_username"));
		usernameInput.sendKeys("wronguser");

		WebElement passwordInput = driver.findElement(By.id("j_password"));
		passwordInput.sendKeys("administrator");

		WebElement loginButton = driver.findElement(By.id("loginButton"));
		loginButton.click();

		driver.findElement(By.id("logoutButton"));
	}
}