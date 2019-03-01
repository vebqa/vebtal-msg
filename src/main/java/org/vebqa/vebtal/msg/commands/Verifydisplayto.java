package org.vebqa.vebtal.msg.commands;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifyDisplayTo", hintTarget = "The display name of the recipient")
public class Verifydisplayto extends AbstractCommand {

	public Verifydisplayto(String aCommand, String aTarget, String aValue) {
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

		String expectedDisplayTo = this.target;
		String actualDisplayTo = "";

		try {
			actualDisplayTo = msgDriver.getMessage().getDisplayTo();
		} catch (ChunkNotFoundException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No recipient address found: " + e.getMessage());
			return tResp;
		}

		if (!actualDisplayTo.contains(expectedDisplayTo)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Expected display name of Recipient: " + expectedDisplayTo + ". Found Recipients: " + actualDisplayTo);
			return tResp;
		}

		tResp.setCode(Response.PASSED);
		tResp.setMessage("Success: " + expectedDisplayTo + " is verfied as the display name of recipient.");

		return tResp;
	}

}
