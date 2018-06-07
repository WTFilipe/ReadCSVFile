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

	ArrayList<Object> leArquivo(Path caminho, Charset codificacao, String separador, ClasseDeTeste classeCriada) throws Exception {
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
		classePassada = classeCriada.getClass();
		//Crio um array com objetos passados
		for(int i = 0; i < colunas.length; i++) {
			objetosCriados.add(classePassada.newInstance());
		}
		
		//Gero os me
		for(int i = 0; i < colunas.length; i++) {
			setters.add(classePassada.getMethod(nomeDosSetters[i], String.class));
		}
		
		return objetosCriados;
	}
	
	

}
