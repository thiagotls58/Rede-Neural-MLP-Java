package mlp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tiago
 */
public class Util {
    
    /**
     * Este método percorre todos os dados do Array e converte em duas matrizes
     * a primeira matriz corresponde aos dados de amostra para o treinamento da rede,
     * a segunda matriz contém a saída desejada para cada amostra, onde o valor 1 
     * correponde a saída correta, e o valor 0 a saída incorreta.
     * 
     * @param dados ArrayList<String[]> - Contém os dados lidos do arquivo CSV
     * @param nEntrada int - Número de entradas da rede
     * @param nSaida int - Número de saídas da rede
     * @return ArrayList<double[][]> - Contém as matrizes de amostras e de saídas da rede
     */
    public ArrayList<double[][]> converterDados(ArrayList<String[]> dados, int nEntrada, int nSaida) {

        double[][] matrizEntradas = new double[dados.size()][nEntrada];
        double[][] matrizSaidas = new double[dados.size()][nSaida];
        ArrayList<double[][]> retorno = new ArrayList<double[][]>();
        int qtdLinhas = dados.size();
        int qtdColunas = dados.get(0).length;

        for (int lin = 0; lin < qtdLinhas; lin++) {
            for (int col = 0; col < qtdColunas; col++) {
                if (col < qtdColunas - 1) {
                    matrizEntradas[lin][col] = Double.parseDouble(dados.get(lin)[col]);
                } else {
                    double classe = Double.parseDouble(dados.get(lin)[col]);
                    int indexClasse = (int) classe;
                    for (int k = 0; k < nSaida; k++) {
                        if (k == indexClasse) {
                            matrizSaidas[lin][k] = 1.0;
                        } else {
                            matrizSaidas[lin][k] = 0.0;
                        }
                    }
                }
            }
        }
        
        retorno.add(matrizEntradas);
        retorno.add(matrizSaidas);
        return retorno;
    }

}
