package org.csc.wlt.config;

import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.fc.zippo.ordbutils.rest.RestfulDBStoreProvider;
import org.osgi.framework.BundleContext;

import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.iPojoBean;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ojpa.api.StoreServiceProvider;
import onight.tfw.otransio.api.PacketFilter;
import onight.tfw.proxy.IActor;

@iPojoBean
@Slf4j
@Provides(specifications = { IActor.class, StoreServiceProvider.class, ActorService.class }, strategy = "SINGLETON")
@Instantiate
public class HZQOrclStoreProvider extends RestfulDBStoreProvider implements IActor, ActorService {

	BundleContext bundleContext;

	public HZQOrclStoreProvider(BundleContext bundleContext) {
		super(bundleContext);
		this.bundleContext = bundleContext;
	}

	@Override
	public String[] getContextConfigs() {
		// return new String[] { "/SpringContext-daoConfig-sys.xml",
		// "/SpringContext-daoConfig-biz.xml",
		// "/SpringContext-daoConfig-mtr.xml",
		// "/SpringContext-daoConfig-bas.xml",
		// "/SpringContext-daoConfig-act.xml",
		// "/SpringContext-daoConfig-bll.xml",
		// "/SpringContext-daoConfig-chg.xml",
		// "/SpringContext-daoConfig-ctm.xml",
		// "/SpringContext-daoConfig-mrd.xml",
		// "/SpringContext-daoConfig-psm.xml"};
		return new String[] { "/SpringContext-daoConfig-wlt.xml" };
	}

	@Validate
	public void startup() {
		super.startup();
		log.debug("startup WltOrmStoreProvider");
	}

	@Override
	public String getProviderid() {
		return "wltorm";
	}

	@Override
	public String[] getWebPaths() {
		return new String[] { "/wltrest" };
	}

	@Override
	public String[] getCtrlPaths() {
		return new String[] { "org.csc.wlt" };
	}

	@Override
	public PacketFilter[] getFilters() {
		return new PacketFilter[] {};
	}

}