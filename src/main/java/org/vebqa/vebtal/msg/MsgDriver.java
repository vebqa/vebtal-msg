package org.vebqa.vebtal.msg;

import java.io.IOException;

import org.apache.poi.hsmf.MAPIMessage;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Outlook message driver
 * 
 * @author doerges
 *
 */
public class MsgDriver extends ExternalResource {

	private static final Logger logger = LoggerFactory.getLogger(MsgDriver.class);

	private MAPIMessage message;
	
	private String pathToMsgFile;

	private boolean msgIsLoaded;

	public MsgDriver() {
		this.msgIsLoaded = false;
		this.pathToMsgFile = null;
	}

	public void setMessage(MAPIMessage aMessage) {
		this.message = aMessage;
		this.msgIsLoaded = true;
	}

	public MAPIMessage getMessage() {
		if (this.message == null) {
			load();
		}
		return this.message;
	}

	public boolean isLoaded() {
		return this.msgIsLoaded;
	}

	public void close() throws IOException {
		if (this.message != null) {
			this.message.close();
			this.msgIsLoaded = false;
		} else {
			logger.warn("No file to close!");
		}
	}
	
	/**
	 * Load .msg file
	 * 
	 * @param pathToMsgFile full path to resource
	 * @return MsgDriver
	 */
	public MsgDriver loadMsgFile(String pathToMsgFile) {
		this.pathToMsgFile = pathToMsgFile;
		return this;
	}
	
	/**
	 * Load the message
	 */
	private void load() {
		try {
			this.message = new MAPIMessage(this.pathToMsgFile);
			this.msgIsLoaded = true;
		} catch (IOException e) {
			logger.error("Error while loading MSG file.", e);
		}
	}
	
	/**
	 * initialize resource before test case
	 */
	@Override
	protected void before() throws Throwable {
		load();
	}	
	
	/**
	 * Clean up resource after test case
	 */
	@Override
	protected void after() {
		try {
			this.message.close();
		} catch (IOException e) {
			logger.error("Error while closing MSG file.", e);
		} finally {
			this.message = null;
		}
	}
}