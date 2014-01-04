package fr.skyost.tickets;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.skyost.tickets.listeners.CommandsExecutor;
import fr.skyost.tickets.listeners.EventsListener;
import fr.skyost.tickets.tasks.DataTasks;
import fr.skyost.tickets.utils.Metrics;
import fr.skyost.tickets.utils.Skyupdater;
import fr.skyost.tickets.utils.Utils;

public class TicketSystemGM extends JavaPlugin {
    
    public static final HashMap<String, String> data = new HashMap<String, String>();
    public static final ArrayList<String> forbiddenPlayers = new ArrayList<String>();
    
    private static Statement stat;
    
    public static ConfigFile config;
    public static MessagesFile messages;
    
    public static Plugin plugin;
    
    @Override
    public final void onEnable() {
		try {
			plugin = this;
		    config = new ConfigFile(this);
		    config.init();
		    messages = new MessagesFile(this);
		    messages.init();
		    MySQLFile mysql = new MySQLFile(this);
		    mysql.init();
		    new Metrics(this).start();
		    if(config.EnableUpdater) {
				new Skyupdater(this, 57987, this.getFile(), true, true);
			}
		    if(mysql.MySQL_Use) {
		    	stat = DriverManager.getConnection("jdbc:mysql://" + mysql.MySQL_Host + ":" + mysql.MySQL_Port + "/" + mysql.MySQL_Database, mysql.MySQL_Username, mysql.MySQL_Password).createStatement();
		    	stat.executeUpdate("CREATE TABLE IF NOT EXISTS TGM_Data(Id TINYTEXT, Ticket TINYTEXT)");
		    }
		    if(config.ReloadDelay <= 0) {
		    	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[Ticket System GM] The plugin will not reload !");
		    }
		    else {
		    	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new DataTasks(), 0, config.ReloadDelay * 20L);
		    }
		    CommandExecutor executor = new CommandsExecutor();
		    PluginCommand command = this.getCommand("ticket");
		    command.setUsage(ChatColor.RED + "Use only /ticket <ticket here>.");
		    command.setExecutor(executor);
		    command = this.getCommand("viewticket");
		    command.setUsage(ChatColor.RED + "Use only /viewticket <id> (or /viewticket all).");
		    command.setExecutor(executor);
		    command = this.getCommand("removeticket");
		    command.setUsage(ChatColor.RED + "Use only /removeticket <id> (or /removeticket all).");
		    command.setExecutor(executor);
		    Bukkit.getPluginManager().registerEvents(new EventsListener(), this);
		}
		catch(Exception ex) {
		    ex.printStackTrace();
		}
    }
    
    @Override
    public final void onDisable() {
    	try {
    		reload(Bukkit.getConsoleSender());
    	}
    	catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static void sendTicket(final Player player, final String message) {
    	final Location loc = player.getLocation();
    	String opMessage = messages.Messages_2.replaceAll("/player/", player.getName());
    	opMessage = opMessage.replaceAll("/message/", message);
    	opMessage = opMessage.replaceAll("/x/", String.valueOf(loc.getBlockX()));
    	opMessage = opMessage.replaceAll("/y/", String.valueOf(loc.getBlockY()));
    	opMessage = opMessage.replaceAll("/z/", String.valueOf(loc.getBlockZ()));
    	opMessage = opMessage.replaceAll("%n", "\n");
    	Bukkit.broadcast(opMessage, "ticket.receive");
    	data.put(String.valueOf(data.size() + 1), "[" + Utils.date() + "] [" + player.getName() + "] [X : " + loc.getBlockX() + " Y : " + loc.getBlockY() + " Z : " + loc.getBlockZ() + "]\n" + message);
    }
    
    public static void reload(final CommandSender receiver) throws Exception {
    	receiver.sendMessage(ChatColor.GOLD + "[Ticket System GM] Reloading now...");
    	if(stat == null) {
    		data.putAll(config.Data);
    		config.Data = data;
			config.save();
		}
		else {
			final ResultSet rs = stat.executeQuery("SELECT Id, Ticket FROM TGM_Data");
			String id;
			while(rs.next()) {
				id = rs.getString("Id");
				if(data.get(id) == null) {
					data.put(id, rs.getString("Ticket"));
				}
			}
			stat.executeUpdate("TRUNCATE TABLE TGM_Data");
			for(Entry<String, String> entry : data.entrySet()) {
				stat.executeUpdate("INSERT INTO TGM_Data(Id, Ticket) VALUES('" + entry.getKey() + "', '" + entry.getValue() + "')");
			}
		}
    	receiver.sendMessage(ChatColor.GREEN + "[Ticket System GM] Done !");
	}

}
