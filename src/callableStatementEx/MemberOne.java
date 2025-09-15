package callableStatementEx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import util.DBUtil;

public class MemberOne {

  static Connection conn = DBUtil.getConnection();


  public static void main(String[] args) {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String sql = "{CALL SP_MEMBER_ONE()}";
    String v_userid = null;
    try {
      System.out.print("조회할 회원의 ID를 입력하세요: ");
      v_userid = br.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }

    try (CallableStatement call = conn.prepareCall(sql)) {
      call.setString(1, v_userid);
      try (ResultSet rs = call.executeQuery()) {
        if (rs.next()) {
          int seq = rs.getInt("m_seq");
          String userId = rs.getString("m_userid");
          String email = rs.getString("m_email");
          String hp = rs.getString("m_hp");
          Timestamp registDate = rs.getTimestamp("m_registdate");
          int point = rs.getInt("m_point");

          System.out.printf("%-5d %-15s %-25s %-20s %-20s %d\n",
              seq, userId, email, hp, registDate.toString(), point);
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
