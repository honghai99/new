
/*=========================================================
*Copyright(c) 2022 CyberLogitec
*@FileName : DOU_TRN_0004.js
*@FileTitle : Practice 4
*Open Issues :
*Change history :
*@LastModifyDate : 2022.05.05
*@LastModifier : 
*@LastVersion : 1.0
* 2022.05.05 
* 1.0 Creation
=========================================================*/
/****************************************************************************************
  이벤트 구분 코드: [초기화]INIT=0; [입력]ADD=1; [조회]SEARCH=2; [리스트조회]SEARCHLIST=3;
					[수정]MODIFY=4; [삭제]REMOVE=5; [리스트삭제]REMOVELIST=6 [다중처리]MULTI=7
					기타 여분의 문자상수  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

/*------------------다음 코드는 JSDoc을 잘 만들기 위해서 추가된 코드임 ------------------*/
   /**
     * @fileoverview 업무에서 공통으로 사용하는 자바스크립트파일로 달력 관련 함수가 정의되어 있다.
     * @author 한진해운
     */

    /**
     * @extends 
     * @class DOU_TRN_0004 : DOU_TRN_0004 생성을 위한 화면에서 사용하는 업무 스크립트를 정의한다.
     */
    //set sheet objects in an array
    var sheetObjects=new Array();   
    //counting for multiple sheets
	var sheetCnt=0;	
	//counting for multiple combo-boxes
	var comboCnt = 0;
	//receive the event from screen that is clicked
	document.onclick=processButtonClick;
	//set combo box objects in an array
	var comboObjects = new Array();

	
	
	//ComSheetObject from JSP call this function
	function setSheetObject(sheet_obj) {
		sheetObjects[sheetCnt++] = sheet_obj;
	}
	
	
	//set config for sheet/ combobox
    function loadPage() {
    	var formObject = document.form;
    	//config sheet
    	for(i = 0; i<sheetObjects.length; i++) {
    		ComConfigSheet(sheetObjects[i]); //SetShowButtonImage, SetDown2ExcelConfig, SetDataRowHeight
    		initSheet(sheetObjects[i], i + 1);//initializing sheet
    		ComEndConfigSheet(sheetObjects[i]); //SetEditableColorDiff/SetEnterBehavior/SetEditTabBehavior
    	}
    	
    	//searching when refreshing the page
    	doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);

    }
	
	//handle the event that user click on page
	function processButtonClick(){
		var formObject = document.form;// assign current form to formObject for further processing
		
		try{
			//get event name which corresponding to button
			var srcName = ComGetEvent("name");
			switch(srcName) {
				//event fires when retrieve button is clicked				
				case "btn_Save": //event fires when Save button is clicked, save new data
					ComPopupOk();
//					doActionIBSheet(sheetObjects[0], formObject, IBSAVE);
					break;		
				case "btn_Search": //event fires when Save button is clicked, save new data
					doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);
					break;	
			}
		} catch(e) {
			if( e == "[object Error]") {
	   			ComShowMessage(ComGetMsg('COM12111'));
	   		} else {
	   			ComShowMessage(e.message);
	   		}
		}
	}


	function initSheet(sheetObj, sheetNo){
		switch(sheetObj.id) {
		case "sheet1":
			with(sheetObj) {
				//set up head title for columns
				var HeadTitle = "Flag|Chk|Customer";
				var headCount = ComCountHeadTitle(HeadTitle);
				SetConfig({SearchMode : 2, MergeSheet : 5, Page : 10, DataRowMerge : 1});	
				var info = {Sort : 1, ColMove : 1, HeaderCheck : 1, ColResize : 1};
				var header = [{ Text:HeadTitle, Align:"Center"}];
				sheetObj.InitHeaders(header, info);
				var cols = [ 
				             { Type : "Status",  Hidden : 1, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "ibflag" }, 
				             { Type : "Radio", 	 Hidden : 0, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "del_chk" }, 
				             { Type : "Text", 	 Hidden : 0, Width : 70,  Align : "Center", ColMerge : 0, SaveName : "cust_seq", 	 KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1,  EditLen: 3   }]; //carrier				            				            
				//configure functionality of each column into JSON format.
				InitColumns(cols);
				//allow edit cells
				SetEditable(1);
				resizeSheet();
			}
			break;
		}
	}
	
	//resize the sheet
	function resizeSheet(){
	    ComResizeSheet(sheetObjects[0]);
	}
	
    
	function doActionIBSheet(sheetObj, formObj,sAction ) {
		switch(sAction) {
		case IBSAVE: //for save button
			// save data based on data transaction status or column to database.
//			if(sheetObj.GetSaveString() != ''){
//				formObj.f_cmd.value = MULTI;
//				sheetObj.DoSave("DOU_TRN_0004GS.do", FormQueryString(formObj));
//			}
//			else
//
//			ComShowCodeMessage("COM132910");
			sheetObj.GetCellValue();
			break;
		case IBSEARCH: //for retrieve
			formObj.f_cmd.value=SEARCH; //assign form command to server
			ComOpenWait(true);
//			var arr1=new Array("sheet1_", "");
//			var sParam1=FormQueryString(formObj)+ "&" + ComGetPrefixParam(arr1);
			sheetObj.DoSearch("DOU_TRN_0004GS.do", FormQueryString(formObj));	
			
			break;
		}
		
	}
	
	//event fires when ending retrieve in sheet1
	function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	 	ComOpenWait(false);
	 	
	}
	
	function sheet1_OnChange(sheetObj, Row, Col, Value, OldValue, RaiseFlag) {
		
	}
	
	function sheet1_OnDblClick(Row, Col, Value, CellX, CellY, CellW, CellH) {
		 if (Value == 2) {
			 var values = new Array();
			 values[0] = sheetObjects[0].GetCellValue(Col, Value);
			 ComPopUpReturnValue(values);
			 ComClosePopup(); 
			 
			
		 }
	}

	function ComPopUpReturnValue(rArray){
 		try{
	 		if(!opener) {
				opener=parent;
	 		}
	 		
				opener.callbackNotFound(rArray);
			
			ComClosePopup(); 
 		} catch (e) {
 			ComShowMessage(e.message);
 		}
 		
 	}


	

	