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