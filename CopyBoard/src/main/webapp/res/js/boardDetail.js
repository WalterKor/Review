var cmtFrmElem = document.querySelector('#cmtFrm');
var cmtListElem = document.querySelector('#cmtList'); //해당 글의 iboard값과 세션의 iuser

//댓글달기 누르면 작동하는거 등록이겠지?
function regCmt(){//값을 저장해놨다가 함수 호출
	
	var cmtVal = cmtFrmElem.cmt.value; //댓글내용cmt
	var param = {
		iboard : cmtListElem.dataset.iboard,
		cmt : cmtVal
	};
	
	regAjax(param);//값을 받아서 객체를 받아서 전달해줘
}

function regAjax(param){
	
	const init = {
		method : 'POST',
		body : new URLSearchParams(param)
	};
	
	fetch('cmtInsSel', init)
	.then(function(res){
		return res.json();
	}).then(function(myJson){
		
		switch(myJson.result){
			case 0:
			alert('등록실패');
			break;
			
			case 1:
				cmtFrmElem.cmt.value = ''; //댓글쓰고 그 안에 내용 깔끔하게 지우기
				getListAjax();//댓글 리스트를 달라고 하는 함수
			break;		
		}
	});
}

//댓글한테 댓글 정보를 달라고 요청하는 메서드
function getListAjax(){
	var iboard = cmtListElem.dataset.iboard;  //해당글에대해서 댓글 달라고 요청
	
	fetch('cmtInsSel?iboard=' + iboard)
	.then(function(res){
		return res.json();
	}).then(function(data){
		makeCmtElemList(data); //받은 리스트정보 토대로 댓글 리스트를 만들기위해서
	});
}

function makeCmtElemList(data){
	cmtListElem.innerHTML = ''; //일단 안의 내용을 지워라
	
	var tableElem = document.createElement('table');
	var trElemTitle = document.createElement('tr');
	var thElemCtnt = document.createElement('th');
	var thElemWriter = document.createElement('th');
	var thElemRegdate = document.createElement('th');
	var thElemBigo = document.createElement('th');
	
	
	thElemCtnt.innerText = '내용';
	thElemWriter.innerText = '글쓴이';
	thElemRegdate.innerText = '작성일';
	thElemBigo.innerText = '비고';
	
	
	trElemTitle.append(thElemCtnt);
	trElemTitle.append(thElemWriter);
	trElemTitle.append(thElemRegdate);
	trElemTitle.append(thElemBigo);
	
	tableElem.append(trElemTitle);
	cmtListElem.append(tableElem);
	
	var loginUserPk = cmtListElem.dataset.login_user_pk;
	
	data.forEach(function(item){
		//댓글창 안에있는것들
		var trElemCtnt = document.createElement('tr');
		var tdElem1 = document.createElement('td');
		var tdElem2 = document.createElement('td');
		var tdElem3 = document.createElement('td');
		var tdElem4 = document.createElement('td');
		
		tdElem1.append(item.cmt);
		tdElem2.append(item.writerNm);
		tdElem3.append(item.regdate);
		
		if( loginUserPk === item.iuser){
			var delBtn = document.createElement('button');
			var modBtn = document.createElement('button');
			
			delBtn.innerText = '삭제';
			modBtn.innerText = '삭제';
			
			delBtn.addEventListener('onclick', function(){
				delAjax(item.icmt);
			});
			
			tdElem4.append(delBtn);
			tdElem4.append(modBtn);
		}
		
		trElemCtnt.append(tdElem1);
		trElemCtnt.append(tdElem2);
		trElemCtnt.append(tdElem3);
		trElemCtnt.append(tdElem4);
		
		tableElem.append(trElemCtnt);
			
	});
}

function delAjax(icmt){
	
	fetch('cmtDelUpd?icmt='+ icmt)
	.then(function(res){
		return res.json(); //res를 json으로 바꿔주고
	})
	.then(function(myjson){
		
		switch(myjson.result){
			case 0:
			alert('삭제에 실패했습니다.');
			break;
			case 1:
				getListAjax();//다시 리스트를 불러서 다시 찍는다.
			break;
		}
	})
}



