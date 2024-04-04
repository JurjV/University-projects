use WorldOfFootball
GO

create or alter procedure addToTables (@tableName varchar(50)) as
    if @tableName in (select Name from Tables) begin
        print 'Table already present in Tables'
        return
    end
    if @tableName not in (select TABLE_NAME from INFORMATION_SCHEMA.TABLES) begin
        print 'Table does not exist in the database'
        return
    end
    insert into Tables (Name) values (@tableName)
go


create or alter procedure addToViews (@viewName varchar(50)) as
    if @viewName in (select Name from Views) begin
        print 'View already present in Views'
        return
    end
    if @viewName not in (select TABLE_NAME from INFORMATION_SCHEMA.VIEWS) begin
        print 'View not present in the database'
        return
    end
    insert into Views (Name) values (@viewName)
go


create or alter procedure addToTests (@testName varchar(50)) as
    if @testName in (select Name from Tests) begin
        print 'Test already present in Tests'
        return
    end
    insert into Tests (Name) values (@testName)
go


create or alter procedure connectTableToTest (@tableName varchar(100), @testName varchar(100), @rows int, @pos int) as
    if @tableName not in (select Name from Tables) begin
        print 'Table not present in Tables'
        return
    end
    if @testName not in (select Name from Tests) begin
        print 'Test not present in Tests'
        return
    end
    if exists(
        select *
        from TestTables T1 join Tests T2 on T1.TestID = T2.TestID
        where T2.Name=@testName and Position=@pos
        ) begin
        print 'Incorrect position'
        return
    end
    insert into TestTables (TestID, TableID, NoOfRows, Position) values (
        (select Tests.TestID from Tests where Name=@testName),
        (select Tables.TableID from Tables where Name=@tableName),
        @rows,
        @pos
    )
go


create or alter procedure connectViewToTest (@viewName varchar(100), @testName varchar(100)) as
    if @viewName not in (select Name from Views) begin
        print 'View not present in Views'
        return
    end
    if @testName not in (select Name from Tests) begin
        print 'Test not present in Tests'
        return
    end
    insert into TestViews (TestID, ViewID) values (
        (select Tests.TestID from Tests where Name=@testName),
        (select Views.ViewID from Views where Name=@viewName)
    )
go


create or alter procedure runTest (@testName varchar(100)) as
    if @testName not in (select Name from Tests) begin
        print 'test not in Tests'
        return
    end
    declare @command varchar(100)
    declare @testStartTime datetime2
    declare @startTime datetime2
    declare @endTime datetime2
    declare @table varchar(50)
    declare @rows int
    declare @pos int
    declare @view varchar(50)
    declare @testId int
    select @testId=TestID from Tests where Name=@testName
    declare @testRunId int
    set @testRunId = (select max(TestRunID)+1 from TestRuns)
    if @testRunId is null
        set @testRunId = 0
    declare tableCursor cursor scroll for
        select T1.Name, T2.NoOfRows, T2.Position
        from Tables T1 join TestTables T2 on T1.TableID = T2.TableID
        where T2.TestID = @testId
        order by T2.Position
    declare viewCursor cursor for
        select V.Name
        from Views V join TestViews TV on V.ViewID = TV.ViewID
        where TV.TestID = @testId

    set @testStartTime = sysdatetime()
    open tableCursor
    fetch last from tableCursor into @table, @rows, @pos
    while @@FETCH_STATUS = 0 begin
        exec ('delete from '+ @table)
        fetch prior from tableCursor into @table, @rows, @pos
    end
    close tableCursor

    open tableCursor
    SET IDENTITY_INSERT TestRuns ON
    insert into TestRuns (TestRunID, Description, StartAt)values (@testRunId, 'Tests results for: ' + @testName, @testStartTime)
    SET IDENTITY_INSERT TestRuns OFF
    fetch tableCursor into @table, @rows, @pos
    while @@FETCH_STATUS = 0 begin
        set @command = 'populate' + @table
        if @command not in (select ROUTINE_NAME from INFORMATION_SCHEMA.ROUTINES) begin
            print @command + 'does not exist'
            return
        end
        set @startTime = sysdatetime()
        exec @command @rows
        set @endTime = sysdatetime()
        insert into TestRunTables (TestRunID, TableId, StartAt, EndAt) values (@testRunId, (select TableID from Tables where Name=@table), @startTime, @endTime)
        fetch tableCursor into @table, @rows, @pos
    end
    close tableCursor
    deallocate tableCursor

    open viewCursor
    fetch viewCursor into @view
    while @@FETCH_STATUS = 0 begin
        set @command = 'select * from ' + @view
        set @startTime = sysdatetime()
        exec (@command)
        set @endTime = sysdatetime()
        insert into TestRunViews (TestRunID, ViewID, StartAt, EndAt) values (@testRunId, (select ViewID from Views where Name=@view), @startTime, @endTime)
        fetch viewCursor into @view
    end
    close viewCursor
    deallocate viewCursor

    update TestRuns
    set EndAt=sysdatetime()
    where TestRunID = @testRunId
go


CREATE OR ALTER PROCEDURE populateLeague(@rows INT)
AS
DECLARE @i INT
SET @i=0
WHILE @i<@rows BEGIN
	INSERT INTO League(LeagueID,LName) VALUES
	(@i,'League'+CAST(@i AS VARCHAR(255)))
	SET @i=@i+1
END
GO

CREATE OR ALTER PROCEDURE populateSponsor(@rows INT)
AS
DECLARE @i INT
SET @i=0
WHILE @i<@rows BEGIN
	INSERT INTO Sponsor(SponsorID,SponsorName,SponsorOrigin,PhoneNR) VALUES
	(@i,'Name'+CAST(@i AS VARCHAR(255)),'Country'+CAST(@i AS VARCHAR(255)),'PhoneNr'+CAST(@i AS VARCHAR(255)))
	SET @i=@i+1
END
GO


CREATE OR ALTER PROCEDURE populateCoach(@rows INT)
AS
DECLARE @i INT
SET @i=0
WHILE @i<@rows BEGIN
	INSERT INTO Coach(CoachID,CName,CRating,CNationality) VALUES
	(@i,'Coach'+CAST(@i AS VARCHAR(255)),CAST(RAND() * 100 AS INT),'Country'+CAST(@i AS VARCHAR(255)))
	SET @i=@i+1
END
GO


CREATE OR ALTER PROCEDURE populateTeam(@rows INT)
AS
DECLARE @i INT
DECLARE @leagueID INT
SET @leagueID=0
SET @i=0
WHILE @i<@rows BEGIN
	INSERT INTO Team(TeamID,TName,TPoints,MatchesPlayed,CoachID,LeagueID) VALUES
	(@i,'Team'+CAST(@i AS VARCHAR(255)),@i*3,@i,@i,@leagueID)
	SET @i=@i+1
END
GO


CREATE OR ALTER PROCEDURE populateTeamSponsor(@rows INT)
AS
DECLARE @i INT
SET @i=0
WHILE @i<@rows BEGIN
	INSERT INTO TeamSponsor(TeamID,SponsorID) VALUES
	(@i,@i)
	SET @i=@i+1
END
GO

--A view with a select statement operating on one table
CREATE OR ALTER VIEW CoachView
AS
	SELECT * FROM Coach
GO

--A view with a SELECT statement that operates on at least 2 different tables and contains at least one JOIN operator
CREATE OR ALTER VIEW TeamCoachView
AS
	SELECT t.TName,c.CName
	FROM Coach c JOIN Team t ON t.CoachID=c.CoachID
GO

-- a view with a SELECT statement that has a GROUP BY clause, operates on at least 2 different tables and contains at least one JOIN operator
CREATE OR ALTER VIEW TeamSponsorView 
AS
	SELECT T.TName AS TeamName,S.SponsorName
	FROM Team T JOIN TeamSponsor TS ON T.TeamID = TS.TeamID
				JOIN Sponsor S ON TS.SponsorID = S.SponsorID
	GROUP BY S.SponsorName, T.TName;
GO


--Test1
exec addToTables 'Coach'
exec addToViews 'CoachView'
exec addToTests 'Test1'
exec connectTableToTest 'Coach','Test1',1000,1
exec connectViewtoTest 'CoachView','Test1'
exec runTest 'Test1'

--Test2
exec addToTables 'League'
exec addToTables 'Coach'
exec addToTables 'Team'
exec addToViews 'TeamCoachView'
exec addToTests 'Test2'
exec connectTableToTest 'League','Test2',100,1
exec connectTableToTest 'Coach','Test2',1000,2
exec connectTableToTest 'Team','Test2',1000,3
exec connectViewtoTest 'TeamCoachView','Test2'
exec runTest 'Test2'

--Test3
exec addToTables 'League'
exec addToTables 'Coach'
exec addToTables 'Team'
exec addToTables 'Sponsor'
exec addToTables 'TeamSponsor'
exec addToViews 'TeamSponsorView'
exec addToTests 'Test3'
exec connectTableToTest 'League','Test3',100,1
exec connectTableToTest 'Coach','Test3',1000,2
exec connectTableToTest 'Team','Test3',1000,3
exec connectTableToTest 'Sponsor','Test3',1000,4
exec connectTableToTest 'TeamSponsor','Test3',1000,5
exec connectViewtoTest 'TeamSponsorView','Test3'
exec runTest 'Test3'

SELECT * FROM TestRuns
SELECT * FROM TestRunTables
SELECT * FROM TestRunViews
