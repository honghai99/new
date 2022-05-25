/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : ErrMsgMgmtDBDAO.java
*@FileTitle : Practice 4
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.integration;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.asprise.util.tiff.C;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.basic.ErrMsgMgmtBCImpl;
import com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.vo.ErrMsgVO;
import com.clt.framework.component.message.ErrorHandler;
import com.clt.framework.component.rowset.DBRowSet;
import com.clt.framework.core.layer.integration.DAOException;
import com.clt.framework.support.db.ISQLTemplate;
import com.clt.framework.support.db.RowSetUtil;
import com.clt.framework.support.db.SQLExecuter;
import com.clt.framework.support.layer.integration.DBDAOSupport;


/**
 * ALPS ErrMsgMgmtDBDAO <br>
 * - ALPS-newgen system Business Logic을 처리하기 위한 JDBC 작업수행.<br>
 * 
 * @author Hai To
 * @see ErrMsgMgmtBCImpl 참조
 * @since J2EE 1.6
 */
public class ErrMsgMgmtDBDAO extends DBDAOSupport {

	/**
	 * @param ErrMsgVO errMsgVO
	 * @return List<ErrMsgVO>
	 * @exception DAOException
	 */
	 @SuppressWarnings("unchecked")
	public List<ErrMsgVO> searchErrMsgVO(ErrMsgVO errMsgVO) throws DAOException {
		DBRowSet dbRowset = null;
		List<ErrMsgVO> list = null;
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();

		try{
			if(errMsgVO != null){
				Map<String, String> mapVO = errMsgVO .getColumnValues();
				List<String> jo_crr_cds = new ArrayList<>();
				if(errMsgVO.getJoCrrCd()!=null) {
					String[] crr_cd = errMsgVO.getJoCrrCd().split(",");
					for(int i = 0; i<crr_cd.length; i++) {
						jo_crr_cds.add(crr_cd[i]);
					}
				}
				
				param.putAll(mapVO);
				param.put("jo_crr_cds", jo_crr_cds);
				
				velParam.putAll(mapVO);
				velParam.put("jo_crr_cds", jo_crr_cds);
				
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVORSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, ErrMsgVO .class);
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * @param ErrMsgVO errMsgVO
	 * @exception DAOException
	 * @exception Exception
	 */
	public void addmanageErrMsgVO(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			
			param.putAll(mapVO);
			velParam.putAll(mapVO);
			
			SQLExecuter sqlExe = new SQLExecuter("");
			int result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVOCSQL(), param, velParam);
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
	}
	
	/**
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	public int modifymanageErrMsgVO(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		int result = 0;
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			
			param.putAll(mapVO);
			velParam.putAll(mapVO);
			
			SQLExecuter sqlExe = new SQLExecuter("");
			result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVOUSQL(), param, velParam);
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return result;
	}
	
	/**
	 * 
	 * @param ErrMsgVO errMsgVO
	 * @return int
	 * @exception DAOException
	 * @exception Exception
	 */
	public int removeManageErrMsgVO(ErrMsgVO errMsgVO) throws DAOException,Exception {
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velocity parameter
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		int result = 0;
		try {
			Map<String, String> mapVO = errMsgVO .getColumnValues();
			
			param.putAll(mapVO);
			velParam.putAll(mapVO);
			
			SQLExecuter sqlExe = new SQLExecuter("");
			result = sqlExe.executeUpdate((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVODSQL(), param, velParam);
			if(result == Statement.EXECUTE_FAILED)
					throw new DAOException("Fail to insert SQL");
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return result;
	}

	/**
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] addManageErrMsgVOS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int insCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(errMsgVO .size() > 0){
				insCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVOCSQL(), errMsgVO,null);
				for(int i = 0; i < insCnt.length; i++){
					if(insCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return insCnt;
	}
	/**
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] modifyManageErrMsgVOS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int updCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(errMsgVO .size() > 0){
				updCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVOUSQL(), errMsgVO,null);
				for(int i = 0; i < updCnt.length; i++){
					if(updCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return updCnt;
	}
	
	/**
	 * 
	 * @param List<ErrMsgVO> errMsgVO
	 * @return int[]
	 * @exception DAOException
	 * @exception Exception
	 */
	public int[] removeManageErrMsgVOS(List<ErrMsgVO> errMsgVO) throws DAOException,Exception {
		int delCnt[] = null;
		try {
			SQLExecuter sqlExe = new SQLExecuter("");
			if(errMsgVO .size() > 0){
				delCnt = sqlExe.executeBatch((ISQLTemplate)new ErrMsgMgmtDBDAOErrMsgVODSQL(), errMsgVO,null);
				for(int i = 0; i < delCnt.length; i++){
					if(delCnt[i]== Statement.EXECUTE_FAILED)
						throw new DAOException("Fail to insert No"+ i + " SQL");
				}
			}
		} catch(SQLException se) {
			log.error(se.getMessage(),se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch(Exception ex) {
			log.error(ex.getMessage(),ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return delCnt;
	}
	
	/**
	 * 
	 * @param errMsgVO
	 * @return
	 * @throws DAOException
	 * @throws Exception
	 */
	public List<ErrMsgVO> searchCarrier(ErrMsgVO errMsgVO) throws DAOException,Exception {
		DBRowSet dbRowset = null;
		List<ErrMsgVO> list = new ArrayList<ErrMsgVO>();
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velparam
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try {
			if(errMsgVO != null) {
				Map<String, String> mapVO = errMsgVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new ErrMsgMgmtDBDAOSearchCarrierRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, ErrMsgVO .class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * 
	 * @param errMsgVO
	 * @return
	 * @throws DAOException
	 * @throws Exception
	 */
	public List<ErrMsgVO> searchLane(ErrMsgVO errMsgVO) throws DAOException,Exception {
		DBRowSet dbRowset = null;
		List<ErrMsgVO> list = new ArrayList<ErrMsgVO>();
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velparam
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try {
			if(errMsgVO != null) {
				Map<String, String> mapVO = errMsgVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new ErrMsgMgmtDBDAOSearchRLaneCdRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, ErrMsgVO .class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	/**
	 * 
	 * @param errMsgVO
	 * @return
	 * @throws DAOException
	 * @throws Exception
	 */
	public List<ErrMsgVO> checkDuplicate(ErrMsgVO errMsgVO) throws DAOException,Exception {
		DBRowSet dbRowset = null;
		List<ErrMsgVO> list = new ArrayList<ErrMsgVO>();
		//query parameter
		Map<String, Object> param = new HashMap<String, Object>();
		//velparam
		Map<String, Object> velParam = new HashMap<String, Object>();
		
		try {
			if(errMsgVO != null) {
				Map<String, String> mapVO = errMsgVO.getColumnValues();
				param.putAll(mapVO);
				velParam.putAll(mapVO);
			}
			dbRowset = new SQLExecuter("").executeQuery((ISQLTemplate)new CheckDuplicateCarrierRSQL(), param, velParam);
			list = (List)RowSetUtil.rowSetToVOs(dbRowset, ErrMsgVO .class);
		} catch (SQLException se) {
			log.error(se.getMessage(), se);
			throw new DAOException(new ErrorHandler(se).getMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new DAOException(new ErrorHandler(ex).getMessage());
		}
		return list;
	}
	
	
	
}