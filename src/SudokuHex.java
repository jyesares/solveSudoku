//Title:       SudokuHex
//Version:     0.1
//Copyright:   2012
//Author:      Francisco Javier Romero Yesares
//E-mail:      jaroye@correo.ugr.es

/**
 * Implementaci—n base para la resoluci—n de un Sudoku hexadecimal.
 * 
 * @author Francisco Javier Romero Yesares
 */

public class SudokuHex
{
	Tablero tab;
	boolean solucionado;
	
	/**
	 * Constructor
	 * @param template Cadena representando una plantilla de sudoku
	 */
	public SudokuHex (String template)
	{
		tab = new Tablero(template);
		solucionado = false;
	}
	
	/**
	 * Resoluci—n de sudokus
	 */
	public void solve()
	{
		if(tab.compruebaSoloUnoPosible()){
			tab.asignaSoloUnoPosible();
			solve();
		}else{
			backtracking(tab);
		}
	}
	
	/**
	 * Funci—n backtracking. El criterio de parada se cumple cuando no encuentra ninguna
	 * posici—n libre en el tablero (pos == -1) y devuelve el control a la funci—n que lo llama.
	 * @param tablero Es el tablero sudoku a solucionar
	 */
	public void backtracking(Tablero tablero)
	{
		int pos;

		pos = tablero.buscaVaciaMenosPosibles();
		
		if(pos==-1){
			solucionado = true;
			return;
		}
		
		for(int i=0; i<16; i++){
			char ca = Integer.toHexString(i).charAt(0);
			
			if(tablero.setCasilla(pos, ca)){
				tablero.creaVectorPosibles();
				backtracking(tablero);
				if(solucionado) return;
				tablero.borraCasilla(pos);
				tablero.creaVectorPosibles();
			}
		}
		
	}
	
	/**
     * Representaci—n del sudoku como una cadena de caracteres
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return tab.imprimeTableroString();
		/*return "a0....3.fd...c.....d459.b.c..2f.be.cd.280...1.7.5..2.e0..834..b."
			     + "....2.83...f.061......1.e3..4f..78..6df4.....e..4.d..c..1.26.b87"
				 + "ca7.f3.d..0..5.4..f.....649a..0b..04..b7.5......dbe.a...27.3...."
				 + ".c..e4d..f6.b..a.d.b...27e.10.cf.7a..1.b.0dc8.....5...7f.2....ed";
				 */
	}


}
 