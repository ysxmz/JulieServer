package util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

public class Base64Utils {
	public static boolean GenerateImage(String imgStr, String imgFilePath) {
	    if (imgStr == null) // 图像数据为空
	        return false;
	BASE64Decoder decoder = new BASE64Decoder();
	    try {
	        // Base64解码
	        byte[] bytes = decoder.decodeBuffer(imgStr);
	        for (int i = 0; i < bytes.length; ++i) {
	            if (bytes[i] < 0) {// 调整异常数据
	                bytes[i] += 256;
	            }
	        }
	        // 生成jpeg图片
	        OutputStream out = new FileOutputStream(imgFilePath);
	        out.write(bytes);
	        out.flush();
	        out.close();
	        return true;
	    } catch (Exception e) {
	        return false;
	    }
	}
}
