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

public class MFFloat {
	String name;
	String grp="n/a";
	float v=-0.1f;
	String node ="";
	String txt ="";
	

	public MFFloat (String name, String grp, float v, String node, String descr) {
		this.name=name;
		this.grp = grp;
		this.v=v;
		this.node=node;
		this.txt = descr;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grp == null) ? 0 : grp.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof MFFloat))
			return false;
		MFFloat other = (MFFloat) obj;
		if (grp == null) {
			if (other.grp != null)
				return false;
		} else if (!grp.equals(other.grp))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
