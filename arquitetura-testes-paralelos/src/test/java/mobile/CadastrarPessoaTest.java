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

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.*;

public class CadastrarPessoaTest extends BaseTest {

    @Test
    public void cadastroComSucesso() {
        // clicar no botão adicionar
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("com.eliasnogueira.workshop:id/fab")));
        driver.findElement(By.id("com.eliasnogueira.workshop:id/fab")).click();

        // preencher os dados e submeter
        driver.findElement(By.id("com.eliasnogueira.workshop:id/txt_nome")).sendKeys("Jon Snow");
        driver.findElement(By.id("com.eliasnogueira.workshop:id/txt_endereco")).sendKeys("The wall");
        driver.findElement(By.id("com.eliasnogueira.workshop:id/txt_hobbies")).sendKeys("Know nothing");
        driver.findElement(By.id("com.eliasnogueira.workshop:id/button")).click();

        // pesquisar pela pessoa criada
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("android:id/search_button")));
        driver.findElement(By.id("android:id/search_button")).click();
        driver.findElement(By.id("android:id/search_src_text")).sendKeys("Jon Snow");

        // validar os dados retornados
        String texto = driver.findElement(By.id("android:id/text1")).getText();

        assertThat((texto)).isEqualTo("Jon Snow");
    }
}
