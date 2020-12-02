package org.openmrs.module.initialpatientqueueapp.page.controller;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.PatientQueueService;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.hospitalcore.util.ObsUtils;
import org.openmrs.module.initialpatientqueueapp.EhrRegistrationUtils;
import org.openmrs.module.initialpatientqueueapp.InitialPatientQueueConstants;
import org.openmrs.module.initialpatientqueueapp.api.InitialPatientqueueappService;
import org.openmrs.module.initialpatientqueueapp.model.PatientModel;
import org.openmrs.module.initialpatientqueueapp.model.RegistrationFee;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowPatientInfoPageController {
	
	private static Log logger = LogFactory.getLog(ShowPatientInfoPageController.class);
	
	public void controller() {
		
	}
	
	public void get(@RequestParam("patientId") Integer patientId,
	        @RequestParam(value = "encounterId", required = false) Integer encounterId,
	        @RequestParam(value = "revisit", required = false) Boolean revisit, PageModel model) throws IOException,
	        ParseException {
		
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy kk:mm");
		model.addAttribute("receiptDate", simpleDate.format(new Date()));
		
		Patient patient = Context.getPatientService().getPatient(patientId);
		HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		PatientModel patientModel = new PatientModel(patient);
		
		model.addAttribute("patient", patientModel);
		model.addAttribute("patientAge", patient.getAge());
		model.addAttribute("patientGender", patient.getGender());
		
		// Get patient registration fee
		if (GlobalPropertyUtil.getInteger(InitialPatientQueueConstants.PROPERTY_NUMBER_OF_DATE_VALIDATION, 0) > 0) {
			List<RegistrationFee> fees = Context.getService(InitialPatientqueueappService.class).getRegistrationFees(
			    patient, GlobalPropertyUtil.getInteger(InitialPatientQueueConstants.PROPERTY_NUMBER_OF_DATE_VALIDATION, 0));
			if (!CollectionUtils.isEmpty(fees)) {
				RegistrationFee fee = fees.get(0);
				Calendar dueDate = Calendar.getInstance();
				dueDate.setTime(fee.getCreatedOn());
				dueDate.add(Calendar.DATE, 30);
				model.addAttribute("dueDate", EhrRegistrationUtils.formatDate(dueDate.getTime()));
				model.addAttribute("daysLeft", dateDiff(dueDate.getTime(), new Date()));
			}
		}
		
		// Get selected OPD room if this is the first time of visit
		if (encounterId != null) {
			List<PersonAttribute> pas = hcs.getPersonAttributes(patientId);
			for (PersonAttribute pa : pas) {
				PersonAttributeType attributeType = pa.getAttributeType();
				PersonAttributeType personAttributePaymentCategory = hcs.getPersonAttributeTypeByName("Payment Category");
				if (attributeType.getPersonAttributeTypeId().equals(
				    personAttributePaymentCategory.getPersonAttributeTypeId())) {
					model.addAttribute("selectedPaymentCategory", pa.getValue());
				}
			}
			
			Boolean firstTimeVisit = true;
			model.addAttribute("firstTimeVisit", firstTimeVisit);
			model.addAttribute("typeOfSlip", "Registration Receipt");
			model.addAttribute("reprint", false);
		}
		
		if ((revisit != null) && revisit) {
			model.addAttribute("typeOfSlip", "Registration Receipt");
			model.addAttribute("revisit", revisit);
			
			Date lastVisitTime = hcs.getLastVisitTime(patient);
			Date currentVisitTime = new Date();
			long visitTimeDifference = this.dateDiffInHours(lastVisitTime, currentVisitTime);
			model.addAttribute("visitTimeDifference", visitTimeDifference);
			
			model.addAttribute("firstTimeVisit", false);
		} else {
			//This patient is a new case and we need to pull registration fee based on the parameters chosen
		}
		
		User user = Context.getAuthenticatedUser();
		model.addAttribute("reVisitFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_REVISIT_REGISTRATION_FEE, ""));
		model.addAttribute("childLessThanFiveYearRegistrationFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_CHILDLESSTHANFIVEYEAR_REGISTRATION_FEE, ""));
		model.addAttribute("specialClinicRegFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_SPECIALCLINIC_REGISTRATION_FEE, ""));
		
		model.addAttribute("user", user.getPersonName().getFullName());
		model.addAttribute("names", Context.getPersonService().getPerson(patientId).getPersonName().getFullName());
		model.addAttribute("patientId", Context.getPatientService().getPatient(patientId).getActiveIdentifiers().get(0)
		        .getIdentifier());
		model.addAttribute("location", Context.getService(KenyaEmrService.class).getDefaultLocation().getName());
		model.addAttribute("age", Context.getPatientService().getPatient(patientId).getAge());
		model.addAttribute("gender", Context.getPatientService().getPatient(patientId).getGender());
		
	}
	
	/**
	 * Get date diff betwwen 2 dates
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	private long dateDiff(Date d1, Date d2) {
		long diff = Math.abs(d1.getTime() - d2.getTime());
		return (diff / (1000 * 60 * 60 * 24));
	}
	
	private long dateDiffInHours(Date d1, Date d2) {
		long diff = Math.abs(d1.getTime() - d2.getTime());
		return (diff / (1000 * 60 * 60));
	}
	
}
