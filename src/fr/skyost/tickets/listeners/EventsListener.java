package fr.skyost.tickets.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.skyost.tickets.TicketSystemGM;

public class EventsListener implements Listener {
	
	@EventHandler
	private final void onPlayerJoin(final PlayerJoinEvent event) {
		final Player player = event.getPlayer();
		if(player.hasPermission("ticket.receive")) {
			player.sendMessage(TicketSystemGM.messages.Messages_3.replaceAll("/n/", String.valueOf(TicketSystemGM.data.size())));
		}
	}

}
