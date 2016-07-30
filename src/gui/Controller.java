package gui;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import signer.JKeytool;
import signer.JarSigner;
import signer.MMakeCert;
import signer.MSigner;
import signer.Signer;

public class Controller {
	
	JKeytool jk = new JKeytool();
	MMakeCert mc = new MMakeCert();
	Signer s;
	
	boolean isJar;
	
	@FXML MenuBar menuBar;
	@FXML Label labelDrag;
	@FXML Label labelFile;
	@FXML VBox vbox;
	@FXML Button verifyButton;
	@FXML AnchorPane anchor;
	@FXML Button signButton;
	@FXML Region dropzone;
	@FXML MenuItem NewPrivateKey, NewCert;

	
	@FXML
	private void ChooseOnAction(ActionEvent e) throws Exception{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Code","*.jar", "*.exe", "*.dll"));
		File temp = fileChooser.showOpenDialog(menuBar.getScene().getWindow());
		if (temp != null) {
			init(temp);
			s.setFile(temp);
			labelDrag.setText(s.getFile().getName());
		 }
	}
		
	
	@FXML
	private void newPrivateKeyOnAction() throws Exception{
		
		FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("gui/keystore.fxml"));
		Stage secondary = new Stage();	
		Scene scene = new Scene((Pane) fxml.load());
		secondary.setScene(scene);
		secondary.setTitle("New Private Key");
		secondary.show();
	}
	
	@FXML
	private void QuitOnAction(ActionEvent event){
		System.exit(0);
	}
	
	
	@FXML
	private void signButtonOnAction() throws Exception{
		Stage stage = (Stage) signButton.getScene().getWindow();
		Scene scene = null;
		
		if(s.getFile() == null){
			System.out.println("FAIL! W�hle File aus");
		}else{
			
			//javapart
			if(s.getFile().getName().substring(s.getFile().getName().lastIndexOf('.'), s.getFile().getName().length()).equals(".jar")){ 
			
				FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("gui/sign.fxml"));
				Pane root = (Pane) fxml.load();
				SignController c = fxml.<SignController>getController();
				c.init(s.getFile());
				scene = new Scene(root);
				stage.setTitle("Signing");
				stage.setScene(scene);
			}
			
			//ms part
			
			else if(s.getFile().getName().substring(s.getFile().getName().lastIndexOf('.'), s.getFile().getName().length()).equals(".exe") ||s.getFile().getName().substring(s.getFile().getName().lastIndexOf('.'), s.getFile().getName().length()).equals(".dll")){
				FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("gui/MS_sign.fxml"));
				Stage secondary = new Stage();	
				Scene scene2 = new Scene((Pane) fxml.load());
				MS_SignController c = fxml.<MS_SignController>getController();
				c.init(s.getFile());
				secondary.setScene(scene2);
				secondary.setTitle("Signing");
				secondary.show();
			}
			
		}
	}

	
	
	@FXML
	private void verifyButtonOnAction() throws Exception{
		if(s.getFile() == null){
			System.out.println("FAIL! W�hle File aus");
		}else{  
			Stage stage = (Stage) verifyButton.getScene().getWindow();
			FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("gui/verify.fxml"));
			Pane root = (Pane) fxml.load();
			ActionController c = fxml.<ActionController>getController();
			c.init(s.getFile());
			Scene scene = new Scene(root);
			stage.setScene(scene);
		}
	}
	

	
	public void init(File temp) throws Exception {
		System.out.println(temp.getName());
		if(temp.getName().substring(temp.getName().lastIndexOf('.'), temp.getName().length()).equals(".jar")) s = new JarSigner(temp);
		else if(temp.getName().substring(temp.getName().lastIndexOf('.'), temp.getName().length()).equals(".exe") ||temp.getName().substring(temp.getName().lastIndexOf('.'), temp.getName().length()).equals(".dll")) s = new MSigner(temp);
		labelDrag.setText(s.getFile().getName());
	}
	
	@FXML
	private void newCertOnAction() throws IOException{
		FXMLLoader fxml = new FXMLLoader(getClass().getClassLoader().getResource("gui/makecert.fxml"));
		Stage secondary = new Stage();	
		Scene scene = new Scene((Pane) fxml.load());
		secondary.setScene(scene);
		secondary.setTitle("New Certificate");
		secondary.show();
	}
	
	@FXML
	private void OnDragDetected(){
		System.out.println("DragDETECTED");
	}
	
	@FXML
	private void OnDragDone(){
		System.out.println("DragDone");
	}
	
	
	@FXML
	private void OnDragExited(DragEvent e) throws Exception{
		Dragboard db = e.getDragboard();
		final boolean isAccepted = db.getFiles().get(0).getName().toLowerCase().endsWith(".jar") ||
				db.getFiles().get(0).getName().toLowerCase().endsWith(".dll") ||
				db.getFiles().get(0).getName().toLowerCase().endsWith(".exe");
		if(db.hasFiles()){
			if(isAccepted){
				File temp = db.getFiles().get(0);
				labelDrag.setText(temp.getAbsolutePath());
				init(temp);
				s.setFile(temp);
			}
		} else
			e.consume();
	}
	
	
}
