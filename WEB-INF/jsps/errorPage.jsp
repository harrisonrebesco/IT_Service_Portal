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
		<h1>Error</h1>
		<p> No page was selected via request dispatcher. Please consider submitting an issue detailing how this error occurred.</p>
		<form method="post" onsubmit="classes/WebDispatch">
			<div>
				<button onclick="submit" name="retButton" value="Return">Return</button>
				<button onclick="submit" name="logErrorButton" value="Log Out">Log out</button>
			</div>
		</form>
	</body>
</html>