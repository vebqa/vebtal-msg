package org.vebqa.vebtal.msg.commands;

import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;

public class StoretextTest {

	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");
	
	@Test
	public void storeTextNoMatchesFound() {

		String regEx = "(http|ftp|https)://(sit1+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";

		Storetext cmd = new Storetext("storeText", regEx, "textFound");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.FAILED);
		resultCheck.setMessage("No matches found following your regular expression!");

		assertThat(resultCheck, samePropertyValuesAs(result));

	}
	
	@Test
	public void storeTextSingleMatch() {

		String regEx = "Curabitur(?<sub>.*?)\\.";
		String expectedText = "Curabitur ullamcorper ultricies nisi.";

		Storetext cmd = new Storetext("storeText", regEx, "textFound");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.PASSED);
		resultCheck.setMessage("Stored match: " + expectedText);
		resultCheck.setStoredKey("textFound");
		resultCheck.setStoredValue(expectedText);

		assertThat(resultCheck, samePropertyValuesAs(result));

	}
	
	@Test
	public void storeTextMultipleMatchingTextFound() {

		String regEx = "Nullam(?<sub>.*?)\\.";

		Storetext cmd = new Storetext("storeText", regEx, "textFound");
		Response result = cmd.executeImpl(mut);

		Response resultCheck = new Response();
		resultCheck.setCode(Response.FAILED);
		resultCheck.setMessage("3 matches found following your regular expression. Does not fetch unique results.");

		assertThat(resultCheck, samePropertyValuesAs(result));

	}

}