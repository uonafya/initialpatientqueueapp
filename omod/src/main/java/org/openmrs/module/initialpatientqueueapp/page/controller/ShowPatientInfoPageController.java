package org.openmrs.module.initialpatientqueueapp.page.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Patient;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.BillingService;
import org.openmrs.module.hospitalcore.HospitalCoreService;
import org.openmrs.module.hospitalcore.model.BillableService;
import org.openmrs.module.initialpatientqueueapp.EhrRegistrationUtils;
import org.openmrs.module.initialpatientqueueapp.InitialPatientQueueConstants;
import org.openmrs.module.initialpatientqueueapp.model.PatientModel;
import org.openmrs.module.kenyaemr.api.KenyaEmrService;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AppPage(InitialPatientQueueConstants.APP_PATIENT_QUEUE)
public class ShowPatientInfoPageController {
	
	private static Log logger = LogFactory.getLog(ShowPatientInfoPageController.class);
	
	public void controller() {
		
	}
	
	public void get(@RequestParam("patientId") Integer patientId,
	        @RequestParam(value = "encounterId", required = false) Integer encounterId,
	        @RequestParam(value = "payCategory", required = false) String payCategory,
	        @RequestParam(value = "roomToVisit", required = false) Integer roomToVisit,
	        @RequestParam(value = "visit", required = false) boolean visit, PageModel model) throws IOException,
	        ParseException {
		
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy kk:mm");
		model.addAttribute("receiptDate", simpleDate.format(new Date()));
		
		Patient patient = Context.getPatientService().getPatient(patientId);
		HospitalCoreService hcs = Context.getService(HospitalCoreService.class);
		PatientModel patientModel = new PatientModel(patient);
		
		Date lastVisitTime = hcs.getLastVisitTime(patient);
		Date currentVisitTime = new Date();
		long visitTimeDifference = 0;
		if (lastVisitTime != null) {
			visitTimeDifference = this.dateDiffInHours(lastVisitTime, currentVisitTime);
		}
		
		model.addAttribute("firstTimeVisit", false);
		
		User user = Context.getAuthenticatedUser();
		
		model.addAttribute("user", user.getPersonName().getFullName());
		model.addAttribute("names", Context.getPersonService().getPerson(patientId).getPersonName().getFullName());
		model.addAttribute("patientId", Context.getPatientService().getPatient(patientId).getPatientIdentifier()
		        .getIdentifier());
		model.addAttribute("location", Context.getService(KenyaEmrService.class).getDefaultLocation().getName());
		model.addAttribute("age", Context.getPatientService().getPatient(patientId).getAge());
		model.addAttribute("gender", Context.getPatientService().getPatient(patientId).getGender());
		model.addAttribute("previousVisit", lastVisitTime);
		String payCat = "";
		boolean paying = false;
		if (payCategory.equals("1")) {
			payCat = "PAYING";
			paying = true;
		} else if (payCategory.equals("2")) {
			payCat = "NON-PAYING";
		} else if (payCategory.equals("3")) {
			payCat = "SPECIAL SCHEMES";
		}
		model.addAttribute("selectedPaymentCategory", payCat);
		model.addAttribute("paying", paying);
		Concept registrationFeesConcept = Context.getConceptService().getConcept(
		    InitialPatientQueueConstants.CONCEPT_NAME_REGISTRATION_FEE);
		Concept revisitFeeConcept = Context.getConceptService().getConcept(
		    InitialPatientQueueConstants.CONCEPT_NAME_REVISIT_FEES);
		Concept specialClinicFeeConcept = Context.getConceptService().getConceptByUuid(
		    InitialPatientQueueConstants.CONCEPT_NAME_SPECIAL_CLINIC_FEES);
		
		Concept specialClinicRevisitFeeConcept = Context.getConceptService().getConceptByUuid(
		    InitialPatientQueueConstants.SPECIAL_CLINIC_REVISIT_FEES_UUID);
		
		BillableService registrationFee = Context.getService(BillingService.class).getServiceByConceptId(
		    registrationFeesConcept.getId());
		BillableService revisitFees = Context.getService(BillingService.class).getServiceByConceptId(
		    revisitFeeConcept.getId());
		BillableService specialClinicFeesAmount = Context.getService(BillingService.class).getServiceByConceptId(
		    specialClinicFeeConcept.getId());
		
		BillableService specialClinicRevisitFeesAmount = Context.getService(BillingService.class).getServiceByConceptId(
		    specialClinicRevisitFeeConcept.getId());
		
		String WhatToBePaid = "";
		String specialClinicFees = "";
		if (!visit) {
			//This a new patient and might be required to pay registration fees
			WhatToBePaid = "Registration fees:		" + registrationFee.getPrice();
		} else {
			WhatToBePaid = "Revisit fees:		" + revisitFees.getPrice();
		}
		if (roomToVisit != null && roomToVisit == 3 && !EhrRegistrationUtils.getLastSpecialClinicVisitForPatient(patient)) {
			specialClinicFees = "Special Clinic fees:		" + specialClinicFeesAmount.getPrice();
		} else {
			specialClinicFees = "Special Clinic revisit fees:		" + specialClinicRevisitFeesAmount.getPrice();
		}
		
		model.addAttribute("WhatToBePaid", WhatToBePaid);
		model.addAttribute("specialClinicFees", specialClinicFees);
		
	}
	
	private long dateDiffInHours(Date d1, Date d2) {
		long diff = Math.abs(d1.getTime() - d2.getTime());
		return (diff / (1000 * 60 * 60));
	}
	
}
