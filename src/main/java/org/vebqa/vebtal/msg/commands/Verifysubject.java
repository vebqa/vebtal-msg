package org.vebqa.vebtal.msg.commands;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgStore;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifySubject", hintTarget = "<text>")
public class Verifysubject extends AbstractCommand {

	public Verifysubject(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACTION;
	}

	@Override
	public Response executeImpl(Object driver) {
		Response tResp = new Response();
		
		if (!MsgStore.getStore().isLoaded()) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No message loaded!");
			return tResp;
		}
		
		String aSubject = "";
		try {
			aSubject = MsgStore.getStore().getMessage().getSubject();
		} catch (ChunkNotFoundException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No subject found in message: " + e.getMessage());
			return tResp;
		}
		
		if (!aSubject.contains(this.target)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Subject [" + aSubject + "] does not contain expected pattern: " + this.target);
			return tResp;
		}
		
		tResp.setCode(Response.PASSED);
		tResp.setMessage("Sucess: Found needle in haystack.");
		
		return tResp;
	}

}
