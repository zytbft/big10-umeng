package com.oldboy.umeng.phone.test;

import com.oldboy.umeng.common.util.DictUtil;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/24.
 */
public class TestDict {

	@Test
	public void test1(){
		Map<String, java.util.List<String>> dict = DictUtil.getDict();
		System.out.println(dict );
	}

	@Test
	public void test2(){
		System.out.println(DictUtil.getRandString("appid")) ;
		System.out.println(DictUtil.getRandString("appid")) ;
		System.out.println(DictUtil.getRandString("appid")) ;
		System.out.println(DictUtil.getRandString("appid")) ;
		System.out.println(DictUtil.getRandString("appid")) ;
	}


}
