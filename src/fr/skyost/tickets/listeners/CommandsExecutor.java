package fr.skyost.tickets.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.skyost.tickets.TicketSystemGM;

public class CommandsExecutor implements CommandExecutor {
    
    @Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
    	Player player = null;
    	if(sender instanceof Player) {
    		player = (Player)sender;
    	}
		if(args.length >= 1) {
	    	if(cmd.getName().equalsIgnoreCase("ticket")) {
		    	if(player != null) {
		    		final String senderName = sender.getName();
		    		if(TicketSystemGM.forbiddenPlayers.contains(senderName)) {
		    			player.sendMessage(TicketSystemGM.messages.Messages_7.replaceAll("/n/", String.valueOf(TicketSystemGM.config.TicketDelay)));
		    			return true;
		    		}
		    		String ticket;
		    		final StringBuilder stringBuilder = new StringBuilder();
		    		for(String arg : args) {
		    			stringBuilder.append(arg);
		    			stringBuilder.append(" ");
		    		}
		    		ticket = stringBuilder.toString();
		    		TicketSystemGM.forbiddenPlayers.add(senderName);
		    		TicketSystemGM.sendTicket(player, ticket.substring(0, ticket.length() - 1));
		    		player.sendMessage(TicketSystemGM.messages.Messages_1);
		    		Bukkit.getScheduler().scheduleSyncDelayedTask(TicketSystemGM.plugin, new Runnable() {

						@Override
						public void run() {
							TicketSystemGM.forbiddenPlayers.remove(senderName);
						}
		    		
		    		}, TicketSystemGM.config.TicketDelay * 20L);
		    	}
		    	else {
		    		sender.sendMessage(ChatColor.RED + "Please do this from the game !");
		    	}
	    	}
	    	else if(cmd.getName().equalsIgnoreCase("viewticket")) {
	    		if(args[0].equalsIgnoreCase("all")) {
	    			for(String ticket : TicketSystemGM.data.values()) {
	    				sender.sendMessage(ticket);
	    				sender.sendMessage("---");
	    			}
	    			sender.sendMessage(TicketSystemGM.messages.Messages_4);
	    		}
	    		else {
	    			if(TicketSystemGM.data.get(args[0]) != null) {
	    				sender.sendMessage(TicketSystemGM.data.get(args[0]));
	    			}
	    			else {
	    				sender.sendMessage(TicketSystemGM.messages.Messages_5);
	    			}
	    		}
	    	}
	    	else {
	    		if(args[0].equalsIgnoreCase("all")) {
	    			TicketSystemGM.data.clear();
	    			sender.sendMessage(TicketSystemGM.messages.Messages_6);
	    		}
	    		else {
	    			if(TicketSystemGM.data.get(args[0]) != null) {
	    				TicketSystemGM.data.remove(args[0]);
	    				sender.sendMessage(TicketSystemGM.messages.Messages_6);
	    			}
	    			else {
	    				sender.sendMessage(TicketSystemGM.messages.Messages_5);
	    			}
	    		}
	    	}
		}
		else {
			return false;
		}
		return true;
    }

}
