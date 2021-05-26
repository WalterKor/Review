package chanho.koreait.board.cmt;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chanho.koreait.board.MyUtils;


@WebServlet("/board/cmtDelUpd")
public class CmtDelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	//댓글 삭제
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int icmt = MyUtils.getParamInt("icmt", request);
		int iuser = MyUtils.getLoginUserPk(request);
		
		CmtEntity param = new CmtEntity();
		param.setIcmt(icmt);
		param.setIcmt(icmt);
		
		int result = CmtDAO.delBoardCmt(param);
		
		response.getWriter()
		.append("{")
		.append("\"result\":")
		.append(String.valueOf(result))
		.append("}")
		.flush();
		
		
				
	}

	//댓글 수정
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
