package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Vector;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import server.ThreadManager;

//DB가 가져야할 정보 num/users/내용
public class Server_chat extends Thread{
	Socket socket;
	BufferedReader buffr;
	BufferedWriter buffw;
	Vector<ThreadManager> userThread=new Vector<ThreadManager>();

	DataInputStream dis;
	File file;
	FileOutputStream fos;
	
	public Server_chat(Socket socket,Vector<ThreadManager> userThread) {
		this.socket=socket;
		this.userThread=userThread;
		try {
			buffr=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			buffw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void listen(){
		//클라에서 받는 것
		String msg=null;

		try {
			msg=buffr.readLine();
			//여기서 판단하기

			JSONParser parser=new JSONParser();
			JSONObject obj=(JSONObject)parser.parse(msg);
			String type=(String)obj.get("type");
			if(type.equals("chat")){
				String str=(String)obj.get("content");
				send(str);
			}
			else if(type.equals("image")){
				Thread thread=new Thread(){
					public void run() {
						String size_s=(String)obj.get("content");//file size
						int size=Integer.parseInt(size_s);

						try {
							dis = new DataInputStream (socket.getInputStream());
							String str=dis.readUTF();
							String path="C:/myserver/data/"+str;
							file=new File(path);
							fos=new FileOutputStream(file);
							
							int totalBytesRead = 0;
							byte[] data = new byte[size];

							while (totalBytesRead < size) {
							    int bytesRemaining = size - totalBytesRead;
							    int bytesRead = dis.read(data, totalBytesRead, bytesRemaining);
							    fos.write(data,totalBytesRead, bytesRemaining);
							    fos.flush();
							    if (bytesRead == -1) {
							        return; // socket has been closed
							    }

							    totalBytesRead += bytesRead;
							}
							send(new File(path));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally {
						    try {
						    	if(fos!=null){
						    		fos.close();
						    	}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};
				thread.start();
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
		//서버가 보내주는 것
		try {
			//판단해서 보내주기
			JSONObject obj=new JSONObject();
			obj.put("type", "chat");
			obj.put("content", msg);
			
			String myString = obj.toString();
			
			for(int i=0;i<userThread.size();i++){
				userThread.get(i).chat.buffw.write(myString+"\n");
				userThread.get(i).chat.buffw.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	public void send(File file){
		JSONObject obj=new JSONObject();
		obj.put("type", "image");
		obj.put("content", "http://"+socket.getInetAddress().getHostAddress()+":9090/data"+file.getName());

		String myString = obj.toString();
	    try {
			for(int i=0;i<userThread.size();i++){
				userThread.get(i).chat.buffw.write(myString+"\n");
				userThread.get(i).chat.buffw.flush();
			}
			
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