package com.nokia.ims.fmprocess.fm.PipelineEngine;

import com.nokia.ims.fmprocess.fm.EventObjectHolder.FmEvent;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;

import java.util.concurrent.ArrayBlockingQueue;

public class PipelineStage extends Thread {

	public ArrayBlockingQueue<FmEvent> dataSource = new ArrayBlockingQueue<FmEvent>(
			10000);

	private boolean lockFlag = false;

	public FmEvent read() throws InterruptedException {
		FmEvent queObj = null;
		while (queObj == null) {
			queObj = dataSource.poll();
			if (queObj == null && !lockFlag) {
				synchronized (this) {
					lockFlag = true;
					this.wait();
					lockFlag = false;
				}
			}
		}
		return queObj;
	}

	public void write(FmEvent alarm) throws InterruptedException {
		dataSource.put(alarm);
		if (lockFlag) {
			synchronized (this) {
				this.notify();
				lockFlag = false;
			}
		}
	}
}
