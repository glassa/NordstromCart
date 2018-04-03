package classes;
/**
 * 
 */

import java.lang.Exception;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;

/**
 * @author Alexander Glass
 * A class to represent all the concepts related to an SKU. Has a constructor, and contains private classes for each field.
 */
public class SKU {
	
	/**
	 * The identifying code assigned to a given item. Used as a primary key in the (proposed) database
	 */
	private SKUId id;
	
	/**
	 * The "style" of the item. Examples would be "Shirt", "Pants", "Socks".
	 */
	private SKUStyle style;
	
	/**
	 * The size of the item. Sizes range from xxs to xxl, in both mens and womens
	 */
	private SKUSize size; 
	
	/**
	 * The color of the item. Allowed values are black, blue, cyan, darkGray, gray, green, lightgray, 
	 * magenta, orange, pink, red, white, and yellow
	 * SKUColor is an wrapper class that allows this.color to be set using strings.
	 */
	private SKUColor color;
	
	/**
	 * The USD price of the item.
	 */
	private SKUPrice price;
	
	/**
	 * The associated promotion of the item, including promotion type, start and end date.
	 */
	private SKUPromotion promotion;
	
	/**
	 * The constructor for the SKU when all fields are available.
	 * 
	 * @param inId The ID of the item.
	 * @param inColor The color of the item.
	 * @param inSize The size of the item.
	 * @param inStyle The style of the item.
	 * @param inPrice The price of the item.
	 * @param inType The type of the promotion.
	 * @param inStart The start date of the promotion.
	 * @param inEnd The end date of the promotion.
	 * @throws Exception BadConstructor "If for some reason the constructor fails, throws this exception to help with debug tracing.
	 */
	public SKU(int inId, String inColor, String inSize, String inStyle, double inPrice, String inType, Date inStart, Date inEnd) throws Exception{
		try{
			this.id = new SKUId(inId);
			this.color = new SKUColor(inColor);
			this.size = new SKUSize(inSize);
			this.style = new SKUStyle(inStyle);
			this.price = new SKUPrice(inPrice);
			this.promotion = new SKUPromotion(inType, inStart, inEnd);
		} catch(Exception BadConstructor){
			System.out.print("Caught in constructor");
			throw BadConstructor;
		}
	}
	
	/**
	 * The constructor for the SKU when there is no promotion.
	 * 
	 * @param inId The ID of the item.
	 * @param inColor The color of the item.
	 * @param inSize The size of the item.
	 * @param inStyle The style of the item.
	 * @param inPrice The price of the item.
	 * @param inType The type of the promotion.
	 * @param inStart The start date of the promotion. Set to 11/11/1000.
	 * @param inEnd The end date of the promotion. Set to 11/11/1000.
	 * @throws Exception BadConstructor "If for some reason the constructor fails, throws this exception to help with debug tracing.
	 */
	public SKU(int inId, String inColor, String inSize, String inStyle, double inPrice, String inType, String inStart, String inEnd) throws Exception{
		try{
			this.id = new SKUId(inId);
			this.color = new SKUColor(inColor);
			this.size = new SKUSize(inSize);
			this.style = new SKUStyle(inStyle);
			this.price = new SKUPrice(inPrice);
			Date start = new SimpleDateFormat("dd/MM/yyyy").parse("11/11/1000");
			Date end = new SimpleDateFormat("dd/MM/yyyy").parse("11/11/1000");
			this.promotion = new SKUPromotion(inType, start, end);
		} catch(Exception BadConstructor){
			System.out.print("Caught in constructor");
			throw BadConstructor;
		}
	}
	
	/**
	 * Gets the SKUID of this instance of SKU. Makes sure that the SKUID has been set, else throws an exception
	 * @return The value of this.SKUID, or throws an exception warning that the ID is still null.
	 */
	public int getSKUID() throws Exception{
		try{
			if(this.id.getId() != -1){
				return this.id.getId();
			}else {
				throw new Exception("ID not yet set");
			}
		} catch(Exception UnsetIdException){
			System.out.println("Caught in getSKUID");
			throw UnsetIdException;
		}
	}
	
	/**
	 * Sets the SKU to an input. Most likely called by the constructor.
	 * @param inSKUID The value to set SKUID to. Must be >= 1 and an int.
	 */
	public void setSKUId(int inSKUID) {
		this.id.setId(inSKUID);
	}
	
	/**
	 * Calls SKUColor.setColor to set color's value using a string input.
	 * @param inColor the input string that color should be set to.
	 * @throws Exception 
	 */
	public void setSKUColor(String inColor) throws Exception{
		color.setColor(inColor);
	}
	
	/**
	 * Gets a string denoting the color of the item.
	 * @return Returns the string representation of the color of the item.
	 */
	public String getSKUColor(){
		return this.color.getColor();
	}
	
	/**
	 * Calls SKUSize.setSize to set the size using a string input
	 * @param inSize the input string that the size should be set to.
	 * @throws Exception
	 */
	public void setSKUSize(String inSize) throws Exception{
		size.setSize(inSize);
	}
	
	/**
	 * Gets a string denoting the size of the item.
	 * @return Returns a string representation of the size of the item.
	 */
	public String getSKUSize(){
		return size.getSize();
	}
	
	/**
	 * Calls SKUStyle.setStyle to set the style using a string input.
	 * @param inStyle The input string that the style should be set to.
	 */
	public void setSKUStyle(String inStyle){
		this.style.setStyle(inStyle);
	}
	
	/**
	 * gets a string denoting the style of the item
	 * @return Returns a string representation of the style of the item.
	 */
	public String getSKUStyle(){
		return this.style.getStyle();
		
	/**
	 * Calls SKUPrice.setPrice to set the price using a double input.
	 * @param inPrice The input double that the price should be set to.	
	 */
	}
	public void setSKUPrice(double inPrice){
		this.price.setPrice(inPrice);
	}
	
	/**
	 * gets a double denoting the Price of the item
	 * @return Returns a double representation of the price of the item.
	 */
	public double getSKUPrice(){
		return this.price.getPrice();
	}
	
	/**
	 * Calls SKUPromotion.setStart to set the start date of a promotion
	 * @param inStart The date the promotion should be set to. Uses a Date object as the input type.
	 */
	public void setSKUPromotionStart(Date inStart){
		this.promotion.setStart(inStart);
	}
	
	/**
	 * Calls SKUPromotion.setEnd to set the end date of a promotion.
	 * @param inEnd The date the promotion should be set to. Uses a Date object as the input type.
	 */
	public void setSKUPromotionEnd(Date inEnd){
		this.promotion.setEnd(inEnd);
	}
	
	/**
	 * Calls SKUPromotion.setEnd to set the Type of a promotion.
	 * @param inType A String denoting the type of promotion. Valid promotion types are "n" for none, "bx
	 */
	public void setSKUPromotionType(String inType){
		this.promotion.setType(inType);
	}
	/**
	 * Gets a Date object that denotes the start of the promotion
	 * @return a Date object that denotes the start of the promotion
	 */
	public Date getSKUPromotionStart(){
		return this.promotion.getStart();
	}
	
	/**
	 * Gets a Date object that denotes the end of the promotion
	 * @return a Date object that denotes the end of the promotion
	 */
	public Date getSKUPromotionEnd(){
		return this.promotion.getEnd();
	}
	
	/**
	 * Gets a String that denotes the type of the promotion
	 * @return a String that denotes the type of promotion
	 */
	public String getSKUPromotionType(){
		return this.promotion.getType();
	}
	
	/**
	 * A wrapper class that contains getters, setters, and constructors for the SKUId.
	 * @author Alex
	 *
	 */
	private class SKUId {
		
		/**
		 * The integer that denotes the unique identifier for the item.
		 */
		private int id;
		
		/**
		 * The constructor for the SKUId.
		 * @param inId The integer value that the id should be set to.
		 */
		public SKUId(int inId){
			this.id = inId;
		}
		
		/**
		 * Gets the current value of the ID. Returns -1 if the id is invalid
		 * @return The integer value that ID is currently set to.
		 */
		public int getId(){
			//Checks that SKUID has been set
			if (this.id > 0){
				return id;
				//If SKUID is still defaulted to zero, returns -1 as a pseudo error code
			} else { 
				return -1;
			}
		}
		
		/**
		 * Sets the value of id to the given input.
		 * @param inId the integer value that id should be set to.
		 */
		public void setId(int inId){
			//Makes sure inSKUID is a valid SKUID
			if (inId >= 1)
				this.id = inId;
		}
	}
	/**
	 * A wrapper class that allows the java.awt.Color static value to be set with Strings, 
	 * and allows Nordstorm.SKU to get color values as Strings.
	 */
	private class SKUColor {

		/**
		 * The color of the item.
		 */
		private Color color;
		
		/**
		 * Constructor for SKUColor. Only allows inputs supported by the java.awt.color class.
		 * @param inColor String representation to be turned into a color object.
		 * @throws Exception Throws an exception if the color input is not one of the accepted types
		 */
		public SKUColor(String inColor) throws Exception{
			try {
				if(inColor.equalsIgnoreCase("black")){
					this.color = Color.black;
				} else if(inColor.equalsIgnoreCase("blue")){
					this.color = Color.blue; 
				}else if(inColor.equalsIgnoreCase("cyan")){
					this.color = Color.cyan;
				}else if(inColor.equalsIgnoreCase("darkGray")){
					this.color = Color.darkGray;
				}else if(inColor.equalsIgnoreCase("gray")){
					this.color = Color.gray;
				}else if(inColor.equalsIgnoreCase("green")){
					this.color = Color.green;
				}else if(inColor.equalsIgnoreCase("lightGray")){
					this.color = Color.lightGray;
				}else if(inColor.equalsIgnoreCase("magenta")){
					this.color = Color.magenta;
				}else if(inColor.equalsIgnoreCase("orange")){
					this.color = Color.orange;
				}else if(inColor.equalsIgnoreCase("pink")){
					this.color = Color.pink;
				}else if(inColor.equalsIgnoreCase("red")){
					this.color = Color.red;
				}else if(inColor.equalsIgnoreCase("white")){
					this.color = Color.white;
				}else if(inColor.equalsIgnoreCase("yellow")){
					this.color = Color.yellow;
				}else if(inColor.equalsIgnoreCase("lightGray")){
					this.color = Color.lightGray;
				}else {
					throw new Exception("Unsupported Color");
				}
			} catch(Exception UnsupportedColorException){
				System.out.println("Caught in ColorConstructor");
				throw UnsupportedColorException;
			}
		}
		
		/**
		 * calls java.awt.color.toString to return a string representation of the color
		 * @return Gets the string representation of the color.
		 */
		public String getColor(){
			return this.color.toString();
		}
		
		/**
		 * Sets the color of the item. Only allows inputs supported by the java.awt.color class.
		 * @param inColor String representation to be turned into a color object.
		 * @throws Exception Throws an exception if the color input is not one of the accepted types
		 */
		public void setColor(String inColor) throws Exception{
			try {
				if(inColor.equalsIgnoreCase("black")){
					this.color = Color.black;
				} else if(inColor.equalsIgnoreCase("blue")){
					this.color = Color.blue; 
				}else if(inColor.equalsIgnoreCase("cyan")){
					this.color = Color.cyan;
				}else if(inColor.equalsIgnoreCase("darkGray")){
					this.color = Color.darkGray;
				}else if(inColor.equalsIgnoreCase("gray")){
					this.color = Color.gray;
				}else if(inColor.equalsIgnoreCase("green")){
					this.color = Color.green;
				}else if(inColor.equalsIgnoreCase("lightGray")){
					this.color = Color.lightGray;
				}else if(inColor.equalsIgnoreCase("magenta")){
					this.color = Color.magenta;
				}else if(inColor.equalsIgnoreCase("orange")){
					this.color = Color.orange;
				}else if(inColor.equalsIgnoreCase("pink")){
					this.color = Color.pink;
				}else if(inColor.equalsIgnoreCase("red")){
					this.color = Color.red;
				}else if(inColor.equalsIgnoreCase("white")){
					this.color = Color.white;
				}else if(inColor.equalsIgnoreCase("yellow")){
					this.color = Color.yellow;
				}else if(inColor.equalsIgnoreCase("lightGray")){
					this.color = Color.lightGray;
				}else {
					throw new Exception("Unsupported Color");
				}
			} catch(Exception UnsupportedColorException){
				System.out.println("Caught in setColor");
				throw UnsupportedColorException;
			}
		}
	}
	
	/**
	 * Wrapper class that takes string inputs to output SKUSize's
	 * @author Alex
	 *
	 */
	private class SKUSize {
		
		/**
		 * The string representation of the size of the item.
		 */
		private String size;
		
		/**
		 * The constructor that takes a String input. Sizes accepted are XXS-XXL, preceded by either mens or womens.
		 * @param inSize The String representation of the size. Accepted sizes are XXS-XXL, preceded by either mens or womens.
		 * @throws Exception
		 */
		public SKUSize(String inSize) throws Exception{
			try {
				if (inSize.equalsIgnoreCase("womens XXS") || inSize.equalsIgnoreCase("womens XS") || inSize.equalsIgnoreCase("womens S")
						|| inSize.equalsIgnoreCase("womens M") || inSize.equalsIgnoreCase("womens L") || inSize.equalsIgnoreCase("womens XL")
						|| inSize.equalsIgnoreCase("womens XXL") || inSize.equalsIgnoreCase("mens XXS") || inSize.equalsIgnoreCase("mens XS") || inSize.equalsIgnoreCase("mens S")
						|| inSize.equalsIgnoreCase("mens M") || inSize.equalsIgnoreCase("mens L") || inSize.equalsIgnoreCase("mens XL")
						|| inSize.equalsIgnoreCase("mens XXL")){
					size = inSize;
				} else{
					throw new Exception ("Unsupported Size");
				}
			} catch(Exception UnsupportedSizeException) {
				System.out.println("Caught in setSize");
				throw UnsupportedSizeException;
			}
		}
		
		/**
		 * Returns a string denoting the size of the item.
		 * @return a string representation of the size.
		 */
		public String getSize(){
			return size;
		}
		
		/**
		 * Sets the size of the item using a string input of either "mens" or "womens" followed by a size XXS-XXL.
		 * @param inSize the size the item should be set to.
		 * @throws Exception Throws an exception if the give size is not supported.
		 */
		public void setSize(String inSize) throws Exception{
			try {
				if (inSize.equalsIgnoreCase("womens XXS") || inSize.equalsIgnoreCase("womens XS") || inSize.equalsIgnoreCase("womenss S")
						|| inSize.equalsIgnoreCase("womens M") || inSize.equalsIgnoreCase("womens L") || inSize.equalsIgnoreCase("womens XL")
						|| inSize.equalsIgnoreCase("womens XXL") || inSize.equalsIgnoreCase("mens XXS") || inSize.equalsIgnoreCase("mens XS") || inSize.equalsIgnoreCase("womenss S")
						|| inSize.equalsIgnoreCase("mens M") || inSize.equalsIgnoreCase("mens L") || inSize.equalsIgnoreCase("mens XL")
						|| inSize.equalsIgnoreCase("mens XXL")){
					size = inSize;
				} else{
					throw new Exception ("Unsupported Size");
				}
			} catch(Exception UnsupportedSizeException) {
				System.out.println("Caught in setSize");
				throw UnsupportedSizeException;
			}
		}
	}
	
	/**
	 * Wrapper class that takes a string input and converts it to an SKUStyle.
	 * @author Alex
	 *
	 */
	private class SKUStyle {
		/**
		 * The String that represents the style of the item. Think "Shirt", "pants", etc.
		 */
		private String style;
		
		/**
		 * The constructor for the SKUStyle, taking a string input for the style.
		 * @param inStyle the string input you want to set style to.
		 */
		public SKUStyle(String inStyle){
			style = inStyle;
		}
		
		/**
		 * Gets a string denoting the style of the item.
		 * @return Returns the current style of the item.
		 */
		public String getStyle(){
			return this.style;
		}
		
		/**
		 * Takes a string input to set style to.
		 * @param inStyle The string representation of the style of the item
		 */
		public void setStyle(String inStyle){
			style = inStyle;
		}
	}
	
	/**
	 * Wrapper class to make double inputs into SKUPrices
	 * @author Alex
	 *
	 */
	class SKUPrice {
		
		/**
		 * The price of the item
		 */
		private double price;
		
		/**
		 * Constructor takes a double input to make an SKUPrice object
		 * @param inPrice
		 */
		public SKUPrice(double inPrice){
			this.price = inPrice;
		}
		
		/**
		 * Returns the double representation of the price.
		 * @return A double representing the price of the item.
		 */
		public double getPrice(){
			return this.price;
		}
		
		/**
		 * Sets the price of the item with a double input.
		 * @param inPrice A double to set the price of the item to.
		 */
		public void setPrice(double inPrice){
			this.price = inPrice;
		}
	}
	
	/**
	 * A wrapper class that contains a promotion's start date, end date, and promotion.
	 * @author Alex
	 *
	 */
	private class SKUPromotion{
		
		/**
		 * The Start date of the promotion.
		 */
		private Date start;
		
		/**
		 * The end date of the promotion.
		 */
		private Date end;
		
		/**
		 * They type of the promotion.
		 */
		private String type;
		
		/**
		 * The Constructor of a promotion
		 * @param inType the Type of the promotion.
		 * @param inStart The date the promotion should have as a start date.
		 * @param inEnd The date the promotion should have as an end date.
		 */
		public SKUPromotion(String inType, Date inStart, Date inEnd){
			this.start = inStart;
			this.end = inEnd;
			this.type = inType;
		}
		
		/**
		 * Returns the start date of the promotion.
		 * @return The start date of the promotion.
		 */
		public Date getStart() {
			return this.start;
		}
		
		/**
		 * Sets the start date of the promotion.
		 * @param inStart The start date of the promotion
		 */
		public void setStart(Date inStart) {
			this.start = inStart;
		}
		
		/**
		 * Returns the end date of the promotion.
		 * @return The end date of the promotion.
		 */
		public Date getEnd() {
			return this.end;
		}
		
		/**
		 * Sets the end date of the promotion
		 * @param inEnd The date the promotion should end.
		 */
		public void setEnd(Date inEnd) {
			this.end = inEnd;
		}
		
		/**
		 * Gets the type of promotion of the item.
		 * @return A string representation of the type of promotion.
		 */
		public String getType() {
			return this.type;
		}
		
		/**
		 * Sets the type of promotion with a string input.
		 * @param inType The type of promotion the item should have.
		 */
		public void setType(String inType) {
			this.type = inType;
		}
	}
}


