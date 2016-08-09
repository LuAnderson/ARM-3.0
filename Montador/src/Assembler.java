import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

import javax.swing.text.html.HTMLEditorKit.Parser;


public class Assembler {

	private Hashtable registradores;
	private Hashtable instrucoes;
	private Hashtable labelEnderecada;
	private Hashtable pseudoInstrucao;

	
	private ArrayList instrucoesUsadas;
	private ArrayList instrucoesUsadasSaltos;
	private ArrayList referenciasSaltos;
	final double memoria =  65536; // 64kbytes
    private ArrayList execução;
    private int proximaLabel = 0;

	private Hashtable diretrizes;
	private ArrayList labels;
	private ArrayList linhas;
	  int numReg = 0;	

	public Assembler(){
		

      
		registradores = new Hashtable();
		diretrizes = new Hashtable();

		instrucoes = new Hashtable();
        labels = new ArrayList();
        linhas = new ArrayList();
        instrucoesUsadas = new ArrayList();
		instrucoesUsadasSaltos = new ArrayList();
		referenciasSaltos = new ArrayList();
		execução = new ArrayList();

		carregarRegistradores();
		carregarInstrucoes();
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
	    instrucoes.put("addi",  "I310000001");
	    instrucoes.put("addiu", "I210000010");
	    instrucoes.put("la",    "I101000011");
	    instrucoes.put("li",    "I210000100");
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
		instrucoes.put("beq",   "I301001100");
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
		instrucoes.put("J",     "J001010000");
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
	        linha =	limparComentario(linha,";");// retirando comentarios
	        	
	        }
	        
	        else if(linha.contains("#")){
	        linha =	limparComentario(linha,"#"); // retirando comentarios
	        	
	        }
	        linha = linha.trim();
	        if(!linha.trim().equals("")){ // retirando linhas em branco
	        	   linhas.add(linha);
	        }
	     
	       
	        if(linha.contains("$")){         // verificando se a linha possui um registrador
	        	
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
	 
	 for(int i = 0; i < linhas.size(); i++){
		char array [] = ((String) linhas.get(i)).toCharArray();
		 
		 
		 if(eDiretriz((String) linhas.get(i))){
           String diretriz = (String)linhas.get(i);			 
           System.out.println("Carregou " + diretriz);
           i++;		 
          

		 }
        if(((String) linhas.get(i)).contains(":")){

        	if(i == linhas.size()-1){
        		break;
        	}
        	
        	i++;

        }
		String codigo [] = ((String) linhas.get(i)).split(" ");
		System.out.println(codigo[0]);
		  String instrucao = codigo[0]; // separando a intrução da operação
		  
		  StringBuilder sb = new StringBuilder();
			
		  for( int j = 1; j< codigo.length; j ++){ // montando a operação
			  sb.append(codigo[j]);
				
				
			                             
			  
		  }
		  String operacao = sb.toString().trim(); // retira espaços em branco da operação
		  System.out.println(operacao);
		 
		String arraycont [];
		
		
			 arraycont = ((String) linhas.get(i)).split(",");
			if( !instrucaoValida( instrucao, arraycont.length ,operacao)){
				
				System.out.println(" O montador será encerrado!!!!");
				break;
				
			}
								
		       
			
		
		
		 

	 
	 }
	 
	 
	 
	 
	 
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


public void filtraInstrucao(String linha){
	String array[] = linha.split(" ");
	System.out.println(array[0]);
	
	
	
}

public boolean eDiretriz(String linha){
	if(linha.charAt(0) == '.'){
		return true;
	}
   
    	return false;
    	


}
  
public boolean instrucaoValida(String codigo, int para, String segundaParte){
	
	if(instrucoes.containsKey(codigo)){
		String codigos = (String) instrucoes.get(codigo);
		char parametros = codigos.charAt(1);
		int numparametros =  Character.getNumericValue(parametros);
		segundaParte = segundaParte.trim();
		
		

if(instrucaoSalto(codigo, segundaParte)){

	System.out.println("Instrucção " + codigo + " Valida");
			 return true;
	 }
else if(para == numparametros){
		 System.out.println("Instrucção " + codigo + " Valida");
		 if( usaLabel(codigos)){
			 
			 String label = pegaLabel(segundaParte);
			 
			 if(labelExiste(label)){
				 
				 instrucoesUsadas.add(codigos);
				   return true;
			 }
			 
			 
			 else{
				 return false;
			 }
		 }
		 instrucoesUsadas.add(codigos);
		   return true;
	   }
		 
		  
		
	}
	return false;
	
}


public boolean instrucaoSalto(String codigo, String segundaParte){
	char inicial = codigo.charAt(0);
	if(inicial == 'J'|| codigo.equals("J")){
		
		String operacao = (String) instrucoes.get(codigo);
		if(usaLabel(operacao)){
			
			if(labelExiste(segundaParte)){
				instrucoesUsadas.add(operacao);
				referenciasSaltos.add(instrucoesUsadas.size()-1, segundaParte);;
				return true;
				
				
			}
		}
		
		
		
		
		
	}
	
	return false;
	
}

public boolean labelExiste(String label){
	
	
	 if( labels.contains(label)){
		 return true;
	 }
	 System.out.println(" A label " + label + "  Não foi declarada");
	 return false;
}
public boolean usaLabel(String codigos){
	char label = codigos.charAt(3);
	System.out.println(label);
	if(label == '1'){
		return true;
	}

    return false;
}

public String pegaLabel(String operacao){
	
	String termos [] = operacao.split(",");
	String label = null;
	for(int i = 0; i < termos.length; i++){
		
		if(termos[i].contains("$") || termos[i].contains(" ")){
		
		}
	
		else{
			label = termos[i];
		}
	
	}
	return label;
}


public void montar (String instrução, String operacao){
	char tipo = instrução.charAt(0);
	
	
	switch(tipo){
	
	
	case 'R' :
		
		int numReg;
		numReg = Character.getNumericValue(instrução.charAt(3)); 
			
		if(numReg == 2){
			String opcode = "000000";
			String registradorDestino = pegarRegistrador(operacao, 0);
			int regDestino = montarRegistrador(registradorDestino);
			String registrador1 = pegarRegistrador(operacao, 1);
			int reg1 = montarRegistrador(registrador1);
		}
		
		else if (numReg == 3){
			String opcode = "000000";
			String registradorDestino = pegarRegistrador(operacao, 0);
			int regDestino = montarRegistrador(registradorDestino);
			String registrador1 = pegarRegistrador(operacao, 1); // pegando o valor numerico do registrador
			int reg1 = montarRegistrador(registrador1);
			String registrador2 = pegarRegistrador(operacao,2);
	        int reg2 = montarRegistrador(registrador2);

		}
			
		
		
		
		
		
		
		
		
		
		break;
		
		
	case 'I':
		break;
		
		
	
	case 'J':
		break;
	
	
	
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}


public String pegarRegistrador(String instrucao, int pos){
	String termos[] = instrucao.split(";");
	
		String registrador = (String)registradores.get(termos[pos]);
		return registrador;
	
	
	
}

public int montarRegistrador (String registrador){
	
	int reg = (int) registradores.get(registrador);
	
	return reg;
	
}


public boolean ePseudo(String instrucao){
	if(pseudoInstrucao.containsKey(instrucao) ){
		
		return true;
	}
	
	return false;
}

public void traduzirPseudo(String operacao []){
	
	
	switch(operacao [0]){
	
	case "li":
	{
		
		
		
		break;
		
		
	}
		
		
		
		
		
	
	
	
	
	
	
	
	
	
	
	}
	
	
}








}




