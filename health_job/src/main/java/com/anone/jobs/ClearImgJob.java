package com.anone.jobs;

import com.anone.constant.RedisConstant;
import com.anone.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * 定时任务对象
 */
public class ClearImgJob {
    /**
     * 定时处理垃圾图片
     */
    @Autowired
    private JedisPool jedisPool;
    public void clearImg() {
        //如何处理垃圾图片
        //通过redis对象 获取自动上传图片的集合 和 上传到数据库图片的差值集合--即是垃圾图片
        Set<String> UnUseImg = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES,
                RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        //获取到垃圾图片的集合
        for (String imgName : UnUseImg) {
            //需要先删除七牛云里面的图片---先删除第三方服务器的
            QiniuUtils.deleteFileFromQiniu(imgName);
            //再删除本地的redis中的图片
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,imgName);
            System.out.println(imgName+"删除成功了.......");
        }




    }
}
