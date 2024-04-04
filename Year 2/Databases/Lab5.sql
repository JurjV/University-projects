use WorldOfFootball

CREATE TABLE Ta (
    aid INT PRIMARY KEY,
    a2 INT UNIQUE,
	a3 INT
);

CREATE TABLE Tb (
    bid INT PRIMARY KEY,
    b2 INT,
	b3 INT
);

CREATE TABLE Tc (
    cid INT PRIMARY KEY,
    aid INT,
    bid INT,
	c INT,
    FOREIGN KEY (aid) REFERENCES Ta(aid),
    FOREIGN KEY (bid) REFERENCES Tb(bid)
);
GO

CREATE or alter PROCEDURE InsertRandomData
    @numInserts INT
AS
BEGIN
	DELETE Ta
    DECLARE @counter INT = 1;

    WHILE @counter <= @numInserts
    BEGIN
        INSERT INTO Ta (aid,a2, a3)
        VALUES 
            (	@counter,
                @counter * 234 ,
                ROUND(RAND() * 20000 + 40000, 2) 
            );

        SET @counter = @counter + 1;
    END;
END;

EXEC InsertRandomData @numInserts = 10000;
Select* from Ta
GO

CREATE or alter PROCEDURE InsertRandomTb
    @numInserts INT
AS
BEGIN
	DELETE Tb
    DECLARE @counter INT = 1;

    WHILE @counter <= @numInserts
    BEGIN
        INSERT INTO Tb (bid,b2, b3)
        VALUES 
            (	@counter,
                @counter * 50 ,
                ROUND(RAND() * 20000 + 40000, 2) 
            );

        SET @counter = @counter + 1;
    END;
END;

EXEC InsertRandomTb @numInserts = 10000;
GO

CREATE or alter PROCEDURE InsertRandomTc
    @numInserts INT
AS
BEGIN
	Delete from Tc
    DECLARE @counter INT = 1;
	DECLARE @maxAID INT;
	DECLARE @maxBID INT;

    -- Get the maximum employee_id in the Employees table
    SELECT @maxAID = MAX(aid) FROM Ta;
	SELECT @maxBID = MAX(bid) FROM Tb;

    WHILE @counter <= @numInserts
    BEGIN
        INSERT INTO Tc (cid,aid,bid,c)
        VALUES 
            (	@counter,
               CEILING(RAND() * @maxAID),  
                CEILING(RAND() * @maxBID),  
                ROUND(RAND() * 70, 2)  
            );

        SET @counter = @counter + 1;
    END;
END;
EXEC InsertRandomTc @numInserts = 10000;

/*Clustered index scan	*/
SELECT * FROM Ta

/*	Clustered index seek	*/
SELECT * FROM Ta WHERE aid=5

DROP INDEX ID_Ta_a2 ON Ta
CREATE NONCLUSTERED INDEX ID_Ta_a2 ON Ta(a2);

/*	Nonclustered Index Scan		*/
SELECT a2 FROM Ta ORDER BY a2

/*	Nonclustered index seek		*/
SELECT a2 FROM Ta WHERE a2=468

/*	Clustered key lookup	*/
SELECT a3 FROM Ta WHERE a2=468



SELECT * FROM Tb WHERE b2=150
--0.0065 w index
--0.032 without index

CREATE NONCLUSTERED INDEX ID_Tb_b2 ON Tb(b2) INCLUDE (bid)
DROP INDEX ID_Tb_b2 ON Tb
GO

CREATE OR ALTER VIEW AllTableView AS 
SELECT 
	a.aid,
	a.a2,
	b.bid,
	b.b2,
	c.cid,
	c.c
FROM Ta a
JOIN Tc c ON a.aid=c.aid
JOIN Tb b ON c.bid=b.bid
where c =700
GO

Select* from AllTableView  
--0.047 no index
--0.013 w index

create nonclustered index ID_TableView_c on Tc(c);