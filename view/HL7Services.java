/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ulb.lisa.infoh400.labs2022.view;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.app.Connection;
import ca.uhn.hl7v2.app.HL7Service;
import ca.uhn.hl7v2.app.Initiator;
import ca.uhn.hl7v2.llp.LLPException;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v23.message.ADT_A01;
import ca.uhn.hl7v2.model.v23.segment.MSH;
import ca.uhn.hl7v2.model.v23.segment.PID;
import ca.uhn.hl7v2.parser.Parser;
import ca.uhn.hl7v2.protocol.ReceivingApplication;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ulb.lisa.infoh400.labs2022.model.Patient;

/**
 *
 * @author Adrien Foucart
 */
public class HL7Services {
    
    public ADT_A01 create_ADT_A01(Patient patient){											// create ADT-A01 message from the patient
        ADT_A01 adt = null;
        try {
            adt = new ADT_A01();
            adt.initQuickstart("ADT", "A01", "P"); 											 //pré rempli le message avec les éléments obligatoires
            
            // Populate the MSH Segment														// Necessary to create an HL7 message  (Message header)
            MSH mshSegment = adt.getMSH();
            mshSegment.getSendingApplication().getNamespaceID().setValue("HIS");
            mshSegment.getSequenceNumber().setValue("123"); 								//permet d'identifier le message envoyé 
            																				// --> message auquel on fait ref (normalement il devrait changer à chaque message)
            
            // Populate the PID Segment
            PID pid = adt.getPID();															// Contains the infos of the patient  (Patient identification)
            pid.getPatientName(0).getFamilyName().setValue(patient.getIdperson().getFamilyname());
            pid.getPatientName(0).getGivenName().setValue(patient.getIdperson().getFirstname());   
            pid.getPatientIDInternalID(0).getID().setValue("123456");
        } catch (HL7Exception ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("message");
        System.out.println(adt);
        
        return adt;
    }
    
    public void send_ADT_A01 (ADT_A01 msg, String host, int port){				// need message, host and port to send the message
        HapiContext context = new DefaultHapiContext();
        Connection connection;
        try {
            connection = context.newClient("localhost", port, false); //Le dernier paramètre demande si on utilise un co sécurisée avec Tls
           
            Initiator initiator = connection.getInitiator();
            Message response = initiator.sendAndReceive(msg);
            Parser p = context.getPipeParser();
            System.out.println("response");
            System.out.println(p.encode(response));
            
            
        } catch (HL7Exception | LLPException | IOException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void startServer() {
        try {
            int port = 65432; // The port to listen on
            boolean useTls = false; // Should we use TLS/SSL?
            HapiContext context = new DefaultHapiContext();
            HL7Service server = context.newServer(port, useTls);						// Create a server 
            
            ReceivingApplication<Message> handler = new ADTReceiverApplication();		 
            server.registerApplication("ADT", "A01", handler);							// If message is ADT-A01 this instance will process it
            
            server.startAndWait();														// Start server -> begin to listen at the given port 
        } catch (InterruptedException ex) {
            Logger.getLogger(HL7Services.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}