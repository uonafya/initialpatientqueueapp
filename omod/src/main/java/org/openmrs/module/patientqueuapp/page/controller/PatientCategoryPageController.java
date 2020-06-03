package org.openmrs.module.patientqueuapp.page.controller;

import org.openmrs.Patient;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.module.patientqueuapp.PatientQueueConstants;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

@AppPage(PatientQueueConstants.APP_PATIENT_QUEUE)
public class PatientCategoryPageController {
	
	public void controller(@RequestParam("patientId") Patient patient, PageModel model) {
		model.addAttribute("currentPatient", patient);
		
	}
}
