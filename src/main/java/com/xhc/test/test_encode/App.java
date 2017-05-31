package com.xhc.test.test_encode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;

/**
 * Hello world!
 *
 */
public class App {

    
    public static void main(String[] args) throws Exception {
        CodeUtil.selfToolOperateLog = true;
        String sysCharset = System.getProperty("sun.jnu.encoding");
        String inStr = "";
        String enOrdecode = "";
        String operateType = "";
        System.out.println("请输入要进行的操作：1.字符串 to base64编码， 2.base64解码 to 字符串");
        Scanner input = new Scanner(System.in);
        enOrdecode = input.nextLine();
        
        System.out.println("请输入使用的工具： 1.自定义工具  , 2.commons-codec , 3.javax.mail , other. 所有依次执行");
        operateType = input.nextLine();
        
        System.out.println("请输入要进行处理的字符串：");
        inStr = input.nextLine();
        
        if(operateType.equals("1")){
            if(enOrdecode.equals("1")){
                System.out.println("使用自定义工具进行base64编码后的结果： "+CodeUtil.encodeStrToBase64(inStr, sysCharset));    
            }else if(enOrdecode.equals("2")){
                System.out.println("抱歉还不支持使用自定义工具进行base64解码");
            }
        }else if(operateType.equals("2")){
            if(enOrdecode.equals("1")){
                System.out.println("使用commons-codec进行base64编码后的结果：" + CodeUtil.commonscodecEncode(inStr, sysCharset));
            }else if(enOrdecode.equals("2")){
                System.out.println("使用commons-codec进行base64解码后的结果：" + CodeUtil.commonscodecDecode(inStr, sysCharset));
            }
        }else if(operateType.equals("3")){
            if(enOrdecode.equals("1")){
                System.out.println("使用javax.mail进行base64编码后的结果：" + CodeUtil.javamailEncode(inStr, sysCharset));
            }else if(enOrdecode.equals("2")){
                System.out.println("使用javax.mail进行base64解码后的结果：" + CodeUtil.javamailDecode(inStr));
            }
        }else {
            if(enOrdecode.equals("1")){
                System.out.println("使用自定义工具进行base64编码后的结果： "+CodeUtil.encodeStrToBase64(inStr, sysCharset));
            }else if(enOrdecode.equals("2")){
                System.out.println("抱歉还不支持使用自定义工具进行base64解码");
            }
            if(enOrdecode.equals("1")){
                System.out.println("使用commons-codec进行base64编码后的结果：" + CodeUtil.commonscodecEncode(inStr, sysCharset));
            }else if(enOrdecode.equals("2")){
                System.out.println("使用commons-codec进行base64解码后的结果：" + CodeUtil.commonscodecDecode(inStr, sysCharset));
            }
            if(enOrdecode.equals("1")){
                System.out.println("使用javax.mail进行base64编码后的结果：" + CodeUtil.javamailEncode(inStr, sysCharset));
            }else if(enOrdecode.equals("2")){
                System.out.println("使用javax.mail进行base64解码后的结果：" + CodeUtil.javamailDecode(inStr));
            }
        }
        
        System.out.println("程序执行结束！");
    }
}
