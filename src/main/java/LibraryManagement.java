import util.Input;
import view.LoginView;

import static assets.Const.*;

/**
 * Created by soomin on 2022/04/02
 */

public class LibraryManagement {
    public static void run() throws Exception {
        int type = DEFAULT;
        while (type != EXIT) {
            type = Input.getInt("입력하세요. 1.프로그램 실행, 0.프로그램 종료");
            if (type == LOGIN) {
                LoginView view = new LoginView();
                view.process();
            }
        }
        System.exit(EXIT);
    }
}
