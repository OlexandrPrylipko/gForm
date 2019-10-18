package settings;

import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import libraries.ReadPropertiesFile;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.util.concurrent.TimeUnit;

import static libraries.ReadPropertiesFile.getProperty;
import static settings.Browser.getByName;

public class BaseTest {

    protected WebDriver driver;
    private ReadPropertiesFile property = new ReadPropertiesFile();
    private Logger LOG = Logger.getLogger(getClass());

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {

        @Override
        protected void failed(Throwable e, Description description) {
            screenshot();
        }

        @Override
        protected void finished(Description description) {
            LOG.info(String.format("Finished test: %s::%s", description.getClassName(), description.getMethodName()));
            try {
                screenshot();
                driver.quit();
            } catch (Exception e) {
                LOG.error(e);
            }
        }

        @Attachment(value = "Page screenshot", type = "image/png")
        private byte[] saveScreenshot(byte[] screenShot) {
            return screenShot;
        }

        private void screenshot() {
            if (driver == null) {
                LOG.info("Driver for screenshot not found");
                return;
            }
            saveScreenshot(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
        }
    };

    @Before
    public void openBrowser() {
        initDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

    private void initDriver() {
        if (driver == null) {
            Browser browser = getByName(getProperty("BROWSER"));
            assert browser != null;
            switch (browser) {
                case CHROME_WINDOWS:
                    System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win.exe");
                    driver = new ChromeDriver();
                    break;
                case CHROME_WINDOWS_HEADLESS:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_win.exe");
                    chromeOptions.setHeadless(true);
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1366,768");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case CHROME_LINUX:
                    System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_linux");
                    driver = new ChromeDriver();
                    break;
                case CHROME_LINUX_HEADLESS:
                    chromeOptions = new ChromeOptions();
                    System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_linux");
                    chromeOptions.setHeadless(true);
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("--window-size=1366,768");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case CHROME_MAC:
                    System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_mac");
                    driver = new ChromeDriver();
                    break;
                case CHROME_MAC_HEADLESS:
                    chromeOptions = new ChromeOptions();
                    System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver_mac");
                    chromeOptions.setHeadless(true);
                    chromeOptions.addArguments("--headless");
                    driver = new ChromeDriver(chromeOptions);
                    break;
                case FIREFOX_WINDOWS:
                    System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver_win.exe");
                    driver = new FirefoxDriver();
                    break;
                case FIREFOX_WINDOWS_HEADLESS:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver_win.exe");
                    firefoxOptions.setHeadless(true);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case FIREFOX_LINUX:
                    System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver_linux");
                    driver = new FirefoxDriver();
                    break;
                case FIREFOX_LINUX_HEADLESS:
                    firefoxOptions = new FirefoxOptions();
                    System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver_linux");
                    firefoxOptions.setHeadless(true);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case FIREFOX_MAC:
                    System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver_mac");
                    driver = new FirefoxDriver();
                    break;
                case FIREFOX_MAC_HEADLESS:
                    firefoxOptions = new FirefoxOptions();
                    System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver_mac");
                    firefoxOptions.setHeadless(true);
                    driver = new FirefoxDriver(firefoxOptions);
                    break;
                case SAFARI_MAC:
                    System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
                    driver = new SafariDriver();
                    break;
                case OPERA_WINDOWS:
                    System.setProperty("webdriver.opera.driver", "./drivers/operadriver_win.exe");
                    driver = new OperaDriver();
                    break;
                default:
                    throw new IllegalStateException("Unsupported browser type: " + "'" + browser + "'");
            }
        }
    }

    @Step("Check expected result")
    protected void checkExpectedResult(String errorMessage, boolean actualResult) {
        Assert.assertTrue(errorMessage, actualResult);
        LOG.info("Expected element is displayed => " + true);
    }

    @Step("Check expected result")
    protected void checkExpectedResult(String errorMessage, String expectedResult, String actualResult) {
        Assert.assertEquals(errorMessage, expectedResult, actualResult);
        LOG.info("Expected result is correct: " + "'" + actualResult + "'");
    }
}