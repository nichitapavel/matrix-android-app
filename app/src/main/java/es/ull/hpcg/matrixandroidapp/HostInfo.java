package es.ull.hpcg.matrixandroidapp;

import android.os.Build;

import matrix.lib.IHostInfo;

public class HostInfo implements IHostInfo {
    @Override
    public String getName() {
        StringBuilder hostname = new StringBuilder();
        hostname.append(Build.DEVICE)
                .append("***")
                // TODO deprecated, update to Build.getSerial()
                .append(Build.SERIAL)
                .append("***")
                .append(Build.ID)
                .append("***")
                .append(Build.BOARD)
                .append("***")
                .append(Build.HARDWARE);

        return hostname.toString();
    }
}
