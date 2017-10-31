package com.vertical.app.common.util;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ls on 10/31/17.
 */

public class InputUtil {
    /**
     * 计算给定的字符串长度
     *
     * @param str
     * @return
     */
    public static int count(String str) {
        int singelC = 0, doubleC = 0;
        String s = "[^\\x00-\\xff]";
        Pattern pattern = Pattern.compile(s);
        Matcher ma = pattern.matcher(str);

        while (ma.find()) {
            doubleC++;
        }
        singelC = str.length() - doubleC;
        if (singelC % 2 != 0) {
            doubleC += (singelC + 1) / 2;
        } else {
            doubleC += singelC / 2;
        }
        return doubleC;
    }

    /**
     * 验证Email地址格式是否合法
     *
     * @param line
     * @return
     */
    public static boolean isEmail(String line) {
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        Matcher m = p.matcher(line);
        return m.find();
    }

    /**
     * 验证是否是合法的手机号码格式
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        int l = mobile.length();
        boolean rs = false;
        switch (l) {
            case 11:
                if (matchingText("^(13[0-9]|14[5|7]|17[0-9]|15[0-9]|18[0-9])\\d{4,8}$", mobile)) {
                    rs = true;
                }
                break;
            default:
                rs = false;
                break;
        }
        return rs;
    }

    public static boolean matchingText(String expression, String text) {
        Pattern p = Pattern.compile(expression); // 正则表达式
        Matcher m = p.matcher(text); // 操作的字符串
        boolean b = m.matches();
        return b;
    }

    /**
     * 验证输入的字符串是否全部是0-9的数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(CharSequence str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * 验证是否是0-9的数字
     * @return
     */
    public static boolean isNumeric(Character c) {
        return Character.isDigit(c);
    }

    public static void hideSoftInputFromWindow(Activity act) {
        if (null == act || null == act.getCurrentFocus()) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), 0);
    }

    //显示虚拟键盘
    public static void showKeyboard(View v) {
        InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
    }

    //隐藏虚拟键盘
    public static void hideSoftInputFromUI(final View v) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
            }
        }, 10);
    }

    public static boolean hasUpperCase(String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断一个字符串是否含有数字
     *
     * @param content
     * @return
     */
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches())
            flag = true;
        return flag;
    }

    /**
     * 判断首字符的合法性
     * @param sequence
     * @return
     */
    public static boolean startWithLegal(CharSequence sequence){
        if (TextUtils.isEmpty(sequence)) return false;
        String firstLetter = sequence.subSequence(0,1).toString();
        String regex = "[a-zA-Z0-9_\\u4e00-\\u9fa5]";
        return firstLetter.matches(regex);
    }

    public static void assertMulti(String title, CharSequence value, Integer max, AssertType... assertType) throws InvalidInputException {
        assertMulti(title, value, max, null, assertType);
    }

    public static void assertMulti(String title, CharSequence value, Integer max, String error, AssertType... assertType) throws InvalidInputException {
        for (AssertType type : assertType) {
            switch (type) {
                case MAXIMUM_COUNT:
                    assertMaximumCharCount(title, value, max, error);
                    break;
                case TYPE_CLASS_PHONE_NUMBER:
                    break;
                case NOT_NULL:
                    assertNotNull(title, value, error);
                    break;
                case DIGIT:
                    assertDigitChars(title, value, error);
                    break;
                case FIRST_LETTER:
                    assertFirstLetter(title, value, error);
                    break;

            }
        }
    }

    public static void assertMaximumCharCount(String title, CharSequence value, Integer max, String errorMessage) throws InvalidInputException {
        if (value != null && max != null && max > 0 && value.length() > max) {
            throw new InvalidInputException(parserDefaultErrorMessage(errorMessage, String.format("%s最多%d个字", title, max)));
        }
    }

    public static void assertNotNull(String title, CharSequence value, String errorMessage) throws InvalidInputException {
        if (value == null || value.length() == 0) {
            throw new InvalidInputException(parserDefaultErrorMessage(errorMessage, String.format("%s必填", title)));
        }
    }
    public static void assertNotNull(String title, CharSequence value) throws InvalidInputException {
        assertNotNull(title,value,null);
    }

    public static void assertDigitChars(String title, CharSequence value, String errorMessage) throws InvalidInputException {
        if (!TextUtils.isEmpty(value) && InputUtil.isNumeric(value)) {
            throw new InvalidInputException(parserDefaultErrorMessage(errorMessage, String.format("%s只支持数字", title)));
        }
    }

    public static void assertFirstLetter(String title, CharSequence value, String errorMessage) throws InvalidInputException {
        if (!TextUtils.isEmpty(value) && !InputUtil.startWithLegal(value)) {
            throw new InvalidInputException(parserDefaultErrorMessage(errorMessage, String.format("%s不能以特殊符号开头", title)));
        }
    }

    private static String parserDefaultErrorMessage(String errorMessage, String byDefault) {
        return TextUtils.isEmpty(errorMessage) ? byDefault : errorMessage;
    }

    public static class InvalidInputException extends Exception {

        private final String errorMessage;

        public InvalidInputException(String errorMessage) {
            super(errorMessage);
            this.errorMessage = errorMessage;
        }
    }

    public enum AssertType {
        MAXIMUM_COUNT,
        TYPE_CLASS_PHONE_NUMBER,
        NOT_NULL,
        DIGIT,
        FIRST_LETTER
    }
}
