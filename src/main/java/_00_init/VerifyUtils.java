package _00_init;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;

public class VerifyUtils {
	public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
	public static final String ADDRESS_VERIFY_URL = "https://maps.google.com/maps/api/geocode/json?sensor=false&language=zh-tw&region=tw&address=";

	public static boolean verify(String gRecaptchaResponse) {
		if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
			return false;
		}

		try {
			URL verifyUrl = new URL(SITE_VERIFY_URL);

			// Open Connection to URL
			HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

			// Add Request Header
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// Data will be sent to the server.
			String postParams = "secret=" + "6LcLfQcUAAAAAJTAPcEeMLMfDxK3cPJoNjcFoa_x" + "&response="
					+ gRecaptchaResponse;

			// Send Request
			conn.setDoOutput(true);

			// Get the output stream of Connection
			// Write data in this stream, which means to send data to Server.
			OutputStream outStream = conn.getOutputStream();
			outStream.write(postParams.getBytes());

			outStream.flush();
			outStream.close();

			// Response code return from server.
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode=" + responseCode);

			// Get the InputStream from Connection to read data sent from the
			// server.
			InputStream is = conn.getInputStream();

			JsonReader jsonReader = (JsonReader) Json.createReader(is);
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			// ==> {"success": true}
			System.out.println("Response: " + jsonObject);

			boolean success = jsonObject.getBoolean("success");
			return success;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static String verifyAdd(String add) {

		try {
			URL verifyUrl = new URL(ADDRESS_VERIFY_URL + add);

			// Open Connection to URL
			HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

			// Add Request Header
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			// Send Request
			conn.setDoOutput(false);

			// Response code return from server.
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode=" + responseCode);

			// Get the InputStream from Connection to read data sent from the
			// server.
			InputStream is = conn.getInputStream();

			JsonReader jsonReader = (JsonReader) Json.createReader(is);
			// System.out.println(jsonReader);
			JsonObject jsonObject = jsonReader.readObject();
			jsonReader.close();

			// ==> {"success": true}
			// System.out.println("Response: " + jsonObject);
			String status = jsonObject.getString("status");
			System.out.println("Response: " + status);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean verifyPicFile(String fileName) {

		int nameIndex = fileName.lastIndexOf(".");
		boolean result = false;
		if (nameIndex != -1) {
			fileName = fileName.substring(nameIndex);
			String[] types = { ".png", ".jpg", ".gif", ".jpeg" };
			for (String x : types) {
				if (fileName.equalsIgnoreCase(x)) {
					result = true;
				}
			}
		}
		return result;
	}

	public static boolean verifyCardNum(String cardNum) {

		if (cardNum.length() != 16) {
			return false;
		}
		int[] x = new int[16];
		int sum1 = 0, sum2 = 0;
		for (int i = 0; i < 16; i++) {
			try {
				x[i] = Integer.parseInt(cardNum.substring(i, i + 1));
			} catch (NumberFormatException nfe) {
				return false;
			}
			if (i % 2 == 0) {
				if (x[i] < 5)
					x[i] *= 2;
				else
					x[i] = x[i] * 2 - 10 + 1;
				sum2 += x[i];
			} else {
				sum1 += x[i];
			}
		}
		sum1 -= x[15];
		int y = 10 - ((sum1 + sum2) % 10);
		return (x[15] == y);
	}

}
