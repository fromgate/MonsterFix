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


import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.EntityBlockFormEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityBreakDoorEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.inventory.ItemStack;



public class MFBlockListener implements Listener {
	MonsterFix plg;
	MFUtil u;
	MFBlockListener (MonsterFix plg) {
		this.plg=plg;
		this.u = plg.u;
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onBlockRedstoneEvent (BlockRedstoneEvent event){
		if (plg.lamps.contains(event.getBlock())){
			if (plg.lamp&&event.getBlock().getType() ==Material.REDSTONE_LAMP_ON) event.setNewCurrent(15);
			plg.lamps.remove(event.getBlock());
		}
		if (plg.lampdebug)  event.setNewCurrent(event.getOldCurrent());
	}


	@EventHandler(priority=EventPriority.LOW)
	public void onSignChange (SignChangeEvent event){
		if (plg.cpfix)
			for (int i = 0; i<4;i++)
				if (!event.getLine(i).isEmpty()) event.setLine(i, plg.recodeText(event.getLine(i)));
	}


	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onBreakDoor (EntityBreakDoorEvent event){
		if (plg.cfgB("zombiedoor")) event.setCancelled(true);
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onBlockPlace (BlockPlaceEvent event){
		Player p = event.getPlayer();
		Block b = event.getBlock();

		if (plg.highlands&&plg.hlbuild&&(plg.CheckPlayerInHighlands(p))) {  //(event.getBlock().getLocation().getBlockY()>=plg.hllevel)&&
			event.setCancelled(true);
			u.PrintMSG(p, "msg_shortbreath");
			return;
		}

		if ((plg.rmvtrash)&&(u.isItemInList (b.getTypeId(), b.getData(), plg.rmvblocks))&&(plg.checkTrash(b))&&(b.getY()>=plg.rmvlevel)) 
			plg.AddToTrash(b);

		if (plg.lamp&&p.hasPermission("monsterfix.lamp.place")) {
			if (b.getType()==Material.REDSTONE_LAMP_ON) plg.lamps.add(b);
			if (b.getRelative(BlockFace.EAST).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.EAST));
			if (b.getRelative(BlockFace.WEST).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.WEST));
			if (b.getRelative(BlockFace.NORTH).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.NORTH));
			if (b.getRelative(BlockFace.SOUTH).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.SOUTH));
			if (b.getRelative(BlockFace.DOWN).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.DOWN));
			if (b.getRelative(BlockFace.UP).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.UP));
		}

		if ((plg.lhplace)&&
				(!p.hasPermission("monsterfix.unlhblock"))&&
				(b.getY()>plg.lheight)&&
				(u.isItemInList(b.getTypeId(),b.getData(), plg.lhblock))){

			if (plg.lhbmsg)	u.PrintMSG(p, "msg_placedenied",b.getType().name(),'c','4');

			event.setCancelled(true);
			return;
		}

	}



	@EventHandler(priority=EventPriority.NORMAL)
	public void onBlockPhysics(BlockPhysicsEvent event) {
		Block b = event.getBlock();
		if ((plg.fixcactusfarm)&&(b.getType()==Material.CACTUS)) {
			if (u.isIdInList(b.getRelative(1, 0, 0).getTypeId(), plg.allowcactus)||
					u.isIdInList(b.getRelative(-1, 0, 0).getTypeId(), plg.allowcactus)||
					u.isIdInList(b.getRelative(0, 0, 1).getTypeId(), plg.allowcactus)||
					u.isIdInList(b.getRelative(0, 0, -1).getTypeId(), plg.allowcactus)) {
				event.getBlock().setType(Material.AIR);
				event.setCancelled(true);
			}
		}
	}



	@EventHandler(priority=EventPriority.HIGH, ignoreCancelled = true)
	public void onBlockBreak (BlockBreakEvent event){
		if (event.isCancelled()) return;

		Block b = event.getBlock();
		Player p = event.getPlayer();

		if (plg.highlands&&plg.hlbuild&&(plg.CheckPlayerInHighlands(p))) { //&&(b.getLocation().getBlockY()>=plg.hllevel)
			event.setCancelled(true);
			u.PrintMSG(p, "msg_shortbreath");
		}

		if ((plg.fixcactusfarm)&&(b.getType()==Material.CACTUS)&&(p.getGameMode()==GameMode.SURVIVAL)){
			World w = b.getWorld();
			for (int i = -3; i<=3; i++)
				if ((b.getRelative(0, i, 0)).getType()==Material.CACTUS) {
					if (i!=0) w.dropItemNaturally(b.getRelative(0, i, 0).getLocation(), new ItemStack (Material.CACTUS, 1));
				}
		}

		if (!p.hasPermission("monsterfix.farmer")) {
			if (plg.fixmelpum) plg.MelPumBreak (event.getBlock());
			if ((plg.fixwheatfarm)&&(b.getType() == Material.CROPS)&&(plg.random.nextInt(100)>plg.soilchance)) 
				b.getRelative(0, -1, 0).setType(Material.DIRT);
		}

		if (plg.fixsnow&&plg.smcrust&&(plg.snowtrails.size()>0)&&(plg.snowtrails.containsKey(event.getBlock().getLocation()))) {
			if (b.getType()==Material.SNOW) {
				plg.snowtrails.remove(event.getBlock().getLocation());
				//event.getDrops().clear(); // новое, ввели в 1.2.4, но потом отменили
				b.getDrops().clear(); //хорошо забытое старое
				event.setCancelled(true);
				b.setType(Material.AIR);
			} else plg.snowtrails.remove(event.getBlock().getLocation());
		}


		//boolean pwrb = plg.isIdInList(b.getTypeId(), plg.powerblck);
		if (plg.lamp&&p.hasPermission("monsterfix.lamp.break")){
			//	((p.hasPermission("monsterfix.lamp.break")&&(!pwrb))||
			//	(p.hasPermission("monsterfix.lamp.break_power")&&(pwrb)))) {
			if (b.getRelative(BlockFace.EAST).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.EAST));
			if (b.getRelative(BlockFace.WEST).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.WEST));
			if (b.getRelative(BlockFace.NORTH).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.NORTH));
			if (b.getRelative(BlockFace.SOUTH).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.SOUTH));
			if (b.getRelative(BlockFace.DOWN).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.DOWN));
			if (b.getRelative(BlockFace.UP).getType()== Material.REDSTONE_LAMP_ON) plg.lamps.add(b.getRelative(BlockFace.UP));
		}

		int i = plg.indexTrashBlock(b);
		if (i>=0) plg.trashcan.remove(i);


	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onBlockFromTo (BlockFromToEvent event){
		if ((plg.obsigen)&&((event.getBlock().getType()==Material.LAVA)||(event.getBlock().getType()==Material.WATER)||(event.getBlock().getType()==Material.STATIONARY_LAVA)||(event.getBlock().getType()==Material.STATIONARY_WATER))&&event.getToBlock().getType()==Material.REDSTONE_WIRE) {
			event.getToBlock().breakNaturally();
		}

		if (plg.waterfarm) {
			Block b = event.getToBlock(); //event.getToBlock().getRelative(BlockFace.DOWN);
			
			if ((b.getRelative(BlockFace.DOWN).getType() == Material.SOIL)&&(u.rollDiceChance(plg.watersoil))) b.getRelative(BlockFace.DOWN).setType(Material.DIRT);
			
			if ((b.getType() == Material.COCOA)&&u.rollDiceChance(plg.watercocoa)) b.setType(Material.AIR);
				
			
		}
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onBlockPistonExtend (BlockPistonExtendEvent event){
		if (plg.fixmelpum) {
			Block b = event.getBlock().getRelative(event.getDirection(),1);
			plg.MelPumBreak (b);
			if (event.isSticky())  {
				b = event.getBlock().getRelative(event.getDirection(),2);
				plg.MelPumBreak (b);
			}
		}
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onBlockForm (BlockFormEvent event){
		if (event.getNewState().getType()== Material.SNOW) {
			if ((event instanceof EntityBlockFormEvent)&&(plg.fixsnowman)) {
				EntityBlockFormEvent evt = (EntityBlockFormEvent) event;
				if (evt.getEntity() instanceof Snowman){
					if (plg.smcrust) plg.snowtrails.put(event.getNewState().getBlock().getLocation(), plg.smcrtime/10);
					else {
						event.getBlock().setType(Material.AIR); // поможет?!
						event.setCancelled(true);  // в 1.2.х глючит: снег остается, но после перезахода исчезает.  
					}
				}
			} else if ((plg.fixsnow)&&
					((!plg.unsnowedblocks.isEmpty())&&
							(u.isItemInList(event.getNewState().getBlock().getRelative(0, -1, 0).getTypeId(),event.getNewState().getBlock().getRelative(0, -1, 0).getData(), plg.unsnowedblocks))||
							(plg.unsnowbiome.contains(event.getNewState().getBlock().getBiome()))))
				event.setCancelled(true);
		}
	}

	@EventHandler(priority=EventPriority.NORMAL)
	public void onExplosionPrimeEvent (ExplosionPrimeEvent event){
		if (plg.cfggroup.get("explosion")){
			EntityType et = event.getEntityType();
			float r = event.getRadius();
			if ((et == EntityType.PRIMED_TNT)&&plg.tnt) {
				r = plg.tntr;
				if (plg.tntmr>r) r = r+(plg.random.nextFloat()*(plg.tntmr-r));

				event.setFire(plg.tntfire);

			} else if ((et == EntityType.CREEPER)&&plg.crp) {
				r = plg.crpr;
				if (plg.crpmr>r) r = r+(plg.random.nextFloat()*(plg.crpmr-r));
				if (event.getEntity() instanceof Creeper){
					Creeper crp = (Creeper) event.getEntity();
					if (crp.isPowered()) r=r*plg.crpmpwr; 
				}
				event.setFire(plg.crpfire);
			} else if ((et == EntityType.FIREBALL)&&plg.fbl) {
				r = plg.fblr;
				if (plg.fblmr>r) r = r+(plg.random.nextFloat()*(plg.fblmr-r));
				event.setFire(plg.fblfire);
			}
			event.setRadius(r);
		}
	}

	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityExplode (EntityExplodeEvent event){
		// неуязвимые блоки
		if (plg.unexplode)
			for (int i = event.blockList().size()-1; i>=0;i--)
				if (u.isItemInList(event.blockList().get(i).getTypeId(), event.blockList().get(i).getData(), plg.unexblock)) 
					event.blockList().remove(i);
				
	}
	
	@EventHandler(priority=EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityChangeBlock (EntityChangeBlockEvent event){
		if ((event.getEntityType() == EntityType.SILVERFISH)&&(plg.cfgB("silverfish"))) event.setCancelled(true);
	}
	
	
	

}
