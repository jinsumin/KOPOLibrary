package manager;

import assets.Const;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileOutputStream;

/**
 * Created by soomin on 2022/04/09
 */

public interface ExcelManager {
    String FILE_PATH = "/Users/soomin/Documents/Projects/KopoLibrary3/src/main/java/records/";
    String FILE_NAME = "KOPOLibrary.xls";
    String MEMBER_SHEET = "member";
    String BOOK_SHEET = "book";

    default void create() throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheetOfBook = workbook.createSheet(BOOK_SHEET);
        HSSFRow rowOfBook = sheetOfBook.createRow(0);
        rowOfBook.createCell(0).setCellValue("No.");
        rowOfBook.createCell(1).setCellValue("TITLE");
        rowOfBook.createCell(2).setCellValue("AUTHOR");
        rowOfBook.createCell(3).setCellValue("IS BORROWED");

        HSSFSheet sheetOfMember = workbook.createSheet(MEMBER_SHEET);
        HSSFRow rowOfMember = sheetOfMember.createRow(0);
        rowOfMember.createCell(0).setCellValue("No.");
        rowOfMember.createCell(1).setCellValue("ID");
        rowOfMember.createCell(2).setCellValue("PW");
        rowOfMember.createCell(3).setCellValue("NAME");
        rowOfMember.createCell(4).setCellValue("PHONE NUMBER");
        rowOfMember.createCell(5).setCellValue("PERMISSION");
        rowOfMember.createCell(6).setCellValue("BORROWED LISTS");

        FileOutputStream out = new FileOutputStream(FILE_PATH + FILE_NAME);
        workbook.write(out);
        out.close();
    }

    void add() throws Exception;

    void delete() throws Exception;
}
