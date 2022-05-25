
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
  
	Event identification code: INIT=0; [Input]ADD=1; [Search]SEARCH=2; [List lookup]SEARCHLIST=3;
					[Modify]MODIFY=4; [Delete]REMOVE=5; [Delete list]REMOVELIST=6 [multiprocessing]MULTI=7
					Other extra character constants  COMMAND01=11; ~ COMMAND20=30;
 ***************************************************************************************/

	/**
        * @fileoverview It is a JavaScript file commonly used in work, and calendar-related functions are defined..
        * @author Hai To
        */

	/**
        * @extends 
        * @class DOU_TRN_0004 : DOU_TRN_0004 Defines the work script used in the screen for creation.
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
	
	/**
	 * Handling some event that produce in form
	 */
	function initControl() {
		var formObject = document.form;
		// Axon event handling. Catch Event
		axon_event.addListenerFormat('blur', 'keypressFormat', formObject);
	}
	
	/**
	 * Check valid number in vendor code
	 */
	function keypressFormat() {
		obj = ComGetEvent();
		if (obj.attributes.dataformat.nodeValue == null) {
			return;
		} else {
			vendor = document.getElementById("s_vndr_seq").value;
			// regex for vendor code
			var regex = /^(\d{6})$/;
			// check whether the message code matches with format.
			if (!vendor.match(regex)) {
				// ("Only number is allowed and atlest 6 digits");
				ComShowCodeMessage("COM140000");
			}
			switch (obj.name) {
				case "s_vndr_seq":
					ComEditFormating();
					break;
			}
		}
	}
    
	/**
	 * ComSheetObject from JSP call this function to assign sheet object to
	 * specific sheet Array
	 * 
	 * @param {object} sheet Object
	 * 
	 */
	function setSheetObject(sheet_obj) {
		switch (sheet_obj.id) {
			case "sheet1":
				sheetObjects[0] = sheet_obj;
		}	
		// sheetObjects[sheetCnt++] = sheet_obj;
	}
	

	/** 
	 *  ComComboObject from JSP call this function
	 *  @param {object} combo Object
	 */

	function setComboObject(combo_obj) {
		//comboObjects[0] = combo_obj;
		switch (combo_obj.element[0].id) {
			case "s_jo_crr_cd":
				comboObjects[0] = combo_obj;
		}	
		//comboObjects[comboCnt++] = combo_obj;
	}
	
	
	/**
	 * Run the relevant function
	 * Set config for sheet/ combobox	 
	 */
	function loadPage() {
		var formObject = document.form;
		//config sheet
		for(i = 0; i<sheetObjects.length; i++) {
			ComConfigSheet(sheetObjects[i]); //SetShowButtonImage, SetDown2ExcelConfig, SetDataRowHeight
			initSheet(sheetObjects[i], i + 1);//initializing sheet
			ComEndConfigSheet(sheetObjects[i]); //SetEditableColorDiff/SetEnterBehavior/SetEditTabBehavior
		}

		//config combo box, generate dopdownlist data
		for(var k=0; k<comboObjects.length; k++) {
			initCombo(comboObjects[k], k+1);
		}
		document.getElementById('s_cre_dt_fm').disabled = true;
		document.getElementById('s_cre_dt_to').disabled = true;
		getCurrentDate();
		//searching when refreshing the page
		doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);
		initControl();
    	}
	
	/**
	 * Handling the event that user click on page
	 */
	function processButtonClick(){
		var formObject = document.form;// assign current form to formObject for further processing
		var cal=new ComCalendar();
		try{
			//get event name which corresponding to button
			var srcName = ComGetEvent("name");
			switch(srcName) {
				//event fires when retrieve button is clicked
				case "btn_Retrieve": 
					doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);
					break;		
				//event fires when New button is clicked, reset form	
				case "btn_New": 
					doActionIBSheet(sheetObjects[0], formObject, IBRESET);
					break;
				//event fires when Save button is clicked, save new data
				case "btn_Save": 
					doActionIBSheet(sheetObjects[0], formObject, IBSAVE);
					break;
				//event fires when DownExcel button is clicked, down sheet to excel file
				case "btn_DownExcel": 
					doActionIBSheet(sheetObjects[0], formObject, IBDOWNEXCEL);
					break;
				//event fires when Add button is clicked/ add new row
				case "btn_RowAdd": 
					doActionIBSheet(sheetObjects[0], formObject, IBINSERT);
					break;
				//event fires when Delete button is clicked, delete current row
				case "btn_RowDelete": 
					doActionIBSheet(sheetObjects[0], formObject, IBDELETE);
					break;			
				case "btns_calendar1":				
					cal.select(formObject.s_cre_dt_fm, 'yyyy-MM-dd');
					break;
				case "btns_calendar2": 
					cal.select(formObject.s_cre_dt_to, 'yyyy-MM-dd');
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

	/**
	 * Set the item for combobox
	 * @param {object} comboObj
	 * @param {int}    comboNo
	 */
	function initCombo(comboObj, comboNo){
		var formObject = document.form;
		switch (comboNo) {
			case 1:
				with (comboObj) {
					//multi boxes are selectable
					SetMultiSelect(1); 
					// set the height for drop list
					SetDropHeight(200); 
				}				
				// split all components to array
				var comboItems = carrier.split("|"); 
				//add item to combobox
				addComboItem(comboObj, comboItems);  
				comboObj.SetSelectIndex(0); //default selection at index 0
				break;
		}
	}
	
	/**
	 * Add items to combo box
	 * @param {object} comboObj
	 * @param {Array}  comboItems
	 */
	function addComboItem(comboObj, comboItems) {
		for (var i=0 ; i < comboItems.length ; i++) {
			comboObj.InsertItem(i, comboItems[i], comboItems[i]);
		}   		
	}
	
	
	/**
	 * 
	 * @param {object} sheetObj
	 * @param {int}    sheetNo
	 */
	function initSheet(sheetObj, sheetNo){
		switch(sheetObj.id) {
			case "sheet1":
				with(sheetObj) {
					//set up head title for columns
					var HeadTitle = "Flag|Chk|Carrier|Rev.Lane|Vendor Code|Customer Code|Customer Code|Trade|DeleteFlag|Create Date|Create User ID|Update Date|Update User ID";
					//counting heade title
					var headCount = ComCountHeadTitle(HeadTitle);
					/*SearchMode:2 - lazy load
					Page: Number of rows to display in one page (default:20)
					DataRowMerge: Whether to allow horizontal merge of the entire row		
					mergeSHeet:5: merge header
					*/
					SetConfig({SearchMode : 2, MergeSheet : 5, Page : 10, DataRowMerge : 1});	

					//set infomation for sheet
					var info = {Sort : 1, ColMove : 1, HeaderCheck : 1, ColResize : 1};

					//setting for header
					var header = [{ Text:HeadTitle, Align:"Center"}];
					//initializing for header, define the header title and function using this method.
					sheetObj.InitHeaders(header, info);

					//set prefix to discriminate between sheets 
					//var prefix="sheet1_";

					//configure for each column
					/* Hidden: 1: hide, 0:show
					 * Width: the width of cell
					 * Align: align position of text
					 * ColMerge: 
					 * KeyField: an be used to configure whether to make a data cell a required field. If the value is 
					 * 			 set as 1 and the data cell is empty, a warning message appears when the saving method is 
					 * 			 called so as to encourage the user to fill the cell. The default value is 0. 
					 * ColMerge:  configures whether to allow vertical merge for data columns. The default value is 1.
					 * SaveName: for corresponding name of column when retrieving 
					 * Format: apply column type
					 * UpdateEdit: could edit to update
					 * InsertEdit: could insert data when add new row
					 * EditLen:maximum number of characters to allow for a piece of data.
					 * AcceptKeys: is where you configure key operations allowed for a column.
					 * 			   Set as "N" for numbers and "E" for English alphabet; 
					 * 			   if both numbers and English alphabets are allowed, connect the two values with "|"				
					 */
					var cols = [ 
						     { Type : "Status",   Hidden : 1, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "ibflag" }, 
						     { Type : "CheckBox", Hidden : 0, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "del_chk" }, 
						     { Type : "Text", 	  Hidden : 0, Width : 70,  Align : "Center", ColMerge : 0, SaveName : "jo_crr_cd", 	 KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1,  EditLen: 3   }, //carrier
						     { Type : "Combo", 	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "rlane_cd",    KeyField : 1, Format : "", UpdateEdit : 0, InsertEdit : 1,  EditLen: 5   }, //rev lane
						     { Type : "Text",	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "vndr_seq",    KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 , EditLen: 6   }, //vendor code
						     { Type : "Text", 	  Hidden : 0, Width : 50,  Align : "Center", ColMerge : 0, SaveName : "cust_cnt_cd", KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 , EditLen: 2   }, //Customer code
						     { Type : "Text", 	  Hidden : 0, Width : 100, Align : "Center", ColMerge : 0, SaveName : "cust_seq",    KeyField : 1, Format : "", UpdateEdit : 1, InsertEdit : 1 , EditLen: 6   }, //customer code
						     { Type : "Text",     Hidden : 0, Width : 70,  Align : "Center", ColMerge : 0, SaveName : "trd_cd", 	 KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1 , EditLen: 3   }, //trade
						     { Type : "Combo",    Hidden : 0, Width : 70,  Align : "Center", ColMerge : 0, SaveName : "delt_flg", 	 KeyField : 0, Format : "", UpdateEdit : 1, InsertEdit : 1				  }, //delete flag
						     { Type : "Text",     Hidden : 0, Width : 150, Align : "Center", ColMerge : 0, SaveName : "cre_dt", 	 KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0 				  }, //create date
						     { Type : "Text", 	  Hidden : 0, Width : 180, Align : "Left",   ColMerge : 0, SaveName : "cre_usr_id",  KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0				  }, //create user ID
						     { Type : "Text", 	  Hidden : 0, Width : 150, Align : "Center", ColMerge : 0, SaveName : "upd_dt", 	 KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0  			  }, //update date
						     { Type : "Text", 	  Hidden : 0, Width : 180, Align : "Left", 	 ColMerge : 0, SaveName : "upd_usr_id",  KeyField : 0, Format : "", UpdateEdit : 0, InsertEdit : 0				  } //update user id
						     ];
					//configure functionality of each column into JSON format.
					InitColumns(cols);
					//allow edit cells
					SetEditable(1);
					SetColProperty("jo_crr_cd",   { AcceptKeys : "E|N", InputCaseSensitive : 1 });
					SetColProperty("vndr_seq",    { AcceptKeys : "N"});
					SetColProperty("cust_cnt_cd", { AcceptKeys : "E",   InputCaseSensitive : 1});
					SetColProperty("cust_seq",	  { AcceptKeys : "N"});
					SetColProperty("trd_cd", 	  { AcceptKeys : "E", InputCaseSensitive : 1 });
					SetColProperty("rlane_cd", 	  { ComboText  : lane,  ComboCode : lane });
					SetColProperty("delt_flg", 	  { ComboText  : "N|Y", ComboCode : "N|Y" });
					resizeSheet();
				}
				break;
		}
	}
	
	/**
	 * Resize the sheet for not breaking the window
	 */
	function resizeSheet(){
	    ComResizeSheet(sheetObjects[0]);
	}

	/**
	* 
	* @param {object} sheetObj
	* @param {object} formObj
	* @param {int}    sAction
	*/
	function doActionIBSheet(sheetObj, formObj, sAction, row, col) {
		switch(sAction) {
			case IBSEARCH: 
				formObj.f_cmd.value=SEARCH; //assign form command to server
				ComOpenWait(true);
				if(validateForm(sheetObj, formObj, sAction)) {
					//var arr1=new Array("sheet1_", "");
					//var sParam1=FormQueryString(formObj)+ "&" + ComGetPrefixParam(arr1);
					sheetObj.DoSearch("DOU_TRN_0004GS.do", FormQueryString(formObj));
				} else {
					ComOpenWait(false);
				}				
				break;
			case IBRESET: 
				//remove header and table data
				sheetObj.RemoveAll();
				//set carrier to the first position.
				comboObjects[0].SetSelectIndex(0);
				//reset date from and date to to current month
				getCurrentDate();
				break;
			case IBSAVE: 
				// save data based on data transaction status or column to database.
				ComOpenWait(true);
				if(sheetObj.GetSaveString() != ''){					
					formObj.f_cmd.value = MULTI;
					sheetObj.DoSave("DOU_TRN_0004GS.do", FormQueryString(formObj));
				} else {
					ComOpenWait(false);
					ComShowCodeMessage("COM132910");
				}
				break;
			case IBINSERT: 
				//new row will be added at the bottom
				sheetObj.DataInsert(-1); 
				break;
			case IBDELETE: 
				//check from to bottom to the top row 
				for( var i = sheetObj.LastRow(); i >= sheetObj.HeaderRows(); i-- ){
					if(sheetObj.GetCellValue(i, "del_chk") == 1){ 
						sheetObj.SetRowHidden(i, 1);
						sheetObj.SetRowStatus(i,"D");
					}			
				}
				break;	
			case IBDOWNEXCEL:	
				if(sheetObj.RowCount() < 1){
					ComShowCodeMessage("COM132501");
				}else{
					sheetObj.Down2Excel({DownCols: makeHiddenSkipCol(sheetObj), SheetDesign:1, Merge:1});
				}
				break;
		}
	}
	
	/**
	 * Event fires when search is completed using a search function and other internal data processing are also completed.
	 * @param {object} sheetObj
	 * @param {int}    Code
	 * @param {String} Msg
	 * @param {int}    StCode
	 * @param {String} StMsg
	 */
	function sheet1_OnSearchEnd(sheetObj, Code, Msg, StCode, StMsg) { 
	 	ComOpenWait(false);	 	
	}
	
	
	
	/**
	 * Get current date, month, year for date from, date to (within a month)
	 */
	function getCurrentDate() {
		today=new Date();
		var thisYear=today.getFullYear(); //get current year
	    var thisDate = today.getDate(); // get current date
	   	var thisMonth=today.getMonth()+1; // because it counts from 0 so add 1
	   	var firstDate = "01"; // first day of a month
	   	if(thisMonth<10 ) { // if date or month < 10, add "0" before value	   		
	   		thisMonth = "0" + thisMonth;
	   	} 
	   	if(thisDate<10) {
	   		thisDate = "0" + thisDate;
	   	}
	   	
   		var prev = thisYear + "-" + thisMonth + "-" + firstDate;
   		var now = thisYear + "-" + thisMonth+ "-" + thisDate;
   		
   		//set by id
		document.getElementById("s_cre_dt_fm").value = prev ;
		document.getElementById("s_cre_dt_to").value = now ;
	}
	
	/**
	 * Event fires when content in sheet1 is modified
	 * @param {object}  sheetObj
	 * @param {int} 	Row
	 * @param {int}		Col
	 * @param {String}  Value
	 * @param {String}  OldValue
	 * @param {boolean} RaiseFlag
	 * 
	 */
	function sheet1_OnChange(sheetObj, Row, Col, Value, OldValue, RaiseFlag) {
		var formObject = document.form; 
		var colName=sheetObj.ColSaveName(Col); //get current column name that changed
		if(colName == "jo_crr_cd" || colName == "rlane_cd"){//check duplicate data
			if(sheetObj.GetCellValue(Row,"jo_crr_cd") != "" && sheetObj.GetCellValue(Row,"rlane_cd") != ""){
				//check on UI First
				var headerRowNum = sheetObj.HeaderRows();
				for(var i = headerRowNum; i <= sheetObj.RowCount(); i++){
					if(i != Row && sheetObj.GetCellValue(Row,"jo_crr_cd") == sheetObj.GetCellValue(i,"jo_crr_cd")
							&& sheetObj.GetCellValue(Row,"rlane_cd") == sheetObj.GetCellValue(i,"rlane_cd")){
						ComShowCodeMessage("COM12115");
						sheetObj.SetCellValue(Row, Col ,OldValue, 0);
						sheetObj.SelectCell(Row, Col);
						return;
					}
				}
				
				//check on Service side
				formObject.f_cmd.value		= COMMAND01;
				var param = FormQueryString(formObject) + "&jo_crr_cd=" + sheetObj.GetCellValue(Row,"jo_crr_cd") + "&rlane_cd=" + sheetObj.GetCellValue(Row,"rlane_cd");
				var sXml  = sheetObj.GetSearchData("DOU_TRN_0004GS.do", param,{sync:1});
				var flag  = ComGetEtcData(sXml, "ISEXIST");
				if(flag == 'Y'){
					ComShowCodeMessage("COM12115");
					sheetObj.SetCellValue(Row, Col, OldValue, 0);
					sheetObj.SelectCell(Row, Col);
				}
			}
		} else {
			formObject.f_cmd.value		= COMMAND02;
			var param = FormQueryString(formObject) + "&jo_crr_cd=" + sheetObj.GetCellValue(Row,"jo_crr_cd") + "&rlane_cd=" + sheetObj.GetCellValue(Row,"rlane_cd");
			var sXml  = sheetObj.GetSearchData("DOU_TRN_0004GS.do", param,{sync:1});
			var flag  = ComGetEtcData(sXml, "ISEXIST");
			console.log(flag);
		}
	}

	/**
	 * Event fires when partner combobox is clicked 
	 * @param {object}  comboObj
	 * @param {int}	    index
	 * @param {int}     code
	 */
	function s_jo_crr_cd_OnCheckClick(comboObj, index, code) {
		if(index==0) {          
			var bChk=comboObj.GetItemCheck(index);
			if(bChk){
			    for(var i=1 ; i < comboObj.GetItemCount() ; i++) {
				comboObj.SetItemCheck(i,0);
			    }
			}
		} else {
			//Whenever All is checked, other checkboxes are unchecked
			var bChk=comboObj.GetItemCheck(index);
			if (bChk) {
			    comboObj.SetItemCheck(0,0);
			}
		}

		//When all Combo Items are Unchecked, All is automatically selected.
		var checkCnt=0;
		var count = parseInt(comboObj.GetItemCount());
		for(var i = 1 ; i <  count; i++) {
			if(comboObj.GetItemCheck(i)) {
			    checkCnt++;
			}
		}
		if(checkCnt == 0) {
			comboObj.SetItemCheck(0,1);
		}
	}
	

	
	/**
	 * Validate the input of user
	 * @param 	{object} sheetObj
	 * @param	{object} formObj
	 * @param   {int}	   sAction
	 * @returns {Boolean}
	 */
	function validateForm(sheetObj, formObj, sAction) {
		switch (sAction) {
			case IBSEARCH:
				var regex = /^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$/; // regex for yyyy/mm/dd
				var creDtFm = form.s_cre_dt_fm;
				var creDtTo = form.s_cre_dt_to;
				creDtFm1 = document.getElementById("s_cre_dt_fm").value; // get value from date from
				creDtTo1 = document.getElementById("s_cre_dt_to").value; // get value from date to
				var compare = document.getElementById("s_cre_dt_to").value.replaceAll('-', '') - document.getElementById("s_cre_dt_fm").value.replaceAll('-',''); // take date to - date fr
//				if (creDtFm.value != "" && creDtTo.value != "") { // if values in box search not blank and date to - date fr < 0
//					if (compare < 0 && creDtFm1.match(regex) && creDtFm1.match(regex)) {
//						ComShowCodeMessage("COM132905"); // show msg date fr cannot > than date to
//						return false;
//					}
//		
//					if (!creDtFm1.match(regex)) { // check date fr is invalid
//						ComShowCodeMessage("COM132907");
//						return false;
//					}
//		
//					if (!creDtTo1.match(regex)) { // check date to is invalid
//						ComShowCodeMessage("COM132907");
//						return false;
//					} else {
//						return true;
//					}
//				} else if (creDtFm.value == "" && creDtTo.value != "") { // check date from if it was inserted
//					ComShowCodeMessage("COM132908");
//					return false;
//				} else if (creDtFm.value != "" && creDtTo.value == "") {// check date to if it was inserted
//					ComShowCodeMessage("COM132909");
//					return false;
//				}	
				
				if(creDtFm.value != "" && creDtTo.value != "" && creDtFm.value > creDtTo.value) {
					ComShowCodeMessage("COM132905"); 
		            ComSetFocus(creDtFm);
		            return false;
		        }
		        if(!ComChkObjValid(creDtFm)) {
		        	ComShowCodeMessage("COM132907");
		        	return false;
		        }
		        if(!ComChkObjValid(creDtTo)) {
		        	ComShowCodeMessage("COM132907");
		        	return false;
		        }
				break;
		}
		return true;
	}
	
	/**
	 * this function will fire when saved transaction end
	 * @param {String} code
	 * @param {int}    msg
	 */
	function sheet1_OnSaveEnd(code, msg) { 
		//thường check message ở server side - specify, modify data.
		ComOpenWait(false);
		doActionIBSheet(sheetObjects[0], document.form, IBSEARCH);
	} 
	
	function sheet1_OnBeforeSave(code, msg) { 
		var formObject = document.form;
		formObject.f_cmd.value	= COMMAND02;
		
		
	} 
	
	/**
	 * For additional issue 
	 * @param sheetObj
	 * @param Row
	 * @param Col
	 */
	function sheet1_OnPopupClick(sheetObj, Row, Col) {
		var formObject = document.form;
		switch (sheetObj.ColSaveName(Col)) {
//			case "cust_seq":
//				var data = sheetObjects[0].GetCellValue(Col, 1);
//				var sUrl = "/opuscntr/DOU_TRN_0004PU.do";
//				doActionIBSheet(sheetObjects[0], formObject, IBSEARCH);
//				//				ComOpenPopup(sUrl, 1000, 800,"get_value", "0,0", true);
////				ComOpenWindowCenter('DOU_TRN_0004PU.do', 'setCustSeq', 800, 600);
//				ComOpenPopup('DOU_TRN_0004PU.do', 1000, 800,"setCustSeq", "1,0,1", true );
////				ComOpenWindow(sUrl, sWinName, sFeatures, true);
////				ComOpenPopupWithTarget('/opuscntr/COM_ENS_071.do?ofc_pts_cd=ALL', 700, 520, "ofc_cd:s_ofc_cd", '0,0,1,1,1,1,1,1', true);
////				ComOpenWindowCenter('/opuscntr/COM_ENS_071.do?ofc_pts_cd=ALL', '', 800, 600);
////				ComOpenWindowCenter('SinhVienPU.do?s_khoahoc=' +data, '', 800, 600);
//				break;
			case "cust_seq":
				ComOpenPopup('/opuscntr/COM_ENS_041.do?', 900, 520, 'setCustCd', '1,0,1', true, false, Row, Col);
				break;
			case "trd_cd":
				ComOpenPopup('/opuscntr/COM_COM_0012.do?', 900, 520, 'setTrdCd', '1,0,1', true, false, Row, Col);
		   		break;	
		}
	}
	
//	function setCustCd(aryPopupData) {
//		var formObject=document.form;
//		sheetObjects[0].SetCellValue(aryPopupData[0], 4, aryPopupData[1]);
//	}
	
	function setCustCd(aryPopupData, row, col, sheetIdx){
		var sheetObject=sheetObjects[0];
		sheetObject.SetCellValue(row,"cust_cnt_cd",aryPopupData[0][3].substring(0,2));
		sheetObject.SetCellValue(row,"cust_seq",aryPopupData[0][3].substring(2));
	}
	
	function setTrdCd(aryPopupData, row, col, sheetIdx){
		var sheetObject=sheetObjects[0];
		sheetObject.SetCellValue(row,col,aryPopupData[0][3]);
	}
	
	
	