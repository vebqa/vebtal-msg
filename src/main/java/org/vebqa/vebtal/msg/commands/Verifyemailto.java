package org.vebqa.vebtal.msg.commands;

import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifyEmailTo", hintTarget = "email addres of sender")
public class Verifyemailto extends AbstractCommand {

	public Verifyemailto(String aCommand, String aTarget, String aValue) {
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

		String expectedEmailToAddress = this.target;
		String actualEmailToAddress = "";

		try {
			actualEmailToAddress = msgDriver.getMessage().getRecipientEmailAddress().toString();
		} catch (Exception e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Sender email address not found: " + e.getMessage());
			return tResp;
		}

		if (!actualEmailToAddress.contains(expectedEmailToAddress)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Expected email address of sender: " + expectedEmailToAddress + ". Found: "
					+ actualEmailToAddress);
			return tResp;
		}

		tResp.setCode(Response.PASSED);
		tResp.setMessage("Success: " + expectedEmailToAddress + " is verfied as the email address of sender.");

		return tResp;
	}

}
