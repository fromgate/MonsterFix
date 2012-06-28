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

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MFCommands implements CommandExecutor{
	MonsterFix plg;
	String px;

	public MFCommands (MonsterFix plg) {
		this.plg=plg;
		this.px = plg.px;
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
						plg.InitCfg();
						plg.LoadCfg();
						plg.UpdateFastVar();
						plg.Rst();
						p.sendMessage(plg.px+"MonsterFix config reloaded");
						return true;
					} else if (args[0].equalsIgnoreCase("cfg")) {
						p.sendMessage(ChatColor.GOLD+"MonsteFix "+plg.des.getVersion()+" Configuration:");
						p.sendMessage(ChatColor.GREEN+"Mobs. M/Spawned: "+ChatColor.AQUA+Integer.toString(plg.mspmobs.size())+ChatColor.GREEN+" Damaged: "+ChatColor.AQUA+Integer.toString(plg.mobdmg.size())
								+ChatColor.GREEN+" Butcheries: "+ChatColor.AQUA+Integer.toString(plg.butch.size()));
						p.sendMessage(ChatColor.GREEN+"Trashcan: "+ChatColor.AQUA+Integer.toString(plg.trashcan.size())+ChatColor.GREEN+" Trails: "+ChatColor.AQUA+Integer.toString(plg.snowtrails.size())+ChatColor.GREEN+" Freezed players: "+ChatColor.AQUA+Integer.toString(plg.fl.fplayers.size()));

						p.sendMessage(ChatColor.GREEN+"Threads (green - active): "+plg.EnDis("save",plg.tid_save_b)+ChatColor.GREEN+", "+plg.EnDis("mobs",plg.tid_mclear_b)+ChatColor.GREEN+", "
								+plg.EnDis("player dmg",plg.tid_pdmg_b)+ChatColor.GREEN+", "
								//+plg.EnDis("freezer",plg.tid_cncl_b)+ChatColor.GREEN+", "
								+plg.EnDis("trash",plg.tid_trash_b));
						String str = "";

						Iterator<String> itr = plg.cfggroup.keySet().iterator();
						while (itr.hasNext()){
							String grp = itr.next();
							if (str.isEmpty()) str = plg.EnDis(grp, plg.cfggroup.get(grp));
							else str = str+", "+plg.EnDis(grp, plg.cfggroup.get(grp));
						}

						p.sendMessage(ChatColor.DARK_GREEN+"Groups: "+str);
						p.sendMessage(" ");
						p.sendMessage(ChatColor.DARK_GRAY+"Type /mfix <groupname> to see configuration of the selected group");
						p.sendMessage(ChatColor.DARK_GRAY+"Type /mfix <groupname>=<on/off> to switch on/off all grouped features");
						return true;
						//}


					} else if (args[0].equalsIgnoreCase("help")) { 

						p.sendMessage(ChatColor.GOLD+"MonsteFix "+plg.des.getVersion()+" Help:");
						p.sendMessage(ChatColor.AQUA+"/mfix help"+ChatColor.WHITE+" - this help");
						p.sendMessage(ChatColor.AQUA+"/mfix cfg"+ChatColor.WHITE+" - display current configuration");
						p.sendMessage(ChatColor.AQUA+"/mfix rst"+ChatColor.WHITE+" - reload configuration and restart plugin");
						p.sendMessage(ChatColor.AQUA+"/mfix <parameter>"+ChatColor.WHITE+" - check variable value");
						p.sendMessage(ChatColor.AQUA+"/mfix <parameter>=<value>"+ChatColor.WHITE+" - set the value of variable");
						p.sendMessage(ChatColor.AQUA+"/mfix <group>"+ChatColor.WHITE+" - view group configuration and parameter list");
						p.sendMessage(ChatColor.AQUA+"/mfix <group>=<on/off>"+ChatColor.WHITE+" - switch on/off group");
						String str = "";
						Iterator<String> itr = plg.cfggroup.keySet().iterator();
						while (itr.hasNext()){
							String grp = itr.next();
							if (str.isEmpty()) str = plg.EnDis(grp, plg.cfggroup.get(grp));
							else str = str+", "+plg.EnDis(grp, plg.cfggroup.get(grp));
						}
						p.sendMessage(ChatColor.DARK_GREEN+"Groups: "+str);
						plg.DemoColor(p);

						return true;

					} else for (int i = 0; i< args.length;i++){

						Iterator<String> itr = plg.cfggroup.keySet().iterator();
						while (itr.hasNext()){
							String grp = itr.next();
							if (args[i].equalsIgnoreCase(grp)) {
								cmdok=true;
								p.sendMessage(ChatColor.GOLD+"MonsteFix "+plg.des.getVersion()+" Configuration: ");
								p.sendMessage(plg.px+"Group "+grp+" is "+plg.EnDis(plg.cfggroup.get(grp)));
								plg.PrintGrp(p, args[i]);

							}  else if (args[i].startsWith(grp+"=")){
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									plg.cfggroup.put(grp, ln[1].equalsIgnoreCase("on")||ln[1].equalsIgnoreCase("true")||ln[1].equalsIgnoreCase("enable"));
								} else plg.cfggroup.put(grp, false);
								cfgchanged = true;
								p.sendMessage(plg.px+ChatColor.GOLD+"Group "+grp+" is "+plg.EnDis(plg.cfggroup.get(grp)));
							}
						}



						for (MFBool c : plg.cfgb) {
							if (args[i].equalsIgnoreCase(c.name)) {
								cmdok=true;
								p.sendMessage(plg.px+c.txt+" "+plg.EnDis(c.v));
							} else if (args[i].startsWith(c.name+"=")){
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									c.v = ln[1].equalsIgnoreCase("on")||ln[1].equalsIgnoreCase("true")||ln[1].equalsIgnoreCase("enable");
									cfgchanged = true;
								} else c.v=false;

								p.sendMessage(plg.px+ChatColor.GOLD+c.txt+" "+plg.EnDis(c.v));
							}

						}

						for (MFInt c : plg.cfgi) {
							if (args[i].equalsIgnoreCase(c.name)) {
								cmdok=true;
								p.sendMessage(plg.px+c.txt+" is set to: "+Integer.toString(c.v));
							} else if (args[i].startsWith(c.name+"=")){
								boolean st = false;
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									if (ln[1].matches("[1-9]+[0-9]*")) {
										c.v = Integer.parseInt(ln[1]);
										st = true;
										cfgchanged = true;
									}
								}
								if (st) p.sendMessage(plg.px+ChatColor.GOLD+c.txt+" is set to: "+ChatColor.GREEN+Integer.toString(c.v));
								else { 
									p.sendMessage(plg.px+"Variable "+ChatColor.GREEN+c.name+ChatColor.WHITE+" was not changed.");
									p.sendMessage(plg.px+c.txt+" is set to: "+ChatColor.GREEN+Integer.toString(c.v));
								}
							}
						}

						for (MFStr c : plg.cfgs) {
							if (args[i].equalsIgnoreCase(c.name)) {
								cmdok=true;
								p.sendMessage(plg.px+c.txt+" "+c.v);
							} else if (args[i].startsWith(c.name+"=")){
								boolean st = false;
								String [] ln;
								ln = args[i].split("=");
								if (ln.length == 2) {
									c.v = ln[1];
									st = true;
									cfgchanged = true;

								}
								if (st) p.sendMessage(plg.px+ChatColor.GOLD+c.txt+" "+c.v);
								else { 
									p.sendMessage(plg.px+"Variable "+ChatColor.GREEN+c.name+ChatColor.WHITE+" was not changed.");
									p.sendMessage(plg.px+c.txt+" is set to: "+ChatColor.GREEN+c.v);
								}
							}
						}

						for (MFFloat c : plg.cfgf) {
							if (args[i].equalsIgnoreCase(c.name)) {
								cmdok=true;
								p.sendMessage(plg.px+c.txt+" is set to: "+Float.toString(c.v));
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
								if (st) p.sendMessage(plg.px+ChatColor.GOLD+c.txt+" is set to "+ChatColor.GREEN+Float.toString(c.v));
								else { 
									p.sendMessage(plg.px+"Variable "+ChatColor.GREEN+c.name+ChatColor.WHITE+" was not changed.");
									p.sendMessage(plg.px+c.txt+" is set to: "+ChatColor.GREEN+Float.toString(c.v));
								}
							}
						}
					}
					if (cfgchanged) {
						plg.UpdateFastVar();
						plg.Rst();
						plg.SaveCfg();
						return true;
					}
				}
			} else {
				if (args.length==1){
					if (args[0].equalsIgnoreCase("help")){
						if (p.hasPermission("monsterfix.fly.flymode")&&plg.cfgB("flymode")) 
							p.sendMessage(px+"You can fly "+ChatColor.DARK_GRAY+"(/mfix fly)");
						plg.DemoColor(p);
						cmdok = true;					
					} else if (args[0].equalsIgnoreCase("fly")){
						if (p.hasPermission("monsterfix.fly.flymode")&&plg.cfgB("flymode")) plg.ToggleFly(p);
						else p.sendMessage(plg.px+ChatColor.RED+"You cannot fly!");
					}
				}

			}
			return cmdok;
		}
		return false;
	}
}
