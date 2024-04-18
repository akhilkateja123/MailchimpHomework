package com.mailchimp.homework.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperlinkParser implements MarkdownParser {
    private static final String MARKDOWN_PATTERN_WITH_TITLE = "(.*)\\[(.+)\\]\\((.+) \"(.*)\"\\)(.*)";
    private static final String MARKDOWN_PATTERN_WITHOUT_TITLE = "(.*)\\[(.+)\\]\\((.+)\\)(.*)";

    private static final String HTML_FORMAT_WITHOUT_TILE = "%s<a href=\"%s\">%s</a>%s";
    private static final String HTML_FORMAT_WITH_TILE = "%s<a href=\"%s\" title=\"%s\">%s</a>%s";


    @Override
    public String parse(String line) {
        Pattern pattern = Pattern.compile(MARKDOWN_PATTERN_WITH_TITLE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.matches()) {
            line = String.format(HTML_FORMAT_WITH_TILE, matcher.group(1), matcher.group(3), matcher.group(4), matcher.group(2), matcher.group(5));
            matcher = pattern.matcher(line);
        }

        pattern = Pattern.compile(MARKDOWN_PATTERN_WITHOUT_TITLE);
        matcher = pattern.matcher(line);
        while (matcher.matches()) {
            line = String.format(HTML_FORMAT_WITHOUT_TILE, matcher.group(1), matcher.group(3), matcher.group(2), matcher.group(4));
            matcher = pattern.matcher(line);
        }

        return line;
    }
}
