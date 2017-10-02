package mavinab.ops.bl;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OPSSessionService extends Service {

	private Timer mTimer = null;
	private SessionTimerTask mSessionTimerTask = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mTimer = new Timer();
		mSessionTimerTask = new SessionTimerTask();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mTimer != null) {
			mSessionTimerTask.cancel();
			mTimer.cancel();
			mTimer = null;
			mSessionTimerTask = null;
		}
	}

	/**
	 * Start the Session Timer
	 */
	public void startTimer() {
		if (mTimer != null && mSessionTimerTask != null) {
			mTimer.schedule(mSessionTimerTask, 1000 * 60 * 30, 1000 * 60 * 30);
		} else {
			mTimer = new Timer();
			mSessionTimerTask = new SessionTimerTask();
			mTimer.schedule(mSessionTimerTask, 1000 * 60 * 30, 1000 * 60 * 30);
		}
	}
	
	/**
	 * Stops the Session Timer
	 */
	public void stopTimer() {
		if (mTimer != null && mSessionTimerTask != null) {
			mSessionTimerTask.cancel();
			mTimer.cancel();
			mSessionTimerTask = null;
			mTimer = null;
		}
	}

	/**
	 * @author Keyur Ashra
	 * 
	 */
	private class SessionTimerTask extends TimerTask {

		@Override
		public void run() {

		}
	}
}
