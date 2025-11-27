package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;

import dev.doctor4t.trainmurdermystery.cca.LocationsWorldComponent;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.math.Box;

import org.jetbrains.annotations.NotNull;

public class AddLocationCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tmm:addLocation")
                        .requires(source -> source.hasPermissionLevel(2))
                            .then(CommandManager.argument("name", StringArgumentType.string())
                            .then(CommandManager.argument("start", BlockPosArgumentType.blockPos())
                            .then(CommandManager.argument("end", BlockPosArgumentType.blockPos())
                            .executes(context -> {
                                LocationsWorldComponent component = LocationsWorldComponent.KEY.get(context.getSource().getWorld());
                                component.addLocation(component.new Location(
                                    StringArgumentType.getString(context, "name"), new Box(
                                    BlockPosArgumentType.getBlockPos(context, "start").toCenterPos(),
                                    BlockPosArgumentType.getBlockPos(context, "end").toCenterPos())));
                                return 1;
                            }))))
        );
    }
}
