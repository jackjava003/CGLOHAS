package _00_init;

public class CountStrUtil {
	
	public static int countSum(String str) {
		int abccount = 0;
		int numcount = 0;
		int spacecount = 0;
		int othercount = 0;
		char[] b = str.toCharArray();
		for (int i = 0; i < b.length; i++) {
			if (b[i] >= 'a' && b[i] <= 'z' || b[i] >= 'A' && b[i] <= 'Z') {
				abccount++;
			} else if (b[i] >= '0' && b[i] <= '9') {
				numcount++;
			} else if (b[i] == ' ') {
				spacecount++;
			} else {
				othercount++;
			}
		}
		int sum = abccount + numcount + spacecount + othercount;
//		System.out.println("字符串中含有的英文字母數为：" + abccount);
//		System.out.println("字符串中含有的數字數为：" + numcount);
//		System.out.println("字符串中含有的空格數为：" + spacecount);
//		System.out.println("字符串中含有的其他字符为：" + othercount);
		return sum;
	}
}
