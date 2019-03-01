package org.vebqa.vebtal.msg.asserts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.msg.MsgDriver;

public class VerifyMessageAssertTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Rule
	public final MsgDriver mut = new MsgDriver().loadMsgFile("./src/test/java/resource/Received Test Mail.msg");
	
	@Test
	public void checkThatMessageHasSpecificSubject() {
		VerifyMessageAssert.assertThat(mut).hasSubject("This is a test mail");
	}
	
	@Test
	public void checkThatMessageDoesNotHaveSpecificSubject() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected subject is");
		
		VerifyMessageAssert.assertThat(mut).hasSubject("This is not a test mail");
	}
	
	@Test
	public void checkIfMessageContainsText() {
		VerifyMessageAssert.assertThat(mut).hasTextInBody("Etiam sit amet orci eget eros faucibus tincidunt.");
	}
	
	@Test
	public void checkIfMessageDoesNotContainText() {
		exception.expect(AssertionError.class);
		exception.expectMessage("is not found in email body");
		
		VerifyMessageAssert.assertThat(mut).hasTextInBody("Wrong Text");
	}
	
	@Test
	public void checkIfGivenNameIsInDisplayFrom() {		
		VerifyMessageAssert.assertThat(mut).hasTheGivenNameInDisplayFrom("Radine, Pia");
	}
	
	@Test
	public void checkIfGivenNameIsNotInDisplayFrom() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected Sender Name");
		
		VerifyMessageAssert.assertThat(mut).hasTheGivenNameInDisplayFrom("Wrong Sender");
	}
	
	@Test
	public void checkIfGivenNameIsInDisplayTo() {		
		VerifyMessageAssert.assertThat(mut).hasTheGivenNameInDisplayTo("Dörges, Karsten");
	}
	
	@Test
	public void checkIfGivenNameIsNotInDisplayTo() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected Recipient Name");
		
		VerifyMessageAssert.assertThat(mut).hasTheGivenNameInDisplayTo("Wrong Recipient");
	}
	
	@Test
	public void checkIfSenderEmailAddressIsFound() {		
		VerifyMessageAssert.assertThat(mut).hasTheGivenSenderEmailAddress("Pia.Radine@vonessenbank.de");
	}
	
	@Test
	public void checkIfSenderEmailAddressIsNotFound() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected Sender email Address");
		
		VerifyMessageAssert.assertThat(mut).hasTheGivenSenderEmailAddress("WrongSender@test.de");
	}
	
	@Test
	public void checkIfGivenRecipientAddressIsFound() {		
		VerifyMessageAssert.assertThat(mut).hasTheGivenRecipientAddress("Karsten.Doerges@vonessenbank.de");
	}
	
	@Test
	public void checkIfGivenRecipientAddressIsNotFound() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected Recipient email");
		
		VerifyMessageAssert.assertThat(mut).hasTheGivenRecipientAddress("WrongRecipient@test.de");
	}
	
	@Test
	public void findUniqueTextFromEmailBody() {		
		VerifyMessageAssert.assertThat(mut).findingTextInBodyUsingRegex("Curabitur(?<sub>.*?)\\.");
	}
	
	@Test
	public void findMultipleTextsFromEmailBody() {
		exception.expect(AssertionError.class);
		exception.expectMessage("matches found following your regular expression");
		
		VerifyMessageAssert.assertThat(mut).findingTextInBodyUsingRegex("Nullam(?<sub>.*?)\\.");
	}
	
	@Test
	public void textNotFoundFromEmailBody() {
		exception.expect(AssertionError.class);
		exception.expectMessage("No matches found following your regular expression");
		
		VerifyMessageAssert.assertThat(mut).findingTextInBodyUsingRegex("(http|ftp|https)://(notfound+(?:(?:\\\\.[\\\\w_-]+)+))([\\\\w.,@?^=%&:/~+#-]*[\\\\w@?^=%&/~+#-])?");
	}

}
