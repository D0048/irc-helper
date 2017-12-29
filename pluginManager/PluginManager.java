package pluginManager;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;

import org.apache.commons.configuration.PropertiesConfiguration;

import enterence.Enterence;
import gui.Gui;

import bot.IRCPlugin;

public class PluginManager {

	public static final String dirPath = "plugins";
	public static final String configFileName = "plugin.prop";
	public static HashSet<IRCPlugin> toReloadPool = new HashSet<IRCPlugin>();
	File pluginDir;

	public PluginManager() {
		File dir = new File(PluginManager.dirPath);
		if (!dir.exists() && !dir.isDirectory()) {
			dir.delete();
			dir.mkdir();
		}

		for (File f : dir.listFiles()) {
			try {
				if (f.isDirectory()) {
					// find config file
					if (!new File(f.getAbsolutePath() + "/"
							+ PluginManager.configFileName).exists()) {
						Gui.log("Skipping directory: " + f.getName()
								+ ": config file not found");
						continue;
					}

					PropertiesConfiguration config = new PropertiesConfiguration(
							f.getAbsolutePath() + "/" + PluginManager.configFileName);
					String mainClassName = config.getString("main");
					String jarName = config.getString("jar");

					if (mainClassName == null && jarName == null) {
						Gui.log("Skipping directory: " + f.getName()
								+ ": config file broken");
						continue;
					}
					// load main class
					URL url1 = new URL("file:" + dir.getAbsolutePath()
							+ jarName);
					URLClassLoader myClassLoader1 = new URLClassLoader(
							new URL[] { url1 }, Thread.currentThread()
									.getContextClassLoader());
					Class<?> myClass1 = myClassLoader1.loadClass(mainClassName);
					Enterence.PluginPool
							.add((IRCPlugin) myClass1.newInstance());
					myClassLoader1.close();
					// call init

				}
			} catch (Exception e) {
				Gui.log("Error handling dir:");
			}
		}

	}
}
