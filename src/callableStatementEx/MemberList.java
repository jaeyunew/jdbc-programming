package callableStatementEx;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import util.DBUtil;

public class MemberList {

  static Connection conn = DBUtil.getConnection();

  public static void main(String[] args) {
    String sql = "{CALL SP_MEMBER_LIST()}";

    try (CallableStatement call = conn.prepareCall(sql)) {
      ResultSet rs = call.executeQuery();
      while (rs.next()) {
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
  }

}
