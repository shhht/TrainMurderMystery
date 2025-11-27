package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;

import dev.doctor4t.trainmurdermystery.cca.AreasWorldComponent;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Box;

import org.jetbrains.annotations.NotNull;

public class SetPlayAreaOffsetCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tmm:setReadyAreaOffset")
                        .requires(source -> source.hasPermissionLevel(2))
                            .then(CommandManager.argument("from", BlockPosArgumentType.blockPos())
                            .then(CommandManager.argument("to", BlockPosArgumentType.blockPos())
                            .executes(context -> {
                                AreasWorldComponent.KEY.get(context.getSource().getWorld()).setPlayAreaOffset(
                                    BlockPosArgumentType.getBlockPos(context, "from").toCenterPos().relativize(
                                        BlockPosArgumentType.getBlockPos(context, "to").toBottomCenterPos()));
                                return 1;
                            })))
        );
    }
}
