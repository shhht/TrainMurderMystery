package dev.doctor4t.trainmurdermystery.util;

import dev.doctor4t.trainmurdermystery.TMM;
import dev.doctor4t.trainmurdermystery.index.TMMItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import org.jetbrains.annotations.NotNull;

public record PsychoActivatePayload() implements CustomPayload {
    public static final Id<PsychoActivatePayload> ID = new Id<>(TMM.id("psychoactivate"));
    public static final PacketCodec<PacketByteBuf, PsychoActivatePayload> CODEC = PacketCodec.unit(new PsychoActivatePayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    @Environment(EnvType.CLIENT)
    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<PsychoActivatePayload> {
        @Override
        public void receive(@NotNull PsychoActivatePayload payload, ClientPlayNetworking.@NotNull Context context) {
            var player = context.player();
            if (player.getMainHandStack().isOf(TMMItems.BAT)) return;
            for (var i = 0; i < 9; i++) {
                if (!player.getInventory().getStack(i).isOf(TMMItems.BAT)) continue;
                player.getInventory().selectedSlot = i;
                break;
            }
        }
    }
}