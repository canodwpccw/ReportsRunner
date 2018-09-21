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
            var  obj = [{key:'REPORT_ID',value:''},{key:'LOCATION',value:''},
                {key:'TRANS_DATE',value:''},{key:'USER_ID',value:''},
                {key:'TRANS_YEAR',value:''},{key:'startdate',value:''},
                {key:'enddate',value:''},{key:'sub1_data0',value:''},
                {key:'sub1_data1',value:''},{key:'sub2_data0',value:''},{key:'sub2_data1',value:''}];
            $('.form-control').each(function (i) {
                obj[i].value=$(this).val();
            })
            $('#parameters').val(JSON.stringify(obj));
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
                    <h4 class="modal-title">Modal Heading</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <form:form id="esgenForm" modelAttribute="report" action="/generateReport" method="post">
                        <table>
                            <tr>
                                <td><form:label path="reportId">Report ID:</form:label></td>
                                <td><form:input path="reportId" type="text" class="form-control" aria-label="Small"
                                                aria-describedby="inputGroup-sizing-sm"/></td>
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
                                                aria-describedby="inputGroup-sizing-sm"/></td>
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
                                           aria-describedby="inputGroup-sizing-sm"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">SubReport 1 Data 1:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                           aria-describedby="inputGroup-sizing-sm"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">SubReport 2 Data 0:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                           aria-describedby="inputGroup-sizing-sm"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="reportId">SubReport 2 Data 1:</form:label></td>
                                <td><input type="text" class="form-control" aria-label="Small"
                                           aria-describedby="inputGroup-sizing-sm"/></td>
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
