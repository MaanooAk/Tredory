// Tredory Copyright (c) 2014-2017 Tredory author list (see README.md)

package com.maanoo.tredory.account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.json.JSONObject;

import com.maanoo.tredory.IStore;
import com.maanoo.tredory.engine.Logger;


/**
 * TODO doc
 *
 * @author MaanooAk
 */
public class AccountStorage {

    public static void createActive(String json) {
        Account.active = IStore.load(json, Account.class);
    }

    public static void store() {
        final Path path = getFileLocation();

        final JSONObject o = Account.active.store();

        writeAllText(path, o.toString(2));

        Logger.log("AccountStorage", "Store account to local file");
    }

    public static void load() {
        final Path path = getFileLocation();

        if (Files.exists(path)) {
            createActive(readAllText(path));
        }

        Logger.log("AccountStorage", "Load account from local file");
    }

    private static Path getFileLocation() {
        return Paths.get("player.json");
    }

    // TODO move elsewhere
    private static String readAllText(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            final StringBuilder sb = new StringBuilder();
            while (true) {
                final String line = reader.readLine();
                if (line == null) break;
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO move elsewhere
    private static void writeAllText(Path path, String text) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                Files.newOutputStream(path, StandardOpenOption.CREATE), StandardCharsets.UTF_8.newEncoder()))) {
            writer.append(text);
            writer.newLine();
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
