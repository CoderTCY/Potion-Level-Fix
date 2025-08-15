package com.teampotato.potion_level_fix;

import com.teampotato.potion_level_fix.network.s2c.LevelPacketS2C;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(PotionLevelFix.MODID)
public class PotionLevelFix {
    public static final String MODID = "potion_level_fix";
    public static final Logger LOGGER = LoggerFactory.getLogger("PotionLevelFix");
    public static ModConfigSpec CONFIG;
    public static ModConfigSpec.BooleanValue EFFECT_NUMBER;
    public static ModConfigSpec.BooleanValue POTION_NUMBER;
    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
            builder.push("Potion Level Fix");
        EFFECT_NUMBER = builder
                .comment("If true, effect display using Roman numerals (vanilla); if false, display using Arabic numerals.")
                .define("Effect Number Type", true);
        POTION_NUMBER = builder
                .comment("If true, potion display using Roman numerals (vanilla); if false, display using Arabic numerals.")
                .define("Potion Number Type", true);
        builder.pop();
        CONFIG = builder.build();
    }
    public PotionLevelFix(ModContainer modContainer, IEventBus iEventBus) {
        modContainer.registerConfig(ModConfig.Type.COMMON, CONFIG);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        iEventBus.<RegisterPayloadHandlersEvent>addListener(event -> {
            PayloadRegistrar registrar = event.registrar("1");
            registrar.playToClient(LevelPacketS2C.TYPE, LevelPacketS2C.STREAM_CODEC, LevelPacketS2C::handle);
        });
    }
}
