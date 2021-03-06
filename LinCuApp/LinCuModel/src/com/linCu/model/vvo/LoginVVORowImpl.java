package com.linCu.model.vvo;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sat Mar 11 00:29:48 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LoginVVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        UserName,
        Password,
        FirstLoginFlag,
        Role,
        RoleDescription,
        UserId,
        CreditUnionId,
        UserTypeCode,
        Email;
        private static AttributesEnum[] vals = null;
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


    public static final int USERNAME = AttributesEnum.UserName.index();
    public static final int PASSWORD = AttributesEnum.Password.index();
    public static final int FIRSTLOGINFLAG = AttributesEnum.FirstLoginFlag.index();
    public static final int ROLE = AttributesEnum.Role.index();
    public static final int ROLEDESCRIPTION = AttributesEnum.RoleDescription.index();
    public static final int USERID = AttributesEnum.UserId.index();
    public static final int CREDITUNIONID = AttributesEnum.CreditUnionId.index();
    public static final int USERTYPECODE = AttributesEnum.UserTypeCode.index();
    public static final int EMAIL = AttributesEnum.Email.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LoginVVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute UserName.
     * @return the UserName
     */
    public String getUserName() {
        return (String) getAttributeInternal(USERNAME);
    }

    /**
     * Gets the attribute value for the calculated attribute Password.
     * @return the Password
     */
    public String getPassword() {
        return (String) getAttributeInternal(PASSWORD);
    }

    /**
     * Gets the attribute value for the calculated attribute FirstLoginFlag.
     * @return the FirstLoginFlag
     */
    public String getFirstLoginFlag() {
        return (String) getAttributeInternal(FIRSTLOGINFLAG);
    }

    /**
     * Gets the attribute value for the calculated attribute Role.
     * @return the Role
     */
    public String getRole() {
        return (String) getAttributeInternal(ROLE);
    }

    /**
     * Gets the attribute value for the calculated attribute RoleDescription.
     * @return the RoleDescription
     */
    public String getRoleDescription() {
        return (String) getAttributeInternal(ROLEDESCRIPTION);
    }

    /**
     * Gets the attribute value for the calculated attribute UserId.
     * @return the UserId
     */
    public Long getUserId() {
        return (Long) getAttributeInternal(USERID);
    }

    /**
     * Gets the attribute value for the calculated attribute CreditUnionId.
     * @return the CreditUnionId
     */
    public String getCreditUnionId() {
        return (String) getAttributeInternal(CREDITUNIONID);
    }

    /**
     * Gets the attribute value for the calculated attribute UserTypeCode.
     * @return the UserTypeCode
     */
    public String getUserTypeCode() {
        return (String) getAttributeInternal(USERTYPECODE);
    }

    /**
     * Gets the attribute value for the calculated attribute Email.
     * @return the Email
     */
    public String getEmail() {
        return (String) getAttributeInternal(EMAIL);
    }
}

