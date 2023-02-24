package utils;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.avaya.product.fetcher.ws.modal.EngineerRecord;
import com.avaya.product.fetcher.ws.modal.SnowRecord;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.avaya.product.fetcher.ws.SRRecord;

public class ExcelOperation {
    final int BUFFER_EMPTY_COLUMN = 0;
    final int MANAGER_COLUMN = 1;
    final int ENGINEER_NAME_COLUMN = 2;
    final int MONTH_NAME_COLUMN = 3;
    final int COMPLIANCE_ADM_COLUMN = 4;
    final int COMPLIANCE_SNOW_SLA_COLUMN = 5;
    final int COMPLIANCE_WOH_COLUMN = 6;
    final int COMPLIANCE_PID_COLUMN = 7;
    final int COMPLIANCE_AGED_SR_COLUMN = 8;
    final int COMPLIANCE_UPDATE_OVERDUE_COLUMN = 9;
    final int COMPLIANCE_MEA_COLUMN = 10;
    final int COMPLIANCE_WORKLOAD_SEPRATION_COLUMN = 11;
    final int WORKLOAD_SIEBEL_SNOW_TOTAL_COLUMN = 12;
    final int WORKLOAD_SIEBEL_ACT_RESOLTUION_COLUMN = 13;
    final int WORKLOAD_SIEBEL_ACT_COMPLETION_COLUMN = 14;
    final int WORKLOAD_SIEBEL_ACT_COMPLETION_SEPRATION_EMPTY_COLUMN = 15;
    final int WORKLOAD_SIEBEL_RESPONSE_TIME_COLUMN = 16;
    final int WORKLOAD_SIEBEL_RESTORATION_TIME_COLUMN = 17;
    final int WORKLOAD_SIEBEL_RESOLTUION_TIME_COLUMN = 18;
    final int WORKLOAD_SIEBEL_COMPLETION_TIME_COLUMN = 19;
    final int WORKLOAD_SIEBEL_COMPLETION_TIME_SEPRATION_EMPTY_COLUMN = 20;
    final int WORKLOAD_SNOW_RESPONSE_AVG_COLUMN = 21;
    final int WORKLOAD_SNOW_RESTORATION_AVG_COLUMN = 22;
    final int WORKLOAD_SNOW_RESOLUTION_AVG_COLUMN = 23;
    final int WORKLOAD_SIEBEL_SNOW_SWARM_COLUMN = 24;
    final int WORKLOAD_SIEBEL_SNOW_SWARM_EMPTY_COLUMN = 25;
    final int UTILIZATION_TOTAL__COLUMN = 26;
    final int UTILIZATION_EXTRA_CONTRIBUTION_COLUMN = 28;
    final int UTILIZATION_EXTRA_CONTRIBUTION_EMPTY_COLUMN = 29;
    final int SURVEY_RESULTS_IC_SURVEY_COLUMN = 30;
    final int SURVEY_RESULTS_SURVEY_COLUMN = 31;
    final int SURVEY_RESULTS_SURVEY_EMPTY_COLUMN = 32;
    final int SIEBEL_OUTAGE_COUNT_COLUMN = 33;
    final int SIEBEL_SBI_COUNT_COLUMN = 34;
    final int SIEBEL_BI_COUNT_COLUMN = 35;
    final int SIEBEL_NSI_COUNT_COLUMN = 36;
    final int SIEBEL_NSI_EMPTY_HANDOFF_COLUMN = 37;
    final int SIEBEL_HANDOFF_COLUMN = 38;
    final int SIEBEL_COLLAB_COLUMN = 49;
    final int SIEBEL_COLLAB_EMPTY_COLUMN = 40;
    final int SIEBEL_RESPONSE_TIME_COLUMN = 41;
    final int SIEBEL_RESOLTUION_TIME_COLUMN = 42;
    final int SIEBEL_RESTORE_TIME_COLUMN = 43;
    final int SIEBEL_COMPLETION_TIME_COLUMN = 44;
    final int SIEBEL_COMPLETION_TIME_EMPTY_COLUMN = 45;
    final int SNOW_CRITICAL_COUNT_COLUMN = 47;
    final int SNOW_HIGH_COUNT_COLUMN = 48;
    final int SNOW_MODERATE_COUNT_COLUMN = 49;
    final int SNOW_LOW_COUNT_COLUMN = 50;
    final int SNOW_LOW_COUNT_EMPTY_COLUMN = 51;
    final int SNOW_ALARM_COUNT_COLUMN = 52;
    final int SNOW_PROJECTS_COUNT_COLUMN = 53;
    final int SNOW_CHANGES_COUNT_COLUMN = 54;
    final int SNOW_TASKS_COUNT_COLUMN = 55;
    final int SNOW_SWARMS_COUNT_COLUMN = 56;
    final int SNOW_SWARMS_EMPTY_COUNT_COLUMN = 57;
    final int SNOW_RESOLUTON_COLUMN = 58;
    final int SNOW_RESTORE_COLUMN = 59;
    final int SNOW_COMPLETION_COLUMN = 60;
    final int SNOW_COMPLETION_EMPTY_COLUMN = 61;
    final int WORKLOAD_DETAILED_COLUMN = 62;
    final int TIME_AVG_EMPTY_COLUMN = 61;
    final int WORK_LOAD_LABEL_COLUMN = 62;
    final int WORK_LOAD_SIEBEL_NO_OF_SIEBEL_COLUMN = 63;
    final int WORK_LOAD_SIEBEL_NO_OF_ACTIVITIES_COLUMN = 64;
    final int WORK_LOAD_SNOW_TOTAL_COUNT_COLUMN = 65;
    final int WORK_LOAD_SNOW_INC_COLUMN = 66;
    final int WORK_LOAD_SNOW_TASK_COLUMN = 67;
    final int WORK_LOAD_SNOW_ALARMS_COLUMN = 68;
    final int WORK_LOAD_SNOW_CHANGE_COLUMN = 69;
    final int WORK_LOAD_SNOW_PRJ_COLUMN = 70;
    final int WORK_LOAD_SNOW_PRB_COLUMN = 71;
    final int WORK_LOAD_SNOW_CLEARED_ON_ACCESS_COLUMN = 72;
    final int WORK_LOAD_SNOW_WORKING_AS_DESIGNED_COLUMN = 73;
    final int WORK_LOAD_SNOW_CHANGE_REQUEST_COLUMN = 74;
    final int WORK_LOAD_SNOW_CUSTOMER_3RD_PARTY_COLUMN = 75;
    final int WORK_LOAD_SNOW_PATCH_UPGRADE_COLUMN = 76;
    final int WORK_LOAD_SNOW_REPAIRED_COLUMN = 77;
    final int WORK_LOAD_SNOW_CANCELLED_COLUMN = 78;
    final int WORK_LOAD_SNOW_DUPLICATE_COLUMN = 79;
    final int WORK_LOAD_SNOW_IGNORE_ALARM_COLUMN = 80;
    final int WORK_LOAD_SNOW_TICKET_IN_ERROR_COLUMN = 81;
    final int WORK_LOAD_TOTAL_TICKET_SIE_SNOW_COLUMN = 82;
    final int WORK_LOAD_COMMENTS_COLUMN = 83;
    final int WORK_LOAD_SIEBEL_EMPTY_COLUMN = 84;
    final int REPEAT_SIEBEL_OUTAGE_NUMBER_COLUMN = 85;
    final int REPEAT_SIEBEL_SBI_NUMBER_COLUMN = 86;
    final int REPEAT_SIEBEL_BI_NUMBER_COLUMN = 87;
    final int REPEAT_SIEBEL_NSI_NUMBER_COLUMN = 88;
    final int REPEAT_SIEBEL_HANDOFF_COLUMN = 89;
    final int REPEAT_SIEBEL_EMPTY_COLUMN = 90;
    final int REPEAT_SIEBEL_RESPONSE_TIME_COLUMN = 91;
    final int REPEAT_SIEBEL_RESOLUTION_TIME_COLUMN = 92;
    final int REPEAT_SIEBEL_RESTORE_TIME_COLUMN = 93;
    final int REPEAT_SIEBEL_COMPLETION_TIME_COLUMN = 94;
    final int REPEAT_SIEBEL_TIME_EMPTY_COLUMN = 95;
    final int REPEAT_SNOW_CRTICIAL_COUNT_COLUMN = 96;
    final int REPEAT_SNOW_HIGH_COUNT_COLUMN = 97;
    final int REPEAT_SNOW_MODERATE_COUNT_COLUMN = 98;
    final int REPEAT_SNOW_LOW_COUNT_COLUMN = 99;
    final int REPEAT_SNOW_COUNT_EMPTY_COLUMN = 100;
    final int REPEAT_SNOW_RESOLTUION_TIME_COLUMN = 101;
    final int REPEAT_SNOW_RESTORE_TIME_COLUMN = 102;
    final int REPEAT_SNOW_COMPLETION_TIME_COLUMN = 103;
    final int REPEAT_SNOW_TIME_EMPTY_COLUMN = 104;
    final int REPEAT_ER_SWARM_COUNT_COLUMN = 105;
    final int REPEAT_ER_SWARM_EMPTY_COLUMN = 106;
    final int REPEAT_HANDOFF_COUNT_COLUMN = 107;

    public void prepareAGTPerformanceSheet(
            Map<String, Map<String, Map<String, Map<String, EngineerRecord>>>> engineerYearlyQuartelyDetails)
            throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Set<String> yearlyKeySet = engineerYearlyQuartelyDetails.keySet();
        for (String year : yearlyKeySet) {
            Map<String, Map<String, Map<String, EngineerRecord>>> engineerQuartelyDetails = engineerYearlyQuartelyDetails
                    .get(year);
            Set<String> quarterKeySet = engineerQuartelyDetails.keySet();
            for (String quarter : quarterKeySet) {
                Map<String, Map<String, EngineerRecord>> engineerMonthlyDetails = engineerQuartelyDetails.get(quarter);
                Set<String> monthlyKeySet = engineerMonthlyDetails.keySet();
                for (String month : monthlyKeySet) {
                    Map<String, EngineerRecord> engineerDetails = engineerMonthlyDetails.get(month);
                    XSSFSheet sheet = workbook.createSheet(year + " - " + quarter + " - " + month + " - AGT PERFORM");
                    XSSFFont font = ((XSSFWorkbook) workbook).createFont();
                    font.setFontName("Calibri");
                    font.setFontHeightInPoints((short) 11);
                    // font.setBold(true);
                    prepareFirstHeader(sheet, workbook, font);
                    prepareSecondHeader(sheet, workbook, font);
                    prepareThirdHeader(sheet, workbook, font);
                    fillRecords(engineerDetails, sheet, workbook);

                    // COLUMNS width
                    sheet.setColumnWidth(BUFFER_EMPTY_COLUMN, 12 * 256);
                    sheet.setColumnWidth(MANAGER_COLUMN, 13 * 256);
                    sheet.setColumnWidth(ENGINEER_NAME_COLUMN, 21 * 256);
                    sheet.setColumnWidth(MONTH_NAME_COLUMN, 2 * 256);
                    sheet.setColumnWidth(COMPLIANCE_ADM_COLUMN, 16 * 256);
                    sheet.setColumnWidth(COMPLIANCE_SNOW_SLA_COLUMN, 20 * 256);
                    sheet.setColumnWidth(COMPLIANCE_WOH_COLUMN, 16 * 256);
                    sheet.setColumnWidth(COMPLIANCE_PID_COLUMN, 4 * 256);
                    sheet.setColumnWidth(COMPLIANCE_AGED_SR_COLUMN, 8 * 256);
                    sheet.setColumnWidth(COMPLIANCE_UPDATE_OVERDUE_COLUMN, 15 * 256);
                    sheet.setColumnWidth(COMPLIANCE_MEA_COLUMN, 5 * 256);
                    sheet.setColumnWidth(11, 1 * 256);
                    sheet.setColumnWidth(12, 17 * 256);
                    sheet.setColumnWidth(13, 13 * 256);
                    sheet.setColumnWidth(14, 13 * 256);
                    sheet.setColumnWidth(15, 1 * 256);
                    sheet.setColumnWidth(16, 8 * 256);// changed to 8 from 14
                    sheet.setColumnWidth(17, 8 * 256);// changed to 8 from 14
                    sheet.setColumnWidth(18, 13 * 256);
                    sheet.setColumnWidth(19, 14 * 256);
                    sheet.setColumnWidth(20, 1 * 256);
                    sheet.setColumnWidth(21, 9 * 256);
                    sheet.setColumnWidth(22, 8 * 256);
                    sheet.setColumnWidth(23, 8 * 256);
                    sheet.setColumnWidth(24, 18 * 256);
                    sheet.setColumnWidth(25, 1 * 256);
                    sheet.setColumnWidth(26, 10 * 256);
                    sheet.setColumnWidth(27, 9 * 256);
                    sheet.setColumnWidth(28, 1 * 256);
                    sheet.setColumnWidth(29, 9 * 256);
                    sheet.setColumnWidth(30, 7 * 256);
                    sheet.setColumnWidth(31, 2 * 256);
                    sheet.setColumnWidth(32, 3 * 256);
                    sheet.setColumnWidth(33, 9 * 256);
                    sheet.setColumnWidth(34, 9 * 256);
                    sheet.setColumnWidth(35, 9 * 256);
                    sheet.setColumnWidth(36, 9 * 256);
                    sheet.setColumnWidth(37, 1 * 256);
                    sheet.setColumnWidth(38, 9 * 256);
                    sheet.setColumnWidth(39, 9 * 256);
                    sheet.setColumnWidth(40, 1 * 256);
                    sheet.setColumnWidth(41, 14 * 256);
                    sheet.setColumnWidth(42, 14 * 256);
                    sheet.setColumnWidth(43, 14 * 256);
                    sheet.setColumnWidth(44, 16 * 256);
                    sheet.setColumnWidth(45, 3 * 256);
                    sheet.setColumnWidth(46, 3 * 256);
                    sheet.setColumnWidth(47, 9 * 256);
                    sheet.setColumnWidth(48, 9 * 256);
                    sheet.setColumnWidth(49, 9 * 256);
                    sheet.setColumnWidth(50, 9 * 256);
                    sheet.setColumnWidth(51, 2 * 256);
                    sheet.setColumnWidth(52, 9 * 256);
                    sheet.setColumnWidth(53, 9 * 256);
                    sheet.setColumnWidth(54, 9 * 256);
                    sheet.setColumnWidth(55, 9 * 256);
                    sheet.setColumnWidth(56, 9 * 256);
                    sheet.setColumnWidth(57, 2 * 256);
                    sheet.setColumnWidth(58, 15 * 256);
                    sheet.setColumnWidth(59, 15 * 256);
                    sheet.setColumnWidth(60, 16 * 256);
                    sheet.setColumnWidth(TIME_AVG_EMPTY_COLUMN, 4 * 256);
                    sheet.setColumnWidth(WORK_LOAD_LABEL_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SIEBEL_NO_OF_SIEBEL_COLUMN, 5 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SIEBEL_NO_OF_ACTIVITIES_COLUMN, 8 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_TOTAL_COUNT_COLUMN, 7 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_INC_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_TASK_COLUMN, 6 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_ALARMS_COLUMN, 7 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_CHANGE_COLUMN, 5 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_PRJ_COLUMN, 8 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_PRB_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_CLEARED_ON_ACCESS_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_WORKING_AS_DESIGNED_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_CHANGE_REQUEST_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_CUSTOMER_3RD_PARTY_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_PATCH_UPGRADE_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_REPAIRED_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_CANCELLED_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_DUPLICATE_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_IGNORE_ALARM_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SNOW_TICKET_IN_ERROR_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_TOTAL_TICKET_SIE_SNOW_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_COMMENTS_COLUMN, 9 * 256);
                    sheet.setColumnWidth(WORK_LOAD_SIEBEL_EMPTY_COLUMN, 1 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_OUTAGE_NUMBER_COLUMN, 8 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_SBI_NUMBER_COLUMN, 8 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_BI_NUMBER_COLUMN, 4 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_NSI_NUMBER_COLUMN, 4 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_HANDOFF_COLUMN, 5 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_EMPTY_COLUMN, 2 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_RESPONSE_TIME_COLUMN, 10 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_RESOLUTION_TIME_COLUMN, 5 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_RESTORE_TIME_COLUMN, 10 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_COMPLETION_TIME_COLUMN, 10 * 256);
                    sheet.setColumnWidth(REPEAT_SIEBEL_TIME_EMPTY_COLUMN, 1 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_CRTICIAL_COUNT_COLUMN, 9 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_HIGH_COUNT_COLUMN, 6 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_MODERATE_COUNT_COLUMN, 9 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_LOW_COUNT_COLUMN, 5 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_COUNT_EMPTY_COLUMN, 1 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_RESOLTUION_TIME_COLUMN, 7 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_RESTORE_TIME_COLUMN, 7 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_COMPLETION_TIME_COLUMN, 10 * 256);
                    sheet.setColumnWidth(REPEAT_SNOW_TIME_EMPTY_COLUMN, 2 * 256);
                    sheet.setColumnWidth(REPEAT_ER_SWARM_COUNT_COLUMN, 9 * 256);
                    sheet.setColumnWidth(REPEAT_ER_SWARM_EMPTY_COLUMN, 1 * 256);
                    sheet.setColumnWidth(REPEAT_HANDOFF_COUNT_COLUMN, 10 * 256);
                    setBordersToMergedCells(workbook, sheet);
                }
            }
        } 
        System.out.println("FILE PATH="+Config.getPropertyValue("access.download_folder"));
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = Config.getPropertyValue("access.download_folder")+"/"+ Config.getPropertyValue("access.quarter")+"-"+Config.getPropertyValue("access.year")+"-EngineerMetrics.xlsx";
        System.out.println(fileLocation);
        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();
    }

    private void setBordersToMergedCells(Workbook workBook, Sheet sheet) {
        List<CellRangeAddress> mergedRegions = sheet.getMergedRegions();
        for (CellRangeAddress rangeAddress : mergedRegions) {
            RegionUtil.setBorderTop(BorderStyle.THIN, rangeAddress, sheet);
            RegionUtil.setBorderLeft(BorderStyle.THIN, rangeAddress, sheet);
            RegionUtil.setBorderRight(BorderStyle.THIN, rangeAddress, sheet);
            RegionUtil.setBorderBottom(BorderStyle.THIN, rangeAddress, sheet);
        }
    }

    private void prepareFirstHeader(XSSFSheet sheet, XSSFWorkbook workbook, XSSFFont font1) {
        int rowNum = 2;
        XSSFRow header = sheet.createRow(rowNum);
        header.setHeight((short) (15 * 20));
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);

        Cell headerCell = header.createCell(1);
        XSSFCellStyle headerCellStyle = getHeaderStyleSheet(workbook, font, new java.awt.Color(169, 208, 142));
        headerCell.setCellStyle(headerCellStyle);
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 1, 2));
        headerCell.setCellValue("LEADERS/AGENTS");

        Cell headerCompliance = header.createCell(4);
        XSSFCellStyle headerStyleComplianceHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(217, 225, 242));
        headerCompliance.setCellValue("Compliance");
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 4, 10));
        headerCompliance.setCellStyle(headerStyleComplianceHeader);

        Cell headerWorkLoadCell = header.createCell(12);
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 12, 24));
        XSSFCellStyle headerStyleWorkloadHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(252, 228, 214));
        headerWorkLoadCell.setCellStyle(headerStyleWorkloadHeader);
        headerWorkLoadCell.setCellValue("Workload (restoration - resolution - completion)");

        Cell headerUtilizationCell = header.createCell(26);
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 26, 27));
        XSSFCellStyle headerUtilizationCellStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 242, 204));
        headerUtilizationCell.setCellStyle(headerUtilizationCellStyle);
        headerUtilizationCell.setCellValue("Utilization");

        Cell headerSurveyCell = header.createCell(29);
        sheet.addMergedRegion(new CellRangeAddress(2, 3, 29, 30));
        XSSFCellStyle headerStyleSurveyHeader = getHeaderStyleSheet(workbook, font, new java.awt.Color(226, 239, 218));
        headerSurveyCell.setCellStyle(headerStyleSurveyHeader);
        headerSurveyCell.setCellValue("Survey Results");

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 33, 44));
        Cell headerSiebelTotalCell = header.createCell(33);
        XSSFCellStyle headerStyleSiebelTotalHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(226, 239, 218));
        headerSiebelTotalCell.setCellStyle(headerStyleSiebelTotalHeader);
        headerSiebelTotalCell.setCellValue("Siebel Total");

        sheet.addMergedRegion(new CellRangeAddress(2, 2, 47, 60));
        headerCell = header.createCell(47);
        XSSFCellStyle headerStyleSnowTotalHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(248, 203, 173));
        headerCell.setCellStyle(headerStyleSnowTotalHeader);
        headerCell.setCellValue("Snow Total");
    }

    private XSSFCellStyle getHeaderStyleSheet(XSSFWorkbook workbook, XSSFFont font, java.awt.Color color) {
        XSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        headerStyle.setWrapText(true);

        IndexedColorMap colorMap = workbook.getStylesSource().getIndexedColors();
        headerStyle.setFillForegroundColor(new XSSFColor(color, colorMap));
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFont(font);
        return headerStyle;
    }

    private void prepareSecondHeader(XSSFSheet sheet, XSSFWorkbook workbook, XSSFFont font) {
        int rowNum = 3;
        XSSFRow header = sheet.createRow(rowNum);
        header.setHeight((short) (15 * 20));
        // sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 1, 2));
        // XSSFCellStyle headerStyle = getHeaderStyleSheet(workbook, font, new
        // java.awt.Color(169, 208, 142));
        // Cell headerCell = CellUtil.createCell(header, 1, "");
        // headerCell.setCellStyle(headerStyle);

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 12, 19));
        Cell headerCell = header.createCell(12);
        XSSFCellStyle headerSiebelHeader = getHeaderStyleSheet(workbook, font, new java.awt.Color(252, 228, 214));
        headerCell.setCellStyle(headerSiebelHeader);
        headerCell.setCellValue("SIEBEL");

        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 21, 23));
        headerCell = header.createCell(21);
        XSSFCellStyle headerStyleSnowHeader = getHeaderStyleSheet(workbook, font, new java.awt.Color(252, 228, 214));
        headerCell.setCellStyle(headerStyleSnowHeader);
        headerCell.setCellValue("SNOW");

        headerCell = header.createCell(24);
        XSSFCellStyle headerStyleSiebleSnowHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(252, 228, 214));
        headerCell.setCellStyle(headerStyleSiebleSnowHeader);
        headerCell.setCellValue("Siebel - Snow");

        headerCell = header.createCell(33);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 33, 36));
        XSSFCellStyle headerStyleTotalLoadPerSeverityHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 255));
        headerCell.setCellStyle(headerStyleTotalLoadPerSeverityHeader);
        headerCell.setCellValue("MAY Total Tickets load per Severity");

        headerCell = header.createCell(41);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 41, 44));
        XSSFCellStyle headerStyleTicketWorkTimeAvgHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 255));
        headerCell.setCellStyle(headerStyleTicketWorkTimeAvgHeader);
        headerCell.setCellValue("Ticket Work Time Average");

        headerCell = header.createCell(47);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 47, 50));
        XSSFCellStyle headerStyleTotalTicketLoadPerPriorityHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 255));
        headerCell.setCellStyle(headerStyleTotalTicketLoadPerPriorityHeader);
        headerCell.setCellValue("Total Tickets load per Priority");

        headerCell = header.createCell(52);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 52, 56));
        XSSFCellStyle headerStyleTotalWorkLoadPerCategoryHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 255));
        headerCell.setCellStyle(headerStyleTotalWorkLoadPerCategoryHeader);
        headerCell.setCellValue("Total Workload Per Category");

        headerCell = header.createCell(58);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 58, 60));
        XSSFCellStyle headerStyleSnowTicketWorkTimeAvgHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 255));
        headerCell.setCellStyle(headerStyleSnowTicketWorkTimeAvgHeader);
        headerCell.setCellValue("Ticket Work Time Average");

        headerCell = header.createCell(63);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 63, 64));
        XSSFCellStyle headerStyleSiebelCountHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(217, 225, 242));
        headerCell.setCellStyle(headerStyleSiebelCountHeader);
        headerCell.setCellValue("Siebel");

        headerCell = header.createCell(65);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 65, 71));
        XSSFCellStyle headerStyleSnowCountHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(248, 203, 173));
        headerCell.setCellStyle(headerStyleSnowCountHeader);
        headerCell.setCellValue("Service Now");

        headerCell = header.createCell(72);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 72, 81));
        XSSFCellStyle headerStyleAlarmReasonStatusHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(248, 203, 173));
        headerCell.setCellStyle(headerStyleAlarmReasonStatusHeader);
        headerCell.setCellValue("Alarms Reason and Status");

        headerCell = header.createCell(85);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 85, 94));
        XSSFCellStyle headerStyleSeieleTotalCatTimerHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(217, 225, 242));
        headerCell.setCellStyle(headerStyleSeieleTotalCatTimerHeader);
        headerCell.setCellValue("Siebel");

        headerCell = header.createCell(96);
        sheet.addMergedRegion(new CellRangeAddress(rowNum, rowNum, 96, 103));
        XSSFCellStyle headerStyleSnowCategTimerHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(226, 239, 218));
        headerCell.setCellStyle(headerStyleSnowCategTimerHeader);
        headerCell.setCellValue("SNOW");

        headerCell = header.createCell(105);
        XSSFCellStyle headerStyleERCounterHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(244, 176, 132));
        headerCell.setCellStyle(headerStyleERCounterHeader);
        headerCell.setCellValue("ER Activities");

        headerCell = header.createCell(107);
        XSSFCellStyle headerStyleHandoffCounterHeader = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(102, 102, 153));
        headerCell.setCellStyle(headerStyleHandoffCounterHeader);
        headerCell.setCellValue("HANDOFF - SIEBEL");
    }

    private void prepareThirdHeader(XSSFSheet sheet, XSSFWorkbook workbook, XSSFFont font1) {
        int rowNum = 4;

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(true);

        XSSFRow header = sheet.createRow(rowNum);
        header.setHeight((short) (57 * 20));
        Cell headerCell = header.createCell(1);
        XSSFCellStyle headerCellStyle = getHeaderStyleSheet(workbook, font, new java.awt.Color(214, 220, 228));
        headerCell.setCellStyle(headerCellStyle);
        headerCell.setCellValue("MANAGERS");
        headerCell = header.createCell(2);
        headerCell.setCellValue("CATEGORIES");
        headerCell.setCellStyle(headerCellStyle);

        XSSFCellStyle headerComplianceStyle = getHeaderStyleSheet(workbook, font, new java.awt.Color(204, 204, 255));
        headerCell = header.createCell(4);
        headerCell.setCellValue("ADM Compliance");
        headerCell.setCellStyle(headerComplianceStyle);
        headerCell = header.createCell(5);
        headerCell.setCellValue("Snow SLA Compliance");
        headerCell.setCellStyle(headerComplianceStyle);
        headerCell = header.createCell(6);
        headerCell.setCellValue("WOH Compliance");
        headerCell.setCellStyle(headerComplianceStyle);
        headerCell = header.createCell(7);
        headerCell.setCellValue("PID");
        headerCell.setCellStyle(headerComplianceStyle);
        headerCell = header.createCell(8);
        headerCell.setCellValue("Aged Srs");
        headerCell.setCellStyle(headerComplianceStyle);
        headerCell = header.createCell(9);
        headerCell.setCellValue("Update Overdue");
        headerCell.setCellStyle(headerComplianceStyle);
        headerCell = header.createCell(10);
        headerCell.setCellValue("MEA");
        headerCell.setCellStyle(headerComplianceStyle);

        XSSFCellStyle headerSiebelWorkLoanSnowSiebelTotalStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 204, 255));
        headerCell = header.createCell(12);
        headerCell.setCellValue("Siebel - Snow total");
        headerCell.setCellStyle(headerSiebelWorkLoanSnowSiebelTotalStyle);

        XSSFCellStyle headerACTResolutionStyle = getHeaderStyleSheet(workbook, font, new java.awt.Color(255, 204, 204));
        headerCell = header.createCell(13);
        headerCell.setCellValue("ACT Resolution");
        headerCell.setCellStyle(headerACTResolutionStyle);
        headerCell = header.createCell(14);
        headerCell.setCellValue("ACT Completion");
        headerCell.setCellStyle(headerACTResolutionStyle);

        XSSFCellStyle headerSiebelResponseTimeStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 153, 153));
        headerCell = header.createCell(16);
        headerCell.setCellValue("Response Time");
        headerCell.setCellStyle(headerSiebelResponseTimeStyle);
        headerCell = header.createCell(17);
        headerCell.setCellValue("SR Restoration");
        headerCell.setCellStyle(headerSiebelResponseTimeStyle);
        headerCell = header.createCell(18);
        headerCell.setCellValue("SR Resolution");
        headerCell.setCellStyle(headerSiebelResponseTimeStyle);
        headerCell = header.createCell(19);
        headerCell.setCellValue("SR Completion");
        headerCell.setCellStyle(headerSiebelResponseTimeStyle);

        XSSFCellStyle headerSiebelSnowRespAvgStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 204));
        headerCell = header.createCell(21);
        headerCell.setCellValue("Resp Avg");
        headerCell.setCellStyle(headerSiebelSnowRespAvgStyle);
        headerCell = header.createCell(22);
        headerCell.setCellValue("Rest Avg");
        headerCell.setCellStyle(headerSiebelSnowRespAvgStyle);
        headerCell = header.createCell(23);
        headerCell.setCellValue("Res Avg");
        headerCell.setCellStyle(headerSiebelSnowRespAvgStyle);

        XSSFCellStyle headerSiebelSnowSwarmNotificationStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 153));
        headerCell = header.createCell(24);
        headerCell.setCellValue("Swarm Notification");
        headerCell.setCellStyle(headerSiebelSnowSwarmNotificationStyle);

        XSSFCellStyle headerUtilizationStyle = getHeaderStyleSheet(workbook, font, new java.awt.Color(51, 204, 204));
        headerCell = header.createCell(26);
        headerCell.setCellValue("Utilization");
        headerCell.setCellStyle(headerSiebelSnowSwarmNotificationStyle);
        headerCell = header.createCell(27);
        headerCell.setCellValue("Extra Contributions (H) (no ct facing)");
        headerCell.setCellStyle(headerUtilizationStyle);

        XSSFCellStyle headerICSurveyStyle = getHeaderStyleSheet(workbook, font, new java.awt.Color(204, 255, 255));
        headerCell = header.createCell(29);
        headerCell.setCellValue("IC Survey");
        headerCell.setCellStyle(headerICSurveyStyle);
        headerCell = header.createCell(30);
        headerCell.setCellValue("Survey");
        headerCell.setCellStyle(headerICSurveyStyle);

        XSSFCellStyle headerVerticalSiebleHaderStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(248, 203, 173));
        headerVerticalSiebleHaderStyle.setRotation((short) 90);
        headerCell = header.createCell(32);
        headerCell.setCellValue("SIEBEL");
        headerCell.setCellStyle(headerVerticalSiebleHaderStyle);

        XSSFCellStyle headerSiebelTotalLoadSeverityStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 204));
        headerCell = header.createCell(33);
        headerCell.setCellValue("Outage");
        headerCell.setCellStyle(headerSiebelTotalLoadSeverityStyle);
        headerCell = header.createCell(34);
        headerCell.setCellValue("SBI");
        headerCell.setCellStyle(headerSiebelTotalLoadSeverityStyle);
        headerCell = header.createCell(35);
        headerCell.setCellValue("BI");
        headerCell.setCellStyle(headerSiebelTotalLoadSeverityStyle);
        headerCell = header.createCell(36);
        headerCell.setCellValue("NSI");
        headerCell.setCellStyle(headerSiebelTotalLoadSeverityStyle);

        XSSFCellStyle headerSiebelTotalHandoffStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(244, 176, 132));
        headerCell = header.createCell(38);
        headerCell.setCellValue("Handoff");
        headerCell.setCellStyle(headerSiebelTotalHandoffStyle);
        headerCell = header.createCell(39);
        headerCell.setCellValue("Collabs");
        headerCell.setCellStyle(headerSiebelTotalHandoffStyle);

        XSSFCellStyle headerSiebelWorkTimeAvgStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(112, 173, 71));
        headerCell = header.createCell(41);
        headerCell.setCellValue("Response Time");
        headerCell.setCellStyle(headerSiebelWorkTimeAvgStyle);
        headerCell = header.createCell(42);
        headerCell.setCellValue("Resolution Time");
        headerCell.setCellStyle(headerSiebelWorkTimeAvgStyle);
        headerCell = header.createCell(43);
        headerCell.setCellValue("Restore Time");
        headerCell.setCellStyle(headerSiebelWorkTimeAvgStyle);
        headerCell = header.createCell(44);
        headerCell.setCellValue("Completion Time");
        headerCell.setCellStyle(headerSiebelWorkTimeAvgStyle);

        XSSFCellStyle headerSnowVerticalHeaderStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(252, 228, 214));
        headerCell = header.createCell(46);
        headerSnowVerticalHeaderStyle.setRotation((short) 90);
        headerCell.setCellValue("SNOW");
        headerCell.setCellStyle(headerSnowVerticalHeaderStyle);

        XSSFCellStyle headerSnowOutageTotalHeaderStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(255, 255, 204));
        headerCell = header.createCell(47);
        headerCell.setCellValue("Critical");
        headerCell.setCellStyle(headerSnowOutageTotalHeaderStyle);
        headerCell = header.createCell(48);
        headerCell.setCellValue("High");
        headerCell.setCellStyle(headerSnowOutageTotalHeaderStyle);
        headerCell = header.createCell(49);
        headerCell.setCellValue("Moderate");
        headerCell.setCellStyle(headerSnowOutageTotalHeaderStyle);
        headerCell = header.createCell(50);
        headerCell.setCellValue("Low");
        headerCell.setCellStyle(headerSnowOutageTotalHeaderStyle);

        XSSFCellStyle headerSnowWorkLoadCategoryHeaderStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(204, 204, 255));
        headerCell = header.createCell(52);
        headerCell.setCellValue("Alarms");
        headerCell.setCellStyle(headerSnowWorkLoadCategoryHeaderStyle);
        headerCell = header.createCell(53);
        headerCell.setCellValue("Projects (snow handoff)");
        headerCell.setCellStyle(headerSnowWorkLoadCategoryHeaderStyle);
        headerCell = header.createCell(54);
        headerCell.setCellValue("Changes");
        headerCell.setCellStyle(headerSnowWorkLoadCategoryHeaderStyle);
        headerCell = header.createCell(55);
        headerCell.setCellValue("Tasks");
        headerCell.setCellStyle(headerSnowWorkLoadCategoryHeaderStyle);
        headerCell = header.createCell(56);
        headerCell.setCellValue("Swarms");
        headerCell.setCellStyle(headerSnowWorkLoadCategoryHeaderStyle);

        XSSFCellStyle headerSnowAvgTimeHeaderStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(112, 173, 71));
        headerCell = header.createCell(58);
        headerCell.setCellValue("Resolution Time");
        headerCell.setCellStyle(headerSnowAvgTimeHeaderStyle);
        headerCell = header.createCell(59);
        headerCell.setCellValue("Restore Time");
        headerCell.setCellStyle(headerSnowAvgTimeHeaderStyle);
        headerCell = header.createCell(60);
        headerCell.setCellValue("Completion Time");
        headerCell.setCellStyle(headerSnowAvgTimeHeaderStyle);

        XSSFCellStyle headerWorkLoadDetailsVerticalHeaderStyle = getHeaderStyleSheet(workbook, font,
                new java.awt.Color(112, 173, 71));
        headerCell = header.createCell(62);
        headerWorkLoadDetailsVerticalHeaderStyle.setWrapText(true);
        headerWorkLoadDetailsVerticalHeaderStyle.setRotation((short) 90);
        headerCell.setCellValue("WORKLOAD DETAILED");
        headerCell.setCellStyle(headerWorkLoadDetailsVerticalHeaderStyle);

        XSSFFont localFont = ((XSSFWorkbook) workbook).createFont();
        localFont.setFontName("Calibri");
        IndexedColorMap colorMap = workbook.getStylesSource().getIndexedColors();
        localFont.setColor(new XSSFColor(new java.awt.Color(255, 255, 255), colorMap));
        localFont.setFontHeightInPoints((short) 11);
        localFont.setBold(true);
        XSSFCellStyle headerWorkLoadDetailsNoOfSielbelHeaderStyle = getHeaderStyleSheet(workbook, localFont,
                new java.awt.Color(112, 173, 71));

        headerCell = header.createCell(63);
        headerCell.setCellValue("# SRs");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(64);
        headerCell.setCellValue("Activity - collab");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(65);
        headerCell.setCellValue("Total SNOW tickets");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(66);
        headerCell.setCellValue("Incidents");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(67);
        headerCell.setCellValue("# Task");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(68);
        headerCell.setCellValue("Alarms");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(69);
        headerCell.setCellValue("CHG");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(70);
        headerCell.setCellValue("PRJTasks- Handoff");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(71);
        headerCell.setCellValue("Problem");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(72);
        headerCell.setCellValue("Cleared on Access");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(73);
        headerCell.setCellValue("Working as Designed");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(74);
        headerCell.setCellValue("Change Request");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(75);
        headerCell.setCellValue("Customer or 3rd party Action");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(76);
        headerCell.setCellValue("Patch/Upgrade");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(77);
        headerCell.setCellValue("Repaired");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(78);
        headerCell.setCellValue("Cancelled");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(79);
        headerCell.setCellValue("Duplicate");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(80);
        headerCell.setCellValue("Ignore Alarm");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(81);
        headerCell.setCellValue("Ticket Created in Error");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(82);
        headerCell.setCellValue("Total of SIEBEL & SNOW Tickets");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(83);
        headerCell.setCellValue("Comments");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);

        headerCell = header.createCell(85);
        headerCell.setCellValue("Outage");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(86);
        headerCell.setCellValue("SBI");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(87);
        headerCell.setCellValue("BI");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(88);
        headerCell.setCellValue("NSI");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);

        headerCell = header.createCell(91);
        headerCell.setCellValue("Response Time");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(92);
        headerCell.setCellValue("Resolution Time");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(93);
        headerCell.setCellValue("Restrore Time");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(94);
        headerCell.setCellValue("Completion Time");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);

        headerCell = header.createCell(96);
        headerCell.setCellValue("Critical");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(97);
        headerCell.setCellValue("High");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(98);
        headerCell.setCellValue("Moderate");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(99);
        headerCell.setCellValue("Low");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);

        headerCell = header.createCell(101);
        headerCell.setCellValue("Resolution Time");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(102);
        headerCell.setCellValue("Restore Time");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);
        headerCell = header.createCell(103);
        headerCell.setCellValue("Completion Time");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);

        headerCell = header.createCell(105);
        headerCell.setCellValue("Swarms");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);

        headerCell = header.createCell(107);
        headerCell.setCellValue("Avgerage Resolution Time");
        headerCell.setCellStyle(headerWorkLoadDetailsNoOfSielbelHeaderStyle);

    }

    private void fillRecords(Map<String, EngineerRecord> engineerMonthlyDetails, XSSFSheet sheet,
                             XSSFWorkbook workbook) {
        int rowNum = 5;

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 11);
        font.setBold(false);

        List<EngineerRecord> engineerRecords = new ArrayList<EngineerRecord>(engineerMonthlyDetails.values());

        for (EngineerRecord engineerRecord : engineerRecords) {

            XSSFRow row = sheet.createRow(rowNum);
            row.setHeight((short) (15 * 20));
            for (int colNum = 1; colNum < 108; colNum++) {
                fillColumnValue(row, colNum, engineerRecord, sheet, workbook, font);
            }

            rowNum++;
        }

    }

    private void fillColumnValue(XSSFRow row, int col, EngineerRecord engineerRecord, XSSFSheet sheet,
                                 XSSFWorkbook workbook, XSSFFont font) {

        XSSFCellStyle headerCellStyle = getHeaderStyleSheet(workbook, font, new java.awt.Color(214, 220, 228));
        XSSFCell cell = row.createCell(col);
        switch (col) {
            case MANAGER_COLUMN:
                cell.setCellValue(engineerRecord.getManagerHandle());
                cell.setCellStyle(headerCellStyle);
                break;
            case ENGINEER_NAME_COLUMN:
                cell.setCellValue(engineerRecord.getEngineerHandle());
                cell.setCellStyle(headerCellStyle);
                break;
            case WORKLOAD_SIEBEL_SNOW_TOTAL_COLUMN:
            case WORK_LOAD_TOTAL_TICKET_SIE_SNOW_COLUMN:
                int srCount = engineerRecord.getListOfSRs() != null ? engineerRecord.getListOfSRs().size() : 0;
                int snowItemCount = engineerRecord.getListSnowItem() != null ? engineerRecord.getListSnowItem().size()
                        : 0;
                cell.setCellValue(srCount + snowItemCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SIEBEL_OUTAGE_COUNT_COLUMN:
            case REPEAT_SIEBEL_OUTAGE_NUMBER_COLUMN:
                int OutageSRCount = engineerRecord.getOutageSRList() != null ? engineerRecord.getOutageSRList().size()
                        : 0;
                cell.setCellValue(OutageSRCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SIEBEL_SBI_COUNT_COLUMN:
            case REPEAT_SIEBEL_SBI_NUMBER_COLUMN:
                int SbiSRCount = engineerRecord.getSbiSRList() != null ? engineerRecord.getSbiSRList().size() : 0;
                cell.setCellValue(SbiSRCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SIEBEL_BI_COUNT_COLUMN:
            case REPEAT_SIEBEL_BI_NUMBER_COLUMN:
                int biSRCount = engineerRecord.getBiSRList() != null ? engineerRecord.getBiSRList().size() : 0;
                cell.setCellValue(biSRCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SIEBEL_NSI_COUNT_COLUMN:
            case REPEAT_SIEBEL_NSI_NUMBER_COLUMN:
                int nsiSRCount = engineerRecord.getNsiSRList() != null ? engineerRecord.getNsiSRList().size() : 0;
                cell.setCellValue(nsiSRCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_CRITICAL_COUNT_COLUMN:
            case REPEAT_SNOW_CRTICIAL_COUNT_COLUMN:
                int SnowCriticalSRCount = engineerRecord.getListSnowCritical() != null
                        ? engineerRecord.getListSnowCritical().size()
                        : 0;
                cell.setCellValue(SnowCriticalSRCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_HIGH_COUNT_COLUMN:
            case REPEAT_SNOW_HIGH_COUNT_COLUMN:
                int SnowHighSRCount = engineerRecord.getListSnowHigh() != null ? engineerRecord.getListSnowHigh().size()
                        : 0;
                cell.setCellValue(SnowHighSRCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_MODERATE_COUNT_COLUMN:
            case REPEAT_SNOW_MODERATE_COUNT_COLUMN:
                int SnowModerateSRCount = engineerRecord.getListSnowModerate() != null
                        ? engineerRecord.getListSnowModerate().size()
                        : 0;
                cell.setCellValue(SnowModerateSRCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_LOW_COUNT_COLUMN:
            case REPEAT_SNOW_LOW_COUNT_COLUMN:
                int SnowLowCount = engineerRecord.getListSnowLow() != null ? engineerRecord.getListSnowLow().size() : 0;
                cell.setCellValue(SnowLowCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_ALARM_COUNT_COLUMN:
            case WORK_LOAD_SNOW_ALARMS_COLUMN:
                int SnowAlarmCount = engineerRecord.getListSnowAlarms() != null
                        ? engineerRecord.getListSnowAlarms().size()
                        : 0;
                cell.setCellValue(SnowAlarmCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_PROJECTS_COUNT_COLUMN:
            case WORK_LOAD_SNOW_PRJ_COLUMN:
                int SnowProjectCount = engineerRecord.getListSnowProjectTask() != null
                        ? engineerRecord.getListSnowProjectTask().size()
                        : 0;
                cell.setCellValue(SnowProjectCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_CHANGES_COUNT_COLUMN:
            case WORK_LOAD_SNOW_CHANGE_COLUMN:
                int SnowChangeCount = engineerRecord.getListSnowChange() != null
                        ? engineerRecord.getListSnowChange().size()
                        : 0;
                cell.setCellValue(SnowChangeCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_TASKS_COUNT_COLUMN:
            case WORK_LOAD_SNOW_TASK_COLUMN:
                int SnowChangeTaskCount = engineerRecord.getListSnowChangeTask() != null
                        ? engineerRecord.getListSnowChangeTask().size()
                        : 0;
                int SnowIncidentTaskCount = engineerRecord.getListSnowIncidentTask() != null
                        ? engineerRecord.getListSnowIncidentTask().size()
                        : 0;
                int SnowProblemTaskCount = engineerRecord.getListSnowProblemTask() != null
                        ? engineerRecord.getListSnowProblemTask().size()
                        : 0;

                cell.setCellValue(SnowChangeTaskCount + SnowIncidentTaskCount + SnowProblemTaskCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_PRB_COLUMN:
                int SnowProblemCount = engineerRecord.getListSnowProblem() != null
                        ? engineerRecord.getListSnowProblem().size()
                        : 0;

                cell.setCellValue(SnowProblemCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_INC_COLUMN:
                int incCount = engineerRecord.getListSnowIncident() != null
                        ? engineerRecord.getListSnowIncident().size()
                        : 0;

                cell.setCellValue(incCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_TOTAL_COUNT_COLUMN:
                int snowCount = engineerRecord.getListSnowItem() != null ? engineerRecord.getListSnowItem().size() : 0;

                cell.setCellValue(snowCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SIEBEL_NO_OF_SIEBEL_COLUMN:
                srCount = 0;
                srCount = engineerRecord.getListOfSRs() != null ? engineerRecord.getListOfSRs().size() : 0;
                cell.setCellValue(srCount);
                cell.setCellStyle(headerCellStyle);
                break;
            case SIEBEL_COMPLETION_TIME_COLUMN:
            case REPEAT_SIEBEL_COMPLETION_TIME_COLUMN:
            case WORKLOAD_SIEBEL_COMPLETION_TIME_COLUMN:
                cell.setCellValue(engineerRecord.getAvgCompletionTime());
                cell.setCellStyle(headerCellStyle);
                break;
//                List<SRRecord> srItem = engineerRecord.getListOfSRs() != null ? engineerRecord.getListOfSRs() : null;
//                float totalSRCount = 0;
//                float totalCompletionTime = 0;
//                if (srItem != null) {
//                    for (SRRecord srRecord : srItem) {
//                        totalCompletionTime += srRecord.getRcpt_to_comp();
//                        totalSRCount += 1;
//                    }
//                }
//
//                cell.setCellValue(totalSRCount > 0 ? (totalCompletionTime / totalSRCount) : 0f);
//                cell.setCellStyle(headerCellStyle);
//                break;
            case REPEAT_SIEBEL_RESOLUTION_TIME_COLUMN:
            case SIEBEL_RESOLTUION_TIME_COLUMN:
            case WORKLOAD_SIEBEL_RESOLTUION_TIME_COLUMN:
                cell.setCellValue(engineerRecord.getAvgResolutionTime());
                cell.setCellStyle(headerCellStyle);
                break;
//                srItem = engineerRecord.getListOfSRs() != null ? engineerRecord.getListOfSRs() : null;
//                totalSRCount = 0;
//                float totalResolutionTime = 0;
//                if (srItem != null) {
//                    for (SRRecord srRecord : srItem) {
//                        totalResolutionTime += srRecord.getRcpt_to_reso();
//                        totalSRCount += 1;
//                    }
//                }
//
//                cell.setCellValue(totalSRCount > 0 ? (totalResolutionTime / totalSRCount) : 0f);
//                cell.setCellStyle(headerCellStyle);
//                break;
            case REPEAT_SIEBEL_RESTORE_TIME_COLUMN:
            case SIEBEL_RESTORE_TIME_COLUMN:
            case COMPLIANCE_MEA_COLUMN:
                cell.setCellValue(engineerRecord.getMEAs());
                cell.setCellStyle(headerCellStyle);
                break;
            case UTILIZATION_TOTAL__COLUMN:
                cell.setCellValue(engineerRecord.getUtilizationHour());
                break;
            case WORKLOAD_SIEBEL_RESPONSE_TIME_COLUMN:
                cell.setCellValue(engineerRecord.getAvgResponseTime());
                break;
            case WORKLOAD_SIEBEL_RESTORATION_TIME_COLUMN:
                cell.setCellValue(engineerRecord.getAvgRestorationTime());
                cell.setCellStyle(headerCellStyle);
                break;
            case WORKLOAD_SNOW_RESOLUTION_AVG_COLUMN:
            case SNOW_RESOLUTON_COLUMN:
            case REPEAT_SNOW_RESOLTUION_TIME_COLUMN:
                List<SnowRecord> snowItem = engineerRecord.getListSnowItem() != null ? engineerRecord.getListSnowItem()
                        : null;
                float totalSnowCount = 0;
                float totalSnowTime = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        totalSnowTime += snowRecord.getResolutionTime();
                        totalSnowCount += 1;
                    }
                }

                cell.setCellValue(totalSnowCount > 0 ? (totalSnowTime / totalSnowCount) : 0f);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORKLOAD_SNOW_RESTORATION_AVG_COLUMN:
            case SNOW_RESTORE_COLUMN:
            case REPEAT_SNOW_RESTORE_TIME_COLUMN:
                snowItem = engineerRecord.getListSnowItem() != null ? engineerRecord.getListSnowItem() : null;
                totalSnowCount = 0;
                totalSnowTime = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        totalSnowTime += snowRecord.getRestorationTime();
                        totalSnowCount += 1;
                    }
                }

                cell.setCellValue(totalSnowCount > 0 ? (totalSnowTime / totalSnowCount) : 0f);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORKLOAD_SNOW_RESPONSE_AVG_COLUMN:
                snowItem = engineerRecord.getListSnowItem() != null ? engineerRecord.getListSnowItem() : null;
                totalSnowCount = 0;
                totalSnowTime = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        totalSnowTime += snowRecord.getResponseTime();
                        totalSnowCount += 1;
                    }
                }
                cell.setCellValue(totalSnowCount > 0 ? (totalSnowTime / totalSnowCount) : 0f);
                cell.setCellStyle(headerCellStyle);
                break;
            case SNOW_COMPLETION_COLUMN:
            case REPEAT_SNOW_COMPLETION_TIME_COLUMN:
                snowItem = engineerRecord.getListSnowItem() != null ? engineerRecord.getListSnowItem() : null;
                totalSnowCount = 0;
                totalSnowTime = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        totalSnowTime += snowRecord.getClosureTime();
                        totalSnowCount += 1;
                    }
                }
                cell.setCellValue(totalSnowCount > 0 ? (totalSnowTime / totalSnowCount) : 0f);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_CLEARED_ON_ACCESS_COLUMN:
                // Possible Status Reason
                // status_reason
                // Additional Information from Client
                // Administrative Support
                // Alarm(s) Cleared on Access
                // Approval from Client to Proceed
                // Approval to Proceed
                // Awaiting Change Request
                // Awaiting Draft Approval
                // Awaiting Success Confirmation
                // Change Request
                // Client Action Required
                // Customer Cancelled
                // Customer or Third Party Action
                // Customer/Location Inactive
                // Dispatch
                // Duplicate Incident
                // Escalation to Product House
                // Ignore Alarm
                // Manager Intervention
                // No Further Action Required
                // No Longer Required
                // Partially Successful
                // Patch / Upgrade
                // RCA Provided
                // Remote Access to Equipment
                // Repaired
                // Replaced
                // Request Created in Error
                // Requester Cancelled
                // Successful
                // Support Contact Hold
                // Test Alarm
                // Test Problem
                // Test Ticket
                // Third Party Vendor Action Required
                // Ticket Created in Error
                // To be Re-scheduled
                // Unable to Engage Client
                // Working as Designed

                int countResolution_snow_clearedOnAccess = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Alarm(s) Cleared on Access".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_clearedOnAccess++;
                    }
                }
                cell.setCellValue(countResolution_snow_clearedOnAccess);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_WORKING_AS_DESIGNED_COLUMN:
                int countResolution_snow_workingAsDesigned = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Working as Designed".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_workingAsDesigned++;
                    }
                }
                cell.setCellValue(countResolution_snow_workingAsDesigned);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_CHANGE_REQUEST_COLUMN:
                int countResolution_snow_changeRequest = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Change Request".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_changeRequest++;
                    }
                }
                cell.setCellValue(countResolution_snow_changeRequest);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_CUSTOMER_3RD_PARTY_COLUMN:
                int countResolution_snow_customerOr3rdParty = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Customer or Third Party Action".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_customerOr3rdParty++;
                    }
                }
                cell.setCellValue(countResolution_snow_customerOr3rdParty);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_PATCH_UPGRADE_COLUMN:
                int countResolution_snow_patchOrUpgrade = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Patch / Upgrade".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_patchOrUpgrade++;
                    }
                }
                cell.setCellValue(countResolution_snow_patchOrUpgrade);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_REPAIRED_COLUMN:
                int countResolution_snow_repaired = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Repaired".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_repaired++;
                    }
                }
                cell.setCellValue(countResolution_snow_repaired);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_CANCELLED_COLUMN:
                int countResolution_snow_cancelled = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Customer Cancelled".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_cancelled++;
                    }
                }
                cell.setCellValue(countResolution_snow_cancelled);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_DUPLICATE_COLUMN:
                int countResolution_snow_duplicate = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Duplicate Incident".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_duplicate++;
                    }
                }
                cell.setCellValue(countResolution_snow_duplicate);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_IGNORE_ALARM_COLUMN:
                int countResolution_snow_ignoreAlarm = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Ignore Alarm".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_ignoreAlarm++;
                    }
                }
                cell.setCellValue(countResolution_snow_ignoreAlarm);
                cell.setCellStyle(headerCellStyle);
                break;
            case WORK_LOAD_SNOW_TICKET_IN_ERROR_COLUMN:
                int countResolution_snow_ticketInError = 0;
                snowItem = engineerRecord.getListSnowAlarms() != null ? engineerRecord.getListSnowAlarms() : null;
                totalSnowCount = 0;
                if (snowItem != null) {
                    for (SnowRecord snowRecord : snowItem) {
                        if ("Ticket Created in Error".equalsIgnoreCase(snowRecord.getStatus_reason()))
                            countResolution_snow_ticketInError++;
                    }
                }
                cell.setCellValue(countResolution_snow_ticketInError);
                cell.setCellStyle(headerCellStyle);
                break;

            default:
                break;
        }
    }
}
