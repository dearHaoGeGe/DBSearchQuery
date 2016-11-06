package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {

    public static void main(String[] args){
        //生成数据库的实体类，对应的是数据库的表
        Schema schema = new Schema(1, "com.my.dbsearchquery.bean");
        addCase(schema);
        schema.setDefaultJavaPackageDao("com.my.dbsearchquery.dao");

        try {
            new DaoGenerator().generateAll(schema,"./app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addCase(Schema schema){
        Entity entity=schema.addEntity("CaseInfo");
        entity.addIdProperty();
        entity.addStringProperty("name");
        entity.addLongProperty("time");
        entity.addStringProperty("number");
    }
}
