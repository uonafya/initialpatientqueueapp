package org.openmrs.module.patientqueuapp.metadata;

import org.openmrs.module.metadatadeploy.bundle.AbstractMetadataBundle;
import org.openmrs.module.metadatadeploy.bundle.Requires;
import org.springframework.stereotype.Component;

import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.idSet;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.privilege;
import static org.openmrs.module.metadatadeploy.bundle.CoreConstructors.role;

/**
 * Implementation of access control to the app.
 */
@Component
@Requires(org.openmrs.module.kenyaemr.metadata.SecurityMetadata.class)
public class PatientQueueMetadata extends AbstractMetadataBundle {
	
	public static class _Privilege {
		
		public static final String APP_PQ_MODULE_APP = "App: patientqueuapp.queue";
	}
	
	public static final class _Role {
		
		public static final String APPLICATION_PQ_MODULE = "Patient Queueing Module";
	}
	
	/**
	 * @see AbstractMetadataBundle#install()
	 */
	@Override
	public void install() {
		
		install(privilege(_Privilege.APP_PQ_MODULE_APP, "Able to access Key Patient Queue module features"));
		install(role(_Role.APPLICATION_PQ_MODULE, "Can access Key patient queue module App",
		    idSet(org.openmrs.module.kenyaemr.metadata.SecurityMetadata._Role.API_PRIVILEGES_VIEW_AND_EDIT),
		    idSet(_Privilege.APP_PQ_MODULE_APP)));
	}
}
