<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  			관리자 물품검색을 위한 db접근 부분	       	     /////
	//////////////////////////////////////////////////////////	
	$hostname = "localhost";
	$username = "DB_ID";
	$password = "DB_PW";
	$connect = mysql_connect($hostname, $username, $password)
		or die("MySQL Server 연결실패");
	mysql_select_db("phs",$connect);
	mysql_query("set names utf8");
	$name=$_REQUEST['name'];
	//품명을 받아온다
	$sql="select * from stuff where name like '%$name%' or kind like '%$name%'";
	//관리자는 물품의 모든정보를 찾는다
	$result=mysql_query($sql);
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .="<node>\n";
	while($obj = mysql_fetch_object($result)) {
		$sid = $obj->ID;
		$sname = $obj->Name;
		$skind = $obj->kind;
		$sprice = $obj->price;
		$scost = $obj->cost;
		$squantity = $obj->quantity;
		$sstock = $obj->stock;
		$sdate = $obj->date;
		$sexpiration = $obj->expiration;
		$l_id = $obj->L_ID;
		printf("success");
		echo "<br \>";
		$xmlcode .="<id>$sid</id>\n";
		$xmlcode .="<name>$sname</name>\n";
		$xmlcode .="<kind>$skind</kind>\n";
		$xmlcode .="<price>$sprice</price>\n";
		$xmlcode .="<cost>$scost</cost>\n";
		$xmlcode .="<quantity>$squantity</quantity>\n";
		$xmlcode .="<stock>$sstock</stock>\n";
		$xmlcode .="<date>$sdate</date>\n";
		$xmlcode .="<expiration>$sexpiration</expiration>\n";
		$xmlcode .="<l_id>$l_id</l_id>\n";
	}
	$xmlcode .="</node>\n";
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/search_adminresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>