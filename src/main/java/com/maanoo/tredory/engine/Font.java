// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.engine;

import org.newdawn.slick.Color;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class Font {

    protected final SpriteSheet sheet;

    protected char chars_offset;

    private int width;

    private int height;

    public Font(SpriteSheet sheet) {
        this(sheet, (char) 0);
    }

    public Font(SpriteSheet sheet, char first_char) {
        this.sheet = sheet;
        this.chars_offset = first_char;

        width = sheet.getSprite(0).getImage().getWidth();
        height = sheet.getSprite(0).getImage().getHeight();
    }

    public void drawString(float x, float y, String text) {
        drawString(x, y, text, Colors.white);
    }

    public void drawString(float x, float y, String text, Color color) {

        final byte[] data = text.getBytes();
        for (int i = 0; i < data.length; i++) {
            final int index = data[i] - chars_offset;

            sheet.getSprite(index).getImage().draw(x + (i * width), y, color);
        }
    }

    public void drawStringCentered(float x, float y, String text) {
        drawStringCentered(x, y, text, Colors.white);
    }

    public void drawStringCentered(float x, float y, String text, Color color) {
        drawString(x - getWidth(text) / 2, y, text, color);
    }

    public void drawStringMultiline(float x, float y, String text) {
        drawStringMultiline(x, y, text, Colors.white);
    }

    public void drawStringMultiline(float x, float y, String text, Color color) {

        int y_offset = 0;
        for (final String line : text.split("\n")) {

            drawString(x, y + y_offset, line);
            y_offset += height;
        }
    }

    public void drawStringCenteredMultiline(float x, float y, String text) {
        drawStringCenteredMultiline(x, y, text, Colors.white);
    }

    public void drawStringCenteredMultiline(float x, float y, String text, Color color) {

        int y_offset = 0;
        for (final String line : text.split("\n")) {

            drawStringCentered(x, y + y_offset, line, color);
            y_offset += height;
        }
    }

    public void drawStringMarkdown(float x, float y, String text) {
        drawStringMarkdown(x, y, text, Colors.grey10, Colors.grey08, Colors.grey05, Colors.grey07);
    }

    public void drawStringMarkdown(float x, float y, String text, Color color1, Color color2, Color color3,
            Color colorP) {

        int y_offset = 0;
        for (String line : text.split("\n")) {

            Color color = colorP;

            if (line.startsWith("###")) {
                line = line.substring(3);
                color = color3;
            } else if (line.startsWith("##")) {
                line = line.substring(2);
                color = color2;
            } else if (line.startsWith("#")) {
                line = line.substring(1);
                color = color1;
            }

            drawString(x, y + y_offset, line, color);

            y_offset += height;
        }
    }

    public int getHeight(String text) {
        return height;
    }

    public int getWidth(String text) {
        return width * text.length();
    }

    public int getWidthMultiline(String text) {
        int max = 0;

        for (int i = 0, line = 0; i < text.length(); i += line) {
            line = 0;
            while (i + line < text.length() && text.charAt(i + line) != '\n') {
                line++;
            }
            if (line > max) {
                max = line;
            }
            line++;
        }
        return max * width;
    }

    public int getLineHeight() {
        return height;
    }

}
