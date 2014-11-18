package jazy.apichallenge;

import java.net.*;
import java.io.*;
import org.json.*;

public class Main {

	public static void main(String[] args) {
		OutputStreamWriter osw;
		BufferedReader br;
		URL site;
		URLConnection con;
		JSONObject send;
		JSONObject reci;
		String token;
		try
		{
			send = new JSONObject();
			send.put("email","jazy555@hotmail.com");
			send.put("github","https://github.com/Jazy5552");
			site = new URL("http://challenge.code2040.org/api/register");
			con = site.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			
			con.connect();
			
			osw = new OutputStreamWriter(con.getOutputStream());
			osw.write(send.toString());
			osw.flush();
			osw.close();
			
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			reci = new JSONObject(br.readLine());
			br.close();
			
			token = reci.getString("result");
			
			System.out.println("Token: " + token);
			
			//Stage 1
			send = new JSONObject();
			send.put("token", reci.getString("result"));
			
			site = new URL("http://challenge.code2040.org/api/getstring");
			con = site.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			
			con.connect();
			
			osw = new OutputStreamWriter(con.getOutputStream());
			osw.write(send.toString());
			osw.flush();
			osw.close();
			
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			reci = new JSONObject(br.readLine());
			br.close();
			
			System.out.println(reci.toString());
			
			String tmp = reci.getString("result");
			tmp = new StringBuilder(tmp).reverse().toString();
			
			send = new JSONObject();
			send.put("token", token);
			send.put("string", tmp);
			
			System.out.println(send.toString());
			
			site = new URL("http://challenge.code2040.org/api/validatestring");
			con = site.openConnection();
			con.setDoInput(true);
			con.setDoOutput(true);
			
			con.connect();
			
			osw = new OutputStreamWriter(con.getOutputStream());
			osw.write(send.toString());
			osw.flush();
			osw.close();
			
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			System.out.println(br.readLine());
			br.close();
			
		}
		catch(Exception e)
		{
			
			System.out.println(e.toString());
		}
		
	}

}
