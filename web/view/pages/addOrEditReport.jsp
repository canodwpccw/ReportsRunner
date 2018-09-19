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
    <script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>

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
            var keyInput = new Array();
            $.ajax({
                url: "${pageContext.request.contextPath}/${action}Report/", success: function (result) {

                }
            })

            $(".keyinput").each(function (i, row) {
                var key = $("#keyInput-" + i).val();
                var value = $("#valueInput-" + i).val();
                alert(ki);
            });
        }
    </script>
    <link rel="stylesheet"
          href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
    <title>Title</title>
</head>
<body>
<div>
    <form:form id="" method="post" action="${pageContext.request.contextPath}/${action}Report" modelAttribute="report" enctype="multipart/form-data">
        <table id="formTable" class="table">
            <tbody>
            <tr>
                <td><form:label path="reportId">Report ID:</form:label></td>
                <td><form:input type="text" path="reportId"/></td>
            </tr>
            <tr>
                <td><form:label path="templateFilename">Template Location:</form:label></td>
                <td><input type="file" name="templateFilename"/></td>
            </tr>
            <tr>
                <td><form:label path="parameters">Parameters:</form:label></td>
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
                <td></td>
            </tr>
            <tr>
                <td colspan="2"><input type="button" value="Submit" onclick="submitForm()"/></td>
            </tr>
            </tbody>
        </table>
    </form:form>
</div>
</body>
</html>
