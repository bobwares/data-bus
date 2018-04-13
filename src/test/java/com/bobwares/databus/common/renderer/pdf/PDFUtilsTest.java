package com.bobwares.databus.common.renderer.pdf;

import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PDFUtilsTest {

    @Test
    public void testWrapText1() throws Exception {
        String example = "This is an example of a String that needs to be wrapped on 03/02/2015 at 11:33:59 AM";
        List<String> lines = PDFUtils.wrapText(example, PDType1Font.HELVETICA, 12, 100);
        assertEquals(6, lines.size());
        assertTrue(Arrays.equals(new String[]
            {"This is an","example of a","String that needs","to be wrapped on", "03/02/2015 at", "11:33:59 AM"},
            lines.toArray()));
    }

    @Test
    public void testWrapText2() throws Exception {
        String example = "This example has areallyreallylongword";
        List<String> lines = PDFUtils.wrapText(example, PDType1Font.HELVETICA, 12, 100);
        assertEquals(3, lines.size());
        assertTrue(Arrays.equals(new String[]
            {"This example has", "areallyreallylongw","ord"},
            lines.toArray()));
    }

    @Test
    public void testWrapText3() throws Exception {
        String example = "Areallyreallylongword for this example";
        List<String> lines = PDFUtils.wrapText(example, PDType1Font.HELVETICA, 12, 100);
        assertEquals(3, lines.size());
        assertTrue(Arrays.equals(new String[]
            {"Areallyreallylongw", "ord for this","example"},
            lines.toArray()));
    }

    @Test
    public void testWrapText4() throws IOException {
        String example = "CBCGP  - Tire & Wheel Premium Reimbursement";
        List<String> lines = PDFUtils.wrapText(example, PDType1Font.HELVETICA, 12, 200);
        assertEquals(2, lines.size());
        assertTrue(Arrays.equals(new String[]
                {"CBCGP  - Tire & Wheel Premium", "Reimbursement"},
            lines.toArray()));
    }

    @Test
    public void testSplitText1() throws IOException {
        String example = "areallyreallyreallylongword";
        List<String> lines = PDFUtils.splitText(example, PDType1Font.HELVETICA, 12, 100);
        assertEquals(2, lines.size());
        assertTrue(Arrays.equals(new String[]
                {"areallyreallyreallyl", "ongword"},
            lines.toArray()));
    }

    @Test
    public void testSplitText2() throws IOException {
        String example = "TERRY@DAVIDSTANLEYAUTOGROUP.COM";
        List<String> lines = PDFUtils.splitText(example, PDType1Font.HELVETICA, 12, 200);
        assertEquals(2, lines.size());
        assertTrue(Arrays.equals(new String[]
                {"TERRY@DAVIDSTANLEYAUTOGR", "OUP.COM"},
            lines.toArray()));
    }

    @Test
    public void testEncodeDatesAndTime() {
        String text = "This needs to be encoded on 03/02/2015 at 1:33 AM, 12:59:59";
        String expected = "This needs to be encoded on 03SLASH02SLASH2015 at 1COLON33 AM, 12COLON59COLON59";
        String actual = PDFUtils.encodeDatesAndTimes(text);
        assertEquals(expected, actual);

        assertEquals("12COLON59", PDFUtils.encodeDatesAndTimes("12:59"));
        assertEquals("12COLON59COLON59", PDFUtils.encodeDatesAndTimes("12:59:59"));
        assertEquals("12SLASH31SLASH2015", PDFUtils.encodeDatesAndTimes("12/31/2015"));
        assertEquals("1SLASH3SLASH2015", PDFUtils.encodeDatesAndTimes("1/3/2015"));
    }

    @Test
    public void testDecodeDatesAndTime() {
        String[] elems = {"This ","needs ","to ","be ","decoded ","on ","03SLASH02SLASH2015 ","at ","1COLON33 ","AM ", "12COLON59COLON59"};
        String[] expected = {"This ","needs ","to ","be ","decoded ","on ","03/02/2015 ","at ","1:33 ","AM ","12:59:59"};
        String[] actual = PDFUtils.decodeDatesAndTimes(elems);
        assertTrue(Arrays.equals(expected, actual));

        assertTrue(Arrays.equals(new String[]{"12:59"}, PDFUtils.decodeDatesAndTimes(new String[]{"12COLON59"})));
        assertTrue(Arrays.equals(new String[]{"12:59:59"}, PDFUtils.decodeDatesAndTimes(new String[]{"12COLON59COLON59"})));
        assertTrue(Arrays.equals(new String[]{"12/31/2015"}, PDFUtils.decodeDatesAndTimes(new String[]{"12SLASH31SLASH2015"})));
        assertTrue(Arrays.equals(new String[]{"1/3/2015"}, PDFUtils.decodeDatesAndTimes(new String[]{"1SLASH3SLASH2015"})));
    }
}