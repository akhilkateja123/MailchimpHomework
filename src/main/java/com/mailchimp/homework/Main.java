package com.mailchimp.homework;

import com.mailchimp.homework.converter.MarkdownToHtmlConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class Main {
    private static final String INPUT_FILE = "./input_markdown.md";  // Input File Path
    private static final String OUTPUT_FILE = "./output_html.html";  // Output File Path

    public static void main(String[] args) {
        String inputMarkdown = readInputMarkdownFromFile(INPUT_FILE);
        String outputHtml = new MarkdownToHtmlConverter().convertMarkdownToHtml(inputMarkdown);
        writeOutputHtmlToFile(OUTPUT_FILE, outputHtml);
    }

    /**
     * Reads the contents of the input file.
     *
     * @param path Input file path.
     * @return Input file content.
     */
    private static String readInputMarkdownFromFile(String path) {
        String content = "";
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            while (true) {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                content = String.format("%s%s\n", content, line);
            }

        } catch (Exception error) {
            System.out.println(error);
            error.printStackTrace();
        }
        return content;
    }

    /**
     * Writes the converted output to the output file.
     *
     * @param path Input file path.
     */
    private static void writeOutputHtmlToFile(String path, String content) {
        try {
            FileWriter writer = new FileWriter(path);
            writer.write(content);
            writer.flush();
            writer.close();

        } catch (Exception error) {
            System.out.println(error);
            error.printStackTrace();
        }
    }
}