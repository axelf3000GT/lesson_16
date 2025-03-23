import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {
    private WebDriver driver;

    private By agreeButtonLocator = By.xpath("//button[contains(text(), 'Принять')]");

    private By blockTitleLocator = By.xpath("//*[@class='pay__wrapper']/h2");
    private By selectNowLocator = By.className("select__now");
    private By selectItemLocator = By.className("select__item");
    private By selectDetailsLinkLocator = By.xpath("//a[contains(text(), 'Подробнее о сервисе')]");
    private By selectlogosLocator = By.xpath("//*[@class='pay__partners']//ul//li");
    private By iframeLocator = By.xpath("//iframe[contains(@class, 'bepaid-iframe')]");
    private By phoneInputLocator = By.xpath("//input[@id='connection-phone']");
    private By sumInputLocator = By.xpath("//input[@id='connection-sum']");
    private By emailInputLocator = By.xpath("//input[@id='connection-email']");
    private By continueButtonLocator = By.xpath("//div[@class='pay__forms']//form[contains(@class, 'pay-form') and contains(@class, 'opened')]//button[contains(@class, 'button__default') and text()='Продолжить']");
    private By inputFields = By.tagName("input");

    public By payConnectionForm = By.id("pay-connection");
    public By payInternetForm = By.id("pay-internet");
    public By payInstalmentForm = By.id("pay-instalment");
    public By payArrearsForm = By.id("pay-arrears");

    public List<WebElement> getInputFieldsInForm(By formLocator) {
        WebElement form = driver.findElement(formLocator);
        return form.findElements(inputFields);
    }

    public List<WebElement> checkPlaceholdersInForm(By formLocator, String[] expectedPlaceholders) {
        return getInputFieldsInForm(formLocator);
    }

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getBlockTitle() {
        WebElement blockTitle = driver.findElement(blockTitleLocator);
        return blockTitle.getText();
    }

    public void selectPaymentOption(String option) {
        driver.findElement(selectNowLocator).click();
        List<WebElement> selectItems = driver.findElements(selectItemLocator);
        for (WebElement item : selectItems) {
            WebElement optionElement = item.findElement(By.className("select__option"));
            if (optionElement.getText().equals(option)) {
                item.click();
                break;
            }
        }
    }

    public void fillPaymentDetails(String phone, String sum, String email) {
        driver.findElement(phoneInputLocator).sendKeys(phone);
        driver.findElement(sumInputLocator).sendKeys(sum);
        driver.findElement(emailInputLocator).sendKeys(email);
    }

    public List<WebElement> selectLogosOption() {
        return driver.findElements(selectlogosLocator);
    }

    public WebElement getDetailsLink() {
        return driver.findElement(selectDetailsLinkLocator);
    }

    public WebElement getContinueButton() {
        return driver.findElement(continueButtonLocator);
    }

    public List<WebElement> getAgreeButton() {
        return driver.findElements(agreeButtonLocator);
    }

    public WebElement clickContinueButton() throws InterruptedException {
        getContinueButton().click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
        Thread.sleep(3000);
        return iframeElement;
    }
}