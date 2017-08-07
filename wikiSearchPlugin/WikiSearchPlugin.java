package wikiSearchPlugin;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import tlPlugin.turing.util.PostServer;

import enterence.Enterence;
import gui.Gui;

import bot.Configs;
import bot.IRCPlugin;

public class WikiSearchPlugin extends IRCPlugin {

	String name = "WikiSearchPlugin";

	@Override
	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {

		if (message.startsWith(Configs.preffix + "wiki") && args[1] != null) {
			String search = args[1];
			Gui.log("Searching: " + search);
			String reply = "no reply";
			boolean success = false;
			try {
				reply = this.search(channel, sender, login, hostname, message,
						args, search);
				success = true;
			} catch (Exception e) {
				reply = e.getMessage();
			}
			String[] replies = reply.split("\"");
			String match = replies[3] != null ? replies[3] : "null";
			String description = replies[5] != null ? replies[5] : "null";
			String url = replies[7] != null ? replies[7] : "null";
			if (success) {
				Enterence.bot
						.sendMessage(channel, sender + ": " + match + "->");
				Enterence.bot.sendMessage(channel, sender + ": " + description);
				Enterence.bot.sendMessage(channel, sender + ": " + "-->" + url
						+ "<--");
			} else {
				Enterence.bot.sendMessage(channel, sender + ": "
						+ "Search failed");
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

	private String search(String channel, String sender, String login,
			String hostname, String message, String args[], String statement)
			throws Exception {

		Gui.log(new String(new Gson().toJson(new Request(statement, sender))
				.getBytes(), "UTF-8"));

		return PostServer
				.SendPost(
						new String(""),
						new String(
								("https://en.wikipedia.org/w/api.php?action=opensearch&search="
										+ statement + "&limit=1&namespace=0&format=json")
										.getBytes(), "UTF-8"));
	}

	@Override
	public void onHelp(String channel, String sender, String login,
			String hostname, String message, String[] args) {
		super.onHelp(channel, sender, login, hostname, message, args);
		Enterence.bot.sendMessage(sender, "---TL Plugin---");
		Enterence.bot
				.sendMessage(sender,
						"-wiki [anything] 					wiki anything you want and get the first result");
	}

	@Override
	public String toString() {
		return this.getName();
	}

	@Override
	public String getName() {
		return name;
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