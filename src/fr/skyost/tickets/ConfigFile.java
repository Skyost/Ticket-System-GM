package fr.skyost.tickets;

import java.io.File;
import java.util.HashMap;

import org.bukkit.plugin.Plugin;

import fr.skyost.tickets.utils.Config;

public class ConfigFile extends Config {
	
    public HashMap<String, String> Data = new HashMap<String, String>();
    
    public boolean EnableUpdater = true;
   
    public int ReloadDelay = 300;
    public int TicketDelay = 300;
	
    public ConfigFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "config.yml");
		CONFIG_HEADER = "##################################################### #";
		CONFIG_HEADER += "\n           Ticket System GM Configuration             #";
		CONFIG_HEADER += "\n             See http://url.skyost.eu/w               #";
		CONFIG_HEADER += "\n              for more informations.                  #";
		CONFIG_HEADER += "\n##################################################### #";
    }
	
}
