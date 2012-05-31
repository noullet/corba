/*
 * Created by JFormDesigner on Tue May 29 18:23:36 CEST 2012
 */

package client;

import interfaces.UserMood;
import interfaces.UserSex;
import interfaces.UserSize;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * @author Bertrand Pages
 */
public class MainFrame extends JFrame {
	public MainFrame() {
		initComponents();
	}

	private void envoyerActionPerformed(ActionEvent e) {
		this.sendMessage();
		
	}

	private void moodActionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.contentMenu)){
			ClientUtils.getUserManager().changeMood(UserMood.CONTENT);
		} else if(e.getSource().equals(this.inquietMenu)){
			ClientUtils.getUserManager().changeMood(UserMood.INQUIET);
		} else if(e.getSource().equals(this.effrayeMenu)){
			ClientUtils.getUserManager().changeMood(UserMood.EFFRAYE);
		} else if(e.getSource().equals(this.hilareMenu)){
			ClientUtils.getUserManager().changeMood(UserMood.HILARE);
		} else if(e.getSource().equals(this.tristeMenu)){
			ClientUtils.getUserManager().changeMood(UserMood.TRISTE);
		}
	}

	private void tailleActionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.geantMenu)){
			ClientUtils.getUserManager().changeSize(UserSize.GEANT);
		} else if(e.getSource().equals(this.grandMenu)){
			ClientUtils.getUserManager().changeSize(UserSize.GRAND);
		} else if(e.getSource().equals(this.moyenMenu)){
			ClientUtils.getUserManager().changeSize(UserSize.MOYEN);
		} else if(e.getSource().equals(this.petitMenu)){
			ClientUtils.getUserManager().changeSize(UserSize.PETIT);
		} else if(e.getSource().equals(this.nainMenu)){
			ClientUtils.getUserManager().changeSize(UserSize.NAIN);
		}
	}

	private void sexeActionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.hommeMenu)){
			ClientUtils.getUserManager().changeSex(UserSex.MALE);
		} else if(e.getSource().equals(this.femmeMenu)){
			ClientUtils.getUserManager().changeSex(UserSex.FEMALE);
		}
	}

	private void messageToSendKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_ENTER) {
        	this.sendMessage();
        }
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Bertrand Pages
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menu2 = new JMenu();
		menu3 = new JMenu();
		contentMenu = new JMenuItem();
		tristeMenu = new JMenuItem();
		effrayeMenu = new JMenuItem();
		inquietMenu = new JMenuItem();
		hilareMenu = new JMenuItem();
		menu4 = new JMenu();
		geantMenu = new JMenuItem();
		grandMenu = new JMenuItem();
		moyenMenu = new JMenuItem();
		petitMenu = new JMenuItem();
		nainMenu = new JMenuItem();
		menu5 = new JMenu();
		hommeMenu = new JMenuItem();
		femmeMenu = new JMenuItem();
		splitPane1 = new JSplitPane();
		scrollPane1 = new JScrollPane();
		messageToSend = new JTextArea();
		button1 = new JButton();
		splitPane2 = new JSplitPane();
		scrollPane2 = new JScrollPane();
		chatArea = new JTextArea();
		scrollPane3 = new JScrollPane();
		list1 = new JList();

		//======== this ========
		setTitle("PizzaChat");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");
			}
			menuBar1.add(menu1);

			//======== menu2 ========
			{
				menu2.setText("Room");
			}
			menuBar1.add(menu2);

			//======== menu3 ========
			{
				menu3.setText("Humeur");

				//---- contentMenu ----
				contentMenu.setText("Content");
				contentMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						moodActionPerformed(e);
					}
				});
				menu3.add(contentMenu);

				//---- tristeMenu ----
				tristeMenu.setText("Triste");
				tristeMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						moodActionPerformed(e);
					}
				});
				menu3.add(tristeMenu);

				//---- effrayeMenu ----
				effrayeMenu.setText("Effray\u00e9");
				effrayeMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						moodActionPerformed(e);
					}
				});
				menu3.add(effrayeMenu);

				//---- inquietMenu ----
				inquietMenu.setText("Inquiet");
				inquietMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						moodActionPerformed(e);
					}
				});
				menu3.add(inquietMenu);

				//---- hilareMenu ----
				hilareMenu.setText("Hilare");
				hilareMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						moodActionPerformed(e);
					}
				});
				menu3.add(hilareMenu);
			}
			menuBar1.add(menu3);

			//======== menu4 ========
			{
				menu4.setText("Taille");

				//---- geantMenu ----
				geantMenu.setText("G\u00e9ant");
				geantMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tailleActionPerformed(e);
					}
				});
				menu4.add(geantMenu);

				//---- grandMenu ----
				grandMenu.setText("Grand");
				grandMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tailleActionPerformed(e);
					}
				});
				menu4.add(grandMenu);

				//---- moyenMenu ----
				moyenMenu.setText("Moyen");
				moyenMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tailleActionPerformed(e);
					}
				});
				menu4.add(moyenMenu);

				//---- petitMenu ----
				petitMenu.setText("Petit");
				petitMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tailleActionPerformed(e);
					}
				});
				menu4.add(petitMenu);

				//---- nainMenu ----
				nainMenu.setText("Nain");
				nainMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						tailleActionPerformed(e);
					}
				});
				menu4.add(nainMenu);
			}
			menuBar1.add(menu4);

			//======== menu5 ========
			{
				menu5.setText("Sexe");

				//---- hommeMenu ----
				hommeMenu.setText("Homme");
				hommeMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sexeActionPerformed(e);
					}
				});
				menu5.add(hommeMenu);

				//---- femmeMenu ----
				femmeMenu.setText("Femme");
				femmeMenu.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						sexeActionPerformed(e);
					}
				});
				menu5.add(femmeMenu);
			}
			menuBar1.add(menu5);
		}
		setJMenuBar(menuBar1);

		//======== splitPane1 ========
		{
			splitPane1.setResizeWeight(0.8);
			splitPane1.setEnabled(false);

			//======== scrollPane1 ========
			{

				//---- messageToSend ----
				messageToSend.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						messageToSendKeyPressed(e);
					}
				});
				scrollPane1.setViewportView(messageToSend);
			}
			splitPane1.setLeftComponent(scrollPane1);

			//---- button1 ----
			button1.setText("Envoyer");
			button1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					envoyerActionPerformed(e);
				}
			});
			splitPane1.setRightComponent(button1);
		}
		contentPane.add(splitPane1, BorderLayout.SOUTH);

		//======== splitPane2 ========
		{
			splitPane2.setEnabled(false);
			splitPane2.setResizeWeight(0.8);
			splitPane2.setPreferredSize(new Dimension(132, 46));

			//======== scrollPane2 ========
			{
				scrollPane2.setViewportView(chatArea);
			}
			splitPane2.setLeftComponent(scrollPane2);

			//======== scrollPane3 ========
			{
				scrollPane3.setViewportView(list1);
			}
			splitPane2.setRightComponent(scrollPane3);
		}
		contentPane.add(splitPane2, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Bertrand Pages
	private JMenuBar menuBar1;
	private JMenu menu1;
	private JMenu menu2;
	private JMenu menu3;
	private JMenuItem contentMenu;
	private JMenuItem tristeMenu;
	private JMenuItem effrayeMenu;
	private JMenuItem inquietMenu;
	private JMenuItem hilareMenu;
	private JMenu menu4;
	private JMenuItem geantMenu;
	private JMenuItem grandMenu;
	private JMenuItem moyenMenu;
	private JMenuItem petitMenu;
	private JMenuItem nainMenu;
	private JMenu menu5;
	private JMenuItem hommeMenu;
	private JMenuItem femmeMenu;
	private JSplitPane splitPane1;
	private JScrollPane scrollPane1;
	private JTextArea messageToSend;
	private JButton button1;
	private JSplitPane splitPane2;
	private JScrollPane scrollPane2;
	private JTextArea chatArea;
	private JScrollPane scrollPane3;
	private JList list1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
	private DefaultListModel connectedList;
	private UserManager userManager;
	
	public void initializeList(ArrayList<String> listConnected, UserManager userManager){
		this.userManager = userManager;
		connectedList = new DefaultListModel();
		for(String connected : listConnected){
			connectedList.addElement(connected);
		}
		this.list1.setModel(connectedList);
		this.list1.updateUI();
		this.chatArea.setEditable(false);
	}
	
	public void newMessage(String username, String message){
		this.updateChatArea(username + " : " + message + "\n");
	}
	
	public void newConnection(String username){
		this.updateChatArea(username + " s'est connecté à la salle\n");
	}
	
	public void newMood(String username, UserMood userMood){
		String mood = "";
		if(userMood == UserMood.CONTENT){
			mood = "content :)";
		} else if(userMood == UserMood.EFFRAYE){
			mood = "effrayé ! Ahhhhh";
		} else if(userMood == UserMood.HILARE){
			mood = "hilare :D";
		} else if(userMood == UserMood.INQUIET){
			mood = "inquiet :s";
		} else if(userMood == UserMood.TRISTE){
			mood = "triste :(";
		}
		updateChatArea(username + " est maintenant " + mood + "\n");
	}
	
	public void newSex(String username, UserSex userSex){
		String sex = "";
		if(userSex == UserSex.MALE){
			sex = "un homme !";
		} else if(userSex == UserSex.FEMALE){
			sex = "une femme !";
		}
		updateChatArea(username + " est maintenant " + sex + "\n");
	}
	
	public void newSize(String username, UserSize userSize){
		String size = "";
		if(userSize == UserSize.GEANT){
			size = "geant !";
		} else if(userSize == UserSize.GRAND){
			size = "grand !";
		} else if(userSize == UserSize.MOYEN){
			size = " de taille moyenne !";
		} else if(userSize == UserSize.PETIT){
			size = "petit !";
		} else if(userSize == UserSize.NAIN){
			size = "un nain !";
		}
		updateChatArea(username + " est maintenant " + size + "\n");
	}	
	
	private void updateChatArea(String message){
		String text = this.chatArea.getText();
		StringBuffer buffer = new StringBuffer(text);
		buffer.append(message);
		this.chatArea.setText(buffer.toString());
		this.chatArea.updateUI();
	}
	
	public void clearChatArea(){
		this.chatArea.setText(null);
		this.chatArea.updateUI();
	}
	
	public void clearMessageToSend(){
		this.messageToSend.setText(null);
		this.messageToSend.setCaretPosition(0);
		this.messageToSend.updateUI();
	}
	
	private void sendMessage(){
		String message = this.messageToSend.getText();
		this.clearMessageToSend();
		ClientUtils.getUserManager().sendBroadCastMessage(message);
	}
	
}
