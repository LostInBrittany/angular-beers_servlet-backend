package org.lostinbrittany.beers.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Beer {


	/*
	 * Each beer was defined by
	 * 
	 * { "alcohol": 6.8, 
	 * "description": "Affligem Blonde, the classic clear blonde abbey ale, with a gentle roundness and 6.8% alcohol. Low on bitterness, it is eminently drinkable.",
	 * "id": "AffligemBlond", 
	 * "img": "img/AffligemBlond.jpg", 
	 * "name": "Affligem Blond",
	 * "availability": "Year round",
     * "brewery": "Brasserie Affligem (Heineken)", 
     * "serving": "Serve in a Snifter",
     * "style": "Belgian-Style Blonde Ale"}
	 */
	
	@Expose private String name;
	@Expose private String id;
	@Expose private String img;
	@Expose private String description;
	@Expose private double alcohol;

	private String availability;
	private String brewery;
	private String label;
	private String serving;
	private String style;


	public static final Gson gson = new Gson();
	
	public static final double MAX_ALCOHOL = 20.0;	
	public static final int MAX_NAME_LENGTH = 150;
	public static final int MAX_DESCRIPTION_LENGTH = 1500;
	public static final int MAX_PARAM_LENGTH = 300;
	
	
	private static String[] schemes = { "http", "https" };
	private static UrlValidator urlValidator = new UrlValidator(schemes);
	
	public static boolean isValidAlcoholParam(String param) {
		double val;
		try {
			val = Double.parseDouble(param);
		} catch (Exception ex) {
			return false;
		}
		if (val < 0 || val > MAX_ALCOHOL) {
			return false;
		}
		return true;
	}	
	
	public static boolean isValidIdParam(String param) {
		if ((null == param) || (param.length() > MAX_NAME_LENGTH)
				|| (param.length() == 0)) {
			return false;
		}
		if (param.indexOf(' ') >= 0) {
			return false;
		}
		return true;
	}

	public static boolean isValidNameParam(String param) {
		if ((null == param) || (param.length() > MAX_NAME_LENGTH)
				|| (param.length() == 0)) {
			return false;
		}
		return true;
	}	

	public static boolean isValidDescriptionParam(String param) {
		if ((null == param) || (param.length() > MAX_DESCRIPTION_LENGTH)
				|| (param.length() == 0)) {
			return false;
		}
		return true;
	}
	

	public static boolean isValidImgParam(String param) {
		if (StringUtils.isBlank(param)) {
			return false;
		}
		
		if ( !urlValidator.isValid(param)) {
			return false;
		}
		if (!param.endsWith(".jpg") && !param.endsWith(".png") && !param.endsWith(".gif")) {
			return false;
		}
		return true;
		
	}
	

	public static String generateValidGenericParam(String param) {
		if (StringUtils.isBlank(param)) {
			return "";
		}
		if (param.length() > MAX_PARAM_LENGTH) {
			return param.substring(0, 297)+"...";
		}
		return param;
	}

	public static String generateValidUrlParam(String param) {
		if (StringUtils.isBlank(param)) {
			return "";
		}
		if (!urlValidator.isValid(param)) {
			return "";
		}
		return param;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		this.id = StringUtils.capitalize(StringUtils.remove(StringUtils.stripAccents(name), " "));
	}
	public String getId() {
		return id;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAlcohol() {
		return alcohol;
	}
	public void setAlcohol(double alcohol) {
		this.alcohol = alcohol;
	}
	public String getAvailability() {
		return availability;
	}
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	public String getBrewery() {
		return brewery;
	}
	public void setBrewery(String brewery) {
		this.brewery = brewery;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getServing() {
		return serving;
	}
	public void setServing(String serving) {
		this.serving = serving;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}	
	
	
	public String toJSONDetail() {
		return gson.toJson(this);		
	}
	
	public static Beer fromJson(String json) {
		return gson.fromJson(json, Beer.class);
	}
	
	public String toJSON() {
		final GsonBuilder builder = new GsonBuilder();
	    builder.excludeFieldsWithoutExposeAnnotation();
	    final Gson gsonShort = builder.create();
		
		return gsonShort.toJson(this);
	}
}
