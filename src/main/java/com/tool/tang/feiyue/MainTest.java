package com.tool.tang.feiyue;

public class MainTest {
    public static void main(String[] args) {
        CommonService commonService = new CommonService();
        //1.循环
        commonService.judgeCharacterByLoop("中文123AGBjkd8还有993");
        //2.递归
        commonService.judgeCharacterByRecursion("中文123AGBjkd8还有993", 0, 0, 0, 0);
        //3.文件
        commonService.writeFile("我是一列竖着的测试字符串!");
        //4.计算价格
        commonService.getTotalPrice("(500CNY+200CNY)*4|(100USD+20USD)*2");
        //6.10000以内7的倍数
        commonService.selectSeven();
        //7.统计字符串中各字符出现的次数，并排序
        commonService.anlysisChar("asdf56dAf4353dSERa2RAH33s1H2sR1S2ssddf");


    }

}
