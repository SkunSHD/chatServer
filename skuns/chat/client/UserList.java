package skuns.chat.client;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import skuns.chat.client.Client;

public class UserList {

	private Map<String, Client> onlineUsers = new HashMap<String, Client>();
	private Map<String, String> history = new HashMap<String, String>();

	public void addUser (String login, Client client) {
		int i = 1;
		while(onlineUsers.containsKey(login)) {
			login = login + i;
			i++;
		}

		this.onlineUsers.put(login, client);
		System.out.println(login + " connected");
	}

	public void deleteUser(String user) {
		onlineUsers.remove(user);
	}


	public ArrayList<Client> getClientList () {
		ArrayList<Client> clientList = new ArrayList<Client>();
		for (Map.Entry<String, Client> m : this.onlineUsers.entrySet()) {
			clientList.add(m.getValue());
		}
		return clientList;
	}

	public int size() {
		return onlineUsers.size();
	}

	// public String[] getUsersInString () {
	// 	return onlineUsers.entrySet().toArray(new String[0]);
	// }
}