<%@page import="com.dimeng.p2p.modules.base.front.service.entity.AdvertSpscRecord"%>
<%@page import="com.dimeng.p2p.modules.base.front.service.AdvertisementManage"%>
<%@page import="com.dimeng.p2p.S50.enums.T5021_F07"%>
<%@page import="com.dimeng.p2p.modules.bid.front.service.entity.IndexStatic"%>
<%@page import="com.dimeng.p2p.modules.bid.front.service.BidManage"%>
<%@page import="com.dimeng.p2p.repeater.policy.OperateDataManage"%>
<%@page import="com.dimeng.p2p.S70.entities.T7051"%>
<%@page import="com.dimeng.p2p.S70.entities.T7050"%>
<%@page import="com.dimeng.p2p.S61.entities.T6196"%>
<%@page import="com.dimeng.p2p.modules.bid.front.service.entity.BidRecord"%>
<%@page import="com.dimeng.p2p.front.servlets.yysj.OperationData"%>
<%
	{
BidManage bidManage = serviceSession.getService(BidManage.class);
IndexStatic indexStatic = bidManage.getIndexStatic();
OperateDataManage manage = serviceSession.getService(OperateDataManage.class);
T6196 t6196 = manage.getT6196();
%>
<div class="w1002 clearfix pb20">
    <!--介绍-->
    <div class="intro">
        <ul class="clearfix">
            <li class="mod01">
                <a href="<%configureProvider.format(out,URLVariable.FINANCING_SBTZ);%>">
                <div class="icon">
                    <div class="high"><span class="high0"></span><span class="high1"></span></div>
                </div>
                </a>
                <div class="til" onclick="location.href='<%configureProvider.format(out,URLVariable.FINANCING_SBTZ);%>'" style="cursor: pointer;">投资理财</div>
                <div class="text"><%
                	configureProvider.format(out, SystemVariable.FETURE_TZLC);
                %></div>
            </li>
            <li class="mod02">
                <a href="<%configureProvider.format(out,URLVariable.CREDIT_CENTER);%>"><div class="icon"><span class="rotate"></span></div></a>
                <div class="til" onclick="location.href='<%configureProvider.format(out,URLVariable.CREDIT_CENTER);%>'" style="cursor: pointer;">快捷借款</div>
                <div class="text"><%
                	configureProvider.format(out, SystemVariable.FETURE_KJDK);
                %></div>
            </li>
            <li class="mod03" style="margin-right:0px;">
                <a href="<%configureProvider.format(out, URLVariable.AQBZ_BXDB);%>"><div class="icon"><span class="scale"></span></div></a>
                <div class="til" onclick="location.href='<%configureProvider.format(out,URLVariable.AQBZ_BXDB);%>'" style="cursor: pointer;">本息保障</div>
                <div class="text"><%
                	configureProvider.format(out, SystemVariable.FETURE_BZBZ);
                %></div>
            </li>
        </ul>
    </div>
    <!--介绍-->
</div>
<%}%>

<div class="video_jplayer" style="display:none;">
    <span class="video_jplayer_close" id="vid_colse">×</span>
    <div class="video_show">
        <%
            AdvertisementManage advertisementManage1 = serviceSession.getService(AdvertisementManage.class);
            AdvertSpscRecord record = advertisementManage1.searchqtSpsc();
        %>
        <%
            if (record != null) {
                if (record.status == T5021_F07.YFB && record.isAuto == 1) {
        %>
        <div id="jp_container_1" class="jp-video jp-video-360p" role="application" aria-label="media player">
            <div class="jp-type-single">
                <div id="jquery_jplayer_1" class="jp-jplayer"></div>
                <div class="jp-gui">
                    <div class="jp-video-play">
                        <button class="jp-video-play-icon" role="button" tabindex="0">play</button>
                    </div>
                    <div class="jp-interface">
                        <div class="jp-progress">
                            <div class="jp-seek-bar">
                                <div class="jp-play-bar"></div>
                            </div>
                        </div>
                        <div class="jp-current-time" role="timer" aria-label="time">&nbsp;</div>
                        <div class="jp-duration" role="timer" aria-label="duration">&nbsp;</div>
                        <div class="jp-controls-holder">
                            <div class="jp-controls">
                                <button class="jp-play" role="button" tabindex="0">play</button>
                                <button class="jp-stop" role="button" tabindex="0">stop</button>
                            </div>
                            <div class="jp-volume-controls">
                                <button class="jp-mute" role="button" tabindex="0">mute</button>
                                <button class="jp-volume-max" role="button" tabindex="0">max volume</button>
                                <div class="jp-volume-bar">
                                    <div class="jp-volume-bar-value"></div>
                                </div>
                            </div>
                            <div class="jp-toggles">
                                <button class="jp-repeat" role="button" tabindex="0">repeat</button>
                                <button class="jp-full-screen" role="button" tabindex="0">full screen</button>
                            </div>
                        </div>
                        <div class="jp-details">
                            <div class="jp-title" aria-label="title">&nbsp;</div>
                        </div>
                    </div>
                </div>
                <div class="jp-no-solution">
                    <span>Update Required</span>
                    To play the media you will need to either update your browser to a recent version or update your <a href="http://get.adobe.com/flashplayer/" target="_blank">Flash plugin</a>.
                </div>
            </div>
        </div>
        <%
                }
            }
        %>
    </div>
</div>
