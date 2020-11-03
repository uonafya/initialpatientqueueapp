<% ui.decorateWith("appui", "standardEmrPage", [title: "Patient Summary"]) %>
<%
    ui.includeCss("registration", "onepcssgrid.css")
    ui.includeCss("registration", "main.css")

%>

<style>
.name {
    color: #f26522;
}
</style>

<openmrs:require privilege="View Patients" otherwise="/login.htm" redirect="/module/registration/showPatientInfo.form"/>
<openmrs:globalProperty key="hospitalcore.hospitalName" defaultValue="ddu" var="hospitalName"/>
<div class="onepcssgrid-1000">

    ${ui.includeFragment("registration", "js_css")}

    <%
        if(revisit) {
    %>
    ${ui.includeFragment("registration", "patientInfoPage")}
    <%
        }else {
    %>
    ${ui.includeFragment("registration", "patientInfoForm")}
    <%
        }
    %>

</div>