package br.com.empresa.Autenticacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws Exception {
    	//Colocando as credenciais
        String client_id = "YOUR-CLIENT-ID";
        String client_secret = "YOUR-CLIENT-SECRET";;
        String basicAuth = Base64.getEncoder().encodeToString(((client_id+':'+client_secret).getBytes()));
      
        //Diretório em que seu certificado em formato .p12 deve ser inserido
        System.setProperty("javax.net.ssl.keyStore", "certificado.p12"); 
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        
        URL url = new URL ("https://pix.api.efipay.com.br/oauth/token"); //Para ambiente de Desenvolvimento              
        HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "Basic "+ basicAuth);
        conn.setSSLSocketFactory(sslsocketfactory);
        String input = "{\"grant_type\": \"client_credentials\"}";
        
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();     

        InputStreamReader reader = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(reader);

        String response;
        while ((response = br.readLine()) != null) {
          System.out.println(response);
        }
        conn.disconnect();
      }
  
}
