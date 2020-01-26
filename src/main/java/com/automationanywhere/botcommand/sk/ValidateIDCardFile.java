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
import java.util.List;

import com.automationanywhere.botcommand.data.impl.BooleanValue;
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
@CommandPkg(label = "Validate ID File", name = "idcardvalidatefile",
        description = "Validate ID File",
        node_label = "Validae ID File", icon = "", return_type=DataType.BOOLEAN,   return_label="Valid", return_required=true)
public class ValidateIDCardFile {

		@Execute
         public BooleanValue action(@Idx(index = "1", type = AttributeType.FILE) @Pkg(label = "ID Text File", default_value_type = DataType.FILE) @NotEmpty String textfile) throws Exception
         {    
        	 boolean valid = false ;
        	 String prefix = null;
        	 
    		String fileContent = Files.readString(Path.of(textfile));
    		
    		fileContent = fileContent.replaceAll("[^A-Za-z0-9<]","");
    		try {
    			prefix = IDCardUtils.prefixCheck(fileContent);
    			if (prefix != null) {
    					switch (prefix) {
    					case "IDD<<":
    						valid = IDCardUtils.checkIDD(fileContent,prefix);
    							break;

    					case "IDCHE":
    						valid = IDCardUtils.checkIDCHE(fileContent,prefix);
    							break;
    							
						default:
							valid = IDCardUtils.checkP(fileContent, prefix);
							break;
    					}
    			}
    		}
    		catch (Exception e) {
    			valid = false;
    		}
    		
    		
    		return new BooleanValue(valid);
         }
		
		

		
}
	
