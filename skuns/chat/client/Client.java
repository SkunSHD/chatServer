package skuns.chat.client;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import skuns.chat.auxiliary.Message;
import skuns.chat.server.Server;

public class Client {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Client(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
		this.socket = socket;
		this.out = out;
		this.in = in;

		ArrayList<Message> historyMesssage = Server.getHistoryClass().getHistory();
		if(historyMesssage.size() != 0) {
			try {
				for(Message m : historyMesssage) {
					out.writeObject(m);
				}
			} catch (IOException ex) {
				System.err.println("Extraction history message error");
				ex.printStackTrace();
			}

		}
	}

	public Socket getSocket() {
		return this.socket;
	}

	public ObjectInputStream getThisObjectInputStream() {
		return this.in;
	}

	public ObjectOutputStream getThisObjectOutputStream() {
		return this.out;
	}
}