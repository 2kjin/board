package dto;

import java.util.Date;
import lombok.Data;

@Data
public class Board {

  private int bno;
  private String btitle;
  private String bcontent;
  private String bwriter;
  private Date bdate;

  public Board(int bno, String btitle, String bcontent, String bwriter, Date bdate) {
    this.bno = bno;
    this.btitle = btitle;
    this.bcontent = bcontent;
    this.bwriter = bwriter;
    this.bdate = bdate;
  }
}
