/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package masacorporal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 *
 * @author jorge
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private RadioButton obesidad;
    @FXML
    private RadioButton sobrepeso;
    @FXML
    private RadioButton normal;
    @FXML
    private RadioButton delgadez;
    @FXML
    private TextField altura;
    @FXML
    private TextField peso;
    @FXML
    private TextField resultado;
    @FXML
    private Button calcular;
    
    @FXML
    private void calcularIMC(ActionEvent event) {
       
        double altura = Double.parseDouble(this.altura.getText());
        double peso = Double.parseDouble(this.peso.getText());
        double resultado = peso/Math.pow(altura/100, 2);
        BigDecimal big = new BigDecimal(resultado);
        big = big.setScale(1, RoundingMode.HALF_UP);
        this.resultado.setText(String.valueOf(big));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
