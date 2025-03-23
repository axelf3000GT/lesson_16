import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.util.List;

public class TestClasses {
    static WebDriver driver;
    HomePage homePage;
    PaymentPage paymentPage;
    private static final String SUMMA_RUB = "10.00";

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.mts.by");
        homePage = new HomePage(driver);

        List<WebElement> agreeButton = homePage.getAgreeButton();
        if (!agreeButton.isEmpty()) {
            agreeButton.get(0).click();
        }
    }

    @Test(priority = 1)
    public void testBlockTitle() {
        Assert.assertEquals(homePage.getBlockTitle(), "Онлайн пополнение\nбез комиссии", "Название блока не совпадает");
    }

    @Test(priority = 2)
    public void testPaymentLogos() {
        List<WebElement> logos = homePage.selectLogosOption();
        System.out.println(logos);
        Assert.assertFalse(logos.isEmpty(), "Логотипы платёжных систем отсутствуют");
    }

    @Test(priority = 3)
    public void testDetailsLink() {
        WebElement detailsLink = homePage.getDetailsLink();
        detailsLink.click();
        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/"), "Ссылка 'Подробнее о сервисе' не работает");
        driver.navigate().back();
    }

    @Test(priority = 4)
    public void testPlaceholdersInPayConnectionForm() {
        String[] expectedPlaceholders = {"Номер телефона", "Сумма", "E-mail для отправки чека"};
        checkPlaceholderList(homePage.checkPlaceholdersInForm(homePage.payConnectionForm, expectedPlaceholders), expectedPlaceholders);
    }

    @Test(priority = 5)
    public void testPlaceholdersInPayInternetForm() {
        String[] expectedPlaceholders = {"Номер абонента", "Сумма", "E-mail для отправки чека"};
        checkPlaceholderList(homePage.checkPlaceholdersInForm(homePage.payInternetForm, expectedPlaceholders), expectedPlaceholders);
    }

    @Test(priority = 6)
    public void testPlaceholdersInPayInstalmentForm() {
        String[] expectedPlaceholders = {"Номер счета на 44", "Сумма", "E-mail для отправки чека"};
        checkPlaceholderList(homePage.checkPlaceholdersInForm(homePage.payInstalmentForm, expectedPlaceholders), expectedPlaceholders);
    }

    @Test(priority = 7)
    public void testPlaceholdersInPayArrearsForm() {
        String[] expectedPlaceholders = {"Номер счета на 2073", "Сумма", "E-mail для отправки чека"};
        checkPlaceholderList(homePage.checkPlaceholdersInForm(homePage.payArrearsForm, expectedPlaceholders), expectedPlaceholders);
    }



    @Test(priority = 8)
    public void testContinueButton() throws InterruptedException {
        homePage.selectPaymentOption("Услуги связи");
        homePage.fillPaymentDetails("297777777", SUMMA_RUB, "7999967@gmail.com");
        WebElement iframe = homePage.clickContinueButton();
        Assert.assertTrue(iframe != null, "Iframe не загрузился");
    }

    @Test(priority = 9)
    public void testPaymentDataFieldPage() {
        paymentPage = new PaymentPage(driver);
        Assert.assertEquals(paymentPage.getAmount(), SUMMA_RUB + " BYN", "Сумма неверная");
        Assert.assertEquals(paymentPage.getButtonAmount(), "Оплатить " + SUMMA_RUB + " BYN", "Сумма неверная");
        Assert.assertTrue(paymentPage.getPhoneNumber().contains("375297777777"),  "Номер телефона неверный");
        Assert.assertTrue(!paymentPage.paymentcardIconsList().isEmpty(), "Иконки платёжных карт отсутствуют");
    }

    @Test(priority = 10)
    public void testPlaceholdersInPaymentForm() {
        Assert.assertEquals(paymentPage.getEmptyFieldPlaceholder(paymentPage.cardNumberLocator), "Номер карты", "Неверный PLACEHOLDER Номер Карты");
        Assert.assertEquals(paymentPage.getEmptyFieldPlaceholder(paymentPage.expirationDateLocator), "Срок действия", "Неверный PLACEHOLDER Срок действия");
        Assert.assertEquals(paymentPage.getEmptyFieldPlaceholder(paymentPage.cvcLocator), "CVC", "Неверный PLACEHOLDER CVC");
        Assert.assertEquals(paymentPage.getEmptyFieldPlaceholder(paymentPage.holderLocator), "Имя держателя (как на карте)", "Неверный PLACEHOLDER Имя держателя (как на карте)");
    }


    public void checkPlaceholderList (List<WebElement> actualPlaceHolderList, String[] expectedPlaceholders){
        for (int i = 0; i < actualPlaceHolderList.size(); i++) {
            String placeholder = actualPlaceHolderList.get(i).getAttribute("placeholder");
            Assert.assertTrue(placeholder != null && placeholder.equals(expectedPlaceholders[i]),
                    "Ожидаемая надпись: " + expectedPlaceholders[i] + ", но найдено: " + placeholder);
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
