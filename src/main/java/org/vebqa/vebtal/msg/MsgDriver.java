package org.vebqa.vebtal.msg;

import java.io.IOException;

import org.apache.poi.hsmf.MAPIMessage;

/**
 * Outlook message driver
 * @author doerges
 *
 */
public class MsgDriver {

	private MAPIMessage message;
	
	private boolean msgIsLoaded;
	
	public MsgDriver() {
		this.msgIsLoaded = false;
	}
	
	public void setMessage(MAPIMessage aMessage) {
		this.message = aMessage;
		this.msgIsLoaded = true;
	}
	
	public MAPIMessage getMessage() {
		return this.message;
	}	
	
	public boolean isLoaded() {
		return this.msgIsLoaded;
	}
	
	public void close() throws IOException {
		this.message.close();
		this.msgIsLoaded = false;
	}	
}
