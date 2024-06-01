package dev.redelegends.lobby.listeners.player;

import dev.redelegends.lobby.cmd.ll.BuildCommand;
import dev.redelegends.lobby.utils.TagUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    evt.setQuitMessage(null);
    BuildCommand.remove(evt.getPlayer());
    TagUtils.reset(evt.getPlayer().getName());
  }
}
