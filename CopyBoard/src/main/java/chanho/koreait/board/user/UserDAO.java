package chanho.koreait.board.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import chanho.koreait.board.DBUtils;



public class UserDAO {

	public static int selIdChk(String uid) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;		
		String sql = "SELECT iuser FROM t_user WHERE uid = ?";		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, uid.trim());
			rs = ps.executeQuery();
			if(rs.next()) { result = 1; }
		} catch (Exception e) {			
			e.printStackTrace();
		} finally {
			DBUtils.close(con, ps, rs);
		}
		return result;
	}

	public static void join(UserEntity vo) {
		
		Connection con = null;
		PreparedStatement ps = null;
		String sql = " INSERT INTO t_user (uid,upw,unm,gender) "
				+ " VALUE (?, ?, ?, ?) ";
			
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getUid());
			ps.setString(2, vo.getUpw());
			ps.setString(3, vo.getUnm());
			ps.setInt(4, vo.getGender());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con, ps);
		}
		
	}

	public static UserEntity loginUser(UserEntity vo) {
		
		UserEntity result = new UserEntity();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = " SELECT uid,upw,iuser,unm FROM t_user "
					+ " WHERE uid = ? ";
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getUid());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				int iuser = rs.getInt("iuser");
				String uid = rs.getString("uid");
				String upw = rs.getString("upw");
				String unm = rs.getString("unm");
				
				result.setIuser(iuser);
				result.setUid(uid);
				result.setUpw(upw);
				result.setUnm(unm);
				
			}
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}finally {
			DBUtils.close(con, ps, rs);
		}
	
	}

}
