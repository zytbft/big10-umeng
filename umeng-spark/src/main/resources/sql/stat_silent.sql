select
  t1.tenantid ,
  t1.appid ,
  t1.appversion ,
  t1.appplatform ,
  t1.ostype ,
  t1.devicestyle ,
  dateformat(daybegin(-1),'yyyy/MM/dd') day ,
  t1.cnt
from
(
  select
    tenantid        ,
    appid				    ,
    appversion			,
    appplatform			,
    ostype				  ,
    devicestyle			,
    deviceid        ,
    count(createdatms) cnt ,
    min(createdatms)   atime
  from
    startuplogs
  WHERE
    concat(ym,day) <= dateformat(daybegin(-1) , 'yyyyMMdd')
  group by
    tenantid , appid ,  appversion , appplatform , ostype , devicestyle  , deviceid
  with
    cube
)t1
where
  t1.tenantid is not null
  and
  t1.appid is not null
  and
  cnt = 1
  and
  atime <= daybegin(-7)
order BY
  t1.tenantid  ,t1.appid ,  t1.appversion , t1.appplatform , t1.ostype , t1.devicestyle
