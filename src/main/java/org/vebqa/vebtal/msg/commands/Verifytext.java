package org.vebqa.vebtal.msg.commands;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifyText", hintTarget = "Any text in email body")
public class Verifytext extends AbstractCommand {

	public Verifytext(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}

	@Override
	public Response executeImpl(Object driver) {
		MsgDriver msgDriver = (MsgDriver) driver;

		Response tResp = new Response();

		if (!msgDriver.isLoaded()) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No message loaded!");
			return tResp;
		}

		String expectedTextInbody = this.target;
		String actualTextBody = "";
		
		try {
			actualTextBody = msgDriver.getMessage().getRtfBody();
		} catch (ChunkNotFoundException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No text body found in message: " + e.getMessage());
			return tResp;
		}

		if (!actualTextBody.contains(expectedTextInbody)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Text body [" + actualTextBody + "] does not contain needle [" + expectedTextInbody + "]");
			return tResp;
		}

		tResp.setCode(Response.PASSED);
		tResp.setMessage("Success: " + expectedTextInbody + " is part of email body.");

		return tResp;
	}

}
