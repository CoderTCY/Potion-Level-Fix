package com.teampotato.potion_level_fix.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.teampotato.potion_level_fix.network.s2c.LevelPacketS2C;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public abstract class MobEffectInstanceMixin {
    @Shadow private int amplifier;
    @Shadow public abstract String getDescriptionId();

    @Shadow public abstract String toString();

    @Inject(method = "<init>(Lnet/minecraft/core/Holder;IIZZZLnet/minecraft/world/effect/MobEffectInstance;)V", at = @At("TAIL"))
    public void noClamp(Holder<MobEffect> effect, int duration, int amplifier, boolean ambient, boolean visible, boolean showIcon, MobEffectInstance hiddenEffect, CallbackInfo ci){
        this.amplifier = Math.max(0, amplifier);
    }

    @ModifyReturnValue(method = "save", at = @At(value = "RETURN"))
    private Tag redirectPutByte(Tag tag) {
        if (tag instanceof CompoundTag compoundTag) {
            compoundTag.putInt("PLF:Amplifier", amplifier);
        }
        return tag;
    }

    @ModifyReturnValue(method = "load", at = @At(value = "RETURN"))
    private static MobEffectInstance amplifierGet(MobEffectInstance original, @Local(argsOnly = true)CompoundTag compoundTag) {
        if (original != null) {
            original.amplifier = compoundTag.getInt("PLF:Amplifier");
        }
        return original;
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void sentAmplifier(LivingEntity pEntity, Runnable pOnExpirationRunnable, CallbackInfoReturnable<Boolean> cir){
        if (pEntity instanceof ServerPlayer serverPlayer){
            LevelPacketS2C.sendToClient(serverPlayer, getDescriptionId(), amplifier);
        }
    }
}
