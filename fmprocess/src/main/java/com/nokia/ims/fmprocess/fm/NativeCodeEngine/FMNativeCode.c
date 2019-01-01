#include <jni.h>
#include <TspInterfaceAPI.h>

#include <RtpEvent.h>
#include <RtpTrace.h>
#include <RtpError.h>

#include <FMNativeCode.h>

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define LEN 100
#define ALARM_FILTER_RULES_MSG 921;
#define ECOMP_TABLE_MSG 922;
#define ECOMP_SCALAR_MSG 923;

TABLEPARAM tParam;
TABLEPARAM ecompConnParam;

SCALARPARAM emsHbIntervalParam;
SCALARPARAM emsCredentialParam;
SCALARPARAM transOsDomainParam;
SCALARPARAM transOsRouteParam;
SCALARPARAM transOsApiParam;
SCALARPARAM transOsPortParam;
SCALARPARAM threshold;

RtpComMsg_t msg = { 0 };
RtpComMsgInfo_t msgInfo = { 0 };
RtpEvtAttr_t eventAttr = { 0 };

static int filterSeverity = 4;
static int filterTimeInterval = 0;
static int filterActive = 1;

static jclass classOfArrayList;
static jmethodID addInArrayList;
static jmethodID getFromArrayList;
static jmethodID sizeOfArrayList;

static jclass classOfHashMap;
static jmethodID initHashMap;
static jmethodID putInHashMap;

static jclass classOfFmAlarmFilter;
static jmethodID initialiseFilter;
static jfieldID setIsActive;
static jfieldID setFilterName;
static jfieldID setAlarmStartId;
static jfieldID setAlarmEndId;
static jfieldID setAlarmSeverity;
static jfieldID setIgnoreVars;
static jfieldID setTimeInterval;
static jfieldID setRateLmt;

static jclass classOfFmEventObject;
static jmethodID constructorOfFmEventObject;
static jfieldID fieldEventId;
static jfieldID fieldEventCeName;
static jfieldID fieldProcName;
static jfieldID fieldEventText;
static jfieldID fieldEventParam;
static jfieldID fieldFaultyObject;
static jfieldID fieldSequenceId;
static jfieldID fieldEventType;
static jfieldID fieldEventSeverity;
static jfieldID fieldSpecificId;
static jfieldID fieldEventTime;

static jobject globalFilterArrayList;
static jobject globalFmEventObject;
static jobject globalEcompDomainMaplist;

static jobject filterObject;
static jstring jStringValue;
static jint jIntValue;
static jboolean jBoolValue;
static jlong jLongValue;

void initialiseJNIObjectTypeRef(JNIEnv *env) {

	jclass tempFilterClass = (*env)->FindClass(env,
			"com/nokia/fm/FMAgentEngine/FmAlarmFilter");
	classOfFmAlarmFilter = (jclass)(*env)->NewGlobalRef(env, tempFilterClass);
	(*env)->DeleteLocalRef(env, tempFilterClass);

	initialiseFilter = (*env)->GetMethodID(env, classOfFmAlarmFilter, "<init>",
			"()V");

	setIsActive = (*env)->GetFieldID(env, classOfFmAlarmFilter, "isActive",
			"Z");

	setFilterName = (*env)->GetFieldID(env, classOfFmAlarmFilter,
			"uniqueRuleName", "Ljava/lang/String;");

	setAlarmStartId = (*env)->GetFieldID(env, classOfFmAlarmFilter,
			"startSpecificProblem", "I");

	setAlarmEndId = (*env)->GetFieldID(env, classOfFmAlarmFilter,
			"endSpecificProblem", "I");

	setAlarmSeverity = (*env)->GetFieldID(env, classOfFmAlarmFilter, "severity",
			"I");

	setIgnoreVars = (*env)->GetFieldID(env, classOfFmAlarmFilter,
			"ignoreVariables", "Z");

	setTimeInterval = (*env)->GetFieldID(env, classOfFmAlarmFilter,
			"timeInterval", "I");

	classOfArrayList = (*env)->FindClass(env, "java/util/ArrayList");
	addInArrayList = (*env)->GetMethodID(env, classOfArrayList, "add",
			"(Ljava/lang/Object;)Z");
	getFromArrayList = (*env)->GetMethodID(env, classOfArrayList, "get",
			"(I)Ljava/lang/Object;");
	sizeOfArrayList = (*env)->GetMethodID(env, classOfArrayList, "size", "()I");

	jclass tempHashClass = (*env)->FindClass(env, "java/util/HashMap");
	classOfHashMap = (jclass)(*env)->NewGlobalRef(env, tempHashClass);

	(*env)->DeleteLocalRef(env, tempHashClass);
	initHashMap = (*env)->GetMethodID(env, classOfHashMap, "<init>", "()V");

	putInHashMap = (*env)->GetMethodID(env, classOfHashMap, "put",
			"(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");

	jclass tempEventClass = (*env)->FindClass(env,
			"com/nokia/fm/EventObjectHolder/FmEvent");
	classOfFmEventObject = (jclass)(*env)->NewGlobalRef(env, tempEventClass);
	(*env)->DeleteLocalRef(env, tempEventClass);

	constructorOfFmEventObject = (*env)->GetMethodID(env, classOfFmEventObject,
			"<init>", "()V");
	fieldEventId = (*env)->GetFieldID(env, classOfFmEventObject, "EVENT_ID",
			"Ljava/lang/String;");
	fieldEventCeName = (*env)->GetFieldID(env, classOfFmEventObject,
			"EVENT_CE_NAME", "Ljava/lang/String;");
	fieldProcName = (*env)->GetFieldID(env, classOfFmEventObject,
			"EVENT_PROCESS_NAME", "Ljava/lang/String;");
	fieldEventText = (*env)->GetFieldID(env, classOfFmEventObject,
			"EVENT_SHORT_TEXT", "Ljava/lang/String;");
	fieldEventParam = (*env)->GetFieldID(env, classOfFmEventObject,
			"EVENT_PARAMETER", "Ljava/lang/String;");
	fieldFaultyObject = (*env)->GetFieldID(env, classOfFmEventObject,
			"EVENT_FAULTY_OBJECT", "Ljava/lang/String;");
	fieldSequenceId = (*env)->GetFieldID(env, classOfFmEventObject,
			"SEQUENCE_ID", "J");
	fieldEventType = (*env)->GetFieldID(env, classOfFmEventObject, "EVENT_TYPE",
			"I");
	fieldEventSeverity = (*env)->GetFieldID(env, classOfFmEventObject,
			"EVENT_SEVERITY", "I");
	fieldSpecificId = (*env)->GetFieldID(env, classOfFmEventObject,
			"SPECIFIC_PROBLEM", "I");
	fieldEventTime = (*env)->GetFieldID(env, classOfFmEventObject, "EVENT_TIME",
			"J");
}

void getThreshold(JNIEnv *env){
        RtpGetScalarData("REST_FMAgent.PriorityAlmProcThreshold", NUMBER, &threshold);
        jclass fmUtilClass = (*env)->FindClass(env, "com/nokia/fm/UtilityEngine/FmUtil");
        jfieldID fid = (*env)->GetStaticFieldID(env, fmUtilClass , "priorityThreshold", "I");
        (*env)->SetStaticIntField(env, fmUtilClass , fid, threshold.iData);
}

JNIEXPORT jint JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_registerToNodeManagerNative(
		JNIEnv *env, jclass obj) {
	RtpComRegisterInfo_t regInfo = { 0 };
	if (RtpComRegister(RTP_COM_SIGNAL_CHANGES, &regInfo) == RtpFailure) {
		return (RtpFailure);
	}
	RtpNmReady();
	initialiseJNIObjectTypeRef(env);
	return RtpSuccess;
}

JNIEXPORT jint JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_registerForEventsNative(
		JNIEnv *env, jclass obj, jobject singleTSPEvent) {

	RtpEvtEventFilter_t filter = { 0 };
	RtpInt32 event_severities[] = { RTP_EVT_SEV_CRITICAL, RTP_EVT_SEV_MAJOR,
			RTP_EVT_SEV_MINOR, RTP_EVT_SEV_WARNING, RTP_EVT_SEV_CLEAR, 0 };
	RtpEvtId_t event_ID = { 0 };
	event_ID.msgSet = 0;
	event_ID.msgNum = 0;
	filter.eventIDs = &event_ID;
	filter.eventSeverities = event_severities;

	if (RtpEvtRegisterForEvents(&filter, -1, RtpTrue, RtpFalse) == RtpFailure)
		return RtpFailure;
	else {
		globalFmEventObject = (jobject)(*env)->NewGlobalRef(env,
				singleTSPEvent);
		return RtpSuccess;
	}
}

jlong getByNumberValue(char col[]) {
	jlong val = 0;
	if (strcmp(col, "") == 0) {
		val = -1;
	} else {
		val = atoi(col);
	}
	return val;
}

jboolean getBoolean(char col[]) {
	jboolean val = false;
	if (strcmp(col, "true") == 0)
		val = true;
	else if (strcmp(col, "TRUE") == 0)
                val = true;
	else if (strcmp(col, "false") == 0)
		val = false;
	else if (strcmp(col, "FALSE") == 0)
                val = false;
	return val;
}

jint severity2Number(char severityStr[]) {
	jint val = -1;
	if (strcmp(severityStr, "critical") == 0)
		val = 1;
	else if (strcmp(severityStr, "major") == 0)
		val = 2;
	else if (strcmp(severityStr, "minor") == 0)
		val = 3;
	else if (strcmp(severityStr, "warning") == 0)
		val = 4;
	return val;
}

void getRules(JNIEnv *env) {

	int i, j;
	if (RtpSuccess == RtpGetTableData("AlarmFilter.Rules", &tParam)) {
		for (i = 0; i < tParam.useMeToLoopRows; i++) {
			jobject filterObject = (*env)->NewObject(env, classOfFmAlarmFilter,
					initialiseFilter);
			RtpChar * rowName;
			for (j = 0; j < tParam.useMeToLoopCols; j++) {
				if (strcmp(tParam.tableRow[i].tableColumn[j].columnName,
						"UniqueRuleName") == 0) {
					jStringValue = (*env)->NewStringUTF(env,
							tParam.tableRow[i].tableColumn[j].data);
					rowName = tParam.tableRow[i].tableColumn[j].data;
					(*env)->SetObjectField(env, filterObject, setFilterName,
							jStringValue);

				} else if (strcmp(tParam.tableRow[i].tableColumn[j].columnName,
						"Severity") == 0) {
					jIntValue = severity2Number(
							tParam.tableRow[i].tableColumn[j].data);
					(*env)->SetIntField(env, filterObject, setAlarmSeverity,
							jIntValue);
					if (jIntValue == 4 && strcmp(rowName, "SuppressAllWarnings"))
						filterSeverity = jIntValue;

				} else if (strcmp(tParam.tableRow[i].tableColumn[j].columnName,
						"IsActive") == 0) {
					jBoolValue = getBoolean(tParam.tableRow[i].tableColumn[j].data);
					(*env)->SetBooleanField(env, filterObject, setIsActive,
							jBoolValue);
					if (strcmp(rowName, "SuppressAllWarnings") && jBoolValue)
						filterActive = 1;
				} else if (strcmp(tParam.tableRow[i].tableColumn[j].columnName,
						"IgnoreVariables") == 0) {
					jBoolValue = getBoolean(
							tParam.tableRow[i].tableColumn[j].data);
					(*env)->SetBooleanField(env, filterObject, setIgnoreVars,
							jBoolValue);

				} else if (strcmp(tParam.tableRow[i].tableColumn[j].columnName,
						"StartSpecificProblem") == 0) {
					jIntValue = getByNumberValue(
							tParam.tableRow[i].tableColumn[j].data);
					(*env)->SetIntField(env, filterObject, setAlarmStartId,
							jIntValue);

				} else if (strcmp(tParam.tableRow[i].tableColumn[j].columnName,
						"EndSpecificProblem") == 0) {
					jIntValue = getByNumberValue(
							tParam.tableRow[i].tableColumn[j].data);
					(*env)->SetIntField(env, filterObject, setAlarmEndId,
							jIntValue);

				} else if (strcmp(tParam.tableRow[i].tableColumn[j].columnName,
						"TimeInterval") == 0) {
					jIntValue = atoi(tParam.tableRow[i].tableColumn[j].data);
					(*env)->SetIntField(env, filterObject, setTimeInterval,
							jIntValue);
					if (strcmp(rowName, "SuppressAllWarnings"))
						filterTimeInterval = jIntValue;
				}
			}
						(*env)->CallVoidMethod(env, globalFilterArrayList,
						addInArrayList, filterObject);
		}
	}
}

JNIEXPORT jint JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_getFilterRulesFromNative(
		JNIEnv *env, jclass obj, jobject jarrayFilterlist) {
	globalFilterArrayList = (jobject)(*env)->NewGlobalRef(env,
			jarrayFilterlist);
	getRules(env);
	return 0;
}

JNIEXPORT void JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_getThresholdFromNative
  (JNIEnv *env, jclass obj){
	getThreshold(env);
}

jint getEcompDomains(JNIEnv *env) {

	RtpGetTableData("FM.EmsConnStrings", &ecompConnParam);

	int i, j;
	for (i = 0; i < ecompConnParam.useMeToLoopRows; i++) {
		jobject hashObj = (*env)->NewObject(env, classOfHashMap, initHashMap);
		for (j = 0; j < ecompConnParam.useMeToLoopCols; j++) {
			jstring jstr = (*env)->NewStringUTF(env,
					ecompConnParam.tableRow[i].tableColumn[j].columnName);
			jstring jvalue = (*env)->NewStringUTF(env,
					ecompConnParam.tableRow[i].tableColumn[j].data);
			(*env)->CallObjectMethod(env, hashObj, putInHashMap, jstr, jvalue);
		}
		(*env)->CallBooleanMethod(env, globalEcompDomainMaplist, addInArrayList,
				hashObj);
	}
	return ecompConnParam.useMeToLoopRows;
}

JNIEXPORT jint JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_getEcompTableFromNative(
		JNIEnv *env, jclass obj, jobject ecompDomainMaplist) {
	globalEcompDomainMaplist = (jobject)(*env)->NewGlobalRef(env, ecompDomainMaplist);
	return getEcompDomains(env);
}

void getEcompScalarParam(JNIEnv *env) {

	jstring jstr, jvalue;
	jobject hashObj = (*env)->NewObject(env, classOfHashMap, initHashMap);

	jstr = (*env)->NewStringUTF(env, "Precedence");
	jvalue = (*env)->NewStringUTF(env, "1");
	(*env)->CallObjectMethod(env, hashObj, putInHashMap, jstr, jvalue);

	RtpGetScalarData("REST_FMAgent.TransOSSApiVer", NUMBER, &transOsApiParam);
	char *apiVer = (char *) malloc(5);
	sprintf(apiVer, "%d\0", transOsApiParam.iData);
	jvalue = (*env)->NewStringUTF(env, apiVer);
	jstr = (*env)->NewStringUTF(env, "ApiVer");
	(*env)->CallObjectMethod(env, hashObj, putInHashMap, jstr, jvalue);

	RtpGetScalarData("REST_FMAgent.TransOSSPort", NUMBER, &transOsPortParam);
	char *port = (char *) malloc(10);
	sprintf(port, "%d\0", transOsPortParam.iData);
	jvalue = (*env)->NewStringUTF(env, port);
	jstr = (*env)->NewStringUTF(env, "Port");
	(*env)->CallObjectMethod(env, hashObj, putInHashMap, jstr, jvalue);

	RtpGetScalarData("REST_FMAgent.EmsCredentials", STRING, &emsCredentialParam);
	jvalue = (*env)->NewStringUTF(env, emsCredentialParam.cData);
	jstr = (*env)->NewStringUTF(env, "Credentials");
	(*env)->CallObjectMethod(env, hashObj, putInHashMap, jstr, jvalue);

	RtpGetScalarData("REST_FMAgent.TransOSSRoutePath", STRING, &transOsRouteParam);
	jvalue = (*env)->NewStringUTF(env, transOsRouteParam.cData);
	jstr = (*env)->NewStringUTF(env, "RoutingPath");
	(*env)->CallObjectMethod(env, hashObj, putInHashMap, jstr, jvalue);

	RtpGetScalarData("REST_FMAgent.TransOSSDomain", STRING, &transOsDomainParam);
	jvalue = (*env)->NewStringUTF(env, transOsDomainParam.cData);
	jstr = (*env)->NewStringUTF(env, "Domain");
	(*env)->CallObjectMethod(env, hashObj, putInHashMap, jstr, jvalue);

	(*env)->CallBooleanMethod(env, globalEcompDomainMaplist, addInArrayList,
					hashObj);
}

JNIEXPORT void JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_getEcompScalarParamFromNative (JNIEnv *env, jclass obj) {
	getEcompScalarParam(env);
}

jint getEcompHBinterval(JNIEnv *env){
	RtpGetScalarData("REST_FMAgent.EmsHBInterval", NUMBER, &emsHbIntervalParam);
	return emsHbIntervalParam.iData;
}

JNIEXPORT jint JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_getEcompHBInterval(JNIEnv *env, jclass obj){
	return getEcompHBinterval(env);
}

void getEventFromTSP(JNIEnv *env, jclass obj, RtpEvtAttr_t eventAttr,
		jobject singleEvent) {

	char *eventAttribStr;
	eventAttribStr = (char *) malloc(10);
	sprintf(eventAttribStr, "%d-%d\0", eventAttr.eventSet, eventAttr.eventNum);

	jStringValue = (*env)->NewStringUTF(env, eventAttribStr);
	(*env)->SetObjectField(env, singleEvent, fieldEventId, jStringValue);

	jStringValue = (*env)->NewStringUTF(env, eventAttr.nodeName);
	(*env)->SetObjectField(env, singleEvent, fieldEventCeName, jStringValue);

	jStringValue = (*env)->NewStringUTF(env, eventAttr.procName);
	(*env)->SetObjectField(env, singleEvent, fieldProcName, jStringValue);

	jStringValue = (*env)->NewStringUTF(env, eventAttr.shortText);
	(*env)->SetObjectField(env, singleEvent, fieldEventText, jStringValue);

	jStringValue = (*env)->NewStringUTF(env, eventAttr.faultyObject);
	(*env)->SetObjectField(env, singleEvent, fieldFaultyObject, jStringValue);

	const RtpChar *ptrParam = NULL;
	char *eventParamStr;
	eventParamStr = (char *) calloc(1, 512);
	for (ptrParam = eventAttr.parameters; ptrParam != NULL && *ptrParam != 0;
			ptrParam += Rtpstrlen(ptrParam) + 1) {
		sprintf(eventParamStr, "%s%s%s", eventParamStr, ptrParam, "^0");
	}
	jStringValue = (*env)->NewStringUTF(env, eventParamStr);
	(*env)->SetObjectField(env, singleEvent, fieldEventParam, jStringValue);

	free(eventParamStr);
	free(eventAttribStr);

	(*env)->SetLongField(env, singleEvent, fieldSequenceId,
			(eventAttr.logRecordId));
	(*env)->SetIntField(env, singleEvent, fieldEventType,
			(eventAttr.alarmType));
	(*env)->SetIntField(env, singleEvent, fieldEventSeverity,
			(eventAttr.severity));

	jIntValue = (eventAttr.eventSet) * 65536 + (eventAttr.eventNum);
	(*env)->SetIntField(env, singleEvent, fieldSpecificId, jIntValue);

	jLongValue = (jlong)(
			((eventAttr.eventTime) * 1000)
					+ ((eventAttr.eventTimeUsec) / 1000));
	(*env)->SetLongField(env, singleEvent, fieldEventTime, jLongValue);
}

void unregisterTSPNative() {
	RtpComUnregister();
	RtpNmDetach(0);
}

JNIEXPORT jint JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_receiveSignalAndEventsFromTSPNative(
		JNIEnv *env, jclass obj) {

	while (true) {
		msg.data = NULL;
		msg.length = 0;
		int result = RtpComReceiveMessage(&msg, &msgInfo, RTP_COM_WAIT_FOREVER,
				0, 0);
		if (RtpFailure == result) {
			continue;
		} else {
			switch (msg.type) {
			case RTP_MSG_EVT_EVENT:
				memset(&eventAttr, 0, sizeof(RtpEvtAttr_t));
				eventAttr.versionFlag = RTP_EVT_ATTR_VERSION;
				RtpEvtGetEventAttrs(msg.data, &eventAttr);
				if (eventAttr.severity == 5
						|| (filterSeverity == eventAttr.severity
								&& filterActive == 1 && filterTimeInterval <= 0)) {
					continue;
				}
				//convert the message and fill singleEventInstance and then return
				if (eventAttr.eventSet != 145) {
					getEventFromTSP(env, obj, eventAttr, globalFmEventObject);
					return msg.type;
				}
				break;
			case RTP_CONFIG_PARAM_DATA:
				if (strstr(msg.data, "PriorityAlmProcThreshold") != NULL) {
					getThreshold(env);
				} else if (strstr(msg.data, "EmsConnStrings") != NULL) {
					if(globalEcompDomainMaplist != NULL){
						getEcompDomains(env);
						return ECOMP_TABLE_MSG;
					}
				} else if (strstr(msg.data, "Rules") != NULL) {
					if(globalFilterArrayList != NULL){
						getRules(env);
						return ALARM_FILTER_RULES_MSG;
					}
				} else if (strstr(msg.data, "EmsHBInterval") != NULL) {
					return getEcompHBinterval(env);
				} else {
					if(globalEcompDomainMaplist != NULL){
						getEcompScalarParam(env);
						return ECOMP_SCALAR_MSG;
					}
				}
				break;
			case RTP_MSG_NM_SHUTDOWNREQUEST:
				unregisterTSPNative();
				return msg.type;
			default:
				break;
			}
		}
	}
}

JNIEXPORT void JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_recoverTSPEventRangeNative
(JNIEnv *env, jclass obj, jobject evtList, jlong start_Seq_Num, jlong end_Seq_Num) {

	RtpEvtAttr_t eventAttr = {0};
	long int i=0;
	for ( i = start_Seq_Num; i <= end_Seq_Num; i++)
	{
		eventAttr.versionFlag=RTP_EVT_ATTR_VERSION;
		if (RtpEvtRecoverEvent(NULL, 0, i, RtpTrue, &eventAttr) == RtpSuccess) {
			if (eventAttr.severity == 5 || eventAttr.severity > 6 || (filterSeverity == eventAttr.severity
							&& filterActive == 1 && filterTimeInterval <= 0)) {
				continue;
			} else {
				if(eventAttr.eventSet != 145) {
					jobject singleEvent = (*env)->NewObject(env, classOfFmEventObject, constructorOfFmEventObject);
					getEventFromTSP(env, obj, eventAttr, singleEvent);
					(*env)->CallVoidMethod(env, evtList, addInArrayList, singleEvent);
				}
			}
		}
	}
}

JNIEXPORT void JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_recoverTSPEventListNative
(JNIEnv *env, jclass obj, jobject esymacAlarmList, jlongArray seqList) {
	RtpEvtAttr_t eventAttr = {0};
	jint sizeOfSeqList = (*env)->GetArrayLength(env, seqList);
	jlong* seqIdArray = (*env)->GetLongArrayElements(env, seqList, 0);
	int i;
	for ( i = sizeOfSeqList - 1; i >= 0; i--) {
		long int seq = seqIdArray[i];
		eventAttr.versionFlag=RTP_EVT_ATTR_VERSION;
		if (RtpEvtRecoverEvent(NULL, 0, seq, RtpTrue, &eventAttr) == RtpSuccess) {
			if (eventAttr.severity == 5 || eventAttr.severity > 6 ) {
				continue;
			} else {
				if(eventAttr.eventSet != 145) {
					jobject singleEvent = (*env)->NewObject(env, classOfFmEventObject, constructorOfFmEventObject);
					getEventFromTSP(env, obj, eventAttr, singleEvent);
					(*env)->CallVoidMethod(env, esymacAlarmList, addInArrayList, singleEvent);
				}
			}
		}
	}
}

JNIEXPORT void JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_recoverTSPEventBulkNative
(JNIEnv *env, jclass obj, jobject eventList, jlong endSeqId, jlong startSeqId, jint maxEvents) {

	RtpEvtAttr_t eventAttr = {0};
	long int i;
	int count = 0;
	for ( i = endSeqId; i > startSeqId && count < maxEvents; i--) {
		eventAttr.versionFlag=RTP_EVT_ATTR_VERSION;
		if (RtpEvtRecoverEvent(NULL, 0, i, RtpTrue, &eventAttr) == RtpSuccess) {
			if (eventAttr.severity == 5 || eventAttr.severity > 6 || (filterSeverity == eventAttr.severity
							&& filterActive == 1 && filterTimeInterval <= 0)) {
				continue;
			} else {
				if(eventAttr.eventSet != 145) {
					jobject singleEvent = (*env)->NewObject(env, classOfFmEventObject, constructorOfFmEventObject);
					getEventFromTSP(env, obj, eventAttr, singleEvent);
					(*env)->CallVoidMethod(env, eventList, addInArrayList, singleEvent);
					count++;
				}
			}
		}
	}
}

JNIEXPORT jint JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_latestTSPSeqIdNative(
		JNIEnv *env, jclass obj) {
	return RtpEvtGetLogRecordId(-1);
}

char *rbac_authorize_user_operation(char *username, char *permission,
		char *service_name);

JNIEXPORT jboolean JNICALL Java_com_nokia_fm_NativeCodeEngine_NativeCode_authorizeUserNative(
		JNIEnv *env, jclass obj, jstring userName, jstring operation,
		jstring serviceType) {

	char *result = NULL;
	const char *username = (*env)->GetStringUTFChars(env, userName, NULL);
	const char *permission = (*env)->GetStringUTFChars(env, operation, NULL);
	const char *service_name = (*env)->GetStringUTFChars(env, serviceType,
			NULL);
	if (NULL == permission || NULL == username || NULL == service_name)
		return 0;

	result = rbac_authorize_user_operation((char *) username,
			(char *) permission, (char *) service_name);
	if (strcmp(result, "TRUE") == 0) {
		return 1;
	} else if (strcmp(result, "CNUM_NOT_ACTIVATED") == 0) {
		return 1;
	} else {
		return 0;
	}
	return 1;
}
