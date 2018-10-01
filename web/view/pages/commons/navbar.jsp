<%--
  Created by IntelliJ IDEA.
  User: 81101610
  Date: 25/09/2018
  Time: 1:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <script>
        $('li > a').click(function() {
            $('li').removeClass();
            $(this).parent().addClass('active');
        });
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Reports Runner</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="${pageContext.request.contextPath}/">Reports</a></li>
            <li><a href="${pageContext.request.contextPath}/addReport">Add Report</a></li>
            <li><a href="${pageContext.request.contextPath}/index">Test Manual Run</a></li>
        </ul>
    </div>
</nav>
</body>
</html>
