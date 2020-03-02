<!-- SENG2050 Assignment 3 - Semester 1 2019
     c3237487_c3215435_c3293398_FinalProject

     This jsp is a shared site header included at the top of a few other pages

     It contains the website's magnificent logo, as well as buttons for users to return
     to the main dashboard page, or to log out of their current session
 -->


<div class="SiteHeader">
		<div class="NavBar">
			<div class="LogoBlock">
				<img class="logo" src="images/ServicePortalHeader.png" alt="BasicHelpDesk" />
			</div>
			<form name="headerButtons" method="post" onsubmit="classes/WebDispatch">
				<div class="NavBar">
					<button onclick="submit" name="logErrorButton" value="Return to Dashboard">Return to Dashboard</button>
					<button onclick="submit" name="logErrorButton" value="Log Out">Log out</button>
				</div>
			</form>
		</div>
</div>