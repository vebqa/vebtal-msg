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
	
	private boolean isLoaded;
	
	public MsgDriver() {
		// TODO Auto-generated constructor stub
	}
	
	public void setMessage(MAPIMessage aMessage) {
		this.message = aMessage;
		this.isLoaded = true;
	}
	
	public MAPIMessage getMessage() {
		return this.message;
	}	
	
	public boolean isLoaded() {
		return this.isLoaded;
	}
	
	public void close() throws IOException {
		this.message.close();
		this.isLoaded = false;
	}	
}
