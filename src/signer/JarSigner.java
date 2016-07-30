package signer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import signer.Signer;


public class JarSigner extends Signer{

	private File binary, file, keystore;
	private String output, error;
	
	

	public JarSigner(File binary, File jar) {
		this.binary = binary;
		this.file = jar;
		initBinary();
	}
	
	public JarSigner(File jar) {
		this.file = jar;
		initBinary();
	}

	public JarSigner() {
		initBinary();
	}

	private void initBinary() {
		if(binary == null){
			File temp = new File(System.getenv("JAVA_HOME")+"\\bin\\jarsigner.exe");
			if(temp.exists()){
				binary = temp;
			}
		}
	}

	public void sign(String alias, String keystore, String keystorepw, String keypw, String signedjar){
		try {
			//jarsigner DATEI -keystore KEYSTOREPATH alias -storepass PW -keypass PW -signedjar NAME			
			
			String command = "jarsigner" + " -keystore " + keystore +  " -storepass "+ keystorepw;
			
			if(!keypw.trim().isEmpty()) command +=" -keypass "+keypw;
			if(!signedjar.trim().isEmpty()){

				if(signedjar.contains(".jar") && signedjar.substring(signedjar.lastIndexOf('.'),signedjar.length()).equals(".jar"))
				   {
				   command +=" -signedjar "+file.getParentFile()+ "\\" + signedjar;
				   }
				
				else command += " -signedjar "+file.getParentFile()+ "\\" + signedjar +".jar";
			}
			command+= " "+file.getPath() +" " + alias;
			Process proc = Runtime.getRuntime().exec(command);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			
		
			
			// read the output from the command
			
			output = "";
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				System.out.println(s);
				output += s+"\n";
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void verify(boolean details){
		
		try {
			Runtime runtime = Runtime.getRuntime();
			//jarsigner -verify asdf.jar 
			Process proc;
			if(details) proc = runtime.exec(binary.getPath() + " -verify -verbose:summary -certs" + " " + file.getPath());
			else  proc = runtime.exec(binary.getPath() + " -verify" + " " + file.getPath());
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			
			// read the output from the command
			
			output = "";
			String s = null;
			while ((s = stdInput.readLine()) != null) {
				output += s+"\n";
			}
			

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFile(File jarfile) {
		this.file = jarfile;
		
	}

	public File getFile() {
		return file;
	}

	public String getOutput() {
		return output;
	}

	public String getError() {
		return error;
	}
	
	public File getKeystore() {
		return keystore;
	}

	public void setKeystore(File keystore) {
		this.keystore = keystore;
	}

	@Override
	public void sign() {
		// TODO Auto-generated method stub
		
	}

	
	
 }
