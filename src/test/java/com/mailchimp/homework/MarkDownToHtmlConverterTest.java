package com.mailchimp.homework;

import com.mailchimp.homework.converter.MarkdownToHtmlConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MarkDownToHtmlConverterTest {

    private final List<String> inputs = List.of(
            //Input#1
            " ",
            //Input#2
            "...",
            //Input#3
            "###### Heading 6",
            //Input#4
            "[Link text](https://www.example.com)",
            //Input#5
            "[Link with Title](https://www.google.com \"Title\")",
            //Input#6
            "Unformatted text",
            //Input#7
            """
                    # Sample Document

                    Hello!

                    This is sample markdown for the [Mailchimp](https://www.mailchimp.com) homework assignment.""",
            //Input#8
            """
                    # Header one

                    Hello there

                    How are you?
                    What's going on?

                    ## Another Header

                    This is a paragraph [with an inline link](http://google.com). Neat, eh?

                    ## This is a header [with a link](http://yahoo.com)"""
    );

    private final List<String> outputs = List.of(
            //Output#1
            " ",
            //Output#2
            "...",
            //Output#3
            "<h6>Heading 6</h6>",
            //Output#4
            "<a href=\"https://www.example.com\">Link text</a>",
            //Output#5
            "<a href=\"https://www.google.com\" title=\"Title\">Link with Title</a>",
            //Output#6
            "<p>Unformatted text</p>",
            //Output#7
            """
                    <h1>Sample Document</h1>

                    <p>Hello!</p>

                    <p>This is sample markdown for the <a href="https://www.mailchimp.com">Mailchimp</a> homework assignment.</p>""",
            //Output#8
            """
                    <h1>Header one</h1>

                    <p>Hello there</p>

                    <p>How are you?
                    What's going on?</p>

                    <h2>Another Header</h2>

                    <p>This is a paragraph <a href="http://google.com">with an inline link</a>. Neat, eh?</p>

                    <h2>This is a header <a href="http://yahoo.com">with a link</a></h2>"""
    );


    @Test
    void testMarkdowns() {
        // Arrange
        for (int i = 0; i < inputs.size(); i++) {
            //Act
            String outputHtml = new MarkdownToHtmlConverter().convertMarkdownToHtml(inputs.get(i));
            //Assert
            Assertions.assertTrue(outputHtml.contains(outputs.get(i)));
        }
    }
}
