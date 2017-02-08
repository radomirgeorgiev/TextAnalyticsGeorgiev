

/* First created by JCasGen Thu Feb 02 17:04:30 CET 2017 */
package de.unidue.langtech.teaching.pp.project.type;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Thu Feb 02 17:04:30 CET 2017
 * XML source: C:/Program Files/eclipse/workspace/de.unidue.langtech.teaching.pp.example/src/main/resources/desc/type/ClusterXML.xml
 * @generated */
public class ClusterXML extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(ClusterXML.class);
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
  protected ClusterXML() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated
   * @param addr low level Feature Structure reference
   * @param type the type of this Feature Structure 
   */
  public ClusterXML(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated
   * @param jcas JCas to which this Feature Structure belongs 
   */
  public ClusterXML(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated
   * @param jcas JCas to which this Feature Structure belongs
   * @param begin offset to the begin spot in the SofA
   * @param end offset to the end spot in the SofA 
  */  
  public ClusterXML(JCas jcas, int begin, int end) {
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
  //* Feature: ListOfClusterCandidates

  /** getter for ListOfClusterCandidates - gets 
   * @generated
   * @return value of the feature 
   */
  public StringArray getListOfClusterCandidates() {
    if (ClusterXML_Type.featOkTst && ((ClusterXML_Type)jcasType).casFeat_ListOfClusterCandidates == null)
      jcasType.jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.ClusterXML");
    return (StringArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((ClusterXML_Type)jcasType).casFeatCode_ListOfClusterCandidates)));}
    
  /** setter for ListOfClusterCandidates - sets  
   * @generated
   * @param v value to set into the feature 
   */
  public void setListOfClusterCandidates(StringArray v) {
    if (ClusterXML_Type.featOkTst && ((ClusterXML_Type)jcasType).casFeat_ListOfClusterCandidates == null)
      jcasType.jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.ClusterXML");
    jcasType.ll_cas.ll_setRefValue(addr, ((ClusterXML_Type)jcasType).casFeatCode_ListOfClusterCandidates, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for ListOfClusterCandidates - gets an indexed value - 
   * @generated
   * @param i index in the array to get
   * @return value of the element at index i 
   */
  public String getListOfClusterCandidates(int i) {
    if (ClusterXML_Type.featOkTst && ((ClusterXML_Type)jcasType).casFeat_ListOfClusterCandidates == null)
      jcasType.jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.ClusterXML");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ClusterXML_Type)jcasType).casFeatCode_ListOfClusterCandidates), i);
    return jcasType.ll_cas.ll_getStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ClusterXML_Type)jcasType).casFeatCode_ListOfClusterCandidates), i);}

  /** indexed setter for ListOfClusterCandidates - sets an indexed value - 
   * @generated
   * @param i index in the array to set
   * @param v value to set into the array 
   */
  public void setListOfClusterCandidates(int i, String v) { 
    if (ClusterXML_Type.featOkTst && ((ClusterXML_Type)jcasType).casFeat_ListOfClusterCandidates == null)
      jcasType.jcas.throwFeatMissing("ListOfClusterCandidates", "de.unidue.langtech.teaching.pp.project.type.ClusterXML");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((ClusterXML_Type)jcasType).casFeatCode_ListOfClusterCandidates), i);
    jcasType.ll_cas.ll_setStringArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((ClusterXML_Type)jcasType).casFeatCode_ListOfClusterCandidates), i, v);}
  }

    