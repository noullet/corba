/*
 * Created by JFormDesigner on Mon Jun 04 22:00:01 CEST 2012
 */

package ui;

import interfaces.User;
import interfaces.UserMood;
import interfaces.UserSex;
import interfaces.UserSize;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Bertrand Pages
 */
public class InformationDialog extends JDialog {
	public InformationDialog(Frame owner) {
		super(owner);
		initComponents();
	}

	public InformationDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void thisWindowClosing(WindowEvent e) {
		this.setVisible(false);
	}

	private void okButtonActionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Bertrand Pages
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		loginDesc = new JLabel();
		login = new JLabel();
		sexDesc = new JLabel();
		sex = new JLabel();
		sizeDesc = new JLabel();
		size = new JLabel();
		moodDesc = new JLabel();
		mood = new JLabel();
		buttonBar = new JPanel();
		okButton = new JButton();

		//======== this ========
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				thisWindowClosing(e);
			}
		});
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

			// JFormDesigner evaluation mark
			dialogPane.setBorder(new javax.swing.border.CompoundBorder(
				new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
					"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
					javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
					java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setLayout(new GridLayout(4, 2));

				//---- loginDesc ----
				loginDesc.setText("Login :");
				contentPanel.add(loginDesc);
				contentPanel.add(login);

				//---- sexDesc ----
				sexDesc.setText("Sexe :");
				contentPanel.add(sexDesc);
				contentPanel.add(sex);

				//---- sizeDesc ----
				sizeDesc.setText("Taille : ");
				contentPanel.add(sizeDesc);
				contentPanel.add(size);

				//---- moodDesc ----
				moodDesc.setText("Humeur : ");
				contentPanel.add(moodDesc);
				contentPanel.add(mood);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

				//---- okButton ----
				okButton.setText("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						okButtonActionPerformed(e);
					}
				});
				buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Bertrand Pages
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JLabel loginDesc;
	private JLabel login;
	private JLabel sexDesc;
	private JLabel sex;
	private JLabel sizeDesc;
	private JLabel size;
	private JLabel moodDesc;
	private JLabel mood;
	private JPanel buttonBar;
	private JButton okButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
	public void setValuesAndShow(User user){
		this.login.setText(user.login);
		
		if(user.mood.equals(UserMood.CONTENT)){
			this.mood.setText("Content");
		} else if(user.mood.equals(UserMood.HILARE)){
			this.mood.setText("Hilare");
		} else if(user.mood.equals(UserMood.EFFRAYE)){
			this.mood.setText("Effrayé");
		} else if(user.mood.equals(UserMood.TRISTE)){
			this.mood.setText("Triste");
		} else if(user.mood.equals(UserMood.INQUIET)){
			this.mood.setText("Inquiet");
		}
		
		if(user.size.equals(UserSize.GEANT)){
			this.size.setText("Géant");
		} else if (user.size.equals(UserSize.GRAND)){
			this.size.setText("Grand");
		} else if (user.size.equals(UserSize.MOYEN)){
			this.size.setText("Moyen");
		} else if (user.size.equals(UserSize.PETIT)){
			this.size.setText("Petit");
		} else if (user.size.equals(UserSize.NAIN)){
			this.size.setText("Nain");
		}
		
		if(user.sex.equals(UserSex.MALE)){
			this.sex.setText("Homme");
		} else if(user.sex.equals(UserSex.FEMALE)){
			this.sex.setText("Femme");
		}
		this.setVisible(true);
	}
}
