/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TouristRoutes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import jdk.nashorn.internal.ir.BreakNode;

/**
 *
 * @author luis_
 */
public class CsvToJson {

    public static void CsvToJson() {

        try {
            //o formato UTF-8 é para ser possivel ler todos os tipos de caracteres
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("src/data.csv"), "UTF-8"));

            int numeroLinhas = 0;
            String str = "{\"Rotas\": ["; //name the JsonArray
            String[] colunas = null;
            String line = "";
            String splitBy = ";";

            //ler linha a linha do ficheiro csv. Leitura dinamica - deixando acrescentar mais colunas ao csv
            while ((line = br.readLine()) != null) {
                line = line.replace(',', '.');
                String[] trajeto = line.split(splitBy);

                if (numeroLinhas == 0) {
                    colunas = trajeto;
                } else {
                    str += "{";
                    for (int i = 0; i < colunas.length; i++) {

                        try {
                            //passar as string(possiveis de converter) para double no formato ficheiro JSON
                            double a = Double.parseDouble(trajeto[i]);
                            if (i == trajeto.length - 1) {
                                str += "\"" + colunas[i] + "\":" + a + "},";
                            } else {
                                str += "\"" + colunas[i] + "\":" + a + ",";
                            }

                        } catch (NumberFormatException es) {
                            //passar as string para os grupos do formato JSON
                            if (i == trajeto.length - 1) {
                                str += "\"" + colunas[i] + "\":\"" + trajeto[i] + "\"},";
                            } else {
                                str += "\"" + colunas[i] + "\":\"" + trajeto[i] + "\",";
                            }
                        }

                    }
                }
                numeroLinhas++;
            }
            br.close();

            char[] temp = str.toCharArray();
            temp[temp.length - 1] = ']';
            str = "";
            int i = 0;
            while (i < temp.length) {
                str += temp[i];
                i++;
            }
            str += "}";

            //escrever a string obtida em ficheiro json 
            //o formato UTF-8 é para ser possivel ler todos os tipos de caracteres
            OutputStreamWriter fr = new OutputStreamWriter(new FileOutputStream("src/rota.json"), "UTF-8");
            fr.write(str, 0, str.length());
            fr.close();

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
