package com.nokia.ims.fmprocess.fm.FMAgentEngine;

import com.nokia.ims.fmprocess.fm.UtilityEngine.FileNameFilter;
import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FmEDSEngine {
	static Logger logger = LogManager.getLogger(FmEDSEngine.class.getName());

	public static ArrayList<String> criticalEventList = new ArrayList<String>();
	public static HashMap <String, ArrayList<String>> clearCriticalEventList = new HashMap <String, ArrayList<String>>();
	
	public static ArrayList<String> majorEventList = new ArrayList<String>();
	public static HashMap <String, ArrayList<String>> clearMajorEventList = new HashMap <String, ArrayList<String>>();
	
	public static ArrayList<String> minorEventList = new ArrayList<String>();
	public static HashMap <String, ArrayList<String>> clearMinorEventList = new HashMap <String, ArrayList<String>>();
	
	public static ArrayList<String> warningEventList = new ArrayList<String>();
	public static HashMap <String, ArrayList<String>> clearWarningEventList = new HashMap <String, ArrayList<String>>();
	
	private ArrayList<File> edsFileNameList = new ArrayList<File>();

	public void processEDSRules(String... pathToRules) {
		for (String path : pathToRules)
			listFilteredFiles(path, ".eds");
		readEDSFiles();
	}

	private void listFilteredFiles(String ruleDir, String edsExt) {
		if (ruleDir != null) {
			File targetDir = new File(ruleDir);
			FileNameFilter selectCriteria = new FileNameFilter(edsExt);
			if (targetDir.isDirectory()) {
				for (File eachFilteredFile : targetDir
						.listFiles(selectCriteria)) {
					edsFileNameList.add(eachFilteredFile);
				}
			} else {
				logger.warn("Input path is not a directory ");
			}
		} else {
			logger.warn("Rule directory is null");
		}
	}

	private void readEDSFiles() {
		for (File file : edsFileNameList) {
			parseFile(file);
		}
	}

	public void parseFile(File filename) {
		String inputLine = null;
		Integer severity;
		try (BufferedReader in = new BufferedReader(new FileReader(
				filename.getAbsolutePath()))) {
			while ((inputLine = in.readLine()) != null) {
				inputLine = inputLine.trim();
				if (!inputLine.isEmpty() && inputLine.charAt(0) == '['
						&& inputLine.endsWith("]")) {
					inputLine = inputLine.substring(1, inputLine.length() - 1);
					inputLine = inputLine.trim();
					String regex = "[ \t]+";
					String[] ecrParts = inputLine.split(regex);
					int eventAlarmId = FmUtil.getAlarmSpecificId(ecrParts[0]);
					if (eventAlarmId != 0) {
						severity = Integer.parseInt(ecrParts[1]);
						if(severity == 1){
							criticalEventList.add(ecrParts[0]);
						} else if(severity == 2){
							majorEventList.add(ecrParts[0]);
						} else if(severity == 3){
							minorEventList.add(ecrParts[0]);
						} else if(severity == 4){
							warningEventList.add(ecrParts[0]);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
