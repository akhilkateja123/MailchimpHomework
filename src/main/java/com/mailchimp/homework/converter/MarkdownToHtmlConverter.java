package com.mailchimp.homework.converter;

import com.mailchimp.homework.parser.HeadingParser;
import com.mailchimp.homework.parser.HyperlinkParser;
import com.mailchimp.homework.parser.MarkdownParser;
import com.mailchimp.homework.parser.ParagraphParser;

import java.util.ArrayList;
import java.util.List;

public class MarkdownToHtmlConverter {
    enum MarkdownType {
        HYPERLINK, HEADING, PARAGRAPH;
    }

    private List<String> convertedLines;
    private boolean isParagraphOpen;
    private boolean isMatchSuccess;

    /**
     * Converts the input markdown to HTML.
     *
     * @param markdownText Markdown text to be converted.
     * @return Converted HTML.
     */
    public String convertMarkdownToHtml(String markdownText) {
        convertedLines = new ArrayList<>();
        isParagraphOpen = false;
        int index = 0;

        for (String line : markdownText.split("\n")) {
            isMatchSuccess = true;
            line = evaluateHtmlElements(line);

            if (isMatchSuccess && isParagraphOpen) {
                closeParagraphTag(index - 1);
                isParagraphOpen = false;
            }
            convertedLines.add(index++, line);
        }

        if (isParagraphOpen)
            closeParagraphTag(index - 1);

        StringBuilder convertedText = new StringBuilder();
        for (String convertedLine : convertedLines) {
            convertedText.append(convertedLine).append("\n");
        }

        return convertedText.toString();
    }

    /**
     * Math the line passed to HTML element patterns.
     *
     * @param line Line from input.
     * @return Converted Line (if matched to one of the HTML elements).
     */
    private String evaluateHtmlElements(String line) {
        // Empty line
        if (line.trim().isEmpty() || line.trim().equals("...")) {
            return line;
        }
        // Links
        line = getParser(MarkdownType.HYPERLINK).parseAndTransform(line);

        // Heading
        String nonConvertedLine = line;
        line = getParser(MarkdownType.HEADING).parseAndTransform(line);
        if (!line.equals(nonConvertedLine)) {
            return line;
        }

        isMatchSuccess = Boolean.FALSE;

        //Paragraph
        if (!isParagraphOpen) {
            line = getParser(MarkdownType.PARAGRAPH).parseAndTransform(line);
            isParagraphOpen = Boolean.TRUE;
        }

        return line;
    }

    /**
     * Returns the corresponding parser to the markdown type.
     *
     * @param markdownType Markdown type.
     * @return Parser.
     */
    private MarkdownParser getParser(MarkdownType markdownType) {
        if (markdownType == MarkdownType.HYPERLINK) {
            return new HyperlinkParser();
        } else if (markdownType == MarkdownType.HEADING) {
            return new HeadingParser();
        }

        return new ParagraphParser();
    }

    /**
     * Close open HTM tags (if any).
     *
     * @param lineIndex Index of the line in the converted lines list.
     */
    private void closeParagraphTag(int lineIndex) {
        String line = ((ParagraphParser) getParser(MarkdownType.PARAGRAPH)).
                close(convertedLines.get(lineIndex));
        convertedLines.remove(lineIndex);
        convertedLines.add(lineIndex, line);
    }
}
