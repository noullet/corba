/*
 * Created by JFormDesigner on Mon Jun 04 23:18:56 CEST 2012
 */

package ui;

import interfaces.User;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import client.Client;

/**
 * @author Bertrand Pages
 */
public class AdminFrame extends JFrame {
	private DefaultListModel<String> userList;

	public AdminFrame() {
		initComponents();
		this.setTitle("Panneau d'administration");
	}

	private void closeActionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void listUserMouseClicked(MouseEvent e) {
		if (!this.listUser.isSelectionEmpty()) {
			String login = (String) this.listUser.getSelectedValue();
			User user = Client.getUserManager().getUserInRoom(login);
			InformationDialog info = new InformationDialog(this, user);
			info.setVisible(true);
		}
	}

	private void kickButtonActionPerformed(ActionEvent e) {
		if (!this.listUser.isSelectionEmpty()) {
			String login = this.listUser.getSelectedValue();
			ConfirmKickDialog confirm = new ConfirmKickDialog(this, login);
			confirm.setVisible(true);
		}
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Bertrand Pages
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label1 = new JLabel();
		scrollPane1 = new JScrollPane();
		listUser = new JList<String>();
		buttonBar = new JPanel();
		kickButton = new JButton();
		okButton = new JButton();

		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
					new javax.swing.border.EmptyBorder(0, 0, 0, 0), "JFormDesigner Evaluation",
					javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font(
							"Dialog", java.awt.Font.BOLD, 12), java.awt.Color.red), dialogPane.getBorder()));
			dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent e) {
					if ("border".equals(e.getPropertyName()))
						throw new RuntimeException();
				}
			});

			dialogPane.setLayout(new BorderLayout());

			// ======== contentPanel ========
			{
				contentPanel.setLayout(new FlowLayout());

				// ---- label1 ----
				label1.setText("Cliquez sur un utilisateur pour le d\u00e9connecter :");
				contentPanel.add(label1);

				// ======== scrollPane1 ========
				{

					// ---- listUser ----
					listUser.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							listUserMouseClicked(e);
						}
					});
					scrollPane1.setViewportView(listUser);
				}
				contentPanel.add(scrollPane1);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			// ======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] { 0, 80 };
				((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[] { 1.0, 0.0 };

				// ---- kickButton ----
				kickButton.setText("Expulser s\u00e9lectionn\u00e9");
				kickButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						kickButtonActionPerformed(e);
					}
				});
				buttonBar.add(kickButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));

				// ---- okButton ----
				okButton.setText("Fermer");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						closeActionPerformed(e);
					}
				});
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Bertrand Pages
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel label1;
	private JScrollPane scrollPane1;
	private JList<String> listUser;
	private JPanel buttonBar;
	private JButton kickButton;
	private JButton okButton;

	// JFormDesigner - End of variables declaration //GEN-END:variables

	public void kickUser(String login) {
		// Client.getUserManager().adminKick(user);
		userList.removeElement(login);
	}

	public void initialiseListUser(User[] users) {
		userList = new DefaultListModel<String>();
		for (User user : users) {
			userList.addElement(user.login);
		}
		this.listUser.setModel(userList);
		this.scrollPane1.updateUI();
	}
}
