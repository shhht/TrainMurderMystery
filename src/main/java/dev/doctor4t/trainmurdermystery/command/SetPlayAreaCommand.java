package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;

import dev.doctor4t.trainmurdermystery.cca.AreasWorldComponent;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Box;

import org.jetbrains.annotations.NotNull;

public class SetPlayAreaCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tmm:setPlayArea")
                        .requires(source -> source.hasPermissionLevel(2))
                            .then(CommandManager.argument("start", BlockPosArgumentType.blockPos())
                            .then(CommandManager.argument("end", BlockPosArgumentType.blockPos())
                            .executes(context -> {
                                AreasWorldComponent.KEY.get(context.getSource().getWorld()).setPlayArea(new Box(
                                    BlockPosArgumentType.getBlockPos(context, "start").toCenterPos(),
                                    BlockPosArgumentType.getBlockPos(context, "end").toBottomCenterPos()));
                                return 1;
                            })))
        );
    }
}
