package com.example.firstspringboot.common.demo.sort;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.math.BigDecimal;
import java.text.Collator;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @Author :sunwenwu
 * @Date : 2020/8/5 9:58
 * @Description :
 */
public class SortBean implements Comparable<SortBean> {


    private final Collator chinaSortUtil =  Collator.getInstance(Locale.CHINA);

    private Long id;

    private String name;

    private BigDecimal balance;

    private Integer useTimes;

    //Y  置顶  N 非置顶
    private String topLogo;

    private LocalDateTime dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getUseTimes() {
        return useTimes;
    }

    public void setUseTimes(Integer useTimes) {
        this.useTimes = useTimes;
    }

    public String getTopLogo() {
        return topLogo;
    }

    public void setTopLogo(String topLogo) {
        this.topLogo = topLogo;
    }

    @Override
    public int compareTo(SortBean o) {

        int top =  topLogoSort(o);

        if (top != 0) {
            return top;
        }

        int balance = this.balance.compareTo(o.getBalance());

        if (balance != 0) {
            return balance;
        }

        int useTimes = this.getUseTimes().compareTo(o.getUseTimes());

        if (useTimes != 0) {
            return useTimes;
        }

        int chinaSort = chinaSortUtil.getCollationKey(this.getName()).compareTo(chinaSortUtil.getCollationKey(o.getName()));

        if (chinaSort != 0) {
            return chinaSort * -1;
        }

        int date = this.getDateTime().compareTo(o.getDateTime());

        if (date != 0) {
            return date;
        }

        return 0;
    }



    private int topLogoSort(SortBean o) {
        if(this.getTopLogo().equals(o.getTopLogo())){
            return 0;
        } else if ("Y".equals(this.getTopLogo())){
            return 1;
        } else {
            return -1;
        }
    }


    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();

        SortBean s1 = new SortBean();
        s1.setId(1L);
        s1.setBalance(new BigDecimal("100"));
        s1.setName("上海");
        s1.setUseTimes(10);
        s1.setTopLogo("Y");
        s1.setDateTime(now.plusMinutes(1));

        SortBean s2 = new SortBean();
        s2.setId(2L);
        s2.setBalance(new BigDecimal("200"));
        s2.setName("成都");
        s2.setUseTimes(21);
        s2.setTopLogo("N");
        s2.setDateTime(now.plusMinutes(2));


        SortBean s3 = new SortBean();
        s3.setId(3L);
        s3.setBalance(new BigDecimal("100"));
        s3.setName("上海");
        s3.setUseTimes(10);
        s3.setTopLogo("Y");
        s3.setDateTime(now.plusMinutes(2));

        List<SortBean> sortBeans = Arrays.asList(s1, s2,s3);


        System.out.println("排序前：======"+JSON.toJSONString(sortBeans));

        Collections.sort(sortBeans,Collections.reverseOrder());

        System.out.println("排序后：======"+JSON.toJSONString(sortBeans));


    }

}