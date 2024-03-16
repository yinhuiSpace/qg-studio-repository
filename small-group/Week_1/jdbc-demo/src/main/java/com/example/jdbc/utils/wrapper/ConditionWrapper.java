package com.example.jdbc.utils.wrapper;

import java.util.*;

/**
 * @author root
 * @packageName: com.example.jdbc.utils.wrapper
 * @className: ConditionWrapper
 * @description: 用于快速构建查询条件
 * @date 2024/3/16 14:09
 */
public class ConditionWrapper {
    //分页查询起始索引
    private Integer pageIndex = 0;

    //分页查询每页数据量
    private Integer pageSize = 0;


    //因为条件可能有多个，所以需要一个容器来存储和管理
    private static final HashMap<String, Object> wrapperMap = new HashMap<>();

    //参数列表：为了支持预编译sql，需要将参数单独抽离
    private List<Object> parameterList=new ArrayList<>();

    /**
     * 等于
     */
    public ConditionWrapper eq(String columnName, Object value) {
        wrapperMap.put(columnName + "=", "?");
        //参数值存入列表
        parameterList.add(value);
        return this;
    }

    /**
     * 模糊查询
     */
    public ConditionWrapper like(String columnName, Object value) {
        //拼接模糊查询条件
        wrapperMap.put(columnName + " like", "?");
        //参数值存入列表
        parameterList.add("%"+value+"%");
        return this;
    }

    /**
     * 分页查询
     */
    public ConditionWrapper page(Integer pageNum, Integer pageSize) {

        //先校验参数合法性
        if (pageNum<=0||pageSize<=0){
            return null;
        }

        //根据当前页码计算出数据库中查询的起始索引
        //赋值给全局变量
        this.pageIndex = (pageNum - 1) * pageSize;
        this.pageSize = pageSize;

        //参数值存入列表
        parameterList.add(pageIndex);
        parameterList.add(pageSize);

        return this;

    }


    /**
     * 拼接成最终条件字符串
     */
    private String buildQueryCondition() {

        //当用户没有传入任何条件时，直接结束方法
        if (wrapperMap.size() == 0) {
            return null;
        }

        String conditionStr = null;
        //用于拼接条件
        StringBuilder queryConditionStr = new StringBuilder();

        //将map中的条件逐个取出，并开始拼接
        Set<Map.Entry<String, Object>> entrySet = wrapperMap.entrySet();

        //循环拼接条件
        for (Map.Entry<String, Object> entry : entrySet) {
            //拼接查询条件
            //注意：不要漏了加空格
            //注意：需要使用占位符?，代替参数
            queryConditionStr.append(" ");
            queryConditionStr.append(entry.getKey());
            queryConditionStr.append(" ?");
            queryConditionStr.append(" and ");
        }
        //注意：语句末尾不能再加and了，要去掉
        if (queryConditionStr.length() > 0) {
            conditionStr = queryConditionStr.substring(0, queryConditionStr.lastIndexOf("and"));
        }
        //返回结果
        return conditionStr;
    }

    /**
     * 拼接分页查询
     */
    private String buildPage() {

        //当用户没有指定分页参数，直接结束方法
        if (this.pageIndex == 0 && this.pageSize == 0) {
            return null;
        }

        //拼接分页条件
        StringBuilder pageStr = new StringBuilder();

        //注意：不要忘了加逗号和空格
        pageStr.append("limit ");
        pageStr.append("?");
        pageStr.append(" , ");
        pageStr.append("?");

        return pageStr.toString();

    }

    /**
     * 拼成最终条件
     */
    public String buildAllQuery() {
        String queryCondition = buildQueryCondition();
        String page = buildPage();

        //最终条件
        StringBuilder allConditions = new StringBuilder();

        //注意：不要漏了where
        //条件查询+分页查询
        if (queryCondition != null && page != null) {
            allConditions.append("where ");
            allConditions.append(queryCondition);
            allConditions.append(page);
            return allConditions.toString();
        }

        //条件查询
        if (queryCondition != null && page == null) {
            allConditions.append("where ");
            allConditions.append(queryCondition);
            return allConditions.toString();
        }

        //分页查询
        if (queryCondition == null && page != null) {
            allConditions.append(page);
            return allConditions.toString();
        }

        return null;
    }

    /**
     * 返回参数列表
     * */
    public List<Object> getParameterList() {
        return parameterList;
    }
}
