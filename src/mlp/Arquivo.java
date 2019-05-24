package mlp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Tiago
 */
public class Arquivo {

    /**
     * Este método faz a leitura do arquivo CSV selecionado, em seguida separa os 
     * dados de cada linha do arquivo em um vetor de String, e insere este vetor no 
     * Array para ser retornado.
     * 
     * @param arquivoCSV - Caminho do arquivo CSV selecionado
     * @return ArrayList<String[]> - Array que contém um vetor de String
     */
    public ArrayList<String[]> obterDados(String arquivoCSV) {

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
            dados = null;
        } catch (IOException e) {
            e.printStackTrace();
            dados = null;
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
