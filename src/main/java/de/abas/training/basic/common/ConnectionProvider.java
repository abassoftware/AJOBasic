package de.abas.training.basic.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import de.abas.erp.db.DbContext;
import de.abas.erp.db.util.ContextHelper;

public class ConnectionProvider {
	public String hostname;
	public String mandant;
	public String password;
	public int port;
	public boolean edpLog;

	public ConnectionProvider() {
	}

	public DbContext createDbContext(String name) {
		loadProperties();
		return ContextHelper.createClientContext(hostname, port, mandant, password,
				name);
	}

	private void loadProperties() {
		Properties pr = new Properties();
		File configFile = new File("gradle.properties");
		try {
			pr.load(new FileReader(configFile));
			hostname = pr.getProperty("EDP_HOST");
			mandant = pr.getProperty("EDP_CLIENT");
			port = Integer.parseInt(pr.getProperty("EDP_PORT", "6550"));
			password = pr.getProperty("EDP_PASSWORD");
			edpLog = Boolean.parseBoolean(pr.getProperty("EDP_LOG", "false"));
		}
		catch (FileNotFoundException e) {
			throw new RuntimeException("Could not find configuration file "
					+ configFile.getAbsolutePath());
		}
		catch (IOException e) {
			throw new RuntimeException("Could not load configuration file "
					+ configFile.getAbsolutePath());
		}
	}
}