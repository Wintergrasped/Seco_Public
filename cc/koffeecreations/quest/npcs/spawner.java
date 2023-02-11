package cc.koffeecreations.quest.npcs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import dev.sergiferry.playernpc.api.NPC;
import dev.sergiferry.playernpc.api.NPCLib;

public class spawner {

	public Plugin pl;
	
	public spawner(Plugin p) {
		pl = p;
	}
	
	
	public void spawnJohn(Player p, Location L) {
    	
    	NPC.Personal npc = NPCLib.getInstance().generatePersonalNPC(p, pl, "john", L);
    	
    	List<String> info = new ArrayList();
    	info.add("John");
    	npc.setText(info);
    	npc.setCollidable(true);
    	
    	npc.create();
    	npc.show();
	}
	
	public void spawnPlayer( String Name,String inf, Player p, Location L) {
		NPC.Personal npc = NPCLib.getInstance().generatePersonalNPC(p, pl, Name, L);
    	
    	List<String> info = new ArrayList();
    	info.add(Name);
    	info.add(inf);
    	npc.setText(info);
    	npc.setCollidable(true);
    	
    	npc.create();
    	npc.show();
	}
	
	public void spawnGlobal(String Name,String inf, Location L) {
		NPC.Global npc = NPCLib.getInstance().generateGlobalNPC(pl, Name, L);
    	
    	List<String> info = new ArrayList();
    	info.add(Name);
    	info.add(inf);
    	npc.setText(info);
    	npc.setCollidable(true);
    	
    	npc.setAutoShow(true);
    	
	}
	
}
