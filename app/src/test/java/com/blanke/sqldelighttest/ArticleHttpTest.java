package com.blanke.sqldelighttest;

import android.widget.TextView;

import com.blanke.sqldelighttest.config.ProjectConfig;

import org.junit.After;
import org.junit.Assert;
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

//    @Before
    public void init() {
        //RESTMOCK初始化
        RESTMockServerStarter.startSync(
                new AndroidAssetsFileParser(
                        RuntimeEnvironment.application),
                new AndroidLogger());//mock rest
        MatchableCall mockServer = RESTMockServer.whenGET(
                RequestMatchers.pathContains("Android"));//包含Android
        Assert.assertNotNull(mockServer);
        mockServer.thenReturnFile(200, "article.json");//返回json
    }

    @Test
    public void articleHttpMockTest1() {
        ShadowLog.stream = System.out;//log
        ActivityController<MainActivity> mainActivityController = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = mainActivityController.get();
        ProjectConfig.setIsTestJunit(true);
        mainActivityController.create().start().resume();//调用生命周期
        TextView textView = (TextView) activity.findViewById(R.id.main_text);
        String text = textView.getText().toString().trim();//获得文本
        System.out.println("TEXT："+text);
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