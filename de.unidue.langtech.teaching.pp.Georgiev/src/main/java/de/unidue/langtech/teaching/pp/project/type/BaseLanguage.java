

/* First created by JCasGen Tue Jan 03 13:00:32 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.DoubleArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.jcas.cas.IntegerArray;


/** 
 * Updated by JCasGen Fri Jan 20 01:52:18 CET 2017
 * XML source: C:/Users/Nivelin Stoyanov/workspace/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/BaseLanguage.xml
 * @generated */
public class BaseLanguage extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(BaseLanguage.class);
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
  protected BaseLanguage() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public BaseLanguage(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public BaseLanguage(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public BaseLanguage(JCas jcas, int begin, int end) {
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
  //* Feature: integerVector

  /** getter for integerVector - gets 
   * @generated
   * @return value of the feature 
   */
  public IntegerArray getIntegerVector() {
    if (BaseLanguage_Type.featOkTst && ((BaseLanguage_Type)jcasType).casFeat_integerVector == null)
      jcasType.jcas.throwFeatMissing("integerVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    return (IntegerArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_integerVector)));}
    
  /** setter for integerVector - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setIntegerVector(IntegerArray v) {
    if (BaseLanguage_Type.featOkTst && ((BaseLanguage_Type)jcasType).casFeat_integerVector == null)
      jcasType.jcas.throwFeatMissing("integerVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    jcasType.ll_cas.ll_setRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_integerVector, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for integerVector - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public int getIntegerVector(int i) {
    if (BaseLanguage_Type.featOkTst && ((BaseLanguage_Type)jcasType).casFeat_integerVector == null)
      jcasType.jcas.throwFeatMissing("integerVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_integerVector), i);
    return jcasType.ll_cas.ll_getIntArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_integerVector), i);}

  /** indexed setter for integerVector - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setIntegerVector(int i, int v) { 
    if (BaseLanguage_Type.featOkTst && ((BaseLanguage_Type)jcasType).casFeat_integerVector == null)
      jcasType.jcas.throwFeatMissing("integerVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_integerVector), i);
    jcasType.ll_cas.ll_setIntArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_integerVector), i, v);}
   
    
  //*--------------*
  //* Feature: doubleVector

  /** getter for doubleVector - gets 
   * @generated
   * @return value of the feature 
   */
  public DoubleArray getDoubleVector() {
    if (BaseLanguage_Type.featOkTst && ((BaseLanguage_Type)jcasType).casFeat_doubleVector == null)
      jcasType.jcas.throwFeatMissing("doubleVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    return (DoubleArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_doubleVector)));}
    
  /** setter for doubleVector - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setDoubleVector(DoubleArray v) {
    if (BaseLanguage_Type.featOkTst && ((BaseLanguage_Type)jcasType).casFeat_doubleVector == null)
      jcasType.jcas.throwFeatMissing("doubleVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    jcasType.ll_cas.ll_setRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_doubleVector, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for doubleVector - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public double getDoubleVector(int i) {
    if (BaseLanguage_Type.featOkTst && ((BaseLanguage_Type)jcasType).casFeat_doubleVector == null)
      jcasType.jcas.throwFeatMissing("doubleVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_doubleVector), i);
    return jcasType.ll_cas.ll_getDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_doubleVector), i);}

  /** indexed setter for doubleVector - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setDoubleVector(int i, double v) { 
    if (BaseLanguage_Type.featOkTst && ((BaseLanguage_Type)jcasType).casFeat_doubleVector == null)
      jcasType.jcas.throwFeatMissing("doubleVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_doubleVector), i);
    jcasType.ll_cas.ll_setDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((BaseLanguage_Type)jcasType).casFeatCode_doubleVector), i, v);}
  }

    