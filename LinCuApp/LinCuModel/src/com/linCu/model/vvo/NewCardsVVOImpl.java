package com.linCu.model.vvo;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Tue May 23 00:11:02 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class NewCardsVVOImpl extends ViewObjectImpl {
    /**
     * This is the default constructor (do not remove).
     */
    public NewCardsVVOImpl() {
    }

    /**
     * Returns the variable value for bindCreditUnionId.
     * @return variable value for bindCreditUnionId
     */
    public String getbindCreditUnionId() {
        return (String) ensureVariableManager().getVariableValue("bindCreditUnionId");
    }

    /**
     * Sets <code>value</code> for variable bindCreditUnionId.
     * @param value value to bind as bindCreditUnionId
     */
    public void setbindCreditUnionId(String value) {
        ensureVariableManager().setVariableValue("bindCreditUnionId", value);
    }

    /**
     * Returns the variable value for bindBranchId.
     * @return variable value for bindBranchId
     */
    public String getbindBranchId() {
        return (String) ensureVariableManager().getVariableValue("bindBranchId");
    }

    /**
     * Sets <code>value</code> for variable bindBranchId.
     * @param value value to bind as bindBranchId
     */
    public void setbindBranchId(String value) {
        ensureVariableManager().setVariableValue("bindBranchId", value);
    }
}

