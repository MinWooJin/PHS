<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	 //////////////////////////////////////////////////////////
	/////  관리자용 알림(수량,유통기한을 확인해 기준보다 미달일경우 알림표시기능) /////
   //////////////////////////////////////////////////////////

	$hostname = "localhost";
	$username = "DB_ID";
	$password = "DB_PW";
			
	$connect = mysql_connect($hostname, $username, $password)
		or die("MySQL Server 연결실패");
	mysql_select_db("phs",$connect);
	mysql_query("set names utf8");
	
	$sql="select ID, Name, quantity, expiration from stuff";
	//쿼리문
	$result=mysql_query($sql);
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .="<node>\n";
	//xml의 시작
	while($obj = mysql_fetch_object($result)) {
		$sid = $obj->ID;
		$sname = $obj->Name;
		$squantity = $obj->quantity;
		$sexpiration = $obj->expiration;
		printf("success");
		echo "<br \>";
		//db연결 확인, php page에 success가 출력되면 db연동이 완료된것이다
	
		$xmlcode .="<id>$sid</id>\n";
		$xmlcode .="<name>$sname</name>\n";
		$xmlcode .="<quantity>$squantity</quantity>\n";
		$xmlcode .="<expiration>$sexpiration</expiration>\n";
		//실제 xml에서 정보를 의미하는 부분
	}
	$xmlcode .="</node>\n";
	//xml의 끝
	$dir = "C:\apache\Apache2\htdocs\PHS";
	//php파일이 저장될 경로
	$filename = $dir."/alram_adminresult.xml";
	//xml파일이 저장될 이름
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>