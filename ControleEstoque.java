package controleEstoque;

import javax.swing.JOptionPane;

public class ControleEstoque {

	public static void main(String[] args) {
		menu();											
		}												  
																																
	public static void menu(){							
		double codVal [][] = new double [8][100];		
		String nomeProd [] = new String [100];			
		int indiceVazio=0, op;							
														 
														 
		do{ 
			op = Integer.parseInt(JOptionPane.showInputDialog(
					"                Sistema de controle de estoque\n\n\n"+
					"                              Menu principal \n\n" +
					"1  - Cadastrar produto \n"+ "" +
					"2  - Cadastrar entradas de produtos \n"+
					"3  - Cadastrar vendas de produtos \n" +
					"4  - Consulta de produtos (Informando código)\n" +
					"5  - Consulta de produtos (Informando nome) \n" +
					"6  - Emitir relatório de compras \n" +
					"7  - Emitir relatório de vendas/faturamento \n" +
					"8  - Emitir relatório de estoque \n" +
					"9  - Alterar preços de produto (custo/venda)\n"+
					"10 - Mostrar todas as informações cadastradas \n"+
					/*"999 - Cadastro automático de 7 produtos para teste\n"+*/
					"11 - Sair do Sistema"));
	
			switch (op) {
			
			case 1:
				indiceVazio = cadastro(nomeProd, codVal, indiceVazio);
				break;
			
			case 2:
				cadEntradas(nomeProd, codVal, indiceVazio);
				break;
			
			case 3:
				cadVendas (nomeProd, codVal, indiceVazio);
				break;
				
			case 4:
				consProdCod(nomeProd, codVal, indiceVazio);
				break;
				
			case 5:
				consProdNome(nomeProd, codVal, indiceVazio);
				break;	
				
			case 6:
				relCompras(nomeProd, codVal, indiceVazio);
				break;
				
			case 7:
				relVendas(nomeProd, codVal, indiceVazio);
				break;
				
			case 8:
				relEstoque(nomeProd, codVal, indiceVazio);
				break;
				
			case 9:
				alteraValores(nomeProd, codVal, indiceVazio);
				break;
				
			case 10:
				mostraTodos(nomeProd, codVal, indiceVazio);
				break;
			
			case  11:
				JOptionPane.showMessageDialog(null, "Finalizando o sistema...");
				break;
				
			case 999:
				indiceVazio = autoCadastro(nomeProd, codVal, indiceVazio);
				break;
				
			default:
				JOptionPane.showMessageDialog(null, "Código inválido");
				break;
			}
			
		}while(op!=11);
	}
	
	private static int cadastro (String nomeProd[], double codVal[][], int indiceVazio) {
		char resp='s', confirmaCad;
		int indiceCad, indiceNomeCad;
		double prCusto, valVenda, codCad;
		String nomeCad;
		
		do {
			if (indiceVazio<nomeProd.length) {
				nomeCad = JOptionPane.showInputDialog("Digite o nome do produto: ");

				indiceCad=procNome(nomeProd, codVal, indiceVazio, nomeCad); 
				if (indiceCad != -1) { 

					JOptionPane.showMessageDialog(null, "Produto já cadastrado com o código: "+codVal[1][indiceCad]); 
				}
				else {
					codCad = Double.parseDouble(JOptionPane.showInputDialog("Digite o código do produto"));

					indiceNomeCad = procCod(nomeProd, codVal, indiceVazio, codCad); 
					if (indiceNomeCad != -1) {

						JOptionPane.showMessageDialog(null, "Código já cadastrado para o produto: "+nomeProd[indiceNomeCad]);
					}
					else {
						prCusto = Double.parseDouble(JOptionPane.showInputDialog("Digite o preço de custo: "));
						valVenda = Double.parseDouble(JOptionPane.showInputDialog("Digite o valor de venda: "));
						
						confirmaCad = (JOptionPane.showInputDialog(
								"           Confirmação de cadastro"+"\n\n"+
								"Código: "+codCad+"\n"+
								"Nome do produto: "+nomeCad+"\n"+
								"Preço de custo: R$"+prCusto+"\n"+
								"Valor de venda: R$"+valVenda+"\n\n"+
								"Confirma entrada dos dados (S/N)?")).charAt(0);
						
						if ((confirmaCad=='s')||(confirmaCad=='S')){
						
						execCad(nomeProd, codVal, indiceVazio, codCad, nomeCad, prCusto, valVenda);
						indiceVazio=(indiceVazio+1);
						}
					}
				}
			
				resp = (JOptionPane.showInputDialog("Deseja cadastrar outro produto (S/N)?")).charAt(0);
			}
			else {
				JOptionPane.showMessageDialog(null, "Limite de cadastros alcançado");
				resp = 'n';
			}
		} while ((resp=='s') || (resp=='S')); 
		return indiceVazio;
	}	
	
	private static int procCod (String nomeProd[], double codVal[][], int indiceVazio, double buscaCod ) {
		int i=0;
		
		while ((i<indiceVazio)&&(buscaCod!=codVal[1][i])) {
			i++;
		}
		if (buscaCod!=codVal[1][i]) {
			return -1;
		}
			else {
				return i;
		}
	}
	
	
	private static int procNome(String nomeProd[], double codVal[][], int indiceVazio, String buscaNome) {
		int i=0;
		
		while ((i<indiceVazio)&&(!(buscaNome.equalsIgnoreCase(nomeProd[i])))) {
			i++;
		}
		if (!(buscaNome.equalsIgnoreCase(nomeProd[i]))) {
			return -1;
		}
			else {
				return i;
			}	
		}	

	private static void execCad (String nomeProd[], double codVal[][], int indice, double cod, String nome, 
								double prCusto, double valVenda) {
		double lucrUn;
	
		lucrUn = valVenda - prCusto;
		nomeProd[indice] = nome;
		codVal[1][indice] = cod;
		codVal[5][indice] = prCusto;
		codVal[6][indice] = valVenda;
		codVal[7][indice] = lucrUn;
	}		
	
	private static void cadEntradas(String nomeProd[], double codVal[][], int indiceVazio){
		double codEntr;
		char resp = 's', confirmaEntr;
		int achouProd;
		double qtdadeEntr;
		
		do {
		codEntr = Double.parseDouble(JOptionPane.showInputDialog("Informe o código do produto: "));
		achouProd = procCod(nomeProd, codVal, indiceVazio, codEntr);
		if (achouProd != -1) {
			qtdadeEntr = Double.parseDouble(JOptionPane.showInputDialog("Informe a quantidade da entrada do produto: "+nomeProd[achouProd]+"\n" ));
			
			confirmaEntr = (JOptionPane.showInputDialog(
					"           Confirmação de entrada"+"\n\n"+
					"Código: "+codVal[1][achouProd]+"\n"+
					"Nome do produto: "+nomeProd[achouProd]+"\n"+
					"Quantidade da entrada: "+qtdadeEntr+"\n"+
					"Valor da entrada: R$"+(qtdadeEntr*codVal[5][achouProd])+"\n\n"+
					"Confirma entrada dos dados (S/N)?")).charAt(0);
			if ((confirmaEntr=='s')||(confirmaEntr=='S')){
			execCadEntr(nomeProd, codVal, achouProd, codEntr, qtdadeEntr);
			}
		}
			else { 
				JOptionPane.showMessageDialog(null,
						"                        O código informado: "+codEntr+ " não foi localizado. \n" +
						"          Certifique-se de que você digitou o código corretamente \n " +
						"                             e que o produto esteja cadastrado.");
			}
		resp = (JOptionPane.showInputDialog("Deseja cadastrar outra entrada (S/N)?")).charAt(0);
			
		}while ((resp=='s') || (resp=='S'));
		}
	
	
	private static void execCadEntr(String nomeProd[], double codVal[][], int indProd, double codEntr, double qtdade){
		
		codVal[2][indProd] = (codVal[2][indProd]+qtdade);
		codVal[4][indProd] = (codVal[4][indProd]+qtdade);
	}
	
	private static void cadVendas (String nomeProd[], double codVal[][], int indiceVazio){
		double codVend;
		char resp = 's', confirmaVenda = 'n';
		int achouProd;
		double qtdadeVend, qtEstoque;
		
		do {
		codVend = Double.parseDouble(JOptionPane.showInputDialog("Informe o código do produto: "));
		achouProd = procCod(nomeProd, codVal, indiceVazio, codVend);
		if (achouProd != -1) {
			qtEstoque = verEstoque(codVal, achouProd);
			if (qtEstoque <=0.0){
				JOptionPane.showMessageDialog(null, "O produto informado: "+nomeProd[achouProd]+" não está em estoque!");
			}
				else { 
					qtdadeVend = Double.parseDouble(JOptionPane.showInputDialog("" +
											"Informe a quantidade vendida do produto: "+nomeProd[achouProd]));
				
						if (qtdadeVend > qtEstoque) {
							JOptionPane.showMessageDialog(null, "A quantidade em estoque do produto: "+nomeProd[achouProd]+ " é somente: " +qtEstoque);
						}
						if (qtdadeVend <= qtEstoque){					
							
					confirmaVenda = (JOptionPane.showInputDialog(
											"Código: "+codVal[1][achouProd]+"\n"+
											"Produto: "+nomeProd[achouProd]+"\n" +
											"Quantidade Vendida: "+qtdadeVend+"\n"+
											"Valor unitário: R$"+codVal[6][achouProd]+"\n"+
											"Valor total: R$"+(codVal[6][achouProd]*qtdadeVend)+"\n"+
											"Confirma dados da venda (S/N)?")).charAt(0);
						}
					if ((confirmaVenda == 's') || (confirmaVenda == 'S')) {					
							execCadVenda(codVal, achouProd, qtdadeVend);
					}
				}
		}
			else {
				JOptionPane.showMessageDialog(null, "O código informado: "+codVend+ " é inválido");
			}
		resp = (JOptionPane.showInputDialog("Deseja cadastrar outra venda (S/N)?")).charAt(0);
	
		}while ((resp =='s') || (resp =='S'));
	
	}
		
	private static double verEstoque (double codVal[][], int achouProd) {
		double estoque;
		
		estoque = codVal[4][achouProd];
		
		return estoque ;
	}

	private static void execCadVenda(double codVal[][], int indProd, double qtdade){
		
		codVal[3][indProd] = (codVal[3][indProd]+qtdade);
		codVal[4][indProd] = (codVal[4][indProd]-qtdade);
	}	

	private static void consProdCod (String nomeProd[], double codVal[][], int indiceVazio){
		double codProcura;
		int achou;
		char resp = 's';
		
		do{
		codProcura = Double.parseDouble(JOptionPane.showInputDialog("Digite o código do produto: "));
		achou = procCod (nomeProd, codVal, indiceVazio, codProcura );
		if (achou == -1){
			JOptionPane.showMessageDialog(null, "Não foi encontrado produto com o código informado: "+codProcura);
		}
		else{
			JOptionPane.showMessageDialog(null, 
									"Código: "+codVal[1][achou]+"\n"+
									"Nome do produto: "+nomeProd[achou]+"\n"+
									"Preço de custo: R$"+codVal[5][achou]+"\n"+
									"Valor de venda: R$"+codVal[6][achou]+"\n"+
									"Quantidade em estoque: "+codVal[4][achou]+"\n"+
									"Quantidade comprada: "+codVal[2][achou]+"\n"+
									"Quantidade vendida: "+codVal[3][achou]+"\n"+
									"Valor das compras do produto: R$"+(codVal[2][achou]*codVal[5][achou])+"\n"+
									"Valor das vendas do produto: R$"+(codVal[3][achou]*codVal[6][achou])+"\n"+
									"Lucro obtido com vendas deste produto: R$"+((codVal[3][achou]*codVal[6][achou])-(codVal[5][achou]*codVal[3][achou]))+"\n"+
									"Valor de custo em estoque do produto: R$"+(codVal[4][achou]*codVal[5][achou])+"\n"+
									"Valor de venda em estoque do produto: R$"+(codVal[4][achou]*codVal[6][achou]));
		}
		resp = (JOptionPane.showInputDialog("Deseja pesquisar outro produto (S/N)?")).charAt(0);
		
		}while ((resp =='s') || (resp =='S')); 
	}

	private static void consProdNome (String nomeProd[], double codVal[][], int indiceVazio){
		String nomeProcura;
		int achou;
		char resp = 's';
		
		do{
		nomeProcura = JOptionPane.showInputDialog("Digite o nome do produto: ");
		achou = procNome (nomeProd, codVal, indiceVazio, nomeProcura );
		if (achou == -1){
			JOptionPane.showMessageDialog(null, "Não foi encontrado produto com o nome informado: "+nomeProcura);
		}
		else{
			JOptionPane.showMessageDialog(null, 
									"Código: "+codVal[1][achou]+"\n"+
									"Nome do produto: "+nomeProd[achou]+"\n"+
									"Preço de custo: R$"+codVal[5][achou]+"\n"+
									"Valor de venda: R$"+codVal[6][achou]+"\n"+
									"Quantidade em estoque: "+codVal[4][achou]+"\n"+
									"Quantidade comprada: "+codVal[2][achou]+"\n"+
									"Quantidade vendida: "+codVal[3][achou]+"\n"+
									"Valor das compras do produto: R$"+(codVal[2][achou]*codVal[5][achou])+"\n"+
									"Valor das vendas do produto: R$"+(codVal[3][achou]*codVal[6][achou])+"\n"+
									"Lucro obtido com vendas deste produto: R$"+((codVal[3][achou]*codVal[6][achou])-(codVal[5][achou]*codVal[3][achou]))+"\n"+
									"Valor de custo em estoque do produto: R$"+(codVal[4][achou]*codVal[5][achou])+"\n"+
									"Valor de venda em estoque do produto: R$"+(codVal[4][achou]*codVal[6][achou]));
		}
		resp = (JOptionPane.showInputDialog("Deseja pesquisar outro produto (S/N)?")).charAt(0);
		
		}while ((resp =='s') || (resp =='S')); 
	}	

	private static int verMov(String nomeProd[], double codVal[][], int indiceVazio, int tipoMov, int indPesq){ //tipoMov é 2 compras, 3 vendas, 4 estoque 
		
		
		if (codVal[tipoMov][indPesq]>0){
			return indPesq;
		}
		else{
			return -1;
		}
	}

	private static void relCompras(String nomeProd[], double codVal[][], int indiceVazio){
		int tipoMov=2, indice=0, indPesq=0, i=0;
		double valorCompras=0;
		
		do{
			
			indice = verMov(nomeProd, codVal, indiceVazio, tipoMov, indPesq);
			if (indice!=-1){
				
				JOptionPane.showMessageDialog(null,
					"                Relatório de compras"+"\n\n"+	
					"Código: "+codVal[1][indice]+"\n"+
					"Nome do produto: "+nomeProd[indice]+"\n"+
					"Preço de custo: R$"+codVal[5][indice]+"\n"+
					"Quantidade comprada: "+codVal[2][indice]+"\n"+
					"Valor das compras do produto: R$"+(codVal[2][indice]*codVal[5][indice]));
			}
			valorCompras = (calcValores(nomeProd, codVal, indiceVazio, tipoMov, 5, indPesq)+valorCompras); 
			indPesq = (indPesq+1);
			
			i++;
		}while (i<indiceVazio);
			
			JOptionPane.showMessageDialog(null, 
					"             Relatório de compras"+"\n\n"+
					"Valor total das compras: R$"+valorCompras);
	}

	private static double calcValores (String nomeProd[], double codVal[][], int indiceVazio, int tipoMov, int preco, int indPesq){
		double valor=0;
		int indice;
		
		indice = verMov(nomeProd, codVal, indiceVazio, tipoMov, indPesq);
		if (indice!=-1){
			valor = (valor+(codVal[preco][indice]*codVal[tipoMov][indice]));
		}		
		return valor;
	}

	private static void relVendas (String nomeProd[], double codVal[][], int indiceVazio){
		int tipoMov=3, indice=0, indPesq=0, i=0;
		double valorVendas=0, custoVendas=0, faturamento=0;
		
		do{
			
			indice = verMov(nomeProd, codVal, indiceVazio, tipoMov, indPesq);
			if (indice!=-1){
				
				JOptionPane.showMessageDialog(null, 
					"    Relatório de vendas/faturamento"+"\n\n"+	
					"Código: "+codVal[1][indice]+"\n"+
					"Nome do produto: "+nomeProd[indice]+"\n"+
					"Valor de venda: R$"+codVal[6][indice]+"\n"+
					"Quantidade vendida: "+codVal[3][indice]+"\n"+
					"Valor das vendas do produto: R$"+(codVal[3][indice]*codVal[6][indice]));
			}
			valorVendas = (calcValores(nomeProd, codVal, indiceVazio, tipoMov, 6, indPesq)+valorVendas); 
			custoVendas = (calcValores(nomeProd, codVal, indiceVazio, tipoMov, 5, indPesq)+custoVendas);
			indPesq = (indPesq+1);
			
			i++;
		}while (i<indiceVazio);
			
			faturamento = (valorVendas-custoVendas); 
				
			JOptionPane.showMessageDialog(null, 
					"  Relatório de vendas/faturamento"+"\n\n"+
					"Valor total das vendas: R$"+valorVendas+"\n"+
					"Lucro líquido: R$"+faturamento);
	}

	private static void relEstoque(String nomeProd[], double codVal[][], int indiceVazio){
		int tipoMov=4, indice=0, indPesq=0, i=0; //tipoMov (4) representa quantidade em estoque
		double valorCustoEstoque=0, valorVendaEstoque=0;
		
		do{
			
			indice = verMov(nomeProd, codVal, indiceVazio, tipoMov, indPesq);
			if (indice!=-1){
				
				JOptionPane.showMessageDialog(null, 
					"                      Relatório de estoque"+"\n\n"+	
					"Código: "+codVal[1][indice]+"\n"+
					"Nome do produto: "+nomeProd[indice]+"\n"+
					"Quantidade em estoque: "+codVal[4][indice]+"\n"+
					"Preço de custo: R$"+codVal[5][indice]+"\n"+
					"Preço de venda: R$"+codVal[6][indice]+"\n"+
					"Valor de custo do estoque do produto: R$"+(codVal[5][indice]*codVal[4][indice])+"\n"+
					"Valor de venda do estoque do produto: R$"+(codVal[6][indice]*codVal[4][indice]));
			}
			valorCustoEstoque = (calcValores(nomeProd, codVal, indiceVazio, tipoMov, 5, indPesq)+valorCustoEstoque); 
			valorVendaEstoque = (calcValores(nomeProd, codVal, indiceVazio, tipoMov, 6, indPesq)+valorVendaEstoque);
				
			indPesq = (indPesq+1);
			
			i++;
		}while (i<indiceVazio);
		
		JOptionPane.showMessageDialog(null,     
												"                                 Totais do estoque \n\n"+
												"Valor dos produtos em estoque (preço de custo): R$"+valorCustoEstoque+"\n"+
												"Valor dos produtos em estoque (valor de venda): R$"+valorVendaEstoque+"\n"+
												"Possível lucro com a venda total do estoque: R$"+(valorVendaEstoque-valorCustoEstoque))
												;
		
	}

	private static void alteraValores(String nomeProd[], double codVal[][], int indiceVazio){
		double prCusto=0,valVenda=0, codProd;
		int indice;
		char confirma='n';
		
		codProd = Double.parseDouble(JOptionPane.showInputDialog("Digite o código do produto que deseja alterar: "));
		indice = procCod(nomeProd, codVal, indiceVazio, codProd);
		if (indice == -1){
			JOptionPane.showMessageDialog(null, "O código informado: "+codProd+" não foi localizado");
		}
			else{
				confirma = (JOptionPane.showInputDialog( 
						"          Dados atuais do produto: \n\n"+
						"Código: "+codProd+"\n"+
						"Nome do produto: "+nomeProd[indice]+"\n"+
						"Preço de custo: R$"+codVal[5][indice]+"\n"+
						"Valor de venda: R$"+codVal[6][indice]+"\n\n"+
						"Deseja alterar este produto (S/N)?")).charAt(0);
				if ((confirma == 's')||(confirma == 'S')){
					prCusto = Double.parseDouble(JOptionPane.showInputDialog("Informe o novo preço de custo: R$"));
					valVenda = Double.parseDouble(JOptionPane.showInputDialog("Informe o novo valor de venda: R$"));
									
					confirma = (JOptionPane.showInputDialog( 
							"          Novos dados do produto: \n\n"+
							"Código: "+codProd+"\n"+
							"Nome do produto: "+nomeProd[indice]+"\n"+
							"Preço de custo: R$"+prCusto+"\n"+
							"Valor de venda: R$"+valVenda+"\n\n"+
							"Confirma alterações (S/N)?")).charAt(0);
					if ((confirma == 's')||(confirma == 'S')){
						codVal[5][indice] = prCusto;
						codVal[6][indice] = valVenda;
						JOptionPane.showMessageDialog(null, "" +
								"                                                             AVISO!!! \n\n"+
								"  Ao gerar relatórios, os valores são baseados nos valores atuais do produto.\n"+
								" Ou seja, os valores de venda, estoque, faturamento, etc. serão baseados nos\n"+
								"                                      valores que você acabou de confirmar.");
					}
				}
				
			}
	}
	
	private static void mostraTodos (String nomeProd[], double codVal[][], int indiceVazio){
		int i=0;
		
		
		do{
		
		JOptionPane.showMessageDialog(null, 
				"Código: "+codVal[1][i]+"\n"+
				"Nome do produto: "+nomeProd[i]+"\n"+
				"Preço de custo: R$"+codVal[5][i]+"\n"+
				"Valor de venda: R$"+codVal[6][i]+"\n"+
				"Quantidade em estoque: "+codVal[4][i]+"\n"+
				"Quantidade comprada: "+codVal[2][i]+"\n"+
				"Quantidade vendida: "+codVal[3][i]+"\n"+
				"Valor das compras do produto: R$"+(codVal[2][i]*codVal[5][i])+"\n"+
				"Valor das vendas do produto: R$"+(codVal[3][i]*codVal[6][i])+"\n"+
				"Lucro obtido com vendas deste produto: R$"+((codVal[3][i]*codVal[6][i])-(codVal[5][i]*codVal[3][i]))+"\n"+
				"Valor de custo em estoque do produto: R$"+(codVal[4][i]*codVal[5][i])+"\n"+
				"Valor de venda em estoque do produto: R$"+(codVal[4][i]*codVal[6][i]));
		
		i++;
		}while (i<indiceVazio);
	}

private static int autoCadastro(String nomeProd[], double codVal[][], int indiceVazio){
	char resp='n';
	
	resp = JOptionPane.showInputDialog(
			"                                                              AVISO!!!\n\n" +
			"                           Esta opção é destinada somente para testes!!!\n" +
			"   Os dados existentes no programa poderão ser perdidos caso continue!\n\n"+
			"                           Tem certeza de que deseja continuar (S/N)?").charAt(0);
		
	if ((resp == 's')||(resp == 'S')){
		nomeProd[0] = "Produto 1"; //Nome do produto
		codVal[1][0] = 1; //Código do produto
		codVal[2][0] = 30; //Unidades compradas do produto
		codVal[3][0] = 8; //Unidades vendidas do produto
		codVal[4][0] = (codVal[2][0]-codVal[3][0]);//Estoque atual do produto
		codVal[5][0] = 33; //Preço de custo do produto 
		codVal[6][0] = 67; //Valor de venda do produto
		codVal[7][0] = (codVal[6][0]-codVal[5][0]); //Lucro unitário do produto
		indiceVazio = (indiceVazio+1);
		
		nomeProd[1] = "Produto 2"; //Nome do produto
		codVal[1][1] = 2; //Código do produto
		codVal[2][1] = 5; //Unidades compradas do produto
		codVal[3][1] = 2; //Unidades vendidas do produto
		codVal[4][1] = (codVal[2][0]-codVal[3][0]);//Estoque atual do produto
		codVal[5][1] = 541; //Preço de custo do produto 
		codVal[6][1] = 798; //Valor de venda do produto
		codVal[7][1] = (codVal[6][0]-codVal[5][0]); //Lucro unitário do produto
		indiceVazio = (indiceVazio+1);
		
		nomeProd[2] = "Produto 3"; //Nome do produto
		codVal[1][2] = 3; //Código do produto
		codVal[2][2] = 78; //Unidades compradas do produto
		codVal[3][2] = 32; //Unidades vendidas do produto
		codVal[4][2] = (codVal[2][0]-codVal[3][0]);//Estoque atual do produto
		codVal[5][2] = 5; //Preço de custo do produto 
		codVal[6][2] = 8.5; //Valor de venda do produto
		codVal[7][2] = (codVal[6][0]-codVal[5][0]); //Lucro unitário do produto
		indiceVazio = (indiceVazio+1);
		
		nomeProd[3] = "Produto 4"; //Nome do produto
		codVal[1][3] = 4; //Código do produto
		codVal[2][3] = 27; //Unidades compradas do produto
		codVal[3][3] = 12; //Unidades vendidas do produto
		codVal[4][3] = (codVal[2][0]-codVal[3][0]);//Estoque atual do produto
		codVal[5][3] = 67; //Preço de custo do produto 
		codVal[6][3] = 96; //Valor de venda do produto
		codVal[7][3] = (codVal[6][0]-codVal[5][0]); //Lucro unitário do produto
		indiceVazio = (indiceVazio+1);
		
		nomeProd[4] = "Produto 5"; //Nome do produto
		codVal[1][4] = 5; //Código do produto
		codVal[2][4] = 78; //Unidades compradas do produto
		codVal[3][4] = 73; //Unidades vendidas do produto
		codVal[4][4] = (codVal[2][0]-codVal[3][0]);//Estoque atual do produto
		codVal[5][4] = 13; //Preço de custo do produto 
		codVal[6][4] = 28; //Valor de venda do produto
		codVal[7][4] = (codVal[6][0]-codVal[5][0]); //Lucro unitário do produto
		indiceVazio = (indiceVazio+1);
		
		nomeProd[5] = "Produto 6"; //Nome do produto
		codVal[1][5] = 6; //Código do produto
		codVal[2][5] = 0; //Unidades compradas do produto
		codVal[3][5] = 0; //Unidades vendidas do produto
		codVal[4][5] = (codVal[2][6]-codVal[3][6]);//Estoque atual do produto
		codVal[5][5] = 8; //Preço de custo do produto 
		codVal[6][5] = 56; //Valor de venda do produto
		codVal[7][5] = (codVal[6][6]-codVal[5][6]); //Lucro unitário do produto
		indiceVazio = (indiceVazio+1);
		
		nomeProd[6] = "Produto 7"; //Nome do produto
		codVal[1][6] = 10; //Código do produto
		codVal[2][6] = 12; //Unidades compradas do produto
		codVal[3][6] = 0; //Unidades vendidas do produto
		codVal[4][6] = (codVal[2][6]-codVal[3][6]);//Estoque atual do produto
		codVal[5][6] = 8; //Preço de custo do produto 
		codVal[6][6] = 56; //Valor de venda do produto
		codVal[7][6] = (codVal[6][6]-codVal[5][6]); //Lucro unitário do produto
		indiceVazio = (indiceVazio+1);
		
		JOptionPane.showMessageDialog(null, "Foram cadastrados 7 produtos no sistema!!!");
	}
		
		return indiceVazio;
	}
}
