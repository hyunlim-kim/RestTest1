package com.example.hyunlim.resttest1;

import java.io.Serializable;

/**
 * Created by qnqnqn1239 on 2017. 7. 21..
 */

public class CommonBean implements Serializable{ //원래 인텐트로 클래스는 넘어갈 수 있는데 Serializable하면 넘길 수 있다.
                                                //따라서 모든 빈들이 상속받는 CommonBean이 인텐트로 넘어가도록 하기 위해 !!!

    private String result;
    private String resultMsg;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}//end class
