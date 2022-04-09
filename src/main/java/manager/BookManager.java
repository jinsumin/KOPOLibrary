package manager;

import assets.Const;
import dto.Book;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.Input;
import dto.Member;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalTime;

/**
 * Created by soomin on 2022/04/02
 */

public class BookManager implements ExcelManager {
    public static BookManager getInstance() {
        return new BookManager();
    }

    @Override
    public void add() throws Exception {
        String no = String.valueOf(LocalTime.now());
        String title = Input.getString("책 제목 : ");
        String author = Input.getString("저자 : ");
        String isBorrowed = "false";
        Book book = new Book(no, title, author, isBorrowed);
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            BookManager.getInstance().create();
            fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        }
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet(BOOK_SHEET);
        HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(book.getNo());
        row.createCell(1).setCellValue(book.getTitle());
        row.createCell(2).setCellValue(book.getAuthor());
        row.createCell(3).setCellValue(book.getIsBorrowed());
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH + FILE_NAME);
        workbook.write(fileOutputStream);
    }

    @Override
    public void delete() throws Exception {
        String title = Input.getString("책 제목 : ");
        String author = Input.getString("저자 : ");
        Book book = getBook(title, author);
        if (book == null) {
            System.out.println("책을 찾을 수 없습니다.");
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet(BOOK_SHEET);
        int rowIndex = sheet.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row.getCell(Const.MEMBER_CELL_NO_INDEX).getStringCellValue().equals(book.getNo())) {
                for (int j = 1; j < row.getLastCellNum(); j++) {
                    row.createCell(j).setCellValue((String) null);
                }
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH + FILE_NAME);
        workbook.write(fileOutputStream);
    }

    private Book getBook(String title, String author) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet(BOOK_SHEET);
        Book book = new Book();
        int rowIndex = sheet.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row.getCell(Const.BOOK_CELL_TITLE_INDEX).getStringCellValue().equals(title)
                    && row.getCell(Const.BOOK_CELL_AUTHOR_INDEX).getStringCellValue().equals(author)) {
                book.setNo(row.getCell(Const.BOOK_CELL_NO_INDEX).getStringCellValue());
                book.setTitle(row.getCell(Const.BOOK_CELL_TITLE_INDEX).getStringCellValue());
                book.setAuthor(row.getCell(Const.BOOK_CELL_AUTHOR_INDEX).getStringCellValue());
                book.setIsBorrowed(row.getCell(Const.BOOK_CELL_IS_BORROWED_INDEX).getStringCellValue());
                return book;
            }
        }
        return null;
    }

    public void borrowBook(Member member) throws Exception {
        String title = Input.getString("책 제목 : ");
        String author = Input.getString("저자 : ");
        if (isAvailableToBorrow(title, author)) {
            System.out.println("대여 시작");
            MemberManager memberManager = MemberManager.getInstance();
            memberManager.updateMember(member, getBook(title, author));
        }
    }

    public void returnBook(Member member) throws Exception {
        String title = Input.getString("책 제목 : ");
        String author = Input.getString("저자 : ");
        if (isAvailableToReturn(title, author, member)) {
            MemberManager memberManager = MemberManager.getInstance();
            memberManager.updateMember(member, getBook(title, author));
        }
    }

    private boolean isAvailableToReturn(String title, String author, Member member) throws Exception {
        Book book = getBook(title, author);
        if (book == null) {
            System.out.println("없는 책 입니다.");
            return false;
        }
        return member.getBorrowList().contains(book.getNo());
    }

    private boolean isAvailableToBorrow(String title, String author) throws Exception {
        Book book = getBook(title, author);
        if (book == null) {
            System.out.println("없는 책 입니다.");
            return false;
        }
        return book.getIsBorrowed().equals("false");
    }
}
