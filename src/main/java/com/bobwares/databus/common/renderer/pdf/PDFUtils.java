package com.bobwares.databus.common.renderer.pdf;

import com.bobwares.databus.common.model.Alignment;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PDFUtils {

    private PDFUtils() {}

    public static void drawString(String text, PDPageContentStream contentStream, float textX, float textY) throws IOException {
        contentStream.beginText();
        contentStream.newLineAtOffset(textX, textY);
        contentStream.showText(text != null ? text : "");
        contentStream.endText();
    }

    public static void drawString(List<String> lines, PDPageContentStream contentStream, PDFont font, float fontSize, float startTextX, float startTextY, float leading, float width, Alignment alignment) throws IOException {
        contentStream.beginText();

        float lastTextX = 0;
        float yOffset = startTextY;

        for (String line : lines) {
            float nextTextX = getTextX(line, font, fontSize, startTextX, width, alignment);
            float xOffset = nextTextX - lastTextX;
            lastTextX = nextTextX;

            // specify next text position, taking into account a change for center and right alignment
            contentStream.newLineAtOffset(xOffset, yOffset);
            contentStream.showText(line);

            yOffset = -leading;
        }

        contentStream.endText();
    }

    private static float getTextX(String text, PDFont font, float fontSize, float startTextX, float width, Alignment alignment) throws IOException {
        float textWidth = getStringWidth(text, font, fontSize);
        float textX = startTextX;
        switch (alignment) {
            case LEFT:
                // nothing to change
                break;
            case CENTER:
                textX += (width - textWidth) / 2;
                break;
            case RIGHT:
                textX += (width - textWidth);
                break;
        }
        return textX;
    }

    public static float getMaxWidth(PDFont font, float fontSize, Collection<String> strings) throws IOException {
        float width = Float.MIN_VALUE;
        for (String s : strings) {
            float strWidth = getStringWidth(s, font, fontSize);
            width = Math.max(width, strWidth);
        }
        return width;
    }

    // if a single word is too wide just split it anywhere, no hyphenation
    public static List<String> splitText(String text, PDFont font, float fontSize, float maxWidth) throws IOException {
        float textWidth = getStringWidth(text, font, fontSize);
        if (textWidth <= maxWidth) {
            return Collections.singletonList(text);
        }
        List<String> chunks = new ArrayList<>();
        char[] chars = text.toCharArray();
        String next = "";
        int i = 0;
        while (i < chars.length) {
            char c = chars[i];
            if (getStringWidth(next + c, font, fontSize) > maxWidth) {
                chunks.add(next.trim());
                next = "";
            } else {
                next += c;
                ++i;
            }
        }
        if (!"".equals(next)) {
            chunks.add(next);
        }
        return chunks;
    }

    public static List<String> wrapText(String text, PDFont font, float fontSize, float maxWidth) throws IOException {

        // this needs some work to deal with formatted dates and times not getting split
        float textWidth = getStringWidth(text, font, fontSize);
        if (textWidth <= maxWidth) {
            return Collections.singletonList(text);
        }
        List<String> lines = new ArrayList<>();
        // split on words
        text = encodeDatesAndTimes(text);
        String[] words = text.split("(?<=\\W)");
        words = decodeDatesAndTimes(words);
        float width = 0;
        final float spaceWidth = getStringWidth(" ", font, fontSize);
        String line = "";
        for (String word : words) {
            boolean hasTrailingSpace = false;
            if (word.endsWith(" ")) {
                word = word.substring(0, word.length() - 1);
                hasTrailingSpace = true;
            }
            float wordWidth = getStringWidth(word, font, fontSize);
            if (wordWidth >= maxWidth) {
                word = line + word;
                List<String> chunks = splitText(word, font, fontSize, maxWidth);
                lines.addAll(chunks.subList(0, chunks.size() - 1));
                line = chunks.get(chunks.size() - 1) + (hasTrailingSpace ? " " : "");
                width = getStringWidth(line, font, fontSize);
                continue;
            }
            if (width + (hasTrailingSpace ? spaceWidth : 0) + wordWidth > maxWidth) {
                lines.add(line.trim());
                width = wordWidth;
                line = word + (hasTrailingSpace ? " " : "");
                continue;
            }
            line += word + (hasTrailingSpace ? " " : "");
            width = getStringWidth(line, font, fontSize);
        }
        if (width > 0) {
            lines.add(line.trim());
        }
        return lines;
    }

    static String encodeDatesAndTimes(String text) {
        text = text.replaceAll("(\\d{1,2})/(\\d{1,2})/(\\d{4})", "$1SLASH$2SLASH$3");
        text = text.replaceAll("(\\d{1,2}):(\\d{2}):(\\d{2})", "$1COLON$2COLON$3");
        text = text.replaceAll("(\\d{1,2}):(\\d{2})", "$1COLON$2");
        return text;
    }

    static String[] decodeDatesAndTimes(String[] elems) {
        for (int i = 0; i < elems.length; ++i) {
            String elem = elems[i];
            elem = elem.replaceFirst("(\\d{1,2})SLASH(\\d{1,2})SLASH(\\d{4})", "$1/$2/$3");
            elem = elem.replaceFirst("(\\d{1,2})COLON(\\d{2})COLON(\\d{2})", "$1:$2:$3");
            elem = elem.replaceFirst("(\\d{1,2})COLON(\\d{2})", "$1:$2");
            elems[i] = elem;
        }
        return elems;
    }

    public static float getStringWidth(String text, PDFont font, float fontSize) throws IOException {
        return font.getStringWidth(text) / 1000 * fontSize;
    }
}
