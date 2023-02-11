package cc.koffeecreations.main.Sickle;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public final class SickleEvent extends Event {
  private static final HandlerList handlers = new HandlerList();
  
  private Player player;
  
  private Block block;
  
  public SickleEvent(Player player, Block block) {
    this.player = player;
    this.block = block;
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public Block getBlock() {
    return this.block;
  }
  
  public HandlerList getHandlers() {
    return handlers;
  }
  
  public static HandlerList getHandlerList() {
    return handlers;
  }
}

