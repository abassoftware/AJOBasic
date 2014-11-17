package de.abas.training.common;

import java.io.FileWriter;
import java.io.IOException;

import de.abas.eks.jfop.FOPException;
import de.abas.eks.jfop.remote.ContextRunnable;
import de.abas.eks.jfop.remote.FOPSessionContext;
import de.abas.erp.db.DbContext;
import de.abas.erp.db.DbMessage;
import de.abas.erp.db.MessageListener;
import de.abas.erp.db.util.ContextHelper;

/**
 * Utility method for getting the client and/or server context.
 * 
 * @author abas Software AG
 *
 */
public abstract class AbstractAjoAccess implements ContextRunnable {
	
	// define EDP connection properties
	private String hostname = "schulung";
	private String mandant = "i7erp4";
	private String password = "sy";
	private int port = 6550;

	private FileWriter fileWriterLogging;
	
	public enum Status {
		/**
		 * Status enumeration.
		 * 
		 * @author abas Software AG
		 *
		 */
		UNDEFINED{
			@Override
			public String toString() {
				return "";
			}
		},
		OK_MODE {

			@Override
			public String toString() {
				return "ok-mode";
			}

		},
		ERROR_MODE {

			@Override
			public String toString() {
				return "error-mode";
			}
		}
	}
	
	/**
	 * Context mode enumeration.
	 * 
	 * @author abas Software AG
	 *
	 */
	public enum ContextMode {
		UNDEFINED {
			@Override
			public String toString() {
				return "";
			}
		},
		SERVER_MODE {
			@Override
			public String toString() {
				return "server-mode";
			}
		},
		CLIENT_MODE {
			@Override
			public String toString() {
				return "client-mode";
			}
		}
	}
	
	// Initialize DbContext 
	private DbContext dbContext = null;
	private ContextMode mode = ContextMode.UNDEFINED;
	
	
	/**
	 * Abstract run method to implement in all derived classes.
	 * 
	 * @param args
	 */
	public abstract void run(String[] args);
	
	
	// server access: get server context. Initialize mode
	public int runFop(FOPSessionContext fopSessionContext, String[] args) throws FOPException {
		this.dbContext = fopSessionContext.getDbContext();
		this.mode = ContextMode.SERVER_MODE;
		run(args);
		return 0;
	}
	
	
	/**
	 * Runs program in client mode. Creates client context if dbContext==null
	 * 
	 * @param args
	 */
	public final void runClientProgram(String[] args) {
		run(args);
		disableLogging();
		getDbContext().close();
	}
	
	
	/**
	 * Disables EDP logging
	 */
	private void disableLogging() {
		if (null != fileWriterLogging) {
			try {
				fileWriterLogging.close();
			} catch (IOException e) {
				getDbContext().out().println(e.getMessage());
			} finally {
				fileWriterLogging = null;
			}
		}
		getDbContext().setLogger(null);
	}
	
	/**
	 * Enables EDP logging. Creates a log file (name is class name).
	 */
	public final void enableLogging() {
		enableLogging(getClass().getSimpleName() + ".log");
	}

	/**
	 * Enables EDP logging. Creates a log file (name as defined in parameter).
	 * 
	 * @param fileName Name of log file.
	 */
	public void enableLogging(String fileName) {
		try {
			fileWriterLogging = new FileWriter(fileName);
			getDbContext().setLogger(fileWriterLogging);
		} catch (IOException e) {
			getDbContext().out().println(e.getMessage());
		}
	}

	/**
	 * Adds a default message listener.
	 * Gets all text, status and error messages.
	 */
	public void addDefaultMessageListener() {
		getDbContext().addMessageListener(new MessageListener() {
			// Displays all text, status and error messages
			public void receiveMessage(DbMessage message) {
				getDbContext().out().println("|"+ message +"|");
			}

		});
	}

	
	/**
	 * Gets the database context. Create a client context if dbContext == null
	 * 
	 * @return The database context.
	 */
	public DbContext getDbContext() {
		if(this.dbContext == null){
			this.dbContext = ContextHelper.createClientContext(hostname, port, mandant, password, this.getClass().getSimpleName());
			mode = ContextMode.CLIENT_MODE;
		}
		return this.dbContext;
	}
	
	/**
	 * Returns the context mode.
	 * 
	 * @return The context mode.
	 */
	public String getMode() {
		return this.mode.toString();
	}

	/**
	 * Returns the host name.
	 * 
	 * @return The host name.
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets the host name if client context is running.
	 * 
	 * @param hostname The host name.
	 */
	public void setHostname(String hostname) {
		if (isClientContextRunning()) {
			this.hostname = hostname;
			dbContext.close();
			this.dbContext = null;
		}
	}

	/**
	 * Returns the client.
	 * 
	 * @return The client.
	 */
	public String getMandant() {
		return mandant;
	}

	/**
	 * Sets the client if client context is running.
	 * 
	 * @param mandant The client.
	 */
	public void setMandant(String mandant) {
		if (isClientContextRunning()) {
			this.mandant = mandant;
			dbContext.close();
			this.dbContext = null;
		}
	}

	/**
	 * Returns the port.
	 * 
	 * @return The port.
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Sets the port if client context is running.
	 * 
	 * @param port The port.
	 */
	public void setPort(int port) {
		if (isClientContextRunning()) {
			this.port = port;
			dbContext.close();
			this.dbContext = null;
		}
	}

	/**
	 * Sets the password if client context is running.
	 * 
	 * @param password The password.
	 */
	public void setPassword(String password) {
		if (isClientContextRunning()) {
			this.password = password;
			dbContext.close();
			this.dbContext = null;
		}
	}

	/**
	 * Checks whether the client context is running.
	 * 
	 * @return true -> client context is running, false -> client context is not running
	 */
	private boolean isClientContextRunning() {
		if (mode.equals(ContextMode.CLIENT_MODE)) {
			return true;
		} else {
			dbContext.out().println("No Client-Mode running -> parameter may not be changed");
			return false;
		}
	}
}
