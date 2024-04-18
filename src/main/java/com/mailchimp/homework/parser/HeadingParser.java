package com.mailchimp.homework.parser;

public class HeadingParser implements MarkdownParser {
    private static final String MARKDOWN_PATTERN = "^[#]{1,6}[\\s].+";
    private static final String HTML_FORMAT = "<h%d>%s</h%d>";

    @Override
    public String parse(String line) {
        if (line.matches(MARKDOWN_PATTERN)) {
            long titleNum = line.replaceFirst("(^[#]{1,6})[\\s].+", "$1").chars().count();
            if (titleNum < 7) {
                line = String.format(HTML_FORMAT, titleNum, line.replaceFirst("^([#]){1,6}[\\s]", ""), titleNum);
            }
        }
        return line;
    }
}
