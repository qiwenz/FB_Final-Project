<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>PubMed QA GOF</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css"
	href="css/bootstrap-theme.min.css" />

<script type="text/javascript" src="js/jquery-2.0.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#question").keyup(function() {
			$.fn.check_q("question", "submit");
		});

		$("#reset").click(function() {
			$.fn.reset("question", "submit");
		});
		$("#submit").click(function() {
			$.fn.submit("question");
		});
		$("#link button").click(function() {
			$("#display").modal("show");
		});
	});
</script>

</head>

<body>
	<div id="main" class="container-fluid">
		<br> <br>
		<div id="head" class="row">
			<div class="col-md-12" align="center">
				<img src="image/pubmed.png" alt="PubMedIcon" class="img-rounded">
			</div>
		</div>

		<div id="question" class="row" align="center">
			<div class="col-md-12 col-md-offset-2">
				<form class="form-inline" role="form">
					<div id="question" class="form-group col-md-8">
						<input type="text" class="form-control"
							placeholder="Please enter your question here">
					</div>
				</form>
			</div>
		</div>
		<div id="link" class="row" align="right">
			<div class="col-md-2 col-md-offset-8">
				<button type="button" class="btn btn-link" disabled="disabled">Recent
					Results</button>
			</div>
		</div>
		<div id="bottom" class="row" align="center">
			<div class="col-md-12" align="center">
				<button id="submit" type="button" class="btn btn-success btn-md"
					disabled="disabled" data-toggle="modal" data-target="#display">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;I
					Want Answer!!!&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>

				<button type="button" class="btn btn-default">&nbsp;&nbsp;Clear
					My Question&nbsp;&nbsp;</button>
			</div>
		</div>

		<div id="display" class="modal fade" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">&times;</button>
						<h4 class="modal-title" id="myModalLabel">Your Top Answers</h4>
					</div>
					<div class="modal-body">
						<div id="results" class="panel-group" id="accordion"></div>
					</div>
					<div class="modal-footer"></div>
				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>
</body>
</html>
