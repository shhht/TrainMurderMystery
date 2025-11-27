package dev.doctor4t.trainmurdermystery.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import dev.doctor4t.trainmurdermystery.cca.LocationsWorldComponent;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import org.jetbrains.annotations.NotNull;

public class RemoveLocationCommand {
    public static void register(@NotNull CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("tmm:removeLocation")
                        .requires(source -> source.hasPermissionLevel(2))
                            .then(CommandManager.argument("index", IntegerArgumentType.integer(0))
                            .executes(context -> {
                                LocationsWorldComponent component = LocationsWorldComponent.KEY.get(context.getSource().getWorld());
                                int index = IntegerArgumentType.getInteger(context, "index");

                                if (index >= component.locationCount()) {
                                    Text text = Text.literal("A location with index: " + index + " does not exist!").formatted(Formatting.RED);
                                    context.getSource().sendFeedback(() -> text, false);
                                    return 1;
                                }
                                
                                component.removeLocation(index);
                                return 1;
                            }))
        );
    }
}
