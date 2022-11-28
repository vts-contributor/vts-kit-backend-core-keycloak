package vn.com.viettel.core.utils;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class NetworkUtils {
	private static Logger logger = Logger.getLogger(NetworkUtils.class);
	public static String getCurrentHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e) {
			logger.error(e);
			return "default";
		}
	}
}
