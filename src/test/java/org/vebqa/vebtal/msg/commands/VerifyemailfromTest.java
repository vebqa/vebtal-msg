package org.vebqa.vebtal.msg.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;

public class VerifyemailfromTest {

	private String actualSenderAddress = "Pia.Radine@vonessenbank.de";

	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");

	@Test
	public void verifyIfGivenSenderAddressIsFound() {

		String expectedSenderAddress = "Pia.Radine@vonessenbank.de";

		Verifyemailfrom cmd = new Verifyemailfrom("verifyDisplayTo", expectedSenderAddress, "");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Success: " + expectedSenderAddress + " is verfied as the email address of sender.");

		assertThat(resultCheck, samePropertyValuesAs(result));

	}

	@Test
	public void verifyIfGivenSenderAddressIsNotFound() {

		String expectedSenderAddress = "nonexistingemailid@vonessenbank.de";

		Verifyemailfrom cmd = new Verifyemailfrom("verifyDisplayTo", expectedSenderAddress, "");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.FAILED);
		resultCheck.setMessage(
				"Expected email address of sender: " + expectedSenderAddress + ". Found: " + actualSenderAddress);

		assertThat(resultCheck, samePropertyValuesAs(result));

	}

}