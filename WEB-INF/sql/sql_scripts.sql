DROP TABLE Tags;
DROP TABLE Notifications;
DROP TABLE Comments;
DROP TABLE Issues;
DROP TABLE Users;

-- user table commands 
CREATE TABLE Users (
	userID INT(4),
	userType BOOLEAN,
	firstName VARCHAR(50), 
	lastName VARCHAR(50), 
	username VARCHAR(50), 
	password VARCHAR(20),
	email VARCHAR(50),
	contactNumber CHAR (10),
	PRIMARY KEY (userID)
);

-- issue table commands 
CREATE TABLE Issues(
	issueID INT(4),
	userID INT(4),
	category INT(1),
	title VARCHAR(99),
	status INT(1),
	description VARCHAR(999),
	resolutionDetails VARCHAR(999) NULL,
	knowledgeBase BOOLEAN, 
	tags VARCHAR(999),
	timeOpened VARCHAR(20),
	timeClosed VARCHAR(20) NULL,
	PRIMARY KEY (issueID),
	FOREIGN KEY (userID) REFERENCES Users(userID)
);

-- comment table creation 
CREATE TABLE Comments (
	commentID INT(4),
	userID INT(4),
	issueID INT(4),
	timePublished VARCHAR(20),
	content VARCHAR(999),
	resolution BOOLEAN,
	PRIMARY KEY (commentID),
	FOREIGN KEY (userID) REFERENCES Users(userID),
	FOREIGN KEY (issueID) REFERENCES Issues(issueID)
);

-- notification table creation 
CREATE TABLE Notifications (
	notificationID INT(4), 
	userID INT(4),
	issueID INT(4), 
	viewed BOOLEAN, 
	content VARCHAR(999),
	PRIMARY KEY (notificationID),
	FOREIGN KEY (userID) REFERENCES Users(userID),
	FOREIGN KEY (issueID) REFERENCES Issues(issueID)
);

-- dummy user values (will provide 1 reporter, 1 staff)
INSERT INTO Users VALUES 
(9999, true, 'Rob', 'Bob', 'staff', 'staff', 'man@gmail.com', 0499999999),
(9998, true, 'Dylan', 'Timmy', 'assignment3', 'password1', 'dyltna@gmail.com', 0499999999),
(1001, false, 'Bob', 'Rob', 'user', 'user', 'boonob@gmail.com', 0433333333),
(1002, false, 'Jim', 'Bob', 'jimbob', 'password2', 'sksks@gmail.com', 0422222222);

INSERT INTO Issues VALUES 
(2001, 1001, 1, "This issue added to safe you time :)", 1, "This for user 1001 (user): Adding this stuff takes ages, we thought we would be nice and give you some dummy data.", null, false, "fun, with, friends", "11/11/11 11:11:11", null),
(2002, 1002, 2, "Here is an issue to test", 1, "This is for user 1002: (jimbob) Adding this stuff takes ages, we thought we would be nice and give you some dummy data.", null, false, "fun, with, friends", "11/11/11 11:11:11", null);

INSERT INTO Notifications VALUES 
(3001, 9999, 2001, false, "New issue has been submitted.");