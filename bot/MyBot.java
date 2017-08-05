package bot;

import enterence.Enterence;
import function.HexTrans;
import gui.Gui;

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

			for (IRCPlugin plugin : Enterence.PluginPool) {
				try {
					plugin.onMessage(channel, sender, login, hostname, message);
				} catch (Exception e) {
					Gui.displayException(e);
					Gui.log("Internal Error when handling " + plugin.getName());
				}

			}
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
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onPrivateMessage(sender, login, hostname, message);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}

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
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSudoCall(sender, login, hostname, message, args);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}

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
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onPrivateCall(sender, login, hostname, message, args);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}

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
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onCall(channel, sender, login, hostname, message, args);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
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
						+ args[1]);
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
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onQuit(sourceNick, sourceLogin, sourceHostname, reason);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onConnect() {
		// TODO Auto-generated method stub
		super.onConnect();
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onConnect();
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onDisconnect() {
		// TODO Auto-generated method stub
		super.onDisconnect();
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onDisconnect();
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onServerResponse(int code, String response) {
		// TODO Auto-generated method stub
		super.onServerResponse(code, response);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onServerResponse(code, response);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onUserList(String channel, User[] users) {
		// TODO Auto-generated method stub
		super.onUserList(channel, users);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onUserList(channel, users);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onAction(String sender, String login, String hostname,
			String target, String action) {
		// TODO Auto-generated method stub
		super.onAction(sender, login, hostname, target, action);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onAction(sender, login, hostname, target, action);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onNotice(String sourceNick, String sourceLogin,
			String sourceHostname, String target, String notice) {
		// TODO Auto-generated method stub
		super.onNotice(sourceNick, sourceLogin, sourceHostname, target, notice);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onNotice(sourceNick, sourceLogin, sourceHostname,
						target, notice);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onJoin(String channel, String sender, String login,
			String hostname) {
		// TODO Auto-generated method stub
		super.onJoin(channel, sender, login, hostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onJoin(channel, sender, login, hostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onPart(String channel, String sender, String login,
			String hostname) {
		// TODO Auto-generated method stub
		super.onPart(channel, sender, login, hostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onPart(channel, sender, login, hostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		// TODO Auto-generated method stub
		super.onNickChange(oldNick, login, hostname, newNick);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onNickChange(oldNick, login, hostname, newNick);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onKick(String channel, String kickerNick,
			String kickerLogin, String kickerHostname, String recipientNick,
			String reason) {
		// TODO Auto-generated method stub
		super.onKick(channel, kickerNick, kickerLogin, kickerHostname,
				recipientNick, reason);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onKick(channel, kickerNick, kickerLogin, kickerHostname,
						recipientNick, reason);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onTopic(String channel, String topic) {
		// TODO Auto-generated method stub
		super.onTopic(channel, topic);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onTopic(channel, topic);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onTopic(String channel, String topic, String setBy,
			long date, boolean changed) {
		// TODO Auto-generated method stub
		super.onTopic(channel, topic, setBy, date, changed);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onTopic(channel, topic, setBy, date, changed);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onChannelInfo(String channel, int userCount, String topic) {
		// TODO Auto-generated method stub
		super.onChannelInfo(channel, userCount, topic);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onChannelInfo(channel, userCount, topic);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onMode(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String mode) {
		// TODO Auto-generated method stub
		super.onMode(channel, sourceNick, sourceLogin, sourceHostname, mode);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onMode(channel, sourceNick, sourceLogin, sourceHostname,
						mode);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onUserMode(String targetNick, String sourceNick,
			String sourceLogin, String sourceHostname, String mode) {
		// TODO Auto-generated method stub
		super.onUserMode(targetNick, sourceNick, sourceLogin, sourceHostname,
				mode);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onUserMode(targetNick, sourceNick, sourceLogin,
						sourceHostname, mode);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		// TODO Auto-generated method stub
		super.onOp(channel, sourceNick, sourceLogin, sourceHostname, recipient);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onOp(channel, sourceNick, sourceLogin, sourceHostname,
						recipient);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onDeop(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		// TODO Auto-generated method stub
		super.onDeop(channel, sourceNick, sourceLogin, sourceHostname,
				recipient);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onDeop(channel, sourceNick, sourceLogin, sourceHostname,
						recipient);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onVoice(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		// TODO Auto-generated method stub
		super.onVoice(channel, sourceNick, sourceLogin, sourceHostname,
				recipient);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onDeVoice(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		// TODO Auto-generated method stub
		super.onDeVoice(channel, sourceNick, sourceLogin, sourceHostname,
				recipient);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onDeVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetChannelKey(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String key) {
		// TODO Auto-generated method stub
		super.onSetChannelKey(channel, sourceNick, sourceLogin, sourceHostname,
				key);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetChannelKey(channel, sourceNick, sourceLogin, sourceHostname, key);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemoveChannelKey(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String key) {
		// TODO Auto-generated method stub
		super.onRemoveChannelKey(channel, sourceNick, sourceLogin,
				sourceHostname, key);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemoveChannelKey(channel, sourceNick, sourceLogin, sourceHostname, key);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetChannelLimit(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, int limit) {
		// TODO Auto-generated method stub
		super.onSetChannelLimit(channel, sourceNick, sourceLogin,
				sourceHostname, limit);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetChannelLimit(channel, sourceNick, sourceLogin, sourceHostname, limit);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemoveChannelLimit(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveChannelLimit(channel, sourceNick, sourceLogin,
				sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemoveChannelLimit(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetChannelBan(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String hostmask) {
		// TODO Auto-generated method stub
		super.onSetChannelBan(channel, sourceNick, sourceLogin, sourceHostname,
				hostmask);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetChannelBan(channel, sourceNick, sourceLogin, sourceHostname, hostmask);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemoveChannelBan(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String hostmask) {
		// TODO Auto-generated method stub
		super.onRemoveChannelBan(channel, sourceNick, sourceLogin,
				sourceHostname, hostmask);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemoveChannelBan(channel, sourceNick, sourceLogin, sourceHostname, hostmask);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetTopicProtection(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetTopicProtection(channel, sourceNick, sourceLogin,
				sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemoveTopicProtection(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveTopicProtection(channel, sourceNick, sourceLogin,
				sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemoveTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetNoExternalMessages(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetNoExternalMessages(channel, sourceNick, sourceLogin,
				sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemoveNoExternalMessages(String channel,
			String sourceNick, String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveNoExternalMessages(channel, sourceNick, sourceLogin,
				sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemoveNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetInviteOnly(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemoveInviteOnly(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveInviteOnly(channel, sourceNick, sourceLogin,
				sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemoveInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetModerated(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetModerated(channel, sourceNick, sourceLogin, sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetModerated(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemoveModerated(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveModerated(channel, sourceNick, sourceLogin,
				sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemoveModerated(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetPrivate(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetPrivate(channel, sourceNick, sourceLogin, sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetPrivate(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemovePrivate(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemovePrivate(channel, sourceNick, sourceLogin, sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemovePrivate(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onSetSecret(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetSecret(channel, sourceNick, sourceLogin, sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onSetSecret(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onRemoveSecret(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveSecret(channel, sourceNick, sourceLogin, sourceHostname);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onRemoveSecret(channel, sourceNick, sourceLogin, sourceHostname);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onInvite(String targetNick, String sourceNick,
			String sourceLogin, String sourceHostname, String channel) {
		// TODO Auto-generated method stub
		super.onInvite(targetNick, sourceNick, sourceLogin, sourceHostname,
				channel);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onInvite(targetNick, sourceNick, sourceLogin, sourceHostname, channel);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onDccSendRequest(String sourceNick, String sourceLogin,
			String sourceHostname, String filename, long address, int port,
			int size) {
		// TODO Auto-generated method stub
		super.onDccSendRequest(sourceNick, sourceLogin, sourceHostname,
				filename, address, port, size);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onDccSendRequest(sourceNick, sourceLogin, sourceHostname, filename, address, port, size);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onDccChatRequest(String sourceNick, String sourceLogin,
			String sourceHostname, long address, int port) {
		// TODO Auto-generated method stub
		super.onDccChatRequest(sourceNick, sourceLogin, sourceHostname,
				address, port);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onDccChatRequest(sourceNick, sourceLogin, sourceHostname, address, port);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onIncomingFileTransfer(DccFileTransfer transfer) {
		// TODO Auto-generated method stub
		super.onIncomingFileTransfer(transfer);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onIncomingFileTransfer(transfer);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onFileTransferFinished(DccFileTransfer transfer, Exception e) {
		// TODO Auto-generated method stub
		super.onFileTransferFinished(transfer, e);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onFileTransferFinished(transfer, e);
			} catch (Exception e2) {
				Gui.displayException(e2);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onIncomingChatRequest(DccChat chat) {
		// TODO Auto-generated method stub
		super.onIncomingChatRequest(chat);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onIncomingChatRequest(chat);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onVersion(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		// TODO Auto-generated method stub
		super.onVersion(sourceNick, sourceLogin, sourceHostname, target);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onVersion(sourceNick, sourceLogin, sourceHostname, target);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onPing(String sourceNick, String sourceLogin,
			String sourceHostname, String target, String pingValue) {
		// TODO Auto-generated method stub
		super.onPing(sourceNick, sourceLogin, sourceHostname, target, pingValue);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onPing(sourceNick, sourceLogin, sourceHostname, target, pingValue);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onServerPing(String response) {
		// TODO Auto-generated method stub
		super.onServerPing(response);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onServerPing(response);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onTime(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		// TODO Auto-generated method stub
		super.onTime(sourceNick, sourceLogin, sourceHostname, target);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onTime(sourceNick, sourceLogin, sourceHostname, target);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onFinger(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		// TODO Auto-generated method stub
		super.onFinger(sourceNick, sourceLogin, sourceHostname, target);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onFinger(sourceNick, sourceLogin, sourceHostname, target);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}

	@Override
	protected void onUnknown(String line) {
		// TODO Auto-generated method stub
		super.onUnknown(line);
		for (IRCPlugin plugin : Enterence.PluginPool) {
			try {
				plugin.onUnknown(line);
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Internal Error when handling " + plugin.getName());
			}
		}
	}
}
