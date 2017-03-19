

/* First created by JCasGen Thu Jan 19 14:45:41 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Sat Mar 18 23:40:52 CET 2017
 * XML source: /Users/biciani/Documents/workspace/TextAnalyticsGeorgiev/de.unidue.langtech.teaching.pp.Georgiev/src/main/resources/desc/type/RawXMLData.xml
 * @generated */
public class RawXMLData extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(RawXMLData.class);
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
  protected RawXMLData() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public RawXMLData(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public RawXMLData(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public RawXMLData(JCas jcas, int begin, int end) {
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
  //* Feature: RawXMLData

  /** getter for RawXMLData - gets 
   * @generated
   * @return value of the feature 
   */
  public String getRawXMLData() {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_RawXMLData == null)
      jcasType.jcas.throwFeatMissing("RawXMLData", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return jcasType.ll_cas.ll_getStringValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_RawXMLData);}
    
  /** setter for RawXMLData - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setRawXMLData(String v) {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_RawXMLData == null)
      jcasType.jcas.throwFeatMissing("RawXMLData", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.ll_cas.ll_setStringValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_RawXMLData, v);}    
   
    
  //*--------------*
  //* Feature: ListOfEntities

  /** getter for ListOfEntities - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getListOfEntities() {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_ListOfEntities == null)
      jcasType.jcas.throwFeatMissing("ListOfEntities", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfEntities)));}
    
  /** setter for ListOfEntities - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setListOfEntities(StringArray v) {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_ListOfEntities == null)
      jcasType.jcas.throwFeatMissing("ListOfEntities", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.ll_cas.ll_setRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfEntities, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for ListOfEntities - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getListOfEntities(int i) {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_ListOfEntities == null)
      jcasType.jcas.throwFeatMissing("ListOfEntities", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfEntities), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfEntities), i);}

  /** indexed setter for ListOfEntities - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setListOfEntities(int i, String v) { 
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_ListOfEntities == null)
      jcasType.jcas.throwFeatMissing("ListOfEntities", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfEntities), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfEntities), i, v);}
   
    
  //*--------------*
  //* Feature: collectionSize

  /** getter for collectionSize - gets 
   * @generated
   * @return value of the feature 
   */
  public int getCollectionSize() {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_collectionSize == null)
      jcasType.jcas.throwFeatMissing("collectionSize", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return jcasType.ll_cas.ll_getIntValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_collectionSize);}
    
  /** setter for collectionSize - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setCollectionSize(int v) {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_collectionSize == null)
      jcasType.jcas.throwFeatMissing("collectionSize", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.ll_cas.ll_setIntValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_collectionSize, v);}    
   
    
  //*--------------*
  //* Feature: tempIndex

  /** getter for tempIndex - gets 
   * @generated
   * @return value of the feature 
   */
  public int getTempIndex() {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_tempIndex == null)
      jcasType.jcas.throwFeatMissing("tempIndex", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return jcasType.ll_cas.ll_getIntValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_tempIndex);}
    
  /** setter for tempIndex - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setTempIndex(int v) {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_tempIndex == null)
      jcasType.jcas.throwFeatMissing("tempIndex", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.ll_cas.ll_setIntValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_tempIndex, v);}    
   
    
  //*--------------*
  //* Feature: ListOfClusterCandidates

  /** getter for ListOfClusterCandidates - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getListOfClusterCandidates() {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_ListOfClusterCandidates == null)
      jcasType.jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfClusterCandidates)));}
    
  /** setter for ListOfClusterCandidates - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setListOfClusterCandidates(StringArray v) {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_ListOfClusterCandidates == null)
      jcasType.jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.ll_cas.ll_setRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfClusterCandidates, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for ListOfClusterCandidates - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getListOfClusterCandidates(int i) {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_ListOfClusterCandidates == null)
      jcasType.jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfClusterCandidates), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfClusterCandidates), i);}

  /** indexed setter for ListOfClusterCandidates - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setListOfClusterCandidates(int i, String v) { 
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_ListOfClusterCandidates == null)
      jcasType.jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfClusterCandidates), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_ListOfClusterCandidates), i, v);}
   
    
  //*--------------*
  //* Feature: isClusteringDone

  /** getter for isClusteringDone - gets 
   * @generated
   * @return value of the feature 
   */
  public boolean getIsClusteringDone() {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_isClusteringDone == null)
      jcasType.jcas.throwFeatMissing("isClusteringDone", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_isClusteringDone);}
    
  /** setter for isClusteringDone - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setIsClusteringDone(boolean v) {
    if (RawXMLData_Type.featOkTst && ((RawXMLData_Type)jcasType).casFeat_isClusteringDone == null)
      jcasType.jcas.throwFeatMissing("isClusteringDone", "de.unidue.langtech.teaching.pp.project.type.RawXMLData");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((RawXMLData_Type)jcasType).casFeatCode_isClusteringDone, v);}    
  }

    