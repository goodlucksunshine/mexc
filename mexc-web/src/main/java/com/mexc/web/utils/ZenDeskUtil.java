package com.mexc.web.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mexc.common.constant.RedisKeyConstant;
import com.mexc.common.util.jedis.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.Group;
import org.zendesk.client.v2.model.GroupMembership;
import org.zendesk.client.v2.model.hc.Article;
import org.zendesk.client.v2.model.hc.Category;
import org.zendesk.client.v2.model.hc.Section;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 2018/2/6
 * Time: 上午10:41
 */
public class ZenDeskUtil {
    private static ZenDeskUtil zenDeskUtil;
    private static Zendesk zd = null;
    private static String zendesk_url = "https://mexchelp.zendesk.com/";
    //PropertyPlaceholderConfigurer.getProperty("zendesk_url");
    private static String zendesk_user = "leechy01@139.com";
    //PropertyPlaceholderConfigurer.getProperty("zendesk_user");
    private static String zendesk_pwd = "leechy";
    //PropertyPlaceholderConfigurer.getProperty("zendesk_pwd");
    private static long sectionId = 360000003474l;

    private static Long lastUpdate = System.currentTimeMillis();

    private ZenDeskUtil() {
    }

    public static ZenDeskUtil getInstall() {
        if (zenDeskUtil == null) {
            zenDeskUtil = new ZenDeskUtil();
        }
        if (zd == null) {
            zd = new Zendesk.Builder(zendesk_url).setUsername(zendesk_user).setPassword(zendesk_pwd).build();
        }
        return zenDeskUtil;
    }


    public List<Article> getArticle() {
        List<Article> articlesList = new ArrayList<>();
        Long accessTime = System.currentTimeMillis();
        String articleJson = RedisUtil.get(RedisKeyConstant.NOTICE);
        if (StringUtils.isEmpty(articleJson) || (accessTime - lastUpdate > 10000000)) {
            Iterable<Article> articles = zd.getArticleFromSearch("", sectionId);
            Iterator<Article> iterator = articles.iterator();
            while (iterator.hasNext()) {
                Article article = iterator.next();
                articlesList.add(article);
            }
            RedisUtil.set(RedisKeyConstant.NOTICE, JSON.toJSONString(articlesList));
        } else {
            articlesList = JSONArray.parseArray(articleJson, Article.class);
        }

        return articlesList;
    }


    private void sections() {
        Iterable<Section> sections = zd.getSections();
        Iterator<Section> iterator = sections.iterator();
        while (iterator.hasNext()) {
            Section section = iterator.next();
            System.out.println("section:" + JSON.toJSONString(section));
        }
    }


    private void getGroups() {
        Iterable<Group> groups = zd.getGroups();
        Iterator<Group> groupIterator = groups.iterator();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            System.out.println("group:" + JSON.toJSONString(group));
        }
    }

    private void getGroupMemberships() {
        Iterable<GroupMembership> groups = zd.getGroupMemberships();
        Iterator<GroupMembership> groupIterator = groups.iterator();
        while (groupIterator.hasNext()) {
            GroupMembership group = groupIterator.next();
            System.out.println("GroupMembership:" + JSON.toJSONString(groupIterator));
        }
    }

    private void getAssignableGroups() {
        Iterable<Group> groups = zd.getAssignableGroups();
        Iterator<Group> groupIterator = groups.iterator();
        while (groupIterator.hasNext()) {
            Group group = groupIterator.next();
            System.out.println("group:" + JSON.toJSONString(group));
        }
    }

    private void getCategrys() {
        Iterable<Category> iterable = zd.getCategories();
        Iterator<Category> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Category category = iterator.next();
            System.out.println("category:" + JSON.toJSONString(category));
        }
    }
}
