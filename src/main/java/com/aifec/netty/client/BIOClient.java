package com.aifec.netty.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class BIOClient {
	
	private static final int BIO_CLIENT_PORT = 8888;
	
	private static final String BIO_CLIENT_IP = "127.0.0.1";
	
	
	public static String start(){
		System.out.println("请输入名称:");
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String name = br.readLine();
			if(name.equals("")){
				return null;
			}
			
			Socket client = new Socket(BIO_CLIENT_IP,BIO_CLIENT_PORT);
			//发送线程
			new Thread(new Send(client,name)).start();
			
			//接收服务器的响应的线程
			new Thread(new Receive(client)).start();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}
	public static void main(String[] args) {
		start();
	}

}
