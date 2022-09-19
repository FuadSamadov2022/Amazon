package com.amazon.utils;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Driver {
    private Driver() {
    }

    private static ThreadLocal<WebDriver> driverPool = new ThreadLocal<>();
    private static final String WINDOW_SIZE = "window-size=1680,1050";
    private final static Logger testLogger = LogManager.getLogger(Driver.class.getSimpleName());

    public static WebDriver getDriver() {
        if (driverPool.get() == null) {
            // check the command line argument browser. if it has value, use that value
            // if no browser value is passed from command line, the user properties file
            // mvn test -Dbrowser=remote-chrome
            // mvn test -Dbrowser=remote-firefox
            // mvn test -Dcucumber.filter.tags=@regression -Dbrowser=remote-firefox
            String browser = System.getProperty("browser") != null ? System.getProperty("browser") : ConfigurationReader.get("browser");
            testLogger.info("About to set up browser " + browser.toUpperCase());
            switch (browser) {
                case "chrome":
                    testLogger.debug("Configuring a Chrome Browser Driver");
                    WebDriverManager.chromedriver().setup();
                    ChromeDriver chromeDriver = new ChromeDriver();
                    driverPool.set(chromeDriver);
                    break;
                case "firefox":
                    testLogger.debug("Configuring a Firefox Browser Driver");
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxDriver firefoxDriver = new FirefoxDriver();
                    driverPool.set(firefoxDriver);
                    break;
                case "chrome-headless":
                    try {
                        testLogger.debug("Configuring a Chrome Headless Browser Driver");
                        WebDriverManager.chromedriver().setup();
                        ChromeOptions chHeadless = new ChromeOptions();
                        chHeadless.setHeadless(true);
                        driverPool.set(new ChromeDriver(chHeadless));
                    } catch (Exception e) {
                        testLogger.error(e.getMessage());
                        throw e;
                    }
                    break;
                case "firefox-headless":
                    testLogger.debug("Configuring a Firefox Headless Browser Driver");
                    WebDriverManager.firefoxdriver().setup();
                    FirefoxOptions fHeadless = new FirefoxOptions();
                    fHeadless.setHeadless(true);
                    driverPool.set(new FirefoxDriver(fHeadless));
                    break;
                case "ie":
                    testLogger.debug("Configuring a IE Browser Driver");
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Internet Explorer");
                    WebDriverManager.iedriver().setup();
                    driverPool.set(new InternetExplorerDriver());
                    break;
                case "edge":
                    testLogger.debug("Configuring a Edge Browser Driver");
                    if (!System.getProperty("os.name").toLowerCase().contains("windows"))
                        throw new WebDriverException("Your OS doesn't support Edge");
                    WebDriverManager.edgedriver().setup();
                    driverPool.set(new EdgeDriver());
                    break;
                case "safari":
                    testLogger.debug("Configuring a Safari Browser Driver");
                    if (!System.getProperty("os.name").toLowerCase().contains("mac"))
                        throw new WebDriverException("Your OS doesn't support Safari");
                    WebDriverManager.getInstance(SafariDriver.class).setup();
                    driverPool.set(new SafariDriver());
                    break;
                case "remote-chrome":
                    testLogger.debug("Configuring a remote Chrome Browser Driver");
                    try {
                        URL url = new URL(ConfigurationReader.get("remote_driver_url"));
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments(WINDOW_SIZE);
                        driverPool.set(new RemoteWebDriver(url, chromeOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    break;
                case "remote-firefox-linux":
                    testLogger.debug("Configuring a remote Firefox Linux Browser Driver");
                    try {
                        URL url = new URL(ConfigurationReader.get("remote_driver_url"));
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.addArguments(WINDOW_SIZE);
                        firefoxOptions.setCapability("platform", Platform.LINUX);
                        driverPool.set(new RemoteWebDriver(url, firefoxOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                case "remote-firefox-win":
                    testLogger.debug("Configuring a remote Firefox Windows Browser Driver");
                    try {
                        URL url = new URL(ConfigurationReader.get("remote_driver_url"));
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        firefoxOptions.addArguments(WINDOW_SIZE);
                        firefoxOptions.setCapability("versopm", "asd");
                        driverPool.set(new RemoteWebDriver(url, firefoxOptions));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                case "remote-safari":
                    testLogger.debug("Configuring a remote Safari Browser Driver");
                    try {
                        URL url = new URL(ConfigurationReader.get("remote_driver_url"));
                        SafariOptions s = new SafariOptions();
                        driverPool.set(new RemoteWebDriver(url, s));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
            }

            testLogger.info("Successfully launched browser " + browser.toUpperCase());
        }

        return driverPool.get();
    }

    public static void closeDriver() {
        testLogger.debug("Closing WebDriver");
        if (driverPool.get() != null) {
            driverPool.get().manage().deleteAllCookies();
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
