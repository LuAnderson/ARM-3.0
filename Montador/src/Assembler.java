import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Assembler {

	private Hashtable registradores;
	private Hashtable instrucoes;
	private Hashtable labelEnderecada;
	private Hashtable pseudoInstrucao;

	
	private ArrayList instrucoesUsadas;
	private Hashtable memoriaAB;

	private ArrayList instrucoesUsadasSaltos;
	private ArrayList referenciasSaltos;
	private List <String> memoria = new ArrayList<String>(65536) ; // 64kbytes
    private ArrayList execução;
    private int numInstrucoes = 0;

	private Hashtable diretrizes;
	private ArrayList labels;
	private ArrayList linhas;
	  int numReg = 0;	
	  Writer fos;   
	  	String palavra;
	  	List palavras = new ArrayList();
    private String nomeArq = null;
	public Assembler() throws IOException{
		

      
		registradores = new Hashtable();
		memoriaAB = new Hashtable();
		diretrizes = new Hashtable();
		labelEnderecada = new Hashtable();
		instrucoes = new Hashtable();
        labels = new ArrayList();
        linhas = new ArrayList();
        instrucoesUsadas = new ArrayList();
		instrucoesUsadasSaltos = new ArrayList();
		referenciasSaltos = new ArrayList();
		execução = new ArrayList();
		fos  = new BufferedWriter(new FileWriter("saida.txt", false));

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
		instrucoes.put("add",  "R3000000000100000");
	    instrucoes.put("addu", "R3000000000000010");
	    instrucoes.put("clz",  "R2000000000000011");
	    instrucoes.put("clo",  "R2000000000000100");
	    instrucoes.put("move", "R2001000000000101");
	    instrucoes.put("negu", "R2001000000000110");
	    instrucoes.put("sub",  "R300000000000111");
	    instrucoes.put("subu", "R300000000001000");
	    instrucoes.put("seh",  "R200000000001001");
	    instrucoes.put("seb",  "R200000000001010");
	    //Logic
	    instrucoes.put("and",  "R300000000001011");
	    instrucoes.put("nor",  "R300000000001100");
	    instrucoes.put("not",  "R2001000000001101");
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
	  //Multiplication and Division
	  	instrucoes.put("multu", "R2000000011000");
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
	    instrucoes.put("movn", "R3001000000100010");
	    instrucoes.put("movz", "R3001000000100011");
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
	    instrucoes.put("addi",  "I3000001000");
	    instrucoes.put("addiu", "I3000001001");
	    instrucoes.put("li",    "I2001000100");
	    instrucoes.put("lui",   "I2000001111");
	    //Logic
	    instrucoes.put("andi",  "I3000001100");
	    instrucoes.put("ori",   "I3000001101");
	    instrucoes.put("xori",  "I3000001110");
	    
		//Conditionals and move
		instrucoes.put("slti",  "I3000001010");
		instrucoes.put("sltiu", "I3000001011");
		//Branches and jump
		instrucoes.put("beq",   "I3100000100");
		instrucoes.put("beqz",  "I1100000110");
		instrucoes.put("bne",   "I2100000101");
		instrucoes.put("bnez",  "I1100000111");
		//Load and store
		instrucoes.put("lb",    "I2110100000");
		instrucoes.put("lw",    "I2100100011");
		instrucoes.put("lh",    "I2110100001");
		instrucoes.put("sb",    "I2110101000");
		instrucoes.put("sh",    "I2110101001");
		instrucoes.put("sw",    "I2100101011");
		
		//J - instructions------------------------------------
		//Branches and jump
		instrucoes.put("J",     "J11000010");
		instrucoes.put("jal",   "J11000011");

	}

 public void  primeiraLeitura(String nome){
          nomeArq = nome;    // usado para leituras futuras no código	 
 
	
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
	

 public void segundaLeitura(String nome) throws IOException{
	 
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
	
		  String instrucao = codigo[0]; // separando a intrução da operação
		 
		  StringBuilder sb = new StringBuilder();
			
		  for( int j = 1; j< codigo.length; j ++){ // montando a operação
			  sb.append(codigo[j]);
				
				
			                             
			  
		  }
		  String operacao = sb.toString().trim(); // retira espaços em branco da operação
			String arraycont [];
           String codigos = (String) instrucoes.get(instrucao);
           System.out.println(instrucao);
     
		  if(codigos.charAt(4) == '1'){
	    		String traducao[] =	traduzirPseudo(instrucao,operacao );
	    		if(traducao[1]!= null){
	    			String instruNova[] = traducao[0].split(" ");
		    		 instruNova[1] = instruNova[1].trim();
		    		arraycont = instruNova[1].split(",");
		    		 if( !instrucaoValida( instruNova[0], arraycont.length ,instruNova[1])){

		 				System.out.println(" O montador será encerrado!!!!");
		 				break;
		 				
		 			}
		    		 
		    		 
		    		 instruNova = traducao[1].split(" ");
		    		 instruNova[1] = instruNova[1].trim();
		    		 arraycont = traducao[1].split(",");
		    		 if( !instrucaoValida( instruNova[0], arraycont.length ,instruNova[1])){
		 				
		    			 
		    			 System.out.println(" O montador será encerrado!!!!");
		 				break;
		 				
		 			}
	    			
	    		}
	    		else if(traducao[1] == null){
	    			
	    			
	    			String instruNova[] = traducao[0].split(" ");
		    		 instruNova[1] = instruNova[1].trim();
		    		arraycont = instruNova[1].split(",");
		    		 if( !instrucaoValida( instruNova[0], arraycont.length ,instruNova[1])){

		 				System.out.println(" O montador será encerrado!!!!");
		 				break;
		 				
		 			}
	    			
	    			
	    			
	    			
	    			
	    			
	    		}
	    		
	    		}
		  
		  
		  else{
			  
			if(operacao.contains(",")){
				arraycont = operacao.split(",");
               if( !instrucaoValida( instrucao, arraycont.length ,operacao)){
					
					System.out.println(" O montador será encerrado!!!!");
					break;
					
				}
			 
			}
				
			else{
if( !instrucaoValida( instrucao, 1 ,operacao)){
					
					System.out.println(" O montador será encerrado!!!!");
					break;
					
				}
				
			}
			  
			  
		  }
		
			 
								
			
		
		
		 

	 
	 }
	 
	 
		for( int i = 0; i< palavras.size(); i ++){
			String aux = (String)palavras.get(i);
			fos.append(aux + "\r\n");
		}
	 
	 fos.close();
	 
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
    			
    			if(array[i+1] == 'z'){
        			StringBuilder sb = new StringBuilder();
        			sb.append(array[i]);
        			sb.append(array[i +1]);
        			sb.append(array[i +2]);
        			sb.append(array[i +3]);
        			sb.append(array[i +4]);
        			nomeRegistrador = sb.toString();
        			
        			
        		}
    		
    			else{
    				
    				StringBuilder sb = new StringBuilder();
        			sb.append(array[i]);
        			sb.append(array[i +1]);
        			sb.append(array[i +2]);
        			nomeRegistrador = sb.toString();
    				
    			}
    			
    			
    			
    			
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
	
	
	
}

public boolean eDiretriz(String linha){
	if(linha.charAt(0) == '.'){
		return true;
	}
   
    	return false;
    	


}
  
public boolean instrucaoValida(String codigo, int para, String segundaParte) throws IOException{
	
	if(instrucoes.containsKey(codigo)){
		String codigos = (String) instrucoes.get(codigo);
		char parametros = codigos.charAt(1);
		int numparametros =  Character.getNumericValue(parametros);
		segundaParte = segundaParte.trim();
		
		

if(instrucaoSalto(codigo, segundaParte)){

			 return true;
	 }
else if(para == numparametros){
		 if( usaLabel(codigos)){
			 
			 String label = pegaLabel(segundaParte);
			 
			 if(labelExiste(label)){
				 
				 instrucoesUsadas.add(codigos);
				 numInstrucoes++;
				 memoria.add(codigos);
				 montar (codigos, segundaParte);

				   return true;
			 }
			 
			 
			 else{
				 return false;
			 }
		 }
		 instrucoesUsadas.add(codigos);
		 numInstrucoes++;
		 memoria.add(codigos);
		 montar (codigos, segundaParte);
  
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
				//referenciasSaltos.add(instrucoesUsadas.size()-1, segundaParte);;
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


public void montar (String instrução, String operacao) throws IOException{
	char tipo = instrução.charAt(0);
	enderecarLabel();
	int instrucaoFinal [] = new int [32];
	int shamt;
	int numReg;
	String opcode;
	int function [];
	char auxiliar[] = null;
      Writer fos  = new BufferedWriter(new FileWriter("saida.txt", false));   
  	String palavra;
	switch(tipo){
	
	
	case 'R' :
	{
		char instruc[] = instrução.toCharArray();
		numReg = Character.getNumericValue(instruc[1]); 
		int registradorDestino;
		int registrador1;
		int registrador2;
		 opcode = "000000";
		 auxiliar = opcode.toCharArray();
		
		 if(numReg ==1){
			shamt = 0;
			 registradorDestino = pegarRegistrador(operacao, 0);
				instrucaoFinal = montarRegistrador(instrucaoFinal,0,0,registradorDestino,true);
				 function = getFunction(instruc);
				 instrucaoFinal = montarDeslocamento(shamt,instrucaoFinal);
					instrucaoFinal = montarFunction(instrucaoFinal,function);
					
			 
			 
		 }
		 
		 if(numReg == 2){
			
			
			
			 registradorDestino = pegarRegistrador(operacao, 0);
			 registrador1 = pegarRegistrador(operacao, 1);
			  function = getFunction(instruc);
				char deslocamento = instrução.charAt(2);;
				if(deslocamento == '1'){
				shamt = pegarConstante(operacao);
					
					
				}
				
				else{
					shamt = 0;
				}
				
				for(int i = 0; i<6 ; i++){
					instrucaoFinal[i] = Character.getNumericValue(auxiliar[i]); // opcode
					
				}
				instrucaoFinal = montarRegistrador(instrucaoFinal,registrador1,0,registradorDestino,true);
				instrucaoFinal = montarDeslocamento(shamt,instrucaoFinal);
				instrucaoFinal = montarFunction(instrucaoFinal,function);
				
				 
	           
	            
	         
				    
			 palavra = Integer.toString(instrucaoFinal[0]); 
				 
				 
			for(int i = 1; i< instrucaoFinal.length; i++){
				palavra = palavra +instrucaoFinal[i]; 
				
			      
				
				System.out.println(instrucaoFinal[i -1]);
			}
			
			
			
			
		 }
		
		else if (numReg == 3){
			shamt = 0;
			
			function = getFunction(instruc);
			
			
			

				registradorDestino = pegarRegistrador(operacao, 0);
				if(registradorDestino == 29){
				
				
				registrador1 = pegarRegistrador(operacao, 1); // pegando o valor numerico do registrador
				int constante = pegarConstante(operacao);
				 instrucaoFinal = montarRegistrador(instrucaoFinal,registrador1,registradorDestino,constante,false);
					instrucaoFinal = montarConstante( constante, instrucaoFinal);
			
					 palavra = Integer.toString(instrucaoFinal[0]); 
						
						for(int i = 1; i< instrucaoFinal.length; i++){
							
							palavra = palavra +instrucaoFinal[i]; 
							System.out.println(instrucaoFinal[i-1]);
						}
				
				
						palavras.add(palavra);
			
				}
			
			
			
			else{
				
				registradorDestino = pegarRegistrador(operacao, 0);
				 registrador1 = pegarRegistrador(operacao, 1); // pegando o valor numerico do registrador
				 registrador2 = pegarRegistrador(operacao,2);
		        
				 instrucaoFinal = montarRegistrador(instrucaoFinal,registrador1,registrador2,registradorDestino,true);
					instrucaoFinal = montarDeslocamento(shamt,instrucaoFinal);
					instrucaoFinal = montarFunction(instrucaoFinal,function);
				
					 palavra = Integer.toString(instrucaoFinal[0]); 
					
					for(int i = 1; i< instrucaoFinal.length; i++){
						
						palavra = palavra +instrucaoFinal[i]; 
						System.out.println(instrucaoFinal[i-1]);
					}
			
			
					palavras.add(palavra);
			}
			
			
				 
				
		        
		}
			
		
		
		
		
		
		
		
		break;
	}	
		
	case 'I':
	{
		
		char instruc[] = instrução.toCharArray();
		char regOrigem [] = new char [3];
		
		numReg = Character.getNumericValue(instruc[1]); 
        System.out.println("Registrador : " + numReg);
		int registradorDestino = 0;
		int registrador1 = 0;
		int registrador2 = 0;
		 int constante = 0;
		instrucaoFinal =  montarOpcode(instrução,instrucaoFinal);
	   
		
		
		if(instrucaoFinal[0] == '1'){
	    	registradorDestino = pegarRegistrador(operacao, 0);
	    	String aux [] = operacao.split(",");
	    	String formato  = aux[1].trim();
	    	int offset = Character.getNumericValue(aux [1].charAt(0));
	    	 regOrigem [0] = aux[1].charAt(2); 
	    	 regOrigem [1] = aux[1].charAt(3);
	    	 regOrigem [2]  = aux[1].charAt(4); 
	    	 instrucaoFinal = montarRegistrador(instrucaoFinal,registrador1,registradorDestino,offset,false);
				instrucaoFinal = montarConstante( offset, instrucaoFinal);
 	    	
	    }
			
	    else if(numReg == 3){
				System.out.println(operacao);
				 registradorDestino = pegarRegistrador(operacao, 0);
				 registrador1 = pegarRegistrador(operacao, 1);
	   			  constante = pegarConstante(operacao);	
			
	   			instrucaoFinal = montarRegistrador(instrucaoFinal,registrador1,registradorDestino,constante,false);
				instrucaoFinal = montarConstante( constante, instrucaoFinal);
			 
			 
			 }
			
			else if(numReg == 2){
				
				System.out.println(operacao);
				 registradorDestino = pegarRegistrador(operacao, 0);
			     constante = pegarConstante(operacao);	
			        instrucaoFinal = montarRegistrador(instrucaoFinal,0,registradorDestino,constante,false);
					instrucaoFinal = montarConstante( constante, instrucaoFinal);
			    
			}

            
			
				 			
			  palavra = Integer.toString(instrucaoFinal[0]); 
				
				
			for(int i = 1; i< instrucaoFinal.length; i++){
				palavra = palavra +instrucaoFinal[i]; 
				
		
				System.out.println(instrucaoFinal[i-1]);
			}
		
			palavras.add(palavra);

		
			
			 
		}
	break;
	case 'J':
		
		if(instrução.equals("J")){
			
			instrucaoFinal =  montarOpcode(instrução,instrucaoFinal);
			String label = pegaLabel(operacao);
			int constante = (int) labelEnderecada.get(label);
			instrucaoFinal = montarConstante(constante,instrucaoFinal);
			
			 palavra = Integer.toString(instrucaoFinal[0]); 
			for(int i = 1; i< instrucaoFinal.length; i++){
				palavra = palavra +instrucaoFinal[i]; 
				System.out.println(instrucaoFinal[i-1]);
			}
			
			 
			palavras.add(palavra);

		}
		 

		if(instrução.equals("jal")){
			
			instrucaoFinal =  montarOpcode(instrução,instrucaoFinal);
			String label = pegaLabel(operacao);
			int constante = (int) labelEnderecada.get(label);
			instrucaoFinal = montarConstante(constante,instrucaoFinal);
			
			 palavra = Integer.toString(instrucaoFinal[0]); 
			for(int i = 1; i< instrucaoFinal.length; i++){
				palavra = palavra +instrucaoFinal[i]; 
				System.out.println(instrucaoFinal[i-1]);
			}
			
			 
			palavras.add(palavra);

		}
		
		
		
		
		
		break;
	
	
	
		
		
	}
	
	
	
	
	
	
	
fos.close();	
	
	
	
	
}



private int[] montarConstante(int constante, int instrucaoFinal[]) {
	String convertido = converterBinario(constante);
	
	String nova = complemento(convertido,16);
	char array [] = nova.toCharArray();
	for(int i = 0; i< 16; i++){
	
		
		instrucaoFinal[i + 16] = Character.getNumericValue(array[i]);		
	}
	
	return instrucaoFinal;
}


public int pegarRegistrador(String instrucao, int pos){
	String termos[] = instrucao.split(",");
	
	System.out.println(instrucao);	
	int registrador = (int) registradores.get(termos[pos]);
		
		return registrador;
	
	
	
}

public int [] montarRegistrador(int instrucaoFinal[],int registradorDestino,int registrador1, int registrador2, boolean flag){
	
	String convertido = converterBinario(registradorDestino);
	String nova = complemento(convertido,5);
	char array [] = nova.toCharArray();
	for(int i = 0; i<5; i++){
		instrucaoFinal[i + 6] = Character.getNumericValue(array[i]);
	}
	
	
	convertido = converterBinario(registrador1);
	nova = complemento(convertido,5);
	array = nova.toCharArray();
	for(int i = 0; i<5; i++){
		instrucaoFinal[i + 11] = Character.getNumericValue(array[i]);
	}
	
		if(flag ){
			convertido = converterBinario(registrador2);
			nova = complemento(convertido,5);
			array = nova.toCharArray();
			for(int i = 0; i<5; i++){
				instrucaoFinal[i + 16] = Character.getNumericValue(array[i]);
			}
			
			return instrucaoFinal;
			
			
			
		}
		
		
		else{
			convertido = converterBinario(registrador2);
			nova = complemento(convertido,16);
			array = nova.toCharArray();
			for(int i = 0; i<5; i++){
				instrucaoFinal[i + 16] = Character.getNumericValue(array[i]);
			}
			
			return instrucaoFinal;
			
		}
		
	

	
	
	




}

public int [] montarDeslocamento(int shamt, int instrucaoFinal []){
	

	String des = converterBinario(shamt);
    String nova = complemento(des,5);
    char aux [] = nova.toCharArray();
    
    for( int i = 0; i< 5; i++){
    	instrucaoFinal[i+21] =Character.getNumericValue(aux[i]) ;
    }
	
  return instrucaoFinal;

}

public int [] montarOpcode(String instrução, int instrucaoFinal []){
	

	char op [] = instrução.toCharArray();
	for(int i = 0; i< 6; i ++){
		
		instrucaoFinal[i] = Character.getNumericValue(op[i +5]);
		
		
		
	}
	
	return instrucaoFinal;
    

}




public int pegarConstante (String instrucao){ 
	String termos[] = instrucao.split(",");
	String aux = termos[termos.length -1];
	char resultado[] = aux.toCharArray();
	int constante =  Character.getNumericValue(resultado[0]);
	return constante;
}

public int montarRegistrador (String registrador){
	
	int reg = (int) registradores.get(registrador);
	
	return reg;
	
}

public int [] montarFunction(int instrucaoFinal[], int function[]){
	for(int i = 0; i< 6; i++){
		
		instrucaoFinal[i+26] = function[i];
		
		
	}
	
	return instrucaoFinal;
}

public int [] montarEndereco(int instrucaoFinal[], int endereco){
String convertido =	converterBinario(endereco);
String end = complemento (convertido, 26);	
char array[] = end.toCharArray();
	for(int i = 0; i< 26; i++){
		
		instrucaoFinal[i +6] = array[i];
		
		
	}
	
	return instrucaoFinal;
}

public boolean ePseudo(String instrucao){
	if(pseudoInstrucao.containsKey(instrucao) ){
		
		return true;
	}
	
	return false;
}

public int [] getFunction(char [] instrucao){
	int function [] = new int[6];
	for(int i = 0; i < 6; i++){
		function[i] = Character.getNumericValue(instrucao[i + 10]);
		
	}

  return function;

}


public void enderecarLabel(){

	 try {
	      FileReader arq = new FileReader(nomeArq);
	      BufferedReader lerArq = new BufferedReader(arq);
	 
	      String linha = lerArq.readLine(); 
	     
	      while (linha != null) {
	        
	        linha = lerArq.readLine();
	        if(linha == null){
	        	break;
	        }
	
	     if(linha.contains(":")){
		     String array [] = linha.split(":");
		     String label = array[0];
	    	 linha = lerArq.readLine();

	    	 if(linha!=null){
	    		 String temp [] = linha.split(" ");
	    		 String codigos = (String) instrucoes.get(temp[0]);
	    		 int endereco = memoria.indexOf(codigos);
	    		 labelEnderecada.put(label, endereco);
	    		 
	    	 }
	     }
	     else{
		        linha = lerArq.readLine();

	     }
	      
	      
	      
	      }
	        arq.close();
		    } catch (IOException e) {
		        System.err.printf("Erro na abertura do arquivo: %s.\n",
		          e.getMessage());
		    }
	      
	      
	      
	      
	      }

public String [] traduzirPseudo(String operacao, String segundaParte ){
	
	String instrucao [] = new String[2];
	
	switch(operacao){
	
	case "li":
	{
		String registrador[] = segundaParte.split(",");
		segundaParte = segundaParte.trim();

		int constante = pegarConstante(segundaParte);
		
		
		instrucao[0] = "lui $at";
		instrucao[0] =  instrucao[0]+ ","+ constante;
		
		
		instrucao[1] = "ori " + registrador[0]+",$at"+","+ constante;
		
		
		break;
		
		
	}
	
	
	case "move":
		
	{
		String registrador[] = segundaParte.split(",");
		segundaParte = segundaParte.trim();
         String regDestino = registrador[0];
         String regOrigem = registrador [1];
         int constante = 0; 
		
		instrucao[0] = "addi " + regDestino + "," + regOrigem +","+ constante;
		
		
		
		
		
		
		
		
		
		
		
		break;
	}
		
		
	case "not" :
		
	{
		String registrador[] = segundaParte.split(",");
		segundaParte = segundaParte.trim();
         String regDestino = registrador[0];
         String regOrigem = registrador [1];
		
		instrucao[0] = "addi " + regDestino + "," + regOrigem +","+ "$zero";
		
		
		
		
		
		break;
		
		
			
	}
	
	case "movn" :
	{
		String registrador[] = segundaParte.split(",");
		segundaParte = segundaParte.trim();
         String regDestino = registrador[0];
         String regOrigem = registrador [1];
		
		instrucao[0] = "beq " + regDestino + "," + regOrigem +","+ 1;
		instrucao[0] = "addi " + regDestino;
		
		
		
		
		break;
	}
	
	
	
	
	}
	return instrucao;
	
}



public String converterBinario(int valor){
	
	
	
	int resto = -1;
	   StringBuilder sb = new StringBuilder();
	 
	   if (valor == 0) {
	      return "0";
	   }
	 
	   // enquanto o resultado da divisão por 2 for maior que 0 adiciona o resto ao início da String de retorno
	   while (valor > 0) {
	      resto = valor % 2;
	      valor = valor / 2;
	      sb.insert(0, resto);
	   }
	 
	   return sb.toString();
}

public String complemento (String convertido, int total){
	 StringBuilder sb = new StringBuilder();
	char auxiliar [] = convertido.toCharArray();
	if(auxiliar.length != total){
	int complemento = total - auxiliar.length;
	for(int i = 0 ; i < complemento; i++){

	sb.append("0");
	
	}
	
	return sb + convertido;
	
	
	}
	return convertido;
}


}




