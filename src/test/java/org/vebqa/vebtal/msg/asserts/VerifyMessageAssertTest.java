package org.vebqa.vebtal.msg.asserts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.msg.MessageResource;

public class VerifyMessageAssertTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Rule
	public final MessageResource sampleReceivedMail = new MessageResource().loadResource("./src/test/java/resource/Received Test Mail.msg");
	public final MessageResource mut = new MessageResource().loadResource("./src/test/java/resource/Ihre TOP-Kreditanfrage Nr. 54427270 Theodor Ebnerus.msg");
	
	@Test
	public void checkThatMessageHasSpecificSubject() {
		VerifyMessageAssert.assertThat(mut).hasSubject("Ihre TOP-Kreditanfrage Nr. 54427270, Theodor Ebnerus");
	}
	
	@Test
	public void checkThatMessageDoesNotHaveSpecificSubject() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected subject is");
		
		VerifyMessageAssert.assertThat(mut).hasSubject("This is not a test subject.");
	}
	
	@Test
	public void checkIfMessageContainsText() {
		VerifyMessageAssert.assertThat(mut).hasTextInBody("54427270");
	}
	
	@Test
	public void checkIfMessageDoesNotContainText() {
		exception.expect(AssertionError.class);
		exception.expectMessage("is not found in email body");
		
		VerifyMessageAssert.assertThat(mut).hasTextInBody("Wrong Text");
	}
	
	@Test
	public void checkIfGivenNameIsInDisplayFrom() {		
		VerifyMessageAssert.assertThat(mut).hasTheGivenNameInDisplayFrom("Consors Finanz");
	}
	
	@Test
	public void checkIfGivenNameIsNotInDisplayFrom() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected Sender Name");
		
		VerifyMessageAssert.assertThat(mut).hasTheGivenNameInDisplayFrom("Not Consors Finanz");
	}
	
	@Test
	public void checkIfGivenNameIsInDisplayTo() {		
		VerifyMessageAssert.assertThat(mut).hasTheGivenNameInDisplayTo("Huber, Manuel (EA)");
	}
	
	@Test
	public void checkIfGivenNameIsNotInDisplayTo() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected Recipient Name");
		
		VerifyMessageAssert.assertThat(mut).hasTheGivenNameInDisplayTo("Not Huber, Manuel (EA)");
	}
	
	@Test
	public void checkIfSenderEmailAddressIsFound() {		
		VerifyMessageAssert.assertThat(mut).hasTheGivenSenderEmailAddress("noreply@consorsfinanz.de");
	}
	
	@Test
	public void checkIfSenderEmailAddressIsNotFound() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected Sender email");
		
		VerifyMessageAssert.assertThat(mut).hasTheGivenSenderEmailAddress("tester@vonessenbank.de");
	}
	
	@Test
	public void checkIfGivenRecipientAddressIsFound() {		
		VerifyMessageAssert.assertThat(mut).hasTheGivenRecipientAddress("Manuel.Huber@consorsfinanz.de");
	}
	
	@Test
	public void checkIfGivenRecipientAddressIsNotFound() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected Recipient email");
		
		VerifyMessageAssert.assertThat(mut).hasTheGivenRecipientAddress("Nithiyaa.Radjindirin@vonessenbank.de");
	}
	
	@Test
	public void findUniqueTextFromEmailBody() {		
		VerifyMessageAssert.assertThat(mut).findingTextInBodyUsingRegex("(http|ftp|https)://(sit1+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
	}
	
	@Test
	public void findMultipleTextsFromEmailBody() {
		exception.expect(AssertionError.class);
		exception.expectMessage("matches found following your regular expression");
		
		VerifyMessageAssert.assertThat(mut).findingTextInBodyUsingRegex("(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?");
	}
	
	@Test
	public void textNotFoundFromEmailBody() {
		exception.expect(AssertionError.class);
		exception.expectMessage("No matches found following your regular expression");
		
		VerifyMessageAssert.assertThat(mut).findingTextInBodyUsingRegex("(http|ftp|https)://(notfound+(?:(?:\\\\.[\\\\w_-]+)+))([\\\\w.,@?^=%&:/~+#-]*[\\\\w@?^=%&/~+#-])?");
	}

}
