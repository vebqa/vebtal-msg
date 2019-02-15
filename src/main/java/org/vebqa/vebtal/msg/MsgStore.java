package org.vebqa.vebtal.msg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsgStore {

	public static final Logger logger = LoggerFactory.getLogger(MsgStore.class);
	
	private static final MsgStore store = new MsgStore();
	
	private MsgDriver msgDriver;
	
	public MsgStore() {
		this.msgDriver = new MsgDriver();
	}
	
	public static MsgStore getStore() {
		return store;
	}

	public MsgDriver getDriver() {
		return msgDriver;
	}
	
}
