<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

	 This jsp is displayed if attempting to login while already logged in
	 This may appear due to having multiple tabs open
 -->


<!DOCTYPE html>
<html lang="en">
	<head>
		<link rel="stylesheet" href="Style.css">
		<title>ErrorPage</title>
	</head>
	<body>
		<h2>Error</h2>

		<p> You are already logged in. Please select an option below.</p>
		<form method="post" onsubmit="classes/WebDispatch">
			<div class="buttonDiv">
				<button onclick="submit" name="logErrorButton" value="Log Out">Log out</button>
				<button onclick="submit" name="logErrorButton" value="Return to Dashboard">Return to Dashboard</button>
			</div>
		</form>
	</body>
</html>