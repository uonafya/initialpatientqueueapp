<% ui.decorateWith("kenyaemr", "standardEmrPage", [title: "Patient Summary"]) %>
<%
    ui.includeCss("ehrconfigs", "onepcssgrid.css")
    ui.includeCss("initialpatientqueueapp", "main.css")

%>

<style>
.ui-tabs-vertical {
    width: 55em;
}
.ui-tabs-vertical .ui-tabs-nav {
    padding: .2em .1em .2em .2em;
    float: left;
    width: 12em;
}
.ui-tabs-vertical .ui-tabs-nav li {
    clear: left;
    width: 100%;
    border-bottom-width: 1px !important;
    border-right-width: 0 !important;
    margin: 0 -1px .2em 0;
}
.ui-tabs-vertical .ui-tabs-nav li a {
    display: block;
}
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-active {
    padding-bottom: 0;
    padding-right: .1em;
    border-right-width: 1px;
}
.ui-tabs-vertical .ui-tabs-panel {
    padding: 1em;
    float: right;
    width: 45em;
}
.red-border {
    border: 1px solid #f00 !important;
}
.myh2,
.tasks-list {
    margin: 0;
    padding: 0;
    border: 0;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}
.tasks {
    font: 13px/20px 'Lucida Grande', Verdana, sans-serif;
    color: #404040;
    width: 100%;
    background: white;
    border: 1px solid #cdd3d7;
    border-radius: 4px;
    -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
    box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
}
.tasks-header {
    position: relative;
    line-height: 24px;
    padding: 7px 15px;
    color: #5d6b6c;
    text-shadow: 0 1px rgba(255, 255, 255, 0.7);
    background: #f0f1f2;
    border-bottom: 1px solid #d1d1d1;
    border-radius: 3px 3px 0 0;
    background-image: -webkit-linear-gradient(top, #f5f7fd, #e6eaec);
    background-image: -moz-linear-gradient(top, #f5f7fd, #e6eaec);
    background-image: -o-linear-gradient(top, #f5f7fd, #e6eaec);
    background-image: linear-gradient(to bottom, #f5f7fd, #e6eaec);
    -webkit-box-shadow: inset 0 1px rgba(255, 255, 255, 0.5), 0 1px rgba(0, 0, 0, 0.03);
    box-shadow: inset 0 1px rgba(255, 255, 255, 0.5), 0 1px rgba(0, 0, 0, 0.03);
}
.tasks-title {
    line-height: inherit;
    font-size: 14px;
    font-weight: bold;
    color: inherit;
}
.tasks-lists {
    position: absolute;
    top: 50%;
    right: 10px;
    margin-top: -11px;
    padding: 10px 4px;
    width: 19px;
    height: 3px;
    font: 0/0 serif;
    text-shadow: none;
    color: transparent;
}
.tasks-lists:before {
    display: block;
    height: 3px;
    background: #8c959d;
    border-radius: 1px;
    -webkit-box-shadow: 0 6px #8c959d, 0 -6px #8c959d;
    box-shadow: 0 6px #8c959d, 0 -6px #8c959d;
}
.tasks-list-item {
    display: block;
    line-height: 24px;
    padding: 5px 10px;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}
.tasks-list-item + .tasks-list-item {
    border-top: 1px solid #f0f2f3;
}
.tasks-list-cb {
    display: none;
}
.tasks-list-mark {
    position: relative;
    display: inline-block;
    vertical-align: top;
    margin-right: 0px;
    width: 16px;
    height: 20px;
    border: 2px solid #c4cbd2;
    border-radius: 12px;
}
.tasks-list-mark:before {
    content: '';
    display: none;
    position: absolute;
    top: 50%;
    left: 50%;
    margin: -5px 0 0 -6px;
    height: 4px;
    width: 8px;
    border: solid #39ca74;
    border-width: 0 0 4px 4px;
    -webkit-transform: rotate(-45deg);
    -moz-transform: rotate(-45deg);
    -ms-transform: rotate(-45deg);
    -o-transform: rotate(-45deg);
    transform: rotate(-45deg);
}
.tasks-list-cb:checked ~ .tasks-list-mark {
    border-color: #39ca74;
}
.tasks-list-cb:checked ~ .tasks-list-mark:before {
    display: block;
}
.tasks-list-desc {
    font-weight: bold;
    color: #555;
}
.tasks-list-cb:checked ~ .tasks-list-desc {
    color: #34bf6e;
}
#form-verification-x {
    color: #f00;
    cursor: pointer;
    float: right;
    margin: -10px -22px 0;
}
.form-verifier-js {
    padding: 10px 30px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -ms-box-sizing: border-box;
    box-sizing: border-box;
    box-shadow: 0 11px 5px -10px rgba(0, 0, 0, 0.3);
    border: 1px solid #F00;
    margin-bottom: 15px;
    display: none;
}
.form-verifier-js p {
    padding-top: 5px;
    padding-bottom: 0px;
    margin-bottom: 5px;
}
.form-duplicate-js {
    padding: 1px 30px 1px 30px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    -ms-box-sizing: border-box;
    box-sizing: border-box;
    box-shadow: 0 11px 5px -10px rgba(0, 0, 0, 0.3);
    border: 1px solid #F00;
    margin-bottom: 15px;
}
.form-duplicate-js p {
    padding-top: 5px;
    padding-bottom: 0px;
    margin-bottom: 5px;
}
.dashboard .info-section {
    margin: 0 5px 5px;
}
.dashboard .info-body li {
    padding-bottom: 2px;
}
.dashboard .info-body li span {
    margin-right: 10px;
}
.dashboard .info-body li small {
}
.dashboard .info-body li div {
    width: 150px;
    display: inline-block;
}
.addon {
    color: #f26522;
    display: inline-block;
    float: right;
    margin: 10px 0 0 190px;
    position: absolute;
}
a.tooltip {outline:none; }
a.tooltip strong {line-height:20px;}
a.tooltip:hover {text-decoration:none;}
a.tooltip span {
    z-index:10;
    display:none;
    padding:14px 20px!important;
    margin-left:-205px;
    width:205px;
    line-height:16px;
    position:absolute;
    color:#111;
    border:1px solid #DCA; background:#fffAF0;
}
a.tooltip span em{
    width: 20px;
    float: left;
    font-family: Times New Roman;
    font-style: italic;
}
a.tooltip:hover span{
    display:inline;
}
.callout {
    z-index:20;
    position:absolute;
    top:-12px;
    border:0;
    left:203px;
}
a.tooltip span {
    border-radius:4px;
    box-shadow: 5px 5px 8px #CCC;
}
</style>
<html>
<body>
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
            <div class="col2" align="left" style="display:inline-block; width: 150px"><b>Gender:</b></div>
            <div class="col2" align="left" style="display:inline-block; width: 150px""><span id="age"></span>${gender}
        </div>
    <div class="onerow" align="left">
            <div class="col2" align="left" style="display:inline-block; width: 150px"><b>You were Served by:</b></div>
            <div class="col2" align="left" style="display:inline-block; width: 150px""><span id="user"></span>${user}
        </div>
    </div>
</div>
<button onclick="window.print()">Print Receipt</button>
</body>
</html>