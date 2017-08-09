package bot;

import java.io.File;
import org.apache.commons.configuration.PropertiesConfiguration;

import enterence.Enterence;
import gui.Gui;

public class MyConfig {
	File file = new File(Enterence.propFileName);

	public void configInit(String[] args) throws Exception {
		/*
		 * FileOutputStream out = new FileOutputStream(Enterence.propFileName);
		 * Enterence.prop.putIfAbsent("sudoers", Enterence.sudoers);
		 * Enterence.prop.putIfAbsent("sudoPwd", Enterence.sudoPwd);
		 * Enterence.prop.putIfAbsent("server", Enterence.server);
		 * Enterence.prop.putIfAbsent("name", Enterence.name);
		 * Enterence.prop.putIfAbsent("channels", Enterence.channels);
		 * Enterence.prop.putIfAbsent("preffix", Enterence.preffix);
		 * Enterence.prop.putIfAbsent("sudoers", Enterence.sudoers);
		 * Enterence.prop.store(out, null); out.close();
		 */

		if (file.exists()) {
			boolean isDamaged = false;
			Gui.log("Config file found, start loading");

			try {
				PropertiesConfiguration config = new PropertiesConfiguration(
						Enterence.propFileName);
				if (config.getStringArray("sudoers") == null) {
					config.setProperty("sudoers", Configs.sudoers);
					isDamaged = true;
				}
				Configs.sudoers = config.getStringArray("sudoers");

				if (config.getString("sudoPwd") == null) {
					config.setProperty("sudoPwd", Configs.sudoPwd);
					isDamaged = true;
				}
				Configs.sudoPwd = (String) config.getString("sudoPwd");

				if (config.getString("server") == null) {
					config.setProperty("server", Configs.server);
					isDamaged = true;
				}
				Configs.server = (String) config.getString("server");

				if (config.getString("name") == null) {
					config.setProperty("name", Configs.name);
					isDamaged = true;
				}
				Configs.name = (String) config.getString("name");

				if (config.getString("pwd") == null) {
					config.setProperty("pwd", Configs.pwd);
					isDamaged = true;
				}
				Configs.pwd = (String) config.getString("pwd");

				if (config.getStringArray("channels") == null) {
					config.setProperty("channels", Configs.channels);
					isDamaged = true;
				}
				Configs.channels = (String[]) config.getStringArray("channels");

				if (config.getString("preffix") == null) {
					config.setProperty("preffix", Configs.preffix);
					isDamaged = true;
				}
				Configs.preffix = (String) config.getString("preffix");

				if (config.getStringArray("sudoers") == null) {
					config.setProperty("sudoers", Configs.sudoers);
					isDamaged = true;
				}
				Configs.sudoers = (String[]) config.getStringArray("sudoers");

				if (config.getString("recordFileName") == null) {
					config.setProperty("recordFileName", Configs.recordFileName);
					isDamaged = true;
				}
				Configs.recordFileName = (String) config
						.getString("recordFileName");

				if (config.getString("useProxy") == null) {
					config.setProperty("useProxy", Configs.useProxy);
					isDamaged = true;
				}
				Configs.useProxy = (String) config.getString("useProxy");

				if (config.getString("Proxy") == null) {
					config.setProperty("Proxy", Configs.Proxy);
					isDamaged = true;
				}
				Configs.Proxy = (String) config.getString("Proxy");

				if (isDamaged) {
					// throw new Exception("File damaged");
					Gui.log("File damaged. Defaut override");
				}
				config.save();
			} catch (Exception e) {
				Gui.displayException(e);
				Gui.log("Config file error: " + e.getMessage()
						+ ", creating one as default");
				createDefault();
			}
		} else {
			Gui.log("Config file not found, creating one as default");
			createDefault();
		}

	}

	public void createDefault() throws Exception {
		if (file.exists()) {
			file.renameTo(new File(Enterence.propFileName + ".bak"));
		}
		file.createNewFile();
		PropertiesConfiguration config = new PropertiesConfiguration(
				Enterence.propFileName);
		config.setProperty("sudoers", Configs.sudoers);
		config.setProperty("sudoPwd", Configs.sudoPwd);
		config.setProperty("server", Configs.server);
		config.setProperty("name", Configs.name);
		config.setProperty("pwd", Configs.pwd);
		config.setProperty("channels", Configs.channels);
		config.setProperty("preffix", Configs.preffix);
		config.setProperty("sudoers", Configs.sudoers);
		config.setProperty("recordFileName", Configs.recordFileName);
		config.setProperty("useProxy", Configs.useProxy);
		config.setProperty("Proxy", Configs.Proxy);
		config.save();
	}
}
