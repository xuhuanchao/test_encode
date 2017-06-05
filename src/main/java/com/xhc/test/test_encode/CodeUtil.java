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
    
    public static boolean selfToolOperateLog = false;
    
    /**
     * 获取字符串对应的ascii码数组
     * @param s
     * @return int[]
     * @throws Exception
     */
    public static int[] getStringAscii(String s , String charset) throws Exception{
        StringBuilder sb = new StringBuilder();
        byte[] bytes = s.getBytes(charset);
        int[] asc2 = new int[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i] + " ");
            asc2[i] = bytes[i];
        }
        if(selfToolOperateLog){
            System.out.println("字符串转byte数组后对应的ascii码： " + sb.toString());    
        }
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
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<asc2.length; i++){
            String b = Integer.toBinaryString(asc2[i] & 0xff);
            if(b.length() < 8){
                for(int j=0 , k=8-b.length(); j<k ; j++){
                    b = '0' + b;    
                }
                
            }
            binary[i] = b;
            if(selfToolOperateLog){
                sb.append(b + " ");
            }
        }
        if(selfToolOperateLog){
            System.out.println("ascii码数组对应的二进制数字为：" + sb.toString());
        }
        return binary;
    }
    
    
    /**
     * 将二进制编码转为base64的格式
     * 将原始二进制 6位一组，组合
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
        if(selfToolOperateLog){
            System.out.println("将二进制数字格式化为base64处理时的格式：" + formateBase64Binary);    
        }
        
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
        String mimeBase64 = javamailEncode(str, charset);
        System.out.println("原始字符串:" + str + " \n编码后:" + mimeBase64);
        
        System.out.println("\n\n ********** 使用 javax.mail.internet.MimeUtility 工具 进行base64解码...");
        String decodeText = javamailDecode(mimeBase64);
        System.out.println("原始base64编码：" + mimeBase64 + "\n解码后：" + decodeText);
    }
    
    /**
     * 使用commons-codec 进行base64编码
     * @param str
     * @param charset
     * @return
     * @throws Exception
     */
    public static String commonscodecEncode (String str, String charset) throws Exception {
        String base64 =  Base64.encodeBase64String(str.getBytes(charset));
        return base64;
    }
    
    /**
     * 使用commons-codec 进行base64解码
     * @param base64
     * @param charset
     * @return
     * @throws Exception
     */
    public static String commonscodecDecode (String base64  , String charset) throws Exception{
        byte[] bytes = Base64.decodeBase64(base64);
        String newStr = new String(bytes, charset);
        return newStr;
    }
    
    /**
     * 使用javax.mail 进行mime base64 编码
     * @param str
     * @param charset
     * @return
     * @throws Exception
     */
    public static String javamailEncode(String str, String charset) throws Exception{
        String mimeBase64 = MimeUtility.encodeText(str ,charset , "B");
        return mimeBase64;
    }
    
    /**
     * 使用javax.mail 进行mime base64解码
     * @param mimeBase64
     * @return
     * @throws Exception
     */
    public static String javamailDecode(String mimeBase64) throws Exception {
        String decodeText = MimeUtility.decodeText(mimeBase64);
        return decodeText;
    }
    
    
    public static void main(String[] args)  throws Exception{
//        String str = "asd1s张";
//        String charset = "gbk";
//        System.out.println(encodeStrToBase64(str, charset));
//        commonsCodec(str, charset);
//        javamail(str, charset);
        String a = javamailDecode("=C8=E7=B9=FB=C4=FA==B4=ED=CE=F3=B5=D8=BD=D3=CA=D5=C1=CB=B1=BE=B5=E7=D7=D3=D3=CA=BC=FE=A3=AC==C7=EB=C1=A2=BC=B4=BB=D8=B8=B4=B2=A2=CD=A8=D6=AA=B7=A2=BC=FE=C8=CB=A3=AC==B2=A2=D3=C0=BE=C3=C9=BE=B3=FD=B8=C3=B5=E7=D7=D3=D3=CA=BC=FE=BC=B0=C6=E4==CB=F9=D3=D0=B8=BD=BC=FE=BA=CD=CF=FA=BB=D9=CB=F9=D3=D0=B8=B4=D3=A1=BC=FE==A1=A3=D0=BB=D0=BB=A3=A1");
        System.out.println(a);
    }
}
