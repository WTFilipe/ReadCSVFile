import java.lang.reflect.Constructor;import java.lang.reflect.Field;
    import java.lang.reflect.Method;
    import java.nio.charset.Charset;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.time.format.TextStyle;
    import java.util.ArrayList;
    import java.util.List;
    
    public class Leitor {
    
    	List<String> linhas;
    	String texto;
    	ArrayList<Object> objetosCriados = new ArrayList<>();
    	String[] colunas;
    	ArrayList<Method> setters;
    
    	ArrayList<Object> leArquivo(Path caminho, Charset codificacao, String separador, Class<?> classe) throws Exception {
    		// Lê e processa o arquivo
    		linhas = Files.readAllLines(caminho, codificacao);
    		colunas = linhas.get(0).split(separador);
    
    		// Crio um array para o nome dos setters
    		String[] nomeDosSetters = new String[colunas.length];
    
    		// Transformo o nome das Colunas para um SetNome
    		for (int i = 0; i < colunas.length; i++) {
    			String nomeDoMetodo;
    			nomeDoMetodo = ("set" + colunas[i].substring(0, 1).toUpperCase() + colunas[i].substring(1).toLowerCase());
    
    			nomeDosSetters[i] = nomeDoMetodo;
    		}
    
    		// Crio um Array de Objetos, será o array a ser retornado no final
    		for (int i = 1; i < linhas.size(); i++) {
    			objetosCriados.add(classe.newInstance());
    		}
    
    		// Verifico a compatibilidade entre os nomes dos campos e os metodos
    		Method[] metodos = classe.getMethods();
    		ArrayList<String> nomeDosMetodos = new ArrayList<>();
    		for (int i = 0; i < metodos.length; i++) {
    			nomeDosMetodos.add(metodos[i].getName());
    		}
    
    		// Percorro as linhas (objetos) do csv
    		for (int i = 1; i < linhas.size(); i++) {
    			String[] valores = linhas.get(i).split(separador);
    
    			// Percorro os valores de cada linha (objeto) do csv
    			for (int j = 0; j < valores.length; j++) {
    
    				try {
    					// Faço a correspondencia entre nome do metodo gerado pela planilha e
    					// nome do metodo da classe, independente do case
    					String nomeDoMetodoComoNaClasse = null;
    					for(String nome : nomeDosMetodos) {
    						if(nomeDosSetters[j].toLowerCase().equals(nome.toLowerCase())) {
    							nomeDoMetodoComoNaClasse = nome;
   						}
    					}
   					// Chamo um metodo e uso invoke para atribuir valores aos campos
    					Method setter = classe.getMethod(nomeDoMetodoComoNaClasse, String.class);
    					setter.invoke(objetosCriados.get(i - 1), valores[j]);
    
    				} catch (Exception e) {
   					throw new NoSuchFieldException("Campo " + colunas[j] + " não encontrado na classe " + classe.getName());
    				}
    
    			}
    		}
    		return objetosCriados;
    
    	}
    
    }
