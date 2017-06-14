package com.linCu.view.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.IOException;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import java.util.List;
import java.util.Iterator;

import org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox;

public class PdfPopulator {
 
	private static PDDocument _pdfDocument;
        
    public PdfPopulator() {
           super();
       }
	
	public static void main(String[] args) {
		
		String originalPdf = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\trunk\\LinCuApp\\LinCuViewController\\public_html\\resources\\ApplicationCreditUnion.pdf";
		String targetPdf = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\trunk\\LinCuApp\\LinCuViewController\\public_html\\resources\\ApplicationCreditUnionFilled.pdf";
		
		try {
			populateAndCopy(new File(originalPdf), new File(targetPdf));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Complete");
	}
 
	private static void populateAndCopy(File originalPdf, File targetPdf) throws Exception {
		_pdfDocument = PDDocument.load(originalPdf);
		
		_pdfDocument.getNumberOfPages();
		printFields();  //Uncomment to see the fields in this document in console
		
		setField("Credit Union", "1234567890");
		_pdfDocument.save(targetPdf);
		_pdfDocument.close();
	}
	
    public static void setField(String name, String value ) throws IOException {
        PDDocumentCatalog docCatalog = _pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        PDField field = acroForm.getField( name );
        if( field != null ) {
            field.setValue(value);
        }
        else {
            System.err.println( "No field found with name:" + name );
        }
    }
 
    @SuppressWarnings("rawtypes")
	public static void printFields() throws IOException {
        PDDocumentCatalog docCatalog = _pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        List fields = acroForm.getFields();
        Iterator fieldsIter = fields.iterator();
 
        System.out.println(new Integer(fields.size()).toString() + " top-level fields were found on the form");
 
        while( fieldsIter.hasNext()) {
            PDField field = (PDField)fieldsIter.next();
               processField(field, "|--", field.getPartialName());
        }
    }
    
    @SuppressWarnings("rawtypes")
	private static void processField(PDField field, String sLevel, String sParent) throws IOException
    {
        List kids = field.getKids();
        if(kids != null) {
            Iterator kidsIter = kids.iterator();
            if(!sParent.equals(field.getPartialName())) {
               sParent = sParent + "." + field.getPartialName();
            }
            
            System.out.println(sLevel + sParent);
            
            while(kidsIter.hasNext()) {
               Object pdfObj = kidsIter.next();
               if(pdfObj instanceof PDField) {
                   PDField kid = (PDField)pdfObj;
                   processField(kid, "|  " + sLevel, sParent);
               }
            }
         }
         else {
//             String outputString = sLevel + sParent + "." + field.getFullyQualifiedName() + ",  type=" + field.getClass().getName();
            String outputString = field.getFullyQualifiedName() ;
             System.out.println("String - " + outputString.replaceAll(" ", ""));
            try{
                if(field instanceof org.apache.pdfbox.pdmodel.interactive.form.PDTextbox)
             field.setValue(field.getFullyQualifiedName());
               else if (field instanceof org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox)
                ((PDCheckbox) field).check();
            } catch(Exception e){
                System.err.println("Unable to set value for "+field.getFullyQualifiedName());
            }
         }
    }
}
