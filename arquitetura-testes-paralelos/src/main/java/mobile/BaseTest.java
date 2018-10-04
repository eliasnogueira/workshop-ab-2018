/*
 * MIT License
 *
 * Copyright (c) 2018 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package mobile;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import utils.enums.Camada;

import java.io.File;
import java.net.URL;

import static utils.Utils.lerPropriedade;

public class BaseTest {

    AppiumDriver<?> driver = null;
    WebDriverWait wait = null;

    @BeforeTest(alwaysRun = true)
    @Parameters( { "plataforma", "udid", "versaoPlataforma"})
    public void beforeTest(String platform, String udid, String platformVersion) throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(MobileCapabilityType.UDID, udid);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);

        String completeURL = lerPropriedade("grid.url", Camada.MOBILE) + ":" + lerPropriedade("grid.porta", Camada.MOBILE) + "/wd/hub";

        switch (platform.toLowerCase()) {
            case "ios":
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.IOS);

                // if iOS 9+ use XCUITest
                if (Boolean.parseBoolean(lerPropriedade("platform.ios.xcode8", Camada.MOBILE))) {
                    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
                }

                if (Boolean.parseBoolean(lerPropriedade("app.instalada", Camada.MOBILE))) {
                    capabilities.setCapability(MobileCapabilityType.APP, new File(lerPropriedade("app.ios.path", Camada.MOBILE)).getAbsolutePath());
                } else {
                    capabilities.setCapability(IOSMobileCapabilityType.APP_NAME, lerPropriedade("app.ios.appName", Camada.MOBILE));
                }

                driver = new IOSDriver<>(new URL(completeURL), capabilities);
                wait = new WebDriverWait(driver, 10);
                break;

            case "android":
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);

                if (Boolean.parseBoolean(lerPropriedade("app.instalada", Camada.MOBILE))) {
                    capabilities.setCapability(MobileCapabilityType.APP, new File(lerPropriedade("app.android.path", Camada.MOBILE)).getAbsolutePath());
                } else {
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, lerPropriedade("app.android.appPackage", Camada.MOBILE));
                    capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, lerPropriedade("app.android.appActivity", Camada.MOBILE));
                }

                driver = new AndroidDriver<>(new URL(completeURL), capabilities);
                wait = new WebDriverWait(driver, 10);
                break;

            default:
                throw new Exception("Plataforma não suportada");
        }
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
