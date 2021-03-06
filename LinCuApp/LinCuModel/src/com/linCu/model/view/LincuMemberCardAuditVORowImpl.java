package com.linCu.model.view;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Thu Apr 20 22:39:40 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LincuMemberCardAuditVORowImpl extends ViewRowImpl {

    public static final int ENTITY_LINCUMEMBERCARDAUDITEO = 0;

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CardAuditId,
        CardId,
        CardReqType,
        Comments,
        CreditUnionId,
        MemberId,
        Status,
        UpdatedBy,
        UpdatedDate;
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

    public static final int CARDAUDITID = AttributesEnum.CardAuditId.index();
    public static final int CARDID = AttributesEnum.CardId.index();
    public static final int CARDREQTYPE = AttributesEnum.CardReqType.index();
    public static final int COMMENTS = AttributesEnum.Comments.index();
    public static final int CREDITUNIONID = AttributesEnum.CreditUnionId.index();
    public static final int MEMBERID = AttributesEnum.MemberId.index();
    public static final int STATUS = AttributesEnum.Status.index();
    public static final int UPDATEDBY = AttributesEnum.UpdatedBy.index();
    public static final int UPDATEDDATE = AttributesEnum.UpdatedDate.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LincuMemberCardAuditVORowImpl() {
    }

    /**
     * Gets LincuMemberCardAuditEO entity object.
     * @return the LincuMemberCardAuditEO
     */
    public EntityImpl getLincuMemberCardAuditEO() {
        return (EntityImpl) getEntity(ENTITY_LINCUMEMBERCARDAUDITEO);
    }

    /**
     * Gets the attribute value for CARD_AUDIT_ID using the alias name CardAuditId.
     * @return the CARD_AUDIT_ID
     */
    public BigDecimal getCardAuditId() {
        return (BigDecimal) getAttributeInternal(CARDAUDITID);
    }

    /**
     * Sets <code>value</code> as attribute value for CARD_AUDIT_ID using the alias name CardAuditId.
     * @param value value to set the CARD_AUDIT_ID
     */
    public void setCardAuditId(BigDecimal value) {
        setAttributeInternal(CARDAUDITID, value);
    }

    /**
     * Gets the attribute value for CARD_ID using the alias name CardId.
     * @return the CARD_ID
     */
    public BigDecimal getCardId() {
        return (BigDecimal) getAttributeInternal(CARDID);
    }

    /**
     * Sets <code>value</code> as attribute value for CARD_ID using the alias name CardId.
     * @param value value to set the CARD_ID
     */
    public void setCardId(BigDecimal value) {
        setAttributeInternal(CARDID, value);
    }

    /**
     * Gets the attribute value for CARD_REQ_TYPE using the alias name CardReqType.
     * @return the CARD_REQ_TYPE
     */
    public String getCardReqType() {
        return (String) getAttributeInternal(CARDREQTYPE);
    }

    /**
     * Sets <code>value</code> as attribute value for CARD_REQ_TYPE using the alias name CardReqType.
     * @param value value to set the CARD_REQ_TYPE
     */
    public void setCardReqType(String value) {
        setAttributeInternal(CARDREQTYPE, value);
    }

    /**
     * Gets the attribute value for COMMENTS using the alias name Comments.
     * @return the COMMENTS
     */
    public String getComments() {
        return (String) getAttributeInternal(COMMENTS);
    }

    /**
     * Sets <code>value</code> as attribute value for COMMENTS using the alias name Comments.
     * @param value value to set the COMMENTS
     */
    public void setComments(String value) {
        setAttributeInternal(COMMENTS, value);
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
     * Gets the attribute value for MEMBER_ID using the alias name MemberId.
     * @return the MEMBER_ID
     */
    public String getMemberId() {
        return (String) getAttributeInternal(MEMBERID);
    }

    /**
     * Sets <code>value</code> as attribute value for MEMBER_ID using the alias name MemberId.
     * @param value value to set the MEMBER_ID
     */
    public void setMemberId(String value) {
        setAttributeInternal(MEMBERID, value);
    }

    /**
     * Gets the attribute value for STATUS using the alias name Status.
     * @return the STATUS
     */
    public String getStatus() {
        return (String) getAttributeInternal(STATUS);
    }

    /**
     * Sets <code>value</code> as attribute value for STATUS using the alias name Status.
     * @param value value to set the STATUS
     */
    public void setStatus(String value) {
        setAttributeInternal(STATUS, value);
    }

    /**
     * Gets the attribute value for UPDATED_BY using the alias name UpdatedBy.
     * @return the UPDATED_BY
     */
    public String getUpdatedBy() {
        return (String) getAttributeInternal(UPDATEDBY);
    }

    /**
     * Sets <code>value</code> as attribute value for UPDATED_BY using the alias name UpdatedBy.
     * @param value value to set the UPDATED_BY
     */
    public void setUpdatedBy(String value) {
        setAttributeInternal(UPDATEDBY, value);
    }

    /**
     * Gets the attribute value for UPDATED_DATE using the alias name UpdatedDate.
     * @return the UPDATED_DATE
     */
    public Timestamp getUpdatedDate() {
        return (Timestamp) getAttributeInternal(UPDATEDDATE);
    }

    /**
     * Sets <code>value</code> as attribute value for UPDATED_DATE using the alias name UpdatedDate.
     * @param value value to set the UPDATED_DATE
     */
    public void setUpdatedDate(Timestamp value) {
        setAttributeInternal(UPDATEDDATE, value);
    }
}

