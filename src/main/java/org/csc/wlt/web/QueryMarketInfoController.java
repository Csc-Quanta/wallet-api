/*
 * package org.csc.wlt.web;
 * 
 * import com.alibaba.fastjson.JSON; import lombok.Data; import
 * lombok.EqualsAndHashCode; import lombok.extern.slf4j.Slf4j; import
 * onight.oapi.scala.commons.SessionModules; import
 * onight.osgi.annotation.NActorProvider; import
 * onight.tfw.async.CompleteHandler; import
 * onight.tfw.ntrans.api.annotation.ActorRequire; import
 * onight.tfw.otransio.api.PacketHelper; import
 * onight.tfw.otransio.api.beans.FramePacket; import
 * org.apache.commons.lang3.StringUtils; import org.csc.wallet.service.Wallet;
 * import org.csc.wlt.model.CscMarketModel; import
 * org.csc.wlt.service.QueryMarketInfoService;
 * 
 * import java.util.List; import java.util.Map;
 * 
 * @NActorProvider
 * 
 * @Slf4j
 * 
 * @Data
 * 
 * @EqualsAndHashCode(callSuper=false) public class QueryMarketInfoController
 * extends SessionModules<Wallet.ReqGetMarketInfo> {
 * 
 * @ActorRequire(name = "queryMarketInfoService") private QueryMarketInfoService
 * queryMarketInfoService;
 * 
 * @Override public String[] getCmds() { return new String[] {
 * Wallet.PWLTCommand.QMK.name() }; }
 * 
 * @Override public String getModule() { return Wallet.PWLTModule.WLT.name(); }
 * 
 * @Override public void onPBPacket(final FramePacket pack, final
 * Wallet.ReqGetMarketInfo reqGetMarketInfo, final CompleteHandler handler) {
 * Wallet.RespGetMarketInfo.Builder ret = Wallet.RespGetMarketInfo.newBuilder();
 * try{ log.info("接口入参数据：{}", reqGetMarketInfo.toString()); String walletType =
 * reqGetMarketInfo.getWalletType(); //判断参数是否为空
 * if(StringUtils.isEmpty(walletType)) {
 * ret.setRplCode(-1).setMsg("param is null");
 * handler.onFinished(PacketHelper.toPBReturn(pack, ret.build())); return; }
 * CscMarketModel cscMarketModel =
 * queryMarketInfoService.queryMarketInfo(walletType); if(cscMarketModel ==
 * null){ ret.setRplCode(-1); ret.setMsg("数据配置错误");
 * handler.onFinished(PacketHelper.toPBReturn(pack, ret.build())); return; }
 * ret.setRplCode(1); ret.setRmbRate(cscMarketModel.getRmbRate());
 * ret.setMainRate(cscMarketModel.getMainRate()); List<Map> maps =
 * cscMarketModel.getList(); for(int i =0;i<maps.size();i++){
 * Wallet.MarketInfDetail.Builder builder = Wallet.MarketInfDetail.newBuilder();
 * builder.setCoinRate(maps.get(i).get("coinRate").toString());
 * builder.setCoinSymbol(maps.get(i).get("coinSymbol").toString());
 * ret.addList(i, builder.build()); } }catch (Exception e){ ret.setRplCode(-1);
 * log.error("查询账户余额 异常：",e); } handler.onFinished(PacketHelper.toPBReturn(pack,
 * ret.build())); } }
 */
