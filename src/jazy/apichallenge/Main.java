package jazy.apichallenge;

import java.net.*;
import java.io.*;
import org.json.*;

public class Main {

	public static void main(String[] args) {
		try
		{
			BufferedReader br;
			JSONObject send;
			JSONObject reci;
			String token;
			
			//Prestage (Send info and get token)
			send = new JSONObject();
			send.put("email","jazy555@hotmail.com");
			send.put("github","https://github.com/Jazy5552");
			
			br = postStringToSite(send.toString(), "http://challenge.code2040.org/api/register");
			reci = new JSONObject(br.readLine());
			br.close();
			
			token = reci.getString("result");
			System.out.println("Token: " + token);
			
			//Stage 1 (Reverse string)
			send = new JSONObject();
			send.put("token", reci.getString("result"));
			
			br = postStringToSite(send.toString(), "http://challenge.code2040.org/api/getstring");
			reci = new JSONObject(br.readLine());
			br.close();
			
			String tmp = reci.getString("result");
			tmp = new StringBuilder(tmp).reverse().toString();
			
			send = new JSONObject();
			send.put("token", token);
			send.put("string", tmp);
			
			System.out.println(reci.toString());
			System.out.println(send.toString());
			
			br = postStringToSite(send.toString(), "http://challenge.code2040.org/api/validatestring");

			System.out.println(br.readLine());
			br.close();
			//END OF STAGE 1
			
		}
		catch(Exception e)
		{
			System.out.println("Error, program halted!");
			System.out.println(e.toString());
		}
		
	}
	
	public static BufferedReader postStringToSite(String msg, String site) throws Exception
	{
		URLConnection con = new URL(site).openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		
		con.connect();
		
		OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
		osw.write(msg);
		osw.flush();
		osw.close();
		
		return new BufferedReader(new InputStreamReader(con.getInputStream()));
	}

}
