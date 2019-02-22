package org.vebqa.vebtal.msg.commands;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifySubject", hintTarget = "The email Subject")
public class Verifysubject extends AbstractCommand {

	public Verifysubject(String aCommand, String aTarget, String aValue) {
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
		
		String expectedSubject = this.target;
		String actualSubject = "";
				
		try {
			actualSubject = msgDriver.getMessage().getSubject();
		} catch (ChunkNotFoundException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No subject found in message: " + e.getMessage());
			return tResp;
		}
		
		if (!actualSubject.equals(expectedSubject)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("The Actual Subject [" + actualSubject + "] does not match with the expected subject [" + expectedSubject + "]");
			return tResp;
		}
		
		tResp.setCode(Response.PASSED);
		tResp.setMessage("Success: The given subject matches.");
		
		return tResp;
	}

}
