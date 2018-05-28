import java.io.FileOutputStream

/**
  * Created by Administrator on 2018/5/27.
  */
object GenScriptFiles {
    def main(args: Array[String]): Unit = {
        val arr = Array[String]("startup", "event" , "error", "usage" , "page")
        val sqlStr =
            """
use big10_umeng ;
-- #{logtype}log
insert into #{logtype}logs partition(ym,day,hm)
select
t.* ,
date_format(cast(t.createdatms as timestamp) , 'yyyyMM') ,
date_format(cast(t.createdatms as timestamp) , 'dd') ,
date_format(cast(t.createdatms as timestamp) , 'HHmm')
from
(
    select
        fork#{logtype}log(clienttimems ,servertimestr , clientip , log)

    from
        raw_logs

    where ym=#ym and day = #day and hm = #hm

)t
            """.stripMargin

        val shStr =
            """#!/bin/bash
cd /home/centos/big10_umeng
time=`cat _time`
ym=`echo -n $time| awk -F '-' '{print $1}'`
day=`echo -n $time| awk -F '-' '{print $2}'`
hm=`echo -n $time| awk -F '-' '{print $3}'`
cp fork_errorlogs.sql _fork_errorlogs.sql
sed -i 's/#ym/'$ym'/g' _fork_errorlogs.sql
sed -i 's/#day/'$day'/g' _fork_errorlogs.sql
sed -i 's/#hm/'$hm'/g' _fork_errorlogs.sql
hive -f _fork_errorlogs.sql"""

        //创建五个文件sql脚本
        for(x <- arr){
            val fout = new FileOutputStream("E:\\大数据10期班\\big10-徐-07umeng-day03\\fork_" +x + "logs.sql") ;
            val tmp = sqlStr.replace("#{logtype}" , x)
            fout.write(tmp.getBytes)
            fout.flush()
            fout.close()
        }

        //创建五个文件shell脚本
        for(x <- arr){
            val fout = new FileOutputStream("E:\\大数据10期班\\big10-徐-07umeng-day03\\fork_" +x + "logs.sh") ;
            //将回车符替换成换行符
            val tmp = shStr.replace("#{logtype}" , x).replace("\r" , "\n")
            fout.write(tmp.getBytes)
            fout.flush()
            fout.close()
        }
    }
}
