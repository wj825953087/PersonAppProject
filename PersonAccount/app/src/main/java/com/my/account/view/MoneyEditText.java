package com.my.account.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;


public class MoneyEditText extends AppCompatEditText {
    private static final String TAG = "MoneyEditText";
    private boolean textChange;
    public MoneyEditText(Context context) {
        super(context);

    }

    public MoneyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MoneyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置可以输入小数,数字文本,带符号的数字
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        setFocusable(true);
        setFocusableInTouchMode(true);
        //监听文字变化
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 判断第一位输入是否是 .
                if(s.toString().startsWith(".")){
                    s="0"+s;
                    setText(s);
                    if(s.toString().length()==2)setSelection(s.length());
                    return;
                }
                //判断首位是否是0
                if (s.toString().startsWith("0")&&s.toString().length()>1){
                    //判断第二位是不是.
                    if(!s.toString().substring(1,2).equals(".")){
                        s=s.toString().substring(1,s.toString().length());
                        setText(s);
                        setSelection(s.length());
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!textChange) {
                    restrictText();
                }
                textChange = false;
            }
        });
    }

    /**
     * 将小数限制为2位
     */
    private void restrictText() {
        String input = getText().toString();
        if (TextUtils.isEmpty(input)) {
            return;
        }
        if (input.contains(".")) {
            int pointIndex = input.indexOf(".");
            int totalLenth = input.length();
            int len = (totalLenth - 1) - pointIndex;
            if (len > 2) {
                input = input.substring(0, totalLenth - 1);
                textChange = true;
                setText(input);
                setSelection(input.length());
            }
        }

        if (input.toString().trim().substring(0).equals(".")) {
            input = "0" + input;
            setText(input);
            setSelection(2);
        }

    }

    /**
     * 获取金额
     */
    public String getMoneyText() {
        String money = getText().toString();
        //如果最后一位是小数点
        if (money.endsWith(".")) {
            return money.substring(0, money.length() - 1);
        }
        return money;
    }
}
