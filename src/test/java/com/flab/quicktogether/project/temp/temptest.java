package com.flab.quicktogether.project.temp;


import com.flab.quicktogether.common.Position;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class temptest {

    @Test
    public void test(){


        Position position3 = Position.DESIGNER;
        Position position = Position.BACKEND;

        ArrayList arr = new ArrayList();
        arr.add(position3);
        arr.add(position);

        Position position2 = Position.FRONTEND;

        arr.remove(position2);
        System.out.println(arr.toString());

    }

}
