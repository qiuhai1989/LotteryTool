package com.yuncai.core.util;

public class CompileUtil {
	
	//正则表达式数字验证 
	public static boolean isNumber(String str) 
    { 
		//[1-9][0-9]*
        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("^[0-9]*$"); 
        java.util.regex.Matcher match=pattern.matcher(str); 
        if(match.matches()==false) 
        { 
           return false; 
        } 
        else 
        { 
           return true; 
        } 
    }

}
