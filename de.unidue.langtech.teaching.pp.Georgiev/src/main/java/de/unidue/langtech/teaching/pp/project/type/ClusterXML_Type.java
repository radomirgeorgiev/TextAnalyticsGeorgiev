
/* First created by JCasGen Thu Feb 02 17:04:30 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Feb 02 17:04:30 CET 2017
 * @generated */
public class ClusterXML_Type extends Annotation_Type {
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = ClusterXML.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("de.unidue.langtech.teaching.pp.project.type.ClusterXML");
 
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
      jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.ClusterXML");
    return ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates);
  }
  /** @generated
   * @param addr low level Feature Structure reference
   * @param v value to set 
   */    
  public void setListOfClusterCandidates(int addr, int v) {
        if (featOkTst && casFeat_ListOfClusterCandidates == null)
      jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.ClusterXML");
    ll_cas.ll_setRefValue(addr, casFeatCode_ListOfClusterCandidates, v);}
    
   /** @generated
   * @param addr low level Feature Structure reference
   * @param i index of item in the array
   * @return value at index i in the array 
   */
  public String getListOfClusterCandidates(int addr, int i) {
        if (featOkTst && casFeat_ListOfClusterCandidates == null)
      jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.ClusterXML");
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
      jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.ClusterXML");
    if (lowLevelTypeChecks)
      ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i);
    ll_cas.ll_setStringArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_ListOfClusterCandidates), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	 * @generated
	 * @param jcas JCas
	 * @param casType Type 
	 */
  public ClusterXML_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ListOfClusterCandidates = jcas.getRequiredFeatureDE(casType, "ListOfClusterCandidates", "uima.cas.StringArray", featOkTst);
    casFeatCode_ListOfClusterCandidates  = (null == casFeat_ListOfClusterCandidates) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ListOfClusterCandidates).getCode();

  }
}



    