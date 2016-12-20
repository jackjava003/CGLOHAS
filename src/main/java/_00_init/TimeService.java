package _00_init;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 本類別負責處理時間的資訊。
public class TimeService {

	// 方法一、取得相對時間：
	public static String getRelativeTime(java.sql.Timestamp absoluteTime) {

		// 把從資料庫取得的時間轉換成下面設定的日期格式
		// 雖然資料庫日期的格式與我們設定的格式相同
		// 但資料庫所取得的時間是一個String型態，而要做時間差的計算需要轉換為Date型態才能做計算
		// 所以下面的程式碼就是將String型態的時間轉換為Date型態的時間
		// MM :月 mm :分 ss:秒 SS:毫秒
		String relativeTime = "Unknown time";

		// 取得當下的時間
		Date now = new Date();
		// 由於Java處理時間是以毫秒計算，所以要除1000L將毫秒轉換成秒
		long between = (now.getTime() - absoluteTime.getTime()) / 1000L;
//		long year = between / (365 * 24 * 3600); // 秒轉換成年
//		long month = between / (30 * 24 * 3600); // 秒傳換成月
//		long week = between / (7 * 24 * 3600); // 秒轉換成週
		long day = between / (24 * 3600); // 秒轉換成天
		long hour = between % (24 * 3600) / 3600; // 秒轉換成小時
		long minute = between % 3600 / 60; // 秒轉換成分鐘
		long second = between; // 秒

		if (day >= 7) { // days<=30 &&
			relativeTime = new Date(absoluteTime.getTime()).toString();
			relativeTime = relativeTime.substring(0,relativeTime.indexOf("GMT"));
		} else if (day >= 1) {
			relativeTime = day + " days ago";
		} else if (hour >= 1) { // days<1 &&
			relativeTime = hour + " hours ago";
		} else if (second > 60) {
			relativeTime = minute + " minutes ago";
		} else {
			relativeTime = second + " seconds ago";
		}

		return relativeTime;
	}
}
