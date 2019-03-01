package org.vebqa.vebtal.msg.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;

public class CloseTest {

	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");

	@Test
	public void closeMessage() {
		
		// create command to test
		Close cmd = new Close("close", "", "");
		Response result = cmd.executeImpl(mut);

		// create a green result object
		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Sucessfully closed message file.");

		// check
		assertThat(resultCheck, samePropertyValuesAs(result));
		
	}
	
}
