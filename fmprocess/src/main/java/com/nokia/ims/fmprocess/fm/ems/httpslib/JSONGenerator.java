package com.nokia.ims.fmprocess.fm.ems.httpslib;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.StringWriter;

public class JSONGenerator {
	private Object template;
	private String fileName;
	public static String templatePath = "/opt/agents/utils/res/";
	
	public JSONGenerator(Object template, String fileName) {
		this.template = template;
		this.fileName = fileName;
	}

	public String getJSON() {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH,
				templatePath);
		ve.init();

		Template t = ve.getTemplate(fileName);
		VelocityContext context = new VelocityContext();
		context.put("event", template);
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		return writer.toString();
	}
}
