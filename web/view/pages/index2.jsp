<%@ page import="com.google.gson.GsonBuilder" %>
<%@ page import="com.google.gson.Gson" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.3.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/popper.js/1.14.3/umd/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/datatables/1.10.19/css/jquery.dataTables.min.css"/>
    <script src="${pageContext.request.contextPath}/webjars/datatables/1.10.19/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap-datepicker/1.3.0/css/datepicker.css"/>
    <title>ReportsRunner</title>
</head>

<script>
    var reportsArray = [];
    var dailyStartdate = '${datesInStr.get("dailyStartdate")}';
    var dailyEnddate = '${datesInStr.get("dailyEnddate")}';
    var monthlyStartdate = '${datesInStr.get("monthlyStartdate")}';
    var monthlyEnddate = '${datesInStr.get("monthlyEnddate")}';
    var yearlyStartdate = '${datesInStr.get("yearlyStartdate")}';
    var yearlyEnddate = '${datesInStr.get("yearlyEnddate")}';
    $(document).ready(function() {
        $('#reportDatatable').dataTable({
            info: false,
            paging: false
        });
        toJSONArray();
    } );

    function transferToModal(reportId){
        $("#mdl_rep_id").html(reportsArray[reportId].reportId);
        $("#mdl_rep_title").html(reportsArray[reportId].reportTitle);
        var startdate="";
        var enddate="";
        var frequency = reportsArray[reportId].frequency;
        if(frequency==="daily"){
            startdate=dailyStartdate;
            enddate=dailyEnddate;
        }else if(frequency==="monthly"){
            startdate=monthlyStartdate;
            enddate=monthlyEnddate;
        }else if(frequency==="yearly"){
            startdate=yearlyStartdate;
            enddate=yearlyEnddate;
        }


        var parameters = JSON.parse(reportsArray[reportId].parameters.replace(/'/gi, '"'));
        var parameterHtml ="";
        parameterHtml+="<table class='table table-borderless'>";
        parameterHtml+= datepickerParam("startdate",startdate);
        parameterHtml+= datepickerParam("enddate",enddate);

        $.each(parameters, function( key, value ) {
            parameterHtml+="<tr>";
            parameterHtml+="<td class='param_key'>"+key+"</td>";
            parameterHtml+="<td>";
            parameterHtml+='<input type="text" class="param_value form-control" value="'+value+'">';
            parameterHtml+= "</td>";
            parameterHtml+="</tr>";
        });
        parameterHtml+="</table>";
        $("#mdl_params").html(parameterHtml);
        datePickerInit();
    }
    function toJSONArray(){
        <c:forEach var="rep" items="${reports}">
        var reportObj ={};
        reportObj.id = ${rep.id};
        reportObj.reportId = "${rep.reportId}";
        reportObj.reportTitle = "${rep.reportTitle}";
        reportObj.parameters = "${rep.parameters}";
        reportObj.frequency = "${rep.frequency}";
        reportsArray["${rep.reportId}"] = reportObj;
        </c:forEach>
    }
    function datepickerParam(key,value){
        var html="";
        html+="<tr>";
        html+="<td class='param_key'>"+key+"</td>";
        html+='<td>';
        html+=     '<div id="" class="input-group date datepicker" data-date-format="yyyymmdd" style="padding:0">';
        html+=         '<input class="form-control param_value" type="text" value="'+value+'"/>';
        html+=         '<span class="input-group-addon"><iclass="glyphicon glyphicon-calendar"></i></span>';
        html+=     '</div>';
        html+='</td>';
        html+="</tr>";
        return html;
    }

    function datePickerInit(){
        $(".datepicker").datepicker({
            autoclose: true,
            todayHighlight: true
        }).datepicker();
    }
    function runReport(){
        var reportId = $("#mdl_rep_id").text();
        var report ={};
        report.id = reportsArray[reportId].id;
        report.parameters = prepareParametersInJSON();
        alert("SENDING... " + JSON.stringify(report));
        $("#jsonStr").val(JSON.stringify(report));
        // $("#jsonStr").val(JSON.stringify(report));
        window.open("","newWindow");
        $("#reportForm").submit();
    <%--$.ajax({--%>
            <%--contentType : 'application/json; charset=utf-8',--%>
            <%--dataType : 'json',--%>
            <%--type: 'POST',--%>
            <%--url: '${pageContext.request.contextPath}/testCreatePDF',--%>
            <%--data: JSON.stringify(report),--%>
            <%--success: function (data,xhr) {--%>
                <%--var win = window.open();--%>
                <%--win.document.write(data);--%>
            <%--},error: function (data,textStatus,xhr) {--%>
                <%--alert("error "+textStatus+" "+ xhr.status);--%>
            <%--},--%>
            <%--async:true--%>
        <%--});--%>
    }


    function prepareParametersInJSON(){
        var parametersObj ={};
        var keys=[];
        var values=[];
        $(".param_key").each(function(i, obj) {
            keys.push($(this).html());
        });
        $(".param_value").each(function(i, obj) {
            values.push($(this).val());
        });
        $.each(keys, function( i, value ) {
            parametersObj[value]=values[i];
        });
        var parameters = JSON.stringify(parametersObj).replace(/"/gi, "'");
        return parameters;
    }



</script>
<style>
    table.dataTable tbody tr {
        background-color: #fee6d3;
    }
    table.dataTable.hover tbody tr:hover, table.dataTable.display tbody tr:hover {
        background-color: #efc8a6;
    }

    element.style {
    }
    .btn:not(:disabled):not(.disabled) {
        cursor: pointer;
    }
    [type=reset], [type=submit], button, html [type=button] {
        -webkit-appearance: button;
    }
    .btn {
        display: inline-block;
        font-weight: 400;
        text-align: center;
        white-space: nowrap;
        vertical-align: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        border: 1px solid transparent;
        padding: 4px 8px;
        font-size: 12px;
        line-height: 1.5;
        border-radius: .25rem;
        transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    }
    .form-control {
        display: block;
        width: 100%;
        padding: 1px 5px;
        height: 25px;
        font-size: 1rem;
        line-height: 1.5;
        color: #495057;
        background-color: #fff;
        background-clip: padding-box;
        border: 1px solid #ced4da;
        border-radius: .25rem;
        transition: border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    }
    .param_key{
        font-weight: bold;
    }
    body {
        margin: 0;
        font-family: -apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif,"Apple Color Emoji","Segoe UI Emoji","Segoe UI Symbol","Noto Color Emoji";
        font-size: 12px;
        font-weight: 400;
        line-height: 1.5;
        color: #212529;
        text-align: left;
        background-color: #fff;
    }
</style>
<body>

<jsp:include page="commons/navbar.jsp"/>
<%--<div class="container">--%>
<div class="card">
    <div class="card-body">
        <table id="reportDatatable" class="compact hover" style="width:100%;font-size:12px">
            <thead>
            <tr>
                <th bgcolor="#ffaf45">ReportID</th>
                <th bgcolor="#ffaf45">Module</th>
                <th bgcolor="#ffaf45">Report Title</th>
                <th bgcolor="#ffaf45">Frequency</th>
                <th bgcolor="#ffaf45">Template</th>
                <th bgcolor="#ffaf45">Last Run</th>
                <th bgcolor="#ffaf45">Next Run</th>
                <th bgcolor="#ffaf45">Last Result</th>
                <th bgcolor="#ffaf45">Manual Run</th>
                <th bgcolor="#ffaf45">Activate</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="rep" items="${reports}">
                <tr id="tr-${rep.id}">
                    <td>
                        <b>${rep.reportId}</b>
                    </td>
                    <td>${rep.module}</td>
                    <td>${rep.reportTitle}</td>
                    <td>${rep.frequency}</td>
                    <td>${rep.templateType}</td>
                    <td>01-Oct-2018</td>
                    <td>01-Oct-2018</td>
                    <td>Report was created.</td>
                    <td>
                        <button type="button" class="btn btn-secondary"
                                data-toggle="modal" data-target="#modalReport"
                                onclick="transferToModal('${rep.reportId}')">
                            Generate
                        </button>
                    </td>
                    <td>${rep.isActive}</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <th bgcolor="#ffaf45">ReportID</th>
                <th bgcolor="#ffaf45">Module</th>
                <th bgcolor="#ffaf45">Report Title</th>
                <th bgcolor="#ffaf45">Frequency</th>
                <th bgcolor="#ffaf45">Template</th>
                <th bgcolor="#ffaf45">Last Run</th>
                <th bgcolor="#ffaf45">Next Run</th>
                <th bgcolor="#ffaf45">Last Result</th>
                <th bgcolor="#ffaf45">Manual Run</th>
                <th bgcolor="#ffaf45">Activate</th>
            </tr>
            </tfoot>
        </table>
    </div>
</div>
<%--</div>--%>
</body>
</html>



<!-- Modal -->
<div class="modal fade" id="modalReport" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="mdl_rep_id"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p id="mdl_rep_title" style="font-size:12px;font-weight:bold"></p>
                <div id="mdl_params">
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="runReport()">Run</button>
                <form:form id="reportForm" action="${pageContext.request.contextPath}/getPdf" method="post" modelAttribute="hiddenModelBean" target="newWindow">
                    <form:hidden path="hiddenValue" id="jsonStr"/>
                </form:form>
            </div>
        </div>
    </div>
</div>
