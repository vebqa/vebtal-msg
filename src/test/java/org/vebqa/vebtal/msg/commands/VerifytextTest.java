package org.vebqa.vebtal.msg.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;

public class VerifytextTest {

	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");

	@Test
	public void verifyText() {

		String expectedTextInBody = "Etiam sit amet orci eget eros faucibus tincidunt.";

		// create command to test
		Verifytext cmd = new Verifytext("verifyText", expectedTextInBody, "");
		Response result = cmd.executeImpl(mut);

		// create a result object
		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Success: " + expectedTextInBody + " is part of email body.");

		// check
		assertThat(resultCheck, samePropertyValuesAs(result));

	}

	@Test
	public void verifyTextNotExisting() {

		Response resultCheck = new Response();
		String testString = "Non-existing Text";
		String actualTextInBody = "";

		try {
			actualTextInBody = mut.getMessage().getRtfBody();
		} catch (ChunkNotFoundException e) {
			resultCheck.setCode(Response.FAILED);
			resultCheck.setMessage("No text body found in message: " + e.getMessage());
		}

		// create command to test
		Verifytext cmd = new Verifytext("verifyText", testString, "");
		Response result = cmd.executeImpl(mut);

		// create a result object

		resultCheck.setCode(Response.FAILED);
		resultCheck.setMessage("Text body [" + actualTextInBody + "] does not contain needle [Non-existing Text]");

		// check
		assertThat(resultCheck, samePropertyValuesAs(result));

	}

}