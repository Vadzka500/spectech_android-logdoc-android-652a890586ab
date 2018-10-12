package com.logdoc.delivery.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by
 */

public class ThreadExecutors {

	private ExecutorService dbThreadExecutor = Executors.newSingleThreadExecutor();

	public ExecutorService dbExecutor() {
		return dbThreadExecutor;
	}

}
