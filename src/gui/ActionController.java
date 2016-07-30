package gui;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import signer.JarSigner;
import signer.MSigner;
import signer.Signer;

public class ActionController {

	@FXML ImageView imageView;
	@FXML CheckBox details;
	
	@FXML Button back;
	
	Signer s;
	@FXML TextArea textbox; 

	@FXML
	private void QuitOnAction(ActionEvent event){
		System.exit(0);
	}
	
	
	@FXML
	private void verifyButtonOnAction() throws Exception{
		s.verify(details.isSelected());
		
		if(s.getOutput() != null){

			if(s.getOutput().contains("unsigned")){
				imageView.setImage(new Image("error.png"));
			}
			else if(s.getOutput().contains("Warning")){
				imageView.setImage(new Image("warning.png"));
			}
			else if(s.getOutput().contains("verified")){		
				imageView.setImage(new Image("success_75.png"));
			}
		}
		textbox.setText(s.getOutput());	
		
	}

	public void init(File file) throws Exception {
		
		if(file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length()).equals(".jar")) s = new JarSigner(file);
		else if(file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length()).equals(".exe") ||file.getName().substring(file.getName().lastIndexOf('.'), file.getName().length()).equals(".dll")) s = new MSigner(file);

	}
	
	@FXML
	public void BackOnAction() throws Exception{
		Stage stage = (Stage) back.getScene().getWindow();
		FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("gui/start.fxml"));
		Pane root = (Pane) fxml.load();
		Controller c = fxml.<Controller>getController();
		c.init(s.getFile());
		Scene scene = new Scene(root);
		stage.setScene(scene);
	}

}
