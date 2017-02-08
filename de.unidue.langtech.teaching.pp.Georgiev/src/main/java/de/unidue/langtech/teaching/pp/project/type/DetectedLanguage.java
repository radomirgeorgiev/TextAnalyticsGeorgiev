

/* First created by JCasGen Tue Oct 15 15:43:58 CEST 2013 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.cas.StringList;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sun Feb 05 23:21:33 CET 2017
 * XML source: C:/Program Files/eclipse/workspace/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/DetectedLanguage.xml
 * @generated */
public class DetectedLanguage extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(DetectedLanguage.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected DetectedLanguage() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public DetectedLanguage(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public DetectedLanguage(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public DetectedLanguage(JCas jcas, int begin, int end) {
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
  //* Feature: Language

  /** getter for Language - gets 
   * @generated
   * @return value of the feature 
   */
  public String getLanguage() {
    if (DetectedLanguage_Type.featOkTst && ((DetectedLanguage_Type)jcasType).casFeat_Language == null)
      jcasType.jcas.throwFeatMissing("Language", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    return jcasType.ll_cas.ll_getStringValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_Language);}
    
  /** setter for Language - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLanguage(String v) {
    if (DetectedLanguage_Type.featOkTst && ((DetectedLanguage_Type)jcasType).casFeat_Language == null)
      jcasType.jcas.throwFeatMissing("Language", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    jcasType.ll_cas.ll_setStringValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_Language, v);}    
   
    
  //*--------------*
  //* Feature: Suggestion

  /** getter for Suggestion - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getSuggestion() {
    if (DetectedLanguage_Type.featOkTst && ((DetectedLanguage_Type)jcasType).casFeat_Suggestion == null)
      jcasType.jcas.throwFeatMissing("Suggestion", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_Suggestion)));}
    
  /** setter for Suggestion - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setSuggestion(StringArray v) {
    if (DetectedLanguage_Type.featOkTst && ((DetectedLanguage_Type)jcasType).casFeat_Suggestion == null)
      jcasType.jcas.throwFeatMissing("Suggestion", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    jcasType.ll_cas.ll_setRefValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_Suggestion, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for Suggestion - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getSuggestion(int i) {
    if (DetectedLanguage_Type.featOkTst && ((DetectedLanguage_Type)jcasType).casFeat_Suggestion == null)
      jcasType.jcas.throwFeatMissing("Suggestion", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_Suggestion), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_Suggestion), i);}

  /** indexed setter for Suggestion - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setSuggestion(int i, String v) { 
    if (DetectedLanguage_Type.featOkTst && ((DetectedLanguage_Type)jcasType).casFeat_Suggestion == null)
      jcasType.jcas.throwFeatMissing("Suggestion", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_Suggestion), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((DetectedLanguage_Type)jcasType).casFeatCode_Suggestion), i, v);}
  }

    