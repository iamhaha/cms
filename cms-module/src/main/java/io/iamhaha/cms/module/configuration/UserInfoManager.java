/**
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package io.iamhaha.cms.module.configuration;

/**
 * User sign in info manager.
 *
 * @author dingshenglong
 */
public class UserInfoManager {
    private static ThreadLocal<UserInfo> info = new ThreadLocal<>();

    public static UserInfo getUserInfo() {
        return info.get();
    }

    public static void setUserInfo(UserInfo one) {
        info.set(one);
    }

    public static void removeUserInfo() {
        info.remove();
    }

}
