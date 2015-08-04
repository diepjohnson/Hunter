package com.vn.cooperate.moneyhunter.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import com.vn.cooperate.moneyhunter.model.UserModel;

import android.content.Context;
import android.util.Log;

public class Util {

	
	public static String key="";
	private static final char []charKey = new char[]{'d','u','c','n','m','2','2','9','0','4'};
	public static String encryptMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
    public static String encryptString(String str,Context mContext) {
    	key="";
		StringBuffer sb = new StringBuffer(str);
		try {
			if(key.equals(""))
			{
			for (int i = 0; i < charKey.length; i++) {
				key+=charKey[i];
			}
			}
			
			int lenStr = str.length();
			int lenKey = key.length();
			UserModel user = UserModel.getUserInfor(mContext);
			Log.e("**accesstoken______", user.getAccessToken());
			int randomeKey = randomNumberLower(1000000,9999999);
			int flag = randomNumberLower(10, 20);
			key = getEndString(user.getAccessToken(),key,randomeKey,flag);
			Log.e("before encrip : *****", key);
			key = encryptMD5(key);
			Log.e("encrip : *****", key+" randomeKey : "+randomeKey+"  flag "+flag+" userId"+user.getUserId());
			// For each character in our string, encrypt it...
//			for (int i = 0, j = 0; i < lenStr; i++, j++) {
//				if (j >= lenKey)
//					j = 0; // Wrap 'round to beginning of key string.
//				sb.setCharAt(i, (char) (str.charAt(i) ^ key.charAt(j)));
//			}
//			key="";
		} catch (Exception e) {
			// TODO: handle exception
			 ByteArrayOutputStream baos = new ByteArrayOutputStream();
			  PrintStream stream = new PrintStream( baos );
			  e.printStackTrace(stream);
			  stream.flush();
			  Log.e("err", new String( baos.toByteArray() ));
			  key="";
		}
		
		return sb.toString();
	}
    static int randomNumberLower(int from,int end)
    {
    	Random r = new Random();
    	int number = r.nextInt(end - from) + from;
    	return number;
    }
    public static String addTostring(String base,String sub,int index)
    {
    	String base1 = base.substring(0, index);
    	String base2 = base.substring(index, base.length());
    	String data = base1+sub+base2;
    	return data;
    }
    
    public static String getEndString(String base1,String base2,int base3,int flag)
    {
    	
    	String data = addTostring(base1,base3+"",flag);
    	data+=base2;
    	return data;
    }
}
