package ddkPlugin;

import tlPlugin.turing.util.PostServer;

import enterence.Enterence;
import gui.Gui;
import bot.Configs;
import bot.IRCPlugin;

public class DDKPlugin extends IRCPlugin {
	String name = "DDKPlugin";
	boolean enable = false;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		if (message.startsWith(Configs.preffix + "ddk") && enable) {
			String[] messages = message.split(" ");
			String search = "", reply = "no reply";
			messages[0] = "";
			for (String s : messages) {
				search += s + " ";
			}
			Gui.log("DDK Searching: " + search);

			try {
				reply = this.search(channel, sender, login, hostname, message,
						args, search);
			} catch (Exception e) {
				reply = e.getMessage();
			}

			Enterence.bot.sendMessage(channel, sender + ": " + reply);
			return;
		}
	}

	private String search(String channel, String sender, String login,
			String hostname, String message, String args[], String statement)
			throws Exception {
		Gui.log("Getting "
				+ "http://api.duckduckgo.com/?pretty=1&format=json&q="
				+ statement);
		return PostServer.SendPost("",
				"http://api.duckduckgo.com/?format=json&q=" + statement);
	}

	@Override
	public void onHelp(String channel, String sender, String login,
			String hostname, String message, String[] args) {
		super.onHelp(channel, sender, login, hostname, message, args);
		Enterence.bot.sendMessage(sender, "---DuckDuckGo Search Plugin---");
		Enterence.bot
				.sendMessage(sender,
						"-ddk [keywords] 		search the specified keyword through duckduckgo");
	}

	@Override
	public boolean onLoad() {
		this.enable = true;
		return super.onLoad();
	}

	@Override
	public void onUnload() {
		this.enable = false;
	}

	@Override
	public String toString() {
		return super.toString();
	}

}
