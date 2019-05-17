/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

/**
 *
 * @author Thiago
 */
public class RedeNeural {

    /**
     * Taxa de aprendizado da rede neural
     */
    private double taxaAprendizado;

    /**
     * Número de entradas da rede neural
     */
    private int nmrEntrada;

    /**
     * Número de neurônios da camada oculta
     */
    private int nmrNeuroniosOculta;

    /**
     * Número de épocas da rede neural
     */
    private int limiteEpocas;

    /**
     * Limite de erro da rede neural
     */
    private double limiteErro;

    /**
     * Matriz de pesos entre a camada de entrada e os neurônios da camada oculta
     */
    private double[][] pesosCamadaEntrada;

    /**
     * Matriz de pesos entre os neurônios da camada oculta e as saídas da rede
     * neural
     */
    private double[][] pesosCamadaOculta;

    /**
     * Recebe os parâmetros inicializa os pesos da rede.
     *
     * @param taxaAprendizado - Taxa de aorenzado da rede
     * @param nmrEntrada - Número de entradas da rede
     * @param nmrNeuroniosOculta - Número de neurônios da camada oculta
     */
    public RedeNeural(double taxaAprendizado, int nmrEntrada, int nmrNeuroniosOculta) {
        this.taxaAprendizado = taxaAprendizado;
        this.nmrEntrada = nmrEntrada;
        this.nmrNeuroniosOculta = nmrNeuroniosOculta;
        this.inicializarPesos();
    }

    /**
     *
     * @return int - Retorna o número de épocas da rede
     */
    public int getLimiteEpocas() {
        return limiteEpocas;
    }

    /**
     *
     * @param limiteEpocas - Atribui o limite de épocas da rede
     */
    public void setLimiteEpocas(int limiteEpocas) {
        this.limiteEpocas = limiteEpocas;
    }

    /**
     *
     * @return double - Retorna taxa de erro da rede
     */
    public double getLimiteErro() {
        return limiteErro;
    }

    /**
     *
     * @param limiteErro - Atribui a taxa de erro desejado da rede
     */
    public void setLimiteErro(double limiteErro) {
        this.limiteErro = limiteErro;
    }

    /**
     * Inicializa as matrizes de peso da rede com valores randomicos que variam
     * de 0 a 0.999999...
     */
    private void inicializarPesos() {
        for (int i = 0; i < nmrEntrada; i++) {
            for (int j = 0; j < nmrNeuroniosOculta; j++) {
                pesosCamadaEntrada[i][j] = Math.random();
                pesosCamadaOculta[i][j] = Math.random();
            }
        }
    }

    /**
     * Efetua o treinamento da rede neural
     *
     * @param dadosTreinamento - Matriz de amostras para o treinamento
     * @param saidaEsperada - Vetor de saída esperada para cada amostra de
     * treinamento
     */
    public void treinar(double[][] dadosTreinamento, double[] saidaEsperada) {
        verificarNmrEpocas();
        verificarErroDaRede();
        double erro = 1.0;
        int epocas = 0;
        
        /** Enquanto não atingir o limite de erro, ou o limite de épocas */
        while ((Math.abs(erro) > limiteErro) || epocas < limiteEpocas) {
            
            /** Percorre cada linha da base dados */
            for (int i = 0; i < dadosTreinamento[i].length; i++) {
                
            }
        }
    }

    /**
     * Verifica se o número de épocas foi definido pelo usuário, caso não tenha
     * sido definido, o valor padrão será 99.999.
     */
    private void verificarNmrEpocas() {
        if (this.limiteEpocas == 0) {
            this.limiteEpocas = 99999;
        }
    }

    /**
     * Verifica se o erro da rede foi definido pelo usuário, caso não tenha sido
     * definido, o valor padrão será 0.9.
     */
    private void verificarErroDaRede() {
        if (this.limiteErro == 0.0) {
            this.limiteErro = 0.9;
        }
    }
}
