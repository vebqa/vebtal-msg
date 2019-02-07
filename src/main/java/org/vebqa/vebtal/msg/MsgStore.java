package org.vebqa.vebtal.msg;

import org.apache.poi.hsmf.MAPIMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgStore {

	public static final Logger logger = LoggerFactory.getLogger(MsgStore.class);
	
	private static final MsgStore store = new MsgStore();
	
	private MAPIMessage message;
	
	public MsgStore() {
		
	}
	
	public static MsgStore getStore() {
		return store;
	}
	
	public void setMessage(MAPIMessage aMessage) {
		this.message = aMessage;
	}
	
	public MAPIMessage getMessage() {
		return this.message;
	}
	
	public boolean isLoaded() {
		if (this.message != null) {
			return true;
		}
		return false;
	}
}
