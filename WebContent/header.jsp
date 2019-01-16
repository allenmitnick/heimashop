<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->

<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
		<!-- 菜单栏 -->
		<c:if test="${empty loginUser }">
			<li><a href="${pageContext.request.contextPath }/loginUI.user">登录</a></li>
			<li><a href="${pageContext.request.contextPath }/registerUI.user">注册</a></li>
		</c:if>
			<li><a href="${pageContext.request.contextPath }/cart.jsp">购物车</a></li>
		<c:if test="${not empty loginUser }">
			欢迎：${loginUser.name }	,
			<li><a href="${pageContext.request.contextPath }/orderList.order">我的订单</a></li>
			<li><a href="${pageContext.request.contextPath }/logout.user">退出</a></li>
		</c:if>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath }">首页</a>
			</div>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="menu">
				
					<c:forEach items="${allCategory }" var="category">
						<li value="${category.cid }"><a href="#">${category.cname }</a></li>
					</c:forEach>
					
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
		</div>
	</nav>
</div>

<script>
	$(function(){
		var url = "${pageContext.request.contextPath}/allCategory.category";
		$.post(url,function(data){
			
			$.each(data,function(i,n){
				
				$("#menu").append("<li value=''><a href='${pageContext.request.contextPath}/productCategory.product?cid="+ n.cid +"'>" + n.cname + "</a></li>")
			})
			
		});
	})
</script>