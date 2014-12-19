<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  			관리자 로그인을 위한 db접근 부분	       	     /////
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
	//id와 pw를 받아온다
	$sql="select * from staff where ID='$id' and PW='$pw'";
	//관리자의 id, pw를 확인한다
	$result=mysql_query($sql);
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .="<node>\n";
	while($obj = mysql_fetch_object($result)) {
		$id = $obj->ID;
		printf("success");
		echo "<br \>";
		$xmlcode .="<id>$id</id>\n";
	}
	$xmlcode .="</node>\n";
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/login_adminresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>