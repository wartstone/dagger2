package com.vertical.core.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Utils {

	/** 获得当前版本信息 */
	public static String getCurrentVersion(Context c) {
		try {
			PackageInfo info = c.getPackageManager().getPackageInfo(c.getPackageName(), 0);
			return info.versionName;
		} catch (NameNotFoundException e) {
			return "1.0";
		}
	}

	/**
	 * 获取应用的版本号
	 */
	public static long getVersionCode(Context context) {
		PackageInfo mPackageInfo = null;
		try {
			mPackageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return mPackageInfo.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return 1l;
		}
	}

	public static void copyToClipBoard(Context context, String content) {
		ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
		ClipData clip = ClipData.newPlainText(content, content);
		clipboard.setPrimaryClip(clip);
	}

	@SuppressWarnings("deprecation")
	public static String getClipBoardContent(Context context) {
		ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
		return (String) clipboard.getText();
	}

	/**
	 * 获取状态栏高度
	 *
	 * @param context
	 * @return
	 */
	public static int getStateHeight(Context context) {
		Class<?> localClass;
		try {
			localClass = Class.forName("com.android.internal.R$dimen");
			Object localObject = localClass.newInstance();
			int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());

			return context.getResources().getDimensionPixelSize(i5);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 隐藏键盘
	 */
	public static void hideSoftKeyBoard(Context context, EditText editText) {
		InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

	public static boolean isZHLanguage() {
//		return getApplication().getResources().getConfiguration().locale.getCountry().equals("CN");
		return false;
	}

	public static String getCurProcessName(Context context) {
		int pid = android.os.Process.myPid();
		ActivityManager mActivityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
				.getRunningAppProcesses()) {
			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

	/**
	 * ISO 639-1 standart, refer to: <br>
	 * https://en.wikipedia.org/wiki/ISO_639-1 <br>
	 * The returned language code is like "en", "fr", "sp", "ar" .... and so on.
	 *
	 * @return
	 */
	public static String getSystemLanguage() {
		String language = Locale.getDefault().getLanguage();
		return TextUtil.isValidate(language) ? language : "en";
	}

	public static int getRandomInt() {
		int max=2;
        int min=1;
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}

	public static boolean equals(Object object1, Object object2) {
		return object1 == object2?true:(object1 != null && object2 != null?object1.equals(object2):false);
	}

	public static boolean notEqual(Object object1, Object object2) {
		return !equals(object1, object2);
	}

	public static String StringFilter(String str)throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; //要过滤掉的字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 截取数据
	 * @param input
	 * @return
	 */
	public static String splitNumber(String input){
		String regEx="[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(input);
		return m.replaceAll("").trim();
	}

	/**
	 * 快速排序（选择一个值作为KEY,然后将大于KEY的值移动到右边，将小于key的值移动到左边）
	 * @param arr
	 * @param lowIndex
	 * @param highIndex
	 */
	public static void getQuickSort(int[] arr,int lowIndex,int highIndex){
		int low = lowIndex,high = highIndex;
		while(low < high){
			while(high > low && arr[high] >= arr[low]){
				high--;
			}
			if(high > low){
				int temp = arr[high];
				arr[high] = arr[low];
				arr[low] = temp;
			}
			while(low < high && arr[low] < arr[high]){
				low++;
			}
			if(low < high){
				int temp = arr[low];
				arr[low] = arr[high];
				arr[high]= temp;
			}
		}
		if(high > lowIndex){
			getQuickSort(arr,lowIndex,--high);
		}
		if(low < highIndex){
			getQuickSort(arr,++low,highIndex);
		}

	}

	public static int[] toPrimitive(Integer[] IntegerArray) {

		int[] result = new int[IntegerArray.length];
		for (int i = 0; i < IntegerArray.length; i++) {
			result[i] = IntegerArray[i].intValue();
		}
		return result;
	}

	/**
	 * 警告不能改变数组的顺序
	 * @return
	 */
	public static String[] getPayMode(){
		List<String> payModes=new ArrayList();
		payModes.add("现金");
		payModes.add("赊账");
//		payModes.add("预存款");
//		payModes.add("货到收款");
		payModes.add("银行卡支付");
		payModes.add("货到付款");
		String[] objects = (String[])payModes.toArray(new String[payModes.size()]);
		return objects;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

}
