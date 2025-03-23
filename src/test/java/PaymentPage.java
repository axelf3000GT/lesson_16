import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PaymentPage {
    private WebDriver driver;

    private By amountLocator = By.xpath("//span[contains(text(), 'BYN')]");
    private By amountButtonLocator = By.xpath("//button[contains(text(), 'Оплатить') and contains(text(), 'BYN')]");
    private By phoneNumberLocator = By.xpath("//span[contains(normalize-space(.), 'Оплата: Услуги связи Номер:')]");
    private By paymentCardIconsLocator = By.xpath("//div[contains(@class, 'cards-brands') and not(contains(@class, 'cards-brands_random'))]/img");

    public By cardNumberLocator = By.xpath("//label[contains(text(), 'Номер карты')]");
    public By expirationDateLocator = By.xpath("//label[contains(text(), 'Срок действия')]");
    public By cvcLocator = By.xpath("//label[contains(text(), 'CVC')]");
    public By holderLocator = By.xpath("//label[contains(text(), 'Имя держателя (как на карте)')]");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getAmount() {
        return driver.findElement(amountLocator).getText();
    }

    public String getButtonAmount() {
        return driver.findElement(amountButtonLocator).getText();
    }

    public String getPhoneNumber() {
        return driver.findElement(phoneNumberLocator).getText();
    }

    public String getEmptyFieldPlaceholder(By xpathField) {
        return driver.findElement(xpathField).getText();
    }

    public List<WebElement> paymentcardIconsList() {
        return driver.findElements(paymentCardIconsLocator);
    }
}