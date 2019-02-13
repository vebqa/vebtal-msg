package org.vebqa.vebtal.msg.commands;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgStore;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "verifyDisplayBCC", hintTarget = "The email address in BCC")
public class Verifydisplaybcc extends AbstractCommand {

	public Verifydisplaybcc(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}
		
	@Override
	public Response executeImpl(Object driver) {
		Response tResp = new Response();
		
		if (!MsgStore.getStore().isLoaded()) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No message loaded!");
			return tResp;
		}
		
		String aBody = "";
		try {
			aBody = MsgStore.getStore().getMessage().getDisplayBCC();
		}
		catch (ChunkNotFoundException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No email address found in BCC: " + e.getMessage());
			return tResp;
		}		
		
		if (!aBody.contains(this.target)) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("eMail does not contain the given address in BCC: " + this.target);
			return tResp;
		}
		
		tResp.setCode(Response.PASSED);
		tResp.setMessage("Success: " + this.target + " is verfied as the address in BCC.");
		
		return tResp;
	}

}
