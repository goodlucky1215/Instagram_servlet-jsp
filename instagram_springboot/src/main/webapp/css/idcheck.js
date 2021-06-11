"use strict"

function idcheck(){
  const id = $('input[name=id]').val();
  if(id.length<=0){
	$('#message').text('')
	return;
  }	
  $.ajax({
    type: 'post',
    url: 'checkid',
    dataType:'text', //서버로부터 내가 받는 데이터 타입
    data:{id:id},
    success: function(data, textStatus){
      if(data === 'usable'){
		$('.message').removeClass("error");
        $('#message').text('사용할 수 있는 ID입니다!!');
        $('#signBtn').prop('disabled', false);
      } else {
        $('.message').addClass("error");
        $('#message').text('이미 사용 중인 아이디입니다.');
        $('#signBtn').prop('disabled', true);
      }
    },
    error:function(data,textStatus){
      console.log('error');
    }

  })
}