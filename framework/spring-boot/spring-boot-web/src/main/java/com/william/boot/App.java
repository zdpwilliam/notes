package com.william.boot;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello world!
 */
@Controller
@EnableAutoConfiguration
public class App {

  @RequestMapping("/download")
  public ResponseEntity<byte[]> download() throws IOException {
    Workbook wb = new HSSFWorkbook();
    Sheet sh = wb.createSheet();
    for (int rownum = 0; rownum < 50; rownum++) {
      Row row = sh.createRow(rownum);
      for (int cellnum = 0; cellnum < 30; cellnum++) {
        Cell cell = row.createCell(cellnum);
        CellReference cr = new CellReference(cell);
        String address = cr.formatAsString();
        cell.setCellValue(address + "row:" + cr.getRow() + " col:" + cr.getRow());
      }
    }

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try {
      wb.write(out);
    } catch (IOException e) {
      e.printStackTrace();
    }
    HttpHeaders headers = new HttpHeaders();
    String fileName = new String("测试.xls".getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
    headers.setContentDispositionFormData("attachment", fileName);
    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
    ResponseEntity<byte[]> filebyte = new ResponseEntity<byte[]>(out.toByteArray(), headers,
        HttpStatus.CREATED);
    try {
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return filebyte;
  }

  @RequestMapping("/")
  @ResponseBody
  String home() {
    return "Hello World!";
  }

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }
}
