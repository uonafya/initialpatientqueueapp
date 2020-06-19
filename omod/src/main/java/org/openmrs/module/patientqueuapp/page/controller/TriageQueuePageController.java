package org.openmrs.module.patientqueuapp.page.controller;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.Concept;
import org.openmrs.ConceptAnswer;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.appframework.domain.AppDescriptor;
import org.openmrs.module.kenyaui.annotation.AppPage;
import org.openmrs.ui.framework.UiUtils;
import org.openmrs.ui.framework.page.PageModel;
import org.openmrs.ui.framework.page.PageRequest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by George Bush 15/06/2020
 */
public class TriageQueuePageController {
    public String get(
            @RequestParam("app") AppDescriptor appDescriptor,
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
            ) {
        pageRequest.getSession().setAttribute(ReferenceApplicationWebConstants.SESSION_ATTRIBUTE_REDIRECT_URL,ui.thisUrl());
        sessionContext.requireAuthentication();
        Boolean isPriviledged = Context.hasPrivilege("Access Triage");
        if(!isPriviledged){
            return "redirect: index.htm";
        }
        model.addAttribute("afterSelectedUrl", appDescriptor.getConfig().get("onSelectUrl").getTextValue());
        User usr = Context.getAuthenticatedUser();
        model.addAttribute("title", "Triage Queue");
        model.addAttribute("date", new Date());
        Concept triageConcept = Context.getConceptService().getConceptByName("TRIAGE");
        List<ConceptAnswer> list = (triageConcept != null
                ? new ArrayList<ConceptAnswer>(triageConcept.getAnswers()) : null);
        if (CollectionUtils.isNotEmpty(list)) {
            Collections.sort(list, new ConceptAnswerComparator());
        }
        model.addAttribute("listOPD", list);
        return null;
    }
}
