/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.net.URL;
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

/**
 * FXML Controller class
 *
 * @author Thiago
 */
public class TelaPrincipalController implements Initializable {

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
    private RadioButton rdLogistica;
    @FXML
    private RadioButton rdHiperbolica;
    @FXML
    private Button btnTreinar;
    @FXML
    private ToggleGroup grupoCriterioParada;
    @FXML
    private ToggleGroup grupoFuncaoAtivacao;
    @FXML
    private ProgressBar progressoTreinamento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void clkBtnProcurar(MouseEvent event) {
    }

    @FXML
    private void clkBtnTreinar(MouseEvent event) {
    }
    
}
