package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectScreenMixin {
    @Inject(method = "getEffectName", at = @At(value = "RETURN"), cancellable = true)
    private void modifyEffectName(MobEffectInstance pEffect, CallbackInfoReturnable<Component> cir) {
        MutableComponent mutablecomponent = pEffect.getEffect().getDisplayName().copy();
        int amplifier = pEffect.getAmplifier() + 1;

        if (amplifier > 1){
            Component amplifierText = Component.literal(String.valueOf(amplifier));
            if (PotionLevelFix.EFFECT_NUMBER.get()) {
                amplifierText = Component.translatable("enchantment.level." + amplifier);
            }
            mutablecomponent.append(CommonComponents.SPACE).append(amplifierText);
        }
        cir.setReturnValue(mutablecomponent);
    }
}
