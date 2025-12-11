/**
 * 
 */
/**
 * 
 */
module Zombi {
	requires java.rmi;
	requires LibreriaRMIMVC;
	requires java.desktop;
	// 1. Hace visible la interfaz IModelo
    exports ar.edu.unlu.zombi.interfaces; 
    
    // 2. Hace visible la clase Mensaje (y otros recursos compartidos)
    exports ar.edu.unlu.zombi.recursos; 
    
    // 3. Hace visible las entidades del Modelo que se envían por red
    exports ar.edu.unlu.zombi.modelo.entidades;
}