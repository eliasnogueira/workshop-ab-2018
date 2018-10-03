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

package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.enums.Camada;
import utils.exceptions.CamadaNotFoundException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Utils {

    private static final Logger LOGGER = LogManager.getLogger();

    private Utils(){}

    /**
     * rertorna o valor da propriedade para o arquivo de configuração dado uma propriedade
     * @param propriedade uma propriedad existente no arquivo de configuração
     * @param camada para qual camada o arquivo será carregado
     * @return o valor da propriedade
     */
    public static String lerPropriedade(String propriedade, Camada camada) {
        Properties properties;
        String valor = null;
        try {
            properties = new Properties();

            switch (camada) {

                case WEB:

                    properties.load(new FileReader(new File("src/main/resources/config/web.properties")));
                    break;
                case MOBILE:
                    properties.load(new FileReader(new File("src/main/resources/config/mobile.properties")));
                    break;

                default:
                    throw new CamadaNotFoundException("Camada informada não existe: " + camada);
            }

            valor = properties.getProperty(propriedade);
        } catch (Exception e) {
            LOGGER.fatal(e);
        }
        return valor;
    }
}
