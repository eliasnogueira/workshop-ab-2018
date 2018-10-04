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

package web;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class CadastrarPessoaTest extends BaseTest {

    @Test
    public void cadastroComSucesso() {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        By addButton  = By.id("add");
        wait.until(ExpectedConditions.presenceOfElementLocated(addButton));
        driver.findElement(addButton).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("back")));
        driver.findElement(By.id("name")).sendKeys("Daenerys Targaryen");
        driver.findElement(By.name("address")).sendKeys("Dragonstone");
        driver.findElement(By.cssSelector("input[ng-model='post.hobbies']")).sendKeys("Break Chains");
        new Actions(driver).click(driver.findElement(By.cssSelector(".w3-btn.w3-teal"))).build().perform();;
        //driver.findElement(By.cssSelector(".w3-btn.w3-teal")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("edit")));
        String dadosPagina = driver.getPageSource();

        assertThat(dadosPagina.contains("Daenerys Targaryen"));
        assertThat(dadosPagina.contains("Dragonstone"));
        assertThat(dadosPagina.contains("Break Chains"));
    }
}
