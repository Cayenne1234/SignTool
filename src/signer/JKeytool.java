package signer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class JKeytool {
	
	private File binary, keystore;
	private String alias, storename, name, ou, unit, city, county, countrycode, storepw, keypw = "";

	
	public JKeytool(File binary) {
		this.binary = binary;
		initBinary();
	}
	
	public JKeytool() {
		initBinary();
	}
	

	public File getKeystore() {
		return keystore;
	}

	public void setKeystore(File keystore) {
		this.keystore = keystore;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getStorename() {
		return storename;
	}

	public void setStorename(String storename) {
		this.storename = storename;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountrycode() {
		return countrycode;
	}

	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	public String getStorepw() {
		return storepw;
	}

	public void setStorepw(String storepw) {
		this.storepw = storepw;
	}

	public String getKeypw() {
		return keypw;
	}

	public void setKeypw(String keypw) {
		this.keypw = keypw;
	}

	
	
	private void initBinary() {
		if(binary == null){
			File temp = new File(System.getenv("JAVA_HOME")+"\\bin\\keytool.exe");
			if(temp.exists()){
				binary = temp;
			}
		}
	}
	
	public void generateKey() throws Exception{
		
		 String[] params;
		 
		
		 if(!keystore.getName().contains(".jks") ||! keystore.getName().substring(keystore.getName().lastIndexOf('.'),keystore.getName().length()).equals(".jks"))
		   {
			 
			 keystore = new File(keystore.getAbsolutePath()+".jks");
		   }
		 
		 System.out.println(keystore.getName());
		if(keypw.trim().isEmpty()) params = new String[] {name, ou, unit, city, county, countrycode, "j", ""};
		else  params = new String[] {name, ou, unit, city, county, countrycode, "j", keypw, keypw};
		
			Process p;
			try {
				ProcessBuilder pb = new ProcessBuilder(new String[] {"cmd", "/C", "keytool", "-genkey", "-alias", alias, "-keystore", keystore.getPath(), "-storepass", storepw});
				p = pb.start();
				
				Runnable r = new Runnable(){

					@Override
					public void run() {
						int paramCnt = 0;
						BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
						BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
						BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
						while(p.isAlive()){
							try {
								char temp;
								if(br.ready()){
									while((temp = (char) br.read()) != Character.MAX_VALUE){
										System.out.print(temp);
									}
								}
								if(err.ready()){
									int i = 0;
									do{
										 i++;
										if(i >200) break;
										temp = (char) err.read();
										System.out.print(temp);
										
									} while(temp != ':');
									System.out.println(params[paramCnt]);
									System.out.println(paramCnt);
									bw.write(params[paramCnt]+"\n");
									bw.flush();
									paramCnt++;
								}
								if(paramCnt > params.length-1) break;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					
				};
				new Thread(r).start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
	}
		
//	public void importKey(){
//		String[] params;
//		if(keypw.trim().isEmpty()) params = new String[] {name, ou, unit, city, county, countrycode, "j", ""};
//		else  params = new String[] {name, ou, unit, city, county, countrycode, "j", keypw, keypw};
//	
//		Process p;
//		try {
//			ProcessBuilder pb = new ProcessBuilder(new String[] {"cmd", "/C", "keytool", "-genkey", "-alias", alias, "-keystore", keystore.getPath(), "-storepass", storepw});
//			p = pb.start();
//			
//			Runnable r = new Runnable(){
//
//				@Override
//				public void run() {
//					int paramCnt = 0;
//					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
//					BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));
//					while(p.isAlive()){
//						try {
//							char temp;
//							if(br.ready()){
//								while((temp = (char) br.read()) != -1){
//									System.out.println(temp);
//									System.out.println("asdf1");
//								}
//							}
//							if(err.ready()){
//								int i = 0;
//								do{
//									 i++;
//									if(i >200) break;
//									temp = (char) err.read();
//									System.out.print(temp);
//									
//								} while(temp != ':');
//								System.out.println(params[paramCnt]);
//								System.out.println(paramCnt);
//								bw.write(params[paramCnt]+"\n");
//								bw.flush();
//								paramCnt++;
//							}
//							if(paramCnt > params.length-1) break;
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//				
//			};
//			new Thread(r).start();
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//}
	
}
