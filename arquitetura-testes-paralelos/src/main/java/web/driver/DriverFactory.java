package web.driver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import utils.enums.Camada;

import java.net.URL;

import static utils.Utils.*;

public enum DriverFactory implements IDriverType {

    FIREFOX {
        public MutableCapabilities retornaDriver() {
            return new FirefoxOptions();
        }
    },

    FIREFOX_HEADLESS {
        public MutableCapabilities retornaDriver() {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setHeadless(true);
            return firefoxOptions;
        }
    },

    CHROME {
        @Override
        public MutableCapabilities retornaDriver() {
            return defaultChromeOptions();
        }
    },

    CHROME_HEADLESS {
        @Override
        public MutableCapabilities retornaDriver() {
            return ((ChromeOptions) defaultChromeOptions()).addArguments("headless");
        }
    },

    SAFARI {
        @Override
        public MutableCapabilities retornaDriver() {
            return new SafariOptions();
        }
    },

    EDGE {
        @Override
        public MutableCapabilities retornaDriver() {
            return new EdgeOptions();
        }
    };

    private static final Logger LOGGER = LogManager.getLogger();

    private static MutableCapabilities defaultChromeOptions() {
        ChromeOptions capabilities = new ChromeOptions();
        capabilities.addArguments("start-maximized");
        capabilities.addArguments("lang=pt-BR");

        return capabilities;
    }

    /**
     * Cria uma nova instância do RemoteWebDriver baseada na URL da grid
     *
     * @param browser browser algo
     * @return uma nova instancia de RemoteWebDriver
     */
    public static WebDriver criarInstancia(String browser) {
        RemoteWebDriver remoteWebDriver = null;

        try {
            String gridURL = lerPropriedade("grid.url", Camada.WEB) + ":" + lerPropriedade("grid.porta", Camada.WEB) + "/wd/hub";

            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), returnCapability(browser));
        } catch (Exception e) {
            LOGGER.error("Browser: " + browser, e);
        }

        return remoteWebDriver;
    }


    public static MutableCapabilities returnCapability(String browser) {
        return valueOf(browser.toUpperCase()).retornaDriver();
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
