package com.ibhh.animalshop.locales;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

/**
 * @author Jordan
 */
public enum Localizer
{

	BAYRISCH("Bayrisch", "de_BY"),
	AFRIKAANS("Afrikaans", "af_ZA"),
	ARABIC("العربية", "ar_SA"),
	BULGARIAN("Български", "bg_BG"),
	CATALAN("Català", "ca_ES"),
	CZECH("Čeština", "cs_CZ"),
	CYMRAEG("Cymraeg", "cy_GB"),
	DANISH("Dansk", "da_DK"),
	GERMAN("Deutsch", "de_DE"),
	GREEK("Ελληνικά", "el_GR"),
	ENGLISH("English", "en_CA"/* "en_GB" */),
	AUSTRALIAN_ENGLISH("Australian English", "en_CA"/* "en_AU" */),
	CANADIAN_ENGLISH("Canadian English", "en_CA"),
	PIRATE_SPEAK("Pirate Speak", "en_PT"),
	ESPERANTO("Esperanto", "eo_EO"),
	ARGENTINEAN_SPANISH("Español Argentino", "es_AR"),
	SPANISH("Español", "es_ES"),
	MEXICO_SPANISH("Español México", "es_MX"),
	URUGUAY_SPANISH("Español Uruguay", "es_UY"),
	VENEZUELA_SPANISH("Español Venezuela", "es_VE"),
	ESTONIAN("Eesti", "et_EE"),
	EUSKARA("Euskara", "eu_ES"),
	ENGLISH1("زبان انگلیسی", "en_CA"/* "fa_IR" */),
	FINNISH("Suomi", "fi_FI"),
	TAGALOG("Tagalog", "fil_PH"),
	FRENCH_CA("Français", "fr_CA"),
	FRENCH("Français", "fr_FR"),
	GAEILGE("Gaeilge", "ga_IE"),
	GALICIAN("Galego", "gl_ES"),
	HEBREW("עברית", "he_IL"),
	ENGLISH2("अंग्रेज़ी", "en_CA"/* "hi_IN" */),
	CROATIAN("Hrvatski", "hr_HR"),
	HUNGARIAN("Magyar", "hu_HU"),
	ARMENIAN("Հայերեն", "hy_AM"),
	BAHASA_INDONESIA("Bahasa Indonesia", "id_ID"),
	ICELANDIC("Íslenska", "is_IS"),
	ITALIAN("Italiano", "it_IT"),
	JAPANESE("日本語", "ja_JP"),
	GEORGIAN("ქართული", "ka_GE"),
	KOREAN("한국어", "ko_KR"),
	KERNEWEK("Kernewek", "kw_GB"),
	ENGLISH3("अंग्रेज़ी", "en_CA"/* "ky_KG" */),
	LINGUA_LATINA("Lingua latina", "la_LA"),
	LETZEBUERGESCH("Lëtzebuergesch", "lb_LU"),
	LITHUANIAN("Lietuvių", "lt_LT"),
	LATVIAN("Latviešu", "lv_LV"),
	MALAY("Bahasa Melayu", "mi_NZ"),
	MALTI("Malti", "mt_MT"),
	NORWEGIAN("Norsk", "nb_NO"),
	DUTCH("Nederlands", "nl_NL"),
	NORWEGIAN_NYNORSK("Norsk nynorsk", "nn_NO"),
	NORWEGIAN1("Norsk", "no_NO"),
	OCCITAN("Occitan", "oc_FR"),
	PORTUGUESE("Português", "pt_BR"),
	QUENYA("Quenya", "qya_AA"),
	ROMANIAN("Română", "ro_RO"),
	RUSSIAN("Русский", "ru_RU"),
	ENGLISH4("Angličtina", "en_CA"/* "sk_SK" */),
	SLOVENIAN("Slovenščina", "sl_SI"),
	SERBIAN("Српски", "sr_SP"),
	SWEDISH("Svenska", "sv_SE"),
	THAI("ภาษาไทย", "th_TH"),
	tlhIngan_Hol("tlhIngan Hol", "tlh_AA"),
	TURKISH("Türkçe", "tr_TR"),
	UKRAINIAN("Українська", "uk_UA"),
	VIETNAMESE("Tiếng Việt", "vi_VI"),
	CHINESE("简体中文", "zh_CN"),
	TRADITIONAL_CHINESE("繁體中文", "zh_TW"),
	POLISH("Polski", "pl_PL");

	private static final String[] spanishVariants = new String[] {"es_AR", "es_MX", "es_UY", "es_VE"};
	private static final String[] frenchVariants = new String[] {"fr_CA"};
	private static final String[] norwegianVariants = new String[] {"no_NO", "nn_NO"};
	private static final String[] malayVariants = new String[] {"ms_MY"};
	private static final String[] portugueseVariants = new String[] {"pt_PT"};
	private static final String[] chineseVariants = new String[] {"zh_TW"};

	private String name;
	private String code;

	private Localizer(String name, String code)
	{
		this.name = name;
		this.code = code;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public String getName()
	{
		return name;
	}
	
	public static Localizer getByCode(String code) {
		for (Localizer l : values()) {
			if (l.getCode().equalsIgnoreCase(code)) { return l; }
		}
		for (String s : spanishVariants) {
			if (s.equalsIgnoreCase(code)) { return Localizer.SPANISH; }
		}
		for (String s : frenchVariants) {
			if (s.equalsIgnoreCase(code)) { return Localizer.FRENCH; }
		}
		for (String s : norwegianVariants) {
			if (s.equalsIgnoreCase(code)) { return Localizer.NORWEGIAN; }
		}
		for (String s : malayVariants) {
			if (s.equalsIgnoreCase(code)) { return Localizer.MALAY; }
		}
		for (String s : portugueseVariants) {
			if (s.equalsIgnoreCase(code)) { return Localizer.PORTUGUESE; }
		}
		for (String s : chineseVariants) {
			if (s.equalsIgnoreCase(code)) { return Localizer.CHINESE; }
		}
		return Localizer.ENGLISH;
	}

	public static Localizer getLocalizer(Player playerFor) {

		try {
			Localizer code = getByCode(getLanguage(playerFor));
			return code;
		} catch (Exception exc) {
			exc.printStackTrace();
			return getByCode("en_CA");
		}
	}
	
	public static String getCodebyLanguage(String language) {
		for (Localizer l : values()) {
			if (l.getName().equalsIgnoreCase(language)) { return l.getCode(); }
		}
		return null;
	}

	public static String getLanguage(Player p) throws NoSuchFieldException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Object ep = getMethod("getHandle", p.getClass()).invoke(p, (Object[]) null);
		Field f = ep.getClass().getDeclaredField("locale");
		f.setAccessible(true);
		String language = (String) f.get(ep);
		return language;
	}

	private static Method getMethod(String name, Class<?> clazz)
	{
		for(Method m : clazz.getDeclaredMethods())
		{
			if(m.getName().equals(name))
				return m;
		}
		return null;
	}
}