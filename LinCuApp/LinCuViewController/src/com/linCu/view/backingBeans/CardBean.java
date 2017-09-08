package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;
import com.linCu.view.utils.JSFUtils;

import com.linCu.view.utils.PasswordUtil;

import com.linCu.view.utils.PdfPopulator;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.OutputStreamWriter;

import java.math.BigDecimal;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import javax.servlet.ServletContext;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;

import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.BindingContainer;
import oracle.binding.OperationBinding;

import oracle.jbo.ViewObject;
import oracle.jbo.domain.BlobDomain;
import oracle.jbo.uicli.binding.JUCtrlHierBinding;

import org.apache.commons.io.IOUtils;
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
    private RichPopup deleteConfirm;

    public CardBean() {
        super();
    }

    public void saveAndSubmit(ActionEvent actionEvent) {
        try {           
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();  
            AttributeBinding attr = (AttributeBinding) bindings.getControlBinding("MemberId1");
            AttributeBinding attr1 = (AttributeBinding) bindings.getControlBinding("CardReqType1");  
            AttributeBinding attr2 = (AttributeBinding) bindings.getControlBinding("RefCardId");  
            AttributeBinding attr3 = (AttributeBinding) bindings.getControlBinding("TopupAmount"); 
            AttributeBinding attr4 = (AttributeBinding) bindings.getControlBinding("TransCardStatus"); 
            //AttributeBinding attr5 = (AttributeBinding) bindings.getControlBinding("TransRefCIFNumber"); 
            BigDecimal totalTopupAmount = new BigDecimal(0);
            
            if (attr1 != null)  
            { 
               String cardType = (String)attr1.getInputValue();
               Object refCardId = attr2.getInputValue();
               Object topupAmount = attr3.getInputValue();
               String status = (String) attr4.getInputValue();
               
                if(("TOPUP_CARD".equalsIgnoreCase(cardType)) && (!refCardId.equals("")) && ((topupAmount != null))){
                    BigDecimal cardId = (BigDecimal)attr2.getInputValue();
                    BigDecimal currentCardAmount = (BigDecimal)topupAmount;
                    Map paramMap = new HashMap();
                    paramMap.put("cardId", cardId);
                    totalTopupAmount = (BigDecimal)ADFUtils.executeOperationBinding("cardTotalTopupToday",paramMap);
                    totalTopupAmount = totalTopupAmount.add(currentCardAmount);
                }
                
               if((("TOPUP_CARD".equalsIgnoreCase(cardType))||("ADDON_CARD".equalsIgnoreCase(cardType))) && (refCardId.equals(""))){
                   JSFUtils.addErrorMessage("Reference Card is required.");  
               }else if("TOPUP_CARD".equalsIgnoreCase(cardType) && (topupAmount == null)){
                   JSFUtils.addErrorMessage("Topup amount is required.");  
               }else if(("TOPUP_CARD".equalsIgnoreCase(cardType)) && (topupAmount != null) && (!refCardId.equals("")) && (totalTopupAmount.compareTo(new BigDecimal(30000)) > 0)){
                   JSFUtils.addErrorMessage("Maximum 30000 topup is allowed per card per day.");                    
               }else{
                   String memberId = (String)attr.getInputValue();
                   Map paramMap = new HashMap();
                   paramMap.put("cardType", cardType);
                   paramMap.put("memberId", memberId);
                   Integer rowCount = (Integer)ADFUtils.executeOperationBinding("findApplicationPerCardType",paramMap);
                   String cifNumber = null;
                   if("NEW_CARD".equalsIgnoreCase(cardType)){
                       Map paramMap1 = new HashMap();
                       paramMap1.put("cardType", cardType);
                       paramMap1.put("memberId", memberId); 
                       cifNumber = (String)ADFUtils.executeOperationBinding("findCIFNumberForCardTypeNew",paramMap1);
                   }else if((refCardId != null) && !("NEW_CARD".equalsIgnoreCase(cardType))){
                   Map paramMap1 = new HashMap();
                   paramMap1.put("cardId", (BigDecimal)refCardId);
                   cifNumber = (String)ADFUtils.executeOperationBinding("findCIFNumberPerCardType",paramMap1);
                   }
                   
                   if((rowCount>0) && ("NEW_CARD".equalsIgnoreCase(cardType)) && !("REJECTED".equalsIgnoreCase(status) || "LINCU_REJECTED".equalsIgnoreCase(status) || "FCB_REJECTED".equalsIgnoreCase(status)) && (cifNumber != null)){
                       JSFUtils.addErrorMessage("Member has been issued with a LinCu card"); 
                   }else if((rowCount>0) && ("NEW_CARD".equalsIgnoreCase(cardType)) && !("REJECTED".equalsIgnoreCase(status) || "LINCU_REJECTED".equalsIgnoreCase(status) || "FCB_REJECTED".equalsIgnoreCase(status)) && (cifNumber == null)){
                       JSFUtils.addErrorMessage("Member application for new card is in process"); 
                   }else if((cifNumber == null) && (("ADDON_CARD".equalsIgnoreCase(cardType)) || ("TOPUP_CARD".equalsIgnoreCase(cardType)))){
                       JSFUtils.addErrorMessage("Application cannot be submitted till primary card issued with CIF number."); 
                   }else if((rowCount>=5) && ("ADDON_CARD".equalsIgnoreCase(cardType)) && !("REJECTED".equalsIgnoreCase(status) || "LINCU_REJECTED".equalsIgnoreCase(status) || "FCB_REJECTED".equalsIgnoreCase(status))){
                       JSFUtils.addErrorMessage("Member is eligible to request maximum 5 Add-On cards"); 
                   }else{
                   
                   if((("TOPUP_CARD".equalsIgnoreCase(cardType))||("ADDON_CARD".equalsIgnoreCase(cardType)))){
                    ADFUtils.executeOperationBinding("deleteDocumentRecords");
                       RichPopup.PopupHints hints = new RichPopup.PopupHints();
                       this.getCommentsPopup().show(hints);
                   }else{                      
                       Boolean isAllReqDocsUploaded = (Boolean)ADFUtils.executeOperationBinding("isAllRequiredDocumentsUploaded");
                       if(isAllReqDocsUploaded){
                           RichPopup.PopupHints hints = new RichPopup.PopupHints();
                           this.getCommentsPopup().show(hints);
                       //             ADFUtils.executeOperationBinding("submitCard");
                       }else{
                           JSFUtils.addErrorMessage("Please add required documents");
                       }  
                   }
               }
               }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void submitNotes(ActionEvent actionEvent){
        try {
              ADFUtils.executeOperationBinding("generateAndSetMPSDID");
              ADFUtils.executeOperationBinding("submitCard");
              ADFUtils.executeOperationBinding("updateAuditTable");
              ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
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
            ADFUtils.executeOperationBinding("approve");
            ADFUtils.executeOperationBinding("updateAuditTable");
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void reject(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("reject");
            ADFUtils.executeOperationBinding("updateAuditTable");
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void lincuApprove(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("lincuApprove");
            ADFUtils.executeOperationBinding("updateAuditTable");
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void lincuReject(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("lincuReject");
            ADFUtils.executeOperationBinding("updateAuditTable");
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void fcbApprove(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("fcbApprove");
            ADFUtils.executeOperationBinding("updateAuditTable");
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void fcbReject(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("fcbReject");
            ADFUtils.executeOperationBinding("updateAuditTable");
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public String attachDocument() {
        try {
            String action = (String)ADFUtils.getPageFlowScopeValue("action");
            if("create".equalsIgnoreCase(action))
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
        InputStream inputStream = null;
        try{
        UploadedFile imageFile = this.getFile();
        String fileName = imageFile.getFilename();
        String fileExtn = getFileExtn(fileName);

        if (!isExcel(fileExtn)) {
            JSFUtils.addErrorMessage("Only follwing file formats are allowed for upload. pdf, txt, png, tif, tiff, gif, jpg, jpeg, jpe, jfif");
            this.setFile(null);
        } else {
            BindingContext bindingCtx = BindingContext.getCurrent();
            BindingContainer bindingcnt = bindingCtx.getCurrentBindingsEntry();
            DCBindingContainer dcContainger = (DCBindingContainer) bindingcnt;
            DCIteratorBinding docItr = dcContainger.findIteratorBinding("LincuMemberCardDocsIterator");

            DCIteratorBinding cardItr = dcContainger.findIteratorBinding("LincuMemberCardIterator");

            oracle.jbo.Row docRow = docItr.getCurrentRow();
            oracle.jbo.Row cardRow = cardItr.getCurrentRow();
            Path p1 = Paths.get("C:\\Users\\DileepKumar\\Desktop\\LinCu\\UploadedDocuments\\"+cardRow.getAttribute("CardId").toString()+"\\"+docRow.getAttribute("DocId").toString());
            Files.createDirectories(p1);
            String path = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\UploadedDocuments\\"+cardRow.getAttribute("CardId").toString()+"\\"+docRow.getAttribute("DocId").toString()+"\\"+fileName;
            FileOutputStream out = new FileOutputStream(path);
            inputStream = imageFile.getInputStream();
            byte[] buffer = new byte[10240];
            int bytesRead = 0;
            while ((bytesRead = inputStream.read(buffer, 0, 10240)) != -1) {
                                out.write(buffer, 0, bytesRead);
                }
            out.flush();
            out.close();
            //Enable this comment when we store value in DB again
            //row.setAttribute("Document", createBlobDomain(imageFile));
            //"C:\\Users\\DileepKumar\\Desktop\\LinCu\\UploadedDocuments";
            docRow.setAttribute("Path", path);
            docRow.setAttribute("DocumentName", fileName);
            //this.getAttachDocument().hide();
        }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if(inputStream != null)
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    String getFileExtn(String filename) {
        String parts[] = filename.split("\\.(?=[^\\.]+$)");
        return parts[1].toLowerCase();
    }

    boolean isExcel(String fileExtn) {
        if (fileExtn.equals("pdf") || fileExtn.equals("txt") || fileExtn.equals("png") || fileExtn.equals("tif") ||
            fileExtn.equals("tiff") || fileExtn.equals("gif") || fileExtn.equals("jpeg") || fileExtn.equals("jpe") ||
            fileExtn.equals("jfif") || fileExtn.equals("jpg"))
            return true;
        else
            return false;

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
            String action = (String)ADFUtils.getPageFlowScopeValue("action");
            if("create".equalsIgnoreCase(action))
            ADFUtils.executeOperationBinding("removeCurrentRow"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getAttachDocument().hide();
    }

    public void confirmUpload(ActionEvent actionEvent) {
//        UploadedFile imageFile = this.getFile();
//        BindingContext bindingCtx = BindingContext.getCurrent();
//        BindingContainer bindingcnt = bindingCtx.getCurrentBindingsEntry();
//        DCBindingContainer dcContainger = (DCBindingContainer) bindingcnt;
//        DCIteratorBinding itr = dcContainger.findIteratorBinding("LincuMemberCardDocsIterator");
//
//        oracle.jbo.Row row = itr.getCurrentRow();
//        row.setAttribute("Document", createBlobDomain(imageFile));
//        row.setAttribute("DocumentName", imageFile.getFilename());
        this.getAttachDocument().hide();
    }

    public void downloadFile(FacesContext facesContext, OutputStream outputStream) {
             BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();  
             //AttributeBinding attr = (AttributeBinding) bindings.getControlBinding("Document");
               AttributeBinding attr = (AttributeBinding) bindings.getControlBinding("Path");  
             if (attr != null)  
             {  
                   try  
                   {   
               String filePath = (String)attr.getInputValue();
               File filed = new File(filePath);
               FileInputStream bis;
                bis = new FileInputStream(filed);

            //BlobDomain blob = (BlobDomain) attr.getInputValue();
                // copy the data from the blobDomain to the output stream   
                 IOUtils.copy(bis, outputStream);  
                 bis.close();  
                 outputStream.flush();  
               }  
               catch (IOException e){  
                 // handle errors  
                 e.printStackTrace();  
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");  
                 FacesContext.getCurrentInstance().addMessage(null, msg);  
               } 
             }  
    }
    
    public void downloadFileOtherCardTypeDocs(FacesContext facesContext, OutputStream outputStream) {
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
    
    public void downloadFileOtherTypes(FacesContext facesContext, OutputStream outputStream) {
             BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();  
             AttributeBinding attr = (AttributeBinding) bindings.getControlBinding("Document1");  
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

    public void downloadDocsAsZip(FacesContext facesContext, OutputStream outputStream) {
        try {
        DCIteratorBinding iter = ADFUtils.findIterator("LincuMemberCardDocsIterator");
        ViewObject cardDocsView = iter.getViewObject();
        oracle.jbo.Row rows[] = cardDocsView.getAllRowsInRange();
        ZipOutputStream zos = new ZipOutputStream(outputStream);
        for(oracle.jbo.Row docRow : rows){
            BlobDomain blob = (BlobDomain) docRow.getAttribute("Document"); 
            String fileName = (String)docRow.getAttribute("DocumentName"); 
            InputStream initialStream = blob.getInputStream();
            // create byte buffer
            File srcFile = stream2file(initialStream);
            IOUtils.closeQuietly(initialStream);    
            byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(srcFile);
                // begin writing a new ZIP entry, positions the stream to the start of the entry data
                zos.putNextEntry(new ZipEntry(fileName));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                // close the InputStream
                fis.close();
            
        }
            zos.close();
            outputStream.flush();
            outputStream.close(); 
        }
        catch (IOException ioe) {
            System.out.println("Error creating zip file: " + ioe);
        }

    }
    
    
    public static File stream2file (InputStream in) throws IOException {
            File tempFile = File.createTempFile("stream2file", ".tmp");
            tempFile.deleteOnExit();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                IOUtils.copy(in, out);
            }
            return tempFile;
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
          * Method to read xlsx file and upload to table.
          * @param myxls
          * @throws IOException
          */
         public void readNProcessExcelx(InputStream xlsx) throws IOException {

             CollectionModel cModel = (CollectionModel) getCardTable().getValue();
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

    public void addToBatch(ActionEvent actionEvent) {
        // Add event code here...
        OperationBinding oper = ADFUtils.findOperation("addToBatch");
        oper.execute();
        if(oper.getErrors() != null && oper.getErrors().size() >0)
          return;
        else {
            ADFUtils.executeOperationBinding("Commit");            
            JSFUtils.addInformationMessage("Selected card requests have been added to a new batch successfully");
        }
    }

    public void dlownloadAgreement(FacesContext facesContext, OutputStream outputStream) {
        String a_PDF_file = "C:\\Users\\DileepKumar\\Desktop\\LinCUDocs\\InteractiveElectr0nicServicesAgreement.pdf";
               
               File file = new File(a_PDF_file);
               
               InputStream is = null;
               
        
               try {
                   is = new FileInputStream(file);
                   
                   
                   int read = 0;
                   byte[] bytes = new byte[1024];
                   
                   while ((read = is.read(bytes)) != -1) {
                        outputStream.write(bytes, 0, read);
                   }
                   
                   
               } catch (IOException e) {
                   e.printStackTrace();
               }
               finally {
                   if (is != null) {
                               try {
                                     is.close();
                                } catch (IOException e) {
                                   e.printStackTrace();
                              }
                    }
                    if (outputStream != null) {
                           try {
                                 outputStream.flush();
                            } catch (Exception e) {
                                   e.printStackTrace();
                            }
                          }
                       }    
           }

    public void save(ActionEvent actionEvent) {
        try {
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();  
            AttributeBinding attr1 = (AttributeBinding) bindings.getControlBinding("CardReqType1");  
            AttributeBinding attr2 = (AttributeBinding) bindings.getControlBinding("RefCardId");  
            AttributeBinding attr3 = (AttributeBinding) bindings.getControlBinding("TopupAmount");  
            if (attr1 != null)  
            { 
               String cardType = (String)attr1.getInputValue();
               Object refCardId = attr2.getInputValue();
               Object topupAmount = attr3.getInputValue();
               if((("TOPUP_CARD".equalsIgnoreCase(cardType))||("ADDON_CARD".equalsIgnoreCase(cardType))) && ((refCardId==null)||(refCardId.equals("")))){
                   JSFUtils.addErrorMessage("Reference Card is required.");  
               }else if("TOPUP_CARD".equalsIgnoreCase(cardType) && (topupAmount == null)){
                   JSFUtils.addErrorMessage("Topup amount is required.");  
               }else{
                   if((("TOPUP_CARD".equalsIgnoreCase(cardType))||("ADDON_CARD".equalsIgnoreCase(cardType)))){
                    ADFUtils.executeOperationBinding("deleteDocumentRecords");
                   }
                   
                   ADFUtils.executeOperationBinding("saveCardRequest");
                   ADFUtils.executeOperationBinding("Commit");
                   this.getCommentsPopup().hide();
               }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void exportCardDetailsDownload(FacesContext facesContext, OutputStream outputStream) {
    try {
    List wb = (List) ADFUtils.executeOperationBinding("createExportCardDetailsWb"); //method for creating WB
    HSSFWorkbook workbook = (HSSFWorkbook) wb.get(0);
    workbook.write(outputStream);
    outputStream.flush();
    } catch (IOException ex) {
    ex.printStackTrace();
    }

    }
    
    public void exportCardDetailsDownloadAsText(FacesContext facesContext, OutputStream outputStream) {
    try {
    String dataText = (String) ADFUtils.executeOperationBinding("createExportCardDetailsAsText"); //method for creating text file
    OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
    writer.write(dataText);
    writer.flush();
    } catch (IOException ex) {
    ex.printStackTrace();
    }

    }
    
    public void exportCardDetailsTopupDownload(FacesContext facesContext, OutputStream outputStream) {
    try {
    List wb = (List) ADFUtils.executeOperationBinding("createExportCardTopupDetailsWb"); //method for creating WB
    HSSFWorkbook workbook = (HSSFWorkbook) wb.get(0);
    workbook.write(outputStream);
    outputStream.flush();
    } catch (IOException ex) {
    ex.printStackTrace();
    }

    }
    
    public void exportCardDetailsTopupDownloadAsText(FacesContext facesContext, OutputStream outputStream) {
    try {
        String dataText = (String) ADFUtils.executeOperationBinding("createExportCardTopupDetailsAsText"); //method for creating text file
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(dataText);
        writer.flush();
    } catch (IOException ex) {
    ex.printStackTrace();
    }

    }

    public void setDeleteConfirm(RichPopup deleteConfirm) {
        this.deleteConfirm = deleteConfirm;
    }

    public RichPopup getDeleteConfirm() {
        return deleteConfirm;
    }

    public void confirmDeleteAction(ActionEvent actionEvent) {
        try {
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getDeleteConfirm().show(hints);              
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCard(ActionEvent actionEvent) {
        try {
            //ADFUtils.executeOperationBinding("Delete"); 
            ADFUtils.executeOperationBinding("closeCard");
            ADFUtils.executeOperationBinding("Commit"); 
            ADFUtils.executeOperationBinding("Execute"); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCardTable());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getDeleteConfirm().hide();
    }

    public void cardReqType(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

//    public void printApplication(ActionEvent actionEvent) {
//        String originalPdf = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\trunk\\LinCuApp\\LinCuViewController\\public_html\\resources\\ApplicationCreditUnion.pdf";
//        String targetPdf = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\trunk\\LinCuApp\\LinCuViewController\\public_html\\resources\\ApplicationCreditUnionFilled.pdf";
//        
//        try {
//            PdfPopulator.populateAndCopy(new File(originalPdf), new File(targetPdf));
//        } catch (Exception e) {
//                e.printStackTrace();
//        }
//        
//        System.out.println("Complete");
//    }
    
    public void printApplication(FacesContext facesContext, OutputStream outputStream) {
        //String originalPdf = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\trunk\\LinCuApp\\LinCuViewController\\public_html\\resources\\ApplicationCreditUnion.pdf";
        //String targetPdf = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\trunk\\LinCuApp\\LinCuViewController\\public_html\\resources\\ApplicationCreditUnionFilled.pdf";
        
        //String originalPdf = "C:\\Users\\DileepKumar\\AppData\\Roaming\\JDeveloper\\system12.2.1.0.42.170105.1157\\o.j2ee\\drs\\LinCuApp\\LinCuViewControllerWebApp.war\\resources\\ApplicationCreditUnion.pdf";
        //String targetPdf = "C:\\Users\\DileepKumar\\AppData\\Roaming\\JDeveloper\\system12.2.1.0.42.170105.1157\\o.j2ee\\drs\\LinCuApp\\LinCuViewControllerWebApp.war\\resources\\ApplicationCreditUnionFilled.pdf";;

        
          try  
          {  // copy the data from the blobDomain to the output stream   
          //InputStream inputStream = new FileInputStream(targetPdf);
          ServletContext servletCtx = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
          String originalPdf = servletCtx.getResource("/resources/ApplicationCreditUnion.pdf").toString();
          originalPdf = originalPdf.substring(6, originalPdf.length());
              System.out.println("---originalPdf----"+originalPdf);
          String targetPdf = servletCtx.getResource("/resources/ApplicationCreditUnionFilled.pdf").toString();
              targetPdf = targetPdf.substring(6, targetPdf.length());
              System.out.println("---targetPdf----"+targetPdf);
           Map applicationData = (Map) ADFUtils.executeOperationBinding("downloadApplication");     
          PdfPopulator.populateAndCopy(new File(originalPdf), new File(targetPdf), applicationData);
          //ServletContext servletCtx = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
          InputStream inputStream = servletCtx.getResourceAsStream("/resources/ApplicationCreditUnionFilled.pdf");
            IOUtils.copy(inputStream, outputStream);  
            inputStream.close();  
            outputStream.flush();  
          }  
          catch (IOException e)  
          {  
            // handle errors  
            e.printStackTrace();  
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");  
            FacesContext.getCurrentInstance().addMessage(null, msg);  
          } catch (Exception e) {
        }

    }
}
