package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;

import dev.doctor4t.trainmurdermystery.cca.LocationsWorldComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;

public class ListLocationsCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tmm:listLocations")
                        .requires(source -> source.hasPermissionLevel(2))
                        .executes(context -> {
                            LocationsWorldComponent.KEY.get(context.getSource().getWorld()).listLocations(context.getSource());
                            return 1;
                        })
        );
    }
}
