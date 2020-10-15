package com.sangis.corejava;

import com.sangis.corejava.domain.core.MunicipalityApplication;

public class Main {

    public static void main(String[] args) {

        MunicipalityApplication application = ApplicationFactory.make();
        application.run();
    }



}
