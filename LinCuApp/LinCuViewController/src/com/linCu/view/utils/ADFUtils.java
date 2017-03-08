package com.linCu.view.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCDataControl;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCParameter;
import oracle.adf.share.ADFContext;
import oracle.adf.share.logging.ADFLogger;
import oracle.adf.share.security.SecurityContext;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.context.AdfFacesContext;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;
import oracle.binding.OperationBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.AttributeDef;
import oracle.jbo.Key;
import oracle.jbo.Row;
import oracle.jbo.VariableValueManager;
import oracle.jbo.ViewObject;
import oracle.jbo.uicli.binding.JUCtrlValueBinding;
import oracle.jbo.uicli.binding.JUFormBinding;

import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;
/**
 * A series of convenience functions for dealing with ADF Bindings.
 * Note: Updated for JDeveloper 11
 *
 * @author Duncan Mills
 * @author Steve Muench
 *
 * $Id: ADFUtils.java 2513 2007-09-20 20:39:13Z ralsmith $.
 */
public final class ADFUtils {

  public static final ADFLogger LOGGER = ADFLogger.createADFLogger(ADFUtils.class);
  
  
  private ADFUtils() {
    
  }

  /**
   * Get application module for an application module data control by userId.
   * @param name application module data control userId
   * @return ApplicationModule
   */
  public static ApplicationModule getApplicationModuleForDataControl(String name) {
    return (ApplicationModule) JSFUtils.resolveExpression("#{data." + name + ".dataProvider}");
  }

  /**
   * Get application module for an application module data control by userId.
   * @param name application module data control userId
   * @return ApplicationModule
   */
  public static Object getDataControl(String name) {
    return JSFUtils.resolveExpression("#{data." + name + "}");
  }

  /**
   * A convenience method for getting the value of a bound attribute in the
   * current page context programatically.
   * @param attributeName of the bound value in the pageDef
   * @return value of the attribute
   */
  public static Object getBoundAttributeValue(String attributeName) {
    return findControlBinding(attributeName).getInputValue();
  }

  /**
   * A convenience method for setting the value of a bound attribute in the
   * context of the current page.
   * @param attributeName of the bound value in the pageDef
   * @param value to set
   */
  public static void setBoundAttributeValue(String attributeName, Object value) {
    findControlBinding(attributeName).setInputValue(value);
  }

  /**
   * Returns the evaluated value of a pageDef parameter.
   * @param pageDefName reference to the page definition file of the page with the parameter
   * @param parameterName userId of the pagedef parameter
   * @return evaluated value of the parameter as a String
   */
  public static Object getPageDefParameterValue(String pageDefName, String parameterName) {
    BindingContainer bindings = findBindingContainer(pageDefName);
    DCParameter param = ((DCBindingContainer) bindings).findParameter(parameterName);
    return param.getValue();
  }

  /**
   * Convenience method to find a DCControlBinding as an AttributeBinding
   * to get able to then call getInputValue() or setInputValue() on it.
   * @param bindingContainer binding container
   * @param attributeName userId of the attribute binding.
   * @return the control value binding with the userId passed in.
   *
   */
  public static AttributeBinding findControlBinding(BindingContainer bindingContainer, String attributeName) {
    if(attributeName != null) {
      if(bindingContainer != null) {
        ControlBinding ctrlBinding = bindingContainer.getControlBinding(attributeName);
        if(ctrlBinding instanceof AttributeBinding) {
          return (AttributeBinding) ctrlBinding;
        }
      }
    }
    return null;
  }

  /**
   * Convenience method to find a DCControlBinding as a JUCtrlValueBinding
   * to get able to then call getInputValue() or setInputValue() on it.
   * @param attributeName userId of the attribute binding.
   * @return the control value binding with the userId passed in.
   *
   */
  public static AttributeBinding findControlBinding(String attributeName) {
    return findControlBinding(getBindingContainer(), attributeName);
  }

  /**
   * Return the current page's binding container.
   * @return the current page's binding container
   */
  public static BindingContainer getBindingContainer() {
    return (BindingContainer) JSFUtils.resolveExpression("#{bindings}");
    //this does not work with listeners::: return BindingContext.getCurrent().getCurrentBindingsEntry();
  }

  /**
   * Return the Binding Container as a DCBindingContainer.
   * @return current binding container as a DCBindingContainer
   */
  public static DCBindingContainer getDCBindingContainer() {
    return (DCBindingContainer) getBindingContainer();
  }

  /**
   * Get List of ADF Faces SelectItem for an iterator binding.
   *
   * Uses the value of the 'valueAttrName' attribute as the key for
   * the SelectItem key.
   *
   * @param iteratorName ADF iterator binding userId
   * @param valueAttrName userId of the value attribute to use
   * @param displayAttrName userId of the attribute from iterator rows to display
   * @return ADF Faces SelectItem for an iterator binding
   */
  public static List<SelectItem> selectItemsForIterator(String iteratorName, String valueAttrName,
    String displayAttrName) {
    return selectItemsForIterator(findIterator(iteratorName), valueAttrName, displayAttrName);
  }

  /**
   * Get List of ADF Faces SelectItem for an iterator binding with description.
   *
   * Uses the value of the 'valueAttrName' attribute as the key for
   * the SelectItem key.
   *
   * @param iteratorName ADF iterator binding userId
   * @param valueAttrName userId of the value attribute to use
   * @param displayAttrName userId of the attribute from iterator rows to display
   * @param descriptionAttrName userId of the attribute to use for description
   * @return ADF Faces SelectItem for an iterator binding with description
   */
  public static List<SelectItem> selectItemsForIterator(String iteratorName, String valueAttrName,
    String displayAttrName, String descriptionAttrName) {
    return selectItemsForIterator(findIterator(iteratorName), valueAttrName, displayAttrName, descriptionAttrName);
  }

  /**
   * Get List of attribute values for an iterator.
   * @param iteratorName ADF iterator binding userId
   * @param valueAttrName value attribute to use
   * @return List of attribute values for an iterator
   */
  public static List attributeListForIterator(String iteratorName, String valueAttrName) {
    return attributeListForIterator(findIterator(iteratorName), valueAttrName);
  }

  /**
   * Get List of Key objects for rows in an iterator.
   * @param iteratorName iterabot binding userId
   * @return List of Key objects for rows
   */
  public static List<Key> keyListForIterator(String iteratorName) {
    return keyListForIterator(findIterator(iteratorName));
  }

  /**
   * Get List of Key objects for rows in an iterator.
   * @param iter iterator binding
   * @return List of Key objects for rows
   */
  public static List<Key> keyListForIterator(DCIteratorBinding iter) {
    List<Key> attributeList = new ArrayList<Key>();
    for(Row r : iter.getAllRowsInRange()) {
      attributeList.add(r.getKey());
    }
    return attributeList;
  }

  /**
   * Get List of Key objects for rows in an iterator using key attribute.
   * @param iteratorName iterator binding userId
   * @param keyAttrName userId of key attribute to use
   * @return List of Key objects for rows
   */
  public static List<Key> keyAttrListForIterator(String iteratorName, String keyAttrName) {
    return keyAttrListForIterator(findIterator(iteratorName), keyAttrName);
  }

  /**
   * Get List of Key objects for rows in an iterator using key attribute.
   *
   * @param iter iterator binding
   * @param keyAttrName userId of key attribute to use
   * @return List of Key objects for rows
   */
  public static List<Key> keyAttrListForIterator(DCIteratorBinding iter, String keyAttrName) {
    List<Key> attributeList = new ArrayList<Key>();
    for(Row r : iter.getAllRowsInRange()) {
      attributeList.add(new Key(new Object[] { r.getAttribute(keyAttrName) }));
    }
    return attributeList;
  }

  /**
   * Get a List of attribute values for an iterator.
   *
   * @param iter iterator binding
   * @param valueAttrName userId of value attribute to use
   * @return List of attribute values
   */
  public static List attributeListForIterator(DCIteratorBinding iter, String valueAttrName) {
    List attributeList = new ArrayList();
    for(Row r : iter.getAllRowsInRange()) {
      attributeList.add(r.getAttribute(valueAttrName));
    }
    return attributeList;
  }

  /**
   * Find an iterator binding in the current binding container by userId.
   *
   * @param name iterator binding userId
   * @return iterator binding
   */
  public static DCIteratorBinding findIterator(String name) {
    DCIteratorBinding iter = getDCBindingContainer().findIteratorBinding(name);
    if(iter == null) {
      throw new RuntimeException("Iterator '" + name + "' not found");
    }
    return iter;
  }

  /**
   * @param bindingContainer
   * @param iterator
   * @return
   */
  public static DCIteratorBinding findIterator(String bindingContainer, String iterator) {
    DCBindingContainer bindings = (DCBindingContainer) JSFUtils.resolveExpression("#{" + bindingContainer + "}");
    if(bindings == null) {
      throw new RuntimeException("Binding container '" + bindingContainer + "' not found");
    }
    DCIteratorBinding iter = bindings.findIteratorBinding(iterator);
    if(iter == null) {
      throw new RuntimeException("Iterator '" + iterator + "' not found");
    }
    return iter;
  }

  /**
   * @param name
   * @return
   */
  public static JUCtrlValueBinding findCtrlBinding(String name) {
    JUCtrlValueBinding rowBinding = (JUCtrlValueBinding) getDCBindingContainer().findCtrlBinding(name);
    if(rowBinding == null) {
      throw new RuntimeException("CtrlBinding " + name + "' not found");
    }
    return rowBinding;
  }

  /**
   * Find an operation binding in the current binding container by userId.
   *
   * @param name operation binding userId
   * @return operation binding
   */
  public static OperationBinding findOperation(String name) {
    OperationBinding op = getDCBindingContainer().getOperationBinding(name);
    if(op == null) {
      throw new RuntimeException("Operation '" + name + "' not found");
    }
    return op;
  }

  /**
   * Find an operation binding in the supplied binding container by userId.
   *
   * @param name operation binding userId
   * @return operation binding
   */
  public static OperationBinding findOperation(String name, BindingContainer container) {
    OperationBinding op = container.getOperationBinding(name);
    if(op == null) {
      throw new RuntimeException("Operation '" + name + "' not found");
    }
    return op;
  }

  /**
   * Find an operation binding in the current binding container by userId.
   *
   * @param bindingContianer binding container userId
   * @param opName operation binding userId
   * @return operation binding
   */
  public static OperationBinding findOperation(String bindingContianer, String opName) {
    DCBindingContainer bindings = (DCBindingContainer) JSFUtils.resolveExpression("#{" + bindingContianer + "}");
    if(bindings == null) {
      throw new RuntimeException("Binding container '" + bindingContianer + "' not found");
    }
    OperationBinding op = bindings.getOperationBinding(opName);
    if(op == null) {
      throw new RuntimeException("Operation '" + opName + "' not found");
    }
    return op;
  }

  /**
   * Get List of ADF Faces SelectItem for an iterator binding with description.
   *
   * Uses the value of the 'valueAttrName' attribute as the key for
   * the SelectItem key.
   *
   * @param iter ADF iterator binding
   * @param valueAttrName userId of value attribute to use for key
   * @param displayAttrName userId of the attribute from iterator rows to display
   * @param descriptionAttrName userId of the attribute for description
   * @return ADF Faces SelectItem for an iterator binding with description
   */
  public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, String valueAttrName,
    String displayAttrName, String descriptionAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for(Row r : iter.getAllRowsInRange()) {
      selectItems.add(new SelectItem(r.getAttribute(valueAttrName), (String) r.getAttribute(displayAttrName),
            (String) r.getAttribute(descriptionAttrName)));
    }
    return selectItems;
  }

  /**
   * Get List of ADF Faces SelectItem for an iterator binding.
   *
   * Uses the value of the 'valueAttrName' attribute as the key for
   * the SelectItem key.
   *
   * @param iter ADF iterator binding
   * @param valueAttrName userId of value attribute to use for key
   * @param displayAttrName userId of the attribute from iterator rows to display
   * @return ADF Faces SelectItem for an iterator binding
   */
  public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, String valueAttrName,
    String displayAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for(Row r : iter.getAllRowsInRange()) {
      selectItems.add(new SelectItem(r.getAttribute(valueAttrName), (String) r.getAttribute(displayAttrName)));
    }
    return selectItems;
  }

  /**
   * Get List of ADF Faces SelectItem for an iterator binding.
   *
   * Uses the rowKey of each row as the SelectItem key.
   *
   * @param iteratorName ADF iterator binding userId
   * @param displayAttrName userId of the attribute from iterator rows to display
   * @return ADF Faces SelectItem for an iterator binding
   */
  public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, String displayAttrName) {
    return selectItemsByKeyForIterator(findIterator(iteratorName), displayAttrName);
  }

  /**
   * Get List of ADF Faces SelectItem for an iterator binding with discription.
   *
   * Uses the rowKey of each row as the SelectItem key.
   *
   * @param iteratorName ADF iterator binding userId
   * @param displayAttrName userId of the attribute from iterator rows to display
   * @param descriptionAttrName userId of the attribute for description
   * @return ADF Faces SelectItem for an iterator binding with discription
   */
  public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, String displayAttrName,
    String descriptionAttrName) {
    return selectItemsByKeyForIterator(findIterator(iteratorName), displayAttrName, descriptionAttrName);
  }

  /**
   * Get List of ADF Faces SelectItem for an iterator binding with discription.
   *
   * Uses the rowKey of each row as the SelectItem key.
   *
   * @param iter ADF iterator binding
   * @param displayAttrName userId of the attribute from iterator rows to display
   * @param descriptionAttrName userId of the attribute for description
   * @return ADF Faces SelectItem for an iterator binding with discription
   */
  public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, String displayAttrName,
    String descriptionAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for(Row r : iter.getAllRowsInRange()) {
      selectItems.add(new SelectItem(r.getKey(), (String) r.getAttribute(displayAttrName),
            (String) r.getAttribute(descriptionAttrName)));
    }
    return selectItems;
  }

  /**
   * Get List of ADF Faces SelectItem for an iterator binding.
   *
   * Uses the rowKey of each row as the SelectItem key.
   *
   * @param iter ADF iterator binding
   * @param displayAttrName userId of the attribute from iterator rows to display
   * @return List of ADF Faces SelectItem for an iterator binding
   */
  public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, String displayAttrName) {
    List<SelectItem> selectItems = new ArrayList<SelectItem>();
    for(Row r : iter.getAllRowsInRange()) {
      selectItems.add(new SelectItem(r.getKey(), (String) r.getAttribute(displayAttrName)));
    }
    return selectItems;
  }

  /**
   * Find the BindingContainer for a page definition by userId.
   *
   * Typically used to refer eagerly to page definition parameters. It is
   * not best practice to reference or set bindings in binding containers
   * that are not the one for the current page.
   *
   * @param pageDefName userId of the page defintion XML file to use
   * @return BindingContainer ref for the named definition
   */
  public static BindingContainer findBindingContainer(String pageDefName) {
    BindingContext bctx = getDCBindingContainer().getBindingContext();
    BindingContainer foundContainer = bctx.findBindingContainer(pageDefName);
    return foundContainer;
  }

  /**
   * @param opList
   */
  public static void printOperationBindingExceptions(List opList) {
    if(opList != null && !opList.isEmpty()) {
      for(Object error : opList) {
        if(error instanceof Throwable) {
          LOGGER.severe((Throwable) error);
        } else {
          LOGGER.severe(error.toString());
        }
      }
    }
  }

  public static Object executeOperationBinding(String methodAction) {
    Map params = null; //remove the ambiguity by setting this null param
    return executeOperationBinding(methodAction, params);
  }

  public static Object executeOperationBinding(String methodAction, Map param) {
    OperationBinding ob = ADFUtils.findOperation(methodAction);

    if(param != null) {
      Map paramOps = ob.getParamsMap();
      paramOps.putAll(param);
    }
    Object result = executeOperation(methodAction, ob);
    return result;
  }

  public static Object executeOperationBinding(String methodAction, String pageDefName) {
    BindingContainer bc = findBindingContainer(pageDefName);
    OperationBinding ob = ADFUtils.findOperation(methodAction, bc);
    Object result = executeOperation(methodAction, ob);
    return result;
  }

  private static Object executeOperation(String methodAction, OperationBinding ob) {
    Object result = ob.execute();
    if(!ob.getErrors().isEmpty()) {
      ADFUtils.printOperationBindingExceptions(ob.getErrors());
      
      //Do not throw system exception , it will not propogate to front end.
      //throw new SystemException("failed to execute operation '" + methodAction + "'");
    }
    return result;
  }

  public static boolean hasErrors(OperationBinding ob) {
    return ob != null && ob.getErrors() != null && ob.getErrors().size() > 0;
  }

  public static OperationBinding executeOperationBindingWithoutException(String methodAction, Map param) {
    OperationBinding ob = ADFUtils.findOperation(methodAction);

    if(param != null) {
      Map paramOps = ob.getParamsMap();
      paramOps.putAll(param);
    }

    ob.execute();
    return ob;
  }

  public static void addScript(String js) {
    ExtendedRenderKitService erks =
      Service.getRenderKitService(JSFUtils.getFacesContext(), ExtendedRenderKitService.class);
    erks.addScript(JSFUtils.getFacesContext(), js);
  }
  
  public static void popupWindow(String url) {
    addScript("window.open('" + url + "','_self');");    
  }

  /**
   * @param dataControlName
   * @return Will return the DCDataControl.
   */
  public static DCDataControl getDataControlBinding(String dataControlName) {
    DCDataControl dataControl = null;
    DCBindingContainer bc = getDCBindingContainer();
    if(bc != null) {
      dataControl = bc.findDataControl(dataControlName);
    }
    return dataControl;
  }

  public static SecurityContext getSecurityContext() {
    return ADFContext.getCurrent().getSecurityContext();
  }
  public static String getUsername() {
    SecurityContext ctx = getSecurityContext();
    return (ctx.getUserPrincipal() == null) ? null : ctx.getUserPrincipal().getName();
  }

  public static String[] getUserRoles() {
    SecurityContext ctx = getSecurityContext();
    return ctx.getUserRoles();
  }

  public static boolean isUserInRole(String roleName) {
    return getSecurityContext().isUserInRole(roleName);
  }
  public static boolean isAuthenticated() {
    return getSecurityContext().isAuthenticated();
  }

  public static JUFormBinding getPageDefBinding(String regionDefName) {
    return (JUFormBinding) getDCBindingContainer().findExecutableBinding(regionDefName);
  }

  public static void resetValueInputItems(AdfFacesContext adfFacesContext, UIComponent component) {
    List<UIComponent> items = component.getChildren();
    for(UIComponent item : items) {
      resetValueInputItems(adfFacesContext, item);
      if(item instanceof RichInputText) {
        RichInputText input = (RichInputText) item;
        if(!input.isDisabled()) {
          input.resetValue();
          adfFacesContext.addPartialTarget(input);
        }
      } else if(item instanceof RichInputDate) {
        RichInputDate input = (RichInputDate) item;
        if(!input.isDisabled()) {
          input.resetValue();
          adfFacesContext.addPartialTarget(input);
        }
      }
    }
  }

  public static boolean hasAttribute(Row row, String attributeName) {
    return row.getAttributeIndexOf(attributeName) >= 0;
  }

  public static void setBindVariable(String voName, String bindName, Object value) {
    ViewObject view = findIterator(voName).getViewObject();
    VariableValueManager vm = view.ensureVariableManager();
    vm.addVariable(bindName);
    vm.setVariableValue(bindName, value);
  }

  public static ViewObject getChildViewObjectFromParent(ViewObject parentVO, String childAccessorName) {
    AttributeDef attrDef = parentVO.findAttributeDef(childAccessorName);
    if(attrDef == null) {
      throw new RuntimeException("Attribute Definition '" + childAccessorName + "' not found on parent view object");
    }
    return attrDef.getAccessorVO(parentVO);
  }  

  public static void setBindVariables(String voName, Map<String, Object> variables) {
    ViewObject view = findIterator(voName).getViewObject();
    setBindVariables(view, variables);
  }

  public static void setBindVariables(ViewObject view, Map<String, Object> variables) {
    VariableValueManager vm = view.ensureVariableManager();
    for(String key : variables.keySet()) {
      vm.setVariableValue(key, variables.get(key));
    }
  }

  public static DCIteratorBinding getIteratorBinding(String containerName, String iteratorName) {
    BindingContext bctx = BindingContext.getCurrent();
    DCBindingContainer bindings = bctx.findBindingContainer(containerName);
    return bindings.findIteratorBinding(iteratorName);
  }
  
  
  public static boolean hasExceptionBeenReportedToContainer() {
    BindingContext bindingContext = BindingContext.getCurrent();
    List list = bindingContext!=null && bindingContext.getCurrentBindingsEntry()!=null?((DCBindingContainer)bindingContext.getCurrentBindingsEntry()).getExceptionsList():null;    return list!=null&& list.size()>0;
  }

  
  /* 
   * Method to set PageflowScope variable
   */
  public static void setPageFlowScopeValue(String name, Object value) {
      ADFContext adfCtx = ADFContext.getCurrent();
      Map pageFlowScope = adfCtx.getPageFlowScope();
      pageFlowScope.put(name, value);
  }

  /* 
   * Method to get PageflowScope variable
   */
  public static Object getPageFlowScopeValue(String name) {
      ADFContext adfCtx = ADFContext.getCurrent();
      Map pageFlowScope = adfCtx.getPageFlowScope();
      return pageFlowScope.get(name);
  }
  
  public static PhaseId getCurrentPhaseID() {
    FacesContext fctx = FacesContext.getCurrentInstance();  
    PhaseId currentPhase=fctx.getCurrentPhaseId();
    return currentPhase;
  }
  /* 
   * Method to set ViewScope variable
   */
  public static void setViewScopeValue(String name, Object value) {
      ADFContext adfCtx = ADFContext.getCurrent();
      Map viewScope = adfCtx.getViewScope();
      viewScope.put(name, value);
  }

  /* 
   * Method to get ViewScope variable
   */
  public static Object getViewScopeValue(String name) {
      ADFContext adfCtx = ADFContext.getCurrent();
      Map viewScope = adfCtx.getViewScope();
      return viewScope.get(name);
  }
  
  }