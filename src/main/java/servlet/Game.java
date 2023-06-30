package servlet;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Game
 */
@WebServlet("/Game")
public class Game extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// リクエストパラメータを取得
		request.setCharacterEncoding("UTF-8");
	    String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		String num3 = request.getParameter("num3");
		String lCount = request.getParameter("lCount");
		int int_lCount = Integer.parseInt(lCount);
		//numをstr型からint型へ
		int n1 = Integer.parseInt(num1);
		int n2 = Integer.parseInt(num2);
		int n3 = Integer.parseInt(num3);
		
		
		// リクエストパラメータをチェック
		String errorMsg = "";
		String msg ="";
		String filePath = "/Users/maho/Desktop/Game/game.txt";
		//String filePath = "/Game/game";
		//ランダムで正解の配列を生成する
		 int[] correct = new int[3];
		 String[] correct_arr = new String[correct.length];
		 String correct_str ="";
		 String[] correct_str_arr = new String[3];
		 
	 if(int_lCount==1) {
		// 乱数の準備
			Random rand = new Random();
			// 重複するかどうかの変数(true:重複しない)
			boolean isUnique = true;
			// 格納した個数の初期化
			int count = 0;
		 
			// 個数が5未満の間繰り返す
			while (count < 3) {
		 
			// 乱数(0～9)を1つ作成
			int value = rand.nextInt(10);
		 
			// 作成した乱数が配列内にすでにあるかのチェック
			for (int i = 0; i < count; i++) {
		 
				// 配列内の値と乱数が等しい(重複している)かどうか
				if (correct[i] == value) {
				isUnique = false; // 重複していればfalse
				break; // 残りのチェックを飛ばす
				} else {
				isUnique = true; // 重複していなければtrue
				}
				}
		 
				// もし、重複していない場合
				if (isUnique == true) {		 
				// 配列に乱数を格納
				correct[count] = value;		 
				// 個数を1加算
				count++;		 
				}
			}
			//配列を文字列に変換する
			 
	        for (int i = 0; i < correct.length; i++) {
	        	//int型の配列をString型に
	            correct_arr[i] = String.valueOf(correct[i]);
	           
	        }
			correct_str = String.join(",", correct_arr);
			
	 }
		
		int Hit =0;
		int Blow =0;
	
		 if (num1 == null || num2 == null || num3 == null) {
			 errorMsg += "1番目、２番目、３番目の数字を入力してください<br>";
		 }else if(n1 > 10 || n2 > 10||n3>10) {
			 errorMsg +="数字を0~9までの数字で入力してください";
		 }else{
			 int[]ans = {n1,n2,n3};//入力された値を格納する
			 int hit=0,blow=0;
			 //１行目の処理はそのままファイルを読み込まずに
			if(int_lCount==1) {

			 for(int o=0;o<3;o++) {
				 if(ans[o] == correct[o]) {
					 hit++;
					 Hit = hit;
				 }else {
					 for(int q=0;q<3;q++) {
						 if(ans[o]==correct[q]) {
							 blow++;
					Blow=blow;
						 }
					 }
				 }
				 
			 }
			 }else {
				 try{FileReader fr = new FileReader( filePath );
				 BufferedReader br = new BufferedReader( fr );
				 correct_str = br.readLine();
				 br.close();
				 //カンマ区切りの文字列strをカンマで区切り配列correct_str_arrに格納
				 correct_str_arr = correct_str.split(",");
				 //String配列のcorrect_str_arrをint型に配列のまま変換
				 for(int i=0;i<correct_str_arr.length;i++) {
					 correct[i] = Integer.parseInt(correct_str_arr[i]);
				 }
				 
				 for(int o=0;o<3;o++) {
					 if(ans[o] == correct[o]) {
						 hit++;
						 Hit = hit;
					 }else {
						 for(int q=0;q<3;q++) {
							 if(ans[o]==correct[q]) {
								 blow++;
						Blow=blow;
							 }
						 }
					 }
					 
				 }
			 }catch( FileNotFoundException e){
				 System.out.println(filePath+"が見つかりません");
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println(filePath+"の読み込みに失敗しました");
					e.printStackTrace();
				
			 }
				 }
			 
			 
			 if(hit==3) {//解答を抜ける
				msg += "<b>Nice Hit!</b><br>正解:"+correct_str;
			 }else {
				 //ファイルに書きこみ
				 FileWriter writer = new FileWriter(filePath,true);
				 if(int_lCount==1) {writer.write(correct_str+"\n");}
				 writer.write(lCount+"回目の結果<br>"+num1+" "+num2+" "+num3+"<br>Hit:"+hit+"blow:"+blow+"<br>\n");
				
				 
				 writer.close();				
				 
			 }
			 
		 }
		
			 
		//htmlを出力
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"UTF-8\">");
			out.println("<title>Hit＆Blow</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Hit&Blow</h1>");
			out.println("<div style=\"text-align: center\"><b>~結果~</b>");
			out.println("<p>"+int_lCount+"回目の結果</p>");
			out.println("<p>あなたの回答は"+num1+","+num2+","+num3+"</p>");
			if(Hit==3) {
				out.println("<p>"+msg+"</p>"); 
				out.println("正解おめでとう！<br>もっと少ない回数で正解できるかな？挑戦しよう！<br>");
			}else {
			out.println("<p>Hit:"+Hit+"Blow:"+Blow+"</p>");
			out.println("<p>もう一回チャレンジしてみよう！</p></div>");
			
			out.println("<p>"+correct_str+"こたえ<br></p>");
			out.println("<div style=\"text-align:center\"><button onclick=\"location.href='http://localhost:8080/game0519/gameForm.jsp'\"style=\"width:100px;height:40px\" >もう一度挑戦！</button></div>");
			out.println("</body>");
			out.println("</html>");
			out.println();
			
			
		}}
	}
	


