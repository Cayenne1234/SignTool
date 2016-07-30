package signer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class MMakeCert {
	private File binary;
	String output;
	//makecert.exe -r -pe -ss MY -sky exchange -n CN=MyPrivateCert CodeSign.cer
	private String storename, certname, certfilename;
	
	
	public MMakeCert(){
		initBinary();
	}
	
	private void initBinary(){
		if(binary == null){
			File temp = new File("C:/Program Files (x86)/Windows Kits/10/bin/x64/makecert.exe");
			if(temp.exists()){
				binary = temp;
			}
		}
	}
	
	public void makecert() throws IOException{
		String command = binary.getPath() + " -r -pe -ss "+storename+" -sky signature -n CN="+certname+" "+certfilename+".cer";
		Process proc = Runtime.getRuntime().exec(command);
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		
		
		
		// read the output from the command
		
		output = "";
		String s = null;
		while ((s = stdInput.readLine()) != null) {
			System.out.println(s);
			output += s+"\n";
		}
	}
	
	public String getStorename() {
		return storename;
	}



	public void setStorename(String storename) {
		this.storename = storename;
	}



	public String getCertname() {
		return certname;
	}



	public void setCertname(String certname) {
		this.certname = certname;
	}



	public String getCertfilename() {
		return certfilename;
	}



	public void setCertfilename(String certfilename) {
		this.certfilename = certfilename;
	}


}
