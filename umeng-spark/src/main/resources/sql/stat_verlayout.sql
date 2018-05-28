--版本分布
--统计每个appid在不同版本下的日活、日新、启动次数
--查询mysql的统计结果即可

select
  appid ,appversion ,day , cnt
from
  stat_tnt023_dayact
WHERE
  day >= '2018/05/21'
  and
  appid is not null
  AND
  appversion is not null
  and
  appplatform is null
  and
  ostype      is NULL
  and
  devicestyle is null
