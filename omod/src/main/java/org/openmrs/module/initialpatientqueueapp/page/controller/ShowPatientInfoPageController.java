package org.openmrs.module.initialpatientqueueapp.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.initialpatientqueueapp.InitialPatientQueueConstants;
import org.openmrs.module.initialpatientqueueapp.model.PatientModel;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowPatientInfoPageController {
	
	private static Log logger = LogFactory.getLog(ShowPatientInfoPageController.class);
	
	public void controller() {
		
	}
	
	public void get(@RequestParam("patientId") Integer patientId,
	        @RequestParam(value = "encounterId", required = false) Integer encounterId,
	        @RequestParam(value = "payCategory", required = false) String payCategory,
	        @RequestParam(value = "visit", required = false) boolean visit, PageModel model) throws IOException,
	        ParseException {
		
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy kk:mm");
		model.addAttribute("receiptDate", simpleDate.format(new Date()));
		
		Patient patient = Context.getPatientService().getPatient(patientId);
		HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		PatientModel patientModel = new PatientModel(patient);
		
		model.addAttribute("patient", patientModel);
		model.addAttribute("patientAge", patient.getAge());
		model.addAttribute("patientGender", patient.getGender());
		
		Date lastVisitTime = hcs.getLastVisitTime(patient);
		Date currentVisitTime = new Date();
		long visitTimeDifference = 0;
		if (lastVisitTime != null) {
			visitTimeDifference = this.dateDiffInHours(lastVisitTime, currentVisitTime);
		}
		model.addAttribute("visitTimeDifference", visitTimeDifference);
		
		model.addAttribute("firstTimeVisit", false);
		
		User user = Context.getAuthenticatedUser();
		model.addAttribute("reVisitFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_REVISIT_REGISTRATION_FEE, "0.0"));
		model.addAttribute("childLessThanFiveYearRegistrationFee", GlobalPropertyUtil.getString(
		    InitialPatientQueueConstants.PROPERTY_CHILDLESSTHANFIVEYEAR_REGISTRATION_FEE, "0.0"));
		model.addAttribute("specialClinicRegFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_SPECIALCLINIC_REGISTRATION_FEE, "0.0"));
		model.addAttribute("registrationFees",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_INITIAL_REGISTRATION_FEE, "0.0"));
		
		model.addAttribute("user", user.getPersonName().getFullName());
		model.addAttribute("names", Context.getPersonService().getPerson(patientId).getPersonName().getFullName());
		model.addAttribute("patientId", Context.getPatientService().getPatient(patientId).getActiveIdentifiers().get(0)
		        .getIdentifier());
		model.addAttribute("location", Context.getService(KenyaEmrService.class).getDefaultLocation().getName());
		model.addAttribute("age", Context.getPatientService().getPatient(patientId).getAge());
		model.addAttribute("gender", Context.getPatientService().getPatient(patientId).getGender());
		model.addAttribute("previousVisit", lastVisitTime);
		String payCat = "";
		if (payCategory.equals("1")) {
			payCat = "PAYING";
		} else if (payCategory.equals("2")) {
			payCat = "NON-PAYING";
		} else if (payCategory.equals("3")) {
			payCat = "SPECIAL SCHEMES";
		}
		model.addAttribute("selectedPaymentCategory", payCat);
		String WhatToBePaid = "";
		if (!visit) {
			//This a new patient and might be required to pay registration fees
			WhatToBePaid = "Registration fees:		"
			        + GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_INITIAL_REGISTRATION_FEE, "0.0");
		} else {
			WhatToBePaid = "Revisit fees:		"
			        + GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_REVISIT_REGISTRATION_FEE, "0.0");
		}
		model.addAttribute("WhatToBePaid", WhatToBePaid);
		
	}
	
	private long dateDiffInHours(Date d1, Date d2) {
		long diff = Math.abs(d1.getTime() - d2.getTime());
		return (diff / (1000 * 60 * 60));
	}
	
}
