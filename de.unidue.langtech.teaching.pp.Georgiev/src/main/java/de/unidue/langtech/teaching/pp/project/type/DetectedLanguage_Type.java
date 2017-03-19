
/* First created by JCasGen Tue Oct 15 15:43:58 CEST 2013 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sat Mar 18 23:41:12 CET 2017
 * @generated */
public class DetectedLanguage_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DetectedLanguage.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
 
  /** @generated */
  final Feature casFeat_Language;
  /** @generated */
  final int     casFeatCode_Language;
  /** @generated */ 
  public String getLanguage(int addr) {
        if (featOkTst && casFeat_Language == null)
      jcas.throwFeatMissing("Language", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Language);
  }
  /** @generated */    
  public void setLanguage(int addr, String v) {
        if (featOkTst && casFeat_Language == null)
      jcas.throwFeatMissing("Language", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    ll_cas.ll_setStringValue(addr, casFeatCode_Language, v);}
    
  
 
  /** @generated */
  final Feature casFeat_Suggestion;
  /** @generated */
  final int     casFeatCode_Suggestion;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getSuggestion(int addr) {
        if (featOkTst && casFeat_Suggestion == null)
      jcas.throwFeatMissing("Suggestion", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    return ll_cas.ll_getRefValue(addr, casFeatCode_Suggestion);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setSuggestion(int addr, int v) {
        if (featOkTst && casFeat_Suggestion == null)
      jcas.throwFeatMissing("Suggestion", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    ll_cas.ll_setRefValue(addr, casFeatCode_Suggestion, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getSuggestion(int addr, int i) {
        if (featOkTst && casFeat_Suggestion == null)
      jcas.throwFeatMissing("Suggestion", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_Suggestion), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_Suggestion), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_Suggestion), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setSuggestion(int addr, int i, String v) {
        if (featOkTst && casFeat_Suggestion == null)
      jcas.throwFeatMissing("Suggestion", "de.unidue.langtech.teaching.pp.project.type.DetectedLanguage");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_Suggestion), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_Suggestion), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_Suggestion), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public DetectedLanguage_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Language = jcas.getRequiredFeatureDE(casType, "Language", "uima.cas.String", featOkTst);
    casFeatCode_Language  = (null == casFeat_Language) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Language).getCode();

 
    casFeat_Suggestion = jcas.getRequiredFeatureDE(casType, "Suggestion", "uima.cas.StringArray", featOkTst);
    casFeatCode_Suggestion  = (null == casFeat_Suggestion) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Suggestion).getCode();

  }
}



    