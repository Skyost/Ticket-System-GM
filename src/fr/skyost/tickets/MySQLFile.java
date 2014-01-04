package fr.skyost.tickets;

import java.io.File;

import org.bukkit.plugin.Plugin;

import fr.skyost.tickets.utils.Config;

public class MySQLFile extends Config {
	
    public boolean MySQL_Use = false;
    public String MySQL_Username = "Skyost";
    public String MySQL_Password = "MyGoodPassword";
    public String MySQL_Host = "localhost";
    public String MySQL_Database = "Database";
    public int MySQL_Port = 3306;
	
    public MySQLFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "mysql.yml");
		CONFIG_HEADER = "##################################################### #";
		CONFIG_HEADER += "\n           Ticket System GM Configuration             #";
		CONFIG_HEADER += "\n             See http://url.skyost.eu/w               #";
		CONFIG_HEADER += "\n              for more informations.                  #";
		CONFIG_HEADER += "\n##################################################### #";
    }

}
