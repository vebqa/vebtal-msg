package org.vebqa.vebtal.msg.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;

public class VerifysubjectTest {

	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");

	@Test
	public void verifySubject() {
		
		// create command to test
		Verifysubject cmd = new Verifysubject("verifySubject", "This is a test mail", "");
		Response result = cmd.executeImpl(mut);
		
		// create a result object
		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Success: The given subject matches.");
		
		// check
		assertThat(resultCheck, samePropertyValuesAs(result));
		
	}
}