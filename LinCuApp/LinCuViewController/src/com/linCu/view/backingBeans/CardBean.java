package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.InputStream;
import java.io.OutputStream;

import javax.faces.application.FacesMessage;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.binding.BindingContainer;
import oracle.adf.view.rich.component.rich.RichPopup;
import org.apache.myfaces.trinidad.model.UploadedFile;
import oracle.jbo.domain.BlobDomain;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
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
public class CardBean {
    private RichPopup commentsPopup;
    private RichPopup attachDocument;
    private UploadedFile _file;
    private UploadedFile _uploadFile;
    private RichTable cardTable;

    public CardBean() {
        super();
    }

    public void saveAndSubmit(ActionEvent actionEvent) {
        try {
             ADFUtils.executeOperationBinding("submitCard");
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openPopup(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getCommentsPopup().show(hints);
    }

    public void setCommentsPopup(RichPopup commentsPopup) {
        this.commentsPopup = commentsPopup;
    }

    public RichPopup getCommentsPopup() {
        return commentsPopup;
    }

    public void approve(ActionEvent actionEvent) {
        try {
            Map paramMap = new HashMap();
            paramMap.put("approver", "test");
            ADFUtils.executeOperationBinding("approve",paramMap);
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void reject(ActionEvent actionEvent) {
        try {
            Map paramMap = new HashMap();
            paramMap.put("rejector", "test");
            ADFUtils.executeOperationBinding("reject",paramMap);
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String attachDocument() {
        try {
            ADFUtils.executeOperationBinding("CreateInsert"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getAttachDocument().show(hints);
        return null;
    }

    public void setAttachDocument(RichPopup attachDocument) {
        this.attachDocument = attachDocument;
    }

    public RichPopup getAttachDocument() {
        return attachDocument;
    }

    public void setFile(UploadedFile _file) {
        this._file = _file;
    }

    public UploadedFile getFile() {
        return _file;
    }

    public String uploadDoc() {
        UploadedFile imageFile = this.getFile();
        BindingContext bindingCtx = BindingContext.getCurrent();
        BindingContainer bindingcnt = bindingCtx.getCurrentBindingsEntry();
        DCBindingContainer dcContainger = (DCBindingContainer) bindingcnt;
        DCIteratorBinding itr = dcContainger.findIteratorBinding("LincuMemberCardDocsIterator");

        oracle.jbo.Row row = itr.getCurrentRow();
        row.setAttribute("Document", createBlobDomain(imageFile));
        return null;
    }
    
        private BlobDomain createBlobDomain(UploadedFile imageFile){
            InputStream ins = null;
            OutputStream ops = null;
            BlobDomain domain = null;
            
            domain = new BlobDomain();
            
            try{
                ins = imageFile.getInputStream();
                ops = domain.getBinaryOutputStream();
                byte[] buffer = new byte[8192];
                int byteRead = 0;
                while((byteRead = ins.read(buffer,0,8192)) != -1){
                    ops.write(buffer,0,byteRead);
                }
                ins.close();
            }catch(Exception e){
                e.printStackTrace();
            }
            
            return domain;
        }

    public void cancelUpload(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("removeCurrentRow"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getAttachDocument().hide();
    }

    public void confirmUpload(ActionEvent actionEvent) {
        UploadedFile imageFile = this.getFile();
        BindingContext bindingCtx = BindingContext.getCurrent();
        BindingContainer bindingcnt = bindingCtx.getCurrentBindingsEntry();
        DCBindingContainer dcContainger = (DCBindingContainer) bindingcnt;
        DCIteratorBinding itr = dcContainger.findIteratorBinding("LincuMemberCardDocsIterator");

        oracle.jbo.Row row = itr.getCurrentRow();
        row.setAttribute("Document", createBlobDomain(imageFile));
        row.setAttribute("DocumentName", imageFile.getFilename());
        this.getAttachDocument().hide();
    }

    public void downloadFile(FacesContext facesContext, OutputStream outputStream) {
        BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();  
             AttributeBinding attr = (AttributeBinding) bindings.getControlBinding("Document");  
             if (attr != null)  
             {  
               BlobDomain blob = (BlobDomain) attr.getInputValue();  
               try  
               {  // copy the data from the blobDomain to the output stream   
                 IOUtils.copy(blob.getInputStream(), outputStream);  
                 blob.closeInputStream();  
                 outputStream.flush();  
               }  
               catch (IOException e)  
               {  
                 // handle errors  
                 e.printStackTrace();  
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");  
                 FacesContext.getCurrentInstance().addMessage(null, msg);  
               }  
             }  
    }

    public void requestBulkCards(ActionEvent actionEvent) {
        // Add event code here...
    }
    
    /**Method to read xls file and upload to table.
        * @param xls
        * @throws IOException
        */
       public void readNProcessExcel(InputStream xls) throws IOException {

           CollectionModel cModel = (CollectionModel) getCardTable().getValue();
           JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) cModel.getWrappedData();
           DCIteratorBinding iter = tableBinding.getDCIteratorBinding();

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
                   for (int column = 0; column < tempRow.getLastCellNum(); column++) {
                       Cell MytempCell = tempRow.getCell(column);
                       if (MytempCell != null) {
                           Index = MytempCell.getColumnIndex();
                       
                       if (Index == 0) {
                           row.setAttribute("MemberId", (int)MytempCell.getNumericCellValue());

                       } else if (Index == 1) {
                           row.setAttribute("CardReqType", MytempCell.getStringCellValue());

                       }  else if (Index == 2) {
                           row.setAttribute("CardStatus", MytempCell.getStringCellValue());

                       } 
                       
                         } else {
                            row.setAttribute("CreditUnionId", 32);
                           Index++;
                        }

    }
                   sno++;
               }
               skipcnt++;
           }
           //Execute table viewObject
           executeOperation("Execute").execute();
       }
    
     /**
          * Method to read xlsx file and upload to table.
          * @param myxls
          * @throws IOException
          */
         public void readNProcessExcelx(InputStream xlsx) throws IOException {

             CollectionModel cModel = (CollectionModel) getCardTable().getValue();

             JUCtrlHierBinding tableBinding = (JUCtrlHierBinding) cModel.getWrappedData();
             //Acess the ADF iterator binding that is used with ADF table binding
             DCIteratorBinding iter = tableBinding.getDCIteratorBinding();

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
                     for (int column = 0; column < tempRow.getLastCellNum(); column++) {

                         Cell MytempCell = tempRow.getCell(column);
                         if (MytempCell != null) {
                             Index = MytempCell.getColumnIndex();
                         
                             if (Index == 0) {
                                 row.setAttribute("MemberId", (int)MytempCell.getNumericCellValue());

                             } else if (Index == 1) {
                                 row.setAttribute("CardReqType", MytempCell.getStringCellValue());

                             }  else if (Index == 2) {
                                 row.setAttribute("CardStatus", MytempCell.getStringCellValue());

                             }  
                         } else {
                             row.setAttribute("CreditUnionId", 32);
                             Index++;
                         }

     }
                 }
                 skipcnt++;
             }
         }
    
    /**Method to get Binding Container of current view port
         * @return
         */
        public BindingContainer getBindingsCont() {
            return BindingContext.getCurrent().getCurrentBindingsEntry();
        }

        /**
         * Generic Method to execute operation
         * */
        public OperationBinding executeOperation(String operation) {
            OperationBinding createParam = getBindingsCont().getOperationBinding(operation);
            return createParam;

        }

    public void setCardTable(RichTable cardTable) {
        this.cardTable = cardTable;
    }

    public RichTable getCardTable() {
        return cardTable;
    }

    public void setUploadFile(UploadedFile _uploadFile) {
        this._uploadFile = _uploadFile;
    }

    public UploadedFile getUploadFile() {
        return _uploadFile;
    }

    public void massUpload(ActionEvent actionEvent) {
        UploadedFile file = this.getFile();
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
                    AdfFacesContext.getCurrentInstance().addPartialTarget(getCardTable());

                } catch (IOException e) {
                    // TODO
                }
    }
}
