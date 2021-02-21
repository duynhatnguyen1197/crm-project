<%@page import="cycbersoft.java10.util.Path"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>404 - không tìm thấy trang</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<%= request.getContextPath() %>/static/css/bootstrap.min.css">
    <link rel="stylesheet" href='<%= request.getContextPath() %>/static/css/style.css'>
</head>

<body>
	<div class="page-wrap d-flex flex-row align-items-center pt-5 " >
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-12 text-center">
                <span class="display-1 d-block">404</span>
               
                <div class="mb-4 lead">The page you are looking for was not found.</div>
                <a href="<c:url value="<%=Path.HOME %>" />" class="btn btn-link">Back to Home</a>
            </div>
        </div>
    </div>
</div>
	<script src="<%= request.getContextPath() %>/static/js/jquery.slim.min.js"></script>
	<script src="<%= request.getContextPath() %>/static/js/popper.min.js"></script>
	<script src="<%= request.getContextPath() %>/static/js/bootstrap.min.js"></script>
</body>
</html>