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
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.metadata.FixedMetadataValue;


public class MFPlayerListener implements Listener {
	MonsterFix plg;
	MFUtil u;

	public MFPlayerListener(MonsterFix plg){
		this.plg=plg;
		this.u = plg.u;
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerMove (PlayerMoveEvent event){
		Player p = event.getPlayer();
		if (plg.fixsneak) {
			if (p.isSneaking()&&(p.getFoodLevel()<=6)&&(p.getGameMode() == GameMode.SURVIVAL)) {
				p.setSneaking(false);
				if (plg.sneakhrt) p.playEffect(EntityEffect.HURT);
				u.printMSG(p, "msg_exhausted",'c');
			}
		}
	}


	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onInvOpen (InventoryOpenEvent event){
		if ((event.getInventory().getType()==InventoryType.MERCHANT)&&plg.cfgB("nomerchants"))
			event.setCancelled(true);
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerPickupItemEvent (PlayerPickupItemEvent event){
		if (plg.mapsend){
			Item item = event.getItem();
			if (item != null){
				ItemStack itemStack = item.getItemStack();
				if ((itemStack != null)&&(itemStack.getType() == Material.MAP)){
					Player p = event.getPlayer();
					Short mapid = itemStack.getDurability();
					p.sendMap(Bukkit.getMap(mapid));				
				}
			}			
		}
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerItemHeld (PlayerItemHeldEvent event){
		if ((!plg.mapsend)&&(!plg.mapshowid)) return;
		Player p = event.getPlayer();
		int itemSlot = event.getNewSlot();
		if (itemSlot>=0){
			ItemStack item = p.getInventory().getItem(itemSlot);
			if ((item != null)&&(item.getType()==Material.MAP)){
				Short mapid = p.getInventory().getItem(itemSlot).getDurability();
				MapView map = Bukkit.getMap(mapid);
				if (plg.mapshowid){
					for (MapRenderer mr : map.getRenderers()){
						if (mr instanceof MFMapRenderer) {
							if (plg.mapsend) p.sendMap(map);
							return;
						}
					}
					map.addRenderer(new MFMapRenderer (plg));
					p.sendMap(map);
					return;
				}
				if (plg.mapsend) p.sendMap(map);
			}
		}
	}



	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerJoinHIGHEST (PlayerJoinEvent event){
		Player p = event.getPlayer();
		if (plg.cfgB("jinvul"))	MFObc.setPlayerInvulTick(p, plg.cfgI("jinvultick"));

		if ((plg.cfgB("jpcenter"))&&(p.hasPlayedBefore())) {
			Location loc = p.getLocation(); //.getBlock().getLocation(); //.add(0.5, 0, 0.5);
			int id1 = loc.getBlock().getTypeId();
			int id2 = loc.getBlock().getRelative(0, 1, 0).getTypeId();
			if ((u.isIdInList(id1, plg.permblck))&&(u.isIdInList(id2, plg.permblck))) {
				loc.setX(loc.getBlockX()+0.5);
				//loc.setY(loc.getBlockY());
				loc.setZ(loc.getBlockZ()+0.5);

				loc.setPitch(p.getLocation().getPitch());
				loc.setYaw(p.getLocation().getYaw());
				p.teleport(loc);
			}


			//TODO что-то делать со всякого рода решетками и стекляными панелями
			/*		else if ((id1 == 85)||(id1 == 107)||(id2 == 85)||(id2 == 107)){
				if (((loc.getX()-loc.getBlockX())>0.92)&&
						plg.isIdInList(loc.getBlock().getRelative(1, 0, 0).getTypeId(), plg.permblck)&&
						plg.isIdInList(loc.getBlock().getRelative(1, 1, 0).getTypeId(), plg.permblck)){
					loc.setX (loc.getBlockX()+1.5);
				} else if (((loc.getX()-loc.getBlockX())<0.075)&&
						plg.isIdInList(loc.getBlock().getRelative(-1, 0, 0).getTypeId(), plg.permblck)&&
						plg.isIdInList(loc.getBlock().getRelative(-1, 1, 0).getTypeId(), plg.permblck)){
					loc.setX (loc.getBlockX()-0.5);
				} 

				if (((loc.getZ()-loc.getBlockZ())>0.92)&&
						plg.isIdInList(loc.getBlock().getRelative(0, 0, 1).getTypeId(), plg.permblck)&&
						plg.isIdInList(loc.getBlock().getRelative(0, 1, 1).getTypeId(), plg.permblck)){
					loc.setZ (loc.getBlockZ()+1.5);
				} else if (((loc.getZ()-loc.getBlockZ())<0.075)&&
						plg.isIdInList(loc.getBlock().getRelative(0, 0, -1).getTypeId(), plg.permblck)&&
						plg.isIdInList(loc.getBlock().getRelative(0, 1, -1).getTypeId(), plg.permblck)){
					loc.setZ (loc.getBlockZ()-0.5);
				}
			} else if ((id1 == 101)||(id1 == 102)||(id2 == 101)||(id2 == 102)){
				if ((loc.getX()-loc.getBlockX())>=0.8625) loc.setX(loc.getBlockX()+0.87);
				else if ((loc.getX()-loc.getBlockX())<=0.13749) loc.setX(loc.getBlockX()+0.13);
				if ((loc.getZ()-loc.getBlockZ())>=0.8625) loc.setX(loc.getBlockX()+0.87);
				else if ((loc.getZ()-loc.getBlockZ())<=0.13749) loc.setX(loc.getBlockX()+0.13);
			}
			 */

		}

	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerJoin (PlayerJoinEvent event){
		if (plg.cfgB("warnplayer")) plg.WarnPlayer(event.getPlayer(), plg.cfgB("warnplempty"));
		plg.u.UpdateMsg(event.getPlayer());
	}


	/*
	 * Центрируются координаты при телепорте
	 * Проверяется телепорт в "непрозрачный блок" или если попытка пробросить жемчужину сквозь стенку 
	 */

	@EventHandler(priority=EventPriority.LOW)
	public void onEnderPearl (PlayerTeleportEvent event){
		if (event.getCause()==TeleportCause.ENDER_PEARL){

			if (plg.enderpearl){
				event.setCancelled(true);
				return;
			}

			Location loc = event.getTo();
			if (plg.eptpblock){
				loc.setX(loc.getBlockX()+0.5);
				loc.setY(loc.getBlockY());
				loc.setZ(loc.getBlockZ()+0.5);			
				if ((!(u.isIdInList(loc.getBlock().getTypeId(), plg.emptyblock)))&&
						(!(u.isIdInList(loc.getBlock().getRelative(BlockFace.UP).getTypeId(), plg.emptyblock)))) loc = event.getFrom();
			}
			event.setTo(loc);
		}
		if ((event.getCause()!=TeleportCause.UNKNOWN)&&event.getPlayer().isInsideVehicle()&&plg.cfgB("tpveject")) event.getPlayer().getVehicle().eject();
	}


	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerPortal(PlayerPortalEvent event) {
		Player p = event.getPlayer();
		if ((event.getCause()==TeleportCause.NETHER_PORTAL)&&(plg.nport)&&(!(p.hasPermission("monsterfix.portal.nether")&&plg.nportperm)))
			event.setCancelled(true);
		if ((event.getCause()==TeleportCause.END_PORTAL)&&(plg.eport)&&(!(p.hasPermission("monsterfix.portal.end")&&plg.eportperm)))
			event.setCancelled(true);	
	}




	//PlayerGameModeChangeEvent
	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerGM (PlayerGameModeChangeEvent event) {
		if ((event.getNewGameMode()!=GameMode.CREATIVE)&&(plg.cfgB("crgod"))){
			Player p = event.getPlayer();
			p.setHealth(p.getMaxHealth());
			p.setExhaustion(0f);
			p.setFoodLevel(20);
			p.setSaturation(20);
		}
	}





	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.NORMAL)
	public void onLavaWaterBucket (PlayerInteractEvent event) {
		if (!plg.lhplace) return;
		if (event.getAction() !=Action.RIGHT_CLICK_BLOCK) return;
		if (event.getClickedBlock().getY()<=plg.lheight) return;
		Player p = event.getPlayer();
		if (p.hasPermission("monsterfix.unlhblock")) return;
		if (p.getItemInHand() == null) return;
		ItemStack ih = event.getPlayer().getItemInHand();
		if (((ih.getType()==Material.LAVA_BUCKET)&&(u.isIdInList(10, plg.lhblock)||u.isIdInList(11, plg.lhblock)))|| 
				((ih.getType()==Material.WATER_BUCKET)&&(u.isIdInList(8, plg.lhblock)||u.isIdInList(9, plg.lhblock)))){
			event.setCancelled(true);
			if (plg.lhbmsg)	u.printMSG(p, "msg_placedenied",'c','4',ih.getType().name().replace("_BUCKET", ""));
			p.updateInventory();
		}
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerInteract (PlayerInteractEvent event) {
		if (event.isCancelled()) return;

		Player p = event.getPlayer();
		Block cb = event.getClickedBlock();

		if (plg.enderpearl&&(event.getAction().equals(Action.RIGHT_CLICK_AIR)||event.getAction().equals(Action.RIGHT_CLICK_BLOCK))&&
				(event.getItem()!=null)&&(event.getItem().getType() == Material.ENDER_PEARL)&&(!p.hasPermission("monsterfix.enderperltp")))	{
			event.setCancelled(true);
			return;
		}

		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (p.getItemInHand().getType() ==null) return; // на всякий случай!

			if (plg.highlands&&plg.hlbuild&&(plg.CheckPlayerInHighlands(p))) {  			//&&(cb.getLocation().getBlockY()>=plg.hllevel)
				event.setCancelled(true);
				u.printMSG(p, "msg_shortbreath"); 
				return;
			}

			//Color Woool
			if ((plg.colorwool)&&(cb.getType()==Material.WOOL)&&(p.getItemInHand().getTypeId()==351)&&(p.hasPermission("monsterfix.colorwool")&&
					((cb.getData()==0)||((cb.getData()>0)&&(!plg.colorwoolwhite))))){
				if ((!u.placeBlock(cb, p, Material.WOOL, (byte) (15-p.getItemInHand().getData().getData()),false))&&(p.getGameMode() != GameMode.CREATIVE)){
					if (p.getItemInHand().getAmount()>1) p.getItemInHand().setAmount(p.getItemInHand().getAmount()-1);
					else p.setItemInHand(new ItemStack(Material.AIR, 0));  //p.getInventory().remove(p.getItemInHand());
				}
			}

			if ((plg.rmvtrash)&&(u.isItemInList (cb.getTypeId(),cb.getData(), plg.rmvblocks))&&(plg.checkTrash(cb)))	plg.AddToTrash(cb);

			// Mushroom
			if ((plg.fixmushroom)&&
					(!p.hasPermission("monsterfix.hugemushroom"))&&
					((cb.getType()==Material.BROWN_MUSHROOM)||(cb.getType()==Material.RED_MUSHROOM))&&
					(p.getItemInHand().getTypeId() == 351)&&
					(p.getItemInHand().getData().getData()== 15)&&
					(cb.getRelative(0, -1, 0).getType() != Material.MYCEL)){
				World w = cb.getWorld(); 
				if (cb.getType()==Material.BROWN_MUSHROOM) w.dropItem(cb.getRelative(0, 1, 0).getLocation(), new ItemStack (Material.BROWN_MUSHROOM,1));
				else w.dropItem(cb.getRelative(0, 1, 0).getLocation(), new ItemStack (Material.RED_MUSHROOM,1));
				cb.setType(Material.AIR);
				event.setCancelled(true);
			}

			//Snowball
			if ((p.getItemInHand().getType() == Material.SNOW_BALL)&&plg.snowball&&p.hasPermission("monsterfix.snowball")) {
				Block tb = p.getTargetBlock(null, 150);
				if ((tb.getRelative(0, 1, 0).getType()==Material.AIR)&&(u.isItemInList(tb.getTypeId(),tb.getData(), plg.snowballable))){
					if (tb.getType()==Material.SNOW) {
						byte sh = tb.getData();
						sh++;
						if (sh<7) u.placeBlock(tb, p, Material.SNOW, sh,true);
						else  u.placeBlock(tb, p, Material.SNOW_BLOCK, (byte) 0,true);
					} else u.placeBlock(tb.getRelative(0, 1, 0), p, Material.SNOW, (byte)0,true);
				}
			}

			if ((plg.fixboatplace)&&(p.getItemInHand().getType() == Material.BOAT)) {
				Block tb = p.getTargetBlock(null, 150);
				if (tb.getType() != Material.STATIONARY_WATER) event.setCancelled(true);
			}

		} 
	}


	@EventHandler(priority=EventPriority.LOW)
	public void onPlayerChatEvent (AsyncPlayerChatEvent event){
		Player p = event.getPlayer();
		if (plg.cpfix) event.setMessage(plg.recodeText(event.getMessage()));
		if (plg.decolor||plg.chatcolor) {
			String msg = event.getMessage();	
			if (plg.decolor) msg = plg.deColorize(msg);
			if (plg.chatcolor) msg = plg.Colorize(p, msg);
			event.setMessage(msg);
		}
	}


	@EventHandler(priority=EventPriority.LOWEST)
	public void onPlayerCommandPreprocess (PlayerCommandPreprocessEvent event){
		Player p = event.getPlayer();

		if (plg.cpfix) event.setMessage(plg.recodeText(event.getMessage()));

		if (plg.cfgB("singlespace")){
			String msg = event.getMessage().trim().replaceAll("\u0020{2,}", "\u0020");
			event.setMessage(msg);
		}

		if (plg.cfgB("blockcmd")){
			String msg = event.getMessage().trim().replaceAll("\u0020{2,}", "\u0020");
			String [] ln = msg.split(" ");
			if (ln.length>0){
				String cmd = ln[0];
				if (u.isWordInList(cmd.replaceFirst("/", ""), plg.cfgS("blockcmdlist"))&&(!p.hasPermission("monsterfix.command."+cmd))){
					u.printMSG(p,"msg_blockcmd",cmd,'c','e'); 
					event.setCancelled(true);
				} else if (ln.length>1) {
					cmd = cmd+"_"+ln[1];
					if (u.isWordInList(cmd.replaceFirst("/", ""), plg.cfgS("blockcmdlist"))&&(!p.hasPermission("monsterfix.command."+cmd))){
						u.printMSG(p,"msg_blockcmd",ln[0]+" "+ln[1],'c','e'); 
						event.setCancelled(true);
					}	
				}
			}
		}

		if (plg.cfgB("cmdcolor")){
			String msg = event.getMessage().trim().replaceAll("\u0020{2,}", "\u0020");
			String [] ln = msg.split(" ");
			if((ln.length>0)&&(u.isWordInList(ln[0].replaceFirst("/", ""), plg.cfgS("cmdcolorlist")))){
				event.setMessage(plg.Colorize(p, event.getMessage()));
			}
		}
	}


	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerRespawn (PlayerRespawnEvent event) {
		if ((event.isBedSpawn())&&(plg.cfgB("bedonce"))){
			event.getPlayer().setBedSpawnLocation(event.getPlayer().getWorld().getSpawnLocation());
			if (plg.cfgB("bedoncemsg")) u.printMSG(event.getPlayer(),'c', "msg_bedonce");
		}
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onCreatureSpawn (CreatureSpawnEvent event) {
		if ((!event.isCancelled())&&(plg.msdrop)&&(event.getSpawnReason() == SpawnReason.SPAWNER)&&
				(!u.isWordInList(event.getEntity().getType().getName(), plg.msexcept))) 
			MFMeta.setMeta(event.getEntity(), "mspmobs");
			//plg.mspmobs.add(event.getEntity());
	}




	//переделано 19.03
	@EventHandler(priority=EventPriority.LOW)
	public void onEntityDeath (EntityDeathEvent event){
		if (event.getEntityType() == EntityType.PLAYER) return;
		boolean clrdrp = false;
		LivingEntity mob = event.getEntity(); 
		String mname = mob.getType().getName();
		if (u.isWordInList(mname, plg.mobnodrop)) clrdrp = true;
		// дроп только от "злонамеренных" причин
		if (plg.plrdrop&&(!(mob.getLastDamageCause() instanceof EntityDamageByEntityEvent))) clrdrp = true;
		// моб народился в мобспавнере
		
		if ((plg.msdrop)&&(MFMeta.hasMeta(mob, "mspmobs")))
			clrdrp = true;
		// скотобойни
		if ((plg.butchery)&&(!u.isWordInList(mname, plg.btchexcept))){
			if (plg.btchprogres) {
				if (plg.random.nextInt(plg.btchcount)<plg.AddButchery(event.getEntity().getLocation())) clrdrp = true;
			} else if (plg.AddButchery(event.getEntity().getLocation())>plg.btchcount) clrdrp = true;				
		}

		// собственно отключаем дроп
		if (clrdrp){
			boolean xpdrop = false;
			boolean itemdrop = false;
			if ((plg.permdrop)&&(mob.getLastDamageCause() instanceof EntityDamageByEntityEvent)){
				EntityDamageByEntityEvent dmg = (EntityDamageByEntityEvent) mob.getLastDamageCause();
				if (dmg.getDamager() instanceof Player){
					Player p = (Player) dmg.getDamager();
					xpdrop = p.hasPermission("monsterfix.drop.xp");
					itemdrop = p.hasPermission("monsterfix.drop.items");
				}
			}
			if (!xpdrop) event.getEntity().setMetadata("mfix-disable-xp-drop", new FixedMetadataValue(plg, true)); 
			if (!itemdrop) {
				event.getEntity().setMetadata("mfix-disable-item-drop", new FixedMetadataValue(plg, true));
				event.getDrops().clear();
			}
			if (plg.cfgB("btcusemeta")) event.getEntity().setMetadata(plg.cfgS("btcmetadata"), new FixedMetadataValue(plg, true));
			if (plg.cfgB("btcdmgcause")) event.getEntity().setLastDamageCause(null);

		}
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void onEntityDeathDisableDrop (EntityDeathEvent event){
		if (event.getEntity().hasMetadata("mfix-disable-xp-drop")) event.setDroppedExp(0);
		if (event.getEntity().hasMetadata("mfix-disable-item-drop")) event.getDrops().clear();
	}


	@EventHandler(priority=EventPriority.NORMAL)
	public void onEntityRegainHealthEvent (EntityRegainHealthEvent event){
		if ((plg.nohpregen)&&(event.getRegainReason() == RegainReason.SATIATED)&&(event.getEntityType() == EntityType.PLAYER)){
			Player p = (Player) event.getEntity();
			if (p.getHealth()>=plg.nohpmax) event.setCancelled(true);
		}
	}

	//riverfish
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onPlayerFish (PlayerFishEvent event){
		if (!plg.cfgB("fishfarm")) return;
		if ((event.getState() != PlayerFishEvent.State.CAUGHT_FISH)&&(event.getState() != PlayerFishEvent.State.CAUGHT_ENTITY)) return;
		if (u.isWordInList(event.getPlayer().getLocation().getBlock().getBiome().name().toLowerCase(), plg.cfgS("fishbiomes"))) return;
		if (u.rollDiceChance(plg.cfgI("fishchance"))) return;

		if (plg.cfgB("fishwarn")) u.printMSG(event.getPlayer(),'c',"msg_fishfarm");
		event.setCancelled(true);
	}


	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onMapInit (MapInitializeEvent event){
		if (plg.mapshowid){
			final MapView map = event.getMap();
			Bukkit.getScheduler().runTaskLaterAsynchronously(plg, new Runnable(){
				public void run (){
					for (MapRenderer mr : map.getRenderers())
						if (mr instanceof MFMapRenderer) return;
					map.addRenderer(new MFMapRenderer (plg));				
				}
			}, 5);
		}
	}


	@EventHandler(priority=EventPriority.LOWEST)
	public void onEntityDamageMob (EntityDamageEvent event) {
		if (plg.fixmobfall){
			if (event.getCause() == null) return;
			Entity e = event.getEntity();
			if (e instanceof Player) return;
			if (MFMeta.hasMeta(e, "mobdmg")) return;
			if (u.isWordInList(e.getType().getName(), plg.mfexcept)) return;
			if (event.getCause() == DamageCause.FALL) event.setDamage(0);
			else MFMeta.setMeta(e, "mobdmg");
		}
	}

	@EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled = true)
	public void onPlayerDamageInCreative (EntityDamageEvent event) {
		if (!plg.cfgB("crgod")) return;
		if (event.getEntity() instanceof Player){
			Player p = (Player) event.getEntity();
			if (p.getGameMode()==GameMode.CREATIVE){
				event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onFallDamage (EntityDamageEvent event) {
		if (event.getCause() != DamageCause.FALL) return;
		if (!plg.cfgB("falldmg")) return;
		if ((event.getEntity() instanceof Player)&&
				(((Player) event.getEntity()).hasPermission("monsterfix.featherfall")))
			event.setDamage(0);
	}


	@EventHandler(priority=EventPriority.LOW)
	public void onEntityDamagePlayer (EntityDamageEvent event){
		if (event.getEntity() instanceof Player) {
			Player tp = (Player) event.getEntity();
			World w = tp.getWorld();
			if (tp.getGameMode()==GameMode.CREATIVE) return;

			// Детонация!!!!
			int ch= plg.random.nextInt(100);

			Long tm = System.currentTimeMillis();

			if (plg.detonate&&tp.getInventory().contains(Material.TNT)&&((tm-plg.lastdtntime)>plg.dtndelay)&& 
					((((event.getCause()==DamageCause.BLOCK_EXPLOSION)||(event.getCause()==DamageCause.ENTITY_EXPLOSION))&&(ch<plg.dexplchance))||
							((event.getCause()==DamageCause.LIGHTNING)&&(ch<plg.dlighchance))||
							((event.getCause()==DamageCause.FIRE)||(event.getCause()==DamageCause.LAVA))&&(ch<plg.dfirechance))) {
				plg.lastdtntime = tm;  // запоминаем время детонации
				int cnt = plg.dtnmax;
				if (cnt>1) cnt = plg.random.nextInt(cnt)+1; // детонируем случайное количество

				for (ItemStack i : tp.getInventory().getContents()) {
					if ((i!= null)&&(cnt>0)&&(i.getAmount()>0)&&(i.getType() == Material.TNT)){
						int droptnt = Math.min(i.getAmount(), cnt);
						if (droptnt>0){
							tp.getInventory().removeItem(new ItemStack (Material.TNT,droptnt));
							cnt = cnt-droptnt;
							for (int j = 0; j<droptnt; j++) {
								TNTPrimed tnt = w.spawn(tp.getLocation().add(plg.random.nextInt(5)-2,plg.random.nextInt(2), plg.random.nextInt(5)-2), TNTPrimed.class);
								int ft = plg.dprtime;
								if (ft<plg.dmprtime) ft = plg.random.nextInt(plg.dprtime-ft+1)+ft;
								tnt.setFuseTicks(ft);
							}
						}
					} else if (cnt<=0) break;
				}
			}
		}
	}



	@EventHandler(priority=EventPriority.HIGH)
	public void onHeadShot (EntityDamageEvent event){
		if ((!plg.headshots)||event.isCancelled()||(!(event.getEntity() instanceof Player)||(event.getCause() != DamageCause.PROJECTILE))) return;
		Player p = (Player) event.getEntity();
		//по идее проверка на NPC

		EntityDamageByEntityEvent evdm = (EntityDamageByEntityEvent) event;
		if (evdm.getDamager() instanceof Projectile) {
			Projectile prj = (Projectile) evdm.getDamager();
			Location eye = p.getEyeLocation(); 
			if (eye.distance(prj.getLocation())<=1.5D){
				boolean wearhelm = ((p.getInventory().getHelmet() != null)&&(p.getInventory().getHelmet().getType()!=Material.AIR));
				int damage=0;
				boolean drophelm = false;
				int breakhelm = 0;
				String msg = "";
				if (prj instanceof Arrow){
					if (!plg.hsarrow) return;
					if (wearhelm) damage = plg.hsadmghelm;
					else damage = plg.hsadmghead;
					drophelm = wearhelm&&plg.hsadrop;
					breakhelm = plg.hsabreak;
					msg = "msg_headshot";

				} else if (prj instanceof Snowball){
					if (!plg.hssnowball) return;
					if (wearhelm) damage = plg.hssbdmghelm;
					else damage = plg.hssbdmghead;
					drophelm = wearhelm&&plg.hssbdrop;
					breakhelm = plg.hssbbreak;
					msg = "msg_snowheadshot";
				} else if (prj instanceof Egg){
					if (!plg.hsegg) return;
					if (wearhelm) damage = plg.hsedmghelm;
					else damage = plg.hsedmghead;
					drophelm = wearhelm&&plg.hsedrop;
					breakhelm = plg.hsebreak;
					msg = "msg_eggheadshot"; 
				} else if (prj instanceof Fish){
					if (!plg.hsfishrod) return;
					if (wearhelm) damage = plg.hsfdmghelm;
					else damage = plg.hsfdmghead;
					drophelm = wearhelm&&plg.hsfdrop;
					breakhelm = plg.hsfbreak;
					msg = "msg_hookheadshot";
				} else return;

				int chance = 0;
				String shname = "unknown";
				Player shooter = null;

				if (prj.getShooter() instanceof Player){
					shooter = (Player) prj.getShooter();
					chance = plg.hschance;
					shname = shooter.getName();

					if ((!shooter.getWorld().getPlayers().contains(shooter))&&(!plg.hsnpc)) return;
					if (shooter.hasPermission("monsterfix.sharpshooter")) chance = chance + plg.hssharp;
					if (shooter.hasPermission("monsterfix.badluck")) chance = chance-plg.hsbadluck; 

				} else {
					chance = plg.hsmobchance;
					LivingEntity mob_shooter = prj.getShooter();
					if (mob_shooter == null) shname = u.getMSGnc("someone");
					else shname = mob_shooter.getType().getName();

				}
				if (p.hasPermission("monsterfix.badluck")) chance=chance+plg.hsbadluck;
				if (chance<0) chance=0;


				if (plg.random.nextInt(100)<chance){
					event.setDamage(damage);

					u.printMSG(p, msg,shname,'c','4');

					if (shooter !=null) u.printMSG(p, "msg_headshot!",'4'); 

					if (p.getInventory().getHelmet()!=null){
						Material item = p.getInventory().getHelmet().getType();


						if ((item!=null)&&(item!=Material.AIR)&&((item == Material.LEATHER_HELMET)||(item == Material.IRON_HELMET)
								||(item == Material.GOLD_HELMET)||(item == Material.CHAINMAIL_HELMET)||(item == Material.DIAMOND_HELMET))){
							short hdur = p.getInventory().getHelmet().getDurability();
							if (breakhelm >0){
								int ndur = hdur + (item.getMaxDurability()/100*breakhelm);
								if (ndur>=item.getMaxDurability()) ndur = item.getMaxDurability()-1;
								hdur = (short) ndur;
								p.getInventory().getHelmet().setDurability(hdur);
							}
							if (drophelm) {
								ItemStack droppedhelm = p.getInventory().getHelmet().clone();
								p.getInventory().setHelmet(new ItemStack (Material.AIR,1));										
								p.getWorld().dropItemNaturally(p.getLocation().add(plg.random.nextInt(5)-2, plg.random.nextInt(2)+2, plg.random.nextInt(5)-2), droppedhelm);
							}						
						}
					}
				}
			}
		}
	}

}
