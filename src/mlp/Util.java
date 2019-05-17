package mlp;

import java.util.ArrayList;

/**
 *
 * @author Tiago
 */
public class Util {

    public double[][] converterEntrada(ArrayList<String[]> dados, int nEntrada) {
        double[][] matrizEntradas = new double[dados.size()][nEntrada];
    
        for (int i = 0; i < dados.size(); i++)
            for (int j = 0; j < nEntrada; j++) 
                    matrizEntradas[i][j] = Double.parseDouble(dados.get(i)[j]);
        
        return matrizEntradas;
    }
    
    public double[] converterSaida(ArrayList<String[]> dados, int indexSaida) {
        double[] vetorSaida = new double[dados.size()];
        
        for (int i = 0; i < dados.size(); i++)
            vetorSaida[i] = Double.parseDouble(dados.get(i)[indexSaida]);
        
        return vetorSaida;
    }

}
