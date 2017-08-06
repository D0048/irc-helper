package tlPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.util.org.apache.commons.io.IOUtils;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreProtocolPNames;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.json.JSONObject;

import enterence.Enterence;
import gui.Gui;

import bot.Configs;
import bot.IRCPlugin;

public class TLPlugin extends IRCPlugin {

	protected String name = "TLPlugin";

	@Override
	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {

		if (message.startsWith(Configs.preffix + "chat")) {
			String myMessage = message.substring(message.indexOf(" "));
			Gui.log("My message: " + myMessage);
			String reply = "no reply";
			try {
				reply = this.toRobot(channel, sender, login, hostname, message,
						args, myMessage);
			} catch (Exception e) {
				reply = e.getMessage();
			}
			Enterence.bot.sendMessage(channel, sender + ": " + reply);
		}
	}

	@Override
	public boolean onLoad() {
		return super.onLoad();
	}

	@Override
	public void onUnload() {

	}

	private String toRobot(String channel, String sender, String login,
			String hostname, String message, String args[], String statement)
			throws Exception {
		String postUrl = "http://www.tuling123.com/openapi/api";
		StringEntity postingString = new StringEntity(new String(new Gson()
				.toJson(new Request(statement, sender)).getBytes(), "UTF-8"));

		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost post = new HttpPost(postUrl);
		post.setEntity(postingString);
		post.setHeader("Content-type", "application/json");
		post.addHeader("Content-Type", "charset=UTF-8");

		HttpResponse res = httpClient.execute(post);
		JSONObject jsonObject = new JSONObject();
		if (res != null) {
			jsonObject = new JSONObject(IOUtils.toString(res.getEntity()
					.getContent(), "UTF-8"));
			String text = jsonObject.get("text").toString();
			if (text != null) { // success
				System.out.println(jsonObject.getString("text"));
			} else {
				throw new Exception("Error #" + jsonObject.get("code") + " "
						+ jsonObject.get("error_message"));
			}
		}
		return jsonObject.toString();
		// return in.readLine().split("\"")[5];
		/*
		 * final URL url = new URL("http://www.tuling123.com/openapi/api");
		 * final URLConnection urlConnection = url.openConnection();
		 * urlConnection.setDoOutput(true);
		 * urlConnection.setRequestProperty("Content-Type",
		 * "application/json; charset=utf-8"); urlConnection.connect(); final
		 * OutputStream outputStream = urlConnection.getOutputStream();
		 * outputStream.write(("{\"key\": \"" +
		 * "3aab1c245bb8401fa8a3b3bf1689bc35" + "\"}").getBytes("UTF-8"));
		 * outputStream.write(("{\"info\": \"" + statement + "\"}")
		 * .getBytes("UTF-8")); outputStream.write(("{\"userid\": \"" + sender +
		 * "\"}") .getBytes("UTF-8")); outputStream.flush(); BufferedReader in =
		 * new BufferedReader(new InputStreamReader(
		 * urlConnection.getInputStream())); return
		 * in.readLine().split("\"")[5];
		 */
		/*
		 * String urlString = "http://www.tuling123.com/openapi/api"; HttpClient
		 * httpClient = HttpClientBuilder.create().build(); HttpPost postRequest
		 * = new HttpPost(urlString);
		 * 
		 * // build request parameters StringBody apiKey = new
		 * StringBody("3aab1c245bb8401fa8a3b3bf1689bc35",
		 * ContentType.MULTIPART_FORM_DATA); StringBody info = new
		 * StringBody(statement, ContentType.MULTIPART_FORM_DATA); StringBody
		 * userid = new StringBody(sender, ContentType.MULTIPART_FORM_DATA);
		 * 
		 * MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		 * builder.addPart("key", apiKey); builder.addPart("info", info); //
		 * builder.addPart("userid", userid);
		 * 
		 * postRequest.setEntity(builder.build()); HttpResponse res = null;
		 * 
		 * // execute the request res = httpClient.execute(postRequest);
		 * 
		 * JSONObject jsonObject = new JSONObject(); if (res != null) {
		 * jsonObject = new JSONObject(IOUtils.toString(res.getEntity()
		 * .getContent(), "UTF-8")); String text =
		 * jsonObject.get("text").toString(); if (text != null) { // success
		 * System.out.println(jsonObject.getString("text")); } else { throw new
		 * Exception("Error #" + jsonObject.get("code") + " " +
		 * jsonObject.get("error_message")); } } return jsonObject.toString();
		 */
	}

	@Override
	public void onHelp(String channel, String sender, String login,
			String hostname, String message, String[] args) {
		// TODO Auto-generated method stub
		super.onHelp(channel, sender, login, hostname, message, args);
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public String getName() {
		return super.getName();
	}
}

class Request {
	String key = "3aab1c245bb8401fa8a3b3bf1689bc35";
	String info;
	String userid;

	public Request(String info, String userid) {
		this.info = info;
		this.userid = userid;
	}
}