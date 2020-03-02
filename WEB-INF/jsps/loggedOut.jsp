<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

	 This page is arrived at if a user logs out, enabling them to return to log in 
	 to use the website as a new user
 -->


<!DOCTYPE html>
<html lang="en">
	<head>
		<link rel="stylesheet" href="Style.css">
		<title>LoggedOut</title>
	</head>
	<body>
		<h1>Session ended</h1>
		<p> Would you like to start a new session?</p>
		<form method="post" onsubmit="classes/WebDispatch">
			<div>
				<button onclick="submit" name="newSession" value="newSess">New Session</button>
			</div>
		</form>
	</body>
</html>