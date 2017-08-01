package com.jlgproject.util;

import okhttp3.MediaType;

/**
 * @author 王锋 on 2017/5/3.
 */

public class ConstUtils {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String IOGIN_INFO="loginInfo";
    public static final String HOME_INFO="homeInfo";
    public  static final  String VIDEO_LISTS="videoInfo";
    public static final String CHECKBOX_AGREEMENT="gouxuan";//勾选
    public static final String CANCEL_AGREEMENT="quxiao";//取消
    public static final String PD="pd";//确认
    public static final String MY_PAGER="my_pager";
    public static final String MONEY="money";//钱
    public static final String OPEN_DEBT_MONEY="0.02";//
    public static final String DEBT_BEIAN="0.01";//
    public static final String ASSET_ID="assetId";//资产Id key
    public static final String IS_OWER="isOwer";//资产Id key
    public static final String ESC="esc";//退出表示
    public static final String WEIXIN_ADDID="wxbc0753acdfa4e7c3";//微信AppID
    public static final String USER_TYPE="userType";//退出表示
    public static final String USER_PHONE="phone";//退出表示
    public static final String YYB_SC="com.tencent.android.qqdownloader";//应用宝市场
    public static final String HUAWEI_SC="com.huawei.appmarket";//华为市场
    public static final String XIAOMI_SC="com.xiaomi.market";//小米市场
    public static final String VIVO_SC="com.huawei.appmarket";//VIVO市场

    public static final String OPPO_SC="com.huawei.appmarket";//OPPO市场



    public static final String[] Tab_Name = {
            "未解决",
            "已解决",
    };
    public static final String[] Tab_Zsr_Name = {
            "债事企业",
            "债事自然人",
    };




    /**
     * 正则：手机号（简单）
     */
    public static final String REGEX_MOBILE_SIMPLE = "^[1]//d{10}$";
    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
     * <p>联通：130、131、132、145、155、156、175、176、185、186</p>
     * <p>电信：133、153、173、177、180、181、189</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     */
    public static final String REGEX_MOBILE_EXACT  = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))//d{8}$";
    /**
     * 正则：电话号码
     */
    public static final String REGEX_TEL = "^0//d{2,3}[- ]?//d{7,8}";
    /**
     * 正则：身份证号码18位
     */
    public static final String REGEX_ID_CARD18  = "^[1-9]//d{5}[1-9]//d{3}((0//d)|(1[0-2]))(([0|1|2]//d)|3[0-1])//d{3}([0-9Xx])$";
    /**
     * 正则：邮箱
     */
    public static final String REGEX_EMAIL  = "^//w+([-+.]//w+)*@//w+([-.]//w+)*//.//w+([-.]//w+)*$";
    /**
     * 正则：URL
     */
    public static final String REGEX_URL  = "[a-zA-z]+://[^//s]*";
    /**
     * 正则：汉字
     */
    public static final String REGEX_ZH  = "^[//u4e00-//u9fa5]+$";
    /**
     * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-18位
     */
    public static final String REGEX_USERNAME      = "^[//w//u4e00-//u9fa5]{6,18}(?<!_)$";
    /**
     * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
     */
    public static final String REGEX_DATE          = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";

    /**
     * 正则：QQ号
     */
    public static final String REGEX_TENCENT_NUM          = "[1-9][0-9]{4,}";
}
