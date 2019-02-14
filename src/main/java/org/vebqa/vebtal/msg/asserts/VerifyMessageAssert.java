package org.vebqa.vebtal.msg.asserts;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import org.assertj.core.api.AbstractAssert;
import org.vebqa.vebtal.msg.MessageResource;

public class VerifyMessageAssert extends AbstractAssert<VerifyMessageAssert, MessageResource>{

	/**
	 * Constructor assertion class, MSG filename is the object we want to make assertions on.
	 * @param	aMsgToTest a document to test
	 */
	public VerifyMessageAssert(MessageResource anActualMsg) {
		super(anActualMsg, VerifyMessageAssert.class);
	}	
	
    /**
     * A fluent entry point to our specific assertion class, use it with static import. 
     * @param	aMsgToTest	Our message to test
     * @return	self
     */
    public static VerifyMessageAssert assertThat(MessageResource anActualMsg) {
        return new VerifyMessageAssert(anActualMsg);
    }
    
    /**
     * A specific assertion
     * @param	pages	pages to expect
     * @return	self
     */
    public VerifyMessageAssert hasSubject(String aSubject) {
    	// check that we really have a message loaded.
    	isNotNull();

    	// do the testing...
		try {
			if (!actual.getDocument().getSubject().contentEquals(aSubject)) {
				failWithMessage("Expected subject is <%s> but was <%s>", aSubject, actual.getDocument().getSubject());
			}
		} catch (ChunkNotFoundException e) {
			failWithMessage("No chunk for subject found!", e);
		}
		
		return this;
    }    
}
