package com.bobwares.databus.common.renderer.pdf;

import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.ResponseObject;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.List;

public interface PDFRendererService {
    /**
     * Generates the PDF document object. Caller is responsible for closing the document when finished with it.
     * /
     * @param configuration report configuration
     * @param headers report header information
     * @param page report data
     * @return the PDF document object
     * @throws IOException if PDF document generation fails
     */
    PDDocument renderAsPDF(ResourceConfiguration configuration, List<PdfHeader> headers, ResponseObject page) throws IOException;
}
