package bot;

import enterence.Enterence;
import gui.Gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.configuration.PropertiesConfiguration;

public class MyRecord {

	File file = new File(Configs.recordFileName);

	public void recordInit(String[] args) throws Exception {
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
			Gui.log("Record file found, start loading");
			try {
				PropertiesConfiguration config = new PropertiesConfiguration(
						Configs.recordFileName);
				Records.verifiedUsers.addAll(Arrays.asList(config
						.getStringArray("verifiedUsers")));
				Records.mutedUsers.addAll(Arrays.asList(config
						.getStringArray("mutedUsers")));

				// Records.records = (HashMap<String, HashSet<String>>)
				// config.getProperty("records");

				String[] usrEntries = config.getStringArray("usrEntries");
				Gui.log("Users Read:" + usrEntries.toString());
				for (String usr : usrEntries) {// per usr
					Gui.log("loading:" + usr);
					String[] statements = config.getStringArray(usr);
					HashSet<String> statementsMap = new HashSet<String>();
					for (String statement : statements) {// per statement
						Gui.log("    loading:" + statement);
						statementsMap.add(statement);
					}
					Records.records.put(usr, statementsMap);
				}

				if (Records.verifiedUsers == null || Records.mutedUsers == null
						| Records.records == null) {
					throw new Exception("File damaged");
				}
			} catch (Exception e) {
				// Gui.displayException(e);
				Gui.log("Record file error: " + e.getMessage()
						+ ", creating one as default");
				createDefault();
			}
		} else {
			Gui.log("Record file not found, creating one as default");
			createDefault();
		}

	}

	public void createDefault() throws Exception {
		if (file.exists()) {
			// file.renameTo(new File(Configs.recordFileName+".bak"));
			file.delete();
		}
		file.createNewFile();
		PropertiesConfiguration record = new PropertiesConfiguration(
				Configs.recordFileName);

		Records.mutedUsers.add("foo_-_");
		Records.records.put("foo_-_", new HashSet<String>() {
			{
				this.add("test");
			}
		});
		Records.verifiedUsers.add("d0048");

		record.addProperty("verifiedUsers", Records.verifiedUsers.toArray());

		// record.addProperty("records", Records.records);

		Object[] usrEntries = Records.records.keySet().toArray();
		record.addProperty("usrEntries", usrEntries);
		for (Object usr : usrEntries) {
			record.addProperty((String) usr, Records.records.get(usr).toArray());
		}
		record.save();
	}

	public void saveRecord() throws Exception {
		if (file.exists()) {
			// file.renameTo(new File(Configs.recordFileName+".bak"));
			file.delete();
		}
		file.createNewFile();
		PropertiesConfiguration record = new PropertiesConfiguration(
				Configs.recordFileName);

		record.addProperty("verifiedUsers", Records.verifiedUsers.toArray());

		// record.addProperty("records", Records.records);

		Object[] usrEntries = Records.records.keySet().toArray();
		record.addProperty("usrEntries", usrEntries);
		for (Object usr : usrEntries) {
			record.addProperty((String) usr, Records.records.get(usr).toArray());
		}
		record.save();

	}
}
