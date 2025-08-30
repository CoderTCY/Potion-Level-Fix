package com.teampotato.potion_level_fix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionUtils.class)
public class PotionUtilsMixin {
    @WrapOperation(
            method = "addPotionTooltip(Lnet/minecraft/world/item/ItemStack;Ljava/util/List;F)V",
            at = @At(
                    value = "NEW",
                    target = "(Ljava/lang/String;)Lnet/minecraft/network/chat/TranslatableComponent;",
                    ordinal = 2
            )
    )
    private static TranslatableComponent configPotionTooltips(String key, Operation<TranslatableComponent> original, @Local MobEffectInstance mobeffectinstance) {
        return PotionLevelFix.POTION_NUMBER.get() ? original.call(key) : new TranslatableComponent(String.format("%s", mobeffectinstance.getAmplifier()+1));
    }
}