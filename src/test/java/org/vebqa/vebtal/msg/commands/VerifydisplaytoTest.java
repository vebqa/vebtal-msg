package org.vebqa.vebtal.msg.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;

public class VerifydisplaytoTest {

	private String actualDisplayTo = "Dörges, Karsten; Radjindirin, Nithiyaa";

	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");

	@Test
	public void verifyIfGivenNameIsInDisplayTo() {

		String expectedDisplayTo = "Dörges, Karsten";

		Verifydisplayto cmd = new Verifydisplayto("verifyDisplayTo", expectedDisplayTo, "");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Success: " + expectedDisplayTo + " is verfied as the display name of recipient.");

		assertThat(resultCheck, samePropertyValuesAs(result));

	}

	@Test
	public void verifyIfGivenNameIsNotInDisplayTo() {

		String expectedDisplayTo = "Wrong Recipient";

		Verifydisplayto cmd = new Verifydisplayto("verifyDisplayTo", expectedDisplayTo, "");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.FAILED);
		resultCheck.setMessage(
				"Expected display name of Recipient: " + expectedDisplayTo + ". Found Recipients: " + actualDisplayTo);

		assertThat(resultCheck, samePropertyValuesAs(result));

	}

}