

/* First created by JCasGen Tue Oct 15 15:45:50 CEST 2013 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Fri Jan 20 01:59:47 CET 2017
 * XML source: C:/Users/Nivelin Stoyanov/workspace/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/GoldLanguage.xml
 * @generated */
public class GoldLanguage extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(GoldLanguage.class);
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
  protected GoldLanguage() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public GoldLanguage(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public GoldLanguage(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public GoldLanguage(JCas jcas, int begin, int end) {
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
    if (GoldLanguage_Type.featOkTst && ((GoldLanguage_Type)jcasType).casFeat_Language == null)
      jcasType.jcas.throwFeatMissing("Language", "de.unidue.langtech.teaching.pp.project.type.GoldLanguage");
    return jcasType.ll_cas.ll_getStringValue(addr, ((GoldLanguage_Type)jcasType).casFeatCode_Language);}
    
  /** setter for Language - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setLanguage(String v) {
    if (GoldLanguage_Type.featOkTst && ((GoldLanguage_Type)jcasType).casFeat_Language == null)
      jcasType.jcas.throwFeatMissing("Language", "de.unidue.langtech.teaching.pp.project.type.GoldLanguage");
    jcasType.ll_cas.ll_setStringValue(addr, ((GoldLanguage_Type)jcasType).casFeatCode_Language, v);}    
  }

    