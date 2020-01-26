/*
 * Copyright (c) 2019 Automation Anywhere.
 * All rights reserved.
 *
 * This software is the proprietary information of Automation Anywhere.
 * You shall use it only in accordance with the terms of the license agreement
 * you entered into with Automation Anywhere.
 */
/**
 * 
 */
package com.automationanywhere.botcommand.sk;


import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.automationanywhere.botcommand.data.Value;
import com.automationanywhere.botcommand.data.impl.DictionaryValue;
import com.automationanywhere.commandsdk.annotations.BotCommand;
import com.automationanywhere.commandsdk.annotations.CommandPkg;

import com.automationanywhere.commandsdk.annotations.Idx;
import com.automationanywhere.commandsdk.annotations.Pkg;
import com.automationanywhere.commandsdk.annotations.rules.NotEmpty;
import com.automationanywhere.commandsdk.model.AttributeType;
import com.automationanywhere.commandsdk.model.DataType;

import com.automationanywhere.commandsdk.annotations.Execute;

/**
 * @author Stefan Karsten
 *
 */

@BotCommand
@CommandPkg(label = "ID Details File", name = "idcardfile",
        description = "ID Details File",
        node_label = "ID Details File", icon = "", return_type=DataType.DICTIONARY ,  return_sub_type= DataType.STRING, return_label="ID Details", return_required=true)
public class GetIDCardFile{


	 	@Execute
		 public DictionaryValue action(@Idx(index = "1", type = AttributeType.FILE) @Pkg(label = "ID Text File", default_value_type = DataType.FILE) @NotEmpty String textfile) throws Exception
         {    
        	
	 		HashMap<String,Value>  dictID = new HashMap<String,Value>();
    		String fileContent = Files.readString(Path.of(textfile));
    		
    		fileContent = fileContent.replaceAll("[^A-Za-z0-9<]","");

    		String prefix = IDCardUtils.prefixCheck(fileContent);
    		  if (prefix != null) {
    			fileContent = IDCardUtils.splitbyPrefix(fileContent,prefix);
    			List<String>parsedString = new ArrayList<String>();
    			switch (prefix) {
						case "IDD<<": 
							if (IDCardUtils.parse(fileContent, parsedString,"D", IDCardUtils.IDCARD)) {
								dictID =  IDCardUtils.parseGermanID(parsedString);
							}
							break;
						
						case "IDCHE": 
							if (IDCardUtils.parse(fileContent, parsedString,"CH",IDCardUtils.IDCARD)) {
								dictID =  IDCardUtils.parseSwissID(parsedString);
							}
							break;
												
						default:
							if (IDCardUtils.parse(fileContent, parsedString,"",IDCardUtils.PASSPORT)) {
								dictID =  IDCardUtils.parsePassport(parsedString);
							}
					} 
    			
   
    		  }
    		  
    		  return new DictionaryValue(dictID);
         }
}
	
