package skuns.chat.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.SocketException;
import java.io.IOException;
import skuns.chat.Config;
import skuns.chat.client.UserList;
import skuns.chat.server.ClientThread;
import skuns.chat.auxiliary.Message;


public class Server {

	private static UserList list = new UserList();

	public Server() {
		try {
			ServerSocket socketListener = new ServerSocket(Config.PORT);
			System.out.println("Server is online and waiting for clients on port " + Config.PORT);

				while(true) {
					Socket client = null;
					while(client == null) {
						client = socketListener.accept();
					}
					new ClientThread(client);
				}

		} catch(SocketException e) {
				System.err.println("Socket exception");
				e.printStackTrace();
		} catch (IOException e) {
				System.err.println("I/O exception");
				e.printStackTrace();
		}
	}



	public synchronized static UserList getUserList() {
		return list;
	}
}