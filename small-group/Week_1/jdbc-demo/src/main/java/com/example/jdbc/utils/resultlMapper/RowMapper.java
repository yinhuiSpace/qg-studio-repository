package com.example.jdbc.utils.resultlMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author root
 * @packageName: com.example.jdbc.utils.resultlMapper
 * @className: rowMapper
 * @description: 专门用于实体类和数据表的列进行映射
 * @date 2024/3/16 11:07
 */
public class RowMapper<T> {

    //储存字段和对应值的映射关系
    private Map<String,Object> fieldValMap=new HashMap<>();

    //实体类字节码对象
    private Class<T> entityClass;

    //结果集对象
    private ResultSet resultSet;

    public RowMapper(Class<T> entityClass, ResultSet resultSet) {
        this.entityClass = entityClass;
        this.resultSet = resultSet;
    }

    public T rowMap() throws Exception {

        //从resultSet中获取数据，并且通过实体类的set方法将属性注入到实体类对象中
        if (!resultSet.next()){
            return null;
        }

        return getEntity();
    }


    /**
    * 批量解析数据
    * */
    public List<T> rowListMap() throws Exception {

        //创建实体类列表
        List<T> entityList = new ArrayList<>();

        //从resultSet中获取数据，并且通过实体类的set方法将属性注入到实体类对象中
        while (resultSet.next()) {
            //解析单行数据
            T entity = getEntity();
            //添加进列表
            entityList.add(entity);
        }

        return entityList;
    }

    /**
    * 下划线转小驼峰
    * */
    private String downToCaml(String downLineStr) {

        StringBuilder camlStr = new StringBuilder();

        //标记下划线后一个字符
        boolean isAfterDownLine=false;


        //逐个字符遍历
        for (int i = 0; i < downLineStr.length(); i++) {

            char c = downLineStr.charAt(i);

            //将下划线之后的字符替换成大写
            if (c=='_'){
                isAfterDownLine=true;
                continue;
            }

            //转大写
            if (isAfterDownLine){
                c=Character.toUpperCase(c);
                isAfterDownLine=false;
            }

            camlStr.append(c);

        }

        return camlStr.toString();
    }

    /**
    * 将单行数据解析成实体类对象
    * */
    private T getEntity() throws Exception {
        //所要封装的实体类对象
        T entity = null;

        //通过反射调用无参构造创建实体类对象
        Constructor<T> constructor = entityClass.getDeclaredConstructor();
        //阻断权限检查
        constructor.setAccessible(true);
        //创建实体类对象
        entity = constructor.newInstance();
        //获取数据库行的元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        //获取列数
        int columnCount = metaData.getColumnCount();
        //循环遍历所有列
        for (int i = 0; i < columnCount; i++) {
            //获取列名，即字段名
            String columnName = metaData.getColumnName(i + 1);

            //逐个遍历
            String fieldName = downToCaml(columnName);
            //获取该行该列数据
            Object value = resultSet.getObject(columnName);

            //添加到映射
            fieldValMap.put(fieldName,value);

        }

        //通过反射给各字段赋值
        Field[] fields = entityClass.getDeclaredFields();

        for (Field field : fields) {
            //静态变量和常量没有赋值的必要
            if (Modifier.isStatic(field.getModifiers())||Modifier.isFinal(field.getModifiers())){
                continue;
            }
            //反射赋值
            field.setAccessible(true);
            Object val = fieldValMap.get(field.getName());
            if (val==null){
                continue;
            }
            //如果是tinyInt还需要强转一下
            if (field.getGenericType().getTypeName().equals("short")){
                field.set(entity,Short.parseShort(val.toString()));
                continue;
            }
            field.set(entity, val);
        }

        return entity;
    }
}
