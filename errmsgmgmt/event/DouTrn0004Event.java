/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DouTrn0004Event.java
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

import com.clt.framework.support.layer.event.EventSupport;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.vo.ErrMsgVO;


/**
 * DOU_TRN_0004 에 대한 PDTO(Data Transfer Object including Parameters)<br>
 * -  DOU_TRN_0004HTMLAction에서 작성<br>
 * - ServiceCommand Layer로 전달하는 PDTO로 사용<br>
 *
 * @author Hai To
 * @see DOU_TRN_0004HTMLAction 참조
 * @since J2EE 1.6
 */

public class DouTrn0004Event extends EventSupport {

	private static final long serialVersionUID = 1L;
	
	/** Table Value Object 조회 조건 및 단건 처리  */
	ErrMsgVO errMsgVO = null;
	
	/** Table Value Object Multi Data 처리 */
	ErrMsgVO[] errMsgVOs = null;

	public DouTrn0004Event(){}
	
	public void setErrMsgVO(ErrMsgVO errMsgVO){
		this. errMsgVO = errMsgVO;
	}

	public void setErrMsgVOS(ErrMsgVO[] errMsgVOs){
		this. errMsgVOs = errMsgVOs;
	}

	public ErrMsgVO getErrMsgVO(){
		return errMsgVO;
	}

	public ErrMsgVO[] getErrMsgVOS(){
		return errMsgVOs;
	}

}