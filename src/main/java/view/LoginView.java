package view;

import manager.MemberManager;
import util.Input;

import static assets.Const.*;

/**
 * Created by soomin on 2022/04/02
 */

public class LoginView {
    public void process() throws Exception {
        int type = DEFAULT;
        while (type != EXIT) {
            type = Input.getInt("1.로그인, 2.회원가입 0.나가기");
            if (type == LOGIN) {
                type = logIn();
            }
            if (type == SIGNUP) {
                type = signUp();
            }
        }
    }

    private int logIn() throws Exception {
        String id = Input.getString("아이디 : ");
        String password = Input.getString("비밀번호 : ");
        MemberManager memberManager = MemberManager.getInstance();
        if (memberManager.isMatched(id, password)) {
            System.out.println("로그인 성공");
            return new MainView().process();
        } else {
            System.out.println("없는 아이디 이거나 비밀번호가 다릅니다.");
        }
        return EXIT;
    }

    private int signUp() throws Exception {
        String id = Input.getString("등록할 관리자 아이디 입력 : ");
        MemberManager memberManager = MemberManager.getInstance();
        if (!memberManager.isContains(id)) {
            memberManager.add();
            return new MainView().process();
        }
        System.out.println("이미 등록된 아이디 입니다.");
        return EXIT;
    }
}
