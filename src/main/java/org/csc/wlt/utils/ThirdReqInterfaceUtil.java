package org.csc.wlt.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.csc.wlt.enums.CoinSymbolEnum;
import org.csc.wlt.helper.ThirdReqInterface;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
@Component(immediate = true)
@Instantiate(name = "thirdReqInterfaceUtil")
@Data
public class ThirdReqInterfaceUtil implements ActorService {
	@ActorRequire(name = "ethThirdReqInterface")
	private ThirdReqInterface ethThirdReqInterface;
	@ActorRequire(name = "eosThirdReqInterface")
	private ThirdReqInterface eosThirdReqInterface;
	@ActorRequire(name = "btcThirdReqInterface")
	private ThirdReqInterface btcThirdReqInterface;

	Map<String, ThirdReqInterface> interfaceMap = new HashMap<>();

	@Validate
	public void startUp(){
		/*new Thread(()->{
			while (ethThirdReqInterface == null){
				try {
					log.info("service not ready ethThirdReqInterface={}",ethThirdReqInterface);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			interfaceMap.put(CoinSymbolEnum.ETH.name(), ethThirdReqInterface);
			interfaceMap.put(CoinSymbolEnum.BTC.name(), btcThirdReqInterface);
			interfaceMap.put(CoinSymbolEnum.EOS.name(), eosThirdReqInterface);
		}).start();*/
	}
}
