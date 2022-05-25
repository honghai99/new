/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0004HTMLAction.java
*@FileTitle : Practice 4
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.event;

import javax.servlet.http.HttpServletRequest;

import com.clt.framework.component.util.JSPUtil;
import com.clt.framework.core.controller.html.HTMLActionException;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.support.controller.HTMLActionSupport;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.vo.ErrMsgVO;


/**
 * HTTP Parser<br>
 * - Parsing the Value of the HTML DOM object sent to the server through the com.clt.apps.opus.esm.clv.newgen screen as a Java variable
 * - Convert parsing information into Event, put it in request and request execution with newgenSC
 * - Set EventResponse to request to send execution result from newgenSC to View (JSP)
 * @author Hai To
 * @see newgenEvent 참조
 * @since J2EE 1.6
 */

public class DOU_TRN_0004HTMLAction extends HTMLActionSupport {

	private static final long serialVersionUID = 1L;
	/**
	 * DOU_TRN_0004HTMLAction 객체를 생성
	 */
	public DOU_TRN_0004HTMLAction() {}

	/**
	 * Parsing HTML DOM object's Value into Java variable<br>
	 * Parsing the information of HttpRequst as newgenEvent and setting it in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @return Event Event interface를 구현한 객체
	 * @exception HTMLActionException
	 */
	public Event perform(HttpServletRequest request) throws HTMLActionException {
		
    	FormCommand command = FormCommand.fromRequest(request); //get request from client
		DouTrn0004Event event = new DouTrn0004Event(); 
		ErrMsgVO errMsgVO = new ErrMsgVO();
		if(command.isCommand(FormCommand.MULTI)) {
			event.setErrMsgVOS((ErrMsgVO[])getVOs(request, ErrMsgVO .class,""));
		}
		else if(command.isCommand(FormCommand.SEARCH)) {		
			errMsgVO.setJoCrrCd(JSPUtil.getParameter(request, "s_jo_crr_cd", "")); //get param carrier
			errMsgVO.setVndrSeq(JSPUtil.getParameter(request, "s_vndr_seq", ""));  //get param lane
			errMsgVO.setCreDtFm(JSPUtil.getParameter(request, "s_cre_dt_fm", ""));
			errMsgVO.setCreDtTo(JSPUtil.getParameter(request, "s_cre_dt_to", ""));
			event.setErrMsgVO(errMsgVO); 
		} else if(command.isCommand(FormCommand.COMMAND01)) {
			event.setErrMsgVO((ErrMsgVO)getVO(request, ErrMsgVO.class,"")); //java reflection
		} else if(command.isCommand(FormCommand.COMMAND02)) {
			event.setErrMsgVO((ErrMsgVO)getVO(request, ErrMsgVO.class,"")); //java reflection
		} 
	
		return  event;
	}

	 /**
	 * Saving the value of the task scenario execution result in the attribute of HttpRequest<br>
	 * Setting the ResultSet that transmits the execution result from ServiceCommand to View (JSP) in the request<br>
	 * @param request HttpServletRequest HttpRequest
	 * @param eventResponse EventResponse interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, EventResponse eventResponse) {
		request.setAttribute("EventResponse", eventResponse);
	}

	 /**
	 * Save HttpRequest parsing result value in HttpRequest attribute<br>
	 * HttpRequest parsing result value set in request<br>
	 * 
	 * @param request HttpServletRequest HttpRequest
	 * @param event Event interface를 구현한 객체
	 */
	public void doEnd(HttpServletRequest request, Event event) {
		request.setAttribute("Event", event);
	}
}