package org.vebqa.vebtal.msg.commands;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msg.MsgStore;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifyText", hintTarget = "<text>")
public class Verifytext extends AbstractCommand {

	public Verifytext(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}

	@Override
	public Response executeImpl(Object driver) {
		MsgDriver msgDriver = (MsgDriver)driver;
		
		Response tResp = new Response();
		
		if (!msgDriver.isLoaded()) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No message loaded!");
			return tResp;
		}
		
		String aBody = "";
		try {
			aBody = msgDriver.getMessage().getHtmlBody();
		} catch (ChunkNotFoundException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No html body found in message: " + e.getMessage());
			return tResp;
		}		
		
		if (!aBody.contains(this.target)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Html body does not contain needle: " + this.target);
			return tResp;
		}
		
		tResp.setCode(Response.PASSED);
		tResp.setMessage("Success: " + this.target + " is part of email body.");
		
		return tResp;
	}

}
