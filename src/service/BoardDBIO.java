package service;

import dto.Board;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import vo.BoardIO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public abstract class BoardDBIO extends ObjectDBIO implements BoardIO {

  public ArrayList<Board> getBoardList(){
    ArrayList<Board> boardlist = new ArrayList<Board>();
    String query = "SELECT * FROM board";
    ResultSet rs = null;

    rs = super.execute(query,rs);

    try{
      rs = super.execute(query, rs);
      while (rs.next()){
        int bno = rs.getInt(1);
        String btitle = rs.getString("btitle");
        String bcontent = rs.getString("bcontent");
        String bwriter = rs.getString("bwriter");
        Date bdate = rs.getDate("bdate");
        Board board = new Board(bno,btitle,bcontent,bwriter,bdate);
        boardlist.add(board);
      }
      rs.close();
      super.close();
    }catch (SQLException e){
      System.err.println(e.getMessage());
    }
    return boardlist;
  }

  public boolean insertBoard(Board board){

    int bno = board.getBno();
    String btitle = board.getBtitle();
    String bcontent = board.getBcontent();
    String bwriter = board.getBwriter();
    Date bdate = board.getBdate();

    String query = "INSERT INTO student VALUES ('" +
                    bno + "', '"       +
                      btitle + "', '" +
                        bcontent + "', '" +
                          bwriter + "', '" +
                            bdate + ")" ;
        super.execute(query);
    return true;
  }
  public ArrayList<Board> searchBoard(String bno) {
    ArrayList<Board> boardList = new ArrayList<>();
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
      conn = open();
      String sql = "SELECT * FROM board WHERE bno = ?";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, bno);

      rs = pstmt.executeQuery();

      if (rs.next()) {
        Board board = new Board(
            rs.getInt("bno"),
            rs.getString("btitle"),
            rs.getString("bcontent"),
            rs.getString("bwriter"),
            rs.getDate("bdate")
        );
        boardList.add(board);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    } finally {
      try {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (conn != null) close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }

    return boardList;
  }

  public boolean updateBoard(Board board) throws SQLException {
    Connection conn = null;
    PreparedStatement pstmt = null;
    boolean result = false;
    try {
      conn = open();
      String sql = "UPDATE board SET btitle = ?, bcontent = ?, bwriter = ?, bdate = ? WHERE bno = ?";
      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, board.getBtitle());
      pstmt.setString(2, board.getBcontent());
      pstmt.setString(3, board.getBwriter());
      pstmt.setDate(4, new java.sql.Date(board.getBdate().getTime()));
      pstmt.setInt(5, board.getBno());

      int count = pstmt.executeUpdate();
      result = count > 0;
    } finally {
      if (pstmt != null) pstmt.close();
      if (conn != null) close();
    }
    return result;
  }

  public boolean deleteBoard(int bno) throws SQLException {
    Connection conn = null;
    PreparedStatement pstmt = null;
    boolean result = false;
    try {
      conn = open();
      String sql = "DELETE FROM board WHERE bno = ?";
      pstmt = conn.prepareStatement(sql);

      pstmt.setInt(1, bno);

      int count = pstmt.executeUpdate();
      result = count > 0;
    } finally {
      if (pstmt != null) pstmt.close();
      if (conn != null) close();
    }
    return result;
  }

  public boolean clearBoards() throws SQLException {
    Connection conn = null;
    Statement stmt = null;
    boolean result = false;
    try {
      conn = open();
      String sql = "DELETE FROM board";
      stmt = conn.createStatement();

      int count = stmt.executeUpdate(sql);
      result = count > 0;
    } finally {
      if (stmt != null) stmt.close();
      if (conn != null) close();
    }
    return result;
  }

}
