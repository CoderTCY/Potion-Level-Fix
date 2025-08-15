package com.teampotato.potion_level_fix.network.s2c;

import com.teampotato.potion_level_fix.PotionLevelFix;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record LevelPacketS2C(String id, int amplifier) implements CustomPacketPayload {
    public static final Type<LevelPacketS2C> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(PotionLevelFix.MODID, "level"));
    public static final StreamCodec<ByteBuf, LevelPacketS2C> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            LevelPacketS2C::id,
            ByteBufCodecs.INT,
            LevelPacketS2C::amplifier,
            LevelPacketS2C::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            if (localPlayer == null) return;
            CompoundTag persistentData = localPlayer.getPersistentData();
            ListTag listTag = new ListTag();

            if (persistentData.contains("PLF:Amplifier")){
                listTag = persistentData.getList("PLF:Amplifier", 10);
            }

            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putInt(this.id, this.amplifier);
            for (int size = 0; size < listTag.size(); size++){
                CompoundTag tag = (CompoundTag) listTag.get(size);
                if (tag.contains(this.id)){
                    listTag.set(size, compoundTag);
                    localPlayer.getPersistentData().put("PLF:Amplifier", listTag);
                    return;
                }
            }
            listTag.add(compoundTag);
            localPlayer.getPersistentData().put("PLF:Amplifier", listTag);
        }).exceptionally(e -> {
            ctx.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        });
    }

    public static void sendToClient(ServerPlayer serverPlayer, String id, int amplifier) {
        PacketDistributor.sendToPlayer(serverPlayer, new LevelPacketS2C(id, amplifier));
    }
}
