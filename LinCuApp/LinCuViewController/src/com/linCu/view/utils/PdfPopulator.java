package com.linCu.view.utils;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckbox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class PdfPopulator {
 
	private static PDDocument _pdfDocument;
        
    public PdfPopulator() {
           super();
       }
	
	public static void main(String[] args) {
		
		String originalPdf = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\trunk\\LinCuApp\\LinCuViewController\\public_html\\resources\\ApplicationCreditUnion.pdf";
		String targetPdf = "C:\\Users\\DileepKumar\\Desktop\\LinCu\\trunk\\LinCuApp\\LinCuViewController\\public_html\\resources\\ApplicationCreditUnionFilled.pdf";
		
		try {
			//populateAndCopy(new File(originalPdf), new File(targetPdf));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Complete");
	}
 
	public static void populateAndCopy(File originalPdf, File targetPdf, Map applicationData) throws Exception {
		_pdfDocument = PDDocument.load(originalPdf);
		
		_pdfDocument.getNumberOfPages();
		printFields();  //Uncomment to see the fields in this document in console
            
	    setField("Credit Union", (String)applicationData.get("CREDIT_UNION"));
	    setField("Credit UnionBranch", (String)applicationData.get("CREDIT_UNION_BRANCH"));
	    setField("Credit Union Member No", (String)applicationData.get("MEMBER_NO"));
	    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	    Date date = new Date();
	    setField("Birthday Date-2", dateFormat.format(date)); 
            
	    setField("Prefix", (String)applicationData.get("PREFIX"));
	    setField("First Name", (String)applicationData.get("FIRST_NAME"));
	    setField("Middle Name", (String)applicationData.get("MIDDLE_NAME"));
	    setField("Last Name", (String)applicationData.get("LAST_NAME"));
	    setField("Mothers Maiden Name", (String)applicationData.get("MOTHER_MAIDEN_NAME"));
	    setField("Birthday Date-1", (String)applicationData.get("DOB"));
	    setField("Credit UnionBranch-Sex", (String)applicationData.get("GENDER"));
	    setField("Credit UnionBranch-MarialStts", (String)applicationData.get("MARITAL_STATUS"));
	    setField("NumDependents", (String)applicationData.get("NO_OF_DEPENDENTS"));
            String shareHolding = (String)applicationData.get("FCB_SHAREHOLDING");
            if ("MAJ".equalsIgnoreCase(shareHolding)) {
            setField("FcbNo", "check");
            setField("FcbMinority", "unCheck");
            setField("NotaShareHolder", "unCheck");
            } else if ("MIN".equalsIgnoreCase(shareHolding)) {
            setField("FcbNo", "unCheck");
            setField("FcbMinority", "check");
            setField("NotaShareHolder", "unCheck");
            } else {
            setField("FcbNo", "unCheck");
            setField("FcbMinority", "unCheck");
            setField("NotaShareHolder", "check");
            }
	    setField("Electorial ID", (String)applicationData.get("NATIONAL_ID"));
	    setField("Passport", (String)applicationData.get("PASSPORT"));
	    setField("Drivers Permit", (String)applicationData.get("DRIVING_LICENCE"));
	    setField("Permanent  Address", (String)applicationData.get("BIR"));
	    setField("Home Phone", (String)applicationData.get("HOME_PHONE"));
	    setField("Mobile Phone", (String)applicationData.get("MOBILE_PHONE"));
	    setField("Fax Number", (String)applicationData.get("FAX_NUMBER"));
	    setField("Credit UnionBranch-Education", (String)applicationData.get("EDUCATION"));
	    setField("Credit UnionBranch-Income", (String)applicationData.get("MONTHLY_SALARY"));
            
	    setField("Line 1", (String)applicationData.get("PER_ADD_LINE1"));
	    setField("Line 2", (String)applicationData.get("PER_ADD_LINE2"));
	    setField("Credit UnionBranch-Street", (String)applicationData.get("PER_STREET"));
	    setField("City", (String)applicationData.get("PER_CITY"));
	    setField("State", (String)applicationData.get("PER_STATE"));
	    setField("Credit UnionBranch-Country", (String)applicationData.get("PER_COUNTRY"));
	    setField("ZIP code",(String)applicationData.get("PER_ZIP"));
	    String homeTownship = (String)applicationData.get("FCB_SHAREHOLDING");
            if("R".equalsIgnoreCase(homeTownship)){
                setField("HomeOwnershipRent", "check");
                setField("HomeOwnership_livewith", "unCheck");
                setField("HomeOwnershipSublet", "unCheck");
                setField("HomeOwnership", "unCheck");    
            }else if("L".equalsIgnoreCase(homeTownship)){
                setField("HomeOwnershipRent", "unCheck");
                setField("HomeOwnership_livewith", "check");
                setField("HomeOwnershipSublet", "unCheck");
                setField("HomeOwnership", "unCheck"); 
            }else if("S".equalsIgnoreCase(homeTownship)){
                setField("HomeOwnershipRent", "unCheck");
                setField("HomeOwnership_livewith", "unCheck");
                setField("HomeOwnershipSublet", "check");
                setField("HomeOwnership", "unCheck"); 
            }else{
                setField("HomeOwnershipRent", "unCheck");
                setField("HomeOwnership_livewith", "unCheck");
                setField("HomeOwnershipSublet", "unCheck");
                setField("HomeOwnership", "check"); 
            }
            
	    setField("Line 1_1", (String)applicationData.get("MAIL_ADD_LINE1"));
	    setField("Line 2_2", (String)applicationData.get("MAIL_ADD_LINE2"));
	    setField("Credit UnionBranch-Street1", (String)applicationData.get("MAIL_ADD_STREET"));
	    setField("City_2", (String)applicationData.get("MAIL_ADD_CITY"));
	    setField("State_2", (String)applicationData.get("MAIL_ADD_STATE"));
	    setField("Credit UnionBranch-MCountry", (String)applicationData.get("MAIL_ADD_COUNTRY"));
	    setField("ZIP code_2", (String)applicationData.get("MAIL_ADD_ZIP"));
            
	    setField("Employers  Name", (String)applicationData.get("EMP_NAME"));
	    setField("Credit UnionBranchEmpInfo-OccuCountry", (String)applicationData.get("EMP_OCCUPATION"));
	    setField("Employer Address", (String)applicationData.get("EMP_ADD_LINE1"));
	    setField("Credit UnionBranch-EmpInfCountry", (String)applicationData.get("EMP_ADD_CITY"));
	    setField("EmpZIP code_2", (String)applicationData.get("EMP_ADD_LINE2"));
	    setField("Business Phone", (String)applicationData.get("BUSINESS_PHONE"));
	    setField("Business Phone Extension", (String)applicationData.get("BUSINESS_PHONE_EXTN"));
            
	    setField("Credit UnionBranch-FACTACountry", (String)applicationData.get("BUSINESS_PHONE_EXTN"));
	    String localTaxExcemption = (String)applicationData.get("LOCAL_TAX_EXMP");
            if("Y".equalsIgnoreCase(localTaxExcemption)){
	    setField("Documents for Foreign Tax exemption", "Yes");
            }else{
            setField("Documents for Foreign Tax exemption", "No"); 
            }
	    
	    setField("Credit UnionBranch-FACTACountry", (String)applicationData.get("COUNTRY_OF_BIRTH"));
	    setField("Credit UnionBranch-FACTANationality", (String)applicationData.get("NATIONALITY"));
	    setField("Credit UnionBranch-ctz3Country", (String)applicationData.get("CITIZENSHIP3"));
	    setField("Credit UnionBranch-ctz4Country", (String)applicationData.get("CITIZENSHIP4"));
	    setField("Credit UnionBranch-ctz1Country", (String)applicationData.get("CITIZENSHIP1"));  
	    setField("Credit UnionBranch-ctz2Country", (String)applicationData.get("CITIZENSHIP2"));
            
	    String eligibleForTax = (String) applicationData.get("ELIGIBLE_FOR_TAX");
        if ("Y".equalsIgnoreCase(eligibleForTax)) {
            setField("Yes", "check");
            setField("No", "unCheck");
        } else {
            setField("Yes", "unCheck");
            setField("No", "check");
        }

        String docsForTaxExmpt = (String) applicationData.get("DOCS_FOR_TAX_EXMP");
        if ("Y".equalsIgnoreCase(docsForTaxExmpt)) {
            setField("FNo", "check");
            setField("FYes", "unCheck");
        } else {
            setField("FNo", "unCheck");
            setField("FYes", "check");
        }
        String citizenInAnotherCountry = (String) applicationData.get("CITIZENS_IN_OTHER_COUNTRY");
        if ("Y".equalsIgnoreCase(citizenInAnotherCountry)) {
            setField("ctzYes", "check");
            setField("ctzNo", "unCheck");
        } else {
            setField("ctzYes", "unCheck");
            setField("ctzNo", "check");
        }

        String powerOfAtterney = (String) applicationData.get("POWER_OF_ATTERNY");
        if ("Y".equalsIgnoreCase(powerOfAtterney)) {
            setField("PwrYes", "unCheck");
            setField("PwrFNo", "check");
        } else {
            setField("PwrYes", "unCheck");
            setField("PwrFNo", "check");
        }
	    
	    setField("Member Service Representative Print Name", "Dileep Kumar Rongali");
	    setField("Date", "15-06-2017");
	    setField("Superviser Print Name", "Lalitha Rani Vekanuru");
	    setField("Date_2", "18-06-2017");
	    
	    setField("SSN No", "");
	    setField("SSN No: sub", "");
	    setField("SSN No4", "");
	    
	    setField("PYes", "unCheck"); 
	    setField("PNo", "check"); 
	    setField("IfYes", "");
        
	    setField("I authorize my Credit Union to debit my account for", "");
	    

       
		_pdfDocument.save(targetPdf);
		_pdfDocument.close();
	}
	
    public static void setField(String name, String value ) throws IOException {
        PDDocumentCatalog docCatalog = _pdfDocument.getDocumentCatalog();
        PDAcroForm acroForm = docCatalog.getAcroForm();
        PDField field = acroForm.getField( name );
        if( field != null ) {
            if("check".equalsIgnoreCase(value)){
                ((PDCheckbox) field).check();    
            }else if("unCheck".equalsIgnoreCase(value)){
                ((PDCheckbox) field).unCheck();  
            }else{
            field.setValue(value);
            }
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
            System.out.println("----Field Full Name----"+field.getFullyQualifiedName());
            //System.out.println("----Field Partial Name----"+field.getPartialName());
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
             //System.out.println("String - " + outputString.replaceAll(" ", ""));
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
