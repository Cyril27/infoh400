package ulb.lisa.infoh400.labs2022.view;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ulb.lisa.infoh400.labs2022.model.Image;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DicomImageWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public DicomImageWindow(Image selected) {
		DicomDirectoryWindow ddw = new DicomDirectoryWindow();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 730, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextArea dicomAttributesTextArea = new JTextArea();
		dicomAttributesTextArea.setBounds(6, 6, 402, 397);
		contentPane.add(dicomAttributesTextArea);
		
		
		String Attribute = ddw.getAttribute(selected);					// use function of the ddw to get the attributes
		dicomAttributesTextArea.setText(Attribute);
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(435, 6, 289, 356);
		contentPane.add(imageLabel);
		
		
		java.awt.Image displayImage = ddw.getImage(selected);  			// use function of the ddw to get the image
		
		imageLabel.setIcon(new ImageIcon(displayImage));
		
		JButton closebtn = new JButton("Close");
		closebtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closebtnActionPerformed();
			}
		});
		closebtn.setBounds(613, 374, 117, 29);
		contentPane.add(closebtn);
		
	}
	
	public void closebtnActionPerformed() {
		this.dispose();
	}
}