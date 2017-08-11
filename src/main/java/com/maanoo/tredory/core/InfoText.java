// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.core;

/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class InfoText {

    private static final String EMPTY = "";

    public static InfoText create() {
        return new InfoText(EMPTY, EMPTY, EMPTY);
    }

    public static InfoText create(String name) {
        return new InfoText(name, EMPTY, EMPTY);
    }

    public static InfoText create(String name, String desc) {
        return new InfoText(name, desc, EMPTY);
    }

    public static InfoText create(String name, String desc, String lore) {
        return new InfoText(name, desc, lore);
    }

    public static InfoText get(String id) {
        // TODO get loaded info text
        return InfoText.create("id(" + id + ")");
    }

    public final String name;
    public final String desc;
    public final String lore;

    private InfoText(String name, String desc, String lore) {
        this.name = name;
        this.desc = desc;
        this.lore = lore;
    }

}
