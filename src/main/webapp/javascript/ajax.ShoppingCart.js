var error = "This item is out of stock";
function AjaxShoppingCart(id,price,discount){

	var qty = encodeURIComponent(document.getElementById('qty'+id).value);
	var id = encodeURIComponent(id);
	var price = encodeURIComponent(price);
	var discount = encodeURIComponent(discount);
	
	$.ajax({
		type : 'GET',
		url : "/CGLOHAS/_04_listItems/BuyItem.do?itemID=" + id +"&price="+price+"&discount="+discount+"&qty="+qty,
		success : function(obj) {
			if(obj!=error){
				updateListShopCart(obj);
				document.getElementById('cartContent').innerHTML = "Cart contain "+obj.length+" items";
				document.getElementById('errorMessage').innerHTML ="";
			}else{
				document.getElementById('errorMessage').innerHTML = obj;
				alert(obj);
			}	
		}
	});
}

function updateListShopCart(obj) {
	var currentPrice = 0;
	$.each(obj,function(index, element) {
			currentPrice += ((parseInt(element.unitPrice) * parseFloat(element.discount)) *parseInt(element.qty));
			//alert(element.itemid +"," + element.qty +"," + element.unitPrice+"," + element.discount);
	});
	if(document.getElementById('currentTotal')!= null){
	document.getElementById('currentTotal').innerHTML = currentPrice+".0";
	}
}


