package chanho.koreait.board.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import chanho.koreait.board.MyUtils;


@WebServlet("/board/list")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//페이징처리기술
		final int recordCnt = 5; //페이지당 처리할 갯수
		
		int cPage = MyUtils.getParamInt("cPage", request);
		
		if(cPage == 0) {cPage = 1;}
		int startIdx = (cPage - 1)*recordCnt;
		BoardDTO param = new BoardDTO();
		
		param.setStartIdx(startIdx);
		param.setRecordCnt(recordCnt);
				
		//검색기능
		int searchType = MyUtils.getParamInt("searchTupe", request);
		String searchText = request.getParameter("searchText");
		// 검색기능이 0이 아니고 안에 내용이 있다면 그리고 빈칸이 아니라면
		if(searchType !=0 && searchText !=null && !searchText.equals("")) {
			param.setSearchType(searchType); //타입을 저장하고
			param.setSearchText(searchText); //글을 저장하고
		}
		
		
		request.setAttribute("pagingCnt", BoardDAO.selPaginCnt(param));
		request.setAttribute("list", BoardDAO.selBoardList(param));
		request.setAttribute("searchText", response);

		MyUtils.openJSP("리스트", "board/list", request, response);
		
	}


	

}
