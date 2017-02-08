

/* First created by JCasGen Wed Jan 04 15:51:14 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Wed Jan 04 15:51:14 CET 2017
 * XML source: C:/Users/Nivelin Stoyanov/workspace/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/DateFormat.xml
 * @generated */
public class DateFormat extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DateFormat.class);
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
  protected DateFormat() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public DateFormat(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public DateFormat(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public DateFormat(JCas jcas, int begin, int end) {
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
  //* Feature: currentDate

  /** getter for currentDate - gets 
   * @generated
   * @return value of the feature 
   */
  public String getCurrentDate() {
    if (DateFormat_Type.featOkTst && ((DateFormat_Type)jcasType).casFeat_currentDate == null)
      jcasType.jcas.throwFeatMissing("currentDate", "de.unidue.langtech.teaching.pp.type.DateFormat");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DateFormat_Type)jcasType).casFeatCode_currentDate);}
    
  /** setter for currentDate - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCurrentDate(String v) {
    if (DateFormat_Type.featOkTst && ((DateFormat_Type)jcasType).casFeat_currentDate == null)
      jcasType.jcas.throwFeatMissing("currentDate", "de.unidue.langtech.teaching.pp.type.DateFormat");
    jcasType.ll_cas.ll_setStringValue(addr, ((DateFormat_Type)jcasType).casFeatCode_currentDate, v);}    
  }

    