package org.vebqa.vebtal.msgrestserver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.AbstractTestAdaptionResource;
import org.vebqa.vebtal.GuiManager;
import org.vebqa.vebtal.TestAdaptionResource;
import org.vebqa.vebtal.model.Command;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgStore;
import org.vebqa.vebtal.sut.SutStatus;

public class MsgResource extends AbstractTestAdaptionResource implements TestAdaptionResource {

	private static final Logger logger = LoggerFactory.getLogger(MsgResource.class);

	public MsgResource() {
	}
	
	public Response execute(Command cmd) {
		
		MsgTestAdaptionPlugin.setDisableUserActions(true);
		
		Response result = new Response();
		boolean hasException = false;
		String failedReason = null;
		
		try {
			Class<?> cmdClass = Class.forName("org.vebqa.vebtal.msg.commands." + getCommandClassName(cmd));
			Constructor<?> cons = cmdClass.getConstructor(String.class, String.class, String.class);
			Object cmdObj = cons.newInstance(cmd.getCommand(), cmd.getTarget(), cmd.getValue());
			
			// get type
			Method mType = cmdClass.getMethod("getType");
			CommandType cmdType = (CommandType)mType.invoke(cmdObj);
			MsgTestAdaptionPlugin.addCommandToList(cmd, cmdType);
			
			// execute
			Method m = cmdClass.getDeclaredMethod("executeImpl", Object.class);
			
			setStart();
			result = (Response) m.invoke(cmdObj, MsgStore.getStore().getDriver());
			setFinished();
			
		} catch (ClassNotFoundException e) {
			logger.error("Command implementation class not found!", e);
			hasException=true;
			failedReason = "Command implementation class not found: " + e.getMessage(); 
		} catch (NoSuchMethodException e) {
			logger.error("Execution method in command implementation class not found!", e);
			hasException=true;
			failedReason = "Execution method in command implementation class not found: " + e.getMessage();
		} catch (SecurityException e) {
			logger.error("Security exception", e);
			hasException=true;
			failedReason = "Security exception: " + e.getMessage();
		} catch (InstantiationException e) {
			logger.error("Cannot instantiate command implementation class!", e);
			hasException=true;
			failedReason = "Cannot instantiate command implementation class: " + e.getMessage();
		} catch (IllegalAccessException e) {
			logger.error("Cannot access implementation class!", e);
			hasException=true;
			failedReason = "Cannot access implementation class: " + e.getMessage();
		} catch (IllegalArgumentException e) {
			logger.error("Wrong arguments!", e);
			hasException=true;
			failedReason = "Wrong arguments: " + e.getMessage();
		} catch (InvocationTargetException e) {
			logger.error("Error while invoking class!", e);
			hasException=true;
			failedReason = "Error while invoking class: " + e.getMessage();
		} catch (Exception e) {
			logger.error("an exception occurred!", e);
			hasException=true;
			failedReason = "An error occured: " + e.getMessage();
		}

		if (hasException) {
			result.setCode(Response.FAILED);
			result.setMessage(failedReason);
			MsgTestAdaptionPlugin.setDisableUserActions(true);
			return result;
		}
		if (result.getCode().equals(Response.PASSED)) {
			MsgTestAdaptionPlugin.setLatestResult(true, result.getMessage());
		} else {
			MsgTestAdaptionPlugin.setLatestResult(false, result.getMessage());
		}
		
		if (MsgStore.getStore().getDriver().isLoaded()) {
			GuiManager.getinstance().setTabStatus(MsgTestAdaptionPlugin.ID, SutStatus.CONNECTED);
		} else {
			GuiManager.getinstance().setTabStatus(MsgTestAdaptionPlugin.ID, SutStatus.DISCONNECTED);
		}		
		
		MsgTestAdaptionPlugin.setDisableUserActions(true);
		return result;
	}

}
