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

import java.net.URL;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class MFUtil {
	MonsterFix plg;
	
	private String version_check_url = "http://dev.bukkit.org/server-mods/monsterfix/files.rss";
	private double version_current=0;
	private double version_new=0;
	private String version_new_str="unknown";
	private String version_info_perm = "monsterfix.config";
	private Logger log = Logger.getLogger("Minecraft");
	
	public MFUtil(MonsterFix plg){
		this.plg = plg;
		this.version_current = Double.parseDouble(plg.des.getVersion().replaceFirst("\\.", "").replace("/", ""));
	}
	
	public void UpdateMsg (Player p){
		if (p.hasPermission(this.version_info_perm)){
			version_new = getNewVersion (version_current);
			if (version_new>version_current){
				p.sendMessage(ChatColor.GOLD+plg.des.getName()+" v"+plg.des.getVersion()+ChatColor.YELLOW+" is outdated! Recommended version is "+ChatColor.GOLD+"v"+version_new_str);
				p.sendMessage(ChatColor.DARK_AQUA+version_check_url.replace("files.rss", ""));
			}
		}
	}
	
	public void UpdateMsg (){
		version_new = getNewVersion (version_current);
		if (version_new>version_current){
			log.info("[mfix] "+plg.des.getName()+" v"+plg.des.getVersion()+" is outdated! Recommended version is v"+version_new_str);
			log.info("[mfix] "+version_check_url.replace("files.rss", ""));
		}
	}
	
	private double getNewVersion(double currentVersion) 
	{
		try {
			URL url = new URL(version_check_url);
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(url.openConnection().getInputStream());
			doc.getDocumentElement().normalize();
			NodeList nodes = doc.getElementsByTagName("item");
			Node firstNode = nodes.item(0);
			if (firstNode.getNodeType() == 1) {
				Element firstElement = (Element)firstNode;
				NodeList firstElementTagName = firstElement.getElementsByTagName("title");
				Element firstNameElement = (Element)firstElementTagName.item(0);
				NodeList firstNodes = firstNameElement.getChildNodes();
				version_new_str = firstNodes.item(0).getNodeValue().replace("MonsterFix v", "").trim();
				return Double.parseDouble(version_new_str.replaceFirst("\\.", "").replace("/", ""));
			}
		}
		catch (Exception e) {
		}
		return currentVersion;
	}
	
	public void PrintMsg(Player p, String msg){
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
	}
	
	
	


}
