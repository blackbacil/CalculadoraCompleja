package Calculadora;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {
	

	
	//modelo
	private Complejo operador1 = new Complejo();
	private Complejo operador2 = new Complejo();
	private Complejo resultado = new Complejo();
	private StringProperty operador = new SimpleStringProperty();
	
	//vista
	private TextField operando1RealText;
	private TextField operando1ImagText;
	private TextField operando2RealText;
	private TextField operando2ImagText;
	private TextField resultadoRealText;
	private TextField resultadoImagText;
	private ComboBox<String> operadorCombo;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		operando1RealText = new TextField();
		operando1RealText.setPrefColumnCount(4);
		
		operando1ImagText = new TextField();
		operando1ImagText.setPrefColumnCount(4);
		
		operando2RealText = new TextField();
		operando2RealText.setPrefColumnCount(4);
		
		operando2ImagText = new TextField();
		operando2ImagText.setPrefColumnCount(4);
		
		resultadoRealText = new TextField();
		resultadoRealText.setPrefColumnCount(4);
		resultadoRealText.setDisable(false);
		
		resultadoImagText = new TextField();
		resultadoImagText.setPrefColumnCount(4);
		resultadoImagText.setDisable(false);
		
		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");
		
		HBox operando1 = new HBox(5, operando1RealText,new Label("+"),  operando1ImagText, new Label("i"));
		//operando1.setAlignment(Pos.BASELINE_CENTER);
		
		HBox operando2 = new HBox(5, operando2RealText,new Label("+"),  operando2ImagText, new Label("i"));
		//operando2.setAlignment(Pos.BASELINE_CENTER);
		
		HBox resultadoBox = new HBox(5, resultadoRealText,new Label("+"),  resultadoImagText, new Label("i"));
		//resultado.setAlignment(Pos.BASELINE_CENTER);
		
		VBox numerosBox = new VBox(5, operando1, operando2, new Separator(), resultadoBox);
		numerosBox.setAlignment(Pos.CENTER);
		
		VBox comboBox = new VBox(5, operadorCombo);
		comboBox.setAlignment(Pos.CENTER);
		
		HBox root = new HBox(5, comboBox, numerosBox);
		root.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		// bindeos
		
		Bindings.bindBidirectional(operando1RealText.textProperty(), operador1.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando1ImagText.textProperty(), operador1.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando2RealText.textProperty(), operador2.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(operando2ImagText.textProperty(), operador2.imaginarioProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultadoRealText.textProperty(), resultado.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultadoImagText.textProperty(), resultado.imaginarioProperty(), new NumberStringConverter());
		operador.bind(operadorCombo.getSelectionModel().selectedItemProperty());
		
		// listeners
		
		operador.addListener((o, ov, nv) -> onOperadorChanged(nv));

		operadorCombo.getSelectionModel().selectFirst();
		

	}

	private void onOperadorChanged(String nv) {
		switch(nv) {
		case "+":
			resultado.realProperty().bind(operador1.realProperty().add(operador2.realProperty()));
			resultado.imaginarioProperty().bind(operador1.imaginarioProperty().add(operador2.imaginarioProperty()));
			break;
		case "-":
			resultado.realProperty().bind(operador1.realProperty().subtract(operador2.realProperty()));
			resultado.imaginarioProperty().bind(operador1.imaginarioProperty().subtract(operador2.imaginarioProperty()));		
			break;
		case "*": 
			resultado.realProperty().bind((operador1.realProperty().multiply(operador2.realProperty())).subtract(operador1.imaginarioProperty().multiply(operador2.imaginarioProperty())));
			resultado.imaginarioProperty().bind((operador1.realProperty().multiply(operador2.imaginarioProperty())).add(operador1.imaginarioProperty().multiply(operador2.realProperty())));
			break;
		case "/":
			resultado.realProperty().bind(((operador1.realProperty().multiply(operador2.realProperty())).add(operador1.imaginarioProperty().multiply(operador2.imaginarioProperty()))).divide((operador2.realProperty().multiply(operador2.realProperty())).add((operador2.imaginarioProperty()).multiply(operador2.imaginarioProperty()))));
			resultado.imaginarioProperty().bind(((operador1.imaginarioProperty().multiply(operador2.realProperty())).subtract(operador1.realProperty().multiply(operador2.imaginarioProperty()))).divide((operador2.realProperty().multiply(operador2.realProperty())).add((operador2.imaginarioProperty()).multiply(operador2.imaginarioProperty()))));
			break;
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

}
