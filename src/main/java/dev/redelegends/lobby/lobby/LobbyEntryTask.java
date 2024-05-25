package dev.redelegends.lobby.lobby;

import org.bukkit.scheduler.BukkitRunnable;

import static dev.redelegends.lobby.lobby.Lobby.QUERY;

public class LobbyEntryTask extends BukkitRunnable {
  
  @Override
  public void run() {
    QUERY.forEach(Lobby::fetch);
  }
}
