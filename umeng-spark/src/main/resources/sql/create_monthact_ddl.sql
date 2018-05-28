create table if not EXISTS stat_%s_monthact
(
  appid varchar(100)      ,
  appversion varchar(20)  ,
  appplatform varchar(20) ,
  ostype varchar(20)      ,
  devicestyle varchar(20) ,
  month varchar(20) ,
  cnt         bigint
)