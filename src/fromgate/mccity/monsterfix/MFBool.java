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

public class MFBool {
	String name;
	String grp="n/a";
	boolean v=false;
	String node ="";
	String txt ="";

	public MFBool (String name, String grp, boolean v, String node, String descr) {
		this.name=name;
		this.grp = grp;
		this.v=v;
		this.node=node;
		this.txt = descr;
	}
	
	
}
