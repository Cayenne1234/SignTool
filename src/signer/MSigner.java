package signer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import signer.Signer;

import java.io.File;

public class MSigner extends Signer{
	
	private File binary, file; //cert;
	String output, storename, cert;
	
	
	
	public MSigner(File file){
		this.file = file;
		initBinary();
	}
	
	public MSigner() {
		initBinary();
	}

	private void initBinary(){
		if(binary == null){
			File temp = new File("C:/Program Files (x86)/Windows Kits/10/bin/x64/signtool.exe");
			if(temp.exists()){
				binary = temp;
			}
		}
	}
	
	public void sign(){
		//Signtool sign /v /s MY /n MyPrivateCert  FileToSign.exe
		try{
		String command = binary.getPath()+" sign /v /s  "+storename+" /n " +cert/*.getPath()*/+" "+file.getPath();
		Process proc = Runtime.getRuntime().exec(command);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		// read the output from the command
		
		output = "";
		String s = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
			output += s+"\n";
		}
		
		String err = "";
		String s2 = null;
		while((s2 = stdError.readLine()) != null){
			System.out.println(s2);
			err += s2+"\n";
		}
		
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void verify(boolean details){
		//verify da shit: signtool verify /pa (f�r verbose /v) asdf.exe
		
		try {
			Runtime runtime = Runtime.getRuntime();

			Process proc;
			//System.out.println(binary.getPath() + " verify  /pa /v " + file.getPath());
			//System.out.println();
			if(details) proc = runtime.exec(binary.getPath() + " verify  /pa /v " + file.getPath());
			else  proc = runtime.exec(binary.getPath() + " verify /pa " + file.getPath());
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

	public File getBinary() {
		return binary;
	}

	public void setBinary(File binary) {
		this.binary = binary;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getCert() {
		return cert;
	}

	public void setCert(String cert) {
		this.cert = cert;
	}


	

}
