package com.mexc.dao.vo.resource;

import java.util.List;

/**
 * Created by huangxinguang on 2017/9/27 下午4:03.
 */
public class MenuVo {
    private Integer id;

    private Integer parentId;

    private String code;

    private String name;

    private String url;

    private Integer type;

    private Integer status;

    private Integer sort;

    private String icon;

    private String target;

    private Boolean spread = false;

    private List<MenuVo> childs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public List<MenuVo> getChilds() {
        return childs;
    }

    public void setChilds(List<MenuVo> childs) {
        this.childs = childs;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }
}
