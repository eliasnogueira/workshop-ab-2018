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

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.ElementOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class RemoverPessoaTest extends BaseTest {

    @Test
    public void removerPessoaPreviamenteCadastrada() {
        // pesquisa por uma pessoa
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("android:id/search_button")));
        driver.findElement(By.id("android:id/search_button")).click();
        driver.findElement(By.id("android:id/search_src_text")).sendKeys("Carlos");

        // retorna o elemento contendo o nome da pessoa
        MobileElement item = driver.findElement(By.id("android:id/text1"));

        // aplica um "pressionar longo" no elelemento da pessoa
        TouchAction touch = new TouchAction(driver);
        touch.longPress(new ElementOption().withElement(item)).perform();

        // confirma que apresentou a mensagem de deleção
        assertThat((driver.findElement(By.id("android:id/message")).getText())).
                isEqualTo("Deseja remover a pessoa selecionada?");

        // clica no botao OK da mensagem
        driver.findElement(By.id("android:id/button1")).click();

        // confirma que a mensagem de sucesso da deleção apareceu
        assertThat((driver.findElement(By.id("android:id/message")).getText())).
                isEqualTo("A pessoa foi removida com sucesso!");

        // clica no botao OK da mensagem
        driver.findElement(By.id("android:id/button3")).click();
    }
}
