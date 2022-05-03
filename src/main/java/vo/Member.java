package vo;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Created by soomin on 2022/04/02
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Member {
    private String no;
    private String id;
    private String password;
    private String name;
    private String phoneNumber;
    private String permissions;
    private ArrayList<String> borrowList;
}
