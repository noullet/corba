package server;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import generated.VworldFactory;
import interfaces.Message;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;
import utils.ServerUtils;

import dao.MessageDao;

@WebService
public class MyService {

	public String getMessages(String login, String password) {
		Connection connexion;
		try {
			connexion = ServerUtils.getDbConnection();
			VworldFactory db = new VworldFactory(connexion);
			List<Message> messages = MessageDao.wsGetMessagesFromCredentials(
					login, password, db);
			StringBuffer buffer = new StringBuffer();
			if (messages.size() > 0) {
				for (Message message : messages) {
					Date date = new Date(message.timestamp);
					buffer.append("From " + message.sender + " ["
							+ getDateString(date) + "] : " + message.content
							+ "\n");
				}
			} else {
				buffer.append("Pas de nouveau messages");
			}
			return buffer.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Erreur de connexion";
	}

	private String getDateString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"dd-MMM-yyyy HH:mm:ss");
		return formatter.format(date);
	}

	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/PizzaChat", new MyService());
		System.out.println("Web Service running...");
	}
}
