package com.hwm.test.http.model.entity;

/**
 * Created by huangwm on 2016/4/30.
 */
public class GeyeEntity {

    /**
     * returnCode : 1
     * apiMethod : /geye/getRtsp
     * subMsg :
     * geye : {"cityId":"350200","accessFlag":0,"cityName":"厦门市","playbackflag":1,"isOnline":1,"countyName":"湖里区","visittimes":0,"vlcplayerversion":"1","userId":"0000000000200000000000006864053","ptzlag":0,"provincesName":"福建省","panoramaflag":0,"longitude":"118.137437","rtspAddr":"rtsp://117.27.130.120:4606/service?PuId-ChannelNo=086591--1632762507&PlayMethod=0&UserId=0000000000200000000000006864053&StartTime=0&EndTime=0&PuProperty=0&hashtoken=7AE4FED6925ECDFF164E2B6227049DBB&StreamingType=2&VauPtzAdd=117.27.130.22&VauPtzPort=50000&HxhtVerification=1771216050&plid=1","geyeId":1590,"countyId":"350206","showPlayErrorFlag":1,"provincesId":"350000","linkType":0,"recommendFlag":0,"oldName":"03天翼景象江头嘉禾路口","longitudeGd":"118.13086041","netType":1,"highflag":0,"puName":"湖里江头嘉禾路口","puidAndChannelno":"086591--1632762507","showflag":1,"latitudeGd":"24.49183022","latitude":"24.497518"}
     * subCode :
     * errorCode :
     * msg : ---获取rtsp成功---
     */

    private String returnCode;
    private String apiMethod;
    private String subMsg;
    /**
     * cityId : 350200
     * accessFlag : 0
     * cityName : 厦门市
     * playbackflag : 1
     * isOnline : 1
     * countyName : 湖里区
     * visittimes : 0
     * vlcplayerversion : 1
     * userId : 0000000000200000000000006864053
     * ptzlag : 0
     * provincesName : 福建省
     * panoramaflag : 0
     * longitude : 118.137437
     * rtspAddr : rtsp://117.27.130.120:4606/service?PuId-ChannelNo=086591--1632762507&PlayMethod=0&UserId=0000000000200000000000006864053&StartTime=0&EndTime=0&PuProperty=0&hashtoken=7AE4FED6925ECDFF164E2B6227049DBB&StreamingType=2&VauPtzAdd=117.27.130.22&VauPtzPort=50000&HxhtVerification=1771216050&plid=1
     * geyeId : 1590
     * countyId : 350206
     * showPlayErrorFlag : 1
     * provincesId : 350000
     * linkType : 0
     * recommendFlag : 0
     * oldName : 03天翼景象江头嘉禾路口
     * longitudeGd : 118.13086041
     * netType : 1
     * highflag : 0
     * puName : 湖里江头嘉禾路口
     * puidAndChannelno : 086591--1632762507
     * showflag : 1
     * latitudeGd : 24.49183022
     * latitude : 24.497518
     */

    private GeyeBean geye;
    private String subCode;
    private String errorCode;
    private String msg;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public GeyeBean getGeye() {
        return geye;
    }

    public void setGeye(GeyeBean geye) {
        this.geye = geye;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class GeyeBean {
        private String cityId;
        private int accessFlag;
        private String cityName;
        private int playbackflag;
        private int isOnline;
        private String countyName;
        private int visittimes;
        private String vlcplayerversion;
        private String userId;
        private int ptzlag;
        private String provincesName;
        private int panoramaflag;
        private String longitude;
        private String rtspAddr;
        private int geyeId;
        private String countyId;
        private int showPlayErrorFlag;
        private String provincesId;
        private int linkType;
        private int recommendFlag;
        private String oldName;
        private String longitudeGd;
        private int netType;
        private int highflag;
        private String puName;
        private String puidAndChannelno;
        private int showflag;
        private String latitudeGd;
        private String latitude;

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public int getAccessFlag() {
            return accessFlag;
        }

        public void setAccessFlag(int accessFlag) {
            this.accessFlag = accessFlag;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getPlaybackflag() {
            return playbackflag;
        }

        public void setPlaybackflag(int playbackflag) {
            this.playbackflag = playbackflag;
        }

        public int getIsOnline() {
            return isOnline;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public int getVisittimes() {
            return visittimes;
        }

        public void setVisittimes(int visittimes) {
            this.visittimes = visittimes;
        }

        public String getVlcplayerversion() {
            return vlcplayerversion;
        }

        public void setVlcplayerversion(String vlcplayerversion) {
            this.vlcplayerversion = vlcplayerversion;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getPtzlag() {
            return ptzlag;
        }

        public void setPtzlag(int ptzlag) {
            this.ptzlag = ptzlag;
        }

        public String getProvincesName() {
            return provincesName;
        }

        public void setProvincesName(String provincesName) {
            this.provincesName = provincesName;
        }

        public int getPanoramaflag() {
            return panoramaflag;
        }

        public void setPanoramaflag(int panoramaflag) {
            this.panoramaflag = panoramaflag;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRtspAddr() {
            return rtspAddr;
        }

        public void setRtspAddr(String rtspAddr) {
            this.rtspAddr = rtspAddr;
        }

        public int getGeyeId() {
            return geyeId;
        }

        public void setGeyeId(int geyeId) {
            this.geyeId = geyeId;
        }

        public String getCountyId() {
            return countyId;
        }

        public void setCountyId(String countyId) {
            this.countyId = countyId;
        }

        public int getShowPlayErrorFlag() {
            return showPlayErrorFlag;
        }

        public void setShowPlayErrorFlag(int showPlayErrorFlag) {
            this.showPlayErrorFlag = showPlayErrorFlag;
        }

        public String getProvincesId() {
            return provincesId;
        }

        public void setProvincesId(String provincesId) {
            this.provincesId = provincesId;
        }

        public int getLinkType() {
            return linkType;
        }

        public void setLinkType(int linkType) {
            this.linkType = linkType;
        }

        public int getRecommendFlag() {
            return recommendFlag;
        }

        public void setRecommendFlag(int recommendFlag) {
            this.recommendFlag = recommendFlag;
        }

        public String getOldName() {
            return oldName;
        }

        public void setOldName(String oldName) {
            this.oldName = oldName;
        }

        public String getLongitudeGd() {
            return longitudeGd;
        }

        public void setLongitudeGd(String longitudeGd) {
            this.longitudeGd = longitudeGd;
        }

        public int getNetType() {
            return netType;
        }

        public void setNetType(int netType) {
            this.netType = netType;
        }

        public int getHighflag() {
            return highflag;
        }

        public void setHighflag(int highflag) {
            this.highflag = highflag;
        }

        public String getPuName() {
            return puName;
        }

        public void setPuName(String puName) {
            this.puName = puName;
        }

        public String getPuidAndChannelno() {
            return puidAndChannelno;
        }

        public void setPuidAndChannelno(String puidAndChannelno) {
            this.puidAndChannelno = puidAndChannelno;
        }

        public int getShowflag() {
            return showflag;
        }

        public void setShowflag(int showflag) {
            this.showflag = showflag;
        }

        public String getLatitudeGd() {
            return latitudeGd;
        }

        public void setLatitudeGd(String latitudeGd) {
            this.latitudeGd = latitudeGd;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }
    }
}
