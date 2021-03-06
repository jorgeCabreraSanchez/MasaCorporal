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
import java.text.ParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

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
    private ToggleGroup estado;
    @FXML
    private ScrollBar pesoscrollbar;
    @FXML
    private Slider alturaSlider;
    @FXML
    private AnchorPane fondo;
    @FXML
    private ListView<String> pesoDescripcion;

    ObservableList<String> descripcion = FXCollections.observableArrayList("(IMC > 30)", "(25 <= IMC < 29.9)", "(18.5 <=  IMC < 24.9)", "(IMC < 18.5)");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.altura.setText("40");
        this.peso.setText("20");
        this.pesoDescripcion.setItems(descripcion);

    }

    @FXML
    private void ejecutar(MouseEvent event) {
        if (event.getSource() == this.alturaSlider) {
            cambiarAlturaSlider();
        } else if (event.getSource() == this.pesoscrollbar) {
            cambiarPesoScrollBar();
        }

        calcularIMC();
    }

    private void cambiarAltura() {    
        Double altura = comprobar(this.altura.getText(), "altura");
        Double maxAltura = this.alturaSlider.getMax();
        Double minAltura = this.alturaSlider.getMin();

        if (altura > maxAltura) {
            altura = maxAltura;
            this.altura.setText(String.valueOf(maxAltura));
        } else if (altura < minAltura) {
            altura = minAltura;
            this.altura.setText(String.valueOf(minAltura));
        }

        this.alturaSlider.setValue(altura);
    }

    private Double comprobar(String altura, String tipo) {
        Double respuesta = 0.0;
        try {
            respuesta = Double.parseDouble(altura);
        } catch (NumberFormatException e) {
            try {
                respuesta = cambiarComa(altura);

            } catch (Exception ej) {
                respuesta = 0.0;
                // Alerta
                if (tipo.equalsIgnoreCase("altura")) {
                    generarErrorAltura();
                } else if (tipo.equalsIgnoreCase("peso")) {
                    generarErrorPeso();
                }
            }
        }

        return respuesta;
    }

    private void generarErrorAltura() {
        Alert Alerta = new Alert(Alert.AlertType.ERROR);
        Alerta.setTitle("Error");
        Alerta.setHeaderText("Error generado por la altura");
        Alerta.setContentText("Ha puesto un dato en la altura inadecuado");
        Optional<ButtonType> response = Alerta.showAndWait();
        if (response.isPresent() && ButtonType.OK == response.get()) {
            this.altura.setText("");
//            Alerta.getButtonTypes();
        }
    }

    private void generarErrorPeso() {
        Alert Alerta = new Alert(Alert.AlertType.ERROR);
        Alerta.setTitle("Error");
        Alerta.setHeaderText("Error generado por la altura");
        Alerta.setContentText("Ha puesto un dato en la altura inadecuado");
        Optional<ButtonType> response = Alerta.showAndWait();
        if (response.isPresent() && ButtonType.OK == response.get()) {
            this.altura.setText("");
//            Alerta.getButtonTypes();
        }
    }

    private void cambiarPeso() {
        Double peso = comprobar(this.peso.getText(), "peso");
        Double minPeso = this.pesoscrollbar.getMin();
        Double maxPeso = this.pesoscrollbar.getMax();

        if (peso > maxPeso) {
            peso = maxPeso;
        } else if (peso < minPeso) {
            peso = minPeso;
        }
        this.peso.setText(String.valueOf(peso));
        this.pesoscrollbar.setValue(peso);

    }

    private void calcularIMC() {
        DecimalFormat formato = new DecimalFormat("0.0");
        Double altura = comprobar(this.altura.getText(), "altura");
        Double peso = Double.parseDouble(this.peso.getText());

        String resultado = formato.format(peso / Math.pow(altura / 100, 2));
        this.resultado.setText(resultado);

        int num = 0;
        double IMC = cambiarComa(resultado);
        if (IMC > 30.0) {
            num = 0;
            this.obesidad.setSelected(true);
            this.resultado.setStyle("-fx-background-color: red");
        } else if (IMC >= 25 && IMC < 29.9) {
            this.sobrepeso.setSelected(true);
            num = 1;
            this.resultado.setStyle("-fx-background-color: white");
        } else if (IMC >= 18.5 && IMC < 24.9) {
            this.normal.setSelected(true);
            num = 2;
            this.resultado.setStyle("-fx-background-color: white");
        } else if (IMC < 18.5) {
            this.delgadez.setSelected(true);
            num = 3;
            this.resultado.setStyle("-fx-background-color: red");
        }

        this.pesoDescripcion.getSelectionModel().select(num);

    }

    private void cambiarAlturaSlider() {
        DecimalFormat formato = new DecimalFormat("0");
        this.altura.setText(formato.format(this.alturaSlider.getValue()));
    }

    private void cambiarPesoScrollBar() {
        DecimalFormat formato = new DecimalFormat("0");
        this.peso.setText(formato.format(this.pesoscrollbar.getValue()));
    }

    private double cambiarComa(String resultado) {
        String[] resultado1 = resultado.split("");
        resultado = "";
        for (int i = 0; i < resultado1.length; i++) {

            if (resultado1[i].equalsIgnoreCase(",")) {
                resultado1[i] = ".";
            }
            resultado += resultado1[i];
        }
        return Double.parseDouble(resultado);
    }

//    private void ejecutar(InputMethodEvent event) {
//        if (event.getSource() == this.altura) {
//            cambiarAltura();
//        } else if (event.getSource() == this.peso) {
//            cambiarPeso();
//        }
//
//        calcularIMC();
//    }
    @FXML
    private void ejecutar(ActionEvent event) {
        if (event.getSource() == this.altura) {
            cambiarAltura();
        } else if (event.getSource() == this.peso) {
            cambiarPeso();
        }

        calcularIMC();

    }

}
