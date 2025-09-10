package jdbc_boards.vo;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Board {
private int bno;
private String btitle;
private String bcontent;
private String bwriter;
private Date bdate;
}
