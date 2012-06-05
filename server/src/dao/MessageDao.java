package dao;

import static generated.tables.Message.MESSAGE;
import static server.Server.getDb;
import generated.tables.records.MessageRecord;
import generated.tables.records.UserRecord;
import interfaces.Message;
import interfaces.MessageType;
import interfaces.User;

import java.sql.Date;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class MessageDao {

	public static List<Message> getMessagesFromUser(User user) {
		int receiverId = UserDao.getIdFromLogin(user.login);
		List<MessageRecord> messageRecords = getDb().selectFrom(MESSAGE).where(MESSAGE.RECEIVER.equal(receiverId))
				.fetch();
		return getMessagesFromRecords(messageRecords);
	}

	public static void deleteMessagesFromUser(User user) {
		int receiverId = UserDao.getIdFromLogin(user.login);
		getDb().delete(MESSAGE).where(MESSAGE.RECEIVER.equal(receiverId)).execute();
	}

	public static Message getMessageFromRecord(MessageRecord messageRecord) {
		UserRecord sender = messageRecord.fetchUserBySender();
		UserRecord receiver = messageRecord.fetchUserByReceiver();
		long timestamp = messageRecord.getDate().getTime();
		return new Message(sender.getLogin(), receiver.getLogin(), messageRecord.getContent(), timestamp,
				MessageType.SINGLECAST);
	}

	public static List<Message> getMessagesFromRecords(List<MessageRecord> messageRecords) {
		return Lists.transform(messageRecords, new Function<MessageRecord, Message>() {
			public Message apply(MessageRecord messageRecord) {
				return getMessageFromRecord(messageRecord);
			}
		});
	}

	public static void saveMessage(Message message) {
		int senderId = UserDao.getIdFromLogin(message.sender);
		int receiverId = UserDao.getIdFromLogin(message.receiver);
		getDb().insertInto(MESSAGE, MESSAGE.CONTENT, MESSAGE.SENDER, MESSAGE.RECEIVER, MESSAGE.DATE)
				.values(message.content, senderId, receiverId, new Date(message.timestamp)).execute();
	}
}
