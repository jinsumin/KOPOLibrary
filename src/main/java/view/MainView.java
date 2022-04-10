package view;

import manager.BookManager;
import manager.MemberManager;
import util.Input;
import dto.Member;

import static assets.Const.EXIT;

/**
 * Created by soomin on 2022/04/02
 */

public class MainView {
    public int process() throws Exception {
        MemberManager memberManager = MemberManager.getInstance();
        BookManager bookManager = BookManager.getInstance();
        while (true) {
            int type = Input.getInt("1.멤버추가, 2.멤버삭제 3.도서추가 4.도서삭제 5.도서대여 6.도서반납 0.나가기");
            switch (type) {
                case 1 -> memberManager.add();
                case 2 -> memberManager.delete();
                case 3 -> bookManager.add();
                case 4 -> bookManager.delete();
                case 5 -> {
                    Member member = findMember();
                    if (member != null) {
                        bookManager.borrowBook(member);
                    }
                }
                case 6 -> {
                    Member member = findMember();
                    if (member != null) {
                        bookManager.returnBook(member);
                    }
                }
                case 0 -> {
                    return logOut();
                }
            }
        }
    }

    private Member findMember() throws Exception {
        String id = Input.getString("회원 아이디 : ");
        String password = Input.getString("회원 비밀번호 : ");
        MemberManager memberManager = MemberManager.getInstance();
        if (memberManager.isMatched(id, password)) {
            return memberManager.getMember(id, password);
        } else {
            System.out.println("없는 아이디 이거나 비밀번호가 다릅니다.");
        }
        return null;
    }

    private int logOut() {
        return EXIT;
    }
}
