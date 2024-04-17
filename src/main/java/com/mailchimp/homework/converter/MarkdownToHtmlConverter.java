package com.mailchimp.homework.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MarkdownToHtmlConverter {
    private static final List<String> openHtmlElements = new ArrayList<>();
    private static List<String> convertedLines;
    private static Boolean matchSuccess;

    /**
     * Converts the input markdown to HTML.
     *
     * @param markdownText Markdown text to be converted.
     * @return Converted HTML.
     */
    public static String convertMarkdownToHtml(String markdownText) {
        convertedLines = new ArrayList<>();
        int index = 0;

        for (String line : markdownText.split("\n")) {
            matchSuccess = Boolean.TRUE;
            line = evaluateHtmlElements(line);

            if (!matchSuccess) {
                line = processParagraph(line);
            } else if (!openHtmlElements.isEmpty()) {
                closeRemainingHtmlTags(index - 1);
            }
            convertedLines.add(index++, line);
        }

        if (!openHtmlElements.isEmpty())
            closeRemainingHtmlTags(index - 1);

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
    private static String evaluateHtmlElements(String line) {
        // Empty line
        if (line.trim().isEmpty() || line.trim().equals("...")) {
            return line;
        }

        // Links
        line = processLinks(line);

        String nonConvertedLine = line;

        // Heading
        line = processHeaders(line);
        if (!line.equals(nonConvertedLine)) {
            return line;
        }

        matchSuccess = Boolean.FALSE;

        return line;
    }

    /**
     * Process hyperlinks.
     *
     * @param line Input line.
     * @return Converted line if matches one of the patterns.
     */
    private static String processLinks(String line) {
        Pattern pattern = Pattern.compile("(.*)\\[(.+)\\]\\((.+) \"(.*)\"\\)(.*)");
        Matcher matcher = pattern.matcher(line);
        while (matcher.matches()) {
            line = String.format("%s<a href=\"%s\" title=\"%s\">%s</a>%s", matcher.group(1), matcher.group(3), matcher.group(4), matcher.group(2), matcher.group(5));
            matcher = pattern.matcher(line);
        }

        pattern = Pattern.compile("(.*)\\[(.+)\\]\\((.+)\\)(.*)");
        matcher = pattern.matcher(line);
        while (matcher.matches()) {
            line = String.format("%s<a href=\"%s\">%s</a>%s", matcher.group(1), matcher.group(3), matcher.group(2), matcher.group(4));
            matcher = pattern.matcher(line);
        }

        return line;
    }

    /**
     * Process headings.
     *
     * @param line Input line.
     * @return Converted line if matches one of the patterns.
     */
    private static String processHeaders(String line) {
        if (line.matches("^[#]{1,6}[\\s].+")) {
            long titleNum = line.replaceFirst("(^[#]{1,6})[\\s].+", "$1").chars().count();
            if (titleNum < 7) {
                line = String.format("<h%d>%s</h%d>", titleNum, line.replaceFirst("^([#]){1,6}[\\s]", ""), titleNum);
            }
        }
        return line;
    }

    /**
     * Process unformatted text.
     *
     * @param line Input line.
     * @return Converted line.
     */
    private static String processParagraph(String line) {
        String element = "p";
        if (isHtmlElementListEmpty(element)) {
            openHtmlElements.add(element);
            return String.format("<%s>%s", element, line);
        }
        return line;
    }

    /**
     * Close open HTM tags (if any).
     *
     * @param lineIndex Index of the line in the converted lines list.
     */
    private static void closeRemainingHtmlTags(int lineIndex) {
        StringBuilder closingTags = new StringBuilder();
        closingTags.append(convertedLines.get(lineIndex));

        for (String element : openHtmlElements) {
            closingTags.append(String.format("</%s>", element));
        }
        openHtmlElements.clear();
        convertedLines.remove(lineIndex);
        convertedLines.add(lineIndex, closingTags.toString());
    }

    /**
     * Checks if the list of open HTML elements contains the passed element.
     *
     * @param element HTML element to be searched.
     * @return True if the list of open HTML elements does not contain the passed element.
     */
    private static boolean isHtmlElementListEmpty(String element) {
        return openHtmlElements.stream().noneMatch(e -> e.equals(element));
    }
}
