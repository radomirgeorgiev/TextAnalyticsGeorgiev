
/* First created by JCasGen Tue Jan 03 13:00:32 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Fri Jan 20 01:52:18 CET 2017
 * @generated */
public class BaseLanguage_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = BaseLanguage.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
 
  /** @generated */
  final Feature casFeat_integerVector;
  /** @generated */
  final int     casFeatCode_integerVector;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getIntegerVector(int addr) {
        if (featOkTst && casFeat_integerVector == null)
      jcas.throwFeatMissing("integerVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    return ll_cas.ll_getRefValue(addr, casFeatCode_integerVector);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIntegerVector(int addr, int v) {
        if (featOkTst && casFeat_integerVector == null)
      jcas.throwFeatMissing("integerVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    ll_cas.ll_setRefValue(addr, casFeatCode_integerVector, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public int getIntegerVector(int addr, int i) {
        if (featOkTst && casFeat_integerVector == null)
      jcas.throwFeatMissing("integerVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_integerVector), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_integerVector), i);
  return ll_cas.ll_getIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_integerVector), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setIntegerVector(int addr, int i, int v) {
        if (featOkTst && casFeat_integerVector == null)
      jcas.throwFeatMissing("integerVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    if (lowLevelTypeChecks)
      ll_cas.ll_setIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_integerVector), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_integerVector), i);
    ll_cas.ll_setIntArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_integerVector), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_doubleVector;
  /** @generated */
  final int     casFeatCode_doubleVector;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getDoubleVector(int addr) {
        if (featOkTst && casFeat_doubleVector == null)
      jcas.throwFeatMissing("doubleVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    return ll_cas.ll_getRefValue(addr, casFeatCode_doubleVector);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDoubleVector(int addr, int v) {
        if (featOkTst && casFeat_doubleVector == null)
      jcas.throwFeatMissing("doubleVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    ll_cas.ll_setRefValue(addr, casFeatCode_doubleVector, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public double getDoubleVector(int addr, int i) {
        if (featOkTst && casFeat_doubleVector == null)
      jcas.throwFeatMissing("doubleVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_doubleVector), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_doubleVector), i);
  return ll_cas.ll_getDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_doubleVector), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setDoubleVector(int addr, int i, double v) {
        if (featOkTst && casFeat_doubleVector == null)
      jcas.throwFeatMissing("doubleVector", "de.unidue.langtech.teaching.pp.project.type.BaseLanguage");
    if (lowLevelTypeChecks)
      ll_cas.ll_setDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_doubleVector), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_doubleVector), i);
    ll_cas.ll_setDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_doubleVector), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public BaseLanguage_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_integerVector = jcas.getRequiredFeatureDE(casType, "integerVector", "uima.cas.IntegerArray", featOkTst);
    casFeatCode_integerVector  = (null == casFeat_integerVector) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_integerVector).getCode();

 
    casFeat_doubleVector = jcas.getRequiredFeatureDE(casType, "doubleVector", "uima.cas.DoubleArray", featOkTst);
    casFeatCode_doubleVector  = (null == casFeat_doubleVector) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_doubleVector).getCode();

  }
}



    