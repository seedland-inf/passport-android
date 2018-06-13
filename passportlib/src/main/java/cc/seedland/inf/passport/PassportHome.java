package cc.seedland.inf.passport;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.https.HttpsUtils;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cc.seedland.inf.network.Networkit;
import cc.seedland.inf.passport.login.LoginCaptchaActivity;
import cc.seedland.inf.passport.login.LoginPasswordActivity;
import cc.seedland.inf.passport.network.ApiUtil;
import cc.seedland.inf.passport.network.PassportInterceptor;
import cc.seedland.inf.passport.network.RuntimeCache;
import cc.seedland.inf.passport.network.TokenCallback;
import cc.seedland.inf.passport.password.ModifyPasswordActivity;
import cc.seedland.inf.passport.password.ResetPasswordActivity;
import cc.seedland.inf.passport.register.RegisterActivity;
import cc.seedland.inf.passport.util.Constant;
import okhttp3.OkHttpClient;

/**
 * Passport门面,这里统一提供对外调用接口
 * Created by xuchunlei on 2017/11/10.
 */

public final class PassportHome {

    private static final PassportHome INSTANCE = new PassportHome();
    private static final PassportLifecycleCallbacks LIFECYCLE_CALLBACKS = new PassportLifecycleCallbacks();
    private PassportHome(){

    }

    /**
     * 初始化方法
     * <p>
     *     初始化PassportSDK开发环境
     * </p>
     * @param app Application实例
     * @param channel 渠道号
     * @param key 开发者key
     */
    public void init(Application app, String channel, String key) {

        Constant.APP = app;
        Networkit.init(app, channel, key);
        // 初始化ApiUtil
        ApiUtil.init(channel, key, app.getString(R.string.http_host));

    }

    public final static PassportHome getInstance() {
        return INSTANCE;
    }

    /**
     * 开启或关闭刷新token，默认关闭自动
     * 必须在初始化之后调用，才会生效
     * @param value
     */
    public final static void enableTokenUpdate(boolean value) {
        if(value) {
            if(Constant.APP != null) {
                Constant.APP.registerActivityLifecycleCallbacks(LIFECYCLE_CALLBACKS);
            }
        }else {
            if(Constant.APP != null) {
                Constant.APP.unregisterActivityLifecycleCallbacks(LIFECYCLE_CALLBACKS);
            }
        }
    }

    /**
     * 打开注册界面
     * @param context
     */
    public void startRegister(Context context, int requestCode) {
        Intent i = new Intent(context, RegisterActivity.class);
        if(context instanceof Activity) {
            ((Activity) context).startActivityForResult(i, requestCode);
        }else {
            context.startActivity(i);
        }
    }

    /**
     * 打开密码登录界面
     * @param context
     */
    public void startLoginByPassword(Context context, int requestCode) {
        startLoginByPassword(context, requestCode, null);
    }

    /**
     * 打开密码登录界面
     * @param context
     * @param requestCode
     * @param defPhone 默认电话号码
     */
    public void startLoginByPassword(Context context, int requestCode, String defPhone) {
        Intent i = new Intent(context, LoginPasswordActivity.class);
        if(!TextUtils.isEmpty(defPhone)) {
            i.putExtra(Constant.EXTRA_KEY_PHONE, defPhone);
        }

        if(context instanceof Activity) {
            ((Activity) context).startActivityForResult(i, requestCode);
        }else {
            context.startActivity(i);
        }
    }

    /**
     * 打开验证码登录界面
     * @param context
     */
    public void startLoginByCaptcha(Context context, int requestCode) {
        Intent i = new Intent(context, LoginCaptchaActivity.class);
        if(context instanceof Activity) {
            ((Activity) context).startActivityForResult(i, requestCode);
        }else {
            context.startActivity(i);
        }
    }

    /**
     * 打开重置密码界面
     * @param context
     */
    public void startResetPassword(Context context, int requestCode) {
        Intent i = new Intent(context, ResetPasswordActivity.class);
        if(context instanceof Activity) {
            ((Activity) context).startActivityForResult(i, requestCode);
        }else {
            context.startActivity(i);
        }
    }

    /**
     * 打开修改密码界面
     * @param context
     */
    public void startModifyPassword(Context context, int requestCode) {
        Intent i = new Intent(context, ModifyPasswordActivity.class);
        if(context instanceof Activity) {
            i.putExtra(Constant.EXTRA_KEY_REQUEST_CODE, requestCode);
            ((Activity) context).startActivityForResult(i, requestCode);
        }else {
            context.startActivity(i);
        }
    }

    /**
     * 登录状态
     * @return
     */
    public String getToken() {
        return RuntimeCache.getToken();
    }

    /**
     * 登出
     */
    public void logout() {
        RuntimeCache.saveToken("");
    }

    public void checkLogin(TokenCallback callback) {
        ApiUtil.refreshToken(callback);
    }

}
