package com.teampotato.potion_level_fix.datagen;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = PotionLevelFix.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        boolean client = event.includeClient();
        generator.addProvider(client, new ModLanguageProvider(generator, "en_us"));
    }
}
