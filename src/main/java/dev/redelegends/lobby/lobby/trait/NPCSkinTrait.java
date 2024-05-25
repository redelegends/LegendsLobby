package dev.redelegends.lobby.lobby.trait;


import dev.redelegends.libraries.npclib.api.npc.NPC;
import dev.redelegends.libraries.npclib.npc.skin.Skin;
import dev.redelegends.libraries.npclib.npc.skin.SkinnableEntity;
import dev.redelegends.libraries.npclib.trait.NPCTrait;

public class NPCSkinTrait extends NPCTrait {
  
  private final Skin skin;
  
  public NPCSkinTrait(NPC npc, String value, String signature) {
    super(npc);
    this.skin = Skin.fromData(value, signature);
  }
  
  @Override
  public void onSpawn() {
    this.skin.apply((SkinnableEntity) this.getNPC().getEntity());
  }
}
