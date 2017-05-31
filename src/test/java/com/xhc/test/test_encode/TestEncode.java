package com.xhc.test.test_encode;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import org.junit.*;

public class TestEncode {

    public static void main(String[] args) throws Exception {
        int a = 13;
        byte[] bytes = (a + "").getBytes();
        System.out.println(FormatUtil.toBinary(a+ "", 10));
        a = a >> 1;
        System.out.println(FormatUtil.toBinary(a+ "", 10));
        System.out.println(a);
        a = 13;
        a = a << 1;
        System.out.println(FormatUtil.toBinary(a+ "", 10));
        System.out.println(a);
    }
    
    @Test
    public void t() throws Exception{
        String s = "1110001111";
        System.out.println(FormatUtil.formateBinary(s));
        
    }
    
    /**
     * 测试各种字符集下
     * @throws Exception
     */
    @Test
    public void testVariousEncode() throws Exception {
        String s = "123abc.,:你好。，：繁體";
        File outfile = new File("F:/temp/coding/out.txt");
        FileWriter fw = new FileWriter(outfile);
        fw.write("原始文本 [" + s + "]\r\n");
        StringBuffer sb = new StringBuffer();

        fw.write("gb2312编码：\r\n");
        Charset charset = Charset.forName("gb2312");
        byte[] bytes = s.getBytes(charset);
        sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toBinaryString(bytes[i] & 0xff) + " ");
        }
        fw.write(sb.toString());
        
        fw.write("\r\ngb18030编码：\r\n");
        charset = Charset.forName("gb18030");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(FormatUtil.formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("gbk编码：\r\n");
        charset = Charset.forName("gbk");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(FormatUtil.formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("utf-8编码：\r\n");
        charset = Charset.forName("utf-8");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(FormatUtil.formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("unicode编码：\r\n");
        charset = Charset.forName("unicode");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(FormatUtil.formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("gb2312编码：\r\n");
        charset = Charset.forName("gb2312");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(FormatUtil.formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

        }

        fw.write("ISO-8859-1编码：\r\n");
        charset = Charset.forName("ISO-8859-1");
        bytes = s.getBytes(charset);
        for (int i = 0; i < bytes.length; i++) {
            fw.write(FormatUtil.formateBinary(Integer.toBinaryString(bytes[i])) + "\r\n");

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
    
    
    
    
    
}
