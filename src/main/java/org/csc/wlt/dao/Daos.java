package org.csc.wlt.dao;

import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wlt.entity.*;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.iPojoBean;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ojpa.api.DomainDaoSupport;
import onight.tfw.ojpa.api.IJPAClient;
import onight.tfw.ojpa.api.OJpaDAO;
import onight.tfw.ojpa.api.annotations.StoreDAO;

@iPojoBean
@Provides(specifications = { IJPAClient.class, ActorService.class }, strategy = "SINGLETON")
@Instantiate(name = "daos")
@Slf4j
@Data
public class Daos implements ActorService, IJPAClient {

//	@ActorRequire
//	public SysDBProvider dbprovider;

	@StoreDAO
	public OJpaDAO<CscWltAddress> wltAddressDao;

	@StoreDAO
	public OJpaDAO<CscWltTx> wltTxDao;

	@StoreDAO
	public OJpaDAO<CscWltContract> wltContractDao;

	@StoreDAO
	public OJpaDAO<CscWltParameter> wltParameterDao;

	@StoreDAO
	public OJpaDAO<CscWltCoinInfo> wltCoinInfoDao;

	@StoreDAO
	public OJpaDAO<CscWltChannelInfo> wltChannelInfoDao;

	@StoreDAO
	public OJpaDAO<CscWltCoinRate> wltCoinRateDao;

	@StoreDAO
	public OJpaDAO<CscWltAppParam> wltAppParamDao;

	@StoreDAO
	public OJpaDAO<CscWltUser> wltUserDao;

	@StoreDAO
	public OJpaDAO<CscWltData> wltDateDao;

	@StoreDAO
	public OJpaDAO<CscCodeMap> codeMapDao;

	@StoreDAO
	public OJpaDAO<CscSwitchCode> switchCodeDao;

	public boolean ready = false;

	@Override
	public void onDaoServiceAllReady() {
		ready = true;
		log.debug("AllDao Ready........");
	}

	@Override
	public void onDaoServiceReady(DomainDaoSupport arg0) {

	}
}
