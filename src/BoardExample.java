import dto.Board;
import java.sql.SQLException;
import java.util.List;
import vo.BoardManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BoardExample {
  static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
  static BoardManager boardManager = BoardManager.getInstance();
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) throws IOException {
    BoardExample Instance = new BoardExample();

    while (true) {
      Instance.mainMenu();
      String sel = br.readLine();

      switch (sel) {
        case "1" ->
          Instance.create();
        case "2" ->
          Instance.read();
        case "3" ->
          Instance.clear();
        case "4" ->
          System.exit(0);
        default -> System.out.println("다시 입력해주세요");
      }
      break;
    }
  }

  public void mainMenu() throws IOException{
    list();
    System.out.println("----------------------------------------------");
    System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
    System.out.print("메뉴 선택: ");
  }

  public void list() throws IOException{
    List<Board> boards = boardManager.getBoardList();
    System.out.println("[게시물 목록]");
    System.out.println("--------------------------------------------------");
    System.out.println("no\t\twriter\t\t\tdate\t\t\t\ttitle");
    System.out.println("--------------------------------------------------");
    for (Board board : boards) {
      System.out.printf("%d\t\t%s\t\t%s\t\t%s\n", board.getBno(), board.getBwriter(), dateFormat.format(board.getBdate()), board.getBtitle());
    }

    }

  public void create() throws IOException {
    System.out.println("[새 게시물 입력]");
    System.out.print("제목 : ");
    String btitle = br.readLine();
    System.out.print("내용 : ");
    String bcontent = br.readLine();
    System.out.print("작성자 : ");
    String bwriter = br.readLine();
    Date bdate = new Date();
    Board board = new Board(0, btitle, bcontent, bwriter, bdate);
    System.out.println("----------------------------------------------------");
    System.out.println("보조메뉴 : 1.Ok | 2.Cancel");
    System.out.print("메뉴 선택 : ");

    while (true){
      String selnum = br.readLine();

      switch (selnum) {
        case "1" -> {
          boardManager.insertBoard(board);
          return;
        }
        case "2" -> {
          break;
        }
        default -> System.out.println("다시 입력해주세요");
      }
    }
  }

  public void read() throws IOException {
    System.out.println("[게시물 읽기]");
    System.out.print("bno : ");
    String bno = br.readLine();

    System.out.println("=======================");
    List<Board> boards = boardManager.searchBoard(bno);
    if (!boards.isEmpty()) {
      for (Board board : boards) {
        System.out.println("번호: " + board.getBno());
        System.out.println("제목: " + board.getBtitle());
        System.out.println("내용: " + board.getBcontent());
        System.out.println("작성자: " + board.getBwriter());
        System.out.println("날짜: " + dateFormat.format(board.getBdate()));
        System.out.println("=======================");
        subMenu(board);
      }
    } else {
      System.out.println("없는 게시물입니다. 다른 번호를 선택해주세요.");
    }
  }

  public void subMenu(Board board) throws IOException {
    System.out.println("보조메뉴 : 1.Update | 2.Delete | 3.List");
    System.out.print("메뉴 선택 : ");
    String sel = br.readLine();

    switch (sel) {
      case "1" ->
          update(board);
      case "2" ->
          delete(board);
      case "3" -> {
        return;
      }
    }
  }

  public void update(Board board) throws IOException {
    System.out.println("[수정 내용 입력]");
    System.out.print("제목 : ");
    String btitle = br.readLine();
    System.out.print("내용 : ");
    String bcontent = br.readLine();
    System.out.print("작성자 : ");
    String bwriter = br.readLine();

    board.setBtitle(btitle);
    board.setBcontent(bcontent);
    board.setBwriter(bwriter);
    board.setBdate(new Date());

    try {
      boardManager.updateBoard(board);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void delete(Board board) {
    try {
      boardManager.deleteBoard(board.getBno());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void clear() {
    try {
      boardManager.clearBoards();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

}


