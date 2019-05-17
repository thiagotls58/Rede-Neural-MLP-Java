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
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
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
public class TreinamentoController implements Initializable {

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
    private ProgressBar progressoTreinamento;
    @FXML
    private TextField txtAtributos;
    @FXML
    private TextField txtClasses;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    /**
     * Este método permite ao usuário selecionar o arquivo do tipo CSV para
     * efetuar o treinamento da rede neural.
     * @param event 
     */
    @FXML
    private void clkBtnProcurar(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.
                ExtensionFilter("CSV files", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(null);
        String caminho = file.getAbsolutePath();
        txtCaminhoArquivo.setText(caminho);
    }

    /**
     * Este método inicia a rede neural e faz o treinamento da mesma.
     * @param event 
     */
    @FXML
    private void clkBtnTreinar(MouseEvent event) {
        
        String caminhoArquivo = txtCaminhoArquivo.getText();
        int nEntrada = Integer.parseInt(txtAtributos.getText());
        int nSaida = Integer.parseInt(txtClasses.getText());
        int nCamadaOculta = (nEntrada + nSaida) / 2;
        double taxaAprendizado = Double.parseDouble(txtTaxaAprendizagem.getText());
        
        List<String> dadosArquivo = new Arquivo().obterDados(caminhoArquivo);
        double[][] dadosTreinamento = new Util().
                converterEntrada(new ArrayList(dadosArquivo), nEntrada);
        double[] saidaEsperada = new Util().
                converterSaida(new ArrayList(dadosArquivo), nEntrada);
        
        RedeNeural mlp = new RedeNeural(taxaAprendizado, nEntrada, nCamadaOculta);
        
        mlp.treinar(dadosTreinamento, saidaEsperada);
        
    }
    
}
