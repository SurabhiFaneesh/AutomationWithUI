package utils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExcelConvertionOperations {
    HSSFWorkbook workbook;
    HSSFSheet firstSheet;
    int rownum = 0;

    public Map<String, List<List<String>>> fileConvert(String fileName, String headerName) {
        LocalDateTime date = LocalDateTime.now();
        Map<String, List<List<String>>> engineerData = new TreeMap<>();
        try {
            FileInputStream fstream = new FileInputStream(fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                engineerData.clear();
                StringTokenizer st1 = new StringTokenizer(strLine, ",");
                while (st1.hasMoreTokens()) {
                    String data1 = st1.nextToken();
                    Document docs = Jsoup.parseBodyFragment(data1);
                    Element content = docs.select("table").get(1);
                    Elements rows = content.select("tr");
                    List<String> header = new ArrayList<>();
                    rows.get(0).getElementsByTag("td").forEach((data) -> header.add(data.text()));
                    int userIndex = header.indexOf(headerName);
                    for (int i = 2; i < rows.size(); i++) {
                        Element row = rows.get(i);
                        Elements cols = row.select("td");
                        List<List<String>> siebeldata = new ArrayList<>();
                        List<String> SRData = new ArrayList<>();
                        SRData.clear();
                        siebeldata.clear();
                        for (Element e : cols) {
                            if (e.select("a").html().length() != 0)
                                SRData.add(e.select("a").html());
                            else
                                SRData.add(e.html());
                        }
                        String owner = SRData.get(userIndex);
                        if (engineerData.containsKey(owner)) {
                            siebeldata = engineerData.get(owner);
                            siebeldata.add(SRData);
                            engineerData.put(owner, siebeldata);
                        } else {
                            siebeldata.add(SRData);
                            engineerData.put(owner, siebeldata);
                        }
                    }
                }
            }
            in.close();
        } catch (Exception e) {
            e.getMessage();
        }
        LocalDateTime date1 = LocalDateTime.now();
        return engineerData;
    }

    public Map<String, List<List<String>>> readTheExcelFile(String fileName, String headerName) {
        LocalDateTime date = LocalDateTime.now();
        Map<String, List<List<String>>> engineerData = new TreeMap<>();
        FileInputStream fis;
        Sheet sheet;
        try {
            fis = new FileInputStream(fileName);
            Workbook workbook = new HSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            Row header = sheet.getRow(0);
            List<String> headerNames = new ArrayList<>();
            header.forEach((data) -> headerNames.add(data.getStringCellValue().trim()));
            int userIndex = headerNames.indexOf(headerName);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                List<List<String>> incidentsDataList = new ArrayList<>();
                List<String> incidentsData = new ArrayList<>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    incidentsData.add(row.getCell(j).toString());
                }
                incidentsDataList.add(incidentsData);
                String user = row.getCell(userIndex).toString();
                if (engineerData.containsKey(user)) {
                    engineerData.get(user).add(incidentsData);
                } else {
                    engineerData.put(user, incidentsDataList);
                }
            }
        } catch (IOException e) {
//            throw new RuntimeException(e);
            e.getMessage();
        }
        return engineerData;
    }

    public Map<Integer, Map<String, List<List<String>>>> readExcelFileFromZipFolder(String fileName) {
        InputStream fis;
        ZipFile zipFile;
        Map<Integer, Map<String, List<List<String>>>> engineeraData = new TreeMap<>();
        try {
            zipFile = new ZipFile(fileName);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                fis = zipFile.getInputStream(entry);
                Workbook workbook = new HSSFWorkbook(fis);
                Sheet sheet = workbook.getSheetAt(0);
                Row header = sheet.getRow(0);
                List<String> headerNames = new ArrayList<>();
                header.forEach((data) -> headerNames.add(data.getStringCellValue()));
                int dateIndex = headerNames.indexOf("Date Completed");
                int userIndex = headerNames.indexOf("Closed By Login");
                // To store incidents handled by an Engineer with handle as key
                Map<String, List<List<String>>> dataEntry = null;
                // store the list of incidents handled by enginner
                List<List<String>> siebeldata = new ArrayList<>();
                List<Integer> indexOfInformation = new ArrayList<>();
                indexOfInformation.add(headerNames.indexOf("SR#"));
                indexOfInformation.add(headerNames.indexOf("Date Created"));
                indexOfInformation.add(headerNames.indexOf("Date Restored"));
                indexOfInformation.add(headerNames.indexOf("Date Resolved"));
                indexOfInformation.add(headerNames.indexOf("Rcpt to Rest(9 hr)"));
                indexOfInformation.add(headerNames.indexOf("Rcpt to Reso(9 hr)"));
                indexOfInformation.add(headerNames.indexOf("Closed By Login"));
                indexOfInformation.add(headerNames.indexOf("Date Completed"));
                indexOfInformation.add(headerNames.indexOf("Rcpt to Comp(9 hr)"));
                siebeldata = readAllIncidents(sheet, dateIndex, userIndex, indexOfInformation);
                List<List<String>> SRHandledByEngineer;
                for (int i = 1; i < siebeldata.size(); i++) {
                    int quarterMonth = findMonth(siebeldata.get(i).get(4));
                    String user = siebeldata.get(i).get(7);
                    switch (quarterMonth) {
                        case 1:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 1);
                            break;
                        case 2:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 2);
                            break;
                        case 3:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 3);
                            break;
                        case 4:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 4);
                            break;
                        case 5:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 5);
                            break;
                        case 6:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 6);
                            break;
                        case 7:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 7);
                            break;
                        case 8:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 8);
                            break;
                        case 9:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 9);
                            break;
                        case 10:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 10);
                            break;
                        case 11:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 11);
                            break;
                        case 12:
                            SRHandledByEngineer = new ArrayList<>();
                            commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 12);
                            break;

                    }
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.getMessage();
        }
        return engineeraData;
    }

    public Map<Integer, Map<String, List<List<String>>>> readExcelFileWithMonth(String fileName) {
        FileInputStream fis;
        Map<Integer, Map<String, List<List<String>>>> engineeraData = new TreeMap<>();
        // Map<Quarter,Map<EngineerName,List<list of Incidents>>>
        try {
            fis = new FileInputStream(fileName);
            Workbook workbook = new HSSFWorkbook(fis);
            Sheet sheet = workbook.getSheetAt(0);
            Row header = sheet.getRow(0);
            // headerNames
            List<String> headerNames = new ArrayList<>();
            header.forEach((data) -> headerNames.add(data.getStringCellValue()));
            int dateIndex = headerNames.indexOf("Date Completed");
            int userIndex = headerNames.indexOf("Closed By Login");
            // To store incidents handled by an Engineer with handle as key
            Map<String, List<List<String>>> dataEntry = null;
            // store the list of incidents handled by enginner
            List<List<String>> siebeldata = new ArrayList<>();
            List<Integer> indexOfInformation = new ArrayList<>();
            indexOfInformation.add(headerNames.indexOf("SR#"));
            indexOfInformation.add(headerNames.indexOf("Date Created"));
            indexOfInformation.add(headerNames.indexOf("Date Restored"));
            indexOfInformation.add(headerNames.indexOf("Date Resolved"));
            indexOfInformation.add(headerNames.indexOf("Rcpt to Rest(9 hr)"));
            indexOfInformation.add(headerNames.indexOf("Rcpt to Reso(9 hr)"));
            indexOfInformation.add(headerNames.indexOf("Closed By Login"));
            indexOfInformation.add(headerNames.indexOf("Date Completed"));
            indexOfInformation.add(headerNames.indexOf("Rcpt to Comp(9 hr)"));
            siebeldata = readAllIncidents(sheet, dateIndex, userIndex, indexOfInformation);
            List<List<String>> SRHandledByEngineer;
            for (int i = 1; i < siebeldata.size(); i++) {
                int quarterMonth = findMonth(siebeldata.get(i).get(4));
                String user = siebeldata.get(i).get(8);
                switch (quarterMonth) {
                    case 1:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 1);
                        break;
                    case 2:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 2);
                        break;
                    case 3:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 3);
                        break;
                    case 4:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 4);
                        break;
                    case 5:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 5);
                        break;
                    case 6:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 6);
                        break;
                    case 7:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 7);
                        break;
                    case 8:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 8);
                        break;
                    case 9:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 9);
                        break;
                    case 10:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 10);
                        break;
                    case 11:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 11);
                        break;
                    case 12:
                        SRHandledByEngineer = new ArrayList<>();
                        commonutil(SRHandledByEngineer, engineeraData, dataEntry, siebeldata, user, i, 12);
                        break;

                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return engineeraData;
    }

    public void commonutil(List<List<String>> SRHandledByEngineer,
                           Map<Integer, Map<String, List<List<String>>>> engineeraData, Map<String, List<List<String>>> dataEntry,
                           List<List<String>> siebeldata, String user, int i, int key) {
        SRHandledByEngineer = new ArrayList<>();
        if (engineeraData.containsKey(key)) {
            dataEntry = engineeraData.get(key);
            if (dataEntry.containsKey(user)) {
                SRHandledByEngineer = dataEntry.get(user);
                SRHandledByEngineer.add(siebeldata.get(i));
                dataEntry.put(user, SRHandledByEngineer);
            } else {
                SRHandledByEngineer.add(siebeldata.get(i));
                dataEntry.put(user, SRHandledByEngineer);
            }
        } else {
            dataEntry = new TreeMap<>();
            SRHandledByEngineer.add(siebeldata.get(i));
            dataEntry.put(user, SRHandledByEngineer);
        }
        engineeraData.put(key, dataEntry);
    }

    public List<List<String>> readAllIncidents(Sheet sheet, int dateIndex, int userIndex,
                                               List<Integer> indexOfInformation) {
        List<List<String>> siebeldata = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            List<String> incidentDetails = new ArrayList<>();
            incidentDetails.clear();
            Row row = sheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if (indexOfInformation.contains(j)) {
                    if (row.getCell(j).getCellType().toString() == "STRING")
                        incidentDetails.add(row.getCell(j).getStringCellValue());
                    else
                        incidentDetails.add(String.valueOf(row.getCell(j).getNumericCellValue()));
                }
            }
            siebeldata.add(incidentDetails);
        }
        return siebeldata;
    }

    public int findMonth(String date) {
        SimpleDateFormat dateWithMonth = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date date1 = null;
        try {
            date1 = dateWithMonth.parse(date);
        } catch (ParseException e) {
            e.getMessage();
        }
        return date1.getMonth() + 1;
    }

    public void deleteFileIfExist(String file) {
        File f1 = new File(file);
        if (isFileExist(file)) {
            f1.delete();
            System.out.println("Deleted the file : "+file);
        }
    }

    public boolean isFileExist(String file) {
    	System.out.print("Checking file existance");
        File f1 = new File(file);
        System.out.println("Checking file existance "+file+" and return "+f1.exists());
        return f1.exists();
    }
}