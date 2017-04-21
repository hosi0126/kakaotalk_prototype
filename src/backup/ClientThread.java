package backup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.file.Files;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ClientThread extends Thread{
	Socket socket;
	Socket imgsocket;
	Client_chat main;
	
	BufferedReader buffr;
	BufferedWriter buffw;
	
	File file;
	
	
	public ClientThread(Socket socket,Client_chat main) {
		// TODO Auto-generated constructor stub
		this.socket=socket;
		this.main=main;
		
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			//output=socket.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen(){
		String msg=null;

		JSONParser parser=new JSONParser();
		FileOutputStream fos;
		
		try {
			msg=buffr.readLine();

			JSONObject obj=(JSONObject)parser.parse(msg);
			String type=(String)obj.get("type");
			if(type.equals("chat")){
				String str=(String)obj.get("content");
				main.area.append(str+"\n");
			}
			else if(type.equals("join")){//접속시 아이디 부여
				String str=(String)obj.get("content");
			}
			else if(type.equals("image")){
					String str=(String)obj.get("content");//file name
					
					String path="C:/myserver/"+str;
					fos=new FileOutputStream(path);
	
					InputStream input=socket.getInputStream();
	
					int data=-1;
					while(true){
						data=input.read();
						if(data==-1) break;
						fos.write(data);
						fos.flush();
					}
					fos.flush();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(String msg){
		try {
			JSONObject obj=new JSONObject();
			obj.put("type", "chat");
			obj.put("content", msg);
			
			String myString = obj.toString();
			buffw.write(myString+"\n");
			buffw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void send(File file){
		JSONObject obj=new JSONObject();
		obj.put("type", "image");
		obj.put("content", file.getName());
		
		String myString = obj.toString();
	    try {
	    	OutputStream output=socket.getOutputStream();
			buffw.write(myString+"\n");
			buffw.flush();
			Files.copy(file.toPath(), output);
			output.flush();
			System.out.println("다보냇냐");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			listen();
		}
	}
}
