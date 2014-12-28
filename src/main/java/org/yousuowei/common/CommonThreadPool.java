package org.yousuowei.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 存放公共线程池等其他公共资源模块
 * 
 * @ClassName: Common
 * @Description: TODO
 * @author: jie
 * @date: 2014-4-23 下午5:19:19
 */
public class CommonThreadPool {

	public static ExecutorService linkedExecutor = Executors
			.newSingleThreadExecutor();

	public static void linkedExecutor(Runnable runner) {
		linkedExecutor.execute(runner);
	}
}
