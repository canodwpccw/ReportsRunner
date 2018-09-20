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
                "<tr>\n" +
                "<td>\n" +
                "<input type=\"text\" id=\"keyInput-" + rowCount + "\" class=\"keyinput\"/>\n" +
                "</td>\n" +
                "<td>\n" +
                "<input type=\"text\" id=\"valueInput-" + rowCount + "\" class=\"valueinput\"/>\n" +
                "</td>\n" +
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
<div>
    <form:form id="reportForm" method="post" action="${pageContext.request.contextPath}/${action}Report" modelAttribute="report" enctype="multipart/form-data">
        <table id="formTable" class="table">
            <tbody>
            <tr>
                <td><form:label path="reportId">Report ID:</form:label></td>
                <td><form:input type="text" path="reportId"/></td>
            </tr>
            <tr>
                <td><form:label path="multipartFiles">Template Location:</form:label></td>
                <td><input type="file" name="multipartFiles" multiple/></td>
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
                                Field Name:
                            </td>
                            <td>
                                Default Value
                            </td>
                            </thead>
                            <tbody>
                            <tr>
                                <td>
                                    <input type="text" id="keyInput-0" class="keyinput"/>
                                </td>
                                <td>
                                    <input type="text" id="valueInput-0" class="valueinput"/>
                                </td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="2">
                                    <input type="button" onclick="addRow()" value="Add Parameter"/>
                                </td>
                            </tr>
                            </tfoot>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td><form:label path="isDaily">Is Daily?</form:label></td>
                <td><form:checkbox path="isDaily"/></td>
            </tr>
            <tr>
                <td><form:label path="isWeekly">Is Weekly?</form:label></td>
                <td><form:checkbox path="isWeekly"/></td>
            </tr>
            <tr>
                <td><form:label path="isMonthly">Is Monthly?</form:label></td>
                <td><form:checkbox path="isMonthly"/></td>
            </tr>
            <tr>
                <td><form:label path="isYearly">Is Yearly?</form:label></td>
                <td><form:checkbox path="isYearly"/></td>
            </tr>
            <tr>
                <td colspan="2"><input type="button" value="Submit" onclick="submitForm()"/></td>
                <%--<td colspan="2"><input type="submit" value="Submit"/></td>--%>
            </tr>
            </tbody>
        </table>
    </form:form>
</div>
</body>
</html>
