package com.oldboy.umeng.spark;

import com.oldboy.umeng.common.util.ResourceUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.ForeachPartitionFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class WeekActiveStatService {
	public static void main(String[] args) {
		//创建配置
		SparkConf conf = new SparkConf() ;
		conf.setAppName("stat") ;
		conf.setMaster("local[4]") ;

		//创建SparkSession
		final SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		spark.sql("use big10_umeng").collect();
		//注册临时函数
		List<String> funcs = ResourceUtil.loadResoureLines("sql/register_temp_functions.sql") ;
		for(String func  : funcs){
			spark.sql(func).collect() ;
		}

		//查询统计结果,入库
		Dataset<Row> df = spark.sql(ResourceUtil.loadResoureString("sql/stat_weekact.sql")) ;
//		df.show(10000,false) ;

		df.foreachPartition(new ForeachPartitionFunction<Row>() {

			public void call(Iterator<Row> t) throws Exception {
				Class.forName("com.mysql.jdbc.Driver") ;
				String url = "jdbc:mysql://192.168.231.1:3306/big9" ;
				String user = "root" ;
				String pass = "root" ;
				Connection conn = DriverManager.getConnection(url ,user , pass) ;
				conn.setAutoCommit(false);

				//当前租户
				String curr_tntid = "" ;
				PreparedStatement ppst = null ;

				while(t.hasNext()){
					Row row = t.next();
					//提取租户
					String tntid = row.getString(0) ;
					//是否是同一租户
					if(!tntid.equals(curr_tntid)){
						//在mysql中准备该租户的统计表(日活)
						String create_ddl = String.format(ResourceUtil.loadResoureString("sql/create_weekact_ddl.sql"), tntid);
						conn.createStatement().execute(create_ddl);

						//先删除之前的删除
						String deleteSQL = String.format(ResourceUtil.loadResoureString("sql/delete_weekact.sql"),
								tntid ,row.getString(6));
						conn.createStatement().execute(create_ddl);

						//insert into ....
						String insertSQL = String.format(ResourceUtil.loadResoureString("sql/insert_weekact.sql"), tntid);
						ppst = conn.prepareStatement(insertSQL);
					}
					ppst.setString(1 , row.getString(1));
					ppst.setString(2 , row.getString(2));
					ppst.setString(3 , row.getString(3));
					ppst.setString(4 , row.getString(4));
					ppst.setString(5 , row.getString(5));
					ppst.setString(6 , row.getString(6));
					ppst.setLong(7 , row.getLong(7));
					ppst.executeUpdate();
				}
				conn.commit();
				if(ppst != null){
					ppst.close();
				}
				conn.close();
			}
		});
	}
}
