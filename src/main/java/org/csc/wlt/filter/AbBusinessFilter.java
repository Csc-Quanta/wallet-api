package org.csc.wlt.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import onight.tfw.async.CompleteHandler;
import onight.tfw.otransio.api.beans.FramePacket;

@Slf4j
@Data
public abstract class AbBusinessFilter {
    /**
     * 前置处理
     * @param pack
     * @return
     */
     public boolean preRoute(FramePacket pack, CompleteHandler handler){
         return true;
     }

    /**
     * 返回处理
     * @param pack
     * @return
     */
    public boolean onComplete(FramePacket pack){
        return true;
    }

    /**
     * 最后处理
     * @param pack
     * @return
     */
    public boolean postRoute(FramePacket pack,CompleteHandler handler){
        return true;
    }

}
