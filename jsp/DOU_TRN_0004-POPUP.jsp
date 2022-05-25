<%
/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0004.jsp
*@FileTitle : Practice 4
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
* 1.0 Creation
=========================================================*/
%>

<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.clt.framework.component.util.JSPUtil"%>
<%@ page import="com.clt.framework.component.util.DateTime"%>
<%@ page import="com.clt.framework.component.message.ErrorHandler"%>
<%@ page import="com.clt.framework.core.layer.event.GeneralEventResponse"%>
<%@ page import="com.clt.framework.support.controller.html.CommonWebKeys"%>
<%@ page import="com.clt.framework.support.view.signon.SignOnUserAccount"%>
<%@ page import="com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.event.DouTrn0004Event"%>
<%@ page import="org.apache.log4j.Logger" %>

<%
	DouTrn0004Event  event = null;					//PDTO(Data Transfer Object including Parameters)
	Exception serverException   = null;			//서버에서 발생한 에러
	String strErrMsg = "";						//에러메세지
	int rowCount	 = 0;						//DB ResultSet 리스트의 건수
	
	String lane        = "";
	String carrier     = "";
	String successFlag = "";
	String codeList    = "";
	String pageRows    = "100";

	String strUsr_id		= "";
	String strUsr_nm		= "";
	Logger log = Logger.getLogger("com.clt.apps.newgen.ErrMsgMgmt");

	try {
	   	SignOnUserAccount account=(SignOnUserAccount)session.getAttribute(CommonWebKeys.SIGN_ON_USER_ACCOUNT);
		strUsr_id =	account.getUsr_id();
		strUsr_nm = account.getUsr_nm();


		event = (DouTrn0004Event)request.getAttribute("Event");
		serverException = (Exception)request.getAttribute(CommonWebKeys.EXCEPTION_OBJECT);
	
		if (serverException != null) {
			strErrMsg = new ErrorHandler(serverException).loadPopupMessage();
		}

		// 초기화면 로딩시 서버로부터 가져온 데이터 추출하는 로직추가 ..
		GeneralEventResponse eventResponse = (GeneralEventResponse) request.getAttribute("EventResponse");
		carrier = eventResponse.getETCData("jo_crr_cd");


	}catch(Exception e) {
		out.println(e.toString());
	}
%>

<head>
<title>Practice 4</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script language="javascript">
	var carrier = "<%=carrier%>";
	function setupPage(){
		var errMessage = "<%=strErrMsg%>";
		if (errMessage.length >= 1) {
			ComShowMessage(errMessage);
		} // end if
		loadPage();
	}
</script>
</head>


<form name="form">
<input type="hidden" name="f_cmd">
<input type="hidden" name="pagerows">
<!-- 개발자 작업	-->
	<div class="page_title_area clear">
		
			<!-- page_title(S) -->
		<h2 class="page_title"><button type="button"><span id="title"></span></button></h2>
		<!-- page_title(E) -->
		
		<!-- opus_design_btn (S) -->
		<div class="opus_design_btn">
			<button class="btn_normal" name="btn_Save" id="btn_Save" type="button">Submit</button><!-- 
			--><button class="btn_normal" name="btn_Search" id="btn_Search" type="button">Search</button>
		</div>
		<!-- opus_design_btn (E) -->
	
		<!-- page_location(E) -->
	</div>
	

	
	<div class="wrap_result">
	<!-- layout_wrap(S) -->
	<div class="layout_wrap">	
		<div class="opus_design_grid">
			<!-- opus_design_btn (E) -->
			<script type="text/javascript">ComSheetObject('sheet1');</script>	
		</div>
		<!-- opus_design_grid(E) -->
			
	</div>
</div>
	


<!-- 개발자 작업  끝 -->
</form>
