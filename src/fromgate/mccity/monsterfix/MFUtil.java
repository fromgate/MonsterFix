/*  
 *  MonsterFix (Minecraft bukkit plugin)
 *  (c)2012, fromgate, fromgate@gmail.com
 *  http://dev.bukkit.org/server-mods/monsterfix/
 *    
 *  This file is part of MonsterFix
 *  
 *  WeatherMan is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  WeatherMan is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with WeatherMan.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package fromgate.mccity.monsterfix;

import java.util.Iterator;
import org.bukkit.entity.Player;

public class MFUtil extends FGUtilCore {
	MonsterFix plg;
	public MFUtil(MonsterFix plugin, boolean vcheck, boolean savelng, String language, String devbukkitname, String version_name, String plgcmd, String px){
		super (plugin, vcheck, savelng, language, devbukkitname, version_name, plgcmd, px);
		this.plg = plugin;
		FillMSG();
	}
	
	public void FillMSG(){
		addMSG("key_unknown", "Key unknown...");
		addMSG("msg_gorupnotfound","Group %1% not found.");
		addMSG("msg_help","Help");
		addMSG("mfix_help","%1% - this help"); 
		addMSG("mfix_cfg","%1% - display current configuration"); 
		addMSG("mfix_rst","%1% - reload configuration and restart plugin");
		addMSG("mfix_param","%1% - check variable value");
		addMSG("mfix_paramval","%1% - set the value of variable");
		addMSG("mfix_group","%1% - view group configuration and parameter list");  
		addMSG("mfix_grouponoff","%1% - switch on/off group");
		addMSG("msg_grouplist", "Groups: %1%"); 
		addMSG("msg_toreset","to reset any state");
		addMSG("msg_reset","reset");
		addMSG("msg_cfgreload","MonsterFix config reloaded");
		addMSG("configuration","Configuration");
		addMSG("msg_cfgline1","Mobs. M/Spawned: %1% Damaged: %2% Butcheries: %3%");
		addMSG("msg_cfgline2","Trashcan: %1% Trails: %2% Freezed players: %3%");
		addMSG("msg_cfgthreads","Threads (green - active)");
		addMSG ("save","save");
		addMSG ("mobs","mobs");
		addMSG ("player dmg","player dmg");
		addMSG ("trash","trash");
		addMSG("msg_warnallperm","You are OP, all permissions active :)");
		addMSG("msg_warnplayerperm","Your active MonsteFix permissions: %1%");
		addMSG("msg_warnpalyerempty","You have no any active MonsteFix permissions");
		addMSG("msg_nochatperm","You have not any MonsterFix-chat permissions!");
		addMSG("msg_allowinchat","You can use in chat: ");
		addMSG("msg_flymode","Fly-mode");
		addMSG("msg_canfly","You can fly %1%");
		addMSG("msg_cannotfly","You can not fly!");
		addMSG("msg_flymodecreative","You are in creative mode and you can fly :)");
		addMSG("msg_cmdexample1","Type %1% to see configuration of the selected group");
		addMSG("msg_cmdexample2","Type %1% to switch on/off all grouped features");
		addMSG("msg_groupendis","Group %1% is");
		addMSG("grp_options","Group of options: %1%");
		addMSG("msg_gorupnotfound","Group %1% not found!");
		addMSG("msg_paramnotchanged","Variable %1% was not changed!");
		addMSG("msg_keyunknown","Unknown variable: %1%");
		addMSG("msg_exhausted","You're exhausted and can not sneak!");
		addMSG("msg_headshot","You receive a headshot from %1%");
		addMSG("msg_snowheadshot","Ooops! %1% throws a snowball in your head!");
		addMSG("msg_eggheadshot","Ooops! %1% throws an egg in your head!");
		addMSG("msg_hookheadshot","Ooops! %1% caught you on the hook!");
		addMSG("someone","Someone");
		addMSG("msg_headshot!","Headshot!");
		addMSG("msg_shortbreath","You're short of breath!");
		addMSG("msg_blockcmd","You cannot execute command %1%");
		addMSG("msg_placedenied","You cannot place %1% here");
		addMSG("msg_savingall","Saving all...");
		
	/*	
		addMSG("","");
		addMSG("","");
		addMSG("","");
		addMSG("","");
		addMSG("","");
		addMSG("","");
		addMSG("",""); */
	}


	public void PrintHlp(Player p){
		PrintMsg(p,"&6&lMonsterFix "+des.getVersion()+" | " +MSG("msg_help"));
		PrintMSG(p, "mfix_help","/mfix help"); 
		PrintMSG(p, "mfix_cfg","/mfix cfg"); 
		PrintMSG(p, "mfix_rst","/mfix rst"); 
		PrintMSG(p, "mfix_param","/mfix <parameter>"); 
		PrintMSG(p, "mfix_paramval","/mfix <parameter>=<value>");
		PrintMSG(p, "mfix_group","/mfix <group>"); 
		PrintMSG(p, "mfix_grouponoff","/mfix <group>=<on/off>"); 
		String str = "";
		Iterator<String> itr = plg.cfggroup.keySet().iterator();
		while (itr.hasNext()){
			String grp = itr.next();
			if (str.isEmpty()) str = EnDis(grp, plg.cfggroup.get(grp));
			else str = str+", "+EnDis(grp, plg.cfggroup.get(grp));
		}
		PrintMSG (p, "msg_grouplist", str); 
		plg.DemoColor(p);
	}

	public void PrintCfg(Player p){
		PrintMsg(p,"&6&lMonsterFix v"+des.getVersion()+" &r&6| "+MSG("configuration",'6')+"&6:");
		PrintMSG(p, "msg_cfgline1",plg.mspmobs.size()+";"+plg.mobdmg.size()+";"+plg.butch.size()); //	p.sendMessage(ChatColor.GREEN+"Mobs. M/Spawned: "+ChatColor.AQUA+Integer.toString(plg.mspmobs.size())+ChatColor.GREEN+" Damaged: "+ChatColor.AQUA+Integer.toString(plg.mobdmg.size())+ChatColor.GREEN+" Butcheries: "+ChatColor.AQUA+Integer.toString(plg.butch.size()));
		PrintMSG(p, "msg_cfgline2",plg.trashcan.size()+";"+plg.snowtrails.size()+";"+plg.fl.fplayers.size()); //p.sendMessage(ChatColor.GREEN+"Trashcan: "+ChatColor.AQUA+Integer.toString(plg.trashcan.size())+ChatColor.GREEN+" Trails: "+ChatColor.AQUA+Integer.toString(plg.snowtrails.size())+ChatColor.GREEN+" Freezed players: "+ChatColor.AQUA+Integer.toString(plg.fl.fplayers.size()));
		PrintMsg(p,MSG("msg_cfgthreads")+EnDis(MSGnc("save"),plg.tid_save_b)+"&2, "+EnDis(MSGnc("mobs"),plg.tid_mclear_b)+"&2, "
				+EnDis(MSGnc("player dmg"),plg.tid_pdmg_b)+"&2, "
				//+EnDis("freezer",plg.tid_cncl_b)+ChatColor.GREEN+"&2, "
				+EnDis(MSGnc("trash"),plg.tid_trash_b));
		String str = "";
		Iterator<String> itr = plg.cfggroup.keySet().iterator();
		while (itr.hasNext()){
			String grp = itr.next();
			if (str.isEmpty()) str = EnDis(grp, plg.cfggroup.get(grp));
			else str = str+", "+EnDis(grp, plg.cfggroup.get(grp));
		}
		PrintMSG(p, "msg_grouplist",str);
		p.sendMessage(" ");
		PrintMSG (p,"msg_cmdexample1","/mfix <groupname>",'8','8'); 
		PrintMSG (p,"msg_cmdexample2","/mfix <groupname>=<on/off>",'8','8');
	}
	
	
	
}
