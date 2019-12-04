package org.vebqa.vebtal.msg.asserts;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.assertj.core.api.AbstractAssert;
import org.vebqa.vebtal.msg.MsgDriver;

public class VerifyMessageAssert extends AbstractAssert<VerifyMessageAssert, MsgDriver> {

	/**
	 * Constructor assertion class, MSG filename is the object we want to make
	 * assertions on.
	 * 
	 * @param anActualMsg a document to test
	 */
	public VerifyMessageAssert(MsgDriver anActualMsg) {
		super(anActualMsg, VerifyMessageAssert.class);
	}

	/**
	 * A fluent entry point to our specific assertion class, use it with static
	 * import.
	 * 
	 * @param anActualMsg Our message to test
	 * @return self
	 */
	public static VerifyMessageAssert assertThat(MsgDriver anActualMsg) {
		return new VerifyMessageAssert(anActualMsg);
	}

	/**
	 * A specific assertion
	 * 
	 * @param aSubject an expected subject
	 * @return self
	 */
	public VerifyMessageAssert hasSubject(String aSubject) {
		// check that we really have a message loaded.
		isNotNull();

		// do the testing...
		try {
			if (!actual.getMessage().getSubject().contentEquals(aSubject)) {
				failWithMessage("Expected subject is <%s> but was <%s>", aSubject, actual.getMessage().getSubject());
			}
		} catch (ChunkNotFoundException e) {
			failWithMessage("No chunk for subject found!", e);
		}

		return this;
	}

	/**
	 * Find text in Body
	 * 
	 * @param expectedTextInBody expected text in email body
	 * @return self
	 */
	public VerifyMessageAssert hasTextInBody(String expectedTextInBody) {
		// check that we really have a message loaded.
		isNotNull();

		// do the testing...
		try {
			String actualTextBody = actual.getMessage().getRtfBody();
			if (!actualTextBody.contains(expectedTextInBody)) {
				failWithMessage("Expected text <%s> is not found in email body <%s>", expectedTextInBody,
						actualTextBody);
			}
		} catch (ChunkNotFoundException e) {
			failWithMessage("No chunk for HTML body found!", e);
		}

		return this;
	}

	/**
	 * eMail Sender Name assertion
	 * 
	 * @param displayFrom the expected sender display name
	 * @return self
	 */
	public VerifyMessageAssert hasTheGivenNameInDisplayFrom(String displayFrom) {
		// check that we really have a message loaded.
		isNotNull();

		// do the testing...
		try {
			String actualDisplayFrom = actual.getMessage().getDisplayFrom();
			if (!actualDisplayFrom.equals(displayFrom)) {
				failWithMessage("Expected Sender Name <%s> was not the same as <%s>", displayFrom, actualDisplayFrom);
			}
		} catch (ChunkNotFoundException e) {
			failWithMessage("No chunk for Sender information found!", e);
		}

		return this;
	}

	/**
	 * eMail Recipient name assertion
	 * 
	 * @param displayTo the expected recipient display name
	 * @return self
	 */
	public VerifyMessageAssert hasTheGivenNameInDisplayTo(String displayTo) {
		// check that we really have a message loaded.
		isNotNull();

		// do the testing...
		try {
			String actualDisplayTo = actual.getMessage().getDisplayTo();
			if (!actualDisplayTo.contains(displayTo)) {
				failWithMessage("Expected Recipient Name <%s> was not the same as <%s>", displayTo, actualDisplayTo);
			}
		} catch (ChunkNotFoundException e) {
			failWithMessage("No chunk for Recipient Addresses found!", e);
		}

		return this;
	}

	/**
	 * eMail address of sender assertion
	 * 
	 * @param expectedSenderEmail the expected sender email address
	 * @return self
	 */
	public VerifyMessageAssert hasTheGivenSenderEmailAddress(String expectedSenderEmail) {
		// check that we really have a message loaded.
		isNotNull();

		// do the testing...
		try {
			String[] header = actual.getMessage().getHeaders();
			for (String value : header) {
				if (value.startsWith("From:")) {
					List<String> allMatches = new ArrayList<String>();
					Matcher m = Pattern.compile("(?<name>[\\w.]+)\\@(?<domain>\\w+\\.\\w+)(\\.\\w+)?").matcher(value);
					while (m.find()) {
						allMatches.add(m.group());
					}
					String[] fromAddress = new String[allMatches.size()];
					fromAddress = allMatches.toArray(fromAddress);
					if (!allMatches.get(0).contains(expectedSenderEmail)) {
						failWithMessage("Expected Sender email Address <%s> does not exist. Actual address found: <%s>",
								expectedSenderEmail, allMatches);
					}
				}
			}
		} catch (Exception e) {
			failWithMessage("No chunk for Sender Addresses found! ", e);
		}

		return this;
	}

	/**
	 * eMail address in To/CC assertion
	 * 
	 * @param expectedRecipientAddress the expected recipient email address
	 * @return self
	 */
	public VerifyMessageAssert hasTheGivenRecipientAddress(String expectedRecipientAddress) {
		// check that we really have a message loaded.
		isNotNull();

		// do the testing...
		try {
			String actualRecipientAddresses = actual.getMessage().getRecipientEmailAddress().toString();
			if (!actualRecipientAddresses.contains(expectedRecipientAddress)) {
				failWithMessage("Expected Recipient email <%s> was not found in <%s>", expectedRecipientAddress,
						actualRecipientAddresses);
			}
		} catch (Exception e) {
			failWithMessage("No chunk for Recipient Addresses found!", e);
		}

		return this;
	}

	/**
	 * Find text from eMail Body using regular expression
	 * 
	 * @param regEx regular expression to find a text in eMail body
	 * @return self
	 */
	public VerifyMessageAssert findingTextInBodyUsingRegex(String regEx) {
		// check that we really have a message loaded.
		isNotNull();

		// do the testing...
		try {
			String actualTextBody = actual.getMessage().getRtfBody();
			List<String> allMatches = new ArrayList<>();
			Matcher matcher = Pattern.compile(regEx).matcher(actualTextBody);
			while (matcher.find()) {
				allMatches.add(matcher.group());
			}
			if (allMatches.size() == 0) {
				failWithMessage("No matches found following your regular expression!");
			}
			if (allMatches.size() > 1) {
				boolean allTheSame = true;
				for (String s : allMatches) {
					if (!s.equals(allMatches.get(0)))
						allTheSame = false;
				}
				if (allTheSame == false) {
					failWithMessage(allMatches.size()
							+ " matches found following your regular expression. Does not fetch unique results.");
				}
			}
		} catch (Exception e) {
			failWithMessage("No chunk for text body found!", e);
		}

		return this;
	}

	/**
	 * Verify that a message has a given number of attachments.
	 * 
	 * @param aNumber
	 * @return self
	 */
	VerifyMessageAssert hasGivenNumberOfAttachments(int aNumber) {
		// check that we really have a message loaded.
		isNotNull();

		// do the testing...
		AttachmentChunks[] attachments = actual.getMessage().getAttachmentFiles();
		if (aNumber != attachments.length) {
			failWithMessage("Expected number of attachments is: " + aNumber + " but is: " + attachments.length);
		}
		return this;
	}
}