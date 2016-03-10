package me.fromgate.monsterfix;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class MFMobFix implements Listener {

	MonsterFix plg;
	MFUtil u;

	Color [] clr = {Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK, 
			Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE,
			Color.LIME, Color.GREEN, Color.AQUA, Color.TEAL,
			Color.BLUE,Color.NAVY,Color.FUCHSIA,Color.PURPLE};

	public MFMobFix (MonsterFix plg){
		this.plg = plg;
		this.u = plg.u;
	}

	@EventHandler(priority=EventPriority.HIGH,ignoreCancelled = true)
	public void onCreatureWearArmour (CreatureSpawnEvent event) {
		if (!plg.cfgB("equip")) return;
		if ((event.getSpawnReason() == SpawnReason.SPAWNER_EGG)&&(!plg.cfgB("eqegg"))) return;
		if ((event.getSpawnReason() == SpawnReason.SPAWNER)&&(!plg.cfgB("eqmobspawn"))) return;
		if ((event.getSpawnReason() == SpawnReason.CUSTOM)&&(!plg.cfgB("eqcustom"))) return;
		float dropchance = ((float) plg.cfgI("eqdropchance"))/100;

		LivingEntity e = event.getEntity();
		if (u.isWordInList(e.getType().name(), plg.cfgS("eqarmmob"))){

			if (plg.cfgB("eqfull")&&(!u.rollDiceChance(plg.cfgI("eqchance")))) return;

			// Helmette
			if (plg.cfgB("eqfull")||u.rollDiceChance(plg.cfgI("eqchance")))	{
				ItemStack item = getRndItem(plg.cfgS("eqhelm"));
				if (plg.cfgB("eqench")&&u.rollDiceChance(plg.cfgI("eqenchance"))) 
					item = setRandomEnchantment (item, plg.cfgS("eqenchelm"),plg.cfgI("eqencmax"));
				e.getEquipment().setHelmet(item);
				e.getEquipment().setHelmetDropChance(dropchance);
			}

			// Chestplate
			if (plg.cfgB("eqfull")||u.rollDiceChance(plg.cfgI("eqchance")))	{
				ItemStack item = getRndItem(plg.cfgS("eqchest"));
				if (plg.cfgB("eqench")&&u.rollDiceChance(plg.cfgI("eqenchance"))) 
					item = setRandomEnchantment (item, plg.cfgS("eqencchest"),plg.cfgI("eqencmax"));
				e.getEquipment().setChestplate(item);
				e.getEquipment().setChestplateDropChance(dropchance);
			}

			// Leggings
			if (plg.cfgB("eqfull")||u.rollDiceChance(plg.cfgI("eqchance")))	{
				ItemStack item = getRndItem(plg.cfgS("eqleg"));
				if (plg.cfgB("eqench")&&u.rollDiceChance(plg.cfgI("eqenchance"))) 
					item = setRandomEnchantment (item, plg.cfgS("eqencleg"),plg.cfgI("eqencmax"));
				e.getEquipment().setLeggings(item);
				e.getEquipment().setLeggingsDropChance(dropchance);
			}

			// Boots
			if (plg.cfgB("eqfull")||u.rollDiceChance(plg.cfgI("eqchance")))	{
				ItemStack item = getRndItem(plg.cfgS("eqboot"));
				if (plg.cfgB("eqench")&&u.rollDiceChance(plg.cfgI("eqenchance"))) 
					item = setRandomEnchantment (item, plg.cfgS("eqencboot"),plg.cfgI("eqencmax"));
				e.getEquipment().setBoots(item);
				e.getEquipment().setBootsDropChance(dropchance);
			}
		}

		if (!plg.cfgB("eqweapon")) return;

		if (u.isWordInList(e.getType().name(), plg.cfgS("eqwpnmob"))) {
			if (plg.cfgB("eqfull")||u.rollDiceChance(plg.cfgI("eqchance"))) {
				ItemStack item = getRndItem(plg.cfgS("eqweapon"));
				if (plg.cfgB("eqench")&&u.rollDiceChance(plg.cfgI("eqenchance"))) 
					item = setRandomEnchantment (item, plg.cfgS("eqencweapon"),plg.cfgI("eqencmax"));
				e.getEquipment().setItemInMainHand(item);
				e.getEquipment().setItemInMainHandDropChance(dropchance);
			}
		} else if ((e.getType()==EntityType.SKELETON)&&plg.cfgB("eqskeleton")){
			ItemStack bow = new ItemStack (Material.BOW);
			if (plg.cfgB("eqfull")||u.rollDiceChance(plg.cfgI("eqchance"))) 
				bow = setRandomEnchantment (bow, plg.cfgS("eqencbow"),plg.cfgI("eqencmax"));
			e.getEquipment().setItemInMainHand(bow);
			e.getEquipment().setItemInMainHandDropChance(dropchance);
		}
	}




	/*
	 * TODO 
	 * + Одеваем мобов в броню +
	 * + Кожанную броню - красить +
	 * + Случайные энчанты +
	 * + Повреждение брони ?
	 */

	public ItemStack getRndItem (String str){
		if (str.isEmpty()) return new ItemStack (Material.AIR);
		String [] ln = str.split(",");
		if (ln.length==0) return new ItemStack (Material.AIR);
		ItemStack item = u.parseItemStack(ln[u.random.nextInt(ln.length)]);
		if (item == null) return new ItemStack (Material.AIR);
		item.setAmount(1);
		item = colorizeRndLeather(item);
		return item;
	}

	public ItemStack colorizeRndLeather(ItemStack item){

		if ((item.getType()!=Material.LEATHER_BOOTS)&&
				(item.getType()!=Material.LEATHER_CHESTPLATE)&&
				(item.getType()!=Material.LEATHER_HELMET)&&
				(item.getType()!=Material.LEATHER_LEGGINGS)) return item.clone();
		if (!plg.cfgB("eqarmcolor")) return item.clone();
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

		meta.setColor(clr[u.random.nextInt(clr.length)]);
		item.setItemMeta(meta);
		return item.clone();
	}


	public ItemStack setRandomEnchantment (ItemStack item, String enchlist, int maxlevel){
		ItemStack itm = item.clone();
		if (enchlist.isEmpty()) return itm.clone();
		String [] ln = enchlist.split(",");
		String enc = ln[u.getRandomInt(ln.length)];
		Enchantment e = Enchantment.getByName(enc.toUpperCase());
		if (e == null) return itm.clone();
		itm.addUnsafeEnchantment(e, u.getRandomInt(maxlevel)+1);
		return itm.clone();
	}


}
