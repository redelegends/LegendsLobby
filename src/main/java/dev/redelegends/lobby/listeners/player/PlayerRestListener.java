package dev.redelegends.lobby.listeners.player;

import dev.redelegends.game.Game;
import dev.redelegends.lobby.cmd.LL.BuildCommand;
import dev.redelegends.player.Profile;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerRestListener implements Listener {
  
  @EventHandler
  public void onPlayerConsume(PlayerItemConsumeEvent evt) {
    if (evt.getItem().getType() == Material.GOLDEN_APPLE) {
      evt.getPlayer().setHealth(20.0);
    }
  }
  
  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      evt.setCancelled(true);
    }
  }
  
  @EventHandler
  public void onPlayerPickupItem(PlayerPickupItemEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      evt.setCancelled(profile.getGame() == null);
    }
  }
  
  @EventHandler
  public void onBlockBreak(BlockBreakEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      Game<?> game = profile.getGame();
      if (game == null) {
        evt.setCancelled(!BuildCommand.isBuilder(evt.getPlayer()));
      }
  
    }
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      Game<?> game = profile.getGame();
      if (game == null) {
        evt.setCancelled(!BuildCommand.isBuilder(evt.getPlayer()));
      }
  
    }
  }
}
