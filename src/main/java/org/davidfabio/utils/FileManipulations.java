package org.davidfabio.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.davidfabio.game.Score;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManipulations {
    public static void initSettingsFromFile(File settingsFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ObjectNode settings = mapper.readValue(settingsFile, ObjectNode.class);
            Settings.username = settings.get("username").asText();
            Settings.levelWidth = (float)settings.get("levelWidth").asDouble();
            Settings.levelHeight = (float)settings.get("levelHeight").asDouble();
            Settings.fullscreenEnabled = settings.get("fullscreenEnabled").asBoolean();
            Settings.windowWidth = settings.get("windowWidth").asInt();
            Settings.windowHeight = settings.get("windowHeight").asInt();
            Settings.sfxEnabled = settings.get("sfxEnabled").asBoolean();
            Settings.musicEnabled = settings.get("musicEnabled").asBoolean();
            Settings.sfxVolume = (float)settings.get("sfxVolume").asDouble();
            Settings.musicVolume = (float)settings.get("musicVolume").asDouble();
        } catch (IOException e) {
            // do nothing
        }
    }

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

    public static ArrayList<Score> initScoresFromFile(File scoresFile) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<Score> scores = mapper.readValue(scoresFile, new TypeReference<ArrayList<Score>>(){});
            return scores;
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public static void writeScoresToFile(File scoresFile, ArrayList<Score> scores) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(scoresFile, scores);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
