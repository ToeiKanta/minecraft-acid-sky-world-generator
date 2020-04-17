package com.toeikanta.acidworldgenerator;

import com.sk89q.jnbt.NBTInputStream;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.extent.clipboard.io.SpongeSchematicReader;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockState;
import com.sk89q.worldedit.world.block.BlockType;
import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Bamboo;
import org.bukkit.block.data.type.Leaves;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;

import java.io.*;
import java.util.Random;
import java.util.zip.GZIPInputStream;

public class LandGenerator extends ChunkGenerator{
    int currentHeight = 64;
    AcidWorldGenerator plugin;

    public LandGenerator(AcidWorldGenerator instance) {
        plugin = instance;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        return new Location(world,0,0,0);
    }

    public Clipboard loadSchematic(File file) throws IOException {
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            return reader.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, BiomeGrid biome) {
        ChunkData chunk = createChunkData(world);
        Clipboard clipboard = null;
        try {
            clipboard = loadSchematic(new File(plugin.getDataFolder(),"island" + (new Random().nextInt(Settings.islandNum-1)+1) + ".schem"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Boolean genIsland = new Random().nextInt(100-1)+1 < 5 ? true : false;
//        int xRegion; //= Math.abs(x % 2);
//        if (x % 2 == 0) {
//            xRegion = 0;
//        } else {
//            xRegion = 1;
//        }
//        //
//        int zRegion; //= Math.abs(z % 2);
//        if (z % 2 == 0) {
//            zRegion = 0;
//        } else {
//            zRegion = 1;
//        }

//        plugin.logger.info("origin = " + clipboard.getOrigin().toString());
//        plugin.logger.info("max = " + clipboard.getMaximumPoint().toString());
//        plugin.logger.info("dimension = " + clipboard.getDimensions().toString());
//        plugin.logger.info("block = " + clipboard.getBlock(clipboard.getOrigin().withY(clipboard.getOrigin().getY()-1)).toString());
//        plugin.logger.info("min = " + clipboard.getMinimumPoint().toString());
        int highRand = new Random().nextInt(100-1)+1;
        for(x=0;x<16;x++){
//            int xBlock = (xRegion * 16) + x;
            for(z=0;z<16;z++){
//                int zBlock = (zRegion * 16) + z;
                for(int y=0;y<80;y++){
                    try {
                        if(y<=clipboard.getMaximumPoint().getY() && genIsland){
                            BaseBlock block = clipboard.getFullBlock(getCoordSchem(clipboard,x, y, z));
                            BlockData blockData = BukkitAdapter.adapt(block);
//                            if(blockData.getAsString().contains("LEAVES")){
//                                blockData = (Leaves) blockData;
                               // ((Leaves) blockData).setPersistent(false);
//                                plugin.logger.info("xxx = " + blockData.toString());
//                            }
                            chunk.setBlock(x, y+currentHeight-9+highRand, z, blockData);
                        }
                        if(y<currentHeight){
                            if(chunk.getType(x,y,z) == Material.AIR){
                                chunk.setBlock(x,y,z,Material.WATER);
                            }
                        }
                    }catch (Exception ex){
                        //
                    }
                }

            }
        }
        return chunk;
    }
    private BlockVector3 getCoordSchem(Clipboard clipboard, int blockX, int blockY, int blockZ){
        return BlockVector3.at(blockX+clipboard.getMinimumPoint().getX(),blockY + clipboard.getMinimumPoint().getY(),blockZ + clipboard.getMinimumPoint().getZ());
    }
}
