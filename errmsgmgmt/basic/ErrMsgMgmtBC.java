/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtBC.java
*@FileTitle : Practice 4
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.basic;

import java.util.List;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.vo.ErrMsgVO;

/**
 * ALPS-Newgen Business Logic Command Interface<br>
 *
 * @author Hai To
 * @since J2EE 1.6
 */

public interface ErrMsgMgmtBC {

	/**
	 * 
	 * @param ErrMsgVO	errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchErrMsgVO(ErrMsgVO errMsgVO) throws EventException;
	
	/**	 
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageErrMsgVO(ErrMsgVO[] errMsgVO,SignOnUserAccount account) throws EventException;
	
	/**
	 * Search carrier for combo box
	 * @param errMsgVO
	 * @return
	 * @throws EventException
	 */
	public List<ErrMsgVO> searchCarrier(ErrMsgVO errMsgVO) throws EventException;
	
	/**
	 * search lane for combobox
	 * @param errMsgVO
	 * @return
	 * @throws EventException
	 */
	public List<ErrMsgVO> searchLane(ErrMsgVO errMsgVO) throws EventException;
	
	/**
	 * 
	 * @param errMsgVO
	 * @return
	 * @throws EventException
	 */
	public List<ErrMsgVO> checkDuplicate(ErrMsgVO errMsgVO) throws EventException;
	
}