<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  장바구니 삭제시 연결을 위한 db접근 부분(로그인 소비자용)          /////
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
	//사용자 id와 물품 id를 받아온다
	if($uid!=null) { 
		$sql="delete from bag where C_ID='$uid' and S_ID='$sid'";
		//사용자가 입력을 재대로 했다면 삭제한다
		$result=mysql_query($sql);
	}
	$sql2="select * from customer where S_ID='$sid'";
	$result2=mysql_query($sql2);
	$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
	$xmlcode .="<node>\n";
	if ($result2==null) {
		$xmlcode .="<execution>execution!!</execution>\n";
		//실제 삭제가 정상적으로 이루어졌다면 쿼리2의 결과는 null이여야 한다
	}
	else {
		while($obj = mysql_fetch_object($result2)) {
			$sid = $obj->S_ID;
			printf("fail");
			echo "<br \>";
			$xmlcode .="<id>$sid</id>\n";
		}
	}
	$xmlcode .="</node>\n";
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/bagdeleteresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>