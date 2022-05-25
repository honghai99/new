/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtBCImpl.java
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

import java.util.ArrayList;
import java.util.List;

import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.integration.ErrMsgMgmtDBDAO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.core.layer.event.EventException;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.layer.basic.BasicCommandSupport;
import com.clt.framework.support.view.signon.SignOnUserAccount;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.vo.ErrMsgVO;

/**
 * ALPS-newgen Business Logic Command Interface<br>
 * 
 *
 * @author Hai To
 * @since J2EE 1.6
 */
public class ErrMsgMgmtBCImpl extends BasicCommandSupport implements ErrMsgMgmtBC {

	// Database Access Object
	private transient ErrMsgMgmtDBDAO dbDao = null;

	/**
	 */
	public ErrMsgMgmtBCImpl() {
		dbDao = new ErrMsgMgmtDBDAO();
	}
	/**
	 * 
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception EventException
	 */
	public List<ErrMsgVO> searchErrMsgVO(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.searchErrMsgVO(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
	}
	
	/**
	 * 
	 * 
	 * @param ErrMsgVO[] errMsgVO
	 * @param account SignOnUserAccount
	 * @exception EventException
	 */
	public void manageErrMsgVO(ErrMsgVO[] errMsgVO, SignOnUserAccount account) throws EventException{
		try {
			List<ErrMsgVO> insertVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> updateVoList = new ArrayList<ErrMsgVO>();
			List<ErrMsgVO> deleteVoList = new ArrayList<ErrMsgVO>();
			
			for ( int i=0; i<errMsgVO .length; i++ ) {
				if ( errMsgVO[i].getIbflag().equals("I")){
					if(checkDuplicate(errMsgVO[i]).size()>0) {
						throw new EventException(new ErrorHandler("ERR99999").getMessage());
					} else {
						errMsgVO[i].setCreUsrId(account.getUsr_id());
						errMsgVO[i].setUpdUsrId(account.getUsr_id());
						insertVoList.add(errMsgVO[i]);
					}
				} else if (errMsgVO[i].getIbflag().equals("U")) {
					errMsgVO[i].setUpdUsrId(account.getUsr_id());
					updateVoList.add(errMsgVO[i]);

				} else if (errMsgVO[i].getIbflag().equals("D")) {
					deleteVoList.add(errMsgVO[i]);
				}
			}
			
			if ( insertVoList.size() > 0 ) {
				dbDao.addManageErrMsgVOS(insertVoList);
			}
			
			if ( updateVoList.size() > 0 ) {
				dbDao.modifyManageErrMsgVOS(updateVoList);
			}
			
			if ( deleteVoList.size() > 0 ) {
				dbDao.removeManageErrMsgVOS(deleteVoList);
			}
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}
	}
	
	
	/**
	 * Search carrier for combo box
	 * @param errMsgVO
	 * @return
	 * @throws EventException
	 */
	@Override
	public List<ErrMsgVO> searchCarrier(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.searchCarrier(errMsgVO);
		} catch(DAOException ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		} catch (Exception ex) {
			throw new EventException(new ErrorHandler(ex).getMessage(),ex);
		}	
	}
	
	/**
	 * Search lane for combobox
	 * @param errMsgVO
	 * @return
	 * @throws EventException
	 */
	@Override
	public List<ErrMsgVO> searchLane(ErrMsgVO errMsgVO) throws EventException {		
		try {
			return dbDao.searchLane(errMsgVO);
		} catch (DAOException e) {
			throw new EventException(new ErrorHandler(e).getMessage(),e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new EventException(new ErrorHandler(e).getMessage(),e);
		}
	}
	

	@Override
	/**
	 * Check duplicate when inserting new data
	 * @param errMsgVO
	 * @return List
	 * @throws EventException
	 */
	public List<ErrMsgVO> checkDuplicate(ErrMsgVO errMsgVO) throws EventException {
		try {
			return dbDao.checkDuplicate(errMsgVO);
		} catch (DAOException e) {
			throw new EventException(new ErrorHandler(e).getMessage(),e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new EventException(new ErrorHandler(e).getMessage(),e);
		}
	}	
}