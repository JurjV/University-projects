use [WorldOfFootball]
GO

drop table TeamSponsor
drop table Player
drop table Ticket
drop table MatchStatistics
drop table [Match]
drop table Referee
drop table Stadium
drop table Team
drop table Sponsor
drop table Coach
drop table League


CREATE TABLE League
    (LeagueID INT PRIMARY KEY,
     LName VARCHAR(50))


CREATE TABLE Coach
    (CoachID INT PRIMARY KEY,
     CName VARCHAR(50),
     CRating INT CHECK (CRating >= 0 AND CRating < 100),
     CNationality VARCHAR(50))

CREATE TABLE Sponsor
    (SponsorID INT PRIMARY KEY,
     SponsorName VARCHAR(100),
	 SponsorOrigin VARCHAR(100),
	 PhoneNR VARCHAR(10))


CREATE TABLE Team
    (TeamID INT PRIMARY KEY,
     TName VARCHAR(70),
     TPoints INT CHECK (TPoints >= 0),
	 MatchesPlayed INT CHECK(MatchesPlayed >=0),
     CoachID INT REFERENCES Coach(CoachID) Unique,
     LeagueID INT REFERENCES League(LeagueID))

CREATE TABLE TeamSponsor
	(TeamID INT REFERENCES Team(TeamID),
    SponsorID INT REFERENCES Sponsor(SponsorID),
    PRIMARY KEY (TeamID, SponsorID))

CREATE TABLE Player
    (PlayerID INT PRIMARY KEY,
     PName VARCHAR(50),
     PRating INT CHECK (PRating >= 1 AND PRating <= 99),
     PNationality VARCHAR(50),
     TeamID INT REFERENCES Team(TeamID))

CREATE TABLE Referee
    (RefereeID INT PRIMARY KEY,
     RefereeName VARCHAR(100),
	 RNationality VARCHAR(50))

CREATE TABLE Stadium
    (StadiumID INT PRIMARY KEY,
     StadiumName VARCHAR(100),
     Capacity INT)

CREATE TABLE [Match]
    (MatchID INT PRIMARY KEY,
     MatchDate DATE,
     HomeTeamID INT REFERENCES Team(TeamID),
     AwayTeamID INT REFERENCES Team(TeamID),
     RefereeID INT REFERENCES Referee(RefereeID),
     StadiumID INT REFERENCES Stadium(StadiumID))


CREATE TABLE MatchStatistics
    (MatchStatisticsID INT PRIMARY KEY,
     MatchID INT REFERENCES [Match](MatchID),
     HomeGoals INT,
     AwayGoals INT,
     YellowCards INT,
     ShotsOnGoal INT)

CREATE TABLE Ticket
    (TicketID INT PRIMARY KEY,
     MatchID INT REFERENCES [Match](MatchID),
     Price INT)

