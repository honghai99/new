/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : JooCarrierMgmtDBDAOSearchRLaneCdRSQL.java
*@FileTitle : fdf
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.06
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.06 
* 1.0 Creation
=========================================================*/
package com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.integration;

import java.util.HashMap;
import org.apache.log4j.Logger;
import com.clt.framework.support.db.ISQLTemplate;

/**
 *
 * @author Hai To
 * @see DAO 참조
 * @since J2EE 1.6
 */

public class ErrMsgMgmtDBDAOSearchCarrierRSQL implements ISQLTemplate{

	private StringBuffer query = new StringBuffer();
	
	Logger log =Logger.getLogger(this.getClass());
	
	/** Parameters definition in params/param elements */
	private HashMap<String,String[]> params = null;
	
	/**
	  * <pre>
	  * get data for combo carrier
	  * </pre>
	  */
	public ErrMsgMgmtDBDAOSearchCarrierRSQL(){
		setQuery();
		params = new HashMap<String,String[]>();
		query.append("/*").append("\n"); 
		query.append("Path : com.clt.apps.opus.esm.clv.newgen.errmsgmgmt.integration").append("\n"); 
		query.append("FileName : JooCarrierMgmtDBDAOSearchRLaneCdRSQL").append("\n"); 
		query.append("*/").append("\n"); 
	}
	
	public String getSQL(){
		return query.toString();
	}
	
	public HashMap<String,String[]> getParams() {
		return params;
	}

	/**
	 * Query 생성
	 */
	public void setQuery(){
		query.append("SELECT JO_CRR_CD" ).append("\n"); 
		query.append("FROM JOO_CARRIER " ).append("\n"); 
		query.append("GROUP BY JO_CRR_CD" ).append("\n"); 
		query.append("ORDER BY JO_CRR_CD " ).append("\n"); 

	}
}