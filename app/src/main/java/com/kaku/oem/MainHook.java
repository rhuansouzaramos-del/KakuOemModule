package com.kaku.oem;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class MainHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("com.android.settings")) return;

        try {
            Class<?> clazz = XposedHelpers.findClass("com.android.settings.development.OemUnlockPreferenceController", lpparam.classLoader);
            
            // Tenta hookar os métodos de visibilidade e status
            XposedHelpers.findAndHookMethod(clazz, "isAvailable", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return true;
                }
            });
            
            XposedHelpers.findAndHookMethod(clazz, "getAvailabilityStatus", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return 0; // 0 = AVAILABLE
                }
            });
        } catch (Throwable t) {
            // Ignora falhas se a classe não existir
        }
    }
}
