package skuns.chat.server;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.lang.Thread;
import java.util.ArrayList;
import skuns.chat.auxiliary.Message;
import skuns.chat.client.Client;

public class ClientThread extends Thread {

	private Client client;
	private Socket socket;
	private Message message;

	public ClientThread(Socket socket) {
		this.socket = socket;
		this.start();
	}

	@Override
	public void run() {
		String login;
		try {

			final ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			final ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			// Забираем логин нового пользователя
			while(true) {
				login = in.readUTF();
				if(login != null) {
					break;
				}
			}

			client = new Client(socket, out, in);

			// Добавляем новичка в список пользователей
			Server.getUserList().addUser(login, client);

			// Передаем всем сообщение пользователя
			while(true) {

				if( (message = (Message) in.readObject()) != null ) {
					if(message.getMessage().equalsIgnoreCase("quit")) {

						// Уведомляем пользователей об отключившемся чуваке
						Message infoMessage1 = new Message("Server", login+" disconected", "service", socket.getInetAddress().getHostAddress());
						this.broadcast(Server.getUserList().getClientList(), infoMessage1);
						// Отсылаем команду клиенту для его остановки
						Message infoMessage2 = new Message("Server", "close", "Info mess", socket.getInetAddress().getHostAddress());
						out.writeObject(infoMessage2);

						Server.getUserList().deleteUser(login);
						System.out.println(login + " disconected");
						out.close();
						in.close();
						socket.close();
						break;
					} else {
						this.broadcast(Server.getUserList().getClientList(), message);
					}
				}
			}


		} catch (SocketException e) {
			System.err.println("ServerSocket error");
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			System.err.println("ClientThread class not found");
			e.printStackTrace();

		} catch (IOException e) {
			System.err.println("I/O error");
			e.printStackTrace();

		}

	}

	public void broadcast (ArrayList<Client> list, Message message) {
		try {
			for (Client cl : list) {
				if(cl != this.client) {
					cl.getThisObjectOutputStream().writeObject(message);
				}
			}
		} catch (IOException e) {
			System.err.println("Broadcast method error");
			e.printStackTrace();
		}
	}
}