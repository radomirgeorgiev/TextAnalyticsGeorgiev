
/* First created by JCasGen Wed Jan 04 15:51:14 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Wed Jan 04 15:51:14 CET 2017
 * @generated */
public class DateFormat_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = DateFormat.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.type.DateFormat");
 
  /** @generated */
  final Feature casFeat_currentDate;
  /** @generated */
  final int     casFeatCode_currentDate;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getCurrentDate(int addr) {
        if (featOkTst && casFeat_currentDate == null)
      jcas.throwFeatMissing("currentDate", "de.unidue.langtech.teaching.pp.type.DateFormat");
    return ll_cas.ll_getStringValue(addr, casFeatCode_currentDate);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCurrentDate(int addr, String v) {
        if (featOkTst && casFeat_currentDate == null)
      jcas.throwFeatMissing("currentDate", "de.unidue.langtech.teaching.pp.type.DateFormat");
    ll_cas.ll_setStringValue(addr, casFeatCode_currentDate, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public DateFormat_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_currentDate = jcas.getRequiredFeatureDE(casType, "currentDate", "uima.cas.String", featOkTst);
    casFeatCode_currentDate  = (null == casFeat_currentDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_currentDate).getCode();

  }
}



    