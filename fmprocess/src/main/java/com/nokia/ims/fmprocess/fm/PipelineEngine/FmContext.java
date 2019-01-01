package com.nokia.ims.fmprocess.fm.PipelineEngine;

import com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine.CriticalConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine.CriticalProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MajorEngine.MajorConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MajorEngine.MajorProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MinorEngine.MinorConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MinorEngine.MinorProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.WarningEngine.WarningConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.WarningEngine.WarningProcessingPipe;

import java.util.HashSet;
import java.util.Set;

/*This class contains references to all the pipes. 
 * When ever a pipe reference is required , we can use the getter methods in this class
*/

public class FmContext {
	
	public static long lastSeqSynced;
	public static boolean startNotifyEvent = false;
	
	private static WarningProcessingPipe warningPipeRef;
	private static WarningConvertStage warningConvertRef;
	private static MinorProcessingPipe minorPipeRef;
	private static MinorConvertStage minorConvertRef;
	private static MajorProcessingPipe majorPipeRef;
	private static MajorConvertStage majorConvertRef;
	private static CriticalProcessingPipe criticalPipeRef;
	private static CriticalConvertStage criticalConvertRef;
	private static EcompProcessingPipe ecompPipeRef;
	
	private static EcompConvertStage ecompConvertRef;
	
	public static Set<String> criticalList = new HashSet<String>();
	public static Set<String> majorList = new HashSet<String>();
	public static Set<String> minorList = new HashSet<String>();
	public static Set<String> warningList = new HashSet<String>();
	
	
	public static WarningProcessingPipe getWarningPipeRef() {
		return warningPipeRef;
	}

	public static WarningConvertStage getWarningConverterRef() {
		return warningConvertRef;
	}
	
	public static MinorProcessingPipe getMinorPipeRef() {
		return minorPipeRef;
	}

	public static MinorConvertStage getMinorConverterRef() {
		return minorConvertRef;
	}
	
	public static MajorProcessingPipe getMajorPipeRef() {
		return majorPipeRef;
	}
	
	public static EcompProcessingPipe getEcompPipeRef() {
		return ecompPipeRef;
	}

	public static MajorConvertStage getMajorConverterRef() {
		return majorConvertRef;
	}
	
	public static CriticalConvertStage getCriticalConverterRef(){
		return criticalConvertRef;
	}
	
	public static CriticalProcessingPipe getCriticalPipeRef() {
		return criticalPipeRef;
	}

	public static void setMinorConverterRef(MinorConvertStage minorEventMapper) {
		minorConvertRef = minorEventMapper;
	}
	
	public static void setMajorConverterRef(MajorConvertStage majorEventMapper) {
		majorConvertRef = majorEventMapper;
	}
	
	public static void setWarningConverterRef(WarningConvertStage warningEventMapper) {
		warningConvertRef = warningEventMapper;
	}

	public static void setCriticalConverterRef(CriticalConvertStage criticalEventMapper) {
		criticalConvertRef = criticalEventMapper;
	}
	
	
	public static void setWarningPipeRef(WarningProcessingPipe warningPipe) {
		warningPipeRef = warningPipe;
	}
	
	public static void setMinorPipeRef(MinorProcessingPipe minorPipe) {
		minorPipeRef = minorPipe;
	}
	
	public static void setMajorPipeRef(MajorProcessingPipe majorPipe) {
		majorPipeRef = majorPipe;
	}
	
	public static void setEcompPipeRef(EcompProcessingPipe ecompPipe) {
		ecompPipeRef = ecompPipe;
	}
	
	public static void setCriticalPipeRef(CriticalProcessingPipe criticalPipe) {
		criticalPipeRef = criticalPipe;	
	}
	
	public static EcompConvertStage getEcompConverterRef() {
		return ecompConvertRef;
	}

	public static void setEventConverterRef(EcompConvertStage eventMapper) {
		ecompConvertRef = eventMapper;
	}
}
