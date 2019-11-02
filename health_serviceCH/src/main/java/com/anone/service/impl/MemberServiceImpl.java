package com.anone.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.anone.dao.LoginDao;
import com.anone.dao.MemberDao;
import com.anone.dao.OrderDao;
import com.anone.pojo.Member;
import com.anone.pojo.Order;
import com.anone.service.MemberService;
import com.anone.utils.DateUtils;
import com.anone.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    @Override
    public Member findMemberByTelephone(String telephone) {
        return memberDao.findMemberByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        if (member.getPassword() != null) {
            String md5Password = MD5Utils.md5(member.getPassword());
            member.setPassword(md5Password);
        }
        memberDao.add(member);
    }

    /**
     * 获取每个月的会员数量
     * @param list
     * @return
     */
    @Override
    public List<Integer> getMemberNumberByMonth(List<String> list) {
        //判断list是否为空
        //创建一个集合存储每月会员数量
        List<Integer> countMemberList=new ArrayList<>();
        if (list !=null && list.size()>0) {
            for (String month : list) {
                String date=month+"-31";
             Integer memberCount= memberDao.getMemberCountByDate(date);
             countMemberList.add(memberCount);
            }
        }
        return countMemberList;
    }

    /**
     * 获取会员的查询报告
     * @return
     */
    @Override
    public Map getBusinessReportData() {
        Map<String,Object> map=new HashMap<>();
        //查询今天新增会员
        try {
            //获取当前日期
            String reportDate = DateUtils.parseDate2String(DateUtils.getToday());
            //获取当前周的周一
            String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
            //获取本月第一天
            String thisMonthFirstDay = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
           //今日会员新增数量
            Integer  todayNewMember =  memberDao.findTodayNewMember(reportDate);
            //总会员数
            //totalMember
            Integer totalMember=  memberDao.findTotalMember();
            //本周新增会员数
            //thisWeekNewMember
            Integer thisWeekNewMember=  memberDao.findThisWeekNewMember(thisWeekMonday);
            //本月新增会员数
            //thisMonthNewMember
            Integer thisMonthNewMember=  memberDao.findThisMonthNewMember(thisMonthFirstDay);
            //今日预约数
            //todayOrderNumber
             Integer todayOrderNumber= orderDao.findTodayOrderNumber(reportDate);
            //今日到诊数
            //todayVisitsNumber
            Integer todayVisitsNumber=orderDao.findTodayVisitsNumber(Order.ORDERSTATUS_YES,reportDate);
            //本周预约数
            //thisWeekOrderNumber
            Integer thisWeekOrderNumber=orderDao.findThisWeekOrderNumber(thisWeekMonday);
            //本周到诊数
            //thisWeekVisitsNumber
            Integer thisWeekVisitsNumber=orderDao.findThisWeekVisitsNumber(thisWeekMonday,Order.ORDERSTATUS_YES);
            //本月预约数
            //thisMonthOrderNumber
            Integer thisMonthOrderNumber=orderDao.findThisMonthOrderNumber(thisMonthFirstDay);
            //本月到诊数
            //thisMonthVisitsNumber
            Integer thisMonthVisitsNumber=orderDao.findThisMonthVisitsNumber(thisMonthFirstDay,Order.ORDERSTATUS_YES);
            //热门套餐(取前四个)
            //hotSetmeal
            // name 套餐名字
            //setmeal_count 当前套餐的数量
            //proportion 占比
            List<Map> hotSetmeal=orderDao.findHotSetmeal();
            map.put("reportDate",reportDate);
            map.put("todayNewMember",todayNewMember);
            map.put("totalMember",totalMember);
            map.put("thisWeekNewMember",thisWeekNewMember);
            map.put("thisMonthNewMember",thisMonthNewMember);
            map.put("todayOrderNumber",todayOrderNumber);
            map.put("todayVisitsNumber",todayVisitsNumber);
            map.put("thisWeekOrderNumber",thisWeekOrderNumber);
            map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
            map.put("thisMonthOrderNumber",thisMonthOrderNumber);
            map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
            map.put("hotSetmeal",hotSetmeal);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取会员的性别占比
     * @return
     */
    @Override
    public Map getMemberGenderReport() {
     List<Map<String,Object>> list= memberDao.findMemberGender();
     List<String> genderList=new ArrayList<>();
        if (list!=null&&list.size()>0) {
            for (Map<String, Object> map : list) {
                String name = (String) map.get("name");
                if (name.equals("1")) {
                    map.put("name", "男");
                    genderList.add((String) map.get("name"));
                } else {
                    map.put("name", "女");
                    genderList.add((String) map.get("name"));
                }
            }
        }
        Map map=new HashMap();
        map.put("memberGender",genderList);
        map.put("memberGenderCount",list);
        return map;
    }

    /**
     * 会员年龄段占比
     * @return
     */
    @Override
    public Map getMemberAgeReport() {
        List<String> ageList=new ArrayList<>();
        ageList.add("0-18岁");
        ageList.add("18-30岁");
        ageList.add("30-45岁");
        ageList.add("45岁以上");
        //0-18
        Map<String,Object> map1=new HashMap<>();
        Integer count1= memberDao.getAgeCount(0,18);
        map1.put("name", ageList.get(0));
        map1.put("value",count1);
        //18-30
        Map<String,Object> map2=new HashMap<>();
        Integer count2= memberDao.getAgeCount(18,30);
        map2.put("name", ageList.get(1));
        map2.put("value",count2);
        //30-45
        Map<String,Object> map3=new HashMap<>();
        Integer count3= memberDao.getAgeCount(30,45);
        map3.put("name", ageList.get(2));
        map3.put("value",count3);
        //45以上
        Map<String,Object> map4=new HashMap<>();
        Integer count4=memberDao.getMorethanAgeCount(45);
        map4.put("name", ageList.get(3));
        map4.put("value",count4);

        List<Map<String,Object>> list=new ArrayList<>();
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);

        Map map=new HashMap();
        map.put("memberAge",ageList);
        map.put("memberAgeCount",list);
        return map;
    }

    /**
     * 展示2个时间段之间会员数量
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public Map showByDate(String beginDate, String endDate) {
        Map<String,Object> map=new HashMap<>();
        try {
            List<String> months = DateUtils.getMonthBetween(beginDate, endDate, "yyyy-MM");
            //将月份封装到map中
            map.put("months",months);
            //获取每个对应会员数量
            List<Integer> memberCountList=new ArrayList<>();
            if (months!=null&&months.size()>0) {
                for (String month : months) {
                    Integer count = memberDao.getMemberCountByDate(month);
                    memberCountList.add(count);
                }
            }
            map.put("memberCount",memberCountList);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
