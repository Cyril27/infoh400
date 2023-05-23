/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.infoh400.labs2022.view;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ulb.lisa.infoh400.labs2022.controller.AppointmentJpaController;
import ulb.lisa.infoh400.labs2022.controller.DoctorJpaController;
import ulb.lisa.infoh400.labs2022.controller.PatientJpaController;
import ulb.lisa.infoh400.labs2022.controller.PersonJpaController;
import ulb.lisa.infoh400.labs2022.controller.exceptions.IllegalOrphanException;
import ulb.lisa.infoh400.labs2022.controller.exceptions.NonexistentEntityException;
import ulb.lisa.infoh400.labs2022.model.Appointment;
import ulb.lisa.infoh400.labs2022.model.Doctor;
import ulb.lisa.infoh400.labs2022.model.Patient;
//import com.toedter.calendar.JDateChooser;

/**
 *
 * @author Adrien Foucart
 */
public class AddAppointmentWindow extends javax.swing.JFrame {
    private final EntityManagerFactory emfac = Persistence.createEntityManagerFactory("infoh400_PU");
    private final PatientJpaController patientCtrl = new PatientJpaController(emfac);
    private final DoctorJpaController doctorCtrl = new DoctorJpaController(emfac);
    private final AppointmentJpaController appointmentCtrl = new AppointmentJpaController(emfac);
    private final PersonJpaController personCtrl = new PersonJpaController(emfac);
    private final SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd  HH:MM");
    
    private static final Logger LOGGER = LogManager.getLogger(AddAppointmentWindow.class.getName());
    
    Appointment appointment = null;
    
    /**
     * Creates new form AddPatientWindow
     */
    public AddAppointmentWindow() {
        initComponents();
    }
    
    public void setAppointment(Appointment appointment){
        this.appointment = appointment;
        
       
		//addPersonPanel.setPerson(appointment.getIdperson());
        //patient.setIdpatient(appointment.getIdpatient());
        PatientSelect.setSelectedItem(appointment.getIdpatient());
        //addPatientWindow.setPatient(appointment.getIdpatient());
        //doctor.setIddoctor(appointment.getIddoctor());
        DoctorSelect.setSelectedItem(appointment.getIddoctor());
        //addDoctorWindow.setDoctor(appointment.getIddoctor());
        appointmenttime.setText(fmt.format(appointment.getAppointmenttime()));
        reasonTextField.setText(appointment.getReason());
        price.setText(String.valueOf(appointment.getPrice()));
        
    }
    
    public Appointment getAppointment(){
        updateAppointment();
                
        return appointment;
    }
    
    public void updateAppointment(){
        if( appointment == null ){
            appointment = new Appointment();
        }
        
        //appointment.setIdperson(addPersonPanel.getPerson());
        //appointment.setIdpatient(addPatientWindow.getPatient());//patient.getIdpatient());
        //appointment.setIddoctor(addDoctorWindow.getDoctor());//doctor.getIddoctor());
        //patient.setStatus((String) statusComboBox.getSelectedItem());
        appointment.setIdpatient((Patient) PatientSelect.getSelectedItem());
        appointment.setIddoctor((Doctor) DoctorSelect.getSelectedItem());
        try {
        	appointment.setAppointmenttime(fmt.parse(appointmenttime.getText()));
        } catch (ParseException ex) {
            LOGGER.error("Error setting appointment", ex);
        }
        //appointment.setAppointmenttime(appointmenttime.getDate());
        appointment.setReason(reasonTextField.getText());
        appointment.setPrice(Float.valueOf(price.getText()));
        //appointment.setStatus((String) statusComboBox.getSelectedItem());
    }

    public Component fillPatientSelect () {
    	try {
    		 String query = "Select * from patient";
    		 PreparedStatement pst = con.prepareStatement(query);
    		 ResultSet rs = pst.executeQuery();
    		 
    		 while (rs.next()) {
    			 PatientSelect.addItem(rs.getString("idpatient"));
    		 }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		return PatientSelect;
    }
    public Component fillDoctorSelect () {
    	try {
    		 String query = "Select * from doctor";
    		 PreparedStatement pst = con.prepareStatement(query);
    		 ResultSet rs = pst.executeQuery();
    		 
    		 while (rs.next()) {
    			 DoctorSelect.addItem(rs.getInt("iddoctor"));
    		 }
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
		return DoctorSelect;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        PatientSelect = new javax.swing.JComboBox<>();
        //addPatientWindow = new ulb.lisa.infoh400.labs2022.view.AddPatientWindow();
        jLabel6 = new javax.swing.JLabel();
        DoctorSelect = new javax.swing.JComboBox<>();;
        //addDoctorWindow = new ulb.lisa.infoh400.labs2022.view.AddDoctorWindow();
        jLabel2 = new javax.swing.JLabel();
        appointmenttime = new javax.swing.JFormattedTextField(); //new com.toedter.calendar.JDateChooser();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        reasonTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        price = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Appointment");
        
        jLabel5.setText("ID Patient");
        
        jLabel6.setText("ID Doctor");

        jLabel2.setText("Appointment Time:");

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Reason : ");
        
        jLabel4.setText("Price : ");

		//statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Active", "Inactive", "Banned" }));
        //fillPatientSelect();
        //fillDoctorSelect();
        PatientSelect.setModel(new javax.swing.DefaultComboBoxModel<>());
        DoctorSelect.setModel(new javax.swing.DefaultComboBoxModel<>());
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    /**.addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(patient addPatientWindow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(doctor addDoctorWindow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))**/
                		/**.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(PatientSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(DoctorSelect, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18)**/
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        	.addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PatientSelect/**fillPatientSelect()**/, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        	.addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(DoctorSelect/**fillDoctorSelect()**/, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(appointmenttime, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saveButton)
                                .addGap(18, 18, 18)
                                .addComponent(cancelButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(40, 40, 40)
                                .addComponent(reasonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                			.addGroup(layout.createSequentialGroup()
                				.addComponent(jLabel4)
                				.addGap(40, 40, 40)
                				.addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(178, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                /**.addComponent(addPatientWindow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addDoctorWindow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)**/
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(PatientSelect/**fillPatientSelect()**/, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(DoctorSelect/**fillDoctorSelect()**/, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(appointmenttime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(reasonTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(cancelButton)
                .addGap(18, 18, 18))
                .addGap(0, 5, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        updateAppointment();

        // Create person if necessary:
       /** if( appointment.getIdperson().getIdperson() == null ){
            personCtrl.create(appointment.getIdperson());
            LOGGER.debug("Created new person (id = %d)".format(appointment.getIdperson().getIdperson().toString()));
        }**/
        // Create patient if necessary
        if( appointment.getIdappointment() == null ){
            appointmentCtrl.create(appointment);
            LOGGER.debug("Created new appointment (id = %d)".format(appointment.getIdappointment().toString()));
        }
        
        // Save changes
        try {
            //personCtrl.edit(appointment.getIdperson());
            appointmentCtrl.edit(appointment);
            LOGGER.debug("Edited appointment (id = %d)".format(appointment.getIdappointment().toString()));
        } catch (NonexistentEntityException | IllegalOrphanException ex) {
            LOGGER.error("Couldn't edit appointment", ex);
        } catch (Exception ex){
            LOGGER.error("Couldn't edit appointment", ex);
        }
        
        this.dispose();        
    }//GEN-LAST:event_saveButtonActionPerformed
    Connection con = null;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    //private ulb.lisa.infoh400.labs2022.view.AddPersonPanel addPersonPanel;
    private ulb.lisa.infoh400.labs2022.view.AddPatientWindow addPatientWindow;
    private ulb.lisa.infoh400.labs2022.view.AddDoctorWindow addDoctorWindow;
    /**private ulb.lisa.infoh400.labs2022.model.Appointment patient;
    private ulb.lisa.infoh400.labs2022.model.Appointment doctor;**/

    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5; 
    private javax.swing.JLabel jLabel6; 
    //private javax.swing.JTextField patient;
    //private javax.swing.JTextField doctor;
    private javax.swing.JTextField reasonTextField;
    private javax.swing.JTextField price;
    private javax.swing.JFormattedTextField appointmenttime;
    //private java.lang.Float price1;
    //private com.toedter.calendar.JDateChooser appointmenttime;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox PatientSelect;
    private javax.swing.JComboBox DoctorSelect;
    //private javax.swing.JSelect DoctorSelect;
    // End of variables declaration//GEN-END:variables
}
