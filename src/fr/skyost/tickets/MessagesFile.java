package fr.skyost.tickets;

import java.io.File;

import org.bukkit.plugin.Plugin;

import fr.skyost.tickets.utils.Config;

public class MessagesFile extends Config {
    
    public String Messages_1 = "§2Your ticket has been sent !";
    public String Messages_2 = "§1§l/player/ has just sent a ticket :%n§r§1'/message/'%nHis position is X : '/x/' Y : '/y/' Z : '/z/'.";
    public String Messages_3 = "§6You have /n/ ticket(s).";
    public String Messages_4 = "The above list is scrollable.";
    public String Messages_5 = "§cThis id was not found.";
    public String Messages_6 = "§2Done with success !";
    public String Messages_7 = "§cYou must wait /n/ second(s) to send a new ticket.";
	
    public MessagesFile(Plugin plugin) {
		CONFIG_FILE = new File(plugin.getDataFolder(), "lang.yml");
		CONFIG_HEADER = "##################################################### #";
		CONFIG_HEADER += "\n           Ticket System GM Configuration             #";
		CONFIG_HEADER += "\n             See http://url.skyost.eu/w               #";
		CONFIG_HEADER += "\n              for more informations.                  #";
		CONFIG_HEADER += "\n##################################################### #";
    }
	
}
