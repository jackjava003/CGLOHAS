$(document).ready(function() {
	$('#board2222').click(function() {
		if($('#board111').css("left")=="-200px")
		{
		$('#board111').animate({
			left : '0px'
		}, 1000, 'easeOutQuad');
	}else{
				$('#board111').animate({
			left : '-200px'
		}, 1000, 'easeOutQuad');
	}
	});
	$('#board2222').click(function() {
		if($(this).css("left")=="0px")
		{
		$(this).animate({
			left : '250px'
		}, 1000, 'easeOutQuad');
	}else{
				$(this).animate({
					left : '0px'
		}, 1000, 'easeOutQuad');
	
	
	 
	 
	}
	});
	
	
	
});