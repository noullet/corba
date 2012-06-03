package dao;

import static generated.tables.Message.MESSAGE;
import static server.Server.getDb;
import generated.tables.records.MessageRecord;
import generated.tables.records.UserRecord;
import interfaces.Message;
import interfaces.MessageType;
import interfaces.User;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class MessageDao {

	public static List<Message> getAllMessagesForUser(User user) {
		int receiverId = UserDao.getIdFromUser(user);
		List<MessageRecord> messageRecords = getDb().selectFrom(MESSAGE).where(MESSAGE.RECEIVER.equal(receiverId))
				.fetch();
		return Lists.transform(messageRecords, new Function<MessageRecord, Message>() {
			public Message apply(MessageRecord messageRecord) {
				return getMessageFromMessageRecord(messageRecord);
			}
		});
	}

	public static void deleteAllMessagesForUser(User user) {
		int receiverId = UserDao.getIdFromUser(user);
		getDb().delete(MESSAGE).where(MESSAGE.RECEIVER.equal(receiverId)).execute();
	}

	private static Message getMessageFromMessageRecord(MessageRecord messageRecord) {
		UserRecord sender = messageRecord.fetchUserBySender();
		UserRecord receiver = messageRecord.fetchUserByReceiver();
		return new Message(sender.getLogin(), receiver.getLogin(), messageRecord.getContent(), MessageType.SINGLECAST);
	}

	public static void saveMessage(Message message) {
		int senderId = UserDao.getIdFromLogin(message.sender);
		int receiverId = UserDao.getIdFromLogin(message.receiver);
		getDb().insertInto(MESSAGE, MESSAGE.CONTENT, MESSAGE.SENDER, MESSAGE.RECEIVER)
				.values(message.content, senderId, receiverId).execute();
	}
}
