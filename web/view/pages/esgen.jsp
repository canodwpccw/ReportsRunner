<%--
  Created by IntelliJ IDEA.
  User: 81101610
  Date: 21/09/2018
  Time: 8:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<jsp:include page="commons/navbar.jsp"/>
<head>
    <script src="${pageContext.request.contextPath}/webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/webjars/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.js"></script>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/webjars/bootstrap-datepicker/1.3.0/css/datepicker.css"/>
    <title>Title</title>
    <script>
        $(function () {
            $(".datepicker").datepicker({
                autoclose: true,
                todayHighlight: true
            }).datepicker('update', new Date());
        })

        function buildParam(){
            var  obj = ["{'REPORT_ID'","'LOCATION'","'TRANS_DATE'","'USER_ID'","'TRANS_YEAR'","'startdate'",
                "'enddate'","'sub1_data0'","'sub1_data1'","'sub2_data0'","'sub2_data1'"];
            $('.form-control').each(function (i) {
                obj[i]+=":'"+$(this).val()+"'";
            })
            // alert(obj+"}")
            $('#parameters').val(obj+"}");
            $('#esgenForm').submit();




        }


    </script>
    <style>
        label {
            margin-left: 20px;
        }

        #datepicker {
            width: 180px;
            margin: 0 20px 20px 20px;
        }

        #datepicker > span:hover {
            cursor: pointer;
        }
        td{
            padding: 5px;
        }
    </style>
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
        <tr>
            <td>ESGEN008</td>
            <td></td>
            <td></td>
            <td>
                <button type="button" id="btnOpen" class="btn btn-primary" data-toggle="modal"
                        data-target="#myModal">
                    Open modal
                </button>
            </td>
        </tr>

        </tbody>
    </table>
    <div class="modal" id="myModal">
        <div class="modal-dialog">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h2 class="modal-title">MANUAL RUN</h2>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <form:form id="esgenForm" modelAttribute="report" action="${pageContext.request.contextPath}/generateReport" method="post">
                        <table>
                            <tr>
                                <td><form:label path="reportId">Report ID:</form:label></td>
                                <td><form:input path="reportId" type="text" class="form-control" aria-label="Small"
                                                aria-describedby="inputGroup-sizing-sm" value="ESGEN008"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">Location:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                                aria-describedby="inputGroup-sizing-sm"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">Transaction Date:</form:label></td>
                                <td>
                                    <div id="" class="input-group date datepicker" data-date-format="mm-dd-yyyy">
                                        <input class="form-control" type="text" />
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">User ID:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                                aria-describedby="inputGroup-sizing-sm" value="UID-1234"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">Transaction Year:</form:label></td>
                                <td>
                                    <div class="input-group date datepicker" data-date-format="mm-dd-yyyy">
                                        <input class="form-control" type="text" />
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">Start Date:</form:label></td>
                                <td>
                                    <div class="input-group date datepicker" data-date-format="mm-dd-yyyy">
                                        <input class="form-control" type="text" />
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">End Date:</form:label></td>
                                <td>
                                    <div class="input-group date datepicker" data-date-format="mm-dd-yyyy">
                                        <input class="form-control" type="text" />
                                        <span class="input-group-addon"><i
                                                class="glyphicon glyphicon-calendar"></i></span>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">SubReport 1 Data 0:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                           aria-describedby="inputGroup-sizing-sm" value="HKS"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">SubReport 1 Data 1:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                           aria-describedby="inputGroup-sizing-sm" value="TD-EKO"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">SubReport 2 Data 0:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                           aria-describedby="inputGroup-sizing-sm" value="EKS"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">SubReport 2 Data 1:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                           aria-describedby="inputGroup-sizing-sm" value="A"/></td>
                            </tr>
                        </table>
                        <form:hidden path="parameters" id="parameters"></form:hidden>
                    </form:form>
                </div>
                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" onclick="buildParam()">Generate</button>
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
