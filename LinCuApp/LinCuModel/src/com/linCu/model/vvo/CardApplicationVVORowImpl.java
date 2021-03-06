package com.linCu.model.vvo;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue May 23 00:17:34 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CardApplicationVVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CardId,
        MemberId,
        CreditUnionId,
        CardReqType,
        CardStatus,
        Active,
        CifNumber,
        TruncCredatedDate,
        TopupAmount,
        RefCardId;
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


    public static final int CARDID = AttributesEnum.CardId.index();
    public static final int MEMBERID = AttributesEnum.MemberId.index();
    public static final int CREDITUNIONID = AttributesEnum.CreditUnionId.index();
    public static final int CARDREQTYPE = AttributesEnum.CardReqType.index();
    public static final int CARDSTATUS = AttributesEnum.CardStatus.index();
    public static final int ACTIVE = AttributesEnum.Active.index();
    public static final int CIFNUMBER = AttributesEnum.CifNumber.index();
    public static final int TRUNCCREDATEDDATE = AttributesEnum.TruncCredatedDate.index();
    public static final int TOPUPAMOUNT = AttributesEnum.TopupAmount.index();
    public static final int REFCARDID = AttributesEnum.RefCardId.index();

    /**
     * This is the default constructor (do not remove).
     */
    public CardApplicationVVORowImpl() {
    }

    /**
     * Gets the attribute value for the calculated attribute CardId.
     * @return the CardId
     */
    public BigDecimal getCardId() {
        return (BigDecimal) getAttributeInternal(CARDID);
    }

    /**
     * Gets the attribute value for the calculated attribute MemberId.
     * @return the MemberId
     */
    public String getMemberId() {
        return (String) getAttributeInternal(MEMBERID);
    }

    /**
     * Gets the attribute value for the calculated attribute CreditUnionId.
     * @return the CreditUnionId
     */
    public String getCreditUnionId() {
        return (String) getAttributeInternal(CREDITUNIONID);
    }

    /**
     * Gets the attribute value for the calculated attribute CardReqType.
     * @return the CardReqType
     */
    public String getCardReqType() {
        return (String) getAttributeInternal(CARDREQTYPE);
    }

    /**
     * Gets the attribute value for the calculated attribute CardStatus.
     * @return the CardStatus
     */
    public String getCardStatus() {
        return (String) getAttributeInternal(CARDSTATUS);
    }

    /**
     * Gets the attribute value for the calculated attribute Active.
     * @return the Active
     */
    public String getActive() {
        return (String) getAttributeInternal(ACTIVE);
    }

    /**
     * Gets the attribute value for the calculated attribute CifNumber.
     * @return the CifNumber
     */
    public String getCifNumber() {
        return (String) getAttributeInternal(CIFNUMBER);
    }

    /**
     * Gets the attribute value for the calculated attribute TruncCredatedDate.
     * @return the TruncCredatedDate
     */
    public Timestamp getTruncCredatedDate() {
        return (Timestamp) getAttributeInternal(TRUNCCREDATEDDATE);
    }

    /**
     * Gets the attribute value for the calculated attribute TopupAmount.
     * @return the TopupAmount
     */
    public BigDecimal getTopupAmount() {
        return (BigDecimal) getAttributeInternal(TOPUPAMOUNT);
    }

    /**
     * Gets the attribute value for the calculated attribute RefCardId.
     * @return the RefCardId
     */
    public BigDecimal getRefCardId() {
        return (BigDecimal) getAttributeInternal(REFCARDID);
    }

}

