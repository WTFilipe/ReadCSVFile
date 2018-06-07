import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
	Class<?> classePassada;
	ArrayList<Method>setters;

	ArrayList<Object> leArquivo(Path caminho, Charset codificacao, String separador, ClasseDeTeste classe) throws Exception {
		// Lê e processa o arquivo
		
		linhas = Files.readAllLines( caminho, codificacao );
		colunas = linhas.get(0).split(separador);
		
		//Crio um array para o nome dos setters
		String[] nomeDosSetters = new String[colunas.length];		
		
		//Transformo o nome das Colunas para um SetNome
		for(int i = 0; i < colunas.length; i ++){
			String nomeDoMetodo;
			nomeDoMetodo = ("set" + colunas[i].substring(0, 1).toUpperCase() +
					colunas[i].substring(1).toLowerCase());
			
			nomeDosSetters[i] = nomeDoMetodo; 
		}
		
		//Crio uma nova instância com o nome da classe passada
		classePassada = classe.getClass();
		
		//Crio um Array de Objetos, será o array a ser retornado no final 
		for(int i = 1; i < linhas.size(); i++) {
			objetosCriados.add(classePassada.newInstance());
		}
		
		//Percorro as linhas (objetos) do csv
		for(int i = 1; i < linhas.size(); i++) {
			String[] valores = linhas.get(i).split(separador);
			
			//Percorro os valores de cada linha (objeto) do csv
			for(int j = 0; j < valores.length; j++) {
				//Chamo um metodo e uso invoke para atribuir valores aos campos
				Method setter2 = classePassada.getMethod(nomeDosSetters[j], String.class);
				setter2.invoke(objetosCriados.get(i-1), valores[j]);
			}
		}
		
		return objetosCriados;
	}
	
	

}