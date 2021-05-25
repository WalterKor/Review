package chanho.koreait.board.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;

import chanho.koreait.board.MyUtils;


@WebServlet("/user/login")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MyUtils.openJSP("로그인", "user/login", request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uid = request.getParameter("uid");
		String upw = request.getParameter("upw");
			
		System.out.println("uid : " +uid);
		System.out.println("upw : " +upw);	
		UserEntity vo = new UserEntity();
		vo.setUid(uid);
		vo.setUpw(upw);
		
		UserEntity result = UserDAO.loginUser(vo);
		
		if(result == null) { //아이디가 없다면
			request.setAttribute("errMsg", "아이디를 확인해주세요");
		}else if (BCrypt.checkpw(upw, result.getUpw())) {
			result.setUpw(null);
			HttpSession hs = request.getSession();
			hs.setAttribute("loginUser", result);			
			response.sendRedirect("/board/list");
			return;
		}else {
			request.setAttribute("errMsg", "비밀번호를 확인해주세요.");
		}
		doGet(request, response);
		
	}

}
