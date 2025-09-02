package com.teampotato.potion_level_fix.mixin;

import com.teampotato.potion_level_fix.impl.S2CAmplifierGetter;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientboundUpdateMobEffectPacket.class)
public class ClientboundUpdateMobEffectPacketMixin implements S2CAmplifierGetter {
    @Unique private int realAmplifier = 0;

    @Inject(method = "<init>(ILnet/minecraft/world/effect/MobEffectInstance;)V", at = @At("TAIL"))
    public void resetInitAmplifier(int entityId, MobEffectInstance mobEffectInstance, CallbackInfo ci) {
        realAmplifier = mobEffectInstance.getAmplifier();
    }

    @Inject(method = "<init>(Lnet/minecraft/network/FriendlyByteBuf;)V", at = @At("TAIL"))
    public void resetBufAmplifier(FriendlyByteBuf buffer, CallbackInfo ci) {
        realAmplifier = buffer.readInt();
    }

    @Inject(method = "write", at = @At("TAIL"))
    public void writeAmplifier(FriendlyByteBuf buffer, CallbackInfo ci) {
        buffer.writeInt(realAmplifier);
    }

    @Override
    public int getRealAmplifier() {
        return realAmplifier;
    }
}
