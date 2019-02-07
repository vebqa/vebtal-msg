package org.vebqa.vebtal.msg.commands;

import java.io.IOException;

import org.apache.poi.hsmf.MAPIMessage;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgStore;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "open", hintTarget = "path/to/message.msg")
public class Open extends AbstractCommand {

	public Open(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACTION;
	}

	@Override
	public Response executeImpl(Object driver) {

		Response tResp = new Response();
		
		if (!this.target.toLowerCase().endsWith(".msg")) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("file suffix has to be .msg!");
			return tResp;
		}
		
		MAPIMessage message;
		try {
			message = new MAPIMessage(this.target);
		} catch (IOException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Could not open message file. " + e.getMessage());
			return tResp;
		}

		MsgStore.getStore().setMessage(message);
		
		tResp.setCode(Response.PASSED);
		tResp.setMessage("Sucessfully loaded message file.");
		
		return tResp;
	}

}
