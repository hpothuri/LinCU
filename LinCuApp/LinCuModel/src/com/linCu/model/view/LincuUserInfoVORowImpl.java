package com.linCu.model.view;

import java.sql.Timestamp;

import oracle.jbo.RowSet;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Mar 05 17:38:34 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LincuUserInfoVORowImpl extends ViewRowImpl {


    public static final int ENTITY_LINCUUSERINFOEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        UserId,
        FirstName,
        LastName,
        CreditUnionId,
        CreditUnionBranchId,
        UserTypeCode,
        UserRole,
        UserSupervisor,
        Email,
        MobilePhoneNumber,
        DateOfBirth,
        Gender,
        CreatedBy,
        CreationDate,
        LastUpdatedBy,
        LastUpdateDate,
        ObjectVersionId,
        LastApprovedBy,
        LastApprovedDate,
        Password,
        UserName,
        SecurityQustn,
        SecurityQustnAns,
        FirstLoginFlag,
        SecurityQustn1,
        SecurityQustn2,
        SecurityQustn3,
        SecurityQustn4,
        SecurityQustnAns1,
        SecurityQustnAns2,
        SecurityQustnAns3,
        SecurityQustnAns4,
        SwitchRoles,
        GenderVA,
        LinCuRolesVA,
        LincuUnionsVA,
        LincuBranchesVA,
        LookUpDataVA,
        SecurityQuestionsVA,
        CreditUnionLinCuRolesVA,
        FCBRolesVA;
        static AttributesEnum[] vals = null;
        ;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int USERID = AttributesEnum.UserId.index();
    public static final int FIRSTNAME = AttributesEnum.FirstName.index();
    public static final int LASTNAME = AttributesEnum.LastName.index();
    public static final int CREDITUNIONID = AttributesEnum.CreditUnionId.index();
    public static final int CREDITUNIONBRANCHID = AttributesEnum.CreditUnionBranchId.index();
    public static final int USERTYPECODE = AttributesEnum.UserTypeCode.index();
    public static final int USERROLE = AttributesEnum.UserRole.index();
    public static final int USERSUPERVISOR = AttributesEnum.UserSupervisor.index();
    public static final int EMAIL = AttributesEnum.Email.index();
    public static final int MOBILEPHONENUMBER = AttributesEnum.MobilePhoneNumber.index();
    public static final int DATEOFBIRTH = AttributesEnum.DateOfBirth.index();
    public static final int GENDER = AttributesEnum.Gender.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATIONDATE = AttributesEnum.CreationDate.index();
    public static final int LASTUPDATEDBY = AttributesEnum.LastUpdatedBy.index();
    public static final int LASTUPDATEDATE = AttributesEnum.LastUpdateDate.index();
    public static final int OBJECTVERSIONID = AttributesEnum.ObjectVersionId.index();
    public static final int LASTAPPROVEDBY = AttributesEnum.LastApprovedBy.index();
    public static final int LASTAPPROVEDDATE = AttributesEnum.LastApprovedDate.index();
    public static final int PASSWORD = AttributesEnum.Password.index();
    public static final int USERNAME = AttributesEnum.UserName.index();
    public static final int SECURITYQUSTN = AttributesEnum.SecurityQustn.index();
    public static final int SECURITYQUSTNANS = AttributesEnum.SecurityQustnAns.index();
    public static final int FIRSTLOGINFLAG = AttributesEnum.FirstLoginFlag.index();
    public static final int SECURITYQUSTN1 = AttributesEnum.SecurityQustn1.index();
    public static final int SECURITYQUSTN2 = AttributesEnum.SecurityQustn2.index();
    public static final int SECURITYQUSTN3 = AttributesEnum.SecurityQustn3.index();
    public static final int SECURITYQUSTN4 = AttributesEnum.SecurityQustn4.index();
    public static final int SECURITYQUSTNANS1 = AttributesEnum.SecurityQustnAns1.index();
    public static final int SECURITYQUSTNANS2 = AttributesEnum.SecurityQustnAns2.index();
    public static final int SECURITYQUSTNANS3 = AttributesEnum.SecurityQustnAns3.index();
    public static final int SECURITYQUSTNANS4 = AttributesEnum.SecurityQustnAns4.index();
    public static final int SWITCHROLES = AttributesEnum.SwitchRoles.index();
    public static final int GENDERVA = AttributesEnum.GenderVA.index();
    public static final int LINCUROLESVA = AttributesEnum.LinCuRolesVA.index();
    public static final int LINCUUNIONSVA = AttributesEnum.LincuUnionsVA.index();
    public static final int LINCUBRANCHESVA = AttributesEnum.LincuBranchesVA.index();
    public static final int LOOKUPDATAVA = AttributesEnum.LookUpDataVA.index();
    public static final int SECURITYQUESTIONSVA = AttributesEnum.SecurityQuestionsVA.index();
    public static final int CREDITUNIONLINCUROLESVA = AttributesEnum.CreditUnionLinCuRolesVA.index();
    public static final int FCBROLESVA = AttributesEnum.FCBRolesVA.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LincuUserInfoVORowImpl() {
    }

    /**
     * Gets LincuUserInfoEO entity object.
     * @return the LincuUserInfoEO
     */
    public EntityImpl getLincuUserInfoEO() {
        return (EntityImpl) getEntity(ENTITY_LINCUUSERINFOEO);
    }

    /**
     * Gets the attribute value for USER_ID using the alias name UserId.
     * @return the USER_ID
     */
    public Long getUserId() {
        return (Long) getAttributeInternal(USERID);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_ID using the alias name UserId.
     * @param value value to set the USER_ID
     */
    public void setUserId(Long value) {
        setAttributeInternal(USERID, value);
    }

    /**
     * Gets the attribute value for FIRST_NAME using the alias name FirstName.
     * @return the FIRST_NAME
     */
    public String getFirstName() {
        return (String) getAttributeInternal(FIRSTNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for FIRST_NAME using the alias name FirstName.
     * @param value value to set the FIRST_NAME
     */
    public void setFirstName(String value) {
        setAttributeInternal(FIRSTNAME, value);
    }

    /**
     * Gets the attribute value for LAST_NAME using the alias name LastName.
     * @return the LAST_NAME
     */
    public String getLastName() {
        return (String) getAttributeInternal(LASTNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_NAME using the alias name LastName.
     * @param value value to set the LAST_NAME
     */
    public void setLastName(String value) {
        setAttributeInternal(LASTNAME, value);
    }

    /**
     * Gets the attribute value for CREDIT_UNION_ID using the alias name CreditUnionId.
     * @return the CREDIT_UNION_ID
     */
    public String getCreditUnionId() {
        return (String) getAttributeInternal(CREDITUNIONID);
    }

    /**
     * Sets <code>value</code> as attribute value for CREDIT_UNION_ID using the alias name CreditUnionId.
     * @param value value to set the CREDIT_UNION_ID
     */
    public void setCreditUnionId(String value) {
        setAttributeInternal(CREDITUNIONID, value);
    }

    /**
     * Gets the attribute value for CREDIT_UNION_BRANCH_ID using the alias name CreditUnionBranchId.
     * @return the CREDIT_UNION_BRANCH_ID
     */
    public String getCreditUnionBranchId() {
        return (String) getAttributeInternal(CREDITUNIONBRANCHID);
    }

    /**
     * Sets <code>value</code> as attribute value for CREDIT_UNION_BRANCH_ID using the alias name CreditUnionBranchId.
     * @param value value to set the CREDIT_UNION_BRANCH_ID
     */
    public void setCreditUnionBranchId(String value) {
        setAttributeInternal(CREDITUNIONBRANCHID, value);
    }

    /**
     * Gets the attribute value for USER_TYPE_CODE using the alias name UserTypeCode.
     * @return the USER_TYPE_CODE
     */
    public String getUserTypeCode() {
        return (String) getAttributeInternal(USERTYPECODE);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_TYPE_CODE using the alias name UserTypeCode.
     * @param value value to set the USER_TYPE_CODE
     */
    public void setUserTypeCode(String value) {
        System.out.println("------------UserTypeCode-------"+value);
        if(value != null){
            if("CREDIT_UNION".equalsIgnoreCase(value)){
                this.setSwitchRoles("LOV_UserRole1");
            }else if("LINCU".equalsIgnoreCase(value)){
                this.setSwitchRoles("LOV_UserRole");
            }else{
                this.setSwitchRoles("LOV_UserRole12");
            }
        }
        setAttributeInternal(USERTYPECODE, value);
    }

    /**
     * Gets the attribute value for USER_ROLE using the alias name UserRole.
     * @return the USER_ROLE
     */
    public String getUserRole() {
        return (String) getAttributeInternal(USERROLE);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_ROLE using the alias name UserRole.
     * @param value value to set the USER_ROLE
     */
    public void setUserRole(String value) {
        setAttributeInternal(USERROLE, value);
    }

    /**
     * Gets the attribute value for USER_SUPERVISOR using the alias name UserSupervisor.
     * @return the USER_SUPERVISOR
     */
    public String getUserSupervisor() {
        return (String) getAttributeInternal(USERSUPERVISOR);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_SUPERVISOR using the alias name UserSupervisor.
     * @param value value to set the USER_SUPERVISOR
     */
    public void setUserSupervisor(String value) {
        setAttributeInternal(USERSUPERVISOR, value);
    }

    /**
     * Gets the attribute value for EMAIL using the alias name Email.
     * @return the EMAIL
     */
    public String getEmail() {
        return (String) getAttributeInternal(EMAIL);
    }

    /**
     * Sets <code>value</code> as attribute value for EMAIL using the alias name Email.
     * @param value value to set the EMAIL
     */
    public void setEmail(String value) {
        setAttributeInternal(EMAIL, value);
        setUserName(value);
    }

    /**
     * Gets the attribute value for MOBILE_PHONE_NUMBER using the alias name MobilePhoneNumber.
     * @return the MOBILE_PHONE_NUMBER
     */
    public String getMobilePhoneNumber() {
        return (String) getAttributeInternal(MOBILEPHONENUMBER);
    }

    /**
     * Sets <code>value</code> as attribute value for MOBILE_PHONE_NUMBER using the alias name MobilePhoneNumber.
     * @param value value to set the MOBILE_PHONE_NUMBER
     */
    public void setMobilePhoneNumber(String value) {
        setAttributeInternal(MOBILEPHONENUMBER, value);
    }

    /**
     * Gets the attribute value for DATE_OF_BIRTH using the alias name DateOfBirth.
     * @return the DATE_OF_BIRTH
     */
    public Timestamp getDateOfBirth() {
        return (Timestamp) getAttributeInternal(DATEOFBIRTH);
    }

    /**
     * Sets <code>value</code> as attribute value for DATE_OF_BIRTH using the alias name DateOfBirth.
     * @param value value to set the DATE_OF_BIRTH
     */
    public void setDateOfBirth(Timestamp value) {
        setAttributeInternal(DATEOFBIRTH, value);
    }

    /**
     * Gets the attribute value for GENDER using the alias name Gender.
     * @return the GENDER
     */
    public String getGender() {
        return (String) getAttributeInternal(GENDER);
    }

    /**
     * Sets <code>value</code> as attribute value for GENDER using the alias name Gender.
     * @param value value to set the GENDER
     */
    public void setGender(String value) {
        setAttributeInternal(GENDER, value);
    }

    /**
     * Gets the attribute value for CREATED_BY using the alias name CreatedBy.
     * @return the CREATED_BY
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }


    /**
     * Sets <code>value</code> as attribute value for CREATED_BY using the alias name CreatedBy.
     * @param value value to set the CREATED_BY
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CREATION_DATE using the alias name CreationDate.
     * @return the CREATION_DATE
     */
    public Timestamp getCreationDate() {
        return (Timestamp) getAttributeInternal(CREATIONDATE);
    }


    /**
     * Sets <code>value</code> as attribute value for CREATION_DATE using the alias name CreationDate.
     * @param value value to set the CREATION_DATE
     */
    public void setCreationDate(Timestamp value) {
        setAttributeInternal(CREATIONDATE, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy.
     * @return the LAST_UPDATED_BY
     */
    public String getLastUpdatedBy() {
        return (String) getAttributeInternal(LASTUPDATEDBY);
    }


    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATED_BY using the alias name LastUpdatedBy.
     * @param value value to set the LAST_UPDATED_BY
     */
    public void setLastUpdatedBy(String value) {
        setAttributeInternal(LASTUPDATEDBY, value);
    }

    /**
     * Gets the attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @return the LAST_UPDATE_DATE
     */
    public Timestamp getLastUpdateDate() {
        return (Timestamp) getAttributeInternal(LASTUPDATEDATE);
    }


    /**
     * Sets <code>value</code> as attribute value for LAST_UPDATE_DATE using the alias name LastUpdateDate.
     * @param value value to set the LAST_UPDATE_DATE
     */
    public void setLastUpdateDate(Timestamp value) {
        setAttributeInternal(LASTUPDATEDATE, value);
    }

    /**
     * Gets the attribute value for OBJECT_VERSION_ID using the alias name ObjectVersionId.
     * @return the OBJECT_VERSION_ID
     */
    public Long getObjectVersionId() {
        return (Long) getAttributeInternal(OBJECTVERSIONID);
    }

    /**
     * Sets <code>value</code> as attribute value for OBJECT_VERSION_ID using the alias name ObjectVersionId.
     * @param value value to set the OBJECT_VERSION_ID
     */
    public void setObjectVersionId(Long value) {
        setAttributeInternal(OBJECTVERSIONID, value);
    }

    /**
     * Gets the attribute value for LAST_APPROVED_BY using the alias name LastApprovedBy.
     * @return the LAST_APPROVED_BY
     */
    public String getLastApprovedBy() {
        return (String) getAttributeInternal(LASTAPPROVEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_APPROVED_BY using the alias name LastApprovedBy.
     * @param value value to set the LAST_APPROVED_BY
     */
    public void setLastApprovedBy(String value) {
        setAttributeInternal(LASTAPPROVEDBY, value);
    }

    /**
     * Gets the attribute value for LAST_APPROVED_DATE using the alias name LastApprovedDate.
     * @return the LAST_APPROVED_DATE
     */
    public Timestamp getLastApprovedDate() {
        return (Timestamp) getAttributeInternal(LASTAPPROVEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for LAST_APPROVED_DATE using the alias name LastApprovedDate.
     * @param value value to set the LAST_APPROVED_DATE
     */
    public void setLastApprovedDate(Timestamp value) {
        setAttributeInternal(LASTAPPROVEDDATE, value);
    }

    /**
     * Gets the attribute value for PASSWORD using the alias name Password.
     * @return the PASSWORD
     */
    public String getPassword() {
        return (String) getAttributeInternal(PASSWORD);
    }

    /**
     * Sets <code>value</code> as attribute value for PASSWORD using the alias name Password.
     * @param value value to set the PASSWORD
     */
    public void setPassword(String value) {
        setAttributeInternal(PASSWORD, value);
    }

    /**
     * Gets the attribute value for USER_NAME using the alias name UserName.
     * @return the USER_NAME
     */
    public String getUserName() {
        return (String) getAttributeInternal(USERNAME);
    }

    /**
     * Sets <code>value</code> as attribute value for USER_NAME using the alias name UserName.
     * @param value value to set the USER_NAME
     */
    public void setUserName(String value) {
        setAttributeInternal(USERNAME, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN using the alias name SecurityQustn.
     * @return the SECURITY_QUSTN
     */
    public String getSecurityQustn() {
        return (String) getAttributeInternal(SECURITYQUSTN);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN using the alias name SecurityQustn.
     * @param value value to set the SECURITY_QUSTN
     */
    public void setSecurityQustn(String value) {
        setAttributeInternal(SECURITYQUSTN, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN_ANS using the alias name SecurityQustnAns.
     * @return the SECURITY_QUSTN_ANS
     */
    public String getSecurityQustnAns() {
        return (String) getAttributeInternal(SECURITYQUSTNANS);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN_ANS using the alias name SecurityQustnAns.
     * @param value value to set the SECURITY_QUSTN_ANS
     */
    public void setSecurityQustnAns(String value) {
        setAttributeInternal(SECURITYQUSTNANS, value);
    }

    /**
     * Gets the attribute value for FIRST_LOGIN_FLAG using the alias name FirstLoginFlag.
     * @return the FIRST_LOGIN_FLAG
     */
    public String getFirstLoginFlag() {
        return (String) getAttributeInternal(FIRSTLOGINFLAG);
    }

    /**
     * Sets <code>value</code> as attribute value for FIRST_LOGIN_FLAG using the alias name FirstLoginFlag.
     * @param value value to set the FIRST_LOGIN_FLAG
     */
    public void setFirstLoginFlag(String value) {
        setAttributeInternal(FIRSTLOGINFLAG, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN1 using the alias name SecurityQustn1.
     * @return the SECURITY_QUSTN1
     */
    public String getSecurityQustn1() {
        return (String) getAttributeInternal(SECURITYQUSTN1);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN1 using the alias name SecurityQustn1.
     * @param value value to set the SECURITY_QUSTN1
     */
    public void setSecurityQustn1(String value) {
        setAttributeInternal(SECURITYQUSTN1, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN2 using the alias name SecurityQustn2.
     * @return the SECURITY_QUSTN2
     */
    public String getSecurityQustn2() {
        return (String) getAttributeInternal(SECURITYQUSTN2);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN2 using the alias name SecurityQustn2.
     * @param value value to set the SECURITY_QUSTN2
     */
    public void setSecurityQustn2(String value) {
        setAttributeInternal(SECURITYQUSTN2, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN3 using the alias name SecurityQustn3.
     * @return the SECURITY_QUSTN3
     */
    public String getSecurityQustn3() {
        return (String) getAttributeInternal(SECURITYQUSTN3);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN3 using the alias name SecurityQustn3.
     * @param value value to set the SECURITY_QUSTN3
     */
    public void setSecurityQustn3(String value) {
        setAttributeInternal(SECURITYQUSTN3, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN4 using the alias name SecurityQustn4.
     * @return the SECURITY_QUSTN4
     */
    public String getSecurityQustn4() {
        return (String) getAttributeInternal(SECURITYQUSTN4);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN4 using the alias name SecurityQustn4.
     * @param value value to set the SECURITY_QUSTN4
     */
    public void setSecurityQustn4(String value) {
        setAttributeInternal(SECURITYQUSTN4, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN_ANS1 using the alias name SecurityQustnAns1.
     * @return the SECURITY_QUSTN_ANS1
     */
    public String getSecurityQustnAns1() {
        return (String) getAttributeInternal(SECURITYQUSTNANS1);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN_ANS1 using the alias name SecurityQustnAns1.
     * @param value value to set the SECURITY_QUSTN_ANS1
     */
    public void setSecurityQustnAns1(String value) {
        setAttributeInternal(SECURITYQUSTNANS1, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN_ANS2 using the alias name SecurityQustnAns2.
     * @return the SECURITY_QUSTN_ANS2
     */
    public String getSecurityQustnAns2() {
        return (String) getAttributeInternal(SECURITYQUSTNANS2);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN_ANS2 using the alias name SecurityQustnAns2.
     * @param value value to set the SECURITY_QUSTN_ANS2
     */
    public void setSecurityQustnAns2(String value) {
        setAttributeInternal(SECURITYQUSTNANS2, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN_ANS3 using the alias name SecurityQustnAns3.
     * @return the SECURITY_QUSTN_ANS3
     */
    public String getSecurityQustnAns3() {
        return (String) getAttributeInternal(SECURITYQUSTNANS3);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN_ANS3 using the alias name SecurityQustnAns3.
     * @param value value to set the SECURITY_QUSTN_ANS3
     */
    public void setSecurityQustnAns3(String value) {
        setAttributeInternal(SECURITYQUSTNANS3, value);
    }

    /**
     * Gets the attribute value for SECURITY_QUSTN_ANS4 using the alias name SecurityQustnAns4.
     * @return the SECURITY_QUSTN_ANS4
     */
    public String getSecurityQustnAns4() {
        return (String) getAttributeInternal(SECURITYQUSTNANS4);
    }

    /**
     * Sets <code>value</code> as attribute value for SECURITY_QUSTN_ANS4 using the alias name SecurityQustnAns4.
     * @param value value to set the SECURITY_QUSTN_ANS4
     */
    public void setSecurityQustnAns4(String value) {
        setAttributeInternal(SECURITYQUSTNANS4, value);
    }

    /**
     * Gets the attribute value for the calculated attribute ViewAttr.
     * @return the ViewAttr
     */
    public String getSwitchRoles() {
        System.out.println("UserTypeCode-------------------"+this.getUserTypeCode());
        return (String) getAttributeInternal(SWITCHROLES);
    }

    /**
     * Sets <code>value</code> as the attribute value for the calculated attribute SwitchRoles.
     * @param value value to set the  SwitchRoles
     */
    public void setSwitchRoles(String value) {
        setAttributeInternal(SWITCHROLES, value);
    }

    /**
     * Gets the view accessor <code>RowSet</code> GenderVA.
     */
    public RowSet getGenderVA() {
        return (RowSet) getAttributeInternal(GENDERVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LinCuRolesVA.
     */
    public RowSet getLinCuRolesVA() {
        return (RowSet) getAttributeInternal(LINCUROLESVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LincuUnionsVA.
     */
    public RowSet getLincuUnionsVA() {
        return (RowSet) getAttributeInternal(LINCUUNIONSVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LincuBranchesVA.
     */
    public RowSet getLincuBranchesVA() {
        return (RowSet) getAttributeInternal(LINCUBRANCHESVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> LookUpDataVA.
     */
    public RowSet getLookUpDataVA() {
        return (RowSet) getAttributeInternal(LOOKUPDATAVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> SecurityQuestionsVA.
     */
    public RowSet getSecurityQuestionsVA() {
        return (RowSet) getAttributeInternal(SECURITYQUESTIONSVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> CreditUnionLinCuRolesVA.
     */
    public RowSet getCreditUnionLinCuRolesVA() {
        return (RowSet) getAttributeInternal(CREDITUNIONLINCUROLESVA);
    }

    /**
     * Gets the view accessor <code>RowSet</code> FCBRolesVA.
     */
    public RowSet getFCBRolesVA() {
        return (RowSet) getAttributeInternal(FCBROLESVA);
    }
}

