package gui;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import signer.JarSigner;
import gui.JGenKeyController;


public class SignController {
	
	@FXML Button backButton, browse;
	JarSigner js = new JarSigner();
	
	@FXML TextField keystore, alias, jarname;
	@FXML PasswordField storepw, keypw;
	
	@FXML
	private void QuitOnAction(ActionEvent event){
		System.exit(0);
	}
	
	@FXML
	public void backButtonOnAction() throws Exception{
		Stage stage = (Stage) backButton.getScene().getWindow();
		FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("gui/start.fxml"));
		Pane root = (Pane) fxml.load();
		Controller c = fxml.<Controller>getController();
		c.init(js.getFile());
		Scene scene = new Scene(root);
		stage.setScene(scene);
	}
	
	public void init(File jar) throws Exception {
		js.setFile(jar);
	}
	
	@FXML
	private void signButtonOnAction(){
		System.out.println(jarname.getText());
		if(!alias.getText().trim().isEmpty() && js.getKeystore() != null && !storepw.getText().trim().isEmpty()){
			js.sign(alias.getText(), js.getKeystore().getPath(),storepw.getText(), keypw.getText(), jarname.getText());
			if(js.getOutput().contains("jar signed.")) success();
			else error();
		}
		else{
			
			Alert alert = JGenKeyController.createAlert(AlertType.ERROR,"Required fields not initialized","In order to sign a file it is required to provide a keystore, its password and an alias.", "Missing information required"  );
			alert.showAndWait();
		}
		
	}

	private void success() {
		Alert alert = JGenKeyController.createAlert(AlertType.CONFIRMATION,"Signing a jar", js.getOutput(), "");
		ImageView imageview = new ImageView(new Image("success_75.png"));
		alert.setGraphic(imageview);
		alert.showAndWait();
	}
	
	@FXML
	private void browseButtonOnAction(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Keystore","*.jks"));
		File temp = fileChooser.showOpenDialog(browse.getScene().getWindow());
		if (temp != null) {
			js.setKeystore(temp);
			keystore.setText(js.getKeystore().getName());
		 }
	}
	
	private void error() {
		Alert alert = JGenKeyController.createAlert(AlertType.ERROR,"Error signing a jar", js.getOutput(), "");
		ImageView imageview = new ImageView(new Image("error.png"));
		alert.setGraphic(imageview);
		alert.showAndWait();
	}
}
