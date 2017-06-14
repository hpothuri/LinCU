package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import com.linCu.view.utils.JSFUtils;

import com.linCu.view.utils.PasswordUtil;

import java.io.IOException;

import java.io.InputStream;

import java.math.BigDecimal;

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
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;

import java.util.Date;

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
                     ADFUtils.executeOperationBinding("Execute"); 
                    this.getMassUpload().hide();
                    JSFUtils.addInformationMessage("Members are uploaded successfully");
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
           DateFormat formatter = new SimpleDateFormat("MM/DD/YYYY"); 
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
                           Map paramMap = new HashMap();
                           paramMap.put("code", MytempCell.getStringCellValue());
                           String creditUnionId = (String) ADFUtils.executeOperationBinding("findCreditUnionIdByCode",paramMap);
                           System.out.println("-------creditUnionId--------"+creditUnionId);
                           row.setAttribute("CreditUnionId", creditUnionId);

                       } else if (Index == 1) {
                           Map paramMap = new HashMap();
                           paramMap.put("code", MytempCell.getStringCellValue());
                           String creditUnionBranchId = (String) ADFUtils.executeOperationBinding("findCreditUnionBranchIdByCode",paramMap);
                           System.out.println("-------creditUnionBranchId--------"+creditUnionBranchId);
                           row.setAttribute("CreditUnionBranchId", creditUnionBranchId);

                       }else if (Index == 2) {
                           row.setAttribute("MemberPrefix", MytempCell.getStringCellValue());

                       }else if (Index == 3) {
                           row.setAttribute("FirstName", MytempCell.getStringCellValue());

                       }else if (Index == 4) {
                           row.setAttribute("MiddleName", MytempCell.getStringCellValue());

                       }else if (Index == 5) {
                           row.setAttribute("LastName", MytempCell.getStringCellValue());

                       }else if (Index == 6) {
                           row.setAttribute("MotherMaidenName", MytempCell.getStringCellValue());

                       }else if (Index == 7) {
                           Date date = MytempCell.getDateCellValue();
                           if(date != null){
                               // try {
                                    //row.setAttribute("DateOfBirth", (java.util.Date) formatter.parse(date));
                                    row.setAttribute("DateOfBirth", date);
                               // } catch (ParseException e) {
                               // }
                            }

                       }else if (Index == 8) {
                           row.setAttribute("Email", MytempCell.getStringCellValue());

                       }else if (Index == 9) {
                           row.setAttribute("Gender", MytempCell.getStringCellValue());

                       }else if (Index == 10) {
                           row.setAttribute("MaritalStatus", MytempCell.getStringCellValue());

                       }else if (Index == 11) {
                           row.setAttribute("NoOfDependents", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 12) {
                           row.setAttribute("ElectorialId", MytempCell.getStringCellValue());

                       }else if (Index == 13) {
                           row.setAttribute("PassportNo", MytempCell.getStringCellValue());

                       }else if (Index == 14) {
                           row.setAttribute("DriverPermit", MytempCell.getStringCellValue());

                       }else if (Index == 15) {
                           row.setAttribute("BirNo", MytempCell.getStringCellValue());

                       }else if (Index == 16) {
                           Double homephone = MytempCell.getNumericCellValue();
                           
                           if(homephone != null){
                           row.setAttribute("HomePhoneNumber", (new BigDecimal(homephone.toString())).setScale(0, BigDecimal.ROUND_DOWN).toString());
                           }else{
                            row.setAttribute("HomePhoneNumber", "");   
                           }

                       }else if (Index == 17) {                           
                           Double homephone = MytempCell.getNumericCellValue();
                           
                           if(homephone != null){
                               row.setAttribute("MobilePhoneNumber", (new BigDecimal(homephone.toString())).setScale(0, BigDecimal.ROUND_DOWN).toString());
                           }else{
                            row.setAttribute("MobilePhoneNumber", "");   
                           }
                           

                       }else if (Index == 18) {
                           Double homephone = MytempCell.getNumericCellValue();
                           
                           if(homephone != null){
                               row.setAttribute("FaxNumber", (new BigDecimal(homephone.toString())).setScale(0, BigDecimal.ROUND_DOWN).toString());
                           }else{
                            row.setAttribute("FaxNumber", "");   
                           }
                           

                       }else if (Index == 19) {
                           row.setAttribute("EducationCode", MytempCell.getStringCellValue());

                       }else if (Index == 20) {
                           row.setAttribute("MonthlySalary", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 21) {
                           row.setAttribute("ShareholderCode", MytempCell.getStringCellValue());

                       }else if (Index == 22) {
                           row.setAttribute("PermanentAddrLine1", MytempCell.getStringCellValue());

                       }else if (Index == 23) {
                           row.setAttribute("PermanentAddrLine2", MytempCell.getStringCellValue());

                       }else if (Index == 24) {
                           row.setAttribute("PermanentAddrLine3", MytempCell.getStringCellValue());

                       }else if (Index == 25) {
                           row.setAttribute("PermanentAddrLine4", MytempCell.getStringCellValue());

                       }else if (Index == 26) {
                           row.setAttribute("PermanentCity", MytempCell.getStringCellValue());

                       }else if (Index == 27) {
                           row.setAttribute("PermanentState", MytempCell.getStringCellValue());

                       }else if (Index == 28) {
                           row.setAttribute("PermanentCountryCode", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 29) {
                           Double zipcode = MytempCell.getNumericCellValue();
                           
                           if(zipcode != null){
                               row.setAttribute("PermanentZipCode", (new BigDecimal(zipcode.toString())).setScale(0, BigDecimal.ROUND_DOWN).toString());
                           }else{
                            row.setAttribute("PermanentZipCode", "");   
                           }
                          // row.setAttribute("PermanentZipCode", MytempCell.getStringCellValue());

                       }else if (Index == 30) {
                           System.out.println("------"+MytempCell.getStringCellValue());
                           row.setAttribute("HomeOwnership", MytempCell.getStringCellValue());

                       }else if (Index == 31) {
                           row.setAttribute("MailingAddrLine1", MytempCell.getStringCellValue());

                       }else if (Index == 32) {
                           System.out.println("--32---"+MytempCell.getStringCellValue());
                           row.setAttribute("MailingAddrLine2", MytempCell.getStringCellValue());

                       }else if (Index == 33) {
                           System.out.println("---33--"+MytempCell.getStringCellValue());
                           row.setAttribute("MailingAddrLine3", MytempCell.getStringCellValue());

                       }else if (Index == 34) {
                           System.out.println("---34--"+MytempCell.getStringCellValue());
                           row.setAttribute("MailingAddrLine4", MytempCell.getStringCellValue());

                       }else if (Index == 35) {
                           System.out.println("---35--"+MytempCell.getStringCellValue());
                           row.setAttribute("MailingCity", MytempCell.getStringCellValue());

                       } else if (Index == 36) {
                           System.out.println("--36---"+MytempCell.getStringCellValue());
                           row.setAttribute("MailingState", MytempCell.getStringCellValue());

                       }else if (Index == 37) {
                           row.setAttribute("MailingCountryCode", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 38) {
                           System.out.println("---38--"+MytempCell.getNumericCellValue());
                           Double zipcode = MytempCell.getNumericCellValue();
                           
                           if(zipcode != null){
                               row.setAttribute("MailingZipCode", (new BigDecimal(zipcode.toString())).setScale(0, BigDecimal.ROUND_DOWN).toString());
                           }else{
                            row.setAttribute("MailingZipCode", "");   
                           }
                           //row.setAttribute("MailingZipCode", MytempCell.getStringCellValue());

                       }else if (Index == 39) {
                           row.setAttribute("Employer", MytempCell.getStringCellValue());

                       }else if(Index == 40) {
                           row.setAttribute("OccupationCode", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 41) {
                           row.setAttribute("EmployerAddress1", MytempCell.getStringCellValue());

                       }else if (Index == 42) {
                           row.setAttribute("EmployerAddress2", MytempCell.getStringCellValue());

                       }else if (Index == 43) {
                           row.setAttribute("EmployerCity", MytempCell.getStringCellValue());

                       }else if (Index == 44) {
                           Double bizPh = MytempCell.getNumericCellValue();
                           
                           if(bizPh != null){
                               row.setAttribute("BusinessPhoneNumber", (new BigDecimal(bizPh.toString())).setScale(0, BigDecimal.ROUND_DOWN).toString());
                           }else{
                            row.setAttribute("BusinessPhoneNumber", "");   
                           }
                           //row.setAttribute("BusinessPhoneNumber", MytempCell.getStringCellValue());

                       }else if (Index == 45) {
                           Double bizExt = MytempCell.getNumericCellValue();
                           
                           if(bizExt != null){
                               row.setAttribute("BusinessPhoneExtn",  (new BigDecimal(bizExt.toString())).setScale(0, BigDecimal.ROUND_DOWN).toString());
                           }else{
                            row.setAttribute("BusinessPhoneExtn", "");   
                           }
                           //row.setAttribute("BusinessPhoneExtn", MytempCell.getStringCellValue());

                       } else if (Index == 46) {
                           row.setAttribute("BirthCountryCode", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 47) {
                           row.setAttribute("LocalTaxExempt", MytempCell.getStringCellValue());

                       }else if (Index == 48) {
                           row.setAttribute("Nationality", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 49) {
                           row.setAttribute("CitizenShipCountry1", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 50) {
                           row.setAttribute("CitizenShipCountry2", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 51) {
                           row.setAttribute("CitizenShipCountry3", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 52) {
                           row.setAttribute("CitizenShipCountry4", (int)MytempCell.getNumericCellValue());

                       }else if (Index == 53) {
                           row.setAttribute("EligibleForeignTax", MytempCell.getStringCellValue());

                       }else if (Index == 54) {
                           row.setAttribute("DocForeignTaxExempt", MytempCell.getStringCellValue());

                       }else if (Index == 55) {
                           row.setAttribute("ForeignCitizenship", MytempCell.getStringCellValue());

                       }else if (Index == 56) {
                           row.setAttribute("PowerOfAttorney", MytempCell.getStringCellValue());

                       }
                       
                         } else {
                            row.setAttribute("Status", "DRAFT");
                            //row.setAttribute("CreditUnionId", userSessionData.getUnionId());
                            row.setAttribute("CreatedBy", userSessionData.getUserName());
                            row.setAttribute("CreatedBy1", userSessionData.getUserName());
                            row.setAttribute("LastUpdatedBy", userSessionData.getUserName());
                            row.setAttribute("LastUpdatedBy1", userSessionData.getUserName());
                            long time = System.currentTimeMillis();
                            java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
                            row.setAttribute("CreationDate", timestamp);
                            row.setAttribute("CreationDate1", timestamp);
                            row.setAttribute("LastUpdateDate", timestamp);
                            row.setAttribute("LastUpdateDate1", timestamp);
                           
                           Index++;
                        }

                    }
                  // ADFUtils.executeOperationBinding("addRequiredDcosRecords"); 
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
