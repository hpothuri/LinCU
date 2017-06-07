package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import com.linCu.view.utils.JSFUtils;

import com.linCu.view.utils.PasswordUtil;

import java.io.IOException;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.myfaces.trinidad.model.CollectionModel;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MemberBean {
    private RichPopup confirmDeletePopup;
    private RichTable memberTable;
    private RichPopup memberCancelComments;
    private RichPopup attachDocument;
    private RichPopup massUpload;
    private UploadedFile _uploadFile;

    public MemberBean() {
        super();
    }

    public void save(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("updateMember"); 
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveAndSubmit(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("updateMemberAndSubmit"); 
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteConfirm(ActionEvent actionEvent) {
        try {
            Boolean deleteCreditUnionAllowed = (Boolean)ADFUtils.executeOperationBinding("deleteMemeberAllowed");
            if(deleteCreditUnionAllowed){
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getConfirmDeletePopup().show(hints);   
            }else{
                JSFUtils.addErrorMessage("Cannot delete the member. This member has cards.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setConfirmDeletePopup(RichPopup confirmDeletePopup) {
        this.confirmDeletePopup = confirmDeletePopup;
    }

    public RichPopup getConfirmDeletePopup() {
        return confirmDeletePopup;
    }

    public void deleteMember(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("closeMember"); 
            ADFUtils.executeOperationBinding("Commit"); 
            ADFUtils.executeOperationBinding("Execute"); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMemberTable());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getConfirmDeletePopup().hide();
    }

    public void onCopyOfPermanantAddress(ValueChangeEvent valueChangeEvent) {
        Map paramMap = new HashMap();
        paramMap.put("copy", valueChangeEvent.getNewValue());
        ADFUtils.executeOperationBinding("copyPermanantAddress",paramMap);
    }

    public void setMemberTable(RichTable memberTable) {
        this.memberTable = memberTable;
    }

    public RichTable getMemberTable() {
        return memberTable;
    }

    public void approve(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("approveMember"); 
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setMemberCancelComments(RichPopup memberCancelComments) {
        this.memberCancelComments = memberCancelComments;
    }

    public RichPopup getMemberCancelComments() {
        return memberCancelComments;
    }

    public void rejectMember(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getMemberCancelComments().show(hints);
    }
    
    public void confirmRejectMember(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("rejectMember");
            ADFUtils.executeOperationBinding("Commit");
            this.getMemberCancelComments().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setAttachDocument(RichPopup attachDocument) {
        this.attachDocument = attachDocument;
    }

    public RichPopup getAttachDocument() {
        return attachDocument;
    }

    public void setMassUpload(RichPopup massUpload) {
        this.massUpload = massUpload;
    }

    public RichPopup getMassUpload() {
        return massUpload;
    }

    public void massUpload(ActionEvent actionEvent) {
        UploadedFile file = this.getUploadFile();
        try {
                    //Check if file is XLSX
                    if (file.getContentType().equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") ||
                        file.getContentType().equalsIgnoreCase("application/xlsx")) {

                        readNProcessExcelx(file.getInputStream()); //for xlsx

                    }
                    //Check if file is XLS
                    else if (file.getContentType().equalsIgnoreCase("application/vnd.ms-excel")) {

                        if (file.getFilename().toUpperCase().endsWith(".XLS")) {
                            readNProcessExcel(file.getInputStream()); //for xls
                        }

                    } else {
                        FacesMessage msg = new FacesMessage("File format not supported.-- Upload XLS or XLSX file");
                        msg.setSeverity(FacesMessage.SEVERITY_WARN);
                        FacesContext.getCurrentInstance().addMessage(null, msg);
                    }
                    ADFUtils.executeOperationBinding("Commit"); 
                    this.getAttachDocument().hide();
                    JSFUtils.addInformationMessage("Applications are uploaded successfully");
                    //AdfFacesContext.getCurrentInstance().addPartialTarget(getCardTable());

                } catch (IOException e) {
                    // TODO
                }
    }

    public void setUploadFile(UploadedFile _uploadFile) {
        this._uploadFile = _uploadFile;
    }

    public UploadedFile getUploadFile() {
        return _uploadFile;
    }
    
    /**
         * Method to read xlsx file and upload to table.
         * @param myxls
         * @throws IOException
         */
        public void readNProcessExcelx(InputStream xlsx) throws IOException {

            CollectionModel cModel = (CollectionModel) getMemberTable().getValue();
            JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) cModel.getWrappedData();
            DCIteratorBinding iter = tableBinding.getDCIteratorBinding();
           
            FacesContext ctx = FacesContext.getCurrentInstance();  
            ExpressionFactory ef = ctx.getApplication().getExpressionFactory();  
            ValueExpression ve = ef.createValueExpression(ctx.getELContext(), "#{user}", UserSessionData.class);  
            UserSessionData userSessionData = (UserSessionData)ve.getValue(ctx.getELContext());
            //Use XSSFWorkbook for XLS file
            XSSFWorkbook WorkBook = null;
            int sheetIndex = 0;

            try {
                WorkBook = new XSSFWorkbook(xlsx);
            } catch (IOException e) {

            }
            XSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);

            Integer skipRw = 1;
            Integer skipcnt = 1;

            //Iterate over excel rows
            for (Row tempRow : sheet) {

                if (skipcnt > skipRw) { //skip first n row for labels.
                    //Create new row in table
                    executeOperation("CreateInsert").execute();
                    //Get current row from iterator
                    oracle.jbo.Row row = iter.getNavigatableRowIterator().getCurrentRow();
                    int Index = 0;
                    //Iterate over row's columns
                    for (int column = 0; column <= tempRow.getLastCellNum(); column++) {

                        Cell MytempCell = tempRow.getCell(column);
                        if (MytempCell != null) {
                            Index = MytempCell.getColumnIndex();
                        
                            if (Index == 0) {
                                row.setAttribute("MemberId", (int)MytempCell.getNumericCellValue());

                            } else if (Index == 1) {
                                row.setAttribute("CardReqType", MytempCell.getStringCellValue());

                            }   
                        } else {
                            row.setAttribute("CardStatus", "DRAFT");
                            row.setAttribute("CreditUnionId", userSessionData.getUnionId());
                            row.setAttribute("CreatedBy", userSessionData.getUserName());
                            long time = System.currentTimeMillis();
                            java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
                            row.setAttribute("CreatedOn", timestamp);
                            Index++;
                        }

    }
                }
                skipcnt++;
            }
        }
    
    /**Method to read xls file and upload to table.
        * @param xls
        * @throws IOException
        */
       public void readNProcessExcel(InputStream xls) throws IOException {

           CollectionModel cModel = (CollectionModel) getMemberTable().getValue();
           JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) cModel.getWrappedData();
           DCIteratorBinding iter = tableBinding.getDCIteratorBinding();
        
           FacesContext ctx = FacesContext.getCurrentInstance();  
           ExpressionFactory ef = ctx.getApplication().getExpressionFactory();  
           ValueExpression ve = ef.createValueExpression(ctx.getELContext(), "#{user}", UserSessionData.class);  
           UserSessionData userSessionData = (UserSessionData)ve.getValue(ctx.getELContext());
           
           //Use HSSFWorkbook for XLS file
           HSSFWorkbook WorkBook = null;

           int sheetIndex = 0;

           try {
               WorkBook = new HSSFWorkbook(xls);
           } catch (IOException e) {
               System.out.println("Exception : " + e);
           }

           HSSFSheet sheet = WorkBook.getSheetAt(sheetIndex);

           Integer skipRw = 1;
           Integer skipcnt = 1;
           Integer sno = 1;

           //Iterate over excel rows
           for (Row tempRow : sheet) {
               System.out.println(skipcnt + "--" + skipRw);
               if (skipcnt > skipRw) { //skip first n row for labels.
                   //Create new row in table
                   executeOperation("CreateInsert").execute();
                   //Get current row from iterator
                   oracle.jbo.Row row = iter.getNavigatableRowIterator().getCurrentRow();

                   int Index = 0;
                   //Iterate over row's columns
                   for (int column = 0; column <= tempRow.getLastCellNum(); column++) {
                       Cell MytempCell = tempRow.getCell(column);
                       if (MytempCell != null) {
                           Index = MytempCell.getColumnIndex();
                       
                       if (Index == 0) {
                           row.setAttribute("MemberId", (int)MytempCell.getNumericCellValue());

                       } else if (Index == 1) {
                           row.setAttribute("CardReqType", MytempCell.getStringCellValue());

                       }   
                       
                         } else {
                            row.setAttribute("CardStatus", "DRAFT");
                            row.setAttribute("CreditUnionId", userSessionData.getUnionId());
                            row.setAttribute("CreatedBy", userSessionData.getUserName());
                            long time = System.currentTimeMillis();
                            java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
                            row.setAttribute("CreatedOn", timestamp);
                           Index++;
                        }

    }
                   ADFUtils.executeOperationBinding("addRequiredDcosRecords"); 
                   sno++;
               }
               skipcnt++;
           }
           //Execute table viewObject
           //executeOperation("Execute").execute();
       }
    
    /**
     * Generic Method to execute operation
     * */
    public OperationBinding executeOperation(String operation) {
        OperationBinding createParam = getBindingsCont().getOperationBinding(operation);
        return createParam;

    }
    
    /**Method to get Binding Container of current view port
         * @return
         */
        public BindingContainer getBindingsCont() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }
}
