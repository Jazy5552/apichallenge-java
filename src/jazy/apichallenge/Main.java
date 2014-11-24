package jazy.apichallenge;

import java.net.*;
import java.io.*;

import org.joda.time.*;
import org.json.*;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br;
		JSONObject send;
		JSONObject reci;
		String token;

		// STAGE 0 (Send info and get token)
		
		send = new JSONObject();
		send.put("email", "jazy555@gmail.com");
		send.put("github", "https://github.com/Jazy5552");

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/register");
		reci = new JSONObject(br.readLine());
		br.close();

		token = reci.getString("result");
		System.out.println("Token: " + token);

		// STAGE 1 (Reverse string)
		
		send = new JSONObject();
		send.put("token", reci.getString("result"));

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/getstring");
		reci = new JSONObject(br.readLine());
		br.close();

		String tmp = reci.getString("result");
		tmp = new StringBuilder(tmp).reverse().toString();

		send = new JSONObject();
		send.put("token", token);
		send.put("string", tmp);

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/validatestring");

		System.out.println(reci.toString());
		System.out.println(send.toString());
		System.out.println(br.readLine());
		br.close(); 
		
		// END OF STAGE 1

		// STAGE 2

		send = new JSONObject();
		send.put("token", token);
		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/haystack");

		reci = new JSONObject(br.readLine());
		br.close();

		JSONObject haystack = reci.getJSONObject("result");
		JSONArray hay = haystack.getJSONArray("haystack");
		String n = haystack.getString("needle");

		int needle;
		for (needle = 0; needle < hay.length(); needle++) {
			if (hay.getString(needle).equals(n)) {
				break; // needle stores the position
			}
		}

		send = new JSONObject();
		send.put("token", token);
		send.put("needle", needle);

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/validateneedle");

		System.out.println(reci.toString());
		System.out.println(send.toString());
		System.out.println(br.readLine());
		br.close();

		// END OF STAGE 2

		// STAGE 3

		send = new JSONObject();
		send.put("token", token);

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/prefix");

		reci = new JSONObject(br.readLine());
		br.close();
		System.out.println(reci.toString());

		JSONObject ray = reci.getJSONObject("result");

		JSONArray array = ray.getJSONArray("array");
		JSONArray newarray = new JSONArray();
		String prefix = ray.getString("prefix");

		for (int i = 0; i < array.length(); i++) {
			if (!array.getString(i).startsWith(prefix)) {
				newarray.put(array.getString(i));
			}
		}

		send = new JSONObject();
		send.put("token", token);
		send.put("array", newarray);

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/validateprefix");
		System.out.println(send.toString());
		System.out.println(br.readLine());
		br.close();

		// END OF STAGE 3

		// STAGE 4
		
		send = new JSONObject();
		send.put("token", token);

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/time");
		reci = new JSONObject(br.readLine());
		br.close();
		System.out.println(reci.toString());

		JSONObject dateinterval = reci.getJSONObject("result");

		DateTime dt = DateTime.parse(dateinterval.getString("datestamp"));
		dt = dt.plusSeconds(dateinterval.getInt("interval"));

		send = new JSONObject();
		send.put("token", token);
		send.put("datestamp", dt.toString());

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/validatetime");
		System.out.println(send.toString());
		System.out.println(br.readLine());
		br.close();
		
		// END OF STAGE 4

		// CHECK STATUS
		
		send = new JSONObject();
		send.put("token", token);

		br = postStringToSite(send.toString(),
				"http://challenge.code2040.org/api/status");
		System.out.println(br.readLine());
		
		// FIN

	}

	public static BufferedReader postStringToSite(String msg, String site)
			throws Exception {
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
