package org.vebqa.vebtal.msg.commands;

import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "close")
public class Close extends AbstractCommand {

	public Close(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACTION;
	}

	@Override
	public Response executeImpl(Object driver) {
		Response tResp = new Response();
		tResp.setCode(Response.PASSED);
		tResp.setMessage("Sucessfully closed message file.");
		
		return tResp;
	}

}