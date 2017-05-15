package com.github.hexocraft.wss.utils;


/*
 * Copyright 2017 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This file is part of AddLight
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class StructureBox
{
    private static BlockFace[] CARDINALS;

    static { CARDINALS = new BlockFace[]{ BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST, BlockFace.WEST, BlockFace.NORTH_WEST}; }

    private final Location location;
    private final int maxX;
    private final int maxY;
    private final int maxZ;
    private final List<MaterialData> excluded;

    private List<Location> unchecked = null;
    private List<Location> confirmed = null;
    private Box            box       = null;

    public StructureBox(Location location, int maxX, int maxY, int maxZ)
    {
        this(location, null, maxX, maxY, maxZ);
    }

    public StructureBox(Location location, List<MaterialData> excluded, int maxX, int maxY, int maxZ)
    {
        this.location = location;
        this.excluded = excluded;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public synchronized Box getBox()
    {
        if(box != null)
            return box;

        findBox();

        return box;
    }

    private int findBox()
    {
        // List of location to check.
        unchecked = Collections.synchronizedList(new ArrayList<Location>());
        // List of location already checked and valid.
        confirmed = Collections.synchronizedList(new ArrayList<Location>());
        // Start a new Box
        box = null;

        // Add the first location (the starting point) as a location to check
        unchecked.add(location);

        // Now it's time to find the bounding box of the structure
        while(unchecked.size() > 0 && confirmed.size() <= (maxX * maxY * maxZ))
        {
            Location uncheckedLocation = unchecked.get(0);
            Block uncheckedBlock = unchecked.get(0).getBlock();

            if(!isValid(uncheckedBlock))
            {
                unchecked.remove(uncheckedLocation);
            }
            else
            {
                unchecked.remove(uncheckedLocation);
                confirmed.add(uncheckedLocation);

                // Add the block to the StructureBox
                if(box == null) box = new Box(uncheckedLocation.getBlock()); else box.add(uncheckedLocation.getBlock());

                // UP
                Block up = uncheckedBlock.getRelative(BlockFace.UP);
                addCandidate(up, maxX, maxY, maxZ);
                for(BlockFace cardinal : CARDINALS)
                    addCandidate(up.getRelative(cardinal), maxX, maxY, maxZ);

                // SELF
                for(BlockFace cardinal : CARDINALS)
                    addCandidate(uncheckedBlock.getRelative(cardinal), maxX, maxY, maxZ);

                // DOWN
                Block down = uncheckedBlock.getRelative(BlockFace.DOWN);
                addCandidate(down, maxX, maxY, maxZ);
                for(BlockFace cardinal : CARDINALS)
                    addCandidate(down.getRelative(cardinal), maxX, maxY, maxZ);
            }
        }

        return confirmed.size();
    }

    // What is a valid a block ?
    // - a block is a block not AIR
    // - a block can be any block except those with a material in the excluded list
    protected boolean isValid(Block toCheck)
    {
        if(toCheck.getType() == Material.AIR)
            return false;

        if(excluded!=null && excluded.contains(toCheck.getState().getData()))
            return false;

        return true;
    }

    /**
     * A candidate block is block that must be check
     * @param candidate The block wichh will be checked
     */
    protected void addCandidate(Block candidate, int maxX, int maxY, int maxZ) {
        if(isUnchecked(candidate) || isConfirmed(candidate))
            return;

        // The candidate block must not expand the box outside the predifined limits
        Box tempBox = new Box(box.getLower(), box.getUpper());
        tempBox.add(candidate);

        //
        if(tempBox.getLengthX() > maxX)  return;
        if(tempBox.getLengthY() > maxY)  return;
        if(tempBox.getLengthZ() > maxZ)  return;

        unchecked.add(candidate.getLocation());
    }

    // Test if the location is in the unchecked list
    protected boolean isUnchecked(Location location)
    {
        return unchecked.contains(location);
    }

    // Test if the block is in the unchecked list
    protected boolean isUnchecked(Block block)
    {
        return isUnchecked(block.getLocation());
    }

    // Test if the block is in the confirmed list
    protected boolean isConfirmed(Location location)
    {
        return confirmed.contains(location);
    }

    // Test if the block is in the confirmed list
    protected boolean isConfirmed(Block block)
    {
        return isConfirmed(block.getLocation());
    }
}
