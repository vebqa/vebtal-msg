package org.vebqa.vebtal.msg.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifyEmailFrom", hintTarget = "email addres of sender")
public class Verifyemailfrom extends AbstractCommand {

	public Verifyemailfrom(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}

	@Override
	public Response executeImpl(Object driver) {
		MsgDriver msgDriver = (MsgDriver) driver;

		Response tResp = new Response();
		List<String> allMatches = new ArrayList<String>();

		if (!msgDriver.isLoaded()) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No message loaded!");
			return tResp;
		}

		String expectedSenderEmail = this.target;

		try {
			String[] header = msgDriver.getMessage().getHeaders();
			for (String value : header) {
				if (value.startsWith("From:")) {
					Matcher m = Pattern.compile("(?<name>[\\w.]+)\\@(?<domain>\\w+\\.\\w+)(\\.\\w+)?").matcher(value);
					while (m.find()) {
						allMatches.add(m.group());
					}
					String[] fromAddress = new String[allMatches.size()];
					fromAddress = allMatches.toArray(fromAddress);
				}
			}
		} catch (Exception e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No chunk for Sender Addresses found! " + e.getMessage());
			return tResp;
		}

		if (!allMatches.get(0).contains(expectedSenderEmail)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Expected email address of sender: " + expectedSenderEmail	+ ". Found: " + allMatches.get(0));
			return tResp;
		}

		tResp.setCode(Response.PASSED);
		tResp.setMessage("Success: " + expectedSenderEmail + " is verfied as the email address of sender.");

		return tResp;
	}

}
