/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.initialpatientqueueapp.fragment.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.Encounter;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.PersonAttribute;
import org.openmrs.api.context.Context;
import org.openmrs.module.hospitalcore.util.GlobalPropertyUtil;
import org.openmrs.module.hospitalcore.util.HospitalCoreUtils;
import org.openmrs.module.initialpatientqueueapp.EhrRegistrationUtils;
import org.openmrs.module.initialpatientqueueapp.InitialPatientQueueConstants;
import org.openmrs.module.initialpatientqueueapp.includable.validator.attribute.PatientAttributeValidatorService;
import org.openmrs.module.initialpatientqueueapp.web.controller.utils.RegistrationWebUtils;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 4 Fragment to process the queueing information for a patient return processed patients
 */
public class QueuePatientFragmentController {
	
	private static Log logger = LogFactory.getLog(QueuePatientFragmentController.class);
	
	public void controller(@FragmentParam("patient") Patient patient, FragmentModel model) {
		
		model.addAttribute("TRIAGE", RegistrationWebUtils.getSubConcepts(InitialPatientQueueConstants.CONCEPT_NAME_TRIAGE));
		model.addAttribute("OPDs", RegistrationWebUtils.getSubConcepts(InitialPatientQueueConstants.CONCEPT_NAME_OPD_WARD));
		model.addAttribute("SPECIALCLINIC",
		    RegistrationWebUtils.getSubConcepts(InitialPatientQueueConstants.CONCEPT_NAME_SPECIAL_CLINIC));
		model.addAttribute("payingCategory",
		    RegistrationWebUtils.getSubConceptsWithName(InitialPatientQueueConstants.CONCEPT_NAME_PAYING_CATEGORY));
		model.addAttribute("nonPayingCategory",
		    RegistrationWebUtils.getSubConceptsWithName(InitialPatientQueueConstants.CONCEPT_NAME_NONPAYING_CATEGORY));
		model.addAttribute("specialScheme",
		    RegistrationWebUtils.getSubConceptsWithName(InitialPatientQueueConstants.CONCEPT_NAME_SPECIAL_SCHEME));
		model.addAttribute("universities",
		    RegistrationWebUtils.getSubConceptsWithName(InitialPatientQueueConstants.CONCEPT_NAME_LIST_OF_UNIVERSITIES));
		
		Map<Integer, String> payingCategoryMap = new LinkedHashMap<Integer, String>();
		Concept payingCategory = Context.getConceptService().getConcept(
		    InitialPatientQueueConstants.CONCEPT_NAME_PAYING_CATEGORY);
		for (ConceptAnswer ca : payingCategory.getAnswers()) {
			payingCategoryMap.put(ca.getAnswerConcept().getConceptId(), ca.getAnswerConcept().getName().getName());
		}
		Map<Integer, String> nonPayingCategoryMap = new LinkedHashMap<Integer, String>();
		Concept nonPayingCategory = Context.getConceptService().getConcept(
		    InitialPatientQueueConstants.CONCEPT_NAME_NONPAYING_CATEGORY);
		for (ConceptAnswer ca : nonPayingCategory.getAnswers()) {
			nonPayingCategoryMap.put(ca.getAnswerConcept().getConceptId(), ca.getAnswerConcept().getName().getName());
		}
		Map<Integer, String> specialSchemeMap = new LinkedHashMap<Integer, String>();
		Concept specialScheme = Context.getConceptService().getConcept(
		    InitialPatientQueueConstants.CONCEPT_NAME_SPECIAL_SCHEME);
		for (ConceptAnswer ca : specialScheme.getAnswers()) {
			specialSchemeMap.put(ca.getAnswerConcept().getConceptId(), ca.getAnswerConcept().getName().getName());
		}
		model.addAttribute("payingCategoryMap", payingCategoryMap);
		model.addAttribute("nonPayingCategoryMap", nonPayingCategoryMap);
		model.addAttribute("specialSchemeMap", specialSchemeMap);
		model.addAttribute("initialRegFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_INITIAL_REGISTRATION_FEE, ""));
		
		model.addAttribute("childLessThanFiveYearRegistrationFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_CHILDLESSTHANFIVEYEAR_REGISTRATION_FEE, ""));
		model.addAttribute("specialClinicRegFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_SPECIALCLINIC_REGISTRATION_FEE, ""));
		
	}
	
	public String post(HttpServletRequest request, PageModel model, UiUtils uiUtils,
	        @RequestParam("patientId") Patient patient, @RequestParam("paym_1") Integer paymentOption) throws IOException {
		
		Map<String, String> parameters = RegistrationWebUtils.optimizeParameters(request);
		Map<String, Object> redirectParams = new HashMap<String, Object>();
		Map<Integer, String> payingCategoryMap = new LinkedHashMap<Integer, String>();
		Concept payingCategory = Context.getConceptService().getConcept(
		    InitialPatientQueueConstants.CONCEPT_NAME_PAYING_CATEGORY);
		for (ConceptAnswer ca : payingCategory.getAnswers()) {
			payingCategoryMap.put(ca.getAnswerConcept().getConceptId(), ca.getAnswerConcept().getName().getName());
		}
		Map<Integer, String> nonPayingCategoryMap = new LinkedHashMap<Integer, String>();
		Concept nonPayingCategory = Context.getConceptService().getConcept(
		    InitialPatientQueueConstants.CONCEPT_NAME_NONPAYING_CATEGORY);
		for (ConceptAnswer ca : nonPayingCategory.getAnswers()) {
			nonPayingCategoryMap.put(ca.getAnswerConcept().getConceptId(), ca.getAnswerConcept().getName().getName());
		}
		Map<Integer, String> specialSchemeMap = new LinkedHashMap<Integer, String>();
		Concept specialScheme = Context.getConceptService().getConcept(
		    InitialPatientQueueConstants.CONCEPT_NAME_SPECIAL_SCHEME);
		for (ConceptAnswer ca : specialScheme.getAnswers()) {
			specialSchemeMap.put(ca.getAnswerConcept().getConceptId(), ca.getAnswerConcept().getName().getName());
		}
		model.addAttribute("payingCategoryMap", payingCategoryMap);
		model.addAttribute("nonPayingCategoryMap", nonPayingCategoryMap);
		model.addAttribute("specialSchemeMap", specialSchemeMap);
		model.addAttribute("TRIAGE", RegistrationWebUtils.getSubConcepts(InitialPatientQueueConstants.CONCEPT_NAME_TRIAGE));
		model.addAttribute("OPDs", RegistrationWebUtils.getSubConcepts(InitialPatientQueueConstants.CONCEPT_NAME_OPD_WARD));
		model.addAttribute("SPECIALCLINIC",
		    RegistrationWebUtils.getSubConcepts(InitialPatientQueueConstants.CONCEPT_NAME_SPECIAL_CLINIC));
		model.addAttribute("payingCategory",
		    RegistrationWebUtils.getSubConceptsWithName(InitialPatientQueueConstants.CONCEPT_NAME_PAYING_CATEGORY));
		model.addAttribute("nonPayingCategory",
		    RegistrationWebUtils.getSubConceptsWithName(InitialPatientQueueConstants.CONCEPT_NAME_NONPAYING_CATEGORY));
		model.addAttribute("specialScheme",
		    RegistrationWebUtils.getSubConceptsWithName(InitialPatientQueueConstants.CONCEPT_NAME_SPECIAL_SCHEME));
		model.addAttribute("universities",
		    RegistrationWebUtils.getSubConceptsWithName(InitialPatientQueueConstants.CONCEPT_NAME_LIST_OF_UNIVERSITIES));
		model.addAttribute("initialRegFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_INITIAL_REGISTRATION_FEE, ""));
		
		model.addAttribute("childLessThanFiveYearRegistrationFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_CHILDLESSTHANFIVEYEAR_REGISTRATION_FEE, ""));
		model.addAttribute("specialClinicRegFee",
		    GlobalPropertyUtil.getString(InitialPatientQueueConstants.PROPERTY_SPECIALCLINIC_REGISTRATION_FEE, ""));
		
		try {
			// create encounter for the visit here
			Encounter encounter = createEncounter(patient, parameters);
			encounter = Context.getEncounterService().saveEncounter(encounter);
			redirectParams.put("status", "success");
			redirectParams.put("patientId", patient.getPatientId());
			redirectParams.put("encounterId", encounter.getId());
			
			model.addAttribute("status", "success");
			model.addAttribute("patientId", patient.getPatientId());
			model.addAttribute("encounterId", encounter.getId());
			return "redirect:" + uiUtils.pageLink("initialpatientqueueapp", "showPatientInfo");
			
		}
		catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("status", "error");
			model.addAttribute("message", e.getMessage());
			return null;
		}
		
	}
	
	/**
	 * Generate Patient From Parameters
	 * 
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	private Patient generatePatient(Map<String, String> parameters) throws Exception {
		
		Patient patient = new Patient();
		
		// get custom person attribute
		PatientAttributeValidatorService validator = new PatientAttributeValidatorService();
		Map<String, Object> validationParameters = HospitalCoreUtils.buildParameters("patient", patient, "attributes",
		    parameters);
		String validateResult = validator.validate(validationParameters);
		logger.info("Attribute validation: " + validateResult);
		if (StringUtils.isBlank(validateResult)) {
			for (String name : parameters.keySet()) {
				if ((name.contains(".attribute.")) && (!StringUtils.isBlank(parameters.get(name)))) {
					String[] parts = name.split("\\.");
					String idText = parts[parts.length - 1];
					Integer id = Integer.parseInt(idText);
					PersonAttribute attribute = EhrRegistrationUtils.getPersonAttribute(id, parameters.get(name));
					patient.addAttribute(attribute);
				}
			}
		} else {
			throw new Exception(validateResult);
		}
		return patient;
	}
	
	/**
	 * Create Encounter For The Visit Of Patient
	 * 
	 * @param patient
	 * @param parameters
	 * @return
	 */
	private Encounter createEncounter(Patient patient, Map<String, String> parameters) {
		
		int rooms1 = Integer.parseInt(parameters.get("rooms1"));
		int paymt1 = Integer.parseInt(parameters.get("paym_1"));
		int paymt2 = Integer.parseInt(parameters.get("paym_2"));
		int status = Integer.parseInt(parameters.get("visitType"));
		
		String paymt3 = null;
		String paymt4 = null;
		
		String tNTriage = null, oNOpd = null, sNSpecial = null, nFNumber;
		String nPayn = null, nNotpayn = null, nScheme = null, nNHIFnumb = null, nWaivernumb = null, nUniID = null, nStuID = null;
		
		switch (rooms1) {
			case 1: {
				tNTriage = parameters.get("rooms2");
				break;
			}
			case 2: {
				oNOpd = parameters.get("rooms2");
				break;
			}
			case 3: {
				sNSpecial = parameters.get("rooms2");
				nFNumber = parameters.get("rooms3");
				break;
			}
		}
		
		switch (paymt1) {
			case 1: {
				paymt3 = "Paying";
				if (paymt2 == 1) {
					nPayn = "GENERAL";
				} else if (paymt2 == 2) {
					nPayn = "CHILD LESS THAN 5 YEARS";
				} else if (paymt2 == 3) {
					nPayn = "EXPECTANT MOTHER";
				}
				
				break;
			}
			case 2: {
				paymt3 = "Non-Paying";
				
				if (paymt2 == 1) {
					nNotpayn = "NHIF CIVIL SERVANT";
					nNHIFnumb = parameters.get("modesummary");
				} else if (paymt2 == 2) {
					nNotpayn = "CCC PATIENT";
				} else if (paymt2 == 3) {
					nNotpayn = "TB PATIENT";
				} else if (paymt2 == 4) {
					nNotpayn = "PRISIONER";
				}
				
				break;
			}
			case 3: {
				paymt3 = "Special Schemes";
				
				if (paymt2 == 1) {
					nUniID = parameters.get("university");
					nStuID = parameters.get("modesummary");
					nScheme = "STUDENT SCHEME";
				} else if (paymt2 == 2) {
					nWaivernumb = parameters.get("modesummary");
					nScheme = "WAIVER CASE";
				} else if (paymt2 == 3) {
					nScheme = "DELIVERY CASE";
				}
				
				nFNumber = parameters.get("rooms3");
				break;
			}
		}
		
		Encounter encounter = RegistrationWebUtils.createEncounter(patient, getRevisit(status));
		
		if (!StringUtils.isBlank(tNTriage)) {
			
			Concept triageConcept = Context.getConceptService().getConcept(InitialPatientQueueConstants.CONCEPT_NAME_TRIAGE);
			
			Concept selectedTRIAGEConcept = Context.getConceptService().getConcept(tNTriage);
			
			String selectedCategory = paymt3;
			Obs triageObs = new Obs();
			triageObs.setConcept(triageConcept);
			triageObs.setValueCoded(selectedTRIAGEConcept);
			encounter.addObs(triageObs);
			RegistrationWebUtils.sendPatientToTriageQueue(patient, selectedTRIAGEConcept, getRevisit(status),
			    selectedCategory);
		} else if (!StringUtils.isBlank(oNOpd)) {
			Concept opdConcept = Context.getConceptService().getConcept(InitialPatientQueueConstants.CONCEPT_NAME_OPD_WARD);
			Concept selectedOPDConcept = Context.getConceptService().getConcept(oNOpd);
			String selectedCategory = paymt3;
			Obs opdObs = new Obs();
			opdObs.setConcept(opdConcept);
			opdObs.setValueCoded(selectedOPDConcept);
			encounter.addObs(opdObs);
			
			RegistrationWebUtils.sendPatientToOPDQueue(patient, selectedOPDConcept, getRevisit(status), selectedCategory);
			
		} else {
			Concept specialClinicConcept = Context.getConceptService().getConcept(
			    InitialPatientQueueConstants.CONCEPT_NAME_SPECIAL_CLINIC);
			//PatientQueueService queueService = (PatientQueueService) Context.getService(PatientQueueService.class);
			Concept selectedSpecialClinicConcept = Context.getConceptService().getConcept(sNSpecial);
			String selectedCategory = paymt3;
			Obs opdObs = new Obs();
			opdObs.setConcept(specialClinicConcept);
			opdObs.setValueCoded(selectedSpecialClinicConcept);
			encounter.addObs(opdObs);
			
			RegistrationWebUtils.sendPatientToOPDQueue(patient, selectedSpecialClinicConcept, getRevisit(status),
			    selectedCategory);
			
		}
		
		// payment category and registration fee
		Concept cnrf = Context.getConceptService().getConcept(InitialPatientQueueConstants.CONCEPT_NAME_REGISTRATION_FEE);
		Concept cnp = Context.getConceptService().getConcept(InitialPatientQueueConstants.CONCEPT_NEW_PATIENT);
		Obs obsn = new Obs();
		obsn.setConcept(cnrf);
		obsn.setValueCoded(cnp);
		Double doubleVal = Double.parseDouble(parameters.get(InitialPatientQueueConstants.FORM_FIELD_REGISTRATION_FEE));
		obsn.setValueNumeric(doubleVal);
		obsn.setValueText(paymt3);
		assert paymt3 != null;
		if (paymt3.equals("Paying")) {
			obsn.setComment(nPayn);
		} else if (paymt3.equals("Non-Paying")) {
			obsn.setComment(nNotpayn);
		} else {
			obsn.setComment(nScheme);
		}
		encounter.addObs(obsn);
		
		return encounter;
	}
	
	private boolean getRevisit(int state) {
		boolean status = false;
		if (state == 2) {
			status = true;
		}
		return status;
	}
}
