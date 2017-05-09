package com.xhc.test.test_encode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeUtility;

import org.apache.commons.codec.binary.Base64;

public class CodeUtil {
    
    //base64 code
    public static Map<Integer, Character> base64Map=  new HashMap<Integer, Character>();
    static {
        int ascii_num_A =  65;
        for(int i=0 ;i<26 ; i++){
            base64Map.put(i , (char)ascii_num_A++);    
        }
        int ascii_num_a = 97;
        for(int i=26 ;i<52 ; i++){
            base64Map.put(i , (char)ascii_num_a++ );    
        }
        
        
        int ascii_num_0 = 48;
        for(int i=52 ;i<62 ; i++){
            base64Map.put(i , (char)ascii_num_0++ );    
        }
        
        base64Map.put(62, '+');
        base64Map.put(63, '/');
    }
    
    
    /**
     * 获取字符串对应的ascii码数组
     * @param s
     * @return int[]
     * @throws Exception
     */
    public static int[] getStringAscii(String s , String code) throws Exception{
        StringBuilder sb = new StringBuilder();
        byte[] bytes = s.getBytes(code);
        int[] asc2 = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i] + " ");
            asc2[i] = bytes[i];
        }
        System.out.println(sb.toString());
        return asc2;
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
            String b = Integer.toBinaryString(asc2[i] & 0xff);
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
     * 将二进制编码转为base64的格式
     * 6位一组
     * @param oriBinary
     * @return
     * @throws Exception
     */
    public static String[] getBase64Binary(String[] oriBinary) throws Exception{
        List<String> base64Binary = new ArrayList<String>(); 
        
        for(int i=0 , j = oriBinary.length; i<j ; i++){
            String oldBinary = oriBinary[i];
            
            if(base64Binary.size() == 0){
                base64Binary.add(oldBinary.substring(0, 6));
                base64Binary.add(oldBinary.substring(6));
            }else{
                String curBinary = base64Binary.get(base64Binary.size()-1);
                if(curBinary.length() < 6){
                    int l = 6 - curBinary.length();
                    curBinary += oldBinary.substring(0, l);
                    base64Binary.set(base64Binary.size()-1 , curBinary);
                    base64Binary.add(oldBinary.substring(l));
                }else{
                    base64Binary.add(oldBinary.substring(0, 6));
                    base64Binary.add(oldBinary.substring(6));
                }    
            }
            
        }
        if(base64Binary.size() > 0){
            String curBinary = base64Binary.get(base64Binary.size()-1);
            int l = 6 - curBinary.length();
            if(l > 0){
                for(int i=0; i<l ; i++){
                    curBinary += "0";
                }
                base64Binary.set(base64Binary.size()-1, curBinary);
            }
        }
        
        String[] a = new String[base64Binary.size()];
        return base64Binary.toArray(a);
    }
    
    /**
     * 将输入的字符串按指定编码转为base64
     * @param str
     * @param charset
     * @return
     * @throws Exception
     */
    public static String encodeStrToBase64 (String str , String charset) throws Exception{
        StringBuffer sb = new StringBuffer();
        int[] asciis = getStringAscii(str, charset);
        String[] binarys = asciiToBinary(asciis);
        String[] base64Binary = getBase64Binary(binarys);
        String formateBase64Binary = FormatUtil.formateStringArray(base64Binary);
        System.out.println(formateBase64Binary);
        for(int i=0; i<base64Binary.length ; i++){
            sb.append(base64Map.get(Integer.parseInt(base64Binary[i],2)));
        }
        int n = base64Binary.length % 4;
        for(int i=0; i<n; i++){
            sb.append("=");
        }        
        return sb.toString();
    }
    
    
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
    
    
    public static void main(String[] args)  throws Exception{
        String str = "张三";
        String charset = "gbk";
        System.out.println(encodeStrToBase64(str, charset));
        commonsCodec(str, charset);
        javamail(str, charset);
    }
}
