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
		String password;

		try {

			final ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			final ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

			// Taking user login
			while(true) {
				login = in.readUTF();
				if(login != null) {
					break;
				}
			}
			// Taking user password
			while(true) {
				password = in.readUTF();
				if(password != null) {
					break;
				}
			}

			// User authentication
			if(Server.getUserList().authentication(login, password)) {
				client = new Client(socket, out, in);
			} else {
				// stop client
				Message nonAuthorize = new Message("Server", "error authorization", "service", "127.0.0.1");
				out.writeObject(nonAuthorize);
				out.close();
				in.close();
				socket.close();
				return;
			}

			// Adding new user to UserList
			Server.getUserList().addUser(login, client);



			// Resending user message
			while(true) {
				if( (message = (Message) in.readObject()) != null ) {
					if(message.getMessage().equalsIgnoreCase("quit")) {
						// Users notification about disconnected user
						Message infoMessage1 = new Message("Server", login+" disconected", "service", socket.getInetAddress().getHostAddress());
						this.broadcast(Server.getUserList().getClientList(), infoMessage1);
						// Sending to client stop-socket message
						Message stopClientMessage = new Message("Server", "close", "Info mess", socket.getInetAddress().getHostAddress());
						out.writeObject(stopClientMessage);

						Server.getUserList().deleteUser(login);
						System.out.println(login + " disconected");
						out.close();
						in.close();
						socket.close();
						break;
					} else {
						this.broadcast(Server.getUserList().getClientList(), message);
						Server.getHistoryClass().addMessage(message);
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