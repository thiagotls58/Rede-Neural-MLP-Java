/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
     * Deve ser calculado ao fim de cada amostra
     */
    private double erroDaRede;

    /**
     * Deve ser calculado ao fim de cada época
     */
    private double erroMedioDaRede;

    /**
     * Número epocas que o treinamento foi realizado
     */
    private int totalEpocas;

    /**
     * Matriz de confusão da rede
     */
    private int[][] matrizConfusao;

    /**
     * Quantidade de dados para os testes
     */
    private int qtdDadosTeste;

    /**
     * Total de acertos no teste da rede
     */
    private int totalAcertos;

    /**
     * Porcentagem de acurácia da rede
     */
    private double acuracia;

    /**
     * Lista de erros para ser utilizado no gráfico de treinamento.
     */
    private List<Double> errosDaRede;

    /**
     * Lista de épocas para ser utilizado no gráfico de treinamento.
     */
    private List<Integer> epocasDaRede;

    /**
     * Total de erros no teste da rede.
     */
    private int totalErros;

    /**
     * Inicializa a rede neural mlp
     *
     * @param nmrEntrada int - número de entradas da rede
     * @param nmrSaida int - número de neurônios na camada de saída da rede
     * @param nmrCamadaOculta int - número de neurônios na camada oculta da rede
     * @param limiteErro double - limite de erro da rede
     * @param limiteEpocas int - limite de épocas da rede
     * @param taxaAprendizado double - taxa de aprendizado da rede
     */
    RedeNeural(int nmrEntrada, int nmrSaida, int nmrCamadaOculta, double limiteErro, int limiteEpocas, double taxaAprendizado) {
        this.nmrEntrada = nmrEntrada;
        this.nmrNeuroniosSaida = nmrSaida;
        this.nmrNeuroniosOculta = nmrCamadaOculta;
        this.limiteErro = limiteErro;
        this.limiteEpocas = limiteEpocas;
        this.taxaAprendizado = taxaAprendizado;
        this.inicializarPesos();
        this.inicializarCamadaOculta();
        this.inicializarCamadaSaida();
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
     * @return List<Double> - Vetor de erros da rede
     */
    public List<Double> getErrosDaRede() {
        return errosDaRede;
    }

    /**
     *
     * @return List<Integer> - Vetor de épocas da rede
     */
    public List<Integer> getEpocasDaRede() {
        return epocasDaRede;
    }

    /**
     *
     * @return double - Taxa de aprendizado da rede.
     */
    public double getTaxaAprendizado() {
        return taxaAprendizado;
    }

    /**
     *
     * @return FuncaoAtivacao - Função de ativação utilizada na rede.
     */
    public FuncaoAtivacao getFuncaoAtivacao() {
        return funcaoAtivacao;
    }

    /**
     *
     * @return double - Erro médio da rede.
     */
    public double getErroMedioDaRede() {
        return erroMedioDaRede;
    }

    /**
     *
     * @return double[][] - matriz de confusão
     */
    public int[][] getMatrizConfusao() {
        return matrizConfusao;
    }

    /**
     *
     * @return int - quantidade total de testes
     */
    public int getQtdDadosTeste() {
        return qtdDadosTeste;
    }

    /**
     *
     * @return int - total de acertos da rede
     */
    public int getTotalAcertos() {
        return totalAcertos;
    }

    /**
     *
     * @return int - total de erros da rede
     */
    public int getTotalErros() {
        return totalErros;
    }

    /**
     *
     * @return double - acurácia da rede
     */
    public double getAcuracia() {
        return acuracia;
    }

    /**
     * Função de ativação Linear f(x) = x/10
     *
     * @param x double - net do neurônio da rede
     * @return double - retorna a saída do neurônio
     */
    private double funcLinear(double x) {
        return x / 10.0;
    }

    /**
     * Calcula a derivada da função linear f'(x) = 1/10
     *
     * @param x double - valor de entrada
     * @return double - retorna a derivada da função linear
     */
    private double funcLinearDerivada(double x) {
        return 1.0 / 10.0;
    }

    /**
     * Função de ativação Logística
     *
     * @param x double - net do neurônio da rede
     * @return double - retorna saída do neurônio
     */
    private double funcLogistica(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    /**
     * Calcula a derivada da função logística
     *
     * @param x double - valor de entrada
     * @return double - retorna a derivada da função logística
     */
    private double funcLogisticaDerivada(double x) {
        double resp = this.funcLogistica(x);
        return resp * (1 - resp);
    }

    /**
     * Função de ativação tangente hiperbólica
     *
     * @param x double - net do neurônio da rede
     * @return double - retorna saída do neurônio
     */
    private double funcTangenteHiperbolica(double x) {
        return Math.tanh(x);
    }

    /**
     * Calcula a derivada da função tangente hiperbólica
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
     * Calcula a função de ativação.
     *
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
     *
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
        erroMedioDaRede = 10.0;
        int epocas = 0;
        int index;

        if (amostrasTreinamento != null && saidaEsperada != null) {

            vetEntradas = new double[nmrEntrada];
            vetSaidas = new double[nmrNeuroniosSaida];
            errosDaRede = new ArrayList<>();
            epocasDaRede = new ArrayList<>();

            /**
             * Enquanto não atingir o limite de erro, ou o limite de épocas
             */
            while ((erroMedioDaRede > limiteErro) && (epocas < limiteEpocas)) {
                erroDaRede = 0.0;
                SortearSequencia();
                //Percorrer todo o conjunto de amostras
                for (int i = 0; i < amostrasTreinamento.length; i++) {

                    index = listSequencia.get(i);
                    System.arraycopy(amostrasTreinamento[index], 0, vetEntradas, 0,
                            amostrasTreinamento[index].length); // copiamos os dados de entrada para o vetor entradas
                    System.arraycopy(saidaEsperada[index], 0, vetSaidas, 0, saidaEsperada[index].length); //copiamos os dados de resultado esperado para vetSaidas

                    propagar();
                    retropropagar();
                    calcularErroDaRede();
                }

                epocas++;
                epocasDaRede.add(epocas);
                calcularErroMedioDaRede();
                errosDaRede.add(erroMedioDaRede);

                System.out.println("Treinando... época: " + epocas + ", erro: " + erroMedioDaRede);
            }
            System.out.println();
            System.out.println();
            System.out.println("Fim do treinamento...");
            System.out.println("Erro: " + erroMedioDaRede);
            System.out.println("Épocas: " + epocas);
            totalEpocas = epocas;
            System.out.println();
            System.out.println("Pesos camada entrada");
            System.out.println(printarMatrizDouble(pesosCamadaEntrada));
            System.out.println();
            System.out.println("Pesos camada saída");
            System.out.println(printarMatrizDouble(pesosCamadaOculta));
        }
    }

    public void testar(double[][] dadosTeste, double[][] saidasEsperadas) {

        int posCorreta;
        int posCalculada;
        int nmrClasses = saidasEsperadas[0].length;
        qtdDadosTeste = dadosTeste.length;
        vetEntradas = new double[nmrEntrada];
        vetSaidas = new double[nmrNeuroniosSaida];
        totalAcertos = 0;
        acuracia = 0.0;
        if (dadosTeste != null && saidasEsperadas != null) {

            matrizConfusao = new int[nmrClasses][nmrClasses];
            for (int i = 0; i < nmrClasses; i++) {
                for (int j = 0; j < nmrClasses; j++) {
                    matrizConfusao[i][j] = 0;
                }
            }

            //Percorrer todo o conjunto de amostras
            for (int i = 0; i < qtdDadosTeste; i++) {

                System.arraycopy(dadosTeste[i], 0, vetEntradas, 0,
                        dadosTeste[i].length); // copiamos os dados de entrada para o vetor entradas
                System.arraycopy(saidasEsperadas[i], 0, vetSaidas, 0, saidasEsperadas[i].length); //copiamos os dados de resultado esperado para vetSaidas

                propagar();
                posCorreta = encontrarPosCorreta(vetSaidas);
                posCalculada = encontrarPosCalculada(camadaSaida);

                if (posCorreta == posCalculada) {
                    totalAcertos++;
                }

                matrizConfusao[posCorreta][posCalculada] += 1;

            }

            totalErros = qtdDadosTeste - totalAcertos;
            acuracia = ((double) totalAcertos / qtdDadosTeste) * 100.0;
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
     * Faz a retropropagação da rede.
     */
    private void retropropagar() {
        calcularErroSaida();
        calcularErroCamadaOculta();
        atualizarPesosCamadaOculta();
        atualizarPesosCamadaEntrada();
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
        double err = 0.0;
        NeuronioSaida neuronio;
        for (int i = 0; i < nmrNeuroniosSaida; i++) {
            neuronio = camadaSaida.get(i);
            err += Math.pow(neuronio.getErroSaida(), 2);
        }
        err = 0.5 * err;
        erroDaRede += err;
    }

    /**
     * Calcula o erro médio da rede, para verificar se atingiu o limiar.
     */
    private void calcularErroMedioDaRede() {
        erroMedioDaRede = erroDaRede / amostrasTreinamento.length;
    }

    /**
     * Calcula o erro de cada neurônio da camada oculta.
     */
    private void calcularErroCamadaOculta() {
        NeuronioOculto neuronio;
        double erro;
        double derivada;
        double gradiente;

        // calcula o erro dos neuronios da camada oculta
        for (int i = 0; i < nmrNeuroniosOculta; i++) {
            neuronio = camadaOculta.get(i);
            derivada = calcularDerivadaFuncAtivacao(neuronio.getNet());
            erro = 0.0;
            for (int j = 0; j < nmrNeuroniosSaida; j++) {
                gradiente = camadaSaida.get(j).getErroGradiente();
                erro += gradiente * pesosCamadaOculta[i][j];
            }

            erro = erro * derivada;

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

    /**
     * Encontra a posição (classe) correta para a entrada.
     *
     * @param vetSaidas double[][] - vetor de saida esperada.
     * @return int - posicação encotrada
     */
    private int encontrarPosCorreta(double[] vetSaidas) {
        for (int pos = 0; pos < vetSaidas.length; pos++) {
            if (vetSaidas[pos] == 1.0) {
                return pos;
            }
        }
        return -1;
    }

    /**
     * Encontra a posição (classe) calculada na rede.
     *
     * @param camadaSaida List<NeuronioSaida> - camada de saída da rede
     * @return int - posição encontrada
     */
    private int encontrarPosCalculada(List<NeuronioSaida> camadaSaida) {
        double maior = 0.0;
        int posicao = -1;
        NeuronioSaida neuronio;
        for (int pos = 0; pos < camadaSaida.size(); pos++) {
            neuronio = camadaSaida.get(pos);
            if (neuronio.getSaida() > maior) {
                maior = neuronio.getSaida();
                posicao = pos;
            }
        }

        return posicao;
    }

    /**
     * Constrói uma String com os dados da matriz.
     *
     * @param matriz double[][] - matriz para printar no console
     */
    private String printarMatrizDouble(double[][] matriz) {
        String str = "";
        String format = "";
        for (int i = 0; i < matriz.length; i++) {
            str += "[";
            for (int j = 0; j < matriz[0].length; j++) {
                format = String.format("%.2f", matriz[i][j]);
                if (j == matriz[0].length - 1) {
                    str += format;
                } else {
                    str += format + ", ";
                }
            }
            str += "]\n";
        }
        return str;
    }

    /**
     * Constrói uma String com os dados da matriz.
     *
     * @param matriz int[][] - matriz para printar no console
     */
    private String printarMatrizInteger(int[][] matriz) {
        int classe;
        String str = "";
        for (int i = 0; i < matriz.length; i++) {
            classe = i + 1;
            str += "    c" + classe;
        }
        str += "\n";
        for (int i = 0; i < matriz.length; i++) {
            classe = i + 1;
            str += "c" + classe;
            str += "[";
            for (int j = 0; j < matriz[0].length; j++) {
                if (j == matriz[0].length - 1) {
                    str += matriz[i][j];
                } else {
                    str += matriz[i][j] + ", ";
                }
            }
            str += "]\n";
        }
        return str;
    }

    /**
     * Contrói um gráfico da matriz de confusão.
     *
     * @param matriz int[][] - dados para o gráfico
     * @return String - Gráfico em formato texto
     */
    private String construirGrafico(int[][] matriz) {
        String str = "";
        String format;
        double percentual;
        int classe;
        int[] vetorSum = new int[matriz.length];

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                vetorSum[i] += matriz[i][j];
            }
        }

        for (int i = 0; i < matriz.length; i++) {
            classe = i + 1;
            str += "classe " + classe + ": ";
            for (int j = 0; j < matriz[0].length; j++) {
                if (i == j) {
                    percentual = ((double) matriz[i][j] / (double) vetorSum[i]) * 100.0;
                    format = String.format("%.1f", percentual);
                    for (int l = 0; l < (int) percentual; l++) {
                        str += "*";
                    }
                    str += "  " + format + "%";
                }
            }
            str += "\n";
        }

        return str;
    }

    /**
     * Retorna os resultados do treinamento
     *
     * @return string - dados do treinamento
     */
    public String resultadoTreinamento() {
        String str = "";
        str += "Fim do treinamento...\n";
        str += "Erro da rede: " + erroMedioDaRede + "\n";
        str += "Número de Épocas: " + totalEpocas + "\n";
        str += "\n";
        str += "Pesos camada entrada\n";
        str += printarMatrizDouble(pesosCamadaEntrada);
        str += "\n";
        str += "Pesos camada saída\n";
        str += printarMatrizDouble(pesosCamadaOculta);

        return str;
    }

    public String resultadoTeste() {
        String str = "";
        str += "Fim dos testes...\n";
        str += "Acertos: " + totalAcertos + " de " + qtdDadosTeste + "\n";
        String format = String.format("%.2f", acuracia);
        str += "Acurácia: " + format + "%\n";
        str += "\n";
        str += "\n";
        str += "Matriz de Confusão\n";
        str += printarMatrizInteger(matrizConfusao);
        str += "\n";
        str += "\n";
        str += "Gráfico\n";
        str += construirGrafico(matrizConfusao);

        return str;
    }

}
