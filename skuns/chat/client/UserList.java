package skuns.chat.client;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import skuns.chat.client.Client;

public class UserList {

	Map<String, Client> onlineUsers = new HashMap<String, Client>();

	public void addUser (String login, Client client) {

		this.onlineUsers.put(login, client);
		System.out.println(login + " connected");
	}

	public void deleteUser(String user) {
		onlineUsers.remove(user);
	}

	public String[] getUsersInString () {
		return onlineUsers.entrySet().toArray(new String[0]);
	}

	public ArrayList<Client> getClientList () {
		ArrayList<Client> clientList = new ArrayList<Client>();
		for (Map.Entry<String, Client> m : this.onlineUsers.entrySet()) {
			clientList.add(m.getValue());
		}
		return clientList;
	}
}