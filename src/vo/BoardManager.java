package vo;

import lombok.Getter;
import service.BoardDBIO;

public class BoardManager extends BoardDBIO {

  @Getter
  private static final BoardManager instance = new BoardManager();

  private BoardManager() {
  }

}
