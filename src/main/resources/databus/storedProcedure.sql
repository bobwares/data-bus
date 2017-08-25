SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO


CREATE PROCEDURE [component].[DATAGRID_CUSTOMERS] (
  @pageNumber               INT = 1,
  @recordsPerPage           INT = -1,
  @sortBy                   NVARCHAR(50) = 'name',
  @sortDir                  NVARCHAR(1) = 'A',

  @paxId                    INT,
  @type                     NVARCHAR(255) = NULL,
  @name                     NVARCHAR(255) = NULL,
  @address                  NVARCHAR(255) = NULL,
  @city                     NVARCHAR(255) = NULL,
  @state                    NVARCHAR(255) = NULL,
  @zip                      NVARCHAR(255) = NULL,
  @phone                    NVARCHAR(255) = NULL
) AS BEGIN
  SET NOCOUNT ON

  DECLARE @recordCount INT,
		  @pageCount INT,
		  @startId INT,
		  @endId INT

    CREATE TABLE #tempTable(
      tempTableId INT IDENTITY (1, 1) NOT NULL PRIMARY KEY,
	  [RELATIONSHIP_ID]  INT,
	  [PAX_ID]           INT,
      [TYPE]             NVARCHAR(255),
      [NAME]             NVARCHAR(255),
      [ADDRESS]          NVARCHAR(255),
      [CITY]             NVARCHAR(255),
      [STATE]            NVARCHAR(255),
      [ZIP]              NVARCHAR(255),
      [PHONE]            NVARCHAR(255),
	  [OEC]              NVARCHAR(255),
	  [DMS_LIST]         NVARCHAR(MAX),
	  [ALERT]            BIT
      )

    CREATE TABLE #ordered (
      [id]    INT IDENTITY (1, 1) NOT NULL PRIMARY KEY,
      data_id INT                 NOT NULL
    )

    INSERT INTO #tempTable
    (
	  [RELATIONSHIP_ID],
	  [PAX_ID],
	  [TYPE],
      [NAME],
      [ADDRESS],
      [CITY],
      [STATE],
      [ZIP],
      [PHONE]
    )
    select r.relationship_id,
		cust_pax.pax_id,
		isnull(pm_ft.misc_data, cust_pg.role_code) as type,
		cust_pax.company_name as name,
		cust_a.address1 as address,
		cust_a.city as city,
		cust_a.state as state,
		cust_a.zip as zip,
		cust_ph.phone as phone
	from component.relationship r
	join component.pax comp_pax on comp_pax.pax_id = r.pax_id_2
	join component.pax cust_pax on cust_pax.pax_id = r.pax_id_1
	join component.pax_group cust_pg on cust_pg.pax_id = cust_pax.pax_id
		and cust_pg.organization_code = 'CCA'
		and cust_pg.role_code in ('ISC','FLEET')
		and cust_pg.thru_date is null
	left join component.pax_misc pm_ft on pm_ft.pax_id = cust_pax.pax_id
		and pm_ft.vf_name = 'FACILITY_TYPE'
		and pm_ft.thru_date is null
	left join component.address cust_a on cust_a.pax_id = cust_pax.pax_id
		and cust_a.address_type_code = 'HOM'
	left join component.phone cust_ph on cust_ph.pax_id = cust_pax.pax_id
		and cust_ph.phone_type_code = 'HOM'
	where r.relationship_type_code = 'BUYSFROM'
		and r.thru_date is null
		and comp_pax.pax_id = @paxid

	update temp
	set OEC = pid.identifier
	from #tempTable temp
	join component.pax_identity pid
	   on pid.pax_id = temp.pax_id
	  and pid.pax_identity_type_code = 'OEC'

	;--add in the dms numbers
	with cte as (
		select relationship_id, misc_data
		from component.relationship_misc
		where vf_name = 'DMS_NUMBER'
	)
	update temp
	   set DMS_LIST = replace(
			(select cte.misc_data AS [data()]
			 from cte
			 where cte.relationship_id = temp.relationship_id
			 for XML PATH('')), ' ', ', ')
	from #tempTable temp

	update temp
	set ALERT = isnull((select top 1 1
						from component.VW_MMC_CUSTOMER_CONTACTS cont
						where cont.company_id = @paxid
							and customer_id = temp.pax_id
							and status = 'PENDING') , 0)
	from #tempTable temp

    IF @type IS NOT NULL
    BEGIN
      DELETE FROM #tempTable
      WHERE TYPE IS NULL OR TYPE NOT LIKE '%' + @type + '%'
    END

    IF @name IS NOT NULL
    BEGIN
      DELETE FROM #tempTable
      WHERE NAME IS NULL OR NAME NOT LIKE '%' + @name + '%'
    END

	IF @address IS NOT NULL
    BEGIN
      DELETE FROM #tempTable
      WHERE ADDRESS IS NULL OR ADDRESS NOT LIKE '%' + @address + '%'
    END

	IF @city IS NOT NULL
    BEGIN
      DELETE FROM #tempTable
      WHERE CITY IS NULL OR CITY NOT LIKE '%' + @city + '%'
    END

	IF @state IS NOT NULL
    BEGIN
      DELETE FROM #tempTable
      WHERE STATE IS NULL OR STATE NOT LIKE '%' + @state + '%'
    END

	IF @zip IS NOT NULL
    BEGIN
      DELETE FROM #tempTable
      WHERE ZIP IS NULL OR ZIP NOT LIKE '%' + @zip + '%'
    END

	IF @phone IS NOT NULL
    BEGIN
      DELETE FROM #tempTable
      WHERE PHONE IS NULL OR PHONE NOT LIKE '%' + @phone + '%'
    END


    INSERT INTO #ordered (data_id)
    SELECT tt.tempTableId
    FROM #tempTable tt
    ORDER BY
      CASE @sortDir
      WHEN 'D'
        THEN
          CASE @sortBy
		  WHEN 'type'
            THEN tt.TYPE
          WHEN 'name'
            THEN tt.NAME
          WHEN 'address'
            THEN tt.ADDRESS
          WHEN 'city'
            THEN tt.CITY
          WHEN 'state'
            THEN tt.STATE
		  WHEN 'zip'
            THEN tt.ZIP
		  WHEN 'phone'
            THEN tt.PHONE
          END
      END DESC,
      CASE @sortDir
      WHEN 'A'
        THEN
          CASE @sortBy
		  WHEN 'type'
            THEN tt.TYPE
          WHEN 'name'
            THEN tt.NAME
          WHEN 'address'
            THEN tt.ADDRESS
          WHEN 'city'
            THEN tt.CITY
          WHEN 'state'
            THEN tt.STATE
		  WHEN 'zip'
            THEN tt.ZIP
		  WHEN 'phone'
            THEN tt.PHONE
          END
      END ASC


    SELECT @recordCount = @@ROWCOUNT

	IF @recordsPerPage = -1
	BEGIN
		SET @recordsPerPage = @recordCount
		SET @pageNumber = 1
	END

	IF @recordsPerPage = 0
	BEGIN
		SET @recordsPerPage = 1
	END

	--Make sure the requested page number is within the valid range of pages (if not, then fix it)
	SET @pageCount = (@recordCount + @recordsPerPage - 1) / @recordsPerPage
	IF @pageNumber > @pageCount
	BEGIN SET @pageNumber = @pageCount END
	IF @pageNumber < 1
	BEGIN SET @pageNumber = 1 END

	SET @endId = @pageNumber * @recordsPerPage
	SET @startId = (@endId - @recordsPerPage) + 1


    SELECT
	  [PAX_ID],
	  [TYPE],
      [NAME],
      [ADDRESS],
      [CITY],
      [STATE],
      [ZIP],
      [PHONE],
	  [OEC],
	  [DMS_LIST],
	  [ALERT],
      @recordCount    AS RECORD_COUNT,
      @recordsPerPage AS RECORDS_PER_PAGE,
      @pageNumber     AS PAGE_NUMBER,
      @pageCount      AS PAGE_COUNT,
      @startId        AS START_ID,
      @endId          AS END_ID
    FROM #ordered o
      INNER JOIN #tempTable tt ON tt.tempTableId = o.data_id
    WHERE o.id BETWEEN @startId AND @endId
    ORDER BY o.id

    DROP TABLE #tempTable
    DROP TABLE #ordered

  END

GO
