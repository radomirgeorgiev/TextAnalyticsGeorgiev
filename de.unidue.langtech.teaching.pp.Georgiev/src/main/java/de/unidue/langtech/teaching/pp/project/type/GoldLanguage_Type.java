
/* First created by JCasGen Tue Oct 15 15:45:50 CEST 2013 */
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
 * Updated by JCasGen Fri Jan 20 01:59:47 CET 2017
 * @generated */
public class GoldLanguage_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = GoldLanguage.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.project.type.GoldLanguage");
 
  /** @generated */
  final Feature casFeat_Language;
  /** @generated */
  final int     casFeatCode_Language;
  /** @generated */ 
  public String getLanguage(int addr) {
        if (featOkTst && casFeat_Language == null)
      jcas.throwFeatMissing("Language", "de.unidue.langtech.teaching.pp.project.type.GoldLanguage");
    return ll_cas.ll_getStringValue(addr, casFeatCode_Language);
  }
  /** @generated */    
  public void setLanguage(int addr, String v) {
        if (featOkTst && casFeat_Language == null)
      jcas.throwFeatMissing("Language", "de.unidue.langtech.teaching.pp.project.type.GoldLanguage");
    ll_cas.ll_setStringValue(addr, casFeatCode_Language, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public GoldLanguage_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_Language = jcas.getRequiredFeatureDE(casType, "Language", "uima.cas.String", featOkTst);
    casFeatCode_Language  = (null == casFeat_Language) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_Language).getCode();

  }
}



    