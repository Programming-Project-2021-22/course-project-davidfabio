package org.davidfabio.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.davidfabio.game.Score;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class provides utility functions to read & write from json-files.
 * In particular this class is used to read and write settings and scores.
 */
public class JSONFileManagement {
    /**
     * This method takes a .json-File and reads the contents. The Settings found
     * inside are copied into the static class {@link Settings}.
     * @param settingsFile .json-File that contains the game settings.
     */
    public static void initSettingsFromFile(File settingsFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode settings = mapper.readValue(settingsFile, JsonNode.class);
            if (settings.has("username"))
                Settings.username = settings.get("username").asText();
            if (settings.has("levelWidth"))
                Settings.levelWidth = (float)settings.get("levelWidth").asDouble();
            if (settings.has("levelHeight"))
                Settings.levelHeight = (float)settings.get("levelHeight").asDouble();
            if (settings.has("fullscreenEnabled"))
                Settings.fullscreenEnabled = settings.get("fullscreenEnabled").asBoolean();
            if (settings.has("windowWidth"))
                Settings.windowWidth = settings.get("windowWidth").asInt();
            if (settings.has("windowHeight"))
                Settings.windowHeight = settings.get("windowHeight").asInt();
            if (settings.has("sfxEnabled"))
                Settings.sfxEnabled = settings.get("sfxEnabled").asBoolean();
            if (settings.has("musicEnabled"))
                Settings.musicEnabled = settings.get("musicEnabled").asBoolean();
            if (settings.has("sfxVolume"))
                Settings.sfxVolume = (float)settings.get("sfxVolume").asDouble();
            if (settings.has("musicVolume"))
                Settings.musicVolume = (float)settings.get("musicVolume").asDouble();
        } catch (IOException e) {
            // do nothing
            e.printStackTrace();
        }
    }

    /**
     * This method writes the mutable values of the {@link Settings} class into
     * a .json File.
     * @param settingsFile .json-file where the settings are written to.
     */
    public static void writeSettingsToFile(File settingsFile) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode settings = mapper.createObjectNode();
        settings.put("username",Settings.username);
        settings.put("levelWidth",Settings.levelWidth);
        settings.put("levelHeight",Settings.levelHeight);
        settings.put("fullscreenEnabled",Settings.fullscreenEnabled);
        settings.put("windowWidth",Settings.windowWidth);
        settings.put("windowHeight",Settings.windowHeight);
        settings.put("sfxEnabled",Settings.sfxEnabled);
        settings.put("musicEnabled",Settings.musicEnabled);
        settings.put("sfxVolume",Settings.sfxVolume);
        settings.put("musicVolume",Settings.musicVolume);

        try {
            mapper.writeValue(settingsFile, settings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads all the scores stored in a .json-file. These scores are returned in an
     * {@link ArrayList}.
     * @param scoresFile .json-file containing all the past scores
     * @return an ArrayList containing {@link Score}s found in the file
     */
    public static ArrayList<Score> initScoresFromFile(File scoresFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<Score> scores = mapper.readValue(scoresFile, new TypeReference<ArrayList<Score>>(){});
            return scores;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Stores the provided scores into a .json-file.
     * @param scoresFile file where the scores are saved to.
     * @param scores the scores that need to be stored.
     */
    public static void writeScoresToFile(File scoresFile, ArrayList<Score> scores) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(scoresFile, scores);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
