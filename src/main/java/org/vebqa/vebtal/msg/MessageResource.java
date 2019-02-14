package org.vebqa.vebtal.msg;

import java.io.IOException;

import org.apache.poi.hsmf.MAPIMessage;
import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageResource extends ExternalResource {
	
	private static final Logger logger = LoggerFactory.getLogger(MessageResource.class);
	
	private MAPIMessage message;
	
	private String pathToResource;
	
	public MessageResource() {
		this.pathToResource = null;
	}
	
	/**
	 * Name the resource.
	 * 
	 * @param aPathToResource full path to resource
	 * @return MessageResource
	 */
	public MessageResource loadResource(String aPathToResource) {
		this.pathToResource = aPathToResource;
		
		return this;
	}

	/**
	 * initialize resource before testcase.
	 */
	@Override
	protected void before() throws Throwable {
		load();
	}	
	
	/**
	 * Clean up resource after testcase.
	 */
	@Override
	protected void after() {
		try {
			this.message.close();
		} catch (IOException e) {
			logger.error("Error while closing resource.", e);
		} finally {
			this.message = null;
		}
	}	
	
	/**
	 * Load the message.
	 */
	private void load() {
		try {
			this.message = new MAPIMessage(this.pathToResource);
		} catch (IOException e) {
			logger.error("Error while loading resource.", e);
		}
	}
	
	/**
	 * Returns the message, lazy loading if not already loaded by @before in JUnit
	 * @return MAPIMessage
	 */
	public MAPIMessage getDocument() {
		if (this.message == null) {
			load();
		}
		return this.message;
	}
}
