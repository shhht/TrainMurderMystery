package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;

import dev.doctor4t.trainmurdermystery.cca.AreasWorldComponent;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Box;

import org.jetbrains.annotations.NotNull;

public class SetResetPasteAreaCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tmm:setResetPasteArea")
                        .requires(source -> source.hasPermissionLevel(2))
                            .then(CommandManager.argument("from", BlockPosArgumentType.blockPos())
                            .then(CommandManager.argument("to", BlockPosArgumentType.blockPos())
                            .executes(context -> {
                                var areas = AreasWorldComponent.KEY.get(context.getSource().getWorld());
                                var offset = 
                                    BlockPosArgumentType.getBlockPos(context, "from").toCenterPos().relativize(
                                    BlockPosArgumentType.getBlockPos(context, "to").toBottomCenterPos());
                                areas.setResetPasteArea(areas.getResetTemplateArea().offset(offset));
                                return 1;
                            })))
        );
    }
}
