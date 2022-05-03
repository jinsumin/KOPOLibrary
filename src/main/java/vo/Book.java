package vo;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by soomin on 2022/04/02
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    private String no;
    private String title;
    private String author;
    private String isBorrowed;
}
