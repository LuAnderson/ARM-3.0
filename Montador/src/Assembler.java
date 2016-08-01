import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class Assembler {

	private Hashtable registradores;
	private Hashtable instrucoes;
	private ArrayList labels;
	  int numReg = 0;	

	public Assembler(){
		

      
		registradores = new Hashtable();
		instrucoes = new Hashtable();
        labels = new ArrayList();

		carregarRegistradores();
	}

	private void carregarRegistradores() {
		registradores.put("$zero", 0);
	    registradores.put("$at", 1);
	    registradores.put("$v0", 2);
	    registradores.put("$v1", 3);
	    registradores.put("$a0", 4);
	    registradores.put("$a1", 5);
	    registradores.put("$a2", 6);
	    registradores.put("$a3", 7);
	    registradores.put("$t0", 8);
	    registradores.put("$t1", 9);
	    registradores.put("$t2", 10);
	    registradores.put("$t3", 11);
	    registradores.put("$t4", 12);
	    registradores.put("$t5", 13);
	    registradores.put("$t6", 14);
	    registradores.put("$t7", 15);
	    registradores.put("$s0", 16);
	    registradores.put("$s1", 17);
	    registradores.put("$s2", 18);
	    registradores.put("$s3", 19);
	    registradores.put("$s4", 20);
	    registradores.put("$s5", 21);
	    registradores.put("$s6", 22);
	    registradores.put("$s7", 23);
	    registradores.put("$t8", 24);
	    registradores.put("$t9", 25);
	    registradores.put("$k0", 26);
	    registradores.put("$k1", 27);
	    registradores.put("$gp", 28);
	    registradores.put("$sp", 29);
	    registradores.put("$fp", 30);
	    registradores.put("$ra", 31);
	}

	private void carregarInstrucoes(){
		instrucoes.put("add",  "R300000000000001");
	    instrucoes.put("addu", "R300000000000010");
	    instrucoes.put("clz",  "R200000000000011");
	    instrucoes.put("clo",  "R200000000000100");
	    instrucoes.put("move", "R200000000000101");
	    instrucoes.put("negu", "R200000000000110");
	    instrucoes.put("sub",  "R300000000000111");
	    instrucoes.put("subu", "R300000000001000");
	    instrucoes.put("seh",  "R200000000001001");
	    instrucoes.put("seb",  "R200000000001010");
	    //Logic
	    instrucoes.put("and",  "R300000000001011");
	    instrucoes.put("nor",  "R300000000001100");
	    instrucoes.put("not",  "R200000000001101");
	    instrucoes.put("or",   "R300000000001110");
	    instrucoes.put("xor",  "R300000000001111");
	    //Multiplication and Division
	    instrucoes.put("div",  "R300000000010000");
	    instrucoes.put("divu", "R300000000010001");
	    instrucoes.put("madd", "R200000000010010");
	    instrucoes.put("maddu","R200000000010011");
	    instrucoes.put("msub", "R200000000010100");
	    instrucoes.put("msubu","R200000000010101");
	    instrucoes.put("mul",  "R300000000010110");
	    instrucoes.put("mult", "R200000000010111");
	    //Shift and Rotate
	    instrucoes.put("sll",  "R210000000011000");
	    instrucoes.put("sllv", "R300000000011001");
	    instrucoes.put("srl",  "R210000000011010");
	    instrucoes.put("sra",  "R210000000011011");
	    instrucoes.put("srav", "R300000000011100");
	    instrucoes.put("srlv", "R300000000011101");
	    instrucoes.put("rotr", "R210000000011110");
	    instrucoes.put("rotrv","R300000000011111");
	    //Conditionals and move
	    instrucoes.put("slt",  "R300000000100000");
	    instrucoes.put("sltu", "R300000000100001");
	    instrucoes.put("movn", "R300000000100010");
	    instrucoes.put("movz", "R300000000100011");
	    //Acc access
	    instrucoes.put("mfhi", "R100000000100100");
	    instrucoes.put("mflo", "R100000000100101");
	    instrucoes.put("mthi", "R100000000100110");
	    instrucoes.put("mtlo", "R100000000100111");
	    //Branches and jump
	    instrucoes.put("jr",   "R100000000101000");
	    instrucoes.put("jalr", "R200000000101001");
	    
	    //I - instructions------------------------------------
	    //Arithmetic
	    instrucoes.put("addi",  "I210000001");
	    instrucoes.put("addiu", "I210000010");
	    instrucoes.put("la",    "I101000011");
	    instrucoes.put("li",    "I110000100");
	    instrucoes.put("lui",   "I110000101");
	    //Logic
	    instrucoes.put("andi",  "I210000110");
	    instrucoes.put("ori",   "I210000111");
	    instrucoes.put("xori",  "I210001000");
	    //Multiplication and Division
		instrucoes.put("multu", "I110001001");
		//Conditionals and move
		instrucoes.put("slti",  "I210001010");
		instrucoes.put("sltiu", "I210001011");
		//Branches and jump
		instrucoes.put("beq",   "I201001100");
		instrucoes.put("beqz",  "I101001101");
		instrucoes.put("bne",   "I201001110");
		instrucoes.put("bnez",  "I101001111");
		//Load and store
		instrucoes.put("lb",    "I210010010");
		instrucoes.put("lw",    "I210010011");
		instrucoes.put("lh",    "I210010100");
		instrucoes.put("sb",    "I210010101");
		instrucoes.put("sh",    "I210010110");
		instrucoes.put("sw",    "I210010111");
		
		//J - instructions------------------------------------
		//Branches and jump
		instrucoes.put("j",     "J001010000");
		instrucoes.put("jal",   "J001010001");
	}

 public void  primeiraLeitura(String nome){
	 
 
	
	    System.out.printf("\nConteúdo do arquivo texto:\n");
	    try {
	      FileReader arq = new FileReader(nome);
	      BufferedReader lerArq = new BufferedReader(arq);
	 
	      String linha = lerArq.readLine(); 
	     
	      while (linha != null) {
	        
	        linha = lerArq.readLine();
	        if(linha == null){
	        	break;
	        }
	        if(linha.contains(";")){
	        linha =	limparComentario(linha,";");
	        	
	        }
	        
	        else if(linha.contains("#")){
	        linha =	limparComentario(linha,"#");
	        	
	        }
	       
	        if(linha.contains("$")){
	        	
	        	 if( !validarRegistradores(linha)){
	  	    	   System.out.println("Registrador não conhecido");
	  	    	   break;
	  	    	   
	  	       }
	        	
	        	
	        }
	       
	        carregarLabels(linha);
	        
	        	
	      }
	      
	 
	      arq.close();
	    } catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n",
	          e.getMessage());
	    }
	 
	    System.out.println();
	  }
	

 public void segundaLeitura(String nome){
	 
	 
	 
	 try {
	      FileReader arq = new FileReader(nome);
	      BufferedReader lerArq = new BufferedReader(arq);
	 
	      String linha = lerArq.readLine(); 
	     
	      while (linha != null) {
	        
	        linha = lerArq.readLine();
	        if(linha == null){
	        	break;
	        }
	        
	        String array [] = linha.split(",");
	        String label = array[array.length -1];
	        if(!labels.contains(label)){
	        	System.out.println("A label : " + label + " não existe");
	        	
	        }
	        
	        
	        	
	      }
	      
	 
	      arq.close();
	    } catch (IOException e) {
	        System.err.printf("Erro na abertura do arquivo: %s.\n",
	          e.getMessage());
	    }
	 
	    System.out.println();
	 
	 
	 
 }
 
 
 
 public String limparComentario(String linha, String tipo){
	String array[] =  new String[2];
	array = linha.split(tipo);

     return array[0];

}

public void carregarLabels(String linha){
	
	if(linha.contains(":")){
    	linha = linha.trim();
    	String array[] =  new String[2];
    	array = linha.split(":");
    	labels.add(array[0]);
    }
}

public boolean validarRegistradores(String linha){
	
	String nomeRegistrador = null;
	   int numReg = 0;
    	
    	for(int i = 0; i < linha.length(); i++){
            if(linha.charAt(i) == '$'){
            	numReg++; 
            	
            }
                
        }
    	
    	char[] array = linha.toCharArray();
    	
    	for(int i = 0; i< linha.length(); i++){
    		char aux = array[i];
    		if(aux == '$'){
    		
    			
    			StringBuilder sb = new StringBuilder();
    			sb.append(array[i]);
    			sb.append(array[i +1]);
    			sb.append(array[i +2]);
    			nomeRegistrador = sb.toString();
    			
    			
    			
    			if(!registradores.containsKey(nomeRegistrador)){
    		    	
    				System.out.println("O Registrador " + nomeRegistrador + " não está disponivel");

        			return false;    		
    	    	}
    			
    	    
    			
    			
    			
    		}
    		
    		
    	}
    	
    	System.out.println("Registrador " +nomeRegistrador +  " Carregado com Sucesso!!");
    	
    	return true;
	
}

public boolean eDiretriz(String linha){
	char array[] = linha.toCharArray();
    for(int i = 0; i< linha.length(); i++){
    	
    	if( array[i] != ' ' ){
    		
    		
    		StringBuilder sb = new StringBuilder();
			sb.append(array[i]);
			sb.append(array[i +1]);
			sb.append(array[i +2]);
			nomeRegistrador = sb.toString();
    		
    		
    		break;
    	
    	
    	
    	}
   
    
    
    }
  



}

}
