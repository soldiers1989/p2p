package com.dimeng.p2p.pay.servlets.huifu.ret;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dimeng.framework.config.ConfigureProvider;
import com.dimeng.framework.resource.ResourceProvider;
import com.dimeng.framework.service.ServiceSession;
import com.dimeng.p2p.S62.entities.T6271;
import com.dimeng.p2p.escrow.huifu.HuiFuConstants;
import com.dimeng.p2p.escrow.huifu.entity.bidexchange.BidExchangeEntity;
import com.dimeng.p2p.escrow.huifu.service.BidExchangeManage;
import com.dimeng.p2p.order.PdfFormationExecutor;
import com.dimeng.p2p.order.PreservationExecutor;
import com.dimeng.p2p.order.TenderExchangeExecutor;
import com.dimeng.p2p.service.ContractManage;
import com.dimeng.p2p.variables.defines.SystemVariable;
import com.dimeng.util.StringHelper;
import com.dimeng.util.parser.IntegerParser;

/**
 * 
 * 债权转让后台回调
 * <功能详细描述>
 * 
 * @author  lingyuanjie
 * @version  [版本号, 2016年7月18日]
 */
public class BidExchangeRet extends AbstractHuifuRetServlet
{
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void processPost(HttpServletRequest request, HttpServletResponse response, ServiceSession serviceSession)
        throws Throwable
    {
        logger.info("-------- 债权转让后台回调开始 ----------");
        BidExchangeManage manage = serviceSession.getService(BidExchangeManage.class);
        BidExchangeEntity entity = manage.bidExchangeReturnDecoder(request);
        String orderId = entity != null ? entity.ordId : request.getParameter("OrdId");
        if (entity != null && HuiFuConstants.Common.SUCCESS_CODE.equals(entity.respCode))
        {
            try
            {
                TenderExchangeExecutor executor = getResourceProvider().getResource(TenderExchangeExecutor.class);
                executor.confirm(IntegerParser.parse(entity.ordId), null);
                
                //债权ID
                int zcbId = Integer.parseInt(entity.merPriv);
                
                ResourceProvider resourceProvider = getResourceProvider();
                final ConfigureProvider configureProvider = resourceProvider.getResource(ConfigureProvider.class);
                //生成债权转让合同PDF并保全
                if (Boolean.parseBoolean(configureProvider.getProperty(SystemVariable.IS_SAVE_TRANSFER_CONTRACT)))
                {
                    try
                    {
                        ContractManage contractManage = serviceSession.getService(ContractManage.class);
                        List<T6271> t6271List = contractManage.getClaimList(zcbId);
                        if (null != t6271List)
                        {
                            String charset = resourceProvider.getCharset();
                            PdfFormationExecutor cpfe = resourceProvider.getResource(PdfFormationExecutor.class);
                            /*调用第三方合同保全执行器*/
                            PreservationExecutor preservationExecutor =
                                resourceProvider.getResource(PreservationExecutor.class);
                            Map<String, Object> valueMap = null;
                            StringBuffer sb = new StringBuffer();
                            StringBuffer sbs = new StringBuffer();
                            for (T6271 t6271 : t6271List)
                            {
                                valueMap = contractManage.getClaimContentMap(zcbId, t6271.F02);
                                if (null != valueMap)
                                {
                                    sb.setLength(0);
                                    sb.append(configureProvider.getProperty(SystemVariable.CONTRACT_TEMPLATE_HTML_HEADER));
                                    sb.append((String)valueMap.get("dzxy_content"));
                                    sb.append(configureProvider.getProperty(SystemVariable.CONTRACT_TEMPLATE_HTML_FOOTER));
                                    String path =
                                        cpfe.createHTML(valueMap,
                                            "contract",
                                            (String)valueMap.get("dzxy_xymc"),
                                            sb.toString(),
                                            charset,
                                            (String)valueMap.get("xy_no"));
                                    if (!StringHelper.isEmpty(path))
                                    {
                                        sbs.setLength(0);
                                        sbs.append(configureProvider.getProperty(SystemVariable.SITE_REQUEST_PROTOCOL))
                                            .append(configureProvider.getProperty(SystemVariable.SITE_DOMAIN))
                                            .append(request.getContextPath())
                                            .append("/");
                                        String pdfPath = cpfe.convertHtml2Pdf(path, sbs.toString(), charset);
                                        t6271.F04 = (String)valueMap.get("xy_no");
                                        t6271.F09 = pdfPath;
                                        contractManage.updateT6271PdfPathNo(t6271);
                                        logger.info("生成pdf合同文档成功！");
                                        preservationExecutor.contractPreservation(t6271.F01);
                                    }
                                }
                            }
                        }
                        
                    }
                    catch (Exception e)
                    {
                        logger.error("生成合同保全pdf文件失败！-------- BidExchangeRet.processPost()", e);
                    }
                }
            }
            catch (Exception e)
            {
                logger.error(String.format("债权转让订单%s异常", entity.ordId), e);
            }
        }
        printMark(response, orderId);
    }
    
}
