import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Principal {

	public static void main(String[] args) {

		ArrayList<Object> listaDeObjetos;
		Leitor classe = new Leitor();

		Path caminho = Paths.get("/home/filipe/eclipse-workspace/ReadCSVFile/src", "a.csv");
		Charset codificacao = Charset.forName("UTF-8");
		String separador = ",";
		Class<?> classeCriada = ClasseDeTeste.class;
		
		try {
			System.out.println(classe.leArquivo(caminho, codificacao, separador, classeCriada));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
