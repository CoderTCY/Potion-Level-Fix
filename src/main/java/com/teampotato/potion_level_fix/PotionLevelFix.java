package com.teampotato.potion_level_fix;

import net.fabricmc.api.ModInitializer;
import com.teampotato.potion_level_fix.config.PotionLevelFixConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PotionLevelFix implements ModInitializer {
    public static final String MODID = "potion_level_fix";
    public static final Logger LOGGER = LoggerFactory.getLogger("PotionLevelFix");
    public static PotionLevelFixConfig CONFIG;

    @Override
    public void onInitialize() {
        CONFIG = PotionLevelFixConfig.load();
    }
}
