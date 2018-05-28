SELECT
	t3.tenantid ,
	t3.appid ,
	t3.appversion ,
	t3.appplatform ,
	t3.ostype ,
	t3.devicestyle,
	count(t3.deviceid)
from
	(
		SELECT
			t1.tenantid ,
			t1.appid ,
			t1.appversion ,
			t1.appplatform ,
			t1.ostype ,
			t1.devicestyle ,
			t1.deviceid
		FROM
			(
				SELECT
					t0.tenantid ,
					t0.appid ,
					t0.appversion ,
					t0.appplatform ,
					t0.ostype ,
					t0.devicestyle ,
					t0.deviceid
				FROM
					(
						SELECT
							tenantid ,
							appid ,
							appversion ,
							appplatform ,
							ostype ,
							devicestyle ,
							deviceid,
							max(createdatms) lasttime
						FROM
							startuplogs
						WHERE
							concat(ym,day) <= dateformat(weekbegin(daybegin(-1)),'yyyyMMdd')
						group BY
							tenantid ,
							appid ,
							appversion ,
							appplatform ,
							ostype ,
							devicestyle ,
							deviceid
						WITH
							cube
					)t0
				where
					t0.lasttime <= weekbegin(daybegin(-1),-1)
					and
					t0.tenantid is not NULL
					AND
					t0.appid is not NULL
			) t1
		where
			(
				t1.tenantid ,
				t1.appid ,
				t1.appversion ,
				t1.appplatform ,
				t1.ostype ,
				t1.devicestyle ,
				t1.deviceid
			)
			in
			(
				select
					t20.tenantid ,
					t20.appid ,
					t20.appversion ,
					t20.appplatform ,
					t20.ostype ,
					t20.devicestyle,
					t20.deviceid
				from
					(
						select
							tenantid        ,
							appid				    ,
							appversion			,
							appplatform			,
							ostype				  ,
							devicestyle			,
							deviceid
						from
							startuplogs
						WHERE
							concat(ym , day) >= dateformat(weekbegin(daybegin(-1),-1) , 'yyyyMMdd')
							and
							concat(ym , day) < dateformat(weekbegin(daybegin(-1), 0) , 'yyyyMMdd')
						group by
							tenantid , appid ,  appversion , appplatform , ostype , devicestyle,deviceid
						with
							cube
					)t20
				where
					t20.tenantid is not null
					and
					t20.appid is not null
					AND
					t20.deviceid is not null
			)
	)t3
group BY
	t3.tenantid ,
	t3.appid ,
	t3.appversion ,
	t3.appplatform ,
	t3.ostype ,
	t3.devicestyle