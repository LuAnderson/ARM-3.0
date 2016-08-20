import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Principal {

	public static void main(String[] args) throws IOException {

	Assembler ass = new Assembler();
	
	String nome = JOptionPane.showInputDialog("Entre com o endereço doa arquivo :");
	
	ass.primeiraLeitura(nome + ".asm");
	ass.segundaLeitura(nome + ".asm");		 
			
		
		  }
		
	

}
