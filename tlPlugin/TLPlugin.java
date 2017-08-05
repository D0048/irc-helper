package tlPlugin;

import org.jibble.pircbot.DccChat;
import org.jibble.pircbot.DccFileTransfer;
import org.jibble.pircbot.User;

import bot.IRCPlugin;

public class TLPlugin extends IRCPlugin {
	
	protected String name = "TLPlugin";

	@Override
	public String getName() {
		
		return super.getName();
	}

	@Override
	public void onPublicCall(String channel, String sender, String login,
			String hostname, String message, String[] args) {
		
		super.onPublicCall(channel, sender, login, hostname, message, args);
	}

	@Override
	public void onPrivateCall(String sender, String login, String hostname,
			String message, String[] args) {
		
		super.onPrivateCall(sender, login, hostname, message, args);
	}
	@Override
	public void onSudoCall(String sender, String login, String hostname,
			String message, String args[]) {
	}
	@Override
	public void onCall(String channel, String sender, String login,
			String hostname, String message, String args[]) {
		
	}

	@Override
	public boolean onLoad() {
		
		return super.onLoad();
	}

	@Override
	public void onUnload() {

	}

	@Override
	public void onConnect() {
		
		super.onConnect();
	}

	@Override
	public void onDisconnect() {
		
		super.onDisconnect();
	}

	@Override
	public void onServerResponse(int code, String response) {
		
		super.onServerResponse(code, response);
	}

	@Override
	public void onUserList(String channel, User[] users) {
		
		super.onUserList(channel, users);
	}

	@Override
	public void onMessage(String channel, String sender, String login,
			String hostname, String message) {
		
		super.onMessage(channel, sender, login, hostname, message);
	}

	@Override
	public void onPrivateMessage(String sender, String login, String hostname,
			String message) {
		
		super.onPrivateMessage(sender, login, hostname, message);
	}

	@Override
	public void onAction(String sender, String login, String hostname,
			String target, String action) {
		
		super.onAction(sender, login, hostname, target, action);
	}

	@Override
	public void onNotice(String sourceNick, String sourceLogin,
			String sourceHostname, String target, String notice) {
		
		super.onNotice(sourceNick, sourceLogin, sourceHostname, target, notice);
	}

	@Override
	public void onJoin(String channel, String sender, String login,
			String hostname) {
		
		super.onJoin(channel, sender, login, hostname);
	}

	@Override
	public void onPart(String channel, String sender, String login,
			String hostname) {
		
		super.onPart(channel, sender, login, hostname);
	}

	@Override
	public void onNickChange(String oldNick, String login, String hostname,
			String newNick) {
		
		super.onNickChange(oldNick, login, hostname, newNick);
	}

	@Override
	public void onKick(String channel, String kickerNick, String kickerLogin,
			String kickerHostname, String recipientNick, String reason) {
		
		super.onKick(channel, kickerNick, kickerLogin, kickerHostname,
				recipientNick, reason);
	}

	@Override
	public void onQuit(String sourceNick, String sourceLogin,
			String sourceHostname, String reason) {
		
		super.onQuit(sourceNick, sourceLogin, sourceHostname, reason);
	}

	@Override
	public void onTopic(String channel, String topic) {
		
		super.onTopic(channel, topic);
	}

	@Override
	public void onTopic(String channel, String topic, String setBy, long date,
			boolean changed) {
		
		super.onTopic(channel, topic, setBy, date, changed);
	}

	@Override
	public void onChannelInfo(String channel, int userCount, String topic) {
		
		super.onChannelInfo(channel, userCount, topic);
	}

	@Override
	public void onMode(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String mode) {
		
		super.onMode(channel, sourceNick, sourceLogin, sourceHostname, mode);
	}

	@Override
	public void onUserMode(String targetNick, String sourceNick,
			String sourceLogin, String sourceHostname, String mode) {
		
		super.onUserMode(targetNick, sourceNick, sourceLogin, sourceHostname,
				mode);
	}

	@Override
	public void onOp(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		
		super.onOp(channel, sourceNick, sourceLogin, sourceHostname, recipient);
	}

	@Override
	public void onDeop(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		
		super.onDeop(channel, sourceNick, sourceLogin, sourceHostname,
				recipient);
	}

	@Override
	public void onVoice(String channel, String sourceNick, String sourceLogin,
			String sourceHostname, String recipient) {
		
		super.onVoice(channel, sourceNick, sourceLogin, sourceHostname,
				recipient);
	}

	@Override
	public void onDeVoice(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String recipient) {
		
		super.onDeVoice(channel, sourceNick, sourceLogin, sourceHostname,
				recipient);
	}

	@Override
	public void onSetChannelKey(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String key) {
		
		super.onSetChannelKey(channel, sourceNick, sourceLogin, sourceHostname,
				key);
	}

	@Override
	public void onRemoveChannelKey(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String key) {
		
		super.onRemoveChannelKey(channel, sourceNick, sourceLogin,
				sourceHostname, key);
	}

	@Override
	public void onSetChannelLimit(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, int limit) {
		
		super.onSetChannelLimit(channel, sourceNick, sourceLogin,
				sourceHostname, limit);
	}

	@Override
	public void onRemoveChannelLimit(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onRemoveChannelLimit(channel, sourceNick, sourceLogin,
				sourceHostname);
	}

	@Override
	public void onSetChannelBan(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String hostmask) {
		
		super.onSetChannelBan(channel, sourceNick, sourceLogin, sourceHostname,
				hostmask);
	}

	@Override
	public void onRemoveChannelBan(String channel, String sourceNick,
			String sourceLogin, String sourceHostname, String hostmask) {
		
		super.onRemoveChannelBan(channel, sourceNick, sourceLogin,
				sourceHostname, hostmask);
	}

	@Override
	public void onSetTopicProtection(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onSetTopicProtection(channel, sourceNick, sourceLogin,
				sourceHostname);
	}

	@Override
	public void onRemoveTopicProtection(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onRemoveTopicProtection(channel, sourceNick, sourceLogin,
				sourceHostname);
	}

	@Override
	public void onSetNoExternalMessages(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onSetNoExternalMessages(channel, sourceNick, sourceLogin,
				sourceHostname);
	}

	@Override
	public void onRemoveNoExternalMessages(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onRemoveNoExternalMessages(channel, sourceNick, sourceLogin,
				sourceHostname);
	}

	@Override
	public void onSetInviteOnly(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onSetInviteOnly(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemoveInviteOnly(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onRemoveInviteOnly(channel, sourceNick, sourceLogin,
				sourceHostname);
	}

	@Override
	public void onSetModerated(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onSetModerated(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemoveModerated(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onRemoveModerated(channel, sourceNick, sourceLogin,
				sourceHostname);
	}

	@Override
	public void onSetPrivate(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onSetPrivate(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemovePrivate(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onRemovePrivate(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onSetSecret(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onSetSecret(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onRemoveSecret(String channel, String sourceNick,
			String sourceLogin, String sourceHostname) {
		
		super.onRemoveSecret(channel, sourceNick, sourceLogin, sourceHostname);
	}

	@Override
	public void onInvite(String targetNick, String sourceNick,
			String sourceLogin, String sourceHostname, String channel) {
		
		super.onInvite(targetNick, sourceNick, sourceLogin, sourceHostname,
				channel);
	}

	@Override
	public void onDccSendRequest(String sourceNick, String sourceLogin,
			String sourceHostname, String filename, long address, int port,
			int size) {
		
		super.onDccSendRequest(sourceNick, sourceLogin, sourceHostname,
				filename, address, port, size);
	}

	@Override
	public void onDccChatRequest(String sourceNick, String sourceLogin,
			String sourceHostname, long address, int port) {
		
		super.onDccChatRequest(sourceNick, sourceLogin, sourceHostname,
				address, port);
	}

	@Override
	public void onIncomingFileTransfer(DccFileTransfer transfer) {
		
		super.onIncomingFileTransfer(transfer);
	}

	@Override
	public void onFileTransferFinished(DccFileTransfer transfer, Exception e) {
		
		super.onFileTransferFinished(transfer, e);
	}

	@Override
	public void onIncomingChatRequest(DccChat chat) {
		
		super.onIncomingChatRequest(chat);
	}

	@Override
	public void onVersion(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		
		super.onVersion(sourceNick, sourceLogin, sourceHostname, target);
	}

	@Override
	public void onPing(String sourceNick, String sourceLogin,
			String sourceHostname, String target, String pingValue) {
		
		super.onPing(sourceNick, sourceLogin, sourceHostname, target, pingValue);
	}

	@Override
	public void onServerPing(String response) {
		
		super.onServerPing(response);
	}

	@Override
	public void onTime(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		
		super.onTime(sourceNick, sourceLogin, sourceHostname, target);
	}

	@Override
	public void onFinger(String sourceNick, String sourceLogin,
			String sourceHostname, String target) {
		
		super.onFinger(sourceNick, sourceLogin, sourceHostname, target);
	}

	@Override
	public void onUnknown(String line) {
		
		super.onUnknown(line);
	}

	@Override
	public String toString() {
		
		return super.toString();
	}

}
