package logic;

public class ConvertLogic {
	public String getTimeSum(int timeSum){
	//timeの合計を時間単位に変換して保存する
    int hours = timeSum / 60;
    int minutes = timeSum % 60;
    String convertedTimeSum=hours+"時間"+minutes+"分";
    return convertedTimeSum;
	}
	
	public double getRoundedNum(double num) {
	//numの値を小数点第2位で四捨五入する
		double numTimesTen=num * 10;
		double roundNum=Math.round(numTimesTen);
		double roundedNum=roundNum/10;
		return roundedNum;
	}
}
