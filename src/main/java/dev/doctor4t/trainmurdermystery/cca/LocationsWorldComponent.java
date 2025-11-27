package dev.doctor4t.trainmurdermystery.cca;

import dev.doctor4t.trainmurdermystery.TMM;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Vector;

import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class LocationsWorldComponent implements AutoSyncedComponent {
    public static final ComponentKey<LocationsWorldComponent> KEY = ComponentRegistry.getOrCreate(TMM.id("locations"), LocationsWorldComponent.class);
    private final World world;

    public class Location {
        public Box area;
        public String name;

        public Location(String name, Box area) {
            this.name = name;
            this.area = area;
        }
    }

    public Vector<Location> getLocationsFromNbt(@NotNull NbtCompound tag) {
        int count = tag.getInt("count");
        Vector<Location> output = new Vector<Location>();

        for (int index = 0; index < count; index++) {
            output.add(new Location(
                tag.getString(index + "_name"),
                new Box(
                    tag.getDouble(index + "_MinX"),
                    tag.getDouble(index + "_MinY"),
                    tag.getDouble(index + "_MinZ"),
                    tag.getDouble(index + "_MaxX"),
                    tag.getDouble(index + "_MaxY"),
                    tag.getDouble(index + "_MaxZ")
                    
                )
            ));
        }

        return output;
    }

    public void writeLocationsToNbt(@NotNull NbtCompound tag, Vector<Location> locations) {
        tag.putInt("count", locations.size());

        for (int index = 0; index < locations.size(); index++) {
            tag.putString(index + "_name", locations.get(index).name);
            tag.putDouble(index + "_MinX", locations.get(index).area.minX);
            tag.putDouble(index + "_MinY", locations.get(index).area.minY);
            tag.putDouble(index + "_MinZ", locations.get(index).area.minZ);
            tag.putDouble(index + "_MaxX", locations.get(index).area.maxX);
            tag.putDouble(index + "_MaxY", locations.get(index).area.maxY);
            tag.putDouble(index + "_MaxZ", locations.get(index).area.maxZ);                    
        }
    }

    public void listLocations(@NotNull ServerCommandSource source) {
        MutableText text = Text.literal("Locations by index:").formatted(Formatting.GRAY);

        for(int index = 0; index < locations.size(); index++) {
            text = text.append(Text.literal("\n%d: ".formatted(index))).append(locations.get(index).name).formatted(Formatting.WHITE);
        }

        MutableText finalText = text;
        source.sendFeedback(() -> finalText, false);
    }

    // Game Locations
    private Vector<Location> locations = new Vector<Location>();

    public Vector<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        locations.add(location);
        sync();
    }

    public void removeLocation(int locationIndex) {
        locations.remove(locationIndex);
        sync();
    }

    public int locationCount() {
        return locations.size();
    }

    public LocationsWorldComponent(World world) {
        this.world = world;
    }

    public void sync() {
        KEY.sync(this.world);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        locations = getLocationsFromNbt(tag);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        writeLocationsToNbt(tag, locations);
    }
}
