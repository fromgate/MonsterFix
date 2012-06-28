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

public class BlockCode {
	String code;
	String perm_global;
	String perm_local;
	boolean invert;
	
	BlockCode (String code, String glb, String lcl, boolean inv){
		this.code = code;
		this.perm_global = glb;
		this.perm_local = lcl;
		this.invert = inv;
	}
}
