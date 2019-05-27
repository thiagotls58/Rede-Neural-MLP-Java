/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Thiago
 */
public class PrincipalController implements Initializable {

    /**
     * Objeto da rede neural.
     */
    RedeNeural mlp;

    /**
     * Lista de parâmetros para ser utilizado na normalização dos dados.
     */
    double[][] parametros;

    /**
     * Lista de erros para ser utilizado no gráfico de treinamento.
     */
    private List<Double> errosDaRede;

    /**
     * Lista de épocas para ser utilizado no gráfico de treinamento.
     */
    private List<Integer> epocasDaRede;

    /**
     * Dados para preencher o gráfico de treinamento.
     */
    private XYChart.Series dadosTreinamento;

    /**
     * Contém todos os dados de cada treinamento realizado.
     */
    private List<XYChart.Series> listaDadosDoGraficoTreinamento;

    /**
     * Lista de treinamentos realizado.
     */
    private List<Treino> treinamentos;

    @FXML
    private AnchorPane pnCriterioParada;
    @FXML
    private AnchorPane pnTaxaAprendazagem;
    @FXML
    private TextField txtTaxaAprendizagem;
    @FXML
    private AnchorPane pnFuncaoAtivacao;
    @FXML
    private RadioButton rdLinear;
    @FXML
    private ToggleGroup grupoFuncaoAtivacao;
    @FXML
    private RadioButton rdLogistica;
    @FXML
    private RadioButton rdHiperbolica;
    @FXML
    private Button btnTreinar;
    @FXML
    private TextField txtAtributos;
    @FXML
    private TextField txtClasses;
    @FXML
    private Button btnIniciarTreino;
    @FXML
    private Button btnTestar;
    @FXML
    private Button btnIniciarTeste;
    @FXML
    private TextField txtErro;
    @FXML
    private TextField txtEpocas;
    @FXML
    private TextField txtQtdNeuronios;
    @FXML
    private RadioButton rdPadrao;
    @FXML
    private ToggleGroup grupoNumNeuronios;
    @FXML
    private RadioButton rdPersonalizado;
    @FXML
    private RadioButton rdSim;
    @FXML
    private ToggleGroup grupoNormalizar;
    @FXML
    private RadioButton rdNao;
    @FXML
    private Button btnProcurarArqTreino;
    @FXML
    private TextArea txtConsoleTreinamento;
    @FXML
    private StackedAreaChart<?, ?> graficoTreinamento;
    @FXML
    private Button btnCancelarTreinamento;
    @FXML
    private AnchorPane pnArquivoTeste;
    @FXML
    private TextField txtCaminhoArquivoTeste;
    @FXML
    private Button btnProcurarArqTeste;
    @FXML
    private Button btnCancelarTeste;
    @FXML
    private TextField txtCaminhoArquivoTreino;
    @FXML
    private AnchorPane pnTreinamento;
    @FXML
    private AnchorPane pnTeste;
    @FXML
    private AnchorPane pnAtributos;
    @FXML
    private AnchorPane pnClasses;
    @FXML
    private AnchorPane pnNeuronios;
    @FXML
    private AnchorPane pnNormalizacao;
    @FXML
    private AnchorPane pnArquivoTreino;
    @FXML
    private AnchorPane pnResultadosTreinamento;
    @FXML
    private TextArea txtResultadosTreinamentos;
    @FXML
    private TextField txtTotal;
    @FXML
    private TextField txtAcertos;
    @FXML
    private TextField txtErros;
    @FXML
    private TextField txtAcuracia;
    @FXML
    private GridPane matrizConfusao;
    @FXML
    private AnchorPane pnResultadoTeste;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        estadoInicialTreinamento();
    }

    /*--------------------------------------------------------------------------------------------------------*/
 /*---------------------------- Parte de Treinamento da rede neural ---------------------------------------*/
 /*--------------------------------------------------------------------------------------------------------*/
    /**
     * Define o estado inicial da aplicação.
     */
    private void estadoInicialTreinamento() {
        listaDadosDoGraficoTreinamento = new ArrayList<>();
        treinamentos = new ArrayList<>();

        pnAtributos.setDisable(true);
        pnClasses.setDisable(true);
        pnNeuronios.setDisable(true);
        pnNormalizacao.setDisable(true);
        pnArquivoTreino.setDisable(true);
        pnCriterioParada.setDisable(true);
        pnTaxaAprendazagem.setDisable(true);
        pnFuncaoAtivacao.setDisable(true);
        pnResultadosTreinamento.setDisable(true);
        btnTreinar.setDisable(false);
        btnIniciarTreino.setDisable(true);
        pnTeste.setDisable(true);
    }

    /**
     * Inicia fase de treinamento da rede.
     *
     * @param event
     */
    @FXML
    private void clkBtnTreinar(MouseEvent event) {
        estadoTreino();
    }

    /**
     * Configura os componentes da tela para fazer o treinamento da rede.
     */
    private void estadoTreino() {
        pnAtributos.setDisable(false);
        pnClasses.setDisable(false);
        pnNeuronios.setDisable(false);
        pnNormalizacao.setDisable(false);
        pnArquivoTreino.setDisable(false);
        pnCriterioParada.setDisable(false);
        pnTaxaAprendazagem.setDisable(false);
        pnFuncaoAtivacao.setDisable(false);
        pnResultadosTreinamento.setDisable(true);
        btnIniciarTreino.setDisable(false);
        btnTreinar.setDisable(true);
        pnTeste.setDisable(true);
    }

    /**
     * Quando o radio button Personalizado for selecionado, desabilita a text
     * field para o usuário informar a quantidade de neurônios desejado.
     *
     * @param event
     */
    @FXML
    private void clkRdPersonalizado(MouseEvent event) {
        txtQtdNeuronios.setDisable(false);
        txtQtdNeuronios.requestFocus();
    }

    /**
     * Quando for selecionado, limpa a text field da quantidade de neurônios, e
     * em seguida desabilita a mesma.
     *
     * @param event
     */
    @FXML
    private void clkRdPadrao(MouseEvent event) {
        txtQtdNeuronios.clear();
        txtQtdNeuronios.setDisable(true);
    }

    /**
     * Este método permite ao usuário selecionar o arquivo do tipo CSV para
     * efetuar o treinamento da rede neural.
     *
     * @param event
     */
    @FXML
    private void clkBtnProcurarArqTreino(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);
        String caminho = file.getAbsolutePath();
        txtCaminhoArquivoTreino.setText(caminho);
    }

    /**
     * Este método inicia a rede neural e faz o treinamento da mesma.
     *
     * @param event
     */
    @FXML
    private void clkBtnIniciarTreino(MouseEvent event) {

        if (validarDadosTreinamento()) {
            int nmrEntrada = Integer.parseInt(txtAtributos.getText());
            int nmrSaida = Integer.parseInt(txtClasses.getText());

            int nmrCamadaOculta = 0;
            if (rdPadrao.isSelected()) {
                nmrCamadaOculta = (nmrEntrada + nmrSaida) / 2;
            } else if (rdPersonalizado.isSelected()) {
                nmrCamadaOculta = Integer.parseInt(txtQtdNeuronios.getText());
            }

            double limiteErro = Double.parseDouble(txtErro.getText());
            int limiteEpocas = Integer.parseInt(txtEpocas.getText());
            double taxaAprendizado = Double.parseDouble(txtTaxaAprendizagem.getText());
            Util util = new Util();

            mlp = new RedeNeural(nmrEntrada, nmrSaida, nmrCamadaOculta, limiteErro, limiteEpocas, taxaAprendizado);

            String caminhoArquivo = txtCaminhoArquivoTreino.getText();
            ArrayList<String[]> dadosArquivo = new Arquivo().obterDados(caminhoArquivo);
            ArrayList<double[][]> dadosConvertidos = util.converterDados(dadosArquivo, nmrEntrada, nmrSaida);
            double[][] matrizAmostras = dadosConvertidos.get(0);
            double[][] matrizSaidasEsperadas = dadosConvertidos.get(1);

            if (rdSim.isSelected()) {
                parametros = util.obterParametrosNormalizacao(matrizAmostras);
                matrizAmostras = util.normalizarDados(matrizAmostras, parametros);
            }

            mlp.setDadosTreinamento(matrizAmostras, matrizSaidasEsperadas, dadosArquivo.size(), nmrEntrada, nmrSaida);

            if (rdLinear.isSelected()) {
                mlp.setFuncaoAtivacao(FuncaoAtivacao.LINEAR);
            } else if (rdLogistica.isSelected()) {
                mlp.setFuncaoAtivacao(FuncaoAtivacao.LOGISTICA);
            } else {
                mlp.setFuncaoAtivacao(FuncaoAtivacao.HIPERBOLICA);
            }

            limparConsoleTreinamento();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Por favor, aguarde o fim do treinamento!");
            alert.showAndWait();
            mlp.treinar();
            txtConsoleTreinamento.appendText(mlp.resultadoTreinamento());
            preencherResultadosTreinamento();
            //limparGraficoTreinameto();
            preencherGraficoTreinamento();
            estadoInicialTeste();
        }
    }

    /**
     * Valida todos os campos da tela de treinamento.
     *
     * @return boolean - true todos os dados estiver corrtos, ou false se tiver
     * algo incorreto
     */
    private boolean validarDadosTreinamento() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (txtAtributos.getText().isEmpty()) {
            alert.setContentText("Por favor, informe o número de atributos do arquivo de treinamento!");
            alert.showAndWait();
            txtAtributos.requestFocus();
            return false;
        }

        try {
            int atributos = Integer.parseInt(txtAtributos.getText());
        } catch (Exception e) {
            alert.setContentText("Por favor, informe o número de atributos do arquivo de treinamento corretamente!");
            alert.showAndWait();
            txtAtributos.requestFocus();
            return false;
        }

        if (txtClasses.getText().isEmpty()) {
            alert.setContentText("Por favor, informe o número de classes do arquivo de treinamento!");
            alert.showAndWait();
            txtClasses.requestFocus();
            return false;
        }

        try {
            int classes = Integer.parseInt(txtClasses.getText());
        } catch (Exception e) {
            alert.setContentText("Por favor, informe o número de classes do arquivo de treinamento corretamente!");
            alert.showAndWait();
            txtClasses.requestFocus();
            return false;
        }

        boolean radioBtnPadrao = rdPadrao.isSelected();
        boolean radioBtnPersonalizado = rdPersonalizado.isSelected();
        if (!radioBtnPadrao && !radioBtnPersonalizado) {
            alert.setContentText("Por favor, informe o número de neurônios da camada oculta!");
            alert.showAndWait();
            pnNeuronios.requestFocus();
            return false;
        }

        if (radioBtnPersonalizado) {
            if (txtQtdNeuronios.getText().isEmpty()) {
                alert.setContentText("Por favor, informe a quantidade de neurônios da camada oculta!");
                alert.showAndWait();
                txtQtdNeuronios.requestFocus();
                return false;
            }

            try {
                int qtdNeuronios = Integer.parseInt(txtQtdNeuronios.getText());
            } catch (Exception e) {
                alert.setContentText("Por favor, informe a quantidade de neurônios da camada oculta corretamente!");
                alert.showAndWait();
                txtQtdNeuronios.requestFocus();
                return false;
            }
        }

        boolean radioBtnSim = rdSim.isSelected();
        boolean radioBtnNao = rdNao.isSelected();
        if (!radioBtnSim && !radioBtnNao) {
            alert.setContentText("Por favor, informe o tipo de normalização dos dados!");
            alert.showAndWait();
            pnNormalizacao.requestFocus();
            return false;
        }

        if (txtCaminhoArquivoTreino.getText().isEmpty()) {
            alert.setContentText("Por favor, selecione o arquivo de treinamento!");
            alert.showAndWait();
            btnProcurarArqTreino.requestFocus();
            return false;
        }

        if (txtErro.getText().isEmpty()) {
            alert.setContentText("Por favor, informe o limite de erro da rede!");
            alert.showAndWait();
            txtErro.requestFocus();
            return false;
        }

        try {
            double erro = Double.parseDouble(txtErro.getText());
        } catch (Exception e) {
            alert.setContentText("Por favor, informe o limite de erro da rede corretamente!");
            alert.showAndWait();
            txtErro.requestFocus();
            return false;
        }

        if (txtEpocas.getText().isEmpty()) {
            alert.setContentText("Por favor, informe o limite de épocas da rede!");
            alert.showAndWait();
            txtEpocas.requestFocus();
            return false;
        }

        try {
            double epocas = Double.parseDouble(txtEpocas.getText());
        } catch (Exception e) {
            alert.setContentText("Por favor, informe o limite de épocas da rede corretamente!");
            alert.showAndWait();
            txtEpocas.requestFocus();
            return false;
        }

        if (txtTaxaAprendizagem.getText().isEmpty()) {
            alert.setContentText("Por favor, informe a taxa de aprendizado da rede!");
            alert.showAndWait();
            txtTaxaAprendizagem.requestFocus();
            return false;
        }

        try {
            double taxa = Double.parseDouble(txtTaxaAprendizagem.getText());
        } catch (Exception e) {
            alert.setContentText("Por favor, informe a taxa de aprendizado da rede corretamente!");
            alert.showAndWait();
            txtTaxaAprendizagem.requestFocus();
            return false;
        }

        boolean radioBtnLinear = rdLinear.isSelected();
        boolean radioBtnLogistica = rdLogistica.isSelected();
        boolean radioBtnHiperbolica = rdHiperbolica.isSelected();
        if (!radioBtnLinear && !radioBtnLogistica && !radioBtnHiperbolica) {
            alert.setContentText("Por favor, informe a função de ativação da rede!");
            alert.showAndWait();
            pnFuncaoAtivacao.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Limpa a text area de treinamento.
     */
    private void limparConsoleTreinamento() {
        pnResultadosTreinamento.setDisable(false);
        txtConsoleTreinamento.setDisable(false);
        txtConsoleTreinamento.clear();
    }

    /**
     * preenche a text area de resultados dos treinamentos.
     */
    private void preencherResultadosTreinamento() {
        limparResultadosTreinamentos();
        
        Treino treino = new Treino();
        treino.setId(treinamentos.size() + 1);
        treino.setTaxaAprendizado(mlp.getTaxaAprendizado());
        treino.setAtivacao(mlp.getFuncaoAtivacao());
        treino.setErro(mlp.getErroMedioDaRede());
        treinamentos.add(treino);
        if (treinamentos.size() > 1) {
            ordenarTreinamentos();
        }
        
        for (Treino t : treinamentos) {
            txtResultadosTreinamentos.appendText(t.toString() + "\n");
        }
    }
    
    /**
     * Ordena a lista de treinamentos do menor erro, para o maior erro.
     */
    private void ordenarTreinamentos() {
        
        List<Treino> list = new ArrayList<>();
        
        for (int i = 0; i < treinamentos.size() - 1; i++) {
            for (int j = i+1; j < treinamentos.size(); j++) {
                Treino ti = treinamentos.get(i);
                Treino tj = treinamentos.get(j);
                
                if (tj.getErro() < ti.getErro()) {
                    treinamentos.set(i, tj);
                    treinamentos.set(j, ti);
                }
            }
        }
            
    }

    /**
     * Limpa a text area de resultados dos treinamentos.
     */
    private void limparResultadosTreinamentos() {
        pnResultadosTreinamento.setDisable(false);
        txtResultadosTreinamentos.setDisable(false);
        txtResultadosTreinamentos.clear();
    }

    /**
     * Preenche o gráfico de treinamento com os valores de épocas e erros da
     * rede neural.
     */
    private void preencherGraficoTreinamento() {
        double erro;
        int epoca;
        int treino = listaDadosDoGraficoTreinamento.size() + 1;
        errosDaRede = mlp.getErrosDaRede();
        epocasDaRede = mlp.getEpocasDaRede();
        dadosTreinamento = new XYChart.Series();

        for (int i = 0; i < errosDaRede.size(); i++) {
            erro = errosDaRede.get(i);
            epoca = epocasDaRede.get(i);
            dadosTreinamento.getData().add(new XYChart.Data(epoca, erro));
        }
        dadosTreinamento.setName("Treino" + treino);
        listaDadosDoGraficoTreinamento.add(dadosTreinamento);
        
        graficoTreinamento.getData().addAll(dadosTreinamento);

    }

    /**
     * Limpa o gráfico de treinamento.
     */
    private void limparGraficoTreinameto() {
        pnResultadosTreinamento.setDisable(false);
        for (int i = 0; i < listaDadosDoGraficoTreinamento.size(); i++) {
            XYChart.Series serie = listaDadosDoGraficoTreinamento.get(i);
            graficoTreinamento.getData().remove(serie);
        }
    }

    /**
     * Limpa os componentes da tela, e retorna ao estado inicial.
     *
     * @param event
     */
    @FXML
    private void clkBtnCancelarTreinamento(MouseEvent event) {
        limparComponentesTreinamento();
        estadoInicialTreinamento();
    }

    /**
     * Limpa os componentes da tela de treinamento.
     */
    private void limparComponentesTreinamento() {
        txtAtributos.clear();
        txtClasses.clear();
        rdPadrao.setSelected(false);
        rdPersonalizado.setSelected(false);
        txtQtdNeuronios.clear();
        rdSim.setSelected(false);
        rdNao.setSelected(false);

        txtCaminhoArquivoTreino.clear();

        txtErro.clear();
        txtEpocas.clear();

        txtTaxaAprendizagem.clear();

        rdLinear.setSelected(false);
        rdLogistica.setSelected(false);
        rdHiperbolica.setSelected(false);

        limparConsoleTreinamento();
        limparResultadosTreinamentos();
        limparGraficoTreinameto();
    }

    /*--------------------------------------------------------------------------------------------------------*/
    /*---------------------------- Parte de Testes da rede neural --------------------------------------------*/
    /*--------------------------------------------------------------------------------------------------------*/
    
    /**
     * Configura a tela para o estado inicial de teste.
     */
    private void estadoInicialTeste() {
        pnTreinamento.setDisable(false);
        pnTeste.setDisable(false);
        pnArquivoTeste.setDisable(true);
        btnTestar.setDisable(false);
        btnIniciarTeste.setDisable(true);
        pnResultadoTeste.setDisable(true);
    }

    /**
     * Inicia fase de testes da rede
     *
     * @param event
     */
    @FXML
    private void clkBtnTestar(MouseEvent event) {
        estadoTeste();
    }

    /**
     * Configura os componentes da tela para testar a rede.
     */
    private void estadoTeste() {
        pnTreinamento.setDisable(true);
        pnArquivoTeste.setDisable(false);
        btnTestar.setDisable(true);
        btnIniciarTeste.setDisable(false);
    }

    /**
     * Este método permite ao usuário selecionar o arquivo do tipo CSV para
     * efetuar o teste da rede neural.
     *
     * @param event
     */
    @FXML
    private void clkBtnProcurarArqTeste(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);
        String caminho = file.getAbsolutePath();
        txtCaminhoArquivoTeste.setText(caminho);
    }

    /**
     * Inicia a fase de testes da rede neural.
     *
     * @param event
     */
    @FXML
    private void clkBtnIniciarTeste(MouseEvent event) {
        if (validarDadosTeste()) {
            Util util = new Util();
            int nEntrada = Integer.parseInt(txtAtributos.getText());
            int nSaida = Integer.parseInt(txtClasses.getText());

            String caminhoArquivo = txtCaminhoArquivoTeste.getText();
            ArrayList<String[]> dadosArquivo = new Arquivo().obterDados(caminhoArquivo);
            ArrayList<double[][]> dadosConvertidos = util.converterDados(dadosArquivo, nEntrada, nSaida);

            double[][] dadosTeste = dadosConvertidos.get(0);
            double[][] SaidasEsperadas = dadosConvertidos.get(1);

            if (rdSim.isSelected()) {
                // normaliza os dados.
                dadosTeste = util.normalizarDados(dadosTeste, parametros);
            }

            mlp.testar(dadosTeste, SaidasEsperadas);
            mostrarResultadosDoTeste();
        }
        
    }
    
    /**
     * Valida os dados para o teste da rede
     * @return boolean - true se estiver correto, caso contrário false.
     */
    private boolean validarDadosTeste() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (txtCaminhoArquivoTeste.getText().isEmpty()) {
            alert.setContentText("Por favor, informe o arquivo para teste!");
            alert.showAndWait();
            btnProcurarArqTeste.requestFocus();
            return false;
        }
        
        return true;
    }
    
    /**
     * Exibe os resultados do teste na rede.
     */
    private void mostrarResultadosDoTeste() {
        pnResultadoTeste.setDisable(false);
        
        String strTotal = String.valueOf(mlp.getQtdDadosTeste());
        txtTotal.setText(strTotal);
        
        String strAcertos = String.valueOf(mlp.getTotalAcertos());
        txtAcertos.setText(strAcertos);
        
        String strErros = String.valueOf(mlp.getTotalErros());
        txtErros.setText(strErros);
        
        String strAcuracia = String.format("%.2f", mlp.getAcuracia());
        txtAcuracia.setText(strAcuracia + " %");
        
        limparMatrizConfusao();
        montarMatrizConfusao();
    }
    
    /**
     * Limpa da matriz de confusão do teste.
     */
    private void limparMatrizConfusao() {
        matrizConfusao.getChildren().clear();
    }
    
    /**
     * Monta a matriz de confusão do teste.
     */
    private void montarMatrizConfusao() {
        int classe;
        int coluna;
        int linha;
        int[][] matriz = mlp.getMatrizConfusao();
        int[] vetTotalAmostras = getTotalAmostras(matriz);
        double[] vetAcuraciaClasse = getAcuraciaDaClasse(matriz);
        TextField textField;
        
        // monta o cabeçalho
        for (int i = 0; i < matriz.length; i++) {
            classe = i+1;
            coluna = i+1;
            linha = i+1;
            textField = getTextFieldCabecalho();
            textField.setText("Classe " + classe);
            matrizConfusao.add(textField, coluna, 0, 1, 1);
            
            textField = getTextFieldCabecalho();
            textField.setText("Classe " + classe);
            matrizConfusao.add(textField, 0, linha, 1, 1);
        }
        
        linha = matriz.length + 1;
        coluna = matriz.length + 1;
        
        textField = getTextFieldCabecalho();
        textField.setText("Total");
        matrizConfusao.add(textField, 0, linha, 1, 1);
        
        textField = getTextFieldCabecalho();
        textField.setText("Acurácia");
        matrizConfusao.add(textField, coluna, 0, 1, 1);
        
        // preencher com os dados
        
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (i == j) {
                    textField = getTextFieldAcerto();
                } else {
                    textField = getTextFieldErro();
                }
                
                textField.setText(String.valueOf(matriz[i][j]));
                coluna = j + 1;
                linha = i + 1;
                matrizConfusao.add(textField, coluna, linha, 1, 1);
            }
        }
        
        for (int i = 0; i < vetTotalAmostras.length; i++) {
            linha = matriz.length + 1;
            coluna = i + 1;
            textField = getTextFieldTotal();
            textField.setText(String.valueOf(vetTotalAmostras[i]));
            matrizConfusao.add(textField, coluna, linha, 1, 1);
        }
        
        for (int i = 0; i < vetAcuraciaClasse.length; i++) {
            linha = i + 1;
            coluna = matriz.length + 1;
            textField = getTextFieldAcuracia();
            String strAcuracia = String.format("%.0f", vetAcuraciaClasse[i]);
            textField.setText(strAcuracia + " %");
            matrizConfusao.add(textField, coluna, linha, 1, 1);
        }
        
    }
    
    /**
     * Calcula o total de amostras de cada coluna da matriz de confusão.
     * @param matriz int[][] - matriz de confusão
     * @return int[] - vetor com somatório das amostras de cada coluna
     */
    private int[] getTotalAmostras(int[][] matriz) {
        int[] vet = new int[matriz.length];
        int total;
        
        for (int i = 0; i < matriz[0].length; i++) {
            total = 0;
            for (int j = 0; j < matriz.length; j++) {
                total += matriz[j][i];
            }
            vet[i] = total;
        }
        
        return vet;
    }
    
    /**
     * Calcula a acurácia de cada classedo arquivo de teste.
     * @param matriz int[][] - matriz de confusão
     * @return double[] - vetor de acurácia de classe.
     */
    private double[] getAcuraciaDaClasse(int[][] matriz) {
        double[] vetAcuracia = new double[matriz.length];
        double acuracia;
        int acertos = 0;
        int totalLinha;
        for (int i = 0; i < matriz.length; i++) {
            totalLinha = 0;
            for (int j = 0; j < matriz[0].length; j++) {
                if (i == j) {
                    acertos = matriz[i][j];
                }
                totalLinha += matriz[i][j];
            }
            acuracia = ((double) acertos / totalLinha) * 100.0;
            vetAcuracia[i] = acuracia;
        }
        
        return vetAcuracia;
    }

    
    /**
     * 
     * @return TextField - TextField formatado para o cabeçalho
     */
    private TextField getTextFieldCabecalho() {
        TextField textField = new TextField();
        textField.setEditable(false);
        textField.setPrefWidth(90.0);
        textField.setPrefHeight(30.0);
        textField.setStyle("-fx-background-color: lightblue;"
            + "-fx-border-color: black;"
            + "-fx-font-weight: bold;"
            + "-fx-alignment: center;");
        
        return textField;
    }
    
    /**
     * 
     * @return TextField - TextField para uma classificação incorreta
     */
    private TextField getTextFieldAcerto() {
        TextField textField = new TextField();
        textField.setEditable(false);
        textField.setPrefWidth(90.0);
        textField.setPrefHeight(30.0);
        textField.setStyle("-fx-border-color: black;"
                + "-fx-font-weight: bold;"
                + "-fx-alignment: center;"
                + "-fx-background-color: lightgreen;");
        
        return textField;
    }
    
    /**
     * 
     * @return TextField - TextField para o somatório de amostas de cada classe
     */
    private TextField getTextFieldTotal() {
        TextField textField = new TextField();
        textField.setEditable(false);
        textField.setPrefWidth(90.0);
        textField.setPrefHeight(30.0);
        textField.setStyle("-fx-border-color: black;"
                + "-fx-font-weight: bold;"
                + "-fx-alignment: center;"
                + "-fx-background-color: #B1B2C7;");

        return textField;
    }
    
    private TextField getTextFieldAcuracia() {
        TextField textField = new TextField();
        textField.setEditable(false);
        textField.setPrefWidth(90.0);
        textField.setPrefHeight(30.0);
        textField.setStyle("-fx-border-color: black;"
                + "-fx-font-weight: bold;"
                + "-fx-alignment: center;"
                + "-fx-background-color: #EDD4FE;");

        return textField;
    }
    
    
    
    /**
     * 
     * @return TextField - TextField para uma classificação correta
     */
    private TextField getTextFieldErro() {
        TextField textField = new TextField();
        textField.setEditable(false);
        textField.setPrefWidth(90.0);
        textField.setPrefHeight(30.0);
        textField.setStyle("-fx-border-color: black;"
                + "-fx-alignment: center;");

        return textField;
    }
    
    /**
     * Limpa os componentes da tela e retorna ao estado de teste
     *
     * @param event
     */
    @FXML
    private void clkBtnCancelarTeste(MouseEvent event) {
        limparComponentesTeste();
        estadoInicialTeste();
    }

    /**
     * Limpa os componentes da tela de testes.
     */
    private void limparComponentesTeste() {
        txtCaminhoArquivoTeste.clear();
        txtTotal.clear();
        txtAcertos.clear();
        txtErros.clear();
        txtAcuracia.clear();
        limparMatrizConfusao();
    }

}
