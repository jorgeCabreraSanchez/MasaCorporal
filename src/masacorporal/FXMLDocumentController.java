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
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
    private ToggleGroup estado;
    @FXML
    private ScrollBar pesoscrollbar;
    @FXML
    private Slider alturaSlider;

    @FXML
    private void calcularIMC(ActionEvent event) {
        Double altura = Double.parseDouble(this.altura.getText());
        Double peso = Double.parseDouble(this.peso.getText());

        if (altura.isNaN() || peso.isNaN()) {
            this.resultado.setText("Error");
        } else {

            double resultado = peso / Math.pow(altura / 100, 2);
            BigDecimal big = new BigDecimal(resultado);
            big = big.setScale(1, RoundingMode.HALF_UP);
            this.resultado.setText(String.valueOf(big));

            double IMC = Double.parseDouble(this.resultado.getText());
            if (IMC > 30.0) {
                this.obesidad.setSelected(true);
            } else if (IMC > 25 && IMC < 29.9) {
                this.sobrepeso.setSelected(true);
            } else if (IMC > 18.5 && IMC < 24.9) {
                this.normal.setSelected(true);
            } else if (IMC < 18.5) {
                this.delgadez.setSelected(true);
            }

        }
    }

    @FXML
    private void cambiarAltura(ActionEvent event) {
        Double altura = Double.parseDouble(this.altura.getText());
        Double maxAltura = this.alturaSlider.getMax();
        Double minAltura = this.alturaSlider.getMin();
        
        if (altura > maxAltura) {
            altura = maxAltura;
            this.altura.setText(String.valueOf(maxAltura));
        } else if ( altura < minAltura){
            altura = minAltura;
            this.altura.setText(String.valueOf(minAltura));
        }
            
        this.alturaSlider.setValue(altura);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}