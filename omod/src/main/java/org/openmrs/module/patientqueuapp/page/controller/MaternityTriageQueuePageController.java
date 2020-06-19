package org.openmrs.module.patientqueuapp.page.controller;

import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 *Created by George Bush 15/06/2020
 */
public class MaternityTriageQueuePageController {
    private static final String MATERNITY_TRIAGE_CONCEPT_UUID = "to be generated";
    public String get(
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
        pageRequest.getSession().setAttribute(ReferenceApplicationWebConstants.SESSION_ATTRIBUTE_REDIRECT_URL,ui.thisUrl());
        sessionContext.requireAuthentication();
        Boolean isPriviledged = Context.hasPrivilege("Access Maternity Triage");
        if(!isPriviledged){
            return "redirect: index.htm";
        }
        Concept maternityConcept = Context.getConceptService().getConceptByUuid(MATERNITY_TRIAGE_CONCEPT_UUID);
        Integer maternityConceptId = maternityConcept.getConceptId();
        model.addAttribute("maternityConceptId",maternityConceptId);
        model.addAttribute("date", new Date());
        return null;
    }
}
