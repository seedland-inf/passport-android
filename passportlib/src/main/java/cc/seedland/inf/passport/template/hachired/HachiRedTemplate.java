package cc.seedland.inf.passport.template.hachired;

import cc.seedland.inf.passport.template.ITemplate;
import cc.seedland.inf.passport.template.IViewAgent;
import cc.seedland.inf.passport.template.LoginCaptchaViewAgent;
import cc.seedland.inf.passport.template.LoginMainViewAgent;

/**
 * 作者 ： 徐春蕾
 * 联系方式 ： xuchunlei@seedland.cc / QQ:22003950
 * 时间 ： 2018/06/21 11:03
 * 描述 ：
 **/
public class HachiRedTemplate implements ITemplate {

    @Override
    public LoginMainViewAgent createLoginMainAgent() {
        return null;
    }

    @Override
    public LoginCaptchaViewAgent createLoginCaptchaAgent() {
        return null;
    }

    @Override
    public <T extends IViewAgent> T createAgent(String clzName) {
        return null;
    }

    @Override
    public int createLayout(String clzName) {
        return 0;
    }
}