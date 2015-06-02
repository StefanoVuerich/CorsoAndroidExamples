package com.lqc.mylocalguide.utilities;

import com.lqc.mylocalguide.activities.WebViewActivity;
import com.parse.Parse;
import com.parse.PushService;

public class Application extends android.app.Application {

  public Application() {
  }

  @Override
  public void onCreate() {
    super.onCreate();

	Parse.initialize(this, "c2QkSFPolTh5GeeCtqVukhDPe5fHwg9xSBrmvoCT", "ppwhAGwh2sbtBczVK7CQPw7t6Fz4yEuJHtEUnOIi"); 

	PushService.setDefaultPushCallback(this, WebViewActivity.class);
  }
}