package com.nokia.ims.fmprocess.fm.PipelineEngine;

import com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine.CriticalConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine.CriticalEsymacOutputHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.CriticalEngine.CriticalProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompOutputHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.EcompEngine.EcompProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MajorEngine.MajorConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MajorEngine.MajorEsymacOutputHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MajorEngine.MajorProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MinorEngine.MinorConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MinorEngine.MinorEsymacOutputHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.MinorEngine.MinorProcessingPipe;
import com.nokia.ims.fmprocess.fm.PipelineEngine.WarningEngine.WarningConvertStage;
import com.nokia.ims.fmprocess.fm.PipelineEngine.WarningEngine.WarningEsymacOutputHandler;
import com.nokia.ims.fmprocess.fm.PipelineEngine.WarningEngine.WarningProcessingPipe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PipelineInitiator {

	static Logger logger = LogManager.getLogger(PipelineInitiator.class.getName());

	public static void init(String outputType) {

		OutputHandler outPutRef;
		if ("ECOMP".equalsIgnoreCase(outputType)) {
			outPutRef = new EcompOutputHandler();
			outPutRef.setName("EcompOutputHandler");
			outPutRef.start();
			logger.info("Starting EcompOutputHandler now " + outPutRef);

			EcompConvertStage ecompMapper = new EcompConvertStage(outPutRef);
			ecompMapper.setName("EcompConvertStage");
			ecompMapper.start();
			FmContext.setEventConverterRef(ecompMapper);
			logger.info("Starting EcompConvertStage now " + ecompMapper);
			
			EcompProcessingPipe ecompPipe = new EcompProcessingPipe();
			ecompPipe.setPriority(4);
			ecompPipe.setName("EcompProcessingPipe");
			ecompPipe.start();
			FmContext.setEcompPipeRef(ecompPipe);
			logger.info("Starting EcompProcessingPipe now " + ecompPipe);
			
		} else {
			WarningEsymacOutputHandler warningOutPutRef = new WarningEsymacOutputHandler();
			warningOutPutRef.setPriority(10);
			warningOutPutRef.setName("WarningEsymacOutputHandler");
			warningOutPutRef.start();
			logger.info("Starting WarningEsymacOutputHandler now " + warningOutPutRef);

			MinorEsymacOutputHandler minorOutPutRef = new MinorEsymacOutputHandler();
			minorOutPutRef.setPriority(10);
			minorOutPutRef.setName("MinorEsymacOutputHandler");
			minorOutPutRef.start();
			logger.info("Starting MinorEsymacOutputHandler now " + minorOutPutRef);

			MajorEsymacOutputHandler majorOutPutRef = new MajorEsymacOutputHandler();
			majorOutPutRef.setPriority(10);
			majorOutPutRef.setName("MajorEsymacOutputHandler");
			majorOutPutRef.start();
			logger.info("Starting MajorEsymacOutputHandler now " + majorOutPutRef);

			CriticalEsymacOutputHandler criticalOutPutRef = new CriticalEsymacOutputHandler();
			criticalOutPutRef.setPriority(10);
			criticalOutPutRef.setName("CriticalEsymacOutputHandler");
			criticalOutPutRef.start();
			logger.info("Starting CriticalEsymacOutputHandler now " + criticalOutPutRef);

			WarningConvertStage warningEventMapper = new WarningConvertStage(warningOutPutRef);
			warningEventMapper.setPriority(9);
			warningEventMapper.setName("WarningConvertStage");
			warningEventMapper.start();
			FmContext.setWarningConverterRef(warningEventMapper);
			logger.info("Starting WarningConvertStage now " + warningEventMapper);

			MinorConvertStage minorEventMapper = new MinorConvertStage(minorOutPutRef);
			minorEventMapper.setPriority(9);
			minorEventMapper.setName("MinorConvertStage");
			minorEventMapper.start();
			FmContext.setMinorConverterRef(minorEventMapper);
			logger.info("Starting MinorConvertStage now " + minorEventMapper);

			MajorConvertStage majorEventMapper = new MajorConvertStage(majorOutPutRef);
			majorEventMapper.setPriority(9);
			majorEventMapper.setName("MajorConvertStage");
			majorEventMapper.start();
			FmContext.setMajorConverterRef(majorEventMapper);
			logger.info("Starting MajorConvertStage now " + majorEventMapper);

			CriticalConvertStage criticalEventMapper = new CriticalConvertStage(criticalOutPutRef);
			criticalEventMapper.setPriority(9);
			criticalEventMapper.setName("CriticalConvertStage");
			criticalEventMapper.start();
			FmContext.setCriticalConverterRef(criticalEventMapper);
			logger.info("Starting CriticalConvertStage now " + criticalEventMapper);

			CriticalProcessingPipe criticalPipe = new CriticalProcessingPipe();
			criticalPipe.setPriority(5);
			criticalPipe.setName("CriticalProcessingPipe");
			criticalPipe.start();
			FmContext.setCriticalPipeRef(criticalPipe);

			MajorProcessingPipe majorPipe = new MajorProcessingPipe();
			majorPipe.setPriority(4);
			majorPipe.setName("MajorProcessingPipe");
			majorPipe.start();
			FmContext.setMajorPipeRef(majorPipe);
			logger.info("Starting MajorProcessingPipe now " + majorPipe);

			MinorProcessingPipe minorPipe = new MinorProcessingPipe();
			minorPipe.setPriority(3);
			minorPipe.setName("MinorProcessingPipe");
			minorPipe.start();
			FmContext.setMinorPipeRef(minorPipe);
			logger.info("Starting MinorProcessingPipe now " + minorPipe);

			WarningProcessingPipe warningPipe = new WarningProcessingPipe();
			warningPipe.setPriority(2);
			warningPipe.setName("WarningProcessingPipe");
			warningPipe.start();
			FmContext.setWarningPipeRef(warningPipe);
			logger.info("Starting WarningProcessingPipe now " + warningPipe);
		}

	}
}
