package com.automationanywhere.botcommand.sk;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class testmain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		
	//	String file = "C:\\Users\\Stefan Karsten\\Documents\\AlleDateien\\Demos\\ID Card\\Pics\\NewID.txt";
	//	String fileContent = Files.readString(Path.of(file));
		
		String fileContent1 = "sa asaIDD<<T220001293<<<<<<<<<<<<<<<\r\n" + 
				"6408125<2010315D<<<<<<<<<<<<<4\r\n" + 
				"MUSTERMANN<<ERIKA<UTE<<<<<<<<<\r\n" ;
		
		
		
		
		String fileContent2 = "AFFAIRES ETRANGERfS\r\n" + 
				"P<USASPECIMEN<<EMMA<<<<<<<<<<<<<<<<<<<<<<<<<\r\n" + 
				"TC000000<9XXA8412265F1903278<<<<<<<<<<<<<<02";
		
		
		String fileContent3 = "		Schweiz •IDCHES0002068<8<<<<<<<<<<<<<<<\r\n" + 
				"8102287F1301014CHE<<<<<<<<<<<4\r\n" + 
				"VADIS<<QUO<MARIE<CLAUDE<<<<<<<<<<<<<<<<<<";
		
		
		String fileContent4 = "﻿SIGNATURE OF BEARER / SIGNATURE Dy.TITULAIRE / FIRMA DEL TITULAR\r\n" + 
				"340007237\r\n" + 
				"Sex/Sexe/Sexo\r\n" + 
				"P	USA\r\n" + 
				"Surname / Norn / Apellidos\r\n" + 
				"TRAVELER\r\n" + 
				"Given Names / PrSnoms / Nombre^adftuMt	. —. .\r\n" + 
				"HAPPY\r\n" + 
				"Nationality I Nat ionalite / Nacionalid^M||jra|j®y j!\r\n" + 
				"UNITED STATES OF AMERICA\r\n" + 
				"bafe'df birth / Date de naissance I Fecha de nacimiento\r\n" + 
				"04 JUL 1967\r\n" + 
				"Place of birth / Lieu de naissance/Lugar de nacimiento\r\n" + 
				"WASHINGTON. D.C., U.S.A.\r\n" + 
				"Date of issue / Date de d6livrance / Fecha de expedicidn\r\n" + 
				"08 AUG 2006\r\n" + 
				"Date of. expiration / Date d'expiration / Fecha de caducidad\r\n" + 
				"07 AUG 2016\r\n" + 
				"Endorsements / Mentions Sp6ciales / Anotaciones\r\n" + 
				"SEE PAGE 27\r\n" + 
				"P<USATRAVELER<<HAPPY<<<<<<<<<<<<<<<<<<<<<<<<\r\n" + 
				"3400072370USA6707046F1608078910000193<113538\r\n" + 
				"Authority / AutoritS / Autoridad.\r\n" + 
				"United States\r\n" + 
				"EP Exemplar Book";
		


		

		String fileContent = fileContent4;
		
		fileContent = fileContent.replaceAll("[^A-Za-z0-9<]","");

		
		String prefix = IDCardUtils.prefixCheck(fileContent);
		
		System.out.println("USA "+ IDCardUtils.checkP(fileContent , prefix));
		
		
		System.out.println("Content0 "+prefix);
		
		if (prefix != null) {
			
			
			System.out.println("Content2 "+prefix);
			fileContent = IDCardUtils.splitbyPrefix(fileContent,prefix);
			
			System.out.println("Content3 "+fileContent);
			
			List<String>parsedString = new ArrayList<String>();

			boolean valid = IDCardUtils.parse(fileContent, parsedString,"D",IDCardUtils.PASSPORT);
			//boolean valid = IDCardUtils.parse(fileContent, parsedString,"D",IDCardUtils.IDCARD);
			
			
			System.out.println(" Parse validation "+valid);

			for (int i = 0; i < parsedString.size(); i++) {
				System.out.println(i+ " "+parsedString.get(i));
			}
			
			valid = IDCardUtils.validateBlock0(parsedString.get(1));
			//valid = IDCardUtils.validateBlock0(parsedString.get(0));
			
			System.out.println( "Valid Block 0 "+valid);
			
			//valid = IDCardUtils.validateBlock1(parsedString.get(1));
			valid = IDCardUtils.validateBlock1(parsedString.get(3));
			
			System.out.println( "Valid Block 1 "+valid);
			
		//	valid = IDCardUtils.validateBlock1(parsedString.get(2));
			valid = IDCardUtils.validateBlock1(parsedString.get(5));
			System.out.println( "Valid Block 2 "+valid);
			
		//	valid = IDCardUtils.validateallBlocks(parsedString.get(0),parsedString.get(1),parsedString.get(2),null,	parsedString.get(4));
			valid = IDCardUtils.validateallBlocks(parsedString.get(1),parsedString.get(3),parsedString.get(5),parsedString.get(6),	parsedString.get(7));
			
			System.out.println( "Valid Block All "+valid);
			
			valid = IDCardUtils.validateCountry(parsedString.get(4));
			
			System.out.println( "Valid Country "+valid);
			
			String name = parsedString.get(0);

			valid = IDCardUtils.validatetName(name);
			

			System.out.println( "Valid Name "+valid);



			//System.out.println(IDCardUtils.checkIDCHE(fileContent, prefix));
			
		}
			

	}

}
