package function;

import java.util.Locale;

public class HexTrans {
	private final static char[] mChars = "0123456789ABCDEF".toCharArray();  
    private final static String mHexStr = "0123456789ABCDEF";  
	/**
	 * 二进制转十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer md5str = new StringBuffer();
		// 把数组每一字节换成16进制连成md5字符串
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			digital = bytes[i];

			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString().toUpperCase();
	}
	/**  
     * 十六进制字符串转换成 ASCII字符串 
     * @param str String Byte字符串 
     * @return String 对应的字符串 
     */    
    public static String hexStr2Str(String hexStr){    
        hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);  
        char[] hexs = hexStr.toCharArray();    
        byte[] bytes = new byte[hexStr.length() / 2];    
        int iTmp = 0x00;;    
  
        for (int i = 0; i < bytes.length; i++){    
            iTmp = mHexStr.indexOf(hexs[2 * i]) << 4;    
            iTmp |= mHexStr.indexOf(hexs[2 * i + 1]);    
            bytes[i] = (byte) (iTmp & 0xFF);    
        }    
        return new String(bytes);    
    }  
      
    /** 
     * bytes转换成十六进制字符串 
     * @param b byte[] byte数组 
     * @param iLen int 取前N位处理 N=iLen 
     * @return String 每个Byte值之间空格分隔 
     */  
    public static String byte2HexStr(byte[] b, int iLen){  
        StringBuilder sb = new StringBuilder();  
        for (int n=0; n<iLen; n++){  
            sb.append(mChars[(b[n] & 0xFF) >> 4]);  
            sb.append(mChars[b[n] & 0x0F]);  
            sb.append(' ');  
        }  
        return sb.toString().trim().toUpperCase(Locale.US);  
    }  

}
