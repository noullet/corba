package dao;

import generated.tables.records.MessageRecord;
import generated.tables.records.UserRecord;
import interfaces.Message;
import interfaces.MessageType;

import java.util.ArrayList;
import java.util.List;

public class MessageDao {

	public static List<Message> getAllMessagesForUser() {
		return new ArrayList<Message>();
	}

	public static void deleteAllMessagesForUser() {

	}

	private static Message getMessageFromMessageRecord(MessageRecord messageRecord) {
		UserRecord sender = messageRecord.fetchUserBySender();
		UserRecord receiver = messageRecord.fetchUserByReceiver();
		return new Message(sender.getLogin(), receiver.getLogin(), messageRecord.getContent(), MessageType.SINGLECAST);
	}

	public static void saveMessage(Message message) {

	}

}
