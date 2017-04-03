package com.linCu.view.controller;

import java.io.Serializable;

import oracle.adf.controller.TaskFlowId;
/**
 * This class manages the dynamic region on the index page
 * It provides the memory of which task flow is currently selected within the 
 * region and the methods to switch that value
 */
public class IndexDynamicRegion implements Serializable {
    @SuppressWarnings("compatibility:-5329817642937831563")
    private static final long serialVersionUID = -4633485426455218156L;
    private static final String CREDIT_UNION_TASKFLOW = "/WEB-INF/flows/Credit-Union-TF.xml#Credit-Union-TF";
    private static final String MEMBER_TASKFLOW = "/WEB-INF/flows/Member-Flow-TF.xml#Member-Flow-TF";
    private static final String USER_INFO_TASKFLOW = "/WEB-INF/flows/User-Info-TF.xml#User-Info-TF";
    private static final String CARD_REQUEST_TASKFLOW = "/WEB-INF/flows/Card-Request.xml#Card-Request";
    //private static final String USER_LOGIN = "/WEB-INF/flows/User-Info-TF.xml#User-Info-TF";
    private String taskFlowId = CREDIT_UNION_TASKFLOW;

    public IndexDynamicRegion() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        return TaskFlowId.parse(taskFlowId);
    }

    public void setDynamicTaskFlowId(String taskFlowId) {
        this.taskFlowId = taskFlowId;
    }

    public String creditUnion() {
        setDynamicTaskFlowId(CREDIT_UNION_TASKFLOW);
        return null;
    }

    public String member() {
        setDynamicTaskFlowId(MEMBER_TASKFLOW);
        return null;
    }

    public String userInfo() {
        setDynamicTaskFlowId(USER_INFO_TASKFLOW);
        return null;
    }
    
    public String cardRequest() {
        setDynamicTaskFlowId(CARD_REQUEST_TASKFLOW);
        return null;
    }
    
}
