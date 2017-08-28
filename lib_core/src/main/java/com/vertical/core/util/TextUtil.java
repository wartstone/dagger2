package com.vertical.core.util;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 *
 * <p>Description: </p>
 * <p>Company: HeNong.Co.Ltd</p>
 * @author Ralken Liao
 * @date 2016-9-26 下午1:15:29
 */
public class TextUtil {

	/** Check the given strings are not null or emtpy */
    public static boolean isValidate(String... values) {
        if (values == null) {
            return false;
        }
        for (String v : values) {
            if (TextUtils.isEmpty(v)) {
                return false;
            }
        }
        return true;
    }

	public static String getDisplayedDouleOfString(String value){
		return TextUtil.getDoubleFormat(TextUtil.parseDoubleFromString(value));
	}

	public static String getDoubleFormat(Double value) {
		return getDoubleFormat(value, 2);
	}

	public static String getDoubleFormat(Double value, boolean stripZeros) {
		return formatDouble1(value, 2, stripZeros);
	}

	public static String getDoubleFormat(Double value, int digits) {
		try {
			if (value != null) {
				return formatDouble1(value, digits, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getDoubleFormat2(Double value) {
		String result = getDoubleFormat(value, 2);

		if (TextUtil.isValidate(result)) {
			if (!result.contains(".")) {
				result += ".0";
			}
		}
		return result;
	}

	/**
	 * The BigDecimal class provides operations for arithmetic, scale
	 * manipulation, rounding, comparison, hashing, and format conversion.
	 *
	 * @param d
	 * @return
	 */
	private static String formatDouble1(double d, int digits, boolean stripZeros) {
		float offset = (float) (0.5f * Math.pow(10f, -digits));
		if (d < 0) {
			offset *= -1;
		}
		BigDecimal bg = new BigDecimal(d + offset).setScale(digits, RoundingMode.DOWN);
		if (stripZeros) {
			return bg.stripTrailingZeros().toPlainString();
		}
		return bg.toPlainString();
	}

	public static Double parseDoubleFromString(String value){
		try {
			if(TextUtil.isValidate(value)){
				return Double.parseDouble(value);
			}
		} catch (Exception e) {
			return 0d;
		}

		return 0d;
	}


	public static String getValidString(String obj) {
		return getValidString(obj, "");
	}

	public static String getValidString(String obj, String defaultStringRes) {
		if (isValidate(obj)) {
			return obj;
		} else if (defaultStringRes != null) {
			return defaultStringRes;
		}
		return "";
	}

	public static boolean isAllEmpty(String... strings) {
		for (String string : strings) {
			if (!TextUtils.isEmpty(string)) {
				return false;
			}
		}
		return true;
	}
	public static boolean isAnyEmpty(String... strings) {
		for (String string : strings) {
			if (TextUtils.isEmpty(string)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * check the input string is all white space
	 * @param string
	 * @return
	 */
	public static boolean isBlankString(String string) {

		if (TextUtils.isEmpty(string)) {
			return false;
		}

		char[] ch = string.toCharArray();
		for (char c : ch) {
			if (!Character.isWhitespace(c)) {
				return false;
			}
		}
		return true;
	}

	public static String joinArr(String[] selectColumns, String defaultValue){
		String columnsStmt = defaultValue;
		if (null != selectColumns && selectColumns.length > 0) {
			StringBuilder sub = new StringBuilder();
			for (int i = 0; i < selectColumns.length; i++) {
				sub.append(selectColumns[i]);
				if (i != selectColumns.length - 1) {
					sub.append(",");
				}
			}
			columnsStmt = sub.toString();
		}
		return columnsStmt;
	}

    public static String getConcatString(String... strings) {
        StringBuffer buffer = new StringBuffer();
        for (String s : strings) {
            buffer.append(s);
        }
        return buffer.toString();
    }

	public static String trimPortForIpAddress(final String ip) {
		if (isValidate(ip) && ip.contains(":")) {
			return ip.substring(0, ip.indexOf(":"));
		}
		return ip;
	}

	public static String parseMoneyString(String amount){
		if(amount == null || amount.length() <= 10){
			return getDisplayedDouleOfString(amount == null ? "0" : amount);
		} else {
			return amount.substring(0,10) + "...";
		}
	}

	/**
	 * 保留两位其他都舍去
	 * @param amount
	 * @return
	 */
	public static String formatMoney(double amount){
		BigDecimal bigDecimal = new BigDecimal(amount);
		DecimalFormat format = new DecimalFormat("#,##0.00");

		return format.format(bigDecimal.setScale(2,BigDecimal.ROUND_DOWN).doubleValue());
	}

	public static SpannableStringBuilder getSpannableString(Context context,
															String src1, String src2, String src3, String src4 ,
															int style1, int style2, int style3,int style4) {
		SpannableStringBuilder spanStr = null;
		String src = src1 + src2 + src3+src4;
		int length1 = src1.length();
		int length12 = src1.length() + src2.length();
		int length123 = src1.length() + src2.length()+src3.length();
		int lengthAll = src.length();
		if (context != null) {
			spanStr = new SpannableStringBuilder(src);
			if (0 != length1) {
				spanStr.setSpan(new TextAppearanceSpan(context, style1), 0, length1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (length1 != length12) {
				spanStr.setSpan(new TextAppearanceSpan(context, style2), length1, length12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (length12 != length123) {
				spanStr.setSpan(new TextAppearanceSpan(context, style3), length12, length123, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (length123 != lengthAll) {
				spanStr.setSpan(new TextAppearanceSpan(context, style4), length123, lengthAll, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return spanStr;
	}
	/**
	 *用于三段式
	 */
	public static SpannableStringBuilder getSpannableString(Context context,String src1, String src2,
															String src3, int style1, int style2, int style3) {
		SpannableStringBuilder spanStr = null;
		String src = src1 + src2 + src3;
		int length1 = src1.length();
		int length12 = src1.length() + src2.length();
		int lengthAll = src.length();
		if (context != null) {
			spanStr = new SpannableStringBuilder(src);
			if (0 != length1) {
				spanStr.setSpan(new TextAppearanceSpan(context, style1), 0, length1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (length1 != length12) {
				spanStr.setSpan(new TextAppearanceSpan(context, style2), length1, length12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (length12 != lengthAll) {
				spanStr.setSpan(new TextAppearanceSpan(context, style3), length12, lengthAll, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return spanStr;
	}

	public static SpannableStringBuilder getSpannableString(Context context,String src1, String src2, int style1, int style2) {
		SpannableStringBuilder spanStr = null;
		String src = src1 + src2;
		int length1 = src1.length();
		int lengthAll = src.length();
		if (context != null) {
			spanStr = new SpannableStringBuilder(src);
			if (0 != length1) {
				spanStr.setSpan(new TextAppearanceSpan(context, style1), 0, length1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
			if (lengthAll != length1) {
				spanStr.setSpan(new TextAppearanceSpan(context, style2), length1, lengthAll, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		return spanStr;
	}

}
