/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulb.lisa.infoh400.labs2022.view;

import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import ca.uhn.hl7v2.protocol.ReceivingApplicationException;
import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.model.v23.message.ADT_A01;
import ca.uhn.hl7v2.model.v23.segment.PID;
import java.io.IOException;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ulb.lisa.infoh400.labs2022.controller.PatientJpaController;
import ulb.lisa.infoh400.labs2022.controller.PersonJpaController;
import ulb.lisa.infoh400.labs2022.model.Patient;
import ulb.lisa.infoh400.labs2022.model.Person;

/**
 *
 * @author Adrien Foucart
 */
public class ADTReceiverApplication implements ReceivingApplication<Message> {

    @Override
    public Message processMessage(Message t, Map<String, Object> map) throws ReceivingApplicationException, HL7Exception {
    	   
        ADT_A01 message = (ADT_A01) t ; //message de la classe Message
        PID pid = message.getPID();
        Person p = new Person();
       
        p.setFamilyname(pid.getPatientName(0).getFamilyName().getValue());
        p.setFirstname(pid.getPatientName(0).getGivenName().getValue());
        p.setDateofbirth(pid.getDateOfBirth().getTimeOfAnEvent().getValueAsDate());
        
        
        EntityManagerFactory emfac = Persistence.createEntityManagerFactory("infoh400_PU");
        PersonJpaController personCtrl = new PersonJpaController(emfac);
        Person duplicate = personCtrl.findDuplicate(p);
        if(duplicate == null){
            PatientJpaController patientCtrl = new PatientJpaController(emfac);
            personCtrl.create(p);
            Patient pat = new Patient();
            pat.setIdperson(p);
            pat.setStatus("active");
            patientCtrl.create(pat);
        }
        
        String encodedMessage = new DefaultHapiContext().getPipeParser().encode(t);
        System.out.println("Received message:\n" + encodedMessage + "\n\n");						// Print processed message
          try {
          	return t.generateACK();
          } catch (IOException e) {
              throw new HL7Exception(e);
          }
    }

    @Override
    public boolean canProcess(Message t) {	// Can we process the message ??
    	return true;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}