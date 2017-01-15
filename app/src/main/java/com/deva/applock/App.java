package com.deva.applock;

import android.app.Application;
import android.content.Intent;

/**
 * Created by HP on 05/01/2017.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        startService(new Intent(this, MyService.class));
    }
}


