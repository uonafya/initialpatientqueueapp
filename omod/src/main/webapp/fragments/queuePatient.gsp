<%
    ui.decorateWith("kenyaui", "panel", [ heading: "Queue Patient" ])
%>
<script type="text/javascript">
    var MODEL,
        arrey,
        alley,
        age,
        emrMessages = {};

    var oRegX = /^[a-zA-Z-` ]*\$/;
    emrMessages["requiredField"] = "Required";
    jq(document).ready(function () {
        MODEL = {
            TRIAGE: "${TRIAGE}",
            OPDs: "${OPDs}",
            SPECIALCLINIC: "${SPECIALCLINIC}"
        }
    });
</script>

<h2>Room to Visit</h2>
<div class="onerow" style="margin-top:10px;">
    <div class="col4">
        <label for="rooms1" id="froom1" style="margin:0px;">Room to Visit<span>*</span></label>
    </div>

    <div class="col4">
        <label for="rooms2" id="froom2" style="margin:0px;">Room Type<span>*</span></label>
    </div>

    <div class="col4 last">
        <label for="rooms3" id="froom3" style="margin:0px;">File Number</label>
    </div>
</div>
<div class="onerow">
    <div class="col4">
        <span class="select-arrow" style="width: 100%">
            <field>
                <select id="rooms1" name="rooms1" onchange="LoadRoomsTypes();"
                        class="required form-combo1">
                    <option value="">Select Room</option>
                    <option value="1">TRIAGE ROOM</option>
                    <option value="2">OPD ROOM</option>
                    <option value="3">SPECIAL CLINIC</option>
                </select>
            </field>
        </span>
    </div>

    <div class="col4">
        <span class="select-arrow" style="width: 100%">
            <field>
                <select id="rooms2" name="rooms2" class="required form-combo1">
                </select>
            </field>
        </span>
    </div>

    <div class="col4 last">
        <field><input type="text" id="rooms3" name="rooms3" value="N/A" placeholder="FILE NUMBER"
                      readonly=""/></field>
    </div>
    <div>
        <a class="button confirm" onclick="PAGE.submit();"
           style="float:right; display:inline-block; margin-left: 5px;">
            <span>QUEUE PATIENT</span>
        </a>
    </div>
</div>
