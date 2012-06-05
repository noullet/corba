/*
 * Created by JFormDesigner on Mon Jun 04 23:35:34 CEST 2012
 */

package ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Bertrand Pages
 */
public class ConfirmKickDialog extends JDialog {
	private AdminFrame adminFrame;
	private String login;
	
	public ConfirmKickDialog(Frame owner) {
		new ConfirmKickDialog((AdminFrame)owner, "Unknow");
	}
	
	public ConfirmKickDialog(AdminFrame owner, String login){
		super(owner);
		this.adminFrame = owner;
		this.login = login;
		initComponents();
	}

	public ConfirmKickDialog(Dialog owner) {
		super(owner);
		initComponents();
	}

	private void KickActionPerformed(ActionEvent e) {
		adminFrame.kickUser(login);
		this.setVisible(false);
	}

	private void cancelButtonActionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	private void thisWindowClosing(WindowEvent e) {
		this.setVisible(false);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Bertrand Pages
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		label2 = new JLabel();
		label1 = new JLabel();
		buttonBar = new JPanel();
		text = new JButton();
		cancelButton = new JButton();

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
				contentPanel.setLayout(new FlowLayout());

				//---- label2 ----
				label2.setText("\u00cates vous sur de vouloir expulser : ");
				contentPanel.add(label2);

				//---- label1 ----
				label1.setText(login);
				contentPanel.add(label1);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0};

				//---- text ----
				text.setText("Oui");
				text.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						KickActionPerformed(e);
					}
				});
				buttonBar.add(text, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 5), 0, 0));

				//---- cancelButton ----
				cancelButton.setText("Non");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						cancelButtonActionPerformed(e);
					}
				});
				buttonBar.add(cancelButton, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
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
	private JLabel label2;
	private JLabel label1;
	private JPanel buttonBar;
	private JButton text;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
