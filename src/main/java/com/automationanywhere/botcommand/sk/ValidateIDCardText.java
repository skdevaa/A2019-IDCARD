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
@CommandPkg(label = "Validate ID Text", name = "idcardvalidatetext",
        description = "Validate ID Text",
        node_label = "Validae ID Text", icon = "", return_type=DataType.BOOLEAN,   return_label="Valid", return_required=true)
public class ValidateIDCardText {

		@Execute
         public BooleanValue action(@Idx(index = "1", type = AttributeType.TEXT) @Pkg(label = "ID Text", default_value_type = DataType.STRING) @NotEmpty String text) throws Exception
         {    
        	 boolean valid = false ;
        	 String prefix = null;
    		
    		text = text.replaceAll("[^A-Za-z0-9<]","");
    		try {
    			prefix = IDCardUtils.prefixCheck(text);
    			if (prefix != null) {
    					switch (prefix) {
    					case "IDD<<":
    						valid = IDCardUtils.checkIDD(text,prefix);
    							break;
    							
    					case "IDCHE":
    						valid = IDCardUtils.checkIDCHE(text,prefix);
    							break;

    						default:
    							valid = IDCardUtils.checkP(text, prefix);
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
	
