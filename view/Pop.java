package ulb.lisa.infoh400.labs2022.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ulb.lisa.infoh400.labs2022.controller.PatientJpaController;
import ulb.lisa.infoh400.labs2022.controller.exceptions.IllegalOrphanException;
import ulb.lisa.infoh400.labs2022.controller.exceptions.NonexistentEntityException;
import ulb.lisa.infoh400.labs2022.model.Patient;

import javax.swing.JTextPane;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Pop extends JFrame {

	private JPanel contentPane;
	private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("infoh400_PU");
	private final PatientJpaController patientCtrl = new PatientJpaController(emfac);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pop frame = new Pop(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Pop(Patient selected) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		
		JButton confirmButton = new JButton("YES");
		confirmButton.setBounds(137, 121, 75, 29);
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Sure(selected, e);
				} catch (IllegalOrphanException | NonexistentEntityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
			}

		
		});
		contentPane.setLayout(null);
		confirmButton.setHorizontalAlignment(SwingConstants.LEADING);
		contentPane.add(confirmButton);
		
		JTextPane txtpnAreYouSure = new JTextPane();
		txtpnAreYouSure.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
		txtpnAreYouSure.setBounds(127, 87, 221, 22);
		txtpnAreYouSure.setText("Are you sure about that ?");
		contentPane.add(txtpnAreYouSure);
		
		JButton cancelButton = new JButton("NO");
		cancelButton.setBounds(265, 121, 75, 29);
		contentPane.add(cancelButton);
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
		
		
	}
	
	
	public void Sure(Patient selected, java.awt.event.ActionEvent evt) throws IllegalOrphanException, NonexistentEntityException {
		
        patientCtrl.destroy(selected.getIdpatient());
        this.dispose();
        

	}
	
	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

}
