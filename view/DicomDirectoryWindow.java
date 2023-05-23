package ulb.lisa.infoh400.labs2022.view;

import java.awt.EventQueue;


import java.awt.Image;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.AttributeTag;
import com.pixelmed.dicom.DicomDirectory;
import com.pixelmed.dicom.DicomDirectoryBrowser;
import com.pixelmed.dicom.DicomDirectoryRecord;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.display.SourceImage;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.StorageSOPClassSCU;

import ulb.lisa.infoh400.labs2022.controller.ImageJpaController;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTree;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.event.TreeSelectionListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class DicomDirectoryWindow extends JFrame {

	private JPanel contentPane;
	File selectedFile = null;
	
	 private static final Logger LOGGER = LogManager.getLogger(DicomDirectoryWindow.class.getName());

	/**
	 * Create the frame.
	 */
	public DicomDirectoryWindow() {	
		
		/* JTree to display the DICOMDIR arborescence */
		JTree tree = new JTree();
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("JTree") {
				{
					add(new DefaultMutableTreeNode(""));
				}
			}
		));
		
		
		JButton SelectDicom = new JButton("Select DICOM Dir");
		JButton save = new JButton("Save");											// to save to the database
		JLabel select_im = new JLabel("");											// to display the image
		JTextPane textdicom = new JTextPane();										// to display the attributes of an element of the tree
		
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 862, 591);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/* JButton to make the connection of the DICOMDIR */
		SelectDicom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectbuttonActionPerformed(e, tree);
			}
		});
		
		SelectDicom.setBounds(6, 6, 173, 29);
		contentPane.add(SelectDicom);
		
		
		/* JButton to save the selected image in the image list */
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savebuttonActionPerformed(e, tree); 
			}
		});
		save.setBounds(249, 6, 117, 29);
		contentPane.add(save);
		
		
		select_im.setBounds(418, 243, 437, 285);
		contentPane.add(select_im);
		
		
		textdicom.setBounds(418, 43, 437, 177);
		contentPane.add(textdicom);
		
		
		tree.setBounds(16, 43, 350, 514);
		contentPane.add(tree);
		
		JButton close = new JButton("Close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closebuttonActionPerformed();
			}
		});
		close.setBounds(738, 528, 117, 29);
		contentPane.add(close);
		
		/* Display info the selected element of the tree*/
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {					// Function launched every time another object is selected
				getAttributeListFromSelectedPath(tree, textdicom, select_im);
			}
		});
	
		
		}
	
   /* End of initiation of components */ 
    
    /* Definition of functions */
	
	
	
	/* Storing of the image in the db */
	public void savebuttonActionPerformed(ActionEvent e, JTree tree) {
		sendToStoreSCP(tree);													// will store the image in the PACS if connection to SCP done before
		saveInstancetoDataBase(tree);											// store some of the image's in the database
	}
	
	/* Closing of the window */
	public void closebuttonActionPerformed() {
		this.dispose();
	}
	
	/* Make the selection of the DICOMDIR */
	public void selectbuttonActionPerformed(ActionEvent e, JTree tree) {    	// code to read a file on the disk
		String defaultDirectory = "/Users/cyrilvanleer/Downloads/DICOMDIR";  	// default directory given
		JFileChooser fc = new JFileChooser(defaultDirectory);

		if( fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){			// Display pop up to choose the file
			selectedFile = fc.getSelectedFile();								// get pointer of the selected file 
			readDicomDirectory(selectedFile, tree);
		}
	}
	
	/* Read the DICOMDIR and create the tree */
	public void readDicomDirectory(File f, JTree tree){
		try {
			AttributeList list = new AttributeList();
			list.read(f);														// Create a DICOMDIR object based on the list of attributes
		    DicomDirectory ddr = new DicomDirectory(list);						// and create the dicomdir tree (arborescence patient/study/serie/image)
		    tree.setModel(ddr);													// tree model to use the Jtree
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/* Display info of the object and image if IMAGE*/
	public void getAttributeListFromSelectedPath(JTree jTree, JTextPane textdicom, JLabel select_im){
	    DicomDirectoryRecord ddr = (DicomDirectoryRecord) jTree.getLastSelectedPathComponent();			// get actually selected object (of DicomDirectoryRecord type)
	    AttributeList a1 = ddr.getAttributeList();														// makes a list from the selected object (with all the tags and their values)
	    
	    String out = "";
		for(AttributeTag tag: a1.keySet() ){ 
	    	out += tag + " : " + a1.get(tag).getDelimitedStringValuesOrEmptyString() + "\n";  //Mettre des cas où si il y a un empty string,...
	    }
	    textdicom.setText(out);																			// Put all DICOM attributes in the textArea
	    
	    if (a1.get(TagFromName.DirectoryRecordType).getSingleStringValueOrEmptyString().equalsIgnoreCase("IMAGE")) { // Check if it is an image
		    File f = getSelectedPath(jTree);															// go get the pixel data
		    try {
				SourceImage sImg = new SourceImage(f.getAbsolutePath());
				Image img = sImg.getBufferedImage();
				select_im.setIcon(new ImageIcon(img));
					
					
			} catch (IOException | DicomException e) {
				System.out.println("There is an error in the file");
			}
	    }
	}
		 
	
	/* Extract the pixel data */
	public File getSelectedPath(JTree jTree) {
		// PixelData not in the DICOMDIR file but need to get the Image DICOM file through the reference file ID
		DicomDirectoryRecord ddr = (DicomDirectoryRecord) jTree.getLastSelectedPathComponent();
	    AttributeList a1 = ddr.getAttributeList();
	    
	    if (a1.get(TagFromName.DirectoryRecordType).getSingleStringValueOrEmptyString().equalsIgnoreCase("IMAGE")) {
	    	String referenceFileId_raw = a1.get(TagFromName.ReferencedFileID).getDelimitedStringValuesOrNull(); 		// path to the image file
	    	String[] ref = referenceFileId_raw.split("\\\\");
	    	String referenceFileId = "";	
	    	for (String word: ref) {
	    		referenceFileId += word + "//";
	    	} 
	    	String basedirectory = selectedFile.getParent();											// Path used to reconstruct
	    	File f = new File(basedirectory, referenceFileId);											// create new file with the original directory and the rest of the path
	    	//System.out.println(f);
	    	return f;
	    }
	    return null;
	}
	
	/* Store the image in the database */
	
	public void saveInstancetoDataBase(JTree jTree) {
		try {
			AttributeList a1 = new AttributeList();														// get attribute list of the image 
			File InstanceFile = getSelectedPath(jTree);
			a1.read(InstanceFile);
			
			EntityManagerFactory emfac = Persistence.createEntityManagerFactory("infoh400_PU");
			ImageJpaController imageCtrl = new ImageJpaController(emfac);								// use controller to create a new image
			
			String instanceUID = a1.get(TagFromName.SOPInstanceUID).getSingleStringValueOrEmptyString();// get the tags 
			String studyUID = a1.get(TagFromName.StudyInstanceUID).getSingleStringValueOrEmptyString();
			String seriesUID = a1.get(TagFromName.SeriesInstanceUID).getSingleStringValueOrEmptyString();
			String patientID = a1.get(TagFromName.PatientID).getSingleStringValueOrEmptyString();
			
			ulb.lisa.infoh400.labs2022.model.Image image = new ulb.lisa.infoh400.labs2022.model.Image();
			image.setInstanceuid(instanceUID);
			image.setStudyuid(studyUID);
			image.setSeriesuid(seriesUID);
			image.setPatientDicomIdentifier(patientID);
			
			imageCtrl.create(image);																	// create it in the db
			LOGGER.info("Saved instance to the database (instancUID:" + instanceUID + ")");
			System.out.println("OK");
			
		} catch(Exception e){
			// System.out.println("Couldn't save the image into the database", e);
			LOGGER.error("Couldn't save the image into the database", e);
			System.out.println("NON");
		}
	}
	
	
	/* Send the instance and SOPclass to the PACS  (TP5) */   
	private void sendToStoreSCP(JTree tree) {
		AttributeList a1 = new AttributeList();
		try {
			a1.read(getSelectedPath(tree));
		} catch(Exception e) {
			System.out.println("Error in the SCP Servor");
		}
		
		String instanceUID = a1.get(TagFromName.SOPInstanceUID).getSingleStringValueOrEmptyString();
		String SOPClassUID = a1.get(TagFromName.SOPClassUID).getSingleStringValueOrEmptyString();
		
		try {
			new StorageSOPClassSCU("localhost", 11115, "STORESCP", "STORESCU", getSelectedPath(tree).getAbsolutePath(), SOPClassUID, instanceUID, 0);
			System.out.println("Connection Servor OK");
			// need coordination of the port depending on what was created on the terminal
			
		} catch (DicomNetworkException e) {								// problem during the CSTORE
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DicomException e) {									// not valid dicom 
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {										// when opening problem (not existing file or cannot read)
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/* Get element from the PACS  (TP5) */
	
	
	
	private File DicomInstanceServices (ulb.lisa.infoh400.labs2022.model.Image image) {
		String instanceUID = image.getInstanceuid();
		File f = new File("/Users/cyrilvanleer/Desktop/PACS", instanceUID);
		return f;
	}
	
	
	/* Extract the image from an element of the PACS */
	public java.awt.Image getImage(ulb.lisa.infoh400.labs2022.model.Image image) {
		try {
			File f = DicomInstanceServices(image);
			SourceImage dicomimg = new SourceImage(f.toString());
			return dicomimg.getBufferedImage();
		} catch (Exception e) {
			System.out.println("Error in opening the image in another window");
		}
		return null;
	}
	
	
	/* Extract the attributes from an element of the PACS */
	public String getAttribute(ulb.lisa.infoh400.labs2022.model.Image image) {
		AttributeList a1 = new AttributeList();
		File InstanceFile = DicomInstanceServices(image);
		try {
			a1.read(InstanceFile);
		} catch (IOException e) {
			System.out.println("Error in the attribute list");
		} catch (DicomException e) {
			System.out.println("Error in the attribute list");
		}
		String out = "";
		for(AttributeTag tag: a1.keySet() ){
	    	out += tag + " : " + a1.get(tag).getDelimitedStringValuesOrEmptyString() + "\n";  //Mettre des cas où si il y a un empty string,...
	    }
		return out; 
	}
}