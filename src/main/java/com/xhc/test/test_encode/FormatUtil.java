package com.xhc.test.test_encode;

public class FormatUtil {

    
    /**
     * 将byte 数组格式化 成字符串，每个 ascii 间隔空格
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String formateBytes(byte[] bytes) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(bytes[i] + " ");
        }
        return sb.toString();
    }
    
    /**
     * 将字符串数组格式化为一个字符串，每个字符串间有一个空格
     * @param ss
     * @return
     * @throws Exception
     */
    public static String formateStringArray(String[] ss) throws Exception{
        StringBuilder sb = new StringBuilder();
        for(String s : ss){
            sb.append(s + " ");
        }
        return sb.toString();
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
     * 补齐一个字符串，使用指定字符，补齐到指定长度
     * @param s
     * @param addStr
     * @param length
     * @return
     * @throws Exception
     */
    public static String addStrForLength(String s, String addStr, int length) throws Exception{
        StringBuffer sb = new StringBuffer();
        int d = length - s.length();
        if(d > 0){
            for(int i=0 ; i<d; i++){
                sb.append(addStr);
            }
            sb.append(s);
        }
        return sb.toString();
    }
    
    
    /**
     * 格式化二进制字符串，8位一组以空格分开
     * 不足8位前面补0
     * @param s
     * @return
     */
    public static String formateBinary(String s) throws Exception{
        StringBuffer sb = new StringBuffer();
        int l = s.length();
        int n = l / 8;
        int m = l % 8;
        if (l > 8) {
            String newStr = "";
            for(int i =0 , j = m>0 ? n:++n ; i<j ; i++){
                if(i > 0){
                    sb.insert(0 , " ");
                }
                if(i == j-1){
                    String substr = s.substring(0 , l-i*8);
                    if(substr.length() < 8){
                        sb.insert(0, addStrForLength(substr , "0" , 8));
                    }
                }else{
                    sb.insert(0 , s.substring(l-(i+1)*8, l-i*8));    
                }
            }
        } else {
            sb.insert(0, addStrForLength(s , "0" , 8));
        }
        return sb.toString();
    }
}
