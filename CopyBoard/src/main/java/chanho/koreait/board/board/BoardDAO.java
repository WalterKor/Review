package chanho.koreait.board.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import chanho.koreait.board.DBUtils;

public class BoardDAO {

	public static Object selBoardList(BoardDTO param) {
		List<BoardDomain> list = new ArrayList<BoardDomain>();
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = "SELECT B.unm as writerNm"
				+ "	, A.iboard, A.iuser, A.regdt, A.title "				
				+ " FROM t_board A "
				+ " INNER JOIN t_user B "
				+ " ON A.iuser = B.iuser "
				+ " ORDER BY iboard DESC "
				+ " LIMIT ?, ? " ;
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getStartIdx());
			ps.setInt(2, param.getRecordCnt());
			rs = ps.executeQuery();
			
			while(rs.next()) {
				BoardDomain vo  = new BoardDomain();
				vo.setIboard(rs.getInt("iboard"));
				vo.setTitle(rs.getString("title"));
				vo.setRegdt(rs.getString("regdt"));
				vo.setIuser(rs.getInt("iuser"));
				vo.setWriterNm(rs.getString("writerNm"));
				list.add(vo);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con, ps, rs);
		}
		return list;
	}

	public static int selPaginCnt(BoardDTO param) {
		int result = 0;
		Connection con =null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT CEIL(COUNT(iboard) / ?)")
		.append("FROM t_board A")
		.append(" INNER JOIN t_user B ")
		.append(" ON A.iuser = B.iuser ");
		
		if(param.getSearchType() > 0) {
			sb.append(" WHERE ");
		}
		
		switch(param.getSearchType()) {
		case 1: //제목+내용
			sb.append("A.title LIKE '%")
			.append(param.getSearchText())
			.append("%' OR A.ctnt LIKE '%")
			.append(param.getSearchText())
			.append("%' ");
			break;
		case 2: //제목
			sb.append("A.title LIKE '%")
			.append(param.getSearchText())
			.append("%' ");			
			break;
		case 3: //내용
			sb.append("A.ctnt LIKE '%")
			.append(param.getSearchText())
			.append("%' ");			
			break;
		case 4: //글쓴이
			sb.append("B.unm LIKE '%")
			.append(param.getSearchText())
			.append("%' ");
			break;
		}
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sb.toString());
			ps.setInt(1, param.getRecordCnt());//페이징 나누는수
			rs = ps.executeQuery();//실행시키고
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con, ps, rs);
		}
		
		return result;
	}

	public static BoardDomain selBoard(BoardDTO param) {
		BoardDomain result = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		String sql = " SELECT B.unm as writerNm "
				+ "	, A.iuser, A.regdt, A.title, A.ctnt "				
				+ " FROM t_board A "
				+ " INNER JOIN t_user B "
				+ " ON A.iuser = B.iuser "
				+ " WHERE A.iboard = ? ";
		
		try {
			con = DBUtils.getCon();
			ps = con.prepareStatement(sql);
			ps.setInt(1, param.getIboard());
			rs = ps.executeQuery();
			
			if(rs.next()) {
				result = new BoardDomain();
				result.setIboard(param.getIboard());
				result.setTitle(rs.getString("title"));
				result.setCtnt(rs.getString("ctnt"));
				result.setIuser(rs.getInt("iuser"));
				result.setWriterNm(rs.getString("writerNm"));
				result.setRegdt(rs.getString("regdt"));
			}
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
		}
		return result;
	}
}


	
