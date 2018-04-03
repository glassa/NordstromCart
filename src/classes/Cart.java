package classes;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Starts by reading over a csv, counting how many lines there are in it. Each line represents an item in the cart.
 * Generates an SKU array of appropriate size, and populates it by reading in data from a csv, and calling the SKU constructor for each line.
 * Once the SKU's are read in, promotion types and Start/End dates are checked for each SKU in the array, and if a promotion is valid,
 * the discount is applied.
 * The price after all discounts have been applied is printed to console.
 * @author Alex
 *
 */
public class Cart {
	/**
	 * Main driver method for the program. Finds the size of the cart, generates an SKU array of appropriate size, populates it from a csv,
	 * then applies any valid discounts.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{

		
		/**
		 * The csv file to be read. CartContents.csv is a test file, with dummy data filled in.
		 */
		String csvFile = "CartContents.csv";

		/**
		 * A counter to determine the size SKUArray should be initialized to.
		 * Calls findCartSize to find the number of items in the cart.
		 */
		int cartSize = findCartSize(csvFile);

		/**
		 * The array that will contain data about each item in the cart, as an array of SKU's.
		 */
		SKU[] SKUArray = new SKU[cartSize];

		populateCart(SKUArray, csvFile);

		applyDiscounts(SKUArray);
	}
	/**
	 * Populates SKUArray by reference with the items in the csv.
	 * @param SKUArray The array that could contain all the SKUs
	 * @param csvFile The location of the csv
	 * @throws Exception Throws filenotfound exceptions if a bad csvFile was provided.
	 */
	public static void populateCart(SKU[] SKUArray, String csvFile) throws Exception{

		/**
		 * The actual reader that will be used to populate the SKUArray with SKU's. 
		 * Made seperate reader to insure that no cross-pollination from cartSizeReader can occur.
		 */
		BufferedReader br = null;

		/**
		 * String that holds the current line of the csv. Eventually, split on ','
		 */
		String line = "";

		/**
		 * When reading in the csv, ',' is the character each cell is seperated by.
		 */
		String cvsSplitBy = ",";


		try {
			/**
			 * Generates a buffered reader called br, and reads in the csv file. 
			 */
			br = new BufferedReader(new FileReader(csvFile));
			/**
			 * currentPos is the tracking variable denoting where in the SKUArray we are.
			 */
			int currentPos = 0;
			while ((line = br.readLine()) != null) {
				/**
				 * splits the input line into a string array. Each cell of the array is a field used by the SKU constructor.
				 */
				String[] input = line.split(cvsSplitBy);
				System.out.println(Arrays.toString(input));
				int tempId = Integer.parseInt(input[0]);
				double tempPrice = Double.parseDouble(input[4]);
				if (!input[6].equalsIgnoreCase("None") && !input[7].equalsIgnoreCase("None")){
					Date start = new SimpleDateFormat("dd/MM/yyyy").parse(input[6]);
					Date end = new SimpleDateFormat("dd/MM/yyyy").parse(input[7]);
					SKUArray[currentPos] = new SKU(tempId, input[1], input[2], input[3], tempPrice, input[5], start, end);
					currentPos++;
				}else if(input[6].equalsIgnoreCase("None") && input[7].equalsIgnoreCase("None")) {
					SKUArray[currentPos] = new SKU(tempId, input[1], input[2], input[3], tempPrice, input[5], "none", "none");
					currentPos++;
				}

			}


		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(ParseException f){
			f.printStackTrace();
		}
	}
	/**
	 * Go's over the SKUArray and checks the price of each item, totalling them up. If an item's price has been added to the 
	 * @param SKUArray
	 */
	public static void applyDiscounts(SKU[] SKUArray){
		/**
		 * When looking at any given cell in the csv, promotion types are seperated by 'x', allowing for promotion type and amount to be in one cell.
		 */
		String typeSplitBy = "x";

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date currentDate = new Date();
			/**
			 * The total price of the cart.
			 */
			double total = 0;
			/**
			 * The total amount of rebate a customer has accrued.
			 */
			double rebateTotal = 0;
			
			/**
			 * A boolean flag used to see if every item in the cart has been totalled.
			 */
			boolean newSale = true;
			
			/**
			 * A boolean flag used to see if a BOGO type promotion is active
			 */
			boolean bogo = false;		
			
			/**
			 * An array of booleans denoting if a given correspondingly indexed item in SKUArray has been totaled.
			 */
			boolean[] appliedSale = new boolean[SKUArray.length];	
			
			/**
			 * Loop over the cart until newSale is not set to true. Every time an item has it's price added to total, newSale is set to true.
			 */
			while (newSale){
				newSale = false;
				/**
				 * The id of the current bogo deal. Used to make sure bogo deals are not used up on different id'd items.
				 */
				int bogoId = 0;
				
				/**
				 * The X in buy X get Y Z% off
				 */
				int bogoX = 0;
				
				/**
				 * The Y in buy X get Y Z% off
				 */
				int bogoY = 0;
				/**
				 * Break label used to escape for loop when a bogo sale is used up.
				 */
				DiscountStart:
					/**
					 * Loops over all of SKUArray
					 */
					for (int i = 0; i < SKUArray.length; i++){
						
						/**
						 * The price of SKUArray[i]
						 */
						double price;
						price = SKUArray[i].getSKUPrice();
						/**
						 * Stops items that have already been talied from being double counted.
						 */
						if(!appliedSale[i]){
							
							/**
							 * Checks that the promotion date is valid. Note that "none" promotion types still require valid dates to be applied.
							 */
							if(SKUArray[i].getSKUPromotionStart().compareTo(currentDate)<= 0 && SKUArray[i].getSKUPromotionEnd().compareTo(currentDate)>0){
								/**
								 * The promotion type of SKUArray[i]
								 */
								String type = new String(SKUArray[i].getSKUPromotionType());
								
								/**
								 * Breaks the promotion type of SKUArray into it's component parts. I.E. type and magnitude
								 */
								String[] typeData = type.split(typeSplitBy);
								
								/**
								 * If the sale is % discount, reduce the price by the given percent.
								 */
								if (typeData[0].equalsIgnoreCase("%")) {	
									appliedSale[i] = true;
									newSale = true;
									double discount = price*(Double.parseDouble(typeData[1])*.01);
									System.out.printf("$%,.2f after %s%% discount.%n", price-discount, typeData[1]);
									total += price-discount;
									
								/**
								 * If the sale is flat rebate, do not change price, but add rebate to rebateTotal.
								 */
								} else if (typeData[0].equalsIgnoreCase("r")){
									appliedSale[i] = true;
									newSale = true;
									System.out.printf("$%,.2f $%,.2f Flat Rebate.%n", price, Double.parseDouble(typeData[1]));
									total += price;
									rebateTotal += Double.parseDouble(typeData[1]);
								
								/**
								 * If the sale is BXGYZ% off..
								 */
								} else if (typeData[0].equalsIgnoreCase("b")){
									
									/**
									 * If a BXGYZ% deal is not already active..
									 * set bogo to true, and set X and Y to the corrisponding values. 
									 * Record the id of the current Item
									 * Increase the total by the item's base price
									 */
									if(!bogo){
										bogo = true;
										bogoX = Integer.parseInt(typeData[1])-1;
										bogoY = Integer.parseInt(typeData[2]);
										bogoId = SKUArray[i].getSKUID();
										total += price;
										appliedSale[i] = true;
										newSale = true;
										if (bogoX != 0){
											System.out.printf("$%,.2f Buy %s Get %s %s%% off sale detected. Buy %d more items to start.%n", 
													price, typeData[1], typeData[2], typeData[3], bogoX);
										} else {
											System.out.printf("$%,.2f Buy %s Get %s %s%% off sale started.%n", 
													price, typeData[1], typeData[2], typeData[3]);
										}
									/**
									 * If a BXGYZ% sale is active, and SKUArray is the correct item, but the X condition has not been met..
									 * Decrement bogoX, and tally the price.
									 */
									} else if(bogo && bogoX > 0 && SKUArray[i].getSKUID() == bogoId){
										bogoX--;
										total += price;
										appliedSale[i] = true;
										newSale = true;
										if (bogoX != 0){
											System.out.printf("$%,.2f Buy %s Get %s %s%% off sale detected. Buy %d more items to start.%n", 
													price, typeData[1], typeData[2], typeData[3], bogoX);
										} else {
											System.out.printf("$%,.2f Buy %s Get %s %s%% off sale started.%n", 
													price, typeData[1], typeData[2], typeData[3]);
										}
									/**
									 * If a BXGYZ% sale is active, and SKUArray is the correct item, and the X condition has been met..
									 * Decrement bogoY, and apply the discount, and tally the price.
									 */
									} else if(bogo && bogoX == 0 && SKUArray[i].getSKUID() == bogoId){

										if (bogoY > 0){
											bogoY--;
											double discount;
											discount = price * (Double.parseDouble(typeData[3])*.01);
											total += price - discount;
											appliedSale[i] = true;
											newSale = true;
											/**
											 * If this most recent item has decreased bogoY to 0, set bogo to false, and break out of the if loop.
											 */
											if(bogoY == 0){
												bogo = false;
												System.out.printf("$%,.2f Buy %s Get %s %s%% off sale applied. Deal has been used up.%n", (price-discount), typeData[1], typeData[2], typeData[3]);
												break DiscountStart;
											} else {
												System.out.printf("$%,.2f Buy %s Get %s %s%% off sale applied. Deal will last %d more items.%n", (price-discount), typeData[1], typeData[2], typeData[3], bogoY);
											}
										}
									}
								/**
								 * If no promotion is applied, just increase the total price of the cart by the item's price.
								 */
								} else if (typeData[0].equalsIgnoreCase("n")) {
									appliedSale[i] = true;
									newSale = true;
									System.out.printf("$%,.2f No sale applied%n", price);
									total += price;
								}
							/**
							 * If the sale dates are not valid, do not apply any discount, and inform the customer.
							 */
							} else {
								System.out.printf("$%,.2f Promotion only valid from ", price);
								System.out.println(formatter.format(SKUArray[i].getSKUPromotionStart())+ " until " + formatter.format(SKUArray[i].getSKUPromotionEnd()));
								appliedSale[i] = true;
								newSale = true;
								total += price;
							}
						}
					}
			}
			/**
			 * Output the total cost of the cart after discounts, and if rebates are available, print that value too.
			 */
			System.out.printf("Total Cart Price after discounts: $%,.2f%n", total);
			if (rebateTotal > 0){
				System.out.println("$"+ rebateTotal + " of rebates available");
			}
		} catch(Exception g){
			g.printStackTrace();
		}

	}

	/**
	 * Finds and returns the size of the cart by going over the csv line by line. Each line corresponds to one item.
	 * @param csvFile The file destination where the csv is saved.
	 * @return cartSize An integer count of how many items are in the cart.
	 */
	public static int findCartSize(String csvFile){
		int cartSize = 0;
		try {

			/**
			 * A bufferedReader used to find the size of the cart
			 */
			BufferedReader cartSizeReader = new BufferedReader(new FileReader(csvFile));

			/**
			 * Never actually holds any data we use, just cartSizeReader's version of line.
			 */
			String sizeLine = "";

			while ((sizeLine = cartSizeReader.readLine()) != null) {
				cartSize++;
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		return cartSize;
	}
}
