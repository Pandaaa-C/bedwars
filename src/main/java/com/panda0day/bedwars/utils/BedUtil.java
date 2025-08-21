package com.panda0day.bedwars.utils;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Bed;
import org.bukkit.block.data.type.Bed.Part;
import org.bukkit.block.BlockFace;

public class BedUtil {
    public static void placeBed(Location base, Material bedMaterial, float yaw) {
        if (base == null || base.getWorld() == null) return;
        if (!bedMaterial.name().endsWith("_BED")) bedMaterial = Material.RED_BED;
        base.getWorld().getChunkAt(base).load();
        BlockFace face = yawToFace(yaw);
        Block foot = base.getBlock();
        Block head = foot.getRelative(face);
        foot.setType(bedMaterial, false);
        head.setType(bedMaterial, false);
        Bed footData = (Bed) foot.getBlockData();
        footData.setPart(Part.FOOT);
        footData.setFacing(face);
        foot.setBlockData(footData, false);
        Bed headData = (Bed) head.getBlockData();
        headData.setPart(Part.HEAD);
        headData.setFacing(face);
        head.setBlockData(headData, false);
    }

    private static BlockFace yawToFace(float yaw) {
        float y = yaw;
        while (y < 0) y += 360F;
        y %= 360F;
        if (y >= 315 || y < 45) return BlockFace.SOUTH;
        if (y < 135) return BlockFace.WEST;
        if (y < 225) return BlockFace.NORTH;
        return BlockFace.EAST;
    }
}
