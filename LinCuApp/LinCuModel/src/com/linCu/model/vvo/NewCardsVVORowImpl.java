package com.linCu.model.vvo;

import java.math.BigDecimal;

import oracle.jbo.server.ViewRowImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue May 23 00:11:01 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class NewCardsVVORowImpl extends ViewRowImpl {
    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CardId,
        MemberId,
        CreditUnionId,
        Active;
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
    public static final int ACTIVE = AttributesEnum.Active.index();

    /**
     * This is the default constructor (do not remove).
     */
    public NewCardsVVORowImpl() {
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
     * Gets the attribute value for the calculated attribute Active.
     * @return the Active
     */
    public String getActive() {
        return (String) getAttributeInternal(ACTIVE);
    }
}

