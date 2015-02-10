package skuns.chat.client;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import skuns.chat.client.Client;
import skuns.chat.Config;

public class UserList {

	private Map<String, Client> onlineUsers = new HashMap<String, Client>();
	private Map<String, String> authorize = new HashMap<String, String>();

	static {

		File file = new File("C:/Java/git/bin/skuns/chat/users.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();

		NodeList nList = doc.getElementByTagName("user");
		for(int i=0; i<nList.getLength(); i++) {
			Node nNode = nList.item(i);
			if(nList.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;

				String login = eElement.getElementByTagName("login").item(0).getTextContent();
				String pass = eElement.getElementByTagName("pass").item(0).getTextContent();
				authorize.put(login, pass);
			}
		}

	}

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

	public void authorization() {
		for(Map.Entry<String, String> node : authorize.entrySet()) {
			System.out.printf("login: %s pass: %s", node.getKey(), node.getValue());
		}
	}
}