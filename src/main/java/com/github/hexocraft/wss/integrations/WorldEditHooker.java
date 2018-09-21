package com.github.hexocraft.wss.integrations;

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

import com.github.hexocraft.wss.utils.Box;
import com.github.hexocraftapi.integration.Hooker;
import com.github.hexocraftapi.message.predifined.message.WarningMessage;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.extension.platform.permission.ActorSelectorLimits;
import com.sk89q.worldedit.regions.selector.CuboidRegionSelector;
import org.bukkit.entity.Player;

public class WorldEditHooker extends Hooker<com.sk89q.worldedit.bukkit.WorldEditPlugin, WorldEditHooker> {
    public WorldEditHooker() {
        super();
    }

    // Capture the plugin if exist
    public WorldEditHooker capture(com.sk89q.worldedit.bukkit.WorldEditPlugin worldEditPlugin) {
        this.plugin = worldEditPlugin;
        return this;
    }

    public void select(Player player, Box box) {
        if(player == null) throw new IllegalArgumentException("Null player not allowed");
        if(!player.isOnline()) throw new IllegalArgumentException("Offline player not allowed");

        //
        BukkitPlayer worldEditPlayer = plugin.wrapPlayer(player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(worldEditPlayer);

        //
        if(box == null) {
            session.getRegionSelector(worldEditPlayer.getWorld()).clear();
            session.dispatchCUISelection(worldEditPlayer);
            return;
        }
        else {
            Vector vLower = new Vector(box.getLower().getX(), box.getLower().getY(), box.getLower().getZ());
            Vector vUpper = new Vector(box.getUpper().getX() - 1, box.getUpper().getY() - 1, box.getUpper().getZ() - 1);

            CuboidRegionSelector selector = new CuboidRegionSelector(worldEditPlayer.getWorld());
            selector.selectPrimary(vLower, ActorSelectorLimits.forActor(worldEditPlayer));
            selector.selectSecondary(vUpper, ActorSelectorLimits.forActor(worldEditPlayer));
            session.setRegionSelector(worldEditPlayer.getWorld(), selector);
            session.dispatchCUISelection(worldEditPlayer);

            WarningMessage.toPlayer(player, "Structure selection done.");
            WarningMessage.toPlayer(player, "Pos1 set to " + vLower.toString());
            WarningMessage.toPlayer(player, "Pos2 set to " + vUpper.toString());
        }
    }
}
