package com.teampotato.potion_level_fix;

import com.teampotato.potion_level_fix.network.NetworkHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class PotionLevelFixClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(NetworkHandler.LEVEL_PACKET_ID, (minecraft, clientPacketListener, friendlyByteBuf, packetSender) -> {
			String id = friendlyByteBuf.readUtf();
			int amplifier = friendlyByteBuf.readInt();

			LocalPlayer localPlayer = Minecraft.getInstance().player;
			if (localPlayer == null) return;
			localPlayer.addAdditionalSaveData();
			CompoundTag persistentData = localPlayer.getPersistentData();
			ListTag listTag = new ListTag();

			if (persistentData.contains("PLF:Amplifier")){
				listTag = persistentData.getList("PLF:Amplifier", 10);
			}

			CompoundTag compoundTag = new CompoundTag();
			compoundTag.putInt(id, amplifier);
			for (int size = 0; size < listTag.size(); size++){
				CompoundTag tag = (CompoundTag) listTag.get(size);
				if (tag.contains(id)){
					listTag.set(size, compoundTag);
					localPlayer.getPersistentData().put("PLF:Amplifier", listTag);
					return;
				}
			}
			listTag.add(compoundTag);
			localPlayer.getPersistentData().put("PLF:Amplifier", listTag);
		});
	}
}