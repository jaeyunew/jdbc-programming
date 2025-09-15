package callableStatementEx;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import util.DBUtil;

public class MemberInsert {
  static Connection conn = DBUtil.getConnection();
  public static void main(String[] args) {
    String m_userid = "blackpink";
    String m_pwd = "blackpink1234";
    String m_email = "blackpink@gmail.com";
    String m_hp = "010-2321-2144";
    String resultString = null;
    String sql = "{CALL SP_MEMBER_INSERT(?,?,?,?,?)}";

    try(CallableStatement call = conn.prepareCall(sql))
    {
      // IN 파라미터 셋팅
      call.setString(1,m_userid);
      call.setString(2,m_pwd);
      call.setString(3,m_email);
      call.setString(4,m_hp);

      //OUT 파라미터 셋팅
      call.registerOutParameter(5,java.sql.Types.INTEGER);

      //실행
      call.execute();

      int rtn = call.getInt(5);

      if(rtn == 100){
        System.out.println("이미 가입된 사용자입니다.");
      }else{
        System.out.println("회원 가입이 되었습니다. 감사합니다.");
      }
    } catch (SQLException e) {
      e.printStackTrace();
      }
    }
  }

