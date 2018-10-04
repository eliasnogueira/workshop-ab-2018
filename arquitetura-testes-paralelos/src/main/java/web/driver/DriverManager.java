package web.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverManager {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final WebDriver driver = null;

    private  DriverManager() {}

    static WebDriver getDriver() {
        return  driver;
    }

    public static void quit() {
        DriverManager.driver.quit();
    }

    public static String getInfo() {
        String nomeBrowser = null, plataforma = null, versao = null;
        try {
            Capabilities cap = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
            nomeBrowser = cap.getBrowserName();
            plataforma = cap.getPlatform().toString();
            versao = cap.getVersion();
        } catch (NullPointerException e) {
            LOGGER.fatal(e);
        }



        return String.format("browser: %s v: %s platform: %s", nomeBrowser, plataforma, versao);
    }
}
