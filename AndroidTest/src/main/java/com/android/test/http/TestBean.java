package com.android.test.http;

/**
 * Created by Administrator on 2015/10/10 0010.
 */
public class TestBean {


    /**
     * returnCode : 1
     * apiMethod : /geye/getRtsp
     * subMsg :
     * geye : {"puidAndChannelno":"086591--1632762507","puName":"湖里江头嘉禾路口","isOnline":1,"playbackflag":1,"ptzlag":0,"userId":"0000000000200000000000006864053","oldName":"03天翼景象江头嘉禾路口","provincesId":"350000","cityId":"350200","countyId":"350206","provincesName":"福建省","cityName":"厦门市","countyName":"湖里区","latitude":"24.497518","longitude":"118.137437","showflag":1,"highflag":0,"panoramaflag":0,"geyeId":1590,"accessFlag":0,"rtspAddr":"rtsp://117.27.130.120:4606/service?PuId-ChannelNo=086591--1632762507&PlayMethod=0&UserId=0000000000200000000000006864053&StartTime=0&EndTime=0&PuProperty=0&hashtoken=7AE4FED6925ECDFF164E2B6227049DBB&StreamingType=2&VauPtzAdd=117.27.130.124&VauPtzPort=50000&HxhtVerification=151789538&plid=1","netType":1,"linkType":0,"showPlayErrorFlag":1,"visittimes":0,"vlcplayerversion":"1","recommendFlag":0}
     * subCode :
     * errorCode :
     * msg : ---获取rtsp成功---
     */

    private String returnCode;
    private String apiMethod;
    private String subMsg;
    /**
     * puidAndChannelno : 086591--1632762507
     * puName : 湖里江头嘉禾路口
     * isOnline : 1
     * playbackflag : 1
     * ptzlag : 0
     * userId : 0000000000200000000000006864053
     * oldName : 03天翼景象江头嘉禾路口
     * provincesId : 350000
     * cityId : 350200
     * countyId : 350206
     * provincesName : 福建省
     * cityName : 厦门市
     * countyName : 湖里区
     * latitude : 24.497518
     * longitude : 118.137437
     * showflag : 1
     * highflag : 0
     * panoramaflag : 0
     * geyeId : 1590
     * accessFlag : 0
     * rtspAddr : rtsp://117.27.130.120:4606/service?PuId-ChannelNo=086591--1632762507&PlayMethod=0&UserId=0000000000200000000000006864053&StartTime=0&EndTime=0&PuProperty=0&hashtoken=7AE4FED6925ECDFF164E2B6227049DBB&StreamingType=2&VauPtzAdd=117.27.130.124&VauPtzPort=50000&HxhtVerification=151789538&plid=1
     * netType : 1
     * linkType : 0
     * showPlayErrorFlag : 1
     * visittimes : 0
     * vlcplayerversion : 1
     * recommendFlag : 0
     */

    private GeyeEntity geye;
    private String subCode;
    private String errorCode;
    private String msg;

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public void setSubMsg(String subMsg) {
        this.subMsg = subMsg;
    }

    public void setGeye(GeyeEntity geye) {
        this.geye = geye;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public String getSubMsg() {
        return subMsg;
    }

    public GeyeEntity getGeye() {
        return geye;
    }

    public String getSubCode() {
        return subCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public static class GeyeEntity {
        private String puidAndChannelno;
        private String puName;
        private int isOnline;
        private int playbackflag;
        private int ptzlag;
        private String userId;
        private String oldName;
        private String provincesId;
        private String cityId;
        private String countyId;
        private String provincesName;
        private String cityName;
        private String countyName;
        private String latitude;
        private String longitude;
        private int showflag;
        private int highflag;
        private int panoramaflag;
        private int geyeId;
        private int accessFlag;
        private String rtspAddr;
        private int netType;
        private int linkType;
        private int showPlayErrorFlag;
        private int visittimes;
        private String vlcplayerversion;
        private int recommendFlag;

        public void setPuidAndChannelno(String puidAndChannelno) {
            this.puidAndChannelno = puidAndChannelno;
        }

        public void setPuName(String puName) {
            this.puName = puName;
        }

        public void setIsOnline(int isOnline) {
            this.isOnline = isOnline;
        }

        public void setPlaybackflag(int playbackflag) {
            this.playbackflag = playbackflag;
        }

        public void setPtzlag(int ptzlag) {
            this.ptzlag = ptzlag;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setOldName(String oldName) {
            this.oldName = oldName;
        }

        public void setProvincesId(String provincesId) {
            this.provincesId = provincesId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public void setCountyId(String countyId) {
            this.countyId = countyId;
        }

        public void setProvincesName(String provincesName) {
            this.provincesName = provincesName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setShowflag(int showflag) {
            this.showflag = showflag;
        }

        public void setHighflag(int highflag) {
            this.highflag = highflag;
        }

        public void setPanoramaflag(int panoramaflag) {
            this.panoramaflag = panoramaflag;
        }

        public void setGeyeId(int geyeId) {
            this.geyeId = geyeId;
        }

        public void setAccessFlag(int accessFlag) {
            this.accessFlag = accessFlag;
        }

        public void setRtspAddr(String rtspAddr) {
            this.rtspAddr = rtspAddr;
        }

        public void setNetType(int netType) {
            this.netType = netType;
        }

        public void setLinkType(int linkType) {
            this.linkType = linkType;
        }

        public void setShowPlayErrorFlag(int showPlayErrorFlag) {
            this.showPlayErrorFlag = showPlayErrorFlag;
        }

        public void setVisittimes(int visittimes) {
            this.visittimes = visittimes;
        }

        public void setVlcplayerversion(String vlcplayerversion) {
            this.vlcplayerversion = vlcplayerversion;
        }

        public void setRecommendFlag(int recommendFlag) {
            this.recommendFlag = recommendFlag;
        }

        public String getPuidAndChannelno() {
            return puidAndChannelno;
        }

        public String getPuName() {
            return puName;
        }

        public int getIsOnline() {
            return isOnline;
        }

        public int getPlaybackflag() {
            return playbackflag;
        }

        public int getPtzlag() {
            return ptzlag;
        }

        public String getUserId() {
            return userId;
        }

        public String getOldName() {
            return oldName;
        }

        public String getProvincesId() {
            return provincesId;
        }

        public String getCityId() {
            return cityId;
        }

        public String getCountyId() {
            return countyId;
        }

        public String getProvincesName() {
            return provincesName;
        }

        public String getCityName() {
            return cityName;
        }

        public String getCountyName() {
            return countyName;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public int getShowflag() {
            return showflag;
        }

        public int getHighflag() {
            return highflag;
        }

        public int getPanoramaflag() {
            return panoramaflag;
        }

        public int getGeyeId() {
            return geyeId;
        }

        public int getAccessFlag() {
            return accessFlag;
        }

        public String getRtspAddr() {
            return rtspAddr;
        }

        public int getNetType() {
            return netType;
        }

        public int getLinkType() {
            return linkType;
        }

        public int getShowPlayErrorFlag() {
            return showPlayErrorFlag;
        }

        public int getVisittimes() {
            return visittimes;
        }

        public String getVlcplayerversion() {
            return vlcplayerversion;
        }

        public int getRecommendFlag() {
            return recommendFlag;
        }
    }
}
