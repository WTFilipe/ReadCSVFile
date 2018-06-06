import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Leitor {
	
	List<String> linhas;
	ArrayList<Object> objetosCriados;
	String[] colunas;
	Object classePassada;
	String[] nomesDosSetters;

	ArrayList<Object> leArquivo(Path caminho, Charset codificacao, String separador, Class<?> classe) throws Exception {
		// Lê e processa o arquivo
		
		linhas = Files.readAllLines( caminho, codificacao );
		colunas = linhas.get(0).split(separador);
		
		//Transformo o nome das Colunas para um SetNome
		for(int i = 0; i < colunas.length; i ++){
			nomesDosSetters[i] = "set" + colunas[i].substring(0, 1).toUpperCase() +
					colunas[i].substring(1).toLowerCase();
		}
		
		//Crio uma nova instância com o nome da classe passada
		classePassada = classe.newInstance();
		
		
		return null;
	}
	
	

}
