package manager;

import assets.Const;
import dto.Book;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.Input;
import dto.Member;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by soomin on 2022/04/02
 */

public class MemberManager implements ExcelManager {
    public static MemberManager getInstance() {
        return new MemberManager();
    }

    @Override
    public void add() throws Exception {
        String no = String.valueOf(LocalTime.now());
        String id = Input.getString("아이디 입력 : ");
        String password = Input.getString("비밀번호 입력 : ");
        String name = Input.getString("이름 입력 : ");
        String phoneNumber = Input.getString("핸드폰번호 입력 : ");
        String permissions = Input.getString("0.관리자권한 1.일반권한 설정 : ");
        Member member = new Member(no, id, password, name, phoneNumber, permissions, new ArrayList<String>());
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            MemberManager.getInstance().create();
            fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        }
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet(MEMBER_SHEET);
        HSSFRow row = sheet.createRow(sheet.getLastRowNum() + 1);
        row.createCell(0).setCellValue(member.getNo());
        row.createCell(1).setCellValue(member.getId());
        row.createCell(2).setCellValue(member.getPassword());
        row.createCell(3).setCellValue(member.getName());
        row.createCell(4).setCellValue(member.getPhoneNumber());
        row.createCell(5).setCellValue(member.getPermissions());
        row.createCell(6).setCellValue(member.getBorrowList().toString());
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH + FILE_NAME);
        workbook.write(fileOutputStream);
    }

    @Override
    public void delete() throws Exception {
        String id = Input.getString("아이디 : ");
        String password = Input.getString("비밀번호 : ");
        Member member = getMember(id, password);
        if (member == null) {
            System.out.println("사용자를 찾을 수 없습니다.");
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet(MEMBER_SHEET);
        int rowIndex = sheet.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row.getCell(Const.MEMBER_CELL_NO_INDEX).getStringCellValue().equals(member.getNo())) {
                for (int j = 1; j < row.getLastCellNum(); j++) {
                    row.createCell(j).setCellValue((String) null);
                }
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH + FILE_NAME);
        workbook.write(fileOutputStream);
    }

    public Member getMember(String id, String password) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet(MEMBER_SHEET);
        Member member = new Member();
        int rowIndex = sheet.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row.getCell(Const.MEMBER_CELL_ID_INDEX).getStringCellValue().equals(id)
                    && row.getCell(Const.MEMBER_CELL_PW_INDEX).getStringCellValue().equals(password)) {
                member.setNo(row.getCell(Const.MEMBER_CELL_NO_INDEX).getStringCellValue());
                member.setId(row.getCell(Const.MEMBER_CELL_ID_INDEX).getStringCellValue());
                member.setName(row.getCell(Const.MEMBER_CELL_NAME_INDEX).getStringCellValue());
                member.setPassword(row.getCell(Const.MEMBER_CELL_PW_INDEX).getStringCellValue());
                member.setPhoneNumber(row.getCell(Const.MEMBER_CELL_PHONE_INDEX).getStringCellValue());
                member.setPermissions(row.getCell(Const.MEMBER_CELL_PERMISSION_INDEX).getStringCellValue());
                member.setBorrowList(getArrayListOfBookNo(row.getCell(Const.MEMBER_CELL_BORROW_LIST_INDEX).getStringCellValue()));
                return member;
            }
        }
        return null;
    }

    private ArrayList<String> getArrayListOfBookNo(String stringCellValue) {
        ArrayList<String> arrayList = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(stringCellValue.substring(1, stringCellValue.length() - 1), ", ");
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        return arrayList;
    }

    public boolean isContains(String id) throws Exception {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            MemberManager.getInstance().create();
            fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        }
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet(MEMBER_SHEET);
        int rowIndex = sheet.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row.getCell(Const.MEMBER_CELL_ID_INDEX).getStringCellValue().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean isMatched(String id, String password) throws Exception {
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            MemberManager.getInstance().create();
            fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        }
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheet = workbook.getSheet(MEMBER_SHEET);
        int rowIndex = sheet.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) {
            HSSFRow row = sheet.getRow(i);
            if (row.getCell(Const.MEMBER_CELL_ID_INDEX).getStringCellValue().equals(id)
                    && row.getCell(Const.MEMBER_CELL_PW_INDEX).getStringCellValue().equals(password)) {
                return true;
            }
        }
        System.out.println("회원 정보 매칭 실패");
        return false;
    }

    public void updateMember(Member member, Book book) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH + FILE_NAME);
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        HSSFSheet sheetOfMember = workbook.getSheet(MEMBER_SHEET);
        int rowIndex = sheetOfMember.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) {
            HSSFRow row = sheetOfMember.getRow(i);
            if (row.getCell(Const.MEMBER_CELL_NO_INDEX).getStringCellValue().equals(member.getNo())) {
                String bookList = row.getCell(Const.MEMBER_CELL_BORROW_LIST_INDEX).getStringCellValue();
                ArrayList<String> bookArrayList = getArrayListOfBookNo(bookList);
                if (bookArrayList.contains(book.getNo())) {
                    bookArrayList.remove(book.getNo());
                } else {
                    bookArrayList.add(book.getNo());
                }
                row.createCell(Const.MEMBER_CELL_BORROW_LIST_INDEX).setCellValue(bookArrayList.toString());
            }
        }
        HSSFSheet sheetOfBook = workbook.getSheet(BOOK_SHEET);
        rowIndex = sheetOfBook.getLastRowNum();
        for (int i = 1; i <= rowIndex; i++) {
            HSSFRow row = sheetOfBook.getRow(i);
            if (row.getCell(Const.BOOK_CELL_NO_INDEX).getStringCellValue().equals(book.getNo())) {
                String isBorrowed;
                if (row.getCell(Const.BOOK_CELL_IS_BORROWED_INDEX).getStringCellValue().equals("true")) {
                    isBorrowed = "false";
                } else {
                    isBorrowed = "true";
                }
                row.createCell(Const.BOOK_CELL_IS_BORROWED_INDEX).setCellValue(isBorrowed);
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH + FILE_NAME);
        workbook.write(fileOutputStream);
    }
}
