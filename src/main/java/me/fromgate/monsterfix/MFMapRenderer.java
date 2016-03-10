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

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.map.MinecraftFont;

public class MFMapRenderer extends MapRenderer {
	MonsterFix plg;
	
	public MFMapRenderer(MonsterFix plg){
		this.plg = plg;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void render(MapView map, MapCanvas canvas, Player p) {
		if (plg.mapshowid){
			byte black = MapPalette.matchColor(0, 0, 0);
			byte white = MapPalette.matchColor(255, 255, 255);
			canvas.drawText(4, 4, MinecraftFont.Font, "§"+Byte.toString(black)+";"+ Short.toString(map.getId()));
			canvas.drawText(4, 5, MinecraftFont.Font, "§"+Byte.toString(black)+";"+ Short.toString(map.getId()));
			canvas.drawText(5, 4, MinecraftFont.Font, "§"+Byte.toString(black)+";"+ Short.toString(map.getId()));
			canvas.drawText(4, 6, MinecraftFont.Font, "§"+Byte.toString(black)+";"+ Short.toString(map.getId()));
			canvas.drawText(6, 4, MinecraftFont.Font, "§"+Byte.toString(black)+";"+ Short.toString(map.getId()));
			canvas.drawText(6, 6, MinecraftFont.Font, "§"+Byte.toString(black)+";"+ Short.toString(map.getId()));
			canvas.drawText(5, 5, MinecraftFont.Font, "§"+Byte.toString(white)+";"+ Short.toString(map.getId()));
		}
	}

}
