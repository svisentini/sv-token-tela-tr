import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Actions {
        static void actionExit(JFrame frame) {
                frame.dispose();
        }

        static void actionCopyClipboard(JTextArea tokenArea) {
//                tokenArea.setText(environmentComboBox.getSelectedItem().toString());

                StringSelection selection = new StringSelection(tokenArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
        }
        static String actionGenerate(JTextField nameInput, JTextField passwordInput, JComboBox<String> systemComboBox, JComboBox<String> environmentComboBox, JTextArea tokenArea) throws IOException {
                tokenArea.setText("Processing .....");
                String usuarioAplicacao = nameInput.getText();
                String senhaAplicacao = passwordInput.getText();
                String sistemaAplicacao = Utils.getCodSistema(systemComboBox.getSelectedItem().toString().trim());
                String ambienteAplicacao = Utils.getEnvironment(environmentComboBox.getSelectedItem().toString().trim());


                if (usuarioAplicacao == null){
                        usuarioAplicacao = "0180500";
                }
                if (senhaAplicacao == null){
                        senhaAplicacao = "swadm123";
                }

                String url = "https://ogt-gtm-next-" + ambienteAplicacao + ".int.thomsonreuters.com/auth-service/oauth/token";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // Configurar a requisição
                con.setRequestMethod("POST");
                con.setRequestProperty("companyId", ambienteAplicacao + "1");
                con.setRequestProperty("Authorization", "Basic Y2xpZW50OjEyMw==");
                con.setRequestProperty("Cookie", "BIGipServerOSGT-DEV-NEXT-80=2768736522.20480.0000");

                // Definir o corpo da requisição
                String urlParameters = "grant_type=password&username=" + usuarioAplicacao + "&password=" + senhaAplicacao;
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(urlParameters.getBytes());
                os.flush();
                os.close();

                // Obter a resposta
                int responseCode = con.getResponseCode();
                if (! "200".equals(responseCode) ){
                        tokenArea.setText("Error : " + responseCode);
                }
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                }
                in.close();

                // Extrair o access_token da resposta
                String responseBody = response.toString();
                String accessToken = responseBody.substring(responseBody.indexOf(":\"") + 2, responseBody.indexOf("\",\"token_type"));


                // =========================================================================================

                String url2 = "https://ogt-gtm-next-" + ambienteAplicacao + ".int.thomsonreuters.com/auth-service/oauth/token";
                URL obj2 = new URL(url2);
                HttpURLConnection con2 = (HttpURLConnection) obj2.openConnection();

                // Configurar a requisição
                con2.setRequestMethod("POST");
                con2.setRequestProperty("companyId", ambienteAplicacao + "1");
                con2.setRequestProperty("Authorization", "Basic Y2xpZW50OjEyMw==");
                con2.setRequestProperty("Cookie", "BIGipServerOSGT-DEV-NEXT-80=2768736522.20480.0000");

                // Definir o corpo da requisição
                String urlParameters2 = "grant_type=access_token&username=" + usuarioAplicacao + "&password=" + senhaAplicacao + "&system_code=" + sistemaAplicacao + "&access_token=" + accessToken;
                con2.setDoOutput(true);
                OutputStream os2 = con2.getOutputStream();
                os2.write(urlParameters2.getBytes());
                os2.flush();
                os2.close();

                // Obter a resposta
                int responseCode2 = con2.getResponseCode();
                BufferedReader in2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
                String inputLine2;
                StringBuffer response2 = new StringBuffer();
                while ((inputLine2 = in2.readLine()) != null) {
                        response2.append(inputLine2);
                }
                in2.close();

                // Extrair o access_token da resposta
                String responseBody2 = response2.toString();
                String accessToken2 = responseBody2.substring(responseBody2.indexOf(":\"") + 2, responseBody2.indexOf("\",\"token_type"));

                return(accessToken2);

        }



}


