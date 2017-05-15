package com.github.hexocraft.wss;

import com.github.hexocraft.wss.utils.Box;
import com.github.hexocraft.wss.utils.StructureBox;
import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.github.hexocraft.wss.WssPlugin.instance;
import static com.github.hexocraft.wss.WssPlugin.worldEdit;

/**
 * @author <b>Hexosse</b> (<a href="https://github.com/hexosse">on GitHub</a>))
 */
public class WssApi
{
	public static class WssPlayer
	{
		public boolean enable = false;
		public int maxX = 32;
		public int maxY = 32;
		public int maxZ = 32;
		public List<MaterialData> excluded = Lists.newArrayList();

		public WssPlayer() {}

		public WssPlayer(boolean enable)
		{
			this.enable = enable;
		}

		public WssPlayer(int maxX, int maxY, int maxZ)
		{
			this.maxX = maxX;
			this.maxY = maxY;
			this.maxZ = maxZ;
		}
	}


	private static Map<UUID, WssPlayer> players = new HashMap<>();      // List of players
	private static int                  delay   = 10;                   // Delay(in ticks) before executing asynchronous tasks


	// Get the player
	public static WssPlayer getPlayer(Player player)
	{
		if(player == null)  return null;

		// Get the player if exist
		WssPlayer wssPlayer = players.get(player.getUniqueId());

		// If not create it
		if(wssPlayer == null)
			players.put(player.getUniqueId(), new WssPlayer());

		return players.get(player.getUniqueId());
	}

	// Enable player to create a chest preview
	public static void enable(Player player) { getPlayer(player).enable = true; }

	// Disable player from creating a chest preview
	public static void disable(Player player) { getPlayer(player).enable = false; }

	// Remove all players from the player list
	public static void remove(Player player) { players.remove(player.getUniqueId()); }
	public static void removeAll() { players.clear(); }

	// Get player info
	public static boolean isEnable(Player player) { return getPlayer(player).enable; };
	public static int getMaxX(Player player) { return getPlayer(player).maxX; };
	public static int getMaxY(Player player) { return getPlayer(player).maxY; };
	public static int getMaxZ(Player player) { return getPlayer(player).maxZ; };


	public static void select(Player player, final Location location)
	{
		final WssPlayer wssPlayer = getPlayer(player);

		if(wssPlayer != null)
			select(player, location, wssPlayer.excluded, wssPlayer.maxX, wssPlayer.maxY, wssPlayer.maxZ);
	}

	public static void select(final Player player, final Location location, final List<MaterialData> excluded, final int lengthX, final int lengthY, final int lengthZ)
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				// Get the minimum and maximum selection points
				Box box = new StructureBox(location, excluded, lengthX, lengthY, lengthZ).getBox();

				// Create the WorldEditselection
				worldEdit.select(player, box);
			}
		}.runTaskLaterAsynchronously(instance, delay);
	}
}
