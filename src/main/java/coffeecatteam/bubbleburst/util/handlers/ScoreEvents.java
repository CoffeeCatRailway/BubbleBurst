package coffeecatteam.bubbleburst.util.handlers;

import coffeecatteam.bubbleburst.util.handlers.score.ScoreboardHandler;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.io.IOException;

public class ScoreEvents {

    @SubscribeEvent
    public void load(WorldEvent.Load event) {
        if (event.getWorld().provider.getDimension() == 0) {
            try {
                File data = new File(DimensionManager.getCurrentSaveRootDirectory(), "scores.dat");
                if (!data.exists()) {
                    return;
                }

                NBTTagCompound nbt = CompressedStreamTools.read(data);
                if (nbt != null) {
                    ScoreboardHandler.INSTANCE.load(nbt);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribeEvent
    public void save(WorldEvent.Save event) {
        if (event.getWorld().provider.getDimension() == 0) {
            try {
                File data = new File(DimensionManager.getCurrentSaveRootDirectory(), "scores.dat");
                if (!data.exists()) {
                    data.createNewFile();
                }

                NBTTagCompound nbt = new NBTTagCompound();
                ScoreboardHandler.INSTANCE.save(nbt);
                CompressedStreamTools.write(nbt, data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
