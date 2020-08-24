/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.patientqueuapp.fragment.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.module.kenyacore.form.FormManager;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.module.patientqueuapp.PatientQueueConstants;
import org.openmrs.module.patientqueuapp.web.controller.utils.RegistrationWebUtils;
import org.openmrs.ui.framework.annotation.FragmentParam;
import org.openmrs.ui.framework.annotation.SpringBean;
import org.openmrs.ui.framework.fragment.FragmentModel;
import org.openmrs.ui.framework.page.PageRequest;

/**
 * 4 Fragment to process the queueing information for a patient return processed patients
 */
public class QueuePatientFragmentController {
	
	private static Log logger = LogFactory.getLog(QueuePatientFragmentController.class);
	
	public void controller(@FragmentParam("patient") Patient patient, FragmentModel model, PageRequest request,
	        @SpringBean FormManager formManager, @SpringBean KenyaUiUtils kenyaUi) {
		
		model.addAttribute("TRIAGE", RegistrationWebUtils.getSubConcepts(PatientQueueConstants.CONCEPT_NAME_TRIAGE));
		model.addAttribute("OPDs", RegistrationWebUtils.getSubConcepts(PatientQueueConstants.CONCEPT_NAME_OPD_WARD));
		model.addAttribute("SPECIALCLINIC",
		    RegistrationWebUtils.getSubConcepts(PatientQueueConstants.CONCEPT_NAME_SPECIAL_CLINIC));
		
	}
}
