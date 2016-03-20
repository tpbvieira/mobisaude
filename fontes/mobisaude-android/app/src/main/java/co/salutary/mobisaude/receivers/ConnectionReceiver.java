package co.salutary.mobisaude.receivers;

import co.salutary.mobisaude.services.ReportProblemService;
import co.salutary.mobisaude.util.DeviceInfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ConnectionReceiver extends BroadcastReceiver {	

	@Override
	public void onReceive(Context context, Intent intent) {
		if(DeviceInfo.thereIsWifiConnection(context)){
			Intent t = new Intent(context, ReportProblemService.class);
			context.startService(t);		
		}
	}	
}