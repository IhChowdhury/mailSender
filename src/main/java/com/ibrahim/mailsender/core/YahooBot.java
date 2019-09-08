package com.ibrahim.mailsender.core;

import com.ibrahim.mailsender.model.FromEmail;
import com.ibrahim.mailsender.model.ToEmail;
import com.ibrahim.mailsender.util.AppUtil;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Ibrahim Chowdhury
 */
public class YahooBot {

    public static FromEmail loginYahoo(ChromeDriver chromeDriver, FromEmail fromEmail) {
        try {
            System.out.println("start");
            WebElement username_form = chromeDriver.findElement(By.tagName("form"));

            username_form.findElement(By.xpath("//*[@id=\"login-username\"]")).sendKeys(fromEmail.getEmail());
            Thread.sleep(1000);

            username_form.findElement(By.cssSelector("input[id='login-signin']")).submit();

            String currentUrl = chromeDriver.getCurrentUrl();
            if (!currentUrl.contains("password")) {
                System.out.println("username not found");
                fromEmail.setLoginReport("username not found");
                return fromEmail;
            }
            Thread.sleep(2000);
            WebElement password_form = chromeDriver.findElement(By.tagName("form"));
            password_form.findElement(By.cssSelector("input[type='password']")).sendKeys(fromEmail.getPassword());
            Thread.sleep(500);
            password_form.findElement(By.cssSelector("button[id='login-signin']")).click();
            Thread.sleep(1000);

//            JavascriptExecutor jse = (JavascriptExecutor) chromeDriver;
//            jse.executeScript("document.getElementById('login-signin').click();");
            currentUrl = chromeDriver.getCurrentUrl();
            if (currentUrl.contains("password")) {
                System.out.println("Invalid Pasword.");
                fromEmail.setLoginReport("Invalid Pasword.");
            } else if (currentUrl.contains("challenge")) {
                System.out.println("currentUrl: " + currentUrl);
                System.out.println("Verification needed");
                fromEmail.setLoginReport("Verification needed");
            } else {

                if (currentUrl.contains("comm-channel")) {
                    chromeDriver.get("https://guce.yahoo.com/consent?gcrumb=DgQS5ZI&trapType=login&done=https%3A%2F%2Fmail.yahoo.com%2Fd&src=ym");
                    Thread.sleep(1000);
                }
                currentUrl = chromeDriver.getCurrentUrl();
                if (currentUrl.contains("consent.yahoo.com")) {
                    chromeDriver.findElement(By.className("agree")).submit();
                    Thread.sleep(1000);

                    chromeDriver.navigate().to("https://mail.yahoo.com/neo/b/compose");
                    Thread.sleep(1000);
                    chromeDriver.get("https://mail.yahoo.com/b/folders/1?.src=ym&reason=myc&action=closeOnboarding");


                }
                
                fromEmail.setSuccessfull(true);

//                sendEmail(chromeDriver, "skyroproject9@gmail.com", "Test", "This is testMessage");
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return fromEmail;
    }

    public static ToEmail sendEmail(ChromeDriver chromeDriver, ToEmail toEmail, String subject, String body) {

//            String receiverEmail = "";
//                if(receiverEmail.isEmpty()){
//                    receiverEmail = toEmail.getEmail();
//                }else{
//                    receiverEmail = receiverEmail +"," +toEmail.getEmail();
//                }
        try {
            if (toEmail.getStatus() == 0) {
                chromeDriver.get("https://mail.yahoo.com/b/compose?.src=ym&reason=myc");
                Thread.sleep(1000);
                chromeDriver.findElement(By.xpath("//*[@id='to']")).sendKeys(toEmail.getEmail());
                chromeDriver.findElement(By.xpath("//*[@id='subject']")).sendKeys(AppUtil.generateMessage(subject));
                chromeDriver.findElement(By.xpath("//*[@id='editorPlainText']")).sendKeys(AppUtil.generateMessage(body));
                Thread.sleep(200);
                chromeDriver.findElement(By.cssSelector("button[value='sendMessage']")).click();

                toEmail.setStatus(1);
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return toEmail;

    }

}
