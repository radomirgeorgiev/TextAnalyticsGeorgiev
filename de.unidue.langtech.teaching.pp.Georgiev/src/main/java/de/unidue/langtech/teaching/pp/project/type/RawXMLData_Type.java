
/* First created by JCasGen Thu Jan 19 14:45:41 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Sat Mar 18 23:40:52 CET 2017
 * @generated */
public class RawXMLData_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = RawXMLData.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.project.type.RawXMLData");
 
  /** @generated */
  final Feature casFeat_RawXMLData;
  /** @generated */
  final int     casFeatCode_RawXMLData;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public String getRawXMLData(int addr) {
        if (featOkTst && casFeat_RawXMLData == null)
      jcas.throwFeatMissing("RawXMLData", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return ll_cas.ll_getStringValue(addr, casFeatCode_RawXMLData);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setRawXMLData(int addr, String v) {
        if (featOkTst && casFeat_RawXMLData == null)
      jcas.throwFeatMissing("RawXMLData", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    ll_cas.ll_setStringValue(addr, casFeatCode_RawXMLData, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ListOfEntities;
  /** @generated */
  final int     casFeatCode_ListOfEntities;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getListOfEntities(int addr) {
        if (featOkTst && casFeat_ListOfEntities == null)
      jcas.throwFeatMissing("ListOfEntities", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ListOfEntities);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setListOfEntities(int addr, int v) {
        if (featOkTst && casFeat_ListOfEntities == null)
      jcas.throwFeatMissing("ListOfEntities", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    ll_cas.ll_setRefValue(addr, casFeatCode_ListOfEntities, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getListOfEntities(int addr, int i) {
        if (featOkTst && casFeat_ListOfEntities == null)
      jcas.throwFeatMissing("ListOfEntities", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfEntities), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfEntities), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfEntities), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setListOfEntities(int addr, int i, String v) {
        if (featOkTst && casFeat_ListOfEntities == null)
      jcas.throwFeatMissing("ListOfEntities", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfEntities), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfEntities), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfEntities), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_collectionSize;
  /** @generated */
  final int     casFeatCode_collectionSize;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getCollectionSize(int addr) {
        if (featOkTst && casFeat_collectionSize == null)
      jcas.throwFeatMissing("collectionSize", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return ll_cas.ll_getIntValue(addr, casFeatCode_collectionSize);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setCollectionSize(int addr, int v) {
        if (featOkTst && casFeat_collectionSize == null)
      jcas.throwFeatMissing("collectionSize", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    ll_cas.ll_setIntValue(addr, casFeatCode_collectionSize, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tempIndex;
  /** @generated */
  final int     casFeatCode_tempIndex;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getTempIndex(int addr) {
        if (featOkTst && casFeat_tempIndex == null)
      jcas.throwFeatMissing("tempIndex", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return ll_cas.ll_getIntValue(addr, casFeatCode_tempIndex);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setTempIndex(int addr, int v) {
        if (featOkTst && casFeat_tempIndex == null)
      jcas.throwFeatMissing("tempIndex", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    ll_cas.ll_setIntValue(addr, casFeatCode_tempIndex, v);}
    
  
 
  /** @generated */
  final Feature casFeat_ListOfClusterCandidates;
  /** @generated */
  final int     casFeatCode_ListOfClusterCandidates;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public int getListOfClusterCandidates(int addr) {
        if (featOkTst && casFeat_ListOfClusterCandidates == null)
      jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setListOfClusterCandidates(int addr, int v) {
        if (featOkTst && casFeat_ListOfClusterCandidates == null)
      jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    ll_cas.ll_setRefValue(addr, casFeatCode_ListOfClusterCandidates, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getListOfClusterCandidates(int addr, int i) {
        if (featOkTst && casFeat_ListOfClusterCandidates == null)
      jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i);
  return ll_cas.ll_getStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i);
  }
   
  /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @param v value to set
   */ 
  public void setListOfClusterCandidates(int addr, int i, String v) {
        if (featOkTst && casFeat_ListOfClusterCandidates == null)
      jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_isClusteringDone;
  /** @generated */
  final int     casFeatCode_isClusteringDone;
  /** @generated
   * @param addr low level Feature Structure reference
   * @return the feature value 
   */ 
  public boolean getIsClusteringDone(int addr) {
        if (featOkTst && casFeat_isClusteringDone == null)
      jcas.throwFeatMissing("isClusteringDone", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isClusteringDone);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setIsClusteringDone(int addr, boolean v) {
        if (featOkTst && casFeat_isClusteringDone == null)
      jcas.throwFeatMissing("isClusteringDone", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isClusteringDone, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public RawXMLData_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_RawXMLData = jcas.getRequiredFeatureDE(casType, "RawXMLData", "uima.cas.String", featOkTst);
    casFeatCode_RawXMLData  = (null == casFeat_RawXMLData) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_RawXMLData).getCode();

 
    casFeat_ListOfEntities = jcas.getRequiredFeatureDE(casType, "ListOfEntities", "uima.cas.StringArray", featOkTst);
    casFeatCode_ListOfEntities  = (null == casFeat_ListOfEntities) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ListOfEntities).getCode();

 
    casFeat_collectionSize = jcas.getRequiredFeatureDE(casType, "collectionSize", "uima.cas.Integer", featOkTst);
    casFeatCode_collectionSize  = (null == casFeat_collectionSize) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_collectionSize).getCode();

 
    casFeat_tempIndex = jcas.getRequiredFeatureDE(casType, "tempIndex", "uima.cas.Integer", featOkTst);
    casFeatCode_tempIndex  = (null == casFeat_tempIndex) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tempIndex).getCode();

 
    casFeat_ListOfClusterCandidates = jcas.getRequiredFeatureDE(casType, "ListOfClusterCandidates", "uima.cas.StringArray", featOkTst);
    casFeatCode_ListOfClusterCandidates  = (null == casFeat_ListOfClusterCandidates) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ListOfClusterCandidates).getCode();

 
    casFeat_isClusteringDone = jcas.getRequiredFeatureDE(casType, "isClusteringDone", "uima.cas.Boolean", featOkTst);
    casFeatCode_isClusteringDone  = (null == casFeat_isClusteringDone) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isClusteringDone).getCode();

  }
}



    