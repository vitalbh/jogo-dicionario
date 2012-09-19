package controle;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.*;
import java.util.*;

public class Xml {
	public static String xml="arquivoxml"; 

	private static Xml theLib ;  

	  

	  public synchronized static Xml getLibrary(){

	    if( theLib == null ) theLib = new Xml();

	    return theLib ;

	  }

	  

	  // instance variables below this

	  private Hashtable domHash ;

	 

	  private String lastErr = "none" ;

	  // private constructor to ensure singleton

	  public Xml(){

	    domHash = new Hashtable();
	    
	    
	  }
	
		
		 private Object loadXML( String src, boolean validate ) {

			    File xmlFile = new File( xml ) ;

			    String err = null ;

			    try {  

			     DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

			      dbf.setValidating( validate );
			      dbf.setNamespaceAware(false);
			      DocumentBuilder db = dbf.newDocumentBuilder();

			      Document doc = db.parse( xml );

			      return doc ;

			    }catch(ParserConfigurationException pce){

			        err = pce.toString();

			    }catch(SAXParseException spe ){

			        StringBuffer sb = new StringBuffer( spe.toString() );

			        sb.append("\n  Line number: " + spe.getLineNumber());

			        sb.append("\nColumn number: " + spe.getColumnNumber() );

			        sb.append("\n Public ID: " + spe.getPublicId() );

			        sb.append("\n System ID: " + spe.getSystemId() + "\n");

			        err = sb.toString();

			    }catch( SAXException se ){

			       err = se.toString();

			       if( se.getException() != null ){

			          err += " caused by: " + se.getException().toString() ;

			       }

			    }catch( IOException ie ){

			       err = ie.toString(); 

			    }

			    return err ;    

			  } // end loadXML
	
		 
//		 ou retorna um Document ou null se tiver problemas

		 public synchronized Document getDOM( String src, boolean validate ){

		    Object doc = domHash.get( src );

		     File f = null ;

		     if( doc == null ){

		         System.out.println("DOMlibrary.getDOM new " + src );

		         doc = loadXML( src, validate );

		         domHash.put( src, doc );

		         if( doc instanceof String ){

		             lastErr = (String) doc ;

		         }

		     }

		     // se não for um documento, então deve ser uma String de erro

		     if( doc instanceof Document ) {

		        return (Document) doc ;

		     }

		     return null ;

		   }
	
//		 utilize isto para forçar a remoção de um DOM. Retorna

		  // a última cópia do DOM ou null caso não exista

		  public synchronized Document removeDOM( String src ){

		    Document dom = (Document)domHash.get( src );

		    if( dom != null ){

		      domHash.remove( src );

		      // System.out.println("Removed " + src );

		    }

		    return dom ;    

		  }

		  

		  // utilize isto para forçar uma atualização de um DOM

		public synchronized Document reloadDOM( String src, boolean validate ){

		    if( domHash.get( src ) != null ){

		      domHash.remove( src );

		    }

		    return getDOM( src, validate );

		  }

		    

		  public String getLastErr(){ return lastErr ;
		  }
		  }

		  

	