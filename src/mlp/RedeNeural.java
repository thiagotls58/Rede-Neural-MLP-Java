/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Thiago
 */
public class RedeNeural {

    /**
     * matriz de amostras para o treinamento
     */
    private double[][] amostrasTreinamento;

    /**
     * matriz de saídas eperadas
     */
    private double[][] saidaEsperada;

    /**
     * Número de entrdadas na rede
     */
    private int nmrEntrada;

    /**
     * Número de neurônios da camada oculta
     */
    private int nmrNeuroniosOculta;

    /**
     * Número de neurônios da camada de saída
     */
    private int nmrNeuroniosSaida;

    /**
     * Neurônios da camada oculta
     */
    private List<NeuronioOculto> camadaOculta;

    /**
     * Neurônios da camada de saída
     */
    private List<NeuronioSaida> camadaSaida;

    /**
     * Taxa de aprendizado da rede neural
     */
    private double taxaAprendizado;

    /**
     * Número de épocas da rede neural
     */
    private int limiteEpocas;

    /**
     * Limite de erro da rede neural
     */
    private double limiteErro;

    /**
     * Função de ativação para calcular a saída da rede
     */
    private FuncaoAtivacao funcaoAtivacao;

    /**
     * vetor de entradas para calcular a rede
     */
    private double[] vetEntradas;
    
    /**
     * List da sequência que as amostras serão calculadas
     */
    private List<Integer> listSequencia;

    /**
     * vetor de saídas da rede
     */
    private double[] vetSaidas;

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
     * bias utilizado no cálculo do net do neurônio
     */
    private final double bias = 1.0;

    /**
     * Deve ser calculado ao fim de cada amostra
     */
    private double erroDaRede;

    /**
     * Deve ser calculado ao fim de cada época
     */
    private double erroMedioDaRede;

    /**
     * Recebe os parâmetros inicializa os pesos da rede.
     *
     * @param taxaAprendizado - Taxa de aorenzado da rede
     * @param nmrEntradas - Número de entradas da rede
     * @param nmrNeuroniosOculta - Número de neurônios da camada oculta
     */
    public RedeNeural(double taxaAprendizado, int nmrEntradas, int nmrNeuroniosOculta, int nmrSaidas) {
        this.taxaAprendizado = taxaAprendizado;
        this.nmrEntrada = nmrEntradas;
        this.nmrNeuroniosOculta = nmrNeuroniosOculta;
        this.nmrNeuroniosSaida = nmrSaidas;
        this.inicializarPesos();
        this.inicializarCamadaOculta();
        this.inicializarCamadaSaida();
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
     * @param limiteEpocas int - Atribui o limite de épocas da rede
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
     * @param funcaoAtivacao FuncaoAtivacao - Atribui a função de ativação para
     * cálculo da saída do neurônio
     */
    public void setFuncaoAtivacao(FuncaoAtivacao funcaoAtivacao) {
        this.funcaoAtivacao = funcaoAtivacao;
    }

    /**
     *
     * @return FuncaoAtivacao - Retorna a função de ativação do cálculo de saída
     * do neurônio
     */
    public FuncaoAtivacao getFuncaoAtivacao() {
        return funcaoAtivacao;
    }

    /**
     *
     * @param limiteErro int - Atribui a taxa de erro desejado da rede
     */
    public void setLimiteErro(double limiteErro) {
        this.limiteErro = limiteErro;
    }

    /**
     * Função de ativação Linear f(x) = x
     *
     * @param x double - net do neurônio da rede
     * @return double - retorna a saída do neurônio
     */
    private double funcLinear(double x) {
        return x;
    }

    /**
     * Calcula a derivada da função linear f'(x) = 1
     *
     * @param x double - valor de entrada
     * @return double - retorna a derivada da função linear
     */
    private double funcLinearDerivada(double x) {
        return 1.0;
    }

    /**
     * Função de ativação Logística f(x) = 1 / (1 + exp(-x))
     *
     * @param x double - net do neurônio da rede
     * @return double - retorna saída do neurônio
     */
    private double funcLogistica(double x) {
        return 1 / (1 + (double) Math.exp(-x));
    }

    /**
     * Calcula a derivada da função logística f'(x) = f(x)(1-f(x))
     *
     * @param x double - valor de entrada
     * @return double - retorna a derivada da função logística
     */
    private double funcLogisticaDerivada(double x) {
        return this.funcLogistica(x) * (1 - this.funcLogistica(x));
    }

    /**
     * Função de ativação tangente hiperbólica f(x) = 2 / (1 + exp(-x)) - 1
     *
     * @param x double - net do neurônio da rede
     * @return double - retorna saída do neurônio
     */
    private double funcTangenteHiperbolica(double x) {
        return 2 / (1 + Math.exp(-2 * x)) - 1;
    }

    /**
     * Calcula a derivada da função tangente hiperbólica f'(x) = 0.5 * (1 +
     * f(x)) * (1 - f(x))
     *
     * @param x double - valor de entrada
     * @return double - retorna a derivada da função tangente hiperbolica
     */
    private double funcTangenteHiperbolicaDerivada(double x) {
        return 1 - Math.pow(this.funcTangenteHiperbolica(x), 2);
    }

    /**
     * Inicializa pesos da camada e entrada e da camada oculta.
     */
    private void inicializarPesos() {
        pesosCamadaEntrada = new double[nmrEntrada][nmrNeuroniosOculta];
        pesosCamadaOculta = new double[nmrNeuroniosOculta][nmrNeuroniosSaida];
        inicializarPesosCamadaEntrada();
        inicializarPesosCamadaOculta();
    }

    /**
     * Inicializa os pesos da camada de entrada com valores randomicos.
     */
    private void inicializarPesosCamadaEntrada() {
        for (int i = 0; i < nmrEntrada; i++) {
            for (int j = 0; j < nmrNeuroniosOculta; j++) {
                pesosCamadaEntrada[i][j] = Math.random();
            }
        }
    }

    /**
     * Inicializa os pesos da camada oculta com valores randomicos.
     */
    private void inicializarPesosCamadaOculta() {
        for (int i = 0; i < nmrNeuroniosOculta; i++) {
            for (int j = 0; j < nmrNeuroniosSaida; j++) {
                pesosCamadaOculta[i][j] = Math.random();
            }
        }
    }

    /**
     * Inicializa a camada oculta.
     */
    private void inicializarCamadaOculta() {
        camadaOculta = new ArrayList<NeuronioOculto>();
        for (int i = 0; i < nmrNeuroniosOculta; i++) {
            camadaOculta.add(new NeuronioOculto());
        }
    }

    /**
     * Inicializa a camada de saída.
     */
    private void inicializarCamadaSaida() {
        camadaSaida = new ArrayList<NeuronioSaida>();
        for (int i = 0; i < nmrNeuroniosSaida; i++) {
            camadaSaida.add(new NeuronioSaida());
        }
    }

    /**
     * Copia os dados para realizar o treinamento da rede.
     *
     * @param matrizAmostras double[][] - matriz de amostras de treinamento
     * @param matrizSaidasEsperadas double[][] - matriz de resultados esperados
     */
    public void setDadosTreinamento(double[][] matrizAmostras, double[][] matrizSaidasEsperadas, int nLinhas, int nEntradas, int nSaidas) {
        amostrasTreinamento = new double[nLinhas][nEntradas];
        saidaEsperada = new double[nLinhas][nSaidas];
        System.arraycopy(matrizAmostras, 0, amostrasTreinamento, 0, matrizAmostras.length);
        System.arraycopy(matrizSaidasEsperadas, 0, saidaEsperada, 0, matrizSaidasEsperadas.length);
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
     * definido, o valor padrão será 0.1.
     */
    private void verificarErroDaRede() {
        if (this.limiteErro == 0.0) {
            this.limiteErro = 0.0000001;
        }
    }
    
    /**
     * Calcula a função de ativação.
     * @param net double - net do neurônio
     * @return double - resultado da função de ativação
     */
    private double calcularFuncAtivacao(double net) {
        double saida = 0.0;
        switch (funcaoAtivacao) {
            case LINEAR:
                saida = funcLinear(net);
                break;
            case LOGISTICA:
                saida = funcLogistica(net);
                break;
            case HIPERBOLICA:
                saida = funcTangenteHiperbolica(net);
                break;
        }
        return saida;
    }
    
    /**
     * Calcula a derivada do net do neurônio.
     * @param net double - net do neurônio
     * @return double - resultado da cálculo da derivada.
     */
    private double calcularDerivadaFuncAtivacao(double net) {
        double derivada = 0.0;
        switch (funcaoAtivacao) {
            case LINEAR:
                derivada = funcLinearDerivada(net);
                break;
            case LOGISTICA:
                derivada = funcLogisticaDerivada(net);
                break;
            case HIPERBOLICA:
                derivada = funcTangenteHiperbolicaDerivada(net);
                break;
        }
        return derivada;
    }
    
    

    /**
     * Efetua o treinamento da rede neural
     */
    public void treinar() {
        verificarNmrEpocas();
        verificarErroDaRede();
        erroMedioDaRede = 10.0;
        int epocas = 0;
        int index;

        vetEntradas = new double[nmrEntrada];
        vetSaidas = new double[nmrNeuroniosSaida];

        if (amostrasTreinamento != null && saidaEsperada != null) {
            /**
             * Enquanto não atingir o limite de erro, ou o limite de épocas
             */
            while ((Math.abs(erroMedioDaRede) > limiteErro) && (epocas < limiteEpocas)) {
                erroDaRede = 0.0;
                epocas++;
                SortearSequencia();
                //Percorrer todo o conjunto de amostras
                for (int i = 0; i < amostrasTreinamento.length; i++) {

                    index = listSequencia.get(i);
                    System.arraycopy(amostrasTreinamento[index], 0, vetEntradas, 0,
                            amostrasTreinamento[index].length); // copiamos os dados de entrada para o vetor entradas
                    System.arraycopy(saidaEsperada[index], 0, vetSaidas, 0, saidaEsperada[index].length); //copiamos os dados de resultado esperado para vetSaidas

                    // propagação
                    propagar();
                    calcularErroSaida();
                    calcularErroDaRede();
                }
                
                calcularErroMedioDaRede();
                if (!atingiuLimiar()) {
                    // retropropagação
                    calcularErroCamadaOculta();
                    atualizarPesosCamadaOculta();
                    atualizarPesosCamadaEntrada();
                }
                
                System.out.println("Treinando... época: " + epocas + ", erro: " + erroMedioDaRede);
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("Fim do treinamento...");
            System.out.println("Erro: " + erroMedioDaRede);
            System.out.println("Épocas: " + epocas);
        }
    }

    /**
     * Faz a propagação da rede.
     */
    private void propagar() {
        calcularNetCamadaOculta();
        calcularSaidaCamadaOculta();
        calcularNetCamadaSaida();
        calcularSaidaCamadaSaida();
    }

    /**
     * Calcula o net de cada neurônio da camada oculta.
     */
    private void calcularNetCamadaOculta() {
        double net;
        double resultado;
        
        for (int i = 0; i < nmrNeuroniosOculta; i++) {
            net = 0.0;
            for (int j = 0; j < nmrEntrada; j++) {
                resultado = vetEntradas[j] * pesosCamadaEntrada[j][i];
                net += resultado;
            }

            net += bias;
            camadaOculta.get(i).setNet(net);
        }
    }

    /**
     * Calcula a saída de cada neurônio da camada oculta.
     */
    private void calcularSaidaCamadaOculta() {
        double saida;
        for (NeuronioOculto n : camadaOculta) {
            saida = calcularFuncAtivacao(n.getNet());
            n.setSaida(saida);
        }
    }

    /**
     * Calcula o net de cada neurônio da camada de saída.
     */
    private void calcularNetCamadaSaida() {
        double net;
        double resultado;
        
        for (int i = 0; i < nmrNeuroniosSaida; i++) {
            net = 0.0;
            for (int j = 0; j < nmrNeuroniosOculta; j++) {
                resultado = camadaOculta.get(j).getSaida() * pesosCamadaOculta[j][i];
                net += resultado;
            }

            net += bias;
            camadaSaida.get(i).setNet(net);
        }
    }

    /**
     * Calcula a saída de cada neurônio da camada de saída.
     */
    private void calcularSaidaCamadaSaida() {
        double saida;
        for (NeuronioSaida n : camadaSaida) {
            saida = calcularFuncAtivacao(n.getNet());
            n.setSaida(saida);
        }
    }

    /**
     * Calcula o erro de saída e erro gradiente de cada neurônio da camada de
     * saída.
     */
    private void calcularErroSaida() {

        double erroSaida;
        double erroGradiente = 0.0;
        double net;
        NeuronioSaida neuronio;

        for (int i = 0; i < nmrNeuroniosSaida; i++) {
            neuronio = camadaSaida.get(i);

            erroSaida = vetSaidas[i] - neuronio.getSaida();
            neuronio.setErroSaida(erroSaida);

            net = neuronio.getNet();
            erroGradiente = erroSaida * calcularDerivadaFuncAtivacao(net);
            neuronio.setErroGradiente(erroGradiente);
        }
    }

    /**
     * Calcula o erro da rede ao fim de cada amostra passar na rede.
     */
    private void calcularErroDaRede() {

        double erro = 0.0;
        double somatorio = 0.0;
        NeuronioSaida neuronio;
        for (int i = 0; i < nmrNeuroniosSaida; i++) {
            neuronio = camadaSaida.get(i);
            somatorio += neuronio.getErroSaida();
        }
        erro = 0.5 * (Math.pow(somatorio, 2));
        erroDaRede += erro;
    }

    /**
     * Calcula o erro médio da rede, para verificar se atingiu o limiar.
     */
    private void calcularErroMedioDaRede() {
        erroMedioDaRede = erroDaRede / amostrasTreinamento.length;
    }

    /**
     * Verifica se o erro médio da rede atingiu o limiar.
     * @return boolean - true -> atingiu limiar, false -> não atingiu limiar
     */
    private boolean atingiuLimiar() {
        if (erroMedioDaRede < limiteErro) {
            return true;
        }
        return false;
    }

    /**
     * Calcula o erro de cada neurônio da camada oculta.
     */
    private void calcularErroCamadaOculta() {
        double erroGradienteSaida = 0.0;
        NeuronioOculto neuronio;
        double erro;
        double derivada;        
        
        // obtêm o somatório do erroGradiente dos neurônios da camada oculta
        for (int i = 0; i < nmrNeuroniosSaida; i++) {
            erroGradienteSaida += camadaSaida.get(i).getErroGradiente();
        }
        // calcula o erro dos neuronios da camada oculta
        for (int i = 0; i < nmrNeuroniosOculta; i++) {
            neuronio = camadaOculta.get(i);
            derivada = calcularDerivadaFuncAtivacao(neuronio.getNet());
            erro = 0.0;
            for (int j = 0; j < nmrNeuroniosSaida; j++) {
                erro += (erroGradienteSaida * pesosCamadaOculta[i][j]) * derivada;
            }
            
            neuronio.setErroGradiente(erro);
        }
    }

    /**
     * Atualiza a matriz de pesos da camada oculta.
     */
    private void atualizarPesosCamadaOculta() {
        double novoPeso;
        double pesoAtual;
        double erroGradiente;
        double saida;
        for (int i = 0; i < nmrNeuroniosOculta; i++) {
            for (int j = 0; j < nmrNeuroniosSaida; j++) {
                pesoAtual = pesosCamadaOculta[i][j];
                erroGradiente = camadaSaida.get(j).getErroGradiente();
                saida = camadaOculta.get(i).getSaida();
                novoPeso = pesoAtual + taxaAprendizado * erroGradiente * saida;
                pesosCamadaOculta[i][j] = novoPeso;
            }
        }
    }

    /**
     * Atualiza a matriz de pesos da camada de entrada.
     */
    private void atualizarPesosCamadaEntrada() {
        double novoPeso;
        double pesoAtual;
        double erroGradiente;
        double entrada;
        for (int i = 0; i < nmrEntrada; i++) {
            for (int j = 0; j < nmrNeuroniosOculta; j++) {
                pesoAtual = pesosCamadaEntrada[i][j];
                erroGradiente = camadaOculta.get(j).getErroGradiente();
                entrada = vetEntradas[i];
                novoPeso = pesoAtual + taxaAprendizado * erroGradiente * entrada;
                pesosCamadaEntrada[i][j] = novoPeso;
            }
        }
    }

    /**
     * Sorteia uma sequência em que as amostras serão calculadas na rede.
     */
    private void SortearSequencia() {
        Random random = new Random();
        listSequencia = new ArrayList<>();
        int totalAmostras = amostrasTreinamento.length;
        int numeroSorteado;
        
        for (int i = 0; i < totalAmostras; i++) {
            numeroSorteado = random.nextInt(totalAmostras);
            while (listSequencia.contains(numeroSorteado)) {
                numeroSorteado = random.nextInt(totalAmostras);
            }
            listSequencia.add(numeroSorteado);
        }
    }
    
}
