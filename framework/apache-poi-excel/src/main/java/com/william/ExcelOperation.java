package com.william;

import com.google.common.collect.Lists;
import com.william.model.AgentTicketReport;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by william on 17-3-30.
 */
public class ExcelOperation {

    public static void main(String[] args) {
        List<AgentTicketReport> reportList = Lists.newArrayList();
        AgentTicketReport ticketReport = new AgentTicketReport();
        ticketReport.setUsername("张三");
        ticketReport.setMobile("12137838557");
        ticketReport.setProcessDuration("2017-09-01至2017-09-31");
        ticketReport.setProcessCount(100);
        ticketReport.setOnceSolvedRate(0.70D);
        ticketReport.setOnceUnconfirmCount(10);
        ticketReport.setUntracedCount(5);
        ticketReport.setReprocessUnconfirmCount(5);
        ticketReport.setReconfirmSolvedCount(5);
        ticketReport.setReconfirmUnsolvedCount(3);
        ticketReport.setReconfirmUnconnectCount(2);
        ticketReport.setTotalSolvedRate(0.75D);
        reportList.add(ticketReport);

        try {
            String filePath = "/home/william/workspaces/william-github/notes/frameworks/apache-poi-excel/src/main/resources/XLSTest_01.xls";                 //文件路径
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = createSheetWithHeader(workbook);
            appendSheetBody(sheet, reportList);
            autoAdjustRowColumSize(sheet);

            FileOutputStream out = new FileOutputStream(filePath);
            workbook.write(out);
            out.close();
            System.out.println("OK!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setRowValues(HSSFRow row, AgentTicketReport ticketReport) {
        row.createCell(0).setCellValue(ticketReport.getUsername());
        row.createCell(1).setCellValue(ticketReport.getMobile());
        row.createCell(2).setCellValue(ticketReport.getProcessDuration());
        row.createCell(3).setCellValue(ticketReport.getProcessCount());
        row.createCell(4).setCellValue(ticketReport.getOnceSolvedRate());
        row.createCell(5).setCellValue(ticketReport.getOnceUnconfirmCount());
        row.createCell(6).setCellValue(ticketReport.getUntracedCount());
        row.createCell(7).setCellValue(ticketReport.getReprocessUnconfirmCount());
        row.createCell(8).setCellValue(ticketReport.getReconfirmSolvedCount());
        row.createCell(9).setCellValue(ticketReport.getReconfirmUnsolvedCount());
        row.createCell(10).setCellValue(ticketReport.getReconfirmUnconnectCount());
        row.createCell(11).setCellValue(ticketReport.getTotalSolvedRate());
    }

    private static void appendSheetBody(HSSFSheet sheet, List<AgentTicketReport> ticketReportList) {
        if(ticketReportList.size() == 0) {
            return;
        }
        int startRow = sheet.getLastRowNum() + 1;
        int appendLength = ticketReportList.size();
        for (int i = 0; i < appendLength; i++) {
            setRowValues(sheet.createRow(startRow + i), ticketReportList.get(i));
        }
    }

    private static HSSFSheet createSheetWithHeader(HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.createSheet();
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints(Integer.valueOf(10).shortValue());
        cellStyle.setFont(font);

        HSSFRow row_0 = sheet.createRow(0);
        HSSFRow row_1 = sheet.createRow(1);
        HSSFRow row_2 = sheet.createRow(2);

        HSSFCell cell0 = row_0.createCell(0);
            cell0.setCellValue("姓名");
            cell0.setCellStyle(cellStyle);
        CellRangeAddress row0_2col0 = new CellRangeAddress(0, 2, 0, 0);
        sheet.addMergedRegion(row0_2col0);//行合并

        HSSFCell cell1 = row_0.createCell(1);
            cell1.setCellValue("电话");
            cell1.setCellStyle(cellStyle);
        CellRangeAddress row0_2col1 = new CellRangeAddress(0, 2, 1, 1);
        sheet.addMergedRegion(row0_2col1);

        HSSFCell cell2 = row_0.createCell(2);
            cell2.setCellValue("时间段");
            cell2.setCellStyle(cellStyle);
        CellRangeAddress row0_2col2 = new CellRangeAddress(0, 2, 2, 2);
        sheet.addMergedRegion(row0_2col2);

        HSSFCell cell3 = row_0.createCell(3);
            cell3.setCellValue("响应工单数");
            cell3.setCellStyle(cellStyle);
        CellRangeAddress row0_2col3 = new CellRangeAddress(0, 2, 3, 3);
        sheet.addMergedRegion(row0_2col3);

        HSSFCell cell4 = row_0.createCell(4);
            cell4.setCellValue("一次解决率");
            cell4.setCellStyle(cellStyle);
        CellRangeAddress row0_2col4 = new CellRangeAddress(0, 2, 4, 4);
        sheet.addMergedRegion(row0_2col4);

        HSSFCell cell5 = row_0.createCell(5);
            cell5.setCellValue("已处理待确认");
            cell5.setCellStyle(cellStyle);
        CellRangeAddress row0_2col5 = new CellRangeAddress(0, 2, 5, 5);
        sheet.addMergedRegion(row0_2col5);

        HSSFCell cell6 = row_0.createCell(6);
            cell6.setCellValue("需跟进");
            cell6.setCellStyle(cellStyle);
        CellRangeAddress col6_10row0 = new CellRangeAddress(0, 0, 6, 10);
        sheet.addMergedRegion(col6_10row0);//列合并

        HSSFCell cell6_10 = row_1.createCell(6);
            cell6_10.setCellValue("没跟进");
            cell6_10.setCellStyle(cellStyle);
        CellRangeAddress row1_2col6 = new CellRangeAddress(1, 2, 6, 6);
        sheet.addMergedRegion(row1_2col6);

        HSSFCell cell7 = row_1.createCell(7);
            cell7.setCellValue("待二次确认");
            cell7.setCellStyle(cellStyle);
        CellRangeAddress row1_2col7 = new CellRangeAddress(1, 2, 7, 7);
        sheet.addMergedRegion(row1_2col7);

        HSSFCell cell8 = row_1.createCell(8);
            cell8.setCellValue("已跟进且已二次确认");
            cell8.setCellStyle(cellStyle);
        CellRangeAddress col8_10row1 = new CellRangeAddress(1, 1, 8, 10);
        sheet.addMergedRegion(col8_10row1);

        HSSFCell cell9 = row_2.createCell(8);
            cell9.setCellValue("确认已解决");
            cell9.setCellStyle(cellStyle);

        HSSFCell cell10 = row_2.createCell(9);
            cell10.setCellValue("确认未解决");
            cell10.setCellStyle(cellStyle);

        HSSFCell cell11 = row_2.createCell(10);
            cell11.setCellValue("未联系");
            cell11.setCellStyle(cellStyle);

        HSSFCell cell12 = row_0.createCell(11);
            cell12.setCellValue("解决率");
            cell12.setCellStyle(cellStyle);
        CellRangeAddress row0_2col11 = new CellRangeAddress(0, 2, 11, 11);
        sheet.addMergedRegion(row0_2col11);

        return sheet;
    }

    private static void autoAdjustRowColumSize(HSSFSheet sheet) {
        int rowLength = sheet.getLastRowNum() + 1;
        for (int i = 0; i < rowLength; i++) {
            sheet.getRow(i).setHeightInPoints(Integer.valueOf(15).shortValue());
            sheet.autoSizeColumn(i, true);
        }
    }
}
