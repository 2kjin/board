import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class BoardExample_collection {
  static Scanner sc = new Scanner(System.in);
  List<Map<String, Object>> BoardList = new ArrayList<>();
  static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
  static int bid = 1;

  public static void main(String[] args) throws IOException {
    BoardExample_collection Instance = new BoardExample_collection();

    while (true) {
      Instance.mainMenu();
      int nSel = System.in.read() - 48;
      System.in.skip(System.in.available());

      switch (nSel) {
        case 1:
          Instance.create();
          continue;
        case 2:
          Instance.read();
          continue;
        case 3:
          Instance.clear();
          continue;
        case 4:
          System.exit(0);
          break;
        default:
          continue;
      }
      break;
    }
  }


  public void mainMenu() {
    list();
    // 게시물 목록 출력
    System.out.println("----------------------------------------------");
    System.out.println("메인 메뉴: 1.Create | 2.Read | 3.Clear | 4.Exit");
    System.out.print("메뉴 선택: ");
  }

  public void list() {

    System.out.println("[게시물 목록]");
    System.out.println("--------------------------------------------------");
    System.out.println("no\t\twriter\t\t\tdate\t\t\t\ttitle");
    System.out.println("--------------------------------------------------");
    for (Map<String, Object> b : BoardList) {
      System.out.printf("%d\t\t\t",b.get("bid"));
      System.out.printf("%s\t\t\t",b.get("bwriter"));
      Object date = b.get("date");
      System.out.print(dateFormat.format(date)+"\t\t");
      System.out.printf("%s\n",b.get("btitle"));

    }
  }

  public void create() throws IOException {
    System.out.println("[새 게시물 입력]");
    Map<String, Object> book = new LinkedHashMap<>();
    System.out.print("제목 : ");
    book.put("btitle",sc.nextLine());
    System.out.print("내용 : ");
    book.put("bcontent",sc.nextLine());
    System.out.print("작성자 : ");
    book.put("bwriter",sc.nextLine());
    book.put("date",new Date());
    book.put("bid",bid++);
    BoardList.add(book);
    System.out.println("----------------------------------------------------");
    System.out.println("보조메뉴 : 1.Ok | 2.Cancel");
    System.out.print("메뉴 선택 : ");

  }


  public void read() throws IOException {
    System.out.println("[게시물 읽기]");
    System.out.print("bno : ");
    int id = Integer.parseInt(sc.nextLine());

    System.out.println("=======================");
    if (id == 0) return;
    Optional<Map<String, Object>> selectedPost = BoardList.stream()
        .filter(post -> post.get("bid").equals(id))
        .findFirst();
    if (selectedPost.isPresent()) {
      Map<String, Object> post = selectedPost.get();
      System.out.println("번호: " + post.get("bno"));
      System.out.println("제목: " + post.get("btitle"));
      System.out.println("내용: " + post.get("bcontent"));
      System.out.println("작성자: " + post.get("bwriter"));
      Date date = (Date) post.get("date");
      System.out.println("날짜: " + dateFormat.format(date));
      System.out.println("=======================");
      subMenu(post);
    } else {
      System.out.println("없는 게시물입니다. 다른 번호를 선택해주세요.");
    }
  }

  public void subMenu(Map<String, Object> post) throws IOException {
    System.out.println("보조메뉴 : 1.Update | 2.Delete | 3.List");
    System.out.print("메뉴 선택 : ");
    int option = Integer.parseInt(sc.nextLine());
    switch (option) {
      case 1:
        update(post);
        break;
      case 2:
        delete(post);
        break;
      case 3:
        return;
    }
  }

  public void update(Map<String, Object> post) throws IOException {
    System.out.println("[수정 내용 입력]");
    System.out.print("제목 : ");
    post.put("btitle", sc.nextLine());
    System.out.print("내용 : ");
    post.put("bcontent", sc.nextLine());
    System.out.print("작성자 : ");
    post.put("bwriter", sc.nextLine());
    post.put("date", new Date());
  }

  public void delete(Map<String, Object> post) {
    BoardList.remove(post);
  }

  public void clear() {
    BoardList.clear();
  }

}


