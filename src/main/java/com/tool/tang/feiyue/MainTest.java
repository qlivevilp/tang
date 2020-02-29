package com.tool.tang.feiyue;

import java.util.Arrays;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {

    }
    //循环方式判断字符类型
    //递归方式判断字符类型

    /*
    判断是否为汉字
     */
    public Boolean isChinese(String mixString) {
        //将字符串拆分成list
        List<String> stringList = Arrays.asList(mixString.split(""));
        //遍历数组
        stringList.stream().filter(string -> isLetter(string)).sum();
        Character.isLetter('s');
        Character.isDigit('s');
        return true;
    }


    /*
  判断是否为数字
   */
    public Boolean isData(String mixString) {
        //将字符串拆分成list
        List<String> stringList = Arrays.asList(mixString.split(""));
        char s = 's';

        //遍历数组
        stringList.stream().filter(string -> isLetter(string)).sum();
        return true;
    }

    /*
  判断是否为字母
   */
    public Boolean isLetter(String mixString) {
        //遍历数组
        mixString.charAt()
        return true;
    }
}
