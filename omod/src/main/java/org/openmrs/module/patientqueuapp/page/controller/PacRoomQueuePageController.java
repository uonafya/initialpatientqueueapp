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
 * Created by George Bush 15/06/2020
 */
public class PacRoomQueuePageController {
    private static final String PAC_ROOM_CONCEPT_UUID = "to be generated";
    public String get(
            UiSessionContext sessionContext,
            PageModel model,
            HttpSession session,
            PageRequest pageRequest,
            UiUtils ui
    ) {
        pageRequest.getSession().setAttribute(ReferenceApplicationWebConstants.SESSION_ATTRIBUTE_REDIRECT_URL,ui.thisUrl());
        sessionContext.requireAuthentication();
        Concept maternityPacRoomConcept = Context.getConceptService().getConceptByUuid(PAC_ROOM_CONCEPT_UUID);
        Integer maternityPacRoomConceptId = maternityPacRoomConcept.getConceptId();
        model.addAttribute("maternityPacRoomConceptId",maternityPacRoomConceptId);
        model.addAttribute("date", new Date());
        return null;
    }
}