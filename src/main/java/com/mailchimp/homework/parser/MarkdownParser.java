package com.mailchimp.homework.parser;

public interface MarkdownParser {
    /**
     * Parses the input based on matching rules and transforms it into the
     * corresponding HTML tag.
     *
     * @param line Input.
     * @return Transformed output.
     */
    String parseAndTransform(String line);
}
