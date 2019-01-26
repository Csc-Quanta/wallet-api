package org.csc.wlt.task;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActWrapper;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.proxy.IActor;
import org.csc.wlt.dao.Daos;
import org.csc.wlt.helper.MarketInfoHelper;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 竞拍状态定时任务
 * 
 * @author Moon
 * @date 2018-05-10
 */
@NActorProvider
@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
public class PropertyJobHandle extends ActWrapper implements ActorService, IActor {

	private static ScheduledThreadPoolExecutor service = null;
	private final static int POOL_SIZE = 100;

	// 间隔时间
	private final int numIntervalTime = 10;
	// 线程大小
	private final int numThredSize = 6;
	private final int numZero = 0;
	private boolean bool = true;

	@ActorRequire(name = "daos", scope = "global")
	Daos dao;

	@ActorRequire(name = "huoBiMarketInfoHelper", scope = "global")
	MarketInfoHelper marketInfoHelper;
/*
	@Override
	public void onDaoServiceAllReady() {
		try {
			log.info("PropertyBidJobHandle start ");
			new Thread(() -> {

				while (dao == null || (dao != null && dao.wltCoinRateDao == null)) {
					try {
						Thread.sleep(5000L);
						log.info("waiting for loading the propertyHelper bean ....");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				log.info("the dao beans loading success....");
				service = getService();
				if (service != null) {
					service.scheduleWithFixedDelay(new SyncCoinRateTask(dao.wltCoinRateDao, marketInfoHelper), numZero,
							10, TimeUnit.MINUTES);
				}
			}).start();
		} catch (Exception e) {
			log.warn("PropertyIncomeJobHandle onDaoServiceAllReady error...", e);
		}
	}*/

	public ScheduledThreadPoolExecutor getService() {
		try {
			if (service != null) {
				service.shutdown();
			}
			service = new ScheduledThreadPoolExecutor(numThredSize);
		} catch (Exception e) {
			log.warn("PropertyIncomeJobHandle getService error...", e);
		}

		return service;
	}
}
