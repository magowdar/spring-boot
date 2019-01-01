package com.nokia.ims.fmprocess.fm.UtilityEngine;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameFilter implements FilenameFilter {
	private String ecrExt;
	private String ddrExt;
	private String edsExt;
	
	public FileNameFilter(String ecrExt, String ddrExt) {
		this.ecrExt = ecrExt;
		this.ddrExt = ddrExt;
	}

	public FileNameFilter(String edsExt) {
		this.edsExt = edsExt;
	}
	
	public boolean accept(File directory, String filename) {

		if(ecrExt != null && filename.endsWith(ecrExt))
			return true;
		if(ddrExt != null && filename.endsWith(ddrExt))
			return true;
		if(edsExt != null && filename.endsWith(edsExt))
			return true;
		return false;
	}
}
