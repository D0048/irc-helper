package bot;

import function.HexTrans;
import gui.Gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.jibble.pircbot.*;

public class MyBot extends PircBot {
	String preffix = "-";
	HashSet<String> verifiedUsers = new HashSet<String>();
	HashSet<String> mutedUsers = new HashSet<String>();
	HashMap<String, HashSet<String>> records = new HashMap<String, HashSet<String>>();

	public MyBot(String name, String preffix) {
		this.setName(name);
		if (preffix != null) {
			this.preffix = preffix;
		}
	}

	@Override
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		if (!this.mutedUsers.contains(sender)) {
			if (message.startsWith(preffix)) {
				if (sender.contains("bot") && !isCred(sender)) {
					this.sendMessage(channel, "Unauthorized access: \"bot\"");
					return;
				} else {
					try {
						this.onCall(channel, sender, login, hostname, message,
								message.split(" "));
					} catch (Exception e) {
						Gui.displayException(e);
						this.sendMessage(
								channel,
								sender
										+ ": Internal error. Usage: {-regrecall [usr] [regex]}or{-recall [usr] [statement]} to trace back in history");
					}
				}
			}

			this.funcRecord(channel, sender, login, hostname, message,
					message.split(" "));
		}
	}

	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {

		if (message.startsWith(preffix + "recall")) {
			this.funcRegexReCall(channel, sender, login, hostname, message,
					args, false);
			return;
		}
		if (message.startsWith(preffix + "regrecall")) {
			this.funcRegexReCall(channel, sender, login, hostname, message,
					args, true);
			return;
		}
		if (message.startsWith(preffix + "time")) {
			this.funcTime(channel, sender, login, hostname, message, args);
			return;
		}
		if (message.startsWith(preffix + "mute")) {
			this.funcMute(channel, sender, login, hostname, message, args);
			return;
		}
		if (message.startsWith(preffix + "md5")) {
			this.sendMessage(channel,
					sender + ": " + function.MD5Cal.toMD5(args[1]));
			return;
		}
		if (message.startsWith(preffix + "hex")) {
			this.sendMessage(channel, sender + ": " + HexTrans.bytesToHex(args[1].getBytes()));
		}
		if (message.startsWith(preffix + "'hex")) {
			this.sendMessage(channel,
					sender + ": " + HexTrans.hexStr2Str((args[1])));
		}
	}

	public void funcRecord(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		if (records.containsKey(sender)) {
			records.get(sender).add(
					"[" + new java.util.Date().toString() + "@" + channel + "]"
							+ sender + ":" + message);
			Gui.log("pushed:" + "[" + new java.util.Date().toString() + "@"
					+ channel + "]" + sender + ":" + message);
		} else {
			records.put(sender, new HashSet<String>());
		}
	}

	public void funcRegexReCall(String channel, String sender, String login,
			String hostname, String message, String args[], boolean regex) {
		String targetUsr = args[1];
		String targetContent = args[2];
		this.sendMessage(channel, ": Searching for " + targetContent + " in "
				+ targetUsr);
		if (records.containsKey(targetUsr)) {// usr
			HashSet<String> record = records.get(targetUsr);
			HashSet<String> matches = new HashSet<String>();

			for (String statement : record) {// statement
				if ((regex && Pattern.matches(targetContent, statement))
						| (!regex && statement.contains(targetContent)))
					matches.add(statement);
			}
			if (matches.size() == 0) {
				this.sendMessage(channel, ": Search failed: content not found");
				return;
			} else {
				int maxout = 5;
				for (String statement : matches) {
					this.sendMessage(channel, statement);
					maxout--;
					if (maxout == 0)
						return;
				}
				return;
			}
		} else {
			this.sendMessage(channel, ": Search failed: target not found");
			return;
		}
	}

	public void funcTime(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		String time = new java.util.Date().toString();
		this.sendMessage(channel, sender + ": The time is now " + time);
		return;
	}

	public void funcMute(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		int i = 0;
		for (String usr : args) {
			if (i == 0) {
				i = 1;
				continue;
			}
			this.mutedUsers.add(usr);
			this.sendMessage(channel, "User:" + usr + " muted");
		}
		return;
	}

	public void funcHelp(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		// TODO:send help message
	}

	@Override
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		super.onPrivateMessage(sender, login, hostname, message);
	}

	public boolean isCred(String name) {
		return true;
	}
}
