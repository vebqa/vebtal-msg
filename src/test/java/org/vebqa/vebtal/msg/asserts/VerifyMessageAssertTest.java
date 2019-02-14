package org.vebqa.vebtal.msg.asserts;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.vebqa.vebtal.msg.MessageResource;

public class VerifyMessageAssertTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Rule
	public final MessageResource mut = new MessageResource().loadResource("./src/test/java/resource/test_01.msg");
	
	@Test
	public void checkThatMessageHasSpecificSubject() {
		VerifyMessageAssert.assertThat(mut).hasSubject("This is a test subject");
	}
	
	@Test
	public void checkThatMessageDoesNotHaveSpecificSubject() {
		exception.expect(AssertionError.class);
		exception.expectMessage("Expected subject is");
		
		VerifyMessageAssert.assertThat(mut).hasSubject("Elephant is not the subject.");
	}
	
}
