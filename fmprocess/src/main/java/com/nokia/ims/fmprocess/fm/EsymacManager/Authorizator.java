package com.nokia.ims.fmprocess.fm.EsymacManager;

//import com.nokia.ims.fmprocess.fm.NativeCodeEngine.FmNativeCodeEngine;
/*import com.nokia.j2ssp.comp.esymac.api.AuthorizationCallback;
import com.nokia.j2ssp.comp.esymac.api.batch.ContentType;
import com.nokia.j2ssp.comp.esymac.api.request.OperationRequest;
import com.nokia.j2ssp.comp.esymac.api.request.ProvisionRequest;*/

//public class Authorizator implements AuthorizationCallback {
	public class Authorizator{/*

	public boolean isRequestAuthorized(OperationRequest request) {
		String userName = request.getRequestor().getUser();
		if (userName.equals("wsuser"))
			return true;
		if (request instanceof ProvisionRequest) {
			try {
				if ((((ProvisionRequest) request).getConfiguration().getManagedObjectDataFileProperties().getContentType()).equals(ContentType.OFAS))
					return FmNativeCodeEngine.authorizeUser(userName, "FM_CLEAR", "FM_AGENT");
			} catch (Exception e) {
				e.printStackTrace();
				return true;
			}
		}
		return true;
	}*/
}