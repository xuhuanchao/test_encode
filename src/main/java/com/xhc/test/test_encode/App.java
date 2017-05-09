package com.xhc.test.test_encode;

import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;

/**
 * Hello world!
 *
 */
public class App {
    
    public static void commonsCodec (String str, String charset) throws Exception {
        System.out.println("\n\n********* 使用 org.apache.commons.codec.binary.Base64 工具进行 base64编码...");
        String base64 =  Base64.encodeBase64String(str.getBytes(charset));
        System.out.println("原始字符串:" + str + " \n编码后:" + base64);
        
        System.out.println("\n\n****************  使用 org.apache.commons.codec.binary.Base64 工具进行 base64解码...");
        byte[] bytes = Base64.decodeBase64(base64);
        String newStr = new String(bytes, charset);
        System.out.println("原始base64编码：" + base64 + "\n解码后：" + newStr);
    }
    
    public static void javamail(String str, String charset) throws Exception{
        System.out.println("\n\n ********** 使用 javax.mail.internet.MimeUtility 工具 进行base64编码...");
        String mimeBase64 = MimeUtility.encodeText(str ,charset , "B");
        System.out.println("原始字符串:" + str + " \n编码后:" + mimeBase64);
        
        System.out.println("\n\n ***************  使用javamail 的 javax.mail.internet.MimeUtility 工具 进行base64解码...");
        System.out.println("原始base64编码：" + mimeBase64 + "\n解码后：" + MimeUtility.decodeText(mimeBase64));

    }
    
    public static void main( String[] args ) throws Exception {
        String str = "张三";
        String charset = "gbk"; 
        commonsCodec(str, charset);
        javamail(str, charset);
    }
}
