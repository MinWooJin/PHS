<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  장바구니 삽입시 연결을 위한 db접근 부분(로그인 소비자용)          /////
	//////////////////////////////////////////////////////////	
	$hostname = "localhost";
	$username = "DB_ID";
	$password = "DB_PW";
	$connect = mysql_connect($hostname, $username, $password)
		or die("MySQL Server 연결실패");
	mysql_select_db("phs",$connect);
	mysql_query("set names utf8");
	$uid=$_REQUEST['uid'];
	$sid=$_REQUEST['sid'];
	$quantity=$_REQUEST['quantity'];
	//사용자 id, 물품 id, 입력한 수량을 받아온다
	if($quantity!=null) { 
		$sql="insert into bag value('$uid','$sid','$quantity')";
		//수량을 정상적으로 입력했다면 장비구니 db에 추가한다
		$result=mysql_query($sql);
	}
	$sql2="select * from bag where C_id='$uid' and S_ID='$sid'";
	$result2=mysql_query($sql2);
	//정상적으로 동작했다면 장바구니에 물품id가 담겨있어야 한다
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .="<node>\n";
	while($obj = mysql_fetch_object($result2)) {
		$sid = $obj->S_ID;
		printf("success");
		echo "<br \>";
		$xmlcode .="<id>$sid</id>\n";
	}
	$xmlcode .="</node>\n";
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/baginsertresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>