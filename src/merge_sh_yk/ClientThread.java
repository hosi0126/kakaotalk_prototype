package merge_sh_yk;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
					String str=(String)obj.get("content");//tomcat file path
					System.out.println(str); 
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
		Thread thread=new Thread(){
			@Override
			public void run() {
				DataInputStream din;
				DataOutputStream dout;
				JSONObject obj=new JSONObject();
				obj.put("type", "image");
				try {
					byte[] size = Files.readAllBytes(file.toPath());
					obj.put("content", Integer.toString(size.length));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String myString = obj.toString();
				try {
					din=new DataInputStream(socket.getInputStream());
					dout=new DataOutputStream(socket.getOutputStream());
					buffw.write(myString+"\n");
					buffw.flush();
					
					FileInputStream fin=new FileInputStream(file);
					dout.writeUTF(file.getName());
					byte[] readData=new byte[1024];
					
					int i=0;
					while((i=fin.read(readData))!=-1){
						dout.write(readData, 0, i);
					}
					fin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			listen();
		}
	}
}
