<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  장바구니 버튼 클릭시 연결을 위한 db접근 부분(로그인 소비자용)       /////
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
	$sql="select b.name, a.quantity, (b.price * a.quantity) k, b.ID, b.price, b.L_ID
			from bag a join stuff b
			on a.S_ID = b.ID where C_ID='$id'";
	//현재 db에 저장되어있는 내용을 꺼내온다
	$result=mysql_query($sql);
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .="<node>\n";
	//xml시작부분
	while($obj = mysql_fetch_object($result)) {
		$sid = $obj->ID;
		$sname = $obj->name;
		$sprice = $obj->price;
		$squantity = $obj->quantity;
		$l_id = $obj->L_ID;
		$btotal = $obj -> k;
		
		printf("success");
		echo "<br \>";
		
		$xmlcode .="<id>$sid</id>\n";
		$xmlcode .="<name>$sname</name>\n";
		$xmlcode .="<price>$sprice</price>\n";
		$xmlcode .="<quantity>$squantity</quantity>\n";
		$xmlcode .="<l_id>$l_id</l_id>\n";
		$xmlcode .="<total>$btotal</total>\n";
		//실제 정보가 되는 xml
	}
	$xmlcode .="</node>\n";
	//xml 끝부분
	$dir = "C:\apache\Apache2\htdocs\PHS";
	//php파일이 저장되는 경로
	$filename = $dir."/bagresult.xml";
	//xml파일이 저장되는 경로
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>