package com.automationanywhere.botcommand.sk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.StringValue;



public class IDCardUtils {
	
	
	final static String IDCARD = "ID";
	final static String PASSPORT = "P";

	private static byte[] checkWeights = new byte[]{7, 3, 1};
	

	
	
	
	public static String prefixCheck(String text) {
		
		String prefix = null;
		
		
		Pattern pattern = Pattern.compile("(P<(([A-Z]){1}<<|([A-Z]){3}|([A-Z]){2}<))");
		Matcher matcher = pattern.matcher(text);
	
		
		if (matcher.find()) {  prefix = text.substring(matcher.start(),matcher.end()); }
		if (text.contains("IDD<<")) { prefix = "IDD<<"; }
		if (text.contains("IDCHE")) {  prefix = "IDCHE"; }

		
		System.out.println("Content "+prefix);
		return prefix;

	}
	
	public static String splitbyPrefix(String text,String prefix) {
		
		String[] splitted = text.split(prefix);
		
		return splitted[1];

	}
	
	
	
	public static HashMap<String,Value> parseGermanID(List<String> parsedString) {
		
		HashMap<String,Value> dict= new HashMap<String,Value>();
		
		String key = "authority";
		Value value= new StringValue(parsedString.get(0).substring(0,4));
		dict.put(key,value);
		
		key = "id";
		value= new StringValue(parsedString.get(0).substring(4,9));
		dict.put(key,value);
		
		key = "birthdate";
		value= new StringValue(formatDate(parsedString.get(1).substring(0,6)));
		dict.put(key,value);
		
		key = "expirydate";
		value= new StringValue(formatDate(parsedString.get(2).substring(0,6)));
		dict.put(key,value);
		
		key = "country";
		value= new StringValue(parsedString.get(3));
		dict.put(key,value);
		
		key = "lastname";
		value= new StringValue(parsedString.get(5));
		dict.put(key,value);
		
		key = "firstname";
		value= new StringValue(parsedString.get(6));
		dict.put(key,value);

		
		return dict;
		
	}
	
	
	public static HashMap<String,Value> parsePassport(List<String> parsedString) {
		
		HashMap<String,Value> dict= new HashMap<String,Value>();
		
		String key = "name";
		Value value= new StringValue(parsedString.get(0));
		dict.put(key,value);
		
		key = "id";
		value= new StringValue(parsedString.get(1).substring(0,9));
		dict.put(key,value);
		
		key = "country";
		value= new StringValue(parsedString.get(2));
		dict.put(key,value);
		
		key = "birthdate";
		value= new StringValue(formatDate(parsedString.get(3).substring(0,6)));
		dict.put(key,value);
		
		key = "gender";
		value= new StringValue(parsedString.get(4));
		dict.put(key,value);
		
		key = "expirydate";
		value= new StringValue(formatDate(parsedString.get(5).substring(0,6)));
		dict.put(key,value);
		
		key = "additonal";
		value= new StringValue(parsedString.get(6));
		dict.put(key,value);

		
		return dict;
		
	}
	
	
	public static HashMap<String,Value> parseSwissID(List<String> parsedString) {
		
		HashMap<String,Value> dict= new HashMap<String,Value>();
		
		String key = "authority";
		Value value= new StringValue(parsedString.get(0).substring(0,4));
		dict.put(key,value);
		
		key = "id";
		value= new StringValue(parsedString.get(0).substring(4,9));
		dict.put(key,value);
		
		key = "birthdate";
		value= new StringValue(formatDate(parsedString.get(1).substring(0,6)));
		dict.put(key,value);
		
		key = "expirydate";
		value= new StringValue(formatDate(parsedString.get(3).substring(0,6)));
		dict.put(key,value);
		
		key = "gender";
		value= new StringValue(parsedString.get(2).substring(0,6));
		dict.put(key,value);
		
		key = "country";
		value= new StringValue(parsedString.get(4));
		dict.put(key,value);
		
		key = "lastname";
		value= new StringValue(parsedString.get(6));
		dict.put(key,value);
		
		key = "firstname";
		value= new StringValue(parsedString.get(7));
		dict.put(key,value);

		
		return dict;
		
	}
	
	
	
	
	
	
	public static boolean checkIDD(String text,String prefix) {
		
		boolean valid = false;
		text = IDCardUtils.splitbyPrefix(text,prefix);
		List<String>parsedString = new ArrayList<String>();
		// Parse
		if (IDCardUtils.parse(text, parsedString,"D",IDCARD)) {
			// validate Block 0
			if (IDCardUtils.validateBlock0(parsedString.get(0))) {
			// validate Block 1
				if (IDCardUtils.validateBlock1(parsedString.get(1))) {
					// validate Block 2
					if (IDCardUtils.validateBlock1(parsedString.get(2))) {
						// validate all Blocks
						if (IDCardUtils.validateallBlocks(parsedString.get(0),parsedString.get(1),parsedString.get(2),null,parsedString.get(4))) {
							// validate Country Letter
							if (IDCardUtils.validateCountry(parsedString.get(3))) {
								// validate Name;First Name
								if (IDCardUtils.validateLastFirstName(parsedString.get(5),parsedString.get(6))) {
									valid = true ;
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
	
	
	public static boolean checkP(String text,String prefix) {
		
		boolean valid = false;
		text = IDCardUtils.splitbyPrefix(text,prefix);
		List<String>parsedString = new ArrayList<String>();
		// Parse
		if (IDCardUtils.parse(text, parsedString,"",PASSPORT)) {
			// validate Block 0
			if (IDCardUtils.validateBlock0(parsedString.get(1))) {
			// validate Block 1
				if (IDCardUtils.validateBlock1(parsedString.get(3))) {
					// validate Block 2
					if (IDCardUtils.validateBlock1(parsedString.get(5))) {
						// validate all Blocks
						if (IDCardUtils.validateallBlocks(parsedString.get(1),parsedString.get(3),parsedString.get(5),parsedString.get(6),parsedString.get(7))) {
							// validate Country Letter
							if (IDCardUtils.validateCountry(parsedString.get(2))) {
								// validate Name;First Name
								if (IDCardUtils.validatetName(parsedString.get(0))) {
									valid = true ;
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
	
	
	public static boolean checkIDCHE(String text,String prefix) {
		
		boolean valid = false;
		text = IDCardUtils.splitbyPrefix(text,prefix);
		List<String>parsedString = new ArrayList<String>();
		// Parse
		if (IDCardUtils.parse(text, parsedString,"CH",IDCARD)) {
			// validate Block 0
			if (IDCardUtils.validateBlock0(parsedString.get(0))) {
			// validate Block 1
				if (IDCardUtils.validateBlock1(parsedString.get(1))) {
					// validate Block 2
					if (IDCardUtils.validateBlock1(parsedString.get(3))) {
						// validate all Blocks
						if (IDCardUtils.validateallBlocks(parsedString.get(0),parsedString.get(1),parsedString.get(3),null,parsedString.get(5))) {
							// validate Country Letter
							if (IDCardUtils.validateCountry(parsedString.get(4))) {
								// validate Name;First Name
								if (IDCardUtils.validateLastFirstName(parsedString.get(6),parsedString.get(7))) {
									valid = true ;
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
	
	
	
	public static  boolean parse(String text, List<String> parsedString,String country, String documentType) {
	 
		boolean valid = false;
		//parsedString = new ArrayList<String>();
		
		
		if (documentType.equals(IDCARD)) {
			switch (country) {
				case "D":
					valid = parseIDD(text, parsedString);
					break;
				case "CH":
					valid = parseIDCH(text, parsedString);
					break;
				
				default:
					valid = false;
					break;
			}
		}
			
		if (documentType.equals(PASSPORT)) {

			valid = parseP(text, parsedString);


		}
			
			
		
		
	return valid;
   

	}	
	
	public static boolean parseIDD(String text, List<String> parsedString) {
		 
		boolean valid = false;
		if (text.length()>85) text = text.substring(0,85);
		if (text.length()<85) return valid;
	
		String block0 = text.substring(0,10);
		if (isAlphaNumeric(removeFiller(block0))) {
			parsedString.add(0,fillerToSpace(block0));
			String block1 = text.substring(25,32);
			if (isNumeric(block1)) {
				parsedString.add(1,block1);
				String block2 = text.substring(33,40);
				if (isNumeric(block2)) {
					parsedString.add(2,block2);
					String country = text.substring(40,41);
					if (isAlpha(country)) {
						parsedString.add(3, country);
						String check = text.substring(54,55);
						if (isNumeric(check)) {
							parsedString.add(4, check);
							String line3[] = text.substring(55,84).split("<<");
							String lastname = fixZeroinAlpha(line3[0]);
							if (isAlpha(lastname)) {
								parsedString.add(5,lastname);
								String firstname = fixZeroinAlpha(removeFiller(line3[1]).trim());
								if (isAlpha(firstname)) {
									parsedString.add(6,fixZeroinAlpha(fillerToSpace((line3[1]).trim())));
									valid = true;
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
	
	
	private static  boolean parseIDCH(String text, List<String> parsedString) {
		 
		boolean valid = false;
		System.out.println("Content "+text);
		if (text.length()>85) text = text.substring(0,85);
		System.out.println("Length "+text.length());
		if (text.length()<85) return valid;
	
	
		String block0 = text.substring(0,10);
		if (isAlphaNumeric(removeFiller(block0))) {
			parsedString.add(0,fillerToSpace(block0));
			String block1 = text.substring(25,32);
			if (isNumeric(block1)) {
				parsedString.add(1,block1);
				String gender = text.substring(32,33);
				if (isAlpha(gender)) {
					parsedString.add(2,gender);
					String block2 = text.substring(33,40);
					if (isNumeric(block2)) {
						parsedString.add(3,block2);
						String country = text.substring(40,43);
						if (isAlpha(country)) {
							parsedString.add(4, country);
							String check = text.substring(54,55);
							if (isNumeric(check)) {
								parsedString.add(5, check);
								String line3[] = text.substring(55,84).split("<<");
								String lastname = fixZeroinAlpha(line3[0]);
								if (isAlpha(lastname)) {
									parsedString.add(6,lastname);
									String firstname = fixZeroinAlpha(removeFiller(line3[1]).trim());
									if (isAlpha(firstname)) {
										parsedString.add(7,fixZeroinAlpha(fillerToSpace((line3[1]).trim())));
										valid = true;
									}
								}	
							}
						}
					}	
				}
			}
		}
		return valid;
	}
	
	
	
	public static boolean parseP(String text, List<String> parsedString) {
		 
		boolean valid = false;
		if (text.length()>83) text = text.substring(0,83);
		if (text.length()<83) return valid;
	
	
		String block0 = fixZeroinAlpha(text.substring(0,39));
		if (isAlphaNumeric(removeFiller(block0))) {
			parsedString.add(0,fillerToSpace(block0));
			String block1 = text.substring(39,49);
			if (isAlphaNumeric(removeFiller(block1))) {
				parsedString.add(1,fillerToSpace(block1));
				String country = fixZeroinAlpha(text.substring(49,52));
				if (isAlpha(removeFiller(country))) {
					parsedString.add(2,removeFiller(country));
					String block2 = text.substring(52,59);
					if (isNumeric(block2)) {
						parsedString.add(3, block2);
						String gender = text.substring(59,60);
						if (isAlpha(gender)) {
							parsedString.add(4, gender);
							String block3 = text.substring(60,67);
							if (isNumeric(block3)) {
								parsedString.add(5, block3);
								String block4 = fillerToSpace(text.substring(67,82));
								parsedString.add(6, block4);
								String check =  text.substring(82,83);
								if (isNumeric(check)) {
									parsedString.add(7, check);
									valid = true;
								}
							}
						}
					}
				}
			}
		}
		return valid;
	}
	
	
	public static boolean validateCountry(String text) {
		boolean valid = false;
		if ((text.length() <= 3) && (text.length() >= 1))
		{
			if (Character.isLetter(text.charAt(0))) {
				valid = true;
			}
		}
		return valid;
	}
	
	public static boolean validateLastFirstName(String lastname, String firstname) {
		
		boolean valid = false;
		
		if(lastname.matches("[A-Z\\s]*")) {
			if(firstname.matches("[A-Z\\s]*")) {
			  valid = true;
			}
		}
		return valid;
	}
	
	public static boolean validatetName(String name) {
		
		boolean valid = false;
		
		if(name.matches("[A-Z\\s]*")) {
			  valid = true;
		}
		return valid;
	}
	
	
	
	public static boolean validateBlock0(String text)
	{
		boolean valid = false;
		if (text.length() == 10)
		{
			if (Character.isDigit(text.charAt(9))) {
				int[] block0 = getBlock(text.substring(0, 9));
				int check = Integer.parseInt(text.substring(9,10));
				if (calculate(block0, 0) == check) {
					valid = true;
				}
			}
		}

		return valid;
	}
	
	
	
	public static boolean validateBlock1(String text)
	{
		boolean valid = false;
		if (text.length() == 7)
		{
			if (Character.isDigit(text.charAt(6))) {
				int[] block1 = getBlock(text.substring(0, 6));
				int check = Integer.parseInt(text.substring(6,7));

				if (calculate(block1, 0) == check) {
					valid = true;
				}
			}
		}

		return valid;
	}
	
	public static boolean validateallBlocks(String block0, String block1, String block2, String block3, String checkAll)
	{
		boolean valid = false;
		
		if (Character.isDigit(checkAll.charAt(0))) {

			
			block3 = (block3 != null) ? fillerToSpace(block3) : "";
		
			String calcbase = block0.substring(0, 10)+block1.substring(0, 7)+block2.substring(0, 7)+block3;

			int sum = 0;
			for (int i = 0; i < calcbase.length(); i++) {
				sum = sum +  getNumber(calcbase.charAt(i))* checkWeights[i%3];
			}
			
			int check =  Character.getNumericValue( checkAll.charAt(0));

	

		    if ( (sum % 10) == check) {
		    	valid = true;
		    }
		}
		return valid;
	}
	
	

	
	 private static int calculate(int[] block, int blockno) {
	        int sum = 0;
	        for (int i = 0; i < block.length; i++)
	            sum += (block[i] * checkWeights[(blockno + i) % 3]);

	        return sum % 10;
	    }
	 
	 
	 private static int[] getBlock(String text) {
		 int[] block = new int[text.length()];
		 for (int i = 0; i < text.length(); i++) {
			 block[i] = getNumber(text.charAt(i));
		}
		 return block;
	 }
	 
	 private static int getNumber(char c) {
		 int value = 0;
		 c = Character.isWhitespace(c) ? '0' : c;
		 value = (Character.isLetter(c) ? (((int)c)-55) : Character.getNumericValue( c ));
		 return value;
	 }
	 
	 
	 private static boolean isAlphaNumeric(String text) {
		 return text.matches("[A-Z0-9]+");
	 }
	 
	 private static boolean isNumeric(String text) {
		 return text.matches("[0-9]+");
	 }
	 
	 
	 private static boolean isAlpha(String text) {
		 return text.matches("[A-Z]+");
	 }
	 
	 private static String removeFiller(String text) {
		 return text.replaceAll("<", "");
	 }
	 
	 private static String fillerToSpace(String text) {
		 return text.replaceAll("<", " ");
	 }
	 
	 private static String fixZeroinAlpha(String text) {
		 return text.replaceAll("0", "O");
	 }
	 
	 
	 private static String formatDate(String text) {
		 return (text.substring(4,6)+"."+text.substring(2,4)+"."+text.substring(0,2));
	 }
	 
	
	



}
