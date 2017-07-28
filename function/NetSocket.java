package function;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashSet;

import bot.Caller;

public class NetSocket{
	Caller caller;
	String Address, id;
	Socket socket;
	String rBuff, WBuff;

	NetSocket(String channel, String sender, String login, String hostname,
			String message, String args[], String Address, int port)
			throws UnknownHostException, IOException {
		this.caller = new Caller(channel, sender, login, hostname, message,
				message.split(" "));
		this.Address = Address;
		this.socket = new Socket(Address, port);
		this.id = new Integer(this.hashCode()).toString();
	}

	public void sendMsg(String wBuff) throws Exception {
		this.WBuff = wBuff;
		socket.setSoTimeout(1000);
		if (socket.isConnected()) {
			socket.getOutputStream().write(wBuff.getBytes());
			socket.getOutputStream().flush();
			String s;
			while ((s = new BufferedReader(new InputStreamReader(
					socket.getInputStream())).readLine()) != null) {

			}
		} else {
			throw new Exception("Now connected");
		}
	}

	public String getrBuff() {
		return rBuff;
	}

	public String getWBuff() {
		return WBuff;
	}

	public void setWBuff(String wBuff) {
		WBuff = wBuff;
	}

}
