package io.github.shiragicord.fpsswitchservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;

public class FpsSwitchSettingService extends Service {
    private static final String SETTING_SYSTEM_PEAK_REFRESH_RATE = "peak_refresh_rate";
    private static final String SETTING_SECURE_MIUI_REFRESH_RATE = "miui_refresh_rate";
    private static final String SETTING_SECURE_USER_REFRESH_RATE = "user_refresh_rate";
    public FpsSwitchSettingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new IFpsSwitchSettingService.Stub() {
            @Override
            public boolean isRefreshRate120() throws RemoteException {
                try {
                    int refreshRate = Settings.Secure.getInt(getContentResolver(), SETTING_SECURE_USER_REFRESH_RATE);
                    return refreshRate == 120;
                } catch (Settings.SettingNotFoundException e) {
                    throw new RemoteException("FpsSwitchSettingService: SettingNotFoundException");
                }
            }

            @Override
            public void setRefreshRate(boolean is120) throws RemoteException {
                Settings.System.putInt(getContentResolver(), SETTING_SYSTEM_PEAK_REFRESH_RATE, is120 ? 120 : 60);
                Settings.Secure.putInt(getContentResolver(), SETTING_SECURE_MIUI_REFRESH_RATE, is120 ? 120 : 60);
                Settings.Secure.putInt(getContentResolver(), SETTING_SECURE_USER_REFRESH_RATE, is120 ? 120 : 60);
            }
        };
    }
}