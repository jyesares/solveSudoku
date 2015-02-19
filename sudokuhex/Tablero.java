import java.math.BigInteger;

/**
 * Implementaci—n de la clase Tablero Hexadecimal
 * @author Francisco Javier Romero Yesares
 */
public class Tablero 
{
	
	public char[] casillas = new char[256];
	public int[] filas = new int[16];
	public int[] columnas = new int[16];
	public int[] bloques = new int[16];
	public int[] numPosibles = new int[256];
	
	/**
	 * Constructor
	 */
	public Tablero(){}
	
	/**
	 * Constructor con parametro
	 * @param template Es la cadena para rellenar el tablero
	 */
	public Tablero(String template)
	{
		for(int i=0; i<256; i++){
			this.casillas[i] = '.';
			this.setCasilla(i, template.charAt(i));
		}
		
		creaVectorPosibles();
	}

	
	/**
	 * Buscar una casilla vac’a
	 * @return Devuelve la posicion en el vector de la casilla que est‡ vac’a — -1 si no hay
	 */
	public int buscaVacia()
	{
		
		for(int i=0; i<casillas.length; i++){
			if(casillas[i]=='.')
				return i;
		}
		return -1;
	}

	/**
	 * Busca una casilla vacia en todo el tablero que tenga menos
	 * valores posibles para asignar en esa fila, columna, y bloque
	 * @return Devuelve la posicion en el tablero a asignar digito — -1 si no hay
	 */
	public int buscaVaciaMenosPosibles()
	{
		int aux = 16, pos = -1;
		
		for(int i=0; i<casillas.length; i++){
			if(casillas[i] == '.' && numPosibles[i] <= aux){
				aux = numPosibles[i];
				pos = i;
			}
		}
		return pos;
	}
	
	/**
	 * Crea un vector de enteros en donde cada posici—n contiene el numero de
	 * digitos posibles a asignar a esa casilla
	 */
	public void creaVectorPosibles()
	{
		int cont, f, c, b;
		cont = 0;
		int aux;
		
		for(int i=0; i<256; i++){
			if(casillas[i]=='.'){
				cont = 0;
				f = i/16;
				c = i%16;
				b = (f/4)*4+c/4;
				
				aux = filas[f] | columnas[c] | bloques[b];
				
				for(int j=0; j<16; j++)
					if((aux & (1<<j)) == 0) cont++;
				
				numPosibles[i] = cont;
				//numPosibles[i] = Integer.bitCount(aux);
				//numPosibles[i] = Integer.numberOfLeadingZeros(aux);
			}
		}
	}
	
	/**
	 * Asigna un digito a una casilla que solo tiene una posibilidad
	 */
	public void asignaSoloUnoPosible()
	{
		char c;
		
		for(int i=0; i<256; i++){
			if(numPosibles[i] == 1){
				for(int j=0; j<16; j++){
					c = Integer.toHexString(j).charAt(0);
					if(setCasilla(i, c)){
						numPosibles[i] = 0;
					}
				}
			}
		}
	}
	
	/**
	 * Comprueba si hay alguna casilla que solo se le pueda asignar un digito
	 * @return Devuelve true si hay alguna casilla — false si no hay ninguna
	 */
	public boolean compruebaSoloUnoPosible()
	{
		//Vector<Object> v = new Vector<Object>();
		boolean hayAlguno = false;
		
		for(int i=0; i<256; i++){
			if(numPosibles[i] == 1){
				hayAlguno = true;
			}
		}
		return hayAlguno;
	}
	
	/**
	 * Inserta un valor en una posicion del vector casillas
	 * y comprueba la consistencia
	 * @param pos Posici—n en la que insertar
	 * @param caracter Valor a insertar
	 */
	public boolean setCasilla(int pos, char caracter)
	{
		int f, c, b, h;
		
		f = pos/16;
		c = pos%16;
		b = (f/4)*4+c/4;
		h = (int)caracter;
		
		if(h>=48 && h<=57){
			h -= 48; // entre 0 y 9
		}else{
			if(h>=97 && h<=102){
				h -= 87; // entre 'a' y 'f'
			}else{
				if(h>=65 && h<=70)
					h -= 55; // entre 'A' y 'F'
				
			}
		}
		if(caracter == '.'){
			return true;
		}
		
		if(casillas[pos] == '.' && (filas[f] & (1<<h)) == 0 && (columnas[c] & (1<<h)) == 0 && (bloques[b] & (1<<h)) == 0 ){
			casillas[pos] = caracter;
			filas[f] |= (1<<h);
			columnas[c] |= (1<<h);
			bloques[b] |= (1<<h);
		}else
			return false;
		
		return true;

	}
	
	/**
	 * Borra casilla insertandole un '.'
	 * @param pos Posici—n que hay que borrar
	 */
	public void borraCasilla(int pos)
	{
		int f, c, b, h;
		
		f = pos/16;
        c = pos%16;
        b = (f/4)*4+c/4;
        h = casillas[pos];
        
        if(h>=48 && h<=57){
			h -= 48; // entre 0 y 9
		}else{
			if(h>=97 && h<=102){
				h -= 87; // entre 'a' y 'f'
			}else{
				if(h>=65 && h<=70)
					h -= 55; // entre 'A' y 'F'
				
			}
		}
      
        casillas[pos]='.';
        filas[f] ^= (1<<h);
        columnas[c] ^= (1<<h);
        bloques[b] ^= (1<<h);
	}
	
	/**
	 * Imprime el tablero en formato de cuadros
	 */
	public void imprimeTablero()
	{
		int pos = 0;
		
		for(int i=0; i<16; i++){
			System.out.print("|");
			for(int j=0; j<16; j++){
				System.out.print(casillas[pos]);
				//System.out.print("|");
				pos++;
				if(pos%4==0)
					System.out.print("|");
			}
			if((i+1)%4==0)
				System.out.print("\n-----+----+----+-----");
			System.out.print("\n");
		}
	}
	
	/**
	 * Imprime el tablero como una cadena
	 * @return String cadena con los valores del tablero
	 */
	public String imprimeTableroString()
	{
		String cadena = new String(casillas);
		return cadena;
	}
	
	/**
	 * Convertir un String a Hexadecimal
	 * @param arg
	 * @return
	 */
	public String toHex(String arg) {
	    return String.format("%040x", new BigInteger(arg.getBytes()));
	}


}
