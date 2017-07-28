package bot;

public class Caller {
	String channel, sender, login, hostname, message;
	String[] args;

	public Caller(String channel, String sender, String login, String hostname,
			String message, String[] args) {
		this.channel = channel;
		this.sender = sender;
		this.login = login;
		this.hostname = hostname;
		this.message = message;
		this.args = args;
	}
}
