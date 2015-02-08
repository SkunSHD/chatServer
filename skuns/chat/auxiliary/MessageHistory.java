package skuns.chat.auxiliary;

import java.util.ArrayList;
import skuns.chat.auxiliary.Message;
import skuns.chat.Config;

// add serialization, for what?

public class MessageHistory {
	private ArrayList<Message> history = new ArrayList<Message>(Config.MAX_MESSAGE);

	public void addMessage(Message mess) {
		if(history.size() >= Config.MAX_MESSAGE) {
			history.remove(0);
		}
		history.add(mess);
	}

	public ArrayList<Message> getHistory() {
		return history;
	}

}