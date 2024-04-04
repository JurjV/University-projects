use [WorldOfFootball]
GO

/* Version 1 to version 2
   Change column type		*/
CREATE OR ALTER PROCEDURE uspChangeSponsorPhoneNrType
AS
		ALTER TABLE Sponsor
		ALTER COLUMN PhoneNR CHAR(10) NOT NULL
GO
/* Version 2 to version 1 */
CREATE OR ALTER PROCEDURE uspChangeSponsorPhoneNrTypeBack
AS
		ALTER TABLE Sponsor
		ALTER COLUMN PhoneNR VARCHAR(10) NOT NULL
GO



/*  Version 2 to version 3 
	Add/Remove a column		*/ 
CREATE OR ALTER PROCEDURE uspAddCornersToMatchStatistics 
AS
		ALTER TABLE MatchStatistics ADD Corners INT
GO
/* Version 3 to version 2 */
CREATE OR ALTER PROCEDURE uspRemoveCornersFromMatchStatistics
AS
		ALTER TABLE MatchStatistics DROP COLUMN Corners
GO


/* Version 3 to version 4 
   Add/Remove a default constraint	*/
CREATE OR ALTER PROCEDURE uspAddDefaultPriceConstraint
AS
		ALTER TABLE Ticket ADD CONSTRAINT defaultPrice DEFAULT(50) FOR Price
GO
/* Version 4 to version 3 */
CREATE OR ALTER PROCEDURE uspRemoveDefaultPriceConstraint
AS
		ALTER TABLE Ticket DROP CONSTRAINT defaultPrice
GO


/* Version 4 to version 5 
   Create/Drop a table	*/
CREATE OR ALTER PROCEDURE uspCreateTablePlayerStats
AS
		CREATE TABLE PlayerStatistics (
			PlayerStatsID INT,
			PlayerPosition VARCHAR(10) NOT NULL,
			PlayerID INT REFERENCES Player(PlayerID),
			TeamID INT REFERENCES Team(TeamID),
			MatchesPlayed INT,
			Goals INT,
			Assists INT,
			CONSTRAINT PK_PlayerStats PRIMARY KEY(PlayerStatsID),
			CONSTRAINT FK_PlayerStats_Player FOREIGN KEY (PlayerID) REFERENCES Player(PlayerID)
		)
GO
/*Version 5 to version 4 */
CREATE OR ALTER PROCEDURE uspDropTablePlayerStats
AS
		DROP TABLE PlayerStatistics
GO


/* Version 5 to version 6 
   Add/Remove a primary key		*/
CREATE OR ALTER PROCEDURE uspChangePrimaryKeyPlayerStats
AS
		ALTER TABLE PlayerStatistics
			DROP CONSTRAINT PK_PlayerStats
		ALTER TABLE PlayerStatistics
			ADD CONSTRAINT PK_PlayerStatistics PRIMARY KEY(PlayerStatsID,PlayerPosition)
GO
/* Version 6 to 5 */
CREATE OR ALTER PROCEDURE uspChangeBackPrimaryKeyPlayerStats
AS
		ALTER TABLE PlayerStatistics
			DROP CONSTRAINT PK_PlayerStatistics
		ALTER TABLE PlayerStatistics
			ADD CONSTRAINT PK_PlayerStats PRIMARY KEY(PlayerStatsID)
GO


/* Version 6 to version 7 
   Add/Remove a foreign key		*/
CREATE OR ALTER PROCEDURE uspAddNewForeignKeyToPlayerStats
AS
		ALTER TABLE PlayerStatistics
			ADD	CONSTRAINT FK_PlayerStats_TeamID FOREIGN KEY(TeamID) REFERENCES Team(TeamID)
GO
/* Version 7 to version 6 */
CREATE OR ALTER PROCEDURE uspRemoveForeignKey
AS
		ALTER TABLE PlayerStatistics DROP CONSTRAINT FK_PlayerStats_TeamID
GO


/* Version 7 to version 8 
   Add/Remove a candidate key	*/
CREATE OR ALTER PROCEDURE uspAddCandidateKey
AS
		ALTER TABLE Player
        ADD CONSTRAINT CK_Player UNIQUE (PName, PNationality)
GO
/* Version 8 to version 7 */
CREATE OR ALTER PROCEDURE uspRemoveCandidateKey
AS
		ALTER TABLE Player
		DROP CONSTRAINT CK_Player
GO

DROP TABLE VersionTable
DROP TABLE ProcedureIndicator

CREATE TABLE VersionTable(
		[Version] INT
		)
INSERT VersionTable ([Version]) VALUES (1)

CREATE TABLE ProcedureIndicator (
		VBefore INT,
		VAfter INT,
		PRIMARY KEY (VBefore,VAfter),
		PName VARCHAR(MAX)
		)

INSERT ProcedureIndicator (VBefore,VAfter,PName) VALUES 
		(1,2,'uspChangeSponsorPhoneNrType'),
		(2,1,'uspChangeSponsorPhoneNrTypeBack'),
		(2,3,'uspAddCornersToMatchStatistics'),
		(3,2,'uspRemoveCornersFromMatchStatistics'),
		(3,4,'uspAddDefaultPriceConstraint'),
		(4,3,'uspRemoveDefaultPriceConstraint'),
		(4,5,'uspCreateTablePlayerStats'),
		(5,4,'uspDropTablePlayerStats'),
		(5,6,'uspChangePrimaryKeyPlayerStats'),
		(6,5,'uspChangeBackPrimaryKeyPlayerStats'),
		(6,7,'uspAddNewForeignKeyToPlayerStats'),
		(7,6,'uspRemoveForeignKey'),
		(7,8,'uspAddCandidateKey'),
		(8,7,'uspRemoveCandidateKey');


GO
CREATE OR ALTER PROCEDURE JumpToVersion(@Target INT)
AS
	DECLARE @CurrentVersion INT
	DECLARE @ProcedureName VARCHAR(MAX)
	SELECT @CurrentVersion = [Version] FROM VersionTable

	IF (@Target >(SELECT MAX(VAfter) FROM ProcedureIndicator) OR @Target < 1)
		RAISERROR('Non existing version!',10,1)
    ElSE
	BEGIN
		
		IF @Target=@CurrentVersion
			PRINT('You are already on that version!');
		ELSE
		BEGIN
			IF @CurrentVersion > @Target
			BEGIN
				WHILE @CurrentVersion > @Target
				BEGIN
					SELECT @ProcedureName = PName FROM ProcedureIndicator WHERE VBefore = @CurrentVersion AND VAfter = @CurrentVersion-1
					PRINT('Executing '+ @ProcedureName);
					EXEC( @ProcedureName )
					SET @CurrentVersion=@CurrentVersion-1
				END
			END

			IF @CurrentVersion < @Target
			BEGIN
			WHILE @CurrentVersion < @Target
				BEGIN
					SELECT @ProcedureName = PName FROM ProcedureIndicator WHERE VBefore = @CurrentVersion AND VAfter = @CurrentVersion+1
					PRINT('Executing '+ @ProcedureName);
					EXEC( @ProcedureName )
					SET @CurrentVersion=@CurrentVersion+1
				END
			END

			UPDATE VersionTable SET [Version] = @Target
		END
	END
GO

EXEC JumpToVersion 1

SELECT * FROM VersionTable
SELECT * FROM ProcedureIndicator