package enterence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import gui.Gui;
import bot.Configs;
import bot.MyBot;
import bot.MyConfig;

public class Enterence{
	
	public static MyConfig config = new MyConfig();
	public static Configs configs = new Configs();
	Properties prop = new Properties();

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Gui.startGui(args);
		try {
			Gui.log("Loading config file");
			config.configInit(args);
		} catch (Exception e) {
			Gui.log("Failed to init config file, use defaut options.\n");
			Gui.displayException(e);
		}

		MyBot bot = new MyBot(configs.name, configs.preffix);
		
		// Enable debugging output.
		bot.setVerbose(true);

		boolean success = false;
		do {
			try {
				Gui.log("Connecting to: " + configs.server + "\n");
				bot.connect(configs.server);
				success = true;
				Gui.log("Successfully connected, now joining channel list!"
						+ configs.channels.toString() + "\n");
			} catch (Exception e) {
				Gui.displayException(e);
			}
		} while (!success);

		// Join the #pircbot channel.
		for (String channel : configs.channels) {
			Gui.log("Joining: " + channel + "\n");
			bot.joinChannel(channel);
		}
	}

	
}
