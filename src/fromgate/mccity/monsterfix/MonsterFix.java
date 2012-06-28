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

/*  
 * Permissions:
 * monsterfix.config
 * monsterfix.farmer
 * monsterfix.hugemushroom
 * monsterfix.kill (устарело)
 * monsterfix.badluck
 * monsterfix.sharpshooter
 * monsterfix.snowball
 * monsterfix.cheats
 * monsterfix.cheats.zmbzcheat
 * monsterfix.cheats.zmbnoclip
 * monsterfix.cheats.cjbxray
 * monsterfix.cheats.reicave
 * monsterfix.fly
 * monsterfix.fly.zmbfly
 * monsterfix.fly.cjbfly
 * monsterfix.radar
 * monsterfix.radar.reiradar
 * monsterfix.radar.cjbradar
 * monsterfix.radar.mcautomap
 * 
 * monsterfix.fly.flymode
 * monsterfix.cheats.unfreeze
 * 
 * monsterfix.enderperltp
 * monstefix.highlander
 * monsterfix.highlander.space-suit
 * 
 * monsterfix.chat.font
 * monsterfix.chat.color-basic
 * monsterfix.chat.color-red
 * monsterfix.chat.hidden
 * 
 *  0.1.8
 * monsterfix.lamp.place
 * monsterfix.lamp.break
 * 
 * 0.2.0
 * monsterfix.command.<команда>
 * monsterfix.paintwool
 * 
 * 
 * 0.2.1
 * monsterfix.joininvulnerability
 * monsterfix.drop.xp
 * monsterfix.drop.items
 * monsterfix.unlhblock
 * 
 */

/*
 * Новое в 0.1.5
 * +1. Раздельные пермишены на все читы
 * -2. При сбитии шапка не "горит". Ну по крайней мере повреждение регулируется ;)
 * +3. warnplayer
 * -4. Отключение бессмертия при логине
 * +5. Центрирование игрока при логине
 * - удалялка воркбенчей и печек установленных на природе
 *   
 *    0.1.6 Change log
 * - исправлена путаница в блоккодах
 * - добавлена заморозка при попытке лечь в кровать
 * - добавлен пермишен monsterfix.cheater.unfreeze на игнорирование заморозки
 * - sneakhrt
 * - Фикс чата, добавлена возможность использования цвета в сообщениях
 * 
 *   0.1.7
 * + New config.yml. Sorry you need to delete old config file
 * + All fixes grouped in atifarm,   
 * + фикс мобоферм - скотобоен // New antifarm: butchery (trap) fix   
 * + новый режим для борьбы с фармом снеговиков: снеговики оставляют снег, но снежки с него будут падать через какое-то время.
 * + введено деление фиксов на группы
 * + изменен подход к конфигу, по идее он будет более наглядным и удобным
 * + фикс дропа при смерти моба не от игроков
 * + небольшое исправление дропа кактусов: если у кактуса разбить один блок - дропается весь кактус
 * + отключение жемчужины 
 * + грустно на высоте (скафандр, затрудненное дыхание) ;)
 * + исправлена ошибка - команда /killl блокировалась всегда
 * + добавлен параметр decolor
 * + /mfix help теперь выводит и подсказку по доступным в чате цветам
 * + фикс генератора обсидиана
 * + ограничение максимума регенерации хп.
 * + контроль взрывов TNT/Криперы/Огненные шары (пока без полного контроля времени детонации....)
 * + автозакрывалка инвентаря игроков при save-all 
 * + блок-код для MCAutomap
 * 
 *  0.1.8
 * + баг с неотправкой кодов
 * + неверное сохранение в конфиге
 * + "штраф" для превышение лимита - умножение счетчик в х раз.
 * + можно устанавливать включенные лампы.
 * 
 * 0.1.9
 * + удален SBC (рекомендация пользоваться SBC)
 * + фикс прохождения сквозь стекло (панели и проч.) с помощью эндер-перлов.
 * + отключалка порталов в незер и энд (по эндовским порталам можно ходить пешком ;))
 * + изменился конфиг для мобспавнеров, падалок и ловушек. Добавлены списки - исключения
 * + убрана переменная btchanimal (вместо неё используется список btchexcept)
 * + Оптимизация работы "фризера".
 * + добавлен список биомов, в которых не будет формироваться снег
 * + добавлен debug-режим для ламп
 * 
 *  0.2.0
 *  + устранена ошибка: игроки не дропали лут в бойнях
 *  + блокировка команд по списку
 *  + замена двойных (тройных) пробелов на одинарный при вводе команд.
 *  + окраска шерсти
 *  + Проверка версий
 *  + Metrics
 *  
 *  0.2.1
 *  + добавлена поддержка заприваченных территорий в фикс на покраску шерсти и снежки со снегом
 *  + убран фикс на деревья прорастающие сквозь бедрок
 *  + полностью переделана система хедшотов
 *  + исправлен баг на пролезание "вприсядку" сквозь закрытые двери
 *  + возможность регулироваь длительность неуязвимости при заходе на сервер
 *  + запрет на установку блоков выше определенной высоты
 *  
 *  0.2.2
 *  + Дополнительный god-mode для игроков в creative
 *  + Восстановление сил при выключение из creative
 *  + Рекод текста в чате, командах, табличках
 *  
 * TODO
 * 
 * - удалялка ссылок (ммм.. нужен грамотный regexp) 
 * - разобраться с пересчетом света (трэш - факелы)
 *  * - давилка на рельсах?
 * - добить идею универсальности хранения переменных: сделать один тип переменных на все типы данных и перевести все это на хэшмэпы. тогда не будут нужны всякие промежуточные "быстрые" переменные.
 * - поддержка мультимиров
 * - - после мультмиров - управление мобами (частотой спавна, возможностью спавна) в каждом мире. 
 * - дополнение к снеговикам - возможность отменять снег только в определенных биомах
 * - дополнение к снегопадам - возможность "выпадение" снега в определенных биомах 
 * - ограничение на рытье "в глубину".
 * - использовать энчант на "дыхание" как необходимый параметр для проверки брони
 * - генератор коблстоуна (нужно ли?)
 * - добавить автозакрывалку инвентаря через какое-то время
 * - регулировать время детонации криперов http://forums.bukkit.org/threads/adjustable-creeper-timer.68000/
 * - доразбираться с кактусами  
 * 
 * 
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Random;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class MonsterFix extends JavaPlugin{



	// Кэш переменных
	float noarmour = 0.1f;
	float leatherarm = 0.4f;
	float chainmail =  0.6f; //1.2f;
	float goldarm = 1.0f; //1.4f
	float steelarm = 1.2f;   //1.5f
	float diamondarm = 1.5f; //2.0f;
	float sneakexh = 0.3f;
	boolean fixsneak = true;
	boolean sneakhrt = true;
	boolean  fixsprint = true;
	boolean fixwatersoil = true;
	int soilchance = 25;
	boolean fixwheatfarm = true;
	boolean fixcactusfarm = true;
	boolean fixmobfall = true;
	boolean fixmushroom = true;
	boolean fixsnow = true;
	String unsnowedblocks = "43,20";
	boolean snowball = true;
	boolean fixsnowman = true;
	boolean fixboatplace = true;
	boolean msdrop = true;
	boolean fixmelpum = true;
	boolean rmvtrash = true;
	int rmvlevel = 63;
	String rmvblocks = "";
	String rmvnatural = "";
	int rmvtime = 60;
	boolean cncfreeze = true; 
	int cncfrtime= 100;
	String cnclswitch = "54,61,62,64,69,77,96,84,107,23";
	boolean decolor = true;
	boolean chatcolor = true;
	boolean butchery = true;
	int btchradius = 5;
	int btchcount = 10;
	int btchpenalty=2;
	boolean btchprogres = true;
	boolean plrdrop = true;
	boolean smcrust = true;
	int smcrtime = 30;
	boolean enderpearl = true;
	boolean highlands = true;
	int hllevel = 200;
	int hldmg = 2;
	boolean hlbuild = true;
	boolean hlswitch = true;
	boolean hlusesps = true;
	String hlspsuit = "305,304,303,302";
	boolean tnt=true;
	float tntr=4;
	float tntmr=6;
	boolean tntfire=false;
	boolean crp=true;
	float crpr=3;
	float crpmr=4;
	float crpmpwr=2;
	boolean crpfire=false;
	boolean fbl=true;
	float fblr=3;
	float fblmr=3;
	boolean fblfire=true;
	boolean detonate = true;
	int dtnmax = 3;
	int dexplchance = 30;
	int dfirechance = 20;
	int dlighchance = 30;
	int dprtime = 20;
	int dmprtime = 80;
	int dtndelay = 1500;
	boolean obsigen = true;
	boolean nohpregen = true;
	int nohpmax = 18;
	boolean lamp=true;
	boolean eptpblock = true;
	//boolean eptpfreeze = true;
	boolean lampdebug = false;
	String btchexcept = "SnowMan,Enderman,Ghast,Cow,Chicken,MushroomCow,Sheep";
	String mfexcept ="Pig,Cow,Sheep,Chicken";
	String msexcept ="Blaze";
	String mobnodrop = "emptylist";
	boolean eport = true;
	boolean eportperm = true;
	boolean nport = true;
	boolean nportperm = true;
	ArrayList<Biome> unsnowbiome = new ArrayList<Biome>();
	boolean colorwool = true;
	boolean colorwoolwhite = true;
	boolean headshots=true;
	boolean hsnpc = false;
	int hschance=20;
	int hsbadluck = 25;
	int hssharp=25;
	int hsmobchance=20;
	boolean hsarrow=true;
	int hsadmghead=7;
	int hsadmghelm=5;
	boolean hsadrop=true;
	int hsabreak=25;
	boolean hssnowball=true;
	int hssbdmghead=4;
	int hssbdmghelm=2;
	boolean hssbdrop=false;
	int hssbbreak=5;
	boolean hsegg=true;
	int hsedmghead=2;
	int hsedmghelm=0;
	boolean hsedrop=false;
	int hsebreak=0;
	boolean hsfishrod=true;
	int hsfdmghead=0;
	int hsfdmghelm=0;
	boolean hsfdrop=true;
	int hsfbreak=0;

	boolean permdrop=false;


	boolean lhplace=true;
	boolean lhbmsg=true;
	int lheight=90;
	String lhblock="10,11";

	boolean cpfix = false;
	String cpwrong = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ¸¨";
	String cpright = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяёЁ";



	/*
	 * Creeper,Skeleton,Spider,Giant,Zombie,Slime,Ghast,PigZombie,Enderman,CaveSpider,Silverfish,
	 * Blaze,LavaSlime,EnderDragon,Pig,Sheep,Cow,Chicken,Squid,Wolf,MushroomCow,SnowMan,Villager
	 */


	//разные переменные


	String permblck = "0,6,8,9,10,11,27,28,31,32,37,38,39,40,50,51,55,59,63,64,65,66,69,75,76,77,83,93,94,104,105,106,117";
	String snowballable ="1,2,3,4,5,7,12,13,14,15,16,17,18,19,20,21,22,23,24,25,35,41,42,43,45,46,47,48,49,52,54,56,57,58,60,61,70,72,73,74,78,79,80,82,84,86,87,88,89,91,95,97,98,99,100,103,110,112,118,121,123,124";
	String allowcactus="0,6,27,28,31,32,37,38,39,50,55,59,64,65,66,69,70,72,75,76,77,83,93,94,104,105,106";
	String emptyblock = "0,6,8,9,10,11,30,31,32,37,38,39,40,50,51,55,59,63,65,66,68,69,70,72,75,76,77,83,90,93,94,104,105,106,115";

	String px = ChatColor.AQUA+"[mfix] "+ChatColor.WHITE;
	Random random = new Random ();

	int tid_freeze =0;
	int tid_save = 0;
	int tid_mclear = 0;
	int tid_pdmg = 0;
	int skipdmg=0;
	int tid_trash = 0;
	boolean tid_freeze_b=false;
	boolean tid_save_b = false;
	boolean tid_mclear_b = false;
	boolean tid_pdmg_b = false;
	boolean tid_trash_b = false;
	int minute_tid = 0;



	ArrayList<Entity> mspmobs = new ArrayList<Entity> (); // мобы из мобспавнера
	ArrayList<Entity> mobdmg = new ArrayList<Entity> ();  // стукнутые мобы
	ArrayList<MFTrashBlock> trashcan = new ArrayList<MFTrashBlock>(); // мусорная корзина
	ArrayList<MFButchery> butch = new ArrayList<MFButchery>(); // скотобойни
	HashMap<Player, Long> openinv = new HashMap<Player, Long>(); // замороженные игроки
	HashMap<Location,Integer> snowtrails = new HashMap<Location,Integer>(); //нарытый снег
	ArrayList<Block> lamps = new ArrayList<Block>();

	Long lastdtntime = 0L; 


	// хранилище переменных
	ArrayList<MFInt> cfgi = new ArrayList<MFInt>();
	ArrayList<MFBool> cfgb = new ArrayList<MFBool>();
	ArrayList<MFStr> cfgs = new ArrayList<MFStr>();
	ArrayList<MFFloat> cfgf = new ArrayList<MFFloat>();
	HashMap<String, Boolean> cfggroup = new HashMap<String, Boolean>();
	HashMap<String, String> perms = new HashMap<String, String>(); 

	public FileConfiguration config;
	File directory;


	private Logger log = Logger.getLogger("Minecraft");
	private MFBlockListener bl;
	private MFPlayerListener pl;
	protected MFFreeze fl;
	private MFCommands Commander;
	PluginDescriptionFile des;
	public MFUtil u;


	public void InitCfg(){
		cfgb.clear();
		cfgi.clear();
		cfgf.clear();
		cfgs.clear();

		cfgb.add(new MFBool ("permdrop","antifarm",false,"drop-depends-by-permissions","Drop depends by permissions"));
		cfgb.add(new MFBool ("msdrop","antifarm",true,"mobspawned-drop.enable","No drop from mobs from mobspawner"));
		cfgs.add(new MFStr ("msexcept","antifarm",msexcept,"mobspawned-drop.exception-mob-list","Mobspawned-mob list that WILL produce drop"));




		cfgb.add(new MFBool ("melon","antifarm",true,"melon-pumpkin-grow","Fix unlimited mellon and pumpkin grow"));
		cfgb.add(new MFBool ("whtfarm","antifarm",true,"wheat-farm","Fix wheat farming (turn soil to dirt)"));
		cfgi.add(new MFInt ("mlnchance","antifarm",25,"smet-wheat-survival-chance","Wheat, melon and pumpkin smets survival chance"));

		cfgb.add(new MFBool ("watersoil","antifarm",true,"water-soil-to-dirt","Fix wheat farming with water (turn soil to dirt)"));

		cfgb.add(new MFBool ("cactus","antifarm",true,"cactus-auto-farm-fix","Fix cactus auto-farming"));
		cfgb.add(new MFBool ("mobfall","antifarm",true,"mob-first-fall-dmg","Fix mob farming (cancel damage from fist fall)"));
		cfgs.add(new MFStr ("mfexcept","antifarm",mfexcept,"mob-first-fall-dmg.exception-mob-list","Exception list for mob fall damage fix"));
		cfgb.add(new MFBool ("mushroom","antifarm",true,"huge-mushroom-micel-only","Huge mushroom grows only on mycelium"));
		cfgb.add(new MFBool ("snowman","antifarm",true,"snowmain-trail.enable","Fix Snowgolem trails"));
		cfgb.add(new MFBool ("smcrust","antifarm",true,"snowmain-trail.wait-snow-crust","Snowgolem trails are remain, but you need time before collect snowballs")); //0.1.7
		cfgi.add(new MFInt ("smcrtime","antifarm",30,"snowmain-trail.wait-snow-crust-time","Time to wait before collect snowballs from snowman trails (seconds, rounded to tens)"));   //0.1.7


		cfgb.add(new MFBool ("butchery","antifarm",true,"butcheries.enable","Detect and prevent butcheries (item and XP farming in builded mobspawner)"));
		cfgi.add(new MFInt ("btchradius","antifarm",5,"butcheries.radius","Butchery radius"));
		cfgi.add(new MFInt ("btchcount","antifarm",50,"butcheries.mob-limit","Butchery mob limit (when limit reached, item and xp drop will be disabled)"));
		cfgb.add(new MFBool ("btchprogres","antifarm",true,"butcheries.progressive-drop-decrease","Progressive (counted by percent of limit)"));
		cfgi.add(new MFInt ("btchpenalty","antifarm",2,"butcheries.limit-exceed-penalty","Butchery mob limit exceed penalty"));
		cfgs.add(new MFStr ("btchexcept","antifarm",btchexcept,"butcheries.exception-mob-list","Mob exception list for butcheries"));
		cfgb.add(new MFBool ("plrdrop","antifarm",true,"drop-only-player-killer","Drop items and XP only if mob killed by player"));
		cfgs.add(new MFStr ("mobnodrop","antifarm",mobnodrop,"mob-with-no-drop","Mob list without any drop"));
		cfgb.add(new MFBool ("obsigen","antifarm",true,"obsidian-generators","Fix unlimited obsidian generators"));
		cfgb.add(new MFBool ("saveall","system",true,"save-all.enable","Periodically run save-all command"));
		cfgi.add(new MFInt ("saveint","system",30,"save-all.time-interval","Saving-all interval (minutes)"));
		cfgb.add(new MFBool ("savemsg","system",true,"save-all.show-message","Show saving-all message"));
		cfgb.add(new MFBool ("saveinvclose","system",true,"save-all.close-inventories","Force players to close inventory (prevent item duping)"));
		cfgb.add(new MFBool ("lampdebug","system",false,"lamps.debug","Debug mode for redstone lamps - disable redstone lamps switching"));
		cfgb.add(new MFBool ("vcheck","system",false,"monsterfix.version-check","MonsterFix version update check"));
		cfgb.add(new MFBool ("crgod","system",true,"full-god-mode-in-creative.enable","Prevent all damages in creative (usually from plugins)"));
		cfgb.add(new MFBool ("crheal","system",true,"full-god-mode-in-creative.heal-in-creative","Heal player in creative mode (will grant full health after switching to survival)"));

		cfgb.add(new MFBool ("cpfix","system",false,"codepage-fix.enable","Enable codepage fix (chat and signs)"));
		cfgs.add(new MFStr ("cpwrong","system","ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ¸¨","codepage-fix.wrong-symbols","Wrong code page symbols"));
		cfgs.add(new MFStr ("cpright","system","АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяёЁ","codepage-fix.right-symbols","Right code page symbols"));

		//protected static String RuASimbol = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ¸¨";


		// TODO что-то нифига не работает. 		
		//cfgb.add(new MFBool ("blocklink","system",true,"block-links-in-chat","Disable links in chat"));
		//cfgb.add(new MFBool ("blockkill","system",true,"block-killll-command","Block command /kill"));


		cfgb.add(new MFBool ("singlespace","system",true,"single-spaces-only","Replace all double (treble..) spaces in typed commands with a single space"));
		cfgb.add(new MFBool ("blockcmd","system",true,"block-commands.enable","Block commands"));
		cfgs.add(new MFStr ("blockcmdlist","system","kill,plugins,pl,pluginlist","block-commands.list","Blocked command list"));

		// цвет!
		cfgb.add(new MFBool ("decolor","system",true,"colored-chat.colors-in-chat.decolorize","Decolorize chat-message received by client"));
		cfgb.add(new MFBool ("chatcolor","system",true,"colored-chat.colors-in-chat.enable","Allow to set color in chat with &1...&f, &k in chat"));
		cfgs.add(new MFStr ("defchatcolor","system","&a","colored-chat.colors-in-chat.default-color","Default color for chat messages"));
		cfgb.add(new MFBool ("cmdcolor","system",true,"colored-chat.colors-in-cmd.enable","Allow to use colors in defined commands"));
		cfgs.add(new MFStr ("cmdcolorlist","system","say,msg,tell,r","colored-chat.colors-in-cmd.list","List of command that allow to use colors"));

		//cfgb.add(new MFBool ("sbc","anticheat",true,"send-block-codes","Block CJB and Zombe mod cheats"));
		cfgb.add(new MFBool ("boat","anticheat",true,"boat-fix","Fix anywhere boat placing"));
		//центирорвание игрока - НЕДОДЕЛАНО! - стекла, ршётки 
		

		cfgb.add(new MFBool ("jpcenter","anticheat",true,"on-join-player-coordinates-fix","Сenter player relative to block on which it stands"));
		//антипролезалка сквозьстены
		cfgb.add(new MFBool ("cncfreeze","anticheat",true,"freeze-after-deny.enable","Freeze player after cancelled action"));
		cfgi.add(new MFInt ("cncfrtime","anticheat",100,"freeze-after-deny.freeze-time","Player freezing time (ms) after cancelled action"));
		//cfgb.add(new MFBool ("sneakdoor","anticheat",true,"block-sneakers-door","Block sneaking players to pass through private doors"));

		// отключение бессмертия при заходе.  Работает, спасибо Галарану :)
		cfgb.add(new MFBool ("jinvul","anticheat",true,"on-join-invulnerability-fix.enable","Fix on-join invulnerability"));
		cfgi.add(new MFInt ("jinvultick","anticheat",1,"on-join-invulnerability.tick-time","Invulnerability period after player join (ticks)"));

		cfgb.add(new MFBool ("eptpblock","anticheat",true,"prevent-tp-into-block","Preventing teleporting into non-empty block"));
		//cfgb.add(new MFBool ("eptpfreeze","anticheat",true,"freeze-after-ender-pearl-tp","Freeze player efter ender pearl teleportation"));

		cfgb.add(new MFBool ("unsnow","world",true,"unsnowable.enable","Prevent snowforming on specified block or in defined biomes"));
		cfgs.add(new MFStr ("unsnowblock","world","43,20","unsnowable.blocks.block-list","Unsnowable blocks list"));
		cfgs.add(new MFStr ("unsnowbiome","world","","unsnowable.biomes","Unsnowable biome list"));


		cfgb.add(new MFBool ("rmvtrash","world",true,"world-trash-remover.enable","Remove blocks placed on natural blocks"));
		cfgi.add(new MFInt ("rmvtime","world",30,"world-trash-remover.remove-delay","Period of time (minutes) after which trash-blocks will be removed"));
		cfgi.add(new MFInt ("rmvlevel","world",60,"world-trash-remover.level-y","The level over which the area will be cleaned"));
		cfgs.add(new MFStr ("rmvblocks","world","58","world-trash-remover.trash-blocks","Blocks to remove from the nature"));  //58,50
		cfgs.add(new MFStr ("rmvnatural","world","0,1,2,3,8,9,10,11,12,13,17,18,24,31,32,37,38,39,40,78,79,82,83,86,99,100,106,110,111,50,58","world-trash-remover.natural-blocks","Natural block list"));

		cfgb.add(new MFBool ("nport","world",true,"portals.nether.prevent","Prevent using Nether portals"));
		cfgb.add(new MFBool ("nportperm","world",true,"portals.nether.use-permissions","Using permissions to accessing nether-portals"));

		cfgb.add(new MFBool ("eport","world",true,"portals.ender.prevent","Prevent using End portals"));
		cfgb.add(new MFBool ("eportperm","world",true,"portals.ender.use-permissions","Using permissions to accessing ender-portals"));

		//0.1.8 
		cfgb.add(new MFBool ("lamp","world",true,"lamps.enable","Allow to place turned on redstone lamps"));
		//0.1.10
		cfgb.add(new MFBool ("colorwool","world",true,"color-wool.enable","Use dyes to set color of wool blocks"));
		cfgb.add(new MFBool ("colorwoolwhite","world",true,"color-wool.only-white","Allow to color only white wool blocks"));

		cfgb.add(new MFBool ("lhplace","world",true,"limit-height.enable","Height restriction for placing defined blocks"));
		cfgb.add(new MFBool ("lhbmsg","world",true,"limit-height.show-message","Show height-limit warning message"));
		cfgi.add(new MFInt ("lheight","world",90,"limit-height.lava","Height value to deny placing forbidden blocks"));
		cfgs.add(new MFStr ("lhblock","world","10,11","limit-height.lava","Forbidden blocks list"));

		cfgb.add(new MFBool ("tpveject","world",true,"eject-player-from-vehicle-on-teleporting","Eject player from any vehicle when he trying to teleport"));


		//cfgb.add(new MFBool ("vines","world",true,"vines.enable","Control grow of the vines"));  //TODO

		cfgb.add(new MFBool ("warnplayer","system",true,"permissions-warning.enable","Warning player about monstefix permissions"));
		cfgb.add(new MFBool ("warnplempty","system",true,"permissions-warning.show-empty-warning","Show empty warning about player permissions"));

		cfgb.add(new MFBool ("snowball","gameplay",true,"snowball-place-snow","Place snow with snowball throw"));
		cfgb.add(new MFBool ("enderpearl","gameplay",true,"block-enderpearl-tp","Prevent teleporting with enderpearl"));		
		cfgb.add(new MFBool ("zombiedoor","gameplay",true,"zombie-break-door-fix","Prvent zombies from breaking doors"));

		// Хедшоты!
		cfgb.add(new MFBool ("headshots","gameplay",true,"headshots.enable","Headshots"));
		cfgb.add(new MFBool ("hsnpc","gameplay",true,"headshots.npc","NPC headshots"));
		cfgi.add(new MFInt ("hschance","gameplay",30,"headshots.chance.basic","Headshot chance"));
		cfgi.add(new MFInt ("hsbadluck","gameplay",25,"headshots.chance.badluck","Badlucker chance modifier"));
		cfgi.add(new MFInt ("hssharp","gameplay",25,"headshots.chance.sharpshooter","Sharpshooter chance modifier"));
		cfgi.add(new MFInt ("hsmobchance","gameplay",20,"headshots.chance.mob","Chance to receive headshot from mob"));

		// Стрелы		
		cfgb.add(new MFBool ("hsarrow","gameplay",true,"headshots.arrow.enable","Arrow-headshots"));
		//cfgs.add(new MFStr("hsadamage","gameplay","5,5,6,6,7,8","headshots.arrow.damage","Arrow damage defined by helmette (Diamond/Iron/Gold/Chain/Leahter/No helm)"));
		cfgi.add(new MFInt ("hsadmghead","gameplay",7,"headshots.arrow.damage.head","Arrow damage (without helm)"));
		cfgi.add(new MFInt ("hsadmghelm","gameplay",5,"headshots.arrow.damage.head","Arrow damage (head protected by helm)"));
		cfgb.add(new MFBool ("hsadrop","gameplay",true,"headshots.arrow.helm.drop","Arrow knocks down the helmet"));
		cfgi.add(new MFInt ("hsabreak","gameplay",20,"headshots.arrow.helm.break","Arrow breaks the helm (percent of max durability)"));

		//Снежки
		cfgb.add(new MFBool ("hssnowball","gameplay",true,"headshots.snowball.enable","Snowball-headshots"));
		cfgi.add(new MFInt ("hssbdmghead","gameplay",3,"headshots.snowball.damage.head","Snowball damage (without helm)"));
		cfgi.add(new MFInt ("hssbdmghelm","gameplay",1,"headshots.snowball.damage.head","Snowball damage (head protected by helm)"));
		cfgb.add(new MFBool ("hssbdrop","gameplay",false,"headshots.snowball.helm.drop","Snowball knocks down the helmet"));
		cfgi.add(new MFInt ("hssbbreak","gameplay",5,"headshots.snowball.helm.break","Snowball breaks the helm (percent of max durability)"));

		//Яйца
		cfgb.add(new MFBool ("hsegg","gameplay",true,"headshots.egg.enable","Egg-headshots"));
		cfgi.add(new MFInt ("hsedmghead","gameplay",2,"headshots.egg.damage.head","Egg damage (without helm)"));
		cfgi.add(new MFInt ("hsedmghelm","gameplay",0,"headshots.egg.damage.head","Egg damage (head protected by helm)"));
		cfgb.add(new MFBool ("hsedrop","gameplay",false,"headshots.egg.helm.drop","Egg knocks down the helmet"));
		cfgi.add(new MFInt ("hsebreak","gameplay",0,"headshots.egg.helm.break","Egg breaks the helm (percent of max durability)"));

		//Удочка
		cfgb.add(new MFBool ("hsfishrod","gameplay",true,"headshots.fish-rod.enable","Fishing rod headshots"));
		cfgi.add(new MFInt ("hsfdmghead","gameplay",0,"headshots.fish-rod.damage.head","Fishing rod damage (without helm)"));
		cfgi.add(new MFInt ("hsfdmghelm","gameplay",0,"headshots.fish-rod.damage.head","Fishing rod damage (head protected by helm)"));
		cfgb.add(new MFBool ("hsfdrop","gameplay",true,"headshots.fish-rod.helm.drop","Fishing rod knocks down the helmet"));
		cfgi.add(new MFInt ("hsfbreak","gameplay",0,"headshots.fish-rod.helm.break","Fishing rod breaks the helm (percent of max durability)"));



		cfgb.add(new MFBool ("sprint","gameplay",true,"sprint.enable","Fix sprinting with weared armour"));
		cfgf.add(new MFFloat ("spnoarm","gameplay",0.1f,"sprint.no-armour-mofifier","Sprinting (without armour) exhaustion modifier"));
		cfgf.add(new MFFloat ("spleather","gameplay",0.4f,"sprint.leather-mofifier","Leather armour's exhaustion modifier"));
		cfgf.add(new MFFloat ("spchain","gameplay",0.6f,"sprint.chainmail-mofifier","Chainmail armour's exhaustion modifier"));
		cfgf.add(new MFFloat ("spgold","gameplay",1.0f,"sprint.gold-mofifier","Golden armour's exhaustion modifier"));
		cfgf.add(new MFFloat ("spiron","gameplay",1.2f,"sprint.iron-mofifier","Iron armour's exhaustion modifier"));
		cfgf.add(new MFFloat ("spdiamond","gameplay",1.5f,"sprint.diamond-mofifier","Diamond armour's exhaustion modifier"));

		cfgb.add(new MFBool ("sneak","gameplay",true,"sneak.enable","Fix long-time sneaking"));
		cfgb.add(new MFBool ("sneakhrt","gameplay",true,"sneak.hurt-effect","Play hurt effect when (after long time sneaking)"));
		cfgf.add(new MFFloat ("sneakex","gameplay",0.2f,"sneak.exhaustion-modifier","Long-time sneaking exhaustion modifier"));

		// highlands
		cfgb.add(new MFBool ("highlands","gameplay",true,"highlands.enable","Hard breathe in highlands"));
		cfgi.add(new MFInt ("hllevel","gameplay",200,"highlands.level","Highlands level height"));
		cfgi.add(new MFInt ("hldmg","gameplay",2,"highlands.damage","Damage per second, dealt in highlands"));
		cfgb.add(new MFBool ("hlbuild","gameplay",true,"highlands.prevent-build","Prevent build and destroy in highlands"));
		cfgb.add(new MFBool ("hlswitch","gameplay",true,"highlands.prevent-switch","Prevent use items (levers, chest, etc...) in highlands"));
		cfgb.add(new MFBool ("hlusesps","gameplay",true,"highlands.use-space-suit","Use space suit in highlands"));
		cfgs.add(new MFStr ("hlspsuit","gameplay","305,304,303,302","highlands.space-suit","Armour that used as space-suit (helmet,chestplate,leggins,boots)"));

		cfgb.add(new MFBool ("nohpregen","gameplay",true,"nohpregen.enable","Limit the maximum health regenerating level"));
		cfgi.add(new MFInt ("nohpmax","gameplay",18,"nohpregen.max-hp-regen","Maximum regenerating health limit value"));
		cfgb.add(new MFBool ("tnt","explosion",true,"explosion.tnt.enable","TNT-explosion control"));
		cfgf.add(new MFFloat ("tntr","explosion",4f,"explosion.tnt.radius","TNT-explosion radius"));
		cfgf.add(new MFFloat ("tntmr","explosion",6f,"explosion.tnt.max-radius","TNT-explosion maximum radius"));
		cfgb.add(new MFBool ("tntfire","explosion",false,"explosion.tnt.fire","TNT-explosion fire"));
		cfgb.add(new MFBool ("crp","explosion",true,"explosion.creeper.enable","Creeper explosion control"));
		cfgf.add(new MFFloat ("crpr","explosion",3f,"explosion.creeper.radius","Creeper explosion radius"));
		cfgf.add(new MFFloat ("crpmr","explosion",4f,"explosion.creeper.max-radius","Creeper explosion maximum radius"));
		cfgf.add(new MFFloat ("crpmpwr","explosion",2f,"explosion.creeper.power-creeper-multiplier","Powered creeper explosion radius multiplier"));
		cfgb.add(new MFBool ("crpfire","explosion",false,"explosion.creeper.fire","Creeper explosion fire"));
		cfgb.add(new MFBool ("fbl","explosion",true,"explosion.fireball.enable","Ghast-fireball explosion control"));
		cfgf.add(new MFFloat ("fblr","explosion",1f,"explosion.fireball.radius","Ghast-fireball explosion radius"));
		cfgf.add(new MFFloat ("fblmr","explosion",2f,"explosion.fireball.max-radius","Ghast-fireball explosion maximum radius"));
		cfgb.add(new MFBool ("fblfire","explosion",true,"explosion.fireball.fire","Ghast-fireball explosion fire"));
		cfgb.add(new MFBool ("detonate","explosion",true,"explosion.detonate.enable","Detonate TNT in player inventory"));
		cfgi.add(new MFInt("dexplchance","explosion",30,"explosion.detonate.expl-chance","Explossion-damage inventory detonation chance "));
		cfgi.add(new MFInt("dfirechance","explosion",20,"explosion.detonate.fire-chance","Fire-damate inventory detonation chance"));
		cfgi.add(new MFInt("dlighchance","explosion",30,"explosion.detonate.light-chance","Lightning-damage inventory detonation chance"));
		cfgi.add(new MFInt("dprtime","explosion",20,"explosion.tnt.prime-time","TNT-prime time (ticks)")); //по умолчанию 80
		cfgi.add(new MFInt("dmprtime","explosion",80,"explosion.tnt.maxi-prime-time","Maximum TNT-prime time (ticks)")); //по умолчанию 80
		cfgi.add(new MFInt("dtnmax","explosion",3,"explosion.detonate.max-per-once","Maximum number of simultaneous TNT-detonation"));
		cfgi.add(new MFInt("dtndelay","explosion",1500,"explosion.detonate.delay","Delay between the detonations (milliseconds)"));


		cfgb.add(new MFBool ("flymode","gameplay",true,"allow-flight","Built-in fly mode"));


	}

	public void UpdateFastVar(){
		noarmour = cfgF("spnoarm");
		leatherarm = cfgF("spleather");
		leatherarm = cfgF("spchain");
		goldarm = cfgF("spgold");
		steelarm = cfgF("spiron");
		diamondarm = cfgF("spdiamond");
		sneakexh = cfgF("sneakex");
		fixsneak = cfgB("sneak");
		fixsprint = cfgB("sprint");
		headshots = cfgB("headshots");
		fixwatersoil = cfgB("watersoil");
		soilchance = cfgI("mlnchance");
		fixwheatfarm = cfgB("whtfarm");
		fixcactusfarm = cfgB("cactus");
		fixmobfall = cfgB("mobfall");
		fixmushroom = cfgB("mushroom");
		fixsnow = cfgB("unsnow");
		unsnowedblocks = cfgS("unsnowblock");
		snowball = cfgB("snowball");
		fixsnowman = cfgB("snowman");
		smcrust = cfgB("smcrust");
		smcrtime = cfgI("smcrtime");
		smcrtime = (smcrtime/10)*10;
		if (smcrtime<10) smcrtime = 10;
		cfgI ("smcrtime",smcrtime);
		fixboatplace = cfgB("boat");
		msdrop = cfgB("msdrop");
		fixmelpum = cfgB("melon");
		rmvtrash = cfgB("rmvtrash");
		rmvblocks = cfgS("rmvblocks");
		rmvnatural = cfgS("rmvnatural");
		rmvlevel = cfgI ("rmvlevel");
		rmvtime = cfgI ("rmvtime");
		cncfreeze = cfgB("cncfreeze");
		cncfrtime = cfgI ("cncfrtime");
		sneakhrt = cfgB("sneakhrt");
		chatcolor = cfgB("chatcolor");
		decolor = cfgB("decolor");
		butchery = cfgB("butchery");
		btchradius = cfgI ("btchradius");
		btchpenalty = cfgI ("btchpenalty");
		btchcount = cfgI ("btchcount");

		btchprogres = cfgB("btchprogres");
		plrdrop = cfgB("plrdrop");
		enderpearl = cfgB("enderpearl");
		highlands = cfgB("highlands");
		hllevel = cfgI ("hllevel");
		hldmg = cfgI ("hldmg");
		hlbuild = cfgB("hlbuild");
		hlswitch = cfgB("hlswitch");
		hlusesps = cfgB("hlusesps");
		hlspsuit = cfgS("hlspsuit");

		tnt=cfgB("tnt");
		tntr=cfgF("tntr");
		tntmr=cfgF("tntmr");
		tntfire=cfgB("tntfire");
		crp=cfgB("crp");
		crpr=cfgF("crpr");
		crpmr=cfgF("crpmr");
		crpmpwr=cfgF("crpmpwr");
		crpfire=cfgB("crpfire");

		fbl=cfgB("fbl");
		fblr=cfgF("fblr");
		fblmr=cfgF("fblmr");
		fblfire=cfgB("fblfire");

		detonate = cfgB("detonate");
		dexplchance = cfgI("dexplchance");
		dfirechance = cfgI("dfirechance");
		dlighchance = cfgI("dlighchance");
		dtnmax = cfgI("dtnmax");
		dprtime =  cfgI("dprtime");
		dmprtime =  cfgI("dmprtime");
		dtndelay = cfgI("dtndelay");

		obsigen=cfgB("obsigen");

		nohpregen =cfgB("nohpregen");
		nohpmax = cfgI("nohpmax");
		//tntprtime = cfgI("tntprtime");
		//tntmprtime = cfgI("tntmprtime");

		lamp = cfgB("lamp");
		//vines = cfgB("vines");

		eptpblock = cfgB("eptpblock");
		//eptpfreeze = cfgB("eptpfreeze");

		lampdebug = cfgB("lampdebug");

		mfexcept=cfgS("mfexcept");
		msexcept=cfgS("msexcept");
		btchexcept=cfgS("btchexcept");
		mobnodrop =cfgS("mobnodrop"); //нужна ли такая фигня?

		eport = cfgB("eport");
		eportperm = cfgB("eportperm");
		nport =  cfgB("nport");
		nportperm =  cfgB("nportperm");
		colorwool =  cfgB("colorwool");
		colorwoolwhite =  cfgB("colorwoolwhite");
		//sneakdoor = cfgB("sneakdoor");


		headshots=cfgB("headshots");
		hsnpc = cfgB("hsnpc");
		hschance=cfgI("hschance");
		hsbadluck = cfgI("hsbadluck");
		hssharp=cfgI("hssharp");
		hsmobchance=cfgI("hsmobchance");
		hsarrow=cfgB("hsarrow");
		hsadmghead=cfgI("hsadmghead");
		hsadmghelm=cfgI("hsadmghelm");
		hsadrop=cfgB("hsadrop");
		hsabreak=cfgI("hsabreak");
		hssnowball=cfgB("hssnowball");
		hssbdmghead=cfgI("hssbdmghead");
		hssbdmghelm=cfgI("hssbdmghelm");
		hssbdrop=cfgB("hssbdrop");
		hssbbreak=cfgI("hssbbreak");
		hsegg=cfgB("hsegg");
		hsedmghead=cfgI("hsedmghead");
		hsedmghelm=cfgI("hsedmghelm");
		hsedrop=cfgB("hsedrop");
		hsebreak=cfgI("hsebreak");
		hsfishrod=cfgB("hsfishrod");
		hsfdmghead=cfgI("hsfdmghead");
		hsfdmghelm=cfgI("hsfdmghelm");
		hsfdrop=cfgB("hsfdrop");
		hsfbreak=cfgI("hsfbreak");
		permdrop = cfgB("permdrop");

		lhplace=cfgB("lhplace");
		lhbmsg=cfgB("lhbmsg");
		lheight=cfgI("lheight");
		lhblock=cfgS("lhblock");

		cpfix = cfgB("cpfix");
		cpwrong = cfgS("cpwrong");
		cpright = cfgS("cpright");


		InitBiomeList();

	}


	public String cfgB2Str (String name){
		if (cfgB(name)) return ChatColor.GREEN+name;
		else return ChatColor.RED+name;
	}

	public String cfgI2Str (String name){
		return ChatColor.GREEN+name+"["+ChatColor.AQUA+Integer.toString(cfgI(name))+ChatColor.GREEN+"]";
	}

	public String cfgF2Str (String name){
		return ChatColor.GREEN+name+"["+ChatColor.LIGHT_PURPLE+Float.toString(cfgF(name))+ChatColor.GREEN+"]";
	}

	public String cfgS2Str (String name){
		return ChatColor.GREEN+name+"["+ChatColor.YELLOW+cfgS(name)+ChatColor.GREEN+"]";
	}


	public void PrintGrp (Player p, String grp){
		if ((!grp.isEmpty())&& cfggroup.containsKey(grp)){
			String str = ChatColor.YELLOW+"Group of options: "+ChatColor.LIGHT_PURPLE+grp+":";
			String tstr="~";

			for (int i = 0; i<cfgb.size();i++)
				if (cfgb.get(i).grp.equalsIgnoreCase(grp))	tstr = tstr+", "+cfgB2Str(cfgb.get(i).name);

			tstr = tstr.replace("~, ", "~");
			if (tstr.equalsIgnoreCase("~")) tstr = "";
			str = str+tstr;

			tstr = "~";
			for (int i = 0; i<cfgi.size();i++)
				if (cfgi.get(i).grp.equalsIgnoreCase(grp))
					tstr = tstr+", "+cfgI2Str(cfgi.get(i).name);

			tstr = tstr.replace("~, ", "~");
			if (tstr.equalsIgnoreCase("~")) tstr = "";
			str = str+tstr;

			tstr = "~";
			for (int i = 0; i<cfgf.size();i++)
				if (cfgf.get(i).grp.equalsIgnoreCase(grp))
					tstr = tstr+", "+cfgF2Str(cfgf.get(i).name);
			tstr = tstr.replace("~, ", "~");
			if (tstr.equalsIgnoreCase("~")) tstr = "";
			str = str+tstr;

			tstr = "~";
			for (int i = 0; i<cfgs.size();i++)
				if (cfgs.get(i).grp.equalsIgnoreCase(grp))
					tstr = tstr+", "+cfgS2Str(cfgs.get(i).name);
			tstr = tstr.replace("~, ", "~");
			if (tstr.equalsIgnoreCase("~")) tstr = "";
			str = str+tstr;

			String [] ln = str.split("~");

			p.sendMessage(ln);

		} else p.sendMessage(px+ChatColor.RED+"Group "+ChatColor.DARK_RED+grp+ChatColor.RED+" not found.");

	}

	@Override
	public void onDisable() {
		ClearTrash();
		log.info("MonsterFix disabled");
	}

	@Override
	public void onEnable() {


		bl = new MFBlockListener(this);
		pl = new MFPlayerListener(this);
		fl = new MFFreeze(this);

		des = getDescription();

		u = new MFUtil(this); //проверка версий и metrics
		u.UpdateMsg();


		log.info("MonsterFix v"+des.getVersion()+" enabled");

		config = getConfig();
		directory = getDataFolder();

		if (!directory.exists()) directory.mkdir();

		InitCfg();
		FillGroups();
		LoadCfg();
		SaveCfg();
		UpdateFastVar();
		Rst();

		Commander = new MFCommands(this);
		getCommand("mfix").setExecutor(Commander);

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this.pl, this);
		pm.registerEvents(this.bl, this);
		pm.registerEvents(this.fl, this);

		try {
			MetricsLite metrics = new MetricsLite(this);
			metrics.start();
		} catch (IOException e) {
			log.info("[wm] failed to submit stats to the Metrics (mcstats.org)");
		}


	}

	protected void FillGroups(){
		cfggroup.clear();
		for (int i = 0; i<cfgb.size();i++) if (!cfggroup.containsKey(cfgb.get(i).grp)) cfggroup.put(cfgb.get(i).grp, true);
		for (int i = 0; i<cfgi.size();i++) if (!cfggroup.containsKey(cfgi.get(i).grp)) cfggroup.put(cfgi.get(i).grp, true);
		for (int i = 0; i<cfgf.size();i++) if (!cfggroup.containsKey(cfgf.get(i).grp)) cfggroup.put(cfgf.get(i).grp, true);
		for (int i = 0; i<cfgs.size();i++) if (!cfggroup.containsKey(cfgs.get(i).grp)) cfggroup.put(cfgs.get(i).grp, true);
	}

	protected void TickMobClear() {
		if (mspmobs.size()>0) 
			for (int i = mspmobs.size()-1;i>=0;i--)
				if (mspmobs.get(i).isDead()) mspmobs.remove(i);
		if (mobdmg.size()>0)
			for (int i = mobdmg.size()-1;i>=0;i--)
				if (mobdmg.get(i).isDead()) mobdmg.remove(i);

	}

	protected void UnButch(){
		if (butch.size()>0)
			for (int i = butch.size()-1; i >=0;i--){
				butch.get(i).count--;
				if (butch.get(i).count <= 0) butch.remove(i);
			}
	}
	protected void TickSnowTrailsClear(){
		if (snowtrails.size()>0){
			Iterator<Entry<Location,Integer>> it = snowtrails.entrySet().iterator(); 
			while (it.hasNext()){
				Map.Entry<Location, Integer> ent = (Entry<Location, Integer>) it.next(); 
				if (ent.getValue()<=1) it.remove();
				else ent.setValue(ent.getValue()-1);
			}
		}

	}

	public void LoadCfg() {
		Set<Entry<String, Boolean>> entries = cfggroup.entrySet(); 
		Iterator<Entry<String, Boolean>> itr = entries.iterator();
		while (itr.hasNext()){
			Map.Entry<String, Boolean> ent = (Entry<String, Boolean>) itr.next();
			ent.setValue(getConfig().getBoolean(ent.getKey()+".enable",true));
		}

		for (MFBool c : cfgb) c.v=getConfig().getBoolean(c.grp+"."+c.node, c.v);
		for (MFInt c : cfgi) c.v=getConfig().getInt(c.grp+"."+c.node, c.v);
		for (MFStr c : cfgs) c.v=getConfig().getString(c.grp+"."+c.node, c.v);
		for (MFFloat c : cfgf) c.v=(float) getConfig().getDouble(c.grp+"."+c.node, c.v);
	}

	public void SaveCfg() {
		//config.options().header(getGroupComment());

		config.options().header("MonsterFix v"+des.getVersion()+" configuration file");

		Set<Entry<String, Boolean>> entries = cfggroup.entrySet(); 
		Iterator<Entry<String, Boolean>> itr = entries.iterator();
		while (itr.hasNext()){
			Map.Entry<String, Boolean> ent = (Entry<String, Boolean>) itr.next();
			config.set (ent.getKey()+".enable", ent.getValue());
		}


		//getConfig().setComment(String key, String comment)

		for (MFBool c : cfgb) config.set(c.grp+"."+c.node+"_description",c.txt);
		for (MFBool c : cfgb) config.set(c.grp+"."+c.node,c.v);
		for (MFInt c : cfgi) config.set(c.grp+"."+c.node+"_description",c.txt);
		for (MFInt c : cfgi) config.set(c.grp+"."+c.node,c.v);
		for (MFStr c : cfgs) config.set(c.grp+"."+c.node+"_description",c.txt);
		for (MFStr c : cfgs) config.set(c.grp+"."+c.node,c.v);
		for (MFFloat c : cfgf) config.set(c.grp+"."+c.node+"_description",c.txt);
		for (MFFloat c : cfgf) config.set(c.grp+"."+c.node,c.v);
		saveConfig(); 		
	}



	/*
	 * Наверное это все-таки бесполезно в том виде в котором оно действует...

	public String getGroupComment(){
		String str ="MonsterFix Configuration \n(c)2011,2012 fromgate, fromgate@gmail.com";
		Iterator<String> itr = cfggroup.keySet().iterator();
		while (itr.hasNext()){
			String grp = itr.next();
			str = str+ "\n\n==== "+ grp+"====";
			for (MFBool c : cfgb) 
				if (c.grp.equalsIgnoreCase(grp)) str = str + "\n"+c.node+" : "+c.txt;
			for (MFInt c : cfgi)
				if (c.grp.equalsIgnoreCase(grp)) str = str + "\n"+c.node+" : "+c.txt;
			for (MFStr c : cfgs) 
				if (c.grp.equalsIgnoreCase(grp)) str = str + "\n"+c.node+" : "+c.txt;
			for (MFFloat c : cfgf)
				if (c.grp.equalsIgnoreCase(grp)) str = str + "\n"+c.node+" : "+c.txt;
		}
		return str;
	}

	 */


	public String EnDis (boolean b){
		if (b) return ChatColor.GREEN+"enabled"+ChatColor.WHITE;
		return ChatColor.RED+"disabled"+ChatColor.WHITE;
	}

	public String EnDis (String str, boolean b){
		if (b) return ChatColor.GREEN+str+ChatColor.WHITE;
		return ChatColor.RED+str+ChatColor.WHITE;
	}

	public void SaveTick(){
		if (cfgB("saveinvclose")) {
			for (Player p: this.getServer().getOnlinePlayers()){
				if (p.isOnline()) p.closeInventory();
			}
		}
		if (cfgB("savemsg")) this.getServer().broadcastMessage(px+ChatColor.DARK_GRAY+"saving all...");
		this.getServer().dispatchCommand(this.getServer().getConsoleSender(), "save-all");

	}
	public float calcArmourModifier(Player p) {
		return calcBoots(p)+calcHelm(p)+calcChest(p)+calcLegs(p);
	}

	public float calcBoots (Player p){
		float fullarm = noarmour;
		if ((p.getInventory().getBoots()!=null)&&(p.getInventory().getBoots().getType()!=Material.AIR)){
			if (p.getInventory().getBoots().getType()==Material.LEATHER_BOOTS) fullarm = leatherarm;
			else if (p.getInventory().getBoots().getType()==Material.CHAINMAIL_BOOTS) fullarm = chainmail;
			else if (p.getInventory().getBoots().getType()==Material.IRON_BOOTS) fullarm = steelarm;
			else if (p.getInventory().getBoots().getType()==Material.GOLD_BOOTS) fullarm = goldarm;
			else if (p.getInventory().getBoots().getType()==Material.DIAMOND_BOOTS) fullarm = diamondarm;
		}
		return (fullarm/24*4);
	}

	public float calcHelm (Player p){
		float fullarm = noarmour;
		if ((p.getInventory().getHelmet()!=null)&&(p.getInventory().getHelmet().getType()!=Material.AIR)){
			if (p.getInventory().getHelmet().getType()==Material.LEATHER_HELMET) fullarm = leatherarm;
			else if (p.getInventory().getHelmet().getType()==Material.CHAINMAIL_HELMET) fullarm = chainmail;
			else if (p.getInventory().getHelmet().getType()==Material.IRON_HELMET) fullarm = steelarm;
			else if (p.getInventory().getHelmet().getType()==Material.GOLD_HELMET) fullarm = goldarm;
			else if (p.getInventory().getHelmet().getType()==Material.DIAMOND_HELMET) fullarm = diamondarm;			
		}

		return (fullarm/24*5);
	}

	public float calcChest (Player p){
		float fullarm = noarmour;
		if ((p.getInventory().getChestplate()!=null)&&(p.getInventory().getChestplate().getType()!=Material.AIR)){
			if (p.getInventory().getChestplate().getType()==Material.LEATHER_CHESTPLATE) fullarm = leatherarm;
			else if (p.getInventory().getChestplate().getType()==Material.CHAINMAIL_CHESTPLATE) fullarm = chainmail;
			else if (p.getInventory().getChestplate().getType()==Material.IRON_CHESTPLATE) fullarm = steelarm;
			else if (p.getInventory().getChestplate().getType()==Material.GOLD_CHESTPLATE) fullarm = goldarm;
			else if (p.getInventory().getChestplate().getType()==Material.DIAMOND_CHESTPLATE) fullarm = diamondarm;			
		}
		return (fullarm/24*8);
	}

	public float calcLegs (Player p){
		float fullarm = noarmour;
		if ((p.getInventory().getLeggings()!=null)&&(p.getInventory().getLeggings().getType()!=Material.AIR)){
			if (p.getInventory().getLeggings().getType()==Material.LEATHER_LEGGINGS) fullarm = leatherarm;
			else if (p.getInventory().getLeggings().getType()==Material.CHAINMAIL_LEGGINGS) fullarm = chainmail;
			else if (p.getInventory().getLeggings().getType()==Material.IRON_LEGGINGS) fullarm = steelarm;
			else if (p.getInventory().getLeggings().getType()==Material.GOLD_LEGGINGS) fullarm = goldarm;
			else if (p.getInventory().getLeggings().getType()==Material.DIAMOND_LEGGINGS) fullarm = diamondarm;
		}
		return (fullarm/24*7);
	}

	public void TickPlayerDmg(){
		if (Bukkit.getOnlinePlayers().length>0) {
			skipdmg++;
			if (skipdmg > 4) skipdmg = 0;

			for (Player p : Bukkit.getOnlinePlayers()) 
				if (p.getGameMode() == GameMode.SURVIVAL){

					//бегун
					if (fixsprint&&p.isSprinting()) IncExh(p, calcArmourModifier(p));

					//приседатель
					if (fixsneak&&p.isSneaking()) {
						if (p.getFoodLevel()>6)	IncExh(p, sneakexh);
						else p.setSneaking(false);
					}

					//высотник
					if ((skipdmg==0)&&highlands&&CheckPlayerInHighlands (p)) {
						if (hldmg>0) p.damage(hldmg);
						else p.playEffect(EntityEffect.HURT);
					}
				}
		}
	}


	protected boolean CheckPlayerInHighlands (Player p){
		if (p.hasPermission("monsterfix.highlander")||(p.getGameMode()==GameMode.CREATIVE)) return false;
		if (p.getLocation().getBlockY()>=hllevel){
			if (hlusesps&&p.hasPermission("monsterfix.highlander.space-suit")&&CheckSpaceSuit(p)) return false;
			return true;
		}
		return false;

	}

	protected boolean CheckSpaceSuit (Player p){
		if (!hlspsuit.isEmpty()){
			String ln [] = hlspsuit.split(",");
			if (ln.length==4)
				for (int i = 0;i<4; i++){
					int id = Integer.parseInt(ln[i]);
					if ((id>0)&&(id != p.getInventory().getArmorContents()[i].getTypeId())) return false;
				}
		}
		return true;
	}


	protected void IncExh (Player p, float dexh){
		float exh =  p.getExhaustion();
		float str = p.getSaturation();
		int flv = p.getFoodLevel();
		exh +=dexh;
		if ((exh>=4.0f)&&(str>0f)) {
			exh-=4.0f;
			if (str>0) {
				str = Math.max (str-1.0f, 0f);
				p.setSaturation(str);
			} else {
				flv = Math.max(flv-1, 0);
				p.setFoodLevel(flv);
			}
		}
		p.setExhaustion(exh);
	}	


	protected boolean cfgB (String name){
		for (MFBool bc : cfgb) 
			if ((bc.name.equalsIgnoreCase(name))&&(cfggroup.containsKey(bc.grp))) return (bc.v&&cfggroup.get(bc.grp));
		log.info("[mfix] Variable "+name+" was not found in cfgb-list!");			
		return false;
	}

	protected int cfgI (String name){
		for (MFInt ic : cfgi) 
			if (ic.name.equalsIgnoreCase(name)) return ic.v;
		log.info("[mfix] Variable "+name+" was not found in cfgi-list!");
		return -1;
	}

	protected void cfgI (String name, int nv){
		for (MFInt ic : cfgi) 
			if (ic.name.equalsIgnoreCase(name)) {
				ic.v=nv;
				return;
			} 
		log.info("[mfix] Variable "+name+" was not found in cfgi-list!");
	}

	protected String cfgS (String name){
		for (MFStr sc : cfgs) 
			if (sc.name.equalsIgnoreCase(name)) return sc.v;
		log.info("[mfix] Variable "+name+" was not found in cfgs-list!");
		return "";
	}

	protected float cfgF (String name){
		for (MFFloat fc : cfgf) 
			if (fc.name.equalsIgnoreCase(name)) return fc.v;
		log.info("[mfix] Variable "+name+" was not found in cfgf-list!");				
		return 0f;
	}


	// Функция предназначена для снижения параноидальных проявлений у игроков
	protected void WarnPlayer(Player p, boolean showempty){
		if (p.isOp()) {
			p.sendMessage(px+"You are OP, all permissions active :)");
			return;
		}
		ArrayList<String> str = new ArrayList<String>();
		if (p.hasPermission("monsterfix.farmer")) str.add("farmer");
		if (p.hasPermission("monsterfix.hugemushroom")) str.add("hugemushroom");
		if (p.hasPermission("monsterfix.kill")) str.add("kill");
		if (p.hasPermission("monsterfix.badluck")) str.add("badluck");
		if (p.hasPermission("monsterfix.sharpshooter")) str.add("sharphooter");

		if (p.hasPermission("monsterfix.cheats")) str.add("cheats");
		if (p.hasPermission("monsterfix.cheats.zmbzcheat")) str.add("Zombe Z-cheat");
		if (p.hasPermission("monsterfix.cheats.zmbnoclip")) str.add("Zombe NoClip");
		if (p.hasPermission("monsterfix.cheats.cjbxray")) str.add("CJB X-Ray");
		if (p.hasPermission("monsterfix.cheats.reicave")) str.add("Rei's cave");

		if (p.hasPermission("monsterfix.fly")) str.add("fly");
		if (p.hasPermission("monsterfix.fly.zmbfly")) str.add("Zombe fly");
		if (p.hasPermission("monsterfix.fly.cjbfly")) str.add("CJB Fly");
		if (p.hasPermission("monsterfix.fly.flymode")) str.add("MF flymode");

		if (p.hasPermission("monsterfix.radar")) str.add("map-radar");
		if (p.hasPermission("monsterfix.radar.reiradar")) str.add("Rei's radar");
		if (p.hasPermission("monsterfix.radar.cjbradar")) str.add("CJB radar");
		if (p.hasPermission("monsterfix.radar.mcautomap")) str.add("MC Automap");

		if (p.hasPermission("monsterfix.cheats.unfreeze")) str.add("MF unfreeze");

		if (p.hasPermission("monsterfix.chat.color-basic")) str.add("chat color - basic");
		if (p.hasPermission("monsterfix.chat.font")) str.add("chat font");
		if (p.hasPermission("monsterfix.chat.color-red")) str.add("chat color - red");
		if (p.hasPermission("monsterfix.chat.hidden")) str.add("chat color - hidden");
		if (p.hasPermission("monsterfix.enderpearltp")) str.add("enderpearltp");

		if (p.hasPermission("monstefix.highlander")) str.add("highlander");
		if (p.hasPermission("monsterfix.highlander.space-suit")) str.add("space suit");


		if (str.size()>0) {
			String strout = "";	
			for (int i=0; i<str.size(); i++){
				if (i==0) strout = str.get(i);
				else strout = strout+", " + str.get(i);
			}
			p.sendMessage(px+"Your active MonsteFix permissions: "+ChatColor.GREEN+strout);
		} else if (showempty) p.sendMessage(px+"You have no any active MonsteFix permissions."); 
	}

	protected int indexTrashBlock (Block b){
		if (trashcan.size()>0){
			for (int i = 0; i< trashcan.size();i++)
				if (trashcan.get(i).b.equals(b)) return i;
		}
		return -1;
	}



	protected boolean isIdInList (int id, String str){
		String [] ln = str.split(",");
		if (ln.length>0) {
			for (int i = 0; i<ln.length; i++)
				if (Integer.parseInt(ln[i])==id) return true;
		}		
		return false;
	}


	protected boolean isStrInList (String str, String list){
		String [] ln = list.split(",");
		if (ln.length>0) {
			for (int i = 0; i<ln.length; i++)
				if (ln[i].equalsIgnoreCase(str)) return true;
		}		
		return false;
	}

	//	protected boolean isTrashBlock(int id){
	//		return isIdInList (id, this.rmvblocks);
	//	}

	protected void AddToTrash (Block b){
		int i = indexTrashBlock(b); 
		if (i<0) trashcan.add(new MFTrashBlock (b));
		else trashcan.get(i).health=10;
	}

	protected void ClearTrash(){
		if (trashcan.size()>0)
			for (int i=trashcan.size()-1; i>=0;i--){
				RemoveTrash(trashcan.get(i).b);
				trashcan.remove(i);
			}
	}

	protected void DecayTrash(){
		if (trashcan.size()>0){
			for (int i=trashcan.size()-1; i>=0;i--){
				trashcan.get(i).health--;
				if (trashcan.get(i).health<=0) {
					RemoveTrash(trashcan.get(i).b);
					trashcan.remove(i);
				}
			}
		}
	}

	protected boolean RemoveTrash (Block b){
		int i = indexTrashBlock (b);
		if (i>=0) {
			if (checkTrash (b)){
				while (!b.getChunk().isLoaded()) b.getChunk().load();
				/* TODO проверить рефреш чанков, по идее должен пересчитываться свет.
				 * 
				 * if (b.getType()==Material.TORCH) {
					b.setTypeIdAndData(0, (byte) 0, false);	
					b.getWorld().refreshChunk(b.getChunk().getX(), b.getChunk().getZ()); // У чанков снова только ДВЕ координаты?! 1.2.3?

				} else */ 

				b.setTypeIdAndData(0, (byte) 0, false);
				return true;
			}
		}
		return false;
	}

	protected boolean checkTrash(Block b){
		for (int dx = -1; dx<=1; dx++)
			for (int dy = -1; dy<=1; dy++)
				for (int dz = -1; dz<=1; dz++) {
					if ((dx!=0)||(dy!=0)||(dz!=0))
						if (!isIdInList (b.getRelative(dx, dy, dz).getTypeId(), rmvnatural)) return false;
				}
		return true;
	}


	protected void Rst(){
		if (tid_save_b) Bukkit.getScheduler().cancelTask(tid_save);
		if (tid_freeze_b) Bukkit.getScheduler().cancelTask(tid_freeze);
		if (tid_mclear_b) Bukkit.getScheduler().cancelTask(tid_mclear);
		if (tid_pdmg_b) Bukkit.getScheduler().cancelTask(tid_pdmg);
		if (tid_trash_b) Bukkit.getScheduler().cancelTask(tid_trash);

		mobdmg.clear();
		mspmobs.clear();
		trashcan.clear();
		fl.fplayers.clear();
		//canceler.clear();
		butch.clear();
		snowtrails.clear();
		minute_tid = 0;


		tid_save_b = cfgB("saveall");
		tid_mclear_b = msdrop||fixmobfall||butchery||(fixsnowman&&smcrust);
		tid_pdmg_b = fixsneak||fixsprint||highlands;
		tid_trash_b = rmvtrash;
		tid_freeze_b = cncfreeze;

		if (tid_save_b) {
			tid_save = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
				public void run (){
					SaveTick();	
				}
				// 1 sec = 20;
				// 1 min = 1200
				// 1 hour = 72000
				// 24 hour = 1728000
			}, 1200L*cfgI("saveint"), 1200L*cfgI("saveint"));	
		}

		if (tid_mclear_b) {
			tid_mclear = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
				public void run (){
					minute_tid++;
					if (minute_tid>=6){
						TickMobClear();
						UnButch();						
						minute_tid =0;
					}
					TickSnowTrailsClear(); // раз в 10 сек. 
				}
			}, 200L, 200L);			
		}

		if (tid_pdmg_b) {
			tid_pdmg = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
				public void run (){
					TickPlayerDmg();	
				}
			}, 5L, 5L);
		}

		//удалялка мусора
		if (tid_trash_b) {
			tid_trash = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
				public void run (){
					DecayTrash();	
				}
			}, rmvtime*120L, rmvtime*120L);
		}
	}


	protected String deColorize (String str){
		return ChatColor.stripColor(str);
	}


	protected void DemoColor(Player p){
		String str = "";
		if (p.hasPermission("monsterfix.chat.color-basic")) str = "§0&0§1&1§2&2§3&3";
		if (p.hasPermission("monsterfix.chat.color-red")) str = str+"§4&4";
		if (p.hasPermission("monsterfix.chat.color-basic")) str = str+"§5&5§6&6§7&7§8&8§9&9§a&a§b&b";
		if (p.hasPermission("monsterfix.chat.color-red")) str = str+"§c&c";
		if (p.hasPermission("monsterfix.chat.color-red")) str = str+"§d&d§e&e§f&f";
		if (p.hasPermission("monsterfix.chat.hidden")) str = str+"§3&k§k&k§r";
		if (p.hasPermission("monsterfix.chat.font")) str = str+"§l&l§r§m&m§r§n&n§r";

		if (str.isEmpty()) p.sendMessage(px+ChatColor.RED+"You have not any MonsterFix-chat permissions!");
		else {
			str = str+" §r§8(&r - reset)";
			p.sendMessage(ChatColor.DARK_GREEN+"You can use in chat: ");
			p.sendMessage(str);
			p.sendMessage(ChatColor.DARK_GRAY+"&r - to reset any state.");
		}

	}


	protected String Colorize (Player p, String str){
		String rstr = str;
		if (p.hasPermission("monsterfix.chat.color-basic")) rstr = rstr.replaceAll("(&([a-bd-fr0-35-9]))", "\u00A7$2");
		if (p.hasPermission("monsterfix.chat.color-red")) {
			rstr = rstr.replaceAll("(&([cr4]))", "\u00A7$2");
			if (rstr.startsWith("!!!"))	rstr = rstr.replaceFirst("!!!", "§4");
			if (rstr.startsWith("!!"))	rstr = rstr.replaceFirst("!!", "§c");
		}
		if (p.hasPermission("monsterfix.chat.hidden")) {
			rstr = rstr.replaceAll("&k", "§k");
			rstr = rstr.replaceAll("&r", "§r");
		}
		if (p.hasPermission("monsterfix.chat.font")) rstr = rstr.replaceAll("(&([lmnor]))", "\u00A7$2");
		return rstr; 
	}

	protected int AddButchery(Location loc){
		Location bloc = loc.getBlock().getLocation();
		if (butch.size()>0)
			for (int i = 0; i <butch.size();i++)
				if ((butch.get(i).loc.getWorld().equals(loc.getWorld()))&&(butch.get(i).loc.distance(bloc)<=btchradius)) {
					if ((butch.get(i).count >= btchcount)&&(btchpenalty>=2)) {
						if (butch.get(i).count == btchcount) butch.get(i).count = butch.get(i).count * btchpenalty; 
						else butch.get(i).count = butch.get(i).count+btchpenalty;
					} else butch.get(i).count++;
					return butch.get(i).count;
				}
		butch.add(new MFButchery (loc));
		return 1;
	}



	protected boolean CheckPermBC (Player p, BlockCode b){
		boolean r = ((p.hasPermission("monstefix."+b.perm_global)||p.hasPermission("monstefix."+b.perm_global+"."+b.perm_local))||
				(p.hasPermission("sbc."+b.perm_global)||p.hasPermission("sbc."+b.perm_global+"."+b.perm_local))); 
		if (b.invert) r = !r;
		return r;
	}




	public void MelPumBreak (Block b) {
		if ((b.getType() == Material.MELON_STEM)||(b.getType() == Material.PUMPKIN_STEM)) {
			if (random.nextInt(100)>soilchance) 
				b.getRelative(0, -1, 0).setType(Material.DIRT);
		} else if ((b.getType()==Material.MELON_BLOCK)||(b.getType()==Material.PUMPKIN)) {
			Material stem = Material.MELON_STEM;
			if (b.getType() == Material.PUMPKIN) stem = Material.PUMPKIN_STEM;
			if ((b.getRelative(-1, 0, 0).getType() == stem)&&(b.getRelative(-1, 0, 0).getData()>=7)&&(random.nextInt(100)>soilchance)) {
				b.getRelative(-1, 0, 0).setType(Material.AIR);
				b.getRelative(-1, -1, 0).setType(Material.DIRT);
			}

			if ((b.getRelative(1, 0, 0).getType() == stem)&&(b.getRelative(1, 0, 0).getData()>=7)&&(random.nextInt(100)>soilchance)) {
				b.getRelative(1, 0, 0).setType(Material.AIR);
				b.getRelative(1, -1, 0).setType(Material.DIRT);
			}
			if ((b.getRelative(0, 0, -1).getType() == stem)&&(b.getRelative(0, 0, -1).getData()>=7)&&(random.nextInt(100)>soilchance)) {
				b.getRelative(0, 0, -1).setType(Material.AIR);
				b.getRelative(0, -1, -1).setType(Material.DIRT);
			}
			if ((b.getRelative(0, 0, 1).getType() == stem)&&(b.getRelative(0, 0, 1).getData()>=7)&&(random.nextInt(100)>soilchance)) {
				b.getRelative(0, 0, 1).setType(Material.AIR);
				b.getRelative(0, -1, 1).setType(Material.DIRT);
			}

		}
	}

	/*	public boolean Freeze (Player p){
		return Freeze (p,p.getLocation());
	} */




	public void ToggleFly (Player p){
		if (p.getGameMode() == GameMode.SURVIVAL) {
			p.setAllowFlight(!p.getAllowFlight());
			p.setFlying(p.getAllowFlight());
			p.sendMessage(px+"Fly-mode "+EnDis(p.getAllowFlight()));
		} else p.sendMessage(px+" You are in creative mode and you can fly :)");
	}

	public Biome BiomeByName (String bn){
		Biome [] bm = Biome.values();
		for (int i = 0; i<bm.length;i++){
			if ((bm[i].name().equalsIgnoreCase(bn))||(bm[i].name().replace("_", "")).equalsIgnoreCase(bn)) return bm[i];
		}

		return null;
	}

	public void InitBiomeList (){
		unsnowbiome.clear();
		String bl = cfgS("unsnowbiome");
		if (!bl.isEmpty()){
			String [] bln = bl.replace(" ", "").split(",");
			if (bln.length>0)
				for (int i = 0; i< bln.length;i++){
					Biome b = BiomeByName(bln[i]);
					if (b != null) unsnowbiome.add(b);
					else log.info("[mfix] Wrong biome name: "+bln[i]);
				}
		}
	}

	public String getPrjName(Projectile prj){
		String prjn = prj.getType().getName();
		if (prjn == null) prjn = prj.getType().toString();
		return prjn.toLowerCase();
	}

	public boolean PlaceBlock(Location loc, Player p, Material newType, byte newData, boolean phys){
		return PlaceBlock (loc.getBlock(),p,newType,newData, phys);
	}

	public boolean PlaceBlock(Block block, Player p, Material newType, byte newData, boolean phys){
		BlockState state = block.getState();
		block.setTypeIdAndData(newType.getId(), newData, phys);
		BlockPlaceEvent event = new BlockPlaceEvent(state.getBlock(), state, block, p.getItemInHand(), p, true);
		this.getServer().getPluginManager().callEvent(event);
		if (event.isCancelled()) state.update(true);
		return event.isCancelled();
	}


	protected String recodeText (String str){
		String nstr = str;
		if (!str.isEmpty()){
			for (int i = 0; i<cpwrong.length();i++)
				nstr = nstr.replace(cpwrong.charAt(i), cpright.charAt(i));
		}
		return nstr;
	}
	
	/*protected void updateMaps(){
		CraftServer server = (CraftServer) Bukkit.getServer(); 
		
						
	}*/


	public void BC (String str) {
		Bukkit.broadcastMessage(px+str);
	}




}