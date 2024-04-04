use WorldOfFootball
GO

DROP TABLE RoutesStations
DROP TABLE Stations
DROP TABLE [Routes]
DROP TABLE Trains
DROP TABLE TrainTypes

CREATE TABLE TrainTypes
( TTID INT PRIMARY KEY,
  TTName VARCHAR(50),
  TTDesscr VARCHAR(200))

CREATE TABLE Trains
( TID INT PRIMARY KEY,
  TName VARCHAR(50),
  TTID INT REFERENCES TrainTypes(TTID))

CREATE TABLE [Routes]
( RID INT PRIMARY KEY,
  RName VARCHAR(50) UNIQUE,
  TID INT REFERENCES Trains(TID))

CREATE TABLE Stations
( SID INT PRIMARY KEY,
  SName VARCHAR(50) UNIQUE)

CREATE TABLE RoutesStations
( RID INT REFERENCES [Routes](RID),
  SID INT REFERENCES Stations(SID),
  Arrival TIME,
  Departure TIME,
  PRIMARY KEY(RID,SID))

GO

--stored proc
CREATE OR ALTER PROCEDURE uspUpdateStationsOnRoute(@RName VARCHAR(50) ,@SName VARCHAR(50),@Arrival TIME,@Departure TIME)
AS
	DECLARE @RID INT = (SELECT RID FROM Routes WHERE RName=@RName),
		@SID INT = (SELECT SID FROM Stations WHERE SName = @Sname)
	IF @RID IS NULL
	BEGIN
		RAISERROR('route does not exist',16,1)
		RETURN 
	END

	IF @SID IS NULL
	BEGIN
		RAISERROR('station does not exist',16,1)
		RETURN 
	END

	IF EXISTS (SELECT * FROM RoutesStations WHERE RID = @RID AND SID=@SID)
		UPDATE RoutesStations
		SET Arrival = @Arrival ,Departure=@Departure
		WHERE RID = @RID AND SID= @SID
	ELSE
		INSERT RoutesStations(RID,SID,Arrival,Departure)
		VALUES(@RID,@SID,@Arrival,@Departure)
GO

INSERT TrainTypes VALUES(1,'tt1','tt1desc'),(2,'tt2','tt2desc')
INSERT Trains VALUES(1,'t1',1),(2,'t2',2),(3,'t3',1)
INSERT [Routes] VALUES(1,'r1',1),(2,'r2',2),(3,'r3',3)
INSERT Stations VALUES(1,'s1'),(2,'s2'),(3,'s3')

SELECT * FROM TrainTypes
SELECT * FROM Trains
SELECT * FROM [Routes]
SELECT * FROM Stations
SELECT * FROM RoutesStations


--uspUpdateStationsOnRoute 'r4','s1','10:00','10:05'--err
--uspUpdateStationsOnRoute 'r1','s4','10:00','10:05'--err

EXEC uspUpdateStationsOnRoute 'r1','s1','10:00','10:05'
EXEC uspUpdateStationsOnRoute 'r1','s2','10:10','10:15'
EXEC uspUpdateStationsOnRoute 'r1','s3','10:20','10:25'
EXEC uspUpdateStationsOnRoute 'r2','s1','11:00','11:05'
EXEC uspUpdateStationsOnRoute 'r2','s2','11:10','11:15'
EXEC uspUpdateStationsOnRoute 'r2','s3','11:20','11:25'
EXEC uspUpdateStationsOnRoute 'r3','s1','12:00','12:05'

--view
CREATE VIEW vRoutesWithAllStations
AS
	SELECT r.[RName]
	FROM Routes r
	WHERE NOT EXISTS
	(
		SELECT SID FROM Stations
		EXCEPT
		SELECT SID
		FROM RoutesStations 
		WHERE RID=r.RID)

SELECT * FROM vRoutesWithAllStations

--function
CREATE FUNCTION ufFilterStationsByNumOfRoutes(@R INT)
RETURNS TABLE
RETURN
	SELECT s.SName
	FROM Stations s
	WHERE s.SID IN
		(SELECT rs.SID
		FROM RoutesStations rs
		GROUP BY rs.SID
		HAVING COUNT(*) >@R)

SELECT * 
FROM ufFilterStationsByNumOfRoutes(1)