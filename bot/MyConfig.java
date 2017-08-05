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
			Gui.log("Config file found, start loading");
			try {
				PropertiesConfiguration config = new PropertiesConfiguration(
						Enterence.propFileName);
				Configs.sudoers = config.getStringArray("sudoers");
				Configs.sudoPwd = (String) config.getString("sudoPwd");
				Configs.server = (String) config.getString("server");
				Configs.name = (String) config.getString("name");
				Configs.channels = (String[]) config.getStringArray("channels");
				Configs.preffix = (String) config.getString("preffix");
				Configs.sudoers = (String[]) config.getStringArray("sudoers");
				Configs.recordFileName = (String) config.getString("recordFileName");
				if (Configs.sudoers == null || Configs.sudoPwd == null
						|| Configs.server == null || Configs.name == null
						|| Configs.channels == null || Configs.preffix == null
						|| Configs.sudoers == null || Configs.recordFileName == null) {
					throw new Exception("File damaged");
				}
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
		if(file.exists()){
			file.renameTo(new File(Enterence.propFileName+".bak"));
		}
		file.createNewFile();
		PropertiesConfiguration config = new PropertiesConfiguration(
				Enterence.propFileName);
		config.setProperty("sudoers", Configs.sudoers);
		config.setProperty("sudoPwd", Configs.sudoPwd);
		config.setProperty("server", Configs.server);
		config.setProperty("name", Configs.name);
		config.setProperty("channels", Configs.channels);
		config.setProperty("preffix", Configs.preffix);
		config.setProperty("sudoers", Configs.sudoers);
		config.setProperty("recordFileName", Configs.recordFileName);
		config.save();
	}
}
