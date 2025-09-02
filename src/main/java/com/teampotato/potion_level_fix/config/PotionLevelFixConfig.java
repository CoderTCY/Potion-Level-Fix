package com.teampotato.potion_level_fix.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.teampotato.potion_level_fix.PotionLevelFix;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PotionLevelFixConfig {
    public boolean effectNumberType = true;
    public boolean potionNumberType = true;
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("potion_level_fix.json");
    
    public static PotionLevelFixConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try {
                String json = Files.readString(CONFIG_PATH);
                return GSON.fromJson(json, PotionLevelFixConfig.class);
            } catch (Exception e) {
                PotionLevelFix.LOGGER.error("Failed to load config, using defaults", e);
            }
        }
        
        PotionLevelFixConfig config = new PotionLevelFixConfig();
        config.save();
        return config;
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, GSON.toJson(this));
        } catch (IOException e) {
            PotionLevelFix.LOGGER.error("Failed to save config", e);
        }
    }
}