package ulb.lisa.infoh400.labs2022.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pixelmed.dicom.AttributeList;
import com.pixelmed.dicom.DicomException;
import com.pixelmed.dicom.DicomInputStream;
import com.pixelmed.dicom.TagFromName;
import com.pixelmed.network.DicomNetworkException;
import com.pixelmed.network.ReceivedObjectHandler;
import com.pixelmed.network.StorageSOPClassSCPDispatcher;

import ulb.lisa.infoh400.labs2022.controller.AppointmentJpaController;
import ulb.lisa.infoh400.labs2022.controller.DoctorJpaController;
import ulb.lisa.infoh400.labs2022.controller.ImageJpaController;
import ulb.lisa.infoh400.labs2022.controller.PatientJpaController;
import ulb.lisa.infoh400.labs2022.controller.exceptions.IllegalOrphanException;
import ulb.lisa.infoh400.labs2022.controller.exceptions.NonexistentEntityException;
import ulb.lisa.infoh400.labs2022.model.Appointment;
import ulb.lisa.infoh400.labs2022.model.Doctor;
import ulb.lisa.infoh400.labs2022.model.Patient;
import java.awt.event.ActionListener;
//import java.awt.Image;
import ulb.lisa.infoh400.labs2022.model.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle.ComponentPlacement;










public class OurReceivedObjectHandler extends ReceivedObjectHandler {
    public void sendReceivedObjectIndication(String dicomFileName,String transferSyntax,String callingAETitle) throws DicomNetworkException, DicomException, IOException {
    	System.out.println("okidoki");
        }
    }
