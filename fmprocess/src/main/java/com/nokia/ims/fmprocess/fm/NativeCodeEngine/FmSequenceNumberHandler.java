package com.nokia.ims.fmprocess.fm.NativeCodeEngine;

import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class FmSequenceNumberHandler {

	static Logger logger = LogManager.getLogger(FmSequenceNumberHandler.class
			.getName());

	public static Long previousStoredNumber = -1L;

	public static Long toBeSyncedSeqId = -1L;
	
	public static long getLastSequenceNumber() {
		long ret = -1;
		try (BufferedReader in = new BufferedReader(new FileReader(
				FmUtil.LAST_SEQ_NUM_FILE))) {
			String line;
			while ((line = in.readLine()) != null) {
				ret = Integer.parseInt(line);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ret;
	}

	public static void setLastSequenceNumber(long lastSequenceNumber) {
		if (lastSequenceNumber > previousStoredNumber) {
			try (BufferedWriter seqWriter = new BufferedWriter(new FileWriter(
					FmUtil.LAST_SEQ_NUM_FILE))) {
				seqWriter.write(""+lastSequenceNumber);
				seqWriter.flush();
				previousStoredNumber = lastSequenceNumber;
				logger.info("Storing Sequence number to File is "
						+ lastSequenceNumber);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
	
	
}
