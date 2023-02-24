<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Metric Automation</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css">
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="#"> <img
			src="${pageContext.request.contextPath}${logo}" width="100" height="30"
			class="d-inline-block align-top" alt="Logo">
		</a>
		<div class="mx-auto">
			<h2 class="text-center">
				<span class="text text-danger">M</span>etric <span
					class="text text-danger">A</span>utomation
			</h2>
		</div>
	</nav>
	<div class="container mt-3">
		<form action="<%= request.getContextPath() %>/EntryPoint"
			method="post">
			<div class="form-group">
				<label for="year">Select the year from the below dropdown:</label> <select
					id="year" class="form-control" name="year">
				</select>
			</div>
			<hr>
			<div class="form-group">
				<label for="quarter">Select the Quarter :</label> <br> <select
					id="quarter" class="form-control" name="quarter">
					<option value="Q1">Quarter 1 [Oct - Dec]</option>
					<option value="Q2">Quarter 2 [Jan - Mar]</option>
					<option value="Q3">Quarter 3 [Apr - Jun]</option>
					<option value="Q4">Quarter 4 [Jul - Sep]</option>
				</select>
			</div>
			<hr>
			<div class="form-group">
				<input id="report" type="submit" class="btn btn-lg btn-danger"
					value="Generate Report" />
			</div>
		</form>

	</div>



	<script>
        $(function () {
            var currentYear = new Date().getFullYear();
            var startYear = currentYear - 5;
            var endYear = currentYear + 5;
            for (var i = startYear; i <= endYear; i++) {
                $("#year").append("<option value='" + i + "'>" + i + "</option>");
            }
        });
    </script>
</body>

</html>