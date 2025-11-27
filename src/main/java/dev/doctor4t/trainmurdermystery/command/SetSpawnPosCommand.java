package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;

import dev.doctor4t.trainmurdermystery.cca.AreasWorldComponent;
import dev.doctor4t.trainmurdermystery.cca.AreasWorldComponent.PosWithOrientation;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import org.jetbrains.annotations.NotNull;

public class SetSpawnPosCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
            CommandManager.literal("tmm:setSpawnPos")
                .requires(source -> source.hasPermissionLevel(2))
                    .executes(context -> {
                        AreasWorldComponent.KEY.get(context.getSource().getWorld()).setSpawnPos(new PosWithOrientation(
                            context.getSource().getEntity().getPos(),
                            context.getSource().getEntity().getYaw(),
                            context.getSource().getEntity().getPitch()));
                        return 1;
                    })
        );
    }
}
