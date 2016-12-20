package _00_init;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionUtil {
	
	public static boolean isAcValid(String ac) {
		String pattern = "^(?=.*[a-zA-Z]+)(?=.*\\d+)[a-zA-Z0-9]{8,16}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(ac);
		return m.matches();
	}
	
	public static boolean isPwValid(String pw) {
		String pattern = "^(?=.*[a-zA-Z]+)(?=.*\\d+)[a-zA-Z0-9]{8,16}$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(pw);
		return m.matches();
	}

	public static boolean isPhoneValid(String phone) {
		String pattern = "(04)+[\\d]{8}";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(phone);
		return m.matches();
	}
	
	public static boolean isEmailValid(String email) {
		String pattern = "^[_a-z0-9-]+([._a-z0-9-]+)*@[a-z0-9-]+([.a-z0-9-]+)*$";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	
	public static String removeTag(String str){
		Pattern p_script, p_style, p_html, p_special;
		Matcher m_script, m_style, m_html, m_special;

		//script
		String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
		p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		m_script = p_script.matcher(str);
		str = m_script.replaceAll("");
		
		//style
		String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
		p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		m_style = p_style.matcher(str);
		str = m_style.replaceAll("");
		
		//HTML
		String regEx_html = "<[^>]+>";
		p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		m_html = p_html.matcher(str);
		str = m_html.replaceAll("");
		
		//special case
		String regEx_special = "\\&[a-zA-Z]{1,10};";
		p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
		m_special = p_special.matcher(str);
		str = m_special.replaceAll("");
		
		return str;
	}

}
