package org.openmrs.module.patientqueuapp.page.controller;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.hospitalcore.util.ConceptAnswerComparator;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class OpdQueuePageController {
	
	public String get(@RequestParam("app") AppDescriptor appDescriptor, UiSessionContext sessionContext, PageModel model,
	        UiUtils ui, HttpSession session, PageRequest pageRequest) {
		Boolean isPriviledged = Context.hasPrivilege("Access OPD");
		if (!isPriviledged) {
			return "redirect: index.htm";
		}
		model.addAttribute("afterSelectedUrl", appDescriptor.getConfig().get("onSelectUrl").getTextValue());
		User usr = Context.getAuthenticatedUser();
		model.addAttribute("title", "OPD Queue");
		model.addAttribute("date", new Date());
		Concept opdWardConcept = Context.getConceptService().getConceptByName("OPD WARD");
		Concept specialClinicConcept = Context.getConceptService().getConceptByName("SPECIAL CLINIC");
		List<ConceptAnswer> patientList = new ArrayList<ConceptAnswer>();
		List<ConceptAnswer> opdList = (opdWardConcept != null ? new ArrayList<ConceptAnswer>(opdWardConcept.getAnswers())
		        : null);
		List<ConceptAnswer> specialClinicList = (specialClinicConcept != null ? new ArrayList<ConceptAnswer>(
		        specialClinicConcept.getAnswers()) : null);
		patientList.addAll(specialClinicList);
		patientList.addAll(opdList);
		if (CollectionUtils.isNotEmpty(patientList)) {
			Collections.sort(patientList, new ConceptAnswerComparator());
		}
		model.addAttribute("listOPD", patientList);
		return null;
	}
	
}
