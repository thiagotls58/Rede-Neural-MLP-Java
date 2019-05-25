package mlp;

import java.util.ArrayList;

/**
 *
 * @author Thiago
 */
public class Util {

    /**
     * Este método percorre todos os dados do Array e converte em duas matrizes
     * a primeira matriz corresponde aos dados de amostra para o treinamento da
     * rede, a segunda matriz contém a saída desejada para cada amostra, onde o
     * valor 1 correponde a saída correta, e o valor 0 a saída incorreta.
     *
     * @param dados ArrayList<String[]> - Contém os dados lidos do arquivo CSV
     * @param nEntrada int - Número de entradas da rede
     * @param nSaida int - Número de saídas da rede
     * @return ArrayList<double[][]> - Contém as matrizes de amostras e de
     * saídas da rede
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
                    indexClasse -= 1;
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

    /**
     * Efetua normalização dos dados da matriz
     * novoX = (x - min(matriz)) / (max(matriz) - min(matriz)).
     * @param matriz double[][] - matriz de dados para ser normalizada
     * @return double[][] - matriz de dados normalizada
     */
    public double[][] normalizarDados(double[][] matriz) {
        int numCol = matriz[0].length;
        int numLin = matriz.length;
        double[][] matrizNormalizada = new double[numLin][numCol];
        double min;
        double max;
        double novoValor;
        double valorAtual;

        for (int col = 0; col < numCol; col++) {
            min = valorMinimo(matriz, col);
            max = valorMaximo(matriz, col);
            for (int lin = 0; lin < numLin; lin++) {
                valorAtual = matriz[lin][col];
                novoValor = (valorAtual-min)/(max-min);
                matrizNormalizada[lin][col] = novoValor;
            }
        }
        
        return matrizNormalizada;
    }

    /**
     * Retorna o maior valor de uma coluna da matriz
     * @param matriz double[][] - matriz de dados
     * @param col int - indice da coluna para buscar o menor valor
     * @return double - menor valor encontrado
     */
    private double valorMinimo(double[][] matriz, int col) {
        double menor = 999999.0;
        for(int i = 0; i < matriz.length; i++) {
            if (matriz[i][col] < menor)
                menor = matriz[i][col];
        }
        return menor;
    }

    /**
     * Retorna o maior valor de uma coluna da matriz
     * @param matriz double[][] - matriz de dados
     * @param col int - indice da coluna para buscar o menor valor
     * @return double - maior valor encontrado
     */
    private double valorMaximo(double[][] matriz, int col) {
        double maior = 0.0;
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][col] > maior) {
                maior = matriz[i][col];
            }
        }
        return maior; 
    }

}
