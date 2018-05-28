select
  t1.tenantid ,
  t1.appid ,
  t1.appversion ,
  t1.appplatform ,
  t1.ostype ,
  t1.devicestyle ,
  dateformat(daybegin(-3),'yyyy/MM/dd') day ,
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
    count(distinct deviceid) cnt
  from
    startuplogs
  WHERE
    ym = dateformat(daybegin(-3),'yyyyMM') and day = dateformat(daybegin(-3),'dd')
  group by
    tenantid , appid ,  appversion , appplatform , ostype , devicestyle
  with
    cube
)t1
where
  t1.tenantid is not null and t1.appid is not null
order BY
  t1.tenantid  ,t1.appid ,  t1.appversion , t1.appplatform , t1.ostype , t1.devicestyle
