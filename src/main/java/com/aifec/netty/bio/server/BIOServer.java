package com.aifec.netty.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class BIOServer {
	
	private static final int BIO_SERVER_PORT = 8888;
	
	private static List<MyChannel> all = new ArrayList<MyChannel>();
	
	public static String start(){
		try {
			ServerSocket server =new ServerSocket(BIO_SERVER_PORT);
			while(true){
				Socket client = server.accept();
				MyChannel channel = new MyChannel(client,all);
				all.add(channel);//统一管理
				new Thread(channel).start(); //一条道路
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	public static void main(String[] args) {
		start();
	}

}
