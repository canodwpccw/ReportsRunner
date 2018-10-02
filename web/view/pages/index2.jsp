<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap-datepicker/1.3.0/css/datepicker.css"/>
    <title>ReportsRunner</title>
</head>

<script>
    $(document).ready(function() {
        $('#example').dataTable({
            info: false,
            paging: false
        });
    } );
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
</style>
<body>

<jsp:include page="commons/navbar.jsp"/>
<%--<div class="container">--%>
    <div class="card">
        <div class="card-body">
            <table id="example" class="compact hover" style="width:100%;font-size:12px">
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
                <c:forEach var="report" items="${reports}">
                    <tr>
                        <td>${report.reportId}</td>
                        <td>${report.module}</td>
                        <td>${report.reportTitle}</td>
                        <td>${report.frequency}</td>
                        <td>${report.templateType}</td>
                        <td>01-Oct-2018</td>
                        <td>01-Oct-2018</td>
                        <td>Report was created.</td>
                        <td>
                            <button type="button" class="btn btn-secondary">Generate</button>
                        </td>
                        <td>${report.isActive}</td>
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
