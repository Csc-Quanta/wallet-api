/*
package org.csc.wlt.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import onight.osgi.annotation.NActorProvider;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.outils.conf.PropHelper;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Invalidate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Validate;
import org.csc.wlt.common.Constants;
import org.osgi.framework.BundleContext;

@NActorProvider
@Slf4j
@Data
@Instantiate(name = "cacheUtil")
@Provides(specifications = { ActorService.class }, strategy = "SINGLETON")
public class CacheUtil implements ActorService {

    private CacheManager singletonManager;
    private PropHelper props;
    BundleContext bundleContext;
    public CacheUtil(BundleContext bundleContext) {
        this.bundleContext = bundleContext;
    }
    */
/**
     * 获得短信cache
     * @return
     *//*

    public Cache getSmsCache(){
        return singletonManager.getCache(Constants.CACHE_SMS);
    }

    */
/**
     * 获取用户的缓存
     * @return
     *//*

    public Cache getUserLoginCache(){
        return singletonManager.getCache(Constants.CACHE_USER_LOGIN);
    }

    */
/**
     * 根据缓存名字获取缓存
     * @param cacheName
     * @return
     *//*

    public Cache getCache(String cacheName){
        return singletonManager.getCache(cacheName);
    }
    @Validate
    public void startUp(){
        log.info("cacheManage init start。。。");
        props = new PropHelper(bundleContext);
        System.setProperty(net.sf.ehcache.CacheManager.ENABLE_SHUTDOWN_HOOK_PROPERTY,"true");
        String filePath = props.get("org.csc.cache.disk.path","/home/csc/ecache");
        Configuration configuration = new Configuration()
                .diskStore( new DiskStoreConfiguration().path(filePath))
                .cache(new CacheConfiguration().name(Constants.CACHE_USER_LOGIN)
                .maxEntriesLocalHeap(5000)
                .eternal(false)
                .timeToIdleSeconds(30*24*3600)
                .timeToLiveSeconds(30*24*3600)
                .maxEntriesLocalDisk(50000)
                .diskPersistent(true)
                .overflowToDisk(true)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU.toString()));
        singletonManager = CacheManager.newInstance(configuration);
        singletonManager.addCache(new Cache(Constants.CACHE_SMS,5000,false,false,3600,3600));
        log.info("cacheManage int end。。。");
    }
}
*/
