package com.board.util;

public class Page {

	
		public String getPageList(int pageNum, int postNum, int listCount, int totalCount, String searchType, String keyword) throws Exception {
		
		//listCount : 페이지 리스트에서 보여지는 페이지 갯수
		int section = 0; //페이지 리스트가 보여지는 단위 섹터
		int totalSection = 0; //페이지 리스트 전체 섹터 갯수
		
		int totalPage = totalCount/postNum; //전체 페이지 갯수
		if(totalCount == 0) totalCount ++;
		if(totalCount%listCount > 0  ) totalPage++;
		
		totalSection = totalPage/listCount;
		if(totalPage%listCount > 0) totalSection++;
		
		pageNum = pageNum-1; //계산에 사용되는 페이지 번호는 0부터 시작
		section = pageNum/listCount;	

		String pageList = "";
		int i;
		if(totalPage != 1 )
		{
			for(i=0; i < listCount ; i++){ 
				if(section > 0 && i == 0) 
					pageList +=	"<a href=/board/list?num=" + Integer.toString((section-1)*listCount+(listCount-1)) + "&serachType=" + searchType + "&keyword=" + keyword + ">◀</a> ";
				if(pageNum != (section*listCount + i))
					pageList += " <a href=/board/list?num=" + Integer.toString(i+section*listCount+1) + "&serachType=" + searchType + "&keyword=" + keyword + ">" + Integer.toString(i+section*listCount+1) + "</a> ";
				else pageList += " <span style='font-weight: bold'>" + Integer.toString(section*listCount+i+1) + "</span>";
				if(totalSection >1 && i==(listCount-1) && totalPage != (i+section*listCount+1)) 
					pageList += "<a href=/board/list?num=" + Integer.toString((section+1)*listCount+1) + "&serachType=" + searchType + "&keyword=" + keyword + ">▶</a>";
				if(totalPage == (i+section*listCount)){  break; }
			}
	 	} 
		return pageList;
	}
	
}
