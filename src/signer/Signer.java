package signer;

import java.io.File;

public abstract class Signer {
	private File binary, file;
	String output;
	
	abstract public void verify(boolean details);
	abstract public void sign();
	
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
	
	
}
