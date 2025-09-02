package com.teampotato.potion_level_fix.mixin.compoat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.teampotato.potion_level_fix.PotionLevelFix;
import net.minecraft.nbt.CompoundTag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Pseudo
@Mixin(targets = "snownee.jade.addon.vanilla.PotionEffectsProvider")
public abstract class JadeEffectMixin {
    @WrapOperation(method = "appendBody", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/I18n;func_188566_a(Ljava/lang/String;)Z"))
    private boolean configNumber(String key, Operation<Boolean> original, @Local CompoundTag compound) {
        return (PotionLevelFix.EFFECT_NUMBER.get() || compound.getInt("Amplifier") == 0) && original.call(key);
    }

    @ModifyArg(method = "appendBody", at = @At(value = "INVOKE", target = "Ljava/lang/Integer;toString(I)Ljava/lang/String;"))
    private int plusOne(int i) {
        return i + 1;
    }
}
