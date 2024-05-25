package dev.redelegends.lobby.listeners.player;

import dev.redelegends.game.Game;
import dev.redelegends.lobby.cmd.LL.BuildCommand;
import dev.redelegends.player.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getWhoClicked() instanceof Player) {
      Player player = (Player) evt.getWhoClicked();
      Profile profile = Profile.getProfile(player.getName());
      
      if (profile != null) {
        Game<?> game = profile.getGame();
        if (game == null) {
          evt.setCancelled(!BuildCommand.isBuilder(player));
        }
      }
    }
  }
}
