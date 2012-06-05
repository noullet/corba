package client;

import interfaces.Message;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceRef;

import utils.ClientUtils;

public class Client {
	Service service;

	public void test() {
		try { // Call Web Service Operation
			URL wsdlLocation = new URL("http://localhost:8080/PizzaChat?WSDL");
			QName serviceQName = new QName("http://server/", "MyServiceService");
			service = Service.create(wsdlLocation, serviceQName);
			MyService myService = service.getPort(MyService.class);
			System.out.println("Veuillez entrer votre login : ");
			Scanner sc = new Scanner(System.in);
			String login = sc.nextLine();
			System.out.println("Veuillez entrer votre mot de passe : ");
			String password = sc.nextLine();
			String message = myService.getMessages(login, password);
			System.out.println(message);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// TODO code application logic here
	}

	public static void main(String[] args) {
		Client client = new Client();
		client.test();
	}
}