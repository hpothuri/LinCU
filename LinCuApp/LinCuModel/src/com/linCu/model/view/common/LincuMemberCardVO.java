package com.linCu.model.view.common;

import oracle.jbo.ViewObject;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Mon Apr 03 10:30:57 IST 2017
// ---------------------------------------------------------------------
public interface LincuMemberCardVO extends ViewObject {
    void submitCard();

    void approve(String approver);

    void reject(String rejector);

}

