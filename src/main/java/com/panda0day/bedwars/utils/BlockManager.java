package com.panda0day.bedwars.utils;

import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class BlockManager {
    private static final List<Block> breakableBlocks = new ArrayList<Block>();

    public static List<Block> getBreakableBlocks() {
        return breakableBlocks;
    }

    public static void addBreakableBlock(Block block) {
        breakableBlocks.add(block);
    }

    public static void removeBreakableBlock(Block block) {
        breakableBlocks.remove(block);
    }

    public static boolean isBreakableBlock(Block block) {
        return breakableBlocks.contains(block);
    }
}
