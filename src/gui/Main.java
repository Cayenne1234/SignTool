package gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
	}
	
}
