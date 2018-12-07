<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.3.1/jquery.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.1.3/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/web-resource/css/ReportsRunner.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/web-resource/css/font-awesome.min.css"/>
    <script>
        $(function () {
            $('input[type="file"]').change(function (e) {
                var fileName = e.target.files[0].name;
                var fileStr = fileName.split(".");
                $("#reportId").val(fileStr[0]);
                $("#templateType").val(fileStr[1]);
            });
        });

        function addRow() {
            var rowCount = $('#paramTable>tbody:last>tr').length;
            var trString = "" +
                "<tr>" +
                "<td class='td-param'>" +
                "<input type=\"text\" id=\"keyInput-" + rowCount + "\" class=\"keyinput form-control\" aria-label=\"Small\" aria-describedby=\"inputGroup-sizing-sm\"/>" +
                "</td>" +
                "<td class='td-param'>" +
                "<input type=\"text\" id=\"valueInput-" + rowCount + "\" class=\"valueinput form-control\" aria-label=\"Small\" aria-describedby=\"inputGroup-sizing-sm\"/>" +
                "</td>" +
                "</tr>";
            $("#paramTable>tbody:last").append(trString);
            $("#paramHead").show();
        }


        function submitForm() {
            $('#modal-builder').modal({backdrop: 'static',keyboard: true});
            $(".hdControl").prop('disabled',status);
            var params = [];
            var parameter ="";
            $(".keyinput").each(function (i, row) {
                var key = $("#keyInput-" + i).val();
                var value = $("#valueInput-" + i).val();
                params[i] = "'" + key + "':'" + value + "'";
            });
            parameter = ($(".keyinput").length===0)?"{}": "{" +params + "}";
            $("#parameters").val(parameter);
            $("#reportForm").submit();
        }

        function clearForm() {
            $("#reportForm input[type=text]").val('');
        }
    </script>
</head>

<style>
    .keyinput{
        margin: 5px;
    }
    .td-param{
        padding:5px;
    }

</style>
<body>
<jsp:include page="commons/navbar.jsp"/>
<div class="card">
    <div class="card-body">
        <div class="container-fluid">
    <form:form id="reportForm" method="post" action="${pageContext.request.contextPath}/${action}Report"
               modelAttribute="report" enctype="multipart/form-data">
        <div class="row">
        <div class="col-sm-3"></div>
        <div class="col-sm-6">
            <table id="formTable" class='table table-bordered'>
                <tbody>
                <tr>
                    <td><form:label path="reportId">Report ID:</form:label></td>
                        <%--<td><form:input type="text" path="reportId"/></td>--%>
                    <td><form:input path="reportId" type="text" class="form-control" aria-label="Small"
                                    aria-describedby="inputGroup-sizing-sm" readonly="true"/></td>
                </tr>
                <tr>
                    <td><form:label path="templateType">Report ID:</form:label></td>
                    <td><form:input path="templateType" type="text" class="form-control" aria-label="Small"
                                    aria-describedby="inputGroup-sizing-sm" readonly="true"/></td>
                </tr>
                <tr>
                    <td><form:label path="reportTitle">Title:</form:label></td>
                    <td><form:input path="reportTitle" type="text" class="form-control" aria-label="Small"
                                    aria-describedby="inputGroup-sizing-sm"/>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="module">Module:</form:label></td>
                    <td><form:input path="module" type="text" class="form-control" aria-label="Small"
                                    aria-describedby="inputGroup-sizing-sm" value="eServices2"/>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="multipartFiles">Template Location:</form:label></td>
                    <td><input type="file" name="multipartFiles" multiple class="form-control" aria-label="Small"
                               aria-describedby="inputGroup-sizing-sm"/></td>
                </tr>
                <tr>
                    <td>
                        <form:label path="parameters">Parameters:</form:label>
                        <form:hidden path="parameters"/>
                    </td>
                    <td>
                        <table id="paramTable">
                            <tr>
                                <thead id="paramHead" style="display:none">
                                <td>
                                    <form:label path="parameters">Field Name:</form:label>
                                </td>
                                <td>
                                    <form:label path="parameters">Default Value</form:label>
                                </td>
                                </thead>
                                <tbody>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <td colspan="1"></td>
                                    <td colspan="1"><input type="button" class="btn btn-primary" onclick="addRow()"value="Add Parameter" style="float:right"/></td>
                                </tr>
                                </tfoot>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>Frequency :</td>
                    <td><form:radiobutton path="frequency" value="daily" cssStyle="margin-left:15px;margin-right:5px;"/>daily
                        <form:radiobutton path="frequency" value="monthly" cssStyle="margin-left:15px;margin-right:5px;"/>monthly
                        <form:radiobutton path="frequency" value="quarterly" cssStyle="margin-left:15px;margin-right:5px;"/>quarterly
                        <form:radiobutton path="frequency" value="yearly" cssStyle="margin-left:15px;margin-right:5px;"/>yearly
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="button" class="btn btn-primary center-block" value="Submit" onclick="submitForm()"/>
                    </td>
                    <td><input type="button" class="btn btn-default center-block" value="Clear" onclick="clearForm()"/></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-sm-3"></div>
        </div>
    </form:form>
</div>
    </div>
</div>



<div class="modal" id="modal-builder" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content" style="padding-top:25px">
            <center><h3 id="mdl_txt">Saving...</h3></center>
            <center><i class="fa fa-refresh fa-spin fa-3x fa-fw"></i></center>
            <br/><br/>
        </div>
    </div>
</div>
</body>
</html>
