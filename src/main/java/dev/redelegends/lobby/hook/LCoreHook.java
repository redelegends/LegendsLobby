package dev.redelegends.lobby.hook;

import com.comphenix.protocol.ProtocolLibrary;
import dev.redelegends.lobby.hook.hotbar.LHotbarActionType;
import dev.redelegends.lobby.hook.protocollib.HologramAdapter;
import dev.redelegends.lobby.Language;
import dev.redelegends.lobby.Main;
import dev.redelegends.player.Profile;
import dev.redelegends.player.hotbar.Hotbar;
import dev.redelegends.player.hotbar.HotbarAction;
import dev.redelegends.player.hotbar.HotbarActionType;
import dev.redelegends.player.hotbar.HotbarButton;
import dev.redelegends.player.scoreboard.LegendsScoreboard;
import dev.redelegends.player.scoreboard.scroller.ScoreboardScroller;
import dev.redelegends.plugin.config.LegendsConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class LCoreHook {
  
  public static void setupHook() {
    setupHotbars();
    new BukkitRunnable() {
      @Override
      public void run() {
        Profile.listProfiles().forEach(profile -> {
          if (profile.getScoreboard() != null) {
            profile.getScoreboard().scroll();
          }
        });
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, Language.scoreboards$scroller$every_tick);
    
    new BukkitRunnable() {
      @Override
      public void run() {
        Profile.listProfiles().forEach(profile -> {
          if (!profile.playingGame() && profile.getScoreboard() != null) {
            profile.update();
          }
        });
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
    
    ProtocolLibrary.getProtocolManager().addPacketListener(new HologramAdapter());
  }
  
  public static void reloadScoreboard(Profile profile) {
    Player player = profile.getPlayer();
    List<String> lines = new ArrayList<>(Language.scoreboards$lobby);
    Collections.reverse(lines);
    profile.setScoreboard(new LegendsScoreboard() {
      @Override
      public void update() {
        this.updateHealth();
        for (int index = 0; index < lines.size(); index++) {
          String line = lines.get(index);
          line = PlaceholderAPI.setPlaceholders(player, line);
          
          this.add(index + 1, line);
        }
      }
    }.scroller(new ScoreboardScroller(Language.scoreboards$scroller$titles)).to(profile.getPlayer()).build());
    profile.update();
    profile.getScoreboard().scroll();
  }
  
  private static void setupHotbars() {
    HotbarActionType.addActionType("lobby", new LHotbarActionType());
    
    LegendsConfig config = Main.getInstance().getConfig("hotbar");
    for (String id : new String[]{"lobby"}) {
      Hotbar hotbar = new Hotbar(id);
      
      ConfigurationSection hb = config.getSection(id);
      for (String button : hb.getKeys(false)) {
        try {
          hotbar.getButtons().add(new HotbarButton(hb.getInt(button + ".slot"), new HotbarAction(hb.getString(button + ".execute")), hb.getString(button + ".icon")));
        } catch (Exception ex) {
          Main.getInstance().getLogger().log(Level.WARNING, "Falha ao carregar o botao \"" + button + "\" da hotbar \"" + id + "\": ", ex);
        }
      }
      
      Hotbar.addHotbar(hotbar);
    }
  }
}
