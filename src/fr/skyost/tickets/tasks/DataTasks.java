package fr.skyost.tickets.tasks;

import org.bukkit.Bukkit;

import fr.skyost.tickets.TicketSystemGM;

public class DataTasks implements Runnable {

	@Override
	public void run() {
		try {
			TicketSystemGM.reload(Bukkit.getConsoleSender());
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
