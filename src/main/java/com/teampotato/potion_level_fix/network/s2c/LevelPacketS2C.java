package com.teampotato.potion_level_fix.network.s2c;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record LevelPacketS2C(String id, int amplifier) {
    public static LevelPacketS2C sentEffect(String id, int amplifier){
        return new LevelPacketS2C(id, amplifier);
    }

    public static void encode(LevelPacketS2C packet, FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUtf(packet.id);
        friendlyByteBuf.writeInt(packet.amplifier);
    }

    public static LevelPacketS2C decode(FriendlyByteBuf friendlyByteBuf) {
        return new LevelPacketS2C(friendlyByteBuf.readUtf(), friendlyByteBuf.readInt());
    }

    public static void handle(LevelPacketS2C packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {

        });
        ctx.get().setPacketHandled(true);
    }
}
