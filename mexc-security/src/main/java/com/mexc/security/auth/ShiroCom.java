package com.mexc.security.auth;

import org.apache.commons.collections.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sunshine on 16/9/8.
 */
public class ShiroCom {

    public static Set<String> bulidPermissions(List<String> permissions) {
        Set<String> permissionsCode = new HashSet<String>();
        if (CollectionUtils.isEmpty(permissions)) {
            return permissionsCode;
        }
        for (String permission : permissions) {
            permissionsCode.add(permission);
        }
        return permissionsCode;
    }
}
