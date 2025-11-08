package com.teampotato.potion_level_fix.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionContents.class)
public class PotionContentsMixin {
    @WrapOperation(
            method = "addPotionTooltip(Ljava/lang/Iterable;Ljava/util/function/Consumer;FF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Component;translatable(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;",
                    ordinal = 1
            )
    )
    private static MutableComponent configPotionTooltips(String key, Operation<MutableComponent> original, @Local() MobEffectInstance mobeffectinstance) {
        return PotionLevelFix.POTION_NUMBER.get() ? original.call(key) : Component.literal("%s".formatted(mobeffectinstance.getAmplifier()+1));
    }
}
