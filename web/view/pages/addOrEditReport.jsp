<%--
  Created by IntelliJ IDEA.
  User: 81101610
  Date: 18/09/2018
  Time: 11:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>

    <script>
        function addRow() {
            var rowCount = $('#paramTable>tbody:last>tr').length;
            var trString = " " +
                "<tr>" +
                    "<td>" +
                        "<input type=\"text\" id=\"keyInput-" + rowCount + "\" class=\"keyinput form-control\" aria-label=\"Small\" aria-describedby=\"inputGroup-sizing-sm\"/>\n" +
                    "</td>" +
                    "<td>" +
                        "<input type=\"text\" id=\"valueInput-" + rowCount + "\" class=\"valueinput form-control\" aria-label=\"Small\" aria-describedby=\"inputGroup-sizing-sm\"/>\n" +
                    "</td>" +
                "</tr>";
            $("#paramTable>tbody:last").append(trString);
        }

        function submitForm() {
            var params = [];
            var parameter = "[";
            $(".keyinput").each(function (i, row) {
                var key = $("#keyInput-" + i).val();
                var value = $("#valueInput-" + i).val();
                params[i]="{'key':'"+key+"','value':'"+value+"'}";
            });
            parameter+=params+"]";
            $("#parameters").val(parameter);
            $("#reportForm").submit();
        }
    </script>
    <link rel="stylesheet"
          href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
    <title>Title</title>
</head>
<body>
<div class="container-fluid">
    <form:form id="reportForm" method="post" action="${pageContext.request.contextPath}/${action}Report" modelAttribute="report" enctype="multipart/form-data">
        <table id="formTable" class="table">
            <tbody>
            <tr>
                <td><form:label path="reportId">Report ID:</form:label></td>
                <%--<td><form:input type="text" path="reportId"/></td>--%>
                <td><form:input path="reportId" type="text" class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm"/></td>
            </tr>
            <tr>
                <td><form:label path="multipartFiles">Template Location:</form:label></td>
                <td><input type="file" name="multipartFiles" multiple class="form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm"/></td>
            </tr>
            <tr>
                <td>
                    <form:label path="parameters">Parameters:</form:label>
                    <form:hidden path="parameters"/>
                </td>
                <td>
                    <table id="paramTable">
                        <tr>
                            <thead>
                            <td>
                                <form:label path="parameters">Field Name:</form:label>
                            </td>
                            <td>
                                <form:label path="parameters">Default Value</form:label>
                            </td>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <input type="text" id="keyInput-0" class="keyinput form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm"/>
                                </td>
                                <td>
                                    <input type="text" id="valueInput-0"  class="valueinput form-control" aria-label="Small" aria-describedby="inputGroup-sizing-sm"/>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="2">
                                    <input type="button" class="btn btn-primary" onclick="addRow()" value="Add Parameter"/>
                                </td>

                            </tr>
                            </tfoot>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td><form:label path="isDaily">Schedule</form:label></td>
                <td>
                <form:checkbox path="isDaily"/><form:label path="isDaily">Daily</form:label>
                <form:checkbox path="isWeekly"/><form:label path="isWeekly">Weekly</form:label>
                <form:checkbox path="isMonthly"/><form:label path="isMonthly">Monthly</form:label>
                <form:checkbox path="isYearly"/><form:label path="isYearly">Is Yearly</form:label>
                </td>
            </tr>
            <tr>
                <td><input type="button" class="btn btn-primary center-block" value="Submit" onclick="submitForm()"/></td>
                <td><input type="button" class="btn btn-default center-block" value="Clear" onclick="clearForm()"/></td>
            </tr>
            </tbody>
        </table>
    </form:form>
</div>
</body>
</html>
