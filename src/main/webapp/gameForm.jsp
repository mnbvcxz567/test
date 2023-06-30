<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import=
"java.io.FileNotFoundException,
 java.io.FileReader,
 java.io.IOException,
 java.io.BufferedReader"
 %>
 <%String filePath = "/Users/maho/Desktop/Game/game.txt";
 String log = "";
 String st ="";
 long lCount = 1;
	try {// ファイルを読み込みモードでオープンする
		FileReader fileReader = new FileReader(filePath);
		// BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		// ファイルの終わりになるまで処理を繰り返す
		bufferedReader.readLine();//ログに不要な１行目を取り込む
		while ((st = bufferedReader.readLine()) != null) {
			log += st;
			// 1行読み込むに成功するたびに、行数のカウントを1増やす。
         lCount++;
           
		}

	//最後にファイルを閉じてリソースを開放する
		bufferedReader.close(); 
		if(log == null){
			log = "";
		}
	} catch (FileNotFoundException e) {
		System.out.println(filePath+"が見つかりません");
		e.printStackTrace();
	} catch (IOException e) {
		System.out.println(filePath+"の読み込みに失敗しました");
		e.printStackTrace();
	}
	%>
    <!--入力画面  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hit＆Blow</title>
</head>
<body>
<form action = "Game" method = "get"><!-- サーブレットと揃える -->
<h1>Hit&Blow</h1>
<div style="text-align: center"><b>~Hit&Blowの遊び方~</b><br>
・０〜９の数字を一つずつ入れよう!※空白が入らないように気をつけてね！<br>
・同じ数字が入ることはないよ！半角の数字で入力してね！<br>
・HitとBlowの数から答えを推測しよう！<br><br>
<b>~これまでの履歴~</b><br>
<p><%= log %></p><br>
Hit：場所も数字も正解！Blow：数字は正解！場所を入れ替えてみよう！<br>
現在：<%= lCount %>回目<br>
数字１：<input type="text" name= "num1"><br>
数字２：<input type="text" name= "num2"><br>
数字３：<input type="text" name= "num3"><br>
<input type="hidden" name="lCount" value="<%= lCount %>"><br></div>
<div style="text-align:center"><input type="submit" value="OK!" style="width:100px;height:40px" ></div>
</form>
</body>
</html>