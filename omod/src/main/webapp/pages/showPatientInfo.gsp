<% ui.decorateWith("kenyaemr", "standardEmrPage", [title: "Patient Summary"]) %>
<%
    ui.includeCss("ehrconfigs", "onepcssgrid.css")
    ui.includeCss("initialpatientqueueapp", "main.css")

%>

<style>
.name {
    color: #f26522;
}
</style>

<div class="onepcssgrid-1000">

    ${ui.includeFragment("initialpatientqueueapp", "js_css")}

    <div id="printDiv">
        <center>
            <center>
                <img width="60" height="60" align="center" title="OpenMRS" alt="OpenMRS"
                     src="${ui.resourceLink('ehrinventoryapp', 'images/kenya_logo.bmp')}">
            </center>
        </center>

        <h3><center><u><b>${location}</b></u></center></h3>
        <h4 style="font-size: 1.4em;"><center><b>Registration Receipt</b></center></h4>
        <div style="display: block;	margin-left: auto; margin-right: auto; width: 350px">
            <div>
                <div class="col2" align="left" style="display:inline-block; width: 150px">
                    <b>Receipt Date:</b>
                </div>

                <div class="col2" align="left" style="display: inline-block; width: 150px;">
                    <span>${receiptDate}</span>
                </div>
            </div>

            <div class="onerow" align="left">
                <div class="col2" align="left" style="display:inline-block; width: 150px"><b>Name:</b></div>
                <div class="col2" align="left" style="display:inline-block; width: 150px"><span id="patientName">${names}</span></div>
            </div>

            <div class="onerow" align="left">
                <div class="col2" align="left" style="display:inline-block; width: 150px"><b>Patient ID:</b></div>
                <div class="col2" align="left" style="display:inline-block; width: 150px""><span id="identifier">${patientId}</span></div>
        </div>
        <div class="onerow" align="left">
            <div class="col2" align="left" style="display:inline-block; width: 150px"><b>Age:</b></div>
            <div class="col2" align="left" style="display:inline-block; width: 150px""><span id="age"></span>${age}
        </div>
    <div class="onerow" align="left">
            <div class="col2" align="left" style="display:inline-block; width: 150px"><b>You were Served by:</b></div>
            <div class="col2" align="left" style="display:inline-block; width: 150px""><span id="user"></span>${user}
        </div>
    </div>

</div>