package com.mailchimp.homework.parser;

public class ParagraphParser implements MarkdownParser {
    private static final String HTML_OPEN_FORMAT = "<%s>%s";
    private static final String HTML_CLOSE_FORMAT = "%s</%s>";
    private static final String ELEMENT = "p";

    @Override
    public String parse(String line) {
        return String.format(HTML_OPEN_FORMAT, ELEMENT, line);
    }

    public String close(String line) {
        return String.format(HTML_CLOSE_FORMAT, line, ELEMENT);
    }
}
