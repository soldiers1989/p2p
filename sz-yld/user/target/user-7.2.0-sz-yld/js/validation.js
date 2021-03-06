var flgs = true;

var passwordRegex = /^(?![a-zA-Z0-9]+$)(?![^a-zA-Z/D]+$)(?![^0-9/D]+$).{8,12}$/;
var errorPwdMsg = "数字+字母+特殊字符，8-12位长度";

var newPasswordRegex = eval($("#newPasswordRegexId").val());
var passwordRegexContent = $("#passwordRegexContentId").val()+"";

var newTxPasswordRegex = eval($("#newTxPasswordRegexId").val());
var txPasswordRegexContent = $("#txPasswordRegexContentId").val()+"";

if(newPasswordRegex !='' && newPasswordRegex != undefined && newPasswordRegex != "undefined" ){
	passwordRegex = newPasswordRegex;
}
if(passwordRegexContent !='' && passwordRegexContent != undefined && passwordRegexContent != "undefined"){
	errorPwdMsg = passwordRegexContent;
}


var txPasswordRegex = /^[a-zA-Z](?![a-zA-Z]*$)[a-zA-Z0-9]{7}$/;
var txErrorPwdMsg = "密码需由八个字符组成：字母+数字，且首个字符必须是字母";
if(newTxPasswordRegex !='' && newTxPasswordRegex != undefined && newTxPasswordRegex != "undefined" ){
	txPasswordRegex = newTxPasswordRegex;
}
if(txPasswordRegexContent !='' && txPasswordRegexContent != undefined && txPasswordRegexContent != "undefined"){
	txErrorPwdMsg = txPasswordRegexContent;
}

$(function(){
	initVal();
});

function initVal(){
	$submit =  $(".sumbitForme");
	$intext =$('input[type="text"]');
	$inpassword =$('input[type="password"]');
	$textarea =$('textarea');
	$inselect =$('select');
	$submit.click(function(){
		flgs = true;
		var cname = $(this).attr("fromname");
		$form_intext =$('.'+cname+' input[type="text"]');
		$form_inhidden =$('.'+cname+' input[type="hidden"]');
		$form_inpassword =$('.'+cname+' input[type="password"]');
		$form_textarea =$('.'+cname+' textarea');
		$form_select =$('.'+cname+' select');
		
		$form_intext.each(function(){
			checkText($(this));
		});
		$form_inhidden.each(function(){
			return checkHidden($(this));
		});
		
		$form_inpassword.each(function(){
			return checkPassword($(this));
		});
		
		$form_textarea.each(function(){
			return checkTextarera($(this));
		});
		
		$form_select.each(function(){
			return checkSelect($(this));
		});
		
		
		var $isread = $("input[name='isread']");
		if($isread.size()>0){
			if($isread[0].checked!=true){
				alert("你还没有阅读并同意签署协议!");
				flgs = false;
			}
		}
		if($(this).attr("savefromname") != undefined){
			//提交判定
			var $save = $('.'+cname+' input[name="save"]');
			if($save.size()>0){
				$save.val(1);
			}
		}
		
		if(!flgs){
			return false;
		}
	 });
	$intext.focus(function(){
		$(this).blur(function(){
			return checkText($(this));
		 });
	});
	
	$textarea.focus(function(){
		$(this).blur(function(){
			checkTextarera($(this));
		 });
	});
	$inpassword.focus(function(){
		$(this).blur(function(){
			checkPassword($(this));
		 });
	});
	$inselect.focus(function(){
		$(this).blur(function(){
			checkSelect($(this));
		 });
	});
	 //提交校验
}


function submitVal(fromname){
	$submit =  $(".sumbitForme");
	$intext =$('input[type="text"]');
	$inpassword =$('input[type="password"]');
	$textarea =$('textarea');
	$inselect =$('select');
	flgs = true;
	var cname = fromname;
	$form_intext =$('.'+cname+' input[type="text"]');
	$form_inhidden =$('.'+cname+' input[type="hidden"]');
	$form_inpassword =$('.'+cname+' input[type="password"]');
	$form_textarea =$('.'+cname+' textarea');
	$form_select =$('.'+cname+' select');
	
	$form_intext.each(function(){
		checkText($(this));
	});
	
	$form_inhidden.each(function(){
		return checkHidden($(this));
	});
	
	$form_inpassword.each(function(){
		return checkPassword($(this));
	});
	
	$form_textarea.each(function(){
		return checkTextarera($(this));
	});
	
	$form_select.each(function(){
		return checkSelect($(this));
	});
	
	
	var $isread = $("input[name='isread']");
	if($isread.size()>0){
		if($isread[0].checked!=true){
			alert("你还没有阅读并同意签署协议!");
			flgs = false;
		}
	}
	if($(this).attr("savefromname") != undefined){
		//提交判定
		var $save = $('.'+cname+' input[name="save"]');
		if($save.size()>0){
			$save.val(1);
		}
	}
	
	return flgs;
}

//校验输入文本框
function checkText($eve){
	
	if($eve.is(":hidden")){
		return;
	} 
	
	flg = true;
	if($eve== undefined){
		return false;
	}
	if($eve.attr("class")== undefined){
		return false;
	}
	msg = $eve.attr("class").split(" ");
	var mtest = $eve.attr("mtest");
	value = $eve.val();
	$error = $eve.nextAll("p[errortip]");
	$tip = $eve.nextAll("p[tip]");

    //防止SQL注入
    var re= /select|update|delete|exec|count|’|"|=|;|>|<|%/i;

	for(var i=0;i<msg.length;i++){
		var temp = $.trim(msg[i]);
		if(temp.length>0){
			//validation无效
			if(temp == "nocall"){
				return;
			}
			if(temp == "required"){
				if($.trim(value) == ""){
					$error.addClass("red");
					$error.html("不能为空！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
                else if(re.test(value))
                {
                    $error.addClass("red");
                    $error.html("存在特殊字符");
                    $tip.hide();
                    $error.show();
                    flg = false;
                    flgs = false;
                    return false;
                }
			}

			
			if(temp == "isint"){
				var myreg = /^[0-9]([0-9])*$/;
				if($.trim(value) == ""){
					return false;
				}
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html("必须为整数！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}
			
			if(temp == "postcode"){
				var myreg = /^[1-9]\d{5}(?!\d)$/;
				if($.trim(value) == ""){
					return false;
				}
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html("请输入正确的邮政编码！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}

			if(temp == "isyear"){
				var myreg = /^[1-9][0-9]{3}$/;
				if($.trim(value) == ""){
					return false;
				}
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html("必须为年份！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}

			if(temp.indexOf('is-number')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					var myreg = /^[0-9]([0-9])*$/;
					if($.trim(value) == ""){
						return false;
					}
					if(!myreg.test(value))
					{
						$error.addClass("red");
						$error.html("请输入"+tsize+"位数字！");
						$tip.hide();
						$error.show();
						flg = false;
						flgs = false;
						return false;
					}
				}
			}
			//整型数字最小范围限制
			if(temp.indexOf('min-size')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(parseInt(value) < parseInt(tsize)){
						$error.addClass("red");
						$error.html("小于最小范围值: " + tsize);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
			//整型数字最大范围限制
			if(temp.indexOf('max-size')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(parseInt(value) > parseInt(tsize)){
						$error.addClass("red");
						$error.html("超出最大范围值:" + tsize);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
			//输入值减合同剩余金额不得小于3000
			if(temp.indexOf('max-ht-size')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if((parseInt(tsize) - parseInt(value))<3000){
						$error.addClass("red");
						$error.html("剩余合同金额不足3000");
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
			//浮点型数字最小范围限制
			if(temp.indexOf('minf-size')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(parseFloat(value) < parseFloat(tsize)){
						$error.addClass("red");
						$error.html("小于最小范围值: " + tsize);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
			//浮点型数字最大范围限制
			if(temp.indexOf('maxf-size')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(parseFloat(value) > parseFloat(tsize)){
						$error.addClass("red");
						$error.html("超出最大范围值: " + tsize);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
			if(temp.indexOf('mulriple')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=2){
					tsize = tsizearry[1];
					if(parseInt(value) % parseInt(tsize) != 0){
						$error.addClass("red");
						$error.html("必须是"+tsize+"的倍数");
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
			//限制输入内容的长度下限
			if(temp.indexOf('min-length')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(value.length < parseInt(tsize)){
						$error.addClass("red");
						$error.html("小于输入限制"+tsize+"，当前长度为"+value.length);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
			//限制输入内容的长度上限
			if(temp.indexOf('max-length')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(value.length>tsize){
						$error.addClass("red");
						$error.html("超过输入限制"+tsize+"，当前长度为"+value.length);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
			//校验邮件格式是否正确
			if(temp == "e-mail"){
				/*var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,4}$/;*/
				var myreg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html("邮箱地址不正确!");
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			//校验手机格式是否正确
			if(temp == "phonenumber"){
				var myreg = /^(13|14|15|17|18)[0-9]{9}$/;
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html("请输入正确手机号!");
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			
			if(temp == "idcard"){
				if(!isIdCardNot(value,$error))
				{
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			if(temp == "textv-b"){
				var ddindx = $eve.index(".textv-b");
				$passwords = $(".textv-a:eq("+ddindx+")");
				if(value.replace(/\ +/g,"") != $passwords.val().replace(/\ +/g,"")){
					$error.addClass("red");
					$error.html("两次输入不一致!");
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			
			if(temp == "lcjesx-size"){
				if(value!=""){
					var ddindx = $eve.index(".lcjesx-size");
					$lcjesx = $(".lcjexx-size:eq("+ddindx+")");
					if($lcjesx.val()!=""){
						if(parseFloat(value) < parseFloat($lcjesx.val())){
							$error.addClass("red");
							$error.html("用户投资上限大于等于用户投资下限!");
							flg = false;
							flgs = false;
							$tip.hide();
							$error.show();
							return false;
						}
					}
				}
			}
			
			if(temp == "jkje-size"){
				if(value!=""){
					var ddindx = $eve.index(".jkje-size");
					$lcjesx = $(".lcjesx-size:eq("+ddindx+")");
					if($lcjesx.val()!=""){
						if(parseFloat(value) < parseFloat($lcjesx.val())){
							$error.addClass("red");
							$error.html("计划金额大于等于用户投资上限!");
							flg = false;
							flgs = false;
							$tip.hide();
							$error.show();
							return false;
						}
					}
				}
			}
			
			if(temp == "jzsj-size"){
				if(value!=""){
					var ddindx = $eve.index(".jzsj-size");
					$kssj = $(".kssj-size:eq("+ddindx+")");
					if($kssj.val()!=""){
						var startDateTemp = $kssj.val().split(" ");   
						var endDateTemp = value.split(" ");
						var arrStartDate = startDateTemp[0].split("-");   
						var arrEndDate = endDateTemp[0].split("-");   
						var arrStartTime = startDateTemp[1].split(":");   
						var allStartDate = new Date(arrStartDate[0],arrStartDate[1],arrStartDate[2],arrStartTime[0],arrStartTime[1],arrStartTime[2]);   
						var allEndDate = new Date(arrEndDate[0],arrEndDate[1],arrEndDate[2],"23","59","59");
						if(allStartDate.getTime()>allEndDate.getTime()){   
							$error.addClass("red");
							$error.html("申请截止时间小于申请开始时间!");
							flg = false;
							flgs = false;
							$tip.hide();
							$error.show();
							return false;
						}
					}
				}
			}
			
			if(temp == "lxmax-size"){
				if(value!=""){
					var ddindx = $eve.index(".lxmax-size");
					$lcjesx = $(".lxmin-size:eq("+ddindx+")");
					if($lcjesx.val()!=""){
						if(parseFloat(value) < parseFloat($lcjesx.val())){
							$error.addClass("red");
							$error.html("利率范围有误!");
							flg = false;
							flgs = false;
							$tip.hide();
							$error.show();
							return false;
						}
					}
				}
			}
			if(temp == "isNaN"){
				if(isNaN($.trim(value))){
					$error.addClass("red");
					$error.html("只能输入数字！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}
			if(temp == "verify-code"){
				var myreg = /^[a-zA-Z0-9]+$/;
				if($.trim(value) == ""){
					return false;
				}
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html("请输入正确的验证码！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}
			if(temp.indexOf('zqzrbl-')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					var zqzrxx = tsizearry[1];
					var zqzrsx = tsizearry[2];
					if((parseFloat(zqzrsx)!=0 && parseFloat(value)>parseFloat(zqzrsx)) || (parseFloat(zqzrxx)!=0 && parseFloat(value)<parseFloat(zqzrxx))){
						$error.addClass("red");
						$error.html("债权转让的价格区间需在" + zqzrxx + "元-" + zqzrsx + "元之间");
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
					}
				}
			}
		}
	}
	if(mtest != undefined){
		if(mtest.length>0){
			var myreg = eval(mtest);  
			if(!myreg.test(value))
			{
				$error.addClass("red");
				$error.html($eve.attr("mtestmsg"));
				$tip.hide();
				$error.show();
				flg = false;
				flgs = false;
				return false;
			}
		}
	}
	
	if(!flg){
		$tip.hide();
		$error.show();
		return false;
	}else{
		$error.removeClass("red");
		$error.hide();
		$tip.show();
	}
}

//校验输入文本框
function checkSelect($eve){
	if($eve.is(":hidden")){
		return;
	} 
	flg = true;
	if($eve== undefined){
		return false;
	}
	if($eve.attr("class")== undefined){
		return false;
	}
	msg = $eve.attr("class").split(" ");
	value = $eve.val();
	$error = $eve.nextAll("p[errortip]");
	$tip = $eve.nextAll("p[tip]");

	for(var i=0;i<msg.length;i++){
		var temp = $.trim(msg[i]);
		if(temp.length>0){
			if(temp == "required"){
				if($.trim(value) == ""){
					$error.addClass("red");
					$error.html("请选择！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}
		}
	}
	if(!flg){
		$tip.hide();
		$error.show();
		return false;
	}else{
		$error.removeClass("red");
		$error.hide();
		$tip.show();
	}
}

//校验隐藏域文本框
function checkHidden($eve){
	flg = true;
	if($eve== undefined){
		return;
	}
	if($eve.attr("class")== undefined){
		return;
	}
	msg = $eve.attr("class").split(" ");
	value = $eve.val();
	$error = $eve.nextAll("p[errortip]");
	$tip = $eve.nextAll("p[tip]");
	if(!$error || $error.length == 0){
		$error = $eve.parent().nextAll("p[errortip]");
	}
	if(!$tip || $tip.length == 0){
		$tip = $eve.parent().nextAll("p[tip]");
	}
	for(var i=0;i<msg.length;i++){
		var temp = $.trim(msg[i]);
		if(temp.length>0){
			if(temp == "required"){
				if($.trim(value) == ""){
					$error.addClass("red");
					$error.html("请选择！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}
		}
	}
	if(!flg){
		$tip.hide();
		$error.show();
		return false;
	}else{
		$error.removeClass("red");
		$error.hide();
		$tip.show();
	}
}

function checkPassword($eve){
	if($eve.is(":hidden")){
		return;
	}
	flg = true;
	if($eve== undefined){
		return false;
	}
	if($eve.attr("class")== undefined){
		return false;
	}
	msg = $eve.attr("class").split(" ");
	var mtest = $eve.attr("mtest");
	value = $eve.val();
	$error = $eve.nextAll("p[errortip]");
	$tip = $eve.nextAll("p[tip]");

	for(var i=0;i<msg.length;i++){
		var temp = $.trim(msg[i]);
		if(temp.length>0){
			if(temp == "required"){
				if($.trim(value) == ""){
					$error.addClass("red");
					$error.html("不能为空！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}
			if(temp == "password-a"){
				var myreg = passwordRegex;
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html(errorPwdMsg);
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			if(temp == "cpassword-a"){
				if($.trim(value)!=""){
					var myreg = passwordRegex;
					if(!myreg.test(value))
					{
						$error.addClass("red");
						$error.html(errorPwdMsg);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
					}
				}
			}
			if(temp == "cpassword-f"){
				var myreg = txPasswordRegex;///^[a-zA-Z](?![a-zA-Z]*$)[a-zA-Z0-9]{7}$/;
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html(txErrorPwdMsg);//"密码需由八个字符组成：字母+数字，且首个字符必须是字母"
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			
			if(temp == "password-bf"){
				var ddindx = $eve.index(".password-bf");
				$passwords = $(".cpassword-f:eq("+ddindx+")");
				if(value != $passwords.val()){
					$error.addClass("red");
					$error.html("两次输入密码不一致!");
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			if(temp == "password-b"){
				var ddindx = $eve.index(".password-b");
				$passwords = $(".password-a:eq("+ddindx+")");
				if(value != $passwords.val()){
					$error.addClass("red");
					$error.html("两次输入密码不一致!");
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			if(temp == "password-e"){
				var myreg =  passwordRegex;
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html(errorPwdMsg);
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
				var ddindx = $eve.index(".password-e");
				$passwords = $(".password-a:eq("+ddindx+")");
				if(value == $passwords.val()){
					$error.addClass("red");
					$error.html("密码和交易密码输入不能一致!");
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			if(temp == "password-f"){
				var myreg =  passwordRegex;
				if(!myreg.test(value))
				{
					$error.addClass("red");
					$error.html(errorPwdMsg);
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
				
				var ddindx = $eve.index(".password-f");
				$passwords = $(".password-e:eq("+ddindx+")");
				if(value != $passwords.val()){
					$error.addClass("red");
					$error.html("两次输入交易密码不一致!");
					flg = false;
					flgs = false;
					$tip.hide();
					$error.show();
					return false;
				}
			}
			if(temp == "cpassword-b"){
				if($.trim(value)!=""){
					var myreg =  passwordRegex;
					if(!myreg.test(value))
					{
						$error.addClass("red");
						$error.html(errorPwdMsg);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
					}
					var ddindx = $eve.index(".cpassword-b");
					$passwords = $(".cpassword-a:eq("+ddindx+")");
					if(value != $passwords.val()){
						$error.addClass("red");
						$error.html("两次输入密码不一致!");
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
					}
				}
			}
			if(temp == "spassword-b"){
				if(value!=""){
					var myreg = passwordRegex;
					if(!myreg.test(value))
					{
						$error.addClass("red");
						$error.html(errorPwdMsg);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
					}
					var ddindx = $eve.index(".spassword-b");
					$passwords = $(".spassword-a:eq("+ddindx+")");
					if(value != $passwords.val()){
						$error.addClass("red");
						$error.html("两次输入密码不一致!");
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
					}
				}
			}
			if(temp.indexOf('max-length')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(value.length>tsize){
						$error.addClass("red");
						$error.html("超过输入限制"+tsize+"，当前长度为"+value.length);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
		}
	}
	if(mtest != undefined){
		if(mtest.length>0){
			var myreg = eval(mtest);  
			if(!myreg.test(value))
			{
				$error.addClass("red");
				$error.html($eve.attr("mtestmsg"));
				$tip.hide();
				$error.show();
				flg = false;
				flgs = false;
				return false;
			}
		}
	}

	var mntest = $eve.attr("mntest");
	if(mntest != undefined){
		if(mntest.length>0){
			var myreg = eval(mntest);  
			if(myreg.test(value))
			{
				$error.addClass("error_tip");
				$error.html($eve.attr("mntestmsg"));
				$tip.hide();
				$error.show();
				flg = false;
				flgs = false;
				return false;
			}
		}
	}

	if(!flg){
		$tip.hide();
		$error.show();
		return false;
	}else{
		$error.removeClass("red");
		$error.hide();
		$tip.show();
	}
}

//校验文本域
function checkTextarera($eve){
	if($eve.is(":hidden")){
		return;
	} 
	flg = true;
	if($eve== undefined){
		return false;
	}
	if($eve.attr("class")== undefined){
		return false;
	}
	msg = $eve.attr("class").split(" ");
	value = $eve.val();
	$error = $eve.nextAll("p[errortip]");
	$tip = $eve.nextAll("p[tip]");
	flg = true;
	for(var i=0;i<msg.length;i++){
		var temp = $.trim(msg[i]);
		if(temp.length>0){
			if(temp == "required"){
				if($.trim(value) == ""){
					$error.addClass("red");
					$error.html("不能为空！");
					$tip.hide();
					$error.show();
					flg = false;
					flgs = false;
					return false;
				}
			}
			if(temp.indexOf('max-length')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(value.length>tsize){
						$error.addClass("red");
						$error.html("超过输入限制"+tsize+"，当前长度为"+value.length);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
					}
				}
			}
			if(temp.indexOf('min-length')!=-1){
				tsizearry = temp.split("-");
				if(tsizearry.length>=3){
					tsize = tsizearry[2];
					if(value.length < parseInt(tsize)){
						$error.addClass("red");
						$error.html("小于输入限制"+tsize+"，当前长度为"+value.length);
						flg = false;
						flgs = false;
						$tip.hide();
						$error.show();
						return false;
						
					}
				}
			}
		}
	}
	if(!flg){
		$tip.hide();
		$error.show();
		return false;
	}else{
		$error.removeClass("red");
		$error.hide();
		$tip.show();
	}
}

function isIdCardNot(num,$error) {            
    num = num.toUpperCase();           //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。        
    if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num))) {     
        $error.addClass("red");
		$error.html('输入的身份证号不符合规定！');
        return false;         
    } //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
    //下面分别分析出生日期和校验位 
    var len, re; len = num.length; if (len == 15) { 
        re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/); 
        var arrSplit = num.match(re);  //检查生日日期是否正确
        var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3] + '/' + arrSplit[4]); 
        var bGoodDay; bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4]));
        if (!bGoodDay) {         
            $error.addClass("red");
    		$error.html('输入的身份证号里出生日期不对！');
            return false;
        } else { //将15位身份证转成18位 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。        
            var arrInt = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            var arrCh = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
            var nTemp = 0, i;            
            num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);           
            for(i = 0; i < 17; i ++) {                 
                nTemp += num.substr(i, 1) * arrInt[i];        
            }
            num += arrCh[nTemp % 11]; 
            return true;
        }
    }
    if (len == 18) {
        re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/); 
        var arrSplit = num.match(re);  //检查生日日期是否正确 
        var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/" + arrSplit[4]); 
        var bGoodDay; bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2])) && ((dtmBirth.getMonth() + 1) == Number(arrSplit[3])) && (dtmBirth.getDate() == Number(arrSplit[4])); 
        if (!bGoodDay) { 
            $error.addClass("red");
    		$error.html('输入的身份证号里出生日期不对！');
            return false; 
        }
        else { //检验18位身份证的校验码是否正确。 //校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。 
            var valnum; 
            var arrInt = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            var arrCh = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'];
            var nTemp = 0, i; 
            for(i = 0; i < 17; i ++) { 
                nTemp += num.substr(i, 1) * arrInt[i];
            } 
            valnum = arrCh[nTemp % 11]; 
            if (valnum != num.substr(17, 1)) { 
                $error.addClass("red");
        		$error.html('18位身份证号校验失败！');
                return false; 
            } 
            return true; 
        } 
    } return false;
}