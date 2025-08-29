package com.teampotato.potion_level_fix.datagen;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = PotionLevelFix.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        if (event.includeClient()) {
            generator.addProvider(new ModLanguageProvider(generator, "en_us"));
        }
    }
}
