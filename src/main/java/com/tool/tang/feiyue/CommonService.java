package com.tool.tang.feiyue;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @author: create by shujuan.tang
 * @description: com.tool.tang.feiyue
 * @date:2020/2/29
 **/
@Service
public class CommonService {

    public static final String LETTER = "letter";
    public static final String CHINESE = "chinese";
    public static final String DIGIT = "digit";

    //循环方式判断字符类型
    public void judgeCharacterByLoop(String mixString) {
        //将字符串拆成字符数组
        char[] chars = mixString.toCharArray();
        int chineseCount = 0, letterCount = 0, digitCount = 0;
        String charType = "";
        //循环数组判断每一个
        for (int i = 0; i < chars.length; i++) {
            charType = getCharType(chars[i]);
            switch (charType) {
                case CHINESE:
                    chineseCount++;
                    break;
                case LETTER:
                    letterCount++;
                    break;
                case DIGIT:
                    digitCount++;
                    break;
                default:
                    break;

            }
        }
        System.out.printf("中文:%d 英文:%d 数字:%d", chineseCount, letterCount, digitCount);
    }

    //递归方式判断字符类型
    public void judgeCharacterByRecursion(String mixString, int index, int chineseCount, int letterCount, int digitCount) {
        //判断是否是最后一个字符
        if (index == mixString.length()) {
            System.out.printf("中文:%d 英文:%d 数字:%d", chineseCount, letterCount, digitCount);
        } else {
            String charType = getCharType(mixString.charAt(index));
            switch (charType) {
                case CHINESE:
                    chineseCount++;
                    break;
                case LETTER:
                    letterCount++;
                    break;
                case DIGIT:
                    digitCount++;
                    break;
                default:
                    break;

            }
            judgeCharacterByRecursion(mixString, ++index, chineseCount, letterCount, digitCount);

        }
    }

    public String getCharType(char c) {
        //汉字
        if (isChinese(c)) {
            return CHINESE;
        }
        //数字
        else if (Character.isDigit(c)) {
            return DIGIT;
        }
        //字母
        else if (Character.isLetter(c)) {
            return LETTER;
        } else {
            return "";
        }
    }

    /**
     * 判断是否为汉字
     */
    public Boolean isChinese(char chinese) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(chinese);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 文件操作，分行将字符串写入txt文件
     */
    public void writeFile(String text) {
        File file = new File("C:/test");
        if (!file.exists()) {//如果文件夹不存在
            file.mkdirs();//创建文件夹
        }
        try {
            List<String> stringList = Arrays.asList(text.split(""));
            FileOutputStream fileOutputStream = new FileOutputStream("C:/test/test.txt");
            //获取系统的分行符
            String newLine = System.getProperty("line.separator");
            //每个字后插入一个分行符
            String join = Joiner.on(newLine).join(stringList);
            //写入文件中
            fileOutputStream.write(join.getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 解析价格 (500CNY+200CNY)*4|(100USD+20USD)*2
     * 计算公式：美元USD兑换人民币CNY的汇率为6.82432;
     * 总价=(500+200)*4+(100+20) *6.82432*2=2800+1637.8368=4437.8368
     * 四舍五入取整后为4438
     */
    public static final String USD = "USD";
    public BigDecimal getTotalPrice(String priceText) {
        //将美元和人名币分开
        List<String> stringList = Splitter.on("|").splitToList(priceText);
        String adultString = stringList.get(0);
        String childString = stringList.get(1);
        Integer adultNum = Integer.valueOf(adultString.substring(adultString.length() - 1, adultString.length() ));
        Integer childNum = Integer.valueOf(childString.substring(childString.length() - 1, childString.length()));
        //匹配币种前的数字作为价格
        //成人票价
        Integer adultTicket = Integer.valueOf(adultString.substring(1, adultString.indexOf("CNY")));
        //成人税费
        Integer adultTax = Integer.valueOf(adultString.substring(adultString.indexOf("+")+1, adultString.lastIndexOf("CNY")));
        //儿童票价
        Integer childTicket = Integer.valueOf(childString.substring(1, childString.indexOf("USD")));
        //儿童税费
        Integer childTax = Integer.valueOf(childString.substring(childString.indexOf("+")+1, childString.lastIndexOf("USD")));
        double exchangeRate=6.82432;
        double totalPrice=adultNum*(adultTax+adultTicket)+childNum*(childTax+childTicket)*exchangeRate;
        BigDecimal  totalPriceFormat = new BigDecimal(totalPrice).setScale(0, BigDecimal.ROUND_HALF_UP);
        return totalPriceFormat;
    }


    //9996为10000以内7的最大倍数
    public void selectSeven() {
        for (int seven = 7, mulptiple = 0, presentNum = 0; presentNum < 9996; mulptiple++) {
            presentNum = mulptiple * seven;
            System.out.printf("%8d", presentNum);
            //每输出十个数换一次行
            if (mulptiple % 10 == 0) {
                System.out.println();
            }
        }

        /**
         * 统计字符串中各字符出现的次数，并按照规定格式和自然顺序
         * 输出给定字符串："asdf56dAf4353dSERa2RAH33s1H2sR1S2ssddf" ，
         * 输出：1:2 2:3 3:4 4:1 5:2 6:1 A:2 E:1 H:2 R:3 S:2 a:2 d:5 f:3 s:5
         */
    }
    /**
     * 统计字符串中各字符出现的次数，
     * 并按照规定格式和 自然顺序 输出给定字符串
     * 数字-》大写字母-》小写字母
     */
    public LinkedHashMap anlysisChar(String mixString){
        List<String> stringList = Arrays.asList(mixString.split(""));
        //存放每个字符出现的次数
        HashMap<String,Integer> countMap= Maps.newHashMap();
        for (int i = 0; i <stringList.size() ; i++) {
            if(countMap.containsKey(stringList.get(i))){
                //已经计数的字符，计数加一
                countMap.put(stringList.get(i),countMap.get(stringList.get(i))+1);
            }
            else{
                //每出现一个新字符，计数一次
                countMap.put(stringList.get(i),1);
            }
        }
        //将所有出现过的字符排序，根据ASCII码来排序
        LinkedHashMap<String, Integer> sortedCountMap = countMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return sortedCountMap;
    }
}
