package org.openmrs.module.initialpatientqueueapp;

import org.openmrs.Patient;
import org.openmrs.Person;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientWrapper extends Patient implements Serializable {
    private Date lastVisitTime;
    private String wrapperIdentifier,formartedVisitDate;
    private static final Logger log = Logger.getLogger( PatientWrapper.class.getName() );

    public PatientWrapper(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public PatientWrapper(Person person, Date lastVisitTime) {
        super(person);
        this.lastVisitTime = lastVisitTime;
        this.wrapperIdentifier = ((Patient)person).getPatientIdentifier().getIdentifier();
    }

    public PatientWrapper(Integer patientId, Date lastVisitTime) {
        super(patientId);
        this.lastVisitTime = lastVisitTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }
    public String getFormartedVisitDate(){

        Format formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try{
            formartedVisitDate = formatter.format(lastVisitTime);
        }catch(Exception e){
            if(lastVisitTime!=null){
                formartedVisitDate = lastVisitTime.toString();
            }     else{
                formartedVisitDate = "N/A";
            }
            log.log( Level.SEVERE, e.toString(), e );

        }

        return formartedVisitDate;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getWrapperIdentifier() {
        return wrapperIdentifier;
    }

    public void setWrapperIdentifier(String wrapperIdentifier) {
        this.wrapperIdentifier = wrapperIdentifier;
    }

    public void setFormartedVisitDate(String formartedVisitDate) {
        this.formartedVisitDate = formartedVisitDate;
    }

}
