'use strict'

function heartClick(click_id){
  let heartsu = parseInt($(`#num__${click_id}`).text());
  $.ajax({
    type: 'post',
    url: 'heart',
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

