package com.ibrahim.mailsender.util;

import java.util.Random;

/**
 *
 * @author Ibrahim Chowdhury
 */
public class AppUtil {
    
    public static String generateMessage(String messagePattern) {
        String generatedMessage = "";

//        LOGGER.debug("Given message:-----" + messagePattern);
        if(messagePattern == null){
            return generatedMessage;
        }

//        messagePattern = "{hey|hello|hi}" ;
//        messagePattern = "I'm {omar|rafi}@@@{hey|hello|hi} {can you|are you able to|is it possible to|could you|would you} {give|provide} me your {number|quantity} {that's|that is} how {we can|we are able to|we could} {communicate|talk|connect|speak} {directly|straight|immediately|right}@@@{hey|hello|hi} {can you|are you able to|is it possible to|could you|would you} {give|provide} me your {number|quantity} {that's|that is} how {we can|we are able to|we could} {communicate|talk|connect|speak} {directly|straight|immediately|right}";
        String[] splitMessagePart = messagePattern.split("@@@");
        String selectedMessage = splitMessagePart[new Random().nextInt(splitMessagePart.length)];

//        LOGGER.debug(selectedMessage);
        String[] splitedSelectedMessage = null;

        splitedSelectedMessage = selectedMessage.trim().split(" ");
//        LOGGER.debug(splitedSelectedMessage.length);

        if (splitedSelectedMessage.length == 0) {
            return "";
        }
//        LOGGER.debug(splitedSelectedMessage.length);

        String multipleText = "";
        boolean isMultipletext = false;
        for (String words : splitedSelectedMessage) {
//            LOGGER.info("Words:" + words);

            if (words.contains("{")) {

                if (words.contains("}")) {
                    multipleText += words + " ";
                    isMultipletext = false;

                    String aa = multipleText.substring(multipleText.indexOf("{") + 1, multipleText.indexOf("}"));
//                    LOGGER.info(aa);
                    String[] chooseWord = aa.trim().replace("|", "@@").split("@@");

                    String choosenWord = chooseWord[new Random().nextInt(chooseWord.length)];

                    generatedMessage += choosenWord + " ";
//                    LOGGER.debug("Generate from first block" + choosenWord);

                    multipleText = "";
                } else {
                    multipleText += words + " ";
                    isMultipletext = true;
                }

            } else if (isMultipletext) {

                if (words.contains("}")) {
                    multipleText += words + " ";
                    isMultipletext = false;

                    String sameTypeText = multipleText.substring(multipleText.indexOf("{") + 1, multipleText.indexOf("}"));
//                    LOGGER.debug(sameTypeText);
                    String[] chooseWord = sameTypeText.trim().replace("|", "@@").split("@@");

                    String choosenWord = chooseWord[new Random().nextInt(chooseWord.length)];

                    generatedMessage += choosenWord + " ";
//                    LOGGER.debug("Generate from first block" + choosenWord);

                    multipleText = "";
                } else {
                    multipleText += words + " ";
                }
            } else {

                generatedMessage += words + " ";
            }

        }

        return generatedMessage;
    }
    
}
