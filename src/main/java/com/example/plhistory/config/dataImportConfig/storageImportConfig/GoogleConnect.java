package com.example.plhistory.config.dataImportConfig.storageImportConfig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Base64;

public class GoogleConnect {

    public byte[] searchImage(String searchString){

        int indexOfTheFirstImageFromTheSearch = 0;
        int marginToTheBSFCode = 1;
        int morePagesThanExpected = 2;
        int waitToRenderPageWithSearch = 150;
        int waitToRenderPageWithImages = 500;

        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com/imghp");
        driver.findElement(By.id("L2AGLb")).click();
        try {
            Thread.sleep(waitToRenderPageWithSearch);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(searchString);
        searchBox.sendKeys(Keys.RETURN);

        try {
            Thread.sleep(waitToRenderPageWithImages);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (driver.getWindowHandles().size() == morePagesThanExpected){
            driver.findElement(By.id("TYtByb")).click();
        }

        String html = driver.getPageSource();
        Document doc = Jsoup.parse(html);
        Elements imagesString = doc.select("div#islrg");

        if(imagesString.isEmpty()){
            imagesString = doc.select("div#rcnt");
        }

        Elements images = imagesString.select("img");

        String image = images.get(indexOfTheFirstImageFromTheSearch).attr("src");
        String BSFImage = image.substring(image.indexOf(",") + marginToTheBSFCode);

        driver.quit();

        return Base64.getDecoder().decode(BSFImage);
    }
}
