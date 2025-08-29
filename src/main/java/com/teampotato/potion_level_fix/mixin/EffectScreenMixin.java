package com.teampotato.potion_level_fix.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectScreenMixin {
    @ModifyArg(method = "renderLabels", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawShadow(Lcom/mojang/blaze3d/vertex/PoseStack;Ljava/lang/String;FFI)I", ordinal = 0), index = 1)
    private String modifyEffectName(String string, @Local MobEffectInstance pEffect) {
        String id = I18n.get(pEffect.getEffect().getDescriptionId());
        int amplifier = potion_level_fix$getAmplifier(pEffect);

        if (amplifier > 1){
            String amplifierText = String.valueOf(amplifier);
            if (PotionLevelFix.EFFECT_NUMBER.get()) {
                amplifierText = I18n.get("enchantment.level." + amplifier);
            }
            id = id + " " + amplifierText;
        }
        return id;
    }

    @Unique
    private static int potion_level_fix$getAmplifier(MobEffectInstance pEffect) {
        LocalPlayer localPlayer = Minecraft.getInstance().player;
        CompoundTag persistentData = localPlayer.getPersistentData();
        ListTag listTag = new ListTag();
        int amplifier = 0;

        if (persistentData.contains("PLF:Amplifier")){
            listTag = persistentData.getList("PLF:Amplifier", 10);
        }

        for (Tag value : listTag) {
            CompoundTag tag = (CompoundTag) value;
            if (tag.contains(pEffect.getDescriptionId())) {
                amplifier = tag.getInt(pEffect.getDescriptionId()) + 1;
            }
        }
        return amplifier;
    }
}
