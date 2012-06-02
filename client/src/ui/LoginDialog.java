/*
 * Created by JFormDesigner on Tue May 29 19:00:58 CEST 2012
 */

package ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.Client;

/**
 * @author Bertrand Pages
 */
public class LoginDialog extends JDialog {
	public LoginDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public LoginDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void okButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
		this.login();
	}

	private void dialogKeyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_ENTER) {
			this.login();
		}
	}

	private void thisWindowClosing(WindowEvent e) {
		System.exit(0);
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		System.exit(0);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Bertrand Pages
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		panel1 = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		label2 = new JLabel();
		passwordField1 = new JPasswordField();
		buttonBar = new JPanel();
		okButton = new JButton();
		cancelButton = new JButton();

		// ======== this ========
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				dialogKeyPressed(e);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				thisWindowClosing(e);
			}
		});
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
				contentPanel.setLayout(new GridLayout(2, 2));

				// ======== panel1 ========
				{
					panel1.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							dialogKeyPressed(e);
						}
					});
					panel1.setLayout(new FlowLayout());

					// ---- label1 ----
					label1.setText("Login : ");
					panel1.add(label1);

					// ---- textField1 ----
					textField1.setColumns(35);
					textField1.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							dialogKeyPressed(e);
						}
					});
					panel1.add(textField1);

					// ---- label2 ----
					label2.setText("Password : ");
					panel1.add(label2);

					// ---- passwordField1 ----
					passwordField1.setColumns(35);
					passwordField1.addKeyListener(new KeyAdapter() {
						@Override
						public void keyPressed(KeyEvent e) {
							dialogKeyPressed(e);
						}
					});
					panel1.add(passwordField1);
				}
				contentPanel.add(panel1);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			// ======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout) buttonBar.getLayout()).columnWidths = new int[] { 0, 85, 80 };
				((GridBagLayout) buttonBar.getLayout()).columnWeights = new double[] { 1.0, 0.0, 0.0 };

				// ---- okButton ----
				okButton.setText("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 5), 0, 0));

				// ---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed(e);
					}
				});
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
						GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Bertrand Pages
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JPanel panel1;
	private JLabel label1;
	private JTextField textField1;
	private JLabel label2;
	private JPasswordField passwordField1;
	private JPanel buttonBar;
	private JButton okButton;
	private JButton cancelButton;

	// JFormDesigner - End of variables declaration //GEN-END:variables

	private void login() {
		String username = this.textField1.getText();
		String password = new String(this.passwordField1.getPassword());
		Client.getUserManager().login(username, password);
		this.setVisible(false);
	}
}
