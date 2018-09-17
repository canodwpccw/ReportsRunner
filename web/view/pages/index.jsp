<%--
  Created by IntelliJ IDEA.
  User: 81101610
  Date: 13/09/2018
  Time: 8:41 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
    <link rel="stylesheet"
          href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css"/>
    <title>Title</title>
    <script>
        $(document).ready(function(){
            // $(".modal-body").appe
        })
        function openModal(){
            $(".modal-body div").remove();
            $(".modal-body").append("<p>TEST STRING</p>")
        }
    </script>
</head>
<body>
<div class="container"><br/>
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
            <td>ESASE202</td>
            <td></td>
            <td></td>
            <td>
                <button type="button" id="btnOpen" class="btn btn-primary" data-toggle="modal" data-target="#myModal" onclick="openModal()">
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
