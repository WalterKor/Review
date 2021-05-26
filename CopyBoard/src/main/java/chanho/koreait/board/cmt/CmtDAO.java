package chanho.koreait.board.cmt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import chanho.koreait.board.DBUtils;
//댓글 추출하는 메서드
public class CmtDAO {

	public static List<CmtDomain> selBoardCmtList(CmtEntity param) {
		List<CmtDomain> list = new ArrayList<CmtDomain>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT A.icmt, A.cmt, A.regdate "
				+ " , B.iuser, B.unm AS writerNm "
				+ " FROM t_board_cmt A"
				+ " INNER JOIN t_user B"
				+ " ON A.iuser = B.iuser"
				+ " WHERE A.iboard = ?"
				+ " ORDER BY A.icmt ";
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getIboard());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				
				CmtDomain vo = new CmtDomain();
				
				vo.setIcmt(rs.getInt("icmt"));
				vo.setCmt(rs.getString("cmt"));
				vo.setRegdate(rs.getString("regdate"));
				vo.setIuser(rs.getInt("iuser"));
				vo.setWriterNm(rs.getString("writerNm"));
				
				list.add(vo);
			}
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally {
			DBUtils.close(con, ps, rs);
		}	
		return list;
	}
	//댓글 등록 메서드
	public static int insBoardCmt(CmtEntity param) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		
		String sql = "INSERT INTO t_board_cmt"
				+ " (iboard, iuser, cmt) "
				+ " VALUES "
				+ " (?, ?, ?)";
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getIboard());
			ps.setInt(2, param.getIuser());
			ps.setString(3, param.getCmt());
			result = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con, ps);
		}
		return result;
	}
	public static int delBoardCmt(CmtEntity param) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps =null;
		String sql = " DELETE FROM t_board_cmt "
				+" WHERE icmt= ? AND iuser= ? "; 
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getIcmt());
			ps.setInt(2, param.getIuser());
			
			result = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con, ps);
		}
		return result;
	}

}
