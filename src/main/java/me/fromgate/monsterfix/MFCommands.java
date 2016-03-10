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


import java.util.Iterator;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MFCommands implements CommandExecutor{
	MonsterFix plg;
	MFUtil u;

	public MFCommands (MonsterFix plg) {
		this.plg=plg;
		this.u = plg.u;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		if (sender instanceof Player) {
			boolean cmdok=false; 
			Player p = (Player) sender;
			if (p.isOp()||p.hasPermission("monsterfix.config")) {
				if (args.length>0) {

					boolean cfgchanged = false;

					if (args[0].equalsIgnoreCase("fly")) {
						plg.ToggleFly(p);
						return true;
					} else if (args[0].equalsIgnoreCase("rst")) {
						plg.ClearTrash();
						plg.reloadCfg();
						plg.InitCfg();
						plg.loadGroupsCfg();
						plg.UpdateFastVar();
						plg.Rst();
						u.printMSG(p, "msg_cfgreload"); 
						
						return true;
					} else if (args[0].equalsIgnoreCase("cfg")) {
						u.PrintCfg(p);
						return true;

					} else if (args[0].equalsIgnoreCase("help")) {
						u.PrintHlp(p);
						return true;


					} else for (int i = 0; i< args.length;i++){

						Iterator<String> itr = plg.cfggroup.keySet().iterator();
						while (itr.hasNext()){
							String grp = itr.next();
							if (args[i].equalsIgnoreCase(grp)) {
								cmdok=true;
								p.sendMessage(ChatColor.GOLD+"MonsteFix "+u.des.getVersion()+" | "+u.getMSG("configuration",'6')); 
								plg.PrintGrp(p, args[i]);
							}  else if (args[i].startsWith(grp+"=")){
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									plg.cfggroup.put(grp, ln[1].equalsIgnoreCase("on")||ln[1].equalsIgnoreCase("true")||ln[1].equalsIgnoreCase("enable"));
								} else plg.cfggroup.put(grp, false);
								cfgchanged = true;
								u.printPxMsg(p, u.getMSG("msg_groupendis",'a','6',grp)+" "+u.EnDis(plg.cfggroup.get(grp)));
							}
						}



						for (MFBool c : plg.cfgb) {
							if (args[i].equalsIgnoreCase(c.name)) {
								cmdok=true;
								plg.printParam(p, c.name);
							} else if (args[i].startsWith(c.name+"=")){
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									c.v = ln[1].equalsIgnoreCase("on")||ln[1].equalsIgnoreCase("true")||ln[1].equalsIgnoreCase("enable");
									cfgchanged = true;
								} else c.v=false;
								plg.printParam(p, c.name);
							}
						}

						for (MFInt c : plg.cfgi) {
							if (args[i].equalsIgnoreCase(c.name)) {
								cmdok=true;
								plg.printParam(p, c.name);
							} else if (args[i].startsWith(c.name+"=")){
								boolean st = false;
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									if (ln[1].matches("[0-9]+[0-9]*")) {
										c.v = Integer.parseInt(ln[1]);
										st = true;
										cfgchanged = true;
									}
								}
								plg.printParam(p, c.name);
								if (!st) u.printMSG(p, "msg_paramnotchanged"); 
							}
						}

						for (MFStr c : plg.cfgs) {
							if (args[i].equalsIgnoreCase(c.name)) {
								cmdok=true;
								plg.printParam(p, c.name);
							} else if (args[i].startsWith(c.name+"=")){
								boolean st = false;
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									c.v = ln[1];
									st = true;
									cfgchanged = true;
								}
								plg.printParam(p, c.name);
								if (!st) u.printMSG(p, "msg_paramnotchanged"); 
							}
						}

						for (MFFloat c : plg.cfgf) {
							if (args[i].equalsIgnoreCase(c.name)) {
								cmdok=true;
								plg.printParam(p, c.name);
							} else if (args[i].startsWith(c.name+"=")){
								boolean st = false;
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									if (ln[1].matches("[0-9]+\\.[0-9]+")||ln[1].matches("[1-9]+[0-9]*")) {
										c.v = Float.parseFloat(ln[1]);
										st = true;
										cfgchanged = true;
									}
								}
								plg.printParam(p, c.name);
								if (!st) u.printMSG(p, "msg_paramnotchanged"); 
							}
						}
					}
					if (cfgchanged) {
						//plg.SaveCfg();
						plg.saveGroupsCfg();
						plg.UpdateFastVar();
						plg.Rst();
						return true;
					}
				}
			} else {
				if (args.length==1){
					if (args[0].equalsIgnoreCase("help")){
						if (p.hasPermission("monsterfix.fly.flymode")&&plg.cfgB("flymode"))
							u.printMSG(p, "msg_canfly","(/mfix fly)");
						plg.DemoColor(p);
						cmdok = true;					
					} else if (args[0].equalsIgnoreCase("fly")){
						if (p.hasPermission("monsterfix.fly.flymode")&&plg.cfgB("flymode")) plg.ToggleFly(p);
						else u.printMSG(p, "msg_cannotfly",'c');
					}
				}

			}
			return cmdok;
		}
		return false;
	}
}
