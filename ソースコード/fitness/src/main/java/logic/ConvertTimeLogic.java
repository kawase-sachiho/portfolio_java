package logic;

public class ConvertTimeLogic {
	public String getTimeSum(int timeSum){
	//time1の合計を時間単位に変換して保存する
    int hours = timeSum / 60;
    int minutes = timeSum % 60;
    String convertedTimeSum=hours+"時間"+minutes+"分";
    return convertedTimeSum;
	}
}
