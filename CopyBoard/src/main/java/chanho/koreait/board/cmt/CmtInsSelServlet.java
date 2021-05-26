package chanho.koreait.board.cmt;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chanho.koreait.board.MyUtils;

@WebServlet("/board/cmtInsSel")
public class CmtInsSelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	//리스트뿌리기
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int iboard = MyUtils.getParamInt("iboard", request);
		System.out.println("iboard : " +iboard);
		CmtEntity param = new CmtEntity();
		param.setIboard(iboard);
		
		List<CmtDomain> list = CmtDAO.selBoardCmtList(param);
	}
	//등록
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		int iboard = MyUtils.getParamInt("iboard", request);
		String cmt = request.getParameter("cmt");
		int iuser = MyUtils.getLoginUserPk(request);
		
		CmtEntity param = new CmtEntity();
		param.setIboard(iboard);
		param.setCmt(cmt);
		param.setIuser(iuser);
		
		int result = CmtDAO.insBoardCmt(param);
		
		response.getWriter()
		.append("{")
		.append("\"result\":")
		.append(String.valueOf(result))
		.append("}")
		.flush();
		
		
		
		
	}

}
