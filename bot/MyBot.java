package bot;

import enterence.Enterence;
import function.HexTrans;
import gui.Gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
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
						this.sendMessage(channel, sender + ": Internal error.");
						this.funcHelp(channel, sender, login, hostname,
								message, message.split(" "));
					}
				}
			}

			this.funcRecord(channel, sender, login, hostname, message,
					message.split(" "));
		}
	}

	@Override
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		super.onPrivateMessage(sender, login, hostname, message);
		String[] args = message.split(" ");
		for (String name : Enterence.sudoers) {
			if (sender == name & args[0] == preffix
					& args[1] == Enterence.sudoPwd) {
				this.onSudoCall(sender, login, hostname, message, args);
			}
		}
	}

	public void onSudoCall(String sender, String login, String hostname,
			String message, String args[]) {
		if (message.startsWith(preffix + "mute")) {
			this.funcMute(sender, login, hostname, message, args, true);
			return;
		}
		if (message.startsWith(preffix + "'mute")) {
			this.funcMute(sender, login, hostname, message, args, false);
			return;
		}
	}

	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		if (message.startsWith(preffix + "help")) {
			this.funcHelp(channel, sender, login, hostname, message, args);
			return;
		}

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

		if (message.startsWith(preffix + "md5")) {
			this.sendMessage(channel,
					sender + ": " + function.MD5Cal.toMD5(args[1]));
			return;
		}
		if (message.startsWith(preffix + "hex")) {
			this.sendMessage(channel,
					sender + ": " + HexTrans.bytesToHex(args[1].getBytes()));
			return;
		}
		if (message.startsWith(preffix + "'hex")) {
			this.sendMessage(channel,
					sender + ": " + HexTrans.hexStr2Str((args[1])));
			return;
		}
		if (message.startsWith(preffix + "base32")) {
			this.sendMessage(
					channel,
					sender + ": "
							+ new Base32().encodeToString(args[1].getBytes()));
			return;
		}
		if (message.startsWith(preffix + "'base32")) {
			this.sendMessage(channel,
					sender + ": " + new String(new Base32().decode(args[1])));
			return;
		}
		if (message.startsWith(preffix + "base64")) {
			this.sendMessage(
					channel,
					sender + ": "
							+ new Base64().encodeToString(args[1].getBytes()));
			return;
		}
		if (message.startsWith(preffix + "'base64")) {
			this.sendMessage(channel,
					sender + ": " + new String(new Base64().decode(args[1])));
			return;
		}
		try {
			if (message.startsWith(preffix + "urlcodec")) {
				this.sendMessage(channel,
						sender + ": " + new URLCodec().encode(args[1]));
				return;
			}
			if (message.startsWith(preffix + "'urlcodec")) {

				this.sendMessage(channel, sender + ": "
						+ new String(new URLCodec().decode(args[1])));

				return;
			}
		} catch (Exception e) {
			Gui.displayException(e);
			this.sendMessage(channel, sender + ": err: " + e.getMessage());
			return;
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
				if ((regex && Pattern.matches(targetContent, statement)))
					matches.add(statement);

				if ((!regex && statement.contains(targetContent))) {
					matches.add(statement);
				}
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

	public void funcMute(String sender, String login, String hostname,
			String message, String args[], boolean action) {
		int i = 0;
		for (String usr : args) {
			if (i == 0) {
				i = 1;
				continue;
			}
			if (action) {
				this.mutedUsers.add(usr);
				this.sendMessage(sender, "User:" + usr + " muted");
			} else {
				this.mutedUsers.remove(usr);
				this.sendMessage(sender, "User:" + usr + " unmuted");
			}
		}
		return;
	}

	public void funcHelp(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		this.sendMessage(channel, "Help message sent through private chat.");
		this.sendMessage(sender, "Normal commands:\n");
		this.sendMessage(sender, "-help 							Show this help");
		this.sendMessage(
				sender,
				"-recall [user] [statement] 	Recall certain phases in the chat history of a user\n");
		this.sendMessage(
				sender,
				"-regrecall [user] [statement] 	Recall certain phases using regex in the chat history of a user\n");
		this.sendMessage(sender, "-time 							Get current time");
		this.sendMessage(sender, "-md5 [msg] 					Encrypt a message with md5\n");
		this.sendMessage(sender,
				"-base32 [msg] 					Encrypt a message with base32\n");
		this.sendMessage(sender,
				"-'base32 [msg] 				Decrypt a message with base32\n");
		this.sendMessage(sender,
				"-base64 [msg] 					Encrypt a message with base64\n");
		this.sendMessage(sender,
				"-'base64 [msg] 				Decrypt a message with base64\n");
		this.sendMessage(sender, "-hex [msg] 					String to hex\n");
		this.sendMessage(sender, "-'hex [msg] 					Hex to string");
		this.sendMessage(sender,
				"-urlcodec [msg] 				Encrypt a message with URLCodec\n");
		this.sendMessage(sender,
				"-urlcodec [msg] 				Decrypt a message with URLCodec\n");
		this.sendMessage(sender, "Sudo commands:\n");
		this.sendMessage(sender, "[hidden]");
	}

	public boolean isCred(String name) {
		return true;
	}
}
