Use [WorldOfFootball]
go

Delete from [Match]
Delete from TeamSponsor
Delete from Player
Delete from Team
Delete from Sponsor
Delete from Coach
Delete from League
Delete from Referee
Delete from Stadium


INSERT [League](LeagueID,LName) values 
		(1,'Liga Intai')

Insert [Coach] (CoachID,CName,CRating,CNationality) values
		(1,'Laurentiu Reghecampf',80,'Romanian'),
		(2,'Piturca',78,'Hungarian'),
		(3,'Vasile',25,'Romanian'),
		(4,'Dorel',15,'Romanian');

Insert [Sponsor](SponsorID,SponsorName,SponsorOrigin,PhoneNR) values
		(1,'Nike','Romania','0722156432')
SELECT * FROM Sponsor

Insert [Team] (TeamID,TName,TPoints,MatchesPlayed,CoachID,LeagueID) values
		(1,'FC Badoaca',30,10,1,1),
		(2,'FC Bihor',27,10,2,1),
		(3,'FCSB',0,10,4,1);

UPDATE [Team]
SET TName='FC Bihoru Oradea'
WHERE TeamID=2


Insert [TeamSponsor] (TeamID,SponsorID) values (1,1),(2,1);

Insert [Player] (PlayerID,PName,PRating,PNationality,TeamID) 
values
		(1,'Jurj Victor',99,'Romanian',1),
		(2,'Ionut Jorza',98,'Romanian',1),
		(3,'Darius Lacatus',97,'Romanian',2),
		(4,'Edy Kover',92,'Hungarian',2),
		(5,'Dumitru Albut',67,'Romanian',1),
		(6,'Laurentiu Stangaciu',30,'Romanian',2),
		(7,'Alex Ganea',57,'Romanian',2),
		(8,'Ionut Popa',80,'Romanian',1),
		(9,'Adrian Elicopter',45,'Romanian',2),
		(10,'Adi Mutu',90,'Romanian',3),
		(11,'Zoli Nagy',78,'Hungarian',3);

Update [Player]
SET PRating=PRating-7,TeamID=1
WHERE Player.PlayerID=4 AND Player.PRating>90

DELETE FROM [Player] WHERE TeamID=1 AND PRating BETWEEN 10 AND 70


Insert [Referee] (RefereeID,RefereeName,RNationality)
values
		(1,'Istvan Kovacic','Hungarian'),
		(2,'Ionut Popa','Romanian'),
		(3,'Alex Ganea','Romanian');

Insert [Stadium] (StadiumID,StadiumName,Capacity) values
		(1,'Arena Nationala',100000),
		(2,'Cluj Arena',75000),
		(3,'Stadionul Giulesti',35000);

Update [Stadium]
SET Capacity=125000
WHERE Stadium.StadiumID=1 

DELETE FROM [Stadium] WHERE Capacity<50000


Insert [Match] (MatchID,MatchDate,HomeTeamID,AwayTeamID,RefereeID,StadiumID) values
		(1,'2022-04-22',1,2,1,1),(2,'2023-10-31',2,1,2,2);


/*a)1.A Table made from all the Romanian players and coaches.*/
SELECT PName as [Name],PNationality as Nationality from Player
WHERE PNationality='Romanian'
UNION ALL
SELECT CName as [Name],CNationality as Nationality from Coach
WHERE CNationality='Romanian'
ORDER BY [Name];

/*a)2.A Table made from all the highest and lowest rated players and coaches.*/
SELECT PName as [Name],PRating as Rating from Player
WHERE PRating>90 or PRating<50
UNION
SELECT CName as [Name],CRating as Rating from Coach
WHERE CRating>90 or CRating<30


/*b)1.Intersecting the Romanian referees that also play for a team.*/
SELECT PName as [Name],PNationality as Nationality from Player
WHERE PNationality IN('Romanian')
INTERSECT
SELECT RefereeName as [Name],RNationality as Nationality from Referee

/*b)2.Intersecting the Romanian players and coaches that have the same rating,which is bigger than 90.*/
SELECT PNationality as Nationality,PRating as Rating from Player
WHERE PRating>90 AND PNationality IN('Romanian')
INTERSECT
SELECT CNationality as Nationality,CRating as Rating from Coach
WHERE CRating>90 AND CNationality IN('Romanian')


/*c)1.Showing all the referees that do not play for a team.*/
SELECT RefereeName as [Name],RNationality as Nationality from Referee
EXCEPT
SELECT PName as [Name],PNationality as Nationality from Player

/*c)2.Showing all players that are not Hungarian and also not referees.*/
SELECT PName as [Name],PNationality as Nationality from Player
WHERE PNationality NOT IN('Hungarian')
EXCEPT
SELECT RefereeName as [Name],RNationality as Nationality from Referee
WHERE RNationality NOT IN('Hungarian')


/*d)1.A query that shows the name of the sponsor for every team.*/
SELECT TName as TeamName,SponsorName from Team
INNER JOIN TeamSponsor on Team.TeamID=TeamSponsor.TeamID
INNER JOIN Sponsor on TeamSponsor.SponsorID=Sponsor.SponsorID

/*d)2.A query that shows every coaches team,even if they do not have one.*/
SELECT CName as CoachName,TName as TeamName from Team
RIGHT JOIN Coach on Team.CoachID=Coach.CoachID

/*d)3.A query that shows the name of the stadium and the teams that play in every match.*/
SELECT StadiumName, MatchDate,HomeTeam.TName as HomeTeam,AwayTeam.TName as AwayTeam from [Match] M
LEFT JOIN Stadium on Stadium.StadiumID=M.StadiumID
INNER JOIN Team HomeTeam on M.HomeTeamID=HomeTeam.TeamID
INNER JOIN Team AwayTeam on M.AwayTeamID=AwayTeam.TeamID

/* d)4. The ID of the teams that have a sponsor and the date of the matches they play in. 
The query that joins 2 many-to-many relationships.*/
Select TeamSponsor.TeamID,[Match].MatchDate from TeamSponsor
LEFT JOIN [Match] on ([Match].HomeTeamID=TeamSponsor.TeamID or [Match].AwayTeamID=TeamSponsor.TeamID)


/*e)1.Retrieve teams that have coaches from Romania.*/
SELECT Team.TeamID, Team.TName from Team
WHERE Team.CoachID IN(
	SELECT Coach.CoachID FROM Coach
	WHERE Coach.CNationality IN('Romanian'))

/*e)2.Retrieve players that are playing in teams that have coaches from Hungary.*/
SELECT Player.PlayerID,Player.PName from PLayer
WHERE Player.TeamID IN(
		SELECT Team.TeamID from Team
		WHERE Team.CoachID IN(
			SELECT Coach.CoachID FROM Coach
			WHERE Coach.CNationality IN('Hungarian')))


/*f)1.Retrieve the players from FC Badoaca that have a rating higher than 70.*/
SELECT PName as [Name],PRating as Rating from Player
WHERE EXISTS(SELECT TName FROM Team WHERE Team.TeamID=Player.TeamID AND Team.TName='FC Badoaca' AND Player.PRating>=70)

/*f)2.Retrieve the players that are not from FC Badoaca and have a rating lower than 90.*/
SELECT PName as [Name],PRating as Rating from Player
WHERE EXISTS(SELECT TName FROM Team WHERE Team.TeamID=Player.TeamID AND Team.TName!='FC Badoaca' AND Player.PRating<90)


/*g)1.Retrieve the average player rating for every team.*/
SELECT TName as TeamName,AVG(PRating) as AverageRating FROM Team
LEFT JOIN Player ON Team.TeamID=Player.TeamID
GROUP BY Team.TName;

/*g)2.Retrieve the number of Romanian players for every team.*/
SELECT TName as TeamName,COUNT(PlayerID) as TotalRomanianPlayers FROM Team
LEFT JOIN Player ON Team.TeamID=Player.TeamID
WHERE Player.PNationality='Romanian'
GROUP BY Team.TName;


/*h)1.Find the total number of players in each team and display only the teams with less than 11.*/
SELECT Team.TName,COUNT(Player.PlayerID) as TotalPlayers FROM Team
LEFT JOIN Player ON Team.TeamID=Player.TeamID
GROUP BY Team.TeamID,Team.TName
HAVING COUNT(Player.PlayerID)<11

/*h)2.Calculate the average player rating for each team and display only those teams with an average rating above 65.*/
SELECT Team.TName,AVG(Player.PRating) as AverageRating FROM Team
LEFT JOIN Player ON Team.TeamID=Player.TeamID
GROUP BY Team.TeamID,Team.TName
HAVING AVG(Player.PRating)>65

/*h)3.Find the two teams that are placed first and last.*/
SELECT Team.TName, SUM(Team.TPoints) AS TotalPoints
FROM Team
GROUP BY Team.TeamID, Team.TName
HAVING SUM(Team.TPoints) = (SELECT MAX(TPoints) FROM Team)
   OR SUM(Team.TPoints) = (SELECT MIN(TPoints) FROM Team);

/*h)4.Find the teams that have more than 10 points.*/
SELECT Team.TeamID,Team.TName,SUM(Team.TPoints) as TotalPoints
FROM Team
GROUP BY Team.TeamID,Team.TName
HAVING SUM(Team.TPoints)>10;


/*i)1.Find the teams that have at least one Hungarian player.*/
SELECT Team.TeamID, Team.TName FROM Team
WHERE Team.TeamID = ANY (
    SELECT Player.TeamID FROM Player
    WHERE Player.PNationality = 'Hungarian')

/*Same thing but with IN instead of ANY.*/
SELECT Team.TeamID, Team.TName FROM Team
WHERE Team.TeamID IN (
    SELECT Player.TeamID FROM Player
    WHERE Player.PNationality = 'Hungarian');


/*i)2.Find the players that have a rating above the ratings of all coaches.*/
SELECT PlayerID, PName, PRating FROM Player
WHERE PRating > ALL(SELECT Coach.CRating FROM Coach)

/*Same thing but with the aggregation operator MAX*/
SELECT PlayerID, PName, PRating FROM Player
WHERE PRating > (SELECT MAX(CRating) FROM Coach);


/*i)3.Find the matches that are played with a Romanian or a Hungarian referee.*/
SELECT Referee.RefereeName,[Match].MatchDate FROM [Match]
INNER JOIN Referee on  [Match].RefereeID=Referee.RefereeID
WHERE Referee.RNationality=ANY(SELECT'Romanian'UNION SELECT 'Hungarian');

/*Same thing but with IN instead of ANY.*/
SELECT Referee.RefereeName,[Match].MatchDate FROM [Match]
INNER JOIN Referee on  [Match].RefereeID=Referee.RefereeID
WHERE Referee.RNationality IN(SELECT'Romanian'UNION SELECT 'Hungarian');


/*i)4.Find the coaches that have a rating below the rating of all players.*/
SELECT Coach.CName,Coach.CRating FROM Coach
WHERE CRating < ALL(SELECT Player.PRating FROM Player)

/*Same thing but with the aggregation operator MIN*/
SELECT Coach.CName,Coach.CRating FROM Coach
WHERE CRating < (SELECT MIN(Player.PRating) FROM Player)
