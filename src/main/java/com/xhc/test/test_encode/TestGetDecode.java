package com.xhc.test.test_encode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;

public class TestGetDecode {

    public static String formateBinary(String s) {
        if (s.length() > 8) {
            String newStr = "";
            int n = s.length() / 8;
            for (int i = 0; i < n; i++) {
                if (i == 0) {
                    newStr = s.substring(i * 8, (i + 1) * 8);
                } else {
                    newStr += (" " + s.substring(i * 8, (i + 1) * 8));
                }
            }
            return newStr;
        } else {
            String newStr = s;
            int n = 8 - s.length();
            for (int i = 0; i < n; i++) {
                newStr = "0" + newStr;
            }
            return newStr;
        }
    }

    public static void test3() throws Exception {
        String s = "123 abc .,: 你好 。，： 繁體 。，：";
        File outfile = new File("F:/temp/coding/out.txt");
        FileWriter fw = new FileWriter(outfile);
        fw.write("原始文本：" + s + "\r\n");

        fw.write("gb2312编码：\r\n");
        Charset charset = Charset.forName("gb2312");
        byte[] bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("gb18030编码：\r\n");
        charset = Charset.forName("gb18030");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("gbk编码：\r\n");
        charset = Charset.forName("gbk");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("utf-8编码：\r\n");
        charset = Charset.forName("utf-8");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("unicode编码：\r\n");
        charset = Charset.forName("unicode");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("gb2312编码：\r\n");
        charset = Charset.forName("gb2312");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("ISO-8859-1编码：\r\n");
        charset = Charset.forName("ISO-8859-1");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.close();

    }

    public static void test() throws Exception {
        String test = "张三";
        String s = new String(test.getBytes());
        String s2 = new String(test.getBytes(), "gbk");
        String s3 = new String(test.getBytes("ISO-8859-1"), "ISO-8859-1");
        System.out.println(test);
        System.out.println(s);
        System.out.println(s2);
        System.out.println(s3);
        test1(test);
        test1(s);
        test1(s2);
        test1(s3);

        System.out.println(System.getProperty("file.encoding"));// java默认编码是UTF-8
        System.out.println(test); // getBytes已经是转码操作，不填的就默认用系统规定的
        System.out.println(new String(test.getBytes()));
        System.out.println(new String(test.getBytes(), "gbk"));
        System.out.println(new String(test.getBytes("ISO-8859-1"), "ISO-8859-1"));// 用什么拆就用什么组装，否则显示乱码

    }

    public static void test1(String s) throws Exception {
        String charset = "";
        byte[] first3Bytes = s.getBytes();

        if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
            charset = "UTF-16LE";
        }
        // FEFF 开头，为UTF-16BE
        else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
            charset = "UTF-16BE";
        }
        // EFBBBF 开头，为UTF-8
        else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
            charset = "UTF-8";
        }
        // FFFE 开头，为unicode
        else if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
            charset = "unicode";
        }
        // FEFF 开头，为unicode big endian
        else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
            charset = "unicode big endian";
        }
        // 其他情况默认为GBK编码
        else {
            charset = "GBK";
        }

        System.out.println("charset is " + charset);
    }

    public static void testAscii(String s) throws Exception {
        if(s != null && s.length() > 0){
            char[] charArray = s.toCharArray();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<charArray.length; i++){
                sb.append(charArray[i] + " ");
            }
            System.out.println( sb.toString());
        }
        
    }

    public static void stringToBase64(String s) throws Exception {
//        byte[] result = Base64.encodeBase64(s.getBytes());
//        outBytes(result);
        
        
        String base64 = Base64.encodeBase64URLSafeString(s.getBytes());
        System.out.println(base64);
    }

    public static void outBytes(byte[] bytes) throws Exception {
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i] + " ");
        }
        System.out.println("");
    }

    /**
     * 将指定进制的数字 转为 二进制
     * @param num 
     * @param radix
     * @return
     * @throws Exception
     */
    public static String toBinary(String num , int radix) throws Exception{
        int intversion = Integer.parseInt(num, radix);
        String binaryVers = Integer.toBinaryString(intversion);
        return binaryVers;
    }
    
    /**
     * 将asc2码转为二进制数组
     * @param asc2
     * @return
     * @throws Exception
     */
    public static String[] asciiToBinary(int[] asc2) throws Exception{
        String[] binary = new String[asc2.length];
        for(int i=0; i<asc2.length; i++){
            String b = Integer.toBinaryString(asc2[i]);
            if(b.length() < 8){
                for(int j=0 , k=8-b.length(); j<k ; j++){
                    b = '0' + b;    
                }
                
            }
            binary[i] = b;
            System.out.print(b + " ");
        }
        System.out.println();
        return binary;
    }
    
    
    /**
     * 获取字符串对应的ascii码数组
     * @param s
     * @return int[]
     * @throws Exception
     */
    public static int[] getStringAscii(String s) throws Exception{
        StringBuilder sb = new StringBuilder();
        byte[] bytes = s.getBytes();
        int[] asc2 = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i] + " ");
            asc2[i] = bytes[i];
        }
        System.out.println(sb.toString());
        return asc2;
    }
    
    
    public static void main(String[] args)  throws Exception{
        String s = "s13中国";
        System.out.println("原始字符串：" + s);
        System.out.println("ascii编码：");
        int[] asc2 = getStringAscii(s);
        
        System.out.println("ascii码 转为二进制数据");
        
        String[] asciiToBinary = asciiToBinary(asc2);
        System.setProperty("sun.jnu.encoding", "gb2312");
        System.out.println(System.getProperty("sun.jnu.encoding"));
        
        System.out.println("base64编码：");
        stringToBase64(s);
        
//        outBytes(s.getBytes());
//        
//        
    }
}
