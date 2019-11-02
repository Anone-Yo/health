package com.anone.service;

import com.anone.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * 登录 服务 接口
 */
public interface MemberService {
    /**
     * 根据电话查询会员
     * @param telephone
     * @return
     */
    Member findMemberByTelephone(String telephone);

    /**
     * 新增会员
     * @param member
     */
    void add(Member member);

    /**
     * 获取每个月的会员数量
     * @param list
     * @return
     */
    List<Integer> getMemberNumberByMonth(List<String> list);

    /**
     * 查询会员统计报告
     * @return
     */
    Map getBusinessReportData();

    /**
     * 查询会员性别占比
     * @return
     */
    Map getMemberGenderReport();

    /**
     * 会员年龄分段占比
     * @return
     */
    Map getMemberAgeReport();

    /**
     * 展示时间段的会员数量
     * @param beginDate
     * @param endDate
     * @return
     */
    Map showByDate(String beginDate, String endDate);
}
