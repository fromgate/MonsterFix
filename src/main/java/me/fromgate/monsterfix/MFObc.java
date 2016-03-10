/*  
 *  MonsterFix (Minecraft bukkit plugin)
 *  (c)2012, fromgate, fromgate@gmail.com
 *  http://dev.bukkit.org/server-mods/monsterfix/
 *    
 *  This file is part of MonsterFix
 *  
 *  MonsterFix is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MonsterFix is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MonsterFix.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package me.fromgate.monsterfix;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MFObc {

	private static String version = "";
	//private static String [] tested_versions = {"v1_9_R1"};
	private static String cboPrefix = "org.bukkit.craftbukkit.";
	private static String nmsPrefix = "net.minecraft.server.";
	private static boolean blocked = false;

	private static Class<?> CraftEntity;
	private static Field CraftEntity_entity;
	private static Class<?> EntityPlayer;
	private static Field EntityPlayer_invulnerableTicks; 

	public static void init(){
		try{
			Object s = Bukkit.getServer();
			Method m = s.getClass().getMethod("getHandle");
			Object cs = m.invoke(s);
			String className = cs.getClass().getName();
			String [] v = className.split("\\.");
			if (v.length==5){
				version = v[3];
				cboPrefix = "org.bukkit.craftbukkit."+version+".";
				nmsPrefix = "net.minecraft.server."+version+".";;
			}
			CraftEntity = cboClass("entity.CraftEntity");
			CraftEntity_entity = CraftEntity.getDeclaredField("entity");
			CraftEntity_entity.setAccessible(true);
			EntityPlayer = nmsClass("EntityPlayer");
			EntityPlayer_invulnerableTicks = EntityPlayer.getDeclaredField("invulnerableTicks");
		} catch (Exception e){
			blocked = true;
			e.printStackTrace();
		}
	}

	public static String getMinecraftVersion(){
		return version;
	}

	public static boolean isBlocked(){
		return blocked;
	}
	/*
	public static boolean isTestedVersion(){
		if (version.isEmpty()) return true;
		for (int i = 0; i< tested_versions.length;i++){
			if (tested_versions[i].equalsIgnoreCase(version)) return true;
		}
		return false;
	} */


	private static Class<?> nmsClass(String classname) throws Exception{
		return Class.forName(nmsPrefix+classname);
	}


	private static Class<?> cboClass(String classname) throws Exception{
		return Class.forName(cboPrefix+classname);
	}



	public static int getPlayerInvulTick (Player p){
		if (blocked) return -1;
		try {
			Object craftEntity = p;
			Object nmsPlayer = CraftEntity_entity.get(craftEntity);
			if (!EntityPlayer.isInstance(nmsPlayer)) return -1;
			return EntityPlayer_invulnerableTicks.getInt(nmsPlayer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void setPlayerInvulTick (Player p, int ticks){
		if (blocked) return;
		try {
			Object craftEntity = p;
			Object nmsPlayer = CraftEntity_entity.get(craftEntity);
			if (!EntityPlayer.isInstance(nmsPlayer)) return;
			EntityPlayer_invulnerableTicks.set(nmsPlayer, ticks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
