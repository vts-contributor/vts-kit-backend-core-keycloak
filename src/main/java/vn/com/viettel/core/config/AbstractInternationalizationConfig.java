package vn.com.viettel.core.config;

import vn.com.viettel.core.utils.InternationalizationUtils;
import vn.com.viettel.core.utils.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public abstract class AbstractInternationalizationConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {
	protected static final Locale LOCALE_EN = new Locale("en");
	protected static final Locale LOCALE_VI = new Locale("vi");

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		List<Locale> locales = getListLocale();
		Locale defaultLocale = Locale.lookup(Locale.LanguageRange.parse(getDefaultLanguage()), locales);
		if (StringUtils.isNullOrEmpty(request.getHeader("Accept-Language"))) {
			return defaultLocale;
		}
		List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
		Locale locale = Locale.lookup(list, locales);
		if (locale == null) {
			locale = defaultLocale;
		}
		return locale;
	}

	protected abstract String getDefaultLanguage();

	protected List<Locale> getListLocale(){
		return Arrays.asList(LOCALE_EN, LOCALE_VI);
	}

	@Bean
	public ResourceBundleMessageSource messageSourceBean() {
		return InternationalizationUtils.messageBundle();
	}
}
