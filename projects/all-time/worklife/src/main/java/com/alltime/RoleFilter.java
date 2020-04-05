package com.alltime;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RoleFilter {

    private static final Map<String, RoleMapping> ROLE_CONTAINER = new ConcurrentHashMap<>();

    static {
        RoleMapping roleMapping = new RoleMapping();
        roleMapping.roleRefId = "1000011";
        roleMapping.roleName = "天猫供应商";
        roleMapping.authUrls.add("/url110011");
        roleMapping.authUrls.add("/url110012");
        ROLE_CONTAINER.put(roleMapping.getRoleRefId(), roleMapping);
    }

    public Object filterViews(String roleRefId, Object beanObj) {
        Object obj = new Object();
        
        return obj;
    }

    public boolean containUrl(String roleRefId, String url) {
        RoleMapping role = ROLE_CONTAINER.get(roleRefId);
        if(null == role) {
            return false;
        }
        return role.authUrls.contains(url);
    }

    public boolean containUrls(String roleRefId, List<String> urls) {
        RoleMapping role = ROLE_CONTAINER.get(roleRefId);
        if(null == role) {
            return false;
        }
        return role.authUrls.containsAll(urls);
    }

    public static final class RoleMapping {
        private String roleRefId;
        private String roleCode;
        private String roleName;
        private Set<String> authUrls = new HashSet<>();
        private Map<String, TabView> authTabs = new HashMap<>();

        public String getRoleRefId() {
            return roleRefId;
        }

        public void setRoleRefId(String roleRefId) {
            this.roleRefId = roleRefId;
        }

        public String getRoleCode() {
            return roleCode;
        }

        public void setRoleCode(String roleCode) {
            this.roleCode = roleCode;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public Set<String> getAuthUrls() {
            return authUrls;
        }

        public void setAuthUrls(Set<String> authUrls) {
            this.authUrls = authUrls;
        }

        @Override
        public String toString() {
            return "RoleMapping{" +
                    "roleRefId='" + roleRefId + '\'' +
                    ", roleCode='" + roleCode + '\'' +
                    ", roleName='" + roleName + '\'' +
                    ", authUrls=" + authUrls +
                    '}';
        }
    }

    public static final class TabView {
        private Map<String, String> tabViews = new HashMap<>();
        private Map<String, String> authBtns = new HashMap<>();



    }
}
