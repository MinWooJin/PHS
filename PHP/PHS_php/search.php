<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  			소비자 물품검색을 위한 db접근 부분	       	     /////
	//////////////////////////////////////////////////////////
	$hostname = "localhost";
	$username = "DB_ID";
	$password = "DB_PW";
	$connect = mysql_connect ( $hostname, $username, $password ) or die ( "MySQL Server 연결실패" );
	mysql_select_db ( "phs", $connect );
	mysql_query ( "set names utf8" );
	$name = $_REQUEST ['name'];
	$sql = "select ID, Name, price, quantity, L_ID from stuff
				where name like '%$name%' or kind like '%$name%'";
	//소비자는 물품의 id, name, 가격, 수량, 위치를 필요로 한다
	//echo ($sql);
	$result = mysql_query ( $sql );
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .= "<node>\n";
	while ( $obj = mysql_fetch_object ( $result ) ) {
		$sid = $obj->ID;
		$sname = $obj->Name;
		$sprice = $obj->price;
		$squantity = $obj->quantity;
		$l_id = $obj->L_ID;
		printf ( "success" );
		echo "<br \>";
		$xmlcode .= "<id>$sid</id>\n";
		$xmlcode .= "<name>$sname</name>\n";
		$xmlcode .= "<price>$sprice</price>\n";
		$xmlcode .= "<quantity>$squantity</quantity>\n";
		$xmlcode .= "<l_id>$l_id</l_id>\n";
	}
	$xmlcode .= "</node>\n";
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir . "/searchresult.xml";
	file_put_contents ( $filename, $xmlcode );
	mysql_close ( $connect );
?>
</body>
</html>