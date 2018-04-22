package com.mexc.common.plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/6. 参数传输
 */
public class SqlCondition {
    private Object condition;

    private Object value;

    private boolean haveValue;

    private List<SqlCondition> list = new ArrayList<SqlCondition>();

    public SqlCondition() {
        this(null, null);
    }

    public SqlCondition(Object condition, Object value) {
        this.condition = condition;
        this.value = value;
        this.haveValue = (value != null);
    }

    public SqlCondition(Object key) {
        this(key, null);
    }

    public Object getCondition() {
        return condition;
    }

    public Object getValue() {
        return value;
    }

    public SqlCondition put(String condition) {
        this.list.add(new SqlCondition(condition));
        return this;
    }

    public SqlCondition put(String condition, Object value) {
        this.list.add(new SqlCondition(condition, value));
        return this;
    }

    public SqlCondition put(int index, String condition, Object value) {
        this.list.add(index, new SqlCondition(condition, value));
        return this;
    }

    public SqlCondition put(int index, String condition) {
        this.list.add(index, new SqlCondition(condition));
        return this;
    }

    public SqlCondition remove(int index) {
        if (this.list.size() >= index + 1)
            this.list.remove(index);
        return this;
    }

    public SqlCondition clean() {
        list.clear();
        return this;
    }

    public List<SqlCondition> getList() {
        return list;
    }

    public boolean isHaveValue() {
        return haveValue;
    }
}