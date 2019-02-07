package org.vebqa.vebtal.msg.commands;

import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "storeText", hintTarget = "regex=", hintValue = "<buffer>")
public class Storetext extends AbstractCommand {

	public Storetext(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACCESSOR;
	}

	@Override
	public Response executeImpl(Object driver) {
		Response tResp = new Response();
		tResp.setCode(Response.PASSED);
		tResp.setMessage("Sucessfully loaded message file.");
		
		return tResp;
	}

}
