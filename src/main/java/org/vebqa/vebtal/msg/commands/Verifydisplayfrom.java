package org.vebqa.vebtal.msg.commands;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifyDisplayFrom", hintTarget = "The display name of the sender")
public class Verifydisplayfrom extends AbstractCommand {

	public Verifydisplayfrom(String aCommand, String aTarget, String aValue) {
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

		String expectedDisplayFrom = this.target;
		String actualDisplayFrom = "";

		try {
			actualDisplayFrom = msgDriver.getMessage().getDisplayFrom();
		} catch (ChunkNotFoundException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No display names for sender found: " + e.getMessage());
			return tResp;
		}

		if (!actualDisplayFrom.equals(expectedDisplayFrom)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Expected display name of sender: " + expectedDisplayFrom + ". Found: " + actualDisplayFrom);
			return tResp;
		}

		tResp.setCode(Response.PASSED);
		tResp.setMessage("Success: " + expectedDisplayFrom + " is verfied as the display name of sender.");

		return tResp;
	}

}
