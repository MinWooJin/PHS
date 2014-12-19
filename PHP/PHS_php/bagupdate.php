<html>
<head><meta http-equiv="Contect-type" content="text/html" charset="utf-8"></head>
<body>
<?php
	//////////////////////////////////////////////////////////
	/////  장바구니 수정시 연결을 위한 db접근 부분(로그인 소비자용)          /////
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
	if($quantity!=null) { 
		$sql="update bag set quantity='$quantity'
			  where C_ID='$uid' and S_ID='$sid'";
		//사용자가 정상적으로 입력했다면 update 한다
		$result=mysql_query($sql);
		$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
		$xmlcode .="<node>\n";
		$xmlcode .="<execution>execution!!</execution>\n";
		$xmlcode .="</node>\n";
	}
	else {
		$xmlcode = "<?xml version = \"1.0\" encoding =\"utf-8\"?>\n";
		//정상적으로 입력하지 않았을 경우엔 실패라고 알려준다.
		$xmlcode .="<node>\n";
		$xmlcode .="<fail>fail!!</fail>\n";
		$xmlcode .="</node>\n";
	}
	$dir = "C:\apache\Apache2\htdocs\PHS";
	$filename = $dir."/bagupdateresult.xml";
	file_put_contents($filename, $xmlcode);
	mysql_close($connect);
?>
</body>
</html>