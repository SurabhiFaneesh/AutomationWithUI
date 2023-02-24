package com.avaya.product.fetcher.ws;

import com.avaya.product.fetcher.ws.modal.EngineerRecord;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import com.avaya.product.fetcher.ws.modal.SnowRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import utils.ExcelConvertionOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import utils.*;
import utils.ExcelOperation;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

public class PrepareSiebelReportService { 
	
	static Map<String, String> managersData = new TreeMap<>();
    static final Logger log = LogManager.getLogger(PrepareSiebelReportService.class);
    static PrepareSiebelReportService instance;
    Semaphore semaphore;
    int maxConcurrentSessions = utils.Config.getIntParam("report.max_concurrent_sessions");
    static CookieManager cm;
    String data;
    static ChromeDriver driver;
    static ChromeDriverService driverService;
    ExcelConvertionOperations excelConvert;
    ChromeOptions options = new ChromeOptions();
    public static String quarterYear;
    public static String quaterToFind;
    public static String downloadFolder;
    
	 private PrepareSiebelReportService() {
	        setProperties();
	        semaphore = new Semaphore(maxConcurrentSessions);
	        excelConvert = new ExcelConvertionOperations();
	        cm = new CookieManager(null, CookiePolicy.ACCEPT_ALL);
	        CookieHandler.setDefault(cm);
	        // PKIXAuthenticator.authenticate();
	        authenticateMe();
	        setDriverCapabilities();
	    }
	 
	 
	 public void setProperties() {
	        log.info("Setting properties");
	        utils.Config.setPropertyValue("access.year", quarterYear);
	        utils.Config.setPropertyValue("access.quarter", quaterToFind);
	        utils.Config.setPropertyValue("access.download_folder", downloadFolder);
	        setManagerInformation();
	        log.info("Download folder path : " + downloadFolder);
	    }

	    private void setDriverCapabilities() {
	        log.info("Setting driver capabilities");
//	        System.setProperty("webdriver.remote.quietExceptions", "true");
	        options.addArguments("--headless","--ignore-certificate-errors", "--enable-automation","--log-level=OFF", "--silent", "--disable-gpu", "--no-sandbox");
	        options.addArguments("download.default_directory=" + downloadFolder);
	        options.addArguments("--disable-features=VizDisplayCompositor");
	        options.addArguments("--disable-extensions");
	        options.addArguments("--disable-dev-shm-usage");
	        options.addArguments("disable-infobars"); 
	        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
	        Map<String, String> chromePref = new HashMap<String, String>();
	        log.info("Download default directory is {}",downloadFolder);
	        chromePref.put("download.default_directory", downloadFolder);
	        options.setExperimentalOption("prefs", chromePref);
	        options.setAcceptInsecureCerts(true);
	        WebDriverManager.chromedriver().setup();
	    }
	    public static PrepareSiebelReportService getInstance() {
	        if (instance == null) {
	            instance = new PrepareSiebelReportService();
	        }
	        return instance;
	    }

	    
	    public void setDriver() {
	        try{
	            log.info("Setting chrome driver");
	            driverService = ChromeDriverService.createDefaultService();
	            driver = new ChromeDriver(driverService, options);
	            Map<String, Object> commandParams = new HashMap<>();
	            commandParams.put("cmd", "Page.setDownloadBehavior");
	            Map<String, String> params = new HashMap<>();
	            params.put("behavior", "allow");
	            params.put("downloadPath", downloadFolder);
	            commandParams.put("params", params);
	            ObjectMapper objectMapper = new ObjectMapper();
	            HttpClient httpClient = HttpClientBuilder.create().build();
	            String command =  objectMapper.writeValueAsString(commandParams);
	            String u = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium/send_command";
	            HttpPost request = new HttpPost(u);
	            request.addHeader("content-type", "application/json");
	            request.setEntity(new StringEntity(command));
	            httpClient.execute(request);
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }
	    
	    synchronized public void authenticateMe() {
	        log.info("Start authentication");
	        java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
	    }

	    public void setManagerInformation() {
	        JSONObject json = new JSONObject(utils.Config.getPropertyValue("access.managers_list"));
	        Map<String, Object> map = json.toMap();
	        for (Map.Entry<String, Object> data : map.entrySet())
	            managersData.put(data.getKey(), data.getValue().toString());
	        log.info("Manager List=" + managersData.entrySet());
	    }

	    public List<String> getTeamHandle() {
	        List<String> teamHandles = new ArrayList<String>();
	        try {
	            JSONObject returnJsonObject = ElasticClient.getInstance()
	                    .GetElasticRecordsSQL(
	                            "" + "SELECT owner_hanle, count(*) from t_srs " + "where owned_by_cas='true'   "
	                                    + "group by owner_hanle " + "order by owner_hanle");

	            JSONArray objSearchOrdersDto = returnJsonObject.getJSONArray("rows");
	            for (int i = 0; i < objSearchOrdersDto.length(); ++i) {
	                JSONArray rec = objSearchOrdersDto.getJSONArray(i);
	                teamHandles.add(rec.getString(0));
	            }

	        } catch (Exception e) {
	            teamHandles = null;
	            return null;
	        }
	        return teamHandles;
	    }

	    public void authorizeMe(URL url) throws TimeoutException {
	        JavascriptExecutor js = null;
	        try {
	            final String username = utils.Config.getPropertyValue("access.username");
	            final String password = utils.Config.getPropertyValue("access.password");
	            log.info("Authenticating the url");
	            ((HasAuthentication) driver)
	                    .register(() -> new UsernameAndPassword(username, password));
	            log.info("Authenticated the url with provided credential");
	            js = (JavascriptExecutor) driver;
	            Thread.sleep(1000);
	            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(100));
	            log.info("Navigating to the url {}", url);
	             driver.get(url.toString());
	            Thread.sleep(1500);
	            log.info("Refreshing the page");
	            driver.navigate().refresh();
	            Thread.sleep(1500);
	        } 
//	            catch (InterruptedException e) {
//	        	log.error("Taking maximum time to load")
//	            driver.navigate().refresh();
//	            throw new RuntimeException(e);
//	        } 
	        catch (Exception e) {
	        	log.error("Taking maximum time to load the page");
	            driver.navigate().refresh();
	            ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
	            driver.manage().deleteAllCookies();
	            driver.switchTo().newWindow(WindowType.TAB);
	            driver.get(url.toString());
	            js.executeScript("return window.stop");
	            driver.close();
	            driver.switchTo().window(tabs2.get(0));
	        }

	    }

	    private Document getData(URL url) {
	        try {
	            authorizeMe(url);
	        } catch (TimeoutException  e) {
	            throw new RuntimeException(e);
	        }
	        return null;
	    }

	    public List<LocalDate> getFromToDate(String month, int year) {
	        List<LocalDate> fromTodate = new ArrayList<>();
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, year);
	        LocalDateTime nowDateTime = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
	        LocalDate previousMonth = nowDateTime.withMonth(Integer.parseInt(month)).toLocalDate();
	        fromTodate.add(previousMonth.withDayOfMonth(1));
	        fromTodate.add(previousMonth.with(lastDayOfMonth()));
	        return fromTodate;
	    }

	    public void downloadTheFile(String url, String labelId, String fileName) {
	    	log.info("Deleting file from dir {}",downloadFolder);
	        excelConvert.deleteFileIfExist(downloadFolder + fileName);
	        try {
	            log.info("Fetching data from the Url {}", url);
	            getData(new URL(url));
	            Thread.sleep(1000);
	            log.info("Downloading the file from the DOM path : //*[@id=\'" + labelId + "\']/a/img");
	            driver.findElement(By.xpath("//*[@id=\'" + labelId + "\']/a/img")).click();
	            Thread.sleep(3000);
	            log.info("successfully downloaded th file");
	        } catch (MalformedURLException | InterruptedException e) {
	            e.getMessage();
	        }
	    }

	    public Map<String, List<List<String>>> downloadExcel(String url, String dataToBeFetch, String labelId,
	            Map<String, List<List<String>>> engineerData, String fileName) {
	        excelConvert.deleteFileIfExist(downloadFolder + fileName);
	        engineerData.clear();
	        downloadTheFile(url, labelId, fileName);
	        while (!excelConvert.isFileExist(downloadFolder + fileName)) {
	            try {
	                Thread.sleep(5000);
	            } catch (InterruptedException e) {
	                e.getMessage();
	            }
	        }
	        if (excelConvert.isFileExist(downloadFolder + fileName)) {
	            engineerData = new ExcelConvertionOperations()
	                    .fileConvert(downloadFolder + fileName, dataToBeFetch);
	        }

	        return engineerData;
	    }

	    public Map<String, List<List<String>>> downloadExcel(String url, String dataToBeFetch, String labelId,
	            Map<String, List<List<String>>> engineerData) {
	        String fileName = "/report.xls";
	        excelConvert.deleteFileIfExist(downloadFolder + fileName);
	        engineerData.clear();
	        downloadTheFile(url, labelId);
	        while (!excelConvert.isFileExist(downloadFolder + fileName)) {
	            try {
	                Thread.sleep(5000);
	            } catch (InterruptedException e) {
	                e.getMessage();
	            }
	        }
	        if (excelConvert.isFileExist(downloadFolder + fileName)) {
	            engineerData = new ExcelConvertionOperations()
	                    .fileConvert(downloadFolder + fileName, dataToBeFetch);
	        }
	        log.info("fileName downloaded and the data is {}",engineerData);
	        return engineerData;
	    }

	    synchronized public void downloadTheFile(String url, String labelId) {
	        String fileName = "/report.xls";
	        excelConvert.deleteFileIfExist(downloadFolder + fileName);
	        try {
	            getData(new URL(url));
	            Thread.sleep(3000);
	            driver.findElement(By.xpath("//*[@id=\'" + labelId + "\']/a/img")).click();
	            Thread.sleep(3000);
	        } catch (MalformedURLException | InterruptedException e) {
	            e.getMessage();
	        }
	    }

	    synchronized public Map<Integer, Map<String, List<List<String>>>> getQuarterSRAverageRestorationTime(int startMonth,
	            int endMonth, int year) {
	        String fileName = "/report.xls";
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, year);
	        LocalDateTime nowDateTime = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
	        LocalDate fromMonth = nowDateTime.withMonth(startMonth).toLocalDate();
	        LocalDate toMonth = nowDateTime.withMonth(endMonth).toLocalDate();
	        LocalDate fromDateObj = fromMonth.withDayOfMonth(1);
	        LocalDate toDateObj = toMonth.with(lastDayOfMonth());
	        Map<Integer, Map<String, List<List<String>>>> engineerQuarterWiseData = new TreeMap<>();
	        try {
	        	setDriver() ;
	            for (Map.Entry<String, String> managers : managersData.entrySet()) {
	                String baseUrl = "https://report.avaya.com/siebelreports/casedurationdetail.aspx?dpGroup=All&dpDiagMeth=All&dpSeverityNew=0&tbSe=&lbSeLob=&lbProdFam=&lbProdSkill=&dpProdGroup=All&dpStatus=All&dpSubStatus=All&dpType=All&dpRegion=All&dpSubRegion=All&dpCountry=All&tbSRIDs=&tbFLs=&tbParents=&tbLHNs=&dpMiscExcld=0&dpDispPCARs=0&dpPrefLang=All&tbCustSeg=&FromDatePicker="+ fromDateObj.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
	                        "&ToDatePicker="
	                        + toDateObj.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
	                        +
	                        "&dpEmpRegion3=All&dpResolution=0&dpAgeBreak=0&dpBillable=0&cbInactive=True&cbTestFls=True&dpProductHouse=&dpServiceAction=All&dpSpecEvent=All&dpBp=0&tbBpLinkId=&tbMatrixId=&dpNetwork=&cbCoachHours=False&lbCapsule=All&lbSource=&lbSalesTheatre=&ddlChild=1&ddlAssetSubscription=0&tbProdName=&tbCoachId="
	                        + managers.getKey().toUpperCase()+"&col=cases";
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    throw new RuntimeException(e);
	                }
	                downloadTheFile(baseUrl, "lExcelLabel", fileName);
	                // store all engineer data for given month
	                while (!excelConvert.isFileExist(downloadFolder + fileName)) {
		                try {
		                    Thread.sleep(1000);
		                } catch (InterruptedException e) {
		                    e.getMessage();
		                }
		            }
	                Map<String, List<List<String>>> engineerMap = new TreeMap<>();
	                Map<Integer, Map<String, List<List<String>>>> currentData = new TreeMap<>();
	                currentData = excelConvert.readExcelFileWithMonth(downloadFolder + fileName);
	                log.info("Data fetched from the excel sheet is {}",currentData);
	                for (Map.Entry<Integer, Map<String, List<List<String>>>> engineerdata : currentData.entrySet()) {
	                    if (engineerQuarterWiseData.containsKey(engineerdata.getKey())) {
	                        engineerMap = engineerQuarterWiseData.get(engineerdata.getKey());
	                        engineerMap.putAll(engineerdata.getValue());
	                        engineerQuarterWiseData.put(engineerdata.getKey(), engineerMap);
	                    } else {
	                        engineerQuarterWiseData.put(engineerdata.getKey(), engineerdata.getValue());
	                    }
	                }
	                Thread.sleep(3000);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (!Objects.isNull(driver)) {
	                driver.close();
	            }

	        }
	        log.info("Engineer QuarterWise Data is {}",engineerQuarterWiseData);
	        return engineerQuarterWiseData;
	    }

	    // Siebel Restoration time
	    // https://report.avaya.com/siebelreports/casedurationdetail.aspx?dpGroup=All&dpDiagMeth=All&dpSeverityNew=0&tbSe=&lbSeLob=&lbProdFam=&lbProdSkill=&dpProdGroup=All&dpStatus=All&dpSubStatus=All&dpType=All&dpRegion=All&dpSubRegion=All&dpCountry=All&tbSRIDs=&tbFLs=&tbParents=&tbLHNs=&dpMiscExcld=0&dpDispPCARs=0&dpPrefLang=All&tbCustSeg=&FromDatePicker=04/01/2022&ToDatePicker=04/30/2022&dpEmpRegion3=All&dpResolution=3&dpAgeBreak=0&dpBillable=0&cbInactive=True&cbTestFls=True&dpProductHouse=&dpServiceAction=All&dpSpecEvent=All&dpBp=0&tbBpLinkId=&tbMatrixId=&dpNetwork=&cbCoachHours=False&lbCapsule=All&lbSource=&lbSalesTheatre=&ddlChildAlarms=1&ddlAssetSubscription=0&tbProdName=&tbCoachId=SIMAM
	    // Filter out with Engineer
	    // Rcpt to Rest(9 hr)
	    public Map<String, Float> getSRAverageRestorationTime(String month, int year,
	            Map<Integer, Map<String, List<List<String>>>> quarterSRAverageRestorationTimeMap) {
	    	log.info("Engineers Restoration data {}",quarterSRAverageRestorationTimeMap);
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, year);
	        LocalDateTime nowDateTime = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
	        LocalDate previousMonth = nowDateTime.withMonth(Integer.parseInt(month)).toLocalDate();
	        int currentMonth = previousMonth.getMonthValue();
	        Map<String, List<List<String>>> SRData = new HashMap<String, List<List<String>>>();
	        SRData = quarterSRAverageRestorationTimeMap.get(currentMonth);
	        Map<String, Float> engineerRestoreTime = new HashMap<>();
	        for (Map.Entry<String, List<List<String>>> entry : SRData.entrySet()) {
	            float restorationTime = 0;
	            for (List<String> time : entry.getValue()) {
	                // Rcpt to Rest(9 hr) = 8
	                // MEA = 78
	                restorationTime += Float.parseFloat(time.get(5));
	            }
	            float avgTime = (float) (restorationTime / entry.getValue().size());
	            engineerRestoreTime.put(entry.getKey(), avgTime);
	        }
	        return engineerRestoreTime;
	    }

	    public Map<String, Float> getSRAverageTime(String month, int year,
	            Map<Integer, Map<String, List<List<String>>>> quarterSRAverageRestorationTimeMap, int index) {
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, year);
	        LocalDateTime nowDateTime = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
	        LocalDate previousMonth = nowDateTime.withMonth(Integer.parseInt(month)).toLocalDate();
	        int currentMonth = previousMonth.getMonthValue();
	        Map<String, List<List<String>>> SRData = new HashMap<String, List<List<String>>>();
	        SRData = quarterSRAverageRestorationTimeMap.get(currentMonth);
	        Map<String, Float> engineerAvgTimeMap = new HashMap<>();
	        for (Map.Entry<String, List<List<String>>> entry : SRData.entrySet()) {
	            float engineerAvgTime = 0;
	            for (List<String> time : entry.getValue()) {
	                // Rcpt to Rest(9 hr) = 8
	                // MEA = 78
	                engineerAvgTime += Float.parseFloat(time.get(index));
	            }
	            float avgTime = (float) (engineerAvgTime / entry.getValue().size());
	            engineerAvgTimeMap.put(entry.getKey(), avgTime);
	        }
	        return engineerAvgTimeMap;
	    }

	    public Map<String, Float> getSRAverageResponseTime(String month, int year) {
//	        driver = new ChromeDriver(options);
	    	setDriver() ;
	        List<LocalDate> fromTodate = getFromToDate(month, year);
	        Map<String, List<List<String>>> SRData = new HashMap<String, List<List<String>>>();
	        Map<String, Float> engineerResponseTeam = new HashMap<>();
	        try {
	            for (Map.Entry<String, String> manager : managersData.entrySet()) {
	                SRData.clear();
	                String baseUrl = "https://report.avaya.com/SiebelReports/FirstActionDetail4.aspx?dpPosition=All&dpGroup=All&dpSeverityNew=0&tbSe=&dpSeLob=All&dpProdSkill=All&dpStatus=All&dpSubStatus=All&dpType=All&dpRegion=All&dpSubRegion=All&dpCountry=All&tbSRIDs=&tbFLs=&tbLHNs=&dpMiscExcld=0&dpDispPCARs=0&dpPrefLang=All&dpSource=All&dpCustSeg=All&FromDatePicker="
	                        + fromTodate.get(0).format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
	                        "&ToDatePicker=" + fromTodate.get(1).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
	                        + "&dpEmpRegion=All&dpCrt=0&dpAlarms=0&dpRespType=1&dpSvcHrs=0&cbTestFls=True&dpCapsule=All&cbInactive=True&tbCoachId="
	                        + manager.getKey().toUpperCase();
	                SRData = downloadExcel(baseUrl, "User", "lExcelLabel", SRData);
	                for (Map.Entry<String, List<List<String>>> entry : SRData.entrySet()) {
	                    float responseTime = 0;
	                    for (List<String> time : entry.getValue()) {
	                        // index of Duration (SLG) = 4
	                        responseTime += Float.parseFloat(time.get(4));
	                    }
	                    float avgTime = responseTime / entry.getValue().size();
	                    engineerResponseTeam.put(entry.getKey(), avgTime);
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Exception raised:-" + e.getMessage());
	        } finally {
	            driver.close();
	        }
	        return engineerResponseTeam;
	    }

	    // Total MEA
	    // url=https://report.avaya.com/SiebelReports/ActivityDurationDetail.aspx?dpPosition=All&dpGroup=CUSTOMER+APPLICATION+SUPPORT&dpSeverityNew=0&tbSe=&dpSeLob=All&dpProdSkill=All&dpProdGroup=All&dpStatus=All&dpType=All&dpRegion=All&dpSubRegion=All&dpCountry=All&tbSRIDs=&tbFLs=&tbLHNs=&FromDatePicker=05/01/2022&ToDatePicker=05/31/2022&dpEmpRegion=All&dpResolution=0&lbSource=&dpAgeBreak=0&cbInactive=True&cbTestFls=True&dpCapsule=All&dpActivityType2=Management+Escalation&dpMiscExcld=0&dpDispPcars=0&cbExcludeSameOwner=False&tbCoachId=&col=cases
	    public Map<String, Integer> getTotalMea(String month, int year) {
	        String fileName = "/report.xls";
//	        driver = new ChromeDriver(options);
	        setDriver() ;
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, year);
	        LocalDateTime nowDateTime = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
	        LocalDate previousMonth = nowDateTime.withMonth(Integer.parseInt(month)).toLocalDate();
	        LocalDate fromDateObj = previousMonth.withDayOfMonth(1);
	        LocalDate toDateObj = previousMonth.with(lastDayOfMonth());

	        Map<String, List<List<String>>> SRData = new HashMap<String, List<List<String>>>();
	        Map<String, Integer> engineerMEACount = new HashMap<>();
	        try {
	            String baseUrl = "https://report.avaya.com/SiebelReports/ActivityDurationDetail.aspx?dpPosition=All&dpGroup=CUSTOMER+APPLICATION+SUPPORT&dpSeverityNew=0&tbSe=&dpSeLob=All&dpProdSkill=All&dpProdGroup=All&dpStatus=All&dpType=All&dpRegion=All&dpSubRegion=All&dpCountry=All&tbSRIDs=&tbFLs=&tbLHNs=&FromDatePicker="
	                    + fromDateObj.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
	                    "&ToDatePicker="
	                    + toDateObj.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
	                    +
	                    "&dpEmpRegion=All&dpResolution=0&lbSource=&dpAgeBreak=0&cbInactive=True&cbTestFls=True&dpCapsule=All&dpActivityType2=Management+Escalation&dpMiscExcld=0&dpDispPcars=0&cbExcludeSameOwner=False&tbCoachId=&col=cases";
	            downloadTheFile(baseUrl, "lExcelLabel", fileName);
	            while (!excelConvert.isFileExist(downloadFolder + fileName)) {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.getMessage();
	                }
	            }
	            SRData = excelConvert.readTheExcelFile(downloadFolder + fileName, "SR Owner");
	            for (Map.Entry<String, List<List<String>>> data : SRData.entrySet()) {
	                engineerMEACount.put(data.getKey(), data.getValue().size());
	            }
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        } finally {
	            driver.close();
	        }
	        log.info("Engineer MEA data :: " + engineerMEACount);
	        return engineerMEACount;
	    }

	    // Utilization :
	    // https://report.avaya.com/crossplatform/utilizationdetails3.aspx?ddlMeasured=All&lbMeasuredMethod=All&lbExclusionCategory=All&FromDatePicker=06/01/2022&ToDatePicker=06/25/2022&cbIncludeNotMeasured=False&tbCoachId=HRUPARELIA&lbEmpRegion=All&ddlEmpCountry=All&lbEmpFunction=All&lbEmpContractingAgency=All&ddlViewBy=Direct+Report&col=avg_hc
	    // https://report.avaya.com/crossplatform/utilizationdetails3.aspx?ddlMeasured=All&lbMeasuredMethod=All&lbExclusionCategory=All&FromDatePicker=04/01/2022&ToDatePicker=04/30/2022&cbIncludeNotMeasured=False&tbCoachId=KULKARNIB&lbEmpRegion=All&ddlEmpCountry=All&lbEmpFunction=All&lbEmpContractingAgency=BETSOL+LLC&ddlViewBy=Direct+Report&col=avg_hc
	    public Map<String, Float> getUtilizationHours(String month, int year) {
	        log.info("fetching the utilization data for the month {} and year {}", month, year);
	        String fileName = "/report.xls";
//	        driver = new ChromeDriver(options);
	        setDriver() ;
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.YEAR, year);
	        LocalDateTime nowDateTime = LocalDateTime.ofInstant(cal.toInstant(), ZoneId.systemDefault());
	        LocalDate previousMonth = nowDateTime.withMonth(Integer.parseInt(month)).toLocalDate();
	        LocalDate fromDateObj = previousMonth.withDayOfMonth(1);
	        LocalDate toDateObj = previousMonth.with(lastDayOfMonth());
	        Map<String, List<List<String>>> SRData = new HashMap<String, List<List<String>>>();
	        Map<String, Float> engineerUtilizationHour = new TreeMap<>();
	        try {
	            for (Map.Entry<String, String> manager : managersData.entrySet()) {
	                SRData.clear();
	                String baseUrl = "https://report.avaya.com/crossplatform/utilizationdetails3.aspx?ddlMeasured=All&lbMeasuredMethod=All&lbExclusionCategory=All&FromDatePicker="
	                        + fromDateObj.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")) +
	                        "&ToDatePicker="
	                        + toDateObj.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
	                        + "&cbIncludeNotMeasured=False&tbCoachId=" + manager.getKey().toUpperCase()
	                        + "&lbEmpRegion=All&ddlEmpCountry=All&lbEmpFunction=All&lbEmpContractingAgency=All&ddlViewBy=Direct+Report&col=avg_hc";
	                
	                downloadTheFile(baseUrl, "lExcelLabel", fileName);
	                Thread.sleep(3000);
	                log.info("Is file existed {}",downloadFolder);
	                while (!(excelConvert.isFileExist(downloadFolder + fileName))) {
	                    try {
	                        Thread.sleep(1000);
	                    } catch (InterruptedException e) {
	                        e.getMessage();
	                    }
	                }
	                log.info("Is file existed {}",!(excelConvert.isFileExist(downloadFolder + fileName)));
	                log.info("Reading the Excel data");
	                SRData = excelConvert.readTheExcelFile(downloadFolder + fileName, "Login");
	                log.info("Data extracted from Excel sheet is {}",SRData);
	                // utilizationHour
	                // (Customer Hours*100)/AvailableHours
	                for (Map.Entry<String, List<List<String>>> entry : SRData.entrySet()) {
	                    float availableHour = 0;
	                    float customerHour = 0;
	                    for (List<String> time : entry.getValue()) {
	                        availableHour += time.get(45).length() == 0 ? 0 : Float.parseFloat(time.get(45));
	                        customerHour += time.get(47).length() == 0 ? 0 : Float.parseFloat(time.get(47));

	                    }
	                    float utilizationHour = customerHour == 0 ? 0 : (customerHour * 100) / availableHour;
	                    engineerUtilizationHour.put(entry.getKey(), utilizationHour);
	                }
	            }
	        } catch (Exception e) {
	            System.out.println("Exception Caught:-" + e.getMessage());
	        } finally {
	            driver.close();
	        }
	        log.info("Engineer Utilization data " + engineerUtilizationHour);
	        return engineerUtilizationHour;
	    }

	    public SnowRecord loadIncDetails(String inc_num) {
	        SnowRecord incInstance = null;
	        try {
	            JSONArray incJson = ElasticClient.getInstance().GetDocs("t_snow",
	                    "{\"query\": {\"ids\" : {\"type\" : \"_doc\",\"values\" : [\"" + inc_num + "\"]}}}");
	            if (incJson.length() == 0)
	                return null;
	            for (int i = 0; i < incJson.length(); i++) {
	                String incRecord = incJson.getJSONObject(i).getString("_id");
	                JSONObject data = incJson.getJSONObject(i).getJSONObject("_source");
	                incInstance = new SnowRecord(data);
	            }
	        } catch (Exception e) {
	            e.getMessage();
	        }
	        return incInstance;
	    }

	    public SRRecord loadSRDetails(String sr_num) {
	        SRRecord srInstance = null;
	        try {
	            JSONArray srJson = ElasticClient.getInstance().GetDocs("t_srs",
	                    "{\"query\": {\"ids\" : {\"type\" : \"_doc\",\"values\" : [\"" + sr_num + "\"]}}}");
	            if (srJson.length() == 0)
	                return null;
	            for (int i = 0; i < srJson.length(); i++) {
	                String srRecord = srJson.getJSONObject(i).getString("_id");
	                JSONObject data = srJson.getJSONObject(i).getJSONObject("_source");
	                srInstance = new SRRecord(data);
	            }
	        } catch (Exception e) {
	            e.getMessage();

	            return null;
	        }
	        return srInstance;
	    }

	    public EngineerRecord getAllSRsWorkedByEngineer(String engineerName, String financialYear, String quarter,
	            String month) {
	        // System.out.println("getAllSRsWorkedByEngineer; engineerName: " + engineerName
	        // + "; financialYear: "
	        // + financialYear + "; quarter: " + quarter + ", month: " + month);

	        List<SRRecord> listOfSRs = new ArrayList<>();
	        List<SnowRecord> listOfSnowItems = new ArrayList<>();

	        List<SRRecord> outageSRList = new ArrayList<>();

	        List<SRRecord> sbiSRList = new ArrayList<>();

	        List<SRRecord> biSRList = new ArrayList<>();

	        List<SRRecord> nsiSRList = new ArrayList<>();

	        List<SRRecord> handoffSRList = new ArrayList<>();

	        List<SRRecord> extraContributionSRList = new ArrayList<>();

	        List<SnowRecord> listSnowIncident = new ArrayList<>();
	        List<SnowRecord> listSnowChange = new ArrayList<>();
	        List<SnowRecord> listSnowIncidentTask = new ArrayList<>();
	        List<SnowRecord> listSnowChangeTask = new ArrayList<>();
	        List<SnowRecord> listSnowAlarms = new ArrayList<>();
	        List<SnowRecord> listSnowProjectTask = new ArrayList<>();
	        List<SnowRecord> listSnowProblemTask = new ArrayList<>();
	        List<SnowRecord> listSnowProblem = new ArrayList<>();
	        List<SnowRecord> listSnowCritical = new ArrayList<>();
	        List<SnowRecord> listSnowHigh = new ArrayList<>();
	        List<SnowRecord> listSnowModerate = new ArrayList<>();
	        List<SnowRecord> listSnowLow = new ArrayList<>();
	        List<SnowRecord> listSnowAlarmCritical = new ArrayList<>();
	        List<SnowRecord> listSnowAlarmHigh = new ArrayList<>();
	        List<SnowRecord> listSnowAlarmModerate = new ArrayList<>();
	        List<SnowRecord> listSnowAlarmLow = new ArrayList<>();

	        EngineerRecord engineerRecord = new EngineerRecord();
	        engineerRecord.setEngineerHandle(engineerName);

	        // Siebel Records
	        Map<String, EngineerRecord> engineerSRRecords = new HashMap<String, EngineerRecord>();
	        try {
	            String query = "SELECT owner_hanle, sr_no, severity from t_srs where "
	                    + "owned_by_cas='true' and owner_hanle='" + engineerName.toUpperCase() + "' and close_fiscal_year='"
	                    + financialYear + "' and close_quarter='" + quarter + "'" + " and close_month='" + month + "'";
	            JSONObject returnJsonObject = ElasticClient.getInstance().GetElasticRecordsSQL("" + query);
	            JSONArray objSearchOrdersDto = returnJsonObject.getJSONArray("rows");
	            if (!objSearchOrdersDto.isEmpty()) {
	                for (int i = 0; i < objSearchOrdersDto.length(); ++i) {
	                    JSONArray rec = objSearchOrdersDto.getJSONArray(i);
	                    SRRecord srRecordForEngineer = loadSRDetails(rec.getString(1));
	                    engineerRecord.setEngineerHandle(engineerName.toUpperCase());
	                    engineerRecord.setManagerHandle(srRecordForEngineer.getSupervisor_handle().toUpperCase());
	                    listOfSRs.add(srRecordForEngineer);
	                    if ("OUTG".equalsIgnoreCase(srRecordForEngineer.getOriginal_severity())) {
	                        outageSRList.add(srRecordForEngineer);
	                    } else if ("SBI".equalsIgnoreCase(srRecordForEngineer.getOriginal_severity())) {
	                        sbiSRList.add(srRecordForEngineer);
	                    } else if ("BI".equalsIgnoreCase(srRecordForEngineer.getOriginal_severity())) {
	                        biSRList.add(srRecordForEngineer);
	                    } else if ("NSI".equalsIgnoreCase(srRecordForEngineer.getOriginal_severity())) {
	                        nsiSRList.add(srRecordForEngineer);
	                    }
	                }
	                engineerRecord.setListOfSRs(listOfSRs);
	                engineerRecord.setBiSRList(biSRList);
	                engineerRecord.setSbiSRList(sbiSRList);
	                engineerRecord.setOutageSRList(outageSRList);
	                engineerRecord.setNsiSRList(nsiSRList);
	                // System.out.println(
	                // "Fetched SRs" + engineerRecord.getListOfSRs().size() + " for Engineer: " +
	                // engineerName);
	            } else {
	                System.err.println("[ERROR] No Siebel Records found for an engineer " + engineerName);
	            }
	        } catch (Exception e) {
	            System.err.println("[ERROR] Exception occurred during siebel records fetched " + e.getMessage());
	            e.getMessage();
	        }

	        // SNOW Records
	        try {
	            String query = "SELECT owner_hanle, ticket_no from t_snow where " +
	                    "owned_by_cas='true' and owner_hanle='"
	                    + engineerName.toLowerCase() + "' and close_fiscal_year='" + financialYear +
	                    "' and close_quarter='"
	                    + quarter
	                    + "'" + " and close_month='" + month + "'";
	            // System.out.println(query);
	            JSONObject returnJsonObject = ElasticClient.getInstance().GetElasticRecordsSQL("" + query);

	            JSONArray objSearchOrdersDto = returnJsonObject.getJSONArray("rows");
	            if (!objSearchOrdersDto.isEmpty()) {
	                for (int i = 0; i < objSearchOrdersDto.length(); i++) {
	                    JSONArray rec = objSearchOrdersDto.getJSONArray(i);
	                    SnowRecord incRecordForEngineer = loadIncDetails(rec.getString(1));
	                    engineerRecord.setEngineerHandle(incRecordForEngineer.getOwner_hanle().toUpperCase());
	                    engineerRecord.setManagerHandle(incRecordForEngineer.getSupervisor_handle().toUpperCase());
	                    listOfSnowItems.add(incRecordForEngineer);

	                    // Let's Seperate Alarms
	                    if ("Alarm".equalsIgnoreCase(incRecordForEngineer.getSubclass())) {
	                        if ("1 -Critical".equalsIgnoreCase(incRecordForEngineer.getOriginal_priority())) {
	                            listSnowCritical.add(incRecordForEngineer);
	                        } else if ("2 -High".equalsIgnoreCase(incRecordForEngineer.getOriginal_priority())) {
	                            listSnowHigh.add(incRecordForEngineer);
	                        } else if ("3 -Moderate".equalsIgnoreCase(incRecordForEngineer.getOriginal_priority())) {
	                            listSnowModerate.add(incRecordForEngineer);
	                        } else if ("4 -Low".equalsIgnoreCase(incRecordForEngineer.getOriginal_priority())) {
	                            listSnowLow.add(incRecordForEngineer);
	                        }
	                    } else if ("CHG".equalsIgnoreCase(incRecordForEngineer.getCas_subtype())) {
	                        listSnowChange.add(incRecordForEngineer);
	                    } else if ("CHG_TASK".equalsIgnoreCase(incRecordForEngineer.getCas_subtype())) {
	                        listSnowChangeTask.add(incRecordForEngineer);
	                    } else if ("PRJ_TASK".equalsIgnoreCase(incRecordForEngineer.getCas_subtype())) {
	                        listSnowProjectTask.add(incRecordForEngineer);
	                    } else if ("PRB".equalsIgnoreCase(incRecordForEngineer.getCas_subtype())) {
	                        listSnowProblem.add(incRecordForEngineer);
	                    } else if ("PRB_TASK".equalsIgnoreCase(incRecordForEngineer.getCas_subtype())) {
	                        listSnowProblemTask.add(incRecordForEngineer);
	                    } else if ("INC_TASK".equalsIgnoreCase(incRecordForEngineer.getCas_subtype())) {
	                        listSnowIncidentTask.add(incRecordForEngineer);
	                    } else if ("INC".equalsIgnoreCase(incRecordForEngineer.getCas_subtype())) {
	                        listSnowIncident.add(incRecordForEngineer);
	                    }
	                    if ("1 -Critical".equalsIgnoreCase(incRecordForEngineer.getOriginal_priority())) {
	                        listSnowCritical.add(incRecordForEngineer);
	                    } else if ("2 -High".equalsIgnoreCase(incRecordForEngineer.getOriginal_priority())) {
	                        listSnowHigh.add(incRecordForEngineer);
	                    } else if ("3 -Moderate".equalsIgnoreCase(incRecordForEngineer.getOriginal_priority())) {
	                        listSnowModerate.add(incRecordForEngineer);
	                    } else if ("4 -Low".equalsIgnoreCase(incRecordForEngineer.getOriginal_priority())) {
	                        listSnowLow.add(incRecordForEngineer);
	                    }
	                }

	                engineerRecord.setListSnowAlarms(listSnowAlarms);
	                engineerRecord.setListSnowAlarmCritical(listSnowAlarmCritical);
	                engineerRecord.setListSnowAlarmHigh(listSnowAlarmHigh);
	                engineerRecord.setListSnowAlarmModerate(listSnowAlarmModerate);
	                engineerRecord.setListSnowAlarmLow(listSnowAlarmLow);

	                engineerRecord.setListSnowChange(listSnowChange);
	                engineerRecord.setListSnowChangeTask(listSnowChangeTask);

	                engineerRecord.setListSnowIncident(listSnowIncident);
	                engineerRecord.setListSnowCritical(listSnowCritical);
	                engineerRecord.setListSnowHigh(listSnowHigh);
	                engineerRecord.setListSnowModerate(listSnowModerate);
	                engineerRecord.setListSnowLow(listSnowLow);

	                engineerRecord.setListSnowIncidentTask(listSnowIncidentTask);

	                engineerRecord.setListSnowProblem(listSnowProblem);
	                engineerRecord.setListSnowProblemTask(listSnowProblemTask);

	                engineerRecord.setListSnowProjectTask(listSnowProjectTask);
	                engineerRecord.setListSnowItem(listOfSnowItems);
	                System.out.println("Fetched SNOW Items " + listOfSnowItems.size() + " for Engineer: " + engineerName);
	            } else {
	                System.err.println("[ERROR] No SNOW Records found for an engineer " +
	                        engineerName);
	            }

	        } catch (Exception e) {
	            System.err.println("[ERROR] Exception occurred during SNOW records fetched "
	                    + e.getMessage());
	        }

	        return engineerRecord;
	    }

	    private Map<String, Map<String, EngineerRecord>> fetchEngineerMonthlyDetails(int startMonth, int endMonth,
	            int quarterNo,
	            int currentQuarter, int currentMonth, int year, int currentYear, List<String> teamHandles) {
	        Map<String, Map<String, EngineerRecord>> engineerMonthlyDetails = new HashMap<String, Map<String, EngineerRecord>>();
	        for (int monthNo = startMonth; monthNo <= endMonth
	                || (year == currentYear && quarterNo == (currentQuarter) && monthNo == currentMonth); monthNo++) {
	            boolean flag = false;
	            if (year == currentYear) {
	                if ((currentMonth + 1) < monthNo) {
	                    flag = true;
	                }
	            } else if (year >= currentYear) {
	                flag = true;
	            }
	            if (flag) {
	                break;
	            }
	            String monthNo_STR = String.format("%02d", monthNo);
	            Map<String, EngineerRecord> engineerWiseDetails = new HashMap<String, EngineerRecord>();

	            // Utilization Time
	            Map<String, Float> engineerUtilizationHour = getUtilizationHours(monthNo_STR, year);
	            // Avg. Restoration time
	            Map<Integer, Map<String, List<List<String>>>> quarterSRAverageRestorationTimeMap = getQuarterSRAverageRestorationTime(
	                    monthNo, monthNo, year);
	            Map<String, Float> restorationTime = getSRAverageRestorationTime(monthNo_STR,
	                    year, quarterSRAverageRestorationTimeMap);
	            // Avg resolution time
	            Map<String, Float> resolutionTime = getSRAverageTime(monthNo_STR,
	                    year, quarterSRAverageRestorationTimeMap, 6);
	            // Completion Time
	            Map<String, Float> completionTime = getSRAverageTime(monthNo_STR,
	                    year, quarterSRAverageRestorationTimeMap, 7);
	            // Avg. response time
	            Map<String, Float> responseData = getSRAverageResponseTime(monthNo_STR,
	                    year);
	            // Mea count
	            Map<String, Integer> MEACompliance = getTotalMea(monthNo_STR, year);

	            for (String engineerName : teamHandles) {
	                EngineerRecord engineerRecord = getAllSRsWorkedByEngineer(engineerName,
	                        "FY" + Integer.toString(year).substring(2), "Q" +
	                                Integer.toString(quarterNo),
	                        monthNo_STR + " - " + Month.of(monthNo).name().toUpperCase());
	                // setting Avg.Response Time
	                if (responseData.get(engineerName) != null) {
	                    engineerRecord.setAvgResponseTime(responseData.get(engineerName));
	                }
	                // Settring Avg.Restoration Time
	                if (restorationTime.get(engineerName) != null) {
	                    engineerRecord.setAvgRestorationTime(restorationTime.get(engineerName));
	                }
	                // Settring Avg.Resolution Time
	                if (restorationTime.get(engineerName) != null) {
	                    engineerRecord.setAvgResolutionTime(resolutionTime.get(engineerName));
	                }
	                // Settring Avg.completion Time
	                if (restorationTime.get(engineerName) != null) {
	                    engineerRecord.setAvgCompletionTime(completionTime.get(engineerName));
	                }
	                // Setting MEA count
	                if (MEACompliance.containsKey(engineerName)) {
	                    engineerRecord.setMEAs(MEACompliance.get(engineerName));
	                } else {
	                    engineerRecord.setMEAs(0);
	                }
	                // setting Utilization hour
	                if (engineerUtilizationHour.containsKey(engineerName)) {
	                    engineerRecord.setUtilizationHour(engineerUtilizationHour.get(engineerName));
	                } else {
	                    engineerRecord.setUtilizationHour(0);
	                }
	                engineerWiseDetails.put(engineerName, engineerRecord);
	            }
	            // STORE Object in Map for any future requirement for parsing, iterating, etc..
	            engineerMonthlyDetails.put(monthNo_STR + " - " + Month.of(monthNo).name().toUpperCase(),
	                    engineerWiseDetails);
	        }
	        return engineerMonthlyDetails;
	    }

	    public void initProcess() {
	        List<String> teamHandles = getTeamHandle();
	        int currentQuarter = (Calendar.getInstance().get(Calendar.MONTH) / 3) + 1;
	        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
	        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
	        // Hold Yearly Records Quartely wise
	        Map<String, Map<String, Map<String, Map<String, EngineerRecord>>>> engineerYearlyQuartelyDetails = new TreeMap<>();
	        // Hold Quartely Record Month wise
	        int year = Integer.parseInt(Config.getPropertyValue("access.year"));
	        Map<String, Map<String, Map<String, EngineerRecord>>> engineerQuartelyDetails = new HashMap<String, Map<String, Map<String, EngineerRecord>>>();
	        String quarter = utils.Config.getPropertyValue("access.quarter");
	        int quarterNo = quarter.equalsIgnoreCase("Q1") ? 1
	                : quarter.equalsIgnoreCase("Q2") ? 2 : quarter.equalsIgnoreCase("Q3") ? 3 : 4;

	        // Hold Monthly Record Engineer wise
	        Map<String, Map<String, EngineerRecord>> engineerMonthlyDetails = new HashMap<String, Map<String, EngineerRecord>>();
	        switch (quarterNo) {
	            case 1:
	                engineerQuartelyDetails.put("Q" + Integer.toString(quarterNo),
	                        fetchEngineerMonthlyDetails(10, 12,
	                                quarterNo, currentQuarter, currentMonth, year, currentYear, teamHandles));
	                break;
	            case 2:
	                engineerQuartelyDetails.put("Q" + Integer.toString(quarterNo),
	                        fetchEngineerMonthlyDetails(1, 3,
	                                quarterNo, currentQuarter, currentMonth, year, currentYear, teamHandles));
	                break;
	            case 3:
	                engineerQuartelyDetails.put("Q" + Integer.toString(quarterNo),
	                        fetchEngineerMonthlyDetails(4, 6,
	                                quarterNo, currentQuarter, currentMonth, year, currentYear, teamHandles));
	                break;
	            case 4:
	                engineerQuartelyDetails.put("Q" + Integer.toString(quarterNo),
	                        fetchEngineerMonthlyDetails(7, 9,
	                                quarterNo, currentQuarter, currentMonth, year, currentYear, teamHandles));
	                break;
	            default:
	                break;
	        }
	        engineerYearlyQuartelyDetails.put("FY" + utils.Config.getPropertyValue("access.year").substring(2),
	                engineerQuartelyDetails);
	        // EXCEL SHEET CREATION FOR A MONTH
	        ExcelOperation excelOperation = new ExcelOperation();
	        try {
	            excelOperation.prepareAGTPerformanceSheet(engineerYearlyQuartelyDetails);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            // e.printStackTrace();
	            e.getMessage();
	        }

	    }
	    
	    
	    
	    public void startReportGeneration(String quarter,String year,File javaFile) {
	    	 LocalDateTime date1 = LocalDateTime.now();
	          quarterYear = year;
	          quaterToFind = quarter;
	          downloadFolder = javaFile.toString();
	          log.info("Generating report for the year " + quarterYear + " and quarter " + quaterToFind+ " downloadfolder "+downloadFolder);
	          new PrepareSiebelReportService().initProcess();
	          LocalDateTime date2 = LocalDateTime.now();
	          driver.quit();
	          System.out.println("Start time=" + date1 + " and end at " + date2);
	     }
}