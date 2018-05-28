select
  t1.tenantid ,
  t1.appid ,
  t1.appversion ,
  t1.appplatform ,
  t1.ostype ,
  t1.devicestyle ,
  dateformat(monthbegin(daybegin(-1)),'yyyyMM') month ,
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
    ym = dateformat(monthbegin(daybegin(-1)),'yyyyMM')
  group by
    tenantid , appid ,  appversion , appplatform , ostype , devicestyle
  with
    cube
)t1
where
  t1.tenantid is not null and t1.appid is not null
order BY
  t1.tenantid  ,t1.appid ,  t1.appversion , t1.appplatform , t1.ostype , t1.devicestyle
