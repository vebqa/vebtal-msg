package org.vebqa.vebtal.msg.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;

public class VerifydisplayfromTest {

	private String actualDisplayFrom = "Radine, Pia";

	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");

	@Test
	public void verifyIfGivenNameIsInDisplayFrom() {

		String expectedDisplayFrom = "Radine, Pia";

		Verifydisplayfrom cmd = new Verifydisplayfrom("verifyDisplayFrom", expectedDisplayFrom, "");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Success: " + expectedDisplayFrom + " is verfied as the display name of sender.");

		assertThat(resultCheck, samePropertyValuesAs(result));

	}

	@Test
	public void verifyIfGivenNameIsNotInDisplayFrom() {

		String expectedDisplayFrom = "Non-Existing Name";

		Verifydisplayfrom cmd = new Verifydisplayfrom("verifyDisplayFrom", expectedDisplayFrom, "");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.FAILED);
		resultCheck.setMessage(
				"Expected display name of sender: " + expectedDisplayFrom + ". Found: " + actualDisplayFrom);

		assertThat(resultCheck, samePropertyValuesAs(result));

	}

}