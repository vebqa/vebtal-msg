package org.vebqa.vebtal.msg.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "storeText", hintTarget = "regex=", hintValue = "<buffer>")
public class Storetext extends AbstractCommand {

	public Storetext(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ACCESSOR;
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
		
		String aBody = "";
		try {
			aBody = msgDriver.getMessage().getRtfBody();
		} catch (ChunkNotFoundException e) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No text body found in message: " + e.getMessage());
			return tResp;
		}
		
		List<String> allMatches = new ArrayList<>();
		Matcher matcher = Pattern.compile(this.target).matcher(aBody);
		
		while (matcher.find()) {
			allMatches.add(matcher.group());
		}
		
		if (allMatches.size() == 0) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No matches found following your regular expression!");
			return tResp;
		}

		if (allMatches.size() > 1) {
			boolean allTheSame = true;
			for (String s : allMatches) {
				if(!s.equals(allMatches.get(0)))
					allTheSame = false;
			}
			if(allTheSame == false) {
				tResp.setCode(Response.FAILED);
				tResp.setMessage(allMatches.size() + " matches found following your regular expression. Does not fetch unique results.");
				return tResp;
			}
		}
		
		tResp.setCode(Response.PASSED);
		tResp.setStoredKey(this.value);
		tResp.setStoredValue(allMatches.get(0));
		tResp.setMessage("Stored match: " + allMatches.get(0));
		
		return tResp;
	}

}
