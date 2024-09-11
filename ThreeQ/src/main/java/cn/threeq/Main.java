package cn.threeq;

import cn.threeq.core.exception.ThreeQException;
import cn.threeq.core.toolkit.Print;
import cn.threeq.starter.QuickStarter;

public class Main {

    public static void main(String[] args) {
        QuickStarter.ThreeQ();
        Print.ThreeQPrint("三七");
        throw new ThreeQException();
    }


}
