package dev.redelegends.lobby.hook.hotbar;


import dev.redelegends.lobby.menus.MenuLobbies;
import dev.redelegends.player.Profile;
import dev.redelegends.player.hotbar.HotbarActionType;

public class LHotbarActionType extends HotbarActionType {
  
  @Override
  public void execute(Profile profile, String action) {
    if (action.equalsIgnoreCase("lobbies")) {
      new MenuLobbies(profile);
    }
  }
}
