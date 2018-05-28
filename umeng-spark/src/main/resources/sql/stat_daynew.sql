-- 统计日新
		select
			t2.*
		from
		(
			select
				t1.appid ,
				t1.appversion ,
				t1.appplatform ,
				t1.ostype ,
				t1.devicestyle ,
				count(deviceid) cnt
			from
			(
				select
					appid				,
					appversion			,
					appplatform			,
					ostype				,
					devicestyle			,
					deviceid			,
					min(createdatms)	ftime
				from
					startuplogs
				group by
					appid ,  appversion , appplatform , ostype , devicestyle,deviceid
				with cube
			)t1
			where
				dateformat(t1.ftime) = '2018/05/25'
				and appid is not null
				and deviceid is not null
			group by
				appid ,  appversion , appplatform , ostype , devicestyle
		)t2
		where
			t2.appid is not null
		order by
			appid ,  appversion , appplatform , ostype , devicestyle