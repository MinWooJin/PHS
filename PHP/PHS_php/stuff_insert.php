<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  물품추가 기능을 위한 db접근 부분(관리자용)           	     /////
	//////////////////////////////////////////////////////////	
	$hostname = "localhost";
	$username = "DB_ID";
	$password = "DB_PW";
	$connect = mysql_connect($hostname, $username, $password)
		or die("MySQL Server 연결실패");
	mysql_select_db("phs",$connect);
	mysql_query("set names utf8");
	$id=$_REQUEST['id'];
	$name=$_REQUEST['name'];
	$kind=$_REQUEST['kind'];
	$price=$_REQUEST['price'];
	$cost=$_REQUEST['cost'];
	$quantity=$_REQUEST['quantity'];
	$stock=$_REQUEST['stock'];
	$date=$_REQUEST['date'];
	$expiration=$_REQUEST['expiration'];
	$l_id=$_REQUEST['l_id'];
	//입력한 물품정보를 받아온다
	if($id!=null) { 
		$sql="insert into stuff values('$id','$name','$kind','$price','$cost',
						'$quantity','$stock','$date','$expiration','$l_id')";
		//정상적으로 입력했다면 등록한다
		$result=mysql_query($sql);
	}
	$sql2="select * from stuff where ID='$id'";
	$result2=mysql_query($sql2);
	//등록되었는지 id로 검색해서 찾아본다
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .="<node>\n";
	while($obj = mysql_fetch_object($result2)) {
		$sid = $obj->ID;
		printf("success");
		echo "<br \>";
		$xmlcode .="<id>$sid</id>\n";
	}
	$xmlcode .="</node>\n";
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/stuff_insertresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>