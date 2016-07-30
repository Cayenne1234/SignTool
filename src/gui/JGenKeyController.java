package gui;

import java.io.File;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import signer.JKeytool;

public class JGenKeyController {
	@FXML TextField alias, storename,  name, ou, unit, city, county, countrycode, Exstorename;
	@FXML PasswordField storepw, keypw;
	@FXML Button browseButton, genkeyButton, browseDir; 
	@FXML RadioButton ExKeystore, NewKeystore;
	File temp;
	
	
	JKeytool jk = new JKeytool();
	
	@FXML
	private void genkeyButtonOnAction() throws Exception{
		
		if(temp != null){		
			if(NewKeystore.isSelected()){
				if(storename.getText().trim().isEmpty()){
					Alert alert = createAlert(AlertType.ERROR, "Required fields not initialized", "In order to generate keys all fields must be filled.","Missing information required" );
					alert.showAndWait();
				}
				temp = new File(temp.getPath()+File.separator+storename.getText());	
			}
			
			jk.setKeystore(temp);
			System.out.println(jk.getKeystore().getAbsolutePath());
			
		}
		if(alias.getText().trim().isEmpty()|| name.getText().trim().isEmpty()|| ou.getText().trim().isEmpty()|| unit.getText().trim().isEmpty()|| city.getText().trim().isEmpty()|| county.getText().trim().isEmpty()|| countrycode.getText().trim().isEmpty() || storepw.getText().trim().isEmpty()){
		
			Alert alert = createAlert(AlertType.ERROR, "Required fields not initialized", "In order to generate keys all fields must be filled.","Missing information required" );
			alert.showAndWait();

		}
		else if(storepw.getLength() < 6 || !keypw.getText().trim().isEmpty() && keypw.getLength() < 6){ 
			Alert alert = createAlert(AlertType.ERROR,"Password too short!","Passwords must be at least 6 characters long", "Password too short!" );
			alert.showAndWait();
		}
				
		else{
			jk.setAlias(alias.getText());
			jk.setName(name.getText());
			jk.setUnit(unit.getText());
			jk.setOu(ou.getText());
			jk.setCity(city.getText());
			jk.setCounty(county.getText());
			jk.setCountrycode(countrycode.getText());
			jk.setStorename(storename.getText());
			jk.setStorepw(storepw.getText());
			
			if(!keypw.getText().trim().isEmpty()) jk.setKeypw(keypw.getText());
			
			if(jk.getKeystore() == null){
				jk.setKeystore(new File(storename.getText()));
			}
			
			//Keystore already existent?
			
			File existing = jk.getKeystore();		
			if(NewKeystore.isSelected() && existing.exists()){
					Alert alert = createAlert(AlertType.ERROR,"Keystore already exists", "Please choose a new name or select 'existing keystore'", "Keystore already exists" );
					alert.showAndWait();
				}
			
			else{
				jk.generateKey();
				success();
			}

		}
		
	}

	
	@FXML
	private void browseDirOnAction(){
		DirectoryChooser dirchooser = new DirectoryChooser();
		dirchooser.setTitle("Select directory for keystore");
		temp = dirchooser.showDialog(browseDir.getScene().getWindow());
	}
	
	@FXML
	private void browseButtonOnAction(){
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Keystore");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java Keystore","*.jks"));
		temp = fileChooser.showOpenDialog(browseButton.getScene().getWindow());
		if (temp != null) {
			Exstorename.setText(jk.getKeystore().getPath());
		 }
	}
	
	@FXML
	private void ccOnKeyTyped(KeyEvent arg0){
		if(countrycode.getLength() >= 2) arg0.consume();
	}
	
	public void success(){
		Alert alert = createAlert(AlertType.INFORMATION, "Keystore created!", "Keystore successfully created!", "");
		ImageView imageview = new ImageView(new Image("success_75.png"));
		alert.setGraphic(imageview);
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK){

			Stage stage = (Stage) genkeyButton.getScene().getWindow();
			stage.close();
		}

	}
	
	@FXML
	private void selectExisting(){
		//enable textfield for existing keystore
		Exstorename.setDisable(false);
		browseButton.setDisable(false);
		
		//disable textfield for new keystore
		storename.setDisable(true);
		browseDir.setDisable(true);
		
	}
	
	@FXML
	private void selectNew(){
		//enable textfield for new keystore
		storename.setDisable(false);
		browseDir.setDisable(false);
		
		//disable textfield for existing keystore
		Exstorename.setDisable(true);
		browseButton.setDisable(true);
		
	}
	
	public static Alert createAlert(AlertType type, String title, String content, String header){
		Alert a = new Alert(type);
		a.setTitle(title);
		a.setContentText(content);
		a.setHeaderText(header);
		return a;
	}
}
