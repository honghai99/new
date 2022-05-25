/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : newgenSC.java
*@FileTitle : Practice 4
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.newgen;

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.basic.ErrMsgMgmtBC;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.event.DouTrn0004Event;
import com.clt.framework.core.layer.event.Event;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.event.EventResponse;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.GeneralEventResponse;
import com.clt.framework.support.controller.html.FormCommand;
import com.clt.framework.support.layer.service.ServiceCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.vo.ErrMsgVO;


/**
 * ALPS-newgen Business Logic ServiceCommand 
 * @author Hai To
 * @see ErrMsgMgmtDBDAO
 * @since J2EE 1.6
 */

public class NewGenSC extends ServiceCommandSupport {
	// Login User Information
	private SignOnUserAccount account = null;

	/**
	 * newgen system Preceding work scenario<br>
	 * 
	 */
	public void doStart() {
		log.debug("NewGenSC ");
		try {
			account = getSignOnUserAccount();
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

	/**
	 * newgen system Closing the work scenario
	 * Release related internal objects at the end of the business scenario
	 */
	public void doEnd() {
		log.debug("NewGenSC");
	}

	/**
	 * 
	 * @param e Event
	 * @return EventResponse
	 * @exception EventException
	 */
	public EventResponse perform(Event e) throws EventException {
		// RDTO(Data Transfer Object including Parameters)
		EventResponse eventResponse = null;

		// SC - The part you should use if you are handling multiple events
		if (e.getEventName().equalsIgnoreCase("DouTrn0004Event")) {
			if (e.getFormCommand().isCommand(FormCommand.SEARCH)) {
				eventResponse = searchErrMsgVO(e);
			} else if (e.getFormCommand().isCommand(FormCommand.MULTI)) {
				eventResponse = manageErrMsgVO(e);
			} else if(e.getFormCommand().isCommand(FormCommand.DEFAULT)) {
				eventResponse = initData(e);
			} else if(e.getFormCommand().isCommand(FormCommand.COMMAND01)) {
				eventResponse = checkDuplicated(e);
			} else if(e.getFormCommand().isCommand(FormCommand.COMMAND02)) {
				eventResponse = checkUpdated(e);
			}
		}
		return eventResponse;
	}
	
	/**
	 * 
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse searchErrMsgVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();

		try{
			List<ErrMsgVO> list = command.searchErrMsgVO(event.getErrMsgVO());
			eventResponse.setRsVoList(list);
		}catch(EventException ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}catch(Exception ex){
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
		return eventResponse;
	}
	
	/**
	 * Use to manage the carrier such as: create, modify, or delete
	 *
	 * @param Event e
	 * @return EventResponse
	 * @exception EventException
	 */
	private EventResponse manageErrMsgVO(Event e) throws EventException {
		// PDTO(Data Transfer Object including Parameters)
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
		try{
			begin();
			command.manageErrMsgVO(event.getErrMsgVOS(),account);
			eventResponse.setUserMessage(new ErrorHandler("BKG06071").getUserMessage());
			//chủ động message, check ErrorHandler. 
			commit();
		} catch(EventException ex) {
			rollback();
			throw new EventException(new ErrorHandler("BKG06072").getMessage(),ex);
		} catch(Exception ex) {
			rollback();
			throw new EventException(new ErrorHandler("BKG06072").getMessage(),ex);
		}
		return eventResponse;
	}

	/**
	 * Set ETC data for UI to make combobox
	 * @param e
	 * @return
	 * @throws EventException
	 */
	private EventResponse initData(Event e) throws EventException {
		GeneralEventResponse eventResponse = new GeneralEventResponse();
		DouTrn0004Event event = (DouTrn0004Event)e;
		ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
		
		try {
			List<ErrMsgVO> carriers = command.searchCarrier(event.getErrMsgVO());
			StringBuilder carrierBuilder = new StringBuilder();
			
			if(carriers!=null && carriers.size()>0) {
				for(int i = 0; i<carriers.size(); i++) {
					carrierBuilder.append(carriers.get(i).getJoCrrCd());	
					if (i<carriers.size()-1) {
						carrierBuilder.append("|");
					}
				}
			}		
			
			List<ErrMsgVO> lanes = command.searchLane(event.getErrMsgVO());
			StringBuilder laneBuilder = new StringBuilder();
			
			if(lanes!=null && lanes.size()>0) {
				for(int i = 0; i<lanes.size(); i++) {
					laneBuilder.append(lanes.get(i).getRlaneCd());
					if(i<lanes.size()-1) {
						laneBuilder.append("|");
					}
				}
			}
			
		eventResponse.setETCData("jo_crr_cd", carrierBuilder.toString());
		eventResponse.setETCData("rlane_cd", laneBuilder.toString());
					
		} catch (EventException de) {
			log.error("err " +de.getMessage(),de );
			throw new EventException(de.getMessage());
			} 	
		
		return eventResponse;		
	}
	
		/**
		 * Check duplicated carrier and rev lane 
		 * @param e
		 * @return
		 * @throws EventException
		 */
		private EventResponse checkDuplicated(Event e) throws EventException {
			GeneralEventResponse eventResponse = new GeneralEventResponse();
			DouTrn0004Event event = (DouTrn0004Event)e;
			ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
			
			try {
				//if any carrier and code exists in List, it will be > than 0 
				List<ErrMsgVO> carriers = command.searchErrMsgVO(event.getErrMsgVO());
				if(null == carriers){
					carriers = new ArrayList<>();
				}
				eventResponse.setETCData("ISEXIST", carriers.size() > 0 ? "Y" : "N");		 //catch ETC data from client "ISEXIST"
			} catch (EventException de) {
				log.error("err " +de.getMessage(),de );
				throw new EventException(de.getMessage());
				} 			
			return eventResponse;			
		}
		
		private EventResponse checkUpdated(Event e) throws EventException {
			GeneralEventResponse eventResponse = new GeneralEventResponse();
			DouTrn0004Event event = (DouTrn0004Event)e;
			ErrMsgMgmtBC command = new ErrMsgMgmtBCImpl();
			ErrMsgVO errMsg = new ErrMsgVO();
			try {			
				List<ErrMsgVO> carriers = command.searchErrMsgVO(event.getErrMsgVO());
				carriers.get(2).toString(); 
				System.out.println(carriers.toString());
				eventResponse.setETCData("ISEXIST", carriers.get(2).toString());		 //catch ETC data from client "ISEXIST"
			} catch (EventException de) {
				log.error("err " +de.getMessage(),de );
				throw new EventException(de.getMessage());
				} 
			
			
			
			return eventResponse;			
		}
		

	
}