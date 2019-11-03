package org.vebqa.vebtal.msg.commands;

import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifyNumberAttachments", hintTarget = "Number of attachments")
public class Verifynumberattachments extends AbstractCommand {

	public Verifynumberattachments(String aCommand, String aTarget, String aValue) {
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

		AttachmentChunks attachments[] = msgDriver.getMessage().getAttachmentFiles();
		int expectedNumber = Integer.parseInt(this.target);
		if (attachments.length != expectedNumber) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Expected number is: " + expectedNumber + " but was: " + attachments.length);
			return tResp;
		}
		
		tResp.setCode(Response.PASSED);
		return tResp;
	}

}
