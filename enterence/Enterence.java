package enterence;

import gui.Gui;
import bot.MyBot;

public class Enterence {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Gui.startGui(args);
		String server = "irc.freenode.net";
		String name = "testbot";
		String[] channels = { "#berton-research", "#linuxba"};
		String preffix = "-";

		MyBot bot = new MyBot(name, preffix);

		// Enable debugging output.
		bot.setVerbose(true);

		boolean success = false;
		do {
			try {
				Gui.log("Connecting to: " + server + "\n");
				bot.connect(server);
				success = true;
				Gui.log("Successfully connected, now joining channel list!"
						+ channels.toString() + "\n");
			} catch (Exception e) {
				Gui.displayException(e);
			}
		} while (!success);

		// Join the #pircbot channel.
		for (String channel : channels) {
			Gui.log("Joining: " + channel + "\n");
			bot.joinChannel(channel);
		}
	}

}
