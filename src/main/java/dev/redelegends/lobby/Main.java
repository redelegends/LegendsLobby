package dev.redelegends.lobby;

import dev.redelegends.Core;
import dev.redelegends.lobby.cmd.Commands;
import dev.redelegends.lobby.hook.LCoreHook;
import dev.redelegends.lobby.listeners.Listeners;
import dev.redelegends.lobby.lobby.DeliveryNPC;
import dev.redelegends.lobby.lobby.Lobby;
import dev.redelegends.lobby.lobby.PlayNPC;
import dev.redelegends.lobby.utils.TagUtils;
import dev.redelegends.plugin.LegendsPlugin;
import dev.redelegends.utils.BukkitUtils;

import java.io.File;
import java.io.FileInputStream;

public class Main extends LegendsPlugin {
  
  public static String currentServerName;
  private static Main instance;
  private static boolean validInit;
  
  public static Main getInstance() {
    return instance;
  }
  
  @Override
  public void start() {
    instance = this;
  }
  
  @Override
  public void load() {
  }
  
  @Override
  public void enable() {
    saveDefaultConfig();
    currentServerName = getConfig().getString("lobby");
    if (getConfig().getString("spawn") != null) {
      Core.setLobby(BukkitUtils.deserializeLocation(getConfig().getString("spawn")));
    }
    
    LCoreHook.setupHook();
    Lobby.setupLobbies();
    Listeners.setupListeners();
    Language.setupLanguage();
    PlayNPC.setupNPCs();
    DeliveryNPC.setupNPCs();
    Commands.setupCommands();
    
    validInit = true;
    this.getLogger().info("O plugin foi ativado.");
  }
  
  @Override
  public void disable() {
    if (validInit) {
      PlayNPC.listNPCs().forEach(PlayNPC::destroy);
      DeliveryNPC.listNPCs().forEach(DeliveryNPC::destroy);
      TagUtils.reset();
    }
    
    File update = new File("plugins/LegendsLobby/update", "LegendsLobby.jar");
    if (update.exists()) {
      try {
        this.getFileUtils().deleteFile(new File("plugins/" + update.getName()));
        this.getFileUtils().copyFile(new FileInputStream(update), new File("plugins/" + update.getName()));
        this.getFileUtils().deleteFile(update.getParentFile());
        this.getLogger().info("Update di aplicada.");
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    this.getLogger().info("O plugin foi desativado.");
  }
}
