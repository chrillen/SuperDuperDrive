package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

import java.sql.Driver;
import java.util.function.Function;

public class HomePage {

    @FindBy(css="#logout-button")
    private WebElement logoutField;

    //region Notes
    @FindBy(xpath = "//*[@id=\"addnote-button\"]")
    private WebElement addnoteField;

    @FindBy(id = "noteSubmit")
    private WebElement notesubmitField;

    @FindBy(id = "note-title")
    private WebElement noteTitleField;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionField;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr[last()]/th")
    private WebElement noteContentField;

    @FindBy(xpath = "//*[@id=\"nav-notes-tab\"]")
    private WebElement noteNavigationField;

    @FindBy(id = "noteModal")
    private WebElement noteModelField;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/button")
    private WebElement noteEditField;

    @FindBy(xpath = "//*[@id=\"userTable\"]/tbody/tr/td[1]/a")
    private WebElement noteDeleteField;
    //endregion

    //region Credentials
    @FindBy(xpath = "//*[@id=\"addcredential-button\"]")
    private WebElement addCredentialField;

    @FindBy(id = "credentialSubmit")
    private WebElement credentialSubmitField;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[last()]/th")
    private WebElement credentialUrlContentField;

    @FindBy(xpath = "//*[@id=\"nav-credentials-tab\"]")
    private WebElement credentialNavigationField;

    @FindBy(id = "credentialModal")
    private WebElement credentialModelField;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[last()]/td[1]/button")
    private WebElement credentialEditField;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[last()]/td[1]/a")
    private WebElement credentialDeleteField;

    @FindBy(xpath = "//*[@id=\"credentialTable\"]/tbody/tr[last()]/td[3]")
    private WebElement credentialPasswordContentField;
    //endregion

    @FindBy(xpath = "//*[@id=\"confirm-delete\"]/div/div/div[3]/a")
    private WebElement allDeleteModalField;

    public HomePage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
    }

    //region NotesTests
    public void createNote(String title,String description, WebDriver driver) {
        this.noteNavigationField.click();
        var script = this.addnoteField.getAttribute("onclick");
        var js = (JavascriptExecutor)driver;
        js.executeScript(script);
        driver.switchTo().activeElement();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.visibilityOf(this.noteDescriptionField));
        w.until(ExpectedConditions.visibilityOf(this.noteTitleField));
        this.noteDescriptionField.sendKeys(description);
        this.noteTitleField.sendKeys(title);
        this.notesubmitField.submit();
    }

    public void editNote(String title,String description, WebDriver driver) {
        this.noteNavigationField.click();
        var script = this.noteEditField.getAttribute("onclick");
        var js = (JavascriptExecutor)driver;
        js.executeScript(script);
        driver.switchTo().activeElement();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.visibilityOf(this.noteDescriptionField));
        w.until(ExpectedConditions.visibilityOf(this.noteTitleField));
        this.noteDescriptionField.clear();
        this.noteDescriptionField.sendKeys(description);
        this.noteTitleField.clear();
        this.noteTitleField.sendKeys(title);
        this.notesubmitField.submit();
    }

    public void deleteNote(WebDriver driver) {
        this.noteNavigationField.click();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.elementToBeClickable(this.noteDeleteField));
        this.noteDeleteField.click();
        driver.switchTo().activeElement();
        w.until(ExpectedConditions.elementToBeClickable(this.allDeleteModalField));
        this.allDeleteModalField.click();
    }

    public String getNoteContent() {
        this.noteNavigationField.click();
        try {
            return this.noteContentField.getAttribute("innerHTML");
        } catch (NoSuchElementException ex) {
            return "";
        }
    }
    //endregion

    //region CredentialsTest
    public void createCredential(String url,String username,String password, WebDriver driver) {
        this.credentialNavigationField.click();
        var script = this.addCredentialField.getAttribute("onclick");
        var js = (JavascriptExecutor)driver;
        js.executeScript(script);
        driver.switchTo().activeElement();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.visibilityOf(this.credentialUrlField));
        w.until(ExpectedConditions.visibilityOf(this.credentialUsernameField));
        w.until(ExpectedConditions.visibilityOf(this.credentialPasswordField));
        this.credentialUrlField.sendKeys(url);
        this.credentialPasswordField.sendKeys(password);
        this.credentialUsernameField.sendKeys(username);
        this.credentialSubmitField.submit();
    }

    public String editCredential(String url, String username, String password,WebDriver driver) {
        this.credentialNavigationField.click();
        var script = this.credentialEditField.getAttribute("onclick");
        var js = (JavascriptExecutor) driver;
        js.executeScript(script);
        driver.switchTo().activeElement();
        var w = new WebDriverWait(driver, 10);
        w.until(ExpectedConditions.visibilityOf(this.credentialPasswordField));
        w.until(ExpectedConditions.visibilityOf(this.credentialUsernameField));
        w.until(ExpectedConditions.visibilityOf(this.credentialUrlField));
         var decryptedPassword =  driver.findElement(By.xpath("//*[@id=\"credential-password\"]")).getAttribute("value");
        this.credentialUrlField.clear();
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.clear();
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.clear();
        this.credentialPasswordField.sendKeys(password);
        this.credentialSubmitField.submit();
        return decryptedPassword;
    }

    public void deleteCredential(WebDriver driver) {
        this.credentialNavigationField.click();
        var w = new WebDriverWait(driver,3);
        w.until(ExpectedConditions.elementToBeClickable(this.credentialDeleteField));
        this.credentialDeleteField.click();
        driver.switchTo().activeElement();
        w.until(ExpectedConditions.elementToBeClickable(this.allDeleteModalField));
        this.allDeleteModalField.click();
    }

    public String getCredentialPassword() {
        this.credentialNavigationField.click();
        try {
            return this.credentialPasswordContentField.getAttribute("innerHTML");
        } catch (NoSuchElementException ex) {
            return "";
        }
    }

    public String getCredentialUrl() {
        this.credentialNavigationField.click();
        try {
            return this.credentialUrlContentField.getAttribute("innerHTML");
        } catch (NoSuchElementException ex) {
            return "";
        }
    }
    //endregion

    public void logout() {
        this.logoutField.submit();
    }
}
