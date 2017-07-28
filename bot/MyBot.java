package bot;

import enterence.Enterence;
import function.HexTrans;
import gui.Gui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.net.URLCodec;
import org.jibble.pircbot.*;

public class MyBot extends PircBot {

	public MyBot(String name, String preffix) {
		this.setName(name);
		if (preffix != null) {
			Configs.preffix = preffix;
		}
	}

	@Override
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		if (!Records.mutedUsers.contains(sender)) {
			// call handle
			if (message.startsWith(Configs.preffix)) {
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
			// apm handle
			this.priFuncAnonymousHandle(channel, sender, login, hostname,
					message, message.split(" "));
			this.funcRecord(channel, sender, login, hostname, message,
					message.split(" "));
		}
	}

	@Override
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		super.onPrivateMessage(sender, login, hostname, message);
		String[] args = message.split(" ");

		// sudo calls
		try {
			for (String name : Configs.sudoers) {
				if (sender.equals(name) & args[0].equals(Configs.preffix)
						& args[1] != null & args[1].equals(Configs.sudoPwd)) {
					this.onSudoCall(sender, login, hostname, message, args);
					Gui.log(sender + " has issued an sudo call");
				} else {
					Gui.log(sender + " failed to authorize with: " + args[1]);
				}
			}
		} catch (Exception e) {
			Gui.log(sender + " generated an invalid private call");
		}

		// private funcs
		try {
			if (args[0].startsWith(Configs.preffix)) {
				this.onPrivateCall(sender, login, hostname, message, args);
				Gui.log(sender + " generated an successful private call");
			}
		} catch (Exception e) {
			Gui.log(sender + " generated an invalid private call");
		}
	}

	public void onSudoCall(String sender, String login, String hostname,
			String message, String args[]) {
		if (message.startsWith(Configs.preffix + " " + Configs.sudoPwd + " "
				+ "mute")) {
			this.funcMute(sender, login, hostname, message, args, true);
			return;
		}
		if (message.startsWith(Configs.preffix + " " + Configs.sudoPwd + " "
				+ "'mute")) {
			this.funcMute(sender, login, hostname, message, args, false);
			return;
		}
	}

	public void onPrivateCall(String sender, String login, String hostname,
			String message, String args[]) {
		if (message.startsWith(Configs.preffix + "apm")) {
			this.priFuncAnonymousCall(sender, login, hostname, message, args,
					true);
		}
		if (message.startsWith(Configs.preffix + "am")) {
			this.priFuncAnonymousCall(sender, login, hostname, message, args,
					false);
		}
	}

	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		if (message.startsWith(Configs.preffix + "help")) {
			this.funcHelp(channel, sender, login, hostname, message, args);
			return;
		}
		
		if (message.startsWith(Configs.preffix + "ping")) {
			this.sendMessage(channel, "-pong");
			return;
		}

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
		if (message.startsWith(Configs.preffix + "time")) {
			this.funcTime(channel, sender, login, hostname, message, args);
			return;
		}

		if (message.startsWith(Configs.preffix + "md5")) {
			this.sendMessage(channel,
					sender + ": " + function.MD5Cal.toMD5(args[1]));
			return;
		}
		if (message.startsWith(Configs.preffix + "hex")) {
			this.sendMessage(channel,
					sender + ": " + HexTrans.bytesToHex(args[1].getBytes()));
			return;
		}
		if (message.startsWith(Configs.preffix + "'hex")) {
			this.sendMessage(channel,
					sender + ": " + HexTrans.hexStr2Str((args[1])));
			return;
		}
		if (message.startsWith(Configs.preffix + "base32")) {
			this.sendMessage(
					channel,
					sender + ": "
							+ new Base32().encodeToString(args[1].getBytes()));
			return;
		}
		if (message.startsWith(Configs.preffix + "'base32")) {
			this.sendMessage(channel,
					sender + ": " + new String(new Base32().decode(args[1])));
			return;
		}
		if (message.startsWith(Configs.preffix + "base64")) {
			this.sendMessage(
					channel,
					sender + ": "
							+ new Base64().encodeToString(args[1].getBytes()));
			return;
		}
		if (message.startsWith(Configs.preffix + "'base64")) {
			this.sendMessage(channel,
					sender + ": " + new String(new Base64().decode(args[1])));
			return;
		}
		try {
			if (message.startsWith(Configs.preffix + "urlcodec")) {
				this.sendMessage(channel,
						sender + ": " + new URLCodec().encode(args[1]));
				return;
			}
			if (message.startsWith(Configs.preffix + "'urlcodec")) {

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
		if (Records.records.containsKey(sender)) {
			Records.records.get(sender).add(
					"[" + new java.util.Date().toString() + "@" + channel + "]"
							+ sender + ":" + message);
			Gui.log("pushed:" + "[" + new java.util.Date().toString() + "@"
					+ channel + "]" + sender + ":" + message);
		} else {
			Records.records.put(sender, new HashSet<String>());
		}
	}

	public void funcRegexReCall(String channel, String sender, String login,
			String hostname, String message, String args[], boolean regex) {
		String targetUsr = args[1];
		String targetContent = args[2];
		this.sendMessage(channel, ": Searching for " + targetContent + " in "
				+ targetUsr);
		if (Records.records.containsKey(targetUsr)) {// usr
			HashSet<String> record = Records.records.get(targetUsr);
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

	public void funcMute(String sender, String login, String hostname,// - pwd
																		// mute
																		// usr
			String message, String args[], boolean action) {

		if (args[4] == null) {
			Gui.log(sender + ": usage: - sudo [pwd] [cmd] [opt]");
		}
		if (action) {
			Records.mutedUsers.add(args[3]);
			this.sendMessage(sender, "User:" + args[3] + " muted");
		} else {
			Records.mutedUsers.remove(args[3]);
			this.sendMessage(sender, "User:" + args[3] + " unmuted");
		}
		return;
	}

	public void priFuncAnonymousCall(String sender, String login,
			String hostname, String message, String args[], boolean isPrivate) {
		try {
			if (isPrivate) {
				this.sendMessage(sender, "message will be delivered to "
						+ args[1] + " at " + args[2]);
				if (args[1] != null & args[2] != null & args[3] != null) {
					String usr = args[1], channel = args[2], myMessage = "";
					for (String msg : args) {
						if (!msg.equals(args[0]) & !msg.equals(args[1])
								& !msg.equals(args[2])) {
							myMessage += msg + " ";
						}
					}
					String[] queneObj = { usr, channel, myMessage };
					Enterence.APMPool.add(queneObj);
					this.sendMessage(
							channel,
							usr
									+ ", you have an unread anonymous message at this channel. Reply \""
									+ Configs.preffix + "hapm\"/\""
									+ Configs.preffix
									+ "papm\" to hear it here/privately.");
				} else {
					this.sendMessage(
							sender,
							"Usage: -apm [usr] [channel] [msg] Deliver a anonymous message when the user showed up");
				}
			} else {
				this.sendMessage(sender, "message has been delivered to "
						+ args[2]);
				if (args[1] != null & args[2] != null) {// -am channel msg
					String channel = args[1], myMessage = "";
					for (String msg : args) {
						if (!msg.equals(args[0]) & !msg.equals(args[1])) {
							myMessage += msg + " ";
						}
					}
					this.sendMessage(channel, "Anonymous: " + myMessage);
				} else {
					this.sendMessage(sender,
							"Usage: -apm [channel] [msg] Deliver a anonymous message to a certain channel");
				}
			}
		} catch (Exception e) {
			this.funcHelp("#berton-research", sender, login, hostname, message,
					args);
		}
	}

	public void priFuncAnonymousHandle(String channel, String sender,
			String login, String hostname, String message, String args[]) {
		for (String[] queneObj : Enterence.APMPool) {
			// Gui.log("Handling: " + channel + "|" + queneObj[0] + "|" + sender
			// + queneObj[1]);
			if (channel.equals(queneObj[1]) & sender.equals(queneObj[0])) {
				if (message.startsWith(Configs.preffix + "papm")) {
					this.sendMessage(sender, sender + ",someone told you:"
							+ queneObj[2]);
				} else if (message.startsWith(Configs.preffix + "hapm")) {
					this.sendMessage(channel, sender + ",someone told you:"
							+ queneObj[2]);
				}
				Enterence.APMPool.remove(queneObj);
			}
		}
	}

	public void funcHelp(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		this.sendMessage(channel, "Help message sent through private chat.");
		this.sendMessage(sender, "Normal commands:\n");
		this.sendMessage(sender, "-help 							Show this help");
		this.sendMessage(sender, "-ping 							Check if bot is alive");
		this.sendMessage(
				sender,
				"-recall [user] [statement] 	Recall certain phases in the chat history of a user\n");
		this.sendMessage(
				sender,
				"-regrecall [user] [keyword] 	Recall certain phases using regex in the chat history of a user\n");
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
		this.sendMessage(
				sender,
				"/msg "
						+ Configs.name
						+ " -apm [usr] [channel] [msg] 		Deliver an anonymous msg to that channel when the user is around\n");
		this.sendMessage(
				sender,
				"/msg "
						+ Configs.name
						+ " -am [channel] [msg] 			Deliver an anonymous msg to that channel\n");
		this.sendMessage(sender, "Sudo commands:\n");
		this.sendMessage(sender, "[hidden]");
	}

	public boolean isCred(String name) {
		return true;
	}

	@Override
	protected void onQuit(String sourceNick, String sourceLogin,
			String sourceHostname, String reason) {
		super.onQuit(sourceNick, sourceLogin, sourceHostname, reason);
	}
}
