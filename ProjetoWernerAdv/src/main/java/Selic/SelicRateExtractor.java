package Selic;

import java.time.Duration;
import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SelicRateExtractor {
	
	private String taxaARecuperar = "SELIC";
	private WebDriver driver;
	private String defaultInitialDate = "09122021";
	
	public SelicRateExtractor() {
		this(true);
	}
	
	/**
	 * @param boolean headless: Indicates if SelicRateExtractor should run in headless mode
	 * 
	 * */
	public SelicRateExtractor(boolean headless) {
		ChromeOptions options = headless ? new ChromeOptions().addArguments("--headless") : new ChromeOptions();
		
		driver = new ChromeDriver(options);
	}
	
	/**
	 * @param finalDate: final date of interval, format DDMMYYYY
	 * 
	 * @return SELIC index in interval 09/12/2021 and finalDate according to Banco Central do Brasil
	 * */
	public String getTaxaSelicPeriod(String finalDate) {
		return getTaxaSelicPeriod(defaultInitialDate, finalDate);
	}
	
	/**
	 * @param initialDate: initial date of interval, format DDMMYYYY
	 * @param finalDate: final date of interval, format DDMMYYYY
	 * 
	 * @return Index of the given interval according to Banco Central do Brasil
	 * */
	public String getTaxaSelicPeriod(String initialDate, String finalDate) {
		try {
			driver.get(Rates.valueOf(taxaARecuperar).getURL());
		} catch (InvalidArgumentException e) {
			System.out.println("Couldn't open page " + Rates.valueOf(taxaARecuperar).getURL());
		}
		waitPageToLoad(10);
		
		findElementByNameAndEnterValue(Rates.valueOf(taxaARecuperar).getDataInicialFieldName(), initialDate);
	    findElementByNameAndEnterValue(Rates.valueOf(taxaARecuperar).getDataFinalFieldName(), finalDate);
	    findElementByNameAndEnterValue(Rates.valueOf(taxaARecuperar).getValorCorrecaoFieldName(), "10000");
		
	    WebElement submitButton = driver.findElement(By.cssSelector("[value='Corrigir valor']"));
        submitButton.click();
      
        waitPageToLoad(10);
        WebElement index = driver.findElement(By.xpath("/html/body/div[6]/table/tbody/tr/td/form/div[2]/table[1]/tbody/tr[6]/td[2]"));
        return index.getText();
	}
	
	/**
	 * @return SELIC index in interval 09/12/2021 and present day according to Banco Central do Brasil
	 * */
	public String getTaxaSelicDefaultPeriod() {
		LocalDate currentdate = LocalDate.now();
		return getTaxaSelicPeriod(
				String.valueOf(currentdate.getDayOfMonth()) + 
				String.valueOf(currentdate.getMonth().getValue()) +
				String.valueOf(currentdate.getYear()));
	}
	
 	private void findElementByNameAndEnterValue(String name, String dataInput) {
		WebElement dataInicioSerie = clickButton(name);
	    dataInicioSerie.sendKeys(dataInput);
	}
	
	private WebElement clickButton(String name) {
		WebElement element = driver.findElement(By.name(name));
		element.click();
		
	    return element;
	}
	
	private void waitPageToLoad(int duration) {
		new WebDriverWait(driver, Duration.ofSeconds(duration)).until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	}
	
}
