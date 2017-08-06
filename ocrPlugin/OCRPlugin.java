package ocrPlugin;

import enterence.Enterence;
import gui.Gui;
import net.minecraft.util.org.apache.commons.io.IOUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.User;
import org.json.JSONObject;

import bot.Configs;
import bot.IRCPlugin;

public class OCRPlugin extends IRCPlugin {
	String name = "OCRPlugin";

	@Override
	public String getName() {

		return super.getName();
	}

	@Override
	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		if (message.startsWith(Configs.preffix + "ocr")) {
			if (args[1] != null) {
				String reply = "null";
				try {
					reply = this.link2Str(channel, sender, login, hostname,
							message, args, args[1]).split("\"")[3];
				} catch (Exception e) {
					reply = e.getMessage();
				}
				Enterence.bot.sendMessage(channel, sender + ": " + reply);
			}
		}
	}

	@Override
	public boolean onLoad() {
		return super.onLoad();
	}

	@Override
	public void onUnload() {

	}

	private String link2Str(String channel, String sender, String login,
			String hostname, String message, String args[], String link)
			throws Exception {
		/*
		 * load image from url This snippet is using Apache HttpComponent
		 * libraries ver 4.4. You can down load it here:
		 * http://mvnrepository.com
		 * /artifact/org.apache.httpcomponents/httpclient
		 * http://mvnrepository.com/artifact/org.apache.httpcomponents/httpmime
		 * JSON library at json.org
		 */

		String urlString = "http://www.bitocr.com/api";
		String imageURL = link;
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost postRequest = new HttpPost(urlString);

		// build request parameters
		StringBody apiKey = new StringBody("d0ef5178fc567db2",
				ContentType.MULTIPART_FORM_DATA);
		StringBody lang = new StringBody("en", ContentType.MULTIPART_FORM_DATA);
		StringBody url = new StringBody(imageURL,
				ContentType.MULTIPART_FORM_DATA);

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addPart("apikey", apiKey);
		builder.addPart("lang", lang);
		builder.addPart("url", url);

		postRequest.setEntity(builder.build());
		HttpResponse res = null;

		// execute the request
		res = httpClient.execute(postRequest);

		JSONObject jsonObject = new JSONObject();
		if (res != null) {

			jsonObject = new JSONObject(IOUtils.toString(res.getEntity()
					.getContent(), "UTF-8"));
			String error = jsonObject.get("error").toString();
			if (error.equals("0")) {
				// success
				System.out.println(jsonObject.getString("result"));
			} else {
				throw new Exception("Error #" + jsonObject.get("error_code")
						+ " " + jsonObject.get("error_message"));
			}
		}
		return jsonObject.toString();
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
