package vn.com.viettel.core.utils;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

public class InternationalizationUtils {

	private static ResourceBundleMessageSource messageBundle;

	private InternationalizationUtils(){
		// Disable Constructor
	}

	public static String getMessage(String msgCode) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageBundle().getMessage(msgCode, null, locale);
	}

	public static String getMessage(String msgCode,String lang) {
		Locale locale = Locale.forLanguageTag(lang);
		return messageBundle().getMessage(msgCode, null, locale);
	}

	public static synchronized ResourceBundleMessageSource messageBundle() {
		if(messageBundle != null){
			return messageBundle;
		}
		messageBundle = new ResourceBundleMessageSource();
		messageBundle.setBasenames("messages");
		messageBundle.setDefaultEncoding("UTF-8");
		messageBundle.setUseCodeAsDefaultMessage(true);
		return messageBundle;
	}

}
