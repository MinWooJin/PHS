<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  회원가입 기능을 위한 db접근 부분(로그인 소비자용)       	     /////
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
	$name=$_REQUEST['name'];
	$phone=$_REQUEST['phone'];
	$birthday=$_REQUEST['birthday'];
	//입력한 회원정보를 받아온다
	if($id!=null && $pw!=null && $name!=null && $phone!=null && $birthday!=null) { 
		$sql="insert into customer value('$id','$pw','$name','$phone','$birthday','10')";
		//정상적으로 입력했다면 등록한다
		$result=mysql_query($sql);
	}
	$sql2="select * from customer where C_ID='$id'";
	$result2=mysql_query($sql2);
	//등록되었는지 id로 검색해서 찾아본다
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .="<node>\n";
	while($obj = mysql_fetch_object($result2)) {
		$uid = $obj->C_ID;
		printf("success");
		echo "<br \>";
		$xmlcode .="<id>$uid</id>\n";
	}
	$xmlcode .="</node>\n";
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/joinresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>