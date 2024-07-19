package vo;

import dto.Board;
import java.util.ArrayList;

public interface SearchBoard extends BoardOutput {
  public ArrayList<Board> searchBoard(String bno);

}
