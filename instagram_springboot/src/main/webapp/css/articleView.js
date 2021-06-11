'use strict'


let section = document.querySelector('section');

window.onload=function(){
	articleList();
}

function articleList(){
  $.ajax({
    type: 'post',
    url: 'index',
    dataType:'json', //서버로부터 내가 받는 데이터 타입
    success: function(data){
      showHeroes(data);
    },
    error:function(data){
      console.log('error');
    }

  })
}


function showHeroes(jsonObj) {
  for (let i = 0; i < jsonObj.length; i++) {
    let articles__shape__width = document.createElement('div');
    articles__shape__width.className="articles__shape__width";
    let articlesbox = document.createElement('div');
    articlesbox.className='articles__box';
    let articlesid = document.createElement('div');
    articlesid.className="articles__id";
    let articlesidtext = document.createTextNode(`👤 ${jsonObj[i]['memberid']}`);
    articlesid.appendChild(articlesidtext);
	let articlehref = document.createElement('a');
	articlehref.className="href";
	articlehref.href = `read.do?no=${jsonObj[i]['fileNo']}`;
    let articlesimg = document.createElement('img');
    articlesimg.className="src";
    articlesimg.src=`/upload/${jsonObj[i]['fileName']}`;
    articlesimg.className="articles__img";
    let articlesbottom = document.createElement('div');
    articlesbottom.className="articles__bottom";
    let articlesbottomheart = document.createElement('div');
	let articlesbottomhearttext = document.createTextNode("🤍")
	if(`${jsonObj[i]['heart']}`==='1') {articlesbottomhearttext = document.createTextNode("💗");}
    articlesbottomheart.appendChild(articlesbottomhearttext);
    articlesbottomheart.className="articles__bottom__heart";
    let articlesbottomheartnum = document.createElement('div');
    let articlesbottomheartnumtext = document.createTextNode(`${jsonObj[i]['read_cnt']}`);
    articlesbottomheartnum.appendChild(articlesbottomheartnumtext);
    articlesbottomheartnum.className="articles__bottom__heart__num";
    let articlesbottomtext = document.createElement('div');
    let articlesbottomtexttext = document.createTextNode(`${jsonObj[i]['contentText']}`);
    articlesbottomtext.appendChild(articlesbottomtexttext);
    articlesbottomtext.className="articles__bottom__text";
    let articlesspace = document.createElement('div');
    articlesspace.className="articles__space";


	articlehref.appendChild(articlesimg);
    articlesbottom.appendChild(articlesbottomheart);
    articlesbottom.appendChild(articlesbottomheartnum);
    articlesbottom.appendChild(articlesbottomtext);
    articlesbox.appendChild(articlesid);
    articlesbox.appendChild(articlehref);
    articlesbox.appendChild(articlesbottom);
    articlesbox.appendChild(articlesspace);
    articles__shape__width.appendChild(articlesbox);
    section.appendChild(articles__shape__width);
    document.querySelectorAll('.articles__bottom__heart')[i].setAttribute('onclick',"heartClick(this.id)");
    document.querySelectorAll('.articles__bottom__heart')[i].setAttribute('id',`${jsonObj[i]['fileNo']}`);
    document.querySelectorAll('.articles__bottom__heart__num')[i].setAttribute('id',`num__${jsonObj[i]['fileNo']}`);
  }
}


function heartClick(click_id){
  let heartsu = parseInt($(`#num__${click_id}`).text());
  $.ajax({
    type: 'post',
    url: 'heart.do',
    dataType:'text', //서버로부터 내가 받는 데이터 타입
	data:{click_id:click_id},
    success: function(data){
      if(data=="usable"){
	 	$(`#${click_id}`).text("🤍");
	 	$(`#num__${click_id}`).text(heartsu-1);	 	
	  }
	  else{
	 	$(`#${click_id}`).text("💗");
	 	$(`#num__${click_id}`).text(heartsu+1);	 	
	  }
    },
    error:function(data){
      console.log('error');
    }

  })
}


function findtext(){
	$("#articles__shape").empty();
  let findtext = $(".header__text__find__input__findtext").val();
  console.log(findtext);
  $.ajax({
    type: 'get',
    url: 'findtext',
    dataType:'json', //서버로부터 내가 받는 데이터 타입
	data:{findtext:findtext},
    success: function(data){
      showHeroes(data);
    },
    error:function(data){
      console.log('error');
    }

  })
}