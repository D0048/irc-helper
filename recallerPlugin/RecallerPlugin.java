package recallerPlugin;

import java.util.HashSet;
import java.util.regex.Pattern;

import enterence.Enterence;
import bot.Configs;
import bot.IRCPlugin;
import bot.Records;

public class RecallerPlugin extends IRCPlugin {
	String name = "RecallerPlugin";

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		if (!Records.mutedUsers.contains(sender)) {
			this.funcRecord(channel, sender, login, hostname, message,
					message.split(" "));
		}
	}

	@Override
	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		if (message.startsWith(Configs.preffix + "recall")) {
			this.funcRegexReCall(channel, sender, login, hostname, message,
					args, false);
			return;
		}
		if (message.startsWith(Configs.preffix + "regrecall")) {
			this.funcRegexReCall(channel, sender, login, hostname, message,
					args, true);
			return;
		}
	}

	@Override
	public void onHelp(String channel, String sender, String login,
			String hostname, String message, String[] args) {
		super.onHelp(channel, sender, login, hostname, message, args);
		Enterence.bot.sendMessage(sender, "---History Recaller Plugin---");
		Enterence.bot
				.sendMessage(
						sender,
						"-recall [user] [statement] [amount] Recall certain phases in the chat history of a user\n");
		Enterence.bot
				.sendMessage(
						sender,
						"-regrecall [user] [keyword] [amount] Recall certain phases using regex in the chat history of a user\n");
	}

	public void funcRegexReCall(String channel, String sender, String login,
			String hostname, String message, String args[], boolean regex) {
		int maxout = 3;
		String targetUsr = args[1];
		String targetContent = "";

		for (int i = 0; i < args.length; i++) {
			if (i == 0)
				continue;
			if (i == 1)
				continue;
			if (i == args.length - 1) {
				try {
					maxout = Integer.parseInt(args[i]);
					break;
				} catch (Exception e) {
					break;
				}
			}
			targetContent += args[i];
		}

		Enterence.bot.sendMessage(channel, ": Searching for " + targetContent
				+ " in " + targetUsr);
		if (Records.records.containsKey(targetUsr)) {// usr
			HashSet<String> record = Records.records.get(targetUsr);
			HashSet<String> matches = new HashSet<String>();

			for (String statement : record) {// statement

				if ((regex && Pattern.matches(targetContent, statement))// regex
						&& statement.contains(channel)
						&& !statement.contains("-regrecall")
						&& !statement.contains("-recall"))
					matches.add(statement);

				if ((!regex && statement.contains(targetContent))// normal
						&& statement.contains(channel)
						&& !statement.contains("-regrecall")
						&& !statement.contains("-recall")) {
					matches.add(statement);
				}
			}

			if (matches.size() == 0) {
				Enterence.bot.sendMessage(channel,
						": Search failed: content not found");
				return;
			} else {
				boolean pm = false;
				if (maxout > 2) {
					pm = true;
					Enterence.bot.sendMessage(channel, sender
							+ ", msg sent through private chat.");
				}
				if (maxout > 15) {
					Enterence.bot.sendMessage(channel, sender
							+ ", Rejected: jamming!");
					return;
				}
				for (String statement : matches) {
					if (!pm)
						Enterence.bot.sendMessage(channel, statement);
					else
						Enterence.bot.sendMessage(sender, statement);
					maxout--;
					if (maxout == 0)
						return;
				}
				return;
			}
		} else {
			Enterence.bot.sendMessage(channel,
					": Search failed: target not found");
			return;
		}
	}

	public void funcRecord(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		if (Records.records.containsKey(sender)) {
			Records.records.get(sender).add(
					"[" + new java.util.Date().toString() + "@" + channel + "]"
							+ sender + ":" + message);
			// Gui.log("pushed:" + "[" + new java.util.Date().toString() + "@"
			// + channel + "]" + sender + ":" + message);
		} else {
			Records.records.put(sender, new HashSet<String>());
		}
	}

	@Override
	public boolean onLoad() {
		return super.onLoad();
	}

	@Override
	public void onUnload() {

	}

	@Override
	public String toString() {
		return super.toString();
	}

}
