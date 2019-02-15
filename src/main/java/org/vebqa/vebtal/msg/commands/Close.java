package org.vebqa.vebtal.msg.commands;

import java.io.IOException;

import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "close")
public class Close extends AbstractCommand {

	public Close(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACTION;
	}

	@Override
	public Response executeImpl(Object driver) {
		
		MsgDriver msgDriver = (MsgDriver)driver;
		
		Response tResp = new Response();
		
		if (!msgDriver.isLoaded()) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No loaded message.");
			return tResp;
		}
		
		try {
			msgDriver.close();
		} catch (IOException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Could not close message: " + e.getMessage());
			return tResp;
		}
		
		tResp.setCode(Response.PASSED);
		tResp.setMessage("Sucessfully closed message file.");
		
		return tResp;
	}

}
