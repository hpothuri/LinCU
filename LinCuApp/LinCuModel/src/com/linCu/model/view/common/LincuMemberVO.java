package com.linCu.model.view.common;

import oracle.jbo.ViewObject;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 14 22:41:11 IST 2017
// ---------------------------------------------------------------------
public interface LincuMemberVO extends ViewObject {
    void createMember(String user);

    void updateMember(String user);

    void copyPermanantAddress(Boolean copy);

    void updateMemberAndSubmit(String user);
}

