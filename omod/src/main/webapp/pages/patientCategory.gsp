<%
    ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="clear"></div>
<div id="content" class="container">
    <div class="dashboard clear"></div>

        <div class="info-header">
            <h3>Patient Category Information</h3>
            ${ui.includeFragment("patientqueuapp", "patientCategoryInfo", [patient: currentPatient])}
        </div>
</div>