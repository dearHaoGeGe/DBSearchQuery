package com.my.dbsearchquery.dbmanager;

import android.content.Context;
import android.util.Log;

import com.my.dbsearchquery.bean.CaseInfo;
import com.my.dbsearchquery.dao.CaseInfoDao;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * 数据库常用工具类
 * <p/>
 * Created by YJH on 2016/5/22.
 */
public class DBUtils {

    private static final String TAG = "DBUtils------>";
    private DaoManager manager;

    public DBUtils(Context context) {
        manager = DaoManager.getInstance();
        manager.init(context);
    }

    /**
     * 向数据库中插入单条数据
     *
     * @param cla
     * @return
     */
    public boolean insertOneData(Class cla) {
        boolean flag = false;
        flag = manager.getDaoSession().insert(cla) != -1 ? true : false;
        Log.d(TAG, "插入单条数据的结果是------>" + flag);
        return flag;
    }

    /**
     * 向数据库中插入多条数据
     *
     * @param list
     * @return
     */
    public boolean insertMoreData(final List<CaseInfo> list) {
        boolean flag = false;
        try {
            manager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < list.size(); i++) {
                        manager.getDaoSession().insertOrReplace(list.get(i));
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "插入多条数据的结果是------>" + flag);
        return flag;
    }

    /**
     * 删除表内所有数据
     *
     * @param cla
     * @return
     */
    public boolean deleteAllData(Class cla) {
        boolean flag = false;
        try {
            manager.getDaoSession().deleteAll(cla);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 通过name这列模糊查询CaseInfo这个表
     * select * from CaseInfo where name like "%xx%" or number like %xx% or time like %xx% order by desc
     *
     * @param queryContent
     * @return
     */
    public List<CaseInfo> fuzzyQueryByName(String queryContent) {
        QueryBuilder<CaseInfo> builder = manager.getDaoSession().queryBuilder(CaseInfo.class);
        //builder.where(CaseInfoDao.Properties.Name.like("%" + queryContent + "%"));
        builder.whereOr(CaseInfoDao.Properties.Name.like("%" + queryContent + "%"),
                CaseInfoDao.Properties.Number.like("%" + queryContent + "%"),
                CaseInfoDao.Properties.Time.like("%" + queryContent + "%"));
        List<CaseInfo> list = builder.orderDesc(CaseInfoDao.Properties.Time).list();
        Log.d(TAG, "一共有" + builder.count() + "条");
        return list;
    }

    /**
     * 按时间倒叙查询
     *
     * @return
     */
    public List<CaseInfo> orderDesc() {
        QueryBuilder<CaseInfo> builder = manager.getDaoSession().queryBuilder(CaseInfo.class);
        builder.orderDesc(CaseInfoDao.Properties.Time);
        List<CaseInfo> list = builder.list();
        return list;
    }

    /**
     * 关闭数据库资源
     */
    public void closeConnection() {
        manager.closeConnection();
    }
}
