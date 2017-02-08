
/* First created by JCasGen Fri Jan 20 01:28:21 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Jan 26 15:30:53 CET 2017
 * @generated */
public class RawTextData_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = RawTextData.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.project.type.RawTextData");
 
  /** @generated */
  final Feature casFeat_documentRawText;
  /** @generated */
  final int     casFeatCode_documentRawText;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDocumentRawText(int addr) {
        if (featOkTst && casFeat_documentRawText == null)
      jcas.throwFeatMissing("documentRawText", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_documentRawText);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDocumentRawText(int addr, String v) {
        if (featOkTst && casFeat_documentRawText == null)
      jcas.throwFeatMissing("documentRawText", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    ll_cas.ll_setStringValue(addr, casFeatCode_documentRawText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_documentCreationDate;
  /** @generated */
  final int     casFeatCode_documentCreationDate;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getDocumentCreationDate(int addr) {
        if (featOkTst && casFeat_documentCreationDate == null)
      jcas.throwFeatMissing("documentCreationDate", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_documentCreationDate);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDocumentCreationDate(int addr, String v) {
        if (featOkTst && casFeat_documentCreationDate == null)
      jcas.throwFeatMissing("documentCreationDate", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    ll_cas.ll_setStringValue(addr, casFeatCode_documentCreationDate, v);}
    
  
 
  /** @generated */
  final Feature casFeat_documentID;
  /** @generated */
  final int     casFeatCode_documentID;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getDocumentID(int addr) {
        if (featOkTst && casFeat_documentID == null)
      jcas.throwFeatMissing("documentID", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    return ll_cas.ll_getIntValue(addr, casFeatCode_documentID);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setDocumentID(int addr, int v) {
        if (featOkTst && casFeat_documentID == null)
      jcas.throwFeatMissing("documentID", "de.unidue.langtech.teaching.pp.project.type.RawTextData");
    ll_cas.ll_setIntValue(addr, casFeatCode_documentID, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public RawTextData_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_documentRawText = jcas.getRequiredFeatureDE(casType, "documentRawText", "uima.cas.String", featOkTst);
    casFeatCode_documentRawText  = (null == casFeat_documentRawText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_documentRawText).getCode();

 
    casFeat_documentCreationDate = jcas.getRequiredFeatureDE(casType, "documentCreationDate", "uima.cas.String", featOkTst);
    casFeatCode_documentCreationDate  = (null == casFeat_documentCreationDate) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_documentCreationDate).getCode();

 
    casFeat_documentID = jcas.getRequiredFeatureDE(casType, "documentID", "uima.cas.Integer", featOkTst);
    casFeatCode_documentID  = (null == casFeat_documentID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_documentID).getCode();

  }
}



    