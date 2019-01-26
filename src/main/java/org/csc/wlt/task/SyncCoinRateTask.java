package org.csc.wlt.task;

import java.util.ArrayList;
import java.util.List;

import org.csc.wlt.entity.CscWltCoinRate;
import org.csc.wlt.helper.MarketInfoHelper;
import org.csc.wlt.model.CscWltCoinRateModel;

import lombok.extern.slf4j.Slf4j;
import onight.tfw.ojpa.api.OJpaDAO;

/**
 * 定时任务生成指纹信息随机数
 * 
 * @author Moon
 * @date 2018-05-15
 */
@Slf4j
public class SyncCoinRateTask implements Runnable {
	// 防止收益数据冲突

	private OJpaDAO<CscWltCoinRate> cscWltCoinRateDao;
	private MarketInfoHelper marketInfoHelper;

	public SyncCoinRateTask(OJpaDAO<CscWltCoinRate> cscWltCoinRateDao, MarketInfoHelper marketInfoHelper) {
		super();
		this.cscWltCoinRateDao = cscWltCoinRateDao;
		this.marketInfoHelper = marketInfoHelper;
	}

	@Override
	public void run() {
		log.info("SyncCoinRateTask start ....");
		try {
			List<CscWltCoinRate> cscWltCoinRates = (List) cscWltCoinRateDao.findAll(new ArrayList<>());
			if (cscWltCoinRates == null || cscWltCoinRates.isEmpty()) {
				log.info("数据库中没有配置需要查询行情的币信息");
				return;
			}
			List<CscWltCoinRateModel> list = marketInfoHelper.queryMarketInfoHelper();
			if (list == null) {
				return;
			}
			List<CscWltCoinRate> updateList = new ArrayList<>();
			cscWltCoinRates.forEach(cscWltCoinRate -> {
				list.forEach(cscWltCoinRateModel -> {
					if (cscWltCoinRate.getSymbol().equals(cscWltCoinRateModel.getSymbol())) {
						CscWltCoinRate updateCoinRate = new CscWltCoinRate();
						updateCoinRate.setCoinRate(cscWltCoinRateModel.getCoinRate());
						updateCoinRate.setSymbol(cscWltCoinRateModel.getSymbol());
						updateList.add(updateCoinRate);
					}
				});
			});
			cscWltCoinRateDao.batchUpdate((List) updateList);
		} catch (Exception e) {
			log.error(" SyncCoinRateTask error->", e);
		}
		log.info("SyncCoinRateTask ended ....");

	}

}
