package org.csc.wlt.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.osgi.annotation.iPojoBean;
import onight.tfw.async.CompleteHandler;
import onight.tfw.ntrans.api.ActWrapper;
import onight.tfw.ntrans.api.ActorService;
import onight.tfw.ntrans.api.annotation.ActorRequire;
import onight.tfw.otransio.api.PacketFilter;
import onight.tfw.otransio.api.SimplePacketFilter;
import onight.tfw.otransio.api.beans.FramePacket;
import org.apache.felix.ipojo.annotations.Provides;
import org.csc.wlt.common.Constants;
import org.csc.wlt.utils.FilterUtil;
import org.fc.zippo.filter.exception.FilterException;

@iPojoBean
@Slf4j
@Provides(specifications = { PacketFilter.class, ActorService.class }, strategy = "SINGLETON")
@Data
public class SystemFilter extends SimplePacketFilter implements ActorService  {

    @ActorRequire(name = "filterUtil", scope = "global")
    FilterUtil filterUtil;

    @Override
    public boolean preRoute(ActWrapper actor, FramePacket pack, CompleteHandler handler) throws FilterException {
        AbBusinessFilter filter;
        String grantToken = pack.getHttpServerletRequest().getHeader(Constants.GRANT_TOKEN);
        String appAuth = pack.getHttpServerletRequest().getHeader(Constants.X_APP_AUTH);
        log.info("SystemFilter preRoutr grantToken:{}",grantToken);
        log.info("SystemFilter preRoutr appAuth:{}",appAuth);
        return (filter = filterUtil.getFilterMap().get(pack.getModuleAndCMD())) == null || filter.preRoute(pack,handler);
    }
    @Override
    public boolean postRoute(ActWrapper actor, FramePacket pack, CompleteHandler handler) throws FilterException {
        AbBusinessFilter filter;
        return (filter = filterUtil.getFilterMap().get(pack.getModuleAndCMD())) == null || filter.postRoute(pack,handler);
    }
    @Override
    public boolean onComplete(ActWrapper actor, FramePacket completepack) throws FilterException {
        AbBusinessFilter filter;
        return (filter = filterUtil.getFilterMap().get(completepack.getModuleAndCMD())) == null || filter.onComplete(completepack);
    }
}
