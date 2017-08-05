package ocrPlugin;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.User;

import bot.IRCPlugin;

public class OCRPlugin extends IRCPlugin {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName();
	}

	@Override
	public void onPublicCall(String channel, String sender, String login,
			String hostname, String message, String[] args) {
		// TODO Auto-generated method stub
		super.onPublicCall(channel, sender, login, hostname, message, args);
	}

	@Override
	public void onPrivateCall(String sender, String login, String hostname,
			String message, String[] args) {
		// TODO Auto-generated method stub
		super.onPrivateCall(sender, login, hostname, message, args);
	}

	@Override
	public boolean onLoad() {
		// TODO Auto-generated method stub
		return super.onLoad();
	}

	@Override
	public void onConnect() {
		// TODO Auto-generated method stub
		super.onConnect();
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		super.onDisconnect();
	}

	@Override
	public void onServerResponse(int code, String response) {
		// TODO Auto-generated method stub
		super.onServerResponse(code, response);
	}

	@Override
	public void onUserList(String channel, User[] users) {
		// TODO Auto-generated method stub
		super.onUserList(channel, users);
	}

	@Override
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		// TODO Auto-generated method stub
		super.onMessage(channel, sender, login, hostname, message);
	}

	@Override
	public void onPrivateMessage(String sender, String login, String hostname,
			String message) {
		// TODO Auto-generated method stub
		super.onPrivateMessage(sender, login, hostname, message);
	}

	@Override
	public void onAction(String sender, String login, String hostname,
			String target, String action) {
		// TODO Auto-generated method stub
		super.onAction(sender, login, hostname, target, action);
	}

	@Override
	public void onNotice(String sourceNick, String sourceLogin,
			String sourceHostname, String target, String notice) {
		// TODO Auto-generated method stub
		super.onNotice(sourceNick, sourceLogin, sourceHostname, target, notice);
	}

	@Override
	public void onJoin(String channel, String sender, String login,
			String hostname) {
		// TODO Auto-generated method stub
		super.onJoin(channel, sender, login, hostname);
	}

	@Override
	public void onPart(String channel, String sender, String login,
			String hostname) {
		// TODO Auto-generated method stub
		super.onPart(channel, sender, login, hostname);
	}

	@Override
	public void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		// TODO Auto-generated method stub
		super.onNickChange(oldNick, login, hostname, newNick);
	}

	@Override
	public void onKick(String channel, String kickerNick, String kickerLogin,
			String kickerHostname, String recipientNick, String reason) {
		// TODO Auto-generated method stub
		super.onKick(channel, kickerNick, kickerLogin, kickerHostname, recipientNick,
				reason);
	}

	@Override
	public void onQuit(String sourceNick, String sourceLogin,
			String sourceHostname, String reason) {
		// TODO Auto-generated method stub
		super.onQuit(sourceNick, sourceLogin, sourceHostname, reason);
	}

	@Override
	public void onTopic(String channel, String topic) {
		// TODO Auto-generated method stub
		super.onTopic(channel, topic);
	}

	@Override
	public void onTopic(String channel, String topic, String setBy, long date,
			boolean changed) {
		// TODO Auto-generated method stub
		super.onTopic(channel, topic, setBy, date, changed);
	}

	@Override
	public void onChannelInfo(String channel, int userCount, String topic) {
		// TODO Auto-generated method stub
		super.onChannelInfo(channel, userCount, topic);
	}

	@Override
	public void onMode(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String mode) {
		// TODO Auto-generated method stub
		super.onMode(channel, sourceNick, sourceLogin, sourceHostname, mode);
	}

	@Override
	public void onUserMode(String targetNick, String sourceNick,
			String sourceLogin, String sourceHostname, String mode) {
		// TODO Auto-generated method stub
		super.onUserMode(targetNick, sourceNick, sourceLogin, sourceHostname, mode);
	}

	@Override
	public void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		// TODO Auto-generated method stub
		super.onOp(channel, sourceNick, sourceLogin, sourceHostname, recipient);
	}

	@Override
	public void onDeop(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		// TODO Auto-generated method stub
		super.onDeop(channel, sourceNick, sourceLogin, sourceHostname, recipient);
	}

	@Override
	public void onVoice(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		// TODO Auto-generated method stub
		super.onVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
	}

	@Override
	public void onDeVoice(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		// TODO Auto-generated method stub
		super.onDeVoice(channel, sourceNick, sourceLogin, sourceHostname, recipient);
	}

	@Override
	public void onSetChannelKey(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String key) {
		// TODO Auto-generated method stub
		super.onSetChannelKey(channel, sourceNick, sourceLogin, sourceHostname, key);
	}

	@Override
	public void onRemoveChannelKey(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String key) {
		// TODO Auto-generated method stub
		super.onRemoveChannelKey(channel, sourceNick, sourceLogin, sourceHostname, key);
	}

	@Override
	public void onSetChannelLimit(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, int limit) {
		// TODO Auto-generated method stub
		super.onSetChannelLimit(channel, sourceNick, sourceLogin, sourceHostname, limit);
	}

	@Override
	public void onRemoveChannelLimit(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveChannelLimit(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onSetChannelBan(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String hostmask) {
		// TODO Auto-generated method stub
		super.onSetChannelBan(channel, sourceNick, sourceLogin, sourceHostname,
				hostmask);
	}

	@Override
	public void onRemoveChannelBan(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String hostmask) {
		// TODO Auto-generated method stub
		super.onRemoveChannelBan(channel, sourceNick, sourceLogin, sourceHostname,
				hostmask);
	}

	@Override
	public void onSetTopicProtection(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemoveTopicProtection(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveTopicProtection(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onSetNoExternalMessages(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetNoExternalMessages(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemoveNoExternalMessages(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveNoExternalMessages(channel, sourceNick, sourceLogin,
				sourceHostname);
	}

	@Override
	public void onSetInviteOnly(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemoveInviteOnly(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onSetModerated(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetModerated(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemoveModerated(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveModerated(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onSetPrivate(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetPrivate(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemovePrivate(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemovePrivate(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onSetSecret(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onSetSecret(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemoveSecret(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		// TODO Auto-generated method stub
		super.onRemoveSecret(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onInvite(String targetNick, String sourceNick,
			String sourceLogin, String sourceHostname, String channel) {
		// TODO Auto-generated method stub
		super.onInvite(targetNick, sourceNick, sourceLogin, sourceHostname, channel);
	}

	@Override
	public void onDccSendRequest(String sourceNick, String sourceLogin,
			String sourceHostname, String filename, long address, int port,
			int size) {
		// TODO Auto-generated method stub
		super.onDccSendRequest(sourceNick, sourceLogin, sourceHostname, filename,
				address, port, size);
	}

	@Override
	public void onDccChatRequest(String sourceNick, String sourceLogin,
			String sourceHostname, long address, int port) {
		// TODO Auto-generated method stub
		super.onDccChatRequest(sourceNick, sourceLogin, sourceHostname, address, port);
	}

	@Override
	public void onIncomingFileTransfer(DccFileTransfer transfer) {
		// TODO Auto-generated method stub
		super.onIncomingFileTransfer(transfer);
	}

	@Override
	public void onFileTransferFinished(DccFileTransfer transfer, Exception e) {
		// TODO Auto-generated method stub
		super.onFileTransferFinished(transfer, e);
	}

	@Override
	public void onIncomingChatRequest(DccChat chat) {
		// TODO Auto-generated method stub
		super.onIncomingChatRequest(chat);
	}

	@Override
	public void onVersion(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		// TODO Auto-generated method stub
		super.onVersion(sourceNick, sourceLogin, sourceHostname, target);
	}

	@Override
	public void onPing(String sourceNick, String sourceLogin,
			String sourceHostname, String target, String pingValue) {
		// TODO Auto-generated method stub
		super.onPing(sourceNick, sourceLogin, sourceHostname, target, pingValue);
	}

	@Override
	public void onServerPing(String response) {
		// TODO Auto-generated method stub
		super.onServerPing(response);
	}

	@Override
	public void onTime(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		// TODO Auto-generated method stub
		super.onTime(sourceNick, sourceLogin, sourceHostname, target);
	}

	@Override
	public void onFinger(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		// TODO Auto-generated method stub
		super.onFinger(sourceNick, sourceLogin, sourceHostname, target);
	}

	@Override
	public void onUnknown(String line) {
		// TODO Auto-generated method stub
		super.onUnknown(line);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
