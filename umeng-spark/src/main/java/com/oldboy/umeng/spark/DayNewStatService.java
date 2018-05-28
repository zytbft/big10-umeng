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
public class DayNewStatService {
	public static void main(String[] args) {
		//创建配置
		SparkConf conf = new SparkConf() ;
		conf.setAppName("stat") ;
		conf.setMaster("local[4]") ;

		//创建SparkSession
		SparkSession spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
		spark.sql("use big10_umeng").collect();
		//执行
		spark.sql("drop table if exists stat_daynew_20180525").show();

		//注册临时函数
		List<String> funcs = ResourceUtil.loadResoureLines("register_temp_functions.sql") ;
		for(String func  : funcs){
			spark.sql(func).collect() ;
		}

		//查询统计结果,入库
		Dataset<Row> df = spark.sql(ResourceUtil.loadResoureString("stat_daynew.sql")) ;

		df.foreachPartition(new ForeachPartitionFunction<Row>() {

			public void call(Iterator<Row> t) throws Exception {
				Class.forName("com.mysql.jdbc.Driver") ;
				String url = "jdbc:mysql://192.168.231.1:3306/big9" ;
				String user = "root" ;
				String pass = "root" ;
				Connection conn = DriverManager.getConnection(url ,user , pass) ;
				conn.setAutoCommit(false);
				String sql = "insert into stat_daynew values(?,?,?,?,?,?)" ;
				PreparedStatement ppst = conn.prepareStatement(sql) ;
				while(t.hasNext()){
					Row row = t.next();
					ppst.setString(1 , row.getString(0));
					ppst.setString(2 , row.getString(1));
					ppst.setString(3 , row.getString(2));
					ppst.setString(4 , row.getString(3));
					ppst.setString(5 , row.getString(4));
					ppst.setLong(6 , row.getLong(5)) ;
					ppst.executeUpdate();
				}
				conn.commit();
				ppst.close();
				conn.close();
			}
		});

		Object arr = df.collect();
		System.out.println();
	}
}
