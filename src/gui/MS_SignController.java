package gui;

import java.io.File;
import java.util.Optional;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import signer.MSigner;
import gui.JGenKeyController;

public class MS_SignController {
	MSigner ms = new MSigner();
	
	@FXML Button signButton, cancelButton;
	@FXML TextField cert, storename;
	
	public void init(File file) {
		ms.setFile(file);
	}
	
//	@FXML
//	private void certBrowseOnAction(){
//		FileChooser fileChooser = new FileChooser();
//		fileChooser.setTitle("Open Resource File");
//		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Certificate","*.cer"));
//		File temp = fileChooser.showOpenDialog(cert.getScene().getWindow());
//		if (temp != null) {
//			ms.setCert(temp.getAbsolutePath());
//			cert.setText(ms.getCert()/*.getName()*/);
//		 }
//		
//	}
	
	
	//when sign Button is pressed
	@FXML
	private void signButtonOnAction(){
		//System.out.println(cert.getText().trim().isEmpty() || storename.getText().trim().isEmpty());
		//check if all fields are initialized
		if(cert.getText().trim().isEmpty() || storename.getText().trim().isEmpty()){
			Alert alert = JGenKeyController.createAlert(AlertType.ERROR, "Required fields not initialized","In order to sign a file it is required to provide a certificate and an alias.","Missing information required" );
			alert.showAndWait();
		}
		else{
			//set attributes and sign
			ms.setStorename(storename.getText());
			ms.setCert(cert.getText());
			ms.sign();
			success();
		}
	}
	
	//When cancel Button is pressed
	@FXML
	private void cancelButtonOnAction(){
		Stage stage = (Stage) cancelButton.getScene().getWindow();
		stage.close();
	}
	
	//Method for sucessful signing
	private void success(){
		//alert
		Alert alert = JGenKeyController.createAlert(AlertType.INFORMATION, "File signed!", ms.getOutput(), "" );
		Optional<ButtonType> result = alert.showAndWait();
		
		//close window
		if(result.isPresent() && result != null && result.get() == ButtonType.OK ){
		System.out.println("here2");
			Stage stage = (Stage) signButton.getScene().getWindow();
			stage.close();
		}
		

	}
}
