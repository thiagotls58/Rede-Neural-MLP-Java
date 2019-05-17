package mlp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class Arquivo {

    public List<String> obterDados(String arquivoCSV) {

        BufferedReader br = null;
        String linha = "";
        String csvDivisor = ",";
        ArrayList dados = new ArrayList();
        try {

            br = new BufferedReader(new FileReader(arquivoCSV));
            br.readLine();
            while ((linha = br.readLine()) != null) {

                String[] dadosLinha = linha.split(csvDivisor);
                dados.add(dadosLinha);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dados;
    }

}
