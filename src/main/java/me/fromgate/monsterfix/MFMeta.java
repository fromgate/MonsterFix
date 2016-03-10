package me.fromgate.monsterfix;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;

public class MFMeta {
	private static MonsterFix plg;
	private static String mpx = "mfix-"; 
	
	public static void init(MonsterFix plugin){
		plg = plugin;
	}
	
	public static boolean hasMeta (Entity e, String meta){
		return e.hasMetadata(mpx+meta);
	}
	
	public static void setMeta (Entity e, String meta){
		e.setMetadata(mpx+meta, new FixedMetadataValue (plg, true));
	}
	
	public static void removeMeta (Entity e, String meta){
		if (hasMeta(e,meta))
			e.removeMetadata(mpx+meta, plg);
	}

}
