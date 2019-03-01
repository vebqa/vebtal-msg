package org.vebqa.vebtal.msg.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;

public class VerifyemailtoTest {

	private String actualRecipientAddresses = "Karsten.Doerges@vonessenbank.de; Nithiyaa.Radjindirin@vonessenbank.de; Ramona.Arns@vonessenbank.de; Afsar.Habibi@vonessenbank.de";

	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");

	@Test
	public void verifyIfGivenRecipientAddressIsFound() {

		String expectedRecipientAddress = "Karsten.Doerges@vonessenbank.de";

		Verifyemailto cmd = new Verifyemailto("verifyDisplayTo", expectedRecipientAddress, "");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Success: " + expectedRecipientAddress + " is verfied as the email address of recipient.");

		assertThat(resultCheck, samePropertyValuesAs(result));

	}
	
	@Test
	public void verifyIfGivenRecipientAddressIsNotFound() {

		String expectedRecipientAddress = "nonexistingemailid@vonessenbank.de";

		Verifyemailto cmd = new Verifyemailto("verifyDisplayTo", expectedRecipientAddress, "");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.FAILED);
		resultCheck.setMessage("Expected email address of recipient: " + expectedRecipientAddress + ". Found: " + actualRecipientAddresses);

		assertThat(resultCheck, samePropertyValuesAs(result));

	}

}