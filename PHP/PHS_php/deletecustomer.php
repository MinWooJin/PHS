<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  회원탈퇴 기능을 위한 db접근 부분(로그인 소비자용)       	     /////
	//////////////////////////////////////////////////////////	
	$hostname = "localhost";
	$username = "DB_ID";
	$password = "DB_PW";
	$connect = mysql_connect($hostname, $username, $password)
		or die("MySQL Server 연결실패");
	mysql_select_db("phs",$connect);
	mysql_query("set names utf8");
	$id=$_REQUEST['id'];
	$pw=$_REQUEST['password'];
	//id와 pw를 다시한번 입력하게 한다
	if($id!=null) { 
		$sql="delete from customer where C_ID='$id' and PW='$pw'";
		//입력이 정상적으로 이루어졌다면 삭제한다
		$result=mysql_query($sql);
		$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
		$xmlcode .="<node>\n";
		$xmlcode .="<execution>execution!!</execution>\n";
		$xmlcode .="</node>\n";
	}
	else {
		$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
		$xmlcode .="<node>\n";
		$xmlcode .="<fail>fail!!</fail>\n";
		$xmlcode .="</node>\n";
	}
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/deletecustomerresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>