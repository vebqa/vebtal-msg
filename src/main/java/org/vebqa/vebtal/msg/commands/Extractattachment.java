package org.vebqa.vebtal.msg.commands;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hsmf.datatypes.AttachmentChunks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vebqa.vebtal.annotations.Keyword;
import org.vebqa.vebtal.command.AbstractCommand;
import org.vebqa.vebtal.model.CommandType;
import org.vebqa.vebtal.model.Response;
import org.vebqa.vebtal.msg.MsgDriver;
import org.vebqa.vebtal.msgrestserver.MsgTestAdaptionPlugin;

@Keyword(module = MsgTestAdaptionPlugin.ID, command = "extractAttachment", hintTarget = "Number of attachments", hintValue = "Path to extracted file")
public class Extractattachment extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(Extractattachment.class);
	
	public Extractattachment(String aCommand, String aTarget, String aValue) {
		super(aCommand, aTarget, aValue);
		this.type = CommandType.ASSERTION;
	}

	@Override
	public Response executeImpl(Object driver) {
		MsgDriver msgDriver = (MsgDriver) driver;

		Response tResp = new Response();

		if (!msgDriver.isLoaded()) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("No message loaded!");
			return tResp;
		}

		AttachmentChunks attachments[] = msgDriver.getMessage().getAttachmentFiles();
		int number = Integer.parseInt(this.target);
		if (attachments.length == 0) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("EMail has no attachment!");
			return tResp;
		}
		
		number=number-1;
		if (number > attachments.length) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Email has no attachment nr.: " + number);
			return tResp;
		}
		
		String destination = this.value;
		if (destination == null || destination.length() == 0) {
			tResp.setCode(Response.FAILED);
			tResp.setMessage("Destination has to be set in value.");
			return tResp;
		}
		
		// 
		for (AttachmentChunks entry : attachments) {
            // extract attachment
            ByteArrayInputStream fileIn = new ByteArrayInputStream(entry.getAttachData().getValue());
            File f = new File(destination, entry.getAttachLongFileName().toString());
            
            OutputStream fileOut = null;
            try {
                fileOut = new FileOutputStream(f);
                byte[] buffer = new byte[2048];
                int bNum = fileIn.read(buffer);
                while(bNum > 0) {
                    fileOut.write(buffer);
                    bNum = fileIn.read(buffer);
                }
                tResp.setCode(Response.PASSED);
                tResp.setMessage("Attachment saved to: " + f.getAbsoluteFile()); 
            } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            finally {
                try {
                    if(fileIn != null) {
                        fileIn.close();
                    }
                } catch (IOException e) {
					e.printStackTrace();
				}
                finally {
                    if(fileOut != null) {
                        try {
							fileOut.close();
						} catch (IOException e) {
							logger.error("Cannot close file!", e);
						}
                    }
                }
            }
		}
		
		tResp.setCode(Response.PASSED);
		return tResp;
	}

}
