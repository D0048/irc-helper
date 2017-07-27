package bot;



import java.io.File;

import org.apache.commons.configuration.PropertiesConfiguration;

import enterence.Enterence;

public class MyConfig {

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
		File file = new File(Configs.propFileName);

		if (!file.exists()) {
			file.createNewFile();
			PropertiesConfiguration config = new PropertiesConfiguration(Configs.propFileName);
			config.addProperty("sudoers", Configs.sudoers);
			config.addProperty("sudoPwd", Configs.sudoPwd);
			config.addProperty("server", Configs.server);
			config.addProperty("name", Configs.name);
			config.addProperty("channels", Configs.channels);
			config.addProperty("preffix", Configs.preffix);
			config.addProperty("sudoers", Configs.sudoers);
			config.save();
		} else {
			
		}
		// prop.
	}
}
