<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  			소비자 로그아웃을 위한 db접근 부분	       	     /////
	//////////////////////////////////////////////////////////	
	$hostname = "localhost";
	$username = "DB_ID";
	$password = "DB_PW";
	$connect = mysql_connect($hostname, $username, $password)
		or die("MySQL Server 연결실패");
	mysql_select_db("phs",$connect);
	mysql_query("set names utf8");
	$id=$_REQUEST['id'];
	//소비자의 id를 받아온다
	//////////////////////////////////////////////////////////
	/////  	매장에서 사용하는 시스템이기 때문에 로그아웃했다면,       	     /////
	/////  		물품구매를 완료한것으로 가정하고, 장바구니를 삭제한다.      /////
	//////////////////////////////////////////////////////////
	if($id!=null) {
		$sql="delete from bag where C_ID='$id'";
		//정상적인 입력일 경우 장바구니를 삭제한다
		$result=mysql_query($sql);
		$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
		$xmlcode .="<node>\n";
		$xmlcode .="<execution>execution!!</execution>\n";
		//정상적으로 동작함을 알려준다
		$xmlcode .="</node>\n";
	}
	else {
		$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
		$xmlcode .="<node>\n";
		$xmlcode .="<fail>fail!!</fail>\n";
		//입력이 실패했음을 알려준다.
		$xmlcode .="</node>\n";
	}
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/logoutresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>