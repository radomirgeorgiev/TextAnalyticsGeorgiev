

/* First created by JCasGen Fri Jan 20 01:28:21 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Jan 26 15:30:53 CET 2017
 * XML source: C:/Users/Nivelin Stoyanov/workspace/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/RawTextData.xml
 * @generated */
public class RawTextData extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RawTextData.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated
   * @return index of the type  
   */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected RawTextData() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RawTextData(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RawTextData(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RawTextData(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** 
   * <!-- begin-user-doc -->
   * Write your own initialization here
   * <!-- end-user-doc -->
   *
   * @generated modifiable 
   */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: documentRawText

  /** getter for documentRawText - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDocumentRawText() {
    if (RawTextData_Type.featOkTst && ((RawTextData_Type)jcasType).casFeat_documentRawText == null)
      jcasType.jcas.throwFeatMissing("documentRawText", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RawTextData_Type)jcasType).casFeatCode_documentRawText);}
    
  /** setter for documentRawText - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocumentRawText(String v) {
    if (RawTextData_Type.featOkTst && ((RawTextData_Type)jcasType).casFeat_documentRawText == null)
      jcasType.jcas.throwFeatMissing("documentRawText", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    jcasType.ll_cas.ll_setStringValue(addr, ((RawTextData_Type)jcasType).casFeatCode_documentRawText, v);}    
   
    
  //*--------------*
  //* Feature: documentCreationDate

  /** getter for documentCreationDate - gets 
   * @generated
   * @return value of the feature 
   */
  public String getDocumentCreationDate() {
    if (RawTextData_Type.featOkTst && ((RawTextData_Type)jcasType).casFeat_documentCreationDate == null)
      jcasType.jcas.throwFeatMissing("documentCreationDate", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RawTextData_Type)jcasType).casFeatCode_documentCreationDate);}
    
  /** setter for documentCreationDate - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocumentCreationDate(String v) {
    if (RawTextData_Type.featOkTst && ((RawTextData_Type)jcasType).casFeat_documentCreationDate == null)
      jcasType.jcas.throwFeatMissing("documentCreationDate", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    jcasType.ll_cas.ll_setStringValue(addr, ((RawTextData_Type)jcasType).casFeatCode_documentCreationDate, v);}    
   
    
  //*--------------*
  //* Feature: documentID

  /** getter for documentID - gets 
   * @generated
   * @return value of the feature 
   */
  public int getDocumentID() {
    if (RawTextData_Type.featOkTst && ((RawTextData_Type)jcasType).casFeat_documentID == null)
      jcasType.jcas.throwFeatMissing("documentID", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    return jcasType.ll_cas.ll_getIntValue(addr, ((RawTextData_Type)jcasType).casFeatCode_documentID);}
    
  /** setter for documentID - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDocumentID(int v) {
    if (RawTextData_Type.featOkTst && ((RawTextData_Type)jcasType).casFeat_documentID == null)
      jcasType.jcas.throwFeatMissing("documentID", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    jcasType.ll_cas.ll_setIntValue(addr, ((RawTextData_Type)jcasType).casFeatCode_documentID, v);}    
  }

    