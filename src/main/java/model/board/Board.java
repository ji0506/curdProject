package model.board;

import java.util.Date;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Board {
	private int bno;
	private String btitle;
	private String bcontent;
	private String bwritter;
	private Date bdate;


}
