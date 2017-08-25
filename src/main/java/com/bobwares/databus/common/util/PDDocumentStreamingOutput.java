package com.bobwares.databus.common.util;


import com.bobwares.core.util.stream.StreamingOutput;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.io.OutputStream;

public class PDDocumentStreamingOutput implements StreamingOutput {

    private PDDocument document;

    public PDDocumentStreamingOutput(PDDocument document) {
        this.document = document;
    }

    @Override
    public void write(OutputStream os) throws IOException {
        if (document != null) {
            try {
                document.save(os);
            }
            finally {
                document.close();
            }
        }
    }

}