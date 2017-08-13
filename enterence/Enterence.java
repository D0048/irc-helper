package enterence;

import java.util.HashSet;
import java.util.Properties;

import ddkPlugin.DDKPlugin;

import ocrPlugin.OCRPlugin;

import recallerPlugin.RecallerPlugin;
import tlPlugin.TLPlugin;
import wikiSearchPlugin.WikiSearchPlugin;

import function.NetSocket;
import gui.Gui;
import bot.Configs;
import bot.IRCPlugin;
import bot.MyBot;
import bot.MyConfig;
import bot.MyRecord;
import bot.Records;

public class Enterence {
	// TODO:serilize record
	// TODO:add robot
	// TODO:pluginlize

	public static String propFileName = "helper.properties";
	public static MyConfig config = new MyConfig();
	public static Configs configs = new Configs();
	public static MyRecord record = new MyRecord();
	public static Records records = new Records();
	public static HashSet<String[]> APMPool = new HashSet<String[]>();
	public static HashSet<NetSocket> NSPool = new HashSet<NetSocket>();
	public static HashSet<IRCPlugin> PluginPool = new HashSet<IRCPlugin>();
	Properties prop = new Properties();
	public static MyBot bot;

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				try {
					Enterence.record.createDefault();
				} catch (Exception e) {
					Gui.displayException(e);
				}
				Gui.log("Record saved");

				// plugin unload
				Gui.log("Unloading plugins");
				for (IRCPlugin plugin : PluginPool) {
					try {
						plugin.onUnload();
					} catch (Exception e) {
						Gui.log(plugin.getName() + " failed to unload: "
								+ e.getMessage());
					}
				}
			}
		}));

		Gui.startGui(args);
		try {
			Gui.log("Loading config file");
			config.configInit(args);
			record.recordInit(args);
		} catch (Exception e) {
			Gui.log("Failed to init config/record file, use defaut options.\n");
			Gui.displayException(e);
		}

		System.setProperty("java.net.useSystemProxies", "true");
		try {
			Gui.log("Proxy:" + Configs.useProxy);
			if (Configs.useProxy == "1") {
				Gui.log("Loading proxies(support socks only)");
				String ip = Configs.Proxy.split(":")[0];
				String port = Configs.Proxy.split(":")[1];
				if (ip != null && port != null) {
					Gui.log("Proxy:" + ip + "|" + port);
				} else {
					Gui.displayException(new Exception(
							"Wrong proxy format, use host:port"));
					Configs.useProxy = "0";
				}
			}
		} catch (Exception e) {
			Gui.displayException(e);
			Gui.log("Error setting proxy, fallback");
			Configs.useProxy = "0";
		}

		bot = new MyBot(Configs.name, Configs.preffix);
		// Enable debugging output.
		bot.setVerbose(true);

		boolean success = false;
		do {
			try {
				Gui.log("Connecting to: " + Configs.server + "\n");
				bot.connect(Configs.server);
				success = true;
				Gui.log("Successfully connected, now joining channel list!"
						+ Configs.channels.toString() + "\n");
			} catch (Exception e) {
				Gui.displayException(e);
			}
		} while (!success);

		// identification
		bot.sendMessage("NickServ", "identify" + " " + Configs.pwd);
		try {
			Gui.log("Waiting for identification: 0s");
			Thread.sleep(0L);
		} catch (Exception e) {
			Gui.displayException(e);
		}

		// Join the #pircbot channel.
		for (String channel : Configs.channels) {
			Gui.log("Joining: " + channel + "\n");
			bot.joinChannel(channel);
		}

		PluginPool.add(new OCRPlugin());
		PluginPool.add(new TLPlugin());
		PluginPool.add(new WikiSearchPlugin());
		PluginPool.add(new RecallerPlugin());
		PluginPool.add(new DDKPlugin());

		// plugin load
		Gui.log("Loading plugins");
		for (IRCPlugin plugin : PluginPool) {
			if (!plugin.onLoad()) {
				Gui.log(plugin.getName() + " failed to load!");
			}
		}
	}
}
