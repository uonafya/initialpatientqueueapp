package org.openmrs.module.initialpatientqueueapp.util;

public class RegistrationWebUtils {

    /**
	 * Optimize the request's parameters
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> optimizeParameters(HttpServletRequest request) {
		Map<String, String> parameters = new HashMap<String, String>();
		for (@SuppressWarnings("rawtypes")
		Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
			String parameterName = (String) e.nextElement();
			String[] values = request.getParameterValues(parameterName);
			String value = StringUtils.join(values, ',');
			parameters.put(parameterName, value);
		}
		return parameters;
	}
	
	/**
	 * Get the list of concepts which are answered for a concept
	 * 
	 * @param conceptName
	 * @return
	 */
	public static String getSubConcepts(String conceptName) {
		Concept opdward = Context.getConceptService().getConcept(conceptName);
		StringBuilder sb = new StringBuilder();
		for (ConceptAnswer ca : opdward.getAnswers()) {
			sb.append(ca.getAnswerConcept().getConceptId() + "," + ca.getAnswerConcept().getName().getName() + "|");
		}
		return sb.toString();
	}
	
	public static String getSubConceptsWithName(String conceptName) {
		Concept concept = Context.getConceptService().getConcept(conceptName);
		StringBuilder sb = new StringBuilder();
		for (ConceptAnswer ca : concept.getAnswers()) {
			sb.append(ca.getAnswerConcept().getName().getName() + "," + ca.getAnswerConcept().getName().getName() + "|");
		}
		return sb.toString();
    }
    
    /**
	 * Send patient for OPD Queue
	 * 
	 * @param patient
	 * @param selectedOPDConcept
	 * @param revisit
	 */
	public static void sendPatientToOPDQueue(Patient patient, Concept selectedOPDConcept, boolean revisit, String selectedCategory) {
		Concept visitStatus = null;
		if (!revisit) {
			visitStatus = Context.getConceptService().getConcept("NEW PATIENT");
		} else {
			visitStatus = Context.getConceptService().getConcept("REVISIT");
		}
		
		OpdPatientQueue queue = Context.getService(PatientQueueService.class).getOpdPatientQueue(
		    patient.getPatientIdentifier().getIdentifier(), selectedOPDConcept.getConceptId());
		if (queue == null) {
			queue = new OpdPatientQueue();
			queue.setUser(Context.getAuthenticatedUser());
			queue.setPatient(patient);
			queue.setCreatedOn(new Date());
			queue.setBirthDate(patient.getBirthdate());
			queue.setPatientIdentifier(patient.getPatientIdentifier().getIdentifier());
			queue.setOpdConcept(selectedOPDConcept);
			queue.setOpdConceptName(selectedOPDConcept.getName().getName());
			if(null!=patient.getMiddleName())
			{
				queue.setPatientName( patient.getGivenName() + " " + patient.getFamilyName() + " " + patient.getMiddleName());
			}
			else
			{
				queue.setPatientName( patient.getGivenName() + " " + patient.getFamilyName());
			}
			//queue.setReferralConcept(referralConcept);
			//queue.setReferralConceptName(referralConcept.getName().getName());
			queue.setSex(patient.getGender());
			queue.setCategory(selectedCategory);
			queue.setVisitStatus(visitStatus.getName().getName());
			PatientQueueService queueService = Context.getService(PatientQueueService.class);
			queueService.saveOpdPatientQueue(queue);
			
		}
		
	}
	
	public static void sendPatientToTriageQueue(Patient patient, Concept selectedTriageConcept, boolean revisit, String selectedCategory) {
		Concept visitStatus = null;
		if (!revisit) {
			visitStatus = Context.getConceptService().getConcept("NEW PATIENT");
		} else {
			visitStatus = Context.getConceptService().getConcept("REVISIT");
		}
		
		TriagePatientQueue queue = Context.getService(PatientQueueService.class).getTriagePatientQueue(
		    patient.getPatientIdentifier().getIdentifier(), selectedTriageConcept.getConceptId());
		if (queue == null) {
			queue = new TriagePatientQueue();
			queue.setUser(Context.getAuthenticatedUser());
			queue.setPatient(patient);
			queue.setCreatedOn(new Date());
			queue.setBirthDate(patient.getBirthdate());
			queue.setPatientIdentifier(patient.getPatientIdentifier().getIdentifier());
			queue.setTriageConcept(selectedTriageConcept);
			queue.setTriageConceptName(selectedTriageConcept.getName().getName());
			if(null!=patient.getMiddleName())
			{
				queue.setPatientName( patient.getGivenName() + " " + patient.getFamilyName() + " " + patient.getMiddleName());
			}
			else
			{
				queue.setPatientName( patient.getGivenName() + " " + patient.getFamilyName());
			}
			//queue.setReferralConcept(referralConcept);
			//queue.setReferralConceptName(referralConcept.getName().getName());
			queue.setSex(patient.getGender());
			queue.setCategory(selectedCategory);
			queue.setVisitStatus(visitStatus.getName().getName());
			PatientQueueService queueService = Context.getService(PatientQueueService.class);
			queueService.saveTriagePatientQueue(queue);
			
		}
		
	}
}
