package com.blanke.sqldelighttest;

import android.widget.TextView;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.util.ActivityController;

import io.appflate.restmock.MatchableCall;
import io.appflate.restmock.RESTMockServer;
import io.appflate.restmock.RESTMockServerStarter;
import io.appflate.restmock.android.AndroidAssetsFileParser;
import io.appflate.restmock.android.AndroidLogger;
import io.appflate.restmock.utils.RequestMatchers;

/**
 * Created by blanke on 16-5-25.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class ArticleHttpTest {

    @Before
    public void init() {
        RESTMockServerStarter.startSync(
                new AndroidAssetsFileParser(
                        RuntimeEnvironment.application),
                new AndroidLogger());//mock rest
        MatchableCall mockServer = RESTMockServer.whenGET(
                RequestMatchers.pathContains("Android"));
        Assert.assertNotNull(mockServer);
        mockServer.thenReturnFile(200, "article.json");
    }

    @Test
    public void articleHttpMockTest1() {
        ShadowLog.stream = System.out;
        ActivityController<MainActivity> mainActivityController = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = mainActivityController.get();
        activity.setTest(true);
        mainActivityController.create().start().resume();
        TextView textView = (TextView) activity.findViewById(R.id.main_text);
        String text = textView.getText().toString().trim();
        System.out.println(text);
        Assert.assertTrue(text.length() > 100);
    }

    @After
    public void end() {
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
