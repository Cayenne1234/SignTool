package gui;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import signer.MMakeCert;
import gui.JGenKeyController;

public class MakeCertController {
	@FXML Button create;
	@FXML TextField storename, certname, certfilename;
	
	MMakeCert mc = new MMakeCert();
	
	@FXML
	private void createButtonOnAction() throws IOException{
		//Storename and Certfilename must not be empty
		if(storename.getText().trim().isEmpty() || certname.getText().trim().isEmpty() || certfilename.getText().trim().isEmpty()){
			Alert alert = JGenKeyController.createAlert(AlertType.ERROR, "Required fields not initialized", "In order to generate keys all fields must be filled.","Missing information required" );
			alert.showAndWait();
		}
		else{
		
		//set properties and create certificate
		mc.setCertfilename(certfilename.getText());
		mc.setCertname(certname.getText());
		mc.setStorename(storename.getText());
		mc.makecert();
		success();
		}
		
	
	}
	
	private void success(){
		//alert
		Alert alert = JGenKeyController.createAlert(AlertType.INFORMATION, "Keystore created!","Keystore successfully created!", "" );
		ImageView imageview = new ImageView(new Image("success_75.png"));
		alert.setGraphic(imageview);
		Optional<ButtonType> result = alert.showAndWait();
		
		//close window
		if(result.get() == ButtonType.OK){
			
			Stage stage = (Stage) create.getScene().getWindow();
			stage.close();
		}

	}

}
