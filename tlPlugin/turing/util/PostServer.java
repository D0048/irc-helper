package tlPlugin.turing.util;

import gui.Gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import bot.Configs;

/**
 * HTTP工具类
 * 
 * @author 图灵机器人
 * 
 */
public class PostServer {

	/**
	 * 向后台发送post请求
	 * 
	 * @param param
	 * @param url
	 * @return
	 */
	public static String SendPost(String param, String url) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = "";
		Proxy proxy = null;
		try {
			if (Configs.useProxy == "1") {
				proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(
						Configs.Proxy.split(":")[0],
						Integer.parseInt(Configs.Proxy.split(":")[1])));
				Gui.log("Using proxy" + proxy.toString());
			}
			URL realUrl = new URL(url);
			HttpURLConnection conn;
			if (Configs.useProxy == "1" && proxy != null) {
				conn = (HttpURLConnection) realUrl.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) realUrl.openConnection();
			}

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "token");
			conn.setRequestProperty("tag", "htc_new");

			conn.connect();

			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(param);

			out.flush();
			out.close();
			//
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line = "";
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
}
