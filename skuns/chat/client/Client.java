package skuns.chat.client;

import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Client {

	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;

	public Client(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
		this.socket = socket;
		this.out = out;
		this.in = in;
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