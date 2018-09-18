<%--
  Created by IntelliJ IDEA.
  User: 81101610
  Date: 13/09/2018
  Time: 8:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
    <link rel="stylesheet"
          href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
    <title>Title</title>
    <script>
        $(document).ready(function () {
            var obj;
        })

        function openModal(id) {
            $(".modal-body div").remove();
            $.ajax({
                url: "${pageContext.request.contextPath}/getReportById/" + id, success: function (result) {
                    obj = JSON.parse(JSON.stringify(result));
                    $(".modal-body").append("<div><p>" + createTable(obj)+ "</p></div>");
                    $(".modal-body").append("</div>");
                }
            })
        }

        function createTable(obj) {
            var content = "<table>";
            var tr = "<tr><td>Report ID: </td><td>"+obj.reportId+"</td></tr>"
            content = tr+"</table>"
            return content;
        }
    </script>
</head>
<body>
<div><br/>
    <table class="table table-bordered">
        <thead>
        <tr>
            <td>Report ID</td>
            <td>Status</td>
            <td></td>
            <td></td>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="report" items="${reports}">
            <tr>
                <td>${report.reportId}</td>
                <td></td>
                <td></td>
                <td>
                    <button type="button" id="btnOpen" class="btn btn-primary" data-toggle="modal"
                            data-target="#myModal"
                            onclick="openModal(${report.id})">
                        Open modal
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="modal" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">Modal Heading</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="col-md-12">
                        <p>Test</p>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>

            </div>
        </div>
    </div>

    <div class="alert alert-success">
        <strong>Success!</strong> It is working as we expected.
    </div>
</div>
</body>
</html>
