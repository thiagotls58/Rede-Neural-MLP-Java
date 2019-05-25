/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mlp;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Thiago
 */
public class PrincipalController implements Initializable {

    @FXML
    private AnchorPane pnArquivo;
    @FXML
    private TextField txtCaminhoArquivo;
    @FXML
    private Button btnProcurar;
    @FXML
    private AnchorPane pnCriterioParada;
    @FXML
    private RadioButton rdErro;
    @FXML
    private ToggleGroup grupoCriterioParada;
    @FXML
    private RadioButton rdIteracao;
    @FXML
    private TextField txtCriterioParada;
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
    private TextArea txtSaidas;
    @FXML
    private Button btnIniciarTreino;
    @FXML
    private Button btnTestar;
    @FXML
    private Button btnIniciarTeste;
    @FXML
    private Button btnCancelar;
    
    /**
     * Objeto da rede neural.
     */
    RedeNeural mlp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        estadoInicial();
    }

    /**
     * Este método permite ao usuário selecionar o arquivo do tipo CSV para
     * efetuar o treinamento da rede neural.
     *
     * @param event
     */
    @FXML
    private void clkBtnProcurar(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);
        String caminho = file.getAbsolutePath();
        txtCaminhoArquivo.setText(caminho);

    }

    /**
     * Inicia fase de treinamento da rede.
     * @param event 
     */
    @FXML
    private void clkBtnTreinar(MouseEvent event) {
        estadoTreino();
    }

    /**
     * Este método inicia a rede neural e faz o treinamento da mesma.
     *
     * @param event
     */
    @FXML
    private void clkBtnIniciarTreino(MouseEvent event) {
        
        int nEntrada = Integer.parseInt(txtAtributos.getText());
        int nSaida = Integer.parseInt(txtClasses.getText());
        int nCamadaOculta = (nEntrada + nSaida) / 2;
        double taxaAprendizado = Double.parseDouble(txtTaxaAprendizagem.getText());
        Util util = new Util();

        mlp = new RedeNeural(taxaAprendizado, nEntrada, nCamadaOculta, nSaida);

        String caminhoArquivo = txtCaminhoArquivo.getText();
        ArrayList<String[]> dadosArquivo = new Arquivo().obterDados(caminhoArquivo);
        ArrayList<double[][]> dadosConvertidos = util.converterDados(dadosArquivo, nEntrada, nSaida);
        double[][] matrizAmostras = dadosConvertidos.get(0);
        double[][] matrizSaidasEsperadas = dadosConvertidos.get(1);
        double[][] amostrasNormalizadas = util.normalizarDados(matrizAmostras);

        // Minha base de dados
        //mlp.setDadosTreinamento(matrizAmostras, matrizSaidasEsperadas, dadosArquivo.size(), nEntrada, nSaida);
        
        // Base de dados do professor
        mlp.setDadosTreinamento(amostrasNormalizadas, matrizSaidasEsperadas, dadosArquivo.size(), nEntrada, nSaida);

        if (rdErro.isSelected()) {
            double limiteErro = Double.parseDouble(txtCriterioParada.getText());
            mlp.setLimiteErro(limiteErro);
        } else {
            int limiteEpocas = Integer.parseInt(txtCriterioParada.getText());
            mlp.setLimiteEpocas(limiteEpocas);
        }

        if (rdLinear.isSelected()) {
            mlp.setFuncaoAtivacao(FuncaoAtivacao.LINEAR);
        } else if (rdLogistica.isSelected()) {
            mlp.setFuncaoAtivacao(FuncaoAtivacao.LOGISTICA);
        } else {
            mlp.setFuncaoAtivacao(FuncaoAtivacao.HIPERBOLICA);
        }

        txtSaidas.clear();
        mlp.treinar();
        txtSaidas.appendText(mlp.resultadoTreinamento());
        btnTestar.setDisable(false);
    }

    /**
     * Inicia fase de testes da rede
     * @param event 
     */
    @FXML
    private void clkBtnTestar(MouseEvent event) {
        estadoTeste();
    }

    @FXML
    private void clkBtnIniciarTeste(MouseEvent event) {
        Util util = new Util();
        int nEntrada = Integer.parseInt(txtAtributos.getText());
        int nSaida = Integer.parseInt(txtClasses.getText());
        
        String caminhoArquivo = txtCaminhoArquivo.getText();
        ArrayList<String[]> dadosArquivo = new Arquivo().obterDados(caminhoArquivo);
        ArrayList<double[][]> dadosConvertidos = util.converterDados(dadosArquivo, nEntrada, nSaida);
        
        double[][] dadosTeste = dadosConvertidos.get(0);
        double[][] SaidasEsperadas = dadosConvertidos.get(1);
        double[][] dadosTesteNormalizados = util.normalizarDados(dadosTeste);
        
        txtSaidas.clear();
        // Minha base de dados
        //mlp.testar(dadosTeste, SaidasEsperadas);
        
        // Base de dados do professor
        mlp.testar(dadosTesteNormalizados, SaidasEsperadas);
        txtSaidas.appendText(mlp.resultadoTeste());
    }

    /**
     * Limpa todos os componentes da tela, e retorna para o estado inicial
     * @param event 
     */
    @FXML
    private void clkBtnCancelar(MouseEvent event) {
        limparComponentes();
        estadoInicial();
    }

    /**
     * Define estado inicial dos componentes da aplicação.
     */
    private void estadoInicial() {
        txtAtributos.setDisable(true);
        txtClasses.setDisable(true);
        pnArquivo.setDisable(true);
        pnCriterioParada.setDisable(true);
        pnTaxaAprendazagem.setDisable(true);
        pnFuncaoAtivacao.setDisable(true);
        txtSaidas.setDisable(true);
        btnTreinar.setDisable(false);
        btnIniciarTreino.setDisable(true);
        btnTestar.setDisable(true);
        btnIniciarTeste.setDisable(true);
    }

    /**
     * Limpa todos os componentes da tela.
     */
    private void limparComponentes() {
        txtAtributos.clear();
        txtClasses.clear();

        txtCaminhoArquivo.clear();

        rdErro.setSelected(false);
        rdIteracao.setSelected(false);
        txtCriterioParada.clear();

        txtTaxaAprendizagem.clear();

        rdLinear.setSelected(false);
        rdLogistica.setSelected(false);
        rdHiperbolica.setSelected(false);

        txtSaidas.clear();
    }

    /**
     * Configura os componentes da tela para fazer o treinamento da rede.
     */
    private void estadoTreino() {
        txtAtributos.setDisable(false);
        txtClasses.setDisable(false);
        pnArquivo.setDisable(false);
        pnCriterioParada.setDisable(false);
        pnTaxaAprendazagem.setDisable(false);
        pnFuncaoAtivacao.setDisable(false);
        txtSaidas.setDisable(false);
        btnTreinar.setDisable(true);
        btnIniciarTreino.setDisable(false);
        btnTestar.setDisable(true);
        btnIniciarTeste.setDisable(true);
    }

    /**
     * Configura os componentes da tela para testar a rede.
     */
    private void estadoTeste() {
        txtAtributos.setDisable(false);
        txtClasses.setDisable(false);
        pnArquivo.setDisable(false);
        pnCriterioParada.setDisable(true);
        pnTaxaAprendazagem.setDisable(true);
        pnFuncaoAtivacao.setDisable(true);
        txtSaidas.setDisable(false);
        txtSaidas.clear();
        btnTreinar.setDisable(true);
        btnIniciarTreino.setDisable(true);
        btnTestar.setDisable(true);
        btnIniciarTeste.setDisable(false);
        btnProcurar.requestFocus();
    }

}
