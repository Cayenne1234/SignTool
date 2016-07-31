package gui;
import java.util.Optional;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
	public Stage stage;
	public Scene scene;
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("Gui/start.fxml"));
		Pane root = (Pane) fxml.load();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.setTitle("SignTool");
		stage.show();
		
		System.out.println(System.getenv("JAVA_HOME"));
		
		if(!System.getenv("JAVA_HOME").contains("jdk1.8")) {
			Alert alert = JGenKeyController.createAlert(AlertType.ERROR,"JAVA_HOME not set to JDK","In order to use SignTool set JAVA_HOME to a JDK", "JAVA_HOME missing"  );
			Optional<ButtonType> result = alert.showAndWait();
			if(result.get() == ButtonType.OK){
				
				System.exit(0);
			}
		}
	}
	
}
