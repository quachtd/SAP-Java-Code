package com.quachtd.sap.pi.javamapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public class Delay5Minutes extends AbstractTransformation {

	@Override
	public void transform(TransformationInput arg0, TransformationOutput arg1) throws StreamTransformationException {
		int minutes = arg0.getInputParameters().getInt("minutes");
		this.exeLogic(arg0.getInputPayload().getInputStream(), arg1.getOutputPayload().getOutputStream(), minutes);
	}
	
	private void exeLogic(InputStream is, OutputStream os, int minutes) throws StreamTransformationException {
		// TODO: logic to transform
		try {
			Thread.sleep(minutes * 60 * 1000);
		} catch (InterruptedException e) {
			getTrace().addWarning(e.getMessage());
		}
		
		try {
			this.pipe(is, os);
		} catch (IOException e) {
			getTrace().addWarning(e.getMessage(), new StreamTransformationException(e.getMessage()));
		}
	}

	private void pipe(InputStream is, OutputStream os) throws IOException {
        byte[] buffer = new byte[is.available()];
        while(is.read(buffer) > -1) {
            os.write(buffer);   
            buffer = new byte[is.available() + 1];
        }
    }

}
