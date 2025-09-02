package com.teampotato.potion_level_fix.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectScreenMixin {
    @Definition(id = "getAmplifier", method = "Lnet/minecraft/world/effect/MobEffectInstance;getAmplifier()I")
    @Expression("?.getAmplifier() <= 9")
    @ModifyExpressionValue(method = "getEffectName", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean greaterTrue(boolean original) {
        return true;
    }

    @Expression("'enchantment.level.'")
    @ModifyExpressionValue(method = "getEffectName", at = @At("MIXINEXTRAS:EXPRESSION"))
    private String hackI18Key(String original) {
        return PotionLevelFix.EFFECT_NUMBER.get() ? original : "";
    }
}
