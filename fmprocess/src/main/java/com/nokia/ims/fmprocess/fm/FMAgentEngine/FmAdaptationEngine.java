package com.nokia.ims.fmprocess.fm.FMAgentEngine;

import com.nokia.ims.fmprocess.fm.UtilityEngine.FmUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.*;

public class FmAdaptationEngine {
	static Logger logger = LogManager.getLogger(FmAdaptationEngine.class
			.getName());

	public static HashMap<Integer, FmMapInfo> mapInfo = new HashMap<Integer, FmMapInfo>();
	private static HashMap<String, String> appsAndMsgSets = new HashMap<String, String>();
	private static Set<String> neApps = new HashSet<String>();
	public static FmMapInfo defaultMapInfo;
	private static Set<String> adaptationFiles = new HashSet<String>();
	private static String applicationPath = "";

	private static DocumentBuilder builder;
	private static XPath xpath;

	public FmAdaptationEngine() throws Exception {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory
				.newInstance();
		domFactory.setNamespaceAware(true);
		builder = domFactory.newDocumentBuilder();
		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		defaultMapInfo = new FmMapInfo("TSP", "DUMMY-1");
	}

	public void prepAdaptation() throws Exception {

		String adaptationDir = FmUtil.ADAPTATIONS_PATH;
		String pathToAdaptationFile = null;
		String contents = getContents();
		String[] adaptationFolders = contents.split("\n");
		for (String adaptationFolder : adaptationFolders) {
			pathToAdaptationFile = adaptationDir + File.separator
					+ adaptationFolder;
			File adaptationPath = new File(pathToAdaptationFile);
			if (adaptationPath.exists() && adaptationPath.canRead())
				retrieveAdaptationFilesFromPath(adaptationPath);
		}
		buildMsgSetTable();
		FmApplicationNode rootNode = new FmApplicationNode();
		buildApplicationNodeTree(rootNode);
		buildAlarmingPaths(rootNode);
		builder = null;
		xpath = null;
	}

	private static void buildApplicationNodeTree(FmApplicationNode rootNode) throws Exception {
		buildRootNode(rootNode);
		buildRootChildren(rootNode);
		buildAssociatedRootChildren(rootNode);
	}

	private static void buildRootNode(FmApplicationNode rootNode) throws Exception {

		String searchPattern = "*[//@adaptationRootClass]";
		for (String filePath : adaptationFiles) {
			NodeList applNode = (NodeList) DocumentProcessor(filePath,
					searchPattern);
			String app = null;
			if (applNode.getLength() > 0) {
				app = applNode.item(0).getAttributes()
						.getNamedItem("adaptationRootClass").getNodeValue();
				app = app.substring(2);
				if ((!app.equals("TSP")) && (!app.equals("PLATFORM"))
						&& (!app.equals("IMSNFM"))) {
					rootNode.application = app;
				}
				if (rootNode.application != null)
					break;
			}
		}
	}

	private static void buildRootChildren(FmApplicationNode aNode) throws Exception {

		FmApplicationNode child = buildNativeRootChildren(aNode);
		if (child.childs.size() > 0) {
			for (FmApplicationNode cld : child.childs) {
				buildRootChildren(cld);
			}
		}
	}

	private static FmApplicationNode buildNativeRootChildren(
			FmApplicationNode theNode) throws Exception {
		ArrayList<FmApplicationNode> cldList = new ArrayList<FmApplicationNode>();
		String relationshipPattern = "//relationshipDefs[@source = '//"
				+ theNode.application + "']/@target";
		Set<String> rootApps = retrieveApplicationNodes(adaptationFiles,
				relationshipPattern);

		for (String app : rootApps) {
			if (neApps.contains(app.substring(2))) {
				FmApplicationNode aNode = new FmApplicationNode();
				aNode.application = app.substring(2);
				cldList.add(aNode);
			}
		}
		theNode.childs = cldList;
		return theNode;
	}

	private static void buildAssociatedRootChildren(
			FmApplicationNode theRootNode) throws Exception {
		ArrayList<String> cldList = new ArrayList<String>();
		for (FmApplicationNode node : theRootNode.childs) {
			cldList.add(node.application);
		}

		Set<String> assocApps = null;
		String assocAppPattern = "//relationshipDefs[@source = '//"
				+ theRootNode.application + "']/target/@href";
		assocApps = retrieveApplicationNodes(adaptationFiles, assocAppPattern);
		for (String assocApplication : assocApps) {
			assocApplication = assocApplication.substring(assocApplication
					.lastIndexOf("//") + 2);
			if (neApps.contains(assocApplication)
					&& !cldList.contains(assocApplication)) {
				FmApplicationNode aNode = new FmApplicationNode();
				aNode.childs = new ArrayList<FmApplicationNode>();
				aNode.application = assocApplication;
				cldList.add(assocApplication);
				theRootNode.childs.add(aNode);
			}
		}
	}

	private static void createMapInfo(String app, String appPath) {

		FmMapInfo mapInfoRef = new FmMapInfo(app, appPath);
		logger.info(appsAndMsgSets.entrySet());
		if (appsAndMsgSets.containsKey(app)) {
			String[] msgSetRange = appsAndMsgSets.get(app).split("-");
			int lowerSet = Integer.parseInt(msgSetRange[0]);
			int upperSet = lowerSet;
			if (msgSetRange.length == 2)
				upperSet = Integer.parseInt(msgSetRange[1]);
			for (int setId = lowerSet; setId <= upperSet; setId++) {
				mapInfo.put(setId, mapInfoRef);
			}
		}
	}

	private static void buildAlarmingPaths(FmApplicationNode rootNode) {
		createMapInfo(rootNode.application, rootNode.application + "-1");
		traverseApplicationTree(rootNode);
	}

	private static void traverseApplicationTree(FmApplicationNode rootNode) {
		if (rootNode.childs.size() > 0) {
			applicationPath = applicationPath + rootNode.application + "-1/";
			for (FmApplicationNode cld : rootNode.childs) {
				traverseApplicationTree(cld);
				applicationPath = rootNode.application + "-1/";
			}
		} else {
			applicationPath = applicationPath + rootNode.application + "-1";
			createMapInfo(rootNode.application, applicationPath);
		}
	}

	private static String getContents() {

		StringBuilder contents = new StringBuilder();
		try (BufferedReader input = new BufferedReader(new FileReader(new File(
				FmUtil.ADAPTATION_TXT)))) {
			String line = null;
			while ((line = input.readLine()) != null) {
				contents.append(line + "\n");
			}
		} catch (Exception ex) {
			logger.error(ex);
		}
		return contents.toString();
	}

	private static void retrieveAdaptationFilesFromPath(File dir) {

		try {
			File listFile[] = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.endsWith(FmUtil.ADAPTATION_FILE_EXT);
				}
			});
			for (int i = 0; i < listFile.length; i++) {
				adaptationFiles.add(listFile[i].getPath());
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}

	private static void buildMsgSetTable() throws Exception {

		String searchPattern;
		for (String filePath : adaptationFiles) {
			searchPattern = "//attributeGroups/annotations/elements[@name = 'CAFClassAlarmSetMapping']/../../@id";
			NodeList applNodes = (NodeList) DocumentProcessor(filePath,
					searchPattern);
			for (int i = 0; i < applNodes.getLength(); i++) {
				String app = applNodes.item(i).getNodeValue();
				searchPattern = "//attributeGroups[@id = '"
						+ applNodes.item(i).getNodeValue()
						+ "']"
						+ "/annotations/elements[@name = 'CAFClassAlarmSetMapping']/@value";
				NodeList msgSetNodes = (NodeList) DocumentProcessor(filePath,
						searchPattern);
				if (msgSetNodes.getLength() > 0) {
					String msgSet = msgSetNodes.item(0).getNodeValue();
					appsAndMsgSets.put(app, msgSet);
				}
			}
		}
		if (appsAndMsgSets != null)
			neApps = appsAndMsgSets.keySet();
	}

	private static Object DocumentProcessor(String filePath,
			String searchPattern) throws Exception {
		Document doc = builder.parse(filePath);
		XPathExpression getApplications = xpath.compile(searchPattern);
		return getApplications.evaluate(doc, XPathConstants.NODESET);
	}

	private static Set<String> retrieveApplicationNodes(
			Set<String> adaptationFiles, String xpathPattern) throws Exception{
		Set<String> applicationList = new HashSet<String>();
		for (String filePath : adaptationFiles) {
			NodeList applNodes = (NodeList) DocumentProcessor(filePath, xpathPattern);
			for (int i = 0; i < applNodes.getLength(); i++) {
				String app = null;
				app = applNodes.item(i).getNodeValue();
				applicationList.add(app);
			}
		}
		return applicationList;
	}

	private static class FmApplicationNode {
		public String application; // the application name
		public List<FmApplicationNode> childs; // the subordinate applications
	}

	private static class FmMapInfo {
		public FmMapInfo(String app, String appPath) {
			application = app;
			alarmPath = appPath;
		}

		public String application; // the application name
		public String alarmPath; // the alarming path
	}

	public static String getAlarmText(String eventId) {
		int msgSetNum = -1;
		try {
			msgSetNum = Integer.parseInt(eventId.split("-")[0]);
		} catch (Exception e) {
			logger.error(e);
		}
		return (mapInfo.getOrDefault(msgSetNum, defaultMapInfo)).application;
	}

	public static String getMOI(String eventId) {
		int msgSetNum = -1;
		try {
			msgSetNum = Integer.parseInt(eventId.split("-")[0]);
		} catch (Exception e) {
			logger.error(e);
		}
		return (mapInfo.getOrDefault(msgSetNum, defaultMapInfo)).alarmPath;
	}
}
