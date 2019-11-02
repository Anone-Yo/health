package com.anone.dao;

import com.anone.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 会员 dao  接口
 */
public interface MemberDao {
    /**
     * 根据telephone查询会员
     * @param telephone
     * @return
     */
    Member findMemberByTelephone(String telephone);

    /**
     * 新增会员对象
     * @param newMember
     */
    void add(Member newMember);

    /**
     * 查询当前日期之前的会员数量
     * @param date
     * @return
     */
    Integer getMemberCountByDate(String date);

    /**
     * 今日新增会员
     * @param reportDate
     * @return
     */
    Integer findTodayNewMember(String reportDate);

    /**
     * 总会员数量
     * @return
     */
    Integer findTotalMember();

    /**
     * 本周新增会员
     * @param thisWeekMonday
     * @return
     */
    Integer findThisWeekNewMember(String thisWeekMonday);

    /**
     * 本月新增会员数量
     * @param thisMonthFirstDay
     * @return
     */
    Integer findThisMonthNewMember(String thisMonthFirstDay);

    /**
     * 查询会员性别 男女的比
     * @return
     */
    List<Map<String, Object>> findMemberGender();

    /**
     * 查询年龄段的数量
     * @param num1
     * @param num2
     * @return
     */
    Integer getAgeCount(@Param("num1") int num1, @Param("num2") int num2);

    /**
     * 查询大于某年龄段的数量
     * @param num
     * @return
     */
    Integer getMorethanAgeCount(int num);
}
