package gui;

import java.awt.Color;

public class StatusFlag{
	private static int//优先级从高到低
			isOffline = 0,
			isError = 0,
			isWarning = 0,
			isPending = 0;
	private static  boolean	isOk = false;
	/*private static int//优先级从高到低
			countOffline = 0,
			countError = 0,
			countWarning = 0,
			countPending = 0,
			countOk = 0;
	int topCount = 0;*/
	
	public static Color getHighestStatus(){
		if(isOffline > 0){
			isOffline --;
			return MyColor.color_offline;
		}
		if(isError > 0){
			isError --;
			return MyColor.color_error;
		}
		if(isWarning > 0){
			isWarning --;
			return MyColor.color_warning;
		}
		if(isPending > 0){
			isPending --;
			return MyColor.color_pending;
		}
		if(isOk){
			return MyColor.color_ok;
		}
		else{
			return MyColor.color_offline;
		}
	}

	public static int getIsOffline() {
		return isOffline;
	}

	public static void setIsOffline(int isOffline) {
		StatusFlag.isOffline = isOffline;
	}

	public static int getIsError() {
		return isError;
	}

	public static void setIsError() {
		StatusFlag.isError++;
	}

	public static int getIsWarning() {
		return isWarning;
	}

	public static void setIsWarning() {
		StatusFlag.isWarning++;
	}

	public static int getIsPending() {
		return isPending;
	}

	public static void setIsPending() {
		StatusFlag.isPending++;
	}

	public static boolean isOk() {
		return isOk;
	}

	public static void setOk(boolean isOk) {
		StatusFlag.isOk = isOk;
	}

}
