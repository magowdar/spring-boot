package com.nokia.ims.fmprocess.fm.EventObjectHolder;

public class AlarmWrapper {
	
	private FmEvent fmAlarm;
	
	public AlarmWrapper(FmEvent alarm) {
		fmAlarm = alarm;
	}

	public FmEvent getFmAlarm(){
		return fmAlarm;
	}
}
