
select
	t3.tenantid				,
	t3.appid				,
	t3.appversion			,
	t3.appplatform			,
	t3.ostype				,
	t3.devicestyle			,
	count(t3.deviceid)	  cnt
from
	(
		select
			t2.tenantid				,
			t2.appid				,
			t2.appversion			,
			t2.appplatform			,
			t2.ostype				,
			t2.devicestyle			,
			t2.deviceid				,
			count(t2.week)		weeks
		from
			(
				select
					t1.tenantid				,
					t1.appid				,
					t1.appversion			,
					t1.appplatform			,
					t1.ostype				,
					t1.devicestyle			,
					t1.deviceid				,
					t1.week
				from
					(
						select
							tenantid			,
							appid				,
							appversion			,
							appplatform			,
							ostype				,
							devicestyle			,
							deviceid			,
							dateformat(weekbegin(createdatms)) week
						from
							startuplogs
						WHERE
							concat(ym , day) >= dateformat(weekbegin(daybegin(-1),-2) , 'yyyyMMdd')
							and
							concat(ym , day) < dateformat(weekbegin(daybegin(-1), 1) , 'yyyyMMdd')
						group by
							tenantid , appid ,  appversion , appplatform , ostype , devicestyle,deviceid , dateformat(weekbegin(createdatms))
						with
							cube
					)t1
				where
					t1.tenantid is not null
					and
					t1.appid is not null
					and
					t1.deviceid is not null
					and
					t1.week is not null
			)t2
		group by
			tenantid , appid ,  appversion , appplatform , ostype , devicestyle,deviceid
			having weeks = 3
	)t3
group by
	t3.tenantid				,
	t3.appid				,
	t3.appversion			,
	t3.appplatform			,
	t3.ostype				,
	t3.devicestyle


