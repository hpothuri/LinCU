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
import oracle.jbo.Row;
import oracle.jbo.domain.BlobDomain;
import org.apache.commons.io.IOUtils;
public class CardBean {
    private RichPopup commentsPopup;
    private RichPopup attachDocument;
    private UploadedFile _file;
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

        Row row = itr.getCurrentRow();
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

        Row row = itr.getCurrentRow();
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
}
