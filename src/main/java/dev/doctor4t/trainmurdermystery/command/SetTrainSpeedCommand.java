package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import dev.doctor4t.trainmurdermystery.cca.TrainWorldComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import org.jetbrains.annotations.NotNull;

public class SetTrainSpeedCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tmm:setTrainSpeed")
                        .requires(source -> source.hasPermissionLevel(2))
                            .then(CommandManager.argument("speed", IntegerArgumentType.integer(0))
                            .executes(context -> {
                                TrainWorldComponent.KEY.get(context.getSource().getWorld()).setSpeed(IntegerArgumentType.getInteger(context, "speed"));
                                return 1;
                            }))
        );
    }
}
