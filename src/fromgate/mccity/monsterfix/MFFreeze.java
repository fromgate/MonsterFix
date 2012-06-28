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

import java.util.HashMap;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.util.Vector;

public class MFFreeze implements Listener {

	MonsterFix plg;
	protected HashMap<Player,Long> fplayers = new HashMap<Player,Long>();

	public MFFreeze (MonsterFix plg){
		this.plg = plg;
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void freezeInventoryOpenEvent (InventoryOpenEvent event){
		if (event.getPlayer() instanceof Player){
			Player p = (Player) event.getPlayer();
			if (CheckFreezed(p)) {
				p.closeInventory();
				event.setCancelled(true);
			} else if (event.isCancelled()) Freeze(p); 
		}
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void freezePlayerBedEnterEvent (PlayerBedEnterEvent event){
		if (CheckFreezed(event.getPlayer())) {
			event.setCancelled(true);
		} else if (event.isCancelled()) Freeze(event.getPlayer()); 
	}

	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerQuit (PlayerQuitEvent event){
		if (fplayers.containsKey(event.getPlayer())) fplayers.remove(event.getPlayer());
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void freezePlayerInteract (PlayerInteractEvent event){
		if (CheckFreezed(event.getPlayer())) {
			event.setCancelled(true);
			return;
		}
		if (event.isCancelled()&&((event.getAction()==Action.LEFT_CLICK_BLOCK)||(event.getAction()==Action.RIGHT_CLICK_BLOCK))) Freeze (event.getPlayer());
	}
	


	@EventHandler(priority=EventPriority.MONITOR)
	public void freezePlayerMove (PlayerMoveEvent event){
		Player p = event.getPlayer();
		if (CheckFreezed(p)){
			//if (event.getTo().getBlockY()!=event.getFrom().getBlockY()) {
				p.setVelocity(new Vector().zero());
				p.setSneaking(false);
				p.setSprinting(false);
				//event.setTo(event.getFrom());
				//p.teleport(event.getFrom());
				event.setCancelled(true);
			//}
		} else if (event.isCancelled()) Freeze(event.getPlayer()); 
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void freezePlayerTeleportEvent (PlayerTeleportEvent event){
		if (event.getCause()==TeleportCause.UNKNOWN) return;
		
		if (CheckFreezed(event.getPlayer())) event.setTo(event.getFrom());
		else if (event.isCancelled()) Freeze(event.getPlayer());
	}


	@EventHandler(priority=EventPriority.MONITOR)
	public void freezeBlockPlace (BlockPlaceEvent event){
		if (CheckFreezed(event.getPlayer())) event.setCancelled(true);
		else if (event.isCancelled()) Freeze(event.getPlayer());
	}

	@EventHandler(priority=EventPriority.MONITOR)
	public void freezeBlockBreak (BlockBreakEvent event){
		if (CheckFreezed(event.getPlayer())) {
			event.setCancelled(true);
			return;
		}
		if (event.isCancelled()) {
			event.getPlayer().sendBlockChange(event.getBlock().getLocation(), event.getBlock().getType(), event.getBlock().getData());
			Freeze(event.getPlayer());
		}
	}


	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
	public void freezePlayerPortal(PlayerPortalEvent event) {
		if (CheckFreezed(event.getPlayer())) event.setCancelled(true);
	}
	
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled = true)
	public void freezePlayerDropItem (PlayerDropItemEvent event){
		if (CheckFreezed(event.getPlayer())) event.setCancelled(true);
	}

	
	protected boolean CheckFreezed (Player p){
		if ((plg.cncfreeze)&&(fplayers.containsKey(p))) {
			if ((System.currentTimeMillis()-fplayers.get(p))<=plg.cncfrtime) {
				return true;
			} else fplayers.remove(p);
		}
		return false;
	}

	protected boolean Freeze (Player p){
		if (plg.cncfreeze&&(!p.hasPermission("monsterfix.cheats.unfreeze"))){
			fplayers.put(p, System.currentTimeMillis());
			return true;
		}
		return false;
	}


}
