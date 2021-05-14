"use strict"
function previewFile() {
		console.log("Dfdsf");
	const preview = document.querySelector('img');
	const file = document.querySelector('input[type=file]').files[0];
	const reader = new FileReader();

	reader.addEventListener("load", function () {
		preview.src = reader.result;
	}, false);

	if (file) {
		reader.readAsDataURL(file);
	}
}

function textCnt(){
	const textarea = document.querySelector('#textarea');
	const text_cnt = document.querySelector('#textcnt');
	let text_len= textarea.value.length;
	if(text_len<=100) text_cnt.innerHTML=`(${text_len}/100)`;
	else if((text_len>100)) {
		textarea.value=textarea.value.substring(0,100);
		console.log(textarea.value.substring(0,100));
		text_cnt.innerHTML=`(${100}/100)`;
	}
	
}
